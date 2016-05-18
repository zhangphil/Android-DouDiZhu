package com.qp.lib.interface_ex;


public interface ILoginCompleteListener
{

	public static final int LOGIN_FAILD = 0;
	public static final int LOGIN_SUCCEED = 1;
	public static final int LOGIN_FINISH = 2;
	public static final int LOGIN_NOTIFY = 3;

	public abstract void onLoginComplete(int i, String s);
}
