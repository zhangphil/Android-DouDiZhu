package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_SHOP_BUT_RET  implements ICmd{
	public int cmd;	//	C2S_SHOP_BUY
	public int ret;	//1 ¹ºÂòokµÈ´ýÖ§¸¶  2Ê§°Ü
	public int billid;//¶©µ¥
	public int goodsid;
	public int price;//×Ü¼Û
	public String desc ;//char desc[32];//Ê§°ÜÃèÊö
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		ret = NetEncoding.read4Byte(data, index);
		index += 4;
		billid = NetEncoding.read4Byte(data, index);
		index += 4;
		goodsid = NetEncoding.read4Byte(data, index);
		index += 4;
		price = NetEncoding.read4Byte(data, index);
		index += 4;
		desc = NetEncoding.byteToString(data, index,32);
		index += 32;
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		return index - pos;
	}
	
}
