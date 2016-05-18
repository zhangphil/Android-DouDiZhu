package com.game.ddz;
import com.qp.lib.utility.Util;

import android.util.Log;

/*
*
* 购买完整源码联系 q344717871
* 
* */

public class DDZServer {

 
 
	private  static final String TAG="DDZServer";
	
	static
	{
	 
		try {  
			System.loadLibrary("ddzserver");  
		} catch (Exception e) {
			e.printStackTrace();
			//System.err.println("WARNING: Could not load library!");  
			Util.e( TAG ,"WARNING: Could not load library!");
		}  

	}
	
  
	public native void Start();
	
	public native void Update();
	 
	public native void Close();
	
}
