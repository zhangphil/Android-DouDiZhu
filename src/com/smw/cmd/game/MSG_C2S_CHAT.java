package com.smw.cmd.game;
 
import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C2S_CHAT implements ICmd {
	  
		public int cmd;  //√¸¡Ó  C2S_CHAT
		public int    		 TargetUserID;
		public int			 ChatColor;
		public String		 ChatString; //[64];	//
  
		@Override
		public int ReadFromByteArray(byte[] data, int pos) {
			int index = pos;
 
			return index - pos;
		}
		
		@Override
		public int WriteToByteArray(byte[] data, int pos) {
			int index = pos;

			index += NetEncoding.write4byte(data, cmd, index);
 			index += NetEncoding.write4byte(data, TargetUserID, index);
 			index += NetEncoding.write4byte(data, ChatColor, index);
			index += NetEncoding.writeString(data, ChatString , index, 64,"GBK");
 
			return index - pos;
		}
}
