package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C_CallBanker implements ICmd{
	 
		 
		public int cmd; //ÃüÁî C2S_CALL_BANKER
		public int	 bBanker;							//×ö×¯±êÖ¾
		
		@Override
		public int ReadFromByteArray(byte[] data, int pos) {
			int index = pos;
			
//			cmd = NetEncoding.read4Byte(data, index);
//			index += 4;
			return index - pos;
		}
		
		@Override
		public int WriteToByteArray(byte[] data, int pos) {
			int index = pos;

			index += NetEncoding.write4byte(data, cmd, index);
 			index += NetEncoding.write4byte(data, bBanker, index);
   
			return index - pos;
		}
}

