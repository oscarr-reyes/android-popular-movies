package dev.oscarreyes.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import dev.oscarreyes.popularmovies.R;
import dev.oscarreyes.popularmovies.api.ReviewCollection;
import dev.oscarreyes.popularmovies.entity.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
	private static final String TAG = ReviewAdapter.class.getSimpleName();

	private final ReviewCollection reviewCollection;

	public ReviewAdapter(ReviewCollection reviewCollection) {
		this.reviewCollection = reviewCollection;
	}

	@NonNull
	@Override
	public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View view = layoutInflater.inflate(R.layout.review_list_item, parent, false);

		return new ReviewViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
		final Review review = this.reviewCollection.getResults().get(position);

		holder.bind(review);
	}

	@Override
	public int getItemCount() {
		return this.reviewCollection.getResults().size();
	}

	class ReviewViewHolder extends RecyclerView.ViewHolder {
		private TextView reviewAuthor;
		private TextView reviewContent;

		public ReviewViewHolder(@NonNull View itemView) {
			super(itemView);

			this.reviewAuthor = itemView.findViewById(R.id.review_item_author);
			this.reviewContent = itemView.findViewById(R.id.review_item_content);
		}

		void bind(Review review) {
			this.reviewAuthor.setText(review.getAuthor());
			this.reviewContent.setText(review.getContent());
		}
	}
}
