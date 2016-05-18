// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CMD_GR_UserStandUp.java

package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_UserStandUp implements ICmd {

	public int	nTableID;
	public int	nChairID;
	public byte	cbForceLeave;

	public CMD_GR_UserStandUp() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		NetEncoding.write2byte(data, nTableID, nIndex);
		nIndex += 2;
		NetEncoding.write2byte(data, nChairID, nIndex);
		nIndex += 2;
		data[nIndex++] = cbForceLeave;
		return nIndex - pos;
	}
}
