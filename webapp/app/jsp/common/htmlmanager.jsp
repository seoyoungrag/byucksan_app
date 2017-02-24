<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%
	// 결재 회사 이름
	String appCompName = (String) session.getAttribute("COMP_NAME");

	// 서버 임시 업로드 폴더
	String appUploadTempPath = AppConfig.getProperty("upload_temp", "", "attach") + "/" + session.getAttribute("COMP_ID") + "/";

%>

<script type="text/javascript">
<!--

	/* ---------- 결재라인 정보 ---------- 
		lineOrder : 1
		lineSubOrder : 1
		approverId : Uaac18b23e4f402fffb8
		approverName : xygate
		approverPos : 팀장
		approverDeptId : 1000011
		approverDeptName : 대표문서과
		representativeId : 
		representativeName : 
		representativePos : 
		representativeDeptId : 
		representativeDeptName : 
		askType : ART010
		procType : 
		absentReason : 
		editBodyYn : N
		editAttachYn : N
		editLineYn : N
		mobileYn : N
		procOpinion : 
		signFileName : 15BD19E7108A113E014A3E37FFFF803A.jpg
		readDate : 
		processDate : 
		lineHisId : 
		fileHisId : 
		bodyHisId : 
		approverRole : 
		changeYn : 1
		lineNum : undefined	
	*/

	// 문서 수행 상태 (isnert:등록, view:보기)
	var AppLineStatus = "";

	// 결재문서 상태 (APP100 : 기안대기, APP500 : 결재대기)
	var AppLineDocStatus = "";
	
	// 결재라인 정보
	var AppLineArray = [];
	var AppLineArrayCount = 0; 
	var AppLineDataList = "";

	// 임시저장시 제목
	var temporaryTitle = "";		

	// 서버 임시 업로드 폴더
	var appUploadTempPath = "<%= appUploadTempPath %>";

	// 수정이 완료되었는지 판단
	var completeEditOK = false;

	// HTML 편집기에서 삭제, 이동할 수 있는 결재 타입  
	/*
		ART010 : 기안
		ART050 : 결재
		ART130 : 개인순차합의
		ART131 : 개인병렬합의
		ART132 : 부서합의
		ART055 : 통보
	*/ 	
	var AppLineTypes = [ "ART010", "ART050", "ART130", "ART131", "ART132", "ART055" ];

	//===============================================================
	//- 기능 : InsertAppDoc.jsp 파일에서 처음 시작할때 loadInsertDocument 메소드를 호출한다.
	//- 인자 : apprline -> 결재정보
	//===============================================================
	function loadInsertDocument(editBodyYn, apprline, yn) {
		AppLineStatus = "insert";
		
		AppLineDataList = apprline;

		var bodyFileName = document.getElementById("bodyFileName").value;
		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveForm.jsp?bodyFileName=" + bodyFileName + "&status=insert";
	}

	//===============================================================
	//- 기능 : 결재라인 정보에서 기안자를 결재라인란에 추가한다.
	// HtmlApproveForm.jsp ifrmae -> onload 이벤트에서 호출됨
	//===============================================================	
	function loadIFrameHTML() {
		AppLineArray = [];
		AppLineArrayCount = 0; 

		var line = getApproverList(AppLineDataList);	// approvalmanager.jsp 
		var lineCount = getAppLineCount(line);
		
		// 기안(상신) 결재라인 정보 추가
		if (lineCount > 0) {

			if (AppLineStatus == "change" || AppLineStatus == "temporary") {
				AppLineStatus = "insert";
			}

			for (var i = 0; i < lineCount; i++) {
				appendAppLineOption(line[i]);
				arrayAddByIndex(AppLineArray, AppLineArrayCount++, line[i]);
			}

			// 전체 결재경로 정보를  appLine 태그에 설정한다.
			$("#appLine", "#approvalitem" + getHTMLCurrentItem()).val(makeAppLineList());
		}

		// 제목 설정
		putHtmlTitleText(temporaryTitle);
	}

	//===============================================================
	//- 기능 : SELECT 태그에 OPTION에 결재경로 값을 추가한다.
	//- 인자 : line -> 결재경로 정보 (숫자 / 결재구분 / 이름 / 직위 / 소속 / 회사이름)
	//-     app_type : 3 
	//===============================================================
	function appendAppLineOption(line) {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;
			
		var objSelectLine = frameDoc.getElementById('selectline');
		var objOption = document.createElement('OPTION');

		objOption.setAttribute("askType", line.askType);
		objOption.setAttribute("approverId", line.approverId);
		objOption.setAttribute("approverName", line.approverName);
		objOption.setAttribute("approverPos", line.approverPos);
		objOption.setAttribute("approverDeptName", line.approverDeptName);

		var userCount = 0;
		if (line.askType != "ART010") {
			if (isAppLineTypes(line.askType)) {
				var curUserCount = getApprovalUserCount(line.askType);
				userCount = parseInt(curUserCount) + 1;

				setApprovalUserCount(line.askType, userCount);
			}
		}
				
		// 결재라인 정보 구성 (숫자 / 결재구분 / 이름 / 직위 / 소속 / 회사이름)
		var lineText = AppLineArrayCount + ' / ' + typeOfApp(line.askType) + ' / ' + line.approverName + ' / ' + line.approverPos + ' / ' + line.approverDeptName + ' / ' + '<%= appCompName %>';

		objOption.text = lineText;
		objSelectLine.add(objOption);
	}

	//===============================================================
	//- 기능 : SELECT 태그에 OPTION (결재경로)을 모두 삭제한다.
	//===============================================================
	function clearAllAppLineOption() {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;
	
		var objSelectLine = frameDoc.getElementById('selectline');
		var optionCount = objSelectLine.options.length;
				
	    for (var i = optionCount; i > 0; i--) {
	        var objOriOption = objSelectLine.options[i - 1];
	        objSelectLine.removeChild(objOriOption);
	    }
	}	

	//===============================================================
	//- 기능 : SELECT 태그에 OPTION에 결재경로 값을 설정한다.
	//- ApprovalLinePop.jsp에서 '확인' 버튼시 호출된다.
	//===============================================================
	function setApprLineForHTML(appline, yn) {
		var line = getApproverList(appline);
		var lineCount = getAppLineCount(line);

		if (lineCount > 0) {
			// 보기 및 수정(결재시)일경우에는 결재경로가 없으므로 아래 코드를 수행하지 않는다.
			if ((AppLineStatus != "view") && (AppLineStatus != "edit")) {
				// 결재문서 상태가 기안대기(APP100)가 아닐경우
				if (AppLineDocStatus != "APP100") {
					clearAllAppLineOption();
					initializeApprovalUserCount();
				}
				
				AppLineArray = [];
				AppLineArrayCount = 0; 			
	
				for (var i = 0; i < lineCount; i++) {
					// 결재문서 상태가 기안대기(APP100)가 아닐경우
					if (AppLineDocStatus != "APP100") {
						appendAppLineOption(line[i]);
						arrayAddByIndex(AppLineArray, AppLineArrayCount++, line[i]);
					}
				}
			}

			// 전체 결재경로 정보를  appLine 태그에 설정한다.
			$("#appLine", "#approvalitem" + getHTMLCurrentItem()).val(appline);
		}
	}

	//===============================================================
	//- 기능 : SELECT 태그에 OPTION에 있는 결재경로 값을 삭제한다.
	//===============================================================
	function deleteOption() {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;

		var objSelectLine = frameDoc.getElementById('selectline');

		if (objSelectLine.selectedIndex == -1) {
			// alert("삭제할 결재자가 선택되지 않았습니다.");
			alert("<spring:message code='approval.msg.noSelectApprover'/>");
			return;
		} else if (objSelectLine.selectedIndex == 0) {
			// alert("기안자는 삭제할 수 없습니다.");
			alert("<spring:message code='approval.msg.applines.cannotsubmiter'/>");
			return;
		}

		var deleteFlag = false;
		var optionCount = objSelectLine.options.length;
		
	    for (var i = optionCount; i > 0; i--) {
		    var pos = i - 1;
	    	if (objSelectLine.options[pos].selected) {
	    		var objOption = objSelectLine.options[pos];

	    		// 결재경로에 있는 숫자를 하나 감소한다.
	    		if (isAppLineTypes(objOption.askType)) {
					var curUserCount = getApprovalUserCount(objOption.askType);
					setApprovalUserCount(objOption.askType, parseInt(curUserCount) - 1);
	    		}
	    		
				// 현재 선택된 경로에 있는 정보를 배열에서 삭제한다.
				arrayRemoveByIndex(AppLineArray, pos);
				AppLineArrayCount--;

	        	objSelectLine.removeChild(objOption);

	        	deleteFlag = true;
	    	}
	    }

	    // 결재경로 갱신
	    if (deleteFlag) {
			// 전체 결재경로 정보를  appLine 태그에 설정한다.
			$("#appLine", "#approvalitem" + getHTMLCurrentItem()).val(makeAppLineList());
			refreshOption();
	    }
	}

	//===============================================================
	//- 기능 : OPTION에 있는 결재경로 순서를 다시 갱신하여 보여준다.
	//===============================================================
	function refreshOption() {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;

		var objSelectLine = frameDoc.getElementById('selectline');
		
		for (var i = 0; i < AppLineArrayCount; i++) {
			var line = AppLineArray[i];

			var optionText = i  + ' / ' + typeOfApp(line.askType) + ' / ' + line.approverName + ' / ' + line.approverPos + ' / ' + line.approverDeptName + ' / ' + '<%= appCompName %>';

			objSelectLine.options[i].text = optionText;
		}
	}

	//===============================================================
	//- 기능 : SELECT 태그에 OPTION에 있는 결재경로 값의 순서를 변경한다.
	//===============================================================
	function changeOption(direction) {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;

		var objSelectLine = frameDoc.getElementById('selectline');

		var selectedIndex = objSelectLine.selectedIndex;
		if (selectedIndex == -1) {
			// alert("변경할 결재자가 선택되지 않았습니다.");
			alert("<spring:message code='approval.msg.noChangeSelectApprover'/>");
			return;
		} else if (selectedIndex == 0) {
			// alert("기안자는 변경할 수 없습니다.");
			alert("<spring:message code='approval.msg.noChangeReporter'/>");
			return;
		}

		var optionCount = objSelectLine.options.length;
		if (direction == "up") {
			if (selectedIndex == 1) {
				// alert("더 이상 위로 변경할 수 없습니다.");
				alert("<spring:message code='approval.msg.noMoreUpMove'/>");
				return;
			}

			var objOption = objSelectLine.options[selectedIndex];
			var targetOption = objSelectLine.options[selectedIndex - 1];

			changeOptionData(objOption, targetOption);

			objSelectLine.options[selectedIndex].selected = false;
			objSelectLine.options[selectedIndex - 1].selected = true;
		} else if (direction == "down") {
			if (optionCount == (selectedIndex + 1)) {
				// alert("더 이상 아래로 변경할 수 없습니다.");
				alert("<spring:message code='approval.msg.noMoreUpMove'/>");
				return;
			}

			var objOption = objSelectLine.options[selectedIndex];
			var targetOption = objSelectLine.options[selectedIndex + 1];
			
			changeOptionData(objOption, targetOption);

			objSelectLine.options[selectedIndex].selected = false;
			objSelectLine.options[selectedIndex + 1].selected = true;
		}
	}

	//===============================================================
	//- 기능 : OPTION에 있는 값을 서로 바꾼다.
	//===============================================================
	function changeOptionData(sourceObj, targetObj) {
		var sourceIndex = getMatchAppLineIndex(sourceObj.approverId);
		var targetIndex = getMatchAppLineIndex(targetObj.approverId);
		
		var sourceText = targetIndex  + ' / ' + typeOfApp(sourceObj.askType) + ' / ' + sourceObj.approverName + ' / ' + sourceObj.approverPos + ' / ' + sourceObj.approverDeptName + ' / ' + '<%= appCompName %>';
		var targetText = sourceIndex  + ' / ' + typeOfApp(targetObj.askType) + ' / ' + targetObj.approverName + ' / ' + targetObj.approverPos + ' / ' + targetObj.approverDeptName + ' / ' + '<%= appCompName %>';
			
		changeAppLineArray(sourceIndex, targetIndex);	

		var askType           = sourceObj.askType;
		var approverId        = sourceObj.approverId;
		var approverName      = sourceObj.approverName;
		var approverPos       = sourceObj.approverPos;
		var approverDeptName  = sourceObj.approverDeptName;

		sourceObj.setAttribute("askType", targetObj.askType);
		sourceObj.setAttribute("approverId", targetObj.approverId);
		sourceObj.setAttribute("approverName", targetObj.approverName);
		sourceObj.setAttribute("approverPos", targetObj.approverPos);
		sourceObj.setAttribute("approverDeptName", targetObj.approverDeptName);
		sourceObj.text = targetText;

		targetObj.setAttribute("askType", askType);
		targetObj.setAttribute("approverId", approverId);
		targetObj.setAttribute("approverName", approverName);
		targetObj.setAttribute("approverPos", approverPos);
		targetObj.setAttribute("approverDeptName", approverDeptName);
		targetObj.text = sourceText;
	}

	//===============================================================
	//- 기능 : 전체 결재경로 정보를 저장한 배열에서 결재경로 정보를 바꾼다.
	//===============================================================
	function changeAppLineArray(sourceIndex, targetIndex) {
		var sourceLineInfo = AppLineArray[sourceIndex];
		var targetLineInfo = AppLineArray[targetIndex];

		AppLineArray[sourceIndex] = targetLineInfo;
		AppLineArray[targetIndex] = sourceLineInfo;

		// 전제 결재경로 정보에서 순위를 재설정한다.
		for (var i = 0; i < AppLineArrayCount; i++) {
			AppLineArray[i].lineOrder = i + 1;
		}

		// 전체 결재경로 정보를  appLine 태그에 설정한다.
		$("#appLine", "#approvalitem" + getHTMLCurrentItem()).val(makeAppLineList());
	}

	//===============================================================
	//- 기능 : 결재경로에 있는 결재 숫자를 구한다.
	//- 결재숫자 예 : [0]
	//===============================================================
	function getApprovalUserCount(approvalType) {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;

		var userObj = frameDoc.getElementById(approvalType + '_COUNT');
		var userObjValue = userObj.value;
		userObjValue = userObjValue.substring(1, userObjValue.length - 1);

		return userObjValue;
	}
	
	//===============================================================
	//- 기능 : 결재경로에 있는 결재 숫자를 설정한다.
	//===============================================================
	function setApprovalUserCount(approvalType, userCount) {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;

		var userObj = frameDoc.getElementById(approvalType + '_COUNT');
		userObj.value = "[" + userCount + "]";
	}

	//===============================================================
	//- 기능 : 결재경로에 있는 결재 숫자를 모두 초기화한다.
	//===============================================================
	function initializeApprovalUserCount() {
		setApprovalUserCount("ART050", "0");	// 결재
		setApprovalUserCount("ART130", "0");	// 개인순차합의
		setApprovalUserCount("ART131", "0");	// 개인병렬합의
		setApprovalUserCount("ART132", "0");	// 부서합의
		setApprovalUserCount("ART055", "0");	// 통보
	}

	//===============================================================
	//- 기능 : 결재 타입에 맞는 인덱스 번호를 리턴한다.
	//===============================================================
	function getMatchAppLineIndex(approverId) {
		for (var i = 0; i < AppLineArrayCount; i++) {
			if (AppLineArray[i].approverId == approverId) {
				return i;
			}
		}
	}

	//===============================================================
	//- 기능 : 결재경로 정보를 생성한다.
	//===============================================================
	function makeAppLineList() {
		var strAppline = "";

		for (var i = 0; i < AppLineArrayCount; i++) {
			var line = AppLineArray[i];

			strAppline += line.lineOrder 			+ String.fromCharCode(2) + line.lineSubOrder 			+ String.fromCharCode(2); 
			strAppline += line.approverId 			+ String.fromCharCode(2) + line.approverName 			+ String.fromCharCode(2); 
			strAppline += line.approverPos 			+ String.fromCharCode(2) + line.approverDeptId 			+ String.fromCharCode(2); 
			strAppline += line.approverDeptName 	+ String.fromCharCode(2) + line.representativeId 		+ String.fromCharCode(2); 
			strAppline += line.representativeName 	+ String.fromCharCode(2) + line.representativePos 		+ String.fromCharCode(2); 
			strAppline += line.representativeDeptId + String.fromCharCode(2) + line.representativeDeptName 	+ String.fromCharCode(2); 
			strAppline += line.askType	 			+ String.fromCharCode(2) + line.procType 				+ String.fromCharCode(2);
			strAppline += line.absentReason 		+ String.fromCharCode(2) + line.editBodyYn 				+ String.fromCharCode(2);
			strAppline += line.editAttachYn 		+ String.fromCharCode(2) + line.editLineYn 				+ String.fromCharCode(2);
			strAppline += line.mobileYn 			+ String.fromCharCode(2) + line.procOpinion 			+ String.fromCharCode(2);
			strAppline += line.signFileName			+ String.fromCharCode(2) + line.readDate				+ String.fromCharCode(2); 
			strAppline += line.processDate 			+ String.fromCharCode(2) + line.lineHisId  				+ String.fromCharCode(2);
			strAppline += line.fileHisId 			+ String.fromCharCode(2) + line.bodyHisId  				+ String.fromCharCode(2);
			strAppline += line.approverRole 		+ String.fromCharCode(2);
			strAppline += line.lineNum  			+ String.fromCharCode(4);
		}
		return strAppline;				
	}

   	//===============================================================
   	//- 기능 : 배열의 특정 인덱스에 값 추가
   	//===============================================================
	function arrayAddByIndex(arrayName, arrayIndex, arrayData){ 
		arrayName.splice(arrayIndex, 0, arrayData); 
	}
   	
   	//===============================================================
   	//- 기능 : 배열의 특정 인덱스의 값 삭제
   	//===============================================================
	function arrayRemoveByIndex(arrayName, arrayIndex){ 
		arrayName.splice(arrayIndex, 1); 
	}

   	//===============================================================
   	//- 기능 : 상신할 수 있는 조건이 되는지 검사한다.
   	//        제목 및 결재경로 등을 검사한후에  HTML 본문 내용을 파일로 저장하는 기능을 수행한다.
   	//===============================================================
	function checkSubmitDataHTML(option, status) {
		var itemnum = getHTMLCurrentItem();
		
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;

		// 제목을 구한다.
		var title = "";
		if (status == "insert") {
			title = frameDoc.getElementById('subject').value;
			if (title == "") {
				alert("<spring:message code='approval.msg.notitle'/>");
				insertDocInfo();	// 확인시 opener.setDocInfo 메소드 호출시
				return false;
			} else if (!checkSubmitMaxLength(title, '', 512)) {
				return false;
			} else {
				$("#title", "#approvalitem" + itemnum).val(title);
			}
		}

		// 편철 사용 여부 
		var htmlOpt423 = document.getElementById("htmlOpt423").value;
		if ("Y" == htmlOpt423) {
			if ($("#bindingId", "#approvalitem" + itemnum).val() == "") {
				alert("<spring:message code='approval.msg.nobind'/>");
				insertDocInfo();
				return false;
			}
		}

		// 문서분류체계 사용유무
		var htmlOpt422 = document.getElementById("htmlOpt422").value;
		if ("Y" == htmlOpt422) {
			if ($("#classNumber", "#approvalitem" + itemnum).val() == "") {
				alert("<spring:message code='approval.msg.nomanage.number'/>");
				insertDocInfo();
				return false;
			}
		}

		// alert("status -> " + status + "\n" + "AppLineStatus -> " + AppLineStatus);
		// 제목란에 있는 제목으로 설정한다.
		if(AppLineStatus == "insert") {
			title = getHtmlTitleText();
			$("#title", "#approvalitem" + itemnum).val(title);
		}
		
		if (option == "submit") {
			// 결재경로 검사
			if (arrangeAppline()) {
				if (status == "insert") {
					// 본문 내용을 파일로 저장
					return saveHtmlBodyContent(itemnum);
				}
				return true;	
			} 
		} else {
			if (status == "insert") {
				// 본문 내용을 파일로 저장
				return saveHtmlBodyContent(itemnum);
			}
			return true;	
		}
		return false;
	}

   	//===============================================================
   	//- 기능 : 임시폴더에 HTML 본문 파일을 생성하고 bodyFile 태그에 본문 정보를 설정한다.
   	//===============================================================
	function saveHtmlBodyContent(itemnum) {
		var fileName = "HtmlBody_" + UUID.generate() + ".html";
		var fileServerPath = appUploadTempPath + fileName;
		// var displayName = getHtmlTitleText() + ".html";
		
		// 임시폴더에  HTML 본문 파일을 생성한다.
		try {
			var	returnData = document.getElementById('editHtmlFrame').contentWindow.saveHtmlBodyValue(fileServerPath);

			if (returnData == "false") {
				return false;
			} 
		} catch(e) {
			alert("saveHtmlBodyContent Error : " + e.message);
			return false;
		}

		// 파일을 업로드하고 파일정보를 구성한다.
		var bodyinfo = "" + String.fromCharCode(2) + fileName + String.fromCharCode(2) + fileName + String.fromCharCode(2) + 
				       "AFT001" + String.fromCharCode(2) + FileManager.getfilesize(fileServerPath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
				       "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + fileServerPath + String.fromCharCode(4);

		$("#bodyFile", "#approvalitem" + itemnum).val(bodyinfo);
		// alert("saveHtmlBodyContent 메소드 (bodyinfo) --> " + bodyinfo);

		return true;
	}
   	
   	//===============================================================
   	//- 기능 : 임시폴더에 HTML 모바일본문 파일을 생성한다.
   	//===============================================================
	function saveHtmlDocument(itemnum, fileName) {
		var fileServerPath = appUploadTempPath + fileName;
		
		try {
			var	returnData = document.getElementById('editHtmlFrame').contentWindow.saveHtmlBodyValue(fileServerPath);

			if (returnData == "false") {
				return false;
			} 
			
		} catch(e) {
			alert("saveHtmlDocument Error : " + e.message);
			return false;
		}
		
		var bodyinfo = $("#bodyFile", "#approvalitem" + itemnum).val();
		var bodylist = transferFileList(bodyinfo);
		
		if(bodylist != null && bodylist != "") {
			for(var i = 0; i < bodylist.length; i++) {
				if(bodylist[i].filetype == "AFT001") {
					// 본문파일 정보 Setting
					bodyinfo = bodylist[i].fileid + String.fromCharCode(2) + bodylist[i].filename + String.fromCharCode(2) + bodylist[i].displayname + String.fromCharCode(2) + 
					bodylist[i].filetype + String.fromCharCode(2) + bodylist[i].size + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
				       "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + bodylist[i].localpath + String.fromCharCode(4);
				}
			}
		}
		
		// 본문파일 정보 + 모바일본문파일 정보 Setting
		bodyinfo += "" + String.fromCharCode(2) + fileName + String.fromCharCode(2) + fileName + String.fromCharCode(2) + 
				       "AFT002" + String.fromCharCode(2) + FileManager.getfilesize(fileServerPath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
				       "3" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + fileServerPath + String.fromCharCode(4);
		
		$("#bodyFile", "#approvalitem" + itemnum).val(bodyinfo);

		return true;
	}

   	//===============================================================
   	//- 기능 : 제목란에 입력한 제목을 설정한다.
   	//===============================================================
	function putHtmlTitleText(title) {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;

		if (AppLineStatus == "view" || AppLineStatus == "edit") {
			frameDoc.getElementById('subject').innerHTML = title;
		} else {
			frameDoc.getElementById('subject').value = title;
		}
	}

   	//===============================================================
   	//- 기능 : 제목란에 입력한 제목을 구한다.
   	//===============================================================
	function getHtmlTitleText() {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;

		if (AppLineStatus == "view") {
			return frameDoc.getElementById('subject').innerHTML;
		} else {
			return frameDoc.getElementById('subject').value;
		}
	}	

	//===============================================================
	//- 기능 : 본문 문서를 보여준다.
	//===============================================================	
	function loadSelectDocument(bodyExt, bodyFilePath, docState) {
		completeEditOK = false;
		AppLineStatus = "view";
		AppLineDocStatus = docState;

		var itemnum = getHTMLCurrentItem();
		var title = $("#title", "#approvalitem" + itemnum).val();

		title = encodeURIComponent(title);
		temporaryTitle = title;
		
		var bodyFileName = document.getElementById("bodyFileName").value;
		
		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveViewForm.jsp?bodyFileName=" + bodyFileName + "&title=" + title;
	}

	//===============================================================
	//- 기능 : 인쇄 버튼 기능을 수행한다.
	//===============================================================	
	function printHtmlDocument(appLine) {
		if (typeof appLine === "undefined") { 
			appLine = $("#appLine", "#approvalitem").val();	
		}  

		var appLineHTML = "";
		
		if (appLine != "") { 
			appLineHTML = makeApproveLine(appLine);
		}

		var titleHTML = makeApproveTitle();
		
		try {
			document.getElementById('editHtmlFrame').contentWindow.printHtmlForm(titleHTML + "<br/>" + appLineHTML);
		} catch(e) {
			alert("printHtmlDocument Error : " + e.message);
		}
	}

	//===============================================================
	//- 기능 : 결재경로 정보를 HTML으로 구성한다.
	//===============================================================	
	function makeApproveLine(appLine) {
		var line = getApproverList(appLine);	// approvalmanager.jsp
		var lineCount = getAppLineCount(line);
		
		var appLineData = "";
		for(var i = 0; i < lineCount; i++) {
			appLineData += "<tr style='height:23px'>" +  
					       "<td width='20%' bgcolor='#e0e1dd' style='border-bottom:1px solid #a09181;font-size:12px;text-align:center;'>" + typeOfApp(line[i].askType) + "</td>" +
			               "<td width='80%' bgcolor='#ffffff' style='border-bottom:1px solid #a09181;font-size:12px;text-align:left;'>&nbsp;" + line[i].approverName + "</td>" +
						   "</tr>";
		}		

		var appLineHTML = "<table border='0' cellspacing='0' cellpadding='0' width='100%' style='border-top:1px solid #a09181;border-left:1px solid #a09181;border-right:1px solid #a09181'>" +
 		                  "<tr style='height:23px'>" +  
			              "<td width='100%' colspan='2' style='border-bottom:1px solid #a09181;font-size:12px;text-align:center;'>" + "결재경로" + "</td>" +
		                  "</tr>" +
		                  appLineData +
		                  "</table>";
        return appLineHTML;
	}

	//===============================================================
	//- 기능 : 결재 제목을 HTML으로 구성한다.
	//===============================================================	
	function makeApproveTitle() {
		var titleHTML = "<table border='0' cellspacing='0' cellpadding='0' width='100%' style='border:1px solid #a09181'>" +
                        "<tr style='height:23px'>" +  
	                    "<td width='20%' bgcolor='#e0e1dd' style='font-size:12px;text-align:center;'>" + "제목" + "</td>" +
                        "<td width='80%' bgcolor='#ffffff' style='font-size:12px;text-align:left;'>&nbsp;" + getHtmlTitleText() + "</td>" +
		                "</tr>" + 
		                "</table>";
        return titleHTML;
	}
	
	//===============================================================
	//- 기능 : 본문저장 버튼 기능을 수행한다.
	//===============================================================	
	function downloadHtmlBodyContent(displayFileName, appLine) {
		if (typeof appLine === "undefined") { 
			appLine = $("#appLine", "#approvalitem").val();	
		}  
		
		var appLineHTML = "";
		
		if (appLine != "") { 
			appLineHTML = makeApproveLine(appLine);
		}

		var titleHTML = makeApproveTitle();

		var fileName = "";	
		var fileServerPath = "";
		var returnData = "";
		var htmlFile = new Object();

		// 등록
		if (AppLineStatus == "insert") {
			fileName = "TempBody_" + UUID.generate() + ".html";	
			fileServerPath = appUploadTempPath + fileName;

			// 임시폴더에  HTML 본문 파일을 생성한다.
			try {
				returnData = document.getElementById('editHtmlFrame').contentWindow.downloadHtmlBodyWithAppLine(fileServerPath, titleHTML + "<br/>" + appLineHTML);
				if (returnData == "false") {
					// alert("본문 저장시 에러가 발생했습니다.");
					alert("<spring:message code='approval.msg.errorSaveHTMLEditContent'/>");
					return;
				} 

				htmlFile.filename = fileName;
				htmlFile.displayname = getHtmlTitleText() + ".html";
				
				FileManager.setExtension("html");
				FileManager.savefile(htmlFile);				
			} catch(e) {
				alert("Error [Insert downloadHtmlBodyContent] -> " + e.message);
			}
		}

		// 본문 수정이 된 HTML 문서일경우
		if (AppLineStatus == "edit" && completeEditOK == true) {
			var itemnum = getHTMLCurrentItem();
			var imsiTitle = $("#title", "#approvalitem" + itemnum).val();
			if (imsiTitle == "") { imsiTitle = "No_Title"; }

			fileName = "EditBody_" + UUID.generate() + ".html";	
			fileServerPath = appUploadTempPath + fileName;

			// 임시폴더에  HTML 본문 파일을 생성한다.
			try {
				returnData = document.getElementById('editHtmlFrame').contentWindow.downloadHtmlBodyWithAppLine(fileServerPath, titleHTML + "<br/>" + appLineHTML);
				if (returnData == "false") {
					// alert("본문 저장시 에러가 발생했습니다.");
					alert("<spring:message code='approval.msg.errorSaveHTMLEditContent'/>");
					return;
				} 

				htmlFile.filename = fileName;
				htmlFile.displayname = imsiTitle + ".html";

				FileManager.setExtension("html");
				FileManager.savefile(htmlFile);				
			} catch(e) {
				alert("Error [View downloadHtmlBodyContent] -> " + e.message);
			}

			/*
			var htmlFile = new Object();
			htmlFile.filename = document.getElementById("bodyFileName").value;
			htmlFile.displayname = imsiTitle + ".html";
			
			FileManager.setExtension("html");
			FileManager.savefile(htmlFile);
			*/				
		}
		
		// 보기
		if (AppLineStatus == "view") {
			fileName = "ViewBody_" + UUID.generate() + ".html";	
			fileServerPath = appUploadTempPath + fileName;

			// 임시폴더에  HTML 본문 파일을 생성한다.
			try {
				returnData = document.getElementById('editHtmlFrame').contentWindow.downloadHtmlBodyWithAppLine(fileServerPath, titleHTML + "<br/>" + appLineHTML);
				if (returnData == "false") {
					// alert("본문 저장시 에러가 발생했습니다.");
					alert("<spring:message code='approval.msg.errorSaveHTMLEditContent'/>");
					return;
				} 

				htmlFile.filename = fileName;
				htmlFile.displayname = getHtmlTitleText() + ".html";

				FileManager.setExtension("html");
				FileManager.savefile(htmlFile);				
			} catch(e) {
				alert("Error [View downloadHtmlBodyContent] -> " + e.message);
			}

			/*
			var bodyFileId   = document.getElementById("bodyFileId").value;
			var bodyFileName = document.getElementById("bodyFileName").value;

		 	var attach = new Object();
		    attach.fileid = bodyFileId;
		    attach.filename = bodyFileName;
		    attach.displayname = displayFileName;
		    attach.type = "save";
		    attach.gubun = "";
		    attach.docid = "none";
		 
		    FileManager.download(attach);
		    */
		}
	}

	//===============================================================
	//- 기능 : SelectTemporary.jsp 파일에서 처음 시작할때 loadTemporaryDocument 메소드를 호출한다.
	//- 인자 : apprline -> 결재정보
	//===============================================================
	function loadTemporaryDocument(apprline, tempTitle) {
		AppLineStatus = "temporary";
		
		AppLineDataList = apprline;
		temporaryTitle  = tempTitle;
		
		var bodyFileName = document.getElementById("bodyFileName").value;
		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveForm.jsp?bodyFileName=" + bodyFileName + "&status=insert";
	}

	//===============================================================
	//- 기능 : HTML 본문 내용을 수정할 수 있는 화면으로 전환한다. 
	//===============================================================	
	function startEditHtmlBodyContent() {
		AppLineStatus = "edit";

		var itemnum = getHTMLCurrentItem();
		temporaryTitle = encodeURIComponent($("#title", "#approvalitem" + itemnum).val());

		var bodyFileName = document.getElementById("bodyFileName").value;
		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveForm.jsp?bodyFileName=" + bodyFileName + "&status=edit&title=" + temporaryTitle;
	}

	//===============================================================
	//- 기능 : 수정한 HTML 본문 내용을 저장한다. 
	//===============================================================	
	function saveEditHtmlBodyContent() {
		var fileName = "HtmlBody_" + UUID.generate() + ".html";
		var fileServerPath = appUploadTempPath + fileName;

		// 임시폴더에  HTML 본문 파일을 저장한다.
		try {
			var	returnData = document.getElementById('editHtmlFrame').contentWindow.saveHtmlBodyValue(fileServerPath);

			if (returnData == "false") {
				// alert("본문 저장시 에러가 발생했습니다.");
				alert("<spring:message code='approval.msg.errorSaveHTMLEditContent'/>");
			} else {
				document.getElementById("bodyFileName").value = fileName;
				completeEditOK = true;

				// 파일정보를 구성한다.
				var bodyinfo = "" + String.fromCharCode(2) + fileName + String.fromCharCode(2) + fileName + String.fromCharCode(2) + 
						       "AFT001" + String.fromCharCode(2) + FileManager.getfilesize(fileServerPath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
						       "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + fileServerPath + String.fromCharCode(4);

				$("#bodyFile", "#approvalitem" + itemnum).val(bodyinfo);
			}
		} catch(e) {
			alert("saveEditHtmlBodyContent Error : " + e.message);
		}		
	}

	//===============================================================
	//- 기능 : 관리자 -> 관리자목록 -> 전자결재문서 -> 문서조회 후 HTML 문서에서
	//        '본문수정' 버튼 수행후 '본문수정확인' 버튼을 클릭하면 수정한 HTML 
	//        본문 내용을 저장하고 파일 정보를 리턴한다.
	//- 리턴 값 : 본문 파일 정보 
	//===============================================================	
	function saveEditHtmlBodyContentByAdmin() {
		var fileName = "HtmlBody_" + UUID.generate() + ".html";
		var fileServerPath = appUploadTempPath + fileName;

		// 임시폴더에  HTML 본문 파일을 저장한다.
		try {
			var	returnData = document.getElementById('editHtmlFrame').contentWindow.saveHtmlBodyValue(fileServerPath);

			if (returnData == "false") {
				// alert("본문 저장시 에러가 발생했습니다.");
				alert("<spring:message code='approval.msg.errorSaveHTMLEditContent'/>");
				return "";
			} else {
				document.getElementById("bodyFileName").value = fileName;
				completeEditOK = true;

				// 파일정보를 구성한다.
				var bodyinfo = "" + String.fromCharCode(2) + fileName + String.fromCharCode(2) + fileName + String.fromCharCode(2) + 
						       "AFT001" + String.fromCharCode(2) + FileManager.getfilesize(fileServerPath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
						       "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + fileServerPath + String.fromCharCode(4);

				return bodyinfo;
			}
		} catch(e) {
			alert("saveEditHtmlBodyContentByAdmin Error : " + e.message);
			return "";
		}		
	}

	//===============================================================
	//- 기능 : 현재 HTML 본문 내용을 보여준다.
	//===============================================================	
	function showHtmlBodyContent() {
		if (completeEditOK == true) {
			AppLineStatus = "edit";
		} else {
			AppLineStatus = "view";
		}

		var itemnum = getHTMLCurrentItem();
		var title = $("#title", "#approvalitem" + itemnum).val();
		if (title === undefined) {
			title = $("#title", "#approvalitem").val();	
		}
		
		title = encodeURIComponent(title);
		
		var bodyFileName = document.getElementById("bodyFileName").value;
		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveViewForm.jsp?bodyFileName=" + bodyFileName + "&title=" + title;
	}

	//===============================================================
	//- 기능 : 반려된 문서 -> 재기안 -> 상신에서 호출된다. 
	//===============================================================	
	function startInsertHtmlBodyContent() {
		AppLineStatus = "insert";
		
		var itemnum = getHTMLCurrentItem();
		AppLineDataList = $("#appLine", "#approvalitem" + itemnum).val();

		temporaryTitle = $("#title", "#approvalitem" + itemnum).val();

		var bodyFileName = document.getElementById("bodyFileName").value;
		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveForm.jsp?bodyFileName=" + bodyFileName + "&status=insert";
	}

	//===============================================================
	//- 기능 : 지정 버튼 클릭후 선택한 사용자의 정보를 결재란에 추가한다.
	//===============================================================	
	function setUserApproval(userObj) {
		var searchPopup = true;
		
		if (userObj != null) {
			var userId   = userObj.userId;
			var userName = userObj.userName;
			var deptId   = userObj.deptId;
			var deptName = userObj.deptName;

			if (typeof(userId) == "undefined") {
				userId = userObj.userUID;
				searchPopup = false;
			}

			if (typeof(deptId) == "undefined") {
				deptId = userObj.deptID;
			}
			/*
			alert("1. userId -> " + userId + "\n" +
				  "1. userName -> " + userName + "\n" +
				  "1. deptId -> " + deptId + "\n" +
				  "1. deptName -> " + deptName);	
			*/
			var selectedApprovalType = "";
			try {
				selectedApprovalType = document.getElementById('editHtmlFrame').contentWindow.getRadioValue();
			} catch (e) {
				alert("Error [setUserApproval] : " + e.message);
				return;
			}

			if (selectedApprovalType != "") {

				if (isApprovalUser(userId)) {
					if (searchPopup) {
						return "AddedUser";
					} else {
						alert("<spring:message code='approval.msg.applines.ckchoice'/>");
						return;
					}
				}
				
				var approver = makeApprovalUser(userObj, selectedApprovalType);

				appendAppLineOption(approver);
				arrayAddByIndex(AppLineArray, AppLineArrayCount++, approver);

				// 전체 결재경로 정보를  appLine 태그에 설정한다.
				$("#appLine", "#approvalitem" + getHTMLCurrentItem()).val(makeAppLineList());

				return "NewUser";
			}		
		}
		return "NoUser";
	}

	//===============================================================
	//- 기능 : 결재자란에 이미 사용자가 있는지 검사한다.
	//===============================================================	
	function isApprovalUser(userId) {
		for (var i = 0; i < AppLineArrayCount; i++) {
			var line = AppLineArray[i];

			// alert("userId, line.approverId -> " + userId + " : " + line.approverId + "\n" + "artType, line.askType -> " + artType + " : " + line.askType);
			
			if (line.approverId == userId) {
				return true;
			}
		}
		return false;
	}

	//===============================================================
	//- 기능 : 결재자 정보를 구성한다.
	//===============================================================	
	function makeApprovalUser(userObj, artType) {
		var approver = new Object();

		var userId = userObj.userId;
		var deptId = userObj.deptId;

		if (typeof(userId) == "undefined") {
			userId = userObj.userUID;
		}

		if (typeof(deptId) == "undefined") {
			deptId = userObj.deptID;
		}			
		
		approver.lineOrder = AppLineArrayCount + 1;
		approver.lineSubOrder = 1;
		approver.approverId = userId;
		approver.approverName = userObj.userName;
		approver.approverPos = "";
		approver.approverDeptId = deptId;
		approver.approverDeptName = userObj.deptName;
		approver.representativeId = "";
		approver.representativeName = "";
		approver.representativePos = "";
		approver.representativeDeptId = "";
		approver.representativeDeptName = "";
		approver.askType = artType;
		approver.procType = "";
		approver.absentReason = "";
		approver.editBodyYn = "";
		approver.editAttachYn = "";
		approver.editLineYn = "";
		approver.mobileYn = "";
		approver.procOpinion = "";
		approver.signFileName = "";
		approver.readDate = "";
		approver.processDate = "";
		approver.lineHisId = "";
		approver.fileHisId = "";
		approver.bodyHisId = "";
		approver.approverRole = "";
		approver.lineNum = 1;

		return approver;
	}

	//===============================================================
	//- 기능 : 결재 타입을 변경한다.
	//===============================================================	
	function changeApprovalArtType(approvalType) {
		var iframe = document.getElementById('editHtmlFrame');
		var frameDoc = iframe.contentDocument || iframe.contentWindow.document;

		var objSelectLine = frameDoc.getElementById('selectline');

		if (objSelectLine.selectedIndex == -1) {
			return;
		} else if (objSelectLine.selectedIndex == 0) {
			// alert("기안자는 변경할 수 없습니다.");
			alert("<spring:message code='approval.msg.noChangeReporterApprovalType'/>");
			return;
		}

		var refreshFlag = false;
		var optionCount = objSelectLine.options.length;
		var curUserCount = "";
		
	    for (var i = optionCount; i > 0; i--) {
		    var pos = i - 1;
	    	if (objSelectLine.options[pos].selected) {
	    		var objOption = objSelectLine.options[pos];

				if (approvalType != objOption.askType) {

					if (isAppLineTypes(objOption.askType)) {
						curUserCount = getApprovalUserCount(objOption.askType);
						setApprovalUserCount(objOption.askType, parseInt(curUserCount - 1));
					}

					var matchIndex = getMatchAppLineIndex(objOption.approverId);
					AppLineArray[matchIndex].askType = approvalType;

					curUserCount = getApprovalUserCount(approvalType);
					setApprovalUserCount(approvalType, parseInt(curUserCount) + 1);

					var lineText = (i - 1) + ' / ' + typeOfApp(approvalType) + ' / ' + objOption.approverName + ' / ' + objOption.approverPos + ' / ' + objOption.approverDeptName + ' / ' + '<%= appCompName %>';
					objOption.text = lineText;
					objOption.askType = approvalType;
					
					refreshFlag = true;
				}
	    	}
	    }

	    // 결재경로 갱신
	    if (refreshFlag) {
			// 전체 결재경로 정보를  appLine 태그에 설정한다.
			$("#appLine", "#approvalitem" + getHTMLCurrentItem()).val(makeAppLineList());
	    } 
	}

	//===============================================================
	//- 기능 : 현재 상태를 조회한다.
	//===============================================================	
	function getAppLineStatus() {
		return AppLineStatus;
	}

	//===============================================================
	//- 기능 : 참조기안에서 호출한다.
	//===============================================================
	function loadReferdraftAppHtml(apprline, title) { 
		AppLineStatus = "insert";

		AppLineDataList = apprline;
		temporaryTitle = title;

		var bodyFileName = document.getElementById("bodyFileName").value;
		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveForm.jsp?bodyFileName=" + bodyFileName + "&status=insert";
	}

	//===============================================================
	//- 기능 : 자신의 PC에 있는 기안문으로 변경한다.
	//===============================================================
	function changeHTMLForm(bodyFileName) {
		AppLineStatus = "change";
		
		AppLineDataList = makeAppLineList();
		temporaryTitle = getHtmlTitleText();
		
		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveForm.jsp?bodyFileName=" + bodyFileName + "&status=insert";
	}

	//===============================================================
	//- 기능 : 관리자 -> 관리자목록 -> 전자결재문서 -> 문서조회 후 HTML 문서에서
	//        '본문수정(파일변경) 버튼을 클릭하면 변경할 문서로 변경한다. 
	//- 리턴 값 : 본문 파일 정보 
	//===============================================================	
	function changeHTMLFormByAdmin(bodyFileName) {
		var itemnum = getHTMLCurrentItem();
		var title = $("#title", "#approvalitem" + itemnum).val();

		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveViewForm.jsp?bodyFileName=" + bodyFileName + "&title=" + title;

		// 파일정보를 구성한다.
		var fileServerPath = appUploadTempPath + bodyFileName;
		var bodyinfo = "" + String.fromCharCode(2) + bodyFileName + String.fromCharCode(2) + bodyFileName + String.fromCharCode(2) + 
				       "AFT001" + String.fromCharCode(2) + FileManager.getfilesize(fileServerPath) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + 
				       "1" + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + String.fromCharCode(2) + fileServerPath + String.fromCharCode(4);
		
		return bodyinfo;
	}	

	//===============================================================
	//- 기능 : 현재 안건번호를 구한다.
	//- 중복 : approval.jsp 파일에 getHTMLCurrentItem() 메소드로 정의되어 있고
	//        이 파일에서도 사용한다. 하지만 SenderAppDoc.jsp, EnforceDocument.jsp  
	//        파일에서는  approval.jsp 가 인클루안되어 있어서 HTML 편집기에는 에러가 방생하므로
	//        여기에 다시 중복(복사)해서 하나를 더 생성한다.        
	//===============================================================
	function getHTMLCurrentItem() {
		var itemcount = $("div[name=approvalitem]").length;
		for (var loop = 1; loop <= itemcount; loop++) {
			if ($("#id_tab_bg_" + loop).attr("class") == "tab") {
				return loop;
			}
		}
	
		return 1;
	}

	//===============================================================
	//- 기능 : HTML 편집기에서 보이는 결재 타입인지 검사한다.
	//- 인자 : appLineType -> 결재타입
	//===============================================================
	function isAppLineTypes(appLineType) {
		for (var x = 0; x < AppLineTypes.length; x++) {
			if (appLineType == AppLineTypes[x]) {			
				return true;
			}
		}
		return false;
	}

	//===============================================================
	//- 기능 : 결재경로 팝업창에서 문서 정보를 보여준다.
	//===============================================================	
	function openAppBody(title) {
		AppLineStatus = "view";

		title = encodeURIComponent(title);
		
		var bodyFileName = document.getElementById("bodyFileName").value;
		
		document.getElementById("editHtmlFrame").src = "<%= webUri %>/app/jsp/approval/HtmlApproveViewForm.jsp?bodyFileName=" + bodyFileName + "&title=" + title;
	}	

-->
</script>
<script type="text/javascript" src="<%= webUri %>/app/ref/js/htmlmanager.js"></script>