package dev.oscarreyes.popularmovies.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import dev.oscarreyes.popularmovies.database.model.MovieRow;

@Dao
public interface MovieDao {
	@Insert(onConflict = OnConflictStrategy.IGNORE)
	void insertMovie(MovieRow movieRow);

	@Query("DELETE FROM movie WHERE api_id = :apiId")
	void deleteMovie(int apiId);
}
