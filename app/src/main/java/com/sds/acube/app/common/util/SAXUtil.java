package com.sds.acube.app.common.util;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

/** 
 *  Class Name  : SAXUtil.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 3. 20. <br>
 *  수 정  자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 3. 20.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.util.SAXUtil.java
 */

public final class SAXUtil {
    public static final String	ENCODING        	= "UTF-8";
    public static final String	DATA_ENCODING   	= "UTF-8";
    public static final String	ENCODING_EUCKR      = "EUC-KR";
    public static final String	DATA_ENCODING_EUCKR = "EUC-KR";
    public static final int		XML_BUF_SIZE    = 1024 * 15;

    public static final void addCDATACharacters(TransformerHandler h, String str) throws SAXException {
        char[] c = str.replaceAll( "\r\n" , "\n" ).toCharArray( );
        h.startCDATA( );
        h.characters( c , 0 , c.length );
        h.endCDATA( );
    }

    public static final void addCharacters(TransformerHandler h, String str) throws SAXException {
        char[] c = str.replaceAll( "\r\n" , "\n" ).toCharArray( );
        h.characters( c , 0 , c.length );
    }

    public static final void addAttribute(AttributesImpl s, String n, String v) throws SAXException {
        if ( v != null && !"".equals(v) )
        {
            s.addAttribute( "" , "" , n , "CDATA" , v );
        }
    }

    public static final void addAttribute(AttributesImpl a, String n, int v) throws SAXException {
        a.addAttribute( "" , "" , n , "CDATA" , Integer.toString( v ) );
    }
    
    public static final void startEndElement(TransformerHandler h, AttributesImpl a, String v) throws SAXException {
        h.startElement( "" , "" , v , a );
        h.endElement( "" , "" , v );
    }
}
