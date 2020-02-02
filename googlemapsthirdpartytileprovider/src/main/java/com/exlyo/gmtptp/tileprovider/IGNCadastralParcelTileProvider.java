package com.exlyo.gmtptp.tileprovider;

public class IGNCadastralParcelTileProvider extends AbstractIGNTileProvider {
	public IGNCadastralParcelTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Override
	protected String getUrlSuffixString() {
		return "LAYER=CADASTRALPARCELS.PARCELS&FORMAT=image/png";
	}

	public float getMaxZoomLevel() {
		return 20F;
	}
}
