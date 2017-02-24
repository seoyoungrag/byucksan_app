/**
 * 
 */
package com.sds.acube.app.bind;

import com.sds.acube.app.bind.vo.BindVO;

/**
 * Class Name : AppConstants.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 4. 7. <br>
 * 수 정 자 : I-ON <br>
 * 수정내용 : <br>
 * 
 * @author I-ON
 * @since 2011. 4. 7.
 * @version 1.0
 * @see com.sds.acube.app.bind.BindConstants.java
 */

public interface BindConstants {

    String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";

    String ROOT_ID = "*";

    String ID = "id";
    String COMP_ID = "compId";
    String COMP_NAME = "compName";
    String DEPT_ID = "deptId";
    String DEPT_NAME = "deptName";
    String UNIT_ID = "unitId";
    String UNIT_DEPTH = "unitDepth";
    String UNIT_ORDER = "unitOrder";
    String UNIT_NAME = "unitName";
    String PARENT_UNIT_ID = "parentUnitId";
    String UNIT_TYPE = "unitType";
    String ORDERED = "ordered";
    String ALL = "all";

    String DOC_ID = "docId";
    String BIND_ID = "bindId";
    String BIND_NAME = "bindName";
    String RETENTION_PERIOD = "retentionPeriod";
    String APPLIED = "applied";
    String CREATED = "created";
    String CREATED_ID = "createdId";
    String MODIFIED = "modified";
    String SUCCESS = "success";
    String MESSAGE_ID = "msgId";
    String MESSAGE = "msg";
    String DESCRIPTION = "description";
    String CREATE_YEAR = "createYear";
    String EXPIRE_YEAR = "expireYear";

    String ROWS = "rows";
    String ROW = "row";

    String DOC_TYPE = "docType";
    String YEAR = "year";
    String MONTH = "month";
    String DAY = "day";
    String TYPE = "type";
    String VALUE = "value";
    String DTYPE = "dType";
    String TARGET = "target";
    String ARRANGE = "arrange";
    String BINDING = "binding";

    String OPTION = "option";
    String PERIOD = "period";
    String TARGET_ID = "targetId";
    
    String CHILD_STATE_YES = "Y";
    String CHILD_STATE_NO = "N";
    
    // DRY001 = 1년
    // DRY002 = 3년
    // DRY003 = 5년
    // DRY004 = 10년
    // DRY005 = 20년
    // DRY006 = 30년
    // DRY007 = 준영구
    // DRY008 = 영구
    /**
	 * Class Name  : BindConstants.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
	 * @author    kimside 
	 * @since   2012. 5. 23.
	 * @version   1.0 
	 * @see  com.sds.acube.app.bind.BindConstants.java
	 */
    enum RETENTION_PERIODS {
	/**
	 */
	DRY001, /**
	 */
	DRY002, /**
	 */
	DRY003, /**
	 */
	DRY004, /**
	 */
	DRY005, /**
	 */
	DRY006, /**
	 */
	DRY007, /**
	 */
	DRY008
    };

    // DTY001 일반문서
    // DTY002 도면류
    // DTY003 사진, 필름류 시청각기록물
    // DTY004 녹음, 동영상류 시청각기록물
    // DTY005 카드류
    /**
	 * Class Name  : BindConstants.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
	 * @author    kimside 
	 * @since   2012. 5. 23.
	 * @version   1.0 
	 * @see  com.sds.acube.app.bind.BindConstants.java
	 */
    enum DOCUMENT_TYPES {
	/**
	 */
	DTY001, /**
	 */
	DTY002, /**
	 */
	DTY003, /**
	 */
	DTY004, /**
	 */
	DTY005
    }

    // DST001 사용
    // DST002 인수
    // DST003 인계
    // DST004 공유
    /**
	 * Class Name  : BindConstants.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
	 * @author    kimside 
	 * @since   2012. 5. 23.
	 * @version   1.0 
	 * @see  com.sds.acube.app.bind.BindConstants.java
	 */
    enum SEND_TYPES {
	/**
	 */
	DST001, /**
	 */
	DST002, /**
	 */
	DST003, /**
	 */
	DST004
    }

    String LANG_TYPE_CODE = "LANG_TYPE";
    String KO = "ko";

    String TRUE = "true";
    String FALSE = "false";
    String PATH = "path";

    String TEXT = "text";
    String EXPANDED = "expanded";
    String CLS = "cls";
    String FOLDER = "folder";
    String FOLDER_GRAY = "icon-folder-gray";
    String LEAF = "leaf";
    String UNIT = "unit";
    String ICON_CLS = "iconCls";
    String ICON_UNIT = "icon-unit";
    String CALC = "calc";
    String MANAGER = "manager";
    String BIND = "bind";
    String QTIP = "qtip";

    String SEARCH_YEAR = "searchYear";
    String SEARCH_TYPE = "searchType";
    String SEARCH_VALUE = "searchValue";
    String SEARCH_OPTION = "searchOption";
    String TRANS_YEAR = "transYear";
    String SEARCH_MONTH = "searchMonth";

