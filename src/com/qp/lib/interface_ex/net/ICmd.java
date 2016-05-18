package com.qp.lib.interface_ex.net;


public interface ICmd
{

	public abstract int WriteToByteArray(byte data[], int pos);

	public abstract int ReadFromByteArray(byte data[], int pos);
}
