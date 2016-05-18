package com.qp.ddz.game;
/*
*
* 购买完整源码联系 q344717871
* 
* */

import java.util.Random;

import com.qp.ddz.define.GDF;
import com.qp.lib.utility.Util;

public class CGameLogic {

	public static final int	CardData[]	= {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16,
			0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x21, 0x22, 0x23, 0x24, 0x25, 0x26, 0x27, 0x28, 0x29, 0x2A, 0x2B, 0x2C, 0x2D, 0x31, 0x32, 0x33, 0x34,
			0x35, 0x36, 0x37, 0x38, 0x39, 0x3A, 0x3B, 0x3C, 0x3D, 0x4E, 0x4F,};

	// 构造函数
	public CGameLogic() {

	}

	public void RandCardList(int nCardData[], int nCardCount) {
		int nTempData[] = new int[54];
		System.arraycopy(CardData, 0, nTempData, 0, CardData.length);
		Random rand = new Random(System.currentTimeMillis());

		int nRandCount = 0, nPosition = 0;
		do {
			nPosition = rand.nextInt(nCardCount - nRandCount);
			nCardData[nRandCount++] = nTempData[nPosition];
			nTempData[nPosition] = nTempData[nCardCount - nRandCount];
		} while (nRandCount < nCardCount);
	}

