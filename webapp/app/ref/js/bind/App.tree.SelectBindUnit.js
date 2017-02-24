/** 단위업무 선택 트리 */
Ext.ns('App');
Ext.ns('App.grid');
Ext.ns('App.menu');
Ext.ns('App.tree');
Ext.ns('App.util');
Ext.ns('App.win');

App.tree.SelectBindUnit = Ext.extend(Ext.tree.TreePanel, {
	border : true,
	rootId : '*',
	rootText : 'root',
	rootVisible : false,
	useArrows : false,
	autoScroll : true,
	height : 300,
	width : 310,
	method : 'post',
	newdirText : 'New Category',
	layout : 'fit',
	textAdd : 'Add',
	textEdit : 'Edit',
	textDel : 'Delete',
	textRefresh : 'Refresh',
	msgConfirmDelete : 'Delete Message',
	currentTime : '0000-00-00',
	appliedTime : '0000-00-00',
	title : 'Select Business Code',
	
	initComponent : function() {
		
		this.treeLoader = new Ext.tree.TreeLoader({
			dataUrl : webUri+'/app/bind/unit/selectTree.do',
			method : this.method,
			preloadChildren : true,
 			listeners: {
				beforeload: function(loader, node) {
					this.baseParams.id = node.attributes.unitId||node.id;
				}
			}
		});

		Ext.apply(this, {
			id : 'app_tree_selectbindunit',
			enableDD : false,
			ddAppendOnly: false,
			enableDrag: false,
			enableDrop: false,

			root : new Ext.tree.AsyncTreeNode({
				text : this.rootText,
				draggable : false,
				id : this.rootId,
				iconCls : 'icon-doc-root',
				leaf : false,
				expanded : true
			}),

			loader : this.treeLoader,

			treeSorter : new App.tree.TreeSorter(this, {
	 			folderSort : true,
	    		dir : 'ASC',
	    		caseSensitive : true,
	    		sortType : function(node) {
	       			return node.attributes.unitType + node.id;
			    }
	 		})
		});

		App.tree.SelectBindUnit.superclass.initComponent.call(this, arguments);

		this.on({
			click : {scope : this, fn : this.onClick, stopEvent : true},
			dblclick : {scope : this, fn : this.onDblClick, stopEvent : true}
		});
	},
	
	listeners : {
		load : {
			fn : function(node) {
				if(node.id == this.rootId) {
					node.fireEvent('cilck', node);
					node.expand(false, false, function(node) {
						this.fireEvent('click', node);
					});
				}
			}
		}
	},
	
	onClick : function(node, e) {
		if(node) {
			var isUnit = node.attributes.unitType == 'unit';

			document.getElementById('unitId').value = isUnit ? node.attributes.unitId : '';
			document.getElementById('unitName').value = isUnit ? node.attributes.unitName||node.text : '';
			document.getElementById('retentionPeriod').value = isUnit ? node.attributes.retentionPeriod : '';
			document.getElementById('serialNumber').value = isUnit ? node.attributes.serialNumber : '';
		}
	},

	onDblClick : function(node, e) {
		if(node) {
			var isUnit = node.attributes.unitType == 'unit';

			if(isUnit) {
				var unit = {};
				unit['unitId'] = node.attributes.unitId;
				unit['unitName'] = node.attributes.unitName||node.text;
				unit['retentionPeriod'] = node.attributes.retentionPeriod;
				unit['serialNumber'] = node.attributes.serialNumber;

				if(opener && opener.setUnit) {
					opener.setUnit(unit);
					window.close();
				}
			}
		}
	},
	
	onCanceledit : function(node) {
		this.topToolbar.newfolder.setDisabled(false);

		if(node.editNode.id.indexOf('xnode-') == 0) {
			node.editNode.remove();
		}
	},
	
	onNewFolder : function(btn, e) {
		var node = this.getSelectionModel().getSelectedNode();
		var parentId = node.attributes.unitId||node.id;
		var options = "toolbar=no,menubar=no,personalbar=no,width=350,height=160,scrollbars=no,resizable=no,modal=yes,dependable=yes";

		window.open(webUri+'/app/bind/unit/add.do?parentId=' + parentId, 'unit_category', options);
	},
	
	onNewFolder_old : function(btn, e) {
		this.topToolbar.newfolder.setDisabled(true);
		
		var node = this.getSelectionModel().getSelectedNode();
		
		var treeEditor = this.treeEditor;
		// create new folder after the appendNode is expanded
		node.expand(false, false, function(n) {
			// create new node
			newNode = n.appendChild(
				new Ext.tree.AsyncTreeNode({
					text: this.newdirText,
					leaf : true,
					children : '',
					iconCls : 'icon-folder',
					expanded : true
				})
			);
			
			// setup one-shot event handler for editing completed
			treeEditor.on({
				complete: {
					scope: this,
					single: true,
					fn: this.onNewDir
				}
			});
	 
			// creating new directory flag
			treeEditor.creatingNewDir = true;
			// start editing after short delay
			(function(){treeEditor.triggerEdit(newNode);}.defer(10));
			// expand callback needs to run in this context
		}.createDelegate(this));
	},

	onNewDir: function(editor) {
		this.topToolbar.newfolder.setDisabled(false);

		var options = {
			url : webUri+'/app/bind/unit/create.do',
			method : this.method,
			scope : this,
			node : editor.editNode,
			callback : this.cmdCallback,
			params : {
				cmd : 'newdir',
				unitName : encodeURIComponent(editor.editNode.text),
				parentId : editor.editNode.parentNode.attributes.unitId||editor.editNode.parentNode.id,
				unitType : 'folder'
			}			
		};
		Ext.Ajax.request(options);
	},

	onEditFolder : function(btn, e) {
		var node = this.getSelectionModel().getSelectedNode();
		var options = "toolbar=no,menubar=no,personalbar=no,width=350,height=160,scrollbars=no,resizable=no,modal=yes,dependable=yes";

		window.open(webUri+'/app/bind/unit/edit.do?parentId=' + node.parentNode.id + '&unitId=' + node.id, 'unit_category', options);
	},

	onEditFolder_old : function(btn, e) {
		this.treeEditor.triggerEdit(this.getSelectionModel().getSelectedNode());
	},
	
	onEditComplete: function(editor, newName, oldName) {
		if(newName === oldName || editor.creatingNewDir) {
			editor.creatingNewDir = false;
			return;
		}

		var options = {
			url : webUri+'/app/bind/unit/rename.do',
			method : this.method,
			scope : this,
			callback : this.cmdCallback,
			node : editor.editNode,
			oldName : oldName,
			params : {
				cmd : 'rename',
				unitName : encodeURIComponent(newName),
				unitId : editor.editNode.attributes.unitId
			}
		};
		Ext.Ajax.request(options);
	},

	onBeforeEditComplete: function(editor, newName, oldName) {
		if(editor.cancellingEdit) {
			editor.cancellingEdit = false;
			return;
		}
		var oldPath = this.getPath(editor.editNode);
		var newPath = oldPath.replace(/\/[^\\]+$/, '/' + newName);
	 
		if(false === this.fireEvent('beforerename', this, editor.editNode, newPath, oldPath)) {
			editor.cancellingEdit = true;
			editor.cancelEdit();
			return false;
		}
	},

	onDeleteFolder : function(btn, e) {
		var node = this.getSelectionModel().getSelectedNode();
		
		if(confirm(this.msgConfirmDelete)) {
			var options = {
				url : webUri+'/app/bind/unit/remove.do',
				scope : this,
				callback : this.cmdCallback,
				node : node,
				params : {
					cmd : 'delete',
					unitId : node.attributes.unitId,
					parentId : node.parentNode.attributes.unitId||node.parentNode.id
				}
			};
			Ext.Ajax.request(options);
		}
	}, // eo function deleteNode
	
	getPath: function(node) {
		var path, p, a;

		// get path for non-root node
		if(node !== this.root) {
			p = node.parentNode;
			a = [node.text];
			while(p && p !== this.root) {
				a.unshift(p.text);
				p = p.parentNode;
			}
			a.unshift(this.root.attributes.path || '');
			path = a.join(this.pathSeparator);
		}
		// path for root node is it's path attribute
		else {
			path = node.attributes.path || '';
		}
	 
		// a little bit of security: strip leading / or .
		// full path security checking has to be implemented on server
		path = path.replace(/^[\/\.]*/, '');
		
		return path;
	}, // eo function getPath

	cmdCallback: function(options, success, response) {
		var i, o, node;
		var showMsg = true;

		// process Ajax success
		if(true === success) {
			// try to decode JSON response
			try {
				o = Ext.decode(response.responseText);
			} catch(ex) {
				App.util.showError(response.responseText);
			}

			// process command success
			if(true === o.success) {
				switch(options.params.cmd) {
					case 'delete':
						if(true !== this.eventsSuspended) {
							this.fireEvent('delete', this, this.getPath(options.node));
						}

						var parentNode = options.node.parentNode;
						options.node.parentNode.removeChild(options.node);

						this.fireEvent('click', parentNode);
					break;

					case 'newdir':
						options.node.setId(o.id);
						if(true !== this.eventsSuspended) {
							this.fireEvent('click', options.node);
						}
					break;

					case 'rename':
						if(true !== this.eventsSuspended) {
							this.fireEvent('rename', options.node);
							var node = options.node;
							var isFolder = node.attributes.unitType == 'folder';
							document.getElementById(isFolder ? 'parentName' : 'unitName').value = node.text;
						}
					break;
					
					case 'move':
						if(true !== this.eventsSuspended) {
							this.fireEvent('click', options.node);
						}
					break;
				}
			} // eo process command success
			// process command failure
			else {
				switch(options.params.cmd) {
					case 'rename':
						// handle drag & drop rename error
						if(options.oldParent) {
							options.oldParent.appendChild(options.node);
						}
						// handle simple rename error
						else {
							options.node.setText(options.oldName);
						}
						// signal failure to onNodeDrop
						if(options.e) {
							options.e.failure = true;
						}
						if(true !== this.eventsSuspended) {
							this.fireEvent('renamefailure', this, options.node, options.params.newname, options.params.oldname);
						}
					break;
	
					case 'newdir':
						if(false !== this.eventsSuspended) {
							this.fireEvent('newdirfailure', this, options.params.dir);
						}
						options.node.parentNode.removeChild(options.node);
					break;
	
					case 'delete':
						if(true !== this.eventsSuspended) {
							this.fireEvent('deletefailure', this, options.node);
						}
						// options.node.parentNode.reload.defer(1, options.node.parentNode);
						// options.node.parentNode.reload();
					break;

					case 'move':
						// handle drag & drop error
						options.node.parentNode.reload();

						// signal failure to onNodeDrop
						if(options.e) {
							options.e.failure = true;
						}
						if(true !== this.eventsSuspended) {
							this.fireEvent('click', options.node);
						}
					break;

					default:
						this.root.reload();
					break;
				}
	
				if(o.msg) {
					alert(o.msg);
				} else {
					// show default message box with server error
					App.util.showError(o.error || response.responseText);
				}
			} // eo process command failure
		} // eo process Ajax success
	
		// process Ajax failure
		else {
			App.util.showError(response.responseText);
		}
	},

	hasChild : function(node, childName) {
		return (node.isLeaf() ? node.parentNode : node).findChild('text', childName) !== null;
	}
});
Ext.reg('app-tree-selectbindunit', App.tree.SelectBindUnit);
