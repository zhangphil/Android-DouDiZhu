package com.qp.ddz;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.ddzserver.DDZBotService;
import com.example.ddzserver.DDZService;
import com.qp.ddz.BroadcastReceiver.BatteryLevelRcvr;
import com.qp.ddz.define.GDF;
import com.qp.ddz.game.BuyScoreActivity;
import com.qp.ddz.game.GameClientActivity;
import com.qp.ddz.game.UserInfoView;
import com.qp.ddz.scene.about.AboutActivity;
import com.qp.ddz.scene.menu.LoginActivity;
import com.qp.ddz.scene.menu.MenuActivity;
import com.qp.ddz.scene.popupwindow.BankView;
import com.qp.ddz.scene.popupwindow.Chat;
import com.qp.ddz.scene.popupwindow.HornRecord;
import com.qp.ddz.scene.popupwindow.OptionMenu;
import com.qp.ddz.scene.popupwindow.UserInfoViewRoom;
import com.qp.ddz.scene.rankinglist.RankingListActivity;
import com.qp.ddz.scene.server.ServerActivity;
import com.qp.ddz.scene.shop.ShopActivity;
import com.qp.ddz.scene.welcome.WelComeActivity;
import com.qp.ddz.utility.CImage;
import com.qp.ddz.utility.CustomFaceManage;
import com.qp.lib.cmd.CMD;
import com.qp.lib.interface_ex.option.IOptionControl;
import com.qp.lib.main.AppMain;
import com.qp.lib.utility.Util;
import com.qp.ddz.R;
import com.smw.cmd.game.MSG_C2S_CHAT;
import com.smw.cmd.plazz.MSG_C2S_HEARTBEAT;
import com.smw.net.PacketHandler;
import com.smw.net.TrueClient;
import com.smw.net.UserItem;
 
//import com.nd.commplatform.NdCallbackListener;
//import com.nd.commplatform.NdCommplatform;
//import com.nd.commplatform.NdMiscCallbackListener.OnUserInfoChangeListener;
//import com.nd.commplatform.entry.NdIcon;
/*
*
* 购买完整源码联系 q344717871
* 
* */

public class GameActivity extends AppMain {

	public static final String		TAG					= "GameActivity";
	public PacketHandler m_PacketHandler;
	public boolean m_stop ,m_stopserver;
	public TrueClient m_loginclient;
	public TrueClient m_gameclient;
	
	public   static String m_user="aa@aa";
	public   static String m_pwd="aaaa";
 
	public int  m_meUID; 
	public ArrayList<UserItem>		m_userlist;
	/** 场景容器 **/
	protected ViewFlipper			m_ViewFlipper;
	/** 进入动画 **/
	protected Animation				Alpha_In;
	/** 退出动画 **/
	protected Animation				Alpha_Out;
	/** 场景标示记录 **/
	protected int					TAG_ID;
	/** 游戏设置 **/
	public OptionMenu				m_OptionMenu;
	/** 银行 **/
	public BankView					m_BankView;
	/** 询问对话框 **/
	public Dialog					m_QueryDialog;
	/** 电量监听 **/
	public BatteryLevelRcvr			m_BatteryLevelRcvr	= new BatteryLevelRcvr();
	/** 聊天控件 **/
	public Chat						m_Chat;
	/** 喇叭公告记录 **/
	public HornRecord				m_HornRecord;
	/** 自定义头像管理 **/
	CustomFaceManage				m_CustomFaceManage;
 
	/** 默认头像 **/
	public static CImage[]			m_ImageDefFace		= new CImage[2];
	static {
		m_ImageDefFace[0] = new CImage();
		m_ImageDefFace[1] = new CImage();
	}
	/** 91头像 **/
	//public static BitmapDrawable	m_ImageUserHead;
	/** 头像检测 **/
	//public static String			m_szFaceCheckSum	= "";
	/********************************************************************************************************************/

	public static GameActivity getGameActivityInstance() {
		return (GameActivity) instance;
	}
	
	//get积分
	public static int getScore(){
		//get
		SharedPreferences sp= getGameActivityInstance().getSharedPreferences("shared_file", 0);
 
		return  sp.getInt("score", 2000 );
	}
	//set积分
	public static void setScore(int s){
 
 		//set
		SharedPreferences    sp= getGameActivityInstance().getSharedPreferences("shared_file", 0);
 
 		sp.edit().putInt("score",s ).commit(); 
  
	}
	
