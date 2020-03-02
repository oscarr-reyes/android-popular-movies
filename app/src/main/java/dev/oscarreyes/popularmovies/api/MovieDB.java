package dev.oscarreyes.popularmovies.api;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.Objects;

import dev.oscarreyes.popularmovies.io.HTTP;

public class MovieDB {
	private static final String TAG = MovieDB.class.getSimpleName();
	private static final String QUERY_API_KEY = "api_key";

	public static final String API_HOST = "api.themoviedb.org";
	public static final String IMAGE_HOST = "image.tmdb.org";
	public static final String VERSION = "3";

	private static String apiKey;

	public static void setApiKey(String key) {
		apiKey = key;
	}

	public static void getPopular(APIResult apiResult) throws Exception {
		Log.d(TAG, "Requesting popular movies");

		final String path = "movie/popular";

		sendRequest(path, httpResponse -> {
			Gson gson = new Gson();

			MovieCollection collection = gson.fromJson(httpResponse.getBody(), MovieCollection.class);

			apiResult.result(collection);
		});
	}

	public static void getRated(APIResult apiResult) throws Exception {
		Log.d(TAG, "Requesting top rated movies");

		final String path = "movie/top_rated";

		sendRequest(path, httpResponse -> {
			Gson gson = new Gson();

			MovieCollection collection = gson.fromJson(httpResponse.getBody(), MovieCollection.class);

			apiResult.result(collection);
		});
	}

	private static void sendRequest(String path, HTTP.CallbackResponse callbackResponse) throws Exception {
		if (apiKey == null) {
			throw new Exception("API Key is not set for MovieDB");
		}

		final Uri uri = new Uri.Builder()
			.scheme("https")
			.authority(API_HOST)
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

	public interface APIResult {
		void result(MovieCollection collection);
	}
}
