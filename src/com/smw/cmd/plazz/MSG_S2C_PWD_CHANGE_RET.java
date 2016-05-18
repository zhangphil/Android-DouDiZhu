package com.smw.cmd.plazz;

import com.qp.lib.utility.NetEncoding;
import com.smw.net.ICmd;

public class MSG_S2C_PWD_CHANGE_RET  implements ICmd{
	public int cmd; //   S2C_PWD_CHANGE_RET 
	public int		 ret;	 
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		ret = NetEncoding.read4Byte(data, index);
		index += 4;
	 
		return index - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		return index - pos;
	}
	
}
