package com.qp.lib.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetHelp {
	
	static public boolean NetIsWifi(Context c)
	{
		ConnectivityManager connectMgr = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		 NetworkInfo info = connectMgr.getActiveNetworkInfo();
		 if(info!=null && info.getType() == ConnectivityManager.TYPE_WIFI ){
			 return true;
		 }
		 return false;//没网或不是wifi
	}
 

}
