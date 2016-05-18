package com.qp.ddz.scene.popupwindow;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ViewFlipper;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF;
import com.qp.ddz.scene.popupwindow.adapter.ChatInfo;
import com.qp.ddz.scene.popupwindow.adapter.ExpressionAdapter;
import com.qp.ddz.scene.popupwindow.adapter.NormalChatAdapter;
import com.qp.ddz.scene.popupwindow.adapter.RecordChatAdapter;
import com.qp.lib.cmd.CMD;
import com.smw.cmd.game.MSG_C2S_CHAT;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class Chat implements OnClickListener, OnItemClickListener {

	@SuppressWarnings("unused")
	private static final String	TAG			= "Chat";

	PopupWindow					m_PopupWindow;

	ArrayList<ChatInfo>			chatRecord	= new ArrayList<ChatInfo>();

	int							m_nSelectIndex;

	GridView					m_ExpressionView;

	ListView					m_ChatRecordView;

	ListView					m_NormalMsgView;

	Button						m_btRecord;
	//Button						m_btExpression;
	Button						m_btNormal;
	Button						m_btSend;

	EditText					m_EditText;

	ViewFlipper					m_ViewFlipper;

	public static Chat			instance;

	RecordChatAdapter			m_RecordChatAdapter;
	public Chat() {
		instance = this;
	}

	public static Chat getInstance() {
		return instance;
	}

	public boolean onShowChat(Activity activity, View view) {

		if (m_PopupWindow == null) {

			onInitUserChatRecord();

			View popupview = activity.getLayoutInflater().inflate(R.layout.chat, null, false);
			switch (GameActivity.getOption().getDeviceType()) {
				case GDF.DEVICETYPE_WVGA :
					m_PopupWindow = new PopupWindow(popupview, 522, 320, true);
					break;
				case GDF.DEVICETYPE_HVGA :
					m_PopupWindow = new PopupWindow(popupview, 320, 180, true);
					break;
				case GDF.DEVICETYPE_QVGA :
					m_PopupWindow = new PopupWindow(popupview, 200, 120, true);
					break;
			}

			m_PopupWindow.setBackgroundDrawable(new BitmapDrawable());
			m_PopupWindow.setOutsideTouchable(true);

			m_btRecord = (Button) popupview.findViewById(R.id.chat_bt_record);
			//m_btExpression = (Button) popupview.findViewById(R.id.chat_bt_expression);
			m_btNormal = (Button) popupview.findViewById(R.id.chat_bt_normal);
			m_btSend = (Button) popupview.findViewById(R.id.chat_bt_send2);

			m_btRecord.setOnClickListener(this);
			//m_btExpression.setOnClickListener(this);
			m_btNormal.setOnClickListener(this);
			m_btSend.setOnClickListener(this);

			m_EditText = (EditText) popupview.findViewById(R.id.chat_send_txt);

			m_ViewFlipper = (ViewFlipper) popupview.findViewById(R.id.chat_flipper);

			View listview1 = View.inflate(popupview.getContext(), R.layout.chat_record, null);

			m_ViewFlipper.addView(listview1);
			m_NormalMsgView = (ListView) listview1.findViewById(R.id.chat_record_list);
			m_RecordChatAdapter = new RecordChatAdapter(chatRecord);
			m_NormalMsgView.setAdapter(m_RecordChatAdapter);
			m_NormalMsgView.setOnItemClickListener(this);

			View gridview = View.inflate(popupview.getContext(), R.layout.chat_expression, null);

			m_ViewFlipper.addView(gridview);
			m_ExpressionView = (GridView) gridview.findViewById(R.id.chat_grid);
			m_ExpressionView.setNumColumns(6);
			m_ExpressionView.setAdapter(new ExpressionAdapter());
			m_ExpressionView.setSelector(new ColorDrawable(Color.TRANSPARENT));
			m_ExpressionView.setOnItemClickListener(this);

			View listview2 = View.inflate(popupview.getContext(), R.layout.chat_normal, null);

			m_ViewFlipper.addView(listview2);
			m_NormalMsgView = (ListView) listview2.findViewById(R.id.chat_normal_list);
			m_NormalMsgView.setAdapter(new NormalChatAdapter());
			m_NormalMsgView.setOnItemClickListener(this);

		}

		m_ViewFlipper.setDisplayedChild(m_nSelectIndex);
		m_PopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//			case R.id.chat_bt_expression :
//				onSetRecordIndex(1);
//				break;
			case R.id.chat_bt_normal :
				onSetRecordIndex(2);
				break;
			case R.id.chat_bt_record :
				onSetRecordIndex(0);
				break;
			case R.id.chat_bt_send2 :
				onSaveUserChat();
				break;
		}

	}

	public void onDestroy() {
		if (m_PopupWindow != null) {
			m_PopupWindow.dismiss();
		}
	}

	public boolean isVisibility() {
		return m_PopupWindow != null && m_PopupWindow.isShowing();
	}

	@Override
	public void onItemClick(AdapterView<?> adapterview, View view, int position, long id) {
		if (adapterview.getId() == m_NormalMsgView.getId()) {
			onSendNormalMsg(GameActivity.getInstance().getResources().getString(R.string.chatitem_00 + position));
		} else if (adapterview.getId() == m_ExpressionView.getId()) {
			onSendExpression(position);
		}
	}

	private void onSendExpression(int position) {
	//	GameActivity.getKernel().sendUserExpression(0, position);
		onDestroy();
	}

	private void onSendNormalMsg(String szinfo) {
//		String s="";
//		try {
//			s = new String( szinfo.getBytes("GBK"),"GBK" );
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		MSG_C2S_CHAT o=new MSG_C2S_CHAT();
		o.cmd=CMD.C2S_CHAT;
		o.TargetUserID=-1;
		o.ChatColor=1;
		o.ChatString=szinfo;
		GameActivity.getGameActivityInstance().m_gameclient.send(o);
		
		//GameActivity.getKernel().sendUserChatMessage(0, szinfo, 0xff000000);
		onDestroy();
	}

	public void onRecordUserChat(String szNickName, String szMsg) {

		chatRecord.add(new ChatInfo(szNickName, szMsg));

		if (m_RecordChatAdapter != null)
			m_RecordChatAdapter.notifyDataSetChanged();
	}

	public void onInitUserChatRecord() {
		chatRecord.clear();

		chatRecord.add(new ChatInfo("聊天记录", GDF.getDate(System.currentTimeMillis())));
	}
	public void onSaveUserChat() {
		String info = m_EditText.getText().toString();
		if (info != null && !info.equals(""))
			onSendNormalMsg(info);
	}

	public void onSetRecordIndex(int index) {
		if (index < 3 && index > -1) {
			m_nSelectIndex = index;
			m_btRecord.getBackground().setColorFilter(
					m_nSelectIndex == 0 ? new ColorMatrixColorFilter(GDF.BT_SELECTED) : new ColorMatrixColorFilter(GDF.BT_NOT_SELECTED));
			m_btRecord.setBackgroundDrawable(m_btRecord.getBackground());

		//	m_btExpression.getBackground().setColorFilter(
			//		m_nSelectIndex == 1 ? new ColorMatrixColorFilter(GDF.BT_SELECTED) : new ColorMatrixColorFilter(GDF.BT_NOT_SELECTED));
		//	m_btExpression.setBackgroundDrawable(m_btExpression.getBackground());

			m_btNormal.getBackground().setColorFilter(
					m_nSelectIndex == 2 ? new ColorMatrixColorFilter(GDF.BT_SELECTED) : new ColorMatrixColorFilter(GDF.BT_NOT_SELECTED));
			m_btNormal.setBackgroundDrawable(m_btNormal.getBackground());

			m_ViewFlipper.setDisplayedChild(m_nSelectIndex);
		}
	}
}
