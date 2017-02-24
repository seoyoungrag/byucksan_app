package com.sds.acube.app.common.util;

import java.io.File;

import org.jconfig.Configuration;

import com.unidocs.workflow.client.WFJob;
import com.unidocs.workflow.common.FileEx;
import com.unidocs.workflow.common.JobResult;

/** 
 *  Class Name  : PdfUtil.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 5. 16. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 5. 16.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.util.PdfUtil.java
 */

public class PdfUtil {
    private static String server = AppConfig.getProperty("server", "", "pdf");
    private static int port = AppConfig.getIntProperty("port", 715, "pdf");
    private static String[] extensions = AppConfig.getArray("extensions", new String[]{"hwp"}, "pdf");

    public static int generatePdf(String sourcePath, String targertPath) {

	int result = 0;
	boolean ispossible = false;
	String extension = "hwp";
	int pos = sourcePath.lastIndexOf(".");
	if (pos != -1 && pos < sourcePath.length()) {
	    extension = sourcePath.substring(pos +1).toLowerCase();
	}
	for (int loop = 0; loop < extensions.length; loop++) {
	    if (extension.equals(extensions[loop])) {
		ispossible = true;
		break;
	    }
	}
	
	if (ispossible) {
	    UniFlow.UniFlowGenPDF genPDF = new UniFlow.UniFlowGenPDF();
	    genPDF.SetServerAddress(server, port);
	    result = genPDF.GeneratePDF(sourcePath, targertPath, 0);
	}
	
	return result;
    }

    public static int generatePDF(String sourcePath, String targertPath) {

	int result = 0;
	boolean ispossible = false;
	String extension = "hwp";
	int pos = sourcePath.lastIndexOf(".");
	if (pos != -1 && pos < sourcePath.length()) {
	    extension = sourcePath.substring(pos +1).toLowerCase();
	}
	for (int loop = 0; loop < extensions.length; loop++) {
	    if (extension.equals(extensions[loop])) {
		ispossible = true;
		break;
	    }
	}
	
	if (ispossible) {
	    LogWrapper.getLogger("com.sds.acube.app").debug("##### Start : generatePDF[" + sourcePath + "]");
	    WFJob job = null;
	    try {
		job = new WFJob();		

		FileEx srcFile = new FileEx(sourcePath);
		// PDF변환
		JobResult jr = job.generatePDF(srcFile, new File(sourcePath).getName().toString()+".pdf", 0);            

		if (jr.getStatus() == JobResult.JOB_OK) {
		    FileEx[] out = jr.getOutFile();	

		    for (int j=0; j< out.length; j++) {
			out[j].saveTo(new File(targertPath), true);
			result++;
		    }
		} else 	{			 
		    jr.getException().printStackTrace();				
		}            
		job = null;			
	    } catch(Exception e) {
		e.printStackTrace();
	    }
/*	    
	    try {
		WFJob job = new WFJob();
		job.setJobBatch(true);			

		FileEx srcFile = new FileEx(sourcePath);
		//PDF변환
		JobResult jr = job.generatePDF(srcFile, GuidUtil.getGUID() + ".pdf", 0);

		if ( jr.getStatus() == JobResult.JOB_OK) {
		    jr = job.getJobResult();
		    FileEx[] out = jr.getOutFile();
		    int fileCount = out.length;
		    for (int loop = 0; loop < fileCount; loop++) {
			out[loop].saveTo(new File(targertPath));
			result++;
		    }
		} else {			 
		    LogWrapper.getLogger("com.sds.acube.app").error(jr.getException().getMessage());
		}   
		job.clearJobDirectory();
		job = null;	
	    } catch(Exception e)	{
		LogWrapper.getLogger("com.sds.acube.app").error(e.getMessage());
	    }
*/	    
	    LogWrapper.getLogger("com.sds.acube.app").debug("##### End : generatePDF[" + sourcePath + "]");
	}

	return result;
    }
}
