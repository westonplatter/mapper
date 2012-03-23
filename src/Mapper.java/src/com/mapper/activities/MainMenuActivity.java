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
import android.app.ListActivity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainMenuActivity extends Activity implements OnClickListener {

	Button skywayMapButton;
	Button campusMapButton;
	Button favoritesButton;
	Button helpButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		// this.setContentView(R.layout.main);
		this.skywayMapButton = (Button) this
				.findViewById(R.id.skywayMapMenuButton);
		this.campusMapButton = (Button) this
				.findViewById(R.id.campusMapMenuButton);
		this.favoritesButton = (Button) this
				.findViewById(R.id.favoritesMenuButton);
		this.helpButton = (Button) this
				.findViewById(R.id.helpMenuButton);

		
		this.skywayMapButton.setOnClickListener(this);
		this.campusMapButton.setOnClickListener(this);
		this.favoritesButton.setOnClickListener(this);
		this.helpButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub

		Intent myIntent = null;

		if (((TextView) view).getText().equals("Skyway Map")) {
			myIntent = new Intent(view.getContext(),
					MplsSkywayMapActivity.class);
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

		startActivity(myIntent);
	}
}