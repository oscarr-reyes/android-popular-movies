package dev.oscarreyes.popularmovies.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
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
import dev.oscarreyes.popularmovies.util.SearchCriteria;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String[] PERMISSIONS = new String[]{Manifest.permission.INTERNET};
	private static final int PERMISSION_CODE = 101;

	private SearchCriteria selectedCriteria = SearchCriteria.TOP_RATED;

	private ActionBar actionBar;
	private ProgressBar progressBar;
	private RecyclerView moviesRecycler;
	private Menu menu;

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

		this.actionBar = this.getSupportActionBar();
	}

	@Override
	protected void onStart() {
		super.onStart();

		this.fetchMovieCollection();

		this.setToolbarTitle();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.menu = menu;

		this.getMenuInflater().inflate(R.menu.main_menu, this.menu);

		this.setMenuItemTitle();

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		this.progressBar.setVisibility(View.VISIBLE);
		this.moviesRecycler.setVisibility(View.INVISIBLE);

		if (this.selectedCriteria == SearchCriteria.TOP_RATED) {
			this.selectedCriteria = SearchCriteria.POPULAR;
		} else {
			this.selectedCriteria = SearchCriteria.TOP_RATED;
		}

		this.setMenuItemTitle();
		this.fetchMovieCollection();

		return true;
	}

	private void fetchMovieCollection() {
		try {
			if(this.selectedCriteria == SearchCriteria.TOP_RATED) {
				MovieDB.getRated(this::dataResponse);
			} else {
				MovieDB.getPopular(this::dataResponse);
			}
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

	private void setMenuItemTitle() {
		MenuItem menuItem = this.menu.findItem(R.id.action_switch_movie_criteria);

		if (this.selectedCriteria == SearchCriteria.TOP_RATED) {
			menuItem.setTitle(R.string.menu_switch_popular);
		} else {
			menuItem.setTitle(R.string.menu_switch_top_rated);
		}

		this.setToolbarTitle();
	}

	private void dataResponse(MovieCollection collection) {
		this.progressBar.setVisibility(View.INVISIBLE);
		this.moviesRecycler.setVisibility(View.VISIBLE);
		this.setupAdapter(collection);
	}

	private void setToolbarTitle() {
		if (this.selectedCriteria == SearchCriteria.TOP_RATED) {
			this.actionBar.setTitle(R.string.menu_switch_top_rated);
		} else {
			this.actionBar.setTitle(R.string.menu_switch_popular);
		}
	}
}
