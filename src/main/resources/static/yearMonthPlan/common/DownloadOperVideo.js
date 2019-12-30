/**
 * 年月报表入口界面 下载操作视频控件
 */
define('yearMonthPlan/common/DownloadOperVideo',[
	'bui/common',
	],function(require){
	var BUI = require('bui/common'),Component = BUI.Component;
	
	var DownloadOperVideo = Component.Controller.extend({
		initializer : function() {
			var _self = this;
			_self.addChild(_self._initDownloadOperVideo());
		},
		bindUI : function() {
			var _self = this;
			$('.download').on('click',function(){
				window.location.href = _self.get('url');
			});
		},
		/**
		 * 初始化下载操作视频
		 */
		_initDownloadOperVideo : function(){
			var _self = this;
			var content = new Component.Controller({
//				content : "<input type='button' class='button button-mini button-primary download' value =" + _self.get('title') + "></input>",
				width : 200,
				elStyle : _self.get('elStyle')
			});
			return content;
		}
	}, {
		ATTRS : {
			title : {},
			url : {},
			elStyle : {}
		}
	})
	return DownloadOperVideo;
});