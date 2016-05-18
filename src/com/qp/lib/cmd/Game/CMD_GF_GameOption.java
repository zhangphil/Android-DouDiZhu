package com.qp.lib.cmd.Game;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GF_GameOption implements ICmd {

	public byte	cbAllowLookon;
	public long	lFrameVersion;
	public long	lClientVersion;

	public CMD_GF_GameOption() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		data[nIndex++] = cbAllowLookon;
		NetEncoding.write4byte(data, lFrameVersion, nIndex);
		nIndex += 4;
		NetEncoding.write4byte(data, lClientVersion, nIndex);
		return (nIndex += 4) - pos;
	}
}
