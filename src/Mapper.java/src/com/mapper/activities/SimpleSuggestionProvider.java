// SimpleSuggestionProvider.java
/**
 * Copyright 2012 Usha Kumar
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

import android.content.SearchRecentSuggestionsProvider;
import android.util.Log;

public class SimpleSuggestionProvider extends SearchRecentSuggestionsProvider {
    final static String AUTHORITY = "com.mapper.activities.SimpleSuggestionProvider";
    final static int MODE = DATABASE_MODE_2LINES | DATABASE_MODE_QUERIES;
    
    public SimpleSuggestionProvider() {
        
        super();
        Log.i ("INFO", "SimpleSearchSuggestionsProvider called");
        setupSuggestions(AUTHORITY, MODE);
    }
}