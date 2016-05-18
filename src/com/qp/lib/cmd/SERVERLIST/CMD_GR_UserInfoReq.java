package com.qp.lib.cmd.SERVERLIST;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_UserInfoReq implements ICmd {

	public long	lUserIDReq;
	public int	nTablePos;

	public CMD_GR_UserInfoReq() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		NetEncoding.write4byte(data, lUserIDReq, nIndex);
		nIndex += 4;
		NetEncoding.write2byte(data, nTablePos, nIndex);
		return (nIndex += 2) - pos;
	}
}
