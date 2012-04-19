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

import java.util.UUID;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.mapper.yelp.YelpBusiness;
import com.mapper.yelp.YelpQueryManager;
import com.mapper.yelp.YelpResultsResponse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class FavoritesActivity extends PreferenceActivity
{
    Button settingsButton;
    TextView displayText;
    public static final String PREFERENCES_NAME = "MyFavorites";    
    public static final int PREFERENCES_MODE = MODE_PRIVATE; 
    public static final int MAX_ITEMS=50;
    private static final int DIALOG_YES_NO_MESSAGE = 1;
    public static final int SAVE = 1;
    public static final int LIST = 2;
    public static final int REMOVE = 3;
    
    private SharedPreferences m_pref;
    public  static YelpBusiness userSelection;
    private static ArrayList<String> pref_list;
    private int nItems =0;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.favorites); 
            PreferenceManager.setDefaultValues(this, PREFERENCES_NAME, PREFERENCES_MODE, R.xml.settings, false);
            m_pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());// list of favorites initiated. 
            pref_list = getPreferences();            
            if (pref_list == null)
            {
                Log.v("INFO:", "No Preferences available");
                return;
            }
            nItems = pref_list.size(); // make number of items same as the items already in the list
            Log.i("INFO", "Num Items");
            Log.d("DEBUG:OnCreate", "Number of Favorites stored: " + Integer.toString(nItems));         
            
            
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
                displayFavorites(SAVE);
                return;
            }  
            
            if (action == R.id.remove)
            {
                Log.v("info:", "remove button pressed");
                Log.d("DEBUG", "Remove choice: "+userFav);
                removePreference (userFav);
                displayFavorites(SAVE);
                return;
            } 

            displayFavorites(LIST);
            
        }
        catch (Exception e)
        {       
            Log.v("Error:Exception caught", e.getMessage());
        }
    }
    
    public void displayFavorites(int caller)
    {
        try
        {
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
        
        Log.i("INFO", "In display favorites");
        final int button_id = (caller == REMOVE)? R.id.remove:R.id.save;        
        
        // Add a click listener
        lv.setOnItemClickListener(new OnItemClickListener()
        {   
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // When clicked, show a toast with the TextView text                   
                YelpQueryManager yelpQueryManager = new YelpQueryManager();
                
                //String query = pref_list.get(position);
                //view = getCurrentFocus();
                //String query = (String) getListAdapter().getItem(position);
                String query = (String) ((TextView) view).getText();
                Log.d ("DEBUG: query = ", query);
                YelpResultsResponse yelpResultsResponse = yelpQueryManager.search(query); 
                int numBusinesses = yelpResultsResponse.getBusinesses().size();
                
                while (numBusinesses > 0)
                {
                    userSelection = yelpResultsResponse.getBusinesses().get(numBusinesses -1); //.get(position);
                    Log.d("DEBUG: current selection = ", userSelection.getName());
                    if (userSelection.getName().equals( query))
                    {
                        
                        Intent myIntent = new Intent(view.getContext(), SingleSearchResultView.class);                 
                        myIntent.putExtra("callerId", button_id); // convey to single search result view that it is from favorites.
                        startActivity(myIntent); 
                    } 
                    else
                    {
                        Log.d("DEBUG: Did not find selection = ", userSelection.getName()+":"+query);
                    }
                    numBusinesses--;
                }
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
        Log.i("INFO", "getPreferences");
        // Read all the preferences from the file       
        ArrayList<String> fav_list= new ArrayList<String>();        
        Map<String, String> str_map = (Map<String, String>) m_pref.getAll(); 
        Set s=str_map.entrySet();
        Iterator it=s.iterator();  
        int count=0;
        while(it.hasNext())
        {
            // key=value separator this by Map.Entry to get key and value
            Map.Entry m =(Map.Entry)it.next();   
            // getValue is used to get value of key in Map
            String fav_item=(String)m.getValue(); 
            fav_list.add(fav_item); 
            count++;
        }
        
        nItems = count;
        return fav_list;        
    }
    
    public void savePreference (String new_pref)
    {   
     // check if we have already reached the maximum count
       
        if (nItems >= MAX_ITEMS)
        {
            Log.i ("INFO", "MAX ITEMs reached, remove some favorites to proceed further");
            showDialog(DIALOG_YES_NO_MESSAGE); 
            return;
        }
        Log.i("INFO", "Save Favorite");
        Log.d ("DEBUG: save  favorite: ", new_pref);
        UUID id = UUID.randomUUID();
        SharedPreferences.Editor editor = m_pref.edit(); 
        editor.putString("favourite-" + id, new_pref); 
        editor.commit();  
        Log.i("INFO","Favourite saved!");                     
        nItems++;        
    }
    
    public  SharedPreferences getSharedPreferences() 
    {        
        return m_pref;   
    }   
    
    public void removePreference (String new_pref)
    {    
        Log.i("INFO", "Remove favorites function called ");
        // check if we have already reached the maximum count
        Map<String, String> str_map = (Map<String, String>) m_pref.getAll(); 
        Set s=str_map.entrySet();
        Iterator it=s.iterator();        
        while(it.hasNext())
        {
            // key=value separator this by Map.Entry to get key and value
            Map.Entry m =(Map.Entry)it.next();   
            // getValue is used to get value of key in Map
            String fav_item=(String)m.getValue(); 
            if (fav_item.equals(new_pref))
            {
                SharedPreferences.Editor editor = m_pref.edit();
                editor.remove(m.getKey().toString());  // get the key, so we can remove it
                editor.commit(); 
                Log.d ("DEBUG: REMOVE ", new_pref+":"+fav_item);
                Log.i ("INFO", "Favorite removed");
                nItems--;
                break; // skip this entry from being added to list that need to be stored
            }            
        }        
    }
    
    protected Dialog onCreateDialog(int id) 
    {
        Dialog dialog = null;
        switch (id)
        {
            case 1:       
            AlertDialog.Builder builder = new AlertDialog.Builder(this); 
            builder.setMessage("MAX favorites reached, Do you want to remove some favorites?") 
               .setCancelable(false) 
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
                   public void onClick(DialogInterface dialog, int id) { 
                       // User want to see list of favorites, to make space
                       displayFavorites(REMOVE);
                   } 
               }) 
               .setNegativeButton("No", new DialogInterface.OnClickListener() { 
                   public void onClick(DialogInterface dialog, int id) { 
                        dialog.cancel(); 
                   } 
               }) 
               //Set your icon here 
               .setTitle("Alert!");               
            dialog = builder.create();              
        }
        return dialog;
    }     
    
}