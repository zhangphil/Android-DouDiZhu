package com.qp.ddz.scene.popupwindow.adapter;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class ExpressionAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private static final String	TAG	= "ExpressionAdapter";

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 16;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ImageView viewitem;
		if (convertView == null) {
			convertView = LayoutInflater.from(GameActivity.getInstance()).inflate(R.layout.chat_expitem_view, null);
			viewitem = (ImageView) convertView.findViewById(R.id.chat_expression_itemview);
			convertView.setTag(viewitem);
		} else {
			viewitem = (ImageView) convertView.getTag();
		}

		viewitem.setBackgroundResource(R.drawable.girl_ex_00 + position);
		return convertView;
	}

}
