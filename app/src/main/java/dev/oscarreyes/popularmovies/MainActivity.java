package dev.oscarreyes.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.util.Objects;

import dev.oscarreyes.popularmovies.io.HTTP;

/*
 * Common project requirements
 */
// TODO: Replace ConstraintLayout with a GridLayout in the main activity view
// TODO: Add a spinner in the main view for loading feedback
// TODO: Create Detail activity for selecting a movie item
// TODO: Add title, release date, movie poster, vote average, and plot synopsis for Detail activity

/*
 * User interface requirements
 */
// TODO: View gets updated correctly when an user changes the sort criteria
// TODO: Movie details is started when a poster thumbnail is selected

/*
 * Network API implementation
 */
// TODO: app queries the /movie/popular or /movie/top_rated API for the sort criteria in settings menu

public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String[] PERMISSIONS = new String[]{Manifest.permission.INTERNET};
	private static final int PERMISSION_CODE = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.checkPermissions();
	}

	public void onClick(View view) {
		new AsyncRequest().execute("https://jsonplaceholder.typicode.com/todos/1");
	}

	private void checkPermissions() {
		boolean granted = true;

		for (String permission : PERMISSIONS) {
			if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
				granted = false;
			}
		}

		if (!granted) {
			this.requestPermissions(PERMISSIONS, PERMISSION_CODE);
		}
	}

	public class AsyncRequest extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... strings) {
			String url = strings[0];
			StringBuilder stringBuilder = new StringBuilder();

			try {
				HTTP http = new HTTP(url, HTTP.RequestMethod.GET);

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
			Log.i(TAG, String.format("Response: %s", s));
		}
	}

}
