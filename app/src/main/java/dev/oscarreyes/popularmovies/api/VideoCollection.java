package dev.oscarreyes.popularmovies.api;

import java.util.List;

import dev.oscarreyes.popularmovies.entity.Review;

public class VideoCollection {
	private int id;
	private List<Review> results;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Review> getResults() {
		return results;
	}

	public void setResults(List<Review> results) {
		this.results = results;
	}
}
