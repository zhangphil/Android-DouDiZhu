package com.qp.ddz.scene.shop.adapter;

public class Bill {
 
	String getProductName(){return goodsname;}
 
	
	public int billID; //订单编号
	public String goodsname;//char goodsname[32];//商品名称
	public String  buytime;//char buytime[32];//下单时间
	public int pstate;//订单状态 1未支付 2交易成功 3交易取消 
	
}
