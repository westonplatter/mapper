//SkywayNodeTest.java
/**
 * Copyright 2012 desilva
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

import java.util.List;

import junit.framework.Assert;

import com.google.android.maps.GeoPoint;
import com.mapper.map.SkywayEdge;
import com.mapper.map.SkywayNode;

import android.test.AndroidTestCase;

/**
 * @author desilva
 *
 */
public class SkywayNodeTest extends AndroidTestCase
{
    //CONSTANTS
    private static final int KELLER_LAT  = (int)(-93.232016 * 1E6);
    private static final int KELLER_LONG = (int)(44.974686 * 1E6);
    
    private static final int ARCH_LAT  = (int)(-93.23321 * 1E6);
    private static final int ARCH_LONG = (int)(44.97629 * 1E6);
    
    private static final int INIT_NODE_ID = 0;
    
    
    //PRIVATE DATA MEMBERS
    private GeoPoint nodePoint;
    private SkywayNode node;
    

    /* (non-Javadoc)
     * @see android.test.AndroidTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        nodePoint = new GeoPoint(KELLER_LAT, KELLER_LONG);
        node = new SkywayNode(INIT_NODE_ID, nodePoint);
    }

    /* (non-Javadoc)
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
     * Test method for {@link com.mapper.map.SkywayNode#getNodeId()}.  Tests
     * the constructor and getter.
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
     * Test method for {@link com.mapper.map.SkywayNode#getAdjacentSkywayEdges()}.
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
     * Test method for {@link com.mapper.map.SkywayNode#addAdjacentSkywayEdge(com.mapper.map.SkywayEdge)}.
     */
    public void testAddAdjacentSkywayEdge()
    {
        GeoPoint rapsonHall = new GeoPoint(ARCH_LAT, ARCH_LONG);
        SkywayEdge edge = new SkywayEdge(5, this.nodePoint, rapsonHall);
        node.addAdjacentSkywayEdge(edge);
        
        List<SkywayEdge> edges = node.getAdjacentSkywayEdges();
        Assert.assertNotNull(edges);
        Assert.assertEquals(1, edges.size());
        Assert.assertEquals(rapsonHall, edges.get(0));
    }

    /**
     * Test method for {@link com.mapper.map.SkywayNode#getNodeLocation()}.
     */
    public void testGetNodeLocation()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.mapper.map.SkywayNode#setNodeLocation(com.google.android.maps.GeoPoint)}.
     */
    public void testSetNodeLocation()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.mapper.map.SkywayNode#compareTo(com.mapper.map.SkywayNode)}.
     */
    public void testCompareTo()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link com.mapper.map.SkywayNode#equals(java.lang.Object)}.
     */
    public void testEqualsObject()
    {
        fail("Not yet implemented");
    }

}
