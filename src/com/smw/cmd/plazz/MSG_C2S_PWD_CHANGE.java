package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C2S_PWD_CHANGE implements ICmd {
	  
		public int cmd;  //√¸¡Ó   C2S_PWD_CHANGE 

		public String		 user; // 
		public String		 pwd;
		public String		 newpwd;
		
		@Override
		public int ReadFromByteArray(byte[] data, int pos) {
			int index = pos;

			return index - pos;
		}
		
		@Override
		public int WriteToByteArray(byte[] data, int pos) {
			int index = pos;

			index += NetEncoding.write4byte(data, cmd, index);  
			index += NetEncoding.writeString(data, user , index, 20,"GBK");
			index += NetEncoding.writeString(data, pwd , index, 20,"GBK");
			index += NetEncoding.writeString(data, newpwd , index, 20,"GBK");

			return index - pos;
		}
}

