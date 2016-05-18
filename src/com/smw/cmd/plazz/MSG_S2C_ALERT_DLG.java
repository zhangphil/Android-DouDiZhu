package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_ALERT_DLG  implements ICmd{
	public int cmd; //√¸¡Ó S2C_ALERT_DLG 
	public int type;
	public String  msg;  // char msg[64];
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		type = NetEncoding.read4Byte(data, index);
		index += 4;
		msg=NetEncoding.byteToString(data,index,64);
		index+=64;
	 
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		return index - pos;
	}
	
}
