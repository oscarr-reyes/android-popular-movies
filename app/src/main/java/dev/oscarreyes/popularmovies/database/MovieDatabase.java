package dev.oscarreyes.popularmovies.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dev.oscarreyes.popularmovies.database.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
	private static final String DATABASE_NAME = "moviedb";

	private static MovieDatabase instance;

	public static MovieDatabase getInstance(Context context) {
		if (instance == null) {
			Context applicationContext = context.getApplicationContext();
			instance = Room.databaseBuilder(applicationContext, MovieDatabase.class, DATABASE_NAME)
				.build();
		}

		return instance;
	}
}
