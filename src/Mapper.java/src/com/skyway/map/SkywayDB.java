package com.skyway.map;

import java.util.ArrayList;


import android.util.Log;
import android.util.Pair;

import com.google.android.maps.GeoPoint;

public class SkywayDB {

	private int currentNodeCount = 0;
	private int currentEdgeCount = 0;

	private static final String LOG_TAG = "JonLee";

	private ArrayList<SkywayNode> skywayNodeList = new ArrayList<SkywayNode>();

	public SkywayDB(ArrayList<Pair<GeoPoint, GeoPoint>> arrayList) {
		for (Pair<GeoPoint, GeoPoint> pair : arrayList) {

			GeoPoint point1 = (GeoPoint) pair.first;
			GeoPoint point2 = (GeoPoint) pair.second;

			SkywayEdge skywayEdge = new SkywayEdge(currentEdgeCount, point1, point2);

			SkywayNode skywayNode = null;

			if (skywayNodeList.contains(point1)) {
				skywayNode = skywayNodeList.get(skywayNodeList.indexOf(point1));
				skywayNode.addAdjacentSkywayEdge(skywayEdge);
			} else {
				skywayNode = new SkywayNode(currentNodeCount, point1);
				skywayNode.addAdjacentSkywayEdge(skywayEdge);
				skywayNodeList.add(skywayNode);
				++currentNodeCount;
			}

			if (skywayNodeList.contains(point2)) {
				skywayNode = skywayNodeList.get(skywayNodeList.indexOf(point2));
				skywayNode.addAdjacentSkywayEdge(skywayEdge);
			} else {
				skywayNode = new SkywayNode(currentNodeCount, point2);
				skywayNode.addAdjacentSkywayEdge(skywayEdge);
				skywayNodeList.add(skywayNode);
				++currentNodeCount;
			}
			
			++currentEdgeCount;
		}
	}
	
	public ArrayList<SkywayNode> getSkyway()
	{
		return this.skywayNodeList;
	}

	public void printSkywayDB() {
		for (SkywayNode node : skywayNodeList) {
			Log.v(LOG_TAG, String.valueOf(node.getNodeId()));
			for (SkywayEdge edge : node.getAdjacentSkywayEdges()) {
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
