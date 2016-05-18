package com.util.cipher;

import com.qp.lib.utility.Util;

public class SCipher 
{
	static 
	{
		try
		{
			System.loadLibrary("SCipher");//SCipher");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Util.e("SCipher", "º”‘ÿ ß∞‹");
		}
	}
 
	public static native   int encryptBuffer(byte abyte0[], int i);

	public static native   int decryptBuffer(byte abyte0[], int i );
  
}
