package com.smw.net;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;

import com.qp.ddz.GameActivity;
import com.qp.ddz.utility.HttpDownloadAsyncTask;
import com.qp.lib.help.SDCardHelp;
import com.qp.lib.utility.NetHelp;
import com.qp.lib.utility.Util;
import com.qp.ddz.define.GDF;
/**
*
* 购买完整源码联系 q344717871
* 
*/


public class DynamicUI {

	public static String DYNAMIC_UI_PATH="/ui/";
	
	// 加载配置 设置bg layoutID为最外面的一个层id 非layout的id
	static public void LoadBG(String TAG, Activity ac, int layoutID) {
		Util.i(TAG, "== LoadBG ===");

		LinearLayout layout = (LinearLayout) ac.findViewById(layoutID);// 需要设置linearlayout的id为layout
		if (layout == null) {
			Util.e(TAG, "LoadBG 加载不到!!");
			return;
		}
		// get
		SharedPreferences sp = ac.getSharedPreferences("shared_file", 0);
		boolean on = sp.getBoolean(TAG, false);
		String url = sp.getString(TAG + "url", "");

		if (on) {
			Util.i(TAG, "== On ===");
			// String test="http://www.qq.com/abcd/jk.jpg";
			int po = url.lastIndexOf("/");
			if (po != -1) {
				String filename = url.substring(po + 1);

				Bitmap bm = SDCardHelp.LoadImage(GDF.GAME_SD_PATH +  DYNAMIC_UI_PATH,
						filename);
				// byte st[]=SDCardHelp.LoadFile(GDF.GAME_SD_PATH+"/ui/",
				// filename);
				// 转bmp
				// Bitmap bm =BitmapFactory.decodeByteArray( st , 0, st.length
				// );
				if (bm != null) {

					layout.setBackgroundDrawable(new BitmapDrawable(bm));

					// /if(!bm.isRecycled()) bm.recycle();

					Util.i(TAG, "== sd卡加载图片 ===" + filename);
					return;
				} else {

					Util.i(TAG, "== sd卡加载图片失败 ===" + filename);
					return;
				}
			} else {
				Util.i(TAG, "== url err ===" + url);
			}

		} else {
			Util.i(TAG, "== Off ===");

		}

		System.gc();
		System.out.println(Runtime.getRuntime().totalMemory());
	}

	static public byte[] LoadBG2(String TAG, Activity ac) {
		Util.i(TAG, "== LoadBG2 ===");

		// get
		SharedPreferences sp = ac.getSharedPreferences("shared_file", 0);
		boolean on = sp.getBoolean(TAG, false);
		String url = sp.getString(TAG + "url", "");

		if (!on) {
			Util.i(TAG, "== Off ===");
			return null;
		}

		Util.i(TAG, "== On ===");
		// String test="http://www.qq.com/abcd/jk.jpg";
		int po = url.lastIndexOf("/");
		if (po != -1) {
			String filename = url.substring(po + 1);

//			Bitmap bm = SDCardHelp.LoadImage(GDF.GAME_SD_PATH + "/ui/",
//					filename);
			 byte st[]=SDCardHelp.LoadFile(GDF.GAME_SD_PATH+DYNAMIC_UI_PATH, filename);
			// 转bmp
			// Bitmap bm =BitmapFactory.decodeByteArray( st , 0, st.length );
			if (st != null) {

				// /if(!bm.isRecycled()) bm.recycle();

				Util.i(TAG, "== sd卡加载图片 ===" + filename);
				return st;
			} else {

				Util.i(TAG, "== sd卡加载图片失败 ===" + filename);
				return null;
			}
		} else {
			Util.i(TAG, "== url err ===" + url);
			return null;
		}

	}

	static public void SaveBG(Activity ac, String TAG, boolean on, String url) {
		SharedPreferences sp = ac.getSharedPreferences("shared_file", 0);
		sp.edit().putBoolean(TAG, on).commit();
		
		//删除原来的资源
//		String oulurl = sp.getString(TAG + "url", "");
//		int po = oulurl.lastIndexOf("/");
//		if (po != -1) {
//			String filename = url.substring(po + 1);//有越界!
//			SDCardHelp.rmFile(GDF.GAME_SD_PATH+DYNAMIC_UI_PATH+filename);
//		}
		 
		//保存新资源
		sp.edit().putString(TAG + "url", url).commit();

		if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) {
			// url截出 文件名
			int  po = url.lastIndexOf("/");
			if (po != -1) {
				String filename = url.substring(po + 1);

				// 构造sdk卡+程序+ 路径
				String p = SDCardHelp.MakeFilePahtToSDCard(GDF.GAME_SD_PATH  +  DYNAMIC_UI_PATH );
				p = p + "/" + filename;

				// 已存在了 就不用再下了！
				if (SDCardHelp.fileExists(p)) {
					Util.i(TAG, "已存在了 就不用再下了！" + p);
					return;
				}

				// 只在wifi时下载.
				if (NetHelp.NetIsWifi(ac)) {

					// 尝试下载
					HttpDownloadAsyncTask task = new HttpDownloadAsyncTask(url,
							GDF.GAME_SD_PATH+ DYNAMIC_UI_PATH );  
					task.execute("");
				}

			}

		}

	}

	static public void onDestroy(Activity ac, int layoutID) {
		LinearLayout layout = (LinearLayout) ac.findViewById(layoutID);
		// 把历史的ImageView 图片对象（image_)释放
		BitmapDrawable bitmapDrawable = (BitmapDrawable) layout.getBackground();
		if (bitmapDrawable != null) {
			Bitmap hisBitmap = bitmapDrawable.getBitmap();

			if (hisBitmap!=null && !hisBitmap.isRecycled()  ) {
				//  hisBitmap.recycle();
				  hisBitmap = null;
			} 
			 
		}
	}

	static public void LoadBGM(String TAG){
			 
			
		// get bgm config
					SharedPreferences sp = GameActivity.getInstance().getSharedPreferences("shared_file", 0);
					boolean on = sp.getBoolean(TAG, false);
					String url = sp.getString(TAG+"url", "");

					 
					if (on)
					{
						Util.i(TAG, "== "+TAG+" On ===");
						// String test="http://www.qq.com/abcd/jk.jpg";
						int po = url.lastIndexOf("/");
						if (po != -1) {
							String filename = url.substring(po + 1);
							
							//no sd card
							 if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
							 {
								 Util.e(TAG," no sd card!!!");
								  
							 }
							String p=SDCardHelp.getSdCardPath()+"/"+GDF.GAME_SD_PATH +DynamicUI.DYNAMIC_UI_PATH;
							if(p.length()>1){
								Util.e(TAG,"   sd card!!! " + p );
								if(GameActivity.getOptionControl()!=null) GameActivity.getOptionControl().PlayBackGroundMusic(p +filename, true);
							}
							
							 
						}
					}
					else{
						if(GameActivity.getOptionControl()!=null) GameActivity.getOptionControl().StopBackGroundMusic() ;//PlayBackGroundMusic(R.raw.lgbg, true);
					}
	}
	
}
