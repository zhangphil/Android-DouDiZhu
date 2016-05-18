package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_TABLEINFO implements ICmd{
	public int cmd; //命令 S2C_TABLE_INFO
	public int TableID;//桌子id
	public int GameStatus;//桌子状态 没开始 等叫庄 下注
	public int UserStatus[]=new int[3];//4人状态  GS_TK_FREE可准备。GS_TK_CALL可叫庄 GS_TK_SCORE可下注 GS_TK_PLAYING可游戏。
	public int Bankeruid; 	//庄家用户
	public int uid[]=new int[3];//4人唯一id
	public int img_checksum[]=new int[3];//头像
	public String  nickname[]=new String[3]; //4人 昵称 char nickname[32][4]; 
	public int money[]=new int[3];//4人的金
	public int rank[]=new int[3];//4人排名
	public int wincount[]=new int[3];//赢
	public int losecount[]=new int[3];//
	public int escapecount[]=new int[3];//逃跑
	public String maxim[]=new String[3];;//char 20
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		TableID = NetEncoding.read4Byte(data, index);
		index += 4;
		GameStatus = NetEncoding.read4Byte(data, index);
		index += 4;
		for(int i=0;i<3;i++){
			UserStatus[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		Bankeruid = NetEncoding.read4Byte(data, index);
		index += 4;
		for(int i=0;i<3;i++){
			uid[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<3;i++){
			img_checksum[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		} 
		for(int i=0;i<3;i++){
			nickname[i] = NetEncoding.byteToString(data, index,32);
			index += 32;
		}
		for(int i=0;i<3;i++){
			money[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<3;i++){
			rank[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<3;i++){
			wincount[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<3;i++){
			losecount[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<3;i++){
			escapecount[i] = NetEncoding.read4Byte(data, index);
			index += 4;
		}
		for(int i=0;i<3;i++){
			maxim[i] = NetEncoding.byteToString(data, index,20);
			index += 20;
		}
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
