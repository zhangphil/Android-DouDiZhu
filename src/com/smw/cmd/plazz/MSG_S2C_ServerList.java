package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_ServerList  implements ICmd{
	public int	cmd;	//	S2C_ServerList
	public int gametype;
	public int gameroomid;
	public String servername;// char servername[32];
	public int ScoreMIN;
	public int ScoreMAX;
	public String ip;//char ip[20];
	public int port;
  
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		gametype = NetEncoding.read4Byte(data, index);
		index += 4;
		gameroomid = NetEncoding.read4Byte(data, index);
		index += 4;
		servername=NetEncoding.byteToString(data,index,32);
		index+=32;
		ScoreMIN = NetEncoding.read4Byte(data, index);
		index += 4;
		ScoreMAX = NetEncoding.read4Byte(data, index);
		index += 4;
		ip=NetEncoding.byteToString(data,index,20);
		index+=20;
		port = NetEncoding.read4Byte(data, index);
		index += 4;
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;

		return index - pos;
	}
	
	
}
