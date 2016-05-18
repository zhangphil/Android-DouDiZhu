package com.qp.ddz.scene.menu;
//
//import com.nd.commplatform.NdCallbackListener;
//import com.nd.commplatform.NdCommplatform;
//import com.nd.commplatform.NdErrorCode;
//import com.nd.commplatform.NdMiscCallbackListener.OnLoginProcessListener;
//import com.nd.commplatform.entry.NdIcon;
import java.net.InetAddress;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF;
import com.qp.ddz.scene.server.ServerActivity;
import com.qp.ddz.utility.BtBackGround;
import com.qp.lib.interface_ex.ILoginCompleteListener;
import com.qp.lib.main.QPActivity;
import com.qp.lib.utility.Util;
import com.smw.net.DynamicUI;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class MenuActivity extends QPActivity implements OnClickListener
//, OnLoginProcessListener
, ILoginCompleteListener {

	public static String		TAG	= "MenuActivity";

//	ImageButton					m_btLogoff;
	Button						m_btFastStart;
	Button						m_btReg;
//	ImageButton					m_btPlazzEnter;
//	ImageButton					m_bt91Login;

//	ImageView					m_ImageHead;

//	TextView					m_TextNickName;
//	TextView					m_TextSocre;
//	TextView					m_TextInsure;
 

	public static MenuActivity	instance;

	public int					nDelayAction;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu);

		instance = this;
 

//		m_btLogoff = (ImageButton) findViewById(R.id.menu_bt_logoff);
		m_btFastStart = ( Button) findViewById(R.id.menu_bt_fastenter);
//		m_btPlazzEnter = (ImageButton) findViewById(R.id.menu_bt_plazz);
		m_btReg = ( Button) findViewById(R.id.menu_bt_reg);

//		m_btLogoff.setOnTouchListener(new BtBackGround());
		m_btFastStart.setOnTouchListener(new BtBackGround());
	//	m_btPlazzEnter.setOnTouchListener(new BtBackGround());
	//	m_bt91Login.setOnTouchListener(new BtBackGround());

	//	m_btLogoff.setOnClickListener(this);
		m_btFastStart.setOnClickListener(this);
		m_btReg.setOnClickListener(this);
		
	//	m_btPlazzEnter.setOnClickListener(this);
	//	m_bt91Login.setOnClickListener(this);

	//	m_ImageHead = (ImageView) findViewById(R.id.menu_face);

	//	m_TextNickName = (TextView) findViewById(R.id.menu_text_name);
	//	m_TextSocre = (TextView) findViewById(R.id.menu_text_score);
	//	m_TextInsure = (TextView) findViewById(R.id.menu_text_insure);

//		NdCommplatform nd = NdCommplatform.getInstance();
//		if (nd.isLogined() && GameActivity.m_ImageUserHead != null) {
//			BitmapDrawable bitmap = new BitmapDrawable(GameActivity.m_ImageUserHead.getBitmap());
//			m_ImageHead.setImageDrawable(bitmap);
//		}
//		if (nd.isLogined()) {
//			m_TextNickName.setText(nd.getLoginNickName());
//		}
		
		//GameActivity.getGameActivityInstance().m_loginclient.close();
		
	   

