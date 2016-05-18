package com.qp.lib.interface_ex;


public interface ILoadingInfo
{

	public abstract void onUpdate(int i, String s);

	public abstract String getLoadingText();

	public abstract int getLoadingProgerss();
}
