package com.qp.lib.cmd.logon;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_MB_LogonFailure implements ICmd {

	public long		lErrorCode;
	public String	szDescribeString;

	public CMD_MB_LogonFailure() {
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int nIndex = pos;
		lErrorCode = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		szDescribeString = NetEncoding.wcharUnicodeBytesToString(data, nIndex, 0);
		return nIndex - pos;
	}
}
