package dev.oscarreyes.popularmovies.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;

import dev.oscarreyes.popularmovies.R;
import dev.oscarreyes.popularmovies.entity.Movie;

public class DetailActivity extends AppCompatActivity {

	Movie movie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity);

		Intent intent = this.getIntent();
		String data = intent.getStringExtra(Intent.EXTRA_TEXT);

		this.movie = new Gson().fromJson(data, Movie.class);
	}
}
