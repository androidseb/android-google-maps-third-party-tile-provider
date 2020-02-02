package com.exlyo.gmtptp.tileprovider;

import android.support.annotation.Nullable;

public abstract class AbstractIGNTileProvider extends AbstractUrlTileProvider {
	AbstractIGNTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	@Nullable
	@Override
	protected String getUserAgentString() {
		return settings.getIgnUserAgentString();
	}

	protected abstract String getUrlSuffixString();

	@Override
	protected final String getFormattedUrlString(final int _x, final int _y, final int _zoom) {
		return//
			"https://wxs.ign.fr/"//
				+ settings.getIgnApiKey()//
				+ "/geoportail/wmts?SERVICE=WMTS&VERSION=1.0.0&REQUEST=GetTile"//
				+ "&STYLE=normal&EXCEPTIONS=text/xml&TILEMATRIXSET=PM"//
				+ "&TILEMATRIX="//
				+ _zoom//
				+ "&TILEROW="//
				+ _y//
				+ "&TILECOL="//
				+ _x//
				+ "&"//
				+ getUrlSuffixString()//
			;
	}
}
