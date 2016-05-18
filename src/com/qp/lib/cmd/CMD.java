package com.qp.lib.cmd;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public interface CMD {
	//client <-> login服务器
	public static final int  C2S_LOGIN = 0;  //客户请求登陆
	public static final int  S2C_LOGIN_FAILED = 1; //客户登陆失败
	public static final int  S2C_LOGIN_OK =2 ;  //客户登陆成功
	public static final int  S2C_USERINFO  =9; //s->c info   和gameserver通用
	public static final int  C2S_USER_HORN =20; 
	public static final int  S2C_ALERT_DLG	=30; //s->c 弹出信息框.. toast
	
	public static final int C2S_RANK_LIST	= 100	;		//金币排行
	public static final int S2C_RANK_LIST	=	101	;		//金币排行
	public static final int S2C_SystemMessage		=	102;			//系统消息
	public static final int C2S_HEARTBEAT			=	200;  //客户发送服务器心跳包..
	 
	public static final int C2S_GET_IMAGE =250;				//查看图像
	public static final int S2C_IMAGE_RET =251;				//返回图像
	public static final int C2S_UPLOAD_IMAGE =252;			//上传图像
	public static final int S2C_UPLOAD_IMAGE_RET =253;		//上传结果
	
	public static final int	 C2S_PWD_CHANGE	=	300	;		//修改密码
	public static final int	 S2C_PWD_CHANGE_RET	= 301;		//修改结果
	
	public static final int	C2S_SET_MAXIM 		=302;		//修改签名
	public static final int	S2C_SET_MAXIM_RET	=303;		//修改结果
	
	public static final int	 C2S_SET_NICKNAME	=	304	;	//修改昵称
	public static final int	 S2C_SET_NICKNAME_RET	=305;		//修改结果
	
	public static final int	 C2S_OPEN_SHOP		=500;		//打开商店
	public static final int	 S2C_SHOP_GOODS	=	501;		//商店销售的物品
	public static final int	  C2S_GET_BILLS		=502;		//订单查看
	public static final int	  S2C_BILLS_RET		=503;		//订单返回

	public static final int	 C2S_SHOP_BUY				=504;		//购买
	public static final int	 S2C_SHOP_BUT_RET			=505;		//购买返回
	
	public static final int	 S2C_SYSTEM_CONFIG	=700;		//系统配置!	
	public static final int	 S2C_SYSTEM_CONFIG2	=701;		//系统配置!	
	
	public static final int S2C_ServerList	=	750	;	//房间列表
	public static final int C2S_GetServerList =	751;
	
	
	//client <-> game服务器
	public static final int  C2S_ENTER_ROOM =1000 ; //客户请求进入房间
	public static final int  S2C_ENTER_ROOM_FAILED= 1001;  //客户进入房间失败,
	public static final int  S2C_ENTER_ROOM_OK =1002; //客户进入房间成功
 
	public static final int  S2C_TABLE_INFO = 1003;   //s->c table info
	public static final int  C2S_SAY_READY =1004;	//c->s 准备好了
 
	public static final int  S_CallScore =1005; //s->c  
	
	
	public static final int  S_BANKER_INFO	=	1006;	//庄家信息
	public static final int  S_OUT_CARD			=1007;	//用户出牌
	public static final int  S_PASS_CARD		=	1008; //用户放弃
 
	public static final int  S2C_GameStart		=	1009;			 		//游戏开始
	public static final int  S_GAME_CONCLUDE		=	1010;		 			//游戏结束
	public static final int  S_SET_BASESCORE		=	1011	;		 		//设置基数

	public static final int C2S_CallScore	=1025	;
	
 
	
	public static final int  C2S_CHAT =1100;  //c->s chat
	public static final int  S2C_CHAT= 1101;  //s->c chat 
 
	public static final int C2S_OUT_CARD	=				1023	;					//用户出牌
	public static final int C2S_PASS_CARD	 =				1024	; 					// 过
	
	public static final int C2S_CHANGE_TABLE = 				1030;  					//c->s 换桌
	
	public static final int GAME_SCENE_Status_FREE	=1051	;
	public static final int GAME_SCENE_Status_Call	=1052	;
	public static final int GAME_SCENE_Status_Play	=1053	;
	
	 
	
	
	public static final int	VIEW_MODE_ALL					= 1;
	public static final int	VIEW_MODE_PART					= 2;
	public static final int	VIEW_INFO_LEVEL_1				= 16;
	public static final int	VIEW_INFO_LEVEL_2				= 32;
	public static final int	VIEW_INFO_LEVEL_3				= 64;
	public static final int	VIEW_INFO_LEVEL_4				= 128;
	public static final int	RECVICE_GAME_CHAT				= 256;
	public static final int	RECVICE_ROOM_CHAT				= 512;
	public static final int	RECVICE_ROOM_WHISPER			= 1024;
	public static final int	BEHAVIOR_LOGON_NORMAL			= 0;
	public static final int	BEHAVIOR_LOGON_IMMEDIATELY		= 4096;
	public static final int	MDM_MB_LOGON					= 100;
	public static final int	SUB_MB_LOGON_GAMEID				= 1;
	public static final int	SUB_MB_LOGON_ACCOUNTS			= 2;
	public static final int	SUB_MB_REGISTER_ACCOUNTS		= 3;
	public static final int	SUB_MB_LOGON_OTHERPLATFORM		= 4;
	public static final int	SUB_MB_LOGON_SUCCESS			= 100;
	public static final int	SUB_MB_LOGON_FAILURE			= 101;
	public static final int	SUB_MB_UPDATE_NOTIFY			= 200;
	public static final int	MDM_GP_LOGON					= 1;
	public static final int	SUB_GP_UPDATE_NOTIFY			= 200;
	public static final int	MDM_MB_SERVER_LIST				= 101;
	public static final int	SUB_MB_LIST_KIND				= 100;
	public static final int	SUB_MB_LIST_SERVER				= 101;
	public static final int	SUB_MB_LIST_FINISH				= 200;
	public static final int	SUB_MB_USER_SERVICE				= 102;
	public static final int	SUB_MB_MODIFY_ACCOUNTS			= 1;
	public static final int	SUB_MB_MODIFY_LOGON_PASS		= 2;
	public static final int	SUB_MB_MODIFY_INSURE_PASS		= 3;
	public static final int	SUB_MB_MODIFY_INDIVIDUAL		= 4;
	public static final int	SUB_MB_QUERY_INDIVIDUAL			= 10;
	public static final int	SUB_MB_OPERATE_SUCCESS			= 100;
	public static final int	SUB_MB_OPERATE_FAILURE			= 101;
	public static final int	SUB_MB_USER_INDIVIDUAL			= 200;
	public static final int	MDM_GR_LOGON					= 1;
	public static final int	SUB_GR_LOGON_MOBILE				= 2;
	public static final int	SUB_GR_LOGON_SUCCESS			= 100;
	public static final int	SUB_GR_LOGON_FAILURE			= 101;
	public static final int	SUB_GR_LOGON_FINISH				= 102;
	public static final int	SUB_GR_UPDATE_NOTIFY			= 200;
	public static final int	MDM_GR_GONFIG					= 2;
	public static final int	SUB_GR_CONFIG_COLUMN			= 100;
	public static final int	SUB_GR_CONFIG_SERVER			= 101;
	public static final int	SUB_GR_CONFIG_PROPERTY			= 102;
	public static final int	SUB_GR_CONFIG_FINISH			= 103;
	public static final int	MDM_GR_USER						= 3;
	public static final int	SUB_GR_USER_RULE				= 1;
	public static final int	SUB_GR_USER_LOOKON				= 2;
	public static final int	SUB_GR_USER_SITDOWN				= 3;
	public static final int	SUB_GR_USER_STANDUP				= 4;
	public static final int	SUB_GR_USER_INFO_REQ			= 9;
	public static final int	SUB_GR_USER_CHAIR_REQ			= 10;
	public static final int	SUB_GR_USER_CHAIR_INFO_REQ		= 11;
	public static final int	SUB_GR_USER_ENTER				= 100;
	public static final int	SUB_GR_USER_SCORE				= 101;
	public static final int	SUB_GR_USER_STATUS				= 102;
	public static final int	SUB_GR_REQUEST_FAILURE			= 103;
	public static final int	SUB_GR_USER_CHAT				= 201;
	public static final int	SUB_GR_WISPER_CHAT				= 202;
	public static final int	SUB_GR_USER_EXPRESSION			= 203;
	public static final int	SUB_GR_WISPER_EXPRESSION		= 204;
	public static final int	SUB_GR_INVITE_USER				= 300;
	public static final int	SUB_GR_PROPERTY_TRUMPET			= 305;
	public static final int	SUB_GR_PROPERTY_FAILURE			= 302;
	public static final int	MDM_GR_STATUS					= 4;
	public static final int	SUB_GR_TABLE_INFO				= 100;
	public static final int	SUB_GR_TABLE_STATUS				= 101;
	public static final int	MDM_GF_FRAME					= 100;
	public static final int	SUB_GF_GAME_OPTION				= 1;
	public static final int	SUB_GF_USER_READY				= 2;
	public static final int	SUB_GF_LOOKON_GONFIG			= 3;
	public static final int	SUB_GF_USER_CHAT				= 10;
	public static final int	SUB_GF_USER_EXPRESSION			= 11;
	public static final int	SUB_GF_GAME_STATUS				= 100;
	public static final int	SUB_GF_GAME_SCENE				= 101;
	public static final int	SUB_GF_LOOKON_STATUS			= 102;
	public static final int	SUB_GF_SYSTEM_MESSAGE			= 200;
	public static final int	SUB_GF_ACTION_MESSAGE			= 201;
	public static final int	MDM_GF_GAME						= 200;
	public static final int	MDM_CM_SYSTEM					= 1000;
	public static final int	SUB_CM_SYSTEM_MESSAGE			= 1;
	public static final int	SUB_CM_ACTION_MESSAGE			= 2;
	public static final int	SUB_CM_DOWN_LOAD_MODULE			= 3;
	public static final int	MDM_GR_INSURE					= 5;
	public static final int	SUB_GR_QUERY_INSURE_INFO		= 1;
	public static final int	SUB_GR_SAVE_SCORE_REQUEST		= 2;
	public static final int	SUB_GR_TAKE_SCORE_REQUEST		= 3;
	public static final int	SUB_GR_TRANSFER_SCORE_REQUEST	= 4;
	public static final int	SUB_GR_USER_INSURE_INFO			= 100;
	public static final int	SUB_GR_USER_INSURE_SUCCESS		= 101;
	public static final int	SUB_GR_USER_INSURE_FAILURE		= 102;

	public static final int	MDM_GP_USER_SERVICE				= 3;

	public static final int	SUB_GP_MODIFY_MACHINE			= 100;
	public static final int	SUB_GP_MODIFY_LOGON_PASS		= 101;
	public static final int	SUB_GP_MODIFY_INSURE_PASS		= 102;
	public static final int	SUB_GP_MODIFY_UNDER_WRITE		= 103;

	public static final int	SUB_GP_USER_FACE_INFO			= 200;
	public static final int	SUB_GP_SYSTEM_FACE_INFO			= 201;
	public static final int	SUB_GP_CUSTOM_FACE_INFO			= 202;

	public static final int	SUB_GP_USER_INDIVIDUAL			= 301;
	public static final int	SUB_GP_QUERY_INDIVIDUAL			= 302;
	public static final int	SUB_GP_MODIFY_INDIVIDUAL		= 303;

	public static final int	SUB_GP_USER_SAVE_SCORE			= 400;
	public static final int	SUB_GP_USER_TAKE_SCORE			= 401;
	public static final int	SUB_GP_USER_TRANSFER_SCORE		= 402;
	public static final int	SUB_GP_USER_INSURE_INFO			= 403;
	public static final int	SUB_GP_QUERY_INSURE_INFO		= 404;
	public static final int	SUB_GP_USER_INSURE_SUCCESS		= 405;
	public static final int	SUB_GP_USER_INSURE_FAILURE		= 406;
	public static final int	SUB_GP_QUERY_USER_INFO_REQUEST	= 407;
	public static final int	SUB_GP_QUERY_USER_INFO_RESULT	= 408;

	public static final int	SUB_GP_OPERATE_SUCCESS			= 900;
	public static final int	SUB_GP_OPERATE_FAILURE			= 901;

	public static final int	DTP_GP_UI_NICKNAME				= 1;
	public static final int	DTP_GP_UI_USER_NOTE				= 2;
	public static final int	DTP_GP_UI_UNDER_WRITE			= 3;
	public static final int	DTP_GP_UI_QQ					= 4;
	public static final int	DTP_GP_UI_EMAIL					= 5;
	public static final int	DTP_GP_IO_SEAT_PHONE			= 6;
	public static final int	DTP_GP_UI_MOBILE_PHONE			= 7;
	public static final int	DTP_GP_UI_COMPELLATION			= 8;
	public static final int	DTP_GP_UI_DWELLING_PLACE		= 9;

}
