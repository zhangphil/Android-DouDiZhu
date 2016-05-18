package com.qp.ddz.game.cmd;

 
import com.qp.lib.utility.NetEncoding;
import com.qp.ddz.define.GDF;
import com.smw.net.ICmd;
/**
*
* 购买完整源码联系 q344717871
* 
*/

/**
 * 空闲状态
 * 
 */
public class CMD_S_StatusFree implements ICmd {
	public int cmd;//CMD_S_StatusFree 
	// 游戏属性
	public int		lCellScore;									// 基础积分
	// 时间信息
	public byte		cbTimeOutCard;									// 出牌时间
	public byte		cbTimeCallScore;								// 叫分时间
	public byte		cbTimeStarGame;								// 开始时间
	public byte		cbTimeHeadOutCard;								// 首出时间

	// 历史积分
	public long[]	lTurnScore		= new long[GDF.GAME_PLAYER];	// 积分信息
	public long[]	lCollectScore	= new long[GDF.GAME_PLAYER];	// 积分信息

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cmd = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;

		lCellScore = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;

		cbTimeOutCard = data[nIndex++];
		cbTimeCallScore = data[nIndex++];
		cbTimeStarGame = data[nIndex++];
		cbTimeHeadOutCard = data[nIndex++];

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
