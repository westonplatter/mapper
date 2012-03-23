// NodeDB.java
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

import android.util.Log;
import android.util.Pair;

import com.google.android.maps.GeoPoint;

/**
 * 
 * 
 * @author jonlee
 *
 */
public class NodeDB
{

    private int currentNodeCount = 0;
    private int currentEdgeCount = 0;

    private static final String LOG_TAG = "JonLee";

    private ArrayList<MapNode> nodeList = new ArrayList<MapNode>();

    /**
     * Constructor
     * @param arrayList
     */
    public NodeDB(ArrayList<Pair<GeoPoint, GeoPoint>> arrayList)
    {
        for(Pair<GeoPoint, GeoPoint> pair : arrayList)
        {

            GeoPoint point1 = (GeoPoint) pair.first;
            GeoPoint point2 = (GeoPoint) pair.second;

            MapEdge mapEdge = new MapEdge(currentEdgeCount, point1,
                    point2);

            MapNode mapNode = null;

            if(nodeList.contains(point1))
            {
                mapNode = nodeList.get(nodeList.indexOf(point1));
                mapNode.addAdjacentSkywayEdge(mapEdge);
            }
            else
            {
                mapNode = new MapNode(currentNodeCount, point1);
                mapNode.addAdjacentSkywayEdge(mapEdge);
                nodeList.add(mapNode);
                ++currentNodeCount;
            }

            if(nodeList.contains(point2))
            {
                mapNode = nodeList.get(nodeList.indexOf(point2));
                mapNode.addAdjacentSkywayEdge(mapEdge);
            }
            else
            {
                mapNode = new MapNode(currentNodeCount, point2);
                mapNode.addAdjacentSkywayEdge(mapEdge);
                nodeList.add(mapNode);
                ++currentNodeCount;
            }

            ++currentEdgeCount;
        }
    }

    /**
     * Gets the list of nodes.
     * @return  the list of nodes
     */
    public ArrayList<MapNode> getNodeList()
    {
        return this.nodeList;
    }

    /**
     * Prints out a list of all nodes in this database to the log file.
     */
    public void printNodeDB()
    {
        for(MapNode node : nodeList)
        {
            Log.v(LOG_TAG, String.valueOf(node.getNodeId()));
            for(MapEdge edge : node.getAdjacentEdges())
            {
                Log.v(LOG_TAG,
                        "  "
                                + String.valueOf((double) edge.getFirstNode()
                                        .getLatitudeE6() / 1000000)
                                + " "
                                + String.valueOf((double) edge.getFirstNode()
                                        .getLongitudeE6() / 1000000));
                Log.v(LOG_TAG,
                        "  "
                                + String.valueOf((double) edge.getSecondNode()
                                        .getLatitudeE6() / 1000000)
                                + " "
                                + String.valueOf((double) edge.getSecondNode()
                                        .getLongitudeE6() / 1000000));

            }
        }
    }
}
