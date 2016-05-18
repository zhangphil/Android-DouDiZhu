package com.qp.lib.cmd.Bank;

import com.qp.lib.interface_ex.net.ICmd;

public class CMD_GR_C_QuerInsureInfoRequest implements ICmd {

	public byte	cbActivityGame;

	public CMD_GR_C_QuerInsureInfoRequest() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		data[nIndex++] = cbActivityGame;
		return nIndex - pos;
	}
}
