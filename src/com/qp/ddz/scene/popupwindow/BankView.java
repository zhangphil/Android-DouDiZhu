package com.qp.ddz.scene.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;
import com.qp.ddz.utility.BtBackGround;
import com.qp.lib.interface_ex.bank.IBank;
import com.qp.lib.utility.Util;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class BankView implements OnClickListener, OnCheckedChangeListener {

	private static final String	TAG	= "BankView";

	PopupWindow					m_PopupWindow;

	IBank						m_BankControl;

	TextView					m_UserScore;
	TextView					m_BankScore;

	TextView					m_FlagTager;
	TextView					m_FlagScore;

	EditText					m_TextTager;
	EditText					m_TextScore;

	RadioButton					m_rbtSaveTake;
	RadioButton					m_rbtTransfer;

	ImageButton					m_btTake;
	ImageButton					m_btSave;
	ImageButton					m_btTransfer;

	ImageButton					m_btFresh;

	public BankView(IBank bankcontrol) {
		m_BankControl = bankcontrol;
	}

	public boolean onShowBankView(Activity activity, View view) {

		if (m_PopupWindow == null) {
			View popupview = activity.getLayoutInflater().inflate(R.layout.bank, null, false);

			m_PopupWindow = new PopupWindow(popupview, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

			m_PopupWindow.setBackgroundDrawable(new BitmapDrawable());
			m_PopupWindow.setOutsideTouchable(true);

			m_UserScore = (TextView) popupview.findViewById(R.id.bank_text_userscore);
			m_UserScore.setText("获取中...");
			m_BankScore = (TextView) popupview.findViewById(R.id.bank_text_bankscore);
			m_BankScore.setText("获取中...");

			m_TextTager = (EditText) popupview.findViewById(R.id.bank_text_tager);
			m_TextScore = (EditText) popupview.findViewById(R.id.bank_text_score);

			RadioGroup mode = (RadioGroup) popupview.findViewById(R.id.bank_mode);
			mode.setOnCheckedChangeListener(this);
			m_rbtSaveTake = (RadioButton) popupview.findViewById(R.id.rbt_savetake);
			m_rbtTransfer = (RadioButton) popupview.findViewById(R.id.rbt_transfer);

			m_btTake = (ImageButton) popupview.findViewById(R.id.bt_bank_take);
			m_btTake.setOnTouchListener(new BtBackGround());
			m_btTake.setOnClickListener(this);

			m_btSave = (ImageButton) popupview.findViewById(R.id.bt_bank_save);
			m_btSave.setOnTouchListener(new BtBackGround());
			m_btSave.setOnClickListener(this);

			m_btTransfer = (ImageButton) popupview.findViewById(R.id.bt_bank_transfer);
			m_btTransfer.setOnTouchListener(new BtBackGround());
			m_btTransfer.setOnClickListener(this);

			m_btFresh = (ImageButton) popupview.findViewById(R.id.bt_bank_fresh);
			m_btFresh.setOnTouchListener(new BtBackGround());
			m_btFresh.setOnClickListener(this);

			m_FlagTager = (TextView) popupview.findViewById(R.id.bank_flag_tager);
			m_FlagScore = (TextView) popupview.findViewById(R.id.bank_flag_score);

		}
//
//		if (GameActivity.getKernelControl().isInService()) {
//			onQueryInfo();
//
//			onModeChanged(R.id.rbt_savetake);
//
//			m_PopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//		} else {
//			Toast.makeText(GameActivity.getInstance(), "获取连接失败，请退出后重新登录!", Toast.LENGTH_SHORT).show();
//		}

		return true;
	}

	@Override
	public void onClick(View v) {

		if (m_BankControl == null)
			return;
		int id = v.getId();
		switch (id) {
			case R.id.bt_bank_fresh :
				onQueryInfo();
				break;
			case R.id.bt_bank_transfer :
				onTransferScore();
				break;
			case R.id.bt_bank_save :
				onSaveScore();
				break;
			case R.id.bt_bank_take :
				onTakeScore();
				break;
			default :
				break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		Util.v(TAG, "onCheckedChanged" + group.getId() + ";" + checkedId);
		switch (group.getId()) {
			case R.id.bank_mode :
				onModeChanged(checkedId);
				break;
			default :
				break;
		}
	}

	private void onModeChanged(int checkedId) {
		switch (checkedId) {
			case R.id.rbt_savetake :
				m_TextTager.setVisibility(View.INVISIBLE);
				m_FlagTager.setVisibility(View.INVISIBLE);

				m_btTransfer.setVisibility(View.INVISIBLE);
				m_btSave.setVisibility(View.VISIBLE);
				m_btTake.setVisibility(View.VISIBLE);

				break;
			case R.id.rbt_transfer :
				m_TextTager.setVisibility(View.VISIBLE);
				m_FlagTager.setVisibility(View.VISIBLE);

				m_btTransfer.setVisibility(View.VISIBLE);
				m_btSave.setVisibility(View.INVISIBLE);
				m_btTake.setVisibility(View.INVISIBLE);
				break;
		}

	}

	private void onQueryInfo() {
		m_btFresh.setClickable(false);
		m_BankControl.PerformQueryInfo();
	}

	private void onTakeScore() {

		if (m_TextScore.getEditableText() == null || m_TextScore.getEditableText().toString().equals("")) {
			Toast.makeText(GameActivity.getInstance(), "金额输入错误!", Toast.LENGTH_SHORT).show();
			return;
		}

		long lTakeScore = Long.parseLong(m_TextScore.getEditableText().toString());

		if (lTakeScore < 1) {
			Toast.makeText(GameActivity.getInstance(), "金额输入错误!", Toast.LENGTH_SHORT).show();
			return;
		}

		if (lTakeScore > m_BankControl.getUserInsure()) {
			Toast.makeText(GameActivity.getInstance(), "保险柜余额不足!", Toast.LENGTH_SHORT).show();
			return;
		}
		m_btFresh.setClickable(false);
		m_BankControl.PerformTakeScore(lTakeScore, "888888");
	}

	private void onSaveScore() {

		if (m_TextScore.getEditableText() == null || m_TextScore.getEditableText().toString().equals("")) {
			Toast.makeText(GameActivity.getInstance(), "金额输入错误!", Toast.LENGTH_SHORT).show();
			return;
		}
		long lSaveScore = Long.parseLong(m_TextScore.getEditableText().toString());

		if (lSaveScore < 1) {
			Toast.makeText(GameActivity.getInstance(), "金额输入错误!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (lSaveScore > m_BankControl.getUserScore()) {
			Toast.makeText(GameActivity.getInstance(), "携带余额不足!", Toast.LENGTH_SHORT).show();
			return;
		}
		m_btFresh.setClickable(false);
		m_BankControl.PerformSaveScore(lSaveScore);

	}

	private void onTransferScore() {

		if (m_TextScore.getEditableText() == null || m_TextScore.getEditableText().toString().equals("")) {
			Toast.makeText(GameActivity.getInstance(), "金额输入错误!", Toast.LENGTH_SHORT).show();
			return;
		}

		int cbByNickName = 0;

		String szNickName = m_TextTager.getEditableText().toString();

		String szInsurePass = "888888";

		long lTransferScore = Long.parseLong(m_TextScore.getEditableText().toString());

		if (szNickName == null || szNickName.equals("")) {
			Toast.makeText(GameActivity.getInstance(), "目标用户输入错误!", Toast.LENGTH_SHORT).show();
			return;
		}

		if (lTransferScore < 1) {
			Toast.makeText(GameActivity.getInstance(), "金额输入错误!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (lTransferScore > m_BankControl.getUserInsure()) {
			Toast.makeText(GameActivity.getInstance(), "保险柜余额不足!", Toast.LENGTH_SHORT).show();
			return;
		}

		m_btFresh.setClickable(false);
		m_BankControl.PerformTransferScore(cbByNickName, szNickName, lTransferScore, szInsurePass);

	}

	public void onDestroy() {
		if (m_PopupWindow != null) {
			m_PopupWindow.dismiss();
		}
	}

	public boolean isVisibility() {
		return m_PopupWindow != null && m_PopupWindow.isShowing();
	}

	public void QueryInsureInfoSucceed() {
		m_btFresh.setClickable(true);
		if (m_UserScore != null) {
			m_UserScore.setText(m_BankControl.getUserScore() + "");
		}
		if (m_BankScore != null) {
			m_BankScore.setText(m_BankControl.getUserInsure() + "");
		}
		String msg = "";
		msg += "取款税率：" + m_BankControl.getRevenueTake() + "\n";
		msg += "转账税率：" + m_BankControl.getRevenueTransfer() + "\n";
		msg += "最低转账：" + m_BankControl.getTransferPrerequisite() + "\n";
		Toast.makeText(GameActivity.getInstance(), msg, Toast.LENGTH_LONG).show();
	}

	public void ActivityInsureSucceed() {
		m_btFresh.setClickable(true);
		if (m_UserScore != null) {
			m_UserScore.setText(m_BankControl.getUserScore() + "");
		}
		if (m_BankScore != null) {
			m_BankScore.setText(m_BankControl.getUserInsure() + "");
		}
	}

	public void ActivityInsureFailure() {
		m_btFresh.setClickable(true);
	}
}
