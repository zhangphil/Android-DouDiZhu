package com.qp.lib.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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

	public static void write2byte(byte list[], int content, int startIndex) {
		byte dataAddArray2[] = new byte[2];
		dataAddArray2 = build2ByteArray(content);
		for (int i = 0; i < 2; i++)
			list[startIndex + i] = dataAddArray2[1 - i];

	}

	public static void write4byte(byte list[], long content, int startIndex) {
		byte dataAddArray4[] = new byte[4];
		dataAddArray4 = build4ByteArray(content);
		for (int i = 0; i < 4; i++)
			list[startIndex + i] = dataAddArray4[3 - i];

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
}
