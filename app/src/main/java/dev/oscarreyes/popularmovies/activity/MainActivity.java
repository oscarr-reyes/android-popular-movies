package dev.oscarreyes.popularmovies.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.Objects;

import dev.oscarreyes.popularmovies.R;
import dev.oscarreyes.popularmovies.adapter.MovieAdapter;
import dev.oscarreyes.popularmovies.api.MovieCollection;
import dev.oscarreyes.popularmovies.api.MovieDB;
import dev.oscarreyes.popularmovies.entity.Movie;

/*
 * Common project requirements
 */
// TODO: Replace ConstraintLayout with a GridLayout in the main activity view
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
	private RecyclerView moviesRecycler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.checkPermissions();

		final String apiKey = this.getString(R.string.moviedb_api_key);

		MovieDB.setApiKey(apiKey);

		this.loadViews();

		GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

		layoutManager.setOrientation(RecyclerView.VERTICAL);

		this.moviesRecycler.setLayoutManager(layoutManager);
		this.moviesRecycler.setHasFixedSize(true);
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
				this.setupAdapter(collection);
			});
		} catch (Exception e) {
			Log.w(TAG, Objects.requireNonNull(e.getMessage()));

			e.printStackTrace();
		}
	}

	private void setupAdapter(MovieCollection movieCollection) {
		MovieAdapter movieAdapter = new MovieAdapter(movieCollection, index -> {
			final Gson gson = new Gson();
			final Movie movie = movieCollection.getResults().get(index);

			this.transitionToDetail(gson.toJson(movie));
		});

		this.moviesRecycler.setAdapter(movieAdapter);
	}

	private void transitionToDetail(String data) {
		Intent intent = new Intent(this, DetailActivity.class);

		intent.putExtra(Intent.EXTRA_TEXT, data);

		this.startActivity(intent);
	}

	private void loadViews() {
		this.moviesRecycler = this.findViewById(R.id.rv_movies);
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
