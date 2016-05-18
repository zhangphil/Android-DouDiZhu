package com.smw.net;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

import com.qp.lib.utility.Util;
import com.util.cipher.SCipher;



/**
*
* 购买完整源码联系 q344717871
* 
*/

public class MyTcpClient {

	private ISocketEventListener m_onReadListener;
	
	private boolean m_bisclose;
	private Socket s;
	private InputStream is;
	private BufferedInputStream bis;

	private OutputStream os;
	private BufferedOutputStream bos;
	
	private final static int MAXBUFFSIZE=1024*15;
	
	byte recvbuf[] =new byte[MAXBUFFSIZE];
	byte sendbuf2[] =new byte[MAXBUFFSIZE];
	byte sendbuff[]=new byte[MAXBUFFSIZE];
	
	private boolean m_crypt=true;
	
	public MyTcpClient( ){
		m_bisclose=false;
	}
	
	public Socket getSock(){return s;}
	
	public void SetOnReadListener(ISocketEventListener sm)
	{
		m_onReadListener=sm;
	}
	
	
	public boolean connect(String ip, int port) {
	
		try {
			s = new Socket(ip, port);

			 is = s.getInputStream();
			 bis = new BufferedInputStream(is);
			 os = s.getOutputStream();
			 bos = new BufferedOutputStream(os);
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		work();
		
		//System.out.println("conn ok");
		return true;
	}

	public void close() {
		Util.v("mytcpclient","close");
		try {
			m_bisclose=true;
			bis=null;
			is=null;
			bos=null;
			os=null;
			if(s!=null){
				s.close();
				s = null;
			}
			 
			
			m_onReadListener.onClose();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void work() {

			 
			new Thread(new Runnable() {
				public void run() {
					//Looper.prepare();

					//BufferedReader in;
					try {
						DataInputStream dis=new DataInputStream(s.getInputStream());
						//in = new BufferedReader(new InputStreamReader(s.getInputStream(), "gbk"));
						//PrintWriter out = new PrintWriter(s.getOutputStream());
						while (! m_bisclose  ) 
						{
						//	Log.i("","---------recvpack开始!--------");
							if(!recvpack(dis))
							{
								Util.v("","---------recvpack err!  !--------");
								break;
							}
						//	Log.i("","---------recvpack结束!--------");
							/*
							String str;
							str = in.readLine();
							out.println("Hello!world!! " + str);
							out.flush();
							if (str == null || str.equals("end"))
								break;
							
							System.out.println(str);*/
						}
						 
					} catch (IOException e) {
						Util.v("","net close();");
					}
					close();
				//	Looper.loop();

				}
			}).start();
	
	}
	
	int toUnsigned(short s) {   
	    return s ;// & 0x0FFFF;   
	}  
	/**  
	   * 将 int转为低字节在前，高字节在后的byte数组  
	   * @param n int  
	   * @return byte[]  
	   */   
	public   static   byte [] toLH( int  n) {  
	   byte [] b =  new   byte [ 4 ];  
	   b[0 ] = ( byte ) (n &  0xff );  
	   b[1 ] = ( byte ) (n >>  8  &  0xff );  
	   b[2 ] = ( byte ) (n >>  16  &  0xff );  
	   b[3 ] = ( byte ) (n >>  24  &  0xff );  
	   return  b;  
	}   
	/**  
	   * 将 short转为低字节在前，高字节在后的byte数组  
	   * @param n short  
	   * @return byte[]  
	   */   
	public   static   byte [] toLH( short  n) {  
	   byte [] b =  new   byte [ 2 ];  
	   b[0 ] = ( byte ) (n &  0xff );  
	   b[1 ] = ( byte ) (n >>  8  &  0xff );  
	   
	   System.out.println(" lh:"+ b[0] + "  "+ b[1] );
	   
	   return  b;  
	}

	int sock_read_Ushort(DataInputStream in) throws IOException
	{
		byte bt[]=new byte[4];
		
		in.read(bt,0,1);// throw new IOException("read_Ushort net err") ;
		in.read(bt,1,1); //throw new IOException("read_Ushort net err2") ;
	
		return NetEncoding.readFrom2Byte(bt);
	}
	
	int sock_read_int(DataInputStream in) throws IOException
	{
		byte bt[]=new byte[4];
		
		if(in.read(bt,0,4) !=4 ) throw new IOException("read_int net err") ;
	
		return NetEncoding.readFrom4Byte(bt);
	}
	
	private boolean recvpack(DataInputStream in)
	{
		//Log.i(""," recvpack begin");
		int packlen=0;
		  
		int recvcount=0;
		  
		try {

			packlen=sock_read_Ushort(in);
			if(packlen<=0 || packlen>MAXBUFFSIZE  ){
				Util.v(""," packlen ex!  packlen:" + packlen);
				 return false;
			}
			//packlen 0-65535 
			//packlen = Integer.parseInt((toLH(te).toString()));
		 
			//System.out.println("recv packlen:"+packlen  );
			 
			 while(recvcount<packlen)
			 {
				 int  ret=in.read(recvbuf, recvcount, packlen-recvcount);
				 if(ret>0)
				 {
					 recvcount+=ret;
					// System.out.println("read "+ ret +"字节 ");
				 }else{
					
					 Util.v("","read ret出错 err:"+ret);
					 //-1
					 return false;
				 }
				 
			 }
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
 
			return false;
		}
			
		if(recvcount==packlen)
		{
			//System.out.println("recvpack read完整");
			
			if(m_crypt){
				//recvbuf=XOR(recvbuf);
 
				 SCipher.decryptBuffer(recvbuf, recvbuf.length );
			}
			
			//msgid  int
			//int msgid=  NetEncoding.readFrom4Byte(recvbuf);
			
			//System.arraycopy(recvbuf, 4, recvbuf, 0,  packlen  );
 
			
		//	System.out.println("recv msgid:"+msgid );
			
			if (  m_onReadListener != null )  //SocketReceiveManage.
				m_onReadListener.onRead( recvbuf  ,packlen);
		 }else{
			 
			 //System.out.println("recvpack read不全 "+ recvcount);
			 return false;
		 }
		Util.v(""," recvpack end");
		return true;
	}
	
	public boolean send(ICmd o)
	{
		for(int i=0;i<sendbuf2.length;i++){
			sendbuf2[i]=0;
		}
		for(int i=0;i<sendbuff.length;i++){
			sendbuff[i]=0;
		}
		int len=o.WriteToByteArray(sendbuf2, 0);
		//System.out.println("send len"+len);
		if(m_crypt){
			//sendbuff=XOR(sendbuff );
			SCipher.encryptBuffer(sendbuf2, len );
		}
		
		return send(sendbuf2,len);
	}
	
	private boolean send(byte data[],int datasize)
	{
 
		int writelen=0;
		
		//header    2 byte
		writelen+=NetEncoding.write2byte(sendbuff, datasize, writelen);
		

		//content
		System.arraycopy(data, 0, sendbuff, writelen, datasize);
		writelen+=datasize;
		
//		//跳过2byte header. 
//		if(m_crypt){
//			//sendbuff=XOR(sendbuff,2); 
//		}
		
		try {
			if(bos!=null){
				bos.write(sendbuff, 0, writelen);
				bos.flush(); 
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	} 
	 
	public byte[] XOR(byte data[])
	{
		byte d[]=new byte[data.length];
		for (int i=0;i<data.length;i++)
		{
			//data[i] ^= i;
			d[i]= (byte) (data[i] ^ (i+5)*4);
		}
		
		return d;
	}
	
	public byte[] XOR(byte data[],int beginpos)
	{
		byte d[]=new byte[data.length];
		for (int i=0;i< data.length ;i++)
		{
			if(i>=beginpos){
				//data[i] ^= i;
				d[i]= (byte) (data[i] ^ (i-beginpos)*4);
			}else{
				d[i]= data[i];
			}
			 
		}
		
		return d;
	}
	 
	
}
