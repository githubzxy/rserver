/**
 * 带加载遮罩层的数据请求组件，以POST方式提交请求 
 */
define('common/data/PostLoad', ['bui/common','bui/data','bui/mask'], function(r) {
	var BUI = r('bui/common'),Data = r('bui/data'),Mask = r('bui/mask');
	function PostLoad(config){
		PostLoad.superclass.constructor.call(this,config);
		this._initProxy();
		this._initEvent();
	}
	PostLoad.ATTRS = {
		el : {},
		ajaxOptions : {},
		loadMsg : {value : '数据处理中...'}
	};
	BUI.extend(PostLoad,Data.AbstractStore);
	BUI.augment(PostLoad,{
		_initProxy : function(){
			var _self = this,url = _self.get('url'),
			proxy = new Data.Proxy.Ajax({method : 'post',url : url,ajaxOptions : _self.get('ajaxOptions')});
			_self.set('proxy',proxy);
		},
		_initEvent : function(){
			var _self = this;
			var mask = new Mask.LoadMask({el : _self.get('el'),msg : _self.get('loadMsg')});
			_self.set('mask',mask);
			_self.on('beforeload',function(){
				mask.show();
			});
			_self.on('exception',function(){
				mask.hide();
				mask.destroy();
				BUI.Message.Alert('网络连接出现问题，请联系管理员。','error');
			});
		},
		afterProcessLoad : function(){
			var _self = this,mask = _self.get('mask');
			mask.hide();
			mask.destroy();
		}
	});
	return PostLoad;
});