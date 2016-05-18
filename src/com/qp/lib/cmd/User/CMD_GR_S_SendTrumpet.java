package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_S_SendTrumpet implements ICmd {

	public int		wPropertyIndex;
	public long		dwSendUserID;
	public int		TrumpetColor;
	public String	szSendNickName;
	public String	szTrumpetContent;

	public CMD_GR_S_SendTrumpet() {
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		wPropertyIndex = NetEncoding.read2Byte(data, index);
		index += 2;
		dwSendUserID = NetEncoding.read4Byte(data, index);
		index += 4;
		TrumpetColor = NetEncoding.read4Byte(data, index);
		index += 4;
		szSendNickName = NetEncoding.wcharUnicodeBytesToString(data, index, 64);
		index += 64;
		szTrumpetContent = NetEncoding.wcharUnicodeBytesToString(data, index, 0);
		return (index += 256) - pos;
	}
}
