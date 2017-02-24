package com.sds.acube.app.exchange.convert;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.sds.acube.app.bind.BindConstants;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.exchange.service.IConvertService;
import com.sds.acube.app.exchange.service.IDocumentService;
import com.sds.acube.app.exchange.vo.ConvertVO;


/**
 * Class Name : ConvertThread.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 7. 8. <br> 수 정 자 : yucea <br> 수정내용 : <br>
 * @author  yucea
 * @since  2011. 7. 8.
 * @version  1.0
 * @see  com.sds.acube.app.exchange.convert.ConvertThread.java
 */

public class ConvertThread implements Runnable, BindConstants {

    private String compId;
    private String startDay;
    private String endDay;

    /**
	 */
    private IDocumentService documentService;
    /**
	 */
    private IConvertService convertService;

    /**
	 */
    private ConvertLog log;


    public ConvertThread(IDocumentService documentService, IConvertService convertService, String compId, String startDay, String endDay) {
	this.documentService = documentService;
	this.convertService = convertService;
	this.compId = compId;
	this.startDay = startDay;
	this.endDay = endDay;

	log = new ConvertLog();
    }


    public void run() {
	try {
	    Map<String, String> param = new HashMap<String, String>();
	    param.put(COMP_ID, compId);
	    param.put(START_DAY, startDay);
	    param.put(END_DAY, endDay);

	    List<ConvertVO> rows = convertService.getTargetList(param);

	    int totalCount = rows.size();

	    StringBuilder ss = new StringBuilder();
	    ss.append("##########################################################\n");
	    ss.append("Rhodes Conversion Start\n");
	    ss.append("CompID       : " + compId + "\n");
	    ss.append("StartDay     : " + startDay + "\n");
	    ss.append("EndDay       : " + endDay + "\n");
	    ss.append("Total count  : " + CommonUtil.currency(totalCount) + "\n");
	    ss.append("##########################################################\n");

	    log.log(ss.toString());

	    long start = System.currentTimeMillis();

	    int count = 0;

	    for (ConvertVO vo : rows) {
		try {
		    if (documentService.convert(vo, false)) {
			++count;

			if (count % 100 == 0) {
			    StringBuilder se = new StringBuilder();
			    se.append("##########################################################\n");
			    se.append("CompID       : " + compId + "\n");
			    se.append("StartDay     : " + startDay + "\n");
			    se.append("EndDay       : " + endDay + "\n");
			    se.append("ExecDate     : " + vo.getExecDate() + "\n");
			    se.append("RunCount     : " + CommonUtil.currency(count) + "\n");
			    se.append("RunTime      : " + CommonUtil.currency(System.currentTimeMillis() - start) + "(sec)\n");
			    se.append("##########################################################\n");

			    log.log(se.toString());
			}
		    }
		} catch (Exception e) {
	    	    log.log(e.getMessage());

		    String msg = CommonUtil.getExceptionMessage(e);

		    StringBuilder se = new StringBuilder();
		    se.append("##########################################################\n");
		    se.append("ERROR MESSAGE\n");
		    se.append("CompID       : " + compId + "\n");
		    se.append("DocID        : " + vo.getDocId() + "\n");
		    se.append("UsingType    : " + vo.getUsingType() + "\n");
		    se.append("ExecDate     : " + vo.getExecDate() + "\n");
		    se.append("Message      : " + msg + "\n");
		    se.append("RunTime      : " + CommonUtil.currency(System.currentTimeMillis() - start) + "(sec)\n");
		    se.append("##########################################################\n");

		    log.log(se.toString());

		    try {
			convertService.inputError(vo, msg);
		    } catch (Exception e1) {
			log.log(e.getMessage());
		    }
		}
	    }

	    long end = System.currentTimeMillis();

	    StringBuilder se = new StringBuilder();
	    se.append("##########################################################\n");
	    se.append("Rhodes Conversion End\n");
	    se.append("CompID       : " + compId + "\n");
	    se.append("StartDay     : " + startDay + "\n");
	    se.append("EndDay       : " + endDay + "\n");
	    se.append("Execute Time : " + CommonUtil.currency(end - start) + "(sec)\n");
	    se.append("Total count  : " + CommonUtil.currency(totalCount) + "\n");
	    se.append("Success count: " + CommonUtil.currency(count) + "\n");
	    se.append("Fail count   : " + CommonUtil.currency(totalCount - count) + "\n");
	    se.append("##########################################################\n");

	    log.log(se.toString());
	} catch (Exception e) {
	    log.log(e.getMessage());
	}
    }


    private int compareDate(String d1, String d2) throws ParseException {
	d1 = d1.replaceAll("/", "").replaceAll("-", "").replaceAll("\\.", "");
	d2 = d2.replaceAll("/", "").replaceAll("-", "").replaceAll("\\.", "");

	Calendar c1 = Calendar.getInstance(new Locale("kr"));
	c1.set(Calendar.YEAR, Integer.parseInt(d1.substring(0, 4)));
	c1.set(Calendar.MONTH, Integer.parseInt(d1.substring(4, 6)));
	c1.set(Calendar.DATE, Integer.parseInt(d1.substring(6, 8)));

	Calendar c2 = Calendar.getInstance();
	c2.set(Calendar.YEAR, Integer.parseInt(d2.substring(0, 4)));
	c2.set(Calendar.MONTH, Integer.parseInt(d2.substring(4, 6)));
	c2.set(Calendar.DATE, Integer.parseInt(d2.substring(6, 8)));

	return c1.compareTo(c2);
    }
}
