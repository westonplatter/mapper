// MplsSkywayMapActivity.java
/**
 * Copyright 2012 Jon Lee
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mapper.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.mapper.map.MapEdge;
import com.mapper.map.MapNode;
import com.mapper.map.MapOverlay;

import com.mapper.map.NodeDB;
import com.mapper.yelp.YelpQueryManager;

public class MplsSkywayMapActivity extends MapActivity {

    private static NodeDB skywayDB;
    private MapController mc;
    private LocationManager lm;
    private LocationListener ll;
    GeoPoint p = null;
    String gps_provider = LocationManager.GPS_PROVIDER;
    String network_provider = LocationManager.NETWORK_PROVIDER;
    Location loc;
    boolean enableCurrentLocation = false;
    MyLocationOverlay myLocationOverlay;
    MapView mapView;

    private static double MapCenterLatitude = 44.975667;
    private static double MapCenterLongitude = -93.270793;

    @Override
    protected boolean isRouteDisplayed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map);

        // Create map view
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(false);

        // get MapOverlap Object List
        List<Overlay> mapOverlays = mapView.getOverlays();

        // get skyway from adaptation
        skywayDB = new NodeDB(readSkywayAdaptation());

        ArrayList<MapNode> skyway = skywayDB.getNodeList();
        ArrayList<Integer> alreadyDrawnSkyways = new ArrayList<Integer>();

        for (MapNode node : skyway) {
            for (MapEdge edge : node.getAdjacentEdges()) {
                if (!alreadyDrawnSkyways.contains(edge.getUniqueID())) {
                    mapOverlays.add(new MapOverlay(edge.getFirstNode(), edge.getSecondNode()));
                    alreadyDrawnSkyways.add(edge.getUniqueID());
                }
            }
        }

        // get Map Controller to set location and zoom
        mc = mapView.getController();

        try {
            new YelpQueryManager();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Center Map
        p = new GeoPoint((int) (MapCenterLatitude * 1000000), (int) (MapCenterLongitude * 1000000));
        mc.animateTo(p);
        mc.setZoom(15);

        myLocationOverlay = new MyLocationOverlay(this, mapView);
        myLocationOverlay.enableMyLocation();
        myLocationOverlay.enableCompass();

        List<Overlay> list = mapView.getOverlays();
        list.add(myLocationOverlay);

        ll = new MyLocationListener();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
        lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, ll);

        Button currentlocationButton = (Button) findViewById(R.id.curLoc);
        currentlocationButton.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) 
            {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
                lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, ll);

                enableCurrentLocation = true;
                centerOnLocation();

                return true;
            }
        });

        mapView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View arg0) {
                // TODO Auto-generated method stub
                return false;
            }
        });

        mapView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // do your thing
                    enableCurrentLocation = false;
                    lm.removeUpdates(ll);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.skyway_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search:
                onSearchRequested();
                return true;
            case R.id.business_directory:
                // Do something here
                return true;
            case R.id.quit:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<Pair<GeoPoint, GeoPoint>> readSkywayAdaptation() {
        ArrayList<String> stringXmlContent = null;
        ArrayList<Pair<GeoPoint, GeoPoint>> returnList = new ArrayList<Pair<GeoPoint, GeoPoint>>();

        try {
            stringXmlContent = getEventsFromAnXML(this);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line : stringXmlContent) {
            String coordinate = line.replaceAll("\n", ",");
            coordinate = coordinate.replaceAll(" ", "");
            String[] coordinates = coordinate.split(",");

            returnList.add(new Pair<GeoPoint, GeoPoint>(new GeoPoint(
                    (int) (Double.valueOf(coordinates[1]) * 1000000),
                    (int) (Double.valueOf(coordinates[0]) * 1000000)),
                    new GeoPoint(
                            (int) (Double.valueOf(coordinates[4]) * 1000000),
                            (int) (Double.valueOf(coordinates[3]) * 1000000))));
        }
        return returnList;
    }

    private ArrayList<String> getEventsFromAnXML(Activity activity) throws XmlPullParserException, IOException {
        ArrayList<String> coordinateList = new ArrayList<String>();
        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(R.xml.skywayxml);
        xpp.next();
        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.TEXT) {
                coordinateList.add(xpp.getText());
            }
            eventType = xpp.next();
        }
        return coordinateList;
    }

    private void centerOnLocation() {
        if (loc != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            GeoPoint newPoint = new GeoPoint((int) (lat * 1e6), (int) (lon * 1e6));

            mc.animateTo(newPoint);
            mapView.invalidate();

        } else if ((loc = lm.getLastKnownLocation(gps_provider)) != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            GeoPoint newPoint = new GeoPoint((int) (lat * 1e6), (int) (lon * 1e6));

            mc.animateTo(newPoint);
            mapView.invalidate();
        }
    }

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location argLocation) {
            GeoPoint myGeoPoint = new GeoPoint(
                    (int) (argLocation.getLatitude() * 1000000),
                    (int) (argLocation.getLongitude() * 1000000));

            loc = argLocation;

            if (enableCurrentLocation) {
                myLocationOverlay.enableMyLocation();
                mc.animateTo(myGeoPoint);
                mapView.invalidate();
            }
        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
}