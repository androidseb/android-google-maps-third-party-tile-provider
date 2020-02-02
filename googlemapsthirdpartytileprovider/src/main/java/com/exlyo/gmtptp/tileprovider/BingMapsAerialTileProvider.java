package com.exlyo.gmtptp.tileprovider;

public class BingMapsAerialTileProvider extends AbstractBingMapsTileProvider {
	public BingMapsAerialTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Override
	protected String getBingMapType() {
		return "Aerial";
	}
}
