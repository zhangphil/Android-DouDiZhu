package com.qp.lib.interface_ex.user;

import com.qp.lib.tag.UserScore;
import com.qp.lib.tag.UserStatus;

public interface IUserEvent {

	public abstract IUserItem onEventUserEnter(IUserItem iuseritem, boolean flag);

	public abstract void onEventUserLeave(long l, boolean flag);

	public abstract IUserItem onEventUserScore(long l, UserScore userscore, UserScore userscore1);

	public abstract IUserItem onEventUserStatus(long l, UserStatus userstatus, UserStatus userstatus1);

	public abstract IUserItem onEventCustomFace(IUserItem iuseritem, boolean flag);

	public abstract IUserItem onEventMyNickName(String szName);
}
