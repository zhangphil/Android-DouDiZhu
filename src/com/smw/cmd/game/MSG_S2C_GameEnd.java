package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_GameEnd implements ICmd {
	public int			cmd; // 	S2C_GAME_END
	public int		 	lGameTaxd[]=new int[4];				//游戏税收
	public int 			uid[]=new int[4];//4人唯一id
	public int			lGameScored[]=new int[4];			//游戏得分 
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		for(int i=0;i<4;i++){
			lGameTaxd[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<4;i++){
			uid[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<4;i++){
			lGameScored[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;

 
		return index - pos;
	}
 

}
