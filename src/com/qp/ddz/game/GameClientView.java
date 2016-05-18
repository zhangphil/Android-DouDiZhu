package com.qp.ddz.game;
/*
*
* 购买完整源码联系 q344717871
* 
* */

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.qp.ddz.GameActivity;
import com.qp.ddz.utility.BtBackGround;
import com.qp.ddz.utility.CImage;
import com.qp.ddz.utility.GifHep;
import com.qp.ddz.utility.UserClock;
import com.qp.lib.cmd.CMD;
import com.qp.lib.interface_ex.kernel.IKernel;
import com.qp.lib.utility.Util;
import com.qp.ddz.R;
import com.qp.ddz.BroadcastReceiver.BatteryLevelRcvr;
import com.qp.ddz.define.GDF;
import com.qp.ddz.game.assist.SendItem;
import com.smw.cmd.game.MSG_C2S_CHAT;
import com.smw.net.UserItem;

public class GameClientView extends ViewGroup implements OnClickListener {

	@SuppressWarnings("unused")
	private static final String	TAG					= "GameClientView";
	public static GameClientView instance;
	
	public static int playerArray[]=new int[3];//3人唯一id
	/** 按钮标示 **/
	public static final int		IDC_GAME_START		= 100;
	public static final int		IDC_CHANGE_DESK		= 101;
	public static final int		IDC_GAME_CHAT		= 102;

	public static final int		IDC_GAME_HORN		= 103;
	public static final int		IDC_GAME_HORNRECORD	= 104;

	public static final int		IDC_MENU			= 110;
	public static final int		IDC_TRUSTEE			= 111;

	public static final int		IDC_SCORE_ONE		= 112;
	public static final int		IDC_SCORE_TWO		= 113;
	public static final int		IDC_SCORE_THREE		= 114;
	public static final int		IDC_SCORE_PASS		= 115;

	public static final int		IDC_OUT_CARD		= 116;
	public static final int		IDC_PASS_CARD		= 117;
	public static final int		IDC_TIP_CARD		= 118;
	public static final int		IDC_SHOOT_CARD		= 119;

	public static final int		IDC_USER_HEAD_0		= 120;
	public static final int		IDC_USER_HEAD_1		= 121;
	public static final int		IDC_USER_HEAD_2		= 122;

	public static final int		IDC_END_SCORE_CLOSE	= 123;

	/** 我的视图位置 **/
	public static final int		MY_VIEW_ID			= 1;

	/** 发牌步数 **/
	public static final int		MAX_STEP			= 5;

	/** 牌类间隔 **/
	public static int			m_nCardSpaceX_1		= 32;
	public static int			m_nCardSpaceX_2		= 25;
	public static int			m_nCardSpaceY_1		= 30;
	public static int			m_nCardSpaceY_2		= 30;

	/** 游戏引用 **/
	GameClientActivity			gameClientActivity;

	/** 游戏按钮 **/
	public Button				m_btStart;
	public Button				m_btScoreOne;
	public Button				m_btScoreTwo;
	public Button				m_btScoreThree;
	public Button				m_btScorePass;
	public Button				m_btOutCard;
	public Button				m_btPassCard;
	public Button				m_btTipCard;
	public Button				m_btShootCard;

	/** 顶部控件 **/
	public View					TopInfoview;
	public ImageView[]			m_BackCard			= new ImageView[3];
	public ImageView			m_Battery;
	public TextView				m_txt_BatteryPer;
	public TextView				m_txt_BankTimes;
	public TextView				m_txt_BombTimes;
	public TextView				m_txt_SysTimes;

	public Button				m_btMenu;
	public Button				m_btChangeDesk;
	public Button				m_btTrustee;
	public Button				m_btChat;

	// 喇叭相关
	public Button				m_btHorn;
	public Button				m_btHornRecord;
	public EditText				m_txt_HornInfo;

	/** 头像控件 **/
	View[]						m_HeadView			= new View[GDF.GAME_PLAYER];
	TextView[]					m_txt_Score			= new TextView[GDF.GAME_PLAYER];
	TextView[]					m_txt_NickName		= new TextView[GDF.GAME_PLAYER];
	ImageView[]					m_img_Head			= new ImageView[GDF.GAME_PLAYER];

	/** 聊天控件 **/
	View[]						UserChat			= new View[GDF.GAME_PLAYER];
	TextView[]					UserChatInfo		= new TextView[GDF.GAME_PLAYER];

	/** 表情控件 **/
	GifHep[]					m_UserExpression	= new GifHep[GDF.GAME_PLAYER];

	/** 图片资源 **/
	CImage						m_ImageReady		= new CImage();
	CImage						m_ImageClock		= new CImage();
	CImage						m_ImageTimeNum		= new CImage();
	CImage						m_ImageWaitBank		= new CImage();
	CImage						m_ImageCallScore	= new CImage();
	CImage						m_ImagePass			= new CImage();
	CImage						m_ImageCardNum		= new CImage();
	CImage						m_ImageFlagLand		= new CImage();
	CImage						m_ImageFlagFramer	= new CImage();

	CardRes						m_CardRes			= new CardRes();
	MidCardRes					m_MidCardRes		= new MidCardRes();

	/** 位置信息 **/
	Point[]						m_ptUserHead		= new Point[GDF.GAME_PLAYER];
	Point[]						m_ptUserClock		= new Point[GDF.GAME_PLAYER];
	Point[]						m_ptUserReady		= new Point[GDF.GAME_PLAYER];
	Point[]						m_ptOutCard			= new Point[GDF.GAME_PLAYER];
	Point[]						m_ptCardCount		= new Point[GDF.GAME_PLAYER];
	Point[]						m_ptPass			= new Point[GDF.GAME_PLAYER];
	Point[]						m_ptPoint			= new Point[GDF.GAME_PLAYER];

	/** 发牌位置 **/
	Point[]						m_ptSendDst			= new Point[GDF.GAME_PLAYER];
	Point						m_ptSendSrc			= new Point();

	/** 发牌相关 **/
	ArrayList<SendItem>			m_SendCardList		= new ArrayList<SendItem>();
	int							m_nSendCount;
	Thread						m_SendThread;
	boolean						m_bSendCard;

	/** 手牌控件 **/
	public HandCardControl		m_HandCardControl	= new HandCardControl();

	/** 动画控件 **/
	ImageView					m_BombView;
	ImageView[]					m_PointView			= new ImageView[GDF.GAME_PLAYER];
	ImageView					m_RocketView;
	ImageView					m_PlaneView;
	ImageView[]					m_WarnView			= new ImageView[GDF.GAME_PLAYER];

	/** 结算控件 **/
	public View					m_GameEndView;
	public ImageView			m_GameEndFlag;
	public ImageView			m_GameEndWord;
	public ImageView			m_GameEndSpring;
	public TextView				m_GameEndBomb;
	public TextView				m_GameEndBankScore;
	public TextView				m_GameEndScore;

	/** 游戏参数 **/
	int							m_nBankerUser;
	int[]						m_nHandCardCount	= new int[GDF.GAME_PLAYER];
	int[][]						m_nOutCard			= new int[GDF.GAME_PLAYER][GDF.MAX_CARDCOUNT];
	int[]						m_nOutCount			= new int[GDF.GAME_PLAYER];
	int[]						m_nCallPoint		= new int[GDF.GAME_PLAYER];
	boolean						m_nWaitCallScore;
	boolean[]					m_bPass				= new boolean[GDF.GAME_PLAYER];

