/**
 * 
 */
package com.sds.acube.app.bind.schedule;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;

import com.sds.acube.app.bind.BindConstants;
import com.sds.acube.app.bind.service.BindBatchService;
import com.sds.acube.app.bind.vo.BatchVO;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.PeriodVO;


/**
 * Class Name : BindScheduler.java <br>
 * Description : 단위업무 일괄생성 스케줄러 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 6. 7. <br>
 * 수 정 자 : yucea <br>
 * 수정내용 : <br>
 * 
 * @author yucea
 * @since 2011. 6. 7.
 * @version 1.0
 * @see com.sds.acube.app.bind.schedule.BindScheduler.java
 */

public class BindScheduler implements BindConstants {

    /**
     * <pre> 
     *  단위업무 일괄생성
     * </pre>
     * @param bindBatchService
     * @param optionAPIService
     * @param config
     * @throws Exception
     * @see  
     *
     */
    public static void batch(BindBatchService bindBatchService, IEnvOptionAPIService optionAPIService) throws Exception {
	String compIdList[] = AppConfig.getArray("compid", null, "companyinfo");
	int compCount = 0;
	if (compIdList != null) {
	    compCount = compIdList.length;
	}

	Calendar cal = Calendar.getInstance();

	for (int nLoop = 0; nLoop < compCount; ++nLoop) {
	    String compId = compIdList[nLoop];

	    PeriodVO periodVO = optionAPIService.getCurrentPeriod(compId);
	    String endDay = periodVO.getEndDate(); // 2011-11-20

	    Calendar cal2 = Calendar.getInstance();
	    cal2.set(Calendar.YEAR, Integer.parseInt(endDay.substring(0, 4)));
	    cal2.set(Calendar.MONTH, Integer.parseInt(endDay.substring(5, 7)) - 1);
	    cal2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(endDay.substring(8, 10)));
	    // 7일 전 부터 확인
	    cal2.add(Calendar.DATE, -7);

	    // -1 : cal < cal2, 0 : cal == cal2, 1 : cal > cal2
	    if (cal.compareTo(cal2) >= 0) {
		// 다음 년도/회기 생성
		optionAPIService.insertPeriodAuto(compId, String.valueOf(cal.get(Calendar.YEAR)));

		String batchType = AppConfig.getProperty("bindBatchType", "default", "etc");
	
		execute(bindBatchService, compId, cal.get(Calendar.YEAR) + 1, batchType);
	    }
	}
    }

    private static void execute(BindBatchService bindBatchService, String compId, int year, String batchType)
	    throws Exception {
	BatchVO batchVO = bindBatchService.get(compId, String.valueOf(year));

	if (batchVO == null || batchVO.getCompId() == null) {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put(COMP_ID, compId);
	    params.put(CREATE_YEAR, String.valueOf(year));
	    params.put(EXPIRE_YEAR, String.valueOf(year));
	    params.put(CREATED, DateUtil.getCurrentDate(DEFAULT_DATE_FORMAT));
	    params.put(CREATED_ID, "SYSTEM");
	    params.put(BEFORE_YEAR, String.valueOf(year - 1));

	    long start = System.currentTimeMillis();

	    int result = bindBatchService.execute(params, batchType);

	    long end = System.currentTimeMillis();

	    if (result > 0) {
		insertBatch(bindBatchService, compId, year, start, end);
	    }
	}
    }


    private static void insertBatch(BindBatchService bindBatchService, String compId, int year, long start, long end) throws Exception {
	String startDate = FastDateFormat.getInstance(DEFAULT_DATE_FORMAT).format(start);
	String endDate = FastDateFormat.getInstance(DEFAULT_DATE_FORMAT).format(end);

	BatchVO vo = new BatchVO();
	vo.setCompId(compId);
	vo.setStartDate(startDate);
	vo.setEndDate(endDate);
	vo.setYear(String.valueOf(year));
	vo.setExecuteTime(end - start);

	bindBatchService.insert(vo);
    }
}
