package dev.oscarreyes.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.oscarreyes.popularmovies.R;
import dev.oscarreyes.popularmovies.api.MovieCollection;
import dev.oscarreyes.popularmovies.entity.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
	private final MovieCollection movieCollection;


	public MovieAdapter(MovieCollection movieCollection) {
		this.movieCollection = movieCollection;
	}

	@NonNull
	@Override
	public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View view = layoutInflater.inflate(R.layout.movie_list_item, parent, false);

		return new MovieViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
		final Movie movie = this.movieCollection.getResults().get(position);

		holder.bind(movie);
	}

	@Override
	public int getItemCount() {
		return this.movieCollection.getResults().size();
	}

	class MovieViewHolder extends RecyclerView.ViewHolder {
		private TextView title;

		public MovieViewHolder(@NonNull View itemView) {
			super(itemView);

			this.title = itemView.findViewById(R.id.movie_item_title);
		}

		void bind(Movie movie) {
			this.title.setText(movie.getTitle());
		}
	}
}
