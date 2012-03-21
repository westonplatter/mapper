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

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainMenuActivity extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
      
	  // Create a menu of items
      String[] menuItems = getResources().getStringArray(R.array.menu_array);
      setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems));
      
      // Create the list view
	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  // Add a click listener
	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	    {    
	        Intent myIntent = null;
	        
	        if(((TextView) view).getText().equals("Skyway Map")){
	         myIntent = new Intent(view.getContext(), MplsSkywayMapActivity.class);
	        }
	         
	        if(((TextView) view).getText().equals("Campus Map")){
	         myIntent = new Intent(view.getContext(), CampusMapActivity.class);
	        }

	        if(((TextView) view).getText().equals("Favorites")){
	         myIntent = new Intent(view.getContext(), FavoritesActivity.class);
	        }

	        if(((TextView) view).getText().equals("Help")){
	          myIntent = new Intent(view.getContext(), HelpActivity.class);
	        }

	        // Start the user-selected activity
	        startActivity(myIntent);
	    }
	  });
	}
}