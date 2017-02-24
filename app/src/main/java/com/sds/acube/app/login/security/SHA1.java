package com.sds.acube.app.login.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;import com.sds.acube.app.common.util.LogWrapper;

/**
 * Reference : http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml
 */
public class SHA1 {
    private static String convertToHex(byte[] data) {
	StringBuffer buf = new StringBuffer();
	for(int i = 0; i < data.length; i++) {/*
	    int halfbyte = (data[i] >>> 4) & 0x0F;
	    int two_halfs = 0;
	    do {
		if((0 <= halfbyte) && (halfbyte <= 9))
		    buf.append((char) ('0' + halfbyte));
		else
		    buf.append((char) ('a' + (halfbyte - 10)));
		halfbyte = data[i] & 0x0F;
	    } while(two_halfs++ < 1);*/		buf.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
	}
	return buf.toString();
    }


    public static String hash(String text) {
	if(text==null) {
	    return "";
	}

	byte[] sha1hash = new byte[40];
	try {
	    MessageDigest md;
	    md = MessageDigest.getInstance("SHA-1");
	    md.update(text.getBytes("iso-8859-1"), 0, text.length());
	    sha1hash = md.digest();
	} catch(NoSuchAlgorithmException e) {
	    LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
	} catch(UnsupportedEncodingException e) {
	    LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
	}
	return convertToHex(sha1hash);
    }

    /*
	public static void main(String[] args) {
		String hash1 = hash("������1");
		String hash2 = hash("������1");
	}
     */
}
