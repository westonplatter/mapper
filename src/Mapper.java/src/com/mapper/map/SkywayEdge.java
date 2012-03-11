// SkywayEdge.java
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

public class SkywayEdge {
	private int uniqueID;
	private GeoPoint point1;
	private GeoPoint point2;
	private double distanceInMeters;

	public SkywayEdge(int lat1, int lon1, int lat2, int lon2) {
		point1 = new GeoPoint(lat1 * 1000000, lon1 * 1000000);
		point2 = new GeoPoint(lat2 * 1000000, lon2 * 1000000);

		Location location = new Location("");
		location.setLatitude(lat1);
		location.setLongitude(lon1);

		Location location2 = new Location("");
		location2.setLatitude(lat2);
		location2.setLongitude(lon2);

		distanceInMeters = location.distanceTo(location2);
	}

	public SkywayEdge(int uniqueId, GeoPoint point1, GeoPoint point2) {
		this.uniqueID = uniqueId;
		this.point1 = point1;
		this.point2 = point2;

		Location location = new Location("");
		location.setLatitude(point1.getLatitudeE6() / 1000000);
		location.setLongitude(point1.getLongitudeE6() / 1000000);

		Location location2 = new Location("");
		location2.setLatitude(point2.getLatitudeE6() / 1000000);
		location2.setLongitude(point2.getLongitudeE6() / 1000000);

		distanceInMeters = location.distanceTo(location2);
	}

	public GeoPoint getFirstNode() {
		return point1;
	}

	public GeoPoint getSecondNode() {
		return point2;
	}

	public double getDistance() {
		return distanceInMeters;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}
}
