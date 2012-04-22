// MapEdge.java
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
package com.mapper.map;

import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.mapper.util.MapUtilities;

/**
 * Edge in the skyway graph.
 * 
 * @author jonlee
 * 
 */
public class MapEdge
{
    private int uniqueID;
//    private GeoPoint point1;
//    private GeoPoint point2;
    private MapNode node1;
    private MapNode node2;
    private double distanceInMeters;
    private boolean isTunnel;
    private boolean isOutside;

//    /**
//     * Constructor
//     * 
//     * @param uniqueId
//     * @param point1
//     *            the first point on the map making the edge
//     * @param point2
//     *            the second point making up the edge.
//     */
//    public MapEdge(int uniqueId, GeoPoint point1, GeoPoint point2)
//    {
//        this.uniqueID = uniqueId;
//        this.point1 = point1;
//        this.point2 = point2;
//
//        distanceInMeters = this.calculateLength();
//    }

    /**
     * Constructor
     * 
     * @param uniqueId
     * @param point1
     *            the first node on the map making the edge
     * @param point2
     *            the second node making up the edge.
     */
    public MapEdge(int uniqueId, MapNode point1, MapNode point2)
    {
        this.uniqueID = uniqueId;
//        this.point1 = point1.getNodeLocation();
//        this.point2 = point2.getNodeLocation();
        this.node1 = point1;
        this.node2 = point2;
        distanceInMeters = this.calculateLength();
    }

    /**
     * Copy constructor
     * 
     * @param that
     *            the {@link MapEdge} object to copy.
     */
    public MapEdge(MapEdge that)
    {
        this.uniqueID = that.uniqueID;
//        this.point1 = that.point1;
//        this.point2 = that.point2;
        this.node1 = that.node1;
        this.node2 = that.node2;
        this.distanceInMeters = that.distanceInMeters;
    }

    /**
     * Gets the first node of the edge
     * 
     * @return
     */
    public GeoPoint getFirstNode()
    {
        return this.node1.getNodeLocation();
    }

    /**
     * Gets the second node of the edge
     * 
     * @return
     */
    public GeoPoint getSecondNode()
    {
        return this.node2.getNodeLocation();
    }

    /**
     * Gets the first node of the edge
     * 
     * @return
     */
    public MapNode getSourceNode()
    {
        return node1;
    }

    /**
     * Gets the second node of the edge
     * 
     * @return
     */
    public MapNode getTargetNode()
    {
        return node2;
    }
    /**
     * Get the distance between the two points making up this edge.
     * 
     * @return
     */
    public double getDistance()
    {
        return distanceInMeters;
    }

    /**
     * Gets the unique identifier.
     * 
     * @return
     */
    public int getUniqueID()
    {
        return uniqueID;
    }

    /**
     * Sets the unique identifier.
     * 
     * @param uniqueID
     */
    public void setUniqueID(int uniqueID)
    {
        this.uniqueID = uniqueID;
    }

    @Override
    public boolean equals(Object anObject)
    {
        // Is this slower than getting the class and comparing those (using
        // reflection)?
        if(!(anObject instanceof MapEdge))
            return false;

        MapEdge that = (MapEdge) anObject;

        if(!this.node1.equals(that.node1))
            return false;
        if(!this.node2.equals(that.node2))
            return false;
        if(this.uniqueID != that.uniqueID)
            return false;
        if(this.distanceInMeters != that.distanceInMeters)
            return false;

        return true;

    }

    // Private methods.
    /**
     * Calculates the distance between the nodes at either end of the edge.
     * 
     * @return the length of the edge.
     */
    private double calculateLength()
    {
        Location location = new Location("");

        GeoPoint node1Location = node1.getNodeLocation();

        location.setLatitude(MapUtilities.convertToStdCoordinate(node1Location
                .getLatitudeE6()));
        location.setLongitude(MapUtilities.convertToStdCoordinate(node1Location
                .getLongitudeE6()));

        Location location2 = new Location("");
        GeoPoint node2Location = node2.getNodeLocation();
        location.setLatitude(MapUtilities.convertToStdCoordinate(node2Location
                .getLatitudeE6()));
        location.setLongitude(MapUtilities.convertToStdCoordinate(node2Location
                .getLongitudeE6()));

        return location.distanceTo(location2);
    }

	public boolean isOutside() {
		return isOutside;
	}

	public void setOutside(boolean isOutside) {
		this.isOutside = isOutside;
	}

	public boolean isTunnel() {
		return isTunnel;
	}

	public void setTunnel(boolean isTunnel) {
		this.isTunnel = isTunnel;
	}
}