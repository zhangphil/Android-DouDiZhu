package com.qp.lib.control;

import com.qp.lib.interface_ex.net.ISocketMission;
import com.qp.lib.utility.Util;

public abstract class SocketMission implements ISocketMission {

	@SuppressWarnings("unused")
	private static final String	TAG			= "SocketMission";
	protected static int		m_nRevMode	= 0;

	public SocketMission() {
	}

	public static void setSocketMode(int mode) {
		m_nRevMode = mode;
		Util.d("SocketMission", (new StringBuilder("[接收模式:")).append(m_nRevMode).append("]").toString());
	}

	public static int getRevMode() {
		return m_nRevMode;
	}

	public boolean onEventSocketError(int main, int sub, Object obj) {
		Util.e("SocketMission", "onEventSocketError-not_here");
		return false;
	}

}
