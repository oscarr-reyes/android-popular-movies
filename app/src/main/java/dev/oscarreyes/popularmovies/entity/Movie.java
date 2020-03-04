package dev.oscarreyes.popularmovies.entity;

import com.google.gson.annotations.SerializedName;

import dev.oscarreyes.popularmovies.database.model.MovieRow;

public class Movie {
	private int id;
	private String title;
	private String overview;

	@SerializedName("poster_path")
	private String posterPath;

	@SerializedName("vote_count")
	private String voteCount;

	@SerializedName("release_date")
	private String releaseDate;

	public Movie() {
	}

	public Movie(MovieRow movieRow) {
		this.id = movieRow.getApiId();
		this.title = movieRow.getTitle();
		this.overview = movieRow.getOverview();
		this.posterPath = movieRow.getPosterPath();
		this.voteCount = movieRow.getVoteCount();
		this.releaseDate = movieRow.getReleaseDate();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public String getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(String voteCount) {
		this.voteCount = voteCount;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
}
