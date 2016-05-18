package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_UPLOAD_IMAGE_RET   implements ICmd{
	public int cmd; //√¸¡Ó  S2C_UPLOAD_IMAGE_RET 
	public int  ret;	//0 ß∞‹ 1ok;
	public int img_checksum;

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		ret = NetEncoding.read4Byte(data, index);
		index += 4;
		img_checksum = NetEncoding.read4Byte(data, index);
		index += 4;
		return index - pos;
	}
		
		
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
	 
		return index - pos;
	}
		
}
