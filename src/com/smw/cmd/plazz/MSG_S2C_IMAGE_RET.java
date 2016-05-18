package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_IMAGE_RET  implements ICmd{
	public int cmd; //√¸¡Ó  S2C_IMAGE_RET  
	public int img_checksum;
	public int img_size;
	public byte[] img=new byte[1024*7];//∂®≥§10k
	

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		img_checksum = NetEncoding.read4Byte(data, index);
		index += 4;
		img_size = NetEncoding.read4Byte(data, index);
		index += 4;
		img=NetEncoding.readByteArray(data,index,1024*7);
		index+=1024*7;
		
		return index - pos;
	}
	
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		return index - pos;
	}
	
}
