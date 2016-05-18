package com.qp.lib.interface_ex;


public interface IDownLoadProgress
{

	public abstract void onUpdate(int i, String s);

	public abstract void onDownLoadCompelte(boolean flag, int i, String s);
}
