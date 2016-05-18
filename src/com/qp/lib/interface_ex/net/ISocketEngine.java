package com.qp.lib.interface_ex.net;


public interface ISocketEngine
{

	public abstract boolean ConnectSocket(String url, int port,  boolean bHeat);

	public abstract boolean Close();

	public abstract boolean SendSocketDate(int i, int j, Object obj);

	public abstract boolean IsAlive();

	public abstract void SetConnetTimeOut(int i);

	public abstract void SetRevtTimeOut(long l);

	public abstract void SetTCPValidate(boolean flag);

	boolean SendSocketDateEx(int main, int sub, byte[] data);
}
