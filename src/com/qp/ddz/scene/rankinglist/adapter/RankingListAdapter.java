package com.qp.ddz.scene.rankinglist.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qp.ddz.GameActivity;
import com.qp.ddz.utility.CustomFaceManage;
import com.qp.ddz.utility.RankingItem;
import com.qp.ddz.R;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class RankingListAdapter extends BaseAdapter {

	public ArrayList<RankingItem>	m_RankingList;
 

	public class RankingItemView {
		public TextView	num;
		public ImageView img;
		public TextView	name;
		public TextView	score;
		public TextView maxim;
	}
	public RankingListAdapter(ArrayList<RankingItem> RankItemList    ) {
		m_RankingList = RankItemList;
  
 
	}

	@Override
	public int getCount() { 
			return m_RankingList.size();
	}

	@Override
	public Object getItem(int position) {
		if (m_RankingList != null && m_RankingList.size() > 0)
			return m_RankingList.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final RankingItemView itemview;
		if (convertView == null) {
			itemview = new RankingItemView();
			convertView = LayoutInflater.from(GameActivity.getInstance()).inflate(R.layout.ranking_item_view, null);
			itemview.num = (TextView) convertView.findViewById(R.id.ranking_item_num);
			itemview.name = (TextView) convertView.findViewById(R.id.ranking_item_name);
			itemview.score = (TextView) convertView.findViewById(R.id.ranking_item_score);
			 //头像   
			itemview.img=(ImageView) convertView.findViewById(R.id.ranking_item_img);
			 
			itemview.maxim = (TextView) convertView.findViewById(R.id.ranking_item_maxim);
			
			convertView.setTag(itemview);
		} else {
			itemview = (RankingItemView) convertView.getTag();
		}
		int color = 0;
		switch (position) {
			case 0 :
				color = Color.RED;
				break;
			case 1 :
				color = Color.GREEN;
				break;
			case 2 :
				color = Color.YELLOW;
				break;
			default :
				color = Color.rgb(105, 142, 254);
				break;
		}

		RankingItem item = (RankingItem) getItem(position);
		itemview.num.setTextColor(color);
		itemview.name.setTextColor(color);
		itemview.score.setTextColor(color);
		itemview.maxim.setTextColor(color);
		if (item != null) {
			itemview.num.setText((position + 1) + ".");
			itemview.name.setText(item.szName);
			itemview.score.setText(item.szScore);
			
			if (item.imgchecksum!=0  && CustomFaceManage.getInstance() != null) {
				itemview.img.setImageBitmap( CustomFaceManage.getInstance().getBitmap( Integer.toHexString(item.imgchecksum), item.imgchecksum));
			}else{
				itemview.img.setImageResource(R.drawable.hall_playerhead);
			}
			itemview.maxim.setText(item.szMaxim);	
		} else {
			itemview.num.setText("");
			itemview.name.setText("尚未有数据");
			itemview.score.setText("");
			itemview.maxim.setText("");
		}

		return convertView;
	}

}
