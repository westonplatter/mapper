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
import com.mapper.util.MapperConstants.MAP_LOCATION;
import com.mapper.yelp.YelpBusiness;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class SingleSearchResultView extends Activity implements OnClickListener {
	public static YelpBusiness currentSelection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_result_layout);
		Bundle extra = getIntent().getExtras();
		int caller = -1;
		if (extra != null)
			caller = extra.getInt("callerId");
		// Set the business name
		if (caller == R.id.save) {
			currentSelection = FavoritesActivity.userSelection;
			Log.v("INFO", "Received query from favorite");
		} else {
			currentSelection = SearchableActivity.userSelection;
			Log.v("INFO", "Received query from search");
		}

		TextView businessName = (TextView) findViewById(R.id.business_name);
		businessName.setText(currentSelection.getName());

		// Build and set the address
		String streetAddress = currentSelection.getAddress();
		String city = currentSelection.getCity();
		String state = currentSelection.getState();
		int zip = currentSelection.getPostalCode();
		String address = streetAddress + '\n' + city + ", " + state + ' ' + zip;

		TextView businessAddress = (TextView) findViewById(R.id.business_address);
		businessAddress.setText(address);

		// Set the phone number
		TextView businessPhone = (TextView) findViewById(R.id.business_phone);
		businessPhone.setText(currentSelection.getPhoneNumber());

		Button currentlocationButton = (Button) findViewById(R.id.get_directions);
		currentlocationButton.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_UP) {
					System.out.println("Get Directions Button Pressed "
							+ currentSelection.getName() + " "
							+ currentSelection.getLatitude() + " "
							+ currentSelection.getLongitude());

					Class intentClass = null;

					if (MapperConstants.currentMap == MAP_LOCATION.DOWNTOWN) {
						intentClass = MplsSkywayMapActivity.class;
					} else if (MapperConstants.currentMap == MAP_LOCATION.CAMPUS) {
						intentClass = CampusMapActivity.class;
					}

					// "GetDirections" clicked.
					Intent myIntent = new Intent(v.getContext(), intentClass);
					// myIntent.putExtra("businessName",
					// currentSelection.getName());
					// myIntent.putExtra("businessLat",
					// String.valueOf(currentSelection.getLatitude()));
					// myIntent.putExtra("businessLong",
					// String.valueOf(currentSelection.getLongitude()));

					myIntent.putExtra("selection",
							MapperConstants.GET_DIRECTIONS_SELECTION);
					myIntent.putExtra("businessName",
							currentSelection.getName());
					myIntent.putExtra("latitude",
							currentSelection.getLatitude());
					myIntent.putExtra("longitude",
							currentSelection.getLongitude());

					startActivity(myIntent);
				}
				return true;
			}
		});

		Button mapItButton = (Button) findViewById(R.id.map_it);
		mapItButton.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_UP) {
					System.out.println("Map it Button Pressed "
							+ currentSelection.getName() + " "
							+ currentSelection.getLatitude() + " "
							+ currentSelection.getLongitude());

					Class intentClass = null;

					if (MapperConstants.currentMap == MAP_LOCATION.DOWNTOWN) {
						intentClass = MplsSkywayMapActivity.class;
					} else if (MapperConstants.currentMap == MAP_LOCATION.CAMPUS) {
						intentClass = CampusMapActivity.class;
					}

					// "GetDirections" clicked.
					Intent myIntent = new Intent(v.getContext(), intentClass);

					myIntent.putExtra("selection",
							MapperConstants.MAP_IT_SELECTION);
					myIntent.putExtra("businessName",
							currentSelection.getName());
					myIntent.putExtra("latitude",
							currentSelection.getLatitude());
					myIntent.putExtra("longitude",
							currentSelection.getLongitude());

					startActivity(myIntent);
				}
				return true;
			}
		});
	}

	public void saveToFavorites(View view) {
		// "Add To Favorites" clicked.
		Intent myIntent = new Intent(view.getContext(), FavoritesActivity.class);
		myIntent.putExtra("myAction", R.id.save);
		myIntent.putExtra("myFavorite", currentSelection.getName());
		startActivity(myIntent);
	}

	@Override
	public void onClick(View view) {
		Intent myIntent = null;

		if (((TextView) view).getText().equals("Add To Favorites")) {
			myIntent = new Intent(view.getContext(), FavoritesActivity.class);
		}
		startActivity(myIntent);
	}
}