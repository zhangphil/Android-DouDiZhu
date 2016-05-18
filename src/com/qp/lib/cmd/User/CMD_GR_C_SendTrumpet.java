package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_C_SendTrumpet implements ICmd {

	public byte		cbRequstArea;
	public int		wPropertyIndex;
	public int		TrumpetColor;
	public String	szTrumpetContent;

	public CMD_GR_C_SendTrumpet() {
	}

	public int WriteToByteArray(byte data[], int pos) {
		int index = pos;
		data[index++] = cbRequstArea;
		NetEncoding.write2byte(data, wPropertyIndex, index);
		index += 2;
		NetEncoding.write4byte(data, TrumpetColor, index);
		index += 4;
		NetEncoding.stringToWcharUnicodeBytes(szTrumpetContent, data, index);
		return (index += 256) - pos;
	}

	public int ReadFromByteArray(byte data[], int pos) {
		return 0;
	}
}
