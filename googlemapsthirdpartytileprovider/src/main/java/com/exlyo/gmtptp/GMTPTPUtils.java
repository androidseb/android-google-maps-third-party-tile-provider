package com.exlyo.gmtptp;

import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GMTPTPUtils {
	public static String inputStreamToString(final InputStream _is) throws Throwable {
		final StringBuilder sb = new StringBuilder();
		final BufferedReader br = new BufferedReader(new InputStreamReader(_is));
		String line = br.readLine();
		if (line != null) {
			sb.append(line);
			line = br.readLine();
		}
		while (line != null) {
			sb.append("\n");
			sb.append(line);
			line = br.readLine();
		}
		return sb.toString();
	}

	public static String urlToString(final String _rawUrlString) throws Throwable {
		final URL url = new URL(_rawUrlString);
		final URLConnection urlConnection = url.openConnection();
		final InputStream inputStream = urlConnection.getInputStream();
		final String res;
		try {
			res = inputStreamToString(inputStream);
		} finally {
			inputStream.close();
		}
		return res;
	}

	public static boolean isStringNullOrEmpty(final String _str) {
		return _str == null || "".equals(_str);
	}

	public static byte[] bitmapToPngByteArray(final Bitmap _bitmap) {
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		_bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}
}
