package com.exlyo.gmtptp.tileprovider;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.TileProvider;

public abstract class AbstractTileProvider implements TileProvider {
	@NonNull
	protected final ThirdPartyTileProviderSettings settings;

	public AbstractTileProvider(@NonNull final ThirdPartyTileProviderSettings _settings) {
		settings = _settings;
	}

	public float getMaxZoomLevel() {
		return 21F;
	}
}
