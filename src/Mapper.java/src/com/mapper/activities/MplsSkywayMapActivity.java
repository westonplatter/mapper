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
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.mapper.map.MapDirections;
import com.mapper.map.MapEdge;
import com.mapper.map.MapItemizedOverlay;
import com.mapper.map.MapNode;
import com.mapper.map.MapOverlay;
import com.mapper.map.MapOverlayItem;

import com.mapper.map.NodeDB;
import com.mapper.yelp.YelpQueryManager;

public class MplsSkywayMapActivity extends MapActivity
{

    private static NodeDB skywayDB;
    private MapController mc;
    private LocationManager lm;
    private LocationListener ll;
    GeoPoint p = null;
    String gps_provider = LocationManager.GPS_PROVIDER;
    String network_provider = LocationManager.NETWORK_PROVIDER;
    Location loc;
    MyLocationOverlay myLocationOverlay;
    MapView mapView;

    public static boolean followLocation = false;

    private static double MapCenterLatitude = 44.975667;
    private static double MapCenterLongitude = -93.270793;

    @Override
    protected boolean isRouteDisplayed()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
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

        // /////////////////////////////////////////////////////////////////////

        // Manual Insertion of Pin

        // instantiate the picture for the location
        Drawable marker = getResources().getDrawable(R.drawable.pin);
        int markerHeight = marker.getIntrinsicHeight();
        int markerWidth = marker.getIntrinsicWidth();
        marker.setBounds(0, markerHeight, markerWidth, 0);

        // instantiate the ItemizedOverlay (collection of items within connected
        // to overaly)
        MapItemizedOverlay itemizedOverlay = new MapItemizedOverlay(marker);
        mapOverlays.add(itemizedOverlay);

        // instantiate OverlayItem
        GeoPoint point_1 = new GeoPoint((int) (MapCenterLatitude * 1000000),
                (int) (MapCenterLongitude * 1000000));
        MapOverlayItem item = new MapOverlayItem(point_1, "title", "snippet");

        // add pin to the map
        itemizedOverlay.addItem(item);

        // /////////////////////////////////////////////////////////////////////

        // get Map Controller to set location and zoom
        mc = mapView.getController();

        // Center Map
        p = new GeoPoint((int) (MapCenterLatitude * 1000000),
                (int) (MapCenterLongitude * 1000000));
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
        currentlocationButton.setOnTouchListener(new OnTouchListener()
        {

            public boolean onTouch(View v, MotionEvent event)
            {
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                        ll);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                        0, ll);
                lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0,
                        0, ll);

                followLocation = true;
                centerOnLocation();

                DrawDirections(44.969595, -93.274012, 44.977231, -93.268068);

