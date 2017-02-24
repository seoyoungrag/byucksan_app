package com.sds.acube.app.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jconfig.Configuration;


public class ExcelUtil {

    private static final String BYINT = "ByInt";
    private static final String SET = "set";
    private static final String JAVABYTE = "[B";
    private static final String JAVAINT = "int";
    private static final String JAVABOOLEAN = "boolean";
    private static final String JAVASTRING = "java.lang.String";
    private static final String JAVAINTEGER = "java.lang.Integer";
    private static final String JAVAOBJECT = "java.lang.Object";

    protected Log logger = LogFactory.getLog(this.getClass().getName());
    private String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
    
    
    @SuppressWarnings("unchecked")
    public Map<String, String> exportListData(HttpServletRequest request, HttpServletResponse response, 
	    ArrayList dataList, String[] titleList, String sheetName, String compId, String lobCode, String sftpYn) throws Exception {

	ListIterator li = dataList.listIterator();
	ArrayList data = new ArrayList();
	Object[] arrayObject = null;
	Object tmpObject = null;
	int titleCount = titleList.length;
	
	Map<String, String> map = new HashMap<String, String>();
	
	while (li.hasNext()) {
	    Object object = (Object) li.next();
	    if (object instanceof HashMap) {
		HashMap resultMap = (HashMap) object;
		Iterator<String> it = resultMap.keySet().iterator();
		int keyCount = resultMap.keySet().size();
		if (titleCount > keyCount) {
		    arrayObject = new Object[titleCount];
		} else { 
		    arrayObject = new Object[keyCount];
		}
		int loop = 0;
		while (it.hasNext()) {
		    String key = it.next();
		    tmpObject = resultMap.get(key);
		    arrayObject[loop++] = tmpObject;
		}
		data.add(arrayObject);
	    } else if (object instanceof ArrayList) {
		ArrayList resultList = (ArrayList) object;
		int listCount = resultList.size();
		if (titleCount > listCount) {
		    arrayObject = new Object[titleCount];
		} else { 
		    arrayObject = new Object[listCount];
		}
		for (int loop = 0; loop < listCount; loop++) {
		    tmpObject = resultList.get(loop);
		    arrayObject[loop] = tmpObject;
		}
		data.add(arrayObject);
	    } else {
		Method[] methods = object.getClass().getDeclaredMethods();
		int methodCount = methods.length;
		if (titleCount > methodCount) {
		    arrayObject = new Object[titleCount];
		} else { 
		    arrayObject = new Object[methodCount];
		}
		for (int loop = 0; loop < methodCount; loop++) {
		    if (methods[loop].getName().indexOf(SET) == 0 && !methods[loop].getName().endsWith(BYINT)) {
			String methodName = methods[loop].getName().substring(3);
			Class[] param = methods[loop].getParameterTypes();
			Method sourcemethod = object.getClass().getMethod("get" + methodName, null);
			Object[] params = null;
			if (param[0].toString().indexOf(JAVASTRING) != -1) {
			    if (sourcemethod.invoke(object, null) != null)
				params = new Object[] { sourcemethod.invoke(object, null).toString() };
			} else if (param[0].toString().indexOf(JAVAINTEGER) != -1) {
			    if (!"".equals(sourcemethod.invoke(object, null).toString()))
				params = new Object[] { new Integer(sourcemethod.invoke(object, null).toString()) };
			} else if (param[0].toString().indexOf(JAVABYTE) != -1) {
			    if (sourcemethod.invoke(object, null) != null)
				params = new Object[] { sourcemethod.invoke(object, null) };
			} else if (param[0].toString().indexOf(JAVAINT) != -1) {
			    params = new Object[] { new Integer(sourcemethod.invoke(object, null).toString()) };
			} else if (param[0].toString().indexOf(JAVABOOLEAN) != -1) {
			    params = new Object[] { new Boolean(sourcemethod.invoke(object, null).toString()) };
			} else if (param[0].toString().indexOf(JAVAOBJECT) != -1) {
			    if (sourcemethod.invoke(object, null) != null)
				params = new Object[] { sourcemethod.invoke(object, null) };
			} else {
			    continue;
			}
			if (params != null)
			    tmpObject = params[0];
			else
			    tmpObject = "";
			arrayObject[loop] = tmpObject;
		    }
		}	
		data.add(arrayObject);
	    }
	}

	String filePath = checkDir(uploadTemp + "/" + compId);
	String current = DateUtil.getCurrentDate("yyyyMMddHHmmss");
	String fileName = GuidUtil.getGUID() + "_" + current + ".xls";
	String downloadFileName = sheetName + "_" + current + ".xls";
	
	makeExcel(request, data, titleList, sheetName, fileName, filePath);

	map.put("filePath",filePath);
	map.put("fileName",fileName);
	map.put("downloadFileName",downloadFileName);
	
	return map;
    }
    
