package com.skyway.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class SkywayOverlay extends Overlay implements Overlay.Snappable {

	private GeoPoint point1;
	private GeoPoint point2;

	public SkywayOverlay(GeoPoint point1, GeoPoint point2) {
		this.point1 = point1;
		this.point2 = point2;
	}

	@Override
	public void draw(Canvas canvas, MapView mv, boolean shadow) {
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
		mPaint.setStrokeWidth(9);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.RED);
		canvas.drawPath(p, mPaint);
		super.draw(canvas, mv, shadow);
	}

	@Override
	public boolean onSnapToItem(int x, int y, Point snapPoint, MapView mapView) {
		// TODO Auto-generated method stub
		return false;
	}

	
	//http://api.yelp.com/business_review_search?&term=chinese&limit=10&tl_lat=44.961761&tl_long=-93.285255&br_lat=44.978764&br_long=-93.262596&ywsid=TwGnx42_E0fDmMN4ZTy5gw
}