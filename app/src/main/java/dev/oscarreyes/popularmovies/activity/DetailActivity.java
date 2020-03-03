package dev.oscarreyes.popularmovies.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import dev.oscarreyes.popularmovies.R;
import dev.oscarreyes.popularmovies.api.MovieAPI;
import dev.oscarreyes.popularmovies.database.MovieDatabase;
import dev.oscarreyes.popularmovies.database.model.MovieRow;
import dev.oscarreyes.popularmovies.entity.Movie;
import dev.oscarreyes.popularmovies.util.AppExecutor;
import dev.oscarreyes.popularmovies.viewmodel.MovieDetailsViewModel;
import dev.oscarreyes.popularmovies.viewmodel.MovieDetailsViewModelFactory;

public class DetailActivity extends AppCompatActivity {
	private static final String TAG = DetailActivity.class.getSimpleName();

	private TextView movieTitle;
	private TextView movieDate;
	private TextView movieVote;
	private TextView movieOverview;
	private ToggleButton movieFavorite;
	private ImageView movieImage;
	private Movie movie;
	private MovieDatabase movieDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.detail_activity);
		this.loadViews();

		Intent intent = this.getIntent();
		String data = intent.getStringExtra(Intent.EXTRA_TEXT);

		this.movie = new Gson().fromJson(data, Movie.class);
		this.movieDatabase = MovieDatabase.getInstance(this);
	}

	@Override
	protected void onStart() {
		super.onStart();

		this.movieTitle.setText(this.movie.getTitle());
		this.movieDate.setText(this.movie.getReleaseDate());
		this.movieVote.setText(this.movie.getVoteCount());
		this.movieOverview.setText(this.movie.getOverview());

		this.loadImage();
		this.setupLikeToggle();
	}

	private void loadViews() {
		this.movieImage = this.findViewById(R.id.movie_detail_image);
		this.movieTitle = this.findViewById(R.id.movie_detail_title);
		this.movieDate = this.findViewById(R.id.movie_detail_date);
		this.movieVote = this.findViewById(R.id.movie_detail_vote);
		this.movieOverview = this.findViewById(R.id.movie_detail_overview);
		this.movieFavorite = this.findViewById(R.id.movie_detail_button_favorite);
	}

	private void loadImage() {
		final int imageWidth = 185;

		final String url = String.format(
			"https://%s/t/p/w%d/%s"
			, MovieAPI.IMAGE_HOST, imageWidth, this.movie.getPosterPath()
		);

		Picasso.with(this)
			.load(url)
			.into(this.movieImage);
	}

	private void makeFavorite() {
		Log.d(TAG, "Making current movie as favorite");

		MovieRow movieRow = MovieRow.fromEntityAPI(this.movie);

		AppExecutor.getInstance()
			.getDiskIO()
			.execute(() -> this.movieDatabase.movieDao().insertMovie(movieRow));
	}

	private void removeFavorite() {
		Log.d(TAG, "Removing current movie from favorite");

		AppExecutor.getInstance()
			.getDiskIO()
			.execute(() -> this.movieDatabase.movieDao().deleteMovie(this.movie.getId()));
	}

	private void setupLikeToggle() {
		final MovieDetailsViewModelFactory factory = new MovieDetailsViewModelFactory(this.movieDatabase, this.movie.getId());
		final MovieDetailsViewModel viewModel = ViewModelProviders.of(this, factory)
			.get(MovieDetailsViewModel.class);

		viewModel.getMovie().observe(this, movieRow -> {
			this.movieFavorite.setChecked(movieRow != null);
		});
	}

	public void toggleLike(View view) {
		final boolean isChecked = this.movieFavorite.isChecked();

		if (isChecked) {
			makeFavorite();
		} else {
			removeFavorite();
		}
	}
}
