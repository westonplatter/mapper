// MapNode.java
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

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;

/**
 * 
 * @author jonlee
 * 
 */
public class MapNode
{

    private int nodeId;

    private GeoPoint nodeLocation;

    private ArrayList<MapEdge> adjacentSkywayEdges = new ArrayList<MapEdge>();

    private double minDistance;

    /**
     * Constructs a node
     * 
     * @param nodeId
     * @param nodeLocation
     */
    public MapNode(int nodeId, GeoPoint nodeLocation)
    {
        this.nodeId = nodeId;
        this.setNodeLocation(nodeLocation);
    }

    /**
     * Gets the identifier of the node.
     * 
     * @return
     */
    public int getNodeId()
    {
        return nodeId;
    }

    /**
     * Sets the node identifier
     * 
     * @param nodeId
     */
    public void setNodeId(int nodeId)
    {
        this.nodeId = nodeId;
    }

    /**
     * Gets the skyway edges that connect to this node.
     * 
     * @return
     */
    public ArrayList<MapEdge> getAdjacentEdges()
    {
        return adjacentSkywayEdges;
    }

    /**
     * Adds an edge that connects to this node.
     * 
     * @param adjacentEdge
     */
    public void addAdjacentSkywayEdge(MapEdge adjacentEdge)
    {
        this.adjacentSkywayEdges.add(adjacentEdge);
    }

    /**
     * Gets the location of this node on the map.
     * 
     * @return
     */
    public GeoPoint getNodeLocation()
    {
        return nodeLocation;
    }

    /**
     * Gets the location of this node on the map.
     * 
     * @param nodeLocation
     */
    public void setNodeLocation(GeoPoint nodeLocation)
    {
        this.nodeLocation = nodeLocation;
    }

    // This could be an issue in the future since this is the method name used
    // by
    // Comparable. I don't see where it is used, so commented it out for
    // now.
    // /**
    // * Compares this skyway node with another.
    // * @param node the other skyway node to compare with.
    // * @return
    // */
    // public boolean compareTo(SkywayNode node)
    // {
    // if(this.getNodeLocation().equals(node.getNodeLocation()))
    // return true;
    // else
    // return false;
    // }

    /**
     * Compares this node with another to determine if the locations of the
     * nodes are the same.
     */
    @Override
    public boolean equals(Object other)
    {
        if(this.getClass() == other.getClass())
        {
            MapNode otherNode = (MapNode) other;
            if(this.getNodeLocation().equals(otherNode.getNodeLocation()))
            {
                return true;
            }
            else if(this.getNodeId() == otherNode.getNodeId())
            {
                return true;
            }
        }

        return false;
    }

    public String toString()
    {
        return (double) nodeLocation.getLatitudeE6() / 1000000 + " "
                + (double) nodeLocation.getLongitudeE6() / 1000000;
        // return String.valueOf(nodeId);
    }

    public int compareTo(MapNode other)
    {
        // TODO Auto-generated method stub
        return Double.compare(minDistance, other.minDistance);

    }
}
