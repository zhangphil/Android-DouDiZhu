package com.qp.ddz.scene.popupwindow.adapter;

import java.util.ArrayList;

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

public class RecordChatAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private static final String	TAG	= "RecordChatAdapter";
	
	ArrayList<ChatInfo>	recordlist;

	public class RecordViewItem {
		public TextView	txt_name;
		public TextView	txt_info;
	}
	public RecordChatAdapter(ArrayList<ChatInfo> list) {
		recordlist = list;
	}
	@Override
	public int getCount() {
		if (recordlist != null)
			return recordlist.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (recordlist != null)
			return recordlist.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final RecordViewItem viewitem;
		if (convertView == null) {
			viewitem = new RecordViewItem();
			convertView = LayoutInflater.from(GameActivity.getInstance()).inflate(R.layout.chat_record_itemview, null);
			viewitem.txt_name = (TextView) convertView.findViewById(R.id.chat_txt_record_name);
			viewitem.txt_info = (TextView) convertView.findViewById(R.id.chat_txt_record_info);
			convertView.setTag(viewitem);
		} else {
			viewitem = (RecordViewItem) convertView.getTag();
		}

		viewitem.txt_name.setText(recordlist.get(position).name);
		viewitem.txt_info.setText(recordlist.get(position).info);

		return convertView;
	}

}