	@SuppressWarnings("unused")
	public GameClientView(GameClientActivity activity, Context context) {
		super(context);
		instance=this;
		
		int w = GameActivity.getOption().getWidth();
		int h = GameActivity.getOption().getHeight();

		gameClientActivity = activity;
		setBackgroundDrawable(GameActivity.getInstance().getResources().getDrawable(R.drawable.bg));

		// 顶部信息初始化
		TopInfoview = inflate(context, R.layout.game_top_info, null);
		addView(TopInfoview);
		m_btMenu = (Button) TopInfoview.findViewById(R.id.game_bt_menu);
		m_btTrustee = (Button) TopInfoview.findViewById(R.id.game_bt_trustee);
		m_btChangeDesk = (Button) TopInfoview.findViewById(R.id.game_bt_changedesk);
		m_BackCard[0] = (ImageView) TopInfoview.findViewById(R.id.game_top_backcard_00);
		m_BackCard[1] = (ImageView) TopInfoview.findViewById(R.id.game_top_backcard_01);
		m_BackCard[2] = (ImageView) TopInfoview.findViewById(R.id.game_top_backcard_02);
		m_Battery = (ImageView) TopInfoview.findViewById(R.id.game_top_battery);
		m_txt_BankTimes = (TextView) TopInfoview.findViewById(R.id.game_top_txtbanktime);
		m_txt_BombTimes = (TextView) TopInfoview.findViewById(R.id.game_top_txtbombtime);
		m_txt_SysTimes = (TextView) TopInfoview.findViewById(R.id.game_top_txtsystime);
		m_txt_BatteryPer = (TextView) TopInfoview.findViewById(R.id.game_top_txtbattery);
		m_btChat = (Button) TopInfoview.findViewById(R.id.game_bt_chat);

		// 頂部操作相关
		m_Battery.setBackgroundResource(R.drawable.battery_00 + BatteryLevelRcvr.BatteryLevel);
		m_txt_BatteryPer.setText(BatteryLevelRcvr.BatteryPer + "%");
		m_txt_SysTimes.setText(GDF.getDate(System.currentTimeMillis()));

		m_btMenu.setTag(IDC_MENU);
		m_btTrustee.setTag(IDC_TRUSTEE);
		m_btChangeDesk.setTag(IDC_CHANGE_DESK);
		m_btChat.setTag(IDC_GAME_CHAT);

		m_btMenu.setOnClickListener(this);
		m_btTrustee.setOnClickListener(this);
		m_btChangeDesk.setOnClickListener(this);
		m_btChat.setOnClickListener(this);

		m_btMenu.setOnTouchListener(new BtBackGround());
		m_btTrustee.setOnTouchListener(new BtBackGround());
		m_btChangeDesk.setOnTouchListener(new BtBackGround());
		m_btChat.setOnTouchListener(new BtBackGround());

		// 喇叭相关
		m_btHorn = (Button) TopInfoview.findViewById(R.id.game_bt_horn);
		m_btHornRecord = (Button) TopInfoview.findViewById(R.id.game_bt_horninfo);
		m_txt_HornInfo = (EditText) TopInfoview.findViewById(R.id.game_txt_msgin);

		m_btHorn.setTag(IDC_GAME_HORN);
		m_btHornRecord.setTag(IDC_GAME_HORNRECORD);

		m_btHorn.setOnClickListener(this);
		m_btHornRecord.setOnClickListener(this);

		m_btHorn.setOnTouchListener(new BtBackGround());
		m_btHornRecord.setOnTouchListener(new BtBackGround());

		m_txt_HornInfo.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				switch (actionId) {
					case EditorInfo.IME_ACTION_SEND : {
						String msg = m_txt_HornInfo.getText().toString();
						if (msg != null && !msg.equals("")) {
							//IKernel kernel = GameActivity.getKernel();
							//if (kernel != null)
							//	kernel.sendUserHorn(msg);
							GameActivity.getGameActivityInstance().sendHorn(msg);
						}
						GameActivity.getGameActivityInstance().m_HornRecord.onDestroy();
						m_txt_HornInfo.setVisibility(View.INVISIBLE);
						m_btHornRecord.setVisibility(View.VISIBLE);
						InputMethodManager imm = (InputMethodManager) GameActivity.getInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(m_txt_HornInfo.getWindowToken(), 0);

						return true;
					}
				}
				return false;
			}
		});

		m_txt_HornInfo.setVisibility(INVISIBLE);

		TopInfoview.setVisibility(VISIBLE);

		// 游戏按钮初始化
		m_btStart = new Button(context);
		m_btScoreOne = new Button(context);
		m_btScoreTwo = new Button(context);
		m_btScoreThree = new Button(context);
		m_btScorePass = new Button(context);
		m_btOutCard = new Button(context);
		m_btPassCard = new Button(context);
		m_btTipCard = new Button(context);
		m_btShootCard = new Button(context);

		m_btStart.setVisibility(INVISIBLE);
		m_btScoreOne.setVisibility(INVISIBLE);
		m_btScoreTwo.setVisibility(INVISIBLE);
		m_btScoreThree.setVisibility(INVISIBLE);
		m_btScorePass.setVisibility(INVISIBLE);
		m_btOutCard.setVisibility(INVISIBLE);
		m_btPassCard.setVisibility(INVISIBLE);
		m_btTipCard.setVisibility(INVISIBLE);
		m_btShootCard.setVisibility(INVISIBLE);

		m_btStart.setBackgroundResource(R.drawable.game_bt_start);
		m_btScoreOne.setBackgroundResource(R.drawable.game_bt_score_one);
		m_btScoreTwo.setBackgroundResource(R.drawable.game_bt_score_two);
		m_btScoreThree.setBackgroundResource(R.drawable.game_bt_score_three);
		m_btScorePass.setBackgroundResource(R.drawable.game_bt_score_pass);
		m_btOutCard.setBackgroundResource(R.drawable.game_bt_outcard);
		m_btPassCard.setBackgroundResource(R.drawable.game_bt_passcard);
		m_btTipCard.setBackgroundResource(R.drawable.game_bt_tipcard);
		m_btShootCard.setBackgroundResource(R.drawable.game_bt_shootcard);

		m_btStart.setTag(IDC_GAME_START);
		m_btScoreOne.setTag(IDC_SCORE_ONE);
		m_btScoreTwo.setTag(IDC_SCORE_TWO);
		m_btScoreThree.setTag(IDC_SCORE_THREE);
		m_btScorePass.setTag(IDC_SCORE_PASS);
		m_btOutCard.setTag(IDC_OUT_CARD);
		m_btPassCard.setTag(IDC_PASS_CARD);
		m_btTipCard.setTag(IDC_TIP_CARD);
		m_btShootCard.setTag(IDC_SHOOT_CARD);

		m_btStart.setOnClickListener(this);
		m_btScoreOne.setOnClickListener(this);
		m_btScoreTwo.setOnClickListener(this);
		m_btScoreThree.setOnClickListener(this);
		m_btScorePass.setOnClickListener(this);
		m_btOutCard.setOnClickListener(this);
		m_btPassCard.setOnClickListener(this);
		m_btTipCard.setOnClickListener(this);
		m_btShootCard.setOnClickListener(this);

		m_btStart.setOnTouchListener(new BtBackGround());
		m_btScoreOne.setOnTouchListener(new BtBackGround());
		m_btScoreTwo.setOnTouchListener(new BtBackGround());
		m_btScoreThree.setOnTouchListener(new BtBackGround());
		m_btScorePass.setOnTouchListener(new BtBackGround());
		m_btOutCard.setOnTouchListener(new BtBackGround());
		m_btPassCard.setOnTouchListener(new BtBackGround());
		m_btTipCard.setOnTouchListener(new BtBackGround());
		m_btShootCard.setOnTouchListener(new BtBackGround());

		addView(m_btStart);
		addView(m_btScoreOne);
		addView(m_btScoreTwo);
		addView(m_btScoreThree);
		addView(m_btScorePass);
		addView(m_btOutCard);
		addView(m_btPassCard);
		addView(m_btTipCard);
		addView(m_btShootCard);

		// 头像控件初始化
		int headw = 82 * w / 800;
		int headh = 129 * w / 480;
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_HeadView[i] = inflate(context, R.layout.game_head, null);
			m_HeadView[i].setOnClickListener(this);
			addView(m_HeadView[i]);

			m_HeadView[i].getLayoutParams().width = headw > 82 ? 82 : headw;
			m_HeadView[i].getLayoutParams().height = headh > 129 ? 129 : headh;

			m_HeadView[i].setTag(IDC_USER_HEAD_0 + i);
			m_txt_NickName[i] = (TextView) m_HeadView[i].findViewById(R.id.game_head_txt_name);
			m_txt_NickName[i].setMaxWidth(headw > 82 ? (80) : (headw - 2));
			m_txt_NickName[i].setTextSize(10);

			m_txt_Score[i] = (TextView) m_HeadView[i].findViewById(R.id.game_head_txt_point);
			m_txt_Score[i].setMaxWidth(headw > 82 ? (80) : (headw - 2));
			m_txt_Score[i].setTextSize(10);

			m_img_Head[i] = (ImageView) m_HeadView[i].findViewById(R.id.game_head_img_head);
			m_img_Head[i].setMaxWidth(headw > 82 ? (64) : (headw - 2));
			m_img_Head[i].setMaxHeight(headh > 129 ? (64) : (headh - 2));
		}

		// 表情控件初始化
		for (int i = 0; i < m_UserExpression.length; i++) {
			m_UserExpression[i] = new GifHep(context);
			addView(m_UserExpression[i]);
		}

		// 聊天显示控件初始化
		for (int i = 0; i < UserChat.length; i++) {
			UserChat[i] = inflate(context, R.layout.userchatview, null);
			UserChat[i].setBackgroundResource(R.drawable.user_chat_frame_00 + i);
			addView(UserChat[i]);
			UserChatInfo[i] = (TextView) UserChat[i].findViewById(R.id.userchatview_txt_info);
			int pading = 35;
			UserChatInfo[i].setMaxWidth(250 * GameActivity.getOption().getWidth() / 800);
			pading = 35 * GameActivity.getOption().getWidth() / 800;

			UserChatInfo[i].setPadding(pading, pading, pading, pading);
			UserChat[i].setVisibility(INVISIBLE);
		}

		// 动画初始化
		m_BombView = new ImageView(context);
		m_BombView.setBackgroundResource(R.anim.anim_bomb_list);
		addView(m_BombView);
		m_BombView.setVisibility(INVISIBLE);

		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_PointView[i] = new ImageView(context);
			m_PointView[i].setBackgroundResource(R.anim.anim_call_one);
			addView(m_PointView[i]);
			m_PointView[i].setVisibility(INVISIBLE);
		}

		m_RocketView = new ImageView(context);
		m_RocketView.setBackgroundResource(R.drawable.game_rocket);
		m_RocketView.setVisibility(INVISIBLE);
		addView(m_RocketView);

		m_PlaneView = new ImageView(context);
		m_PlaneView.setBackgroundResource(R.drawable.game_plane);
		m_PlaneView.setVisibility(INVISIBLE);
		addView(m_PlaneView);

		// 结算控件初始化
		m_GameEndView = inflate(context, R.layout.game_end_scoreview, null);
		addView(m_GameEndView);

		m_GameEndFlag = (ImageView) findViewById(R.id.game_end_img_flag);
		m_GameEndWord = (ImageView) findViewById(R.id.game_end_img_word);
		m_GameEndSpring = (ImageView) findViewById(R.id.game_end_img_spring);
		m_GameEndBomb = (TextView) findViewById(R.id.game_end_txt_bomb);
		m_GameEndBankScore = (TextView) findViewById(R.id.game_end_txt_bankscore);
		m_GameEndScore = (TextView) findViewById(R.id.game_end_txt_score);
		m_GameEndView.setVisibility(INVISIBLE);
		Button bt = (Button) findViewById(R.id.game_end_bt_close);
		bt.setTag(IDC_END_SCORE_CLOSE);
		bt.setOnClickListener(this);

		//
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_ptUserHead[i] = new Point();
			m_ptUserClock[i] = new Point();
			m_ptUserReady[i] = new Point();
			m_ptSendDst[i] = new Point();
			m_ptOutCard[i] = new Point();
			m_ptCardCount[i] = new Point();
			m_ptPass[i] = new Point();
			m_ptPoint[i] = new Point();
		}

	}
	protected void rectifyControl(int nWidth, int nHeight) {

		m_ptUserHead[0].set(2, 5);
		m_ptUserHead[1].set(2, nHeight - m_HeadView[1].getMeasuredHeight() - 2);
		m_ptUserHead[2].set(nWidth - 2 - m_HeadView[2].getMeasuredWidth(), 5);

		for (int i = 0; i < m_HeadView.length; i++)
			m_HeadView[i].layout(m_ptUserHead[i].x, m_ptUserHead[i].y, m_ptUserHead[i].x + m_HeadView[i].getMeasuredWidth(),
					m_ptUserHead[i].y + m_HeadView[i].getMeasuredHeight());

		int clockw = m_ImageClock.getWidth(), clockh = m_ImageClock.getHeight();

		m_ptUserClock[0].set(nWidth / 2 - TopInfoview.getMeasuredWidth() / 2, TopInfoview.getMeasuredHeight());
		m_ptUserClock[1].set(m_ptUserHead[1].x, m_ptUserHead[1].y - clockh);
		m_ptUserClock[2].set(nWidth / 2 + TopInfoview.getMeasuredWidth() / 2 - clockw, TopInfoview.getMeasuredHeight());

		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			m_ptUserReady[i].set(m_ptUserClock[i].x, m_ptUserClock[i].y);
		}

		m_ptCardCount[0].set(m_HeadView[0].getLeft(), m_HeadView[0].getBottom());
		m_ptCardCount[1].set(m_HeadView[1].getRight() - m_ImageCardNum.getWidth() * 2 / 10, m_HeadView[1].getTop() - m_ImageCardNum.getHeight());
		m_ptCardCount[2].set(m_HeadView[2].getRight() - m_ImageCardNum.getWidth() * 2 / 10, m_HeadView[2].getBottom());

		m_ptSendDst[0].set(m_ptCardCount[0].x, m_ptCardCount[0].y);
		m_ptSendDst[1].set(nWidth / 2 - m_CardRes.getCardWidth() / 2, nHeight - m_CardRes.getCardHeight());
		m_ptSendDst[2].set(m_ptCardCount[2].x, m_ptCardCount[2].y);

		m_ptSendSrc.set(nWidth / 2 - m_CardRes.getCardWidth() / 2, nHeight / 2 - m_CardRes.getCardHeight() / 2);

		m_ptOutCard[0].set(m_HeadView[0].getRight() + 15, TopInfoview.getMeasuredHeight() + 2);
		m_ptOutCard[1].set(nWidth / 2, nHeight - m_CardRes.getCardHeight() - m_nCardSpaceY_1 - m_MidCardRes.getCardHeight());
		m_ptOutCard[2].set(m_ptUserHead[2].x - 15, TopInfoview.getMeasuredHeight() + 2);

		m_ptPass[0].set(m_ptUserClock[0].x, m_ptUserClock[0].y);
		m_ptPass[1].set(nWidth / 2 - m_ImagePass.getWidth() / 2, nHeight / 2);
		m_ptPass[2].set(m_ptUserClock[2].x - m_ImagePass.getWidth() + clockw, m_ptUserClock[2].y);

		int callw = m_ImageCallScore.getWidth() / 3;
		m_ptPoint[0].set(m_ptUserClock[0].x, m_ptUserClock[0].y);
		m_ptPoint[1].set(nWidth / 2 - callw / 2, nHeight / 2);
		m_ptPoint[2].set(m_ptUserClock[2].x + clockw - callw, m_ptUserClock[2].y);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int w = r - l, h = b - t;

		for (int i = 0; i < this.getChildCount(); i++) {
			View v = getChildAt(i);
			if (v != null)
				v.measure(w, h);
		}

		rectifyControl(w, h);

		int btw = 0, bth = 0;

		btw = TopInfoview.getMeasuredWidth();
		bth = TopInfoview.getMeasuredHeight();
		TopInfoview.layout(w / 2 - btw / 2, 0, w / 2 + btw / 2, bth);

		btw = m_btScoreThree.getMeasuredWidth();
		bth = m_btScoreThree.getMeasuredHeight();
		m_btScoreThree.layout(w - 10 - btw, h / 2, w - 10, h / 2 + bth);
		m_btScoreTwo.layout(w - 20 - btw * 2, h / 2, w - 20 - btw, h / 2 + bth);
		m_btScoreOne.layout(w - 30 - btw * 3, h / 2, w - 30 - btw * 2, h / 2 + bth);
		m_btScorePass.layout(w - 40 - btw * 4, h / 2, w - 40 - btw * 3, h / 2 + bth);

		btw = m_btShootCard.getMeasuredWidth();
		bth = m_btShootCard.getMeasuredHeight();
		m_btOutCard.layout(w - 10 - btw, h / 2, w - 10, h / 2 + bth);
		m_btShootCard.layout(w - 20 - btw * 2, h / 2, w - 20 - btw, h / 2 + bth);
		m_btTipCard.layout(w - 30 - btw * 3, h / 2, w - 30 - btw * 2, h / 2 + bth);
		m_btPassCard.layout(w - 40 - btw * 4, h / 2, w - 40 - btw * 3, h / 2 + bth);

		btw = m_UserExpression[0].getMeasuredWidth();
		bth = m_UserExpression[0].getMeasuredHeight();
		int nspacex = m_HeadView[0].getMeasuredWidth();
		int nspacey = m_HeadView[0].getMeasuredHeight();

		m_UserExpression[0].layout(m_ptUserHead[0].x + nspacex, m_ptUserHead[0].y + nspacey, m_ptUserHead[0].x + nspacex + btw, m_ptUserHead[0].y + bth
				+ nspacey);
		m_UserExpression[1].layout(m_ptUserHead[1].x, m_ptUserHead[1].y - bth, m_ptUserHead[1].x + btw, m_ptUserHead[1].y);
		m_UserExpression[2].layout(m_ptUserHead[2].x - btw - nspacex, m_ptUserHead[2].y + nspacey, m_ptUserHead[2].x - nspacex, m_ptUserHead[2].y + bth
				+ nspacey);

		btw = UserChat[0].getMeasuredWidth();
		bth = UserChat[0].getMeasuredHeight();

		UserChat[0].layout(m_ptUserHead[0].x, m_ptUserHead[0].y + nspacey, m_ptUserHead[0].x + btw, m_ptUserHead[0].y + nspacey + bth);
		UserChat[1].layout(m_ptUserHead[1].x, m_ptUserHead[1].y - bth, m_ptUserHead[1].x + btw, m_ptUserHead[1].y);
		UserChat[2].layout(w - 2 - btw, m_ptUserHead[2].y + nspacey, w - 2, m_ptUserHead[2].y + nspacey + bth);

		int cardspacex = (w - m_HeadView[1].getMeasuredWidth() - m_CardRes.getCardWidth()) / 19;
		m_HandCardControl.setPosition(m_ptUserHead[1].x + nspacex + ((w - nspacex) / 2), h - m_CardRes.getCardHeight() - m_nCardSpaceY_1, cardspacex,
				m_nCardSpaceY_1, m_CardRes.getCardWidth(), m_CardRes.getCardHeight());

		m_BombView.layout(w / 2 - m_BombView.getMeasuredWidth() / 2, h / 2 - m_BombView.getMeasuredHeight() / 2, w / 2 + m_BombView.getMeasuredWidth() / 2, h
				/ 2 + m_BombView.getMeasuredHeight() / 2);
		btw = m_PointView[0].getMeasuredWidth();
		bth = m_PointView[0].getMeasuredHeight();

		m_PointView[0].layout(m_ptUserClock[0].x, m_ptUserClock[0].y, m_ptUserClock[0].x + btw, m_ptUserClock[0].y + bth);
		m_PointView[1].layout(w / 2 - btw / 2, h / 2, w / 2 + btw / 2, h / 2 + bth);
		m_PointView[2].layout(m_ptUserClock[2].x + m_ImageClock.getWidth() - btw, m_ptUserClock[2].y, m_ptUserClock[2].x + m_ImageClock.getWidth(),
				m_ptUserClock[2].y + bth);

		m_RocketView.layout(w / 2 - m_RocketView.getMeasuredWidth() / 2, h - m_RocketView.getMeasuredHeight(), w / 2 + m_RocketView.getMeasuredWidth() / 2, h);

		m_PlaneView.layout(w - m_PlaneView.getMeasuredWidth(), TopInfoview.getMeasuredHeight(), w,
				TopInfoview.getMeasuredHeight() + m_PlaneView.getMeasuredHeight());

		m_GameEndView.layout(w / 2 - m_GameEndView.getMeasuredWidth() / 2, h / 2 - m_GameEndView.getMeasuredHeight() / 2,
				w / 2 + m_GameEndView.getMeasuredWidth() / 2, h / 2 + m_GameEndView.getMeasuredHeight() / 2);

		btw = m_btStart.getMeasuredWidth();
		bth = m_btStart.getMeasuredHeight();
		m_btStart.layout(w / 2 - btw / 2, h / 2 + m_GameEndView.getMeasuredHeight() / 2, w / 2 + btw / 2, h / 2 + m_GameEndView.getMeasuredHeight() / 2 + bth);
	}
	/** 加载资源 **/
	public void onResume() {
		Resources res = getResources();
		if (m_CardRes != null)
			m_CardRes.onLoadImage(res);
		if (m_MidCardRes != null)
			m_MidCardRes.onLoadImage(res);
		if (m_ImageReady != null)
			m_ImageReady.LoadImage(res, R.drawable.flag_ready);
		if (m_ImageClock != null)
			m_ImageClock.LoadImage(res, R.drawable.clock);
		if (m_ImageTimeNum != null)
			m_ImageTimeNum.LoadImage(res, R.drawable.timenum);
		if (m_ImageWaitBank != null)
			m_ImageWaitBank.LoadImage(res, R.drawable.game_tip_wait_call);
		if (m_ImageCallScore != null)
			m_ImageCallScore.LoadImage(res, R.drawable.game_tip_call_score);
		if (m_ImagePass != null)
			m_ImagePass.LoadImage(res, R.drawable.game_tip_pass);
		if (m_ImageCardNum != null)
			m_ImageCardNum.LoadImage(res, R.drawable.game_num_cardcount);
		if (m_ImageFlagLand != null)
			m_ImageFlagLand.LoadImage(res, R.drawable.card_55);
		if (m_ImageFlagFramer != null)
			m_ImageFlagFramer.LoadImage(res, R.drawable.card_54);
	}
	/** 销毁资源 **/
	public void onDestroy() {
		if (m_CardRes != null)
			m_CardRes.onDestroy();
		if (m_MidCardRes != null)
			m_MidCardRes.onDestroy();
		if (m_ImageReady != null)
			m_ImageReady.onDestroy();
		if (m_ImageClock != null)
			m_ImageClock.onDestroy();
		if (m_ImageTimeNum != null)
			m_ImageTimeNum.onDestroy();
		if (m_ImageWaitBank != null)
			m_ImageWaitBank.onDestroy();
		if (m_ImageCallScore != null)
			m_ImageCallScore.onDestroy();
		if (m_ImagePass != null)
			m_ImagePass.onDestroy();
		if (m_ImageCardNum != null)
			m_ImageCardNum.onDestroy();
		if (m_ImageFlagLand != null)
			m_ImageFlagLand.onDestroy();
		if (m_ImageFlagFramer != null)
			m_ImageFlagFramer.onDestroy();
		
		instance=null;
	}

	@Override
	public void onClick(View v) {
		if (v.getTag() != null) {
			int tag = (Integer) v.getTag();
			switch (tag) {
				case IDC_MENU :
					GameActivity.getGameActivityInstance().onShowOptionMenu(null);
					break;
				case IDC_GAME_START :
					onGameStart();
					break;
				case IDC_CHANGE_DESK :
					onChangeDesk();
					break;
				case IDC_GAME_CHAT :
					
					 GameActivity.getInstance().onShowScene(GDF.SCENE_BUY);
					//GameActivity.getGameActivityInstance().onShowChat(null);
					break;
				case IDC_USER_HEAD_0 :
				case IDC_USER_HEAD_1 :
				 case IDC_USER_HEAD_2 :
					onShowUserInfo(tag - IDC_USER_HEAD_0);
					break;
				case IDC_SHOOT_CARD :
					if (m_HandCardControl.getCardCount() != 0)
						m_HandCardControl.setAllCardShoot(false);
					break;
				case IDC_TRUSTEE :
					onTrustee();
					break;
				case IDC_OUT_CARD :
					onOutCard();
					break;
				case IDC_TIP_CARD :
					onTipCard();
					break;
				case IDC_PASS_CARD :
					onPassCard();
					break;
				case IDC_SCORE_ONE :
					onCallScore(1);
					break;
				case IDC_SCORE_TWO :
					onCallScore(2);
					break;
				case IDC_SCORE_THREE :
					onCallScore(3);
					break;
				case IDC_SCORE_PASS :
					onCallScore(0xff);
					break;
				case IDC_END_SCORE_CLOSE :
					m_GameEndView.setVisibility(INVISIBLE);
					break;
				case IDC_GAME_HORN :
					onHornClick();
					break;
				case IDC_GAME_HORNRECORD :
					GameActivity.getGameActivityInstance().ShowHornRecord(null);
					break;
			}
		}
	}
	private void onHornClick() {
		if (m_txt_HornInfo.getVisibility() == View.INVISIBLE) {
			m_txt_HornInfo.setVisibility(View.VISIBLE);
			m_btHornRecord.setVisibility(View.INVISIBLE);
			GameActivity.getGameActivityInstance().m_HornRecord.onDestroy();
			m_txt_HornInfo.setVisibility(View.VISIBLE);
			m_btHornRecord.setVisibility(View.INVISIBLE);
			m_txt_HornInfo.requestFocus();
			InputMethodManager imm = (InputMethodManager) GameActivity.getGameActivityInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		} else {
			String msg = m_txt_HornInfo.getText().toString();
			if (msg != null && !msg.equals("")) {
				GameActivity.getGameActivityInstance().sendHorn(msg);
				//IKernel kernel = GameActivity.getKernel();
				//kernel.sendUserHorn(msg);
			}
			m_txt_HornInfo.setText("");
			m_txt_HornInfo.clearFocus();
			m_txt_HornInfo.setVisibility(View.INVISIBLE);
			m_btHornRecord.setVisibility(View.VISIBLE);
			InputMethodManager imm = (InputMethodManager) GameActivity.getInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(m_txt_HornInfo.getWindowToken(), 0);
		}
	}
	 public  void UpdateColor(int color) {
		 m_btHornRecord.setTextColor(color);
	}
	/** 出牌按钮 **/
	private void onOutCard() {
		gameClientActivity.onOutCard();
	}

	/** 提示按钮 **/
	private void onTipCard() {
		gameClientActivity.onTipCard();
	}

	/** 过牌按钮 **/
	private void onPassCard() {
		gameClientActivity.onPassCard();
	}

	/** 叫分按钮 **/
	private void onCallScore(int i) {
		gameClientActivity.onCallScore(i);
	}
	/** 托管按钮 **/
	private void onTrustee() {
		gameClientActivity.onTrustee();
	}

	/** 显示用户 **/
	private void onShowUserInfo(int viewid) {
		
		 
		UserItem it=GameActivity.getGameActivityInstance().sm_getuser( this.playerArray2[viewid] );
		
		UserInfoView.onShowUserInfo(  it );//GameEngine.getInstance().getClientUserItem(viewid));
	}

	/** 开始按钮 **/
	private void onGameStart() {
		gameClientActivity.onGameStart(true);
	}

	/** 换桌按钮 **/
	private void onChangeDesk() {
		gameClientActivity.onChangeDesk();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (m_bSendCard) {
			DrawSendCard(canvas);
		}

		m_HandCardControl.onDrawHandCardControl(canvas, m_CardRes);

		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			if (m_nOutCount[i] > 0) {
				m_MidCardRes.onDrawCard(canvas, m_ptOutCard[i].x, m_ptOutCard[i].y, m_nOutCard[i], m_nOutCount[i], i);
			}

			if (m_nHandCardCount[i] > 0) {
				if (i != MY_VIEW_ID) {
					if (i == m_nBankerUser)
						m_ImageFlagLand.DrawImage(canvas, m_ptCardCount[i].x, m_ptCardCount[i].y);
					else
						m_ImageFlagFramer.DrawImage(canvas, m_ptCardCount[i].x, m_ptCardCount[i].y);
					onDrawNum(canvas, m_ImageCardNum, m_ptCardCount[i].x + (i == 0 ? m_ImageFlagLand.getWidth() : 0), m_ptCardCount[i].y, m_nHandCardCount[i]
							+ "", "0123456789", i);
				} else {
					onDrawNum(canvas, m_ImageCardNum, m_ptCardCount[i].x, m_ptCardCount[i].y, m_nHandCardCount[i] + "", "0123456789", 0);
				}
			}

			if (m_bPass[i]) {
				Util.v(TAG,"m_ImagePass.DrawImage c:"+i);
				m_ImagePass.DrawImage(canvas, m_ptPass[i].x, m_ptPass[i].y);
			}

			if (m_nCallPoint[i] != 0) {
				int index = 0;
				switch (m_nCallPoint[i]) {
					case 1 :
					case 2 :
					case 3 :
						index = m_nCallPoint[i] - 1;
						break;
					default :
						index = 3;
						break;
				}
				m_ImageCallScore.DrawImage(canvas, m_ptPoint[i].x, m_ptPoint[i].y, m_ImageCallScore.getWidth() / 4, m_ImageCallScore.getHeight(), index
						* m_ImageCallScore.getWidth() / 4, 0);

			}
		}

		onDrawUser(canvas);

	}
	/** 发牌绘制 **/
	private void DrawSendCard(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		for (int i = 0; i < m_nSendCount && i < m_SendCardList.size(); i++) {

			SendItem item = m_SendCardList.get(i);
			if (item.nStep <= MAX_STEP) {
				m_MidCardRes.onDrawCard(canvas, 0xff, item.nCurX, item.nCurY);
			}
			if (m_nSendCount != m_SendCardList.size())
				m_MidCardRes.onDrawCard(canvas, 0xff, m_ptSendSrc.x, m_ptSendSrc.y);
		}

		if (m_nWaitCallScore)
			m_ImageWaitBank.DrawImage(canvas, width / 2 - m_ImageWaitBank.getWidth(), height / 2 - m_ImageWaitBank.getHeight());

	}

	/** 用户绘制 **/
	private void onDrawUser(Canvas canvas) {
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
		// IUserItem item = GameEngine.getInstance().getClientUserItem(i);
			UserItem item = GameActivity.getGameActivityInstance().sm_getuser(playerArray2[i]);
			 
			if (item == null || item.GetGameID()== 0) {
				continue;
			}

			// RectF rchead = new RectF(m_ptUserHead[i].x + 5, m_ptUserHead[i].y
			// + 5, m_ptUserHead[i].x + 5 + m_ImageHeadBg.getWidth() - 10,
			// m_ptUserHead[i].y + 5
			// + m_ImageHeadBg.getHeight() - 10);
			// if (m_nBankerUser != GDF.INVALID_CHAIR) {
			// Rect rcimage = null;
			// if (i == m_nBankerUser) {
			// rcimage = new Rect(0, 0, m_ImageHeadLand.getWidth(),
			// m_ImageHeadLand.getHeight());
			// canvas.drawBitmap(m_ImageHeadLand.getBitmap(), rcimage, rchead,
			// null);
			// } else {
			// rcimage = new Rect(0, 0, m_ImageHeadFarmer.getWidth(),
			// m_ImageHeadFarmer.getHeight());
			// canvas.drawBitmap(m_ImageHeadFarmer.getBitmap(), rcimage, rchead,
			// null);
			// }
			// } else {
			// if (i == 2 && GameActivity.m_ImageUserHead != null) {
			// Rect rcimage = new Rect(0, 0,
			// GameActivity.m_ImageUserHead.getWidth(),
			// GameActivity.m_ImageUserHead.getHeight());
			// canvas.drawBitmap(GameActivity.m_ImageUserHead, rcimage, rchead,
			// null);
			// } else {
			// Rect rcimage = new Rect(0, 0,
			// GameActivity.m_ImageDefFace[0].getWidth(),
			// GameActivity.m_ImageDefFace[0].getHeight());
			// canvas.drawBitmap(GameActivity.m_ImageDefFace[item.GetGender() ==
			// GDF.GENDER_MANKIND ? 0 : 1].getBitmap(), rcimage, rchead, null);
			// }
			// }
			if (item.GetUserStatus() == GDF.US_READY) {
				m_ImageReady.DrawImage(canvas, m_ptUserReady[i].x, m_ptUserReady[i].y);
			}

			int times = getUserTimes(i);
			if (times > 0)
				onDrawUserClock(canvas, m_ptUserClock[i].x, m_ptUserClock[i].y, times);

		}

	}

	/** 时钟绘制 **/
	private void onDrawUserClock(Canvas canvas, int x, int y, int clock) {
		m_ImageClock.DrawImage(canvas, x, y);
		int time = clock % 100;
		int Ten = time / 10;
		int Unit = time % 10;
		int w = m_ImageTimeNum.getWidth() / 10;
		int h = m_ImageTimeNum.getHeight();

		m_ImageTimeNum.DrawImage(canvas, x + m_ImageClock.getWidth() / 2 - w, y + m_ImageClock.getHeight() / 2 - h / 2, w, h, Ten * w, 0);
		m_ImageTimeNum.DrawImage(canvas, x + m_ImageClock.getWidth() / 2, y + m_ImageClock.getHeight() / 2 - h / 2, w, h, Unit * w, 0);

	}

	/** 数字绘制 **/
	private void onDrawNum(Canvas canvas, CImage img, int x, int y, String szdst, String szsrc, int type) {
		int dstlenght = szdst.length();
		int srclenght = szsrc.length();

		int singlew = img.getWidth() / srclenght;
		int singleh = img.getHeight();

		int startx = 0;
		switch (type) {
			case 0 :
				startx = x;
				break;
			case 1 :
				startx = x - singlew * dstlenght / 2;
				break;
			case 2 :
				startx = x - singlew * dstlenght;
				break;
			default :
				return;
		}

		for (int i = 0; i < dstlenght; i++) {
			for (int j = 0; j < srclenght; j++) {
				if (szdst.charAt(i) == szsrc.charAt(j)) {
					img.DrawImage(canvas, startx + i * singlew, y, singlew, singleh, j * singlew, 0);
					break;
				}
			}
		}
	}

	/** 飞机动画 **/
	public void onStartPlaneAnim() {
		m_PlaneView.clearAnimation();

		TranslateAnimation anim = new TranslateAnimation(0, -getWidth(), 0, 0);
		anim.setDuration(800);
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				m_PlaneView.setVisibility(INVISIBLE);
				int w = GameActivity.getOption().getWidth();
				m_PlaneView.layout(w - m_PlaneView.getMeasuredWidth(), TopInfoview.getMeasuredHeight(), w,
						TopInfoview.getMeasuredHeight() + m_PlaneView.getMeasuredHeight());
			}
		});
		m_PlaneView.setVisibility(VISIBLE);
		m_PlaneView.startAnimation(anim);

	}

	/** 叫分动画 **/
	public void onStartPoint(int viewid, int point) {
		AnimationDrawable drawable = null;
		if (viewid < GDF.GAME_PLAYER) {
			switch (point) {
				case 0 :
					m_PointView[viewid].setVisibility(VISIBLE);
					m_PointView[viewid].setBackgroundResource(R.anim.anim_call_one);
					drawable = (AnimationDrawable) m_PointView[viewid].getBackground();
					break;
				case 1 :
					m_PointView[viewid].setVisibility(VISIBLE);
					m_PointView[viewid].setBackgroundResource(R.anim.anim_call_two);
					drawable = (AnimationDrawable) m_PointView[viewid].getBackground();
					break;
				case 2 :
					m_PointView[viewid].setVisibility(VISIBLE);
					m_PointView[viewid].setBackgroundResource(R.anim.anim_call_three);
					drawable = (AnimationDrawable) m_PointView[viewid].getBackground();
					break;

				default :
					break;
			}
			if (drawable != null) {
				if (drawable.isRunning())
					drawable.stop();
				drawable.start();
			}
		} else {
			for (int i = 0; i < GDF.GAME_PLAYER; i++) {
				m_PointView[i].setVisibility(INVISIBLE);
				drawable = (AnimationDrawable) m_PointView[i].getBackground();
				if (drawable != null) {
					drawable.stop();
				}
			}
		}
	}

	/** 火箭动画 **/
	public void onStartRocketAnim() {
		m_RocketView.clearAnimation();

		TranslateAnimation anim = new TranslateAnimation(0, 0, 0, -getHeight());
		anim.setDuration(800);
		anim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				m_RocketView.setVisibility(INVISIBLE);
				int w = GameActivity.getOption().getWidth();
				int h = GameActivity.getOption().getHeight();
				m_RocketView.layout(w / 2 - m_RocketView.getMeasuredWidth() / 2, h - m_RocketView.getMeasuredHeight(), w / 2 + m_RocketView.getMeasuredWidth()
						/ 2, h);
			}
		});
		m_RocketView.setVisibility(VISIBLE);
		m_RocketView.startAnimation(anim);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (m_txt_HornInfo.getVisibility() == VISIBLE) {
			m_txt_HornInfo.setVisibility(INVISIBLE);
			InputMethodManager imm = (InputMethodManager) GameActivity.getInstance().getSystemService(Activity.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(m_txt_HornInfo.getWindowToken(), 0);
		}
		if (m_btHornRecord.getVisibility() == INVISIBLE) {
			m_btHornRecord.setVisibility(VISIBLE);
		}
		/** 手牌操作 **/
		if (m_HandCardControl.onTouchEvent(event) == true) {
			UpdateCard();
			return true;
		}
		return super.onTouchEvent(event);
	}

	/** 设置底注 **/
	public void setCellScore(long m_lCellScore) {
		// TODO Auto-generated method stub

	}

	/** 添加发牌 **/
	public void addSendCardItem(int viewid, int carddata) {
		SendItem item = new SendItem();
		item.nCardData = carddata;
		item.nDstChair = viewid;
		item.nCurX = m_ptSendSrc.x;
		item.nCurY = m_ptSendSrc.y;
		item.nStep = 0;
		m_SendCardList.add(item);
	}

	/** 停止发牌 **/
	public void onStopSendCard() {
		m_bSendCard = false;
		m_nSendCount = 0;
		m_SendCardList.clear();
		gameClientActivity.m_ClockHandler.removeMessages(GameClientActivity.IDI_SEND_FINISH);
	}

	/** 开始发牌 **/
	public void onStartSendCard() {

		m_SendThread = new Thread() {

			@Override
			public void run() {
				while (m_bSendCard) {
					if (m_nSendCount < m_SendCardList.size()) {
						m_nSendCount++;
						onPlayGameSound(GDF.SOUND_GAME_SEND);
					}
					for (int i = 0; i < m_nSendCount; i++) {
						SendItem item = m_SendCardList.get(i);
						if(item.nDstChair>3) continue;
						if (item.nStep < MAX_STEP) {
							item.nStep++;
							item.nCurX = m_ptSendSrc.x + (m_ptSendDst[item.nDstChair].x - m_ptSendSrc.x) * item.nStep / MAX_STEP;
							item.nCurY = m_ptSendSrc.y + (m_ptSendDst[item.nDstChair].y - m_ptSendSrc.y) * item.nStep / MAX_STEP;
						} else if (item.nStep == MAX_STEP) {
							item.nStep++;
							if (m_nHandCardCount[item.nDstChair] < GDF.MAX_CARDCOUNT)
								m_nHandCardCount[item.nDstChair]++;
							if (item.nDstChair == MY_VIEW_ID && m_HandCardControl.getCardCount() < GDF.MAX_CARDCOUNT) {
								m_HandCardControl.addCardData(item.nCardData);
							}
							if (m_nSendCount == m_SendCardList.size() && i == m_nSendCount - 1) {
								GameClientActivity.getInstance().m_ClockHandler.obtainMessage(GameClientActivity.IDI_SEND_FINISH).sendToTarget();
								m_bSendCard = false;
								GameClientView.this.postInvalidate();
								return;
							}
						}

					}
					GameClientView.this.postInvalidate();
					try {
						Thread.sleep(100);
					} catch (Exception e) {
					}
				}
			}
		};
		m_bSendCard = true;
		m_SendThread.start();
	}

	/** 时钟更新 **/
	public void onUpdateClock(int viewid) {
		m_txt_SysTimes.setText(GDF.getDate(System.currentTimeMillis()));
		postInvalidate();
	}

	/** 获取时间 **/
	public int getUserTimes(int viewid) {
		return UserClock.getUserClock(viewid);
	}

	/** 播放声音 **/
	public void onPlayGameSound(int id) {
		GameActivity.getOptionControl().PlayGameSound(id);
	}

	/** 用户喇叭 **/
	public void onHornInfo(String nickname, String info) {

	}

	/** 用户聊天 **/
	public void onUserChat(int chair, String info) {
		if (chair < UserChat.length) {
			UserChatInfo[chair].setText(info);
			UserChat[chair].setVisibility(VISIBLE);
			Handler handler = GameClientActivity.getInstance().m_ClockHandler;
			if (handler != null) {
				handler.removeMessages(GameClientActivity.IDI_HIDE_CHAT_0 + chair);
				Message msg = handler.obtainMessage(GameClientActivity.IDI_HIDE_CHAT_0 + chair);
				handler.sendMessageDelayed(msg, 1000);
			}
		}
	}

	/** 用户表情 **/
	public void onUserExpression(int chair, int index) {
		if (chair < m_UserExpression.length && index < 16)
			m_UserExpression[chair].onStartGif(R.drawable.girl_00 + index);
	}

	public int playerArray2[]=new int[3];//view array 玩家自己始终在1号位置
	/** 更新显示 **/
	public void UpdateGameView() {
	//	onUpdateNickName();
	//	onUpdateScore();
		
		
		playerArray2[0]=playerArray[0];
		playerArray2[1]=playerArray[1];
		playerArray2[2]=playerArray[2];
		
		//数组位置 玩家不在下标1的地方
		if(playerArray[MY_VIEW_ID]!=GameActivity.getGameActivityInstance().m_meUID)
		{
			//自己在0号位置
			if(playerArray[0]==GameActivity.getGameActivityInstance().m_meUID){
				playerArray2[0]=playerArray[2];
				playerArray2[1]=playerArray[0];
				playerArray2[2]=playerArray[1];
			}
			 
			//自己在2号位置
			if(playerArray[2]==GameActivity.getGameActivityInstance().m_meUID){
				playerArray2[0]=playerArray[1];
				playerArray2[1]=playerArray[2];
				playerArray2[2]=playerArray[0];
			}
			
		}
		
 
		
		onUpdateUserFace();
	}
	
	 public int GetRealChairID(int uid)
	 {
		for(int i=0;i<GDF.GAME_PLAYER;i++)
		{
			if(playerArray[i]==uid)
			{
				return i;
			}
		}
		return -1;
	 }
	//uid找玩家显示的座位.  playerArray存真实的座位顺序 playerArray2存显示的座位顺序
	 public int GetViewChairID(int uid)
	 {
			for (int i = 0; i < GDF.GAME_PLAYER; i++) 
			{
				if(playerArray2[i]==uid){
					UserItem item=GameActivity.getGameActivityInstance().sm_getuser(uid);
					if (item != null) {
						Util.v(TAG, item.GetNickName()+"]真实chairid-"+GetRealChairID(uid));
						Util.v(TAG, item.GetNickName()+"]viewcharid-"+i);
			
					}
					
					return i;
				}
				 
			}
			
			return -1;//找不到
	}
	public void onUpdateUserFace() {
		 
		
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			//  IUserItem item = GameEngine.getInstance().getClientUserItem(i);
			UserItem item=GameActivity.getGameActivityInstance().sm_getuser(playerArray2[i]);
			if (item == null) {
				m_txt_Score[i].setText("");
				m_txt_NickName[i].setText("");
	 
				m_img_Head[i].setBackgroundDrawable(null);
				m_HeadView[i].setVisibility(INVISIBLE);
			} else {
	 
				m_txt_Score[i].setText(getScoreString(item.GetUserScore()));
				m_txt_NickName[i].setText(item.GetNickName());
				
				 
				//是玩家自己，并开启了托管
				if (i == MY_VIEW_ID && gameClientActivity.m_bTrustee) {
					//头像更新为机器人图标
					m_img_Head[i].setBackgroundResource(R.drawable.flag_trustee);
					continue;
				} 
				
				if(m_nBankerUser<0 || m_nBankerUser>3 )
				{		
					Bitmap bitmap = item.GetFaceImage(); 
  
						if (bitmap != null) {
							BitmapDrawable bit = new BitmapDrawable(bitmap);
							m_img_Head[i].setBackgroundDrawable(bit);
						} else {
							m_img_Head[i].setBackgroundResource(item.GetGender() == GDF.GENDER_MANKIND ? R.drawable.head_00 : R.drawable.head_01);
						} 
				 
				} else {
					if (item.GetGameID() == playerArray2[m_nBankerUser])//m_nBankerUser后来被转成了viewid
						m_img_Head[i].setBackgroundResource(R.drawable.head_land);
					else
						m_img_Head[i].setBackgroundResource(R.drawable.head_farmer);
				}
				m_HeadView[i].setVisibility(VISIBLE);
			}
		}
	}

