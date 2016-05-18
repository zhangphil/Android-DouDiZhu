package com.qp.lib.utility;

import android.os.AsyncTask;
import java.io.*;
import java.net.*;

import com.qp.lib.help.SDCardHelp;
import com.qp.lib.interface_ex.IDownLoadProgress;

public class CDownLoadAsynctask extends AsyncTask<String, Integer, Integer> {

	@SuppressWarnings("unused")
	private static final String	TAG	= "CDownLoad";
	private int					nTimeoutConnection;
	private boolean				isStop;
	private IDownLoadProgress	downLoadProgress;
	FileOutputStream			fostream;
	HttpURLConnection			httpConnection;
	URL							url;
	InputStream					inputStream;
	File						SaveFile;

	public CDownLoadAsynctask(IDownLoadProgress downLoadProgress, int timeoutConnection) {
		nTimeoutConnection = 5000;
		isStop = false;
		this.downLoadProgress = null;
		fostream = null;
		httpConnection = null;
		url = null;
		inputStream = null;
		SaveFile = null;
		this.downLoadProgress = downLoadProgress;
		if (timeoutConnection > 1000)
			nTimeoutConnection = timeoutConnection;
	}

	protected Integer doInBackground(String... params) {
		String szurl;
		String szCheckType;
		String szSDFilePath;
		szurl = params[0];
		String szFilePath = params[1];
		szCheckType = params[2];
		szSDFilePath = SDCardHelp.MakeFilePahtToSDCard(szFilePath);
		if (szFilePath.equals("")) {
			Util.e("CDownLoad", "SDCARD创建文件夹失败");
			return 1;
		}
		try {
			url = new URL(szurl);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setConnectTimeout(nTimeoutConnection);
			inputStream = httpConnection.getInputStream();
			if (inputStream == null) {
				Util.e("CDownLoad", "输入流创建失败");
				return 1;
			}
			httpConnection.connect();
			int responseCode = httpConnection.getResponseCode();
			if (responseCode > 1000) {
				Util.e("CDownLoad", "创建链接超时");
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		long lTotalLenght;
		long lCurrentLenght;
		lTotalLenght = httpConnection.getContentLength();
		Util.d("CDownLoad", (new StringBuilder("下载文件长度-")).append(lTotalLenght).toString());
		if (lTotalLenght <= 0L) {
			Util.e("CDownLoad", "获取文件长度失败");
			return 1;
		}
		String szFileName = Util.getFileName(httpConnection);
		Util.d("CDownLoad", (new StringBuilder("下载文件名-")).append(szFileName).toString());
		if (szFileName == null || szFileName.equals("")) {
			Util.e("CDownLoad", "获取下载文件名失败");
			return 1;
		}
		if (szCheckType != null && !szCheckType.equals("")) {
			String end = szFileName.substring(szFileName.lastIndexOf(".") + 1, szFileName.length()).toLowerCase();
			if (end == null || !end.equals(szCheckType)) {
				Util.e("CDownLoad", "获取下载文件类型不符合");
				return 1;
			}
		}
		String szSaveFullPath = (new StringBuilder(String.valueOf(szSDFilePath))).append("/").append(szFileName).toString();
		SaveFile = new File(szSaveFullPath);
		try {
			fostream = new FileOutputStream(SaveFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Util.e("CDownLoad", "创建保存文件失败");
			return 1;
		}
		lCurrentLenght = 0L;

		while (!isStop && inputStream != null && fostream != null) {
			byte buffer[];
			buffer = new byte[1024];
			int numRead = 0;
			try {
				numRead = inputStream.read(buffer);
				if (numRead <= 0)
					break;
				fostream.write(buffer, 0, numRead);
			} catch (IOException e) {
				e.printStackTrace();
				Util.e("CDownLoad", "链接中断");
				return Integer.valueOf(1);
			}

			if (lTotalLenght != 0L) {
				int npercent = (int) ((lCurrentLenght * 100L) / lTotalLenght);
				lTotalLenght += numRead;
				publishProgress(npercent);
			}
		}

		return Integer.valueOf(lCurrentLenght != lTotalLenght ? 1 : 0);
	}
	protected void onPostExecute(Integer nresult) {
		StopDownLoad();
		if (downLoadProgress != null)
			downLoadProgress.onDownLoadCompelte(nresult.intValue() == 0, nresult.intValue(), "Completed.");
	}

	protected void onPreExecute() {
	}

	protected void onProgressUpdate(Integer... values) {
		if (downLoadProgress != null)
			downLoadProgress.onUpdate(values[0].intValue(), (new StringBuilder()).append(values[0]).append("%").toString());
	}

	protected void onCancelled() {
		StopDownLoad();
	}

	public void StopDownLoad() {
		isStop = true;
		if (httpConnection != null)
			httpConnection.disconnect();
		try {
			if (fostream != null)
				fostream.close();
			if (inputStream != null)
				inputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		if (SaveFile != null && !SaveFile.exists())
			SaveFile.delete();
		SaveFile = null;
		httpConnection = null;
		fostream = null;
		inputStream = null;

	}
}
