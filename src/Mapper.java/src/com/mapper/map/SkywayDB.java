// SkywayDB.java
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
public class SkywayDB
{

    private int currentNodeCount = 0;
    private int currentEdgeCount = 0;

    private static final String LOG_TAG = "JonLee";

    private ArrayList<SkywayNode> skywayNodeList = new ArrayList<SkywayNode>();

    /**
     * Constructor
     * @param arrayList
     */
    public SkywayDB(ArrayList<Pair<GeoPoint, GeoPoint>> arrayList)
    {
        for(Pair<GeoPoint, GeoPoint> pair : arrayList)
        {

            GeoPoint point1 = (GeoPoint) pair.first;
            GeoPoint point2 = (GeoPoint) pair.second;

            SkywayEdge skywayEdge = new SkywayEdge(currentEdgeCount, point1,
                    point2);

            SkywayNode skywayNode = null;

            if(skywayNodeList.contains(point1))
            {
                skywayNode = skywayNodeList.get(skywayNodeList.indexOf(point1));
                skywayNode.addAdjacentSkywayEdge(skywayEdge);
            }
            else
            {
                skywayNode = new SkywayNode(currentNodeCount, point1);
                skywayNode.addAdjacentSkywayEdge(skywayEdge);
                skywayNodeList.add(skywayNode);
                ++currentNodeCount;
            }

            if(skywayNodeList.contains(point2))
            {
                skywayNode = skywayNodeList.get(skywayNodeList.indexOf(point2));
                skywayNode.addAdjacentSkywayEdge(skywayEdge);
            }
            else
            {
                skywayNode = new SkywayNode(currentNodeCount, point2);
                skywayNode.addAdjacentSkywayEdge(skywayEdge);
                skywayNodeList.add(skywayNode);
                ++currentNodeCount;
            }

            ++currentEdgeCount;
        }
    }

    /**
     * Gets the list of skyway nodes.
     * @return  the list of nodes in the skyway
     */
    public ArrayList<SkywayNode> getSkyway()
    {
        return this.skywayNodeList;
    }

    /**
     * 
     */
    public void printSkywayDB()
    {
        for(SkywayNode node : skywayNodeList)
        {
            Log.v(LOG_TAG, String.valueOf(node.getNodeId()));
            for(SkywayEdge edge : node.getAdjacentSkywayEdges())
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
