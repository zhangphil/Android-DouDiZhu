package com.qp.lib.cmd;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class CMD_CM_AcitonMessage implements ICmd {

	public int		nType;
	public int		nLength;
	public int		nButtonType;
	public String	szString;

	public CMD_CM_AcitonMessage() {
		szString = "";
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		nType = NetEncoding.read2Byte(data, index);
		index += 2;
		nLength = NetEncoding.read2Byte(data, index);
		index += 2;
		nButtonType = NetEncoding.read4Byte(data, index);
		index += 2;
		szString = NetEncoding.wcharUnicodeBytesToString(data, index, 0);
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
