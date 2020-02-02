package com.exlyo.gmtptp.tileprovider;

public class BingMapsAerialWithLabelsTileProvider extends AbstractBingMapsTileProvider {
	public BingMapsAerialWithLabelsTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Override
	protected String getBingMapType() {
		return "AerialWithLabels";
	}
}
