package com.mapper.yelp;

import java.util.Vector;

public class YelpResultsResponse {

	private double totalResults;

	private double latitudeCenter;

	private double longitudeCenter;

	private Vector<YelpBusiness> businesses = new Vector<YelpBusiness>();

	public double getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(double total) {
		this.totalResults = total;
	}

	public double getLatitude() {
		return latitudeCenter;
	}

	public void setLatitude(double latitude) {
		this.latitudeCenter = latitude;
	}

	public double getLongitude() {
		return longitudeCenter;
	}

	public void setLongitude(double longitude) {
		this.longitudeCenter = longitude;
	}

	public Vector<YelpBusiness> getBusinesses() {
		return businesses;
	}

	public void addBusinesses(YelpBusiness business) {
		this.businesses.add(business);
	}
}