/**
 * 
 */
package com.sds.acube.app.common.servlet;


/**
 * <pre>
 * Copyright (c) 2012 Samsung SDS.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Samsung
 * SDS. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with Samsung SDS.
 *
 * Author	        : 
 * Date   	        : 2012.03
 * Description 	    : 
 * <pre/>
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.DeferredFileOutputStream;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.GuidUtil;

/**
 * Desc :.
 * 
 * @Author :
 * @Date : 2012.03
 * @Version : 1.0
 */
public class AjaxUploadServlet extends HttpServlet {

	/** The logger. */
	private Logger logger = LoggerFactory.getLogger("luxor_common");

	/** The Constant FILE_URI_PATH. */
	private static final String FILE_URI_PATH = "/file/";

	/** The Constant UPLOADING_URI_PATH. */
	private static final String UPLOADING_URI_PATH = "/uploading/";
	// private static final String DELETE_URI_PATH = "/delete/";

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The repository. */
	private File repository = new File("/temp/files");

	/** The config. */
	private Properties config;

	/** The enc from. */
	private static String encFrom = "";

	/** The enc to. */
	private static String encTo = "";

	/**
	 * Convert encoding.
	 * 
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String convertEncoding(String s) {
		return s;
	}

	/**
	 * Instantiates a new ajax upload servlet.
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public AjaxUploadServlet() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {
		super.init();
		if (config == null) {
			config = new Properties();
			config.setProperty("buffer.size", "10240");
			// config.setProperty("script.url", "upload");
			// config.setProperty("param.name", "files");
			// config.setProperty("max.number.of.files", "50");
			// config.setProperty("discard.aborted.uploads", "true");
		}
		repository = new File(AppConfig.getProperty("upload_temp", "", "attach"));
		if (!repository.exists()) {
			repository.mkdirs();
		}
	}
	
	/**
	 * 파일경로(다운로드와 같은 경우)에 대한 XSS에 대해서 변경
	 * 
	 * @since 2014.02.19
	 * @param data
	 * @return
	 */
	public static String clearXSSFilePath(String data) {
		data = org.springframework.util.StringUtils.replace(data, "../", "");
		data = org.springframework.util.StringUtils.replace(data, "..\\", "");
		return data;
	}

