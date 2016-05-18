package com.smw.net;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
/**
*
* 购买完整源码联系 q344717871
* 
*/

public class NetEncoding {

	public NetEncoding() {
	}

	public static final String changeToMD5(String str) throws NoSuchAlgorithmException {
		if (str != null && !str.equals(""))
			return toMd5(str.getBytes());
		else
			return "";
	}

	private static String toMd5(byte bytes[]) throws NoSuchAlgorithmException {
		MessageDigest algorithm = MessageDigest.getInstance("MD5");
		algorithm.reset();
		algorithm.update(bytes);
		return toHexString(algorithm.digest(), "0");
	}

	private static String toHexString(byte bytes[], String separator) {
		StringBuffer ret = new StringBuffer("");
		byte abyte0[];
		int j = (abyte0 = bytes).length;
		for (int i = 0; i < j; i++) {
			byte buf = abyte0[i];
			String str = Integer.toHexString(buf);
			if (str.length() < 2)
				ret.append((new StringBuilder(String.valueOf(separator))).append(str).toString());
			else
				ret.append(str.substring(str.length() - 2));
		}

		return new String(ret);
	}

	public static int read2Byte(byte list[], int startIndex) {
		byte dataAddArray2[] = new byte[2];
		for (int i = 0; i < 2; i++)
			dataAddArray2[i] = list[startIndex + i];

		return readFrom2Byte(dataAddArray2);
	}

	public static int read4Byte(byte list[], int startIndex) {
		byte dataAddArray4[] = new byte[4];
		for (int i = 0; i < 4; i++)
			dataAddArray4[i] = list[startIndex + i];

		return readFrom4Byte(dataAddArray4);
	}

	public static long read8Byte(byte bb[], int index) {
		return (long) (bb[index + 7] & 0xff) << 56 | (long) (bb[index + 6] & 0xff) << 48 | (long) (bb[index + 5] & 0xff) << 40
				| (long) (bb[index + 4] & 0xff) << 32 | (long) (bb[index + 3] & 0xff) << 24 | (long) (bb[index + 2] & 0xff) << 16
				| (long) (bb[index + 1] & 0xff) << 8 | (long) (bb[index + 0] & 0xff) << 0;
	}

	public static int write2byte(byte list[], int content, int startIndex) {
		byte dataAddArray2[] = new byte[2];
		dataAddArray2 = build2ByteArray(content);
		for (int i = 0; i < 2; i++)
			list[startIndex + i] = dataAddArray2[1 - i];
		return 2;
	}

	public static int write4byte(byte list[], long content, int startIndex) {
		byte dataAddArray4[] = new byte[4];
		dataAddArray4 = build4ByteArray(content);
		for (int i = 0; i < 4; i++)
			list[startIndex + i] = dataAddArray4[3 - i];
		return 4;
	}

	public static void write8byte(byte list[], long content, int startIndex) {
		byte dataAddArray8[] = new byte[8];
		dataAddArray8 = build8ByteArray(content);
		for (int i = 0; i < 8; i++)
			list[startIndex + i] = dataAddArray8[7 - i];

	}

	public static int readFrom2Byte(byte getData[]) {
		return (getData[1] & 0xff) << 8 | getData[0] & 0xff;
	}

	public static int readFrom4Byte(byte getData[]) {
		return (getData[3] & 0xff) << 24 | (getData[2] & 0xff) << 16 | (getData[1] & 0xff) << 8 | getData[0] & 0xff;
	}

	public static long read8Byte(byte b[]) {
		long temp = 0L;
		long res = 0L;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = b[i] & 0xff;
			res |= temp;
		}

