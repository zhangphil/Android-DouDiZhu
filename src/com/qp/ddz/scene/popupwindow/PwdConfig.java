package com.qp.ddz.scene.popupwindow;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;
import com.qp.ddz.scene.server.ServerActivity;
import com.qp.lib.cmd.CMD;
import com.smw.cmd.plazz.MSG_C2S_PWD_CHANGE;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class PwdConfig {

	private static final String	TAG	= "PwdConfig";
 
	 public static String pwd;
    static   PopupWindow popupWindow;  
 	
 	public static  void Show ( ) {
 
 		if(ServerActivity.instance==null)
 			return;
 		if (popupWindow != null) {
 			onDestroy();
 		}
 		View popupview = null;
 		if (popupWindow == null) { 
 			 popupview = GameActivity.getInstance().getLayoutInflater().inflate(R.layout.pwdconfig, null, false);

		           popupWindow = new PopupWindow( popupview, LayoutParams.MATCH_PARENT , LayoutParams.MATCH_PARENT ,true);  
		         //  popupWindow.setContentView(contentView);  
 
		           popupWindow.setBackgroundDrawable(new BitmapDrawable());
		           popupWindow.setOutsideTouchable(false);
		           popupview.setBackgroundColor(Color.BLACK);  
		           
		           //取消
		           Button btn = (Button)popupview.findViewById(R.id.pwdconfig_btncancel);  
		           btn.setOnClickListener(new OnClickListener() {
		               public void onClick(View v) {
		            	   onDestroy();
		               }
		           });
		 		       
		           //
		           Button btnok = (Button)popupview.findViewById(R.id.pwdconfig_btnok);  
		           btnok.setOnClickListener(new OnClickListener() {
		               public void onClick(View v) {
		            	  
			           		EditText  pw1 = (EditText)   popupWindow.getContentView().findViewById(R.id.pwdconfig_pwd);
			        		EditText pw2 = (EditText)   popupWindow.getContentView().findViewById(R.id.pwdconfig_pwd2);
			        		////两次密码不对!
			        		if(!  pw1.getText().toString().equals( pw2.getText().toString() )  )
			        		{
			        			Toast.makeText(GameActivity.getInstance(), "两次密码不一致!", Toast.LENGTH_SHORT).show();
			        			 return;
			        		} 
			               //长度最大10字符
			               if(pw1.getText().toString().length()>10){
			            	   Toast.makeText(GameActivity.getInstance(), "密码长度过长!", Toast.LENGTH_SHORT).show();
			            	    return;
			               }
			               
			               	//请求修改
			               	MSG_C2S_PWD_CHANGE o=new MSG_C2S_PWD_CHANGE();
				    		o.cmd=CMD.C2S_PWD_CHANGE;
				    		o.newpwd=pw1.getText().toString();
				    		o.user=GameActivity.m_user;
				    		o.pwd= GameActivity.m_pwd;
				    		
				    		GameActivity.getGameActivityInstance().m_loginclient.send(o);
				    		
				    		pwd=o.newpwd;
				    		
				    		onDestroy();
			               
			          }
		           });
		            

	 	} else {
			popupview = popupWindow.getContentView();
		}
 		  //获得popupwindow外的焦点  
 	        ColorDrawable cd = new ColorDrawable(-0000);    
 		      popupWindow.setBackgroundDrawable(cd);   
 		      popupWindow.setFocusable(true);  
 
           popupWindow.showAtLocation(GameActivity.getInstance().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
       	
 	}
 		
 	public static  void onDestroy() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}  

}
