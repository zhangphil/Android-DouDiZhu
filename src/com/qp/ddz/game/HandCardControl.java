package com.qp.ddz.game;
/*
*
* 购买完整源码联系 q344717871
* 
* */

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

import com.qp.ddz.define.GDF;

public class HandCardControl {

	@SuppressWarnings("unused")
	private static final String	TAG					= "HandCardControl";

	public Card					m_CardData[]		= new Card[GDF.MAX_CARDCOUNT];

	public int					m_nCardCount		= 0;

	public Point				m_ptPos				= new Point();

	public Point				m_ptBasPos			= new Point();
	public boolean				m_bClickable		= false;

	public int					m_nXspace;
	public int					m_nYspace;

	public int					m_nSelectIndex		= GDF.INVALID_CHAIR;
	public int					m_nSelectIndexEx	= GDF.INVALID_CHAIR;

	public int					m_nCardW;
	public int					m_nCardH;

	public int					m_nAreaW;
	public int					m_nAreaH;

	public HandCardControl() {
		for (int i = 0; i < m_CardData.length; i++)
			m_CardData[i] = new Card();
	}

	public void setPosition(int x, int y, int spacex, int spacey, int cardw, int cardh) {
		m_ptBasPos.set(x, y);
		m_nXspace = spacex;
		m_nYspace = spacey;
		m_nCardW = cardw;
		m_nCardH = cardh;
		m_nAreaH = cardh + spacey;
	}

	public void onSetCardData(int data[], int count) {
		for (int i = 0; i < count && i < m_CardData.length; i++) {
			m_CardData[i].data = data[i];
			m_CardData[i].shoot = false;
		}

		if (count > 0) {
			m_nAreaW = (count - 1) * m_nXspace + m_nCardW;
			m_ptPos.set(m_ptBasPos.x - m_nAreaW / 2, m_ptBasPos.y);
		} else {
			m_nAreaW = 0;
		}

		m_nCardCount = count;
	}

	public int getCardCount() {
		return m_nCardCount;
	}

	public void addCardData(int data) {
		if (m_nCardCount < GDF.MAX_CARDCOUNT) {
			m_CardData[m_nCardCount].data = data;
			m_CardData[m_nCardCount++].shoot = false;
			m_nAreaW = (m_nCardCount - 1) * m_nXspace + m_nCardW;
			m_ptPos.set(m_ptBasPos.x - m_nAreaW / 2, m_ptBasPos.y);
		}
	}

	public void onDrawHandCardControl(Canvas canvas, CardRes res) {
		if (m_nCardCount > 0) {

			for (int i = 0; i < m_nCardCount; i++) {
				boolean bmask = isMask(i);
				res.onDrawCard(canvas, m_CardData[i].data, m_ptPos.x + i * m_nXspace, m_ptPos.y + (m_CardData[i].shoot ? 0 : m_nYspace), bmask);
			}
		}
	}
	private boolean isMask(int index) {
		if (m_nSelectIndex != GDF.INVALID_CHAIR) {
			if (m_nSelectIndexEx == GDF.INVALID_CHAIR) {
				return index == m_nSelectIndex;
			} else {
				if ((index >= m_nSelectIndex && index <= m_nSelectIndexEx) || (index >= m_nSelectIndexEx && index <= m_nSelectIndex))
					return true;
			}
		}
		return false;
	}
	public void onReleaseCard() {
		m_nCardCount = 0;
		for (int i = 0; i < m_CardData.length; i++)
			m_CardData[i].reSet();
	}

	public void setClickable(boolean bclick) {
		m_bClickable = bclick;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (m_bClickable && m_nCardCount > 0) {
			int action = event.getAction();
			int x = (int) event.getX(), y = (int) event.getY();
			switch (action) {
				case MotionEvent.ACTION_DOWN :
					m_nSelectIndex = getSelectIndex(x, y);
					if (m_nSelectIndex != GDF.INVALID_CHAIR)
						return true;
					break;
				case MotionEvent.ACTION_MOVE :
					if (m_nSelectIndex != GDF.INVALID_CHAIR) {
						m_nSelectIndexEx = getSelectIndexEx(x, y);
					}
					return true;
				case MotionEvent.ACTION_UP :
					if (m_nSelectIndex != GDF.INVALID_CHAIR) {
						m_nSelectIndexEx = getSelectIndexEx(x, y);
						if (m_nSelectIndexEx == m_nSelectIndex && m_nSelectIndexEx < m_nCardCount) {
							m_CardData[m_nSelectIndexEx].shoot = !m_CardData[m_nSelectIndexEx].shoot;
						} else if (m_nSelectIndexEx != GDF.INVALID_CHAIR && m_nSelectIndex < m_nCardCount) {
							int start = Math.min(m_nSelectIndex, m_nSelectIndexEx);
							int end = Math.max(m_nSelectIndex, m_nSelectIndexEx);
							for (int i = start; i <= end; i++) {
								m_CardData[i].shoot = !m_CardData[i].shoot;
							}
						}
					}
					m_nSelectIndex = GDF.INVALID_CHAIR;
					m_nSelectIndexEx = GDF.INVALID_CHAIR;
					return true;
			}
		}
		return false;
	}

	private int getSelectIndexEx(int x, int y) {
		if (x >= m_ptPos.x && x <= m_ptPos.x + m_nAreaW) {
			int index = (x - m_ptPos.x) / m_nXspace;
			if (index > m_nCardCount - 1)
				index = m_nCardCount - 1;
			return index;
		} else if (x < m_ptPos.x)
			return 0;
		else
			return m_nCardCount - 1;
	}

	private int getSelectIndex(int x, int y) {
		if (x >= m_ptPos.x && x <= m_ptPos.x + m_nAreaW && y >= m_ptPos.y && y <= m_ptPos.y + m_nAreaH) {
			int index = (x - m_ptPos.x) / m_nXspace;
			if (index > m_nCardCount - 1)
				index = m_nCardCount - 1;
			return index;
		}
		return GDF.INVALID_CHAIR;
	}

	public void setAllCardShoot(boolean shoot) {
		for (int i = 0; i < m_CardData.length; i++)
			m_CardData[i].shoot = shoot;
	}

	public int getShootCard(int data[], boolean shoot) {
		int index = 0;
		for (int i = 0; i < m_nCardCount; i++) {
			if (m_CardData[i].shoot == shoot) {
				if (data != null && index < data.length)
					data[index] = m_CardData[i].data;
				index++;
			}
		}
		return index;
	}

	public void setShootCard(int data, boolean shoot) {
		for (int i = 0; i < m_nCardCount; i++) {
			if (m_CardData[i].data == data) {
				m_CardData[i].shoot = shoot;
				break;
			}
		}
	}

	public void setShootCard(int data[], int count, boolean shoot) {
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < m_nCardCount; j++) {
				if (m_CardData[j].data == data[i]) {
					m_CardData[j].shoot = shoot;
					break;
				}
			}
		}
	}

	public void setSpace(int spacex, int spacey) {
		m_nXspace = spacex;
		m_nYspace = spacey;
	}
}
