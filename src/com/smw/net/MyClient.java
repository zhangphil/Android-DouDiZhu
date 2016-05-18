package com.smw.net;

import android.os.Message;

import com.qp.ddz.GameActivity;
import com.qp.lib.utility.Util;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class MyClient extends MyTcpClient
							implements ISocketEventListener
{
	 
	
	public MyClient()
	{
		 
	}
	
	public static String TAG="net";
	public int on_close_msg_id;
	
	public   void  onClose( )
	{
		  //消息发给GameActivity处理
	 	Message msg=new Message();
		msg.what=on_close_msg_id;  
		GameActivity.getGameActivityInstance().m_handler.sendMessage(msg);
	}
	
	public  boolean onRead(  byte data[],int len){
		
		int msgid=NetEncoding.readFrom4Byte(data);
		Util.v(TAG,"onEventSocketRead  msgid["+ msgid+"]");
	 
		Object obj= GameActivity.getGameActivityInstance().m_PacketHandler.ParsePacket(data, len);
		 if(obj!=null){
			 
			 //消息发给GameActivity处理
			 	Message msg=new Message();
				msg.what=1; //消息code
				msg.obj=obj; //消息内容
				msg.arg1=msgid;
				GameActivity.getGameActivityInstance().m_handler.sendMessage(msg);
		 }
			/* 
			CMD_TEST obj=new CMD_TEST();
			int readlen=obj.ReadFromByteArray(data, 0);
			if(readlen==len)
			{
				//解包大小无误
				System.out.println("  a["+ obj.a + "] time["+ obj.time + "] name["+ obj.name +"]"
						+ "name2[" + NetEncoding.ByteArrayToString(obj.name2) +"]");
			
				 
//				byte dd[]=new byte[1024];
//				
//				//obj.a=obj.a+1;
//				obj.a=obj.a+2;
//				obj.time=555;
//				obj.name="没料到abc";
//				
//				int l=obj.WriteToByteArray(dd, 0);
//				System.out.println("send len"+l);
//				send(3,dd,l); 
				
				obj.a=1314;
				obj.time=521;
				obj.name="没到abc12";
				obj.name2[0]='S';
				
				//obj.name2="alibaba3";
				
				send(obj);
				 
			}else{
				//解包大小不正确!
				
			} 
			 
		}*/

		return true;
	}
 
}
