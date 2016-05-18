package com.qp.ddz.utility;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

import com.qp.lib.help.SDCardHelp;
import com.qp.lib.utility.Util;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class HttpDownloadAsyncTask extends AsyncTask<String, Integer, String> {
	private static final String	TAG			= "HttpDownloadAsyncTask";
	public String					m_url;
	public String 					m_savepath;
	

	public HttpDownloadAsyncTask(String u  ,String save) {
		m_url=u;
		m_savepath=save;
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		
		int po=m_url.lastIndexOf("/");
		String filename="";
		if( po!= -1){
			  filename =m_url.substring(po+1);
		}
		
		try {
			InputStream is = getImageStream(m_url);
			byte data[]=readStream(is);
			SDCardHelp.SaveFile(data,data.length, m_savepath , filename );
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Util.e(TAG, "下载["+m_url+"]失败!");
			
			
		}
		
		/*String downURL = "http://" + GDF.UrlLogin + "/" + "CustomFace.aspx?UserID=" + lUserID + "&CustomID=" + lCustomID;
		Util.i(TAG, "头像downURL:" + downURL);
		InputStream bitmapIs = null;
		Bitmap bitmap = null;
		try {
			bitmapIs = Util.getStreamFromURL(downURL);
			if (bitmapIs != null) {
				byte[] buffer = new byte[9216];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = bitmapIs.read(temp)) > 0) {
					System.arraycopy(temp, 0, buffer, destPos, readLen);
					destPos += readLen;
				}
				if (destPos == data_size) {
					int[] nARGB = changebyte(buffer);
					bitmap = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888);
					for (int i = 0; i < 48; i++) {
						for (int j = 0; j < 48; j++) {
							bitmap.setPixel(j, i, nARGB[i * 48 + j]);
						}
					}

					if (CustomFaceManage.getInstance() != null) {
						CustomFaceManage.getInstance().onAddBitmap(lUserID + "-" + lCustomID, bitmap);
					}
					SDCardHelp.SaveImage(bitmap, GDF.GAME_NAME + "/" + lUserID, lCustomID + ".png");
					if (GameClientActivity.getInstance() != null) {
						GameClientActivity.getInstance().m_ClockHandler.obtainMessage(GameClientActivity.IDI_UPDATEVIEW).sendToTarget();
					}
					Util.i(TAG, "创建成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bitmapIs != null)
					bitmapIs.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				bitmapIs = null;
			}
		}*/
		

		 
		return null;
	}
	 /** 
	68.     * Get data from stream 
	69.     * @param inStream 
	70.     * @return byte[] 
	71.     * @throws Exception 
	72.     */  
	    public static byte[] readStream(InputStream inStream) throws Exception{  
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] buffer = new byte[1024];  
	        int len = 0;  
	        while( (len=inStream.read(buffer)) != -1){  
	            outStream.write(buffer, 0, len);  
	        }  
	        outStream.close();  
	        inStream.close();  
	        return outStream.toByteArray();  
	    }  

	 /** 
	52.     * Get image from newwork 
	53.     * @param path The path of image 
	54.     * @return InputStream 
	55.     * @throws Exception 
	56.     */  
	    public InputStream getImageStream(String path) throws Exception{  
	        URL url = new URL(path);  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5 * 1000);  
	        conn.setRequestMethod("GET");  
	        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
	            return conn.getInputStream();  
	        }  
	        return null;  
	   }  
 

	@Override
	protected void onPostExecute(String s) {
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onProgressUpdate(Integer... ainteger) {
	}

	@Override
	protected void onCancelled() {
	}

	 

}
