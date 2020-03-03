package dev.oscarreyes.popularmovies.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import dev.oscarreyes.popularmovies.entity.Movie;

@Entity(tableName = "movie")
public class MovieRow {
	@PrimaryKey(autoGenerate = true)
	private int id;

	@ColumnInfo(name = "api_id")
	private int apiId;

	@ColumnInfo(name = "poster_path")
	private String posterPath;

	@ColumnInfo(name = "vote_count")
	private String voteCount;

	@ColumnInfo(name = "release_data")
	private String releaseDate;

	private String title;
	private String overview;

	@Ignore
	public MovieRow() {
	}

	public MovieRow(int id, int apiId, String title, String overview){
		this.id = id;
		this.apiId = apiId;
		this.title = title;
		this.overview = overview;
	}

	public int getId() {
		return id;
	}

	public int getApiId() {
		return apiId;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public String getTitle() {
		return title;
	}

	public String getOverview() {
		return overview;
	}

	public String getVoteCount() {
		return voteCount;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setApiId(int apiId) {
		this.apiId = apiId;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public void setVoteCount(String voteCount) {
		this.voteCount = voteCount;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public static MovieRow fromEntityAPI(Movie movie) {
		MovieRow movieRow = new MovieRow();

		movieRow.apiId = movie.getId();
		movieRow.posterPath = movie.getPosterPath();
		movieRow.title = movie.getTitle();
		movieRow.overview = movie.getOverview();
		movieRow.voteCount = movie.getVoteCount();
		movieRow.releaseDate = movie.getReleaseDate();

		return movieRow;
	}
}
