package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C2S_SHOP_PAY  implements ICmd {
	public int cmd;	//	C2S_SHOP_PAY
	  
	public int  orderId;
	public int resultCode;
	public  String resultString;//char resultString[32];
	public int payType;
	public int amount;
		 
			@Override
			public int ReadFromByteArray(byte[] data, int pos) {
				int index = pos;

				return index - pos;
			}
			
			@Override
			public int WriteToByteArray(byte[] data, int pos) {
				int index = pos;

				index += NetEncoding.write4byte(data, cmd, index);
				index += NetEncoding.write4byte(data, orderId, index);
				index += NetEncoding.write4byte(data, resultCode, index);
				
				index += NetEncoding.writeString(data, resultString , index, 32,"GBK");
				index += NetEncoding.write4byte(data, payType, index);
				index += NetEncoding.write4byte(data, amount, index);
				return index - pos;
			}
 }

 