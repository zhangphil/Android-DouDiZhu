package com.qp.lib.help;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import com.qp.lib.utility.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class SDCardHelp
{

	public static String		TAG					= "SDCardHelp";
	public SDCardHelp()
	{
	}

	public static String getSdCardPath()
	{
		String state = Environment.getExternalStorageState();
		if ("mounted".equals(state) && Environment.getExternalStorageDirectory().canWrite())
			return Environment.getExternalStorageDirectory().getPath();
		else
			return null;
	}

	//path前加入sd卡路径 顺便如果没文件夹 就建立个
	public static String MakeFilePahtToSDCard(String path)
	{
		String szFullPath = (new StringBuilder(String.valueOf(getSdCardPath()))).append("/").append(path).toString();
		File tmpFile = new File(szFullPath);
		if (!tmpFile.exists() && !tmpFile.mkdirs())
			return "";
		else
			return szFullPath;
	}

	public static long getSdCardSurplusSize(String filePath)
	{
		StatFs statFs = new StatFs(filePath);
		long blocSize = statFs.getBlockSize();
		long availaBlock = statFs.getAvailableBlocks();
		long SurplusSize = availaBlock * blocSize;
		return SurplusSize;
	}
	
	private static boolean SDwrite(String FILE,byte b[],int len){ 
        try { 
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            { 
                File sdDir = Environment.getExternalStorageDirectory(); 
                File targetFile = new File(FILE  );//sdDir.getCanonicalPath()+ FILE); 
                RandomAccessFile raf = new RandomAccessFile(targetFile, "rw"); 
                raf.seek(targetFile.length()); 
                raf.write(b, 0, len); 
                raf.close(); 
                return true;
            } 
        } catch (Exception e) { 
        	e.printStackTrace();
        } 
        return false;
    } 
	private static boolean SDread(String FILE,byte dst[],int dstlen){ 
        try { 
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            { 
                File sdDir = Environment.getExternalStorageDirectory(); 
             //   File targetFile = new File(FILE  );//sdDir.getCanonicalPath()+ FILE); 
                File targetFile = new File(FILE  );//sdDir.getCanonicalPath()+ FILE); 
                RandomAccessFile raf = new RandomAccessFile(targetFile, "rw"); 
                //raf.seek(targetFile.length()); 
                raf.read(dst, 0, dstlen);
                raf.close(); 
                
                return true;
            } 
        } catch (Exception e) { 
        	e.printStackTrace();
        } 
        return false;
    } 
	 public static final boolean rmFile(String filePath) {
	        File f = new File(filePath);
	        if(f.exists()) {
	            if(!f.delete()) {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	  public static final boolean fileExists(String s)
	  {
	        return (new File(s)).exists();
	  }
	   public static  int   getFileSize(String filePath) {
	        File f = new File(filePath);
	        if(!f.exists()) {
	            return 0;
	        }
	        return  (int)f.length() ;
		     
	    }
	    
	public static boolean SaveFile(byte img[],int size, String path,String filename) {
		Util.i(TAG,"SaveFile path:["+path+"]  ["+filename+"]");
		//no sd card
		 if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		 {
			 Util.e(TAG,"SaveFile  un MEDIA_MOUNTED!!!");
			 return false;
		 }
		String p=MakeFilePahtToSDCard(path);
		if(p.equals("")) return false;
		p=p+ "/"+filename;
		Util.i(TAG,"SaveFile  "+p);
		if(fileExists(p)){
			rmFile(p);//存在就删除！
			Util.i(TAG,"SaveFile  存在就删除！ " );
		}
		
		return SDwrite(p,img,size);
		
	}
	public static Bitmap LoadImage(  String path,String filename  ) {
		Util.i(TAG,"LoadImage path:["+path+"]  ["+filename+"]");
			//no sd card
			 if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			 {
				 Util.e(TAG,"LoadImage  un MEDIA_MOUNTED!!!");
				 return null;
			 }
			 String p=MakeFilePahtToSDCard(path);
				if(p.equals("")) return null;
				p=p+ "/"+filename;
				 
//	                File targetFile = new File(FILE  );//sdDir.getCanonicalPath()+ FILE); 
//	                RandomAccessFile raf = new RandomAccessFile(targetFile, "rw"); 
//	                raf.seek(targetFile.length()); 
//	                raf.write(b, 0, len); 
	                FileInputStream fis;
					try {
						fis = new FileInputStream(p);
						
					  
						
						BitmapFactory.Options opts = new BitmapFactory.Options();
						opts.inSampleSize = 1;
						opts.inPurgeable=true;//设为True的话表示使用BitmapFactory创建的Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收，
						opts.inPreferredConfig = Bitmap.Config.ARGB_4444;//编码 节约内存
						
						Bitmap bitmap=BitmapFactory.decodeStream(fis, null, opts );
						
						Util.i(TAG,"LoadImage ok");
						return bitmap;
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 return null;
	}
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

	public static byte[] LoadFile(  String path,String filename  ) {
		 Util.i(TAG,"LoadFile path:["+path+"]  ["+filename+"]");
		//no sd card
		 if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		 {
			 Util.e(TAG,"LoadFile  un MEDIA_MOUNTED!!!");
			 return null;
		 }
		String p=MakeFilePahtToSDCard(path);
		if(p.equals("")) return null;
		p=p+ "/"+filename;
		
		  FileInputStream fis=null;
			
		  try {
			fis = new FileInputStream(p);
			return readStream(fis);
		}   catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
			
//			
//		int len=  getFileSize(p) ;
//		if(len<=0){
//			Log.e(TAG,"getFileSize  err  !!  " + len);
//			return null;
//		}
//		byte img[]=new byte[len];
//		if( SDread(p,img,len)){
//			return img;
//		}
//		
		//return null;
		
	}
	public static void SaveImage(Bitmap bitmap, String path,String filename) {
		// TODO Auto-generated method stub
		
	}
}
