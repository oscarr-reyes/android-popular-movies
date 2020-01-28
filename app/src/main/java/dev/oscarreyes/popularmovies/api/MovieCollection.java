package dev.oscarreyes.popularmovies.api;

import java.util.List;

import dev.oscarreyes.popularmovies.entity.Movie;

public class MovieCollection {
	private int page;
	private int totalResults;
	private int totalPages;
	private List<Movie> results;

	MovieCollection() {
	}

	void setPage(int page) {
		this.page = page;
	}

	void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	void setResults(List<Movie> results) {
		this.results = results;
	}

	public int getPage() {
		return page;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public List<Movie> getResults() {
		return results;
	}
}
