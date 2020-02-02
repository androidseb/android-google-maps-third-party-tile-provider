package com.exlyo.gmtptp.tileprovider;

import java.util.Locale;

public class ArcGISServerTileProvider extends AbstractUrlTileProvider {
	public ArcGISServerTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Override
	protected String getFormattedUrlString(final int _x, final int _y, final int _zoom) {
		return String
			.format(Locale.US, "https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/%d/%d/%d.png", _zoom,
				_y, _x);
	}

	public float getMaxZoomLevel() {
		return 19F;
	}
}
