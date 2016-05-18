package com.qp.lib.cmd.Bank;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_S_UserInsureInfo implements ICmd {

	public byte	cbActivityGame;
	public int	wRevenueTake;
	public int	wRevenueTransfer;
	public int	wServerID;
	public long	lUserScore;
	public long	lUserInsure;
	public long	lTransferPrerequisite;

	public CMD_GR_S_UserInsureInfo() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int nIndex = pos;
		cbActivityGame = data[nIndex++];
		wRevenueTake = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		wRevenueTransfer = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		wServerID = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		lUserScore = NetEncoding.read8Byte(data, nIndex);
		nIndex += 8;
		lUserInsure = NetEncoding.read8Byte(data, nIndex);
		nIndex += 8;
		lTransferPrerequisite = NetEncoding.read8Byte(data, nIndex);
		nIndex += 8;
		return nIndex - pos;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
