package com.qp.lib.cmd.Bank;

import java.security.NoSuchAlgorithmException;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_C_TransferScoreRequest implements ICmd {

	public int		cbActivityGame;
	public int		cbByNickName;
	public long		lTransferScore;
	public String	szNickName;
	public String	szInsurePass;

	public CMD_GR_C_TransferScoreRequest() {
		szNickName = "";
		szInsurePass = "";
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		data[nIndex++] = (byte) cbActivityGame;
		data[nIndex++] = (byte) cbByNickName;
		NetEncoding.write8byte(data, lTransferScore, nIndex);
		nIndex += 8;
		if (szNickName != null && !szNickName.equals(""))
			NetEncoding.stringToWcharUnicodeBytes(szNickName, data, nIndex);
		nIndex += 64;
		if (szInsurePass != null && !szInsurePass.equals("")) {
			String MD5Str = "";
			try {
				MD5Str = NetEncoding.changeToMD5(szInsurePass);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		return (nIndex += 66) - pos;
	}
}
