package com.qp.lib.cmd.Bank;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GP_UserInsureInfo implements ICmd{

	public int	wRevenueTake;
	public int	wRevenueTransfer;
	public int	wServerID;
	public long	lUserScore;
	public long	lUserInsure;
	public long	lTransferPrerequisite;
	
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;

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
		return nIndex-pos;
	}

}
