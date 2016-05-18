package com.qp.lib.cmd.Bank;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GP_UserInsureFailure implements ICmd {

	public long		lResultCode;
	public String	szDescribeString;
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		lResultCode = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		szDescribeString = NetEncoding.wcharUnicodeBytesToString(data, nIndex, 0);
		nIndex += 128 * 2;
		return nIndex - pos;
	}

}
