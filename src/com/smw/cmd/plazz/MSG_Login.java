package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_Login implements ICmd{
	 
		public int cmd;
		public int VERSION;//版本..
		 //命令  C2S_LOGIN
		public String name;// char name[20]; //用户名
		public String pwd;//    char pwd[20];  //密码
		public int	appgameType;//从哪个游戏app登陆
		public byte	cbDeviceType;//屏幕类型
		public String szMachineID;//char						szMachineID[16]; //机器码
		
		@Override
		public int ReadFromByteArray(byte[] data, int pos) {
			int index = pos;
		 
			return index - pos;
		}
		
		@Override
		public int WriteToByteArray(byte[] data, int pos) {
			int index = pos;

			index += NetEncoding.write4byte(data, cmd, index);
 			index += NetEncoding.write4byte(data, VERSION, index);
			index += NetEncoding.writeString(data, name , index, 20,"GBK");
			index += NetEncoding.writeString(data, pwd , index, 20,"GBK");
			index += NetEncoding.write4byte(data, appgameType, index);
			data[index++]=cbDeviceType;
			index += NetEncoding.writeString(data, szMachineID , index, 16,"GBK");
			
//			index += NetEncoding.writeByteArray(data, name2, index, name_max_len);
			
			return index - pos;
		}
}
