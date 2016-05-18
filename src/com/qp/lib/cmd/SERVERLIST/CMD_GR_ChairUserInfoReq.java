package com.qp.lib.cmd.SERVERLIST;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_ChairUserInfoReq implements ICmd {

	public int	nTablePos;
	public int	nChairPos;

	public CMD_GR_ChairUserInfoReq() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		NetEncoding.write2byte(data, nTablePos, nIndex);
		nIndex += 2;
		NetEncoding.write2byte(data, nChairPos, nIndex);
		return (nIndex += 2) - pos;
	}
}
