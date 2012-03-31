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

public class FavoritesActivity extends PreferenceActivity
{
    Button settingsButton;
    TextView displayText;
    public static final String PREFERENCES_NAME = "MyFavorites";    
    public static final int PREFERENCES_MODE = MODE_PRIVATE; 
    
    
    private static int incrementedValue = 0;
    private SharedPreferences m_pref;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.favorites);    

            PreferenceManager.setDefaultValues(this, PREFERENCES_NAME, PREFERENCES_MODE, R.xml.settings, false);
            m_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()); 
            ArrayList<String> pref_list = getPreferences();            
            if (pref_list == null)
            {
                Log.v("INFO:", "No Preferences available");
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
                    // Add code to call yelp, so we can precisely locate the favorite.
                }
            });            
            

        }
        catch (Exception e)
        {       
            Log.v("Error:Exception caught", e.getMessage());
        }
    }
    public void selfDestruct(View view) {     
        Log.v("info", "In favorite activity");
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