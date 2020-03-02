package dev.oscarreyes.popularmovies.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class Movie implements Parcelable {
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

	private Movie(Parcel parcel) {
		this.apiId = parcel.readInt();
		this.posterPath = parcel.readString();
		this.voteCount = parcel.readString();
		this.releaseDate = parcel.readString();
		this.title = parcel.readString();
		this.overview = parcel.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.apiId);
		dest.writeString(this.posterPath);
		dest.writeString(this.voteCount);
		dest.writeString(this.releaseDate);
		dest.writeString(this.title);
		dest.writeString(this.overview);
	}

	public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
	};
}
