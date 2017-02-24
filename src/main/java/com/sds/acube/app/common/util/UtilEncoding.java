package com.sds.acube.app.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;


public class UtilEncoding
{
    private static String encFrom = "";
    private static String encTo = "";


    public static String convertEncoding(String s)
    {
        return convertEncoding(s, encFrom, encTo);
    }

    public static String convertEncoding(String s, String encFrom, String encTo)
    {
        if (s == null) {
            return null;
        }

        String ret = "";
        try
        {
            if (encFrom.equalsIgnoreCase(encTo)) {
                ret = s;
            }
            else {
                ret = new String(s.getBytes(encFrom), encTo);
            }
        }
        catch (Exception e) {        }

        return ret;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // encodeURI 함수 : Java 1.4.1 Win32버전의 java.net.URLEncoder의 것을 차용하여 구현
    ///////////////////////////////////////////////////////////////////////////////////////////
    static BitSet dontNeedEncoding_Component;
    static BitSet dontNeedEncoding_URI;
    static final int caseDiff = ('a' - 'A');
    static String dfltEncName = null;

    static
    {
        dontNeedEncoding_Component = new BitSet(256);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            dontNeedEncoding_Component.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            dontNeedEncoding_Component.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            dontNeedEncoding_Component.set(i);
        }

        dontNeedEncoding_Component.set(' '); /* encoding a space to a + is done.  * in the encode() method */
        dontNeedEncoding_Component.set('-');
        dontNeedEncoding_Component.set('_');
        dontNeedEncoding_Component.set('.');
        dontNeedEncoding_Component.set('*');

        dontNeedEncoding_URI = (BitSet) dontNeedEncoding_Component.clone();
        dontNeedEncoding_URI.set(',');
        dontNeedEncoding_URI.set('/');
        dontNeedEncoding_URI.set('?');
        dontNeedEncoding_URI.set(':');
        dontNeedEncoding_URI.set('@');
        dontNeedEncoding_URI.set('&');
        dontNeedEncoding_URI.set('=');
        dontNeedEncoding_URI.set('+');
        dontNeedEncoding_URI.set('$');
        dontNeedEncoding_URI.set('#');
    }


    public static String encodeURI(String s) throws UnsupportedEncodingException
    {
        return encodeURI(s, encTo);
    }

    public static String encodeURI(String s, String enc) throws UnsupportedEncodingException
    {
        return encode(dontNeedEncoding_URI, s, enc);
    }

    public static String encodeURIComponent(String s) throws UnsupportedEncodingException
    {
        return encodeURIComponent(s, encTo);
    }

    public static String encodeURIComponent(String s, String enc) throws UnsupportedEncodingException
    {
        return encode(dontNeedEncoding_Component, s, enc);
    }


    public static String encode(BitSet dontNeedEncoding, String s, String enc) throws UnsupportedEncodingException
    {
        boolean needToChange = false;
        boolean wroteUnencodedChar = false;
        int maxBytesPerChar = 10; // rather arbitrary limit, but safe for now
        
        if (s == null || s.length() == 0) return "";
        
        StringBuffer out = new StringBuffer(s.length());
        ByteArrayOutputStream buf = new ByteArrayOutputStream(maxBytesPerChar);

        OutputStreamWriter writer = new OutputStreamWriter(buf, enc);

        for (int i = 0; i < s.length(); i++)
        {
            int c = (int) s.charAt(i);
            if (dontNeedEncoding.get(c))
            {
                if (c == ' ') {
                   c = '+';
                    needToChange = true;
                }
                out.append((char)c);
                wroteUnencodedChar = true;
            }
            else
            {
                // convert to external encoding before hex conversion
                try
                {
                    if (wroteUnencodedChar) { // Fix for 4407610
                        writer = new OutputStreamWriter(buf, enc);
                        wroteUnencodedChar = false;
                    }
                    writer.write(c);

                    /*
                    * If this character represents the start of a Unicode
                    * surrogate pair, then pass in two characters. It's not
                    * clear what should be done if a bytes reserved in the
                    * surrogate pairs range occurs outside of a legal
                    * surrogate pair. For now, just treat it as if it were
                    * any other character.
                    */
                    if (c >= 0xD800 && c <= 0xDBFF)
                    {
                        if ( (i+1) < s.length())
                        {
                            int d = (int) s.charAt(i+1);
                            if (d >= 0xDC00 && d <= 0xDFFF)
                            {
                                writer.write(d);
                                i++;
                            }
                        }
                    }
                    writer.flush();
                }
                catch(IOException e) {
                    buf.reset();
                    continue;
                }

                byte[] ba = buf.toByteArray();
                for (int j = 0; j < ba.length; j++)
                {
                    out.append('%');
                    char ch = Character.forDigit((ba[j] >> 4) & 0xF, 16);
                    // converting to use uppercase letter as part of
                    // the hex value if ch is a letter.
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                    ch = Character.forDigit(ba[j] & 0xF, 16);
                    if (Character.isLetter(ch)) {
                        ch -= caseDiff;
                    }
                    out.append(ch);
                }
                buf.reset();
                needToChange = true;
            }
        }

        return (needToChange? out.toString() : s);
    }
}
