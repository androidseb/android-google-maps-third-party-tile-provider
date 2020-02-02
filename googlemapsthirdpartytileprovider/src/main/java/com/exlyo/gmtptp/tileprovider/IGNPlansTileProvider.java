package com.exlyo.gmtptp.tileprovider;

public class IGNPlansTileProvider extends AbstractIGNTileProvider {
	public IGNPlansTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Override
	protected String getUrlSuffixString() {
		return "LAYER=GEOGRAPHICALGRIDSYSTEMS.PLANIGN&FORMAT=image/jpeg";
	}

	public float getMaxZoomLevel() {
		return 18F;
	}
}
