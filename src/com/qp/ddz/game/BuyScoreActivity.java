package com.qp.ddz.game;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;
import com.qp.ddz.R.layout;
import com.qp.ddz.define.GDF;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

   
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wanpu.pay.PayConnect;
import com.wanpu.pay.PayResultListener;

/*
*
* 购买完整源码联系 q344717871
* 
* */

public class BuyScoreActivity extends Activity {

	 
	// 应用或游戏商自定义的支付订单(每条支付订单数据不可相同)
	String orderId = "";
	// 用户标识
	String userId = "";
	// 支付商品名称
	String goodsName = "10000乐豆";
	// 支付金额
	float price = 0.0f;
	// 支付时间
	String time = "";
	// 支付描述
	String goodsDesc = "";
	// 应用或游戏商服务器端回调接口（无服务器可不填写）
	String notifyUrl = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buy_score);
		
		// 初始化统计器(必须调用)
        PayConnect.getInstance("d275369741d9d5afbca9af87e9a3c143", "WAPS", this);
		
		TextView goodsNameView = (TextView) this.findViewById(R.id.goodsName);
		goodsNameView.setText(goodsName);
		
		final EditText amountView = (EditText) this.findViewById(R.id.amount);
		
		TextView detailsView = (TextView) this.findViewById(R.id.details);
		goodsDesc = "购买" + goodsName;
		detailsView.setText(goodsDesc);
		
		userId = PayConnect.getInstance(this).getDeviceId(this);
		
		Button submitButton = (Button) this.findViewById(R.id.submitBtn);
		
		// 支付SDK版本号
		TextView sdkVersionView = (TextView) this.findViewById(R.id.sdkVersion);
		sdkVersionView.setText("SDK版本：" + PayConnect.LIBRARY_VERSION_NUMBER);
		
		submitButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				try {
//					// 游戏商自定义支付订单号，保证该订单号的唯一性，建议在执行支付操作时才进行该订单号的生成
			        orderId = System.currentTimeMillis() + "";
			        
					String amountStr = amountView.getText().toString();
					if(!"".equals(amountStr)){
						price = Float.valueOf(amountStr);
					}else{
						price = 0.0f;
					}
					
					PayConnect.getInstance(BuyScoreActivity.this).pay(BuyScoreActivity.this, 
																	orderId, 
																	userId, 
																	price, 
																	goodsName, 
																	goodsDesc, 
																	notifyUrl,
																	new MyPayResultListener());
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		Button closeBtn = (Button) this.findViewById(R.id.closeBtn);
		closeBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) { 
				GameActivity.getGameActivityInstance().m_gameclient.close();
				GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
			}
		});
		
	}
	
  
	
	 

	@Override
	protected void onDestroy() {
		//以前版本的finalize()方法作废
		PayConnect.getInstance(this).close();
		super.onDestroy();
	}
	
	/**
	 * 自定义Listener实现PaySuccessListener，用于监听支付成功
	 * @author Administrator
	 *
	 */
	private class MyPayResultListener implements PayResultListener{

		@Override
		public void onPayFinish(Context payViewContext, String orderId, int resultCode, String resultString, int payType,
				float amount, String goodsName) {
			// 可根据resultCode自行判断
			if(resultCode == 0){
				Toast.makeText(getApplicationContext(), resultString + "：" + amount + "元", Toast.LENGTH_LONG).show();
				// 支付成功时关闭当前支付界面
				PayConnect.getInstance(BuyScoreActivity.this).closePayView(payViewContext);	
				
				// TODO 在客户端处理支付成功的操作
				
				//单机 玩家的积分统计----------------------------------
				int Score= (int) (GameActivity.getScore() +10000*amount);
				GameActivity.setScore(Score);// 积分 
				//------------------------------
				
				// 未指定notifyUrl的情况下，交易成功后，必须发送回执
				PayConnect.getInstance(BuyScoreActivity.this).confirm(orderId, payType);
			}else{
				Toast.makeText(getApplicationContext(), resultString, Toast.LENGTH_LONG).show();
			}
		}
	}
	
}
