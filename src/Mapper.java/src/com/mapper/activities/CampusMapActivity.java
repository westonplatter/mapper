// CampusMapActivity.java
/**
 * Copyright 2012 Kristin Mead
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
package com.mapper.activities;

import java.io.IOException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.mapper.exceptions.MapperInvalidArgumentException;
import com.mapper.map.MapDirections;
import com.mapper.map.MapEdge;
import com.mapper.map.MapItemizedOverlay;
import com.mapper.map.MapNode;
import com.mapper.map.MapOverlay;
import com.mapper.map.MapOverlayItem;
import com.mapper.map.NodeDB;
import com.mapper.util.MapperConstants;
import com.mapper.util.MapperConstants.MAP_LOCATION;

public class CampusMapActivity extends MapActivity {
	private static NodeDB campusDB;
	private MapController mc;
	private LocationManager lm;
	private LocationListener ll;
	GeoPoint p = null;
	MapView mapView;
	List<Overlay> mapOverlays;
	MyLocationOverlay myLocationOverlay;
	Location loc;
	String gps_provider = LocationManager.GPS_PROVIDER;
	String network_provider = LocationManager.NETWORK_PROVIDER;
	ArrayList skywayList = new ArrayList();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		System.out.println("CampusMap On Create");
		MapperConstants.currentMap = MAP_LOCATION.CAMPUS;

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.map);

		// Create map view
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);

		// get MapOverlap Object List
		mapOverlays = mapView.getOverlays();

		// Get campus from adaptation
		try {
			campusDB = new NodeDB(readCampusAdaptation());
		} catch (MapperInvalidArgumentException e) {
			throw new RuntimeException(e);
		}

		ArrayList<MapNode> campusNodeList = campusDB.getNodeList();
		ArrayList<Integer> alreadyDrawnEdge = new ArrayList<Integer>();

		for (MapNode node : campusNodeList) {
			for (MapEdge edge : node.getAdjacentEdges()) {
				if (!alreadyDrawnEdge.contains(edge.getUniqueID())) {
					MapOverlay mo = new MapOverlay(edge.getFirstNode(),
							edge.getSecondNode());
					if (edge.isTunnel()) {
						mo.setLineColor(Color.rgb(139, 0, 0));
					}
					if (edge.isOutside()) {
						mo.setLineColor(Color.rgb(218, 154, 32));
					}
					mapOverlays.add(mo);
					alreadyDrawnEdge.add(edge.getUniqueID());
				}
			}
		}
		// get Map Controller to set location and zoom
		mc = mapView.getController();

		// Center Map
		p = new GeoPoint(
				(int) (MapperConstants.CAMPUS_MAP_CENTER_LATITUDE * 1000000),
				(int) (MapperConstants.CAMPUS_MAP_CENTER_LONGITUDE * 1000000));
		mc.animateTo(p);
		mc.setZoom(16);

		myLocationOverlay = new MyLocationOverlay(this, mapView);
//		myLocationOverlay.enableMyLocation();
//		myLocationOverlay.enableCompass();

		List<Overlay> list = mapView.getOverlays();
		list.add(myLocationOverlay);

		if (ll == null) {
			ll = new MyLocationListener();
			lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
					ll);
			lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0,
					ll);
		}

		Button currentlocationButton = (Button) findViewById(R.id.curLoc);
		currentlocationButton.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
						ll);
				lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
						0, ll);
				lm.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0,
						0, ll);

				MplsSkywayMapActivity.followLocation = true;
				centerOnLocation();

				return true;
			}
		});

		mapView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		// Determine whether this is processing a user-selection
		int inputCode = 0;
		inputCode = getIntent().getIntExtra("selection", 0);

		if (inputCode == MapperConstants.MAP_IT_SELECTION) {
			String businessName = getIntent().getExtras().getString(
					"businessName");

			double latitude = getIntent().getExtras().getDouble("latitude");
			double longitude = getIntent().getExtras().getDouble("longitude");
			dropPin(latitude, longitude, businessName);

		} else if (inputCode == MapperConstants.GET_DIRECTIONS_SELECTION) {
			String businessName = getIntent().getExtras().getString(
					"businessName");

			double end_latitude = getIntent().getExtras().getDouble("latitude");
			double end_longitude = getIntent().getExtras().getDouble(
					"longitude");

			double start_latitude = MapperConstants.CAMPUS_MAP_CENTER_LATITUDE;
			double start_longitude = MapperConstants.CAMPUS_MAP_CENTER_LONGITUDE;

			if (loc != null) {
				start_latitude = loc.getLatitude();
				start_longitude = loc.getLongitude();
			} else if ((loc = lm.getLastKnownLocation(gps_provider)) != null) {
				start_latitude = loc.getLatitude();
				start_longitude = loc.getLongitude();
			}
			System.out.println("MapSkywayActivity = Business:" + businessName
					+ " " + end_latitude + " " + end_longitude);

			DrawDirections(start_latitude, start_longitude, end_latitude,
					end_longitude);
			dropPin(end_latitude, end_longitude, businessName);
		}
		
		mapView.postInvalidate();
	}

	public void dropPin(double latitude, double longitude, String Label) {
		// instantiate the picture for the location
		Drawable marker = getResources().getDrawable(R.drawable.pin_tack);
		int markerHeight = marker.getIntrinsicHeight();
		int markerWidth = marker.getIntrinsicWidth();
		marker.setBounds(0, markerHeight, markerWidth, 0);

		// instantiate the ItemizedOverlay (collection of items within connected
		// to overlay)
		MapItemizedOverlay itemizedOverlay = new MapItemizedOverlay(marker);
		mapOverlays.add(itemizedOverlay);

		// instantiate OverlayItem
		GeoPoint point = new GeoPoint((int) (latitude * 1000000),
				(int) (longitude * 1000000));
		MapOverlayItem item = new MapOverlayItem(point, "title", "snippet");

		// add pin to the map
		itemizedOverlay.addItem(item);
	}

	private void centerOnLocation() {
		if (loc != null) {
			double lat = loc.getLatitude();
			double lon = loc.getLongitude();
			GeoPoint newPoint = new GeoPoint((int) (lat * 1e6),
					(int) (lon * 1e6));

			mc.animateTo(newPoint);
			mapView.invalidate();

		} else if ((loc = lm.getLastKnownLocation(gps_provider)) != null) {
			double lat = loc.getLatitude();
			double lon = loc.getLongitude();
			GeoPoint newPoint = new GeoPoint((int) (lat * 1e6),
					(int) (lon * 1e6));

			mc.animateTo(newPoint);
			mapView.invalidate();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.campus_options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.search:
			onSearchRequested();
			return true;
		case R.id.view_favorites:
			Intent intent = new Intent(this, FavoritesActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			return true;
		case R.id.main_menu:
			Intent mainIntent = new Intent(this, MainMenuActivity.class);
			mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(mainIntent);
			return true;
		case R.id.quit:
			Intent quitIntent = new Intent(Intent.ACTION_MAIN);
			quitIntent.addCategory(Intent.CATEGORY_HOME);
			quitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(quitIntent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private ArrayList<Pair<Pair<GeoPoint, GeoPoint>, Integer>> readCampusAdaptation() {
		ArrayList<String> stringXmlContent = null;
		ArrayList<Pair<Pair<GeoPoint, GeoPoint>, Integer>> returnList = new ArrayList<Pair<Pair<GeoPoint, GeoPoint>, Integer>>();
		try {
			stringXmlContent = getEventsFromAnXML(this);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean tunnel = false;
		boolean outside = false;

		for (String line : stringXmlContent) {
			if (line == null) {
				continue;
			}

			String coordinate = line.replaceAll("\n", ",");
			coordinate = coordinate.replaceAll(" ", "");
			String[] coordinates = coordinate.split(",");

			if (line.contains("TUNNEL")) {
				tunnel = true;
				continue;
			}
			if (line.contains("OUTSIDE")) {
				outside = true;
				continue;
			}

			int lineType = MapperConstants.SKYWAY;

			if (tunnel) {
				lineType = MapperConstants.TUNNEL;
			} else if (outside) {
				lineType = MapperConstants.OUTSIDE;
			}

			Pair<Pair<GeoPoint, GeoPoint>, Integer> pair = new Pair<Pair<GeoPoint, GeoPoint>, Integer>(
					new Pair(
							new GeoPoint(
									(int) (Double.valueOf(coordinates[1]) * 1000000),
									(int) (Double.valueOf(coordinates[0]) * 1000000)),
							new GeoPoint(
									(int) (Double.valueOf(coordinates[4]) * 1000000),
									(int) (Double.valueOf(coordinates[3]) * 1000000))),
					lineType);

			returnList.add(pair);

			tunnel = false;
			outside = false;
		}

		return returnList;
	}

	private ArrayList<String> getEventsFromAnXML(Activity activity)
			throws XmlPullParserException, IOException {
		ArrayList<String> coordinateList = new ArrayList<String>();
		Resources res = activity.getResources();
		XmlResourceParser xpp = res.getXml(R.xml.campus423);
		xpp.next();

		int eventType = xpp.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			if (eventType == XmlPullParser.TEXT) {
				coordinateList.add(xpp.getText());
			} else if (eventType == XmlPullParser.START_TAG
					|| eventType == XmlPullParser.END_TAG) {
				System.out.println("NAME:" + xpp.getName());
				if (xpp.getName().contains("LineType")) {
					coordinateList.add(xpp.getText());
				}
			}
			eventType = xpp.next();
		}

		return coordinateList;
	}

	private class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location argLocation) {
			GeoPoint myGeoPoint = new GeoPoint(
					(int) (argLocation.getLatitude() * 1000000),
					(int) (argLocation.getLongitude() * 1000000));

			p = myGeoPoint;
			mc.animateTo(myGeoPoint);
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	}

	public void DrawDirections(double latitude1, double longitude1,
			double latitude2, double longitude2) {
		ArrayList<MapNode> skyway = campusDB.getNodeList();
		MapView mapView = (MapView) findViewById(R.id.mapview);

		// get MapOverlap Object List
		List<Overlay> mapOverlays = mapView.getOverlays();

		// get shortest path from 0 to 7
		MapDirections directions = new MapDirections(campusDB);

		// clear the overlays
		//mapOverlays.clear();

		// get the Start edge and start lat long point
		Pair<GeoPoint, MapEdge> startEdge = getClosestPointOnLineSegment(
				latitude1, longitude1);

		// get the End edge and end lat long point
		Pair<GeoPoint, MapEdge> endEdge = getClosestPointOnLineSegment(
				latitude2, longitude2);

		// get the closest node on the edge from where you are
		MapNode startNode = getClosestNode(
				startEdge.first.getLatitudeE6() / 1E6,
				startEdge.first.getLongitudeE6() / 1E6, startEdge.second);

		// get the closest node on the edge from the destination is
		MapNode endNode = getClosestNode(endEdge.first.getLatitudeE6() / 1E6,
				endEdge.first.getLongitudeE6() / 1E6, endEdge.second);

		// initiate the graph with closest paths from current location
		directions.execute(startNode);

		// get the shortest path
		LinkedList<MapNode> path = directions.getPath(endNode);

		// Draw path. connect current location to starting node then append the
		// end location to the finish location
		MapOverlay mo;

		int i;
		for (i = 0; i < path.size() - 1; ++i) {
			mo = new MapOverlay(path.get(i).getNodeLocation(), path.get(i + 1)
					.getNodeLocation());

			mo.setLineColor(Color.DKGRAY);
			mo.setLineWidth(5);
			mapOverlays.add(mo);
		}

		// Redraw the skyways
		ArrayList<Integer> alreadyDrawnSkyways = new ArrayList<Integer>();

		for (MapNode node : skyway) {
			for (MapEdge edge : node.getAdjacentEdges()) {
				if (!alreadyDrawnSkyways.contains(edge.getUniqueID())) {
					MapOverlay mapOverlay = new MapOverlay(edge.getSourceNode()
							.getNodeLocation(), edge.getTargetNode()
							.getNodeLocation());

					if (edge.isTunnel()) {
						mapOverlay.setLineColor(Color.rgb(139, 0, 0));
					}
					if (edge.isOutside()) {
						mapOverlay.setLineColor(Color.rgb(218, 154, 32));
					}

					mapOverlays.add(mapOverlay);
					alreadyDrawnSkyways.add(edge.getUniqueID());
				}
			}
		}

		List<Overlay> list = mapView.getOverlays();
		list.add(myLocationOverlay);

		mapView.invalidate();
	}

	public MapNode getClosestNode(double latitude, double longitude,
			MapEdge edge) {
		// get the closest node based on a location on the line
		double distanceToNode1 = Math.sqrt(Math.pow(latitude
				- edge.getSourceNode().getNodeLocation().getLatitudeE6() / 1E6,
				2)
				+ Math.pow(longitude
						- edge.getSourceNode().getNodeLocation()
								.getLongitudeE6() / 1E6, 2));
		double distanceToNode2 = Math.sqrt(Math.pow(latitude
				- edge.getTargetNode().getNodeLocation().getLatitudeE6() / 1E6,
				2)
				+ Math.pow(longitude
						- edge.getTargetNode().getNodeLocation()
								.getLongitudeE6() / 1E6, 2));

		return (distanceToNode1 < distanceToNode2) ? edge.getSourceNode()
				: edge.getTargetNode();
	}

	public Pair<GeoPoint, MapEdge> getClosestPointOnLineSegment(
			double latitude, double longitude) {
		// gets the closest point on a line segment
		MapEdge closestEdge = null;
		double closestDistance = Double.POSITIVE_INFINITY;
		for (MapEdge e : campusDB.getEdgeList()) {
			float x = (float) latitude;
			float y = (float) longitude;
			float edgeX1 = (float) ((float) e.getSourceNode().getNodeLocation()
					.getLatitudeE6() / 1E6);
			float edgeY1 = (float) ((float) e.getSourceNode().getNodeLocation()
					.getLongitudeE6() / 1E6);

			float edgeX2 = (float) ((float) e.getTargetNode().getNodeLocation()
					.getLatitudeE6() / 1E6);
			float edgeY2 = (float) ((float) e.getTargetNode().getNodeLocation()
					.getLongitudeE6() / 1E6);

			float A = x - edgeX1;
			float B = y - edgeY1;
			float C = edgeX2 - edgeX1;
			float D = edgeY2 - edgeY1;

			float dot = A * C + B * D;
			float len_sq = C * C + D * D;
			float param = dot / len_sq;

			float xx, yy;

			if (param < 0) {
				xx = edgeX1;
				yy = edgeY1;
			} else if (param > 1) {
				xx = edgeX2;
				yy = edgeY2;
			} else {
				xx = edgeX1 + param * C;
				yy = edgeY1 + param * D;
			}

			float dist = (float) Math.sqrt((Math.abs(x - xx) * (Math
					.abs(x - xx))) + (Math.abs(y - yy) * (Math.abs(y - yy))));

			if (closestDistance > dist) {
				closestDistance = dist;
				closestEdge = e;
			}
		}

		float nodeAtoPointX = (float) Math.abs(latitude
				- closestEdge.getSourceNode().getNodeLocation().getLatitudeE6()
				/ 1E6);
		float nodeAtoPointY = (float) Math.abs(longitude
				- closestEdge.getSourceNode().getNodeLocation()
						.getLongitudeE6() / 1E6);

		float nodeAtoNodeBX = (float) Math.abs(closestEdge.getSourceNode()
				.getNodeLocation().getLatitudeE6()
				/ 1E6
				- closestEdge.getTargetNode().getNodeLocation().getLatitudeE6()
				/ 1E6);

		float nodeAtoNodeBY = (float) Math.abs(closestEdge.getSourceNode()
				.getNodeLocation().getLongitudeE6()
				/ 1E6
				- closestEdge.getTargetNode().getNodeLocation()
						.getLongitudeE6() / 1E6);

		float nodeAtoB = nodeAtoNodeBX * nodeAtoNodeBX + nodeAtoNodeBY
				* nodeAtoNodeBY;

		float aToPDotAToB = nodeAtoPointX * nodeAtoNodeBX + nodeAtoPointY
				* nodeAtoNodeBY;

		float t = aToPDotAToB / nodeAtoB;

		float X = (float) (closestEdge.getSourceNode().getNodeLocation()
				.getLatitudeE6() / 1E6 + nodeAtoNodeBX * t);
		float Y = (float) (closestEdge.getSourceNode().getNodeLocation()
				.getLongitudeE6() / 1E6 + nodeAtoNodeBY * t);

		GeoPoint returnPoint = new GeoPoint((int) (X * 1E6), (int) (Y * 1E6));
		return new Pair<GeoPoint, MapEdge>(returnPoint, closestEdge);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onResume() {
		super.onResume();

//		myLocationOverlay.enableCompass();
		myLocationOverlay.enableMyLocation();
	}

	@Override
	public void onPause() {
		super.onPause();

		//myLocationOverlay.disableCompass();
		myLocationOverlay.disableMyLocation();
	}

}