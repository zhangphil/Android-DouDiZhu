 package com.qp.ddz.scene.server;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.qp.ddz.GameActivity;
import com.qp.ddz.utility.BtBackGround;
import com.qp.ddz.utility.CustomFaceManage;
import com.qp.lib.cmd.CMD;
 
import com.qp.lib.main.QPActivity;
import com.qp.lib.tag.ServerItem;
import com.qp.lib.utility.Util;
import com.qp.ddz.R;
 
import com.qp.ddz.define.GDF;
import com.qp.ddz.scene.popupwindow.UserInfoViewRoom;
import com.qp.ddz.scene.server.adapter.ServerItemAdapter;
import com.smw.cmd.game.MSG_Enter_Room;
import com.smw.cmd.plazz.MSG_C2S_GetServerList;
import com.smw.net.UserItem;
//import com.nd.commplatform.NdCommplatform;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class ServerActivity extends QPActivity implements OnItemClickListener, OnClickListener
//, ILoginCompleteListener, INetCommandListener
{

	public static String			TAG					= "ServerActivity";

	GridView						m_ServerGridView;

	ServerItemAdapter				m_ServerItemAdapter;

	//ServerEngine					serverEngine;
	public    ArrayList<ServerItem>	 serverlist;
	
	public static ServerActivity	instance			= null;

	TextView						m_TextNickName;
	TextView						m_TextScore;
	public ImageView				m_ImgHead;

	ImageButton						m_btShop;
	ImageButton						m_btBank;
	//ImageButton						m_btGames;
	ImageButton						m_btList;
	ImageButton						m_btAbout;

	ImageButton						m_btBack;
	ImageButton						m_btHorn;

	Button							m_btHornInfo;

	EditText						m_txt_HornIn;

	public static final int			IDI_UPDATE_SCORE	= 0;
	public Handler					m_Handler			= new Handler() {
															@Override
															public void handleMessage(Message msg) {
																if (msg.what == IDI_UPDATE_SCORE) {
																	//onUpdateScore();
																	FillHead();
																}
															}
														};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server);

		instance = this;

		//GameActivity.getGameActivityInstance().geti(R.raw.lgbg, true);
