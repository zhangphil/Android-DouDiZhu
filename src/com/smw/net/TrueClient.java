package com.smw.net;

import com.qp.lib.utility.Util;
 
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class TrueClient {

	public static final String	TAG					= "TrueClient";
	
	public boolean send(ICmd o){
		if(c!=null) return c.send(o);
		return false;
	}
	public void close( )
	{
		if(c!=null){
			  c.close();
			  c=null;
		}
	}
	public boolean isClose()
	{
		if(c==null) return true;
		 return false;
	}
	public MyClient c=null; 
	public int on_close_msg_id;
	public static byte[] XOR(byte data[])
	{
		byte d[]=new byte[data.length];
		for (int i=0;i<data.length;i++)
		{
			//data[i] ^= i;
			d[i]= (byte) (data[i] ^ i*4);
		}
		
		return d;
	}
	  public static String byte2hex(byte[] b) {
	  		String hs = "";
	  		String stmp = "";
	  		for (int i = 0; i < b.length; i++) {
	  			stmp = Integer.toHexString(b[i] & 0xFF);
	  			if (stmp.length() == 1) {
	  				hs += "0" + stmp;
	  			} else {
	  				hs += stmp;
	  			}
	  		}
	  		return hs.toUpperCase();
	  	}
	  
	  
	public   boolean start( String ip,int port) {
 
		
		if(c!=null){
			c.close();
			c=null;
		}
		
		c=new MyClient();
		c.SetOnReadListener(c);
		c.on_close_msg_id=on_close_msg_id;
		if(c.connect( ip,port )==false)
		{
			Util.e(TAG, "conn失败");  
			c=null;
			return false;
		}
		Util.e(TAG, "conn ok");  
		return true;
		//conn();
		
 

	}
 

}
