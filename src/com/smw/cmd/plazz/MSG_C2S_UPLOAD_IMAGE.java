package com.smw.cmd.plazz;

import com.smw.net.ICmd;
import com.smw.net.NetEncoding;

public class MSG_C2S_UPLOAD_IMAGE   implements ICmd{
	public int cmd;	//	C2S_UPLOAD_IMAGE 
	public String name;// char name[20]; //”√ªß√˚
	public String pwd;// char pwd[20];  //√‹¬Î
	
	public int img_size;
	public byte[] imgdata=new byte[1024*7];//10k

	@Override
	public int ReadFromByteArray(byte[] data, int pos) {
		int index = pos;
		return index - pos;
	}	
	 
 

	@Override
	public int WriteToByteArray(byte[] data, int pos) {
		int index = pos;
	
		index += NetEncoding.write4byte(data, cmd, index);  
		index += NetEncoding.writeString(data, name , index, 20,"GBK");
		index += NetEncoding.writeString(data, pwd , index, 20,"GBK");
		index += NetEncoding.write4byte(data, img_size, index); 
		index += NetEncoding.writeByteArray(data, imgdata, index, 1024*7);
		
		return index - pos;
	}

}
