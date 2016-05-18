package com.qp.ddz.scene.shop.adapter;

import java.util.ArrayList;

import com.qp.ddz.R;
import com.qp.ddz.define.GDF;
import com.qp.ddz.GameActivity;
import com.qp.lib.tag.SerialItem;
import com.qp.lib.utility.SerialRecord;

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

public class SerialAdapter extends BaseAdapter {

	public final static String	TAG	= "SerialAdapter";
	String						m_szUin;
	ArrayList<SerialItem>		m_SerilaItemList;

	public SerialAdapter(String szUin) {
		m_szUin = szUin;
		m_SerilaItemList = SerialRecord.m_SerialRecord;

	}
	public class SerialItemView {
		TextView	txt_name;
		TextView	txt_serial;
		TextView	txt_data;
	}
	@Override
	public int getCount() {
		int count = 0;
		if (m_SerilaItemList != null && m_szUin != null && !m_szUin.equals("")) {
			for (int i = 0; i < m_SerilaItemList.size(); i++) {
				if (m_szUin.equals(m_SerilaItemList.get(i).szUin)) {
					count++;
				}
			}
		}
		return count;
	}
	@Override
	public Object getItem(int position) {
		if (m_SerilaItemList != null) {
			int count = 0;
			for (int i = 0; i < m_SerilaItemList.size(); i++) {
				if (m_szUin.equals(m_SerilaItemList.get(i).szUin)) {
					if (count == position) {
						return m_SerilaItemList.get(i);
					}
					count++;
				}
			}
		}
		return null;
	}
	@Override
	public long getItemId(int position) {

		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final SerialItemView itemview;
		if (convertView == null) {
			itemview = new SerialItemView();
			convertView = LayoutInflater.from(GameActivity.getInstance()).inflate(R.layout.serila_item_view, null);
			itemview.txt_name = (TextView) convertView.findViewById(R.id.serila_item_view_name);
			itemview.txt_serial = (TextView) convertView.findViewById(R.id.serila_item_view_order);
			itemview.txt_data = (TextView) convertView.findViewById(R.id.serila_item_view_time);

			convertView.setTag(itemview);
		} else {
			itemview = (SerialItemView) convertView.getTag();
		}

		SerialItem item = (SerialItem) getItem(position);
		if (item != null) {
			itemview.txt_name.setText("商品名称：" + item.szProductName);
			itemview.txt_serial.setText("订单编号：" + item.szSerial);
			itemview.txt_data.setText("记录时间：" + GDF.getDate(item.lRecordTime));
		}

		return convertView;
	}
}
