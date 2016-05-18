package com.qp.ddz.game.cmd;

 
import com.qp.lib.utility.NetEncoding;
import com.smw.net.ICmd;

/**
*
* 购买完整源码联系 q344717871
* 
*/

/**
 * 放弃出牌
 * 
 */
public class CMD_S_PassCard implements ICmd {
	public int cmd; //
	public int nTurnOver; // 一轮结束
	public int nCurrentUser; // 当前玩家
	public int nPassCardUser; // 放弃玩家

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cmd = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		nTurnOver = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		nCurrentUser = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		nPassCardUser = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
