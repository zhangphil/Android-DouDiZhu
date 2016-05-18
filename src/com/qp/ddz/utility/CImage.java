package com.qp.ddz.utility;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class CImage {

	Bitmap	bitmap;

	Paint	paint	= null;

	public void LoadImage(Resources res, int id) {
		bitmap = BitmapFactory.decodeResource(res, id);
	}

	public void onDestroy() {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
		bitmap = null;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public Paint getPaint() {
		return paint;
	}
	public int getWidth() {
		if (bitmap != null)
			return bitmap.getWidth();
		return 0;
	}

	public int getHeight() {
		if (bitmap != null)
			return bitmap.getHeight();
		return 0;
	}
	public void setPaint(Paint p) {
		paint = p;
	}

	public void DrawImage(Canvas canvas, int x, int y) {
		if (bitmap == null)
			return;
		canvas.drawBitmap(bitmap, x, y, paint);
	}

	public void DrawImage(Canvas canvas, int x, int y, int w, int h) {
		if (bitmap == null)
			return;
		canvas.save();
		canvas.clipRect(x, y, x + w, y + h);
		canvas.drawBitmap(bitmap, x, y, paint);
		canvas.restore();
	}

	/**
	 * 定点区域 绘制
	 */
	public void DrawImage(Canvas canvas, int desx, int desy, int w, int h, int srcx, int srcy) {
		if (bitmap == null)
			return;
		canvas.save();
		canvas.clipRect(desx, desy, desx + w, desy + h);
		canvas.drawBitmap(bitmap, desx - srcx, desy - srcy, paint);
		canvas.restore();
	}

	public void DrawImage(Canvas canvas, int x, int y, Paint p) {
		if (bitmap == null)
			return;
		canvas.drawBitmap(bitmap, x, y, p);
	}

	public void DrawImage(Canvas canvas, int x, int y, int w, int h, Paint p) {
		if (bitmap == null)
			return;
		canvas.save();
		canvas.clipRect(x, y, x + w, y + h);
		canvas.drawBitmap(bitmap, x, y, p);
		canvas.restore();
	}

	/**
	 * 定点区域 绘制
	 */
	public void DrawImage(Canvas canvas, int desx, int desy, int w, int h, int srcx, int srcy, Paint p) {
		if (bitmap == null)
			return;
		canvas.save();
		canvas.clipRect(desx, desy, desx + w, desy + h);
		canvas.drawBitmap(bitmap, desx - srcx, desy - srcy, p);
		canvas.restore();
	}
}
