package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_SYSTEM_CONFIG implements ICmd{
	public int	cmd;	//	S2C_SYSTEM_CONFIG   
	public 	String scene ;//char scene[32];//	场景TAG
 
	public String url;//char url[64];//使用sd卡时 背景图地址
	 
		@Override
		public int ReadFromByteArray(byte[] data, int pos) {
			int index = pos;
			
			cmd = NetEncoding.read4Byte(data, index);
			index += 4;
		  
			scene = NetEncoding.byteToString(data,index,32);
			index+=32;
			
		 
			url=NetEncoding.byteToString(data,index,64);
			index+=64;
			 
			return index - pos;
		}
		
		@Override
		public int WriteToByteArray(byte[] data, int pos) {
			int index = pos;

	 
			return index - pos;
		}
 

}
