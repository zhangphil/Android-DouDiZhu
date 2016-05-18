package com.qp.lib.cmd.Bank;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GP_UserInsureSuccess implements ICmd {

	/** 用户ID **/
	public long		lUserID;
	/** 用户金币 **/
	public long		lUserScore;
	/** 银行金币 **/
	public long		lUserInsure;
	/** 描述信息 **/
	public String	szDescibeString;
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		lUserID = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		lUserID = NetEncoding.read8Byte(data, nIndex);
		nIndex += 8;
		lUserID = NetEncoding.read8Byte(data, nIndex);
		nIndex += 8;
		szDescibeString = NetEncoding.wcharUnicodeBytesToString(data, nIndex, 0);
		nIndex += 128 * 2;
		return nIndex - pos;
	}

}
