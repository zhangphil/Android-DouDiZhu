package com.qp.lib.cmd.logon;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_MB_UpdateNotify implements ICmd {

	public byte	cbMustUpdate;
	public byte	cbAdviceUpdate;
	public long	lCurrentVersion;

	public CMD_MB_UpdateNotify() {
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int nIndex = pos;
		cbMustUpdate = data[nIndex++];
		cbAdviceUpdate = data[nIndex++];
		lCurrentVersion = NetEncoding.read4Byte(data, nIndex);
		return (nIndex += 4) - pos;
	}
}
