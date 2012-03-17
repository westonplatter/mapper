// SkywayNodeTest.java
/**
 * Copyright 2012 desilva
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
package com.mapper.map.test;

import java.util.List;

import junit.framework.Assert;

import com.google.android.maps.GeoPoint;
import com.mapper.map.SkywayEdge;
import com.mapper.map.SkywayNode;
import com.mapper.util.MapUtilities;

import android.test.AndroidTestCase;

/**
 * @author desilva
 * 
 */
public class SkywayNodeTest extends AndroidTestCase
{
    // CONSTANTS
    // These are the E6 Latitude and Longitudes for Keller Hall at the
    // University
    // of Minnesota and the Architecture Building (Rapson Hall).
    public static final int KELLER_LAT = 
                                MapUtilities.convertToE6Coordinate(-93.232016);
    public static final int KELLER_LONG = 
                                MapUtilities.convertToE6Coordinate(44.974686);

    public static final int ARCH_LAT = 
                                MapUtilities.convertToE6Coordinate(-93.23321);
    public static final int ARCH_LONG = 
                                MapUtilities.convertToE6Coordinate(44.97629);

    private static final int INIT_NODE_ID = 0;

    // PRIVATE DATA MEMBERS
    private GeoPoint nodePoint;
    private SkywayNode node;

    /*
     * (non-Javadoc)
     * 
     * @see android.test.AndroidTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        nodePoint = new GeoPoint(KELLER_LAT, KELLER_LONG);
        node = new SkywayNode(INIT_NODE_ID, nodePoint);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.test.AndroidTestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception
    {
        super.tearDown();
        nodePoint = null;
        node = null;
    }

    /**
     * Test method for {@link com.mapper.map.SkywayNode#getNodeId()}. Tests the
     * constructor and getter.
     */
    public void testGetNodeId()
    {
        Assert.assertEquals(INIT_NODE_ID, node.getNodeId());
    }

    /**
     * Test method for {@link com.mapper.map.SkywayNode#setNodeId(int)}.
     */
    public void testSetNodeId()
    {
        int newNodeId = -40;
        node.setNodeId(newNodeId);
        Assert.assertEquals(newNodeId, node.getNodeId());
    }

    /**
     * Test method for
     * {@link com.mapper.map.SkywayNode#getAdjacentSkywayEdges()}.
     * 
     * Verifies that the skyway edge list, after node initialization, is empty.
     */
    public void testGetAdjacentSkywayEdges_initialized()
    {
        List<SkywayEdge> edges = node.getAdjacentSkywayEdges();
        Assert.assertNotNull(edges);
        Assert.assertEquals(0, edges.size());
    }

    /**
     * Test method for
     * {@link com.mapper.map.SkywayNode#addAdjacentSkywayEdge(com.mapper.map.SkywayEdge)}
     * .
     */
    public void testAddAdjacentSkywayEdge()
    {
        GeoPoint rapsonHall = new GeoPoint(ARCH_LAT, ARCH_LONG);
        SkywayEdge gopherEdge = new SkywayEdge(5, this.nodePoint, rapsonHall);
        node.addAdjacentSkywayEdge(gopherEdge);

        List<SkywayEdge> edges = node.getAdjacentSkywayEdges();
        Assert.assertNotNull(edges);
        Assert.assertEquals(1, edges.size());
        Assert.assertEquals(gopherEdge, edges.get(0));
    }

    /**
     * Test method for {@link com.mapper.map.SkywayNode#getNodeLocation()}.
     */
    public void testGetNodeLocation()
    {
        // Tests constructor.
        Assert.assertEquals(this.nodePoint, this.node.getNodeLocation());
    }

    /**
     * Test method for
     * {@link com.mapper.map.SkywayNode#setNodeLocation(com.google.android.maps.GeoPoint)}
     * .
     */
    public void testSetNodeLocation()
    {
        GeoPoint rapsonHall = new GeoPoint(ARCH_LAT, ARCH_LONG);
        this.node.setNodeLocation(rapsonHall);
        Assert.assertEquals(rapsonHall, this.node.getNodeLocation());
    }

    /**
     * Test method for
     * {@link com.mapper.map.SkywayNode#equals(java.lang.Object)}.
     */
    public void testEqualsObject_different()
    {
        SkywayNode rapsonHallNode = new SkywayNode(INIT_NODE_ID + 1,
                new GeoPoint(ARCH_LAT, ARCH_LONG));
        Assert.assertFalse(this.node.equals(rapsonHallNode));
    }

    /**
     * Test method for
     * {@link com.mapper.map.SkywayNode#equals(java.lang.Object)}.
     */
    public void testEqualsObject_differentLoc()
    {
        SkywayNode rapsonHallNode = new SkywayNode(INIT_NODE_ID, new GeoPoint(
                ARCH_LAT, ARCH_LONG));
        Assert.assertFalse(this.node.equals(rapsonHallNode));
    }

    /**
     * Test method for
     * {@link com.mapper.map.SkywayNode#equals(java.lang.Object)}.
     */
    public void testEqualsObject_differentId()
    {
        // TODO -- Is this a correct assumption that it will fail if the nodes
        // have different IDs?
        SkywayNode rapsonHallNode = new SkywayNode(INIT_NODE_ID + 1,
                new GeoPoint(KELLER_LAT, KELLER_LONG));
        Assert.assertFalse(this.node.equals(rapsonHallNode));
    }

    /**
     * Test method for
     * {@link com.mapper.map.SkywayNode#equals(java.lang.Object)}.
     */
    public void testEqualsObject_sameData()
    {
        SkywayNode rapsonHallNode = new SkywayNode(INIT_NODE_ID, new GeoPoint(
                KELLER_LAT, KELLER_LONG));
        Assert.assertTrue(this.node.equals(rapsonHallNode));
    }

    /**
     * Test method for
     * {@link com.mapper.map.SkywayNode#equals(java.lang.Object)}.
     */
    public void testEqualsObject_identical()
    {
        Assert.assertTrue(this.node.equals(this.node));
    }
}
