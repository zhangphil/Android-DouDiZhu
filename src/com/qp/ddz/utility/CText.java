package com.qp.ddz.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class CText {
	/**
	 * 截断输出
	 * 
	 * @param canvas
	 *            设备
	 * @param str
	 *            信息
	 * @param x
	 *            目的X坐标
	 * @param y
	 *            目的Y坐标
	 * @param pixel
	 *            输出长度
	 * @param paint
	 *            画笔属性
	 */

	public static final void DrawTextEllip(Canvas canvas, String str, int x, int y, float pixel, Paint paint) {
		// 过滤无效
		if (str == null || str.equals("")) {
			return;
		}
		// 画笔确认
		Paint p = paint == null ? new Paint() : paint;
		// 长度确认
		float strw = p.measureText(str);
		// Y坐标修正
		float ny = y + GetTextY(p);

		// 判断截断
		int count = 0;
		if (strw > pixel) {
			count = paint.breakText(str, true, pixel - p.measureText("..."), null);
			if (count > 0) {
				String temp = str.substring(0, count) + "...";
				canvas.drawText(temp, x, ny, p);
			}
		} else {
			canvas.drawText(str, x, ny, p);
		}
	}

	/**
	 * 截断输出
	 * 
	 * @param canvas
	 * @param str
	 * @param rc
	 * @param paint
	 */
	public static final void DrawTextEllip(Canvas canvas, String str, Rect rc, Paint paint) {
		// 过滤无效
		if (str == null || str.equals("") || rc == null) {
			return;
		}
		// 画笔确认
		Paint p = paint == null ? new Paint() : paint;
		// 长度确认
		float strw = p.measureText(str);
		// Y坐标修正
		float ny = rc.top + GetTextY(p);
		// 判断截断
		int count = 0;
		if (strw > rc.width()) {
			count = paint.breakText(str, true, rc.width() - p.measureText("..."), null);
			if (count > 0) {
				String temp = str.substring(0, count) + "...";
				canvas.drawText(temp, rc.left, ny, p);
			}
		} else {
			canvas.drawText(str, rc.left, ny, p);
		}

	}

	/**
	 * 获取字体高
	 * 
	 * @param p
	 * @return
	 */
	public static final float GetTextHeight(Paint p) {
		FontMetrics fm = p.getFontMetrics();
		return (float) Math.ceil(fm.descent - fm.ascent);
	}

	/**
	 * 计算Y位置
	 * 
	 * @param p
	 * @param nH
	 * @return
	 */
	public static final float GetTextY(Paint p) {
		FontMetrics fm = p.getFontMetrics();
		return (GetTextHeight(p) + p.getTextSize()) / 2 - fm.bottom + fm.descent - fm.ascent + fm.top;

	}

	public static String getTimeString() {
		Date now = new Date();

		SimpleDateFormat temp = new SimpleDateFormat("hh:mm");

		String str = temp.format(now);

		return str;

	}

}
