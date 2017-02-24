/*
 * @(#) TimestampAdapter.java 2010. 6. 11.
 * Copyright (c) 2010 Samsung SDS Co., Ltd. All Rights Reserved.
 */
package com.sds.acube.app.common.util;

import java.util.Date;
import java.sql.Timestamp;

import javax.xml.bind.annotation.adapters.XmlAdapter;


/**
 * 
 * @author  Alex, Eum
 * @version $Revision: 1.1.4.1 $ $Date: 2010/08/31 23:44:25 $
 */
public class TimestampAdapter extends XmlAdapter<Date, Timestamp> {
	
    public Date marshal(Timestamp v) {
        return new Date(v.getTime());
    }
	
    public Timestamp unmarshal(Date v) {
        return new Timestamp(v.getTime());
    }
}
