package com.qp.lib.cmd;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class CMD_GP_OperateSuccess implements ICmd {

	public long		lResultCode;
	public String	szDescribeString;

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		return 0;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		lResultCode = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		szDescribeString = NetEncoding.wcharUnicodeBytesToString(data, nIndex, 0);
		return nIndex - pos;

	}

}
