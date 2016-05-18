package com.qp.lib.cmd.Bank;

import java.security.NoSuchAlgorithmException;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_C_TakeScoreRequest implements ICmd {

	public byte		cbActivityGame;
	public long		lTakeScore;
	public String	szInsurePass;

	public CMD_GR_C_TakeScoreRequest() {
		szInsurePass = "";
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		data[nIndex++] = cbActivityGame;
		NetEncoding.write8byte(data, lTakeScore, nIndex);
		nIndex += 8;
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