                return true;
            }
        });

        mapView.setOnLongClickListener(new OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View arg0)
            {
                // TODO Auto-generated method stub
                return false;
            }
        });
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
            case R.id.search:
                onSearchRequested();
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

    private ArrayList<Pair<GeoPoint, GeoPoint>> readSkywayAdaptation()
    {
        ArrayList<String> stringXmlContent = null;
        ArrayList<Pair<GeoPoint, GeoPoint>> returnList = new ArrayList<Pair<GeoPoint, GeoPoint>>();

        try
        {
            stringXmlContent = getEventsFromAnXML(this);
        }
        catch(XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
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

    private void centerOnLocation()
    {
        if(loc != null)
        {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            GeoPoint newPoint = new GeoPoint((int) (lat * 1e6),
                    (int) (lon * 1e6));

            mc.animateTo(newPoint);
            mapView.invalidate();

        }
        else if((loc = lm.getLastKnownLocation(gps_provider)) != null)
        {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            GeoPoint newPoint = new GeoPoint((int) (lat * 1e6),
                    (int) (lon * 1e6));

            mc.animateTo(newPoint);
            mapView.invalidate();
        }
    }

    private class MyLocationListener implements LocationListener
    {

        public void onLocationChanged(Location argLocation)
        {
            GeoPoint myGeoPoint = new GeoPoint(
                    (int) (argLocation.getLatitude() * 1000000),
                    (int) (argLocation.getLongitude() * 1000000));

            loc = argLocation;

            if(followLocation)
            {
                myLocationOverlay.enableMyLocation();
                mc.animateTo(myGeoPoint);
                mapView.invalidate();
                centerOnLocation();
            }

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

    public void DrawDirections(double latitude1, double longitude1,
            double latitude2, double longitude2)
    {
        ArrayList<MapNode> skyway = skywayDB.getNodeList();
        MapView mapView = (MapView) findViewById(R.id.mapview);

        // get MapOverlap Object List
        List<Overlay> mapOverlays = mapView.getOverlays();

        // get shortest path from 0 to 7
        MapDirections directions = new MapDirections(skywayDB);
        System.out.println("Break1");
        // clear the overlays
        mapOverlays.clear();
        System.out.println("Break2");
        // get the Start edge and start lat long point
        Pair<GeoPoint, MapEdge> startEdge = getClosestPointOnLineSegment(
                latitude1, longitude1);
        System.out.println("Break3");
        // get the End edge and end lat long point
        Pair<GeoPoint, MapEdge> endEdge = getClosestPointOnLineSegment(
                latitude2, longitude2);
        System.out.println("Break4");
        // get the closest node on the edge from where you are
        MapNode startNode = getClosestNode(
                startEdge.first.getLatitudeE6() / 1E6,
                startEdge.first.getLongitudeE6() / 1E6, startEdge.second);
        System.out.println("Break5");
        // get the closest node on the edge from the destination is
        MapNode endNode = getClosestNode(endEdge.first.getLatitudeE6() / 1E6,
                endEdge.first.getLongitudeE6() / 1E6, endEdge.second);
        System.out.println("Break6");
        // initiate the graph with closest paths from current location
        directions.execute(startNode);
        System.out.println("Break7");
        // get the shortest path
        LinkedList<MapNode> path = directions.getPath(endNode);
        System.out.println("Break8");
        // Draw path. connect current location to starting node then append the
        // end location to the finish location
        MapOverlay mo = new MapOverlay(startEdge.first,
                startNode.getNodeLocation());
        mo.setLineColor(Color.DKGRAY);
        mo.setLineWidth(5);
        mapOverlays.add(mo);
        System.out.println("Break9 ");
        System.out.println("Break  .   " + (path == null) + " " + (skywayDB == null));
        System.out.println("Break  .   " + path.size());

        int i;
        for(i = 0; i < path.size() - 1; ++i)
        {

            mo = new MapOverlay(path.get(i).getNodeLocation(), path.get(i + 1)
                    .getNodeLocation());
            System.out.println("Break9.1" + i + " " + path.size());

            mo.setLineColor(Color.DKGRAY);

            mo.setLineWidth(5);
            System.out.println("Break9.2");
            mapOverlays.add(mo);
            System.out.println("Break9.3");
        }

        System.out.println("Break9.4");
        mo = new MapOverlay(path.get(i).getNodeLocation(), endEdge.first);
        mo.setLineColor(Color.DKGRAY);
        System.out.println("Break9.5");
        mo.setLineWidth(5);
        System.out.println("Break9.6");
        mapOverlays.add(mo);
        System.out.println("Break10");
        // Redraw the skyways
        ArrayList<Integer> alreadyDrawnSkyways = new ArrayList<Integer>();

        for(MapNode node : skyway)
        {
            for(MapEdge edge : node.getAdjacentEdges())
            {
                if(!alreadyDrawnSkyways.contains(edge.getUniqueID()))
                {
                    mapOverlays.add(new MapOverlay(edge.getSourceNode()
                            .getNodeLocation(), edge.getTargetNode()
                            .getNodeLocation()));
                    alreadyDrawnSkyways.add(edge.getUniqueID());
                }
            }
        }
        System.out.println("Break111");
        mapView.invalidate();
        System.out.println("Break12");
    }

    public MapNode getClosestNode(double latitude, double longitude,
            MapEdge edge)
    {

        // get the closest node based on a location on the line

        double distanceToNode1 = Math.sqrt(Math.pow(latitude
                - edge.getSourceNode().getNodeLocation().getLatitudeE6() / 1E6,
                2)
                + Math.pow(longitude
                        - edge.getSourceNode().getNodeLocation()
                                .getLongitudeE6() / 1E6, 2));
        double distanceToNode2 = Math.sqrt(Math.pow(latitude
                - edge.getTargetNode().getNodeLocation().getLatitudeE6() / 1E6,
                2)
                + Math.pow(longitude
                        - edge.getTargetNode().getNodeLocation()
                                .getLongitudeE6() / 1E6, 2));

        return (distanceToNode1 < distanceToNode2) ? edge.getSourceNode()
                : edge.getTargetNode();
    }

    public Pair<GeoPoint, MapEdge> getClosestPointOnLineSegment(
            double latitude, double longitude)
    {

        // gets the closest point on a line segment
        MapEdge closestEdge = null;
        double closestDistance = Double.POSITIVE_INFINITY;
        for(MapEdge e : skywayDB.getEdgeList())
        {

            float x = (float) latitude;
            float y = (float) longitude;
            float edgeX1 = (float) ((float) e.getSourceNode().getNodeLocation()
                    .getLatitudeE6() / 1E6);
            float edgeY1 = (float) ((float) e.getSourceNode().getNodeLocation()
                    .getLongitudeE6() / 1E6);

            float edgeX2 = (float) ((float) e.getTargetNode().getNodeLocation()
                    .getLatitudeE6() / 1E6);
            float edgeY2 = (float) ((float) e.getTargetNode().getNodeLocation()
                    .getLongitudeE6() / 1E6);

            float A = x - edgeX1;
            float B = y - edgeY1;
            float C = edgeX2 - edgeX1;
            float D = edgeY2 - edgeY1;

            float dot = A * C + B * D;
            float len_sq = C * C + D * D;
            float param = dot / len_sq;

            float xx, yy;

            if(param < 0)
            {
                xx = edgeX1;
                yy = edgeY1;
            }
            else if(param > 1)
            {
                xx = edgeX2;
                yy = edgeY2;
            }
            else
            {
                xx = edgeX1 + param * C;
                yy = edgeY1 + param * D;
            }

            float dist = (float) Math.sqrt((Math.abs(x - xx) * (Math
                    .abs(x - xx))) + (Math.abs(y - yy) * (Math.abs(y - yy))));

            if(closestDistance > dist)
            {
                closestDistance = dist;
                closestEdge = e;
            }
        }

        float nodeAtoPointX = (float) Math.abs(latitude
                - closestEdge.getSourceNode().getNodeLocation().getLatitudeE6()
                / 1E6);
        float nodeAtoPointY = (float) Math.abs(longitude
                - closestEdge.getSourceNode().getNodeLocation()
                        .getLongitudeE6() / 1E6);

        float nodeAtoNodeBX = (float) Math.abs(closestEdge.getSourceNode()
                .getNodeLocation().getLatitudeE6()
                / 1E6
                - closestEdge.getTargetNode().getNodeLocation().getLatitudeE6()
                / 1E6);

        float nodeAtoNodeBY = (float) Math.abs(closestEdge.getSourceNode()
                .getNodeLocation().getLongitudeE6()
                / 1E6
                - closestEdge.getTargetNode().getNodeLocation()
                        .getLongitudeE6() / 1E6);

        float nodeAtoB = nodeAtoNodeBX * nodeAtoNodeBX + nodeAtoNodeBY
                * nodeAtoNodeBY;

        float aToPDotAToB = nodeAtoPointX * nodeAtoNodeBX + nodeAtoPointY
                * nodeAtoNodeBY;

        float t = aToPDotAToB / nodeAtoB;

        float X = (float) (closestEdge.getSourceNode().getNodeLocation()
                .getLatitudeE6() / 1E6 + nodeAtoNodeBX * t);
        float Y = (float) (closestEdge.getSourceNode().getNodeLocation()
                .getLongitudeE6() / 1E6 + nodeAtoNodeBY * t);

        GeoPoint returnPoint = new GeoPoint((int) (X * 1E6), (int) (Y * 1E6));
        return new Pair<GeoPoint, MapEdge>(returnPoint, closestEdge);
    }
}