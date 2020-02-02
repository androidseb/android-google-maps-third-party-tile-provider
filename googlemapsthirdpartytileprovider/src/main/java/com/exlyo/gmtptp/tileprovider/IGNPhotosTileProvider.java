package com.exlyo.gmtptp.tileprovider;

public class IGNPhotosTileProvider extends AbstractIGNTileProvider {
	public IGNPhotosTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Override
	protected String getUrlSuffixString() {
		return "LAYER=ORTHOIMAGERY.ORTHOPHOTOS&FORMAT=image/jpeg";
	}

	public float getMaxZoomLevel() {
		return 19F;
	}
}
