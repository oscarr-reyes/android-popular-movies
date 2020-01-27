package dev.oscarreyes.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
