package com.qp.lib.tag;


public class AccountsDB
{

	public String m_szAccounts;
	public String m_szPass;
	public int m_nAutoLogon;
	public long m_lLastLogon;
	public int m_nFaceID;
	public boolean m_bSave;

	public AccountsDB()
	{
		m_szAccounts = "";
		m_szPass = "";
	}
}
