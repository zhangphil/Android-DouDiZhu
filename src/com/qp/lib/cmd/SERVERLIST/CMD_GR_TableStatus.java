package com.qp.lib.cmd.SERVERLIST;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_TableStatus
	implements ICmd
{

	public int nTableID;
	public tagTableStatus TableStatus;

	public CMD_GR_TableStatus()
	{
		TableStatus = new tagTableStatus();
	}

	public int ReadFromByteArray(byte data[], int pos)
	{
		int index = pos;
		nTableID = NetEncoding.read2Byte(data, pos);
		index = (index += 2) + TableStatus.ReadFromByteArray(data, index);
		return index - pos;
	}

	public int WriteToByteArray(byte data[], int pos)
	{
		return 0;
	}
}
