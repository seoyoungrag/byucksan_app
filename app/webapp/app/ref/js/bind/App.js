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

	var options = {
		rootText : this.rootText,
		textAdd : this.textAdd,
		textEdit : this.textEdit,
		textDel : this.textDel,
		textRefresh : this.textRefresh,
		msgConfirmDelete : this.confirm_delete,
		msgDelete : this.msg_delete,
		currentTime : this.currentTime,
		appliedTime : this.appliedTime
	};

	this.tree = new App.tree.BindUnit(options);

	this.tree.render('tree-div');

});