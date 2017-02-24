Ext.ns('App');
Ext.ns('App.grid');
Ext.ns('App.menu');
Ext.ns('App.tree');
Ext.ns('App.util');
Ext.ns('App.win');

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
		height : 213,
		width : 257,

		root : new Ext.tree.AsyncTreeNode({
			text : this.rootText,
			draggable : false,
			id : this.rootId,
			iconCls : 'folder', // 'icon-doc-root',
			leaf : false,
			expanded : true
		}),

		loader : new Ext.tree.TreeLoader({
			dataUrl : webUri+'/app/bind/getTransferTree.do',
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
			return node.id;
	    }
	});
	
	tree.on('click', function(node, e) {
		if(!node.isRoot && node.attributes.bind) {
			var form = document.getElementById('bindForm');
			form.targetId.value = node.id;
			form.targetName.value = node.text;
		} else {
			var form = document.getElementById('bindForm');
			form.targetId.value = '';
			form.targetName.value = '';
		}
	});

	tree.render('select-dept-tree');
});