    String UTF8 = "utf-8";

    String COMP_ID_CODE = "COMP_ID";
    String COMP_NAME_CODE = "COMP_NAME";
    String USER_ID_CODE = "USER_ID";
    String USER_NAME_CODE = "USER_NAME";
    String DEPT_ID_CODE = "DEPT_ID";
    String DEPT_NAME_CODE = "DEPT_NAME";
    String LOGIN_ID_CODE = "LOGIN_ID";
    String EMPLOYEE_ID = "EMPLOYEE_ID";
    
    String PROXY_DOC_HANDLE_DEPT_CODE = "PROXY_DOC_HANDLE_DEPT_CODE";

    String PAGE_NO = "pageNo";
    String LIST_NUM = "listNum";
    String SCREEN_COUNT = "screenCount";
    String DEFAULT_COUNT = "15";

    String BIND_IDS = "bindIds";
    String DOC_IDS = "docIds";
    String DEPT_IDS = "deptIds";
    String TARGET_IDS = "targetIds";

    String Y = "Y";
    String N = "N";

    String DOC_NUMBER = "docNumber";
    String DEPT_CATEGORY = "deptCategory";
    String SERIAL_NUMBER = "serialNumber";
    String SUBSERIAL_NUMBER = "subSerialNumber";

    String DEFAULT_SEARCH_TYPE = "_";
    String SEND_NAME = "sendName";
    String SEND_TYPE = "sendType";
    String RECEIVERS = "receivers";
    String EMPTY = "";
    String ORIGINAL_TEXT = "originalText";
    String DISPLAY_NAME = "displayName";

    String DEFAULT = "default";
    String CUSTORM = "custom"; // 대우증권

    String BEFORE_YEAR = "beforeYear";
    String BESTEZ_COMP_ID = "bestezCompId";
    String IGNORE_COMP_ID = "ignoreCompId";
    String CREATE_USER_ID = "createUserId";

    String YEAR_SESSION = "Y"; // Y:연도, P:회기

    String COMPANY = "company";

    String START_DAY = "startDay";
    String END_DAY = "endDay";

    String SEQ = "seq";
    String ORG_BIND_ID = "orgBindId";

    String PREFIX = "prefix";
    
    /**
     * TREE검색타입(부모:P, 자식:C)
     */
   String TREE_PARENT           = "P";
   String TREE_CHILD            = "C";
    
   String ROOT = "ROOT";
   String CABAUTH = "CABAUTH";
   String REMOVE_BIND_ERROR_DST3 = "DST3";
   String REMOVE_BIND_MESSAGE_DST3 = "인계한 캐비닛은 폐기할 수 없습니다.";
   String REMOVE_BIND_ERROR_SHARED = "IS_SHARED";
   String REMOVE_BIND_MESSAGE_SHARED = "공유되어 있는 캐비닛은 폐기 할 수 없습니다.";
   String REMOVE_BIND_ERROR_DOC = "IS_DOC";
   String REMOVE_BIND_MESSAGE_DOC = "문서가 존재하는 캐비닛이 있으므로 폐기할 수 없습니다.";
   
   String SHARE_BIND_ERROR_DST3 = "DST3";
   String SHARE_BIND_MESSAGE_DST3 = "인계한 캐비닛은 공유할 수 없습니다.";
   String SHARE_BIND_ERROR_DST4 = "DST4";
   String SHARE_BIND_MESSAGE_DST4 = "공유받은 캐비닛은 공유할 수 없습니다.";
   String SHARE_BIND_ERROR_UTT002 = "UTT002";
   String SHARE_BIND_MESSAGE_UTT002 = "부서고유 분류체계는 공유할 수 없습니다.";
   
   String TRANSPOSE_BIND_ERROR_DST3 = "DST003";
   String TRANSPOSE_BIND_MESSAGE_DST3 = "이미 인계 되었습니다.";
   String TRANSPOSE_BIND_ERROR_DST4 = "DST004";
   String TRANSPOSE_BIND_MESSAGE_DST4 = "공유받은 캐비닛은 인계 할 수 없습니다.";
   String TRANSPOSE_BIND_ERROR_BINDING = "BINDING";
   String TRANSPOSE_BIND_MESSAGE_BINDING = "확정되지 않은 캐비닛은 인계할 수 없습니다.";
   String TRANSPOSE_BIND_ERROR_SHARED = "SHARED";
   String TRANSPOSE_BIND_MESSAGE_SHARED = "공유되어 있는 캐비닛은 인계 할 수 없습니다.";
   String TRANSPOSE_BIND_ERROR_DOC_PROCESS = "DOC_PROCESS";
   String TRANSPOSE_BIND_MESSAGE_DOC_PROCESS = "결재 진행중인 문서가 있으므로 인계할 수 없습니다.";
}
