// HelpActivity.java
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
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HelpActivity extends Activity {
	
	private static int yellow = Color.rgb(218, 154, 32);
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

		System.out.println("HELP ON Create");
		
		String helpText = "1.       Select the appropriate map.\n"
				+ "2.       Upon initialization Mapper detects the user's current position.\n"
				+ "3.       Enter destination into 'Search' or select from 'Favorites'.\n"
				+ "  a.       Mapper will display a list of results nearby user's location.\n"
				+ "  b.      Select destination from list.\n"
				+ "  c.       This destination is automatically added to 'Favorites'.\n"
				+ "4.       Select 'Map It' to display destination.\n"
				+ "5.       Select 'Directions' to display route to destination.\n"
				+ "  a.       Follow the indicator on highlighted route through the skyway/tunnel system.\n"
				+ "  b.      This is the shortest route through the skyway/tunnel system.\n"
				+ "  c.       Tunnels and skyways are distinguished by color to easily understand route.\n"
				+ "6.       Select 'Add to Favorites' to place destination into Favorites.\n"
				+ "___________________________________\n" +
				"Color Indexing\n" +
				"YELLOW - Outside Environment\n" + 
				"RED    - Tunnels\n" + 
				"Gray   - Skyways"
		;

		View linearLayout = findViewById(R.id.info);

		TextView valueTV = new TextView(this);
		valueTV.setText(helpText);
		valueTV.setId(5);
		valueTV.setTextColor(yellow);
		Drawable help = getResources().getDrawable(R.drawable.helpme);

		valueTV.setBackgroundDrawable(help);
		valueTV.setTextSize(12);
		
		valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));

		((LinearLayout) linearLayout).addView(valueTV);
	}
}
