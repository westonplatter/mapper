//NodeDBTest.java
/**
 * Copyright 2012 Jared Swanson
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
package com.mapper.map.test;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;
import com.mapper.exceptions.MapperInvalidArgumentException;
import com.mapper.map.MapNode;
import com.mapper.map.NodeDB;

import android.test.AndroidTestCase;
import android.util.Pair;

/**
 * @author Jared
 *
 */
public class NodeDBTest extends AndroidTestCase
{

    /* (non-Javadoc)
     * @see android.test.AndroidTestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
    }

    /* (non-Javadoc)
     * @see android.test.AndroidTestCase#tearDown()
     */
    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    /**
     * Test method for {@link com.mapper.map.NodeDB#NodeDB(java.util.ArrayList)}.
     * Ensures that it detects when the passed in ArrayList is not null.
     */
    public void testNodeDB_argNotNull()
    {
        
        try
        {
            @SuppressWarnings("unused")
            NodeDB nodeDbTest = new NodeDB(null);
        }
        catch (MapperInvalidArgumentException e) 
        {
            return;
        }
        fail("NodeDB(): Expected exception for null arrayList.  None thrown.");
    }
    
    /**
     * Test method for {@link com.mapper.map.NodeDB#NodeDB(java.util.ArrayList)}.  
     * Checks if, when a non-null list is passed to NodeDB's constructor, that
     * it works correctly. 
     * @throws MapperInvalidArgumentException 
     */
    public void testNodeDB_listEmpty() throws MapperInvalidArgumentException
    {
        //Initialize NodeDB with an empty list.
        NodeDB nodeDbTest = new NodeDB(new ArrayList<Pair<GeoPoint, GeoPoint>>());
        ArrayList<MapNode> nodeList = nodeDbTest.getNodeList();
        
        assertNotNull(nodeList);
        assertEquals(0, nodeList.size());
    }
    
    /**
     * Test method for {@link com.mapper.map.NodeDB#NodeDB(java.util.ArrayList)}.  
     * Checks if, when a non-null list is passed to NodeDB's constructor, that
     * it works correctly. 
     * @throws MapperInvalidArgumentException 
     */
    public void testNodeDB_pointListContainsNull() throws MapperInvalidArgumentException
    {
        ArrayList<Pair<GeoPoint, GeoPoint>> pointList = 
                    new ArrayList<Pair<GeoPoint, GeoPoint>>();
        pointList.add(null);
        NodeDB nodeDbTest = new NodeDB(pointList);
        ArrayList<MapNode> nodeList = nodeDbTest.getNodeList();
        
        assertNotNull(nodeList);
        assertEquals(0, nodeList.size());
    }
    
    /**
     * Test method for {@link com.mapper.map.NodeDB#NodeDB(java.util.ArrayList)}.  
     * Checks if, when a non-null list is passed to NodeDB's constructor, that
     * it works correctly. 
     * @throws MapperInvalidArgumentException 
     */
    public void testNodeDB_pointListPoint1IsNull() throws MapperInvalidArgumentException
    {
        ArrayList<Pair<GeoPoint, GeoPoint>> pointList = 
                    new ArrayList<Pair<GeoPoint, GeoPoint>>();
        GeoPoint kellerHall = new GeoPoint(MapNodeTest.KELLER_LAT, 
                                           MapNodeTest.KELLER_LONG);
        pointList.add(new Pair<GeoPoint, GeoPoint>(null, kellerHall));
        NodeDB nodeDbTest = new NodeDB(pointList);
        ArrayList<MapNode> nodeList = nodeDbTest.getNodeList();
        
        assertNotNull(nodeList);
        assertEquals(0, nodeList.size());
    }
    
    
    /**
     * Test method for {@link com.mapper.map.NodeDB#NodeDB(java.util.ArrayList)}.  
     * Checks if, when a non-null list is passed to NodeDB's constructor, that
     * it works correctly. 
     * @throws MapperInvalidArgumentException 
     */
    public void testNodeDB_pointListPoint2IsNull() throws MapperInvalidArgumentException
    {
        ArrayList<Pair<GeoPoint, GeoPoint>> pointList = 
                    new ArrayList<Pair<GeoPoint, GeoPoint>>();
        GeoPoint kellerHall = new GeoPoint(MapNodeTest.KELLER_LAT, 
                                           MapNodeTest.KELLER_LONG);
        pointList.add(new Pair<GeoPoint, GeoPoint>(kellerHall, null));
        NodeDB nodeDbTest = new NodeDB(pointList);
        ArrayList<MapNode> nodeList = nodeDbTest.getNodeList();
        
        assertNotNull(nodeList);
        assertEquals(0, nodeList.size());
    }
}
