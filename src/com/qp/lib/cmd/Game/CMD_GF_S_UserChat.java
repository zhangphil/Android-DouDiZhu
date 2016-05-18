package com.qp.lib.cmd.Game;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GF_S_UserChat implements ICmd {

	public int		wChatLength;
	public int		nChatColor;
	public long		lSendUserID;
	public long		lTargetUserID;
	public String	szChatString;

	public CMD_GF_S_UserChat() {
		szChatString = "";
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int nIndex = pos;
		wChatLength = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		nChatColor = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		lSendUserID = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		lTargetUserID = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		int lenght = 0;
		szChatString = NetEncoding.wcharUnicodeBytesToString(data, nIndex, lenght);

		return nIndex - pos;
	}

	public int WriteToByteArray(byte arg0[], int arg1) {
		return 0;
	}
}
