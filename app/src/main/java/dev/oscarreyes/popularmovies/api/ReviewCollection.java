package dev.oscarreyes.popularmovies.api;

import java.util.List;

import dev.oscarreyes.popularmovies.entity.Video;

public class ReviewCollection {
	private int id;
	private int page;
	private List<Video> results;

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

	public List<Video> getResults() {
		return results;
	}

	public void setResults(List<Video> results) {
		this.results = results;
	}
}
