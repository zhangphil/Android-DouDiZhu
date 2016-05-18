package com.qp.lib.cmd.Game;

import com.qp.lib.interface_ex.net.ICmd;

public class CMD_GF_GameStatus implements ICmd {

	public int	nGameStatus;
	public int	nAllowLookon;

	public CMD_GF_GameStatus() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		nGameStatus = data[index++];
		nAllowLookon = data[index++];
		return index - pos;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
