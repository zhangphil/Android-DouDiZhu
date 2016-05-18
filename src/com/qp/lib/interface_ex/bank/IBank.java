package com.qp.lib.interface_ex.bank;

public interface IBank {

	public static final int	INSURE_SAVE		= 0;
	public static final int	INSURE_TRANSFER	= 1;

	public abstract boolean PerformQueryInfo();

	public abstract boolean PerformSaveScore(long l);

	public abstract boolean PerformTakeScore(long l, String s);

	public abstract boolean PerformTransferScore(int i, String s, long l, String s1);

	public abstract void QueryInsureInfoSucceed(Object obj);

	public abstract void ActivityInsureSucceed(Object obj);
	
	public abstract void ActivityInsureFailure(Object obj);

	public abstract long getUserScore();

	public abstract long getUserInsure();

	public abstract int getRevenueTake();

	public abstract int getRevenueTransfer();

	public abstract long getTransferPrerequisite();
}
