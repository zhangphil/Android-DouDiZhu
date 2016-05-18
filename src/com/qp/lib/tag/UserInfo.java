package com.qp.lib.tag;

import com.qp.lib.interface_ex.user.IUserItem;

public class UserInfo implements IUserItem {

	@SuppressWarnings("unused")
	private static final String	TAG	= "UserInfo";
	public long					lUserID;
	public long					lGameID;
	public String				szNickName;
	public int					nFaceID;
	public long					lCustomID;
	public byte					cbGender;
	public byte					cbMemberOrder;
	public int					nTableID;
	public int					nChairID;
	public int					nStatus;
	public long					lScore;
	public long					lInsure;
	public long					lWinCount;
	public long					lLostCount;
	public long					lDrawCount;
	public long					lFleeCount;
	public long					lExperience;
	public long					lLoveLiness;

	public UserInfo() {
	}

	public UserInfo GetUserInfo() {
		return this;
	}

	public int GetFaceID() {
		return nFaceID;
	}

	public long GetCustomID() {
		return lCustomID;
	}

	public byte GetGender() {
		return cbGender;
	}

	public long GetUserID() {
		return lUserID;
	}

	public long GetGameID() {
		return lGameID;
	}

	public String GetNickName() {
		return szNickName;
	}

	public long GetUserExperience() {
		return lExperience;
	}

	public int GetMemberOrder() {
		return cbMemberOrder;
	}

	public int GetTableID() {
		return nTableID;
	}

	public int GetChairID() {
		return nChairID;
	}

	public int GetUserStatus() {
		return nStatus;
	}

	public long GetUserScore() {
		return lScore;
	}

	public long GetUserWinCount() {
		return lWinCount;
	}

	public long GetUserLostCount() {
		return lLostCount;
	}

	public long GetUserDrawCount() {
		return lDrawCount;
	}

	public long GetUserFleeCount() {
		return lFleeCount;
	}

	public long GetUserPlayCount() {
		return lFleeCount + lLostCount + lWinCount + lDrawCount;
	}

	public long GetUserWinRate() {
		long sum = GetUserPlayCount();
		if (sum != 0L)
			return (lWinCount * 100L) / sum;
		else
			return 0L;
	}

	public long GetUserLostRate() {
		long sum = GetUserPlayCount();
		if (sum != 0L)
			return (lLostCount * 100L) / sum;
		else
			return 0L;
	}

	public long GetUserDrawRate() {
		long sum = GetUserPlayCount();
		if (sum != 0L)
			return (lDrawCount * 100L) / sum;
		else
			return 0L;
	}

	public long GetUserFleeRate() {
		long sum = GetUserPlayCount();
		if (sum != 0L)
			return (lFleeCount * 100L) / sum;
		else
			return 0L;
	}

	public long GetUserInsure() {
		return lInsure;
	}

	public void Empty() {
		lUserID = 0L;
		lGameID = 0L;
		szNickName = "";
		nFaceID = 0;
		lCustomID = 0L;
		cbGender = 0;
		cbMemberOrder = 0;
		nTableID = 65535;
		nChairID = 65535;
		nStatus = 0;
		lScore = 0L;
		lInsure = 0L;
		lWinCount = 0L;
		lLostCount = 0L;
		lDrawCount = 0L;
		lFleeCount = 0L;
		lExperience = 0L;
	}

	public void SetUserItem(IUserItem item) {
		if (item == null) {
			Empty();
		} else {
			lUserID = item.GetUserID();
			lGameID = item.GetGameID();
			szNickName = item.GetNickName();
			nFaceID = item.GetFaceID();
			lCustomID = item.GetCustomID();
			cbGender = item.GetGender();
			cbMemberOrder = (byte) item.GetMemberOrder();
			nTableID = item.GetTableID();
			nChairID = item.GetChairID();
			nStatus = item.GetUserStatus();
			lScore = item.GetUserScore();
			lInsure = item.GetUserInsure();
			lWinCount = item.GetUserWinCount();
			lLostCount = item.GetUserLostCount();
			lDrawCount = item.GetUserDrawCount();
			lFleeCount = item.GetUserFleeCount();
			lExperience = item.GetUserExperience();
		}
	}

	public boolean IsEmpty() {
		return lUserID == 0L;
	}

	public void SetUserStatus(UserStatus status) {
		nTableID = status.nTableID;
		nChairID = status.nChairID;
		nStatus = status.nStatus;
	}

	public void SetUserScore(UserScore score) {
		lScore = score.lScore;
		lInsure = score.lInsure;
		lWinCount = score.lWinCount;
		lLostCount = score.lLostCount;
		lDrawCount = score.lDrawCount;
		lFleeCount = score.lFleeCount;
		lExperience = score.lExperience;
	}
}
