package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S_SendCard implements ICmd{
	public int cmd; //S2C_SendCard
	public int uid[]=new int[4];//4人唯一id
	public int	 cbCardData [][]=new int[4][5];	//用户扑克 
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		for(int i=0;i<4;i++){
			uid[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<4;i++){
			for(int k=0;k<5;k++){
				cbCardData[i][k] = NetEncoding.read4Byte(data, index);
				index += 4;
			}
			 
		}
		
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		return index - pos;
	}
	
}