		return res;
	}

	public static byte[] build2ByteArray(int data) {
		byte dataOne[] = new byte[2];
		dataOne[0] = (byte) (data >> 8 & 0xff);
		dataOne[1] = (byte) (data & 0xff);
		return dataOne;
	}

	public static byte[] build4ByteArray(long data) {
		byte dataOne[] = new byte[4];
		dataOne[0] = (byte) (int) (data >> 24 & 255L);
		dataOne[1] = (byte) (int) (data >> 16 & 255L);
		dataOne[2] = (byte) (int) (data >> 8 & 255L);
		dataOne[3] = (byte) (int) (data & 255L);
		return dataOne;
	}

	public static byte[] build8ByteArray(long data) {
		byte dataOne[] = new byte[8];
		dataOne[0] = (byte) (int) (data >> 56 & 255L);
		dataOne[1] = (byte) (int) (data >> 48 & 255L);
		dataOne[2] = (byte) (int) (data >> 40 & 255L);
		dataOne[3] = (byte) (int) (data >> 32 & 255L);
		dataOne[4] = (byte) (int) (data >> 24 & 255L);
		dataOne[5] = (byte) (int) (data >> 16 & 255L);
		dataOne[6] = (byte) (int) (data >> 8 & 255L);
		dataOne[7] = (byte) (int) (data & 255L);
		return dataOne;
	}

	public static String byteToHexStr(byte source[]) {
		StringBuffer ret = new StringBuffer("");
		byte abyte0[];
		int j = (abyte0 = source).length;
		for (int i = 0; i < j; i++) {
			byte buf = abyte0[i];
			String str = Integer.toHexString(buf);
			if (str.length() < 2)
				ret.append((new StringBuilder("0")).append(str).toString());
			else
				ret.append(str.substring(str.length() - 2));
		}

		return new String(ret);
	}

	public static byte[] stringToWcharUnicodeBytes(String value, int arraySize) {
		char valueChars[] = value.toCharArray();
		byte data[] = new byte[valueChars.length * 2];
		Arrays.fill(data, (byte) -2);
		for (int i = 0; i < valueChars.length && i < arraySize; i++) {
			data[i * 2] = (byte) valueChars[i];
			data[i * 2 + 1] = (byte) (valueChars[i] >> 8);
		}

		return data;
	}

	public static void stringToWcharUnicodeBytes(String value, byte data[], int pos) {
		char valueChars[] = value.toCharArray();
		Arrays.fill(data, pos, pos + valueChars.length * 2, (byte) -2);
		for (int i = 0; i < valueChars.length; i++) {
			data[i * 2 + pos] = (byte) valueChars[i];
			data[i * 2 + 1 + pos] = (byte) (valueChars[i] >> 8);
		}
	}

	public static String wcharUnicodeBytesToString(byte data[], int pos, int lenght) {
		StringBuilder builder = new StringBuilder();
		if (lenght == 0)
			lenght = data.length - pos;
		if (lenght % 2 != 0)
			lenght--;
		for (int i = pos; i < pos + lenght; i += 2) {
			if (data[i] == -2 || data[i] == 0 && data[i + 1] == 0)
				break;
			int temp[] = new int[2];
			temp[0] = data[i] & 0xff;
			temp[1] = data[i + 1] & 0xff;
			builder.append(wcharUnicodeBytesToChar(temp));
		}

		return builder.toString();
	}

	private static char wcharUnicodeBytesToChar(int data[]) {
		char c = (char) ((data[1] << 8) + data[0]);
		return c;
	}

	public static byte[] doubleToByte(double d) {
		byte b[] = new byte[8];
		long l = Double.doubleToLongBits(d);
		for (int i = 0; i < b.length; i++) {
			b[i] = (new Long(l)).byteValue();
			l >>= 8;
		}

		return b;
	}

	public static void doubleToByte(double d, byte data[], int pos) {
		long l = Double.doubleToLongBits(d);
		for (int i = 0; i < 8; i++) {
			data[i + pos] = (new Long(l)).byteValue();
			l >>= 8;
		}

	}

	public static double byteToDouble(byte b[], int pos) {
		long l = b[pos];
		l &= 255L;
		l |= (long) b[pos + 1] << 8;
		l &= 65535L;
		l |= (long) b[pos + 2] << 16;
		l &= 0xffffffL;
		l |= (long) b[pos + 3] << 24;
		l &= 0xffffffffL;
		l |= (long) b[pos + 4] << 32;
		l &= 0xffffffffffL;
		l |= (long) b[pos + 5] << 40;
		l &= 0xffffffffffffL;
		l |= (long) b[pos + 6] << 48;
		l &= 0xffffffffffffffL;
		l |= (long) b[pos + 7] << 56;
		return Double.longBitsToDouble(l);
	}
 
	public static byte[] byte2byte(byte data[],int len)
	{
		byte bytearray[] = new byte[len];
		System.arraycopy(data, 0, bytearray, 0, len );
		return bytearray;
	}
	
	/*很麻烦 考虑string长度可变.byte固定.
	public static byte[] StringToByteArray(String str)
	{
		byte bytearray[] = new byte[str.length()];
		int i=0;
		for(   i=0;i< stringbyte.length ;i++){
			if( stringbyte[i] == 0 ){
				//bytearray[i-1]=0;
				break;//到达结尾了
			}
			bytearray[i]=stringbyte[i];
		}
	}*/
	
	public static String ByteArrayToString(byte stringbyte[])
	{
		String ret="";
		
		byte bytearray[] = new byte[stringbyte.length];
		
		//找到结尾地址
		int i=0;
		for(   i=0;i< stringbyte.length ;i++){
			if( stringbyte[i] == 0 ){
				//bytearray[i-1]=0;
				break;//到达结尾了
			}
			bytearray[i]=stringbyte[i];
		}
		
		//java byte 转string 有 bug. 必须byte里字符满,即new byte[]空间 要对应字符串长度.
		bytearray=byte2byte(bytearray,i);
		
		
		try {
			ret = new String(bytearray,"GBK");
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public  static String byteToString(byte data[],int pos,int maxStringSize)
	{
		String name="";
		
			byte bytearray[] = new byte[maxStringSize];
	    
		 	//StringBuilder builder = new StringBuilder();
			int i=0;
			for(   i=0;i<maxStringSize;i++){
				if( data[pos+i] == 0 ){
					//bytearray[i-1]=0;
					break;//到达结尾了
				}
				bytearray[i]=data[pos+i];
				
			//	builder.append( data[pos+i]);
				
			//	System.out.print(" char["+ data[pos+i]+"]");
				
			}
			
			//java byte 转string 有 bug. 必须byte里字符满,即new byte[]空间 要对应字符串长度.
			bytearray=byte2byte(bytearray,i);
			
			//System.out.println(" builder："+builder);
			
			try {
				name = new String(bytearray,"GBK");
			
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return name;
	}
	
	public static int writeString(byte list[], String content, int startIndex,int maxStringSize,String charset) {
	 
		try {
			byte strarray[]=content.getBytes(charset);
			//strarray[strarray.length-1]=0;
			
			//System.out.print(" writeString strarray:"+ strarray.length );
		/*	
			byte btarray[]=new byte[maxStringSize];
			for(int i=0;i<strarray.length;i++){
				btarray[i]=strarray[i];
			}*/ 
			
			//copy 10 byte
			if( strarray.length > maxStringSize){
				System.out.println("writeString err out of range!!!");
				strarray[maxStringSize-1]=0;
				System.arraycopy(strarray, 0, list, startIndex, maxStringSize);
			}else{
				System.arraycopy(strarray, 0, list, startIndex, strarray.length);
			}
			 
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return maxStringSize;
	}
	
	public static int writeByteArray(byte list[], byte content[], int startIndex,int maxByteSize){
	//	for (int i = 0; i < content.length && i<maxByteSize  ; i++)
	//		list[startIndex+i] = content[i];
		//法2
		System.arraycopy(content, 0, list, startIndex, maxByteSize);
		
		return maxByteSize;
	}
	
	public static byte[] readByteArray(byte list[], int startIndex,int bytesize) {
		byte data[] = new byte[bytesize];
		for (int i = 0; i < bytesize; i++)
			data[i] = list[startIndex + i];

		return data;
	}
}
