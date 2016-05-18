package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C2S_SAY_READY implements ICmd{
	 
	 public int cmd; //√¸¡Ó C2S_SAY_READY
	 public int flag;
	 

		@Override
		public int ReadFromByteArray(byte[] data, int pos) {
			int index = pos;
 
			return index - pos;
		}
		
		@Override
		public int WriteToByteArray(byte[] data, int pos) {
			int index = pos;

			index += NetEncoding.write4byte(data, cmd, index);
			index += NetEncoding.write4byte(data, flag, index);
			
			return index - pos;
		}
		
		
}
