package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C2S_SET_NICKNAME implements ICmd {
	public int cmd;  //√¸¡Ó   C2S_SET_NICKNAME 
 	public String nickname; //   char nickname[32];
	 
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;

		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;

		index += NetEncoding.write4byte(data, cmd, index);  
		index += NetEncoding.writeString(data, nickname , index, 32,"GBK"); 
		return index - pos;
	}
}

