package com.qp.ddz.game;
/*
*
* 购买完整源码联系 q344717871
* 
* */

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.qp.ddz.GameActivity;
import com.qp.ddz.utility.GameScenePackage;
import com.qp.ddz.utility.UserClock;
import com.qp.lib.cmd.CMD;
 
import com.qp.lib.interface_ex.IGameClientEngine;
import com.qp.lib.interface_ex.net.ICmd;
import com.qp.lib.main.QPActivity;
import com.qp.lib.utility.Util;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF;
import com.qp.ddz.game.cmd.CMD_C_CallScore;
import com.qp.ddz.game.cmd.CMD_C_OutCard;
import com.qp.ddz.game.cmd.CMD_S_BankerInfo;
import com.qp.ddz.game.cmd.CMD_S_CallScore;
import com.qp.ddz.game.cmd.CMD_S_GameConClude;
import com.qp.ddz.game.cmd.CMD_S_GameStart;
import com.qp.ddz.game.cmd.CMD_S_OutCard;
import com.qp.ddz.game.cmd.CMD_S_PassCard;
import com.qp.ddz.game.cmd.CMD_S_StatusCall;
import com.qp.ddz.game.cmd.CMD_S_StatusFree;
import com.qp.ddz.game.cmd.CMD_S_StatusPlay;
import com.smw.cmd.game.CMD_C_PassCard;
import com.smw.cmd.game.MSG_C2S_CHANGE_TABLE;
import com.smw.cmd.game.MSG_C2S_SAY_READY;
import com.smw.cmd.game.MSG_S_GameStart;
import com.smw.net.UserItem;
//
//import com.nd.commplatform.d.c.my;

@SuppressWarnings("unused")
public class GameClientActivity extends QPActivity implements IGameClientEngine {

	public static String		TAG						= "GameClientActivity";
	public	static GameClientActivity	instance;
	GameClientView				gameclientView;
//	GameEngine					gameclientEngine;

	public final static int		INVISIBLE				= View.INVISIBLE;
	public final static int		VISIBLE					= View.VISIBLE;

	public final static int		IDI_GAME_START			= 1;
	public final static int		IDI_CALL_POINT			= 2;
	public final static int		IDI_OUT_CARD			= 3;

	public final static int		TIME_GAME_START			= 30;
	public final static int		TIME_CALL_POINT			= 30;
	public final static int		TIME_OUT_CARD			= 30;

	public final static int		IDI_SEND_FINISH			= 101;

	public final static int		IDI_HIDE_CHAT_0			= 102;
	public final static int		IDI_HIDE_CHAT_1			= 103;
	public final static int		IDI_HIDE_CHAT_2			= 104;

	public final static int		IDI_UPDATE_CUSTOM_FACE	= 105;

	public long					m_lCellScore;

	/** 牌记录 **/
	public int					m_HandCardData[];								// 手牌
																				// //
																				// 数据
	public int					m_HandCardCount[];								// 手牌数目
	public int					m_nTurnCardCount;								// 上轮牌数目
	public int					m_nTurnCardData[];								// 上轮牌数据
	protected int				AnalyseData[];
	protected int				AnalyseCount;
	protected int				SkipCardData[];
	protected int				SkipCount;

	/** 辅助变量 **/
	public int					m_nTrusteeCount;								// 超时次数
	public boolean				m_bTrustee=false;									// 是否托管

	/** 游戏变量 **/
	public int					m_nBombCount;
	public int					m_nBankerUser;									// 地主玩家
	public int					m_nBankerScore;								// 地主分数
	public int					m_nCurrentUser;								// 当前玩家

	public int					m_nSortCardType			= GDF.ST_ORDER;

	public boolean				m_bSendCard;
	public UserClock			m_ClockControl;

	/** 随机数 **/
	Random						rand;

	public Handler				m_ClockHandler			= new Handler() {
															@Override
															public void handleMessage(Message msg) {
																if(GameActivity.getScore()<=100)
																{
																	Toast.makeText(instance, "积分不足!请充值", Toast.LENGTH_SHORT).show();
																	
																}
																if (msg.what < 100)
																	onEventUserClockInfo(msg.what, msg.arg1, msg.arg2);
																else
																	GameClientActivity.this.onUIHandlerMsg(msg);
															}
														};

	String						m_szFastMsg[]			= new String[10];
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	gameclientEngine = GameEngine.getInstance();
		gameclientView = new GameClientView(this, this);
	//	GameEngine.setGameClientEngine(this);
		rand = new Random(System.currentTimeMillis());

