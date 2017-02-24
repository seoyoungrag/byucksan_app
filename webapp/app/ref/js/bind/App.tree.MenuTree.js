/** 편철함 목록 메뉴 트리 */

Ext.ns('App');
Ext.ns('App.tree');

Ext.onReady(function() {

	Ext.QuickTips.init();
	Ext.BLANK_IMAGE_URL = (this.webUri ? this.webUri : '/ep') + '/app/ref/extjs/resources/images/default/s.gif';
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.MessageBox.minWidth = 230;
	Ext.MessageBox.maxWidth = 600;
	
	var tree = new Ext.tree.TreePanel({
		border : false,
		rootVisible : false,
		useArrows : false,
		method : 'post',
		autoScroll : true,
		animate : true,
		width : 170,
		height : 300,
		
		root : new Ext.tree.AsyncTreeNode({
			id : '*',
			text : 'Bind List',
			draggable : false,
			iconCls : 'icon-group-list',
			expanded : true,
			
 			listeners: {
				load : {
					scope : this,
					fn : function(node, b) {
						node.expand(false, true, function(node) {
							this.fireEvent('click', node.item(0).item(0));
						});
					}
				}
			}
		}),

		loader : new Ext.tree.TreeLoader({
			dataUrl : webUri + '/app/bind/selectList.do',
			method : this.method,
			preloadChildren : true,
 			listeners: {
				beforeload: function(loader, node) {
					this.baseParams.id = node.id;
				},
	
				load : {
					//scope : this,
					fn : function(tree, node, target) {
						this.fireEvent('click', target);
					}
				}
			}
		})
	});
	
	tree.treeSorter = new App.tree.TreeSorter(tree, {
		folderSort : true,
		dir : 'ASC',
		caseSensitive : true,
		sortType : function(node) {
    		// sort by a custom, typed attribute:
			return (node.attributes.unitId ? node.attributes.unitId : '') + node.attributes.ordered;
	    }
	});
	
	tree.on('click', function(node, e) {
		if(node.isLeaf()) {
			top.body.location.href = (this.webUri ? this.webUri : '/ep') + '/app/bind/document/list.do?bindId=' + node.id;
		}
	});

	tree.render('tree-div');
});
