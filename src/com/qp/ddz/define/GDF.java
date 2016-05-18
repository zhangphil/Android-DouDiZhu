package com.qp.ddz.define;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.qp.lib.define.DF;
import com.qp.lib.utility.Util;
/*
*
* 购买完整源码联系 q344717871
* 
* */

public class GDF implements DF {

	public final static int		GAME_TYPE			= 200;

	public final static String	GAME_NAME		= "新斗地主";
	public final static int		VIEW_COUNT		= 3;
	public static final int		GAME_PLAYER		= 3;

	public final static String	GAME_SD_PATH	= "ddz";	
 
	public final static String	UrlLogin		= "192.168.1.100";
	public final static int		PortLogin		= 4000;

	public final static long	APP_VERSION		= Util.ProcesVersion(6, 0, 3);
	public final static long	GAME_VERSION	=  1;//Util.ProcesVersion(6, 0, 3);

 
	public final static int		SCENE_WELCOME	= 1;
	public final static int		SCENE_MENU		= 2;
	public final static int		SCENE_SERVER	= 3;
	public final static int		SCENE_GAME		= 4;
	public final static int		SCENE_SHOP		= 5;
	public static final int		SCENE_ABOUT		= 6;
	public static final int		SCENE_GAMES		= 7;
	public static final int		SCENE_RANKING	= 8;
	public final static int		SCENE_LOGIN	= 9;
	public final static int		SCENE_BUY	= 10; 
	
	public final static int		MAX_FACE_ID		= 16;

	public final static int		MSG_UI_CHANGE	= MSG_LIB_END + 1;

	/**
	 * 按下这个按钮进行的颜色过滤
	 */
	public final static float[]	BT_SELECTED		= new float[]{1.5f, 0, 0, 0, 1.5f, 0, 1.5f, 0, 0, 1.5f, 0, 0, 1.5f, 0, 1.5f, 0, 0, 0, 1, 0};

	/**
	 * 按钮恢复原状的颜色过滤
	 */
	public final static float[]	BT_NOT_SELECTED	= new float[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};

	public static final int		MAX_CARDCOUNT	= 20;
	public static final int		FULL_COUNT		= 54;
	public static final int		NORMAL_COUNT	= 17;
	public static final int		MASK_VALUE		= 0X0F;
	public static final int		MASK_COLOR		= 0XF0;