	/**
	 * 搜索出牌
	 * 
	 * @param nCard
	 * @param nCount
	 * @param nTurnCard
	 * @param nTurnCount
	 * @param nResultCard
	 * @return
	 */
	public static int SeachOutCard(int[] nCard, int nCount, int[] nTurnCard, int nTurnCount, int[] nResultCard) {
		// 获取类型
		int nTurnType = GetCardType(nTurnCard, nTurnCount);

		int nValue = 0;
		// 出牌分析
		switch (nTurnType) {
		// 错误类型
			case GDF.CT_ERROR : {
				// 获取数值
				nValue = GetCardLogicValue(nCard[nCount - 1]);
				int nSameCount = 1;
				// 多牌判断
				for (int i = 1; i < nCount; ++i) {
					if (GetCardLogicValue(nCard[nCount - 1 - i]) == nValue) {
						nSameCount++;
					} else {
						break;
					}
				}
				// 完成处理
				for (int i = 0; i < nSameCount; i++) {
					nResultCard[i] = nCard[nCount - 1 - i];
				}
				return nSameCount;
			}
			// 单牌，对牌，三条类型
			case GDF.CT_1 :
			case GDF.CT_2 :
			case GDF.CT_3 : {
				// 获取数值
				nValue = GetCardLogicValue(nTurnCard[0]);
				// 分析扑克
				CAnalyseResult AnalyseResult = new CAnalyseResult();
				AnalysebCardData(nCard, nCount, AnalyseResult);

				int nIndex = 0;
				// 寻找单牌
				if (nTurnCount <= 1) {
					for (int i = 0; i < AnalyseResult.nSignedCount; ++i) {
						nIndex = AnalyseResult.nSignedCount - i - 1;
						if (GetCardLogicValue(AnalyseResult.nSignedCardData[nIndex]) > nValue) {
							System.arraycopy(AnalyseResult.nSignedCardData, nIndex, nResultCard, 0, nTurnCount);
							return nTurnCount;
						}
					}
				}
				if (nTurnCount <= 2) {
					for (int i = 0; i < AnalyseResult.nDoubleCount; ++i) {
						nIndex = (AnalyseResult.nDoubleCount - i - 1) * 2;
						if (GetCardLogicValue(AnalyseResult.nDoubleCardData[nIndex]) > nValue) {
							System.arraycopy(AnalyseResult.nDoubleCardData, nIndex, nResultCard, 0, nTurnCount);
							return nTurnCount;
						}
					}
				}
				if (nTurnCount <= 3) {
					for (int i = 0; i < AnalyseResult.nThreeCount; ++i) {
						nIndex = (AnalyseResult.nThreeCount - i - 1) * 3;
						if (GetCardLogicValue(AnalyseResult.nThreeCardData[nIndex]) > nValue) {
							System.arraycopy(AnalyseResult.nThreeCardData, nIndex, nResultCard, 0, nTurnCount);
							return nTurnCount;
						}
					}
				}
				break;
			}
			// 单连类型
			case GDF.CT_1_LINE : {
				// 长度判断
				if (nCount < nTurnCount) {
					break;
				}

				// 获取数值
				nValue = GetCardLogicValue(nTurnCard[0]);

				// 搜索连牌
				for (int i = nTurnCount - 1; i < nCount; i++) {
					// 获取数值
					int nHandValue = GetCardLogicValue(nCard[nCount - i - 1]);
					// 构造判断
					if (nHandValue >= 15) {
						break;
					}
					if (nHandValue <= nValue) {
						continue;
					}
					// 搜索连牌
					int nLineCount = 0;
					for (int j = nCount - i - 1; j < nCount; j++) {
						if (GetCardLogicValue(nCard[j]) + nLineCount == nHandValue) {
							nResultCard[nLineCount++] = nCard[j];
							// 完成判断
							if (nLineCount == nTurnCount) {
								return nTurnCount;
							}
						}
					}
				}
				break;
			}
			case GDF.CT_2_LINE : {
				// 长度判断
				if (nCount < nTurnCount) {
					break;
				}

				// 获取数值
				nValue = GetCardLogicValue(nTurnCard[0]);

				// 搜索连牌
				for (int i = nTurnCount - 1; i < nCount; i++) {
					// 获取数值
					int nHandValue = GetCardLogicValue(nCard[nCount - i - 1]);
					// 构造判断
					if (nHandValue <= nValue) {
						continue;
					}
					if (nTurnCount > 1 && nHandValue >= 15) {
						break;
					}
					// 搜索连牌
					int nLineCount = 0;
					for (int j = nCount - i - 1; j < nCount - 1; j++) {
						if (GetCardLogicValue(nCard[j]) + nLineCount == nHandValue && GetCardLogicValue(nCard[j + 1]) + nLineCount == nHandValue) {
							nResultCard[nLineCount * 2] = nCard[j];
							nResultCard[(nLineCount++) * 2 + 1] = nCard[j + 1];
							// 完成判断
							if (nLineCount * 2 == nTurnCount) {
								return nTurnCount;
							}
						}
					}
				}
				break;
			}
			case GDF.CT_3_LINE :
			case GDF.CT_3_LINE_1 :
			case GDF.CT_3_LINE_2 : {
				// 长度判断
				if (nCount < nTurnCount) {
					break;
				}

				// 获取数值
				nValue = 0;
				for (int i = 0; i < nTurnCount - 2; i++) {
					nValue = GetCardLogicValue(nTurnCard[i]);
					if (GetCardLogicValue(nTurnCard[i + 1]) != nValue) {
						continue;
					}
					if (GetCardLogicValue(nTurnCard[i + 2]) != nValue) {
						continue;
					}
					break;
				}
				// 属性数值
				int nTurnLintCount = 0;
				if (nTurnType == GDF.CT_3_LINE_1) {
					nTurnLintCount = nTurnCount / 4;
				} else if (nTurnType == GDF.CT_3_LINE_2) {
					nTurnLintCount = nTurnCount / 5;
				} else {
					nTurnLintCount = nTurnCount / 3;
				}

				// 搜索连牌
				for (int i = nTurnLintCount * 3 - 1; i < nCount; i++) {
					// 获取数值
					int nHandValue = GetCardLogicValue(nCard[nCount - i - 1]);
					// 构造判断
					if (nHandValue <= nValue || nHandValue > 15) {
						continue;
					}
					if (nTurnLintCount > 1) {
						break;
					}

					int nLineCount = 0;
					for (int j = (nCount - i - 1); j < nCount - 2; j++) {
						int nResultCount = 0;
						// 三牌判断
						if (GetCardLogicValue(nCard[j]) + nLineCount != nHandValue) {
							continue;
						}
						if (GetCardLogicValue(nCard[j + 1]) + nLineCount != nHandValue) {
							continue;
						}
						if (GetCardLogicValue(nCard[j + 2]) + nLineCount != nHandValue) {
							continue;
						}

						nResultCard[nLineCount * 3] = nCard[j];
						nResultCard[nLineCount * 3 + 1] = nCard[j + 1];
						nResultCard[(nLineCount++) * 3 + 2] = nCard[j + 2];

						// 完成判断
						if (nLineCount == nTurnLintCount) {
							int[] nLeftCard = new int[GDF.MAX_CARDCOUNT];
							nResultCount = nLineCount * 3;
							int nLeftCount = nCount;
							System.arraycopy(nCard, 0, nLeftCard, 0, nCount);
							RemoveCard(nResultCard, nResultCount, nLeftCard, nLeftCount);
							nLeftCount = nCount - nLineCount * 3;
							// 分析扑克
							CAnalyseResult AnalyseLeft = new CAnalyseResult();
							AnalysebCardData(nLeftCard, nLeftCount, AnalyseLeft);

							if (nTurnType == GDF.CT_3_LINE_1) {
								// 提取单牌
								for (int k = 0; k < AnalyseLeft.nSignedCount; k++) {
									// 中止判断
									if (nResultCount == nTurnCount) {
										break;
									}

									// 设置扑克
									nResultCard[nResultCount++] = AnalyseLeft.nSignedCardData[AnalyseLeft.nSignedCount - k - 1];
								}
								// 提取对牌
								for (int k = 0; k < AnalyseLeft.nDoubleCount * 2; k++) {
									// 中止判断
									if (nResultCount == nTurnCount) {
										break;
									}

									// 设置扑克
									nResultCard[nResultCount++] = AnalyseLeft.nDoubleCardData[AnalyseLeft.nDoubleCount * 2 - k - 1];
								}
								// 提取三牌
								for (int k = 0; k < AnalyseLeft.nThreeCount * 3; k++) {
									// 中止判断
									if (nResultCount == nTurnCount) {
										break;
									}

									// 设置扑克
									nResultCard[nResultCount++] = AnalyseLeft.nThreeCardData[AnalyseLeft.nThreeCount * 3 - k - 1];
								}
								// 提取四牌
								for (int k = 0; k < AnalyseLeft.nFourCount * 4; k++) {
									// 中止判断
									if (nResultCount == nTurnCount) {
										break;
									}

									// 设置扑克
									nResultCard[nResultCount++] = AnalyseLeft.nFourCardData[AnalyseLeft.nFourCount * 4 - k - 1];
								}

							}
							// 对牌处理
							if (nTurnType == GDF.CT_3_LINE_2) {
								// 提取对牌
								for (int k = 0; k < AnalyseLeft.nDoubleCount; k++) {
									// 中止判断
									if (nResultCount == nTurnCount) {
										break;
									}
									nResultCard[nResultCount++] = AnalyseLeft.nDoubleCardData[(AnalyseLeft.nDoubleCount - k - 1) * 2];
									nResultCard[nResultCount++] = AnalyseLeft.nDoubleCardData[(AnalyseLeft.nDoubleCount - k - 1) * 2 + 1];
								}
								// 提取三牌
								for (int k = 0; k < AnalyseLeft.nThreeCount; k++) {
									// 中止判断
									if (nResultCount == nTurnCount) {
										break;
									}
									nResultCard[nResultCount++] = AnalyseLeft.nThreeCardData[(AnalyseLeft.nThreeCount - k - 1) * 3];
									nResultCard[nResultCount++] = AnalyseLeft.nThreeCardData[(AnalyseLeft.nThreeCount - k - 1) * 3 + 1];
								}
								// 提取四牌
								for (int k = 0; k < AnalyseLeft.nFourCount; k++) {
									// 中止判断
									if (nResultCount == nTurnCount) {
										break;
									}
									nResultCard[nResultCount++] = AnalyseLeft.nFourCardData[(AnalyseLeft.nFourCount - k - 1) * 4];
									nResultCard[nResultCount++] = AnalyseLeft.nFourCardData[(AnalyseLeft.nFourCount - k - 1) * 4 + 1];
								}
							}
							if (nResultCount == nTurnCount) {
								return nTurnCount;
							}
						}
					}
				}
				break;
			}
		}

		// 搜索炸弹
		if (nCount >= 4 && (nTurnType != GDF.CT_MISSILE_CARD)) {
			nValue = 0;
			if (nTurnType == GDF.CT_BOME_CARD) {
				nValue = GetCardLogicValue(nTurnCard[0]);
			}
			// 搜索炸弹
			int nHandValue;
			for (int i = 3; i < nCount; i++) {
				// 获取数值
				nHandValue = GetCardLogicValue(nCard[nCount - i - 1]);
				// 构造判断
				if (nHandValue <= nValue) {
					continue;
				}
				// 炸弹判断
				int nTempValue = GetCardLogicValue(nCard[nCount - i - 1]);
				int j = 1;
				for (; j < 4; j++) {
					if (GetCardLogicValue(nCard[nCount + j - i - 1]) != nTempValue) {
						break;
					}
				}
				if (j != 4) {
					continue;
				}

				nResultCard[0] = nCard[nCount - i - 1];
				nResultCard[1] = nCard[nCount - i];
				nResultCard[2] = nCard[nCount - i + 1];
				nResultCard[3] = nCard[nCount - i + 2];
				return 4;
			}
		}
		// 搜索火箭
		if (nCount >= 2 && (nCard[0] == 0x4F) && (nCard[1] == 0x4E)) {
			nResultCard[0] = nCard[0];
			nResultCard[1] = nCard[1];
			return 2;
		}
		return 0;
	}

