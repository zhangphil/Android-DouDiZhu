package com.qp.ddz.game;
/*
*
* 购买完整源码联系 q344717871
* 
* */

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.qp.ddz.utility.CImage;
import com.qp.ddz.R;

public class CardRes {

	public CImage	imageflagColor		= new CImage();
	public CImage	imageflagColorSmall	= new CImage();

	public CImage	imageNumRed			= new CImage();
	public CImage	imageNumBlack		= new CImage();

	public CImage	imageCardBKL		= new CImage();
	public CImage	imageCardBKF		= new CImage();
	public CImage	imageCardBG			= new CImage();

	public CImage	imageCardKing		= new CImage();
	public CImage	imageCardSmallKing	= new CImage();

	static Paint	m_MaskPaint;
	static {
		m_MaskPaint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		float fcolor[] = {1, 0, 0, 0, -50, 0, 1, 0, 0, -50, 0, 0, 1, 0, 255, 0, 0, 0, 1, -10,};
		cm.set(fcolor);
		ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
		m_MaskPaint.setColorFilter(cf);
	}
	public CardRes() {

	}

	public void onLoadImage(Resources res) {
		imageflagColor.LoadImage(res, R.drawable.flag_color);
		imageflagColorSmall.LoadImage(res, R.drawable.flag_color_small);
		imageCardBKL.LoadImage(res, R.drawable.card_land);
		imageCardBKF.LoadImage(res, R.drawable.card_farm);
		imageCardBG.LoadImage(res, R.drawable.card_bg);
		imageNumRed.LoadImage(res, R.drawable.num_red);
		imageNumBlack.LoadImage(res, R.drawable.num_black);
		imageCardKing.LoadImage(res, R.drawable.card_king);
		imageCardSmallKing.LoadImage(res, R.drawable.card_smallking);
	}

	public void onDestroy() {
		imageflagColor.onDestroy();
		imageflagColorSmall.onDestroy();
		imageCardBKL.onDestroy();
		imageCardBKF.onDestroy();
		imageCardBG.onDestroy();
		imageNumRed.onDestroy();
		imageNumBlack.onDestroy();
		imageCardKing.onDestroy();
		imageCardSmallKing.onDestroy();
	}

	public void onDrawCard(Canvas canvas, int data, int x, int y) {
		if (data == 0xff) {
			imageCardBKL.DrawImage(canvas, x, y);
			return;
		}
		
		if (data != 0) {
			if (data == 0x4e || data == 0x4f) {
				onDrawKingCard(canvas, data, x, y);
			} else {
				imageCardBG.DrawImage(canvas, x, y);

				int value = (data & 0x0F) - 1;
				int color = ((data & 0xF0) >> 4);

				switch (color) {
					case 0 :
					case 2 :
						imageNumRed.DrawImage(canvas, x, y + 2, imageNumRed.getWidth() / 13, imageNumRed.getHeight(), value * imageNumRed.getWidth() / 13, 0);
						break;
					case 1 :
					case 3 :
						imageNumBlack.DrawImage(canvas, x, y + 2, imageNumBlack.getWidth() / 13, imageNumBlack.getHeight(), value * imageNumBlack.getWidth()
								/ 13, 0);
						break;
				}

				imageflagColorSmall.DrawImage(canvas, x + 4, y + 2 + imageNumRed.getHeight(), imageflagColorSmall.getWidth() / 4,
						imageflagColorSmall.getHeight(), color * imageflagColorSmall.getWidth() / 4, 0);

				imageflagColor.DrawImage(canvas, x + imageCardBG.getWidth() - imageflagColor.getWidth() / 4 - 2, y + imageCardBG.getHeight() - 2
						- imageflagColor.getHeight(), imageflagColor.getWidth() / 4, imageflagColor.getHeight(), color * imageflagColor.getWidth() / 4, 0);
			}

		}

	}

	private void onDrawKingCard(Canvas canvas, int data, int x, int y) {
		if (data == 0x4e)
			imageCardSmallKing.DrawImage(canvas, x, y);
		else
			imageCardKing.DrawImage(canvas, x, y);

	}

	public int getCardWidth() {
		if (imageCardBG != null)
			return imageCardBG.getWidth();
		return 0;
	}

	public int getCardHeight() {
		if (imageCardBG != null)
			return imageCardBG.getHeight();
		return 0;
	}

	public void onDrawCard(Canvas canvas, int data, int x, int y, boolean mask) {
		if (data != 0) {

			if (data == 0x4e || data == 0x4f) {
				onDrawKingCard(canvas, data, x, y, mask);
			} else {
				Paint p = null;
				if (mask) {
					p = m_MaskPaint;
				}
				imageCardBG.DrawImage(canvas, x, y, p);

				int value = (data & 0x0F) - 1;
				int color = ((data & 0xF0) >> 4);

				switch (color) {
					case 0 :
					case 2 :
						imageNumRed.DrawImage(canvas, x, y + 2, imageNumRed.getWidth() / 13, imageNumRed.getHeight(), value * imageNumRed.getWidth() / 13, 0);
						break;
					case 1 :
					case 3 :
						imageNumBlack.DrawImage(canvas, x, y + 2, imageNumBlack.getWidth() / 13, imageNumBlack.getHeight(), value * imageNumBlack.getWidth()
								/ 13, 0);
						break;
				}

				imageflagColorSmall.DrawImage(canvas, x + 4, y + 2 + imageNumRed.getHeight(), imageflagColorSmall.getWidth() / 4,
						imageflagColorSmall.getHeight(), color * imageflagColorSmall.getWidth() / 4, 0);

				imageflagColor.DrawImage(canvas, x + imageCardBG.getWidth() - imageflagColor.getWidth() / 4 - 2, y + imageCardBG.getHeight() - 2
						- imageflagColor.getHeight(), imageflagColor.getWidth() / 4, imageflagColor.getHeight(), color * imageflagColor.getWidth() / 4, 0);
			}

		}

	}

	private void onDrawKingCard(Canvas canvas, int data, int x, int y, boolean mask) {
		Paint p = null;
		if (mask) {
			p = m_MaskPaint;
		}
		if (data == 0x4e) {
			imageCardSmallKing.DrawImage(canvas, x, y, p);
		} else {
			imageCardKing.DrawImage(canvas, x, y, p);
		}

	}
}
