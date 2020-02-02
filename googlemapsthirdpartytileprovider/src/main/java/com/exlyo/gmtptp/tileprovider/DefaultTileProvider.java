package com.exlyo.gmtptp.tileprovider;

import com.google.android.gms.maps.model.Tile;

public class DefaultTileProvider extends AbstractTileProvider {
	public DefaultTileProvider() {
		super(null);
	}

	@Override
	public Tile getTile(final int _x, final int _y, final int _zoom) {
		return NO_TILE;
	}
}
