	/*var cmode = 'add';*/
	var tree_draggable = false;
	var _treeId 	= "unitTree";

	
 	//분류체계등록화면
	/*function fncCreateForm() {
    	cmode = 'add';
    	var t = $.tree.reference($('#'+_treeId));

    	if(t.selected) {
			Resource.reset();
			$('#tree_form #__resource_input').val('');
			
    		var pos = $("#"+_treeId).offset();
    		
    		//수정화면 버튼 '수정' -> '등록'으로 변경
    		$("a#saveBtn").text(registBtn); 
    		
    		$('#'+treeDivId).dialog({
    			position:[pos.left+450,pos.top+100],
    			width:300,
    			height:100,
    			title: addTitle
    		});
    		$('#'+treeDivId).dialog('open');
    	} else {
        	alert(selectUnit);
    	}
	}
	
    // 분류체계수정화면
    function fncModifyForm() {
    	cmode = 'mod';
    	var t = $.tree.reference($('#'+_treeId));
    	
    	if(t.selected) {
    		var unitId		= t.selected.attr("id");
    		var unitNameId	= t.selected.attr("nameId");
    		
        	if(unitId == "ROOT") {
            	alert(modUnit);
            	return;
        	}
        	
    		var pos = $("#"+_treeId).offset();
    		
    		//수정화면 분류체계명 setting
    		Resource.setResourceId(unitNameId);
    		
    		//수정화면 버튼 '등록' -> '수정'으로 변경
    		$("a#saveBtn").text(modBtn); 
    		
    		$('#'+treeDivId).dialog({
    			position:[pos.left+450,pos.top+100],
    			width:300,
    			height:100,
    			title: modTitle
    		});
    		$('#'+treeDivId).dialog('open');
    	} else {
        	alert(selectUnit);
    	}
    }
    
    //분류체계 삭제버튼 클릭
    function fncDelete() {
    	var t = $.tree.reference($('#'+_treeId));
    	var unitId = '';

    	if(t.selected) {
    		unitId = t.selected.attr("id");
    	} else {
        	alert(selectUnit);
        	return;
    	}
    	
    	if(unitId == 'ROOT') {
        	alert(delUnit);
        	return;
    	}
    	
		if(confirm(confirmDelete)) {
			selectUnit(unitId);
    		$.tree.focused().remove();
		}
    }*/
    
    //저장버튼클릭
    function fncUnitTreeAdd(unitId, unitName) {
    	var t			= $.tree.reference($('#'+_treeId));
		t.create( { attributes:{'id':unitId}, data:{title:unitName} } );
		$('#'+unitId+' a').first().attr('href','#');
		$('#'+unitId+' a').first().attr('class','clicked');
		$('#'+unitId+' a').first().removeAttr('style');
		$('#'+unitId+' ins').first().text('');
    }
	
	// 분류체계 수정
	function fncUnitUpdate(unitId, unitName) {
		$('#' + unitId + ' a:first').html("<ins></ins>" + unitName);
	}
	
    
    //분류체계선택
    function selectUnit(unitId) {
    	try{
    		$.tree.reference($('#'+_treeId)).select_branch("#"+unitId);
    	} catch(e) { }
    }
    
	// 선택된 분류체계명을 가져온다.
	function getUnitName(unitId) {
		return $('#'+unitId+' a').first().text();
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
						selectUnitCallback($(node).attr('id'), $(node).attr('unitName'), $(node).attr('unitType')
							, $(node).attr('unitOrder'), $(node).attr('unitDepth'), $(node).attr('parentId')
							, $(node).attr('isChildUnit'));
					} catch(e) {
						selectUnitCallback($(node).attr('id'), $(node).attr('unitName'), $(node).attr('unitType')
							, $(node).attr('unitOrder'), $(node).attr('unitDepth'), $(node).attr('parentId')
							, $(node).attr('isChildUnit'));
					}
				},
				onopen: function(node, tree_obj) {
					try {
						openCallback($(node).attr('id'));
					} catch(e) {
					}
				}/*,
				// 분류체계생성
				oncreate : function(node, parent, type, tree_obj, rollback) {
					var unitId 		= $(node).attr('id');
					var parentUnitId	= tree_obj.parent(node).attr('id');

					var params = 'id.unitId=' + unitId + '&parentUnitId='+parentUnitId 
								+'&resourceNameAll=' + encodeURIComponent(Resource.getResourceNameAll())
								+'&resourceType=' + Resource.getResourceType();

					$.post(context_root+'/rsc/rscUnit.do?method=create', params, function(data) {	
						fncClose();
						try {
					        if(data.result == 0) {
					        	alert(errorMsg);
					        	$.tree.rollback(rollback);
					        	return false;
					        }
					        //parentid setting
					        $(node).attr('parentid', parentUnitId);
					        
					        //nameId setting
					        if(data.resource != null) {
					        	$(node).attr('nameId', data.resource.id.resourceId);
					        }
					        
							insertCallBack(unitId);
						} catch(e) {}
					});
				},
				// 분류체계삭제
				ondelete : function(node, tree_obj, rollback) {
					var unitId 		= $(node).attr('id');
					var params 			= "id.unitId=" + unitId;

					$.post(context_root+'/rsc/rscUnit.do?method=remove', params, function(data) {	
						try {
					        if(data.result == 0) {
					        	alert(errorMsg);
					        	$.tree.rollback(rollback);
					        	return false;
					        }
							deleteCallBack(unitId);
						} catch(e) {}
					});
				}*/
			}
        });
	}
	
    $(document).ready(function() {
    	drawTree(_treeId);
    	//if(selectUnitId=='ROOT')
    		$.tree.focused().close_all("#ROOT");
    	//else $.tree.focused().open_all("#ROOT");
    	selectUnit(selectUnitId);
    });