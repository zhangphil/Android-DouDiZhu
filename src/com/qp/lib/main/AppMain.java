package com.qp.lib.main;

import android.app.ActivityGroup;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
 
import com.qp.lib.cmd.Bank.CMD_GR_C_TransferScoreRequest;
import com.qp.lib.control.AppOption;
 
 
import com.qp.lib.interface_ex.ILoadingInfo;
import com.qp.lib.interface_ex.bank.IBank;
import com.qp.lib.interface_ex.kernel.IKernel;
import com.qp.lib.interface_ex.kernel.IKernelControl;
import com.qp.lib.interface_ex.net.ISocketMission;
import com.qp.lib.interface_ex.option.IOption;
import com.qp.lib.interface_ex.option.IOptionControl;
import com.qp.lib.utility.SerialRecord;
import com.qp.lib.utility.Util;
//
//import com.nd.commplatform.NdCallbackListener;
//import com.nd.commplatform.NdCommplatform;
//import com.nd.commplatform.entry.NdAppInfo;

public abstract class AppMain extends ActivityGroup {

	public class AppConfigAsyncTask extends AsyncTask<String, Integer, String> {
		public static final String	TAG	= "AppConfigAsyncTask";
		private TextView			textView;
		private ProgressBar			progressBar;
		private String				LOAD_TAG;

		public void setLoadTag(String szinfo) {
			LOAD_TAG = szinfo;
		}

		public String getLoadTag() {
			return LOAD_TAG;
		}

		public int getCurrentPro() {
			if (progressBar != null)
				return progressBar.getProgress();
			else
				return 0;
		}

		protected String doInBackground(String... params) {
			setLoadTag("读取配置...");
			onInitOption();
			publishProgress(new Integer[]{Integer.valueOf(20)});
			try {
				Thread.sleep(200L);
			} catch (Exception e) {
				e.printStackTrace();
			}
			setLoadTag("配置内核...");
		//	onInitKernel();
			publishProgress(new Integer[]{Integer.valueOf(40)});
			try {
				Thread.sleep(200L);
			} catch (Exception e) {
				e.printStackTrace();
			}
			setLoadTag("加载控件...");
	 
			publishProgress(new Integer[]{Integer.valueOf(60)});
			try {
				Thread.sleep(200L);
			} catch (Exception e) {
				e.printStackTrace();
			}
			int nprogress = onAsyncTaskLoad(60);
			publishProgress(new Integer[]{Integer.valueOf(nprogress)});
			return "";
		}

		protected void onPostExecute(String result) {
			if (textView != null)
				textView.setText("加载xx控件...");
			if (progressBar != null)
				progressBar.setProgress(progressBar.getProgress() + 10);
			
			onConfigComplete(textView, progressBar);
		/*	NdCommplatform nd91 = NdCommplatform.getInstance();
			if (Util.DEBUG_)
				nd91.ndSetDebugMode(0);
			NdAppInfo appInfo = new NdAppInfo();
			appInfo.setCtx(AppMain.this);
			appInfo.setAppId(ID_91);
			appInfo.setAppKey(KEY_91);
			NdCommplatform.getInstance().initial(0, appInfo);
			int orient = 1;
			nd91.ndSetScreenOrientation(orient);
			if (textView != null)
				textView.setText("检测更新...");
			if (progressBar != null)
				progressBar.setProgress(80);
			nd91.ndAppVersionUpdate(AppMain.this, new NdCallbackListener<Integer>() {

				public void callback(int responseCode, Integer t) {
					if (responseCode == 0) {
						switch (t.intValue()) {
							case 0 :
							case 1 :
							case 3 :
							case 4 :
								onConfigComplete(textView, progressBar);
								break;

							case 5 :
							case 6 :
								Toast.makeText(AppMain.this, "网络异常或者服务端出错", 1).show();
								finish();
								break;

							case 2 :
							default :
								Util.d("AppConfigAsyncTask", (new StringBuilder("91检测更新:")).append(t).toString());
								break;
						}
					} else {
						Toast.makeText(AppMain.this, "网络异常或者服务端出错", 1).show();
						finish();
					}
				}

			});*/
		}