//		if (isConnect(this)==false) 
//	    {   
//		       new AlertDialog.Builder(this) 
//		       .setTitle("网络错误") 
//		       .setMessage("网络连接失败，请确认网络连接") 
//		       .setPositiveButton("确定", new DialogInterface.OnClickListener() { 
//		       @Override 
//		       public void onClick(DialogInterface arg0, int arg1) { 
//		    	   //TODO Auto-generated method stub 
//		    	//   android.os.Process.killProcess(android.os.Process.myPid()); 
//		        //    System.exit(0); 
//		       } 
//		       }).show(); 
//		} else{
//		//	Intent intent = new Intent();
//		//	intent.setClass(this,GonggaoActivity.class);
//		//	startActivity(intent);
//		}
				
	}
	
	
	public void getIPAddress() 
	{
		 try {
			 InetAddress inetAddress = InetAddress.getLocalHost();
			 String ip = inetAddress.getHostAddress();//获取ip地址
			 String canonical =inetAddress.getCanonicalHostName(); //获取本地的主机域名
			 String host =inetAddress.getHostName(); //获取本地的主机名
			 Util.v(TAG, "ip地址:"+ip);
			 Util.v(TAG,"主机域名:"+canonical);
			 Util.v(TAG,"主机名:"+host);
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}

 

	public static boolean isConnect(Context context) { 
		   // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理） 
		try { 
		   ConnectivityManager connectivity = (ConnectivityManager) context 
		           .getSystemService(Context.CONNECTIVITY_SERVICE); 
		   if (connectivity != null) { 
		       // 获取网络连接管理的对象 
		       NetworkInfo info = connectivity.getActiveNetworkInfo(); 
		       if (info != null&& info.isConnected()) { 
		           // 判断当前网络是否已经连接 
		           if (info.getState() == NetworkInfo.State.CONNECTED) { 
		               return true; 
		           } 
		       } 
		   } 
		} catch (Exception e) { 
		//TODO: handle exception 
		//Log.v("error",e.toString()); 
		} 
		   return false; 
		}
	
	public void onDestroy() {
		instance = null;
		super.onDestroy();
		
		DynamicUI.onDestroy(this,R.id.menu_LinearLayout);
		
		
		
//		Intent i = new Intent(  this, Service1.class );
//	  	stopService(i);
	}

	@Override
	public void onResume() {
		super.onResume();
		
		  
	// DynamicUI.LoadBG(TAG, this, R.id.menu_LinearLayout   );
		
		//getIPAddress();
		 
		//斗地主服务器和机器人
//		DDZ ddz=new DDZ();
//		ddz.start();
//     
//		Intent i = new Intent(   this, Service1.class );
//		i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//		startService(i); 
//		     
//		 
		 
		 
		    
		//单机版。
		//GameActivity.m_user="aa@aa";
		//GameActivity.m_pwd="aaaa";
	 
		ServerActivity.conn_server();
		ServerActivity.login_server();
		
		//GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
	}
	@Override
	public void onClick(View v) {
		int ID = v.getId();

		switch (ID) {
		//	case R.id.menu_bt_logoff :
//				NdCommplatform nd = NdCommplatform.getInstance();
//				if (nd.isLogined())
//					onAccountsLogoff();
	//			break;
			case R.id.menu_bt_fastenter :
//				ServerEngine.getInstance().setFastPlay(true);
//				nDelayAction = 1;
//				onLoginBy91();
			//	GameActivity.getInstance().onShowScene(GDF.SCENE_LOGIN);
			    
//				//单机版。
//				GameActivity.getGameActivityInstance().m_user="aa@aa";
//				GameActivity.getGameActivityInstance().m_pwd="aaaa";
//			 
//				GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
				break;
//			case R.id.menu_bt_plazz :
//				ServerEngine.getInstance().setFastPlay(false);
//				nDelayAction = 1;
//				onLoginBy91();
//				break;
			case R.id.menu_bt_reg :
				//onLoginBy91();
			//	GameActivity.getInstance().onShowScene(GDF.SCENE_LOGIN);
				
				
				break;
			default :
				Util.e(TAG, "onClick-unkownid:" + v.getId());
				break;
		}

	}

	private void onLoginBy91() {

		//要加个点击判断. 连点就连续请求了.需要改.

//		NdCommplatform nd = NdCommplatform.getInstance();
//
//		if (nd.isLogined()) {
//			menuEngine.onLoginPlazzOther(nd.getLoginNickName(), Long.parseLong(nd.getLoginUin()), GDF.UrlLogin, GDF.PortLogin);
//		} else {
//			nd.ndLogin(GameActivity.getInstance(), this);
//		}
	}
	////登陆游戏服务器
	private void onLoginPlazz() {
//		NdCommplatform nd = NdCommplatform.getInstance();
//		if (nd.isLogined()) {
//			GameActivity.onShowDialog("登陆", "请稍后...");
//			menuEngine.onLoginPlazzOther(nd.getLoginNickName(), Long.parseLong(nd.getLoginUin()), GDF.UrlLogin, GDF.PortLogin);
//		}
	}

	private void onAccountsLogoff() {
//		m_bt91Login.setClickable(true);
//		m_TextInsure.setText("");
//		m_TextNickName.setText("");
//		m_TextSocre.setText("");
//		m_bt91Login.setVisibility(View.VISIBLE);
//		m_btPlazzEnter.setVisibility(View.INVISIBLE);
//		m_btFastStart.setVisibility(View.INVISIBLE);
//		m_ImageHead.setImageDrawable(null);

		//NdCommplatform.getInstance().ndLogout(NdCommplatform.LOGOUT_TO_RESET_AUTO_LOGIN_CONFIG, GameActivity.getInstance());

	}

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {

		switch (event.getKeyCode()) {
		//	case KeyEvent.KEYCODE_VOLUME_DOWN :
		//	case KeyEvent.KEYCODE_VOLUME_UP :
		//		return GameActivity.onVolume(event.getKeyCode());
			case KeyEvent.KEYCODE_MENU : {
				return ((GameActivity) GameActivity.getInstance()).onShowOptionMenu(null);
			}
			case KeyEvent.KEYCODE_BACK : {
				((GameActivity) GameActivity.getInstance()).OnQueryApp();
				return true;
			}
		}
		return super.onKeyDown(keycode, event);
	}

		/*@Override
	public void finishLoginProcess(int code) {

		GameActivity.dismissDialog();
	
		// 91登录的返回码检查
		if (code == NdErrorCode.ND_COM_PLATFORM_SUCCESS) {
			NdCommplatform nd = NdCommplatform.getInstance();
			m_btFastStart.setVisibility(View.VISIBLE);
			m_btPlazzEnter.setVisibility(View.VISIBLE);
			m_TextNickName.setText(nd.getLoginNickName());

			NdCommplatform.getInstance().ndGetPortrait(nd.getLoginUin(), "", GameActivity.getInstance(), new NdCallbackListener<NdIcon>() {

				@Override
				public void callback(int code, NdIcon image) {
					if (image != null && image.getImg() != null) {
						GameActivity.m_ImageUserHead = new BitmapDrawable(image.getImg());
						GameActivity.m_szFaceCheckSum = image.getCheckSum();
					} else {
						GameActivity.m_ImageUserHead = new BitmapDrawable(NdCommplatform.getInstance().ndGetDefaultPhoto(NdCommplatform.DEFAULT_ICON_TYPE_HEAD,
								GameActivity.getInstance()));
					}
					if (m_ImageHead != null) {
						BitmapDrawable bitmap = new BitmapDrawable(GameActivity.m_ImageUserHead.getBitmap());
						m_ImageHead.setImageDrawable(bitmap);
					}
				}
			});
			
			//登陆游戏服务器
			if (nDelayAction == 1) {
				onLoginPlazz();
			}
		} else if (code == NdErrorCode.ND_COM_PLATFORM_ERROR_CANCEL) {
			Toast.makeText(GameActivity.getInstance(), "取消登录", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(GameActivity.getInstance(), "登录失败，错误代码：" + code, Toast.LENGTH_SHORT).show();
		}

	}*/

	@Override
	public void onLoginComplete(int code, String szdescribe) {
		switch (code) {
			case ILoginCompleteListener.LOGIN_FAILD :
				GameActivity.dismissDialog();
				break;
			case ILoginCompleteListener.LOGIN_SUCCEED :
				break;
			case ILoginCompleteListener.LOGIN_FINISH :
				GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
				break;
		}

	}
}
