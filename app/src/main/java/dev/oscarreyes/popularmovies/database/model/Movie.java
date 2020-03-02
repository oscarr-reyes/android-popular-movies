package dev.oscarreyes.popularmovies.database.model;

import android.os.Parcel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class Movie {
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

	public Movie(int id, int apiId, String title, String overview){
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
}
