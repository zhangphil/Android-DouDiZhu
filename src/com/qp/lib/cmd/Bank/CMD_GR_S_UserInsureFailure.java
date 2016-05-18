package com.qp.lib.cmd.Bank;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_S_UserInsureFailure implements ICmd {

	public byte		cbActivityGame;
	public long		lErrorCode;
	public String	szDescribeString;

	public CMD_GR_S_UserInsureFailure() {
		szDescribeString = "";
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int nIndex = pos;
		cbActivityGame = data[nIndex++];
		lErrorCode = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		szDescribeString = NetEncoding.wcharUnicodeBytesToString(data, nIndex, 0);
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
