package com.qp.lib.cmd.SERVERLIST;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_TableInfo implements ICmd {

	public int				nTableCount;
	public tagTableStatus	TableStatusArray[];

	public CMD_GR_TableInfo() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		nTableCount = NetEncoding.read2Byte(data, pos);
		index += 2;
		if (nTableCount != 0) {
			TableStatusArray = new tagTableStatus[nTableCount];
			for (int i = 0; i < nTableCount; i++) {
				TableStatusArray[i] = new tagTableStatus();
				index += TableStatusArray[i].ReadFromByteArray(data, index);
			}

		}
		return index - pos;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
