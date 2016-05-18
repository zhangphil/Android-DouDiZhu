 
package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class CMD_C_PassCard implements ICmd{
	public int cmd; //C2S_PassCard
  
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
