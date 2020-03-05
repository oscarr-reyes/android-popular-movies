package dev.oscarreyes.popularmovies.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class Youtube {
	private static final String APP_BASE_URI = "vnd.youtube";
	private static final String WEB_BASE_URI = "http://www.youtube.com/watch?v=";

	public static void watch(Context context, String key) {
		Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s:%s", APP_BASE_URI, key)));
		Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s%s", WEB_BASE_URI, key)));

		try {
			context.startActivity(appIntent);
		} catch (ActivityNotFoundException e) {
			context.startActivity(webIntent);
		}
	}
}
