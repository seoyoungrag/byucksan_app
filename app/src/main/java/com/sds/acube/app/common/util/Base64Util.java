package com.sds.acube.app.common.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/** 
 *  Class Name  : Base64Util.java <br>
 *  Description : RFC 2045에서 정의된 BASE64 형식으로 encode/decode 하는 클래스. <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 3. 20. <br>
 *  수 정  자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 3. 20.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.util.Base64Util.java
 */

public class Base64Util {
	private static final char[] S_BASE64CHAR = {
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
		'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
		'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
		'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
		'8', '9', '+', '/'
	};
	private static final char S_BASE64PAD = '=';
	private static final byte[] S_DECODETABLE = new byte[128];
	static {
		for (int i = 0;  i < S_DECODETABLE.length;  i ++)
			S_DECODETABLE[i] = Byte.MAX_VALUE;  // 127
		for (int i = 0;  i < S_BASE64CHAR.length;  i ++) // 0 to 63
			S_DECODETABLE[S_BASE64CHAR[i]] = (byte)i;
	}

	private static int decode0(char[] ibuf, byte[] obuf, int p) {
		int wp = p;
		int outlen = 3;
		if (ibuf[3] == S_BASE64PAD)  outlen = 2;
		if (ibuf[2] == S_BASE64PAD)  outlen = 1;
		int b0 = S_DECODETABLE[ibuf[0]];
		int b1 = S_DECODETABLE[ibuf[1]];
		int b2 = S_DECODETABLE[ibuf[2]];
		int b3 = S_DECODETABLE[ibuf[3]];
		switch (outlen) {
		  case 1:
			obuf[wp] = (byte)(b0 << 2 & 0xfc | b1 >> 4 & 0x3);
			return 1;
		  case 2:
			obuf[wp++] = (byte)(b0 << 2 & 0xfc | b1 >> 4 & 0x3);
			obuf[wp] = (byte)(b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
			return 2;
		  case 3:
			obuf[wp++] = (byte)(b0 << 2 & 0xfc | b1 >> 4 & 0x3);
			obuf[wp++] = (byte)(b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
			obuf[wp] = (byte)(b2 << 6 & 0xc0 | b3 & 0x3f);
			return 3;
		  default:
			return 0;
		}
	}

	/**
	 * BASE64 데이터를 Decode 한다.
	 * 
	 * @original
	 * Decode the base64 data.
	 * <tag>param data The base64 encoded data to be decoded
	 * <tag>param off The offset within the encoded data at which to start decoding
	 * <tag>param len The length of data to decode
	 * <tag>return The decoded data
	 * 
	 * @param data Decode할 base64 데이터.
	 * @param off Decode할 데이터내의 offset.
	 * @param len Decode할 데이터의 길이.
	 * @return Decode된 데이터.
	 */
	public static byte[] decode(char[] data, int off, int len) {
		char[] ibuf = new char[4];
		int ibufcount = 0;
		byte[] obuf = new byte[len/4*3+3];
		int obufcount = 0;
		for (int i = off;  i < off+len;  i ++) {
			char ch = data[i];
			if (ch == S_BASE64PAD
				|| ch < S_DECODETABLE.length && S_DECODETABLE[ch] != Byte.MAX_VALUE) {
				ibuf[ibufcount++] = ch;
				if (ibufcount == ibuf.length) {
					ibufcount = 0;
					obufcount += decode0(ibuf, obuf, obufcount);
				}
			}
		}
		if (obufcount == obuf.length)
			return obuf;
		byte[] ret = new byte[obufcount];
		System.arraycopy(obuf, 0, ret, 0, obufcount);
		return ret;
	}
	
	/**
	 * BASE64 데이터를 Decode 한다.
	 * 
	 * @original
	 * Decode the base64 data.
	 * <tag>param data The base64 encoded data to be decoded
	 * <tag>return The decoded data
	 * 
	 * @param data Decode할 base64 데이터.
	 * @return Decode된 데이터.
	 */
	public static byte[] decode(byte[] data) {
		return decode(new String(data));
	}

	/**
	 * BASE64 데이터를 Decode 한다.
	 * 
	 * @original
	 * Decode the base64 data.
	 * <tag>param data The base64 encoded data to be decoded
	 * <tag>return The decoded data
	 * 
	 * @param data Decode할 base64 데이터.
	 * @return Decode된 데이터.
	 */
	public static byte[] decode(String data) {
		char[] ibuf = new char[4];
		int ibufcount = 0;
		byte[] obuf = new byte[data.length()/4*3+3];
		int obufcount = 0;
		for (int i = 0;  i < data.length();  i ++) {
			char ch = data.charAt(i);
			if (ch == S_BASE64PAD
				|| ch < S_DECODETABLE.length && S_DECODETABLE[ch] != Byte.MAX_VALUE) {
				ibuf[ibufcount++] = ch;
				if (ibufcount == ibuf.length) {
					ibufcount = 0;
					obufcount += decode0(ibuf, obuf, obufcount);
				}
			}
		}
		if (obufcount == obuf.length)
			return obuf;
		byte[] ret = new byte[obufcount];
		System.arraycopy(obuf, 0, ret, 0, obufcount);
		return ret;
	}

	/**
	 * BASE64 데이터를 Decode 한다.
	 * 
	 * @original
	 * Decode the base64 data.
	 * <tag>param data The base64 encoded data to be decoded
	 * <tag>param off The offset within the encoded data at which to start decoding
	 * <tag>param len The length of data to decode
	 * <tag>param ostream The OutputStream to which the decoded data should be
	 *                written
	 * 
	 * @param data Decode할 base64 데이터.
	 * @param off Decode할 데이터내의 offset.
	 * @param len Decode할 데이터의 길이.
	 * @param ostream Decode된 데이터가 출력될 OutputStream.
	 */
	public static void decode(char[] data, int off, int len, OutputStream ostream) throws IOException {
		char[] ibuf = new char[4];
		int ibufcount = 0;
		byte[] obuf = new byte[3];
		for (int i = off;  i < off+len;  i ++) {
			char ch = data[i];
			if (ch == S_BASE64PAD
				|| ch < S_DECODETABLE.length && S_DECODETABLE[ch] != Byte.MAX_VALUE) {
				ibuf[ibufcount++] = ch;
				if (ibufcount == ibuf.length) {
					ibufcount = 0;
					int obufcount = decode0(ibuf, obuf, 0);
					ostream.write(obuf, 0, obufcount);
				}
			}
		}
	}

	/**
	 * BASE64 데이터를 Decode 한다.
	 * 
	 * @original
	 * Decode the base64 data.
	 * <tag>param data The base64 encoded data to be decoded
	 * <tag>param ostream The OutputStream to which the decoded data should be
	 *                written
	 * 
	 * @param data Decode할 base64 데이터.
	 * @param ostream Decode된 데이터가 출력될 OutputStream. 
	 */
	public static void decode(String data, OutputStream ostream) throws IOException {
		char[] ibuf = new char[4];
		int ibufcount = 0;
		byte[] obuf = new byte[3];
		for (int i = 0;  i < data.length();  i ++) {
			char ch = data.charAt(i);
			if (ch == S_BASE64PAD
				|| ch < S_DECODETABLE.length && S_DECODETABLE[ch] != Byte.MAX_VALUE) {
				ibuf[ibufcount++] = ch;
				if (ibufcount == ibuf.length) {
					ibufcount = 0;
					int obufcount = decode0(ibuf, obuf, 0);
					ostream.write(obuf, 0, obufcount);
				}
			}
		}
	}
	
	/**
	 * byte 배열을 BASE64 데이터로 Encode 한다.
	 * 
	 * @original
	 * Returns base64 representation of specified byte array.
	 * <tag>param data The data to be encoded
	 * <tag>return The base64 encoded data
	 * 
	 * @param data Encode할 데이터.
	 * @return Encode된 BASE64 데이터.
	 */
	public static String encode(String data) {
		return encode(data.getBytes(), 0, data.getBytes().length);
	}

	/**
	 * byte 배열을 BASE64 데이터로 Encode 한다.
	 * 
	 * @original
	 * Returns base64 representation of specified byte array.
	 * <tag>param data The data to be encoded
	 * <tag>return The base64 encoded data
	 * 
	 * @param data Encode할 데이터.
	 * @return Encode된 BASE64 데이터.
	 */
	public static String encode(byte[] data) {
		return encode(data, 0, data.length);
	}

	/**
	 * byte 배열을 BASE64 데이터로 Encode 한다.
	 * 
	 * @original
	 * Returns base64 representation of specified byte array.
	 * <tag>param data The data to be encoded
	 * <tag>param off The offset within the data at which to start encoding
	 * <tag>param len The length of the data to encode
	 * <tag>return The base64 encoded data
	 * 
	 * @param data Encode할 데이터.
	 * @param off Encode할 데이터내의 offset.
	 * @param len Encode할 데이터의 길이.
	 * @return Encode된 BASE64 데이터.
	 */
	public static String encode(byte[] data, int off, int len) {
		if (len <= 0)  return "";
		char[] out = new char[len/3*4+4];
		int rindex = off;
		int windex = 0;
		int rest = len;
		while (rest >= 3) {
			int i = ((data[rindex]&0xff)<<16)
				+((data[rindex+1]&0xff)<<8)
				+(data[rindex+2]&0xff);
			out[windex++] = S_BASE64CHAR[i>>18];
			out[windex++] = S_BASE64CHAR[(i>>12)&0x3f];
			out[windex++] = S_BASE64CHAR[(i>>6)&0x3f];
			out[windex++] = S_BASE64CHAR[i&0x3f];
			rindex += 3;
			rest -= 3;
		}
		if (rest == 1) {
			int i = data[rindex]&0xff;
			out[windex++] = S_BASE64CHAR[i>>2];
			out[windex++] = S_BASE64CHAR[(i<<4)&0x3f];
			out[windex++] = S_BASE64PAD;
			out[windex++] = S_BASE64PAD;
		} else if (rest == 2) {
			int i = ((data[rindex]&0xff)<<8)+(data[rindex+1]&0xff);
			out[windex++] = S_BASE64CHAR[i>>10];
			out[windex++] = S_BASE64CHAR[(i>>4)&0x3f];
			out[windex++] = S_BASE64CHAR[(i<<2)&0x3f];
			out[windex++] = S_BASE64PAD;
		}
		return new String(out, 0, windex);
	}

	/**
	 * byte 배열을 BASE64 데이터로 Encode하고 byte stream에 출력한다.
	 * 
	 * @original
	 * Outputs base64 representation of the specified byte array to a byte stream.
	 * <tag>param data The data to be encoded
	 * <tag>param off The offset within the data at which to start encoding
	 * <tag>param len The length of the data to encode
	 * <tag>param ostream The OutputStream to which the encoded data should be
	 *                written
	 * 
	 * @param data Encode할 데이터.
	 * @param off Encode할 데이터내의 offset.
	 * @param len Encode할 데이터의 길이.
	 * @param ostream Encode된 데이터가 출력될 OutputStream.
	 */
	public static void encode(byte[] data, int off, int len, OutputStream ostream) throws IOException {
		if (len <= 0)  return;
		byte[] out = new byte[4];
		int rindex = off;
		int rest = len;
		while (rest >= 3) {
			int i = ((data[rindex]&0xff)<<16)
				+((data[rindex+1]&0xff)<<8)
				+(data[rindex+2]&0xff);
			out[0] = (byte)S_BASE64CHAR[i>>18];
			out[1] = (byte)S_BASE64CHAR[(i>>12)&0x3f];
			out[2] = (byte)S_BASE64CHAR[(i>>6)&0x3f];
			out[3] = (byte)S_BASE64CHAR[i&0x3f];
			ostream.write(out, 0, 4);
			rindex += 3;
			rest -= 3;
		}
		if (rest == 1) {
			int i = data[rindex]&0xff;
			out[0] = (byte)S_BASE64CHAR[i>>2];
			out[1] = (byte)S_BASE64CHAR[(i<<4)&0x3f];
			out[2] = (byte)S_BASE64PAD;
			out[3] = (byte)S_BASE64PAD;
			ostream.write(out, 0, 4);
		} else if (rest == 2) {
			int i = ((data[rindex]&0xff)<<8)+(data[rindex+1]&0xff);
			out[0] = (byte)S_BASE64CHAR[i>>10];
			out[1] = (byte)S_BASE64CHAR[(i>>4)&0x3f];
			out[2] = (byte)S_BASE64CHAR[(i<<2)&0x3f];
			out[3] = (byte)S_BASE64PAD;
			ostream.write(out, 0, 4);
		}
	}

	/**
	 * byte 배열을 BASE64 데이터로 Encode하고 character stream에 출력한다.
	 * 
	 * @original
	 * Outputs base64 representation of the specified byte array to a character stream.
	 * <tag>param data The data to be encoded
	 * <tag>param off The offset within the data at which to start encoding
	 * <tag>param len The length of the data to encode
	 * <tag>param writer The Writer to which the encoded data should be
	 *               written
	 * 
	 * @param data Encode할 데이터.
	 * @param off Encode할 데이터의 offset.
	 * @param len Encode할 데이터내의 길이.
	 * @param writer Encode된 데이터가 출력될 Writer.
	 */
	public static void encode(byte[] data, int off, int len, Writer writer) throws IOException {
		if (len <= 0)  return;
		char[] out = new char[4];
		int rindex = off;
		int rest = len;
		int output = 0;
		while (rest >= 3) {
			int i = ((data[rindex]&0xff)<<16)
				+((data[rindex+1]&0xff)<<8)
				+(data[rindex+2]&0xff);
			out[0] = S_BASE64CHAR[i>>18];
			out[1] = S_BASE64CHAR[(i>>12)&0x3f];
			out[2] = S_BASE64CHAR[(i>>6)&0x3f];
			out[3] = S_BASE64CHAR[i&0x3f];
			writer.write(out, 0, 4);
			rindex += 3;
			rest -= 3;
			output += 4;
			if (output % 76 == 0)
				writer.write("\n");
		}
		if (rest == 1) {
			int i = data[rindex]&0xff;
			out[0] = S_BASE64CHAR[i>>2];
			out[1] = S_BASE64CHAR[(i<<4)&0x3f];
			out[2] = S_BASE64PAD;
			out[3] = S_BASE64PAD;
			writer.write(out, 0, 4);
		} else if (rest == 2) {
			int i = ((data[rindex]&0xff)<<8)+(data[rindex+1]&0xff);
			out[0] = S_BASE64CHAR[i>>10];
			out[1] = S_BASE64CHAR[(i>>4)&0x3f];
			out[2] = S_BASE64CHAR[(i<<2)&0x3f];
			out[3] = S_BASE64PAD;
			writer.write(out, 0, 4);
		}
	}
}
