package com.smw.cmd.game;
 
import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_CHAT implements ICmd {
	 
	public int	  		 cmd; //  S2C_CHAT
	public int			 SendUserID;
	public int    		 TargetUserID;
	public int			 ChatColor;
	public String		 ChatString;//  [64];	//
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		SendUserID = NetEncoding.read4Byte(data, index);
		index += 4;
		TargetUserID = NetEncoding.read4Byte(data, index);
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
