// FavoritesActivity.java
/**
 * Copyright 2012 Usha Kumar
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

import android.app.ListActivity;

import android.content.Context;
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

// This gets called to automatically save the destinations visited.
// This keeps track of the last 25 destinations visited
public class SaveActivity extends PreferenceActivity
{ 
   
    public static final String PREFERENCES_NAME = "SavedPrefs";    
    public static final int PREFERENCES_MODE = MODE_PRIVATE; 
    
    
    private static int incrementedValue = 0;
    private SharedPreferences saved_pref;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.favorites); 
            PreferenceManager.setDefaultValues(this, PREFERENCES_NAME, PREFERENCES_MODE, R.xml.settings, false);
            saved_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); 
            ArrayList<String> pref_list = getSavedPreferences();
            if (pref_list.isEmpty())
            {
                Log.v("INFO:", "No Saved Preferences available");
                return;
            }
            // Now display the preferences
            // Create a menu of items        
            setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, pref_list));
    
            // Create the list view
            ListView lv = getListView();
            lv.setTextFilterEnabled(true);
    
            // Add a click listener
            lv.setOnItemClickListener(new OnItemClickListener()
            {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    // When clicked, show a toast with the TextView text
                    Toast.makeText(getApplicationContext(),
                                    ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                }
            });              
        }
        catch (Exception e)
        {       
            Log.v("Error:Exception caught", e.getMessage());
        }
    }
    
    public ArrayList<String> getSavedPreferences()
    {
        // Read all the preferences from the file       
        ArrayList<String> fav_list= null;        
        Map<String, String> str_map = (Map<String, String>) saved_pref.getAll(); 
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
        SharedPreferences.Editor editor = saved_pref.edit(); 
        editor.putString("location" + incrementedValue, new_pref); 
        editor.commit();  
        Log.i("INFO","Favourite saved!");                     
        incrementedValue++; 
        
    }
    public  SharedPreferences getSharedPreferences() 
    {        
        return saved_pref;   
    }    

}