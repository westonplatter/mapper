//MplsSkywayMapActivityTest.java
/**
 * Copyright 2012, Ian De Silva
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *    
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mapper.activities.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import junit.framework.Assert;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.util.Pair;

import com.google.android.maps.GeoPoint;
import com.google.android.testing.mocking.AndroidMock;
import com.google.android.testing.mocking.UsesMocks;
import com.mapper.activities.MplsSkywayMapActivity;
import com.mapper.map.MapEdge;
import com.mapper.map.MapNode;
import com.mapper.map.NodeDB;
import com.mapper.util.MapUtilities;

/**
 * @author desilva
 *
 */
public class MplsSkywayMapActivityTest extends 
    //ActivityInstrumentationTestCase2<MplsSkywayMapActivity>
    ActivityUnitTestCase<MplsSkywayMapActivity>
{
    //Private Data Members
    private MplsSkywayMapActivity activity;
    
    /**
     * @param activityClass
     */
    public MplsSkywayMapActivityTest()
    {
        super(MplsSkywayMapActivity.class);
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
        //setActivityInitialTouchMode(false)
        
        startActivity(new Intent(), null, null);
        
        this.activity = this.getActivity();
        if(activity == null)
        {
            throw new Exception("Activity was null");
        }
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test method for {@link com.mapper.activities.MplsSkywayMapActivity#isRouteDisplayed()}.
     * @throws NoSuchMethodException  if there is no method called 
     *      isRouteDisplayed in {@link MplsSkywayMapActivity}. 
     * @throws SecurityException 
     * @throws InvocationTargetException  if there was an issue invoking 
     *      isRouteDisplayedMethod().
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public void testIsRouteDisplayed() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        Class<? extends MplsSkywayMapActivity> activityClass = this.activity.getClass();
        
//        StringBuilder debugStr = new StringBuilder();
//        Method[] methods = activityClass.getDeclaredMethods();
//        for(Method method : methods)
//        {
//            debugStr.append(method.getName());
//            debugStr.append(" ");
//        }
//        throw new SecurityException(debugStr.toString());
        
        Method isRouteDisplayedMethod = 
                activityClass.getDeclaredMethod("isRouteDisplayed", new Class<?>[0]);
        Boolean isDisplayed = 
                (Boolean) isRouteDisplayedMethod.invoke(this.activity, 
                                                        new Object[0]);
        Assert.assertFalse(isDisplayed);
    }

    /**
     * Test method for {@link com.mapper.activities.MplsSkywayMapActivity#onCreate(android.os.Bundle)}.
     */
    @UsesMocks(NodeDB.class)
    public void testOnCreateBundle()
    {
        ArrayList<Pair<GeoPoint,GeoPoint>> pointPairsList = 
                        new ArrayList<Pair<GeoPoint,GeoPoint>>();
        
        //Create the GeoPoints for the constructors
        GeoPoint point1 = new GeoPoint(MapUtilities.convertToE6Coordinate(-93.275681),
                MapUtilities.convertToE6Coordinate(44.976124));
        
        GeoPoint point2 = new GeoPoint(MapUtilities.convertToE6Coordinate(-93.275940),
                        MapUtilities.convertToE6Coordinate(44.975780));
        
        GeoPoint point3 = new GeoPoint(MapUtilities.convertToE6Coordinate(-93.276100),
                        MapUtilities.convertToE6Coordinate(44.975845));
        
        pointPairsList.add(new Pair<GeoPoint, GeoPoint>(point1, point2));
        pointPairsList.add(new Pair<GeoPoint, GeoPoint>(point2, point3));
        
        //Create the mock object
        NodeDB nodeDBMock = AndroidMock.createMock(NodeDB.class, pointPairsList);
        

        //Create the nodes list
        ArrayList<MapNode> nodeListValue = new ArrayList<MapNode>();
        MapNode node1 = new MapNode(1, point1);
        MapNode node2 = new MapNode(2, point2);
        MapNode node3 = new MapNode(3, point3);
        
        //Create the edges
        MapEdge edge1_2 = new MapEdge(12, node1, node2);
        MapEdge edge2_3 = new MapEdge(23, node2, node3);
        
        //Add the edges to the nodes
        node1.addAdjacentSkywayEdge(edge1_2);
        node2.addAdjacentSkywayEdge(edge1_2);
        node2.addAdjacentSkywayEdge(edge2_3);
        node3.addAdjacentSkywayEdge(edge2_3);
        
        //Add the nodes to the list.
        nodeListValue.add(node1);
        nodeListValue.add(node2);
        nodeListValue.add(node3);
        
        
        //Add the getNodeList() mock method to the NodeDB so it returns the 
        //  node list we just created.
        AndroidMock.expect(nodeDBMock.getNodeList()).andReturn(nodeListValue);
        
        getActivity().onCreate(null);
        
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.mapper.activities.MplsSkywayMapActivity#onCreateOptionsMenu(android.view.Menu)}.
     */
    public void testOnCreateOptionsMenuMenu()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.mapper.activities.MplsSkywayMapActivity#onOptionsItemSelected(android.view.MenuItem)}.
     */
    public void testOnOptionsItemSelectedMenuItem()
    {
        fail("Not yet implemented");
    }

}
