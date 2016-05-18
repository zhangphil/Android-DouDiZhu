 
package com.qp.ddz.scene.popupwindow;

/**
*
* 购买完整源码联系 q344717871
* 
*/

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
import com.smw.cmd.plazz.MSG_C2S_SET_MAXIM;

public class SetMaxim {

	private static final String	TAG	= "SetMaxim";
 
	public static String  maxim;
    static   PopupWindow popupWindow;  
 	
 	public static  void Show ( ) {
 
 		if(ServerActivity.instance==null)
 			return;
 		if (popupWindow != null) {
 			onDestroy();
 		}
 		View popupview = null;
 		if (popupWindow == null) { 
 			 popupview = GameActivity.getInstance().getLayoutInflater().inflate(R.layout.setmaxim, null, false);

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
			        	 
			               //长度最大10字符
			               if(pw1.getText().toString().length()>=20){
			            	   Toast.makeText(GameActivity.getInstance(), "签名过长!", Toast.LENGTH_SHORT).show();
			            	    return;
			               }
			              
			               
			               	//请求修改
			               MSG_C2S_SET_MAXIM o=new MSG_C2S_SET_MAXIM();
				    		o.cmd=CMD.C2S_SET_MAXIM;
				    		o.maxim=pw1.getText().toString(); 
				    		GameActivity.getGameActivityInstance().m_loginclient.send(o);
				    		 
				    		maxim=o.maxim;
				    		 
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
