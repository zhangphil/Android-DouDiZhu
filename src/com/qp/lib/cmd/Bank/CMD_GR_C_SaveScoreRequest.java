package com.qp.lib.cmd.Bank;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_C_SaveScoreRequest implements ICmd {

	public byte	cbActivityGame;
	public long	lSaveScore;

	public CMD_GR_C_SaveScoreRequest() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		data[nIndex++] = cbActivityGame;
		NetEncoding.write8byte(data, lSaveScore, nIndex);
		return (nIndex += 8) - pos;
	}
}
