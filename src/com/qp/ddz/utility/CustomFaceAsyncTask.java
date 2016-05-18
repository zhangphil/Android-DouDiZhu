package com.qp.ddz.utility;

import android.os.AsyncTask;

import com.qp.ddz.GameActivity;
import com.qp.lib.cmd.CMD;
import com.qp.lib.utility.NetEncoding;
import com.smw.cmd.plazz.MSG_C2S_GET_IMAGE;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class CustomFaceAsyncTask extends AsyncTask<String, Integer, String> {
	private static final String	TAG			= "CustomFaceAsyncTask";
	public int					m_imgchecksum;
 
 

	public CustomFaceAsyncTask(int imgchecksum) {
		m_imgchecksum=imgchecksum;
	}
	@Override
	protected String doInBackground(String... arg0) {
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
		

		MSG_C2S_GET_IMAGE i=new MSG_C2S_GET_IMAGE();
		i.cmd=CMD.C2S_GET_IMAGE; 
		i.img_checksum=m_imgchecksum;
		GameActivity.getGameActivityInstance().m_loginclient.send(i);
		
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

	/**
	 * 
	 * @param data
	 * @return
	 */
	public static int[] changebyte(byte data[]) {
		int[] ndata = null;
		if (data != null && data.length == 9216) {
			ndata = new int[2304];
			for (int i = 0; i < ndata.length; i++) {
				ndata[i] = NetEncoding.read4Byte(data, i * 4);
				ndata[i] |= 0xff000000;
			}
		}
		return ndata;
	}

}
