package dev.oscarreyes.popularmovies.api;

import java.util.List;

import dev.oscarreyes.popularmovies.entity.Review;
import dev.oscarreyes.popularmovies.entity.Video;

public class ReviewCollection {
	private int id;
	private int page;
	private List<Review> results;

	ReviewCollection() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Review> getResults() {
		return results;
	}

	public void setResults(List<Review> results) {
		this.results = results;
	}
}
