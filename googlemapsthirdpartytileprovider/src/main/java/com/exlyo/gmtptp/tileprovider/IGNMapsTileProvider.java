package com.exlyo.gmtptp.tileprovider;

public class IGNMapsTileProvider extends AbstractIGNTileProvider {
	public IGNMapsTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Override
	protected String getUrlSuffixString() {
		return "LAYER=GEOGRAPHICALGRIDSYSTEMS.MAPS&FORMAT=image/jpeg";
	}

	public float getMaxZoomLevel() {
		return 18F;
	}
}
