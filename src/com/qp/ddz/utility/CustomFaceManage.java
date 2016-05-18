package com.qp.ddz.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.qp.ddz.define.GDF;
import com.qp.lib.help.SDCardHelp;
import com.qp.lib.utility.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class CustomFaceManage {

	public static final String	TAG			= "CustomFaceManage";

	HashMap<String, Bitmap>		imageCache	= null;
	boolean						bSave		= false;

	static CustomFaceManage		instance;

	public CustomFaceManage() {
		imageCache = new HashMap<String, Bitmap>();
		instance = this;
	}

	public static CustomFaceManage getInstance() {
		return instance;
	}

	public void Init() {
		imageCache.clear();
		bSave = true;
	}

//	public void onRelease() {
//		//bSave = false;
//		//imageCache.clear();
//	}

	public boolean onAddBitmap(String key, Bitmap map) {
		if (bSave) {
			Util.i(TAG, "添加bitmap[" + key + "]" + map);
			imageCache.put(key, map);
		}
		return bSave;
	}

	private String SDread(String FILE) { 
        // 如果手机插入了SD卡，而且应用程序具有访问SD的权限 
		//  String path = "/sdcard/image2.jpg";  
          //通过BitmapFactory获得Bitmap实例  
       //   Bitmap bm = BitmapFactory.decodeFile(path);  
		 //BitmapFactory.decodeByteArray(data, offset, length);
        try { 
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { 
                // 获取SD卡的目录 
                File sdDirFile = Environment.getExternalStorageDirectory(); 
                //获取指定文件对应的输入流 
                FileInputStream fis = new FileInputStream(sdDirFile.getCanonicalPath()+ FILE); 
                //将指定输入流包装成BufferedReader 
                BufferedReader br = new BufferedReader(new InputStreamReader(fis)); 
                 
                StringBuilder sb = new StringBuilder(""); 
                String line = null; 
                while ((line =br.readLine())!=null) { 
                    sb.append(line); 
                } 
                return sb.toString(); 
            } 
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return null; 
    } 

	public Bitmap getBitmap(String hex,int checksum)//,final long userid, final long customid)
	{
		Bitmap bitmap = null;
		if (imageCache.containsKey(hex) ) //userid + "-" + customid)) 
		{
			bitmap = imageCache.get(hex);
			//Util.i(TAG, "CustomFaceManage-查找Key成功"+hex);//" + userid + "-" + customid);
		} else {
			Util.i(TAG, "CustomFaceManage-查找Key失败"+hex + " ] :"+imageCache.size());// + userid + "-" + customid);
			
			//先尝试sd卡加载! 
//			byte img[]=SDCardHelp.LoadFile(   GDF.GAME_SD_PATH+"/userimg" , Integer.toHexString( checksum)+ ".png");
//			if(img!=null && img.length>0){
//				//转bmp
//				Bitmap bm =BitmapFactory.decodeByteArray(img, 0, img.length );
//				
			
			bitmap = SDCardHelp.LoadImage(   GDF.GAME_SD_PATH+"/userimg" ,Integer.toHexString(checksum)+ ".png");
//			
			
			if(bitmap!=null){	
				onAddBitmap(hex,bitmap);
			}else{
				CustomFaceAsyncTask task = new CustomFaceAsyncTask(checksum);//userid, customid);
				task.execute("");
			}
			
			 
		}
		return bitmap;
	}

}
