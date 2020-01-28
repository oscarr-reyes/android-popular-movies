package dev.oscarreyes.popularmovies.io;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HTTP {
	private static final String[] SUPPORTED_SCHEMES = new String[]{"http", "https"};
	private static final int REQUEST_TIMEOUT = 5000;

	private Map<String, String> parameters = new HashMap<>();
	private Map<String, String> headers = new HashMap<>();
	private HttpURLConnection connection;
	private RequestMethod requestMethod;
	private Uri uri;

	/**
	 * Creates a HTTP with empty properties
	 */
	public HTTP() {
	}

	/**
	 * Creates a HTTP instance with a url
	 * @param url The url where to make the request
	 * @throws Exception If provided url is not supported
	 */
	public HTTP(String url) throws Exception {
		this.setUrl(url);
		this.setRequestMethod(RequestMethod.GET);
	}

	/**
	 * Creates a HTTP instance with url and request method
	 * @param url The url where to make the request
	 * @param method The method on which the request will make
	 * @throws Exception If the provided url is not supported
	 */
	public HTTP(String url, RequestMethod method) throws Exception {
		this.setUrl(url);
		this.setRequestMethod(method);
	}

	/**
	 * Sets the method request that will be used
	 * @param requestMethod The method for the request
	 */
	public void setRequestMethod(RequestMethod requestMethod) {
		this.requestMethod = requestMethod;
	}

	/**
	 * Gets the http query parameters
	 * @return The map of query parameters for the request
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Gets the http headers
	 * @return The map of headers for the request
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Sets the url for the request
	 * @param url The url that will be used for the request
	 * @throws Exception If the provided url is not supported
	 */
	public void setUrl(String url) throws Exception {
		final Uri uri = Uri.parse(url);
		final String scheme = uri.getScheme();

		if (!Arrays.asList(SUPPORTED_SCHEMES).contains(scheme)) {
			throw new Exception("Url protocol is not supported");
		}

		this.uri = uri;
	}

	/**
	 * Sends the request to the target host with the defined settings
	 * @return The buffer data response
	 * @throws Exception If the request is not accepted
	 */
	public BufferedReader send() throws Exception {
		this.prepareConnection();

		if (this.connection.getResponseCode() != 200) {
			final String responseMessage = this.connection.getResponseMessage();

			throw new IOException(responseMessage);
		}

		return new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
	}

	/**
	 * Prepares the connection instance for the http request
	 * @throws Exception If the required values are not set in the HTTP instance
	 */
	private void prepareConnection() throws Exception {
		if (this.requestMethod == null) {
			throw new Exception("Cannot send request without defining a method");
		} else if (this.uri == null) {
			throw new Exception("Cannot send request without defining a url");
		}

		final URL url = new URL(this.getFullyQualifiedURL());

		this.connection = (HttpURLConnection) url.openConnection();
		this.connection.setConnectTimeout(REQUEST_TIMEOUT);
		this.connection.setRequestMethod(this.requestMethod.name());
		this.connection.setUseCaches(false);                                       // We don't need to cache response
		this.connection.setDoInput(true);
		this.connection.setDoOutput(this.shouldOutput());                          // We only have to output if the request requires it

		this.prepareHeaders();
	}

	/**
	 * Resolves whether the connection request has to send data
	 * @return Whether the connection should do output
	 */
	private boolean shouldOutput() {
		return this.requestMethod == RequestMethod.POST;
	}

	/**
	 * Prepares the headers for the connection request
	 * @throws Exception If the connection is not instantiated
	 */
	private void prepareHeaders() throws Exception {
		if (this.connection == null) {
			throw new Exception("Unable to set headers on undefined connection");
		}

		Set<Map.Entry<String, String>> entries = this.headers.entrySet();

		for (Map.Entry<String, String> entry : entries) {
			final String key = entry.getKey();
			final String value = entry.getValue();

			this.connection.setRequestProperty(key, value);
		}
	}

	/**
	 * Resolves the fully qualified URL using the scheme, authority and query parameters
	 * @return The full URL for the request
	 */
	private String getFullyQualifiedURL() {
		Set<Map.Entry<String, String>> entries = this.parameters.entrySet();
		Uri.Builder builder = new Uri.Builder();

		builder = builder.scheme(this.uri.getScheme());
		builder = builder.authority(this.uri.getAuthority());
		builder = builder.path(this.uri.getPath());

		for (Map.Entry<String, String> entry : entries) {
			String key = entry.getKey();
			String value = entry.getValue();

			builder = builder.appendQueryParameter(key, value);
		}

		return builder.build().toString();
	}

	/**
	 * A list of accepted methods for HTTP
	 */
	public enum RequestMethod {
		GET, POST
	}

	public static class HttpResponse {
		private String body;
		private int status;

		public HttpResponse(String body, int status) {
			this.body = body;
			this.status = status;
		}

		public String getBody() {
			return body;
		}

		public int getStatus() {
			return status;
		}
	}

	public interface CallbackResponse {
		void response(HttpResponse httpResponse);
	}
}
