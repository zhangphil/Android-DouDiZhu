package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;

public class CMD_GR_C_UserExpression implements ICmd {

	public int	wItemIndex;
	public long	dwTargetUserID;

	public CMD_GR_C_UserExpression() {
	}

	public int ReadFromByteArray(byte arg0[], int arg1) {
		return 0;
	}

	public int WriteToByteArray(byte data[], int pos) {
		int index = pos;
		NetEncoding.write2byte(data, wItemIndex, index);
		index += 2;
		NetEncoding.write4byte(data, dwTargetUserID, index);
		return (index += 4) - pos;
	}
}
