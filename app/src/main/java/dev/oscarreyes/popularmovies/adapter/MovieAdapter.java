package dev.oscarreyes.popularmovies.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import dev.oscarreyes.popularmovies.R;
import dev.oscarreyes.popularmovies.api.MovieCollection;
import dev.oscarreyes.popularmovies.api.MovieDB;
import dev.oscarreyes.popularmovies.entity.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
	private static final String TAG = MovieAdapter.class.getSimpleName();

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
		private ImageView image;
		private boolean imageLoaded = false;

		public MovieViewHolder(@NonNull View itemView) {
			super(itemView);

			this.title = itemView.findViewById(R.id.movie_item_title);
			this.image = itemView.findViewById(R.id.movie_item_poster);
		}

		void bind(Movie movie) {
			this.title.setText(movie.getTitle());

			if (!this.imageLoaded) {
				final Context context = this.itemView.getContext();
				final int imageWidth = 185;

				final String url = String.format(
					"https://%s/t/p/w%d/%s"
					, MovieDB.IMAGE_HOST, imageWidth, movie.getPosterPath()
				);

				Log.d(TAG, String.format("Download image: %s", url));

				Picasso.with(context)
					.load(url)
					.into(this.image, new Callback() {
						@Override
						public void onSuccess() {
							Log.i(TAG, "image loaded");
						}

						@Override
						public void onError() {
							Log.w(TAG, String.format("Unable to load image for id: %d", movie.getId()));
						}
					});
			}
		}
	}
}