		m_ClockControl = new UserClock(m_ClockHandler);
		onReSetGame();
		setContentView(gameclientView);
		instance = this;
		m_szFastMsg[0] = "快点吧，等到花儿都谢了。";
		m_szFastMsg[1] = "大家好，很高兴见到各位。";
		m_szFastMsg[2] = "又断线了，网络怎么这么差啊。";
		m_szFastMsg[3] = "和你合作真是太愉快了。";
		m_szFastMsg[4] = "我们交个朋友吧，能告诉我你的联系方式么？";
		m_szFastMsg[5] = "你是MM，还是GG？";
		m_szFastMsg[6] = "不要吵了，不要吵了，专心玩游戏吧。";
		m_szFastMsg[7] = "不要走，决战到天亮。";
		m_szFastMsg[8] = "各位真是不好意思，我要走了。";
		m_szFastMsg[9] = "再见了，我会想念大家的。";
	}

	protected void onUIHandlerMsg(Message msg) {
		switch (msg.what) {
			case IDI_SEND_FINISH :
				onSendCardFinish();
				break;
			case IDI_HIDE_CHAT_0 :
			case IDI_HIDE_CHAT_1 :
			case IDI_HIDE_CHAT_2 :
				gameclientView.UserChat[msg.what - IDI_HIDE_CHAT_0].setVisibility(INVISIBLE);
				break;
			case IDI_UPDATE_CUSTOM_FACE :
				gameclientView.UpdateGameView();
				break;
		}

	}

	private void onSendCardFinish() {
		if (m_bSendCard) {
			if (gameclientView.m_bSendCard)
				gameclientView.onStopSendCard();
			m_bSendCard = false;

			CGameLogic.SortCardList(m_HandCardData, m_HandCardCount[getMeChairID()], m_nSortCardType);
			for (int i = 0; i < GDF.GAME_PLAYER; i++) {
				gameclientView.setCardCount(switchViewChairID(i), GDF.NORMAL_COUNT);
				if (i == getMeChairID())
					gameclientView.setHandCardData(m_HandCardData, m_HandCardCount[i]);
			}
			gameclientView.setBackCard(null, true);
			if (m_nCurrentUser == getMeChairID()) {
				gameclientView.m_btScoreOne.setVisibility(VISIBLE);
				gameclientView.m_btScoreTwo.setVisibility(VISIBLE);
				gameclientView.m_btScoreThree.setVisibility(VISIBLE);
				gameclientView.m_btScorePass.setVisibility(VISIBLE);
			} else {
				gameclientView.setWaitCallScore(true);
			}
			setGameClock(m_nCurrentUser, IDI_CALL_POINT, TIME_CALL_POINT);
			gameclientView.postInvalidate();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		m_bSendCard = false;
		gameclientView.onStopSendCard();
		if (gameclientView != null)
			gameclientView.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		Util.i(TAG, "GameClientActivity暂停...");
		if (gameclientView != null) {
			InputMethodManager imm = (InputMethodManager) GameActivity.getInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(gameclientView.m_txt_HornInfo.getWindowToken(), 0);
		}
	}
	@Override
	protected void onDestroy() {
		instance = null;
		if (gameclientView != null)
			gameclientView.onDestroy();
		super.onDestroy();
	}

	public void onReSetGame() {
		
		UserClock.KillAllClock();
		
		m_lCellScore = 1;
		m_bSendCard = false;
		m_nBombCount = 0;
		m_nBankerUser = GDF.INVALID_CHAIR;
		m_nCurrentUser = GDF.INVALID_CHAIR;
		m_nBankerScore = 0;
		m_nTurnCardCount = 0;
		m_nTurnCardData = new int[GDF.MAX_CARDCOUNT];
		m_HandCardData = new int[GDF.MAX_CARDCOUNT];
		m_HandCardCount = new int[GDF.GAME_PLAYER];
		m_nTrusteeCount = 0;
		//m_bTrustee = false;托管开关
		SkipCardData = new int[GDF.MAX_CARDCOUNT];
		SkipCount = 0;
		AnalyseData = new int[GDF.MAX_CARDCOUNT];
		AnalyseCount = 0;

		gameclientView.setBankerScore(0);
		gameclientView.onSetBombCount(0);
		gameclientView.setBankerUser(GDF.INVALID_ITEM);
		gameclientView.setUserCallScore(GDF.INVALID_ITEM, 0);
		gameclientView.setCardCount(GDF.INVALID_CHAIR, 0);
		gameclientView.setUserPassState(GDF.INVALID_ITEM, false);
		gameclientView.setUserCountWarn(GDF.INVALID_ITEM, false);
		gameclientView.setWaitCallScore(false);
		gameclientView.setOutCardData(GDF.INVALID_CHAIR, null, 0);
		gameclientView.setHandCardData(null, 0);
		gameclientView.setBackCard(null, false);
		gameclientView.m_GameEndView.setVisibility(INVISIBLE);

	}
	public static GameClientActivity getInstance() {
		return instance;
	}
//	public void UpdateTableStatus(int nGameStatus)
//	{
//		//桌子状态 
//		 if(nGameStatus==GDF.GAME_SCENE_FREE )
//		 {
//			 //自己没准备!
//			 if (gameclientEngine.getMeUserItem().GetUserStatus() != GDF.US_READY ) 
//			 {
//					gameclientView.m_btStart.setVisibility(View.VISIBLE);
//					gameclientView.m_btChangeDesk.setVisibility(View.VISIBLE);
//					setGameClock(getMeChairID(), IDI_GAME_START, TIME_GAME_START);
//			 }
//		 }
//	}
	
	public void onChangeDesk() {
//		if (gameclientEngine.getMeUserItem().GetUserStatus() != GDF.US_SIT)
//			return;
//		else {
//			GameActivity.onShowDialog("换桌", "请稍后...", false, true, new DialogInterface.OnCancelListener() {
//				@Override
//				public void onCancel(DialogInterface dialog) {
//					GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
//				}
//			});
//			GameActivity.getKernel().autoSitDown();
//		}

		MSG_C2S_CHANGE_TABLE o=new MSG_C2S_CHANGE_TABLE();
		o.cmd=CMD.C2S_CHANGE_TABLE; 
		o.tableID=5555;//5555表示随便找空桌
		 
		GameActivity.getGameActivityInstance().m_gameclient.send(o);
		
	}
	public void UpdateColor(int color)
	{
		if (gameclientView != null)
			gameclientView.UpdateColor(color);
		 
	}
	protected void setGameClock(int chairid, int clockid, int times) {
		m_ClockControl.setGameClock(chairid, clockid, times);
	}

	protected void killGameClock(int clockid) {
		m_ClockControl.killGameClock(clockid);
	}

//	protected void sendSocketData(int subid, ICmd cmd) {
//		GameActivity.getKernel().sendSocketData(CMD.MDM_GF_GAME, subid, cmd);
//	}

	private void sendUserReady() {
		//GameActivity.getKernel().sendUserReady(null, 0);
		MSG_C2S_SAY_READY o=new MSG_C2S_SAY_READY();
		o.cmd=CMD.C2S_SAY_READY;
		o.flag=1; 
		GameActivity.getGameActivityInstance().m_gameclient.send(o);
		
	}

	private int switchViewChairID(int chair) {
		// return GameEngine.getInstance().SwitchViewChairID(chair);
		
		
		return gameclientView.GetViewChairID(gameclientView.playerArray[chair]);
	}
	
	//真实的座位
	private int getMeChairID()
	{
		for(int i=0;i<3;i++){
			if(gameclientView.playerArray[i]== GameActivity.getGameActivityInstance().sm_getme().GetGameID())
			{
				return i;
			}
		}
		
		return -1;
		//return  GameActivity.getGameActivityInstance().sm_getme().ChairID ;//gameclientEngine.GetMeChairID();
	}

	@Override
	public void UpdateGameView() {
		gameclientView.UpdateGameView();

	}

	public void onPlayGameSound(int id) {
		GameActivity.getOptionControl().PlayGameSound(id);
	}

	public void onUserExpression(int chair, int index) {
		gameclientView.onUserExpression(switchViewChairID(chair), index);
		onPlayGameSound(GDF.SOUND_MSG_TIP);
	}

	public void onUserChat(int chair, String info) {
		gameclientView.onUserChat(chair , info);////switchViewChairID(chair), info);
		boolean bplaysound = false;
		 UserItem item = GameActivity.getGameActivityInstance().sm_getuser(gameclientView.playerArray2[chair]); ;//gameclientEngine.getTableUserItem(chair);
		if (item != null && info != null && !info.equals("")) {
			for (int i = 0; i < 10; i++) {
				if (info.equals(m_szFastMsg[i])) {
					bplaysound = true;
					int gender = item.GetGender() == GDF.GENDER_FEMALE ? 1 : 0;
					onPlayGameSound(GDF.SOUND_MSG_M_0 + i * 2 + gender);
					break;
				}
			}
		}
		if (bplaysound == false)
			onPlayGameSound(GDF.SOUND_MSG_TIP);
	}

	public boolean IsLookOnMode() {
		return false;//gameclientEngine.IsAllowLookon();
	}

	public  UserItem getMeUserItem() {
		return  GameActivity.getGameActivityInstance().sm_getme();//getMeUserItem();
	}

	@Override
	public Object OnEventGameMessageAnalysis(int sub, byte[] data) {
		switch (sub) {
			case GDF.SUB_S_GAME_START : {
				CMD_S_GameStart cmd = new CMD_S_GameStart();
				cmd.ReadFromByteArray(data, 0);
				return cmd;
			}
			case GDF.SUB_S_GAME_CONCLUDE : {
				CMD_S_GameConClude cmd = new CMD_S_GameConClude();
				cmd.ReadFromByteArray(data, 0);
				return cmd;
			}
			case GDF.SUB_S_CALL_SCORE : {
				CMD_S_CallScore cmd = new CMD_S_CallScore();
				cmd.ReadFromByteArray(data, 0);
				return cmd;
			}
			case GDF.SUB_S_BANLER_INFO : {
				CMD_S_BankerInfo cmd = new CMD_S_BankerInfo();
				cmd.ReadFromByteArray(data, 0);
				return cmd;
			}
			case GDF.SUB_S_OUT_CARD : {
				CMD_S_OutCard cmd = new CMD_S_OutCard();
				cmd.ReadFromByteArray(data, 0);
				return cmd;
			}
			case GDF.SUB_S_PASS_CARD : {
				CMD_S_PassCard cmd = new CMD_S_PassCard();
				cmd.ReadFromByteArray(data, 0);
				return cmd;
			}

			default :
				break;
		}
		return null;
	}

	@Override
	public Object OnEventSceneMessageAnalysis(int gamestatus, byte[] data) {
		Util.d(TAG, "OnEventSceneMessageAnalysis:"+gamestatus);
		GameScenePackage pack = new GameScenePackage();
		pack.nGameStatus = gamestatus;
		switch (gamestatus) {
			case GDF.GAME_SCENE_FREE : {
				CMD_S_StatusFree cmd = new CMD_S_StatusFree();
				cmd.ReadFromByteArray(data, 0);
				pack.obj = cmd;
				return pack;
			}
			case GDF.GAME_SCENE_CALL : {
				CMD_S_StatusCall cmd = new CMD_S_StatusCall();
				cmd.ReadFromByteArray(data, 0);
				pack.obj = cmd;
				return pack;
			}
			case GDF.GAME_SCENE_PLAY : {
				CMD_S_StatusPlay cmd = new CMD_S_StatusPlay();
				cmd.ReadFromByteArray(data, 0);
				pack.obj = cmd;
				return pack;
			}
			default :
				break;
		}

		return null;
	}

	private void onEventUserClockInfo(int clockid, int chairid, int time) {

		if (gameclientView != null)
			gameclientView.onUpdateClock(switchViewChairID(chairid));
		switch (clockid) {
			case IDI_GAME_START :
				if (chairid == getMeChairID()) {
					if (time == 0) {
						GameActivity.getGameActivityInstance().OnQueryGame();
					}
					if (time > 0 && time < 5) {
						onPlayGameSound(GDF.SOUND_GAME_WARN);
					}
				}
				break;
			case IDI_CALL_POINT :
				if (chairid == getMeChairID() && IsLookOnMode() == false) {
					if (time == 0 || m_bTrustee) {
						onCallScore(0);
					}
					if (time > 0 && time < 5) {
						onPlayGameSound(GDF.SOUND_GAME_WARN);
					}
				}
				break;
			case IDI_OUT_CARD :
				if (chairid == getMeChairID() && IsLookOnMode() == false) {
					if (time == 0 || m_bTrustee) {
						onAutoOutCard();
					}
					if (time > 0 && time < 5) {
						onPlayGameSound(GDF.SOUND_GAME_WARN);
					}
				}
				break;
		}
	}

	@Override
	public boolean OnEventSceneMessage(int nGameStatus, Object obj) {
		if (obj == null)
			return false;
		
	//	GameScenePackage pack = (GameScenePackage) obj;
		Util.d(TAG, "OnEventSceneMessage:"+nGameStatus);
		switch (nGameStatus) {
			case GDF.GAME_SCENE_FREE : {
				CMD_S_StatusFree cmd = (CMD_S_StatusFree) obj;

				m_lCellScore = cmd.lCellScore;
				gameclientView.setCellScore(m_lCellScore);

				if (!IsLookOnMode() && getMeUserItem().GetUserStatus() != GDF.US_READY) {
					gameclientView.m_btStart.setVisibility(VISIBLE);
					setGameClock(getMeChairID(), IDI_GAME_START, TIME_GAME_START);
				}

				GameActivity.dismissDialog();
				return true;
			}
			case GDF.GAME_SCENE_CALL : {
				CMD_S_StatusCall cmd = (CMD_S_StatusCall)   obj;

				// 底注设置
				m_lCellScore = cmd.lCellScore;
				gameclientView.setCellScore(m_lCellScore);

				// 当前玩家
				m_nCurrentUser = cmd.nCurrentUser;

				for (int i = 0; i < GDF.GAME_PLAYER; i++) {
					// 保存手牌数目
					m_HandCardCount[i] = GDF.NORMAL_COUNT;
					gameclientView.setCardCount(i, GDF.NORMAL_COUNT);

					// 叫分设置
					if (cmd.nScoreInfo[i] != 0) {
						gameclientView.setUserCallScore(switchViewChairID(i), cmd.nScoreInfo[i]);
					}
				}
				// 保存手牌
				for (int i = 0; i < GDF.NORMAL_COUNT; i++) {
					m_HandCardData[i] = cmd.nHandCardData[i];
				}
				CGameLogic.SortCardList(m_HandCardData, m_HandCardCount[getMeChairID()], m_nSortCardType);
				// 设置扑克
				gameclientView.m_HandCardControl.onSetCardData(m_HandCardData, m_HandCardCount[getMeChairID()]);

				// 保存分析
				AnalyseData = new int[GDF.MAX_CARDCOUNT];
				System.arraycopy(m_HandCardData, 0, AnalyseData, 0, m_HandCardCount[getMeChairID()]);
				AnalyseCount = m_HandCardCount[getMeChairID()];
				CGameLogic.SortCardList(AnalyseData, AnalyseCount, GDF.ST_ORDER);

				// 叫分按钮
				if (m_nCurrentUser == getMeChairID() && !IsLookOnMode()) {
					gameclientView.m_btScoreOne.setVisibility(cmd.nBankerScore < 1 ? VISIBLE : INVISIBLE);
					gameclientView.m_btScoreTwo.setVisibility(cmd.nBankerScore < 2 ? VISIBLE : INVISIBLE);
					gameclientView.m_btScoreThree.setVisibility(cmd.nBankerScore < 3 ? VISIBLE : INVISIBLE);
					gameclientView.m_btScorePass.setVisibility(VISIBLE);
				}
				gameclientView.setUserCallScore(switchViewChairID(m_nCurrentUser), 0);
				gameclientView.setBackCard(null, true);

				setGameClock(m_nCurrentUser, IDI_CALL_POINT, TIME_CALL_POINT);
				
				GameActivity.dismissDialog();
				return true;
			}
			case GDF.GAME_SCENE_PLAY : {
				CMD_S_StatusPlay cmd = (CMD_S_StatusPlay) obj;
				// 设置变量
				m_nBombCount = cmd.cbBombCount;
				m_nBankerUser = cmd.nBankerUser;
				m_nBankerScore = cmd.cbBankerScore;
				m_nCurrentUser = cmd.nCurrentUser;

				// 出牌变量
				m_nTurnCardCount = cmd.nTurnCardCount;
				for (int i = 0; i < m_nTurnCardCount; i++) {
					m_nTurnCardData[i] = cmd.nTurnCardData[i];
				}

				// 扑克数据
				for (int i = 0; i < GDF.GAME_PLAYER; i++) {
					m_HandCardCount[i] = cmd.nHandCardCount[i];
					gameclientView.setCardCount(switchViewChairID(i), m_HandCardCount[i]);
				}
				for (int i = 0; i < m_HandCardCount[getMeChairID()]; i++) {
					m_HandCardData[i] = cmd.nHandCardData[i];
				}

				// 设置界面
				gameclientView.setCellScore(m_lCellScore);
				gameclientView.onSetBombCount(m_nBombCount);
				gameclientView.setBankerScore(cmd.cbBankerScore);
				gameclientView.setBankerUser(switchViewChairID(m_nBankerUser));
				gameclientView.setBackCard(cmd.nBankerCard, true);

				// 出牌界面
				if (cmd.nTurnWiner != GDF.INVALID_CHAIR) {
					gameclientView.setOutCardData(switchViewChairID(cmd.nTurnWiner), m_nTurnCardData, m_nTurnCardCount);
				}

				// 手牌界面
				CGameLogic.SortCardList(m_HandCardData, m_HandCardCount[getMeChairID()], m_nSortCardType);
				gameclientView.m_HandCardControl.onSetCardData(m_HandCardData, m_HandCardCount[getMeChairID()]);
				if (IsLookOnMode() == false)
					gameclientView.m_HandCardControl.setClickable(true);

				AnalyseData = new int[GDF.MAX_CARDCOUNT];
				System.arraycopy(m_HandCardData, 0, AnalyseData, 0, m_HandCardCount[getMeChairID()]);
				AnalyseCount = m_HandCardCount[getMeChairID()];
				CGameLogic.SortCardList(AnalyseData, AnalyseCount, GDF.ST_ORDER);

				// 控件更新
				if (m_nCurrentUser == getMeChairID() && !IsLookOnMode()) {
					gameclientView.m_btOutCard.setVisibility(View.VISIBLE);
					gameclientView.m_btShootCard.setVisibility(View.VISIBLE);
					gameclientView.m_btTipCard.setVisibility(View.VISIBLE);
					gameclientView.m_btPassCard.setVisibility(m_nTurnCardCount > 0 ? View.VISIBLE : View.INVISIBLE);
				}

				// 设置时间
				setGameClock(m_nCurrentUser, IDI_OUT_CARD, TIME_OUT_CARD);
				GameActivity.dismissDialog();
				return true;
			}
			default :
				break;
		}
		return false;
	}

	@Override	//smw
	public boolean OnEventGameMessage(int sub, Object obj) {

		switch (sub) {
		//	case GDF.SUB_S_GAME_START :
		//		return OnSubGameStart(obj);
		//	case GDF.SUB_S_GAME_CONCLUDE :
		//		return OnSubGameConclude(obj);
		//	case GDF.SUB_S_CALL_SCORE :
		//		return OnSubCallScore(obj);
		//	case GDF.SUB_S_BANLER_INFO :
		//		return OnSubBankerInfo(obj);
		//	case GDF.SUB_S_OUT_CARD :
		//		return OnSubOutCard(obj);
		//	case GDF.SUB_S_PASS_CARD :
		//		return OnSubPassCard(obj);
			default :
				break;
		}
		return false;
	}
	// 游戏开始 发牌了
	public boolean OnSubGameStart(MSG_S_GameStart cmd) {
		//GameEngine.getInstance().onInitGameInfo(GDF.GAME_PLAYER);

		m_nCurrentUser = cmd.nCurrentUser;
		m_nBombCount = 0;
		System.arraycopy(cmd.nCardData, 0, m_HandCardData, 0, GDF.NORMAL_COUNT);
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_HandCardCount[i] = GDF.NORMAL_COUNT;
		}

		if (IsLookOnMode()) {
			onGameStart(false);
		}
		onPlayGameSound(GDF.SOUND_GAME_START);

		int startuser = switchViewChairID(cmd.nStartUser);
		gameclientView.onStopSendCard();
		for (int i = 0; i < GDF.NORMAL_COUNT; i++) {
			for (int j = 0; j < GDF.GAME_PLAYER; j++) {
				int viewid = switchViewChairID((cmd.nStartUser + j) % GDF.GAME_PLAYER);
				gameclientView.addSendCardItem(viewid, viewid == GameClientView.MY_VIEW_ID ? m_HandCardData[i] : 0xff);
			}
		}
		m_bSendCard = true;
		gameclientView.onStartSendCard();
		return true;
	}

	public boolean OnSubCallScore(CMD_S_CallScore cmd) {
 
		killGameClock(IDI_CALL_POINT);
		if (m_bSendCard) {
			onSendCardFinish();
		}

		m_nCurrentUser = cmd.nCurrentUser;

		int viewcallid = switchViewChairID(cmd.nCallScoreUser);
		if (cmd.nCallScoreUser != getMeChairID() || IsLookOnMode()) {
			gameclientView.setUserCallScore(viewcallid, cmd.nUserCallScore);
			PlayActionSound(IDI_CALL_POINT, cmd.nUserCallScore, cmd.nCallScoreUser);
		}

		if (m_nCurrentUser != GDF.INVALID_CHAIR) {
			if (m_nCurrentUser == getMeChairID()) {
				gameclientView.m_btScoreOne.setVisibility(cmd.nCurrentScore < 1 ? VISIBLE : INVISIBLE);
				gameclientView.m_btScoreTwo.setVisibility(cmd.nCurrentScore < 2 ? VISIBLE : INVISIBLE);
				gameclientView.m_btScoreThree.setVisibility(cmd.nCurrentScore < 3 ? VISIBLE : INVISIBLE);
				gameclientView.m_btScorePass.setVisibility(VISIBLE);
			}
			gameclientView.setWaitCallScore(m_nCurrentUser != getMeChairID());
			setGameClock(m_nCurrentUser, getMeChairID(), TIME_CALL_POINT);
		} else {
			gameclientView.setWaitCallScore(false);
		}

		gameclientView.postInvalidate();

		return true;
	}

	public boolean OnSubBankerInfo(CMD_S_BankerInfo cmd) {
 
		killGameClock(IDI_CALL_POINT);
		if (m_bSendCard) {
			onSendCardFinish();
		}

		int nMeChairID = getMeChairID();

		m_nBankerScore = cmd.nBankerScore;
		m_nBankerUser = cmd.nBankerUser;
		m_nCurrentUser = cmd.nCurrentUser;

		if (IsLookOnMode() == false)
			gameclientView.m_HandCardControl.setClickable(true);
		gameclientView.setWaitCallScore(false);
		gameclientView.setUserCallScore(GDF.INVALID_ITEM, 0);
		gameclientView.setBankerScore(cmd.nBankerScore);
		gameclientView.setBankerUser(switchViewChairID(m_nBankerUser));

		gameclientView.setBackCard(cmd.nBankerCard, true);

		m_HandCardCount[m_nBankerUser] += 3;
		gameclientView.setCardCount(switchViewChairID(m_nBankerUser), 20);

		if (m_nBankerUser == nMeChairID) {
			for (int i = 0; i < 3; i++) {
				m_HandCardData[GDF.NORMAL_COUNT + i] = cmd.nBankerCard[i];
			}
			CGameLogic.SortCardList(m_HandCardData, m_HandCardCount[m_nBankerUser], m_nSortCardType);
			gameclientView.setHandCardData(m_HandCardData, m_HandCardCount[nMeChairID]);
			gameclientView.m_btOutCard.setVisibility(VISIBLE);
			gameclientView.m_btPassCard.setVisibility(INVISIBLE);
			gameclientView.m_btTipCard.setVisibility(VISIBLE);
			gameclientView.m_btShootCard.setVisibility(VISIBLE);
		}

		// 保存分析
		System.arraycopy(m_HandCardData, 0, AnalyseData, 0, m_HandCardCount[nMeChairID]);
		AnalyseCount = m_HandCardCount[nMeChairID];
		if (m_nSortCardType != GDF.ST_ORDER)
			CGameLogic.SortCardList(AnalyseData, m_HandCardCount[nMeChairID], GDF.ST_ORDER);

		setGameClock(m_nCurrentUser, IDI_OUT_CARD, TIME_OUT_CARD);
		gameclientView.postInvalidate();
		return true;
	}

	public boolean OnSubOutCard(CMD_S_OutCard cmd) {
 
		killGameClock(IDI_OUT_CARD);
		//切换座位id为viewid
		int viewid = switchViewChairID(cmd.nOutCardUser);
		
		//出牌的不是自己
		if (cmd.nOutCardUser != getMeChairID()) {
			m_HandCardCount[cmd.nOutCardUser] -= cmd.nCardCount;//对方牌count-

			gameclientView.setUserPassState(viewid, false);//对方passflag false
			gameclientView.setOutCardData(viewid, cmd.nCardData, cmd.nCardCount);
			gameclientView.setCardCount(viewid, m_HandCardCount[cmd.nOutCardUser]);
			if (m_HandCardCount[cmd.nOutCardUser] < 3)
				gameclientView.setUserCountWarn(viewid, true);
			int nType = CGameLogic.GetCardType(cmd.nCardData, cmd.nCardCount);
			//炸弹 火箭
			if (nType == GDF.CT_BOME_CARD || nType == GDF.CT_MISSILE_CARD) {
				m_nBombCount++;
				gameclientView.onSetBombCount(m_nBombCount);
				if (nType == GDF.CT_MISSILE_CARD)	//导弹!
					gameclientView.onStartRocketAnim();
			} else {
				boolean bPlane = false;
				if (nType == GDF.CT_3_LINE && cmd.nCardCount > 3)
					bPlane = true;
				if (nType == GDF.CT_3_LINE_1 && cmd.nCardCount > 4)
					bPlane = true;
				if (nType == GDF.CT_3_LINE_2 && cmd.nCardCount > 5)
					bPlane = true;
				if (bPlane) {
					gameclientView.onStartPlaneAnim();
					nType = GDF.CT_MISSILE_CARD;
				}
				PlayCardTypeSound(nType, cmd.nCardData[0], 0, cmd.nOutCardUser, m_nTurnCardCount == 0);
			}
		}

		m_nCurrentUser = cmd.nCurrentUser;
		 

		if (m_nCurrentUser != GDF.INVALID_CHAIR  &&  m_nCurrentUser<=2 ) {
			viewid = switchViewChairID(m_nCurrentUser);
			gameclientView.setUserPassState(viewid, false);
			gameclientView.setOutCardData(viewid, null, 0);

			if (m_nCurrentUser == getMeChairID()) {
				gameclientView.m_btOutCard.setVisibility(VISIBLE);
				gameclientView.m_btPassCard.setVisibility(cmd.nOutCardUser != getMeChairID() ? VISIBLE : INVISIBLE);
				gameclientView.m_btTipCard.setVisibility(VISIBLE);
				gameclientView.m_btShootCard.setVisibility(VISIBLE);
			}

			// 出牌记录
			if (m_nCurrentUser == cmd.nOutCardUser) {
				m_nTurnCardCount = 0;
			} else {
				m_nTurnCardCount = cmd.nCardCount;
				System.arraycopy(cmd.nCardData, 0, m_nTurnCardData, 0, m_nTurnCardCount);
			}

			setGameClock(m_nCurrentUser, IDI_OUT_CARD, TIME_OUT_CARD);
		}
		gameclientView.postInvalidate();

		return true;
	}
	public boolean OnSubPassCard(CMD_S_PassCard cmd) {
	 
		killGameClock(IDI_OUT_CARD);
		// 过牌界面
		int viewid = switchViewChairID(cmd.nPassCardUser);
		if (cmd.nPassCardUser != getMeChairID() || IsLookOnMode()) {
			gameclientView.setOutCardData(viewid, null, 0);
			gameclientView.setUserPassState(viewid, true);
			PlayActionSound(IDI_OUT_CARD, 0, getMeChairID());
		}

		// 一轮结束
		if (cmd.nTurnOver == 1) {
			m_nTurnCardCount = 0;
			m_nTurnCardData = new int[GDF.MAX_CARDCOUNT];
		}

		// 当前用户
		m_nCurrentUser = cmd.nCurrentUser;

		// 当前用户界面
		if (m_nCurrentUser != GDF.INVALID_CHAIR) {
			viewid = switchViewChairID(m_nCurrentUser);

			gameclientView.setUserPassState(viewid, false);
			gameclientView.setOutCardData(viewid, null, 0);

			if (m_nCurrentUser == getMeChairID() && !IsLookOnMode()) {
				gameclientView.m_btOutCard.setVisibility(VISIBLE);
				gameclientView.m_btPassCard.setVisibility(cmd.nTurnOver == 1 ? INVISIBLE : VISIBLE);
				gameclientView.m_btTipCard.setVisibility(VISIBLE);
				gameclientView.m_btShootCard.setVisibility(VISIBLE);
			}

			setGameClock(m_nCurrentUser, IDI_OUT_CARD, TIME_OUT_CARD);
		}

		gameclientView.postInvalidate();

		return true;
	}

	//游戏结束 结算积分。
	public boolean OnSubGameConclude(CMD_S_GameConClude cmd) {
		int nMeChairID = getMeChairID();
		
		//单机 玩家的积分统计----------------------------------
		int Score=(int) (GameActivity.getScore()+cmd.lGameScore[nMeChairID]);
		GameActivity.setScore(Score);//每局都保存下玩家自己的积分。
		//------------------------------
		
		killGameClock(0);
 

		// 停止发牌
		if (m_bSendCard) {
			gameclientView.onStopSendCard();
		}

		if (m_bTrustee)
			onTrustee();

		// 按钮设置
		gameclientView.m_btScoreOne.setVisibility(INVISIBLE);
		gameclientView.m_btScoreThree.setVisibility(INVISIBLE);
		gameclientView.m_btScoreTwo.setVisibility(INVISIBLE);
		gameclientView.m_btScorePass.setVisibility(INVISIBLE);

		gameclientView.m_btOutCard.setVisibility(INVISIBLE);
		gameclientView.m_btTipCard.setVisibility(INVISIBLE);
		gameclientView.m_btPassCard.setVisibility(INVISIBLE);
		gameclientView.m_btShootCard.setVisibility(INVISIBLE);

		 
		gameclientView.setUserPassState(GDF.INVALID_CHAIR, false);
		gameclientView.m_HandCardControl.setClickable(false);
		gameclientView.setHandCardData(null, 0);

		// 剩余手牌显示
		int index = 0;
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			int viewid = switchViewChairID(i);
			if (cmd.nCardCount[i] < 1) {
				gameclientView.setOutCardData(viewid, null, 0);
			} else {
				int carddata[] = new int[cmd.nCardCount[i]];
				System.arraycopy(cmd.nHandCardData, index, carddata, 0, cmd.nCardCount[i]);
				gameclientView.setOutCardData(viewid, carddata, cmd.nCardCount[i]);
				index += cmd.nCardCount[i];
			}
		}

		if (cmd.lGameScore[nMeChairID] != 0) {
			if (cmd.nChunTian == 1 || cmd.nFanChunTian == 1) {
				gameclientView.m_GameEndSpring.setBackgroundResource(cmd.nChunTian == 1 ? R.drawable.game_end_spring : R.drawable.game_end_unspring);
				gameclientView.m_GameEndSpring.setVisibility(VISIBLE);
			} else {
				gameclientView.m_GameEndSpring.setVisibility(INVISIBLE);
			}
			//自己赢了积分
			if (cmd.lGameScore[nMeChairID] > 0) {
				gameclientView.m_GameEndWord.setBackgroundResource(R.drawable.game_end_word_win);
				if (nMeChairID == m_nBankerUser)//地主
					gameclientView.m_GameEndFlag.setBackgroundResource(R.drawable.game_flag_win_0);
				else//农民
					gameclientView.m_GameEndFlag.setBackgroundResource(R.drawable.game_flag_win_1);
			} else {
				//自己输了积分
				gameclientView.m_GameEndWord.setBackgroundResource(R.drawable.game_end_word_lose);
				if (nMeChairID == m_nBankerUser)
					gameclientView.m_GameEndFlag.setBackgroundResource(R.drawable.game_flag_lose_0);
				else
					gameclientView.m_GameEndFlag.setBackgroundResource(R.drawable.game_flag_lose_1);
			}

			gameclientView.m_GameEndBankScore.setText("底分  X " + m_nBankerScore);
			gameclientView.m_GameEndBomb.setText("炸弹  X " + cmd.nBombCount);
			gameclientView.m_GameEndScore.setText(cmd.lGameScore[nMeChairID] + "");

			gameclientView.m_GameEndView.setVisibility(VISIBLE);
		}

		gameclientView.m_btStart.setVisibility(VISIBLE);
		setGameClock(getMeChairID(), IDI_GAME_START, TIME_GAME_START);
		gameclientView.postInvalidate();
		return true;
	}
	//点准备 游戏开始 
	public boolean onGameStart(boolean sendready) {
		killGameClock(IDI_GAME_START);
		gameclientView.m_btStart.setVisibility(INVISIBLE);

		onReSetGame();

		if (sendready)
			sendUserReady();

		gameclientView.postInvalidate();

		return true;
	}
	//出牌
	public boolean onOutCard() {
		int nMeChairID = getMeChairID();
		if (m_nCurrentUser != nMeChairID)
			return false;

		// 获取弹起牌
		int nOutCard[] = new int[GDF.MAX_CARDCOUNT];
		int nCount = gameclientView.m_HandCardControl.getShootCard(nOutCard, true);

		if (nCount > 0) {
			CGameLogic.SortCardList(nOutCard, nCount, GDF.ST_ORDER);
			// 获取牌型
			int nType = CGameLogic.GetCardType(nOutCard, nCount);
			if (nType == GDF.CT_ERROR) {
				Toast.makeText(GameActivity.getInstance(), "请选择正确牌型", Toast.LENGTH_SHORT).show();
				return false;
			}
			// 判断出牌
			if (m_nTurnCardCount == 0 || (m_nTurnCardCount > 0 && CGameLogic.CompareCard(m_nTurnCardData, nOutCard, m_nTurnCardCount, nCount))) {
				killGameClock(IDI_OUT_CARD);
				gameclientView.m_btOutCard.setVisibility(INVISIBLE);
				gameclientView.m_btPassCard.setVisibility(INVISIBLE);
				gameclientView.m_btTipCard.setVisibility(INVISIBLE);
				gameclientView.m_btShootCard.setVisibility(INVISIBLE);

				// 整理手牌
				int nNewData[] = new int[GDF.MAX_CARDCOUNT];
				int nIndex = 0;
				for (int i = 0; i < m_HandCardCount[nMeChairID]; i++) {
					for (int j = 0; j < nCount; j++) {
						if (m_HandCardData[i] == nOutCard[j]) {
							m_HandCardData[i] = 0;
						}
					}
					if (m_HandCardData[i] != 0) {
						nNewData[nIndex++] = m_HandCardData[i];
					}
				}
				m_HandCardCount[nMeChairID] = nIndex;
				m_HandCardData = new int[GDF.MAX_CARDCOUNT];
				System.arraycopy(nNewData, 0, m_HandCardData, 0, nIndex);
				CGameLogic.SortCardList(m_HandCardData, nIndex, m_nSortCardType);
				gameclientView.setHandCardData(m_HandCardData, m_HandCardCount[nMeChairID]);
				gameclientView.setCardCount(GameClientView.MY_VIEW_ID, nIndex);
				gameclientView.setUserPassState(GameClientView.MY_VIEW_ID, false);
				gameclientView.setOutCardData(GameClientView.MY_VIEW_ID, nOutCard, nCount);

				// 炸弹
				if (nType == GDF.CT_BOME_CARD || nType == GDF.CT_MISSILE_CARD) {
					// 更新次数
					m_nBombCount++;
					gameclientView.m_txt_BombTimes.setText("炸弹X" + m_nBombCount);
					// 火箭动画
					if (nType == GDF.CT_MISSILE_CARD)
						gameclientView.onStartRocketAnim();
					if (nType == GDF.CT_BOME_CARD)
						gameclientView.onStartBombAnim();
				} else {
					// 飞机判断
					boolean bPlane = false;
					if (nType == GDF.CT_3_LINE && nCount > 3)
						bPlane = true;
					if (nType == GDF.CT_3_LINE_1 && nCount > 4)
						bPlane = true;
					if (nType == GDF.CT_3_LINE_2 && nCount > 5)
						bPlane = true;
					// 飞机动画
					if (bPlane) {
						gameclientView.onStartPlaneAnim();
						nType = GDF.CT_PLANE;
					}
				}
				PlayCardTypeSound(nType, nOutCard[0], 0, nMeChairID, m_nTurnCardCount == 0);

				// 保存分析
				System.arraycopy(m_HandCardData, 0, AnalyseData, 0, m_HandCardCount[nMeChairID]);
				AnalyseCount = m_HandCardCount[nMeChairID];
				if (m_nSortCardType != GDF.ST_ORDER)
					CGameLogic.SortCardList(AnalyseData, AnalyseCount, GDF.ST_ORDER);

				SkipCount = 0;

				// 发送消息
				CMD_C_OutCard cmd = new CMD_C_OutCard();
				cmd.cmd=CMD.C2S_OUT_CARD;
				cmd.nCardCount = nCount;
				for (int i = 0; i < nCount; i++) {
					cmd.nCardData[i] = nOutCard[i];
				}
				
				GameActivity.getGameActivityInstance().m_gameclient.send(cmd);
				//sendSocketData(GDF.SUB_C_OUT_CARD, cmd);
				gameclientView.postInvalidate();
			}
		}

		return true;
	}
	//提示
	public boolean onTipCard() {

		int outcard[] = new int[GDF.MAX_CARDCOUNT];
		int ncount = 0;
		if (SkipCount != 0) {
			ncount = CGameLogic.SeachOutCard(AnalyseData, AnalyseCount, SkipCardData, SkipCount, outcard);
		} else {
			ncount = CGameLogic.SeachOutCard(AnalyseData, AnalyseCount, m_nTurnCardData, m_nTurnCardCount, outcard);
		}
		if (ncount > 0) {
			SkipCount = ncount;
			System.arraycopy(outcard, 0, SkipCardData, 0, SkipCount);
			gameclientView.m_HandCardControl.setAllCardShoot(false);
			gameclientView.m_HandCardControl.setShootCard(outcard, ncount, true);

			SkipCount = ncount;
			System.arraycopy(outcard, 0, SkipCardData, 0, SkipCount);
			gameclientView.UpdateCard();
		} else {
			Toast.makeText(GameActivity.getInstance(), "未找到合适的牌", Toast.LENGTH_SHORT).show();
			SkipCount = m_nTurnCardCount;
			System.arraycopy(m_nTurnCardData, 0, SkipCardData, 0, SkipCount);
		}
		return true;
	}
	//不出牌
	public boolean onPassCard() {

		if (m_nCurrentUser != getMeChairID())
			return false;
		killGameClock(IDI_OUT_CARD);
		gameclientView.m_btOutCard.setVisibility(INVISIBLE);
		gameclientView.m_btPassCard.setVisibility(INVISIBLE);
		gameclientView.m_btTipCard.setVisibility(INVISIBLE);
		gameclientView.m_btShootCard.setVisibility(INVISIBLE);

		gameclientView.m_HandCardControl.setAllCardShoot(false);
		gameclientView.setOutCardData(GameClientView.MY_VIEW_ID, null, 0);
		gameclientView.setUserPassState(GameClientView.MY_VIEW_ID, true);

		PlayActionSound(IDI_OUT_CARD, 0, getMeChairID());

		SkipCount = 0;

		//sendSocketData(GDF.SUB_C_PASS_CARD, null);
		CMD_C_PassCard p=new CMD_C_PassCard();
		p.cmd=CMD.C2S_PASS_CARD;
		GameActivity.getGameActivityInstance().m_gameclient.send(p);
		
		gameclientView.postInvalidate();

		return true;
	}
	//抢地主
	public boolean onCallScore(int index) {
		killGameClock(IDI_CALL_POINT);
		gameclientView.m_btScoreOne.setVisibility(INVISIBLE);
		gameclientView.m_btScoreTwo.setVisibility(INVISIBLE);
		gameclientView.m_btScoreThree.setVisibility(INVISIBLE);
		gameclientView.m_btScorePass.setVisibility(INVISIBLE);

		gameclientView.setUserCallScore(GameClientView.MY_VIEW_ID, index);

		PlayActionSound(IDI_CALL_POINT, index, GameClientView.MY_VIEW_ID);

		CMD_C_CallScore cmd = new CMD_C_CallScore();
		cmd.cmd=CMD.C2S_CallScore;
		cmd.nCallScore = index;

		//sendSocketData(GDF.SUB_C_CALL_SCORE, cmd); 
		GameActivity.getGameActivityInstance().m_gameclient.send(cmd);
		
		gameclientView.postInvalidate();

		return true;
	}
	//自动出牌
	public void onAutoOutCard() {
		int nMeChairID = getMeChairID();
		if (m_nCurrentUser != nMeChairID)
			return;

		killGameClock(IDI_OUT_CARD);
		gameclientView.m_btOutCard.setVisibility(INVISIBLE);
		gameclientView.m_btPassCard.setVisibility(INVISIBLE);
		gameclientView.m_btTipCard.setVisibility(INVISIBLE);
		gameclientView.m_btShootCard.setVisibility(INVISIBLE);

		int nOutCard[] = new int[GDF.MAX_CARDCOUNT];
		int nCount = CGameLogic.SeachOutCard(AnalyseData, AnalyseCount, m_nTurnCardData, m_nTurnCardCount, nOutCard);

		if (nCount > 0) {
			// 整理手牌
			int nNewData[] = new int[GDF.MAX_CARDCOUNT];
			int nIndex = 0;
			for (int i = 0; i < m_HandCardCount[nMeChairID]; i++) {
				for (int j = 0; j < nCount; j++) {
					if (m_HandCardData[i] == nOutCard[j]) {
						m_HandCardData[i] = 0;
					}
				}
				if (m_HandCardData[i] != 0) {
					nNewData[nIndex++] = m_HandCardData[i];
				}
			}
			m_HandCardCount[nMeChairID] = nIndex;
			m_HandCardData = new int[GDF.MAX_CARDCOUNT];
			System.arraycopy(nNewData, 0, m_HandCardData, 0, nIndex);
			CGameLogic.SortCardList(m_HandCardData, nIndex, m_nSortCardType);
			gameclientView.setHandCardData(m_HandCardData, m_HandCardCount[nMeChairID]);
			gameclientView.setCardCount(GameClientView.MY_VIEW_ID, nIndex);
			gameclientView.setUserPassState(GameClientView.MY_VIEW_ID, false);
			gameclientView.setOutCardData(GameClientView.MY_VIEW_ID, nOutCard, nCount);

			int nType = CGameLogic.GetCardType(nOutCard, nCount);
			// 炸弹
			if (nType == GDF.CT_BOME_CARD || nType == GDF.CT_MISSILE_CARD) {
				// 更新次数
				m_nBombCount++;
				gameclientView.m_txt_BombTimes.setText("炸弹X" + m_nBombCount);
				// 火箭动画
				if (nType == GDF.CT_MISSILE_CARD)
					gameclientView.onStartRocketAnim();
				if (nType == GDF.CT_BOME_CARD)
					gameclientView.onStartBombAnim();
			} else {
				// 飞机判断
				boolean bPlane = false;
				if (nType == GDF.CT_3_LINE && nCount > 3)
					bPlane = true;
				if (nType == GDF.CT_3_LINE_1 && nCount > 4)
					bPlane = true;
				if (nType == GDF.CT_3_LINE_2 && nCount > 5)
					bPlane = true;
				// 飞机动画
				if (bPlane) {
					gameclientView.onStartPlaneAnim();
					nType = GDF.CT_PLANE;
				}
			}
			PlayCardTypeSound(nType, nOutCard[0], 0, nMeChairID, m_nTurnCardCount == 0);
			// 保存分析
			System.arraycopy(m_HandCardData, 0, AnalyseData, 0, m_HandCardCount[nMeChairID]);
			AnalyseCount = m_HandCardCount[nMeChairID];
			if (m_nSortCardType != GDF.ST_ORDER)
				CGameLogic.SortCardList(AnalyseData, AnalyseCount, GDF.ST_ORDER);

			SkipCount = 0;

			// 发送消息
			CMD_C_OutCard cmd = new CMD_C_OutCard();
			cmd.cmd=CMD.C2S_OUT_CARD;
			cmd.nCardCount = nCount;
			for (int i = 0; i < nCount; i++) {
				cmd.nCardData[i] = nOutCard[i];
			}
		//	sendSocketData(GDF.SUB_C_OUT_CARD, cmd);
			
			GameActivity.getGameActivityInstance().m_gameclient.send(cmd);
			
			gameclientView.postInvalidate();
		} else {
			onPassCard();
		}
	}

	public void onTrustee() {
		m_bTrustee = !m_bTrustee;
		gameclientView.onUpdateUserFace();
	}

	public void updateBattery() {
		gameclientView.updateBattery();

	}

	public void onRelease() {
		gameclientView.onRelease();
		killGameClock(0);

	}

	private void PlayActionSound(int main, int sub, int chair) {
		 UserItem item = GameActivity.getGameActivityInstance().sm_getuser(chair);;//gameclientEngine.getTableUserItem(chair);
		int nGender = 0;
		if (item != null) {
			nGender = (item.GetGender() == GDF.GENDER_FEMALE ? 1 : 0);
		}
		switch (main) {
			case IDI_CALL_POINT :
				if (sub > 0 && sub < 4)
					onPlayGameSound(GDF.SOUND_CS_M_1 + (sub - 1) * 2 + nGender);
				else
					onPlayGameSound(GDF.SOUND_CS_M_0 + nGender);
				break;
			case IDI_OUT_CARD :
				onPlayGameSound(GDF.SOUND_PASS_M_0 + (rand.nextInt(2) * 2 + nGender));
				break;
			default :
				break;
		}

	}
	//real chair
	private void PlayCardTypeSound(int type, int data, int count, int chair, boolean bnewturn) {
		
		 UserItem item = GameActivity.getGameActivityInstance().sm_getuser(gameclientView.playerArray[chair]);;//gameclientEngine.getTableUserItem(chair);
		int nGender = 0;
		if (item != null) {
			nGender = (item.GetGender() == GDF.GENDER_FEMALE ? 1 : 0);
		}
		switch (type) {
			case GDF.CT_1 :
				onPlaySingleCard(data, nGender);
				break;
			case GDF.CT_2 :
				if (bnewturn)
					onPlayGameSound(GDF.SOUND_TYPE_M_DUI + nGender);
				else
					onPlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_3 :
				if (bnewturn)
					onPlayGameSound(GDF.SOUND_TYPE_M_SAN + nGender);
				else
					onPlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_1_LINE :
				if (bnewturn)
					onPlayGameSound(GDF.SOUND_TYPE_M_DANSUN + nGender);
				else
					onPlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_2_LINE :
				if (bnewturn)
					onPlayGameSound(GDF.SOUND_TYPE_M_DUISUN + nGender);
				else
					onPlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_3_LINE :
				if (bnewturn)
					onPlayGameSound(GDF.SOUND_TYPE_M_SANSUN + nGender);
				else
					onPlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_3_LINE_1 :
				if (bnewturn) {
					onPlayGameSound(GDF.SOUND_TYPE_M_SANDAIYI + nGender);
				} else
					onPlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_3_LINE_2 :
				if (bnewturn) {
					onPlayGameSound(GDF.SOUND_TYPE_M_SANDAIER + nGender);
				} else
					onPlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_4_LINE_1 :

				break;
			case GDF.CT_4_LINE_2 :
				if (bnewturn)
					onPlayGameSound(GDF.SOUND_TYPE_M_SIDAIER + nGender);
				else
					onPlayGameSound(GDF.SOUND_YA_M_0 + 2 * rand.nextInt(2) + nGender);
				break;
			case GDF.CT_BOME_CARD :
				onPlayGameSound(GDF.SOUND_GAME_BOMB);
				break;
			case GDF.CT_MISSILE_CARD :
				onPlayGameSound(GDF.SOUND_TYPE_M_HUIJIAN + nGender);
				break;
			case GDF.CT_PLANE :
				onPlayGameSound(GDF.SOUND_GAME_PLANE);
				break;
			default :
				break;
		}
	}

	private void onPlaySingleCard(int data, int gender) {
		int index = data & GDF.MASK_VALUE;
		if (index > 0 && index < 16)
			onPlayGameSound(GDF.SOUND_CARD_M_1 + (index - 1) * 2 + gender);
	}

	public void onSubSystemMessage(String string, boolean system) {
		if (gameclientView != null)
			gameclientView.onSubSystemMessage(string, system);

	}

	public void onUpdateScore() {
		if (gameclientView != null)
			gameclientView.UpdateGameView();
	}

	public boolean onKeyDown(int keycode, KeyEvent event) {
		Util.v(TAG,"onKeyDown");
		switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_MENU : {
				
				return ((GameActivity) GameActivity.getInstance()).onShowOptionMenu(null);
			}
			case KeyEvent.KEYCODE_BACK : {
				Util.v(TAG,"onKeyDown KEYCODE_BACK");
				GameActivity.getGameActivityInstance().OnQueryGame();
				//GameActivity.getGameActivityInstance().m_gameclient.close();
				//GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);//大厅界面
				return true;
			}
		}
		return super.onKeyDown(keycode, event);
	}
}
