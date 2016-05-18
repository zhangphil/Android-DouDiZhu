package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_USERINFO implements ICmd{
	public int cmd; //命令 S2C_USERINFO
 
	public String  nickname;//char nickname[32]; //昵称
	public	int money;//金币
	public int rank;//排名
	public 	int uid;//唯一id
	public int  img_checksum;//头像
	public int bankscore;//银行存款
	public int wincount;//赢
	public int losecount;//
	public int escapecount;//逃跑
	public String maxim;//char 20
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
	 
		nickname=NetEncoding.byteToString(data,index,32);
		index+=32;
		money = NetEncoding.read4Byte(data, index);
		index += 4;
		rank = NetEncoding.read4Byte(data, index);
		index += 4;
		uid = NetEncoding.read4Byte(data, index);
		index += 4;
		img_checksum = NetEncoding.read4Byte(data, index);
		index += 4; 
		bankscore = NetEncoding.read4Byte(data, index);
		index += 4;
		wincount = NetEncoding.read4Byte(data, index);
		index += 4;	
		losecount = NetEncoding.read4Byte(data, index);
		index += 4;
		escapecount = NetEncoding.read4Byte(data, index);
		index += 4;
		
		maxim=NetEncoding.byteToString(data,index,20);
		index+=20;
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;

//		index += NetEncoding.write4byte(data, cmd, index);
//		index += NetEncoding.write4byte(data, a, index);
//		index += NetEncoding.write4byte(data, time, index);
//		index += NetEncoding.writeString(data, name, index, name_max_len,"GBK");
//		index += NetEncoding.writeByteArray(data, name2, index, name_max_len);
//		
		return index - pos;
	}
	
}
