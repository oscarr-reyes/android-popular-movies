package dev.oscarreyes.popularmovies.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import dev.oscarreyes.popularmovies.database.MovieDatabase;
import dev.oscarreyes.popularmovies.database.model.MovieRow;

public class MovieDetailsViewModel extends ViewModel {
	private LiveData<MovieRow> movieRowLiveData;

	public MovieDetailsViewModel(MovieDatabase database, int apiId) {
		this.movieRowLiveData = database.movieDao()
			.findMovieByApiId(apiId);
	}

	public LiveData<MovieRow> getMovie() {
		return movieRowLiveData;
	}
}
