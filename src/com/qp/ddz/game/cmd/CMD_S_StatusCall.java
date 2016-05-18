package com.qp.ddz.game.cmd;

 
import com.qp.lib.utility.NetEncoding;
import com.qp.ddz.define.GDF;
import com.smw.net.ICmd;

/**
*
* 购买完整源码联系 q344717871
* 
*/

/***
 * 叫分状态
 * 
 */
public class CMD_S_StatusCall implements ICmd {
	public int cmd;	//GAME_SCENE_Status_Call
	
	// 时间信息
	public byte cbTimeOutCard; // 出牌时间
	public byte cbTimeCallScore; // 叫分时间
	public byte cbTimeStarGame; // 开始时间
	public byte cbTimeHeadOutCard; // 首出时间

	// 游戏信息
	public long lCellScore; // 单元积分
	public int nCurrentUser; // 当前玩家
	public int nBankerScore; // 庄家叫分
	public int[] nScoreInfo = new int[GDF.GAME_PLAYER]; // 叫分信息
	public int[] nHandCardData = new int[GDF.NORMAL_COUNT]; // 手上扑克

	public long[] lTurnScore = new long[GDF.GAME_PLAYER]; // 积分信息
	public long[] lCollectScore = new long[GDF.GAME_PLAYER]; // 积分信息

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		
		cmd = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		
		cbTimeOutCard = data[nIndex++];
		cbTimeCallScore = data[nIndex++];
		cbTimeStarGame = data[nIndex++];
		cbTimeHeadOutCard = data[nIndex++];

		lCellScore = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		nCurrentUser = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		nBankerScore = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			nScoreInfo[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		for (int i = 0; i < GDF.NORMAL_COUNT; i++) {
			nHandCardData[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			lTurnScore[i] = NetEncoding.read8Byte(data, nIndex);
			nIndex += 8;
		}
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			lCollectScore[i] = NetEncoding.read8Byte(data, nIndex);
			nIndex += 8;
		}
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