	// 获取类型
	// 获取类型
	public static int GetCardType(int nCardData[], int nCardCount) {
		// 简单牌型
		switch (nCardCount) {
			case 0 : // 空牌
			{
				return GDF.CT_ERROR;
			}
			case 1 : // 单牌
			{
				return GDF.CT_1;
			}
			case 2 : // 对牌火箭
			{
				if ((nCardData[0] == 0x4f) && (nCardData[1] == 0x4E)) {
					return GDF.CT_MISSILE_CARD; // 火箭
				}
				if (GetCardLogicValue(nCardData[0]) == GetCardLogicValue(nCardData[1])) {
					return GDF.CT_2; // 对牌
				}
				return GDF.CT_ERROR;
			}
		}
		// 分析扑克
		CAnalyseResult AnalyseResult = new CAnalyseResult();

		AnalysebCardData(nCardData, nCardCount, AnalyseResult);

		if (AnalyseResult.nFourCount == 1) {
			if (nCardCount == 4) {
				return GDF.CT_BOME_CARD;
			}
			if (AnalyseResult.nSignedCount == 2 && nCardCount == 6) {
				return GDF.CT_4_LINE_1;
			}
			if (AnalyseResult.nDoubleCount == 2 && nCardCount == 8) {
				return GDF.CT_4_LINE_2;
			}
			return GDF.CT_ERROR;

		}
		int nTempCardData = 0;
		int nFirstLogicValue = 0;
		if (AnalyseResult.nThreeCount > 0) {
			if (AnalyseResult.nThreeCount == 1 && nCardCount == 3) {
				return GDF.CT_3;
			}
			if (AnalyseResult.nThreeCount > 1) {
				nTempCardData = AnalyseResult.nThreeCardData[0];
				nFirstLogicValue = GetCardLogicValue(nTempCardData);

				if (nFirstLogicValue >= 15) {
					return GDF.CT_ERROR;
				}

				for (int i = 1; i < AnalyseResult.nThreeCount; i++) {
					if (nFirstLogicValue != GetCardLogicValue(AnalyseResult.nThreeCardData[i * 3]) + i) {
						return GDF.CT_ERROR;
					}
				}
			}
			// 牌型判断
			if (AnalyseResult.nThreeCount * 3 == nCardCount) {
				return GDF.CT_3_LINE;
			}
			if (AnalyseResult.nThreeCount * 4 == nCardCount) {
				return GDF.CT_3_LINE_1;
			}
			if (AnalyseResult.nThreeCount * 5 == nCardCount && AnalyseResult.nDoubleCount == AnalyseResult.nThreeCount) {
				return GDF.CT_3_LINE_2;
			}

			return GDF.CT_ERROR;
		}

		// 两张类型
		if (AnalyseResult.nDoubleCount >= 3) {
			nTempCardData = AnalyseResult.nDoubleCardData[0];
			nFirstLogicValue = GetCardLogicValue(nTempCardData);

			// 错误过滤
			if (nFirstLogicValue >= 15) {
				return GDF.CT_ERROR;
			}

			// 连牌判断
			for (int i = 1; i < AnalyseResult.nDoubleCount; i++) {
				if (nFirstLogicValue != (GetCardLogicValue(AnalyseResult.nDoubleCardData[i * 2]) + i)) {
					return GDF.CT_ERROR;
				}
			}

			// 二连判断
			if (AnalyseResult.nDoubleCount * 2 == nCardCount) {
				return GDF.CT_2_LINE;
			}

			return GDF.CT_ERROR;
		}

		// 单张判断
		if (AnalyseResult.nSignedCount >= 5 && AnalyseResult.nSignedCount == nCardCount) {
			nTempCardData = AnalyseResult.nSignedCardData[0];
			nFirstLogicValue = GetCardLogicValue(nTempCardData);

			// 错误过滤
			if (nFirstLogicValue >= 15) {
				return GDF.CT_ERROR;
			}

			// 连牌判断

			for (int i = 0; i < AnalyseResult.nSignedCount; i++) {
				if (nFirstLogicValue != GetCardLogicValue(AnalyseResult.nSignedCardData[i]) + i) {
					return GDF.CT_ERROR;
				}
			}

			return GDF.CT_1_LINE;
		}
		return GDF.CT_ERROR;
	}

