package com.qp.ddz.game.cmd;

 
import com.qp.lib.utility.NetEncoding;
import com.qp.ddz.define.GDF;
import com.smw.net.ICmd;


/**
 * 游戏结束
 * 
 */
public class CMD_S_GameConClude implements ICmd{
	public int cmd;//S_GameConClude
	// 积分变量
	public long lCellScore; // 单元积分
	public long[] lGameScore = new long[GDF.GAME_PLAYER]; // 游戏积分
	// 春天标志
	public int nChunTian; // 春天标志
	public int nFanChunTian; // 春天标志
	// 炸弹信息
	public int nBombCount; // 炸弹个数
	public int[] nEachBombCount = new int[GDF.GAME_PLAYER]; // 炸弹个数
	// 游戏信息
	public int nBankerScore; // 叫分数目
	public int[] nCardCount = new int[GDF.GAME_PLAYER]; // 扑克数目
	public int[] nHandCardData = new int[GDF.FULL_COUNT]; // 扑克列表

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int nIndex = pos;
		cmd = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		
		lCellScore = NetEncoding.read4Byte(data, nIndex);
		nIndex += 4;
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			lGameScore[i] = NetEncoding.read8Byte(data, nIndex);
			nIndex += 8;
		}

		nChunTian = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		nFanChunTian = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		nBombCount = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			nEachBombCount[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
		}
		nBankerScore = (data[nIndex] < 0 ? (data[nIndex] + 256) : data[nIndex]);
		nIndex++;
		int nCount = 0;
		for (int i = 0; i < GDF.GAME_PLAYER; i++) {
			nCardCount[i] = (data[nIndex] < 0
					? (data[nIndex] + 256)
					: data[nIndex]);
			nIndex++;
			nCount += nCardCount[i];
		}
		for (int i = 0; i < GDF.FULL_COUNT; i++) {
			nHandCardData[i] = (data[nIndex] < 0
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
