package com.qp.ddz.scene.server.adapter;

import java.util.ArrayList;

import com.qp.ddz.GameActivity;
import com.qp.lib.tag.ServerItem;
import com.qp.ddz.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class ServerItemAdapter extends BaseAdapter {

	Context					context;
	ArrayList<ServerItem>	serveritem;

	public class ServerItemView {
		ImageView	logo;
		TextView	name;
		TextView	counts;

	}

	public ServerItemAdapter(Context context, ArrayList<ServerItem> listitem) {
		this.context = context;
		this.serveritem = listitem;
	}

	@Override
	public int getCount() {
		return serveritem.size();
	}

	@Override
	public Object getItem(int position) {
		return serveritem.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ServerItemView itemview;
		if (convertView == null) {
			itemview = new ServerItemView();
			convertView = LayoutInflater.from(context).inflate(R.layout.server_itemview, null);
			itemview.name = (TextView) convertView.findViewById(R.id.server_itemview_name);
			itemview.counts = (TextView) convertView.findViewById(R.id.server_itemview_count);
			itemview.logo = (ImageView) convertView.findViewById(R.id.server_itemview_logo);
			convertView.setTag(itemview);
		} else {
			itemview = (ServerItemView) convertView.getTag();
		}

		ServerItem item = (ServerItem) getItem(position);
		switch (item.nType) {
			case 0 :
				itemview.logo.setBackgroundDrawable(GameActivity.getInstance().getResources().getDrawable(R.drawable.room_item_mid_01));
				break;
			case 1 :
				itemview.logo.setBackgroundDrawable(GameActivity.getInstance().getResources().getDrawable(R.drawable.room_item_mid_02));
				break;
			case 2 :
				itemview.logo.setBackgroundDrawable(GameActivity.getInstance().getResources().getDrawable(R.drawable.room_item_mid_03));
				break;
			case 3 :
				itemview.logo.setBackgroundDrawable(GameActivity.getInstance().getResources().getDrawable(R.drawable.room_item_mid_04));
				break;
			case 4 :
				itemview.logo.setBackgroundDrawable(GameActivity.getInstance().getResources().getDrawable(R.drawable.room_item_mid_05));
				break;
			default :
				itemview.logo.setBackgroundDrawable(GameActivity.getInstance().getResources().getDrawable(R.drawable.room_item_mid_01));
				break;
		}

		itemview.name.setText(item.szServerName);
		itemview.counts.setText(""+item.lMinEnterScore);
		// itemview.counts.setText(item.lOnLineCount + "/" + item.lOnFullCount +
		// "\n" + item.lMinEnterScore);

		return convertView;
	}
}
