// YelpQueryManager.java
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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class YelpQueryManager
{
    static private double MapCenterLatitude  = 44.975667;
    static private double MapCenterLongitude = -93.270793;

    static private final String ConsumerKey    = "7vt3-VYr6iTT2h8UP1I_TQ";
    static private final String ConsumerSecret = "0NE2zJcCekassEx8vneMqPHCGrE";
    static private final String Token          = "8FbokVwPFOOn5UmnvZXe1ZQgPLJAxYAg";
    static private final String TokenSecret    = "6t3zlSabL5VkVIN9CNEDkbD_vNc";

    static private final String REGION_TAG        = "region";
    static private final String CENTER_TAG        = "center";
    static private final String LATITUDE_TAG      = "latitude";
    static private final String LONGITUDE_TAG     = "longitude";
    static private final String DISPLAY_PHONE_TAG = "display_phone";
    static private final String LOCATION_TAG      = "location";
    static private final String BUSINESS_TAG      = "businesses";
    static private final String ADDRESS_TAG       = "address";
    static private final String COORDINATE_TAG    = "coordinate";
    static private final String CITY_TAG          = "city";
    static private final String POSTAL_CODE_TAG   = "postal_code";
    static private final String STATE_TAG         = "state_code";
    static private final String RATING_IMG_TAG    = "rating_img_url";
    static private final String REVIEW_TAG        = "review_count";
    static private final String URL_TAG           = "url";
    static private final String NAME_TAG          = "name";
    static private final String IMAGE_URL_TAG     = "image_url";

    static private YelpService yelpService;
    static private YelpResultsResponse responseObject;; 

    public YelpQueryManager()
    {
        // Connect to yelp and create a response object
        yelpService = new YelpService(ConsumerKey, ConsumerSecret, Token, TokenSecret);
        responseObject = new YelpResultsResponse(); 
    }

    public YelpResultsResponse search(String currentQuery)
    {
        // Clear the response object
        responseObject.getBusinesses().clear();

        // Parse the results
        String responseString = yelpService.search(currentQuery, MapCenterLatitude, MapCenterLongitude);
        parseResults(responseString);
        return responseObject;
    }

    private void parseResults(String responseString)
    {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(responseString.getBytes()), "UTF-8"));
            JsonElement jse = new JsonParser().parse(in);
            in.close();

            // Iterate through each entry in the response string
            for (final Entry<String, JsonElement> entry : jse.getAsJsonObject().entrySet())
            {
                if (entry.getKey().contains(REGION_TAG))
                {
                    parseRegionTag(entry.getValue());
                }
            }

            // Create an array of business entries
            JsonArray jsa = jse.getAsJsonObject().getAsJsonArray(BUSINESS_TAG);

            // Iterate through each entry
            for (int i = 0; i < jsa.size(); i++)
            {
                // Parse BUSINESS_TAG
                YelpBusiness business = new YelpBusiness();
                parseBusinessTag(business, jsa.get(i));
                responseObject.addBusinesses(business);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parseBusinessTag(YelpBusiness business, JsonElement element)
    {
        for (final Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet())
        {
            final String key = entry.getKey();
            final JsonElement value = entry.getValue();

            if (key.contains(DISPLAY_PHONE_TAG))
            {
                business.setPhoneNumber(value.getAsString());
            }
            else if (key.contains(LOCATION_TAG))
            {
                // Location
                for (final Entry<String, JsonElement> centry : value.getAsJsonObject().entrySet())
                {
                    final String k1 = centry.getKey();
                    final JsonElement v1 = centry.getValue();

                    if (k1.contains(ADDRESS_TAG))
                    {
                        String address = "";
                        for (final JsonElement e2 : v1.getAsJsonArray())
                        {
                            address += e2.getAsString() + " ";
                        }

                        business.setAddress(address);
                    }
                    else if (k1.contains(COORDINATE_TAG))
                    {
                        for (final Entry<String, JsonElement> loc : v1.getAsJsonObject().entrySet())
                        {
                            if (loc.getKey().contains(LATITUDE_TAG))
                                business.setLatitude(loc.getValue().getAsDouble());
                            else if (loc.getKey().contains(LONGITUDE_TAG))
                                business.setLongitude(loc.getValue().getAsDouble());
                        }
                    }
                    else if (k1.contains(CITY_TAG))
                    {
                        business.setCity(v1.getAsString());
                    }
                    else if (k1.contains(POSTAL_CODE_TAG))
                    {
                        business.setPostalCode(v1.getAsInt());
                    }
                    else if (k1.contains(STATE_TAG))
                    {
                        business.setState(v1.getAsString());
                    }
                }
            }
            else if (key.contains(RATING_IMG_TAG))
            {
                business.setRatingUrl(value.getAsString());
            }
            else if (key.contains(REVIEW_TAG))
            {
                business.setReviewCount(value.getAsInt());
            }
            else if (key.contains(URL_TAG))
            {
                business.setUrl(value.getAsString());
            }
            else if (key.contains(NAME_TAG))
            {
                business.setName(value.getAsString());
            }
            else if (key.contains(IMAGE_URL_TAG))
            {
                business.setImageUrl(value.getAsString());
            }
        }
    }

    public void parseRegionTag(JsonElement element)
    {
        for (final Entry<String, JsonElement> centry : element.getAsJsonObject().entrySet())
        {
            final JsonElement value = centry.getValue();

            if (centry.getKey().contains(CENTER_TAG))
            {
                for (final Entry<String, JsonElement> loc : value.getAsJsonObject().entrySet())
                {
                    if (loc.getKey().contains(LATITUDE_TAG))
                    {
                        responseObject.setLatitude(loc.getValue().getAsDouble());
                    }
                    else if (loc.getKey().contains(LONGITUDE_TAG))
                    {
                        responseObject.setLongitude(loc.getValue().getAsDouble());
                    }
                }
            }
        }
    }
}
