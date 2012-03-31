// CampusOverlay.java
/**
 * Copyright 2012 Kristin Mead
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
package com.mapper.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import com.mapper.activities.MplsSkywayMapActivity;

/**
 * 
 * @author kristinmead
 * 
 */
public class MapOverlay extends Overlay implements Overlay.Snappable
{

    private GeoPoint point1;
    private GeoPoint point2;
    private int lineColor = Color.GRAY;
    private int lineWidth = 9;

    /**
     * 
     * @param point1
     * @param point2
     */
    public MapOverlay(GeoPoint point1, GeoPoint point2)
    {
        this.point1 = point1;
        this.point2 = point2;
    }

    /**
     * 
     */
    @Override
    public void draw(Canvas canvas, MapView mv, boolean shadow)
    {
        Projection projection = mv.getProjection();

        Path p = new Path();

        Point from = new Point();
        Point to = new Point();

        projection.toPixels(this.point1, from);
        projection.toPixels(this.point2, to);

        p.moveTo(from.x, from.y);
        p.lineTo(to.x, to.y);

        Paint mPaint = new Paint();
        mPaint = new Paint();
        mPaint.setStrokeWidth(lineWidth);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(lineColor);
        canvas.drawPath(p, mPaint);
        super.draw(canvas, mv, shadow);
    }

    /**
     * 
     */
    @Override
    public boolean onSnapToItem(int x, int y, Point snapPoint, MapView mapView)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event, MapView mapView) {
        // ---when user lifts his finger---
        if (event.getAction() == MotionEvent.ACTION_UP) {
            MplsSkywayMapActivity.followLocation = false;
        }
        
        return false;
    }

    public int getLineWidth()
    {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth)
    {
        this.lineWidth = lineWidth;
    }

    public int getLineColor()
    {
        return lineColor;
    }

    public void setLineColor(int lineColor)
    {
        this.lineColor = lineColor;
    }

}