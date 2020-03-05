package dev.oscarreyes.popularmovies.api;

import java.util.List;

import dev.oscarreyes.popularmovies.entity.Video;

public class VideoCollection {
	private int id;
	private List<Video> results;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Video> getResults() {
		return results;
	}

	public void setResults(List<Video> results) {
		this.results = results;
	}
}
