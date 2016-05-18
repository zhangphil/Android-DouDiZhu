package com.qp.lib.cmd.Bank;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GP_QueryInsureInfo implements ICmd {

	public long	lUserID;

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		NetEncoding.write4byte(data, lUserID, nIndex);
		nIndex += 4;
		return nIndex - pos;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int POS) {
		// TODO Auto-generated method stub
		return 0;
	}

}
