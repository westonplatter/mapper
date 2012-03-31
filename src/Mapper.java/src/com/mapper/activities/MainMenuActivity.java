// MainMenuActivity.java
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends Activity implements OnClickListener {

	private static Button skywayMapButton;
	private static Button campusMapButton;
	private static Button favoritesButton;
	private static Button helpButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		skywayMapButton = (Button) this.findViewById(R.id.skywayMapMenuButton);
		campusMapButton = (Button) this.findViewById(R.id.campusMapMenuButton);
		favoritesButton = (Button) this.findViewById(R.id.favoritesMenuButton);
		helpButton = (Button) this.findViewById(R.id.helpMenuButton);

		skywayMapButton.setOnClickListener(this);
		campusMapButton.setOnClickListener(this);
		favoritesButton.setOnClickListener(this);
		helpButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) 
	{
		Intent myIntent = null;

		if (((TextView) view).getText().equals("Skyway Map")) {
			myIntent = new Intent(view.getContext(), MplsSkywayMapActivity.class);
		}

		if (((TextView) view).getText().equals("Campus Map")) {
			myIntent = new Intent(view.getContext(), CampusMapActivity.class);
		}

		if (((TextView) view).getText().equals("Favorites")) {
			myIntent = new Intent(view.getContext(), FavoritesActivity.class);
		}

		if (((TextView) view).getText().equals("Help")) {
			myIntent = new Intent(view.getContext(), HelpActivity.class);
		}

		myIntent.putExtra("selection", 0);
		startActivity(myIntent);
	}
}