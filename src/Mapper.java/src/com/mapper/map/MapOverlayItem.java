//MapOverlayItem.java
/**
 * Copyright 2012 Weston Platter
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
package com.mapper.map;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
/**
 * @author Weston Platter
 *
 */
public class MapOverlayItem extends OverlayItem
{

    private GeoPoint    geoPoint;
    private String      point;
    private String      snippet;
    
    /**
     * Default constructor of superclass.
     * @param point
     * @param title
     * @param snippet
     */
    public MapOverlayItem(GeoPoint point, String title, String snippet)
    {
        super(point, title, snippet);
    }
    
    //see OverlayItem class for inherited class methods.
}
