package dev.oscarreyes.popularmovies.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dev.oscarreyes.popularmovies.database.MovieDatabase;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
	private final MovieDatabase database;
	private final int apiId;

	public MovieDetailsViewModelFactory(MovieDatabase database, int apiId) {
		this.database = database;
		this.apiId = apiId;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new MovieDetailsViewModel(this.database, this.apiId);
	}
}