//		if(GameActivity.getOptionControl()!=null){
//			GameActivity.getOptionControl().StopBackGroundMusic();
//		}
		
		//serverEngine = ServerEngine.getInstance();
		//serverEngine.setLoginCompleteListener(this);
 
		
		 

		m_TextNickName = (TextView) findViewById(R.id.server_txt_nickname);
		m_TextScore = (TextView) findViewById(R.id.server_txt_score);
		m_ImgHead = (ImageView) findViewById(R.id.server_img_head);

		m_btShop = (ImageButton) findViewById(R.id.server_bt_shop);
		m_btBank = (ImageButton) findViewById(R.id.server_bt_bank);
		//m_btGames = (ImageButton) findViewById(R.id.server_bt_games);
		m_btList = (ImageButton) findViewById(R.id.server_bt_list);
		m_btAbout = (ImageButton) findViewById(R.id.server_bt_about);

		m_btBack = (ImageButton) findViewById(R.id.server_bt_back);
		m_btHorn = (ImageButton) findViewById(R.id.server_bt_horn);

		m_btHornInfo = (Button) findViewById(R.id.server_bt_horninfo);
		m_txt_HornIn = (EditText) findViewById(R.id.server_txt_msgin);

		m_txt_HornIn.setVisibility(View.INVISIBLE);
	//	m_txt_HornIn.setFilters(new InputFilter[]{new InputFilter.LengthFilter(GDF.LEN_USER_CHAT / 2)});

		m_txt_HornIn.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				switch (actionId) {
					case EditorInfo.IME_ACTION_SEND : {
						InputMethodManager imm = (InputMethodManager) GameActivity.getInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(m_txt_HornIn.getWindowToken(), 0);

						String msg = m_txt_HornIn.getText().toString();
						if (msg != null && !msg.equals("")) {
 						//	IKernel kernel = GameActivity.getKernel();
//							if (kernel != null)
 						//		kernel.sendUserHorn(msg);
							GameActivity.getGameActivityInstance().sendHorn(msg);
						}
						GameActivity.getGameActivityInstance().m_HornRecord.onDestroy();
						m_txt_HornIn.setVisibility(View.INVISIBLE);
						m_btHornInfo.setVisibility(View.VISIBLE);
						return true;
					}
				}
				return false;
			}
		});
		m_btShop.setOnTouchListener(new BtBackGround());
		m_btBank.setOnTouchListener(new BtBackGround());
	//	m_btGames.setOnTouchListener(new BtBackGround());
		m_btList.setOnTouchListener(new BtBackGround());
		m_btAbout.setOnTouchListener(new BtBackGround());

		m_btBack.setOnTouchListener(new BtBackGround());
		m_btHorn.setOnTouchListener(new BtBackGround());

		m_btShop.setOnClickListener(this);
		m_btBank.setOnClickListener(this);
		//m_btGames.setOnClickListener(this);
		m_btList.setOnClickListener(this);
		m_btAbout.setOnClickListener(this);
		m_btHornInfo.setOnClickListener(this);

		m_btBack.setOnClickListener(this);
		m_btHorn.setOnClickListener(this);

		m_ImgHead.setOnClickListener(this);

		FillHead();

		m_ServerGridView = (GridView) this.findViewById(R.id.server_gridview);

		 
		serverlist=new ArrayList<ServerItem>();
	 
	}
	public void FillHead() {

//		if(serverlist.size()==0) {
//
//			 //请求服务器列表
//				MSG_C2S_GetServerList o=new MSG_C2S_GetServerList();
//				o.cmd=CMD.C2S_GetServerList;
//			 
//				GameActivity.getGameActivityInstance().m_loginclient.send(o);
//		}
		
		onUpdateHead();
		onUpdateName();
		onUpdateScore();
	}
	public void onLoadCompelet() {
		//GameActivity.dismissDialog();
		
		m_ServerItemAdapter = new ServerItemAdapter(this, serverlist );
		m_ServerGridView.setAdapter(m_ServerItemAdapter);
		m_ServerGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		m_ServerGridView.setOnItemClickListener(this);

		m_ServerGridView.setNumColumns(serverlist.size());
		int displayw = GameActivity.getOption().getWidth();
		int gridw = Math.max(displayw * serverlist.size() / 5, displayw);
		m_ServerGridView.getLayoutParams().width = gridw;
		
	 
		
		 
	}
	@Override
	public void onDestroy() {
		instance = null;
		super.onDestroy();
		
		UserInfoViewRoom.onDestroy();
		 
		 
		
	//	DynamicUI.onDestroy(this,R.id.server_LinearLayout);
	 
		
	}
	
	@Override
	public void onResume() {
		super.onResume();

		 
		Util.e(TAG, "onResume............................");
		
		 //请求服务器列表
//		MSG_C2S_GetServerList o2=new MSG_C2S_GetServerList();
//		o2.cmd=CMD.C2S_GetServerList;
//	 
//		GameActivity.getGameActivityInstance().m_loginclient.send(o2);
	 
	//	DynamicUI.LoadBG(TAG, this, R.id.server_LinearLayout   );
		
	
		
		//sm_updateinfo();
		/*
		IKernelControl kernelcontrol = GameActivity.getKernelControl();

		if (serverEngine.isFastPlay()) {
			GameActivity.onShowDialog("房间", "自动登陆中...", false, true, new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					GameActivity.getInstance().onShowScene(GDF.SCENE_MENU);
				}
			});
			serverEngine.onFastPlay();
		} else if (kernelcontrol.isInService() == false) {
			GameActivity.onShowDialog("房间", "获取消息服务器", false, true, new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					GameActivity.getInstance().onShowScene(GDF.SCENE_MENU);
				}
			});
			//serverEngine.onLoginMsgRoom();
		}
		UserModifyControl control = UserModifyControl.getInstance();
		if (control != null) {

			control.onCloseModifySever();
			boolean bSucceed = control.onConnectModifySever();
			if (bSucceed) {
				if (serverEngine.isNeedUpdataScore()) {
					control.onModifyUserScore();
				}
				String sznd = "";
				String szour = "";
				if (GameActivity.m_szFaceCheckSum != null && !GameActivity.m_szFaceCheckSum.equals("")) {
					szour = null;//GameActivity.getInstance().getFaceCheckSum(NdCommplatform.getInstance().getLoginUin());

					if (szour == null || szour.equals("") || !szour.equals(GameActivity.m_szFaceCheckSum)) {
						if (control.onModifyUserFace(GameActivity.m_ImageUserHead.getBitmap()) == false) {
							Util.e(TAG, "更新头像失败");
							Toast.makeText(GameActivity.getInstance(), "同步头像失败", Toast.LENGTH_SHORT).show();
						} else {
							Util.i(TAG, "图片:#" + szour + "#" + GameActivity.m_szFaceCheckSum);
						}
					}
				}
				sznd = "ncname";//NdCommplatform.getInstance().getLoginNickName();
				szour = GameActivity.getKernel().getMeUserItem().GetNickName();
				if (!sznd.equals(szour)) {
					if (control.onModifyUserNickName(sznd)) {
						GameActivity.getKernel().getMeUserItem().GetUserInfo().szNickName = sznd;
						Util.e(TAG, "昵称:#" + sznd + "#" + szour);
					} else {
						Toast.makeText(GameActivity.getInstance(), "同步昵称失败", Toast.LENGTH_SHORT).show();
					}
				}
			}
		} else {
			Util.e(TAG, "UserModifyControl - null");
		}
*/
	 	FillHead();
	 	
	 	//没连接
	 	if(GameActivity.getGameActivityInstance().m_gameclient.isClose())
	 	{
	 		//连接 
	 		conn_server();
	 		// 登陆
	 		login_server();
	 	}else{
	 		// 登陆
	 		login_server();
	 		 
	 	}
	 	 
	 	
	 	
	 	//单机版
//	 	ServerItem i  = new  ServerItem(); 
//		i.nServerPort= cmd.port ;// 5001
//		i.nServerID=cmd.gameroomid;//10
//		i.szServerName= cmd.servername;//斗地主初级
//		i.szServerUrl= cmd.ip;//127.0.0.1
//		i.lMinEnterScore=cmd.ScoreMIN;  //100
//		i.lMaxEnterScore=cmd.ScoreMAX; //99999999
//		
//		ServerActivity.instance.serverlist.add(i);
//		ServerActivity.instance.onLoadCompelet();
	 	 
	 	//反复连接。。
//	 	while(true)
//	 	{
//	 		if(loginlocalserver())
//	 		{
//	 			return;
//	 		}
//	 		try {
//				Thread.sleep(300);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	 	}
	}
	public static boolean  conn_server()
	{
		 
		 	//GameActivity.getGameActivityInstance().m_gameclient.close();
			boolean ret=GameActivity.getGameActivityInstance().m_gameclient.start( "127.0.0.1" , 5001 );
			 
		  return ret;
	}
	
	public static void  login_server()
	{
		  
				MSG_Enter_Room o=new MSG_Enter_Room();
				o.cmd=CMD.C2S_ENTER_ROOM;
				o.VERSION=(int)GDF.GAME_VERSION;
				o.game_type=GDF.GAME_TYPE;
				o.room_num= 10;//item.nServerID;//
				o.table_num=5555;
				o.name=GameActivity.m_user;
				o.pwd= GameActivity.getScore()+"";//GameActivity.m_pwd;密码信息作为金币
				GameActivity.getGameActivityInstance().m_gameclient.send(o);
			  
	}
	
	@Override
	public void onItemClick(AdapterView<?> adpter, View view, int position, long id) {
//		GameActivity.getGameActivityInstance().m_client.start();
//		
//		MSG_Login o=new MSG_Login();
//		o.cmd=CMD.C2S_LOGIN;
//		o.name="smw";
//		o.pwd="1234";
//		GameActivity.getGameActivityInstance().m_client.send(o);
		//GameActivity.onShowDialog("游戏", "进入游戏中", false, true);
		//serverEngine.onLoginRoom(position);
		ServerItem item=serverlist.get(position);
		if(item!=null){
			GameActivity.getGameActivityInstance().m_gameclient.close();
			GameActivity.getGameActivityInstance().m_gameclient.start(item.szServerUrl,item.nServerPort);
			MSG_Enter_Room o=new MSG_Enter_Room();
			o.cmd=CMD.C2S_ENTER_ROOM;
			o.VERSION=(int)GDF.GAME_VERSION;
			o.game_type=GDF.GAME_TYPE;
			o.room_num=item.nServerID;
			o.table_num=5555;
			o.name=GameActivity.m_user;
			o.pwd=GameActivity.m_pwd;
			GameActivity.getGameActivityInstance().m_gameclient.send(o);
		}
		 
		
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.server_bt_shop :
				onShopClick();
				break;
			case R.id.server_bt_bank :
				onBankClick();
				break;
//			case R.id.server_bt_games :
//				onGamesClick();
//				break;
			case R.id.server_bt_about :
				onAboutClick();
				break;
			case R.id.server_bt_list :
				onListClick();
				break;
			case R.id.server_bt_back :
				onBackClick();
				break;
			case R.id.server_bt_horn :
				onHornClick();
				break;
			case R.id.server_bt_horninfo :
				onHornInfoClick();
				break;
			case R.id.server_img_head :
				onHeadInfoClick();
			default :
				break;
		}

	}

	private void onHeadInfoClick() {
		UserItem me=GameActivity.getGameActivityInstance().sm_getme();
	
		UserInfoViewRoom.onShowUserInfo(me); //GameActivity.getKernel().getMeUserItem());
	}
	private void onHornInfoClick() {
		((GameActivity) GameActivity.getInstance()).ShowHornRecord(null);

	}
	private void onHornClick() {
		if (m_txt_HornIn.getVisibility() == View.INVISIBLE) {
			m_txt_HornIn.setVisibility(View.VISIBLE);
			m_btHornInfo.setVisibility(View.INVISIBLE);
			GameActivity.getGameActivityInstance().m_HornRecord.onDestroy();
			m_txt_HornIn.setVisibility(View.VISIBLE);
			m_btHornInfo.setVisibility(View.INVISIBLE);
			m_txt_HornIn.requestFocus();
			InputMethodManager imm = (InputMethodManager) GameActivity.getGameActivityInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		} else {

			InputMethodManager imm = (InputMethodManager) GameActivity.getInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(m_txt_HornIn.getWindowToken(), 0);

			String msg = m_txt_HornIn.getText().toString();
			if (msg != null && !msg.equals("")) {
//				IKernel kernel = GameActivity.getKernel();
//				kernel.sendUserHorn(msg);

				GameActivity.getGameActivityInstance().sendHorn(msg);
			}
			m_txt_HornIn.setText("");
			m_txt_HornIn.clearFocus();
			m_txt_HornIn.setVisibility(View.INVISIBLE);
			m_btHornInfo.setVisibility(View.VISIBLE);

		}

	}
	private void onBackClick() {
		GameActivity.getInstance().onShowScene(GDF.SCENE_MENU);

	}
	private void onListClick() {
		GameActivity.getInstance().onShowScene(GDF.SCENE_RANKING);
	}
	private void onAboutClick() {
		GameActivity.getInstance().onShowScene(GDF.SCENE_ABOUT);
	}
	private void onGamesClick() {
		GameActivity.getInstance().onShowScene(GDF.SCENE_GAMES);
	}
	private void onBankClick() {
		GameActivity.getGameActivityInstance().onShowBank(null);

	}
	private void onShopClick() {
		GameActivity.getInstance().onShowScene(GDF.SCENE_SHOP);
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
				GameActivity.getInstance().onShowScene(GDF.SCENE_MENU);
				return true;
			}
		}
		return super.onKeyDown(keycode, event);
	}

	/*@Override
	public void onLoginComplete(int code, String szdescribe) {
		switch (code) {
			case ILoginCompleteListener.LOGIN_FAILD :
				GameActivity.dismissDialog();
				GameActivity.getKernelControl().intemitConnect();
				if (serverEngine.IsMessageServer() == false) {
					GameActivity.onShowDialog("房间", "获取消息服务器", false, true, new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							GameActivity.getInstance().onShowScene(GDF.SCENE_MENU);
						}
					});
					serverEngine.onLoginMsgRoom();
				}
				break;
			case ILoginCompleteListener.LOGIN_SUCCEED :

				break;
			case ILoginCompleteListener.LOGIN_FINISH :
				GameActivity.dismissDialog();
				IKernel kernel = GameActivity.getKernel();
				m_TextScore.setText(kernel.getMeUserItem().GetUserScore() + "");
				if (serverEngine.IsMessageServer() == false) {
					GameActivity.getInstance().onShowScene(GDF.SCENE_GAME);
				}
				break;
		}

	}*/

