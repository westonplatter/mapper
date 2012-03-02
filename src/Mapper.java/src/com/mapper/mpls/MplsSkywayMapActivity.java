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
package com.mapper.mpls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Pair;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.mapper.map.SkywayDB;
import com.mapper.map.SkywayEdge;
import com.mapper.map.SkywayNode;
import com.mapper.map.SkywayOverlay;
import com.mapper.yelp.YelpQueryManager;
import com.skyway.mpls.R;

/**
 * 
 * @author jonlee
 *
 */
public class MplsSkywayMapActivity extends MapActivity
{

    private static SkywayDB skywayDB;

    private static double MapCenterLatitude = 44.975667;
    private static double MapCenterLongitude = -93.270793;

    /** 
     * Called when the activity is first created.
     * @param savedInstanceState 
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(false);

        // get MapOverlap Object List
        List<Overlay> mapOverlays = mapView.getOverlays();

        // get skyway from adaptation
        skywayDB = new SkywayDB(readSkywayAdaptation());

        // skywayDB.printSkywayDB();

        ArrayList<SkywayNode> skyway = skywayDB.getSkyway();
        ArrayList<Integer> alreadyDrawnSkyways = new ArrayList<Integer>();

        for(SkywayNode node : skyway)
        {
            for(SkywayEdge edge : node.getAdjacentSkywayEdges())
            {
                if(!alreadyDrawnSkyways.contains(edge.getUniqueID()))
                {
                    mapOverlays.add(new SkywayOverlay(edge.getFirstNode(), edge
                            .getSecondNode()));
                    alreadyDrawnSkyways.add(edge.getUniqueID());
                }
            }
        }

        // get Map Controller to set location and zoom
        MapController mc = mapView.getController();

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
        GeoPoint p = new GeoPoint((int) (MapCenterLatitude * 1000000),
                (int) (MapCenterLongitude * 1000000));
        mc.animateTo(p);
        mc.setZoom(15);
    }

    /**
     * 
     */
    @Override
    protected boolean isRouteDisplayed()
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * 
     * @return
     */
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

    /**
     * 
     * @param activity
     * @return
     * @throws XmlPullParserException
     * @throws IOException
     */
    private ArrayList<String> getEventsFromAnXML(Activity activity)
            throws XmlPullParserException, IOException
    {
        ArrayList<String> coordinateList = new ArrayList<String>();
        Resources res = activity.getResources();
        XmlResourceParser xpp = res.getXml(R.xml.myxml);
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

}