package com.example.hungnv.directionmap.view;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.example.hungnv.directionmap.OnDirectionAttached;
import com.example.hungnv.directionmap.R;
import com.example.hungnv.directionmap.controller.MapListener;
import com.example.hungnv.directionmap.controller.MapController;
import com.example.hungnv.directionmap.model.graphhopper.ResultPath;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.mapsforge.core.graphics.Color;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.rendertheme.AssetsRenderTheme;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.datastore.MultiMapDataStore;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.overlay.Polyline;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.reader.MapFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends BaseActivity implements  MapListener, OnDirectionAttached{

    private static final String MAP_FILE_VIETNAM = "vietnam.map";
    private static final String MAP_FILE_WORLD = "world.map";
    private MapView mapView;
    private MapController mMapController;
    private TappableMarker mMarkerCurrentLocation;
    private TappableMarker mMarkerSourceLocation;
    private TappableMarker mMarkerDesLocation;
    private FloatingActionButton mFabDirection;
    private FloatingActionMenu mFabMenu;
    private FloatingActionButton mFabRating;

    private String TAG = "Auto complete place";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidGraphicFactory.createInstance(getApplication());
        mapView = new MapView(this);
        setContentView(R.layout.activity_maps);
        mapping();
        permissionController.askPermission(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        mMapController = new MapController(this, permissionController);
        mMapController.setmMapListener(this);
        setupMap();
        final DirectionFragment directionFragment = new DirectionFragment();
        final RatingFragment ratingFragment = new RatingFragment();
        directionFragment.setMapController(mMapController);
        directionFragment.setOnDirectionAttached(this);
        mFabDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFabDirection.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().addToBackStack(DirectionFragment.class.getSimpleName()).replace(R.id.Container, directionFragment).commit();
                mFabMenu.close(true);
            }
        });
        mFabRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().addToBackStack(DirectionFragment.class.getSimpleName()).replace(R.id.Container, ratingFragment).commit();
                mFabMenu.close(true);
            }
        });

    }

    private void setupMap(){
        try {
            /*
             * We then make some simple adjustments, such as showing a scale bar and zoom controls.
             */
            mapView.setClickable(true);
            mapView.getMapScaleBar().setVisible(true);
            mapView.setBuiltInZoomControls(false);

            /*
             * To avoid redrawing all the tiles all the time, we need to set up a tile cache with an
             * utility method.
             */
            TileCache tileCache = AndroidUtil.createTileCache(this, "mapcache",
                    mapView.getModel().displayModel.getTileSize(), 1f,
                    mapView.getModel().frameBufferModel.getOverdrawFactor());

            /*
             * Now we need to set up the process of displaying a map. A map can have several layers,
             * stacked on top of each other. A layer can be a map or some visual elements, such as
             * markers. Here we only show a map based on a mapsforge map file. For this we need a
             * TileRendererLayer. A TileRendererLayer needs a TileCache to hold the generated map
             * tiles, a map file from which the tiles are generated and Rendertheme that defines the
             * appearance of the map.
             */
            MultiMapDataStore multiMapDataStore = new MultiMapDataStore(MultiMapDataStore.DataPolicy.RETURN_ALL);
            File mapFileVietnam = new File(Environment.getExternalStorageDirectory(), MAP_FILE_VIETNAM);
            File mapFileWorld = new File(Environment.getExternalStorageDirectory(), MAP_FILE_WORLD);
            MapDataStore mapDataStoreVN = new MapFile(mapFileVietnam);
            MapDataStore mapDataStoreWorld = new MapFile(mapFileWorld);
            multiMapDataStore.addMapDataStore(mapDataStoreVN, true, true);
            multiMapDataStore.addMapDataStore(mapDataStoreWorld, true, true);
            TileRendererLayer tileRendererLayer = new TileRendererLayer(tileCache, multiMapDataStore,
                    mapView.getModel().mapViewPosition, AndroidGraphicFactory.INSTANCE);
//            tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);
            try{
                tileRendererLayer.setXmlRenderTheme(new AssetsRenderTheme(this, "", "rendertheme-v4.xml"));
            }catch (Exception ex){
                ex.printStackTrace();
            }


            /*
             * On its own a tileRendererLayer does not know where to display the map, so we need to
             * associate it with our mapView.
             */
            mapView.getLayerManager().getLayers().add(tileRendererLayer);

            /*
             * The map also needs to know which area to display and at what zoom level.
             * Note: this map position is specific to Berlin area.
             */
            mapView.setCenter(new LatLong(21.006479, 105.818292));
            mapView.setZoomLevel((byte) 12);
            // instantiating the paint object


        } catch (Exception e) {
            /*
             * In case of map file errors avoid crash, but developers should handle these cases!
             */
            e.printStackTrace();
        }
    }

    private void mapping() {
        mapView = findViewById(R.id.map);
        mFabDirection = findViewById(R.id.FabDirection);
        mFabMenu = findViewById(R.id.FabMenu);
        mFabRating = findViewById(R.id.FabLogin);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onGetLocation(LatLong latLng, int function) {
        // Add a marker in Sydney and move the camera

        if (function == MapController.CURRENT_LOCATION) {
            if (mMarkerCurrentLocation != null) {
                mMarkerCurrentLocation.onDestroy();
            }
            mMarkerCurrentLocation = new TappableMarker(latLng, R.drawable.marker_now);
            mapView.getLayerManager().getLayers().add(mMarkerCurrentLocation);
            mapView.setCenter(latLng);
            mapView.setZoomLevel((byte) 12);
        } else if (function == MapController.SOURCE_LOCATION) {
            if (mMarkerSourceLocation != null) {
                mMarkerSourceLocation.onDestroy();
            }
            mMarkerSourceLocation = new TappableMarker(latLng, R.drawable.marker_source);
            mapView.getLayerManager().getLayers().add(mMarkerSourceLocation);
        } else if (function == MapController.DESTINATION_LOCATION) {
            if (mMarkerDesLocation != null) {
                mMarkerDesLocation.onDestroy();
            }
            mMarkerDesLocation = new TappableMarker(latLng, R.drawable.marker_des);
            mapView.getLayerManager().getLayers().add(mMarkerDesLocation);
        }
    }

//    @Override
//    public void onDirectListener(Direction direction) {
//        Leg leg = direction.getListRoutes().get(0).getLegs().get(0);
//        LatLng startLatlng = new LatLng(leg.getStartLocation().getLat(), leg.getStartLocation().getLng());
//        LatLng desLatlng = new LatLng(leg.getEndLocation().getLat(), leg.getEndLocation().getLng());
//        if (mMarkerSourceLocation != null) {
//            mMarkerSourceLocation.remove();
//        }
//        mMarkerSourceLocation = mMap.addMarker(new MarkerOptions().position(startLatlng).title("Start"));
//        mMarkerSourceLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_source));
//
//
//        if (mMarkerDesLocation != null) {
//            mMarkerDesLocation.remove();
//        }
//        mMarkerDesLocation = mMap.addMarker(new MarkerOptions().position(desLatlng).title("Destination"));
//        mMarkerDesLocation.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_des));
//
//        for(Polyline p: mPolylines){
//            p.remove();
//        }
//        mPolylines.clear();
//        List<Route> listRoutes = direction.getListRoutes();
//        for (int i= 0; i < listRoutes.size(); i++){
////            List<LatLng> polyz = decodeOverviewPolyLinePonts(listRoutes.get(i).getOverviewPolyline().getPoints());
//            List<LatLng> polyz = decodeOverviewPolyLinePonts("sve_Cwn{dSD\\\\Dj@uArAaDlCUNoDvCkWfS~DjC_@pA`@Pl@cAvD|CzC|C\\\\p@Cf@yAhDaOrWuI|MeC~CeDnD_@x@g@f@bBbB|@p@rCbDVV`BlB");
//            PolylineOptions polylineOptions = new PolylineOptions();
//            polylineOptions.addAll(polyz);
//            polylineOptions.width(10);
//            switch (i){
//                case 0: polylineOptions.color(Color.RED); break;
//                case 1: polylineOptions.color(Color.GREEN); break;
//                case 2: polylineOptions.color(Color.BLUE); break;
//            }
//            mPolylines.add(mMap.addPolyline(polylineOptions));
//        }
//
////        Toast.makeText(MapsActivity.this, ""+direction.getListRoutes().get(0).getLegs().get(0).getDistance().getText(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(MapsActivity.this, "Có " + direction.getListRoutes().size() + " đường đi", Toast.LENGTH_SHORT).show();
//    }


    @Override
    public void onDirectListener(ResultPath resultPath) {
        LatLong startLatlng = new LatLong(resultPath.getStartPoint().getLatitude(), resultPath.getStartPoint().getLongitude());
        LatLong desLatlng = new LatLong(resultPath.getEndPoint().getLatitude(), resultPath.getEndPoint().getLongitude());
        if (mMarkerSourceLocation != null) {
            mMarkerSourceLocation.onDestroy();
        }
        mMarkerSourceLocation = new TappableMarker(startLatlng, R.drawable.marker_source);
        mapView.getLayerManager().getLayers().add(mMarkerSourceLocation);

        if (mMarkerDesLocation != null) {
            mMarkerDesLocation.onDestroy();
        }
        mMarkerDesLocation = new TappableMarker(desLatlng, R.drawable.marker_des);
        mapView.getLayerManager().getLayers().add(mMarkerDesLocation);

        Paint paint = AndroidGraphicFactory.INSTANCE.createPaint();
        paint.setColor(org.mapsforge.core.graphics.Color.GREEN);
        paint.setStrokeWidth(12);
        paint.setStyle(Style.STROKE);
        Paint paint2 = AndroidGraphicFactory.INSTANCE.createPaint();
//        paint2.setColor(AndroidGraphicFactory.INSTANCE.createColor(1000, 255, 102, 0 ));
        paint2.setColor(Color.BLUE);
        paint2.setStrokeWidth(12);
        paint2.setStyle(Style.STROKE);
        Paint paint3 = AndroidGraphicFactory.INSTANCE.createPaint();
        paint3.setColor(Color.RED);
        paint3.setStrokeWidth(12);
        paint3.setStyle(Style.STROKE);
        System.out.println(resultPath.getInstructionList().size());
        Paint[] paints = new Paint[]{paint, paint2, paint3};
        for(int i=0; i<3; i++){
            // instantiating the polyline object
            Polyline polyline = new Polyline(paints[i], AndroidGraphicFactory.INSTANCE);
            // set lat lng for the polyline
            List<LatLong> coordinateList = polyline.getLatLongs();
            List<LatLong> listLatLong = decodeOverviewPolyLinePonts(resultPath.getInstructionList().get(i).getPolyline());
            for(LatLong latLong: listLatLong){
                coordinateList.add(latLong);
            }
            mapView.getLayerManager().getLayers().add(polyline);
        }

    }

    public List<LatLong> decodeOverviewPolyLinePonts(String encoded) {
        List<LatLong> poly = new ArrayList<>();
        if (encoded != null && !encoded.isEmpty() && encoded.trim().length() > 0) {
            int index = 0, len = encoded.length();
            int lat = 0, lng = 0;

            while (index < len) {
                int b, shift = 0, result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lat += dlat;

                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                lng += dlng;

                LatLong p = new LatLong((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
                poly.add(p);
            }
        }
        return poly;
    }

    @Override
    protected void onDestroy() {
        /*
         * Whenever your activity exits, some cleanup operations have to be performed lest your app
         * runs out of memory.
         */
        mapView.destroyAll();
        AndroidGraphicFactory.clearResourceMemoryCache();
        super.onDestroy();
    }

    @Override
    public void onDetached() {
        mFabDirection.setVisibility(View.VISIBLE);
        mFabMenu.close(true);
    }

    private class TappableMarker extends Marker{
        public TappableMarker(LatLong localLatLong, int icon) {
            super(localLatLong,AndroidGraphicFactory.convertToBitmap(MapsActivity.this.getApplicationContext().getResources().getDrawable(icon)),
                    0,
                    -1*(AndroidGraphicFactory.convertToBitmap(MapsActivity.this.getApplicationContext().getResources().getDrawable(icon)).getHeight())/2);
        }
    }
}
