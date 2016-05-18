// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CMD_MB_LogonByAccounts.java

package com.qp.lib.cmd.logon;

import java.security.NoSuchAlgorithmException;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_MB_LogonByAccounts implements ICmd {

	public int		nModuleID;
	public long		lPlazaVersion;
	public byte		cbDeviceYype;
	public String	szPassWord;
	public String	szAccounts;
	public String	szMachineID;
	public String	szMobilePhone;
	public boolean	bMD5;

	public CMD_MB_LogonByAccounts() {
		szPassWord = "";
		szAccounts = "";
		szMachineID = "";
		szMobilePhone = "";
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		NetEncoding.write2byte(data, nModuleID, nIndex);
		nIndex += 2;
		NetEncoding.write4byte(data, lPlazaVersion, nIndex);
		nIndex += 4;
		data[nIndex++] = cbDeviceYype;
		String MD5Str = "";
		try {
			MD5Str = NetEncoding.changeToMD5(szPassWord);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		nIndex += 66;
		if (szAccounts != null && !szAccounts.equals(""))
			NetEncoding.stringToWcharUnicodeBytes(szAccounts, data, nIndex);
		nIndex += 64;
		if (szMachineID != null && !szMachineID.equals("")) {
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