	// 排列扑克
	public static void SortCardList(int nCardData[], int nCardCount, int nSortType) {
		if (nCardCount == 0) {
			return;
		}

		int[] nSortValue = new int[GDF.MAX_CARDCOUNT];
		for (int i = 0; i < nCardCount; i++) {
			nSortValue[i] = GetCardLogicValue(nCardData[i]);
		}

		boolean bSorted = true;
		int nThreeCount, nLast = nCardCount - 1;

		do {
			bSorted = true;
			for (int i = 0; i < nLast; i++) {
				if (nSortValue[i] < nSortValue[i + 1] || (nSortValue[i] == nSortValue[i + 1] && nCardData[i] < nCardData[i + 1])) {
					nThreeCount = nCardData[i];
					nCardData[i] = nCardData[i + 1];
					nCardData[i + 1] = nThreeCount;
					nThreeCount = nSortValue[i];
					nSortValue[i] = nSortValue[i + 1];
					nSortValue[i + 1] = nThreeCount;
					bSorted = false;
				}
			}
			nLast--;
		} while (bSorted == false);

		if (nSortType == GDF.ST_COUNT) {
			int nIndex = 0;
			CAnalyseResult AnalyseResult = new CAnalyseResult();

			AnalysebCardData(nCardData, nCardCount, AnalyseResult);
			try {
				System.arraycopy(AnalyseResult.nFourCardData, 0, nCardData, nIndex, AnalyseResult.nFourCount * 4);
				nIndex += (AnalyseResult.nFourCount * 4);

				System.arraycopy(AnalyseResult.nThreeCardData, 0, nCardData, nIndex, AnalyseResult.nThreeCount * 3);
				nIndex += (AnalyseResult.nThreeCount * 3);

				System.arraycopy(AnalyseResult.nDoubleCardData, 0, nCardData, nIndex, AnalyseResult.nDoubleCount * 2);
				nIndex += (AnalyseResult.nDoubleCount * 2);

				System.arraycopy(AnalyseResult.nSignedCardData, 0, nCardData, nIndex, AnalyseResult.nSignedCount);
				nIndex += AnalyseResult.nSignedCount;
			} catch (IndexOutOfBoundsException ex) {
				Util.e("SortCardList", "ERROR");
			}
		}
	}

