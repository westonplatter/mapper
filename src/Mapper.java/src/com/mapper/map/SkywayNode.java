// SkywayNode.java
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
public class SkywayNode
{

    private int nodeId;

    private GeoPoint nodeLocation;

    private ArrayList<SkywayEdge> adjacentSkywayEdges = new ArrayList<SkywayEdge>();

    /**
     * Constructs a node in the skyway
     * @param nodeId
     * @param nodeLocation
     */
    public SkywayNode(int nodeId, GeoPoint nodeLocation)
    {
        this.nodeId = nodeId;
        this.setNodeLocation(nodeLocation);
    }

    /**
     * Gets the identifier of the node.
     * @return
     */
    public int getNodeId()
    {
        return nodeId;
    }

    /**
     * Sets the node identifier
     * @param nodeId
     */
    public void setNodeId(int nodeId)
    {
        this.nodeId = nodeId;
    }

    /**
     * Gets the skyway edges that connect to this node.
     * @return
     */
    public ArrayList<SkywayEdge> getAdjacentSkywayEdges()
    {
        return adjacentSkywayEdges;
    }

    /**
     * Adds a skyway edge that connects to this node.
     * @param adjacentSkywayEdge
     */
    public void addAdjacentSkywayEdge(SkywayEdge adjacentSkywayEdge)
    {
        this.adjacentSkywayEdges.add(adjacentSkywayEdge);
    }

    /**
     * Gets the location of this node on the map.
     * @return
     */
    public GeoPoint getNodeLocation()
    {
        return nodeLocation;
    }

    /**
     * Gets the location of this node on the map.
     * @param nodeLocation
     */
    public void setNodeLocation(GeoPoint nodeLocation)
    {
        this.nodeLocation = nodeLocation;
    }

    /**
     * Compares this skyway node with another.
     * @param node  the other skyway node to compare with.
     * @return
     */
    public boolean compareTo(SkywayNode node)
    {
        if(this.getNodeLocation().equals(node.getNodeLocation()))
            return true;
        else
            return false;
    }

    /**
     * Compares this skyway node with another to determine if the locations of 
     * the nodes are the same.
     */
    @Override
    public boolean equals(Object other)
    {
        if(this.getClass() == other.getClass())
        {
            SkywayNode otherNode = (SkywayNode) other;
            if(this.getNodeLocation().equals(otherNode.getNodeLocation()))
            {
                return true;
            }
        }

        return false;
    }
}
