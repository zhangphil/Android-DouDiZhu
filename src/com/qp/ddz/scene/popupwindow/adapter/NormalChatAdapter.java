package com.qp.ddz.scene.popupwindow.adapter;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class NormalChatAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private static final String	TAG	= "NormalChatAdapter";

	public class NormalItemView {
		TextView	txtitem;
	}

	@Override
	public int getCount() {

		return 10;
	}

	@Override
	public Object getItem(int position) {
		return GameActivity.getInstance().getResources().getString(R.string.chatitem_00 + position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final NormalItemView viewitem;
		if (convertView == null) {
			viewitem = new NormalItemView();
			convertView = LayoutInflater.from(GameActivity.getInstance()).inflate(R.layout.chat_normal_itemview, null);
			viewitem.txtitem = (TextView) convertView.findViewById(R.id.chat_txt_normalitem);
			convertView.setTag(viewitem);
		} else {
			viewitem = (NormalItemView) convertView.getTag();
		}
		String szinfo = GameActivity.getInstance().getResources().getString(R.string.chatitem_00 + position);
		viewitem.txtitem.setText(szinfo);
		return convertView;
	}
}
