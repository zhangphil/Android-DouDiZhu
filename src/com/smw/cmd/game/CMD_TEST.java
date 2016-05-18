package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class CMD_TEST  implements ICmd{
 
	public int cmd;

	public int a;
	public long time;
	public String name;//定长10
	public final int name_max_len=10;
	public byte[] name2=new byte[name_max_len];//定长10
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		
		a = NetEncoding.read4Byte(data, index);
		index += 4;
		
		time = NetEncoding.read4Byte(data, index);
		index += 4;
		
		name=NetEncoding.byteToString(data,index,name_max_len);
		index+=name_max_len;
		
		name2=NetEncoding.readByteArray(data,index,name_max_len);
		index+=name_max_len;
		
		//name =builder.toString();//= NetEncoding.wcharUnicodeBytesToString(data, index, 0);用这个也可以接收WCHAR的,但长度不固定。
		
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;

		index += NetEncoding.write4byte(data, cmd, index);
		index += NetEncoding.write4byte(data, a, index);
		index += NetEncoding.write4byte(data, time, index);
		index += NetEncoding.writeString(data, name, index, name_max_len,"GBK");
		index += NetEncoding.writeByteArray(data, name2, index, name_max_len);
		
		return index - pos;
	}
	
}