    @SuppressWarnings("unchecked")
    public void exportListData(HttpServletRequest request, HttpServletResponse response, 
	    ArrayList dataList, String[] titleList, String sheetName, String compId, String lobCode) throws Exception {

	ListIterator li = dataList.listIterator();
	ArrayList data = new ArrayList();
	Object[] arrayObject = null;
	Object tmpObject = null;
	int titleCount = titleList.length;
	
	while (li.hasNext()) {
	    Object object = (Object) li.next();
	    if (object instanceof HashMap) {
		HashMap resultMap = (HashMap) object;
		Iterator<String> it = resultMap.keySet().iterator();
		int keyCount = resultMap.keySet().size();
		if (titleCount > keyCount) {
		    arrayObject = new Object[titleCount];
		} else { 
		    arrayObject = new Object[keyCount];
		}
		int loop = 0;
		while (it.hasNext()) {
		    String key = it.next();
		    tmpObject = resultMap.get(key);
		    arrayObject[loop++] = tmpObject;
		}
		data.add(arrayObject);
	    } else if (object instanceof ArrayList) {
		ArrayList resultList = (ArrayList) object;
		int listCount = resultList.size();
		if (titleCount > listCount) {
		    arrayObject = new Object[titleCount];
		} else { 
		    arrayObject = new Object[listCount];
		}
		for (int loop = 0; loop < listCount; loop++) {
		    tmpObject = resultList.get(loop);
		    arrayObject[loop] = tmpObject;
		}
		data.add(arrayObject);
	    } else {
		Method[] methods = object.getClass().getDeclaredMethods();
		int methodCount = methods.length;
		if (titleCount > methodCount) {
		    arrayObject = new Object[titleCount];
		} else { 
		    arrayObject = new Object[methodCount];
		}
		for (int loop = 0; loop < methodCount; loop++) {
		    if (methods[loop].getName().indexOf(SET) == 0 && !methods[loop].getName().endsWith(BYINT)) {
			String methodName = methods[loop].getName().substring(3);
			Class[] param = methods[loop].getParameterTypes();
			Method sourcemethod = object.getClass().getMethod("get" + methodName, null);
			Object[] params = null;
			if (param[0].toString().indexOf(JAVASTRING) != -1) {
			    if (sourcemethod.invoke(object, null) != null)
				params = new Object[] { sourcemethod.invoke(object, null).toString() };
			} else if (param[0].toString().indexOf(JAVAINTEGER) != -1) {
			    if (!"".equals(sourcemethod.invoke(object, null).toString()))
				params = new Object[] { new Integer(sourcemethod.invoke(object, null).toString()) };
			} else if (param[0].toString().indexOf(JAVABYTE) != -1) {
			    if (sourcemethod.invoke(object, null) != null)
				params = new Object[] { sourcemethod.invoke(object, null) };
			} else if (param[0].toString().indexOf(JAVAINT) != -1) {
			    params = new Object[] { new Integer(sourcemethod.invoke(object, null).toString()) };
			} else if (param[0].toString().indexOf(JAVABOOLEAN) != -1) {
			    params = new Object[] { new Boolean(sourcemethod.invoke(object, null).toString()) };
			} else if (param[0].toString().indexOf(JAVAOBJECT) != -1) {
			    if (sourcemethod.invoke(object, null) != null)
				params = new Object[] { sourcemethod.invoke(object, null) };
			} else {
			    continue;
			}
			if (params != null)
			    tmpObject = params[0];
			else
			    tmpObject = "";
			arrayObject[loop] = tmpObject;
		    }
		}	
		data.add(arrayObject);
	    }
	}

	String filePath = checkDir(uploadTemp + "/" + compId);
	String current = DateUtil.getCurrentDate("yyyyMMddHHmmss");
	String fileName = GuidUtil.getGUID() + "_" + current + ".xls";
	String downloadFileName = URLEncoder.encode(sheetName, "utf8") + "_" + current + ".xls";
	
	makeExcel(request, data, titleList, sheetName, fileName, filePath);

	try {
	    File f = new java.io.File(filePath + fileName);
	    byte b[] = new byte[1024];

	    BufferedInputStream fin = new BufferedInputStream(new java.io.FileInputStream(f));
	    BufferedOutputStream fout = new BufferedOutputStream(response.getOutputStream());

	    String strClient = request.getHeader("User-Agent");

	    if (strClient.indexOf("MSIE 5.5") != -1) {
		response.setHeader("Content-Type", "doesn/matter; charset=euc-kr");
		this.setDBCSHeader("Content-Disposition", "filename=" + downloadFileName + ";", response);
	    } else {
		response.setHeader("Content-Type", "application/octet-stream; charset=euc-kr");
		this.setDBCSHeader("Content-Disposition", "attachment;filename=" + downloadFileName + ";", response);
	    }
	    response.setHeader("Content-Transfer-Encoding", "binary;");
	    response.setHeader("Pragma", "no-cache;");
	    response.setHeader("Expires", "-1;");

	    if (logger.isDebugEnabled())
		logger.debug(downloadFileName);

	    for (int i; (i = fin.read(b)) != -1;) {
		fout.write(b, 0, i);
		fout.flush();
	    }

	    fin.close();
	    fout.flush();
	    fout.close();
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
    }


    @SuppressWarnings("unchecked")
    public void exportData(HttpServletRequest request, HttpServletResponse response, 
	    ArrayList dataList, String[] titleList, String sheetName, String compId, String lobCode) {

	String filePath = checkDir(uploadTemp + "/" + compId);
	String fileName = GuidUtil.getGUID() + ".xls";
	String downloadFileName = sheetName + ".xls";

	makeExcel(request, dataList, titleList, sheetName, fileName, filePath);

	try {
	    File f = new java.io.File(filePath + fileName);
	    byte b[] = new byte[1024];

	    java.io.BufferedInputStream fin = new java.io.BufferedInputStream(new java.io.FileInputStream(f));
	    java.io.BufferedOutputStream fout = new java.io.BufferedOutputStream(response.getOutputStream());

	    String strClient = request.getHeader("User-Agent");

	    if (strClient.indexOf("MSIE 5.5") != -1) {
		response.setHeader("Content-Type", "doesn/matter; charset=euc-kr");
		this.setDBCSHeader("Content-Disposition", "filename=" + downloadFileName + ";", response);
	    } else {
		response.setHeader("Content-Type", "application/octet-stream; charset=euc-kr");
		this.setDBCSHeader("Content-Disposition", "attachment;filename=" + downloadFileName + ";", response);
	    }
	    response.setHeader("Content-Transfer-Encoding", "binary;");
	    response.setHeader("Pragma", "no-cache;");
	    response.setHeader("Expires", "-1;");

	    for (int i; (i = fin.read(b)) != -1;) {
		fout.write(b, 0, i);
		fout.flush();
	    }

	    fin.close();
	    fout.flush();
	    fout.close();
	} catch (java.io.FileNotFoundException e) {
	} catch (java.lang.Throwable e) {
	}
    }


    @SuppressWarnings("unchecked")
    public void makeExcel(HttpServletRequest request, ArrayList data, String[] titleList, String sheetName, String fileName, String filePath) {
	try {
	    String strCell = "";
	    Date dtCell = new Date();
	    WritableWorkbook workbook = Workbook.createWorkbook(new File(filePath + fileName));

	    String fontNames = AppConfig.getProperty("fontname", "Gulim", "excel");
	    String[] fontName = fontNames.split(",");

	    WritableFont.FontName gulim = WritableFont.createFont(fontName[0]);
	    WritableFont gulim9Bold = new WritableFont(gulim, 9, WritableFont.BOLD);
	    WritableCellFormat cellFormatTitle = new WritableCellFormat(gulim9Bold); // 셀의 스타일을 지정.
										    		     
	    cellFormatTitle.setAlignment(Alignment.CENTRE);
	    cellFormatTitle.setVerticalAlignment(VerticalAlignment.CENTRE);
	    cellFormatTitle.setWrap(true);
	    cellFormatTitle.setBorder(Border.ALL, BorderLineStyle.THIN); // 테두리
	    cellFormatTitle.setBackground(Colour.ICE_BLUE); // 라인
	    cellFormatTitle.setWrap(true);

	    // 셀의 스타일을 지정
	    WritableFont gulim9 = new WritableFont(gulim, 9, WritableFont.NO_BOLD);
	    WritableCellFormat cellFormat = new WritableCellFormat(gulim9);
	    cellFormat.setAlignment(Alignment.LEFT);
	    cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
	    cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 테두리 라인
	    
	    // 등록취소된 문서를 위한 셀 스타일 지정
	    WritableFont struckout = new WritableFont(gulim, 9, WritableFont.NO_BOLD, true, UnderlineStyle.NO_UNDERLINE, Colour.GRAY_50, ScriptStyle.NORMAL_SCRIPT);
	    struckout.setStruckout(true);		// 취소선 설정
	    WritableCellFormat struckoutCellFormat = new WritableCellFormat(struckout);
	    struckoutCellFormat.setAlignment(Alignment.LEFT);
	    struckoutCellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
	    struckoutCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN); // 테두리 라인

	    if (workbook != null) {
		int listCount = titleList.length;
		WritableSheet sheet = workbook.createSheet(sheetName, 0);
		for (int k = 0; k < listCount; k++) {
		    Label label = new Label(k, 0, (String)titleList[k], cellFormatTitle);
		    sheet.setColumnView(k, 20);
		    sheet.addCell(label);
		}

		for (int j = 0; j < data.size(); j++) {
		    Object[] cellData = (Object[]) data.get(j);
		    int columnCount = Math.min(listCount, cellData.length);
		   
		    boolean unregistFlag = false;
		    Object lastColumn = cellData[cellData.length - 1];
		    // 마지막 셀에 등록취소여부값을 셋팅하고 등록취소인 경우 취소선 셀 스타일 지정
		    if (lastColumn.getClass() == strCell.getClass()) {
			if ("T".equals((String) lastColumn)) {
			    unregistFlag = true;
			}
		    }
		    for (int k = 0; k < columnCount; k++) {
			Object columnContent = cellData[k];
			if (columnContent != null) {
			    if (columnContent.getClass() == dtCell.getClass()) {
				dtCell = (Date) columnContent;
				DateTime dt = new DateTime(k, j + 1, dtCell);
				sheet.addCell(dt);
			    } else if (columnContent.getClass() == strCell.getClass()) {
				strCell = (String) columnContent;
				Label label = new Label(k, j + 1, strCell, (unregistFlag ? struckoutCellFormat : cellFormat));
				sheet.addCell(label);
			    }
			} else {
			    strCell = "";
			    Label label = new Label(k, j + 1, strCell, (unregistFlag ? struckoutCellFormat : cellFormat));
			    sheet.addCell(label);
			}
		    }
		}

	    }

	    workbook.write();
	    workbook.close();
	} catch (java.io.FileNotFoundException e) {
	} catch (java.lang.Throwable e) {
	}
    }


    public void setDBCSHeader(String header, String value, HttpServletResponse response) {
	byte b[];
	try {
	    b = value.getBytes(response.getCharacterEncoding());
	} catch (Exception ex) {
	    b = value.getBytes();
	}

	char c[] = new char[b.length];
	for (int i = 0; i < b.length; i++) {
	    c[i] = (char) (((char) b[i]) & 0xff);
	}

	response.setHeader(header, new String(c));
    }


    String checkDir(String dir) {
	if (dir == null)
	    return null;

	String sep = "/";
	dir = (dir.endsWith(sep)) ? dir : (dir + sep);

	return dir;
    }
}
