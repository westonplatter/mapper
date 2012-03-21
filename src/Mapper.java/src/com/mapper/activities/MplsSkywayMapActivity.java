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
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.mapper.map.MapEdge;
import com.mapper.map.MapNode;
import com.mapper.map.MapOverlay;
import com.mapper.map.MyLocation;
import com.mapper.map.MyLocation.LocationResult;
import com.mapper.map.NodeDB;
import com.mapper.yelp.YelpQueryManager;

public class MplsSkywayMapActivity extends MapActivity
{

    private static NodeDB skywayDB;
    private MapController mc;
    private LocationManager lm;
    private LocationListener ll;
    GeoPoint p = null;

    private static double MapCenterLatitude = 44.975667;
    private static double MapCenterLongitude = -93.270793;

    /*
     * (non-Javadoc)
     * 
     * @see com.google.android.maps.MapActivity#isRouteDisplayed()
     */
    @Override
    protected boolean isRouteDisplayed()
    {
        // TODO Auto-generated method stub
        return false;
    }

    /** Called when the activity is first created. */
    // @Override

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        // Create map view
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(false);

        // get MapOverlap Object List
        List<Overlay> mapOverlays = mapView.getOverlays();

        // get skyway from adaptation
        skywayDB = new NodeDB(readSkywayAdaptation());

        // skywayDB.printSkywayDB();

        ArrayList<MapNode> skyway = skywayDB.getNodeList();
        ArrayList<Integer> alreadyDrawnSkyways = new ArrayList<Integer>();

        for(MapNode node : skyway)
        {
            for(MapEdge edge : node.getAdjacentEdges())
            {
                if(!alreadyDrawnSkyways.contains(edge.getUniqueID()))
                {
                    mapOverlays.add(new MapOverlay(edge.getFirstNode(), edge
                            .getSecondNode()));
                    alreadyDrawnSkyways.add(edge.getUniqueID());
                }
            }
        }

        // get Map Controller to set location and zoom
        mc = mapView.getController();

        try
        {
            new YelpQueryManager();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Center Map
        p = new GeoPoint((int) (MapCenterLatitude * 1000000),
                (int) (MapCenterLongitude * 1000000));
        mc.animateTo(p);
        mc.setZoom(15);

        MyLocationOverlay myLocationOverlay = new MyLocationOverlay();
        List<Overlay> list = mapView.getOverlays();
        list.add(myLocationOverlay);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        ll = new MyLocationListener();

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
        // lm.requestLocationUpdates(LocationManager.KEY_LOCATION_CHANGED, 0, 0,
        // ll);
        // lm.requestLocationUpdates(LocationManager.KEY_PROVIDER_ENABLED, 0, 0,
        // ll);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.skyway_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch(item.getItemId())
        {
            case R.id.get_directions:
                // newGame();
                return true;
            case R.id.business_directory:
                // newGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    MyLocation myLocation = new MyLocation();

    private void locationClick()
    {
        myLocation.getLocation(this, locationResult);
    }

    public LocationResult locationResult = new LocationResult()
    {
        @Override
        public void gotLocation(Location location)
        {
            // TODO Auto-generated method stub
            System.out.println("Location Received - " + location.getLatitude()
                    + " " + location.getLongitude());
        };
    };

    private ArrayList<Pair<GeoPoint, GeoPoint>> readSkywayAdaptation()
    {
        // TextView myXmlContent = (TextView) findViewById(R.id.my_xml);
        ArrayList<String> stringXmlContent = null;
        ArrayList<Pair<GeoPoint, GeoPoint>> returnList = new ArrayList<Pair<GeoPoint, GeoPoint>>();
        try
        {
            stringXmlContent = getEventsFromAnXML(this);
        }
        catch(XmlPullParserException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for(String line : stringXmlContent)
        {
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

    private ArrayList<String> getEventsFromAnXML(Activity activity)
            throws XmlPullParserException, IOException
    {
        ArrayList<String> coordinateList = new ArrayList<String>();
        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(R.xml.skywayxml);
        xpp.next();
        int eventType = xpp.getEventType();

        while(eventType != XmlPullParser.END_DOCUMENT)
        {
            if(eventType == XmlPullParser.TEXT)
            {
                coordinateList.add(xpp.getText());
            }
            eventType = xpp.next();
        }

        return coordinateList;
    }

    private class MyLocationListener implements LocationListener
    {

        public void onLocationChanged(Location argLocation)
        {
            // TODO Auto-generated method stub
            GeoPoint myGeoPoint = new GeoPoint(
                    (int) (argLocation.getLatitude() * 1000000),
                    (int) (argLocation.getLongitude() * 1000000));

            p = myGeoPoint;
            /*
             * it will show a message on location change
             * Toast.makeText(getBaseContext(), "New location latitude ["
             * +argLocation.getLatitude() + "] longitude [" +
             * argLocation.getLongitude()+"]", Toast.LENGTH_SHORT).show();
             */

            mc.animateTo(myGeoPoint);

        }

        public void onProviderDisabled(String provider)
        {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider)
        {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            // TODO Auto-generated method stub
        }
    }

    protected class MyLocationOverlay extends com.google.android.maps.Overlay
    {
        @Override
        public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
                long when)
        {
            super.draw(canvas, mapView, shadow);

            // Converts lat/lng-Point to OUR coordinates on the screen.
            Point myScreenCoords = new Point();
            mapView.getProjection().toPixels(p, myScreenCoords);

            Paint paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setARGB(255, 255, 255, 255);
            paint.setStyle(Paint.Style.STROKE);

            Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_launcher);
            canvas.drawBitmap(bmp, myScreenCoords.x, myScreenCoords.y, paint);

            return true;
        }
    }
}