		protected void onPreExecute() {
			if (textView != null)
				textView.setText(LOAD_TAG);
		}

		protected void onProgressUpdate(Integer... values) {
			int vlaue = values[0].intValue();
			progressBar.setProgress(vlaue);
			if (textView != null)
				textView.setText(LOAD_TAG);
		}

		protected void onCancelled() {
			super.onCancelled();
		}

		public AppConfigAsyncTask(TextView textView, ProgressBar progressBar) {

			LOAD_TAG = "加载中...";
			this.textView = textView;
			this.progressBar = progressBar;
		}

	}

	protected class RevHandler extends Handler {

		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1 : // '\001'
				 

				case 2 : // '\002'
					m_MainHandler.removeMessages(1);
					onNetError(msg.arg1, msg.arg2, msg.obj);
					return;

				case 3 : // '\003'
					 	return;
			}
			UI_HandleMessage(msg);
		}

	}

	public static final String		TAG				= "AppMain";

	protected static AppMain		instance;
	protected static IOptionControl	m_Option;
//	protected static IKernelControl	m_Kernel;
 
	protected RevHandler			m_MainHandler;
 
	protected static boolean		m_bConfigComplete;
	protected AppConfigAsyncTask	m_ConfigThread;
	protected static ILoadingInfo	m_LoadingInfo;
 
 
	protected static ProgressDialog	m_ProgressDialog;

	private int						m_nRecordSound	= 0;
	private int						m_nRecordMusic	= 0;

	public AppMain() {
	}

	public String getFaceCheckSum(String uin) {
		SharedPreferences record = getSharedPreferences("facesum", Context.MODE_PRIVATE);
		String szchecksum = record.getString(uin, "");
		Util.e(TAG, "getFaceCheckSum:" + szchecksum + "#" + uin);
		return szchecksum;
	}

	public boolean setFaceCheckSum(String uin, String szchecksum) {
		SharedPreferences record = getSharedPreferences("facesum", Context.MODE_PRIVATE);
		if (record != null) {
			android.content.SharedPreferences.Editor editor = record.edit();
			if (editor != null) {
				editor.clear();
				Util.e(TAG, "setFaceCheckSum:" + szchecksum + "#" + uin);
				editor.putString(uin, szchecksum);
				editor.commit();
				return true;
			}
		}
		return false;
	}
	public static void setLoadingInfo(ILoadingInfo loading) {
		m_LoadingInfo = loading;
	}

	public static ILoadingInfo getLoadingInfo() {
		return m_LoadingInfo;
	}

	public void onConfigComplete(TextView textView, ProgressBar progressBar) {
		if (textView != null)
			textView.setText("配置完毕...");
		if (progressBar != null)
			progressBar.setProgress(95);
		on91LoginComplete(0);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,  WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		onInitDisplay();
		InitHandler();
		instance = this;
		onStartApp();
	}

	public abstract int onAsyncTaskLoad(int i);

	public abstract void onShowScene(int i);

	public abstract void on91LoginComplete(int i);

	public void onStartConfigAsyncTask(TextView textView, ProgressBar progressBar) {
		m_ConfigThread = new AppConfigAsyncTask(textView, progressBar);
		m_ConfigThread.execute("");
	}

	protected void onStart() {
		super.onStart();
	}

	protected void onRestart() {
		super.onRestart();
	}

	protected void onResume() {
		Util.w("AppMain", "*******************onresume*********************");

		super.onResume();
		if (m_Option != null) {
			if (m_nRecordMusic == 2 || m_nRecordMusic == 1)
				m_Option.setMusic(m_nRecordMusic == 2);
			if (m_nRecordSound == 2 || m_nRecordSound == 1)
				m_Option.setSound(m_nRecordSound == 2);
		}
	}
	protected void onPause() {
		if (m_Option != null) {
			m_nRecordMusic = m_Option.getMusic() ? 2 : 1;
			m_nRecordSound = m_Option.getSound() ? 2 : 1;
			m_Option.setMusic(false);
			m_Option.setSound(false);
		}
		super.onPause();
	}

	public void onDestroy() {
		if (m_Option != null)
			m_Option.onSaveConfig(this);
		 
		SerialRecord.onSaveSerial();
		Util.d("AppMain", "app-ondestroy");
		super.onDestroy();
	}

	public void onExit() {
		if (m_Option != null)
			m_Option.onSaveConfig(this);
	 
		SerialRecord.onSaveSerial();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
 

	public abstract void onStartApp();

	public void onInitOption() {
		m_Option = new AppOption();
		m_Option.onInitConfig(this);
		SerialRecord.onLoadSerial();
	}

	public void onInitDisplay() {
	}

	private void InitHandler() {
		m_MainHandler = new RevHandler();
	}

	public static IOption getOption() {
		return m_Option;
	}

	public static IOptionControl getOptionControl() {
		return m_Option;
	}

 

	public static int getWidth() {
		return m_Option.getWidth();
	}

	public static int getHeight() {
		return m_Option.getHeight();
	}

	public static boolean isConfigComplete() {
		return m_bConfigComplete;
	}

	public abstract void onNetError(int i, int j, Object obj);

	public abstract void UI_HandleMessage(Message message);

	public static boolean sendUIMessage(int what, int arg1, int arg2, Object obj) {
		Message msg = instance.m_MainHandler.obtainMessage(what, arg1, arg2, obj);
		return instance.m_MainHandler.sendMessage(msg);
	}

	public static boolean sendUIMessageDelay(int what, int arg1, int arg2, Object obj, long delayMillis) {
		Message msg = instance.m_MainHandler.obtainMessage(what, arg1, arg2, obj);
		return instance.m_MainHandler.sendMessageDelayed(msg, delayMillis);
	}

	public static AppMain getInstance() {
		return instance;
	}

	public static void OnQueryTransfer(final CMD_GR_C_TransferScoreRequest cmd) {
		String gamename = "";
		gamename = m_Option.getAppName();
		String Msg = "";
		Msg = (new StringBuilder(String.valueOf(Msg))).append(cmd.cbByNickName != 1 ? "ID：" : "昵称：").append(cmd.szNickName).append("\n").append("金额：")
				.append(cmd.lTransferScore).toString();
		DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				//AppMain.m_Kernel.sendSocketData(5, 4, cmd);
			}
		};
		DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialoginterface, int i) {
			}

		};
		Dialog dialog = (new android.app.AlertDialog.Builder(instance)).setTitle(gamename).setMessage(Msg).setPositiveButton("确定", ok)
				.setNegativeButton("取消", cancle).create();
		dialog.show();
	}

	 

	public abstract void onQueryUpdate(boolean flag);

	public static void onShowDialog(CharSequence title, CharSequence message) {
		dismissDialog();
		m_ProgressDialog = ProgressDialog.show(instance, title, message);
	}

	public static void onShowDialog(CharSequence title, CharSequence message, boolean indeterminate) {
		dismissDialog();
		m_ProgressDialog = ProgressDialog.show(instance, title, message, indeterminate);
	}

	public static void onShowDialog(CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
		dismissDialog();
		m_ProgressDialog = ProgressDialog.show(instance, title, message, indeterminate, cancelable);
	}

	public static void onShowDialog(CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable,
			android.content.DialogInterface.OnCancelListener cancelListener) {
		dismissDialog();
		m_ProgressDialog = ProgressDialog.show(instance, title, message, indeterminate, cancelable, cancelListener);
	}

	public static void dismissDialog() {
		if (m_ProgressDialog != null)
			m_ProgressDialog.dismiss();
		m_ProgressDialog = null;
	}
}
