package com.qp.lib.cmd;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class CMD_CM_SystemMsg
	implements ICmd
{

	public int nType;
	public int nLength;
	public String szString;

	public CMD_CM_SystemMsg()
	{
		szString = "";
	}

	public int ReadFromByteArray(byte data[], int pos)
	{
		int index = pos;
		nType = NetEncoding.read2Byte(data, index);
		index += 2;
		nLength = NetEncoding.read2Byte(data, index);
		index += 2;
		szString = NetEncoding.wcharUnicodeBytesToString(data, index, 0);
		return index - pos;
	}

	public int WriteToByteArray(byte data[], int pos)
	{
		return 0;
	}
}
