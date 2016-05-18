package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_RequestFailuer implements ICmd {

	public long		lFailuerCode;
	public String	szDescribeString;

	public CMD_GR_RequestFailuer() {
		szDescribeString = "";
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		lFailuerCode = NetEncoding.read8Byte(data, index);
		index += 8;
		szDescribeString = NetEncoding.wcharUnicodeBytesToString(data, index, 0);
		index += szDescribeString.length() * 2;
		return index - pos;
	}

	public int WriteToByteArray(byte arg0[], int arg1) {
		return 0;
	}
}
