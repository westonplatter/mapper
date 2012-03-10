package com.skyway.yelp;

public class YelpBusiness {
	
	private String name = "";
	
	private String imageUrl = "";
	
	private String phoneNumber = "";

	private String address = "";

	private String city = "";

	private double latitude = 0.0;

	private double longitude = 0.0;

	private int postalCode = 0;

	private String state = "";

	private String url = "";

	private String ratingUrl = "";

	private int reviewCount = 0;

	public YelpBusiness() {
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public String getRatingUrl() {
		return ratingUrl;
	}

	public void setRatingUrl(String ratingUrl) {
		this.ratingUrl = ratingUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String toString() {
		return (this.name + " " + this.address + " " + this.city + " " + this.state + " "
				+ this.postalCode + " " + this.latitude + " " + this.longitude
				+ " " + this.postalCode + " " + this.url + " " + this.ratingUrl
				+ " " + this.reviewCount + " " + this.imageUrl);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
