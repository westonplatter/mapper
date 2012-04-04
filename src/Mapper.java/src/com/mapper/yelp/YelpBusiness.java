// YelpBusiness.java
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
package com.mapper.yelp;

public class YelpBusiness
{
    private String name        = "";
    private String imageUrl    = "";
    private String phoneNumber = "";
    private String address     = "";
    private String city        = "";
    private String state       = "";
    private String url         = "";
    private String ratingUrl   = "";
    private double latitude    = 0.0;
    private double longitude   = 0.0;
    private int postalCode     = 0;
    private int reviewCount    = 0;

    public YelpBusiness() {}

    public int getReviewCount()
    {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount)
    {
        this.reviewCount = reviewCount;
    }

    public String getRatingUrl()
    {
        return ratingUrl;
    }

    public void setRatingUrl(String ratingUrl)
    {
        this.ratingUrl = ratingUrl;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public int getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(int postalCode)
    {
        this.postalCode = postalCode;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String toString()
    {
        return(this.name + " " + this.address + " " + this.city + " "
                + this.state + " " + this.postalCode + " " + this.latitude
                + " " + this.longitude + " " + this.postalCode + " " + this.url
                + " " + this.ratingUrl + " " + this.reviewCount + " " + this.imageUrl);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
}