//	public void sm_updateinfo(){
//		if(GameActivity.getGameActivityInstance().sm_getme()!=null){
//			m_TextScore.setText(GameActivity.getGameActivityInstance().sm_getme().GetUserScore() + "");
//			m_TextNickName.setText(GameActivity.getGameActivityInstance().sm_getme().GetNickName());
//		}
//		 
//	}
	
	public void onUpdateHead() {
		
//		//
//		if (GameActivity.m_ImageUserHead != null) {
//			BitmapDrawable bitmap = new BitmapDrawable(GameActivity.m_ImageUserHead.getBitmap());
//			m_ImgHead.setImageDrawable(bitmap);
//		} 
		UserItem me=GameActivity.getGameActivityInstance().sm_getme();
		if (CustomFaceManage.getInstance() != null 
				&& me!=null ) 
		{
			
			if(me.GetFaceImage()!=null)
			{
				m_ImgHead.setImageBitmap(me.GetFaceImage());
				//m_ImgHead.setImageDrawable( new BitmapDrawable(me.GetFaceImage()));
			}
			  
		}
		  
//				if (obj.img_checksum!=0 && CustomFaceManage.getInstance() != null) {
//					Bitmap bitmap = CustomFaceManage.getInstance().getBitmap( Integer.toHexString(obj.img_checksum), obj.img_checksum);
//					if(bitmap!=null){
//						Log.i(TAG,"OnS2C_USERINFO  bitmap!=null "  );
//					}else{
//						Log.i(TAG,"OnS2C_USERINFO  bitmap =null getBitmap "  );
//					}
//				}
				
	}

	public void onUpdateName() {
		if(GameActivity.getGameActivityInstance().sm_getme()!=null){
			if(m_TextNickName!=null)m_TextNickName.setText(GameActivity.getGameActivityInstance().sm_getme().GetNickName());
		}
		//m_TextNickName.setText(NdCommplatform.getInstance().getLoginNickName());
		 
		
	}
	public void onUpdateScore() {
//		IKernel kernel = GameActivity.getKernel();
//		m_TextScore.setText(kernel.getMeUserItem().GetUserScore() + "");
		if(GameActivity.getGameActivityInstance().sm_getme()!=null){
			if(m_TextScore!=null)m_TextScore.setText(GameActivity.getGameActivityInstance().sm_getme().GetUserScore() + "");
		}
	}

