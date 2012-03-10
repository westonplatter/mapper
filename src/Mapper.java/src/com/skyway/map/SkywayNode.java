package com.skyway.map;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;

public class SkywayNode {

	private int nodeId;

	private GeoPoint nodeLocation;

	private ArrayList<SkywayEdge> adjacentSkywayEdges = new ArrayList<SkywayEdge>();

	public SkywayNode(int nodeId, GeoPoint nodeLocation) {
		this.nodeId = nodeId;
		this.setNodeLocation(nodeLocation);
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public ArrayList<SkywayEdge> getAdjacentSkywayEdges() {
		return adjacentSkywayEdges;
	}

	public void addAdjacentSkywayEdge(SkywayEdge adjacentSkywayEdge) {
		this.adjacentSkywayEdges.add(adjacentSkywayEdge);
	}

	public GeoPoint getNodeLocation() {
		return nodeLocation;
	}

	public void setNodeLocation(GeoPoint nodeLocation) {
		this.nodeLocation = nodeLocation;
	}

	public boolean compareTo(SkywayNode node) {
		if (this.getNodeLocation().equals(node.getNodeLocation()))
			return true;
		else
			return false;
	}

	@Override
	public boolean equals(Object other) {
		if (this.getClass() == other.getClass())
		{
			SkywayNode otherNode = (SkywayNode) other;
			if (this.getNodeLocation().equals(otherNode.getNodeLocation()))
			{
				return true;
			}
		}

		return false;
	}
}