	// 删除扑克
	public static boolean RemoveCard(int nRemoveCard[], int nRemoveCount, int nCardData[], int nCardCount) {
		// 校验
		if (nRemoveCount > nCardCount || nCardCount > GDF.MAX_CARDCOUNT) {
			return false;
		}

		// 定义变量
		int nDeleteCount = 0;
		int[] nTempCardData = new int[GDF.MAX_CARDCOUNT];
		System.arraycopy(nCardData, 0, nTempCardData, 0, nCardCount);

		// 置零扑克
		for (int i = 0; i < nRemoveCount; i++) {
			for (int j = 0; j < nCardCount; j++) {
				if (nRemoveCard[i] == nTempCardData[j]) {
					nDeleteCount++;
					nTempCardData[j] = 0;
					break;
				}
			}
		}

		if (nDeleteCount != nRemoveCount) {
			Util.e("REMOVECARD", "ERROR");
			return false;
		}
		int nCardPos = 0;
		for (int i = 0; i < nCardCount; i++) {
			if (nTempCardData[i] != 0) {
				nCardData[nCardPos++] = nTempCardData[i];
			}
		}
		return true;
	}

	// 有效判断
	public static boolean IsValidCard(int nCardData) {
		if (nCardData == 0x4E || nCardData == 0x4F) {
			return true;
		}
		// 数值花色
		int nCardValue = GetCardValue(nCardData);
		int nCardColor = GetCardColor(nCardData);

		if (nCardColor <= 0x30 && nCardValue >= 0x01 && nCardValue <= 0x0D) {
			return true;
		}

		return false;
	}

