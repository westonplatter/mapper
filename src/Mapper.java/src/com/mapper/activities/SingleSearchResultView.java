// ListingView.java
/**
 * Copyright 2012 Kristin Mead, Usha Kumar
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

import com.mapper.yelp.YelpBusiness;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SingleSearchResultView extends Activity implements OnClickListener
{
    public  static YelpBusiness currentSelection;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
        super.onCreate(savedInstanceState);
               
        Bundle extra = getIntent().getExtras();
        int caller = -1;
        if (extra != null)
           caller = extra.getInt("callerId");
        // Set the business name
        if (caller == R.id.save)
        {   
            setContentView(R.layout.single_result_layout); 
            currentSelection = FavoritesActivity.userSelection;            
            Log.v("INFO", "Received save action from favorite");                
        }
        else if (caller == R.id.remove)
        {
            setContentView(R.layout.remove_favorite_layout);
            currentSelection = FavoritesActivity.userSelection;            
            Log.v("INFO", "Received remove action from favorite");
        }
        else
        {
            setContentView(R.layout.single_result_layout);
            currentSelection = SearchableActivity.userSelection;
            Log.v("INFO", "Received query from search");
        }  
        
        
        TextView businessName = (TextView)findViewById(R.id.business_name);
        businessName.setText(currentSelection.getName());
        Log.i ("INFO", "got business name");
        // Build and set the address
        String streetAddress = currentSelection.getAddress();
        String city  = currentSelection.getCity();
        String state = currentSelection.getState();
        int zip = currentSelection.getPostalCode();
        String address = streetAddress + '\n' + city + ", " + state + ' ' + zip;        

        TextView businessAddress = (TextView)findViewById(R.id.business_address);
        businessAddress.setText(address);
       
        // Set the phone number
        TextView businessPhone = (TextView)findViewById(R.id.business_phone);
        businessPhone.setText(currentSelection.getPhoneNumber());
        
        }
        catch (Exception e)
        {
            Log.v ("Caught exception:", e.getMessage(), e.fillInStackTrace());
        }
    }
    
    public void saveToFavorites(View view)
    {
        // "Add To Favorites" clicked.
        Log.i("INFO", "Add to Favorites button pressed");
        TextView tv = (TextView) findViewById(R.id.save);
        Intent myIntent = new Intent(view.getContext(), FavoritesActivity.class);
        myIntent.putExtra("myAction",R.id.save); 
        currentSelection = SearchableActivity.userSelection; //String) ((TextView) view).getText();
        myIntent.putExtra("myFavorite", currentSelection.getName());//(String)((TextView)tv).getText());
        
        
        startActivity(myIntent);        
    }
    
    public void removeFavorites(View view)
    {
        Log.i ("INFO", "Remove Favorites button pressed");
        // "Add To Favorites" clicked.
        TextView tv = (TextView) findViewById(R.id.remove);
        Intent myIntent = new Intent(view.getContext(), FavoritesActivity.class);
        currentSelection = FavoritesActivity.userSelection; 
        myIntent.putExtra("myAction",R.id.remove); 
        myIntent.putExtra("myFavorite", currentSelection.getName());
               
        startActivity(myIntent);        
    }
    
    @Override
    public void onClick(View view) 
    {        
        Intent myIntent = null;

        if (((TextView) view).getText().equals("Add To Favorites")) {
            TextView tv = (TextView) findViewById(R.id.save);
            
            myIntent = new Intent(view.getContext(), FavoritesActivity.class);
            myIntent.putExtra("myAction",R.id.save); 
           // myIntent.putExtra("myFavorite", currentSelection.getName());
            myIntent.putExtra("myFavorite", (String)((TextView)tv).getText());
        }   
        if (((TextView) view).getText().equals("Remove Favorites")) {
            TextView tv = (TextView) findViewById(R.id.remove);
            myIntent = new Intent(view.getContext(), FavoritesActivity.class);
            myIntent.putExtra("myAction",R.id.remove); 
            //myIntent.putExtra("myFavorite", currentSelection.getName());
            
            myIntent.putExtra("myFavorite", (String)((TextView)tv).getText());
        }
        startActivity(myIntent);
    }   
    
}