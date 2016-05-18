package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_SystemMessage  implements ICmd{
	public int 			 cmd;	//	S2C_SystemMessage
	public int    		 type;	//0 系统全体广播 -1玩家喇叭  
	public int			 ChatColor;
	public String 		 ChatString;	//char		 ChatString[64];	//
	
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		type = NetEncoding.read4Byte(data, index);
		index += 4;
		ChatColor = NetEncoding.read4Byte(data, index);
		index += 4;
		ChatString = NetEncoding.byteToString(data, index,64);
		index += 64;
		
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		return index - pos;
	}
	
}
