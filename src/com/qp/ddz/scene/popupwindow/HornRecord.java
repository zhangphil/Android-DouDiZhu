package com.qp.ddz.scene.popupwindow;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.qp.ddz.R;
import com.qp.ddz.scene.popupwindow.adapter.HornRecordAdapter;
import com.qp.ddz.utility.MsgRecordItem;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class HornRecord {

	@SuppressWarnings("unused")
	private static final String		TAG		= "HornRecord";

	PopupWindow						m_PopupWindow;

	static HornRecordAdapter		adapter	= null;

	static ArrayList<MsgRecordItem>	m_HornInfoRecord;

	ListView						m_RecordListView;

	Button							m_btClose;
	public HornRecord() {
		m_HornInfoRecord = new ArrayList<MsgRecordItem>();

	}
	public boolean onShowHornRecord(Activity activity, View view) {
		if (m_PopupWindow == null) {
			View popupview = activity.getLayoutInflater().inflate(R.layout.horn_record_view, null, false);

			m_PopupWindow = new PopupWindow(popupview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

			m_PopupWindow.setBackgroundDrawable(new BitmapDrawable());
			m_PopupWindow.setOutsideTouchable(true);

			m_RecordListView = (ListView) popupview.findViewById(R.id.horn_record_info_list);

			adapter = new HornRecordAdapter(m_HornInfoRecord);
			m_RecordListView.setAdapter(adapter);

			m_btClose = (Button) popupview.findViewById(R.id.horn_record_bt_close);
			m_btClose.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					HornRecord.this.onDestroy();
				}
			});
		}
		m_PopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		return true;
	}

	public static void addHornInfo(int nType, String szinfo) {
		if (m_HornInfoRecord.size() > 50)
			m_HornInfoRecord.clear();
		MsgRecordItem item = new MsgRecordItem();
		item.nType = nType;
		item.szMsg = szinfo;
		m_HornInfoRecord.add(0, item);
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	public static void onClear() {
		m_HornInfoRecord.clear();
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	public void onDestroy() {
		if (m_PopupWindow != null) {
			m_PopupWindow.dismiss();
		}
	}

	public boolean isVisibility() {
		return m_PopupWindow != null && m_PopupWindow.isShowing();
	}

}
