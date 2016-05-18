package com.qp.lib.tag;

import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.utility.NetEncoding;
import com.qp.lib.utility.Util;

import android.util.Log;

public class UserInfoHead implements ICmd {

	public static final int	DTP_NULL				= 0;
	public static final int	DTP_GR_USER_NICKNAME	= 10;
	public static final int	DTP_GR_USER_GROUP_NAME	= 11;
	public static final int	DTP_GR_USER_UNDER_WRITE	= 12;
	public long				lGameID;
	public long				lUserID;
	public int				nFaceID;
	public long				lCustomID;
	public byte				cbGender;
	public byte				cbMemberOrder;
	public int				nTableID;
	public int				nChairID;
	public byte				cbUserStatus;
	public long				lScore;
	public long				lWinCount;
	public long				lLostCount;
	public long				lDrawCount;
	public long				lFleeCount;
	public long				lExperience;
	public String			szNickName;

	public UserInfoHead() {
		nTableID = 65535;
		nChairID = 65535;
		szNickName = "";
	}

	public int ReadFromByteArray(byte data[], int pos) {
		int index = pos;
		lGameID = NetEncoding.read4Byte(data, index);
		index += 4;
		lUserID = NetEncoding.read4Byte(data, index);
		index += 4;
		nFaceID = NetEncoding.read2Byte(data, index);
		index += 2;
		lCustomID = NetEncoding.read4Byte(data, index);
		index += 4;
		cbGender = data[index++];
		cbMemberOrder = data[index++];
		nTableID = NetEncoding.read2Byte(data, index);
		index += 2;
		nChairID = NetEncoding.read2Byte(data, index);
		index += 2;
		cbUserStatus = data[index++];
		lScore = NetEncoding.read8Byte(data, index);
		index += 8;
		lWinCount = NetEncoding.read4Byte(data, index);
		index += 4;
		lLostCount = NetEncoding.read4Byte(data, index);
		index += 4;
		lDrawCount = NetEncoding.read4Byte(data, index);
		index += 4;
		lFleeCount = NetEncoding.read4Byte(data, index);
		index += 4;
		lExperience = NetEncoding.read4Byte(data, index);
		index += 4;
		int datasize = 0;
		int datadescribe = 0;
		while (index + 4 < data.length) {
			datasize = NetEncoding.read2Byte(data, index);
			index += 2;
			datadescribe = NetEncoding.read2Byte(data, index);
			index += 2;
			if (datadescribe == 0)
				break;
			switch (datadescribe) {
				case 10 : // '\n'
					szNickName = NetEncoding.wcharUnicodeBytesToString(data, index, datasize);
					index += datasize;
					break;

				default : 
					Util.v("datadUescribe", (new StringBuilder("unkwon dtp:")).append(datadescribe).toString());
					break;
			}
		}
		return index - pos;
	}

	public int WriteToByteArray(byte data[], int pos) {
		return 0;
	}
}
