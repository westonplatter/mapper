// SearchActivity.java
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

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mapper.yelp.YelpBusiness;
import com.mapper.yelp.YelpQueryManager;
import com.mapper.yelp.YelpResultsResponse;

public class SearchableActivity extends ListActivity 
{
    public  static YelpBusiness userSelection;
    private static YelpQueryManager yelpQueryManager;
    private static YelpResultsResponse yelpResultsResponse;

    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        // Get the intent and the query
        Intent intent = getIntent();
        String query = intent.getStringExtra(SearchManager.QUERY);

        // Perform search
        yelpQueryManager = new YelpQueryManager();
        yelpResultsResponse = yelpQueryManager.search(query);

        // Check result count
        if (yelpResultsResponse.getBusinesses().size() > 0) {
            // Create list of business names
            setListAdapter((ListAdapter) new ArrayAdapter<String>(this, R.layout.results_list_layout, yelpResultsResponse.businessNames));

            ListView lv = getListView();
            lv.setTextFilterEnabled(true);
            lv.setOnItemClickListener(new OnItemClickListener() 
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    userSelection = yelpResultsResponse.getBusinesses().get(position);
                    Intent myIntent = new Intent(view.getContext(), SingleSearchResultView.class);
                    myIntent.putExtra("callerId", R.id.search);
                    startActivity(myIntent);
                }
                
            });
        }
        else {
            // Override ListAdapter so that we can display a message
            ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.empty_results_layout, new String[] { "", "No Results" }) 
            { 
                public boolean areAllItemsEnabled() 
                { 
                    return false; 
                } 
                public boolean isEnabled(int position) 
                { 
                    return false; 
                } 
            }; 
            setListAdapter(adapter);
        }        
    }
    
}