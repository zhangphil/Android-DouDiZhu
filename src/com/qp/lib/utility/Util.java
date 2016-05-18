package com.qp.lib.utility;

import android.app.Activity;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	public static boolean	DEBUG_		= false;
	public static boolean	DEBUG_OUT	= false;
	public static boolean	ERROR_OUT	= false;
	public static boolean	WARN__OUT	= false;
	public static boolean	INFO_OUT	= false;

	public Util() {
	}

	public static void d(String tag, String info) {
		if (DEBUG_ && DEBUG_OUT)
			Log.d(tag, info);
	}

	public static void e(String tag, String info) {
		if (DEBUG_ && ERROR_OUT)
			Log.e(tag, info);
	}

	public static void w(String tag, String info) {
		if (DEBUG_ && WARN__OUT)
			Log.w(tag, info);
	}
	public static void v(String tag, String info) {
		if (DEBUG_    )
			Log.v(tag, info);
	}
	public static void i(String tag, String info) {
		if (DEBUG_ && INFO_OUT)
			Log.w(tag, info);
	}

	public static long ProcesVersion(int paramInt1, int paramInt2, int paramInt3) {
		int i = paramInt1 << 16;
		int j = 0x6000000 + i;
		int k = paramInt2 << 8;
		return (long) (j + k + paramInt3);
	}
	public static String getlv(long s)
	{
		 
			if(s>0 && s<=2000) return "务农";
			if(s>2000L && s<=4000) return "佃户";
			if(s>4000L && s<=8000)  return "雇工";
			if(s>8000L && s<=20000) return "作坊主";
			if(s>20000L && s<=40000) return "农场主";
			if(s>40000L && s<=150000)  return "地主";
			if(s>80000L && s<=300000L) return "大地主";
			if(s>150000L && s<=500000L) return "财主";
			if(s>300000L && s<=1000000L) return "富翁";
			if(s>500000L && s<=2000000L) return "大富翁";
			if(s>1000000L && s<=5000000L) return "小财神";
			if(s>2000000L && s<=10000000L) return "大财神";
			if(s>5000000L && s<=50000000L) return "赌棍";
			if(s>10000000L && s<=100000000L) return "赌侠";
			if(s>50000000L && s<=500000000L) return "赌王";
			if(s>100000000L && s<=500000000L) return "赌圣";
			if(s>500000000L && s<=1000000000L) return "赌神";
			if(s>1000000000L) return "职业赌神";
		 
		
		return "";
	}

	public static void CreateDeskIcon(Activity activity, String appname, int icon) {
		if (hasShortCut(activity, appname)) {
			return;
		} else {
			Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
			intent.putExtra("android.intent.extra.shortcut.NAME", appname);
			intent.putExtra("duplicate", false);
			Intent intent2 = new Intent("android.intent.action.MAIN");
			intent2.addCategory("android.intent.category.LAUNCHER");
			intent2.setComponent(new ComponentName(activity.getPackageName(), activity.getClass().getName()));
			intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
			intent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", android.content.Intent.ShortcutIconResource.fromContext(activity, icon));
			activity.sendBroadcast(intent);
			return;
		}
	}

	public static void DeletDeskIcon(Activity activity, String appname) {
		Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
		intent.putExtra("android.intent.extra.shortcut.NAME", appname);
		String action = "android.intent.action.MAIN";
		String appClass = (new StringBuilder(String.valueOf(activity.getPackageName()))).append(".").append(activity.getLocalClassName()).toString();
		ComponentName comp = new ComponentName(activity.getPackageName(), appClass);
		intent.putExtra("android.intent.extra.shortcut.INTENT", (new Intent(action)).setComponent(comp));
		activity.sendBroadcast(intent);
	}

	public static boolean hasShortCut(Activity activity, String appname) {
		ContentResolver resolver = activity.getContentResolver();
		Uri CONTENT_URI;
		if (android.os.Build.VERSION.SDK_INT < 8)
			CONTENT_URI = Uri.parse("content://com.android.launcher.settings/favorites?notify=true");
		else
			CONTENT_URI = Uri.parse("content://com.android.launcher2.settings/favorites?notify=true");
		String PROJECTION[] = {"_id", "title", "iconResource"};
		Cursor cursor = resolver.query(CONTENT_URI, PROJECTION, "title=?", new String[]{appname}, null);
		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		} else {
			return false;
		}
	}

	public static void openFile(Activity activity, String fileName) {
		Intent intent = new Intent();
		intent.addFlags(0x10000000);
		intent.setAction("android.intent.action.VIEW");
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(Uri.fromFile(new File(fileName)), type);
		activity.startActivity(intent);
	}

	public static String getFileName(HttpURLConnection conn) {
		String filename = conn.getURL().toString().substring(conn.getURL().toString().lastIndexOf('/') + 1);
		if (filename == null || "".equals(filename.trim())) {
			int i = 0;
			do {
				String mine = conn.getHeaderField(i);
				if (mine == null)
					break;
				if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())) {
					Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
					if (m.find())
						return m.group(1);
				}
				i++;
			} while (true);
		}
		return filename;
	}

	public static InputStream getStreamFromURL(String path) throws Exception {
		InputStream inputStream = null;
		URL url = new URL(path);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(6 * 1000);
		if (conn.getResponseCode() == 200) {
			inputStream = conn.getInputStream();
		}
		return inputStream;
	}

	/**
	 * 获取MAC地址,
	 * 
	 * @note <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
	 */
	public static String getMacAddress(Activity activity) {
		WifiManager wifi = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	/**
	 * 获取唯一设备码
	 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
	 * @param activity
	 * @return
	 */
//	public static String getDeviceID(Activity activity) {
//		final TelephonyManager tm = (TelephonyManager) activity.getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
//		final String tmDevice, tmSerial, androidId;
//		tmDevice = "" + tm.getDeviceId();
//		tmSerial = "" + tm.getSimSerialNumber();
//		androidId = "" + android.provider.Settings.Secure.getString(activity.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//		UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//		return deviceUuid.toString();
//	}

}
