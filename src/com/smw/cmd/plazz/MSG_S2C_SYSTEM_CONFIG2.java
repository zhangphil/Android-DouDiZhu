package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_SYSTEM_CONFIG2  implements ICmd{
	public int	cmd;	//	S2C_SYSTEM_CONFIG   
	public 	String scene ;//char scene[32];//	场景TAG
	public int useSDImg;//	使用sd卡加载背景图? 或app默认 
	 
		@Override
		public int ReadFromByteArray(byte[] data, int pos) {
			int index = pos;
			
			cmd = NetEncoding.read4Byte(data, index);
			index += 4;
		  
			scene = NetEncoding.byteToString(data,index,32);
			index+=32;
			
			useSDImg = NetEncoding.read4Byte(data, index);
			index += 4;
			 
			return index - pos;
		}
		
		@Override
		public int WriteToByteArray(byte[] data, int pos) {
			int index = pos;

	 
			return index - pos;
		}
 

}
