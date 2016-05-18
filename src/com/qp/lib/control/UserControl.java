package com.qp.lib.control;

import java.util.ArrayList;

import com.qp.lib.interface_ex.user.IUserControl;
import com.qp.lib.interface_ex.user.IUserItem;
import com.qp.lib.tag.UserScore;
import com.qp.lib.tag.UserStatus;
import com.qp.lib.utility.Util;

public class UserControl implements IUserControl {

	@SuppressWarnings("unused")
	private static final String	TAG	= "UserControl";
	ArrayList<IUserItem>		m_UserItem;

	public UserControl() {
	}

	public void onInitUserControl() {
		m_UserItem = new ArrayList<IUserItem>();
	}

	public void onReleaseUserItem(boolean releaseme) {
		for (int i = 0; i < m_UserItem.size(); i++)
			if (i != 0 || releaseme)
				((IUserItem) m_UserItem.get(i)).Empty();

	}

	public IUserItem onEventUserEnter(IUserItem useritem, boolean blookon) {
		IUserItem item = searchUserByUserID(useritem.GetUserID());
		if (item == null)
			item = getEmptyUser();
		if (item == null) {
			m_UserItem.add(useritem);
			return (IUserItem) m_UserItem.get(m_UserItem.size() - 1);
		} else {
			item.SetUserItem(useritem);
			return item;
		}
	}

	private IUserItem getEmptyUser() {
		for (int i = 0; i < m_UserItem.size(); i++) {
			IUserItem item = (IUserItem) m_UserItem.get(i);
			if (item.IsEmpty())
				return item;
		}

		return null;
	}

	public void onEventUserLeave(long lUserid, boolean blookon) {
		if (lUserid > 0L && lUserid != 65535L) {
			for (int i = 0; i < m_UserItem.size(); i++) {
				IUserItem item = (IUserItem) m_UserItem.get(i);
				if (item.GetUserID() != lUserid)
					continue;
				item.Empty();
				break;
			}

		}
	}

	public IUserItem onEventUserScore(long lUserid, UserScore oldscore, UserScore newscore) {
		for (int i = 0; i < m_UserItem.size(); i++) {
			IUserItem item = (IUserItem) m_UserItem.get(i);
			if (item.GetUserID() == lUserid) {
				UserScore old = new UserScore();
				old.lScore = item.GetUserScore();
				old.lInsure = item.GetUserInsure();
				old.lWinCount = item.GetUserWinCount();
				old.lLostCount = item.GetUserLostCount();
				old.lDrawCount = item.GetUserDrawCount();
				old.lFleeCount = item.GetUserFleeCount();
				old.lExperience = item.GetUserExperience();
				item.SetUserScore(newscore);
				return item;
			}
		}

		return null;
	}

	public IUserItem onEventUserStatus(long lUserid, UserStatus oldstatus, UserStatus newstatus) {
		for (int i = 0; i < m_UserItem.size(); i++) {
			IUserItem item = (IUserItem) m_UserItem.get(i);
			if (item.GetUserID() == lUserid) {
				item.SetUserStatus(newstatus);
				return item;
			}
		}

		return null;
	}

	public IUserItem onEventCustomFace(IUserItem useritem, boolean blookon) {
		if (useritem != null) {
			for (int i = 0; i < m_UserItem.size(); i++) {
				IUserItem item = (IUserItem) m_UserItem.get(i);
				if (item.GetUserID() == useritem.GetUserID()) {
					item.GetUserInfo().lCustomID = useritem.GetCustomID();
					return item;
				}
			}
		}
		return null;
	}

	public IUserItem getMeUserItem() {
		if (m_UserItem.size() > 0) {
			return (IUserItem) m_UserItem.get(0);
		} else {
			Util.e("UserControl", (new StringBuilder("获取自己失败-用户个数:")).append(m_UserItem.size()).toString());
			return null;
		}
	}

	public IUserItem searchUserByUserID(long userid) {
		if (userid == 0L || userid == 65535L)
			return null;
		for (int i = 0; i < m_UserItem.size(); i++) {
			IUserItem item = (IUserItem) m_UserItem.get(i);
			if (item.GetUserID() == userid)
				return item;
		}

		return null;
	}

	public IUserItem searchUserByGameID(long gameid) {
		if (gameid == 0L || gameid == 65535L)
			return null;
		for (int i = 0; i < m_UserItem.size(); i++) {
			IUserItem item = (IUserItem) m_UserItem.get(i);
			if (item.GetGameID() == gameid)
				return item;
		}

		return null;
	}

	public IUserItem searchUserByNickName(String nickname) {
		if (nickname == null || nickname.equals(""))
			return null;
		for (int i = 0; i < m_UserItem.size(); i++) {
			IUserItem item = (IUserItem) m_UserItem.get(i);
			if (item.GetNickName().equals(nickname))
				return item;
		}

		return null;
	}

	public IUserItem searchUserByTableInfo(int table, int chair) {
		if (table == 65535 || chair == 65535)
			return null;
		for (int i = 0; i < m_UserItem.size(); i++) {
			IUserItem item = (IUserItem) m_UserItem.get(i);
			if (item.GetTableID() == table && item.GetChairID() == chair)
				return item;
		}

		return null;
	}

	@Override
	public IUserItem onEventMyNickName(String szName) {
		if (m_UserItem.size() > 0 && m_UserItem.get(0) != null) {
			m_UserItem.get(0).GetUserInfo().szNickName = szName;
			return m_UserItem.get(0);
		}
		return null;
	}
}
