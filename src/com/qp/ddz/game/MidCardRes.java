package com.qp.ddz.game;

import android.content.res.Resources;
import android.graphics.Canvas;

import com.qp.ddz.R;
/*
*
* 购买完整源码联系 q344717871
* 
* */

public class MidCardRes extends CardRes {

	public final static int	nspacex			= 20;
	public final static int	nspacey			= 20;

	public final static int	nmaxcountx		= 8;
	public final static int	nmaxcountxex	= 10;

	public void onLoadImage(Resources res) {
		imageflagColor.LoadImage(res, R.drawable.flag_color_00);
		imageflagColorSmall.LoadImage(res, R.drawable.flag_color_small_00);
		imageCardBKL.LoadImage(res, R.drawable.card_land_00);
		imageCardBKF.LoadImage(res, R.drawable.card_farm_00);
		imageCardBG.LoadImage(res, R.drawable.card_bg_00);
		imageNumRed.LoadImage(res, R.drawable.num_red_00);
		imageNumBlack.LoadImage(res, R.drawable.num_black_00);
		imageCardKing.LoadImage(res, R.drawable.card_king_00);
		imageCardSmallKing.LoadImage(res, R.drawable.card_smallking_00);
	}

	public void onDrawCard(Canvas canvas, int x, int y, int data[], int count, int viewid) {
		switch (viewid) {
			case 0 : {
				for (int i = 0; i < count; i++) {
					onDrawCard(canvas, data[i], x + (i % nmaxcountx) * nspacex, y + (i / nmaxcountx) * nspacey);
				}
				break;
			}
			case 1 : {
				int startx = 0;

				if (count > nmaxcountxex)
					startx = x - ((10 - 1) * nspacex + getCardWidth()) / 2;
				else
					startx = x - ((count - 1) * nspacex + getCardWidth()) / 2;

				for (int i = 0; i < count && i < nmaxcountxex; i++) {
					onDrawCard(canvas, data[i], startx + (i % nmaxcountxex) * nspacex, y - nspacey);
				}

				if (count > nmaxcountxex) {
					startx = x - ((count - 10 - 1) * nspacex + getCardWidth()) / 2;
					for (int i = 10; i < count; i++) {
						onDrawCard(canvas, data[i], startx + (i % nmaxcountxex) * nspacex, y);
					}
				}

				break;
			}
			case 2 : {
				int startx = 0;
				if (count > nmaxcountx)
					startx = x - (nmaxcountx - 1) * nspacex - getCardWidth();
				else
					startx = x - (count - 1) * nspacex - getCardWidth();
				for (int i = 0; i < count; i++) {
					onDrawCard(canvas, data[i], startx + (i % nmaxcountx) * nspacex, y + (i / nmaxcountx) * nspacey);
				}
				break;
			}
		}
	}

}
