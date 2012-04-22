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
import com.mapper.exceptions.MapperInvalidArgumentException;
import com.mapper.util.MapperConstants;

/**
 * 
 * 
 * @author jonlee
 * 
 */
public class NodeDB {

	private int currentNodeCount = 0;
	private int currentEdgeCount = 0;

	private static final String LOG_TAG = "JonLee";

	private ArrayList<MapNode> nodeList = new ArrayList<MapNode>();
	private ArrayList<MapEdge> edgeList = new ArrayList<MapEdge>();

	/**
	 * Constructor
	 * 
	 * @param arrayList
	 * @throws MapperInvalidArgumentException
	 */
	public NodeDB(ArrayList<Pair<Pair<GeoPoint, GeoPoint>, Integer>> arrayList
			) throws MapperInvalidArgumentException {
		if (arrayList == null) {
			throw new MapperInvalidArgumentException(
					"NodeDB(arrayList : "
							+ "ArrayList<Pair<GeoPoint, GeoPoint>>): arrayList is null.");
		}

		for (Pair<Pair<GeoPoint, GeoPoint>,Integer> pair : arrayList) {
			// Skip ill-formed pairs in the list.
			if (pair == null || pair.first == null || pair.second == null)
				continue;

			GeoPoint point1 = pair.first.first;
			GeoPoint point2 = pair.first.second;

			MapNode mapNode1 = null;
			MapNode mapNode2 = null;

			if (nodeList.contains(point1)) {
				mapNode1 = nodeList.get(nodeList.indexOf(point1));
			} else {
				mapNode1 = new MapNode(currentNodeCount, point1);
				nodeList.add(mapNode1);
				++currentNodeCount;
			}

			if (nodeList.contains(point2)) {
				mapNode2 = nodeList.get(nodeList.indexOf(point2));
			} else {
				mapNode2 = new MapNode(currentNodeCount, point2);
				nodeList.add(mapNode2);
				++currentNodeCount;
			}

			MapEdge mapEdge = new MapEdge(currentEdgeCount, mapNode1, mapNode2);

			if (pair.second == MapperConstants.TUNNEL) {
				mapEdge.setTunnel(true);
			}
			if (pair.second == MapperConstants.OUTSIDE) {
				mapEdge.setOutside(true);
			}
			mapNode1.addAdjacentSkywayEdge(mapEdge);
			mapNode2.addAdjacentSkywayEdge(mapEdge);

			edgeList.add(mapEdge);

			++currentEdgeCount;
		}
	}

	/**
	 * Gets the list of nodes.
	 * 
	 * @return the list of nodes
	 */
	public ArrayList<MapNode> getNodeList() {
		return this.nodeList;
	}

	/**
	 * Gets the list of nodes.
	 * 
	 * @return the list of nodes
	 */
	public ArrayList<MapEdge> getEdgeList() {
		return this.edgeList;
	}

	/**
	 * Prints out a list of all nodes in this database to the log file.
	 */
	public void printNodeDB() {
		for (MapNode node : nodeList) {
			Log.v(LOG_TAG, String.valueOf(node.getNodeId()));
			for (MapEdge edge : node.getAdjacentEdges()) {
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
