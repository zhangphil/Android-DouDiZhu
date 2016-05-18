package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_SHOP_GOODS  implements ICmd{
	public int cmd;	//	S2C_SHOP_GOODS
	public int id;
	public String  goodsname;// char goodsname[32];
	public int goodsprice;
	public String  desc;//char desc[32];
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
	  
		id = NetEncoding.read4Byte(data, index);
		index += 4;
		
		goodsname=NetEncoding.byteToString(data,index,32);
		index+=32;
		
		goodsprice = NetEncoding.read4Byte(data, index);
		index += 4;
		
		desc=NetEncoding.byteToString(data,index,32);
		index+=32;
		
		 
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;

 
		return index - pos;
	}
	
}
