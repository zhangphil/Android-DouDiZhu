package com.qp.lib.cmd.Bank;

import java.security.NoSuchAlgorithmException;

import com.qp.lib.define.DF;
import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;


public class CMD_GP_UserTakeScore implements ICmd {
	public long		dwUserID;
	public long		lTakeScore;
	public String	szPassWord;
	public String	szMachineID;
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		NetEncoding.write4byte(data, dwUserID, nIndex);
		nIndex += 4;
		NetEncoding.write8byte(data, lTakeScore, nIndex);
		nIndex += 8;
		String MD5Str = "";
		if (szPassWord != null && !szPassWord.equals("")) {
			try {
				MD5Str = NetEncoding.changeToMD5(szPassWord);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += DF.LEN_MD5 * 2;
		if (szMachineID != null && !szMachineID.equals("")) {
			try {
				MD5Str = NetEncoding.changeToMD5(szMachineID);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += DF.LEN_MACHINE_ID * 2;
		return nIndex - pos;
	}
	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}
}
