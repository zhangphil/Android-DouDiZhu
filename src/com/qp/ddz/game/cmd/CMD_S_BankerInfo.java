package com.qp.ddz.game.cmd;
 
import com.qp.lib.utility.NetEncoding;
import com.smw.net.ICmd;

/**
*
* 购买完整源码联系 q344717871
* 
*/

/**
 * 庄家信息
 * 
 */
public class CMD_S_BankerInfo implements ICmd {
	public int cmd;//
	public int nBankerUser; // 庄家玩家
	public int nCurrentUser; // 当前玩家
	public int nBankerScore; // 庄家叫分
	public int nBankerCard[] = new int[3]; // 庄家扑克

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cmd = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		nBankerUser = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		nCurrentUser = NetEncoding.read2Byte(data, nIndex);
		nIndex += 2;
		nBankerScore = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		for (int i = 0; i < 3; i++) {
			nBankerCard[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		return nIndex - pos;
	}

	@Override
	public int WriteToByteArray(byte[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}