	public static String getDate(long datetime) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");// 设置格式
		Date date = new Date(datetime);
		return format.format(date);
	}

	/** 逻辑定义 **/
	public static final int	CT_ERROR				= 0;					// 错误类型
	public static final int	CT_1					= 1;					// 单牌类型
	public static final int	CT_2					= 2;					// 对牌类型
	public static final int	CT_3					= 3;					// 三条类型
	public static final int	CT_1_LINE				= 4;					// 单连类型
	public static final int	CT_2_LINE				= 5;					// 对连类型
	public static final int	CT_3_LINE				= 6;					// 三连类型
	public static final int	CT_3_LINE_1				= 7;					// 三带一单
	public static final int	CT_3_LINE_2				= 8;					// 三带一对
	public static final int	CT_4_LINE_1				= 9;					// 四带两单
	public static final int	CT_4_LINE_2				= 10;					// 四带两对
	public static final int	CT_BOME_CARD			= 11;					// 炸弹类型
	public static final int	CT_MISSILE_CARD			= 12;					// 火箭类型
	public static final int CT_PLANE				= 13;					// 飞机类型（动画及音效用）
	
	/** 排序类型 **/
	public static final int	ST_ORDER				= 1;
	public static final int	ST_COUNT				= 2;

	/** 状态定义 **/
	public final static int	GAME_SCENE_FREE			= GAME_STATUS_FREE;	// 等待开始
	public final static int	GAME_SCENE_CALL			= GAME_STATUS_PLAY;	// 叫分状态
	public final static int	GAME_SCENE_PLAY			= GAME_STATUS_PLAY + 1; // 游戏进行

	/** 服务器端游戏命令 **/
	public final static int	SUB_S_GAME_START		= 100;					// 游戏开始
	public final static int	SUB_S_CALL_SCORE		= 101;					// 用户叫分
	public final static int	SUB_S_BANLER_INFO		= 102;					// 庄家信息
	public final static int	SUB_S_OUT_CARD			= 103;					// 用户出牌
	public final static int	SUB_S_PASS_CARD			= 104;					// 用户放弃
	public final static int	SUB_S_GAME_CONCLUDE		= 105;					// 游戏结束

	/** 客户端游戏命令 **/
	public final static int	SUB_C_CALL_SCORE		= 1;					// 用户叫分
	public final static int	SUB_C_OUT_CARD			= 2;					// 用户出牌
	public final static int	SUB_C_PASS_CARD			= 3;					// 用户放弃

	public final static int	SOUND_GAME_BOMB			= 1;
	public final static int	SOUND_GAME_PLANE		= 2;
	public final static int	SOUND_GAME_END			= 3;
	public final static int	SOUND_GAME_START		= 4;
	public final static int	SOUND_GAME_WARN			= 5;
	public final static int	SOUND_GAME_SEND			= 6;
	public final static int	SOUND_GAME_SHULLE		= 7;
	public final static int	SOUND_GAME_ALERT		= 8;
	public final static int	SOUND_GAME_BANKERINFO	= 9;

	public final static int	SOUND_CS_M_0			= 10;
	public final static int	SOUND_CS_W_0			= 11;
	public final static int	SOUND_CS_M_1			= 12;
	public final static int	SOUND_CS_W_1			= 13;
	public final static int	SOUND_CS_M_2			= 14;
	public final static int	SOUND_CS_W_2			= 15;
	public final static int	SOUND_CS_M_3			= 16;
	public final static int	SOUND_CS_W_3			= 17;

	public final static int	SOUND_PASS_M_0			= 18;
	public final static int	SOUND_PASS_W_0			= 19;
	public final static int	SOUND_PASS_M_1			= 20;
	public final static int	SOUND_PASS_W_1			= 21;

	public final static int	SOUND_YA_M_0			= 22;
	public final static int	SOUND_YA_W_0			= 23;
	public final static int	SOUND_YA_M_1			= 24;
	public final static int	SOUND_YA_W_1			= 25;

	public final static int	SOUND_MSG_M_0			= 26;
	public final static int	SOUND_MSG_W_0			= 27;
	public final static int	SOUND_MSG_M_1			= 28;
	public final static int	SOUND_MSG_W_1			= 29;
	public final static int	SOUND_MSG_M_2			= 30;
	public final static int	SOUND_MSG_W_2			= 31;
	public final static int	SOUND_MSG_M_3			= 32;
	public final static int	SOUND_MSG_W_3			= 33;
	public final static int	SOUND_MSG_M_4			= 34;
	public final static int	SOUND_MSG_W_4			= 35;
	public final static int	SOUND_MSG_M_5			= 36;
	public final static int	SOUND_MSG_W_5			= 37;
	public final static int	SOUND_MSG_M_6			= 38;
	public final static int	SOUND_MSG_W_6			= 39;
	public final static int	SOUND_MSG_M_7			= 40;
	public final static int	SOUND_MSG_W_7			= 41;
	public final static int	SOUND_MSG_M_8			= 42;
	public final static int	SOUND_MSG_W_8			= 43;
	public final static int	SOUND_MSG_M_9			= 44;
	public final static int	SOUND_MSG_W_9			= 45;
	public final static int	SOUND_MSG_TIP			= 46;

	public final static int	SOUND_CARD_M_1			= 47;
	public final static int	SOUND_CARD_W_1			= 48;
	public final static int	SOUND_CARD_M_2			= 49;
	public final static int	SOUND_CARD_W_2			= 50;
	public final static int	SOUND_CARD_M_3			= 51;
	public final static int	SOUND_CARD_W_3			= 52;
	public final static int	SOUND_CARD_M_4			= 53;
	public final static int	SOUND_CARD_W_4			= 54;
	public final static int	SOUND_CARD_M_5			= 55;
	public final static int	SOUND_CARD_W_5			= 56;
	public final static int	SOUND_CARD_M_6			= 57;
	public final static int	SOUND_CARD_W_6			= 58;
	public final static int	SOUND_CARD_M_7			= 59;
	public final static int	SOUND_CARD_W_7			= 60;
	public final static int	SOUND_CARD_M_8			= 61;
	public final static int	SOUND_CARD_W_8			= 62;
	public final static int	SOUND_CARD_M_9			= 63;
	public final static int	SOUND_CARD_W_9			= 64;
	public final static int	SOUND_CARD_M_10			= 65;
	public final static int	SOUND_CARD_W_10			= 66;
	public final static int	SOUND_CARD_M_11			= 67;
	public final static int	SOUND_CARD_W_11			= 68;
	public final static int	SOUND_CARD_M_12			= 69;
	public final static int	SOUND_CARD_W_12			= 70;
	public final static int	SOUND_CARD_M_13			= 71;
	public final static int	SOUND_CARD_W_13			= 72;
	public final static int	SOUND_CARD_M_14			= 73;
	public final static int	SOUND_CARD_W_14			= 74;
	public final static int	SOUND_CARD_M_15			= 75;
	public final static int	SOUND_CARD_W_15			= 76;

	public final static int	SOUND_TYPE_M_DUI		= 77;
	public final static int	SOUND_TYPE_W_DUI		= 78;
	public final static int	SOUND_TYPE_M_SAN		= 79;
	public final static int	SOUND_TYPE_W_SAN		= 80;
	public final static int	SOUND_TYPE_M_DANSUN		= 81;
	public final static int	SOUND_TYPE_W_DANSUN		= 82;
	public final static int	SOUND_TYPE_M_DUISUN		= 83;
	public final static int	SOUND_TYPE_W_DUISUN		= 84;
	public final static int	SOUND_TYPE_M_SANSUN		= 85;
	public final static int	SOUND_TYPE_W_SANSUN		= 86;
	public final static int	SOUND_TYPE_M_SANDAIYI	= 87;
	public final static int	SOUND_TYPE_W_SANDAIYI	= 88;
	public final static int	SOUND_TYPE_M_SANDAIER	= 89;
	public final static int	SOUND_TYPE_W_SANDAIER	= 90;
	public final static int	SOUND_TYPE_M_SIDAIER	= 91;
	public final static int	SOUND_TYPE_W_SIDAIER	= 92;
	public final static int	SOUND_TYPE_M_HUIJIAN	= 93;
	public final static int	SOUND_TYPE_W_HUIJIAN	= 94;
	public static final int	SOUND_CLICK				= 95;

}
