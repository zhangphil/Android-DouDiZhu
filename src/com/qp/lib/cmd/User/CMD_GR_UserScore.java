package com.qp.lib.cmd.User;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;
import com.qp.lib.tag.UserScore;

public class CMD_GR_UserScore implements ICmd {

	public long			lUserID;
	public UserScore	UserScore;

	public CMD_GR_UserScore() {
		UserScore = new UserScore();
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		lUserID = NetEncoding.read4Byte(data, index);
		index += 4;
		UserScore.lScore = NetEncoding.read8Byte(data, index);
		index += 8;
		UserScore.lWinCount = NetEncoding.read4Byte(data, index);
		index += 4;
		UserScore.lLostCount = NetEncoding.read4Byte(data, index);
		index += 4;
		UserScore.lDrawCount = NetEncoding.read4Byte(data, index);
		index += 4;
		UserScore.lFleeCount = NetEncoding.read4Byte(data, index);
		index += 4;
		UserScore.lExperience = NetEncoding.read4Byte(data, index);
		return (index += 4) - pos;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
