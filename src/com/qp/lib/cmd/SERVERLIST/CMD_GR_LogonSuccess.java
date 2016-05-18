package com.qp.lib.cmd.SERVERLIST;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_LogonSuccess
	implements ICmd
{

	public long lUserRight;
	public long lMasterRight;

	public CMD_GR_LogonSuccess()
	{
	}

	public int ReadFromByteArray(byte data[], int pos)
	{
		int index = pos;
		lUserRight = NetEncoding.read4Byte(data, index);
		index += 4;
		lMasterRight = NetEncoding.read4Byte(data, index);
		return (index += 4) - pos;
	}

	public int WriteToByteArray(byte data[], int pos)
	{
		return 0;
	}
}
