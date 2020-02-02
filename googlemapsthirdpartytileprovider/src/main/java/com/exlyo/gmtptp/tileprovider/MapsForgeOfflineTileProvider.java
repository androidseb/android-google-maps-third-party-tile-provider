package com.exlyo.gmtptp.tileprovider;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.exlyo.gmtptp.GMTPTPUtils;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.graphics.AndroidTileBitmap;
import org.mapsforge.map.datastore.MultiMapDataStore;
import org.mapsforge.map.layer.cache.InMemoryTileCache;
import org.mapsforge.map.layer.labels.TileBasedLabelStore;
import org.mapsforge.map.layer.renderer.DatabaseRenderer;
import org.mapsforge.map.layer.renderer.RendererJob;
import org.mapsforge.map.model.DisplayModel;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;
import org.mapsforge.map.rendertheme.rule.RenderThemeFuture;

import java.io.File;
import java.util.List;

public class MapsForgeOfflineTileProvider extends AbstractTileProvider {
	private static final int TILE_SIZE_PIXELS = 256;
	private RenderThemeFuture theme = null;
	private DatabaseRenderer renderer = null;
	private MultiMapDataStore mapDatabase = null;
	private final float displayDensity;

	private final Activity activity;
	private final DisplayModel displayModel;
	@Nullable
	private List<File> currentMapFileList = null;

	public MapsForgeOfflineTileProvider(final Activity _activity, final ThirdPartyTileProviderSettings _settings) {
		super(_settings);
		activity = _activity;
		displayDensity = activity.getResources().getDisplayMetrics().density;
		displayModel = new DisplayModel();
		displayModel.setFixedTileSize((int) (TILE_SIZE_PIXELS * displayDensity));
	}

	private void setMapFiles(@NonNull final List<File> _fileList) {
		if (currentMapFileList == _fileList) {
			return;
		}
		currentMapFileList = _fileList;

		if (mapDatabase != null) {
			mapDatabase.close();
		}
		mapDatabase = new MultiMapDataStore(MultiMapDataStore.DataPolicy.RETURN_ALL);
		for (final File file : currentMapFileList) {
			try {
				final MapFile mapDataStore = new MapFile(file, settings.getLanguage());
				try {
					mapDatabase.addMapDataStore(mapDataStore, false, false);
				} catch (Throwable t) {
					t.printStackTrace();
					mapDataStore.close();
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		AndroidGraphicFactory.createInstance(activity.getApplication());
		if (AndroidGraphicFactory.INSTANCE == null) {
			throw new RuntimeException(
				"Need to initialize the AndroidGraphicFactory.INSTANCE via AndroidGraphicFactory.createInstance(context);");
		}

		renderer =
			new DatabaseRenderer(mapDatabase, AndroidGraphicFactory.INSTANCE, new InMemoryTileCache(2), new TileBasedLabelStore(2), true,
				true, null);

		if (theme == null) {
			theme = new RenderThemeFuture(AndroidGraphicFactory.INSTANCE, InternalRenderTheme.DEFAULT, displayModel);
			theme.run();
		}
	}

	@Override
	public synchronized com.google.android.gms.maps.model.Tile getTile(final int _x, final int _y, final int _zoom) {
		final List<File> offlineMapFiles = settings.getOfflineMapFileList();
		if (offlineMapFiles.isEmpty()) {
			return TileProvider.NO_TILE;
		}
		setMapFiles(offlineMapFiles);
		org.mapsforge.core.model.Tile tile =
			new org.mapsforge.core.model.Tile(_x, _y, (byte) _zoom, (int) (TILE_SIZE_PIXELS * displayDensity));
		try {
			final RendererJob mapGeneratorJob = new RendererJob(tile, mapDatabase, theme, displayModel, 1F, false, false);
			final AndroidTileBitmap rawBitmap = (AndroidTileBitmap) renderer.executeJob(mapGeneratorJob);
			final Bitmap processedBitmap = AndroidGraphicFactory.getBitmap(rawBitmap);
			final byte[] processedBitmapData = GMTPTPUtils.bitmapToPngByteArray(processedBitmap);
			return new Tile(TILE_SIZE_PIXELS, TILE_SIZE_PIXELS, processedBitmapData);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TileProvider.NO_TILE;
	}

	public float getMaxZoomLevel() {
		return 20F;
	}
}
