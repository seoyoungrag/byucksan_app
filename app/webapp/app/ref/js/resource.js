    // 다국어 정보(tgw_app_resource)를 등록하고 생성된 resourceId를 다국어 필드에 업데이트할때 필요한 대상 테이블 정보
	var globalUpdateTableMap = {
		"CATEGORY" : {
			"tableName"      : "TGW_CATEGORY",		/* 양식함 관리 테이블                                							*/
			"updateFields"   : "CATEGORY_NAME",		/* 나중에 resourceId 값으로 변경된다. ','로 여러개 설정할 수 있다. 	*/
			"conditionField" : "CATEGORY_ID"		/* where 절에서 필요한 조건 필드 이름      							*/
		},
		"SENDER_TITLE" : {
			"tableName"      : "TGW_SENDER_TITLE",	/* 발신명의 관리 테이블                                							*/
			"updateFields"   : "SENDER_TITLE",		/* 나중에 resourceId 값으로 변경된다. ','로 여러개 설정할 수 있다. 	*/
			"conditionField" : "SENDER_TITLE_ID"	/* where 절에서 필요한 조건 필드 이름      							*/
		},
		"APP_BIND_UNIT" : {
			"tableName"      : "TGW_APP_BIND_UNIT",	/* 단위업무 관리 테이블                                							*/
			"updateFields"   : "UNIT_NAME",			/* 나중에 resourceId 값으로 변경된다. ','로 여러개 설정할 수 있다. 	*/
			"conditionField" : "UNIT_ID"			/* where 절에서 필요한 조건 필드 이름      							*/
		},
		"APP_BIND" : {
			"tableName"      : "TGW_APP_BIND",		/* 편철 관리 테이블                                								*/
			"updateFields"   : "BIND_NAME",			/* 나중에 resourceId 값으로 변경된다. ','로 여러개 설정할 수 있다. 	*/
			"conditionField" : "BIND_ID"			/* where 절에서 필요한 조건 필드 이름      							*/
		},
		"FORM_ENV" : {
			"tableName"      : "TGW_FORM_ENV",		/* 캠페인 관리 테이블                                							*/
			"updateFields"   : "ENV_INFO",			/* 나중에 resourceId 값으로 변경된다. ','로 여러개 설정할 수 있다. 	*/
			"conditionField" : "FORM_ENV_ID"		/* where 절에서 필요한 조건 필드 이름      							*/
		}		
		
    };   
        
	var globalResourceDataArray = [];		// 현재 입력한 다국어 정보를 저장하는 Array(Hash) 객체	
    var globalThisObj = "";					// this 객체를 저장하는 변수
    var globalSelectLanguage = "";			// 현재 선택한 언어를 저장하는 변수
	var globalUpdateTableName = "";			// 다국어의 키인 resourceId 값을 업데이트할 테이블 이름

	//===================================================================================
	//- 기능 : 다국어 입력 창을 보여준다. 
	//-       TGW_APP_OPTION 테이블를 사용하는 화면에서만 사용한다.  
	//-       (관리자 -> 결재옵션 -> 기본옵션, 결재함, 문서대장, 감사옵션, 공람열람옵션)
	//===================================================================================
	function showOptionResource(obj, conditionValue, resourceId, initMsg) {
		globalThisObj = obj;
		
		if(resourceId == '') {
			resourceId = obj.name;
		}		
		
		var param = "?conditionValue=" + conditionValue + "&resourceId=" + resourceId + "&initMsg=" + decodeURIComponent(initMsg) + "&resourceDataYN=NO";
		var idx = getIndexByResourceId(resourceId);
		if (idx > -1) {
			var resourceData = globalResourceDataArray[idx];

			param = "?resourceDataYN=YES";
			for(var key in resourceData) {
				param += "&" + key + "=" + decodeURIComponent(resourceData[key]);
			}
		} 		
		
		var url = "/ep/app/resource/showOptionResource.do" + param;
		openWindow("showResource", url, 250, 180, 'no', 'post', 'no');		
	}
	
	//===================================================================================
	//- 기능 : JSP에서 등록하는 메소드에서 이 메소드를 호출하여 DB에 다국어 정보를 등록한다. 
	//-       TGW_APP_OPTION 테이블를 사용하는 화면에서만 사용한다.  
	//===================================================================================
	function registOptionResource() {
		if (globalResourceDataArray.length > 0) {
			var actionURL = "/ep/app/resource/registOptionResource.do";
			
			$.ajax({
			    url: actionURL, 
			    type: "POST",
			    dataType: "json",
			    data: JSON.stringify(globalResourceDataArray),
			    contentType: "application/text; charset=utf-8",
			    success: function(data) {
				    // alert(data.result + "\n" + data.msg);
			    },
			    error: function(xhr, txt, err) {
					// alert("error");     
			    }   
			});
		
			globalResourceDataArray = [];
		}
	}
	
	//===================================================================================
	//- 기능 : 다국어 입력 창을 보여준다.
	//-       TGW_APP_OPTION 테이블를 사용하지 않는 화면에서만 사용한다.  
	//- 인자 : 1. obj 			-> this 로 무조건 설정
	//        2. tableKind 		-> 테이블 종류, globalUpdateTableMap 객체에 저장된 'OPTION, CATEGORY'
	//                             값중에 하나로 설정한다.
	//        3. updateField 	-> 나중에 resourceId 값으로 변경되는 필드 이름으로 globalUpdateTableMap 객체의 
	//                             updateFields에 있는 값으로 설정한다.
	//   	  4. resourceId    	-> 다국어 리소스 id로 설정한다. 처음 입력할때는 ''로 설정하고, 값이 ''로 설정되면 
	//                             input 태크의 name 필드 값으로 설정된다.
	//        5. initMsg		-> 처음 입력할때 다국어 입력창에 보여줄 문자열이므로 설정할 필요가 없으면 ''로 설정한다.
	//
	//- 예 : showResource(this, 'CATEGORY', 'CATEGORY_NAME', '', '')
	//===================================================================================
	function showResource(obj, tableKind, updateField, resourceId, initMsg) {
		globalThisObj = obj;
		globalUpdateTableName = tableKind;

		if(resourceId == '') {
			resourceId = obj.name;
		}

		var param = "?updateField=" + updateField + "&resourceId=" + resourceId + "&initMsg=" + decodeURIComponent(initMsg) + "&resourceDataYN=NO";
		var idx = getIndexByResourceId(resourceId);
		if (idx > -1) {
			var resourceData = globalResourceDataArray[idx];

			param = "?resourceDataYN=YES";
			for(var key in resourceData) {
				param += "&" + key + "=" + decodeURIComponent(resourceData[key]);
			}
		} 
			
		var url = "/ep/app/resource/showResource.do" + param;
		openWindow("showResource", url, 250, 180, 'no', 'post', 'no');
	}    

	//===================================================================================
	//- 기능 : JSP에서 등록하는 메소드에서 이 메소드를 호출하여 DB에 다국어 정보를 등록한다. 
	//-       TGW_APP_OPTION 테이블를 사용하지 않는 화면에서만 사용한다.  
	//- 인자 : conditionValue -> 다국어 정보(tgw_app_resource)를 등록하고  생성된 resourceId를 다국어 필드에 업데이트할때
	//                          조건 필드에 대힙하는 조건 값이다.
	//                          globalUpdateTableMap 객체의 conditionField 필드에 매칭되는 조건 값으로 설정한다.
	//===================================================================================
	function registResource(conditionValue) {
		if (globalResourceDataArray.length > 0) {
			var resourceData = globalUpdateTableMap[globalUpdateTableName];
			resourceData["conditionValue"] = conditionValue;
			
			globalResourceDataArray.unshift(resourceData);

			var actionURL = "/ep/app/resource/registResource.do";
			
			$.ajax({
			    url: actionURL, 
			    type: "POST",
			    dataType: "json",
			    data: JSON.stringify(globalResourceDataArray),
			    contentType: "application/text; charset=utf-8",
			    success: function(data) {
				    // alert(data.result + "\n" + data.msg);
			    },
			    error: function(xhr, txt, err) {
					// alert("error");     
			    }   
			});
		
			globalResourceDataArray = [];
		}
	}

	//===================================================================================
	//- 기능 : 다국어 정보를 삭제한다. 
	//===================================================================================
	function deleteResource(resourceId) {
		var map = { "resourceId" : resourceId };
		
		var paramData = decodeURIComponent($.param(map)); 
		var actionURL = "/ep/app/resource/deleteResource.do";
		
		$.post(actionURL, paramData, function(data) {
			// alert(data.result + " : " + data.msg);
		}, 'json');
	}
	
	//===================================================================================
	//- 기능 : 다국어 정보 입력창에서 '확인' 버튼 클릭시 호출되며, 기존에 다국어 정보가 존재하면 삭제하고, 
	//        다국어 정보를 저장한다. 
	//===================================================================================
	function addResource(selectLanguage, map) {
		globalSelectLanguage = selectLanguage;
		
		var idx = getIndexByResourceId(map["resourceId"]);

		if (idx > -1) {
			// 기존의 resourceId 값이 존재하면 삭제한다. 
			globalResourceDataArray.splice(idx, 1); 
		}
		
		var copyMap = {};
		for(var key in map) {
			copyMap[key] = map[key];

			if (key == globalSelectLanguage) {
				globalThisObj.value = map[key];
			}
		}	
			
		globalResourceDataArray.push(copyMap);
	}

	//===================================================================================
	//- 기능 : 다국어 정보를 저장하는 배열에 다국어 정보가 존재하는지 확인하여  그 다국어 정보가 저장된 인텍스를 리턴한다.
	//- 결과 : -1 -> 다국어 정보가 존재하지 않는다.
	//        -1 보다 크면 다국어 정보가 존재한다.
	//===================================================================================
	function getIndexByResourceId(resourceId) {
		for (var i = 0; i < globalResourceDataArray.length; i++) {
			var resourceData = globalResourceDataArray[i];
			
			for(var key in resourceData) {
				if (key == "resourceId" && resourceData[key] == resourceId) {
					return i;
				}	
			}
		}
		return -1;
	}	

	//===================================================================================
	//- 기능 : 다국어 정보를 보여준다. (디버깅 함수) 
	//===================================================================================
	function showDebug() {
		for (var i = 0; i < globalResourceDataArray.length; i++) {
			var resourceData = globalResourceDataArray[i];
			
			var msg = "";
			for(var key in resourceData) {
				msg += "['" + key + "'] -> " + resourceData[key] + "\n";
			}
			alert(msg);
		}
	}
	
