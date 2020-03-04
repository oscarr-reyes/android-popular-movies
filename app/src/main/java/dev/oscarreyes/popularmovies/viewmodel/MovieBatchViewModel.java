package dev.oscarreyes.popularmovies.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.oscarreyes.popularmovies.database.MovieDatabase;
import dev.oscarreyes.popularmovies.database.model.MovieRow;

public class MovieBatchViewModel extends AndroidViewModel {

	private LiveData<MovieRow[]> movies;

	public MovieBatchViewModel(@NonNull Application application) {
		super(application);

		MovieDatabase database = MovieDatabase.getInstance(this.getApplication());

		this.movies = database.movieDao().findAllMovies();
	}

	public LiveData<MovieRow[]> getMovies() {
		return this.movies;
	}
}
