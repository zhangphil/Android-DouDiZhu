package com.qp.ddz.utility;

import com.qp.ddz.GameActivity;
import com.qp.ddz.define.GDF;

import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class BtBackGround implements OnTouchListener {

	public ColorMatrixColorFilter	cm_select;

	public ColorMatrixColorFilter	cm_no_select;

	public BtBackGround() {
		cm_select = new ColorMatrixColorFilter(GDF.BT_SELECTED);
		cm_no_select = new ColorMatrixColorFilter(GDF.BT_NOT_SELECTED);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN && v.isClickable()) {
			v.getBackground().setColorFilter(cm_select);
			v.setBackgroundDrawable(v.getBackground());
			((GameActivity) GameActivity.getInstance()).onPlayGameSound(GDF.SOUND_CLICK);
		} else if (event.getAction() != MotionEvent.ACTION_MOVE) {
			v.getBackground().setColorFilter(cm_no_select);
			v.setBackgroundDrawable(v.getBackground());
		}
		return false;
	}

}
