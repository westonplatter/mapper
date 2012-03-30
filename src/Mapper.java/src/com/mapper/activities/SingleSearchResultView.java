// ListingView.java
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

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SingleSearchResultView extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_result_layout);

        // Set the business name
        TextView businessName = (TextView)findViewById(R.id.business_name);
        businessName.setText(SearchableActivity.userSelection.getName());

        // Build and set the address
        String streetAddress = SearchableActivity.userSelection.getAddress();
        String city  = SearchableActivity.userSelection.getCity();
        String state = SearchableActivity.userSelection.getState();
        int zip = SearchableActivity.userSelection.getPostalCode();
        String address = streetAddress + '\n' + city + ", " + state + ' ' + zip;

        TextView businessAddress = (TextView)findViewById(R.id.business_address);
        businessAddress.setText(address);

        // Set the phone number
        TextView businessPhone = (TextView)findViewById(R.id.business_phone);
        businessPhone.setText(SearchableActivity.userSelection.getPhoneNumber());
    }
}