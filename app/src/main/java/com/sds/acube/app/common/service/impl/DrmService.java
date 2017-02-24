/**
 * 
 */
package com.sds.acube.app.common.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.service.IDrmService;

/** 
 *  Class Name  : DrmService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 25. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 3. 25.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.service.impl.DrmService.java
 */

@Service("drmService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class DrmService extends BaseService implements IDrmService {

    private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public DrmService() {}

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.service.IDrmService#processDownload(java.lang.String[][])
     */
    public void processDownload(String[][] farr) throws Exception {
	// TODO Auto-generated method stub

    }


    /* (non-Javadoc)
     * @see com.sds.acube.app.common.service.IDrmService#processUpload(java.lang.String[][])
     */
    public void processUpload(String[][] farr) throws Exception {
	// TODO Auto-generated method stub

    }

}
