package com.sds.acube.app.appcom.util;

import java.io.File;
import java.util.List;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;

/** 
 *  Class Name  : FileUtil.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 3. 28. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 3. 28.
 *  @version 1.0 
 *  @see  com.sds.acube.app.appcom.util.FileUtil.java
 */

public final class FileUtil {

    public static boolean validateBodyList(List<List<StorFileVO>> storFileVOsList) {
	boolean result = true;
	int listCount = storFileVOsList.size();
	for (int loop = 0; loop < listCount; loop++) {
	    List<StorFileVO> storFileVOs = storFileVOsList.get(loop);
	    result = validateBody(storFileVOs);
	    if (!result) {
		break;
	    }
	}
	
	return result;
    }
    
    
    public static boolean validateBody(List<StorFileVO> storFileVOs) {
	boolean result = true;
	boolean existHwp = false;
	if (storFileVOs == null) {
	    result = false;
	} else {
	    int storFileCount = storFileVOs.size();
	    for (int loop = 0; loop < storFileCount; loop++) {
		StorFileVO storFileVO = storFileVOs.get(loop);
		String filePath = storFileVO.getFilepath();
		File file = new File(filePath);
		if (file.exists()) {
		    int pos = filePath.lastIndexOf(".");
		    if (pos != -1 && pos < (filePath.length() - 1)) {
			String extension = (filePath.substring(pos + 1)).toLowerCase();
			if ("hwp".equals(extension) || "doc".equals(extension)) {//added by jkkim word 추가 작업 관련 작업 2013.04.14
			    existHwp = true;
			    long fileSize = file.length() / 1024;
			    if (fileSize < 6) {
				result = false;
				break;
			    }
			} else if ("html".equals(extension)) {
				existHwp = true;
			}
		    }
		} else {
		    result = false;
		    break;
		}
	    }
	}
	
	return (result && existHwp);
    }

    
    public static boolean validateBody(StorFileVO storFileVO) {
	boolean result = true;
	boolean existHwp = false;
	if (storFileVO == null) {
	    result = false;
	} else {
	    String filePath = storFileVO.getFilepath();
	    File file = new File(filePath);
	    if (file.exists()) {
		int pos = filePath.lastIndexOf(".");
		if (pos != -1 && pos < (filePath.length() - 1)) {
		    String extension = (filePath.substring(pos + 1)).toLowerCase();
		    if ("hwp".equals(extension)) {
			existHwp = true;
			long fileSize = file.length() / 1024;
			if (fileSize < 6) {
			    result = false;
			}
		    }
		}
	    } else {
		result = false;
	    }
	}
	
	return (result && existHwp);
    }
    
    
    public static boolean validateBodyFile(List<FileVO> fileVOs) {
	boolean result = true;
	boolean existHwp = false;
	if (fileVOs == null) {
	    result = false;
	} else {
	    int storFileCount = fileVOs.size();
	    for (int loop = 0; loop < storFileCount; loop++) {
			FileVO fileVO = fileVOs.get(loop);
			String filePath = fileVO.getFilePath();
			File file = new File(filePath);
			if (file.exists()) {
			    int pos = filePath.lastIndexOf(".");
			    if (pos != -1 && pos < (filePath.length() - 1)) {
					String extension = (filePath.substring(pos + 1)).toLowerCase();
					if ("hwp".equals(extension) || "doc".equals(extension)) {
					    existHwp = true;
					    long fileSize = file.length() / 1024;
					    if (fileSize < 6) {
						result = false;
						break;
					    }
					}else if ("html".equals(extension)) {
						existHwp = true;
					}
			    }
			} else {
			    result = false;
			    break;
			}
	    }
	}
	
	return (result && existHwp);
    }
    
    
    public static boolean validateBodyFile(FileVO fileVO) {
	boolean result = true;
	boolean existHwp = false;
	if (fileVO == null) {
	    result = false;
	} else {
	    String filePath = fileVO.getFilePath();
	    File file = new File(filePath);
	    if (file.exists()) {
		int pos = filePath.lastIndexOf(".");
		if (pos != -1 && pos < (filePath.length() - 1)) {
		    String extension = (filePath.substring(pos + 1)).toLowerCase();
		    if ("hwp".equals(extension)) {
			existHwp = true;
			long fileSize = file.length() / 1024;
			if (fileSize < 6) {
			    result = false;
			}
		    }
		}
	    } else {
		result = false;
	    }
	}
	
	return (result && existHwp);
    }
    
}
