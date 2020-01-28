package dev.oscarreyes.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.util.Objects;

import dev.oscarreyes.popularmovies.api.MovieCollection;
import dev.oscarreyes.popularmovies.api.MovieDB;
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

	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.checkPermissions();

		final String apiKey = this.getString(R.string.moviedb_api_key);

		MovieDB.setApiKey(apiKey);

		this.loadViews();
	}

	@Override
	protected void onStart() {
		super.onStart();

		this.fetchMovieCollection();
	}

	private void fetchMovieCollection() {
		try {
			MovieDB.getRated((MovieDB.APIResult) collection -> {
				Log.d(TAG, String.valueOf(collection.getPage()));

				this.progressBar.setVisibility(View.INVISIBLE);
			});
		} catch (Exception e) {
			Log.w(TAG, Objects.requireNonNull(e.getMessage()));

			e.printStackTrace();
		}
	}

	private void loadViews() {
		this.progressBar = this.findViewById(R.id.progressBar);
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
}
