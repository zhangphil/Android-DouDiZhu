package com.qp.lib.cmd.Bank;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_S_UserInsureSuccess implements ICmd {

	public byte		cbActivityGame;
	public long		lUserScore;
	public long		lUserInsure;
	public String	szDescribeString;

	public CMD_GR_S_UserInsureSuccess() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int nIndex = pos;
		cbActivityGame = data[nIndex++];
		lUserScore = NetEncoding.read8Byte(data, nIndex);
		nIndex += 8;
		lUserInsure = NetEncoding.read8Byte(data, nIndex);
		nIndex += 8;
		szDescribeString = NetEncoding.wcharUnicodeBytesToString(data, nIndex, 0);
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
