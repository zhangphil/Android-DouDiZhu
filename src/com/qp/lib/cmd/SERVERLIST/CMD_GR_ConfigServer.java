package com.qp.lib.cmd.SERVERLIST;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_ConfigServer implements ICmd {

	public int	nTableCount;
	public int	nChairCount;
	public int	nServerType;
	public long	lServerRule;

	public CMD_GR_ConfigServer() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		nTableCount = NetEncoding.read2Byte(data, index);
		index += 2;
		nChairCount = NetEncoding.read2Byte(data, index);
		index += 2;
		nServerType = NetEncoding.read2Byte(data, index);
		index += 2;
		lServerRule = NetEncoding.read4Byte(data, index);
		return (index += 4) - pos;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
