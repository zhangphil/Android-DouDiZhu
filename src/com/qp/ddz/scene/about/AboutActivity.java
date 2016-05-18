package com.qp.ddz.scene.about;

import java.io.IOException;
import java.io.InputStream;

import com.qp.ddz.GameActivity;
import com.qp.ddz.utility.BtBackGround;
import com.qp.lib.main.QPActivity;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class AboutActivity extends QPActivity implements OnClickListener {

	public static String	TAG			= "AboutActivity";

	TextView				m_textView;
	Button					m_btBack;

	static String			szAboutInfo	= "";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		m_textView = (TextView) findViewById(R.id.about_txt_info);
		m_btBack = (Button) findViewById(R.id.about_bt_back);
		m_btBack.setOnTouchListener(new BtBackGround());
		m_btBack.setOnClickListener(this);

		if (szAboutInfo.equals("")) {
			Init();
		}

		m_textView.setText(szAboutInfo);
		m_textView.setMovementMethod(ScrollingMovementMethod.getInstance());
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.about_bt_back) {
			GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
		}
	}

	private void Init() {
		try {
			InputStream is = GameActivity.getInstance().getAssets().open("txt/help.txt");
			if (is != null) {
				int size = is.available();
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();

				szAboutInfo = new String(buffer, "UTF-8");
				if (szAboutInfo != null && !"".equals(szAboutInfo)) {
					szAboutInfo = szAboutInfo.replace("\r\n", "\n");
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
