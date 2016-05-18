package com.example.ddzserver;


import com.game.ddz.DDZBot;
import com.game.ddz.DDZServer;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

 
 
public class DDZBotService extends Service
{
 
  private Handler objHandler = new Handler();
  
 
	DDZBot bot;
	
 
  private Runnable mTasks = new Runnable() 
  {
   
    public void run()
    { 
    	 
    	  bot.Update();
      objHandler.postDelayed(mTasks,50);
    } 
  };
  
  @Override
  public void onStart(Intent intent, int startId)
  {
	   
		
		 bot=new DDZBot();
		bot.Start();
		
    objHandler.postDelayed(mTasks, 50);
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
    
    // 當服務結束，移除mTasks執行緒  
    objHandler.removeCallbacks(mTasks);
    super.onDestroy();
  }  
}
