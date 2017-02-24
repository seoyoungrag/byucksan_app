<%@page contentType="text/html;charset=utf-8" %>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.awt.*"%>
<%@page import="java.awt.Image"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="javax.swing.ImageIcon"%>
<%@page import="java.io.File"%>
<%@page import="java.io.IOException"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>
<%@page import="org.apache.commons.fileupload.FileUploadBase"%>
<%@include file="Util.jsp"%>

<%
	String encType = "utf-8"; 
	
	if(request.getParameter("licenseCheck") != null){
		if(request.getParameter("licenseCheck").toLowerCase().equals("true")){
			out.println(InetAddress.getLocalHost().getHostAddress());
			return;
		}
	}
	
	int maxSize = Integer.parseInt(request.getParameter("imageSizeLimit"));
	String defaultUPath = request.getParameter("defaultUPath");
	String imageUPath = request.getParameter("imageUPath");
	String imageUPathHost = "http://" + request.getHeader("host");
	String imagePhysicalPath = "";
%>
<%@include file="ImagePath.jsp"%>
<%
	String imageModify = ""; 
	if (request.getParameter("imagemodify") != null)
		imageModify =request.getParameter("imagemodify");
	
	String imageEditorFlag = "";
	if (request.getParameter("imageEditorFlag") != null)
		imageEditorFlag = request.getParameter("imageEditorFlag");
	
	String uploadFileSubDir = "";
	if (request.getParameter("uploadFileSubDir") != null)
		uploadFileSubDir = request.getParameter("uploadFileSubDir");
	
	String imageDomain = ""; 
	if (request.getParameter("imageDomain") != null)
		imageDomain = request.getParameter("imageDomain");

	String useExternalServer = "";
	if (request.getParameter("useExternalServer") != null)
		useExternalServer = request.getParameter("useExternalServer");
	
	String imageTemp = "";
	String scriptValue = "";
	String fileRealPath = "";
	String saveFolder = "";
	String returnParam ="";
	String scriptTag = "";
	String ContextPath = request.getContextPath();
	String ServerName = request.getServerName();
	
	ServletContext context = getServletConfig().getServletContext();

	if (!imageUPath.equals("")) {
		if (imageUPath.length() > 7) {
			if (imageUPath.substring(0, 7).equals("http://")) {
				imageTemp = imageUPath.substring(7);
				imageUPath = imageTemp.substring(imageTemp.indexOf("/"));
				imageUPathHost = "http://" + imageTemp.substring(0, imageTemp.indexOf("/"));
			}
			else if (imageUPath.substring(0, 8).equals("https://")) {
				imageTemp = imageUPath.substring(8);
				imageUPath = imageTemp.substring(imageTemp.indexOf("/"));
				imageUPathHost = "https://" + imageTemp.substring(0, imageTemp.indexOf("/"));
			}
			else if (!imageUPath.substring(0, 1).equals("/")) {
				scriptValue = executeScript(response, "invalid_path", "", useExternalServer, imageDomain, imageEditorFlag);
				out.println(scriptValue);
				return;
			}
		} else {
			if (!imageUPath.substring(0, 1).equals("/")) {
				scriptValue = executeScript(response, "invalid_path", "" , useExternalServer, imageDomain, imageEditorFlag);
				out.println(scriptValue);
				return;
			}
		}
	} else {
		if (defaultUPath.length() > 7) {
			if (defaultUPath.substring(0, 7).equals("http://")) {
				imageTemp = defaultUPath.substring(7);
				imageUPath = imageTemp.substring(imageTemp.indexOf("/"));
			}
			else if (defaultUPath.substring(0, 8).equals("https://")) {
				imageTemp = defaultUPath.substring(8);
				imageUPath = imageTemp.substring(imageTemp.indexOf("/"));
			} else if (defaultUPath.substring(0, 1).equals("/"))
				imageUPath = defaultUPath;
			else {
				scriptValue = executeScript(response, "invalid_path", "" , useExternalServer, imageDomain, imageEditorFlag);
				out.println(scriptValue);
				return;
			}
		} else {
			if (defaultUPath.substring(0, 1).equals("/"))
				imageUPath = defaultUPath;
			else {
				scriptValue = executeScript(response, "invalid_path", "", useExternalServer, imageDomain, imageEditorFlag);
				out.println(scriptValue);
				return;
			}
		}
	}

	if (imageUPath.lastIndexOf("/") != imageUPath.length() - 1)
		imageUPath = imageUPath + "/";

	if (imagePhysicalPath.equals("")) {
		String DompaserValue = Dompaser(imageUPath);
		if (DompaserValue.equals("")) {
			imagePhysicalPath = context.getRealPath(imageUPath);
			if (!ContextPath.equals("") && !ContextPath.equals("/")) {
				File tempFileRealDIR = new File(imagePhysicalPath);
				if (!tempFileRealDIR.exists()){
					if (imageUPath.indexOf(ContextPath) != -1)
						imagePhysicalPath = context.getRealPath(imageUPath.substring(ContextPath.length()));
				}
			}
		}
		else
			imagePhysicalPath = DompaserValue;
	}
		
	File fileRealFolderWriteCheck = new File(imagePhysicalPath);
	if (!fileRealFolderWriteCheck.exists()) {
		scriptValue = executeScript(response, "invalid_path", "" , useExternalServer, imageDomain, imageEditorFlag);
		out.println(scriptValue);
		out.close();
		return;
	}
	if (!fileRealFolderWriteCheck.canWrite()) {
		scriptValue = executeScript(response, "canWriteErr", imagePhysicalPath, useExternalServer, imageDomain, imageEditorFlag);
		out.println(scriptValue);
		out.close();
		return;
	}

	if (imagePhysicalPath.lastIndexOf(File.separator) != imagePhysicalPath.length() - 1)
		imagePhysicalPath += File.separator;

	String imagePhysicalPathsubFolder = imagePhysicalPath;
	File SaveSubFolder = new File(imagePhysicalPathsubFolder + "upload");
	if(!SaveSubFolder.exists())
		SaveSubFolder.mkdir();
	imagePhysicalPathsubFolder += "upload" + File.separator;
	
	try {
		String tempFileFolder = "";

		if (uploadFileSubDir.equals("false") && !imageUPath.equals(""))
			tempFileFolder = tempFolderCreate(imagePhysicalPath);
		else
			tempFileFolder = imagePhysicalPath;
					
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			String realDir = imagePhysicalPathsubFolder;
			DiskFileItemFactory factory = new DiskFileItemFactory();                                   
			factory.setSizeThreshold(2 * 1024 * 1024);     //10메가가 넘지 않으면 메모리에서 바로 꺼내온다.
			ServletFileUpload upload = new ServletFileUpload(factory);                               
			upload.setSizeMax(maxSize); 
			upload.setHeaderEncoding("utf-8");
			List items = upload.parseRequest(request);       
			Iterator iter=items.iterator();                                                                            

			
			String imageMaxCount = "";
			String imageTitle = "";
			String imageWidth = "";
			String imageWidthUnit ="";
			String imageHeight = "";
			String imageHeightUnit = ""; 
			String imageAlign = "";
			String imageId = "";
			String imageClass = "";
			String imageBorder = "";
			String imageKind = "";
			String imageTempFName = "";
			String imageUNameType = "";
			String imageUNameEncode = "";
			String imageViewerPlay = "";
			String imageOrgPath = "";
			String editorFrame = "";
			String filename = "";
			String type = "";

			while(iter.hasNext()){
				FileItem fileItem = (FileItem) iter.next();    
				if(fileItem.isFormField()){          
						if( fileItem.getFieldName().equals("imageMaxCount") ) imageMaxCount =  toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageTitle") ) imageTitle = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageWidth") ) imageWidth = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageWidthUnit") ) imageWidthUnit = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageHeight") ) imageHeight = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageHeightUnit") ) imageHeightUnit = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageAlign") ) imageAlign = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageId") ) imageId = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageClass") ) imageClass = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageBorder") ) imageBorder = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageKind") ) imageKind = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageTempFName") ) imageTempFName = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageUNameType") ) imageUNameType = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageUNameEncode") ) imageUNameEncode = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageViewerPlay") ) imageViewerPlay = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("imageOrgPath") ) imageOrgPath = toString(fileItem.getString("utf-8"));
						if( fileItem.getFieldName().equals("editorFrame") ) editorFrame = toString(fileItem.getString("utf-8"));
													
				} else {  //파일이면 이부분의 루틴을 탄다
					if(fileItem.getSize()>0) {   //파일이 업로드 되었나 안되었나 체크 size>0이면 업로드 성공
						filename = fileItem.getName();
						if (filename.lastIndexOf("\\") != -1) {
							filename = filename.substring(filename.lastIndexOf("\\")+1, filename.length());
						}
						type = fileItem.getContentType();
						try{
							File uploadedFile=new File(realDir,filename); //실제 디렉토리에 fileName으로 카피 된다.
							fileItem.write(uploadedFile);
							fileItem.delete(); //카피 완료후 temp폴더의 temp파일을 제거
						}catch(IOException ex) {} 
					}
				}
			}
			String fileTempName = "";
			String imageKindSubFolder = ""; 

			if (imageKind.toLowerCase().indexOf("flash") != -1)
				imageKindSubFolder = "flashes"; 	
			else if (imageKind.toLowerCase().indexOf("image") != -1)
				imageKindSubFolder = "images"; 	
			else
				imageKindSubFolder = "movies"; //imageKindSubFolder 서브 폴더 이름을 설정해 준다.
			if (imageUNameType.equals("real")) 
				fileTempName = filename.substring(0, filename.lastIndexOf("."));
			else if(imageUNameType.equals("random"))
				fileTempName = fileNameTimeSetting();
			else {
				fileTempName = imageTempFName;
				if (fileTempName.indexOf("/") != -1)
					fileTempName = fileTempName.replaceAll("/", "==NamOSeSlaSH=="); //fileTempName 파일 이름만 나타낸다.
			}
			String realFileName = fileTempName.replace(' ', '_');
			String fileCheck =filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();	//파일 형식
			String typeCheck = type.substring(0,type.indexOf("/")); //파일 종류(image)
			
			if (!isImageValid(imageKind, fileCheck)) {
				if(uploadFileSubDir.equals("false") && !imageUPath.equals(""))
					tempFolderDelete(tempFileFolder);

				scriptValue = executeScript(response, "invalid_image", getImageKind(imageKind), useExternalServer, imageDomain, imageEditorFlag);
				out.println(scriptValue);
				return;
			}
			if(uploadFileSubDir.equals("false")) { 
				if(imageUPath.equals("")) {
					File imageSaveSubFolder = new File(imagePhysicalPath + imageKindSubFolder);
					if(!imageSaveSubFolder.exists())
						imageSaveSubFolder.mkdir();
					imagePhysicalPath += imageKindSubFolder + File.separator;
				}
			} else {
				File imageSaveSubFolder = new File(imagePhysicalPath + imageKindSubFolder);
				if(!imageSaveSubFolder.exists())
					imageSaveSubFolder.mkdir();
				imagePhysicalPath += imageKindSubFolder + File.separator;

				saveFolder = getChildDirectory(imagePhysicalPath, imageMaxCount); 
				
				if (saveFolder.equals("")) {	
					if(uploadFileSubDir.equals("false") && !imageUPath.equals(""))
						tempFolderDelete(tempFileFolder);

					scriptValue = executeScript(response, "invalid_path", "", useExternalServer, imageDomain, imageEditorFlag);
					out.println(scriptValue);
					return;
				} else
					imagePhysicalPath += saveFolder;	
			}
			String filenamecheck = checkFileUniqueName(realFileName, imagePhysicalPath, fileCheck);	//이미지 이름과 형식까지 붙어서 결과값 배출

			String imgLinkParams = "";
			String urlFilePath = imageUPathHost + imageUPath;

			if(uploadFileSubDir.equals("false")) {
				if(imageUPath.equals(""))
					urlFilePath += imageKindSubFolder + File.separator;
			} else
				urlFilePath += imageKindSubFolder + File.separator + saveFolder + File.separator;
			urlFilePath = urlFilePath.replace('\\', '/');

			if (imageViewerPlay.equals("true")) {
				String curUrlPath = request.getRequestURI();


				curUrlPath = curUrlPath.substring(0, curUrlPath.lastIndexOf("/"));
				String imgLinkPathRename = imageUPathHost + curUrlPath + "/ImageViewer.jsp?imagesrc=";
				
				if (imageUNameType.equals("real")) {
					String enFileName = filenamecheck.substring(0, filenamecheck.lastIndexOf("."));
					String enFileExt = filenamecheck.substring(filenamecheck.lastIndexOf("."));
					sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
					byte[] keyByte = enFileName.getBytes("utf-8");
					imgLinkParams = URLEncoder.encode(urlFilePath + encoder.encode(keyByte).replaceAll("/", "==NamOSeSlaSH==") + enFileExt + "|" + imageUNameType);
					urlFilePath = imgLinkPathRename + imgLinkParams;
				} else {
					imgLinkParams = URLEncoder.encode(urlFilePath + filenamecheck + "|" + imageUNameType);
					urlFilePath = imgLinkPathRename + imgLinkParams;
				}
			} else {
				urlFilePath += filenamecheck;
				imgLinkParams = urlFilePath; //전체 이름붙은 이미지 경로 출력
			}
			if (imageOrgPath != null && !imageOrgPath.equals(""))
				imageOrgPath += "|" + urlFilePath;
	
			if (imageTitle == null)
				imageTitle ="";
			if (imageWidth == null)
				imageWidth ="";
			if (imageWidthUnit == null)
				imageWidthUnit = "";
			if (imageHeight == null)
				imageHeight ="";
			if (imageHeightUnit == null)
				imageHeightUnit = "";
			if (imageAlign == null)
				imageAlign ="";
			if (imageId == null)
				imageId ="";
			if (imageClass == null)
				imageClass = "";
			if (imageBorder == null)
				imageBorder ="";
			if (imageOrgPath == null)
				imageOrgPath ="";
			if (editorFrame == null)
				editorFrame ="";


			returnParam = "{";
			returnParam += "\"imageURL\":\"" + urlFilePath.replaceAll("'", "\\\\\"") + "\",";
			returnParam += "\"imageTitle\":\"" + imageTitle + "\",";
			returnParam += "\"imageWidth\":\"" + imageWidth + "\",";
			returnParam += "\"imageWidthUnit\":\"" + imageWidthUnit + "\",";
			returnParam += "\"imageHeight\":\"" + imageHeight + "\",";
			returnParam += "\"imageHeightUnit\":\"" + imageHeightUnit + "\",";
			returnParam += "\"imageAlign\":\"" + imageAlign + "\",";
			returnParam += "\"imageId\":\"" + imageId + "\",";
			returnParam += "\"imageClass\":\"" + imageClass + "\",";
			returnParam += "\"imageBorder\":\"" + imageBorder + "\",";
			returnParam += "\"imageKind\":\"" + imageKind + "\",";
			returnParam += "\"imageOrgPath\":\"" + imageOrgPath + "\",";
			if(imageKind.equals("image")) {
				int oriWidth = 0;
				int oriHeight = 0;
				try {
					//2012.06.05 [2.0.4.16->2.0.4.17] nkpark heap memory 문제 해결
					File oriObj = new File(imagePhysicalPathsubFolder + filename);
					Image img = new ImageIcon(imagePhysicalPathsubFolder + filename).getImage();
					oriWidth = img.getWidth(null);
					oriHeight = img.getHeight(null);
				} catch(Exception e) {}
				
				returnParam += "\"imageOrgWidth\":\"" + oriWidth + "\",";
				returnParam += "\"imageOrgHeight\":\"" + oriHeight + "\",";
			}
			if (imageModify.equals("true"))
				returnParam += "\"imageModify\":\"true\",";
			returnParam += "\"editorFrame\":\"" + editorFrame + "\"";
			returnParam += "}";	
			
			String moveFilePath = imagePhysicalPath + File.separator + filenamecheck;
			int check = fileCopy(imagePhysicalPathsubFolder + filename, moveFilePath);

			if (check == 1) {
				if(uploadFileSubDir.equals("false") && !imageUPath.equals(""))
					tempFolderDelete(tempFileFolder);

				if (imageEditorFlag.equals("flashPhoto")) {
					scriptValue = "{";
					scriptValue += "\"result\":\"success\",";
					scriptValue += "\"imageURL\":\"" + urlFilePath + "\",";
					scriptValue += "\"addmsg\":" + returnParam;
					scriptValue += "}";
				} else
					scriptValue = executeScript(response, "success", returnParam, useExternalServer, imageDomain, imageEditorFlag);

				out.println(scriptValue);
				return;
			} else {
				if(uploadFileSubDir.equals("false") && !imageUPath.equals(""))
					tempFolderDelete(tempFileFolder);

				scriptValue = executeScript(response, "fileCopyFail", "", useExternalServer, imageDomain, imageEditorFlag);	
				out.println(scriptValue);
				return;			
			}
		}else{
				out.println("인코딩 타입이 multipart/form-data 가 아님.");
		}
	} catch (IOException ioe) {
		scriptValue = executeScript(response, "invalid_size", Integer.toString(maxSize), useExternalServer, imageDomain, imageEditorFlag);
		
		out.println(scriptValue);
		return;
	} catch (org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException e) {
		scriptValue = executeScript(response, "invalid_size", Integer.toString(maxSize), useExternalServer, imageDomain, imageEditorFlag);

        out.println(scriptValue);
		return;
    } catch (Exception e) {	
		String messageText = e.getMessage();
		messageText = "<System Error>" + messageText;
		
		scriptValue = executeScript(response, "", messageText, useExternalServer, imageDomain, imageEditorFlag);
		out.println(scriptValue);
		return;
	}

%>