	/**
	 * .../file/[fileid]/[filename] - 파일 다운로드 .../list/[모듈:bbs,schedule]/[fid] - fid에 따른 첨부 목록 .../uploading/[filename] - 업로드중인 파일 정보
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String compId = request.getParameter("compId");
		logger.debug("*** requested uri : {}", uri);
		if (uri.indexOf(FILE_URI_PATH) > 0) {
			logger.debug("*** [doGet] file download start...");
			uri = clearXSSFilePath(uri); // @add 2014.02.19 XSS
			
			
			String fileInfoString = uri.substring(uri.indexOf(FILE_URI_PATH) + FILE_URI_PATH.length());
			String[] fileInfo = fileInfoString.split("/");
			if (fileInfo == null || fileInfo.length < 2) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "No file information:" + fileInfoString);
			}

			BufferedInputStream fin = null;
			BufferedOutputStream fout = null;
			String serverFile = repository.getAbsolutePath() + File.separator + compId + File.separator + fileInfo[1];

			logger.debug("*** [doGet] serverFile : {}, fileName : {}", serverFile, fileInfo[2]);

			try {
				response.setContentType("application/octet-stream;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + fileInfo[2] + ";");
				response.setHeader("Content-Transfer-Encoding", "binary;");
				response.setHeader("Pragma", "no-cache;");
				response.setHeader("Expires", "-1;");

				byte buf[] = new byte[2 * 1024];
				fin = new BufferedInputStream(new FileInputStream(serverFile));
				fout = new BufferedOutputStream(response.getOutputStream());
				int fread = 0;

				// 파일 읽기 및 쓰기
				while ((fread = fin.read(buf)) != -1) {
					fout.write(buf, 0, fread);
				}
			} catch (Exception e) {
				logger.error("*** [doGet] - download Error (file: {}, fid : {})", fileInfo[1], serverFile);
				logger.error("*** [doGet] - download Error {}", e.getMessage());
			} finally {
				if (fout != null) {
					fout.flush();
					fout.close();
				}
				if (fin != null) {
					fin.close();
				}
				/**
				 * 다운로드 하고나서 temp 파일을 삭제하면 동일 화면에서<br>
				 * 다시 다운로드할때 FileNotFoundException 발생.<br>
				 * temp에 쌓여있는 파일은 어떻게 할 것인지 정책이 필요함.<br>
				 */
				// File sfile = new File(serverFile); // fileInfo[1]
				// if (sfile.exists()) {
				// if (sfile.delete()) {
				// logger.debug("*** [doGet] temp file delete success...");
				// } else {
				// logger.debug("*** [doGet] temp file delete fail...");
				// }
				// }
				logger.debug("*** [doGet] file download end...");
			}
		}
	}



	/**
	 * Desc :.
	 * 
	 * @param item
	 *            the item
	 * @return the file name without path
	 */
	public String getFileNameWithoutPath(FileItem item) {
		String name = convertEncoding(item.getName());
		if (name.indexOf("\\") >= 0) { // IE returns a file name with full paths
			String[] nameParts = name.split("\\\\");
			name = nameParts[nameParts.length - 1];
		}
		return name;
	}

	/**
	 * Desc :.
	 * 
	 * @param item
	 *            the item
	 * @param fileUrl
	 *            the file url
	 * @param uploadedFile
	 *            the uploaded file
	 * @return the json
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String getJson(FileItem item, String fileUrl, File uploadedFile) throws Exception {
		// - name, size, type, url, error, delete_url, delete_type
		// StringBuffer json = new StringBuffer();
		String name = item.getName();
		name = convertEncoding(name);
		if (name == null) {
			return "{}";
		}
		name = getFileNameWithoutPath(item);// IE returns a file name with full paths
		String tempFileName = uploadedFile == null ? getTempFile(item).getName() : uploadedFile.getName();
		// StringBuilder deleteUrl = new StringBuilder(fileUrl);
		// deleteUrl.replace(deleteUrl.lastIndexOf("/"), deleteUrl.length(), DELETE_URI_PATH + tempFileName);

		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("type", item.getContentType());
		json.put("name_temp", tempFileName);
		json.put("size", item.getSize());
		json.put("url", fileUrl);
		json.put("error", "");

		// json.append("{\"name\":\"").append(name).append("\",\"type\":\"").append(item.getContentType()).append("\",\"name_temp\":\"").append(tempFileName).append("\",\"size\":\"")
		// .append(item.getSize()).append("\",\"url\":\"").append(fileUrl).append("\",\"error\":\"").append("").append("\"}");
		String result = json.toString();

		return result;
	}

	/**
	 * Desc :.
	 * 
	 * @param request
	 *            the request
	 * @return the json content type
	 */
	private String getJsonContentType(HttpServletRequest request) {
		String contentType = "text/plain;charset=UTF-8";
		String accept = request.getHeader("Accept");
		if (accept != null && accept.indexOf("application/json") >= 0) {
			contentType = "application/json;charset=UTF-8";
		}
		return contentType;
	}

	/**
	 * Desc :.
	 * 
	 * @param request
	 *            the request
	 * @param item
	 *            the item
	 * @return the file url
	 */
	private String getFileURL(HttpServletRequest request, FileItem item) {
		StringBuilder url = new StringBuilder(request.getRequestURL());
		if (url.indexOf(UPLOADING_URI_PATH) > 1) {
			url.delete(url.indexOf(UPLOADING_URI_PATH), url.length());
		}
		String name = getFileNameWithoutPath(item);
		url.append(UPLOADING_URI_PATH).append(name);
		return url.toString();
	}

	/**
	 * Desc :.
	 * 
	 * @param item
	 *            the item
	 * @param servletUrl
	 *            the servlet url
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private String processUploadedFile(FileItem item, String servletUrl) throws Exception {
		// Process a file upload
		if (!item.isFormField()) {
			File uploadedFile = getUploadFile(item);
			try {
				item.write(uploadedFile);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("processUploadedFile Error {}", e.getMessage());
			}
			return getJson(item, servletUrl, uploadedFile);
		}
		return "";
	}

	/**
	 * Desc :.
	 * 
	 * @param item
	 *            the item
	 * @return the upload file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private File getUploadFile(FileItem item) throws IOException {
		String fileName = item.getName();
		fileName = convertEncoding(fileName);
		String extention = "";
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex > 1) {
			extention = fileName.substring(dotIndex);
		}
		// return new File(repository, UUID.randomUUID().toString() + extention);
		
		String upfile = GuidUtil.getGUID("").concat(extention); 
		return new File(repository, upfile);

	}

	/**
	 * Desc :.
	 * 
	 * @param item
	 *            the item
	 * @return the temp file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private File getTempFile(FileItem item) throws IOException {
		File uploadedFile = null;
		if (item.getOutputStream() instanceof DeferredFileOutputStream) {
			DeferredFileOutputStream dfout = (DeferredFileOutputStream) item.getOutputStream();
			uploadedFile = dfout.getFile();
		}
		return uploadedFile;
	}

}

