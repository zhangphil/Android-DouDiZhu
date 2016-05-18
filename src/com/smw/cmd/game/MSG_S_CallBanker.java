package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S_CallBanker  implements ICmd{
	public int cmd;   //  //命令 S2C_CallBanker  
	public	int				wCallBanker;						//叫庄用户
	public	int				bFirstTimes;						//bool 首次叫庄
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		 
		wCallBanker = NetEncoding.read4Byte(data, index);
		index += 4;
		
		bFirstTimes = NetEncoding.read4Byte(data, index);
		index += 4;
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		return index - pos;
	}
	
}
