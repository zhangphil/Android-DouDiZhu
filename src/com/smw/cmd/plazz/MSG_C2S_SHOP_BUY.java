package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C2S_SHOP_BUY  implements ICmd {
	public int cmd;	//	C2S_SHOP_BUY
	public int goodsid;//要购买的物品id
	 
		 
			@Override
			public int ReadFromByteArray(byte[] data, int pos) {
				int index = pos;

				return index - pos;
			}
			
			@Override
			public int WriteToByteArray(byte[] data, int pos) {
				int index = pos;

				index += NetEncoding.write4byte(data, cmd, index);
				index += NetEncoding.write4byte(data, goodsid, index);
				 
				return index - pos;
			}
 }

 