	// 获取数值
	public static int GetCardValue(int nCardData) {
		return nCardData & GDF.MASK_VALUE;
	}

	// 获取花色
	public static int GetCardColor(int nCardData) {
		return nCardData & GDF.MASK_COLOR;
	}

	// 逻辑数值
	public static int GetCardLogicValue(int nCardData) {
		// 数值花色
		int nCardValue = GetCardValue(nCardData);
		int nCardColor = GetCardColor(nCardData);

		// 转换
		if (nCardColor == 0x40) {
			return nCardValue + 2;
		}
		return (nCardValue <= 2) ? (nCardValue + 13) : nCardValue;
	}

	public static int getCardIndex(int data) {
		
		int nCardValue = GetCardValue(data);
		int nCardColor = (GetCardColor(data) >> 4);
		return nCardColor * 9 + nCardValue;
	}

	// 对比扑克
	public static boolean CompareCard(int nFirstCard[], int nNextCard[], int nFirstCount, int nNextCount) {
		// 获取类型
		int nNextType = GetCardType(nNextCard, nNextCount);

		// 类型判断
		if (nNextType == GDF.CT_ERROR) {
			return false;
		}
		if (nNextType == GDF.CT_MISSILE_CARD) {
			return true;
		}

		// 获取类型
		int nFirstType = GetCardType(nFirstCard, nFirstCount);

		// 炸弹判断
		if (nFirstType != GDF.CT_BOME_CARD && nNextType == GDF.CT_BOME_CARD) {
			return true;
		}
		if (nFirstType == GDF.CT_BOME_CARD && nNextType != GDF.CT_BOME_CARD) {
			return false;
		}

		// 规则判断
		if (nFirstType != nNextType || nFirstCount != nNextCount) {
			return false;
		}

		// 开始对比
		switch (nNextType) {
			case GDF.CT_1 :
			case GDF.CT_2 :
			case GDF.CT_3 :
			case GDF.CT_1_LINE :
			case GDF.CT_2_LINE :
			case GDF.CT_3_LINE :
			case GDF.CT_BOME_CARD : {
				return GetCardLogicValue(nNextCard[0]) > GetCardLogicValue(nFirstCard[0]);
			}
			case GDF.CT_3_LINE_1 :
			case GDF.CT_3_LINE_2 : {
				// 分析获取数值
				CAnalyseResult AnalyseResult = new CAnalyseResult();
				AnalysebCardData(nNextCard, nNextCount, AnalyseResult);
				int nNextLogicValue = GetCardLogicValue(AnalyseResult.nThreeCardData[0]);
				AnalysebCardData(nFirstCard, nFirstCount, AnalyseResult);
				int nFirstLogicValue = GetCardLogicValue(AnalyseResult.nThreeCardData[0]);

				// 返回对比
				return nNextLogicValue > nFirstLogicValue;
			}
			case GDF.CT_4_LINE_1 :
			case GDF.CT_4_LINE_2 : {
				// 分析获取数值
				CAnalyseResult AnalyseResult = new CAnalyseResult();
				AnalysebCardData(nNextCard, nNextCount, AnalyseResult);
				int nNextLogicValue = GetCardLogicValue(AnalyseResult.nFourCardData[0]);
				AnalysebCardData(nFirstCard, nFirstCount, AnalyseResult);
				int nFirstLogicValue = GetCardLogicValue(AnalyseResult.nFourCardData[0]);

				// 返回对比
				return nNextLogicValue > nFirstLogicValue;
			}
		}
		return false;
	}

