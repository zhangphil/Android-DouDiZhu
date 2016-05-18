package com.qp.lib.help;

import com.qp.lib.utility.Util;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLHelp extends SQLiteOpenHelper {

	public static final String	DB_NAME			= "game_database.db";
	public static final int		VERSION			= 1;
	public static final String	TABLE_NAME		= "game_accounts_info";
	 
	public SQLHelp(Context context) {
		super(context, DB_NAME , null, 1);
	}

	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL("create table if not exists game_accounts_info(accounts varchar,password varchar,lastlogon varchar,faceid integer,autologon integer)");
		} catch (SQLException e) {
			Util.e("CSQLHelp", "onCreate db error!");
		}
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS game_accounts_info");
		onCreate(db);
	}
}
