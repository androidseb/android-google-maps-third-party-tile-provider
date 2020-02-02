package com.exlyo.gmtptp.tileprovider;

import android.util.Log;

import com.exlyo.gmtptp.GMTPTPUtils;

import org.json.JSONObject;

import java.util.Locale;

public abstract class AbstractBingMapsTileProvider extends AbstractUrlTileProvider {
	public AbstractBingMapsTileProvider(final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
	}

	protected abstract String getBingMapType();

	private String bingUrlScheme = null;

	@Override
	protected final String getFormattedUrlString(final int _x, final int _y, final int _zoom) {
		final String bingUrlScheme = getBingUrlScheme(getBingMapType());
		if (GMTPTPUtils.isStringNullOrEmpty(bingUrlScheme)) {
			return null;
		}
		final String quadTree = getBingQuadKey(_x, _y, _zoom);
		return String.format(bingUrlScheme, quadTree);
	}

	private static String getBingQuadKey(final int _x, final int _y, final int _zoom) {
		final StringBuilder quadKey = new StringBuilder();
		for (int i = _zoom; i > 0; i--) {
			int digit = 0;
			final int mask = 1 << (i - 1);
			if ((_x & mask) != 0) {
				digit += 1;
			}
			if ((_y & mask) != 0) {
				digit += 2;
			}
			quadKey.append(digit);
		}

		return quadKey.toString();
	}

	private synchronized String getBingUrlScheme(final String _mapType) {
		if (bingUrlScheme != null) {
			return bingUrlScheme;
		}
		if (GMTPTPUtils.isStringNullOrEmpty(settings.getBingApiKey()) || GMTPTPUtils.isStringNullOrEmpty(_mapType)) {
			Log.e("MMTPTP", "Trying to use bing maps but no api key has been set!");
			return null;
		}

		try {
			final String rawJSONResponse = GMTPTPUtils.urlToString(String
				.format(Locale.US, "https://dev.virtualearth.net/REST/V1/Imagery/Metadata/%s?uriScheme=https&output=json&c=%s&key=%s",
					_mapType, settings.getLanguage(), settings.getBingApiKey()));
			final JSONObject resourceJSONObject =
				new JSONObject(rawJSONResponse).getJSONArray("resourceSets").getJSONObject(0).getJSONArray("resources").getJSONObject(0);
			final String subDomain = resourceJSONObject.getJSONArray("imageUrlSubdomains").getString(0);
			String imageUrl = resourceJSONObject.getString("imageUrl");
			imageUrl = imageUrl.replaceAll("\\{culture\\}", settings.getLanguage());
			imageUrl = imageUrl.replaceAll("\\{subdomain\\}", subDomain);
			imageUrl = imageUrl.replaceAll("\\{quadkey\\}", "%s");
			bingUrlScheme = imageUrl;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return bingUrlScheme;
	}

	public float getMaxZoomLevel() {
		return 20F;
	}
}
