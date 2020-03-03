package dev.oscarreyes.popularmovies.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import dev.oscarreyes.popularmovies.R;
import dev.oscarreyes.popularmovies.api.MovieAPI;
import dev.oscarreyes.popularmovies.entity.Movie;

public class DetailActivity extends AppCompatActivity {
	private static final String TAG = DetailActivity.class.getSimpleName();

	private TextView movieTitle;
	private TextView movieDate;
	private TextView movieVote;
	private TextView movieOverview;
	private ImageView movieImage;
	private Movie movie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.detail_activity);
		this.loadViews();

		Intent intent = this.getIntent();
		String data = intent.getStringExtra(Intent.EXTRA_TEXT);

		this.movie = new Gson().fromJson(data, Movie.class);
	}

	@Override
	protected void onStart() {
		super.onStart();

		this.movieTitle.setText(this.movie.getTitle());
		this.movieDate.setText(this.movie.getReleaseDate());
		this.movieVote.setText(this.movie.getVoteCount());
		this.movieOverview.setText(this.movie.getOverview());

		this.loadImage();
	}

	private void loadViews() {
		this.movieImage = this.findViewById(R.id.movie_detail_image);
		this.movieTitle = this.findViewById(R.id.movie_detail_title);
		this.movieDate = this.findViewById(R.id.movie_detail_date);
		this.movieVote = this.findViewById(R.id.movie_detail_vote);
		this.movieOverview = this.findViewById(R.id.movie_detail_overview);
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

	public void like(View view) {
		Log.d(TAG, "Like this movie");
	}
}
