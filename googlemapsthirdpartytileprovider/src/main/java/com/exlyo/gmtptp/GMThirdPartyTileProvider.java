package com.exlyo.gmtptp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;

import com.exlyo.gmtptp.tileprovider.AbstractTileProvider;
import com.exlyo.gmtptp.tileprovider.ArcGISServerTileProvider;
import com.exlyo.gmtptp.tileprovider.BingMapsAerialTileProvider;
import com.exlyo.gmtptp.tileprovider.BingMapsAerialWithLabelsTileProvider;
import com.exlyo.gmtptp.tileprovider.BingMapsRoadTileProvider;
import com.exlyo.gmtptp.tileprovider.DefaultTileProvider;
import com.exlyo.gmtptp.tileprovider.IGNCadastralParcelTileProvider;
import com.exlyo.gmtptp.tileprovider.IGNMapsTileProvider;
import com.exlyo.gmtptp.tileprovider.IGNPhotosTileProvider;
import com.exlyo.gmtptp.tileprovider.IGNPlansTileProvider;
import com.exlyo.gmtptp.tileprovider.MapsForgeOfflineTileProvider;
import com.exlyo.gmtptp.tileprovider.OSMTileProvider;
import com.exlyo.gmtptp.tileprovider.ThirdPartyTileProviderSettings;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;

import java.util.HashMap;
import java.util.Map;

public class GMThirdPartyTileProvider {
	public enum ThirdPartyTileType {
		TILE_TYPE_NONE//
		, TILE_TYPE_GOOGLE_MAPS_NORMAL//
		, TILE_TYPE_GOOGLE_MAPS_TERRAIN//
		, TILE_TYPE_GOOGLE_MAPS_SATELLITE//
		, TILE_TYPE_GOOGLE_MAPS_HYBRID//
		, TILE_TYPE_OFFLINE_MAPSFORGE//
		, TILE_TYPE_BING_ROAD//
		, TILE_TYPE_BING_AERIAL//
		, TILE_TYPE_BING_AERIAL_LABELS//
		, TILE_TYPE_OPEN_STREET_MAP//
		, TILE_TYPE_ARCGIS_SERVER//
		, IGN_MAPS//
		, IGN_PHOTOS//
		, IGN_CADASTRAL_PARCELS//
		, IGN_PLANS//
	}

	private static final class GoogleMapLayerSetup {
		public final int mapType;
		public final AbstractTileProvider tileProvider;

		public GoogleMapLayerSetup(final int _mapType, final AbstractTileProvider _tileProvider) {
			mapType = _mapType;
			tileProvider = _tileProvider;
		}
	}

	@NonNull
	private final Activity activity;
	@NonNull
	private final GoogleMap googleMap;
	private final ThirdPartyTileProviderSettings settings = new ThirdPartyTileProviderSettings();
	@NonNull
	private final AbstractTileProvider defaultTileProvider = new DefaultTileProvider();
	@NonNull
	private final Map<ThirdPartyTileType, GoogleMapLayerSetup> tileProviderMap = new HashMap<>();

	@Nullable
	private ThirdPartyTileType tileType = null;
	@Nullable
	private TileOverlay currentTileOverlay = null;

	public GMThirdPartyTileProvider(@NonNull final Activity _activity, @NonNull final GoogleMap _googleMap) {
		activity = _activity;
		googleMap = _googleMap;
		initTileProviderMap();
	}

	private void initTileProviderMap() {
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_NONE//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, defaultTileProvider)//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_GOOGLE_MAPS_NORMAL//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NORMAL, defaultTileProvider)//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_GOOGLE_MAPS_TERRAIN//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_TERRAIN, defaultTileProvider)//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_GOOGLE_MAPS_SATELLITE//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_SATELLITE, defaultTileProvider)//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_GOOGLE_MAPS_HYBRID//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_HYBRID, defaultTileProvider)//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_OFFLINE_MAPSFORGE//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new MapsForgeOfflineTileProvider(activity, settings))//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_BING_ROAD//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new BingMapsRoadTileProvider(settings))//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_BING_AERIAL//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new BingMapsAerialTileProvider(settings))//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_BING_AERIAL_LABELS//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new BingMapsAerialWithLabelsTileProvider(settings))//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_OPEN_STREET_MAP//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new OSMTileProvider(settings))//
		);
		tileProviderMap.put(ThirdPartyTileType.TILE_TYPE_ARCGIS_SERVER//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new ArcGISServerTileProvider(settings))//
		);
		tileProviderMap.put(ThirdPartyTileType.IGN_MAPS//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new IGNMapsTileProvider(settings))//
		);
		tileProviderMap.put(ThirdPartyTileType.IGN_PHOTOS//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new IGNPhotosTileProvider(settings))//
		);
		tileProviderMap.put(ThirdPartyTileType.IGN_CADASTRAL_PARCELS//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new IGNCadastralParcelTileProvider(settings))//
		);
		tileProviderMap.put(ThirdPartyTileType.IGN_PLANS//
			, new GoogleMapLayerSetup(GoogleMap.MAP_TYPE_NONE, new IGNPlansTileProvider(settings))//
		);
	}

	public ThirdPartyTileProviderSettings getSettings() {
		return settings;
	}

	@UiThread
	public synchronized void setMapType(@NonNull final ThirdPartyTileType _tileType) {
		tileType = _tileType;
		if (currentTileOverlay != null) {
			currentTileOverlay.remove();
			currentTileOverlay.clearTileCache();
		}
		final GoogleMapLayerSetup googleMapLayerSetup = tileProviderMap.get(_tileType);
		if (googleMapLayerSetup == null) {
			return;
		}
		googleMap.setMapType(googleMapLayerSetup.mapType);
		googleMap.setMaxZoomPreference(googleMapLayerSetup.tileProvider.getMaxZoomLevel());
		currentTileOverlay = googleMap.addTileOverlay(new TileOverlayOptions().tileProvider(googleMapLayerSetup.tileProvider));
	}

	@Nullable
	public ThirdPartyTileType getTileType() {
		return tileType;
	}
}