	/******************************************************* UI进程函数 **************************************************/
	public Handler m_handler=new Handler(){
		@Override
		public   void handleMessage(Message msg) {
		// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 1000:
			{
				int color=(Integer) msg.obj;
				 if (ServerActivity.instance != null)
				 	ServerActivity.instance.UpdateColor(  color  );
				 if (GameClientActivity.getInstance() != null)
				 	GameClientActivity.getInstance().UpdateColor( color);
				 UserInfoViewRoom.UpdateColor();
				 UserInfoView.UpdateColor();
				 
				break;
			}
			case 5: 	 
				Util.v(TAG,"5handleMessage SCENE_MENU");
				m_loginclient.close();m_gameclient.close();
				 GameActivity.getInstance().onShowScene(GDF.SCENE_MENU);//login界面
				break;
			case 10:  
				Util.v(TAG,"10handleMessage SCENE_SERVER");
				if(m_loginclient.c!=null){
					if(m_loginclient.c.getSock()!=null){
						if(m_loginclient.c.getSock().isClosed() ){
							GameActivity.getInstance().onShowScene(GDF.SCENE_MENU);//login界面
							return;
						}
					} 
				}
//				if(RankingListActivity.instance!=null){
//					 //说明在排行榜界面了！
//				} else if(ShopActivity.instance!=null){
//					 //说明在商城界面了！
//				}else if(ShopActivity.instance!=null)
				{
				 	GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);//大厅界面
				} 
				break;
			case 1:
				m_PacketHandler.ParseCommand(msg.arg1, msg.obj);
				break;
				
			}
		}
	 };

	
	/**
	 * 开始程序
	 */
	@Override
	public void onStartApp() {
		setContentView(R.layout.appmain);

		Util.DEBUG_ = false;

		m_ViewFlipper = (ViewFlipper) findViewById(R.id.flipper);

		Alpha_In = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
		Alpha_Out = AnimationUtils.loadAnimation(this, R.anim.alpha_out);

		m_ViewFlipper.setInAnimation(Alpha_In);
		m_ViewFlipper.setOutAnimation(Alpha_Out);

		m_CustomFaceManage = new CustomFaceManage();
		m_CustomFaceManage.Init();
		
		m_gameclient=new TrueClient();
		m_loginclient=new TrueClient();
		m_loginclient.on_close_msg_id=5;
		m_gameclient.on_close_msg_id=10;
		m_PacketHandler=new PacketHandler();
		m_userlist = new ArrayList<UserItem>();
		
	 
		
		onShowScene(GDF.SCENE_WELCOME);
 
		Util.v(TAG,"stopService");
    	Intent i ;
     	i= new Intent(   this, DDZService.class );
    	stopService(i); 
    	i= new Intent(   this, DDZBotService.class );
    	stopService(i); 
    	Util.v(TAG,"stopService222");
//      		
       
    	Util.v(TAG,"startService");
      		   i = new Intent(   this, DDZBotService.class );
      		i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK ); 
      	  startService(i); 
      	 	
      	  i = new Intent(   this, DDZService.class );
    		i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
    	  	startService(i); 
    	 	
    	 	
    	  	Util.v(TAG,"startService222");
      		
      		
      		while(true)
      		{
      			try {
      				Thread.sleep(1000);
      			} catch (InterruptedException e) {
      				// TODO Auto-generated catch block
      				e.printStackTrace();
      			}
      			Util.v(TAG,"m_gameclient.start(");
      			boolean ret= ServerActivity.conn_server();
      			 
      			if(ret) break;
      			 
      			
      		}
		
      		Util.v(TAG,"m_gameclient.start ok");
		
		
	m_stop=false;
	
	 
	
	new Thread(new Runnable() {
		public void run() {
			long lastsend=System.currentTimeMillis();
			int r=234,g=234,b=234;
			int i=0;
			
			 
			
			while(m_stop==false){
				 
				try {
					Thread.sleep(2000);
					if(System.currentTimeMillis()-lastsend>40000){
						heartbeat();
						lastsend=System.currentTimeMillis();
					}
					if (ServerActivity.instance != null) {
				 		ServerActivity.instance.m_Handler.obtainMessage(ServerActivity.IDI_UPDATE_SCORE).sendToTarget();
					}
					

					Message msg=new Message();
					msg.what=1000; //消息code
					if(i%3==0) msg.obj= Color.RED;
					if(i%3==1) msg.obj= Color.GREEN;
					if(i%3==2) msg.obj= Color.YELLOW;
					i++;
					 //Color.rgb(r,g,b) ; //消息内容 
					m_handler.sendMessage(msg);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}).start();
	
	/*
	new Thread(new Runnable() {
		public void run() {
			int r=122,g=222,b=255;
			int ct=0;
			while(m_stop==false){
				 
				try {
					Thread.sleep(20);
				 
					 
					if(ct%2==0) b+=5;
					else b-=5;
					
					if(b>240 || b<120) ct++;
					
					Message msg=new Message();
					msg.what=1000; //消息code
					msg.obj=Color.rgb(r,g,b) ; //消息内容 
					m_handler.sendMessage(msg);

					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}).start();*/
}
public void heartbeat()
{
	MSG_C2S_HEARTBEAT  o=new MSG_C2S_HEARTBEAT();
	o.cmd= CMD.C2S_HEARTBEAT;
	m_loginclient.send(o);
	
}
public void sendHorn(String msg){
	MSG_C2S_CHAT o=new MSG_C2S_CHAT();
	o.cmd=CMD.C2S_USER_HORN;
	o.TargetUserID=-1000;//-1000为喇叭
	o.ChatColor=1;
	o.ChatString=msg;
	GameActivity.getGameActivityInstance().m_loginclient.send(o);
}
//public UserItem sm_getTableUserItem(int chairid){
//	if(chairid<0 || chairid>4) return null;
//	return  sm_getuser(GameClientView.playerArray[chairid]);
//}
public UserItem sm_getme( ){
	for (int i = 0; i < m_userlist.size(); i++) {
		UserItem u = (UserItem) m_userlist.get(i); 
		if(u.uid==this.m_meUID)	return  u;
	}
	return null;
}
public UserItem sm_getuser(int uid){
	for (int i = 0; i < m_userlist.size(); i++) {
		UserItem u = (UserItem) m_userlist.get(i); 
		if(u.uid==uid)	return  u;
	}
	return null;
}
	/**
	 * 切换场景
	 * 
	 * @param sceneid
	 *            场景标示
	 */
	public void onShowScene(int sceneid) {
		Util.v(TAG,"onShowScene sceneid"+sceneid);
		if (TAG_ID == sceneid)
		{
			Util.v(TAG,"onShowScene return"  );
			return;
		}
		
		Intent intent = null;

		/** 隐藏设置 **/
		if (m_ProgressDialog != null)
			m_ProgressDialog.dismiss();
		if (m_QueryDialog != null)
			m_QueryDialog.dismiss();
		if (m_OptionMenu != null)
			m_OptionMenu.onDestroy();
		if (m_BankView != null)
			m_BankView.onDestroy();
		if (m_Chat != null)
			m_Chat.onDestroy();
		if (m_HornRecord != null)
			m_HornRecord.onDestroy();

		switch (sceneid) {
			case GDF.SCENE_WELCOME :
				Util.v(TAG,"onShowScene SCENE_WELCOME"  );
				intent = new Intent(GameActivity.this, WelComeActivity.class);
				break;
			case GDF.SCENE_MENU :
				Util.v(TAG,"onShowScene SCENE_MENU"  );
				intent = new Intent(GameActivity.this, MenuActivity.class);
				break;
				 
			case GDF.SCENE_LOGIN :
				Util.v(TAG,"onShowScene SCENE_LOGIN"  );
				intent = new Intent(GameActivity.this, LoginActivity.class);
				break;
			case GDF.SCENE_SERVER :
				Util.v(TAG,"onShowScene SCENE_SERVER"  );
				intent = new Intent(GameActivity.this, ServerActivity.class);
				break;
			case GDF.SCENE_GAME :
				Util.v(TAG,"onShowScene SCENE_GAME"  );
				intent = new Intent(GameActivity.this, GameClientActivity.class);
				break;
			case GDF.SCENE_SHOP :
				intent = new Intent(GameActivity.this, ShopActivity.class);
				break;
			case GDF.SCENE_ABOUT :
				intent = new Intent(GameActivity.this, AboutActivity.class);
				break;
//			case GDF.SCENE_GAMES :
//				intent = new Intent(GameActivity.this, GamesCityActivity.class);
//				break;
			case GDF.SCENE_RANKING :
				intent = new Intent(GameActivity.this, RankingListActivity.class);
				break;
			case GDF.SCENE_BUY :
				intent = new Intent(GameActivity.this, BuyScoreActivity.class);
				break;
		}
		if (intent != null) {
			TAG_ID = sceneid;
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Window subActivity = getLocalActivityManager().startActivity("subActivity" + sceneid, intent);

			if (m_ViewFlipper.getChildCount() == 0) {
				m_ViewFlipper.addView(subActivity.getDecorView());
			} else {
				m_ViewFlipper.addView(subActivity.getDecorView(), 1);
				m_ViewFlipper.removeViewAt(0);
				m_ViewFlipper.getCurrentView().setFocusable(true);
			}
		}
	}

	/**
	 * UI线程消息处理
	 */
	@Override
	public void UI_HandleMessage(Message msg) {
		switch (msg.what) {
			case GDF.MSG_UI_CHANGE :
				onShowScene(msg.arg1);
				break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	/**
	 * 按键响应
	 */
	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_VOLUME_DOWN :
				if (m_OptionMenu != null)
					return m_OptionMenu.onVoiceDown();
				break;
			case KeyEvent.KEYCODE_VOLUME_UP :
				if (m_OptionMenu != null)
					return m_OptionMenu.onVoiceUp();
				break;
			case KeyEvent.KEYCODE_BACK : {
				onKeyBack();
				return true;
			}
			case KeyEvent.KEYCODE_MENU : {
				if (TAG_ID != GDF.SCENE_WELCOME) {
					onShowOptionMenu(getWindow().getDecorView());
				}
				return true;
			}
		}

		return super.onKeyDown(keycode, event);
	}

	/** 显示设置 **/
	public boolean onShowOptionMenu(View view) {
		if (view == null) {
			view = getWindow().getDecorView();
		}
		if (m_OptionMenu == null) {
			m_OptionMenu = new OptionMenu(getOptionControl());
		}
		return m_OptionMenu.onShowOptionMenu(this, view);

	}

	/** 显示保险柜 **/
	public boolean onShowBank(View view) {
		if (view == null) {
			view = getWindow().getDecorView();
		}
//		if (m_BankView == null) {
//			m_BankView = new BankView(m_BankControlRoom);
//		}
		if (m_BankView.isVisibility())
			m_BankView.onDestroy();
		else
			m_BankView.onShowBankView(this, view);
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (m_BatteryLevelRcvr != null)
			m_BatteryLevelRcvr.onDestroy();
		super.onPause();
	}

	@Override
	protected void onResume() {

		super.onResume();
		if (m_BatteryLevelRcvr != null)
			m_BatteryLevelRcvr.onStart();
		if (m_ImageDefFace[0] != null)
			m_ImageDefFace[0].LoadImage(getResources(), R.drawable.head_00);
		if (m_ImageDefFace[1] != null)
			m_ImageDefFace[1].LoadImage(getResources(), R.drawable.head_01);
	}

	public boolean onShowChat(View view) {
		if (view == null) {
			view = getWindow().getDecorView();
		}
		if (m_Chat == null)
			m_Chat = new Chat();
		if (m_Chat.isVisibility())
			m_Chat.onDestroy();
		else
			m_Chat.onShowChat(this, view);
		return true;
	}

	public boolean ShowHornRecord(View view) {
		if (view == null) {
			view = getWindow().getDecorView();
		}
		if (m_HornRecord == null)
			m_HornRecord = new HornRecord();
		if (m_HornRecord.isVisibility())
			m_HornRecord.onDestroy();
		else
			m_HornRecord.onShowHornRecord(this, view);

		return true;

	}
	public void onExit() {
		if (m_OptionMenu != null)
			m_OptionMenu.onDestroy();
		if (m_BankView != null)
			m_BankView.onDestroy();

		super.onExit();
	}

	/**
	 * 返回键处理
	 */
	public void onKeyBack() {

		switch (TAG_ID) {
			case GDF.SCENE_WELCOME :
				onExit();
				break;
			case GDF.SCENE_MENU :
				OnQueryApp();
				break;
			case GDF.SCENE_SERVER :
				onShowScene(GDF.SCENE_MENU);
				break;
			case GDF.SCENE_GAME :
				OnQueryGame();
				break;
			case GDF.SCENE_SHOP :
				onShowScene(GDF.SCENE_SERVER);
				break;
		}
	}

	/**
	 * 退出询问 程序
	 */
	public void OnQueryApp() {
		if (m_QueryDialog != null)
			m_QueryDialog.dismiss();

		DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				GameActivity.this.onExit();
			}
		};

		DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		};
		m_QueryDialog = new AlertDialog.Builder(this).setTitle("退出").setMessage("是否退出程序").setPositiveButton("确定", ok).setNegativeButton("取消", cancle).create();
		m_QueryDialog.show();
	}

	/**
	 * 退出询问 游戏强退
	 */
	public void OnQueryGame() {
		//if (m_QueryDialog != null)
		//	m_QueryDialog.dismiss();
//		if (m_Kernel.getMeUserItem().GetUserStatus() == GDF.US_PLAYING) {
//			DialogInterface.OnClickListener ok = new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					if (GameClientActivity.getInstance() != null)
//						GameClientActivity.getInstance().onRelease();
//					onShowScene(GDF.SCENE_SERVER);
//					m_Kernel.PerformStandupAction(true);
//				}
//			};

//			DialogInterface.OnClickListener cancle = new DialogInterface.OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//
//				}
//			};
//			m_QueryDialog = new AlertDialog.Builder(this).setTitle("退出").setMessage("正在游戏中，确认强制退出？").setPositiveButton("确定", ok)
//					.setNegativeButton("取消", cancle).create();
//			m_QueryDialog.show();
	//	} else {
		
		m_gameclient.close();
		
			//onShowScene(GDF.SCENE_SERVER);
		//	if (m_Kernel.getMeUserItem().GetUserStatus() > GDF.US_FREE) {
		//		m_Kernel.PerformStandupAction(false);
		//	}

	//	}
	}

	public void onShowHorn() {

	}

	@Override
	public void onNetError(int main, int sub, Object obj) {
		m_MainHandler.removeMessages(GDF.MSG_UI_CHANGE);
		dismissDialog();
		Toast.makeText(this, "网络连接错误-" + main, Toast.LENGTH_SHORT).show();
		onShowScene(GDF.SCENE_MENU);
	}

	/********************************************************************************************************************/

	/******************************************************* 非UI进程函数 ************************************************/
	@Override
	public int onAsyncTaskLoad(int currentprogerss) {
	//	m_SocketMisson = new UI_Thread_SocketMission();
		m_Option.setAppVersion(GDF.APP_VERSION);
		m_Option.setGameVersion(GDF.GAME_VERSION);
		//m_Option.setKindID(GDF.KIND_ID);
		m_Option.setViewCount(1);
	  
		m_HornRecord = new HornRecord();
		onLoadSound();

		return currentprogerss + 5;
	}

	private void onLoadSound() {

		// 按键音效
		m_Option.LoadGameSound(R.raw.click, GDF.SOUND_CLICK);
		// 游戏音效
		m_Option.LoadGameSound(R.raw.game_alert, GDF.SOUND_GAME_ALERT);
		m_Option.LoadGameSound(R.raw.game_bankerinfo, GDF.SOUND_GAME_BANKERINFO);
		m_Option.LoadGameSound(R.raw.game_bomb, GDF.SOUND_GAME_BOMB);
		m_Option.LoadGameSound(R.raw.game_end, GDF.SOUND_GAME_END);
		m_Option.LoadGameSound(R.raw.game_plane, GDF.SOUND_GAME_PLANE);
		m_Option.LoadGameSound(R.raw.game_sendcard, GDF.SOUND_GAME_SEND);
		m_Option.LoadGameSound(R.raw.game_shuffle, GDF.SOUND_GAME_SHULLE);
		m_Option.LoadGameSound(R.raw.game_start, GDF.SOUND_GAME_START);
		m_Option.LoadGameSound(R.raw.game_warn, GDF.SOUND_GAME_WARN);
		for (int i = 0; i < 2; i++) {
			m_Option.LoadGameSound(R.raw.game_m_pass0 + i, GDF.SOUND_PASS_M_0 + i * 2);
			m_Option.LoadGameSound(R.raw.game_w_pass0 + i, GDF.SOUND_PASS_W_0 + i * 2);
			m_Option.LoadGameSound(R.raw.game_m_ya0 + i, GDF.SOUND_YA_M_0 + i * 2);
			m_Option.LoadGameSound(R.raw.game_w_ya0 + i, GDF.SOUND_YA_W_0 + i * 2);
		}
		for (int i = 0; i < 9; i++) {
			m_Option.LoadGameSound(R.raw.game_m_type_1 + i, GDF.SOUND_TYPE_M_DUI + i * 2);
			m_Option.LoadGameSound(R.raw.game_w_type_1 + i, GDF.SOUND_TYPE_W_DUI + i * 2);
		}

		for (int i = 0; i < 15; i++) {
			m_Option.LoadGameSound(R.raw.card_m_01 + i, GDF.SOUND_CARD_M_1 + i * 2);
			m_Option.LoadGameSound(R.raw.card_w_01 + i, GDF.SOUND_CARD_W_1 + i * 2);
		}
		for (int i = 0; i < 4; i++) {
			m_Option.LoadGameSound(R.raw.game_m_cs0 + i, GDF.SOUND_CS_M_0 + i * 2);
			m_Option.LoadGameSound(R.raw.game_w_cs0 + i, GDF.SOUND_CS_W_0 + i * 2);
		}
		for (int i = 0; i < 10; i++) {
			m_Option.LoadGameSound(R.raw.msg_m_0 + i, GDF.SOUND_MSG_M_0 + i * 2);
			m_Option.LoadGameSound(R.raw.msg_w_0 + i, GDF.SOUND_MSG_M_0 + i * 2 + 1);
		}
		m_Option.LoadGameSound(R.raw.msg_tip, GDF.SOUND_MSG_TIP);

		 

	//	m_UserModifyControl = new UserModifyControl();
	}

	public void on91LoginComplete(int code) {
		IOptionControl optioncontinue = GameActivity.getOptionControl();
		optioncontinue.PlayBackGroundMusic(R.raw.background, true);
	/*	NdCommplatform nd = NdCommplatform.getInstance();
		// 设置头像改变监听
		nd.setOnUserInfoChangeListener(new OnUserInfoChangeListener() {
			@Override
			public void onUserInfoChanged(int value) {
				NdCommplatform nd = NdCommplatform.getInstance();

				Util.e(TAG, "91--------------" + value);
				if (value == OnUserInfoChangeListener.USER_ICON || value == OnUserInfoChangeListener.USER_ALL) {

					nd.ndGetPortrait(nd.getLoginUin(), "", GameActivity.getInstance(), new NdCallbackListener<NdIcon>() {
						@Override
						public void callback(int code, NdIcon image) {
							if (image != null && image.getImg() != null) {
								GameActivity.m_ImageUserHead = new BitmapDrawable(image.getImg());
								m_szFaceCheckSum = image.getCheckSum();
							} else {
								GameActivity.m_ImageUserHead = new BitmapDrawable(NdCommplatform.getInstance().ndGetDefaultPhoto(
										NdCommplatform.DEFAULT_ICON_TYPE_HEAD, GameActivity.getInstance()));
								m_szFaceCheckSum = "";
							}
						}
					});
				}
			}
		});*/
		sendUIMessage(GDF.MSG_UI_CHANGE, GDF.SCENE_MENU, 0, 0);
		// test
		// onShowScene(GDF.SCENE_GAME);
		// end
	}

	/**
	 * 升级询问
	 */
	@Override
	public void onQueryUpdate(boolean mustupdate) {

	}
	public static BankView getBankView() {
		return ((GameActivity) instance).m_BankView;
	}

	public int getTagActivityID() {
		return TAG_ID;
	}

	public void onPlayGameSound(int id) {
		m_Option.PlayGameSound(id);
	}
	/********************************************************************************************************************/
}
