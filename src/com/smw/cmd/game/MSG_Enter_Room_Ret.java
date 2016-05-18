package com.smw.cmd.game;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_Enter_Room_Ret  implements ICmd{
	public int cmd;   //ÃüÁî S2C_ENTER_ROOM_FAILED  S2C_ENTER_ROOM_OK
	public int errcode;//ÈôÊ§°Ü£¬·µ»ØÂë
	public int table_num; //Íæ¼ÒËùÔÚtableºÅ
	public int chairID;//
	
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		
		cmd = NetEncoding.read4Byte(data, index);
		index += 4;
		 
		errcode = NetEncoding.read4Byte(data, index);
		index += 4;
		
		table_num = NetEncoding.read4Byte(data, index);
		index += 4;
		chairID = NetEncoding.read4Byte(data, index);
		index += 4;
		 
		return index - pos;
	}
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
 
		return index - pos;
	}
	
}
