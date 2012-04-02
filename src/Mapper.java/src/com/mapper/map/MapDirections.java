package com.mapper.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapDirections
{

    public static void getDirections(NodeDB nodeDB)
    {

    }

    private final List<MapEdge> edges;
    private Set<MapNode> settledNodes;
    private Set<MapNode> unSettledNodes;
    private Map<MapNode, MapNode> predecessors;
    private Map<MapNode, Double> distance;

    public MapDirections(NodeDB graph)
    {
        // Create a copy of the array so that we can operate on this array
        this.edges = new ArrayList<MapEdge>(graph.getEdgeList());
    }

    public void execute(MapNode source)
    {
        settledNodes = new HashSet<MapNode>();
        unSettledNodes = new HashSet<MapNode>();
        distance = new HashMap<MapNode, Double>();
        predecessors = new HashMap<MapNode, MapNode>();
        distance.put(source, 0.0);
        unSettledNodes.add(source);
        while(unSettledNodes.size() > 0)
        {
            MapNode node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(MapNode node)
    {
        List<MapNode> adjacentNodes = getNeighbors(node);
        for(MapNode target : adjacentNodes)
        {
            if(getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target))
            {
                distance.put(target,
                        getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private double getDistance(MapNode node, MapNode target)
    {
        for(MapEdge edge : edges)
        {
            if((edge.getSourceNode().equals(node) && edge.getTargetNode()
                    .equals(target))
                    || edge.getTargetNode().equals(node)
                    && edge.getSourceNode().equals(target))
            {
                return edge.getDistance();
            }
        }
        throw new RuntimeException("Error");
    }

    private List<MapNode> getNeighbors(MapNode node)
    {
        List<MapNode> neighbors = new ArrayList<MapNode>();
        // for (MapEdge edge : edges) {
        for(MapEdge edge : edges)
        {
            if(edge.getSourceNode().equals(node)
                    && !isSettled(edge.getTargetNode()))
            {
                neighbors.add(edge.getTargetNode());
            }
            else if(edge.getTargetNode().equals(node)
                    && !isSettled(edge.getSourceNode()))
            {
                neighbors.add(edge.getSourceNode());
            }
        }
        return neighbors;
    }

    private MapNode getMinimum(Set<MapNode> vertexes)
    {
        MapNode minimum = null;
        for(MapNode vertex : vertexes)
        {
            if(minimum == null)
            {
                minimum = vertex;
            }
            else
            {
                if(getShortestDistance(vertex) < getShortestDistance(minimum))
                {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(MapNode vertex)
    {
        return settledNodes.contains(vertex);
    }

    private double getShortestDistance(MapNode destination)
    {
        Double d = distance.get(destination);
        if(d == null)
        {
            return Integer.MAX_VALUE;
        }
        else
        {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<MapNode> getPath(MapNode target)
    {
        LinkedList<MapNode> path = new LinkedList<MapNode>();
        MapNode step = target;
        // Check if a path exists
        if(predecessors.get(step) == null)
        {
            return null;
        }
        path.add(step);
        while(predecessors.get(step) != null)
        {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}
