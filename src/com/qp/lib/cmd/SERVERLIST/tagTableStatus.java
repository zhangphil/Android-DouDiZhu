package com.qp.lib.cmd.SERVERLIST;

import com.qp.lib.interface_ex.net.ICmd;

public class tagTableStatus implements ICmd {

	public byte	cbTableLock;
	public byte	cbPlayStatus;

	public tagTableStatus() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
