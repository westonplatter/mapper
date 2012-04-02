// YelpResultsResponse.java
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

import java.util.ArrayList;
import java.util.List;

public class YelpResultsResponse
{
    private double latitudeCenter;
    private double longitudeCenter;
    public List<String> businessNames = new ArrayList<String>();
    public List<YelpBusiness> businesses = new ArrayList<YelpBusiness>();

    public double getTotalResults()
    {
        return businesses.size();
    }

    public double getLatitude()
    {
        return latitudeCenter;
    }

    public void setLatitude(double latitude)
    {
        this.latitudeCenter = latitude;
    }

    public double getLongitude()
    {
        return longitudeCenter;
    }

    public void setLongitude(double longitude)
    {
        this.longitudeCenter = longitude;
    }

    public List<YelpBusiness> getBusinesses()
    {
        return businesses;
    }

    public void addBusinesses(YelpBusiness business)
    {
        this.businessNames.add(business.getName());
        this.businesses.add(business);
    }
}