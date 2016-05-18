package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;
import com.qp.lib.tag.UserStatus;

public class CMD_GR_UserStatus implements ICmd {

	public long			lUserID;
	public UserStatus	UserStatus;

	public CMD_GR_UserStatus() {
		UserStatus = new UserStatus();
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		lUserID = NetEncoding.read4Byte(data, index);
		index += 4;
		UserStatus.nTableID = NetEncoding.read2Byte(data, index);
		index += 2;
		UserStatus.nChairID = NetEncoding.read2Byte(data, index);
		index += 2;
		UserStatus.nStatus = data[index++];
		return index - pos;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
