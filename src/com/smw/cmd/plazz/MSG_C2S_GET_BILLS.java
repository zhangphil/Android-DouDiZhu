package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C2S_GET_BILLS implements ICmd {
	public int cmd;  //√¸¡Ó     C2S_GET_BILLS 
 	 
	 
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;

		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;

		index += NetEncoding.write4byte(data, cmd, index);  
		 
		return index - pos;
	}
	
}

