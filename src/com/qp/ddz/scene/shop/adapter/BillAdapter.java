package com.qp.ddz.scene.shop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF; 
 

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

public class BillAdapter extends BaseAdapter {

	public final static String	TAG	= "SerialAdapter";
	 
	ArrayList<Bill>		lst;

	public BillAdapter(  List< Bill > listitem) {
	 
		lst = (ArrayList<Bill>) listitem;

	}
	public class SerialItemView {
		TextView	txt_name;
		TextView	txt_serial;
		TextView	txt_data;
		TextView 	txt_state;
	}
	@Override
	public int getCount() {
		return lst.size();
	}
	
	@Override
	public Object getItem(int i) {
		 
		return lst.get(i);
				  
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
			itemview.txt_state=(TextView) convertView.findViewById(R.id.serila_item_view_state);
			convertView.setTag(itemview);
		} else {
			itemview = (SerialItemView) convertView.getTag();
		}

		Bill item = (Bill) getItem(position);
		if (item != null) {
			itemview.txt_name.setText("商品名称：" + item.goodsname);
			itemview.txt_serial.setText("订单编号：" + item.billID);
			itemview.txt_data.setText("记录时间：" +  item.buytime);//GDF.getDate(item.lRecordTime));
			 
			//订单状态 1未支付 2交易成功 3交易取消 
			if(item.pstate==1){
				itemview.txt_state.setText("订单状态：未支付");
			}if(item.pstate==2){
				itemview.txt_state.setText("订单状态：交易成功");
			}if(item.pstate==3){
				itemview.txt_state.setText("订单状态：交易取消 ");
			}
			 
			
		}

		return convertView;
	}
}
