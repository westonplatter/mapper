// FavoritesActivity.java
/**
 * Copyright 2012 Kristin Mead, Usha Kumar
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.xmlpull.v1.XmlPullParserException;

import com.google.android.maps.GeoPoint;
import com.mapper.yelp.YelpBusiness;
import com.mapper.yelp.YelpQueryManager;
import com.mapper.yelp.YelpResultsResponse;

import android.app.ListActivity;
import android.app.SearchManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FavoritesActivity extends PreferenceActivity
{
    Button settingsButton;
    TextView displayText;
    public static final String PREFERENCES_NAME = "MyFavorites";    
    public static final int PREFERENCES_MODE = MODE_PRIVATE; 
    
    
    private static int incrementedValue = 0;
    private SharedPreferences m_pref;
    public  static YelpBusiness userSelection;
    private static YelpQueryManager yelpQueryManager;
    private static YelpResultsResponse yelpResultsResponse;
    private static ArrayList<String> pref_list;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.favorites); 
            PreferenceManager.setDefaultValues(this, PREFERENCES_NAME, PREFERENCES_MODE, R.xml.settings, false);
            m_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Bundle act_bundle = getIntent().getExtras();
            int action=-1;
            String userFav = null;
            if(act_bundle !=null)
            {
                action = act_bundle.getInt("myAction");
                userFav = act_bundle.getString("myFavorite");
            }            
            // If it is save button call to save preference specified.
            if (action == R.id.save)
            {
                Log.v("info:", "SAVE button pressed");
                savePreference (userFav);
                return;
            }                
            // list of favorites initiated. 
            pref_list = getPreferences();            
            if (pref_list == null)
            {
                Log.v("INFO:", "No Preferences available");
                return;
            }
            // Now display the preferences
            // Create a menu of items        
            setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, pref_list));
    
            // Create the list view
            final ListView lv = getListView();
            lv.setTextFilterEnabled(true);
            
    
            // Add a click listener
            lv.setOnItemClickListener(new OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    // When clicked, show a toast with the TextView text                   
                    YelpQueryManager yelpQueryManager = new YelpQueryManager();
                    String query = pref_list.get(position);
                    YelpResultsResponse yelpResultsResponse = yelpQueryManager.search(query); 
                    userSelection = yelpResultsResponse.getBusinesses().get(position);
                    Intent myIntent = new Intent(view.getContext(), SingleSearchResultView.class);                    
                    myIntent.putExtra("callerId", R.id.save); // convey to single search result view that it is from favorites.
                    startActivity(myIntent);                    
                }                                 
            }); 
        }
        catch (Exception e)
        {       
            Log.v("Error:Exception caught", e.getMessage());
        }
    }
   
    public ArrayList<String> getPreferences()
    {
        // Read all the preferences from the file       
        ArrayList<String> fav_list= new ArrayList<String>();        
        Map<String, String> str_map = (Map<String, String>) m_pref.getAll(); 
        Set s=str_map.entrySet();
        Iterator it=s.iterator();        
        while(it.hasNext())
        {
            // key=value separator this by Map.Entry to get key and value
            Map.Entry m =(Map.Entry)it.next();   
            // getValue is used to get value of key in Map
            String fav_item=(String)m.getValue(); 
            fav_list.add(fav_item);            
        }
        return fav_list;        
    }
    
    public void savePreference (String new_pref)
    {           
        SharedPreferences.Editor editor = m_pref.edit(); 
        editor.putString("favourite" + incrementedValue, new_pref); 
        editor.commit();  
        Log.i("INFO","Favourite saved!");                     
        incrementedValue++;        
    }
    
    public  SharedPreferences getSharedPreferences() 
    {        
        return m_pref;   
    }    

}