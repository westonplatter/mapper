// ListingView.java
/**
 * Copyright 2012 Kristin Mead
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *    
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mapper.activities;

import com.mapper.util.MapperConstants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SingleSearchResultView extends Activity implements OnClickListener
{
    private static Button mapItButton;
    private static Button getDirectionsButton;
    private static Button saveFavoritesButton;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_result_layout);

        // Set the business name
        TextView businessName = (TextView)findViewById(R.id.business_name);
        businessName.setText(SearchableActivity.userSelection.getName());

        // Build and set the address
        String streetAddress = SearchableActivity.userSelection.getAddress();
        String city  = SearchableActivity.userSelection.getCity();
        String state = SearchableActivity.userSelection.getState();
        int zip = SearchableActivity.userSelection.getPostalCode();
        String address = streetAddress + '\n' + city + ", " + state + ' ' + zip;

        TextView businessAddress = (TextView)findViewById(R.id.business_address);
        businessAddress.setText(address);

        // Set the phone number
        TextView businessPhone = (TextView)findViewById(R.id.business_phone);
        businessPhone.setText(SearchableActivity.userSelection.getPhoneNumber());
        
        mapItButton = (Button) this.findViewById(R.id.map_it_button);
        getDirectionsButton = (Button) this.findViewById(R.id.get_directions_button);
        saveFavoritesButton = (Button) this.findViewById(R.id.save_button);

        mapItButton.setOnClickListener(this);
        getDirectionsButton.setOnClickListener(this);
        saveFavoritesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) 
    {
        if (((TextView) view).getText().equals("Map It")) 
        {
            double latitude = SearchableActivity.userSelection.getLatitude();
            double longitude = SearchableActivity.userSelection.getLongitude();

            Intent newActivity = new Intent(this, MplsSkywayMapActivity.class);
            newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            newActivity.putExtra("selection", MapperConstants.MAP_IT_SELECTION);
            newActivity.putExtra("latitude", latitude);
            newActivity.putExtra("longitude", longitude);
            startActivity(newActivity);
        }

        else if (((TextView) view).getText().equals("Get Directions")) 
        {
            double latitude = SearchableActivity.userSelection.getLatitude();
            double longitude = SearchableActivity.userSelection.getLongitude();

            Intent newActivity = new Intent(this, MplsSkywayMapActivity.class);
            newActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            newActivity.putExtra("selection", MapperConstants.GET_DIRECTIONS_SELECTION);
            newActivity.putExtra("latitude", latitude);
            newActivity.putExtra("longitude", longitude);
            startActivity(newActivity);
        }

        else if (((TextView) view).getText().equals("Add To Favorites")) {
        }
    }
}