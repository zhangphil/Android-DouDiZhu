package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_RANK_LIST  implements ICmd{
	public int	    cmd; //	S2C_RANK_LIST
	public String  nickname[]=new String[10];  //nickname[10][32];
	public int score[]=new int[10];
	public int  imgchecksum[]=new int[10];
	public String maxim[]=new String[10];	;//char maxim[10][20]; //∏Ò—‘
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
 
		for(int i=0;i<10;i++){
			nickname[i] = NetEncoding.byteToString(data, index,32);
			index += 32;
		}
		for(int i=0;i<10;i++){
			score[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<10;i++){
			imgchecksum[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<10;i++){
			maxim[i] = NetEncoding.byteToString(data, index,32);
			index += 20;
		}
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		return index - pos;
	}
	
}
