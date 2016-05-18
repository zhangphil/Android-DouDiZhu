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

public class CMD_GP_UserTransferScore implements ICmd {
	public long		dwUserID;
	public byte		cbByNickName;
	public long		lTransferScore;
	public String	szPassWord;
	public String	szNickName;
	public String	szMachineID;
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;

		NetEncoding.write4byte(data, dwUserID, nIndex);
		nIndex += 4;
		data[nIndex++] = cbByNickName;
		NetEncoding.write8byte(data, lTransferScore, nIndex);
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

		if (szNickName != null && !szNickName.equals(""))
			NetEncoding.stringToWcharUnicodeBytes(szNickName, data, nIndex);
		nIndex += DF.LEN_ACCOUNTS * 2;

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
