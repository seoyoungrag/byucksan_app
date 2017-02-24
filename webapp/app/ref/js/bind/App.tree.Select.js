/** 편철 등록 시 단위업무 트리 */
Ext.ns('App');
Ext.ns('App.tree');

Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.BLANK_IMAGE_URL = (this.webUri ? this.webUri : '/ep') + '/app/ref/extjs/resources/images/default/s.gif';
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.MessageBox.minWidth = 230;
	Ext.MessageBox.maxWidth = 600;
	
	rootId = '*';
	
	var tree = new Ext.tree.TreePanel({
		border : true,
		rootVisible : true,
		useArrows : false,
		method : 'post',
		autoScroll : true,
		animate: true,
		width : 303,
		height : 175,

		root : new Ext.tree.AsyncTreeNode({
			text : this.rootText,
			draggable : false,
			id : this.rootId,
			iconCls : 'icon-doc-root',
			leaf : false,  
			expanded : true
		}),

		loader : new Ext.tree.TreeLoader({
			dataUrl : webUri+'/app/bind/unit/selectTree.do',
			method : this.method,
			preloadChildren : true,
 			listeners: {
				beforeload: function(loader, node) {
					this.baseParams.id = node.attributes.unitId||node.id;
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
			return node.attributes.unitType + node.id;
	    }
	});
	
	tree.on('click', function(node, e) {
		if(node.attributes.unitType == 'unit') {
			var form = document.getElementById('bindForm');
			form.unitId.value = node.attributes.unitId;
			form.unitName.value = node.text;
			form.retentionPeriod.value = node.attributes.retentionPeriod;
		}
	});

	tree.render('select-tree');
});
