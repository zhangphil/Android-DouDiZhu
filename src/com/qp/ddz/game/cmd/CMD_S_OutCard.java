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
 * 用户出牌
 * 
 */
public class CMD_S_OutCard implements ICmd {
	public int cmd; // S_OUT_CARD
	public int		nCardCount;								// 出牌数目
	public int		nCurrentUser;								// 当前玩家
	public int		nOutCardUser;								// 出牌玩家
	public int[]	nCardData	= new int[GDF.MAX_CARDCOUNT];	// 扑克列表

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cmd = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		
		nCardCount = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		nCurrentUser = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		nOutCardUser = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		for (int i = 0; i < nCardCount; i++) {
			nCardData[i] = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
			nIndex++;
		}
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
