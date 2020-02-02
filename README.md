# Android Google Maps Third Party Tile Provider
This library is useful if you want to use third party tiles provider with a Google Maps view.

I created this library for my app Map Marker (not open source at the moment):
https://play.google.com/store/apps/details?id=com.exlyo.mapmarker

## Credits

The [Mapsforge project](https://github.com/mapsforge/mapsforge) allowed me to build this library for offline maps, and helped me figure out a few provider URL schemes too.

## Project structure
The library project is the `googlemapsthirdpartytileprovider` folder.

## How to use this library in your code
1. You will have to add the library folder manually to your Android project
2. In your code, create a GMThirdPartyTileProvider and initialize it like this
```java
@Override
public void onMapReady(GoogleMap googleMap) {
    //Code related to initializing googleMap related attributes

    //Initializing GMThirdPartyTileProvider instance
    thirdPartyTileProvider = new GMThirdPartyTileProvider(this, googleMap);
}
```
3. Initialize the settings you need
```java
final List<File> offlineMapsFileList = new ArrayList<>();
//Map files can be found here: https://ftp-stud.hs-esslingen.de/pub/Mirrors/download.mapsforge.org/maps/v5
offlineMapsFileList.add(new File("path/to/my/offline/map/file.map"));
thirdPartyTileProvider.getSettings()
    .setLanguage("fr")
    .setBingApiKey("YOUR_API_KEY")
    .setIgnApiKey("YOUR_API_KEY")
    .setIgnUserAgentString("YOUR_USER_AGENT_STRING")
    .setOfflineMapFileList(offlineMapsFileList)
;
```
4. Control the displayed map type from the tile provider object
```java
thirdPartyTileProvider.setMapType(GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_OPEN_STREET_MAP);
```

## List of supported tile provider
- Offline maps with Mapsforge: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_OFFLINE_MAPSFORGE`
- Regular Google Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_GOOGLE_MAPS_NORMAL`
- Terrain Google Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_GOOGLE_MAPS_TERRAIN`
- Satellite Google Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_GOOGLE_MAPS_SATELLITE`
- Hybrid Google Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_GOOGLE_MAPS_HYBRID`
- Bing Road Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_BING_ROAD`
- Bing Aerial Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_BING_AERIAL`
- Bing Hybrid Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_BING_AERIAL_LABELS`
- Open Street Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_OPEN_STREET_MAP`
- ArcGis Server Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.TILE_TYPE_ARCGIS_SERVER`
- IGN Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.IGN_MAPS`
- IGN Photo Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.IGN_PHOTOS`
- IGN Cadastral Parcels Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.IGN_CADASTRAL_PARCELS`
- IGN Plans Maps: `GMThirdPartyTileProvider.ThirdPartyTileType.IGN_PLANS`

## About issues and/or feature requests

I am not willing to invest time to take feature requests at the moment since this library has sufficient features for my needs as is. If you find a bug, I'll probably want to fix it since it might affect my production app, so feel free to report any bugs you may find. If you need a feature and want to build it and then submit it as a pull request, I'm willing to work with you to merge your work into the current code though.
