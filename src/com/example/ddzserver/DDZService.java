package com.example.ddzserver;

  

 
import com.game.ddz.DDZServer;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

/*
*
* 劃鎗俇淕埭鎢薊炵 q344717871
* 
* */

 
public class DDZService extends Service
{
 
  private Handler objHandler = new Handler();
  
	DDZServer g;
   
	
 
  private Runnable mTasks = new Runnable() 
  {
   
    public void run()
    { 
    	 g.Update(); 
      objHandler.postDelayed(mTasks,30);
    } 
  };
  
  @Override
  public void onStart(Intent intent, int startId)
  {
	    g=new DDZServer();
		g.Start();
		  
		
    objHandler.postDelayed(mTasks, 30);
    super.onStart(intent, startId);
  }

  @Override
  public void onCreate()
  {
    // TODO Auto-generated method stub
    super.onCreate();
  }
  
  @Override
  public IBinder onBind(Intent intent)
  {
    // TODO Auto-generated method stub
    
     //IBinder方法為Service建構必須覆寫的方法 
    return null;
  }

  @Override
  public void onDestroy()
  {
    // TODO Auto-generated method stub
    g.Close();
    // 當服務結束，移除mTasks執行緒  
    objHandler.removeCallbacks(mTasks);
    super.onDestroy();
  }  
}
