package com.smw.cmd.game;
 
import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C_AddScore implements ICmd{
	public int cmd; //C2S_AddScore
	public int							lScore;								//¼Ó×¢ÊýÄ¿
	
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
	 
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		index += NetEncoding.write4byte(data, cmd, index);
 		index += NetEncoding.write4byte(data, lScore, index);
		
		return index - pos;
	}
	
}
