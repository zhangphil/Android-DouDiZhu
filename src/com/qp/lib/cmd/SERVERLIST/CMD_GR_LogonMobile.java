package com.qp.lib.cmd.SERVERLIST;

import java.security.NoSuchAlgorithmException;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_LogonMobile implements ICmd {

	public int		nGameID;
	public long		lVersion;
	public byte		cbDeviceType;
	public int		wBehaviorFlags;
	public int		wPageTableCount;
	public long		lUserID;
	public String	szPassword;
	public String	szMachineID;

	public CMD_GR_LogonMobile() {
		szPassword = "";
		szMachineID = "";
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		NetEncoding.write2byte(data, nGameID, nIndex);
		nIndex += 2;
		NetEncoding.write4byte(data, lVersion, nIndex);
		nIndex += 4;
		data[nIndex++] = cbDeviceType;
		NetEncoding.write2byte(data, wBehaviorFlags, nIndex);
		nIndex += 2;
		NetEncoding.write2byte(data, wPageTableCount, nIndex);
		nIndex += 2;
		NetEncoding.write4byte(data, lUserID, nIndex);
		nIndex += 4;
		String MD5Str = "";
		try {
			MD5Str = NetEncoding.changeToMD5(szPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		nIndex += 66;
		if (szMachineID != null && !szMachineID.equals("")) {
			try {
				MD5Str = NetEncoding.changeToMD5(szMachineID);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			NetEncoding.stringToWcharUnicodeBytes(MD5Str, data, nIndex);
		}
		return (nIndex += 66) - pos;
	}

	public String toString() {
		String szinfo = super.toString();
		szinfo = (new StringBuilder(String.valueOf(szinfo))).append("#").toString();
		szinfo = (new StringBuilder(String.valueOf(szinfo))).append("[模块标识:").append(nGameID).append("]").toString();
		szinfo = (new StringBuilder(String.valueOf(szinfo))).append("[广场版本:").append(lVersion).append("]").toString();
		szinfo = (new StringBuilder(String.valueOf(szinfo))).append("[设备类型:").append(cbDeviceType).append("]").toString();
		szinfo = (new StringBuilder(String.valueOf(szinfo))).append("[wBehaviorFlags:").append(wBehaviorFlags).append("]").toString();
		szinfo = (new StringBuilder(String.valueOf(szinfo))).append("[wPageTableCount:").append(wPageTableCount).append("]").toString();
		szinfo = (new StringBuilder(String.valueOf(szinfo))).append("[用户ID:").append(lUserID).append("]").toString();
		szinfo = (new StringBuilder(String.valueOf(szinfo))).append("[密码:").append(szPassword).append("]").toString();
		szinfo = (new StringBuilder(String.valueOf(szinfo))).append("[机器标识:").append(szMachineID).append("]").toString();
		return szinfo;
	}
}
