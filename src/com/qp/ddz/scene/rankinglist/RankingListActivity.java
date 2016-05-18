package com.qp.ddz.scene.rankinglist;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.qp.ddz.GameActivity;
import com.qp.ddz.utility.BtBackGround;
import com.qp.ddz.utility.RankingItem;
import com.qp.lib.cmd.CMD;
import com.qp.lib.main.QPActivity;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF;
import com.qp.ddz.scene.rankinglist.adapter.RankingListAdapter;
import com.smw.cmd.plazz.MSG_C2S_RANK_LIST;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class RankingListActivity extends QPActivity implements OnClickListener {
	public static String	TAG	= "RankingListActivity";

	public class RankingAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
		/*	HttpClient client = new DefaultHttpClient();
			StringBuilder builder = new StringBuilder();
			JSONArray jsonArray = null;
			HttpGet get = new HttpGet("http://www.hj663.com/WS/PhoneRank.ashx?action=getscorerank&pageindex=" + pageindex + "&pagesize=" + pagesize);
			try {
				HttpResponse response = client.execute(get);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				for (String s = reader.readLine(); s != null; s = reader.readLine()) {
					builder.append(s);
					Util.d(TAG, s);
				}
				jsonArray = new JSONArray(builder.toString());
				for (int i = 0; i < jsonArray.length(); ++i) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					RankingItem item = new RankingItem();
					item.szName = jsonObject.getString("NickName");
					item.szScore = jsonObject.getString("Score");
					m_RankItemList.add(item);
					Util.d(TAG, item.szName + "#" + item.szScore);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "fail";
			}
*/
			return "succeed";
		}

		@Override
		protected void onPostExecute(String s) {
		//	if (s != null && s.equals("succeed")) {
		//		onLoadCompelet();
		//	}
		}

	}

	public static RankingListActivity instance;
	public int						pagesize		= 10;
	public int						pageindex		= 1;
	public  ArrayList<RankingItem>	m_RankItemList	= new ArrayList<RankingItem>();

	public RankingListAdapter		m_RankingListAdapter;

	ImageButton						m_btBack;
	ListView						m_RankingListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rankingactivity);
			
		instance=this;
		
		m_btBack = (ImageButton) findViewById(R.id.ranking_bt_back);
		m_RankingListView = (ListView) findViewById(R.id.ranking_list);

		m_btBack.setOnClickListener(this);
		m_btBack.setOnTouchListener(new BtBackGround());

	}

	@Override
	public void onDestroy() {
		instance = null;
		super.onDestroy();
		
	//	DynamicUI.onDestroy(this,R.id.Ranking_LinearLayout);
	}
//	//方法：从resource中的raw文件夹中获取文件并读取数据
//    public byte [] getFromRaw(int resID){
//    	 
//        try{
//        	InputStream in = getResources().openRawResource(resID);	//从Resources中raw中的文件获取输入流
//        	int length = in.available();									//获取文件的字节数
//        	byte [] buffer = new byte[length];								//创建byte数组
//        	in.read(buffer);												//将文件中的数据读取到byte数组中
//        	 
//        	in.close();														//关闭输入流
//        	return buffer;
//        }
//        catch(Exception e){
//        	e.printStackTrace();											//捕获异常并打印
//        }
//    	return null;
//    }

	protected void onResume() {
		super.onResume();
		
		//DynamicUI.LoadBG(TAG, this, R.id.Ranking_LinearLayout   );
		
		if (m_RankingListAdapter == null) {
			GameActivity.onShowDialog("排行榜", "获取数据中...", true, true, new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
				}
			});

			//RankingAsyncTask rank = new RankingAsyncTask();
			//rank.execute("");
			
		//	GameActivity.getGameActivityInstance().m_loginclient.start(); 
			MSG_C2S_RANK_LIST o=new MSG_C2S_RANK_LIST();
			o.cmd=CMD.C2S_RANK_LIST; 
			GameActivity.getGameActivityInstance().m_loginclient.send(o);
			
//			
//			MSG_C2S_UPLOAD_IMAGE i=new MSG_C2S_UPLOAD_IMAGE();
//			i.cmd=CMD.C2S_UPLOAD_IMAGE;
//			i.name=GameActivity.getGameActivityInstance().m_user;
//			i.pwd=GameActivity.getGameActivityInstance().m_pwd;
			
//			byte bt[]=getFromRaw(R.raw.qq);
//			i.img_size=bt.length;
//			System.arraycopy(bt, 0, i.imgdata, 0, bt.length);
			//GameActivity.getGameActivityInstance().m_loginclient.send(i);
			
			
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.ranking_bt_back) {
			GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
		}

	}

	public void onLoadCompelet() {
		GameActivity.dismissDialog();
		m_RankingListAdapter = new RankingListAdapter(m_RankItemList);
		m_RankingListView.setAdapter(m_RankingListAdapter);

		m_RankingListAdapter.notifyDataSetChanged();
	}

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {

		switch (event.getKeyCode()) {
		//	case KeyEvent.KEYCODE_VOLUME_DOWN :
		//	case KeyEvent.KEYCODE_VOLUME_UP :
		 //		return GameActivity.onVolume(event.getKeyCode());
			case KeyEvent.KEYCODE_MENU : {
				return ((GameActivity) GameActivity.getInstance()).onShowOptionMenu(null);
			}
			case KeyEvent.KEYCODE_BACK : {
				GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
				return true;
			}
		}
		return super.onKeyDown(keycode, event);
	}
}
