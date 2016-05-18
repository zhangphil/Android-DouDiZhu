package com.qp.ddz.scene.popupwindow;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;
import com.qp.ddz.scene.server.ServerActivity;
import com.qp.lib.cmd.CMD;
import com.smw.cmd.plazz.MSG_C2S_UPLOAD_IMAGE;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class SelectFile {

	private static final String	TAG	= "SelectFile";

	public static String ret;
	
	static  List<String> items=new ArrayList<String>();  
	static  List<String> paths=new ArrayList<String>();  
     private static ArrayAdapter<String> adapter;  
    private static String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();  
    static   PopupWindow popupWindow;  
 	
 	public static  void Show (    ) {
 		ret="";
 		if(ServerActivity.instance==null)
 			return;
 		if (popupWindow != null) {
 			onDestroy();
 		}
 		View popupview = null;
 		if (popupWindow == null) { 
 			 popupview = GameActivity.getInstance().getLayoutInflater().inflate(R.layout.selectfile, null, false);

		           popupWindow = new PopupWindow( popupview, LayoutParams.FILL_PARENT , LayoutParams.FILL_PARENT ,true);  
		         //  popupWindow.setContentView(contentView);  
 
		           popupWindow.setBackgroundDrawable(new BitmapDrawable());
		           popupWindow.setOutsideTouchable(false);
		           popupview.setBackgroundColor(Color.BLACK);  
		           
		           //取消
		           Button btn = (Button)popupview.findViewById(R.id.selectfile_btn2);  
		           btn.setOnClickListener(new OnClickListener() {
		               public void onClick(View v) {
		            	   onDestroy();
		               }
		           });
		 		       
		           //
		           Button mButton = (Button)popupview.findViewById(R.id.selectfile_btn);  
		           mButton.setOnClickListener(btLis2);  
		   		  ListView listView = (ListView) popupview.findViewById(R.id.selectfile_list);  
		            openDir(rootPath);  
		             adapter = new ArrayAdapter<String>(ServerActivity.instance.getBaseContext(), android.R.layout.simple_list_item_1, items);  
		           listView.setAdapter(adapter);  
		             listView.setOnItemClickListener(lvLis);   
		             
		          

	 	} else {
			popupview = popupWindow.getContentView();
		}
 		  //获得popupwindow外的焦点  
 	        ColorDrawable cd = new ColorDrawable(-0000);    
 		      popupWindow.setBackgroundDrawable(cd);   
 		      popupWindow.setFocusable(true);  

        
 		
           popupWindow.showAtLocation(GameActivity.getInstance().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
       	
 	}
 		
 	public static  void onDestroy() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}
 	
  
 		      
 		     private static OnClickListener btLis2 = new OnClickListener(){  
 		        @Override  
 		      public void onClick(View v) {  
 		            openDir2(rootPath);  
 		           System.out.println(items);  
 		       }};  
 		      
 		   private static OnItemClickListener lvLis = new OnItemClickListener(){  
 		        @Override  
 		        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {  
 		           File file = new File(paths.get(arg2));  
 		           if(file.isDirectory()){  
 		            openDir2(file.toString());  
 		           }  else{
 		        	   //file.getName();file.getAbsolutePath()
 		        	   	ret=file.getAbsolutePath()+file.getName();
 		        	  System.out.println(file.getAbsolutePath()+file.getName());
 		        	 // System.out.println(adapter.getItem(arg2));
 	 		           //System.out.println(items);  
 		        	
 		        	  
 		        	 
 		                
 		        		
 		 			MSG_C2S_UPLOAD_IMAGE i=new MSG_C2S_UPLOAD_IMAGE();
 		 			i.cmd=CMD.C2S_UPLOAD_IMAGE;
 		 			i.name=GameActivity.m_user;
 		 			i.pwd=GameActivity.m_pwd;
 		 			
 		 			RandomAccessFile raf;
					try {
						raf = new RandomAccessFile(file, "r");
						  //raf.seek(targetFile.length()); 
		                raf.read(i.imgdata, 0, (int)file.length() );
		                raf.close(); 
		                
					} catch ( Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
	               
 		 			 
 		 			i.img_size= (int)file.length() ;
 		 			//System.arraycopy(bt, 0, i.imgdata, 0, bt.length);
 		 			 GameActivity.getGameActivityInstance().m_loginclient.send(i);
 		 			
 		        	  
 		        	  
 		        	  
 		        	 onDestroy();
 		        	  
 		           }
 		           
 		          
 		            
 		       }  
 		   };  
 		  private static void openDir(String path)  
 		      {  
 		        File file  = new File(path);  
 		           File[] files = file.listFiles();  
 		         for(int i=0;i<files.length;i++){  
 		               File f = files[i];  
 		             items.add(f.getName());  
 		              paths.add(f.getPath());  
 		          }  
 		       }  
 		     
 		     private static void openDir2(String path)  
 		       {  
 		         File file  = new File(path);  
 		         File[] files = file.listFiles();  
 		           
 		        items.clear();  
 		          paths.clear();  
 		         for(int i=0;i<files.length;i++){  
 		             File f = files[i];  
 		             items.add(f.getName());  
 		            paths.add(f.getPath());  
 		          }  
 		          adapter.notifyDataSetChanged();  
 		      }  

}
