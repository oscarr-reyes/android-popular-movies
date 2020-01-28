package dev.oscarreyes.popularmovies.api;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.util.Objects;

import dev.oscarreyes.popularmovies.io.HTTP;

public class MovieDB {
	private static final String TAG = MovieDB.class.getSimpleName();
	private static final String HOST = "api.themoviedb.org";
	private static final String VERSION = "3";
	private static final String QUERY_API_KEY = "api_key";

	private static String apiKey;

	public static void setApiKey(String key) {
		apiKey = key;
	}

	public static void getPopular(final HTTP.CallbackResponse callbackResponse) throws Exception {
		final String path = "movie/popular";

		sendRequest(path, callbackResponse);

	}

	public static void getRated(HTTP.CallbackResponse callbackResponse) throws Exception {
		final String path = "movie/top_rated";

		sendRequest(path, callbackResponse);
	}

	private static void sendRequest(String path, HTTP.CallbackResponse callbackResponse) throws Exception {
		if (apiKey == null) {
			throw new Exception("API Key is not set for MovieDB");
		}

		final Uri uri = new Uri.Builder()
			.scheme("https")
			.authority(HOST)
			.appendPath(VERSION)
			.appendPath(path)
			.build();

		new AsyncTask<String, Void, String>() {
			@Override
			protected String doInBackground(String... strings) {
				String url = strings[0];
				StringBuilder stringBuilder = new StringBuilder();

				try {
					HTTP http = new HTTP(url, HTTP.RequestMethod.GET);

					http.getParameters().put(QUERY_API_KEY, apiKey);

					final BufferedReader reader = http.send();

					String chunk;

					while ((chunk = reader.readLine()) != null) {
						stringBuilder.append(chunk);
					}

					reader.close();
				} catch (Exception e) {
					Log.e(TAG, Objects.requireNonNull(e.getMessage()));

					e.printStackTrace();
				}

				return stringBuilder.toString();
			}

			@Override
			protected void onPostExecute(String s) {
				callbackResponse.response(new HTTP.HttpResponse(s, 0));
			}
		}.execute(uri.toString());
	}
}
