package com.qp.lib.cmd.logon;

import java.security.NoSuchAlgorithmException;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_MB_RegisterAccounts implements ICmd {

	public int		nModuleID;
	public long		lPlazaVersion;
	public byte		cbDeviceType;
	public String	szLogonPass;
	public String	szInsurePass;
	public int		nFaceID;
	public byte		cbGender;
	public String	szAccounts;
	public String	szNickName;
	public String	szMachineID;
	public String	szMobilePhone;

	public CMD_MB_RegisterAccounts() {
		szLogonPass = "";
		szInsurePass = "";
		szAccounts = "";
		szNickName = "";
		szMachineID = "";
		szMobilePhone = "";
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		NetEncoding.write2byte(data, nModuleID, nIndex);
		nIndex += 2;
		NetEncoding.write4byte(data, lPlazaVersion, nIndex);
		nIndex += 4;
		data[nIndex++] = 16;
		if (szLogonPass != null && !szLogonPass.equals("")) {
			String MD5Str = "";
			try {
				MD5Str = NetEncoding.changeToMD5(szLogonPass);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += 66;
		if (szInsurePass != null && !szInsurePass.equals("")) {
			String MD5Str = "";
			try {
				MD5Str = NetEncoding.changeToMD5(szLogonPass);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += 66;
		NetEncoding.write2byte(data, nFaceID, nIndex);
		nIndex += 2;
		data[nIndex++] = cbGender;
		if (szAccounts != null && !szAccounts.equals(""))
			NetEncoding.stringToWcharUnicodeBytes(szAccounts, data, nIndex);
		nIndex += 64;
		if (szNickName != null && !szNickName.equals(""))
			NetEncoding.stringToWcharUnicodeBytes(szNickName, data, nIndex);
		nIndex += 64;
		if (szMachineID != null && !szMachineID.equals("")) {
			String MD5Str = "";
			try {
				MD5Str = NetEncoding.changeToMD5(szMachineID);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		nIndex += 66;
		if (szMobilePhone != null && !szMobilePhone.equals(""))
			NetEncoding.stringToWcharUnicodeBytes(szMobilePhone, data, nIndex);
		return (nIndex += 24) - pos;
	}

	public int ReadFromByteArray(byte arg0[], int arg1) {
		return 0;
	}
}
