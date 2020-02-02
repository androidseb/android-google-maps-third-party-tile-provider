package com.exlyo.gmtptp.tileprovider;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public abstract class AbstractUrlTileProvider extends AbstractTileProvider {
	private static final int TILE_WIDTH = 256;
	private static final int TILE_HEIGHT = 256;

	AbstractUrlTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Nullable
	protected String getUserAgentString() {
		return null;
	}

	private URL getTileUrl(final int _x, final int _y, final int _zoom) {
		try {
			return new URL(getFormattedUrlString(_x, _y, _zoom));
		} catch (MalformedURLException t) {
			t.printStackTrace();
			return null;
		}
	}

	protected abstract String getFormattedUrlString(final int _x, final int _y, final int _zoom);

	@Override
	public final Tile getTile(int _x, int _y, int _zoom) {
		final String userAgentString = getUserAgentString();
		final URL tileUrl = this.getTileUrl(_x, _y, _zoom);
		if (tileUrl == null) {
			return NO_TILE;
		} else {
			Tile resultTile;
			try {
				final URLConnection urlConnection = tileUrl.openConnection();
				if (userAgentString != null) {
					urlConnection.setRequestProperty("User-Agent", userAgentString);
				}
				final InputStream inputStream = urlConnection.getInputStream();
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buffer = new byte[4096];

				int read;
				while ((read = inputStream.read(buffer)) != -1) {
					baos.write(buffer, 0, read);
				}

				resultTile = new Tile(TILE_WIDTH, TILE_HEIGHT, baos.toByteArray());
			} catch (IOException exception) {
				resultTile = null;
			}

			return resultTile;
		}
	}
}