//	@Override
//	public void onNetCommand(int main, int sub, Object obj) {
//		if (sub == CMD.SUB_GP_USER_INSURE_INFO && main == CMD.MDM_GP_USER_SERVICE) {
//			CMD_GP_UserInsureInfo cmd = (CMD_GP_UserInsureInfo) obj;
//			m_TextScore.setText(cmd.lUserScore + "");
//		}
//
//	}
	
	protected void onPause() {
		super.onPause();
		Util.i(TAG, "GameClientActivity暂停...");

//		UserModifyControl control = UserModifyControl.getInstance();
//		if (control != null)
//			control.onCloseModifySever();

		InputMethodManager imm = (InputMethodManager) GameActivity.getInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(m_txt_HornIn.getWindowToken(), 0);
	}
	
	public void UpdateColor(int color)
	{
		if (m_btHornInfo != null)
			 m_btHornInfo.setTextColor(color);
		 
	}
	
	public void onSubSystemMessage(String szString, boolean system) {
		if (m_btHornInfo != null)
			if (system) {
				m_btHornInfo.setTextColor(Color.YELLOW);
			} else {
				m_btHornInfo.setTextColor(Color.WHITE);
			}
		m_btHornInfo.setText(szString);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (m_txt_HornIn.getVisibility() == View.VISIBLE) {
			InputMethodManager imm = (InputMethodManager) GameActivity.getInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(m_txt_HornIn.getWindowToken(), 0);
			m_txt_HornIn.setVisibility(View.INVISIBLE);
		}
		if (m_btHornInfo.getVisibility() == View.INVISIBLE) {
			m_btHornInfo.setVisibility(View.VISIBLE);
		}
		return super.onTouchEvent(event);
	}
}
