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

public class CMD_GP_CustomFaceInfo implements ICmd {

	public long		lUserID;
	public String	szPassWord;
	public String	szMachineID;
	public long		dwCustomFace[]	= new long[DF.FACE_CX * DF.FACE_CY];
	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int nIndex = pos;
		NetEncoding.write4byte(data, lUserID, nIndex);
		nIndex += 4;
		String MD5Str = "";
		if (szPassWord != null && !szPassWord.equals("")) {
			try {
				MD5Str = NetEncoding.changeToMD5(szPassWord);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += DF.LEN_PASSWORD * 2;
		if (szMachineID != null && !szMachineID.equals("")) {
			try {
				MD5Str = NetEncoding.changeToMD5(szMachineID);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += DF.LEN_MACHINE_ID * 2;

		for (int i = 0; i < dwCustomFace.length; i++) {
			NetEncoding.write4byte(data, dwCustomFace[i], nIndex);
			nIndex += 4;
		}
		return nIndex - pos;
	}

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