	// 分析扑克
	public static void AnalysebCardData(int nCardData[], int nCardCount, CAnalyseResult AnalyseResult) {
		// 重置数据
		AnalyseResult.ResetData();
		for (int i = 0; i < nCardCount; i++) {
			// 变量定义
			int nSameCount = 1;
			int nLogicValue = GetCardLogicValue(nCardData[i]);
			// 搜索同牌
			for (int j = i + 1; j < nCardCount; j++) {
				// 获取扑克
				if (GetCardLogicValue(nCardData[j]) != nLogicValue) {
					break;
				}
				// 设置变量
				nSameCount++;
			}

			// 设置结果
			int nIndex = 0;
			switch (nSameCount) {
				case 1 : // 单张
				{
					nIndex = AnalyseResult.nSignedCount++;
					AnalyseResult.nSignedCardData[nIndex * nSameCount] = nCardData[i];
					break;
				}
				case 2 : // 两张
				{
					nIndex = AnalyseResult.nDoubleCount++;
					for (int k = 0; k < nSameCount; k++) {
						AnalyseResult.nDoubleCardData[nIndex * nSameCount + k] = nCardData[i + k];
					}
					break;
				}
				case 3 : // 三张
				{
					nIndex = AnalyseResult.nThreeCount++;
					for (int k = 0; k < nSameCount; k++) {
						AnalyseResult.nThreeCardData[nIndex * nSameCount + k] = nCardData[i + k];
					}
					break;
				}
				case 4 : // 四张
				{
					nIndex = AnalyseResult.nFourCount++;
					for (int k = 0; k < nSameCount; k++) {
						AnalyseResult.nFourCardData[nIndex * nSameCount + k] = nCardData[i + k];
					}
					break;
				}
			}
			// 设置索引
			i += nSameCount - 1;
		}
	}
}
