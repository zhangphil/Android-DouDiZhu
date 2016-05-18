package com.qp.ddz.scene.popupwindow.adapter;

import java.util.ArrayList;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qp.ddz.GameActivity;
import com.qp.ddz.utility.MsgRecordItem;
import com.qp.ddz.R;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class HornRecordAdapter extends BaseAdapter {

	ArrayList<MsgRecordItem>	szHornInfoRecord;

	public class HornRecordItemView {
		TextView	RecordText;
	}
	public HornRecordAdapter(ArrayList<MsgRecordItem> list) {
		szHornInfoRecord = list;
	}
	@Override
	public int getCount() {
		if (szHornInfoRecord != null)
			return szHornInfoRecord.size();
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (szHornInfoRecord != null)
			return szHornInfoRecord.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final HornRecordItemView itemview;
		if (convertView == null) {
			itemview = new HornRecordItemView();
			convertView = LayoutInflater.from(GameActivity.getInstance()).inflate(R.layout.horn_record_itemview, null);
			itemview.RecordText = (TextView) convertView.findViewById(R.id.horn_record_txt);
			convertView.setTag(itemview);
		} else {
			itemview = (HornRecordItemView) convertView.getTag();
		}

		MsgRecordItem item = szHornInfoRecord.get(position);
		if(item.nType == MsgRecordItem.TYPE_SYS)
			itemview.RecordText.setTextColor(Color.YELLOW);
		else
			itemview.RecordText.setTextColor(Color.WHITE);
		itemview.RecordText.setText(item.szMsg);
		return convertView;
	}

}
