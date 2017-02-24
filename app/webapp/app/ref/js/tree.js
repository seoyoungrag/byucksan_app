	var cmode = 'add';
	var tree_draggable = false;
	var treeDivId = "treeFormDiv";

	
 	//자원종류등록화면
	function fncCreateForm() {
    	cmode = 'add';
    	var t = $.tree.reference($('#'+_treeId));

    	if(t.selected) {
			Resource.reset();
			$('#tree_form #__resource_input').val('');
			$('#tree_form #accessInfos').val('');
			
			fncViewAuth('view', cmode);
			
    		var pos = $("#"+_treeId).offset();
    		
    		//수정화면 버튼 '수정' -> '등록'으로 변경
    		$("a#saveBtn").text(registBtn); 
    		
    		$('#'+treeDivId).dialog({
    			position:[pos.left+450,pos.top+100],
    			width:300,
    			height:120,
    			title: addTitle
    		});
    		$('#'+treeDivId).dialog('open');
    	} else {
        	alert(selectRscType);
    	}
	}
	
    // 자원종류수정화면
    function fncModifyForm() {
    	cmode = 'mod';
    	var t = $.tree.reference($('#'+_treeId));
    	
    	if(t.selected) {
    		var rscTypeId		= t.selected.attr("id");
    		var rscTypeNameId 	= t.selected.attr("nameId");
    		
        	if(rscTypeId == "ROOT") {
            	alert(modRscType);
            	return;
        	}
        	
    		var pos = $("#"+_treeId).offset();
    		
    		fncViewAuth('view', cmode);
    		
    		//수정화면 자원종류명 setting
    		Resource.setResourceId(rscTypeNameId);
    		
    		//수정화면 버튼 '등록' -> '수정'으로 변경
    		$("a#saveBtn").text(modBtn); 
    		
    		$('#'+treeDivId).dialog({
    			position:[pos.left+450,pos.top+100],
    			width:300,
    			height:120,
    			title: modTitle
    		});
    		$('#'+treeDivId).dialog('open');
    	} else {
        	alert(selectRscType);
    	}
    }
    
    //자원종류 삭제버튼 클릭
    function fncDelete() {
    	var t = $.tree.reference($('#'+_treeId));
    	var rscTypeId = '';
		
    	if(t.selected) {
    		rscTypeId = t.selected.attr("id");
    	} else {
        	alert(selectRscType);
        	return;
    	}
    	
    	if(rscTypeId == 'ROOT') {
        	alert(delRscType);
        	return;
    	}
    	
		if(confirm(confirmDelete)) {
			fncCheckRscType();
		}
    }
	
	//권한설정 popup
	function fncSetAuthView(callback) {
		var accessInfos = $('#tree_form #accessInfos').val();
		var t 			= $.tree.reference($('#'+_treeId));
		var rscTypeId 	= t.selected.attr("id");
		
    	if(rscTypeId == "ROOT" && callback == 'fncSet') {
        	alert(selectRoot);
        	return;
    	}

    	//등록일 경우 아직 ID가 존재하지 않음. 이전에 setting된 값 삭제
    	if(cmode == 'add' && callback == 'fncSetAuth') {
    		rscTypeId = '';
    	}
    	
		$('#searchForm')
		.attr('action', context_root+'/rsc/rscResourceType.do?method=aclSetup')
		.attr('method', 'post');

		util.popup('',{width:800,height:550,popname:'setAuth'});	
		$('#searchForm').attr('target','setAuth');
		$('#searchForm [name="id.resourceId"]').val(rscTypeId);
		$('#searchForm #accessInfos').val(accessInfos);
		$('#searchForm #callback').val(callback);
		$('#searchForm').submit();
	}
	
	//권한 View
	function fncViewAuth(actionType, cmode) {
		var t 			= $.tree.reference($('#'+_treeId));
		var rscTypeId 	= t.selected.attr("id");
		var allAccesses = '';
		
		$('#searchForm [name="id.resourceId"]').val(rscTypeId);
		$('#searchForm #callback').val('fncSetAuth');
		$('#searchForm #actionType').val(actionType);
		
		$.post(context_root+'/rsc/rscResourceType.do?method=aclSetup', $('#searchForm').serialize(), function(data) {

	        if(data.accessInfos == "" || cmode == 'add') {
	        	allAccesses = defaultAcl;
	        }else {
	        	allAccesses = data.accessInfos;
	        }
	        
        	fncSetAuth(allAccesses, '');
        	$('#searchForm #actionType').val('');
		});
	}
	
	//자원종류 등록 Dialog에 권한 setting 
	function fncSetAuth(allAccesses, accesses) {
		//기본값 넣기
		var aclType = "";
		var aclText = "";

		if(allAccesses != '') {
			var allAccessArray = allAccesses.split('^');
			var accessArray = allAccessArray[0].split('|');

			if(accessArray.length == 5) {
				var accessAclType = accessArray[2];
				
			    if (accessAclType == aclTypeGroup) {
			    	aclType = aclMsgGroup;
			    }
			    else if (accessAclType == aclTypeRole) {
			    	aclType = aclMsgRole;
			    }
			    else if (accessAclType == aclTypeDept) {
			    	aclType = aclMsgDept;
			    }
			    else if (accessAclType == aclTypeUser) {
			    	aclType = aclMsgUser;
			    }

			    aclText = accessArray[1] + "(" + aclType + ")";
			    
			    if(allAccessArray.length -1 > 0) {
			    	aclText = aclText + " 외" + (allAccessArray.length -1);
			    }
			}else {
				alert(errorMsg);
			}
		}else {
			alert(errorMsg);
		}
		
		//tooltip 효과
		$('#tree_form #setAuth').attr('title', aclText);
		$('#tree_form #accessInfos').val(allAccesses);
		
		//text길이 체크
		$('#tree_form #setAuth').text(aclText);
	}
	
    //리스트에서 권한설정버튼 클릭 CallBack
    function fncSet(allAccesses, accesses) {
    	var t = $.tree.reference($('#'+_treeId));
    	var rscTypeId 	= t.selected.attr("id");

    	$('#tree_form #accessInfos').val(allAccesses);
    	fncUpdateAuth(rscTypeId);
    }
    
    //저장버튼클릭
    function fncSave() {
    	var t = $.tree.reference($('#'+_treeId));
    	var rscTypeId 	= t.selected.attr("id");

    	if(cmode=='add') {
    		var newTreeId = util.generateId('N'); 
    		t.create( { attributes:{'id':newTreeId}, data:{title:Resource.getDefaultResourceName()} } );
    		$('#'+newTreeId+' a').first().attr('href','#');
    		$('#'+newTreeId+' a').first().attr('class','clicked');
    		$('#'+newTreeId+' a').first().removeAttr('style');
    		$('#'+newTreeId+' ins').first().text('');
    	} else {
    		fncUpdate(rscTypeId);
    	}
    }
	
	// 자원종류 수정
	function fncUpdate(rscTypeId) {
		var resourceNameAll = Resource.getResourceNameAll();
		var accessInfos		= $('#tree_form #accessInfos').val();
		
		if(util.isNullOrEmpty(resourceNameAll)) {
			alert(errorMsg);
			return false;
		}

		var params = 'id.rscTypeId=' + rscTypeId 
					+'&resourceNameAll=' + encodeURIComponent(resourceNameAll)
					+'&resourceType=' + Resource.getResourceType()
					+'&accessInfos=' + accessInfos;

		$.post(context_root+'/rsc/rscResourceType.do?method=update', params, function(data) {
			fncClose();

	        if(data.result == 0) {
	        	alert(errorMsg);
	        	$.tree.rollback(rollback);
	        	return false;
	        }else {
	        	$('#' + rscTypeId + ' a:first').html("<ins></ins>" + Resource.getDefaultResourceName());
	        }
	        
			try {
				updateCallBack(rscTypeId);
			} catch(e) {}
		});
	}
	
	// 권한설정(수정)
	function fncUpdateAuth(rscTypeId) {
		var accessInfos		= $('#tree_form #accessInfos').val();

		var params = 'id.rscTypeId=' + rscTypeId 
					+'&accessInfos=' + accessInfos;

		$.post(context_root+'/rsc/rscResourceType.do?method=updateAuth', params, function(data) {
			fncClose();

	        if(data.result == 0) {
	        	alert(errorMsg);
	        	return false;
	        }
	        
			try {
				aclCallBack(rscTypeId);
			} catch(e) {}
		});
	}
	
    //닫기버튼클릭
    function fncClose() {
    	$('#'+treeDivId).dialog('close');
    }
    
	//
	function fncCheckRscType() {

		var childTreeInfo = fncGetChildTreeInfo();
		
    	if(childTreeInfo.length !=0) {
    		var childRscTypeInfo 	= "";
        	var delim 				= "";
        	
        	for(var i=0; i<childTreeInfo.length; i++) {
        		childRscTypeInfo += delim + childTreeInfo[i].rscTypeId;
        		delim = ",";
            } 
        }

		var params = "rscTypeId=" + childRscTypeInfo;

		$.post(context_root+'/rsc/rscResourceType.do?method=checkRscType', params, function(data) {
	        if(data.result > 0) {
		        alert(includeResource);
	        	return false;
	        }else {
	        	//tree에서 선택한 자원종류 삭제
	    		$.tree.focused().remove();
	        }
		});

	}
    
	//선택한 자원종류의 하위의 정보를 가져온다.
	function fncGetChildTreeInfo() {
		var t			= $.tree.reference($('#'+_treeId));
		var selectId 	= t.selected.attr("id");
		var IdArray 	= new Array();
		var selectData 	= { "rscTypeId" : selectId };
		
		//선택한폴더 배열에 추가
		IdArray.push(selectData);

		$('#'+selectId).find('li').each(function(i){
    		var rscTypeId 	= $(this).attr('id');
			var folderData 	= { "rscTypeId" : rscTypeId };
			
			//하위폴더 배열에 추가
			IdArray.push(folderData);
    	});

		return IdArray;
	}
    
    //자원종류선택
    function selectResourceType(rscTypeId) {
    	try{
    		$.tree.reference($('#'+_treeId)).select_branch("#"+rscTypeId);
    	} catch(e) { }
    }
    
	// 선택된 자원종류명을 가져온다.
	function getResourceTypeName(rscTypeId) {
		return $('#'+rscTypeId+' a').first().text();
	}

	/**
	 * Ajax로 하위 트리 가져올때 가져온 트리노드 Object를 이 함수에 전달해주면 됨
	 * 
	 * @param nodeId 이 nodeId 밑으로 하위 트리가 그려짐
	 * @param nodeObj 그려질 트리 데이터
	 * @return
	 */
	function drawSubTree(nodeId, nodeObj) {
		var t = $.tree.focused();
		for(var i=0; i < nodeObj.length; i++) {
			if(typeof($('#'+nodeObj[i].nodeId).attr('parentId'))!='undefined') {
				continue;
			}
			try {
				t.create({attributes:{'id':nodeObj[i].nodeId, 'parentId':nodeObj[i].parentId, 'depth':nodeObj[i].hasChild, 'orgType':nodeObj[i].nodeType, 'treeNodeName':nodeObj[i].nodeName},
					data:{title:nodeObj[i].nodeName}}, '#'+nodeObj[i].parentId);
			} catch(e) {}
		}
	}

	/**
	 * 실제 트리를 그리는 부분
	 * 
	 * @param treeId
	 * @return
	 */
	function drawTree(treeId) {
		if(treeId) {
			_treeId = treeId;
		}
		
        $("#"+_treeId).tree({
    		ui: {
				theme_name : "default"
			},
			types: {
				'default': {
					draggable : tree_draggable
				}
			},
			// 트리 Action 콜백함수들
			callback: {
				// 트리 선택되었을때 호출
				onselect: function(node, tree_obj) {
					try {
						var parentId = tree_obj.parent(node).attr('id');
						selectCallback($(node).attr('id'));
					} catch(e) {
						selectCallback($(node).attr('id'));
					}
				},
				onopen: function(node, tree_obj) {
					try {
						openCallback($(node).attr('id'));
					} catch(e) {
					}
				},
				// 자원종류생성
				oncreate : function(node, parent, type, tree_obj, rollback) {
					if(util.isNullOrEmpty(Resource.getResourceNameAll())) {
						alert(errorMsg);
						return false;
					}
					
					var rscTypeId 		= $(node).attr('id');
					var parentRscTypeId	= tree_obj.parent(node).attr('id');
					var accessInfos		= $('#tree_form #accessInfos').val();

					var params = 'id.rscTypeId=' + rscTypeId + '&parentRscTypeId='+parentRscTypeId 
								+'&resourceNameAll=' + encodeURIComponent(Resource.getResourceNameAll())
								+'&resourceType=' + Resource.getResourceType()
								+'&accessInfos=' + accessInfos;

					$.post(context_root+'/rsc/rscResourceType.do?method=create', params, function(data) {	
						fncClose();
						try {
					        if(data.result == 0) {
					        	alert(errorMsg);
					        	$.tree.rollback(rollback);
					        	return false;
					        }
					        
					        //parentid setting
					        $(node).attr('parentid', parentRscTypeId);
					        
					        //nameId setting
					        if(data.resource != null) {
					        	$(node).attr('nameId', data.resource.id.resourceId);
					        }
					        
							insertCallBack(rscTypeId);
						} catch(e) {}
					});
				},
				// 자원종류삭제
				ondelete : function(node, tree_obj, rollback) {
					var rscTypeId 	= $(node).attr('id');
			    	var params = "id.rscTypeId=" + rscTypeId;
			    	
					$.post(context_root+'/rsc/rscResourceType.do?method=remove', params, function(data) {	
						try {
					        if(data.result == 0) {
					        	alert(errorMsg);
					        	$.tree.rollback(rollback);
					        	return false;
					        }
							deleteCallBack(rscTypeId);
						} catch(e) {}
					});
				}//close ondelete
			}
        });
	}
	
    $(document).ready(function() {
    	try {
    		$('#'+treeDivId).dialog({autoOpen:false,width:270,height:90});
    	} catch(e) {}

    	drawTree();
    	
    	$.tree.focused().open_all("#ROOT");
    	selectResourceType('ROOT');
    });
