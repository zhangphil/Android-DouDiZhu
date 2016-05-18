package com.smw.net;

 
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qp.ddz.GameActivity;
import com.qp.ddz.utility.CustomFaceManage;
import com.qp.ddz.R;

/**
*
* 购买完整源码联系 q344717871
* 
*/


public class UserItem {
	public	int uid;//唯一id 
	public String  nickname;//char nickname[32]; //昵称
	public	int money;//金币
	public  int rank;
	public  int bankscore;//银行存款
	public int wincount;//赢
	public int losecount;//
	public int escapecount;//逃跑
	public String maxim;
	
	public void SetFaceCheckSum(int s){facechecksum=s;}
	public int GetFaceCheckSum(){return facechecksum;}
	private int facechecksum; 
	
	public Bitmap GetFaceImage(){
		
		//没头像？用id计算随机头像
		if(facechecksum==0){
			
			Resources r =  GameActivity.getGameActivityInstance().getResources();
			
			int x=uid%6;
			 
			switch (x)
			{
				case 0: return BitmapFactory.decodeResource(r,R.drawable.head_00   );
				case 1: return BitmapFactory.decodeResource(r,R.drawable.head_01   );
				case 2: return BitmapFactory.decodeResource(r,R.drawable.head_02   );
				case 3: return BitmapFactory.decodeResource(r,R.drawable.head_03   );
				case 4: return BitmapFactory.decodeResource(r,R.drawable.head_04   );
				case 5: return BitmapFactory.decodeResource(r,R.drawable.head_05   );
			}
			
			return null;//为0 表示 空头像.
		}
		
		//获得头像
		if (  CustomFaceManage.getInstance() != null) {
			return CustomFaceManage.getInstance().getBitmap( Integer.toHexString(facechecksum), facechecksum);
		}
		
		
		return null;
	}
	
	public int TableID;
	public int ChairID;
	public int UserStatus; // 
	public int GetUserStatus(){return UserStatus;}
	
	public int GetGameID(){return uid;}
	public int GetGender(){return 1;}
	public int GetUserScore(){return money;}
	public String GetNickName(){return nickname;}
	public int GetUserWinCount(){return wincount;}
	public int GetUserLostCount(){return losecount;}
	//public abstract int GetTableID();

	//public abstract int GetChairID();
}
