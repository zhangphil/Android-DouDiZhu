package com.qp.ddz.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.qp.ddz.GameActivity;
import com.qp.ddz.game.GameClientActivity;
import com.qp.lib.utility.Util;
/*
*
* 购买完整源码联系 q344717871
* 
* */

public class BatteryLevelRcvr extends BroadcastReceiver {

	private static final String	TAG				= "BroadcastReceiver";

	private IntentFilter		batteryLevelFilter;

	public static int			BatteryLevel	= 0;
	public static int			BatteryPer		= 100;

	public void onStart() {

		batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		GameActivity.getInstance().registerReceiver(this, batteryLevelFilter);
	}

	public void onDestroy() {

		GameActivity.getInstance().unregisterReceiver(this);
	}
	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();

		if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {

			int level = intent.getIntExtra("level", 0);

			int scale = intent.getIntExtra("scale", 100);

			BatteryPer = level * 100 / scale;
			Util.d(TAG, "电池:Level:" + level + "#Scale" + scale);
			if (BatteryPer <= 5)
				BatteryLevel = 0;
			else if (BatteryPer <= 10)
				BatteryLevel = 1;
			else if (BatteryPer <= 20)
				BatteryLevel = 2;
			else if (BatteryPer <= 40)
				BatteryLevel = 3;
			else if (BatteryPer <= 60)
				BatteryLevel = 4;
			else if (BatteryPer <= 80)
				BatteryLevel = 5;
			else
				BatteryLevel = 6;

			if (GameClientActivity.getInstance() != null) {
				GameClientActivity.getInstance().updateBattery();
			}
		}

	}

}
