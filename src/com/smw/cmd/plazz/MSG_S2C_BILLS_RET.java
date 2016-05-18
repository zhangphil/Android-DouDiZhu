package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_S2C_BILLS_RET implements ICmd{
	public int cmd;	//	S2C_BILLS_RET
	public int billID; //订单编号
	public String goodsname;//char goodsname[32];//商品名称
	public String buytime; //char buytime[20];//下单时间
	public int pstate;//订单状态 1未支付 2交易成功 3交易取消 
 
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
	  
		billID = NetEncoding.read4Byte(data, index);
		index += 4;
		
		goodsname=NetEncoding.byteToString(data,index,32);
		index+=32;
		
		buytime = NetEncoding.byteToString(data, index,32);
		index +=32;
		
		pstate=NetEncoding.read4Byte(data,index);
		index+=4;
		
		 
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;


		return index - pos;
	}
	
}
