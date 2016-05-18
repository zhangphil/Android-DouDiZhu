package com.qp.lib.main;

import com.qp.ddz.GameActivity;
import com.qp.lib.define.DF;
import com.qp.lib.interface_ex.IGameClientEngine;
import com.qp.lib.interface_ex.kernel.IKernel;
import com.qp.lib.interface_ex.user.IUserItem;
import com.qp.lib.main.AppMain;
import com.qp.lib.utility.Util;
import com.qp.ddz.game.GameClientActivity;
import com.qp.ddz.game.GameClientView;
import com.smw.net.UserItem;

public class GameEngine  {

	public static final String			TAG	= "GameEngine";
	protected static GameEngine			instance;
	protected static IGameClientEngine	GameClientEngine;
	protected int						nGameStatus;
	protected int						nAllowLookon;
	protected int						nTableID;
	protected int						nChairID;
	protected int						nChairCount;
	protected int						nViewID[];

	public GameEngine() {
		nGameStatus = 0;
		nAllowLookon = 0;
		nTableID = 65535;
		nChairID = 65535;
		nChairCount = 0;
		instance = this;
	}

	public static GameEngine getInstance() {
		if (instance == null)
			new GameEngine();
		return instance;
	}

	public static void setGameClientEngine(IGameClientEngine gameClientEngine) {
		GameClientEngine = gameClientEngine;
	}

	 

	 

	public void onSetGameStatus(int gamestatus) {
		Util.d(TAG, "…Ë÷√”Œœ∑◊¥Ã¨£∫" + gamestatus + "#Table" + nTableID + "#Chair" + nChairID);
		nGameStatus = gamestatus;
	}

	public int getGameStatus() {
		return nGameStatus;
	}

	public boolean IsAllowLookon() {
		return nAllowLookon == 1;
	}

//	public boolean IsLookOnMode() {
//		return AppMain.getKernel().getMeUserItem().GetUserStatus() == 4;
//	}

	public void onSetGameAllowLookon(int allowlook) {
		nAllowLookon = allowlook;
	}

	 
	public int SwitchViewChairID(int chair)
	{
		return GameClientView.instance.GetViewChairID(GameClientView.playerArray[chair]);
	}

	public int GetMeTableID() {
		return nTableID;
	}

 

	public  UserItem getMeUserItem() {
		return  GameActivity.getGameActivityInstance().sm_getme();//AppMain.getKernel().getMeUserItem();
	}

//	public UserItem getClientUserItem(int viewchair) {
////		if (nTableID != 65535 && viewchair < nChairCount)
////			return AppMain.getKernel().searchUserByTableInfo(nTableID, nViewID[viewchair]);
////		else
// 			return null;
//	}

//	public IUserItem getTableUserItem(int chair) {
//		if (nTableID != 65535 && chair < nChairCount)
//			return AppMain.getKernel().searchUserByTableInfo(nTableID, chair);
//		else
//			return null;
//	}

//	public void PerformStandupAction(boolean bforce) {
//		IKernel kernel = AppMain.getKernel();
//		kernel.PerformStandupAction(bforce);
//	}

	public void UpdateGameView() {
		if (GameClientEngine != null)
			GameClientEngine.UpdateGameView();
	}

	   
}
