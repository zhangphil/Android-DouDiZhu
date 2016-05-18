package com.qp.ddz.scene.popupwindow;

import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qp.ddz.GameActivity;
import com.qp.lib.utility.Util;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF;
import com.smw.net.UserItem;
//import com.nd.commplatform.NdCommplatform;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class UserInfoViewRoom {

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
			if(txt2.getText().toString().length()<7) return;
			
			Random ran =new Random(System.currentTimeMillis());
			TextView txt0 = (TextView) popupview.findViewById(R.id.user_info_name);
			txt0.setTextColor(Color.rgb(r+ran.nextInt(150),g+ran.nextInt(150),b+ran.nextInt(150)) );

//			TextView txt1 = (TextView) popupview.findViewById(R.id.user_info_id);
//			txt1.setTextColor(color);

			txt2.setTextColor(Color.rgb(r+ran.nextInt(150),g+ran.nextInt(150),b+ran.nextInt(150)));
		}
	}
	
	public static void onShowUserInfo(UserItem item) {
		if (item == null) {
			onDestroy();
			return;
		}
		if (m_PopupWindow != null) {
			onDestroy();
		}
		 
		if (m_PopupWindow == null) {

			popupview = GameActivity.getInstance().getLayoutInflater().inflate(R.layout.user_info_view2, null, false);

			m_PopupWindow = new PopupWindow(popupview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

			m_PopupWindow.setBackgroundDrawable(new BitmapDrawable());
			m_PopupWindow.setOutsideTouchable(true);

		} else {
			popupview = m_PopupWindow.getContentView();
		}

		// 修改签名
		Button bt = (Button) popupview.findViewById(R.id.user_info_bt_setmaxim);
		bt.setTag(item.GetGameID());
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SetMaxim.Show( );
				//UserInfoViewRoom.onDestroy();
				//NdCommplatform.getInstance().ndEnterUserSetting(0, GameActivity.getInstance());
			}
		});
		
		
		// 修改昵称
				  bt = (Button) popupview.findViewById(R.id.user_info_bt_setnickname);
				bt.setTag(item.GetGameID());
				bt.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SetNickName.Show( );
						//UserInfoViewRoom.onDestroy();
						//NdCommplatform.getInstance().ndEnterUserSetting(0, GameActivity.getInstance());
					}
				});
				
				
		// 修改密码
		  bt = (Button) popupview.findViewById(R.id.user_info_bt_setpwd);
		bt.setTag(item.GetGameID());
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PwdConfig.Show( );
				//UserInfoViewRoom.onDestroy();
				//NdCommplatform.getInstance().ndEnterUserSetting(0, GameActivity.getInstance());
			}
		});
		
		//修改头像
		  bt = (Button) popupview.findViewById(R.id.user_info_bt_setimg);
		bt.setTag(item.GetGameID());
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				SelectFile.Show( );

				//aUserInfoViewRoom.onDestroy();
				//NdCommplatform.getInstance().ndEnterUserSetting(0, GameActivity.getInstance());
			}
		});
		
		
		ImageView img = (ImageView) popupview.findViewById(R.id.user_info_head);
		 
		if ( item.GetFaceImage() != null) {
			img.setImageBitmap( item.GetFaceImage());//GameActivity.m_ImageUserHead);
		} else {
			if (item.GetGender() == GDF.GENDER_MANKIND)
					img.setBackgroundResource(R.drawable.head_00);
			else
					img.setBackgroundResource(R.drawable.head_01);
		}

		TextView txt0 = (TextView) popupview.findViewById(R.id.user_info_name);
		txt0.setText(item.GetNickName());

		TextView txt1 = (TextView) popupview.findViewById(R.id.user_info_id);
		txt1.setText("ID: " + item.GetGameID());

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