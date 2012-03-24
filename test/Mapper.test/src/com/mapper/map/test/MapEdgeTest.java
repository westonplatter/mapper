// MapEdgeTest.java
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

import com.google.android.maps.GeoPoint;
import com.mapper.map.MapEdge;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author desilva
 * 
 */
public abstract class MapEdgeTest extends TestCase
{
    protected MapEdge edgeUnderTest;

    protected GeoPoint edgePoint1;
    protected GeoPoint edgePoint2;

    protected double edgeLength;
    protected int edgeUniqueId;

    // /* (non-Javadoc)
    // * @see junit.framework.TestCase#setUp()
    // */
    // protected void setUp() throws Exception
    // {
    // super.setUp();
    // }
    //
    // /* (non-Javadoc)
    // * @see junit.framework.TestCase#tearDown()
    // */
    // protected void tearDown() throws Exception
    // {
    // super.tearDown();
    // }

    /**
     * Test method for {@link com.mapper.map.MapEdge#getFirstNode()}.
     */
    public void testGetFirstNode()
    {
        GeoPoint firstNode = edgeUnderTest.getFirstNode();
        Assert.assertEquals(this.edgePoint1, firstNode);
    }

    /**
     * Test method for {@link com.mapper.map.MapEdge#getSecondNode()}.
     */
    public void testGetSecondNode()
    {
        GeoPoint secondNode = edgeUnderTest.getSecondNode();
        Assert.assertEquals(this.edgePoint2, secondNode);
    }

//    /**
//     * Test method for {@link com.mapper.map.MapEdge#getDistance()}.
//     */
//    public void testGetDistance()
//    {
//        Assert.assertEquals(this.edgeLength, this.edgeUnderTest.getDistance());
//    }

    /**
     * Test method for {@link com.mapper.map.MapEdge#getUniqueID()}.
     */
    public void testGetUniqueID()
    {
        Assert.assertEquals(this.edgeUniqueId, this.edgeUnderTest.getUniqueID());
    }

    /**
     * Test method for {@link com.mapper.map.MapEdge#setUniqueID(int)}.
     */
    public void testSetUniqueID()
    {
        int newUniqueId = this.edgeUniqueId + 1;
        this.edgeUnderTest.setUniqueID(newUniqueId);
        Assert.assertTrue(this.edgeUnderTest.getUniqueID() != this.edgeUniqueId);
        Assert.assertEquals(newUniqueId, this.edgeUnderTest.getUniqueID());
    }

    /**
     * Test method for
     * {@link com.mapper.map.MapEdge#equals(java.lang.Object)}.  Ensures that
     * the equality check works if the object to be compared is the exact same
     * object as the one we are comparing against.
     */
    public void testEqualsObject_Same()
    {
        Assert.assertTrue(this.edgeUnderTest.equals(this.edgeUnderTest));
    }

    /**
     * Test method for
     * {@link com.mapper.map.MapEdge#equals(java.lang.Object)}.  Ensures that
     * the equality check works if the object to be compared has the exact same
     * data as that of the original.
     */
    public void testEqualsObject_Identical()
    {
        MapEdge edgeCopy = new MapEdge(this.edgeUnderTest);
        Assert.assertTrue(this.edgeUnderTest.equals(edgeCopy));
    }

    /**
     * Test method for
     * {@link com.mapper.map.MapEdge#equals(java.lang.Object)}.  Ensures that
     * the equality check fails if the object to be compared has a different 
     * unique id. 
     */
    public void testEqualsObject_DiffUniqueId()
    {
        MapEdge edgeCopy = new MapEdge(this.edgeUnderTest);
        edgeCopy.setUniqueID(edgeCopy.getUniqueID() + 1);
        Assert.assertFalse(this.edgeUnderTest.equals(edgeCopy));
    }

    /**
     * Test method for
     * {@link com.mapper.map.MapEdge#equals(java.lang.Object)}.  Ensures that
     * the equality check fails if the object to be compared is a different 
     * object. 
     */
    public void testEqualsObject_DiffObject()
    {
        String str = new String("Test object");
        Assert.assertFalse(this.edgeUnderTest.equals(str));
    }

    /**
     * Test method for
     * {@link com.mapper.map.MapEdge#equals(java.lang.Object)}.  Ensures that
     * the equality check fails if the object to be compared has a different
     * GeoPoint for the first node. 
     */
    public void testEqualsObject_DiffGeoPoint1()
    {
        int uniqueId = this.edgeUnderTest.getUniqueID();
        GeoPoint node1 = this.edgeUnderTest.getFirstNode();
        GeoPoint node2 = this.edgeUnderTest.getSecondNode();

        GeoPoint newNode1 = new GeoPoint(node1.getLatitudeE6() + 1,
                node1.getLongitudeE6());
        MapEdge edgeCopy = new MapEdge(uniqueId, newNode1, node2);
        Assert.assertFalse(this.edgeUnderTest.equals(edgeCopy));
    }

    /**
     * Test method for
     * {@link com.mapper.map.MapEdge#equals(java.lang.Object)}.  Ensures that
     * the equality check fails if the object to be compared has a different 
     * GeoPoint for the second node. 
     */
    public void testEqualsObject_DiffGeoPoint2()
    {
        int uniqueId = this.edgeUnderTest.getUniqueID();
        GeoPoint node1 = this.edgeUnderTest.getFirstNode();
        GeoPoint node2 = this.edgeUnderTest.getSecondNode();

        GeoPoint newNode2 = new GeoPoint(node2.getLatitudeE6() + 1,
                node2.getLongitudeE6());
        MapEdge edgeCopy = new MapEdge(uniqueId, node1, newNode2);
        Assert.assertFalse(this.edgeUnderTest.equals(edgeCopy));
    }

//    /**
//     * Test method for
//     * {@link com.mapper.map.MapEdge#equals(java.lang.Object)}.  Ensures that
//     * the equality check fails if the object to be compared has a different 
//     * distance between nodes (GeoPoint1 and GeoPoint2), even though the 
//     * GeoPoints are identical.  It does this using Java reflection.
//     * 
//     * @throws NoSuchFieldException
//     * @throws SecurityException
//     * @throws IllegalAccessException
//     * @throws IllegalArgumentException
//     */
//    public void testEqualsObject_DiffDist() throws SecurityException,
//            NoSuchFieldException, IllegalArgumentException,
//            IllegalAccessException
//    {
//        MapEdge edgeCopy = new MapEdge(this.edgeUnderTest);
//
//        double edgeLength = edgeCopy.getDistance();
//        
//        //Use Java reflection to update the distanceInMeters field of the copied
//        //  skyway edge so we can test that the check works in the .equals 
//        //  method.  If this field gets changed, the string in getField() needs
//        //  to be updated.
//        Class<? extends MapEdge> edgeClass = edgeCopy.getClass();
//        edgeClass.getField("distanceInMeters").setDouble(edgeCopy,
//                edgeLength - 1);
//
//        Assert.assertFalse(this.edgeUnderTest.equals(edgeCopy));
//    }
}
