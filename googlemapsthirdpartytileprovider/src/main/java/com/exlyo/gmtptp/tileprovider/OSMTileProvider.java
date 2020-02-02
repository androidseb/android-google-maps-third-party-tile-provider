package com.exlyo.gmtptp.tileprovider;

import java.util.Locale;

public class OSMTileProvider extends AbstractUrlTileProvider {
	public OSMTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Override
	protected String getFormattedUrlString(final int _x, final int _y, final int _zoom) {
		return String.format(Locale.US, "https://a.tile.openstreetmap.org/%d/%d/%d.png", _zoom, _x, _y);
	}

	public float getMaxZoomLevel() {
		return 19F;
	}
}
