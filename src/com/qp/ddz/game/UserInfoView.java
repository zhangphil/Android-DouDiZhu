package com.qp.ddz.game;

import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.ClipboardManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qp.ddz.GameActivity;
import com.qp.lib.utility.Util;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF;
import com.smw.net.UserItem;
/*
*
* 购买完整源码联系 q344717871
* 
* */

public class UserInfoView {

	@SuppressWarnings("unused")
	private static final String	TAG	= "UserInfo";

	static PopupWindow			m_PopupWindow;
	static View popupview = null;
//	static int col=Color.RED;
	static int r=88,g=88,b=88;
	
	public static void UpdateColor(   )
	{
		 
 
		if (m_PopupWindow != null  && popupview!=null ) {
			TextView txt2 = (TextView) popupview.findViewById(R.id.user_info_score);
			
			//百万以内不闪
			if(txt2.getText().toString().length()<12){
				TextView n = (TextView) popupview.findViewById(R.id.user_info_maxim);
				
				TextView txt0 = (TextView) popupview.findViewById(R.id.user_info_name);
				txt0.setTextColor(n.getTextColors() );
 
				txt2.setTextColor(n.getTextColors() );
				return;
			}
			
			Random ran =new Random(System.currentTimeMillis());
			TextView txt0 = (TextView) popupview.findViewById(R.id.user_info_name);
			txt0.setTextColor(Color.rgb(r+ran.nextInt(150),g+ran.nextInt(150),b+ran.nextInt(150)) );

//			TextView txt1 = (TextView) popupview.findViewById(R.id.user_info_id);
//			txt1.setTextColor(color);


			txt2.setTextColor(Color.rgb(r+ran.nextInt(150),g+ran.nextInt(150),b+ran.nextInt(150)));
		}
	}
	
	public static void onShowUserInfo( UserItem item) {
		if (item == null) {
			onDestroy();
			return;
		}
		if (m_PopupWindow != null) {
			onDestroy();
		}
		 
		if (m_PopupWindow == null) {

			popupview = GameActivity.getInstance().getLayoutInflater().inflate(R.layout.user_info_view, null, false);

			m_PopupWindow = new PopupWindow(popupview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

			m_PopupWindow.setBackgroundDrawable(new BitmapDrawable());
			m_PopupWindow.setOutsideTouchable(true);

		} else {
			popupview = m_PopupWindow.getContentView();
		}

		Button bt = (Button) popupview.findViewById(R.id.user_info_bt_getid);
		bt.setTag(item.GetGameID());  //item.GetGameID());
		bt.setText("获取ID");
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ClipboardManager cm = (ClipboardManager) GameActivity.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
				cm.setText(v.getTag() + "");
				Toast.makeText(GameActivity.getInstance(), "获取用户ID：" + v.getTag(), Toast.LENGTH_SHORT).show();
			}
		});

		ImageView img = (ImageView) popupview.findViewById(R.id.user_info_head);
//		 //把历史的ImageView 图片对象（image_)释放
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getBackground();
//        if(bitmapDrawable!=null)
//        { 
//	        Bitmap hisBitmap = bitmapDrawable.getBitmap(); 
//	        if(hisBitmap.isRecycled()==false)
//	        { 
//	        	hisBitmap.recycle(); 
//	        } 
//        }
		
		//if (useritem != null && item.GetGameID() == useritem.GetGameID() && GameActivity.m_ImageUserHead != null) {
		//	img.setImageDrawable(GameActivity.m_ImageUserHead);
	//	} else {
		//img.setBackgroundResource(R.drawable.head_00);
			
		if ( item.GetFaceImage() != null) {
				img.setImageBitmap( item.GetFaceImage() );
		} else {
				if (item.GetGender() == GDF.GENDER_MANKIND)
				//	img.setBackgroundResource(R.drawable.head_00);
					img.setImageDrawable( GameActivity.getInstance().getResources().getDrawable(R.drawable.head_00));

				else
				//	img.setBackgroundResource(R.drawable.head_01);
					img.setImageDrawable( GameActivity.getInstance().getResources().getDrawable(R.drawable.head_01));

		}
		
		 
		//}

		TextView txt0 = (TextView) popupview.findViewById(R.id.user_info_name);
		txt0.setText(item.GetNickName());

		TextView txt1 = (TextView) popupview.findViewById(R.id.user_info_id);
		txt1.setText("ID：" + item.GetGameID());

		TextView txt2 = (TextView) popupview.findViewById(R.id.user_info_score);
		txt2.setText("游戏币：" + item.GetUserScore());

		txt2 = (TextView) popupview.findViewById(R.id.user_info_rank);
		txt2.setText("排  名：" + item.rank);
		
		  txt2 = (TextView) popupview.findViewById(R.id.user_info_lv);
		txt2.setText("级  别：" + Util.getlv( item.GetUserScore()) );
		
		TextView txt3 = (TextView) popupview.findViewById(R.id.user_info_win);
		txt3.setText("胜  利：" + item.GetUserWinCount());

		TextView txt4 = (TextView) popupview.findViewById(R.id.user_info_lose);
		txt4.setText("失  败：" + item.GetUserLostCount());

		TextView txt5 = (TextView) popupview.findViewById(R.id.user_info_escape);
		txt5.setText("逃  跑：" + item.escapecount  );
		
		TextView txt6 = (TextView) popupview.findViewById(R.id.user_info_maxim);
		txt6.setText("签  名：" + item.maxim  );
		
		m_PopupWindow.showAtLocation(GameActivity.getInstance().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
	}
	public static void onDestroy() {
		if (m_PopupWindow != null) {
			m_PopupWindow.dismiss();
			popupview=null;
		}
	}

}
