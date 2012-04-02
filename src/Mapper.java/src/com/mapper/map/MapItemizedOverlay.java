// MapItemizedOverlay.java
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

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem>
{
    private Context context;
    private ArrayList<OverlayItem> myOverlays = new ArrayList<OverlayItem>();
    
    /**
     * Default super class constructor
     * @param marker is the icon shown on the map for this location.
     */
    public MapItemizedOverlay(Drawable marker)
    {
        super(boundCenterBottom(marker));
        populate();
    }
    
    /**
     * Adds item to this overlay.
     * @param item
     * @return void
     */
    public void addItem(MapOverlayItem item)
    {
        myOverlays.add(item);
        populate();
    }
    
    
    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#createItem(int)
     */
    @Override
    protected OverlayItem createItem(int i)
    {
        // TODO Auto-generated method stub
        return myOverlays.get(i);
    }

    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#size()
     */
    @Override
    public int size()
    {
        // TODO Auto-generated method stub
        return myOverlays.size();
    }
    
    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#draw(Canvas, MapView, boolean)
     */
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow)
    {
        super.draw(canvas, mapView, shadow);
    }
}
