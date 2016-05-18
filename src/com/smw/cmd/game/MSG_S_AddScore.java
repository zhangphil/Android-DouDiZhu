package com.smw.cmd.game;

import com.qp.lib.utility.NetEncoding;
import com.smw.net.ICmd;

public class MSG_S_AddScore implements ICmd{
	public int cmd; //  S2C_AddScore
	public int		 AddScoreUser;						//加注用户
	public int			 AddScoreCount;						//加注数目
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		AddScoreUser = NetEncoding.read4Byte(data, index);
		index += 4;
		AddScoreCount = NetEncoding.read4Byte(data, index);
		index += 4;
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;

 	
		return index - pos;
	}
	
}
