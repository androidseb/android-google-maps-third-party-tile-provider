package com.exlyo.gmtptp.tileprovider;

public class BingMapsRoadTileProvider extends AbstractBingMapsTileProvider {
	public BingMapsRoadTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Override
	protected String getBingMapType() {
		return "Road";
	}
}
