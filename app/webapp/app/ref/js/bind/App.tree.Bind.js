/** 편철 지정 목록 트리 */

Ext.ns('App');
Ext.ns('App.tree');

Ext.onReady(function() {

	Ext.QuickTips.init();
	Ext.BLANK_IMAGE_URL = (this.webUri ? this.webUri : '/ep') + '/app/ref/extjs/resources/images/default/s.gif';
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.MessageBox.minWidth = 230;
	Ext.MessageBox.maxWidth = 600;
	
	var tree = new Ext.tree.TreePanel({
		border : true,
		rootVisible : false,
		useArrows : false,
		method : 'post',
		autoScroll : true,
		animate: true,
		height : 250,
		width : 400,

		root : new Ext.tree.AsyncTreeNode({
			id : '*',
			text : 'Bind List',
			draggable : false,
			iconCls : 'icon-group-list',
			leaf : false,
			expanded : true
		}),

		loader : new Ext.tree.TreeLoader({
			dataUrl : webUri+'/app/bind/selectList.do',
			method : this.method,
			preloadChildren : true,
 			listeners: {
				beforeload: function(loader, node) {
					this.baseParams.id = node.id;
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
			return node.attributes.unitId||node.attributes.ordered;
	    }
	});
	
	tree.on('click', function(node, e) {
		var form = document.getElementById('form');
		form.bindId.value = node.isLeaf() ? node.id : '';
		form.bindName.value = node.isLeaf() ? node.attributes.originalText : '';
		form.unitId.value = node.isLeaf() ? node.attributes.unitId||'' : '';
		form.unitName.value = node.isLeaf() ? node.attributes.unitName||'' : '';
		form.retentionPeriod.value = node.isLeaf() ? node.attributes.retentionPeriod||'' : '';
		form.displayName.value = node.isLeaf() ? node.attributes.displayName||'' : '';
	});

	tree.on('dblclick', function(node, e) {
		if(node.isLeaf()) {
			var bind = {};
			bind['bindingId'] = node.id;
			bind['bindingName'] = node.attributes.originalText;
			bind['unitId'] = node.attributes.unitId;
			bind['unitName'] = node.attributes.unitName;
			bind['retentionPeriod'] = node.attributes.retentionPeriod;

			if(opener && opener.setBind) {
				opener.setBind(bind);
				window.close();
			}
		}
	});
	
	tree.render('tree-div');
});
