package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_UserLookon implements ICmd {

	public int	nTableID;
	public int	nChairID;

	public CMD_GR_UserLookon() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		NetEncoding.write2byte(data, nTableID, nIndex);
		nIndex += 2;
		NetEncoding.write2byte(data, nChairID, nIndex);
		return (nIndex += 2) - pos;
	}
}
