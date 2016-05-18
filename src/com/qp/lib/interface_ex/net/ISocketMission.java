package com.qp.lib.interface_ex.net;


public interface ISocketMission
{

	public static final int ER_SOCKET_CONNECT = 0;
	public static final int ER_SOCKET_INPUT = 1;
	public static final int ER_SOCKET_OUTPUT = 2;
	public static final int ER_SOCKET_REV = 3;
	public static final int ER_SOCKET_SEND = 4;
	public static final int ER_PACK = 5;
	public static final int ER_SOCKET_REVTIMEOUT = 6;
	public static final int ER_SOCKET_UNKNOW = 7;
	public static final String ER_REASON[] = {
		"����ʧ��", "��������ȡʧ��", "�������ȡʧ��", "�����ж�", "����ʧ��", "���������ʧ��", "���ճ�ʱ", "δ֪����"
	};
	public static final int SOCKET_ERROR = 3;
	public static final int SOCKET_REV = 2;
	public static final int SOCKET_SEND = 1;

	public abstract boolean onEventSocketRead(int i, int j, Object obj);

	public abstract boolean onEventSocketError(int i, int j, Object obj);

}