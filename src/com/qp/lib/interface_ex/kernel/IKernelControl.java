package com.qp.lib.interface_ex.kernel;

import com.qp.lib.interface_ex.net.ISocketMission;

public interface IKernelControl extends IKernel {

	public abstract void setLoginEntrance(int i);

	public abstract boolean createConnect(String url, int port,boolean heat);

	public abstract boolean intemitConnect();

	public abstract void setSocketMission(ISocketMission isocketmission);

	public abstract boolean isInService();

	public abstract void setSocketMode(int i);

	public abstract void setLoginInfo(String s, String s1, boolean flag, boolean flag1);

	public abstract void setUserRight(long l);

	public abstract void setMasterRight(long l);

	public abstract void onReleaseUserItem(boolean flag);

	public abstract void onSaveAccounts();
}
