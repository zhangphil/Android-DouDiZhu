package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_S_UserExpression implements ICmd {

	public int	wItemIndex;
	public long	lSendUserID;
	public long	lTargetUserID;

	public CMD_GR_S_UserExpression() {
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		wItemIndex = NetEncoding.read2Byte(data, index);
		index += 2;
		lSendUserID = NetEncoding.read4Byte(data, index);
		index += 4;
		lTargetUserID = NetEncoding.read4Byte(data, index);
		return (index += 4) - pos;
	}

	public int WriteToByteArray(byte arg0[], int arg1) {
		return 0;
	}
}
