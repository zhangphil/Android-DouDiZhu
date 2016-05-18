package com.qp.lib.cmd.SERVERLIST;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_UpdateNotify implements ICmd {

	public byte	cbMustUpdatePlaza;
	public byte	cbMustUpdateClient;
	public byte	cbAdviceUpdateClient;
	long		lCurrentPlazaVersion;
	long		lCurrentFrameVersion;
	long		lCurrentClientVersion;

	public CMD_GR_UpdateNotify() {
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int nIndex = pos;
		cbMustUpdatePlaza = data[nIndex++];
		cbMustUpdateClient = data[nIndex++];
		cbAdviceUpdateClient = data[nIndex++];
		lCurrentPlazaVersion = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		lCurrentFrameVersion = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		lCurrentClientVersion = NetEncoding.read4Byte(data, nIndex);
		return (nIndex += 4) - pos;
	}
}
