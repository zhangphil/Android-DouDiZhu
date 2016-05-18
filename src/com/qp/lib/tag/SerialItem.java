package com.qp.lib.tag;

public class SerialItem {
	public String	szSerial		= "";
	public long		lRecordTime;
	public String	szUin;
	public String	szProductName	= "";

	@Override
	public String toString() {
		return szUin + "#" + szProductName + "#" + szSerial + "#" + lRecordTime;
	}

	public SerialItem() {

	}

	public SerialItem(String uin, String name, String serial, long time) {

		szUin = uin;

		szProductName = name;

		szSerial = serial;

		lRecordTime = time;
	}
}
