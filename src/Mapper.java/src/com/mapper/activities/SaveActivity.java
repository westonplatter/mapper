// SaveActivity.java
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

// This gets called to automatically save the destinations visited.
// This keeps track of the last 50 destinations visited
public class SaveActivity extends PreferenceActivity
{ 
   
    public static final String PREFERENCES_NAME = "SavedPrefs";    
    public static final int PREFERENCES_MODE = MODE_PRIVATE;
    public static final int MAX_ITEMS = 50;    
    
    private static int incrementedValue = 0;
    private SharedPreferences savedPref;    
    private static ArrayList<String> pref_list;
    private int nItems=0;
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {  
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.favorites);             
            savedPref = getSharedPreferences(PREFERENCES_NAME, 0); 
            pref_list = getSavedPreferences();
            nItems = pref_list.size(); 
            // automatically save 
            // get the user selection
            savePreference("DUMMY");
                        
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
        Map<String, String> str_map = (Map<String, String>) savedPref.getAll(); 
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
    
    public void deleteOldest ()
    {    
        Log.i("INFO", "Remove favorites function called ");
        // check if we have already reached the maximum count
        Map<String, String> str_map = (Map<String, String>) savedPref.getAll(); 
        Set s=str_map.entrySet();
        Iterator it=s.iterator();        
        while(it.hasNext())
        {
            // key=value separator this by Map.Entry to get key and value
            Map.Entry m =(Map.Entry)it.next();   
            // getValue is used to get value of key in Map
            String firstItemKey = "location_0"; 
            String itemKey=(String)m.getKey(); 
            if (firstItemKey.contains(firstItemKey))
            {
                Log.d ("DEBUG: REMOVED Item", itemKey+":"+m.getValue()); 
                SharedPreferences.Editor editor = savedPref.edit();
                editor.remove(itemKey);  // get the key, so we can remove it
                editor.commit();                                
                nItems--;
                break; // skip this entry from being added to list that need to be stored
            }            
        }        
    }
    
    public void savePreference (String new_pref)
    {     
        if (nItems >= MAX_ITEMS)
        {
            // automatically delete the oldest pref, so there is space for new one
            deleteOldest();
        }
        SharedPreferences.Editor editor = savedPref.edit(); 
        UUID id = UUID.randomUUID();
        editor.putString("location-" + nItems+"-"+id, new_pref); 
        editor.commit();  
        Log.i("INFO","Favourite saved!"); 
        nItems++;
    }
    
    public  SharedPreferences getSharedPreferences() 
    {        
        return savedPref;   
    }    

}