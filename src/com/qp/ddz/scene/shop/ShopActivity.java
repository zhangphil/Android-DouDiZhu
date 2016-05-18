package com.qp.ddz.scene.shop;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.qp.ddz.GameActivity;
import com.qp.ddz.R;
import com.qp.ddz.define.GDF;
import com.qp.ddz.scene.shop.adapter.Bill;
import com.qp.ddz.scene.shop.adapter.BillAdapter;
import com.qp.ddz.scene.shop.adapter.Product;
import com.qp.ddz.scene.shop.adapter.ProductAdapter;
import com.qp.ddz.utility.BtBackGround;
import com.qp.lib.cmd.CMD;
import com.qp.lib.main.QPActivity;
import com.smw.cmd.plazz.MSG_C2S_GET_BILLS;
import com.smw.cmd.plazz.MSG_C2S_RANK_LIST;
import com.wanpu.pay.PayConnect;
 
 
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class ShopActivity extends QPActivity implements OnItemClickListener, OnClickListener {

	public static String							TAG	= "ShopActivity";
	public static ShopActivity instance;
	
	BillAdapter										m_BillAdapter;
 	ProductAdapter									m_ProductAdapter;
	ImageButton										m_btBack;
	ListView										m_GamePropsListView;
	TextView										m_txtNickName;

	//NdCallbackListener<NdPageList<NdProductInfo>>	ProductInfoCallBack;
	//商品列表
	public List< Product >									m_ProductList;

	//订单
	public List< Bill >									m_BillList;
	
//	NdCallbackListener<List<NdCateInfo>>			CateInfoCallBack;
//	List<NdCateInfo>								CateInfoItem;

	HashMap<String, Bitmap>							imageHashMap;

	Button											m_btSerila;
	Button											m_btProp;

	int												m_nCurShow;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop);
		instance=this;
		
		//GameActivity.onShowDialog("商店", "获取商品列表中");
		
		// 初始化统计器(必须调用)
        PayConnect.getInstance("cd61d4a55076b2127a9edceed864c31b", "WAPS", this);
	 

		m_btBack = (ImageButton) findViewById(R.id.shop_bt_back);
	//	m_btBack.setOnTouchListener(new BtBackGround());
		m_btBack.setOnClickListener(this);
		m_GamePropsListView = (ListView) findViewById(R.id.shop_list_goods);

		m_txtNickName = (TextView) findViewById(R.id.shop_txt_nickname);

		//ProductInfoItem = new ArrayList<NdProductInfo>();
		//CateInfoItem = new ArrayList<NdCateInfo>();
		imageHashMap = new HashMap<String, Bitmap>();

		m_btSerila = (Button) findViewById(R.id.shop_bt_orderlist);
		m_btProp = (Button) findViewById(R.id.shop_bt_goods);

		m_btSerila.setOnClickListener(this);
		m_btSerila.setOnTouchListener(new BtBackGround());

		m_btProp.setOnClickListener(this);
		m_btProp.setOnTouchListener(new BtBackGround());

		m_nCurShow = 0;

		m_BillList=new ArrayList< Bill >();
		m_ProductList=new ArrayList< Product >();  
		
		//已购买的物品
		m_BillAdapter = new BillAdapter(m_BillList); 
		
		m_ProductAdapter = new ProductAdapter(m_ProductList, imageHashMap);
		m_GamePropsListView.setAdapter(m_ProductAdapter);
		
		//GameActivity.dismissDialog();
		//onLoginGamePropsType();
	}
	
	protected void onResume() {
		super.onResume();
		

		//DynamicUI.LoadBG(TAG, this, R.id.Shop_LinearLayout   );
		
		
		MSG_C2S_RANK_LIST o=new MSG_C2S_RANK_LIST();
		o.cmd=CMD.C2S_OPEN_SHOP; 
		GameActivity.getGameActivityInstance().m_loginclient.send(o);
		
	}
	public void onLoadCompelet() {
		//GameActivity.dismissDialog();
		
		m_BillAdapter.notifyDataSetChanged();
		
		m_ProductAdapter.notifyDataSetChanged();
	}
	@Override
	public void onDestroy() {
		instance = null;
		super.onDestroy();
		
		imageHashMap=null;
		m_BillList=null;
		m_ProductList=null;
		m_BillAdapter = null;
		m_ProductAdapter = null;
		
		//以前版本的finalize()方法作废
		PayConnect.getInstance(this).close();
		
		
		//DynamicUI.onDestroy(this,R.id.Shop_LinearLayout);
	}
	/*
	private void onLoginGamePropsType() {
		NdCommplatform nd = NdCommplatform.getInstance();
		if (CateInfoCallBack == null) {
			CateInfoCallBack = new NdCallbackListener<List<NdCateInfo>>() {

				@Override
				public void callback(int responseCode, List<NdCateInfo> list) {
					if (responseCode == NdErrorCode.ND_COM_PLATFORM_SUCCESS && list != null) {

						for (int i = 0; i < list.size(); i++) {
							CateInfoItem.add(list.get(i));
						}
						onLoginGamePropsList(CateInfoItem.get(0).getCateId());

					} else {
						Toast.makeText(GameActivity.getInstance(), "获取商品类别失败", Toast.LENGTH_LONG).show();
					}

				}

			};
		}
		nd.ndGetCategoryList(GameActivity.getInstance(), CateInfoCallBack);
	} 
	protected void onLoginGamePropsList(String szcateId) {
		NdCommplatform nd = NdCommplatform.getInstance();

		m_txtNickName.setText(nd.getLoginNickName());
		NdPagination pagination = new NdPagination();
		pagination.setPageIndex(1);
		pagination.setPageSize(20);

		if (ProductInfoCallBack == null) {
			ProductInfoCallBack = new NdCallbackListener<NdPageList<NdProductInfo>>() {

				@Override
				public void callback(int responseCode, NdPageList<NdProductInfo> list) {
					GameActivity.dismissDialog();
					if (responseCode == NdErrorCode.ND_COM_PLATFORM_SUCCESS && list != null) {

						ProductInfoItem.addAll(list.getList());
						m_GameProAdapter = new GamePropAdapter(ProductInfoItem, imageHashMap);
						m_GamePropsListView.setAdapter(m_GameProAdapter);
						for (int i = 0; i < ProductInfoItem.size(); i++) {
							NdProductInfo item = ProductInfoItem.get(i);
							Util.i(TAG,
									item.getProductId() + "#" + item.getProductName() + "#" + item.getSalePrice() + "#" + item.getOrignPrice() + "#"
											+ item.getDesc());
							onLoginGamePropsImg(item);
						}
					} else {
						Toast.makeText(GameActivity.getInstance(), "获取商品类别失败", Toast.LENGTH_LONG).show();
					}

				}
			};
		}
		int nFeeType = NdFeeInfo.FEE_TYPE_NON_CONSUMABLE | NdFeeInfo.FEE_TYPE_SUBSCRIBE | NdFeeInfo.FEE_TYPE_CONSUMABLE;

		nd.ndGetCommodityList(szcateId, nFeeType, 0, pagination, GameActivity.getInstance(), ProductInfoCallBack);

	}

	protected void onLoginGamePropsImg(NdProductInfo infoitem) {
		NdCommplatform nd = NdCommplatform.getInstance();
		nd.ndGetProductIcon(infoitem.getCateId(), infoitem.getCheckSum(), NdIcon.ICON_DIMENSION_SMALL, GameActivity.getInstance(),
				new NdCallbackListener<NdIcon>() {

					@Override
					public void callback(int responseCode, NdIcon icon) {
						if (responseCode == NdErrorCode.ND_COM_PLATFORM_SUCCESS && icon != null) {
							imageHashMap.put(icon.getCheckSum(), icon.getImg());
							m_GameProAdapter.notifyDataSetChanged();
						}
					}
				});
	}*/
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.shop_bt_back) {
			GameActivity.getInstance().onShowScene(GDF.SCENE_SERVER);
		}
		if (v.getId() == R.id.shop_bt_orderlist && m_nCurShow == 0) {
			m_nCurShow = 1;
			m_GamePropsListView.setAdapter(m_BillAdapter);
			m_BillAdapter.notifyDataSetChanged();
			
			//查询订单
			m_BillList.clear();
			MSG_C2S_GET_BILLS o=new MSG_C2S_GET_BILLS();
			o.cmd=CMD.C2S_GET_BILLS; 
			GameActivity.getGameActivityInstance().m_loginclient.send(o);
			
			
		}
		if (v.getId() == R.id.shop_bt_goods && m_nCurShow == 1) {
			m_nCurShow = 0;
			m_GamePropsListView.setAdapter(m_ProductAdapter);
			m_ProductAdapter.notifyDataSetChanged();
		}

	}

/*	public void QueryUserBalance() {
		NdCommplatform nd = NdCommplatform.getInstance();
		nd.ndGetVirtualBalance(GameActivity.getInstance(), new NdCallbackListener<Double>() {

			@Override
			public void callback(int responseCode, Double balance) {
				// if (responseCode == NdErrorCode.ND_COM_PLATFORM_SUCCESS) {
				// m_txtBalance.setText(balance + "");
				// } else {
				// Toast.makeText(GameActivity.getInstance(), "获取虚拟币余额失败",
				// Toast.LENGTH_LONG).show();
				// }

			}
		});
	}*/

	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {

		switch (event.getKeyCode()) {
	//		case KeyEvent.KEYCODE_VOLUME_DOWN :
	//		case KeyEvent.KEYCODE_VOLUME_UP :
	//			return GameActivity.onVolume(event.getKeyCode());
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
