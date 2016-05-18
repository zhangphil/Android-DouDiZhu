package com.qp.lib.cmd.Game;

import com.qp.lib.define.DF;
import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GF_C_UserChat implements ICmd {

	public int		wChatLength;
	public int		nChatColor;
	public long		lTargetUserID;
	public String	szChatString;

	public CMD_GF_C_UserChat() {
		szChatString = "";
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int nIndex = pos;
		NetEncoding.write2byte(data, wChatLength + (128 <= wChatLength ? 0 : 1), nIndex);
		nIndex += 2;
		NetEncoding.write4byte(data, nChatColor, nIndex);
		nIndex += 4;
		NetEncoding.write4byte(data, lTargetUserID, nIndex);
		nIndex += 4;
		NetEncoding.stringToWcharUnicodeBytes(szChatString, data, nIndex);
		nIndex += (szChatString.length() + (DF.LEN_USER_CHAT > szChatString.length() ? 1 : 0)) * 2;
		return nIndex - pos;
	}

}
