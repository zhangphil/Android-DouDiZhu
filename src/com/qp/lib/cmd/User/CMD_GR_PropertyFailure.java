package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_PropertyFailure implements ICmd {

	public int		wRequestArea;
	public long		lErrorCode;
	public String	szDescribeString;

	public CMD_GR_PropertyFailure() {
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		wRequestArea = NetEncoding.read2Byte(data, index);
		index += 2;
		lErrorCode = NetEncoding.read4Byte(data, index);
		index += 4;
		szDescribeString = NetEncoding.wcharUnicodeBytesToString(data, index, 0);
		return (index += 512) - pos;
	}
}
