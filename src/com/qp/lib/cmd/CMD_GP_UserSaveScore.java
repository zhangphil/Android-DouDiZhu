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

public class CMD_GP_UserSaveScore implements ICmd{

	public long		dwUserID;
	public long		lSaveScore;
	public String	szMachineID;
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		NetEncoding.write4byte(data, dwUserID, nIndex);
		nIndex += 4;
		NetEncoding.write8byte(data, lSaveScore, nIndex);
		nIndex += 8;
		if (szMachineID != null && !szMachineID.equals("")) {
			String MD5Str = "";
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
