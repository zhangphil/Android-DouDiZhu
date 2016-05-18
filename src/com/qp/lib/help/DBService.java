package com.qp.lib.help;

import com.qp.lib.tag.AccountsDB;
import com.qp.lib.utility.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBService {

	private static final String	TAG	= null;
	private SQLHelp				m_SqlHelp;

	DBService() {
	}

	public DBService(Context context) {
		m_SqlHelp = new SQLHelp(context);
	}

	public void dropTable(String taleName) {
		SQLiteDatabase db = m_SqlHelp.getWritableDatabase();
		if (db != null) {
			db.execSQL((new StringBuilder("DROP TABLE IF EXISTS ")).append(taleName).toString());
			db.close();
		} else {
			Util.e(TAG, "dropTable-db-null");
		}
	}

	public void CreateTableLogon() {
		SQLiteDatabase db = m_SqlHelp.getWritableDatabase();
		if (db != null) {
			db.execSQL("create table if not exists game_accounts_info(accounts varchar,password varchar,lastlogon varchar,faceid integer,autologon integer)");
			db.close();
		} else {
			Util.e(TAG, "CreateTableLogon-db-null");
		}
	}

	public void SaveTableLogon(AccountsDB data) {
		String sql = (new StringBuilder("insert into game_accounts_info(accounts,password,lastlogon,faceid,autologon) values('")).append(data.m_szAccounts)
				.append("',").append("'").append(data.m_szPass).append("',").append("'").append(data.m_lLastLogon).append("',").append("'")
				.append(data.m_nFaceID).append("',").append(data.m_nAutoLogon).append(")").toString();
		SQLiteDatabase db = m_SqlHelp.getWritableDatabase();
		if (db != null) {
			db.execSQL(sql);
			db.close();
		}
	}

	public void UpdateTableLogon(AccountsDB data) {
		ContentValues values = new ContentValues();
		values.clear();
		values.put("accounts", data.m_szAccounts);
		values.put("password", data.m_szPass);
		values.put("lastlogon", (new StringBuilder(String.valueOf(data.m_lLastLogon))).toString());
		values.put("faceid", Integer.valueOf(data.m_nFaceID));
		values.put("autologon", Integer.valueOf(data.m_nAutoLogon));
		SQLiteDatabase db = m_SqlHelp.getWritableDatabase();
		if (db != null) {
			db.update("game_accounts_info", values, "accounts = data.m_szAccounts", null);
			db.close();
		}
	}

	public Cursor GetDBCursor() {
		Cursor cursor = null;
		SQLiteDatabase db = m_SqlHelp.getWritableDatabase();
		if (db != null) {
			cursor = db.query("game_accounts_info", null, null, null, null, null, "accounts asc");
			if (cursor != null)
				cursor.moveToFirst();
			db.close();
		}
		return cursor;
	}

	public AccountsDB GetDataLogon() {
		Cursor cursor = null;
		AccountsDB dbdata = null;
		SQLiteDatabase db = m_SqlHelp.getWritableDatabase();
		if (db != null) {
			cursor = db.query("game_accounts_info", null, null, null, null, null, "accounts asc");
			if (cursor != null) {
				cursor.moveToFirst();
				if (!cursor.isAfterLast()) {
					dbdata = new AccountsDB();
					dbdata.m_szAccounts = cursor.getString(cursor.getColumnIndex("accounts"));
					dbdata.m_szPass = cursor.getString(cursor.getColumnIndex("password"));
					dbdata.m_lLastLogon = Long.parseLong(cursor.getString(cursor.getColumnIndex("lastlogon")));
					dbdata.m_nFaceID = cursor.getInt(cursor.getColumnIndex("faceid"));
					dbdata.m_nAutoLogon = cursor.getInt(cursor.getColumnIndex("autologon"));
				}
				cursor.close();
			}
			db.close();
		}
		return dbdata;
	}

	public long getDataCount(String tableName) {
		long lCount = 0L;
		SQLiteDatabase db = m_SqlHelp.getReadableDatabase();
		if (db != null) {
			Cursor cursor = db.rawQuery((new StringBuilder("select count(*) from ")).append(tableName).toString(), null);
			if (cursor != null) {
				cursor.moveToFirst();
				lCount = cursor.getLong(0);
				cursor.close();
			}
			db.close();
		}
		return lCount;
	}

	public void close() {
		if (m_SqlHelp != null)
			m_SqlHelp.close();
	}

	public void DeletDataAll(String sztable) {
		SQLiteDatabase db = m_SqlHelp.getWritableDatabase();
		if (db != null) {
			db.delete(sztable, "1", null);
			db.close();
		}
	}

	public boolean DeletDataByAccounts(String sztable, String useraccounts[]) {
		SQLiteDatabase db = m_SqlHelp.getWritableDatabase();
		if (db != null) {
			int ntemp = db.delete(sztable, "accounts=?", useraccounts);
			db.close();
			return ntemp > 0;
		} else {
			return false;
		}
	}

}
