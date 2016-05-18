// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AccountsControl.java

package com.qp.lib.control;

import android.database.Cursor;
import java.util.*;

import com.qp.lib.help.DBService;
import com.qp.lib.tag.AccountsDB;
import com.qp.lib.utility.Util;

public class AccountsControl {

	public static final String	TAG	= "AccountsControl";
	ArrayList<AccountsDB>		m_AccountList;
	public int					m_nLastIndex;

	public AccountsControl() {
		m_AccountList = new ArrayList<AccountsDB>();
	}

	public ArrayList<AccountsDB> getAccountList() {
		return m_AccountList;
	}

	public void onInitAccountsControl(DBService dbcontrol) {
		if (dbcontrol != null) {
			Cursor cursor = dbcontrol.GetDBCursor();
			m_AccountList.clear();
			m_nLastIndex = -1;
			long lRecordLastLogon = 0L;
			if (cursor != null) {
				for (; !cursor.isAfterLast(); cursor.moveToNext()) {
					AccountsDB accountsinfo = new AccountsDB();
					accountsinfo.m_szAccounts = cursor.getString(cursor.getColumnIndex("accounts"));
					accountsinfo.m_lLastLogon = Long.parseLong(cursor.getString(cursor.getColumnIndex("lastlogon")));
					accountsinfo.m_nAutoLogon = cursor.getInt(cursor.getColumnIndex("autologon"));
					accountsinfo.m_szPass = cursor.getString(cursor.getColumnIndex("password"));
					accountsinfo.m_nFaceID = cursor.getInt(cursor.getColumnIndex("faceid"));
					accountsinfo.m_bSave = accountsinfo.m_szPass != null && !accountsinfo.equals("");
					m_AccountList.add(accountsinfo);
					if (accountsinfo.m_lLastLogon > lRecordLastLogon)
						m_nLastIndex = m_AccountList.size() - 1;
				}

				cursor.close();
			}
		}
	}

	public void onSaveAccounts(DBService dbcontrol) {
		if (dbcontrol != null) {
			dbcontrol.DeletDataAll("game_accounts_info");
			AccountsDB dbinfo;
			for (Iterator<AccountsDB> iterator = m_AccountList.iterator(); iterator.hasNext(); dbcontrol.SaveTableLogon(dbinfo))
				dbinfo = (AccountsDB) iterator.next();

		}
	}

	public void onUpdateAccounts(AccountsDB account) {
		boolean bFind = false;
		AccountsDB item = null;
		if (account != null) {
			for (int i = 0; i < m_AccountList.size(); i++) {
				item = (AccountsDB) m_AccountList.get(i);
				if (!item.m_szAccounts.equals(account.m_szAccounts))
					continue;
				bFind = true;
				if (account.m_bSave)
					item.m_szPass = account.m_szPass;
				item.m_lLastLogon = account.m_lLastLogon;
				item.m_nAutoLogon = account.m_nAutoLogon;
				item.m_nFaceID = account.m_nFaceID;
				break;
			}

			if (!bFind) {
				bFind = true;
				item = new AccountsDB();
				item.m_bSave = account.m_bSave;
				item.m_lLastLogon = account.m_lLastLogon;
				item.m_nAutoLogon = account.m_nAutoLogon;
				item.m_nFaceID = account.m_nFaceID;
				item.m_szAccounts = account.m_szAccounts;
				item.m_szPass = account.m_szPass;
				m_AccountList.add(item);
			}
		}
		if (!bFind)
			Util.e("AccountsControl", (new StringBuilder("更新账号信息失败![")).append(account.toString()).append("]").toString());
		Util.d("AccountsControl", (new StringBuilder("size:")).append(m_AccountList.size()).toString());
	}

	public AccountsDB getLastLoginAccount() {
		if (m_nLastIndex != -1 && m_nLastIndex < m_AccountList.size())
			return (AccountsDB) m_AccountList.get(m_nLastIndex);
		else
			return null;
	}

	public void onDeletAccount(int index) {
		if (index >= 0 && index < m_AccountList.size())
			m_AccountList.remove(index);
	}
}
