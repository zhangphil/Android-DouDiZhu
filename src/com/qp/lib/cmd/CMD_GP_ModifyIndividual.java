package com.qp.lib.cmd;

import java.security.NoSuchAlgorithmException;

import com.qp.lib.define.DF;
import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class CMD_GP_ModifyIndividual implements ICmd {

	public byte		cbGender;
	public long		lUserID;
	public String	szPassWord;
	@Override
	public int WriteToByteArray(byte[] data, int pos) {

		int nIndex = pos;
		data[nIndex++] = cbGender;
		NetEncoding.write4byte(data, lUserID, nIndex);
		nIndex += 4;

		String MD5Str = "";
		try {
			MD5Str = NetEncoding.changeToMD5(szPassWord);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		nIndex += DF.LEN_PASSWORD * 2;

		return nIndex - pos;
	}
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
