package com.qp.lib.cmd.logon;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_MB_LogonSuccess implements ICmd {

	public int		nFaceID;
	public byte		cbGender;
	public long		lUserID;
	public long		lGameID;
	public long		lExperience;
	public long		lLoveLiness;
	public String	szNickName;

	public CMD_MB_LogonSuccess() {
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int nIndex = pos;
		nFaceID = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		cbGender = data[nIndex++];
		lUserID = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		lGameID = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		lExperience = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		lLoveLiness = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		szNickName = NetEncoding.wcharUnicodeBytesToString(data, nIndex, 0);
		return nIndex - pos;
	}
}
