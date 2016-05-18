package com.qp.lib.utility;

import android.content.SharedPreferences;
import java.util.ArrayList;

import com.qp.lib.main.AppMain;
import com.qp.lib.tag.SerialItem;

public class SerialRecord {

	public static ArrayList<SerialItem>	m_SerialRecord	= new ArrayList<SerialItem>();

	public SerialRecord() {
	}

	public static int checkSerial(String serila) {
		if (serila != null && !serila.equals("")) {
			for (int i = 0; i < m_SerialRecord.size(); i++) {
				SerialItem item = m_SerialRecord.get(i);
				if ((item.szSerial).equals(serila))
					return i;
			}
		}
		return -1;
	}

	public static boolean addSerial(String Uin, String szname, String serila) {
		if (checkSerial(serila) == -1) {
			SerialItem item = new SerialItem();
			item.szUin = Uin;
			item.szProductName = szname;
			item.szSerial = serila;
			item.lRecordTime = System.currentTimeMillis();
			m_SerialRecord.add(item);
			return true;
		} else {
			return false;
		}
	}
	public static boolean deletSerial(String serila) {
		int index = checkSerial(serila);
		if (index != -1) {
			m_SerialRecord.remove(index);
			return true;
		} else {
			return false;
		}
	}

	public static void onLoadSerial() {
		SharedPreferences record = AppMain.getInstance().getSharedPreferences("serialrecord", 0);
		int index = 0;
		String szSerial = "";
		if (record != null)
			do {
				szSerial = record.getString((new StringBuilder("record")).append(index).toString(), "");
				if (szSerial != null && !szSerial.equals("")) {
					String[] szrecord = szSerial.split("#");
					if (szrecord.length == 4) {
						SerialItem item = new SerialItem(szrecord[0], szrecord[1], szrecord[2], Long.parseLong(szrecord[3]));
						m_SerialRecord.add(item);
					}
				}
				index++;
			} while (szSerial != null && !szSerial.equals(""));
	}

	public static void onSaveSerial() {
		if (m_SerialRecord.size() > 0) {
			SharedPreferences record = AppMain.getInstance().getSharedPreferences("serialrecord", 0);
			android.content.SharedPreferences.Editor editor = record.edit();
			editor.clear();
			for (int i = 0; i < m_SerialRecord.size(); i++) {
				editor.putString((new StringBuilder("record")).append(i).toString(), m_SerialRecord.get(i).toString());
			}
			editor.commit();
		}
	}

}