//	public void onUpdateNickName() {
//		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
//			IUserItem item = GameEngine.getInstance().getClientUserItem(i);
//			if (item == null) {
//				m_txt_NickName[i].setText("");
//			} else {
//				if (i == MY_VIEW_ID)
//					m_txt_NickName[i].setText(item.GetNickName());
//				else
//					m_txt_NickName[i].setText("玩家" + item.GetChairID());
//			}
//		}
//	}

//	public void onUpdateScore() {
//		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
//			IUserItem item = GameEngine.getInstance().getClientUserItem(i);
//			if (item == null) {
//				m_txt_Score[i].setText("");
//			} else {
//				m_txt_Score[i].setText(getScoreString(item.GetUserScore()));
//			}
//		}
//	}
	/** 设置牌数 **/
	public void setCardCount(int viewid, int normalCount) {
		if (viewid == GDF.INVALID_CHAIR)
			m_nHandCardCount = new int[GDF.GAME_PLAYER];
		else
			m_nHandCardCount[viewid] = normalCount;
	}

	/** 设置出牌 **/
	public void setOutCardData(int viewid, int data[], int count) {
		if (viewid == GDF.INVALID_CHAIR) {
			m_nOutCount = new int[GDF.GAME_PLAYER];
			m_nOutCard = new int[GDF.GAME_PLAYER][GDF.MAX_CARDCOUNT];
		} else {
			m_nOutCount[viewid] = count;
			for (int i = 0; i < count; i++) {
				m_nOutCard[viewid][i] = data[i];
			}
		}
	}

	/** 设置手牌 **/
	public void setHandCardData(int data[], int count) {
		m_nHandCardCount[MY_VIEW_ID] = count;
		m_HandCardControl.onReleaseCard();
		if (data != null)
			m_HandCardControl.onSetCardData(data, count);
	}

	/** 等待叫分 **/
	public void setWaitCallScore(boolean wait) {
		m_nWaitCallScore = wait;
	}

	/** 手牌报警 **/
	public void setUserCountWarn(int viewid, boolean warn) {
		// TODO Auto-generated method stub

	}

	/** 玩家过牌 **/
	public void setUserPassState(int viewid, boolean pass) {
		if (viewid == GDF.INVALID_CHAIR)
			m_bPass = new boolean[GDF.GAME_PLAYER];
		else
			m_bPass[viewid] = pass;
	}

	/** 玩家叫分 **/
	public void setUserCallScore(int viewid, int score) {
		if (viewid == GDF.INVALID_CHAIR) {
			m_nCallPoint = new int[GDF.GAME_PLAYER];
		} else {
			m_nCallPoint[viewid] = score;
			if (score > 0 && score < 4)
				onStartPoint(viewid, score - 1);
		}

	}
	/** 设置庄家 **/
	public void setBankerUser(int viewid) {
		m_nBankerUser = viewid;
		UpdateGameView();
	}
	/** 设置庄家分数 **/
	public void setBankerScore(int score) {
		m_txt_BankTimes.setText("底分 X " + score);
	}
	/** 设置底牌 **/
	public void setBackCard(int data[], boolean show) {
		for (int i = 0; i < m_BackCard.length; i++) {
			if (data == null) {
				m_BackCard[i].setBackgroundResource(R.drawable.card_55);
			} else {
				m_BackCard[i].setBackgroundResource(R.drawable.card_00 + CGameLogic.getCardIndex(data[i]));
			}
			m_BackCard[i].setVisibility(show ? VISIBLE : INVISIBLE);
		}
	}

	/** 分数转换 **/
	public String getScoreString(long score) {
		if (score < 999999)
			return score + "";
		else if (score < 99999999) {
			DecimalFormat df = new DecimalFormat("0.00");
			double dscore = score / 10000.;
			return df.format(dscore) + "万";
		} else {
			DecimalFormat df = new DecimalFormat("0.00");
			double dscore = score / 100000000.;
			return df.format(dscore) + "亿";
		}
	}
	public void UpdateCard() {
		invalidate(m_HandCardControl.m_ptPos.x, m_HandCardControl.m_ptPos.y, m_HandCardControl.m_ptPos.x + m_HandCardControl.m_nAreaW,
				m_HandCardControl.m_ptPos.y + m_HandCardControl.m_nAreaH);
	}
	public void updateBattery() {
		m_Battery.setBackgroundResource(R.drawable.battery_00 + BatteryLevelRcvr.BatteryLevel);
		m_txt_BatteryPer.setText(BatteryLevelRcvr.BatteryPer + "%");
	}
	public void onRelease() {
		onStopSendCard();
	}
	public void onStartBombAnim() {
		// TODO Auto-generated method stub

	}

	public void onSetBombCount(int bomb) {
		m_txt_BombTimes.setText("炸弹X " + bomb);
	}
	public void onSubSystemMessage(String string, boolean system) {
		if (m_btHornRecord != null) {
			if (system)
				m_btHornRecord.setTextColor(Color.YELLOW);
			else
				m_btHornRecord.setTextColor(Color.WHITE);
			m_btHornRecord.setText(string);
		}
	}

}
