package com.qp.ddz.game;

import com.qp.ddz.define.GDF;
/*
*
* 购买完整源码联系 q344717871
* 
* */

/**
 * 逻辑分析结构
 * 
 * @author CHENPENG
 * 
 */
public class CAnalyseResult {
	public int nFourCount; // 四张张数
	public int nThreeCount; // 三张张数
	public int nDoubleCount; // 二张张数
	public int nSignedCount; // 单张张数
	public int[] nFourCardData; // 四张数据
	public int[] nThreeCardData; // 三张数据
	public int[] nDoubleCardData; // 二张数据
	public int[] nSignedCardData; // 单张数据

	/**
	 * 构造函数
	 */
	public CAnalyseResult() {
		nFourCount = 0;
		nThreeCount = 0;
		nDoubleCount = 0;
		nSignedCount = 0;
		nFourCardData = new int[GDF.MAX_CARDCOUNT];
		nThreeCardData = new int[GDF.MAX_CARDCOUNT];
		nDoubleCardData = new int[GDF.MAX_CARDCOUNT];
		nSignedCardData = new int[GDF.MAX_CARDCOUNT];
	}

	/**
	 * 重置
	 */
	public void ResetData() {
		nFourCount = 0;
		nThreeCount = 0;
		nDoubleCount = 0;
		nSignedCount = 0;
		for (int i = 0; i < GDF.MAX_CARDCOUNT; i++) {
			nFourCardData[i] = 0;
			nThreeCardData[i] = 0;
			nDoubleCardData[i] = 0;
			nSignedCardData[i] = 0;
		}
	}
}
