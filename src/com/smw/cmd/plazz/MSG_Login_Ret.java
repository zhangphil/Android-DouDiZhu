package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_Login_Ret  implements ICmd{
	public int cmd;
 
 
	public int errcode;
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		errcode = NetEncoding.read4Byte(data, index);
		index += 4;
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
