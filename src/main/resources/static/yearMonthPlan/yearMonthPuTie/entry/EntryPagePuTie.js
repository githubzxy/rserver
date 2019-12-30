/**
 * 普铁年月报表入口界面
 */
define('yearMonthPlan/yearMonthPuTie/entry/EntryPagePuTie',[
	'bui/common','bui/overlay',
	'yearMonthPlan/common/EntryButton',
	'yearMonthPlan/common/DownloadOperVideo',
	],function(require){
	var BUI = require('bui/common'), Component = BUI.Component,
	Overlay = require('bui/overlay'),
	EntryButton = require('yearMonthPlan/common/EntryButton'),
	DownloadOperVideo = require('yearMonthPlan/common/DownloadOperVideo');
	
	var EntryPagePuTie = Component.Controller.extend({
		initializer : function() {
			var _self = this;
			var title = _self._initTitle();
			var left = _self._initBtn(5);
			var middle = _self._initBtn(4);
			var right  = _self._initBtn(3);
			_self.addChild(title);
			_self.addChild(left);
			_self.addChild(middle);
			_self.addChild(right);
			var downloadOperVideo = new DownloadOperVideo({
				title : '点击下载普铁年月报表操作手册及视频',
				url : '',
				elStyle : {
					'margin-left': 136,
			    	'margin-top': 230
				}
			});
			_self.addChild(downloadOperVideo);
		},
		bindUI : function() {
			var _self = this,orgType = _self.get('orgType');
			
			$('body').on('click','.type5',function(){
				if(orgType == 1503 ){
					window.parent.menuClick('工区年月表',_self.get('workAreaUrl'),null);
				}else{
					BUI.Message.Alert('只有工区用户才能打开此页面！','info');
				}
			})
			$('body').on('click','.type4',function(){
				if(orgType == 1502 ){
					window.parent.menuClick('车间年月表',_self.get('workShopUrl'),null);
				}else{
					BUI.Message.Alert("只有车间用户才能打开此页面！","info");
				}
			})
			$('body').on('click','.type3',function(){
				if(orgType == 1501 ){
					window.parent.menuClick('段科室年月表',_self.get('segmentUrl'),null);
				}else{
					BUI.Message.Alert("只有段科室用户才能打开此页面！","info");
				}
			})
		},
		_initBtn:function(orgType){
			var btn = new EntryButton({
				orgType : orgType,
			});
			return btn;
		},
		_initTitle:function(){
			var title = new Component.Controller({
				height:90,
				content : '<center>年月报表编制/执行</center>',
				elStyle : {
					'font-size':'36px',
					'font-weight':'bold'
				}
			});
			return title;
		}
	}, {
		ATTRS : {
			width:{value:763},
			height:{value:300},
			elStyle:{
				value:{
					'margin':'auto',
					'margin-top':($(window).height()-420)/2
				}
			},
			workAreaUrl:{value:'/pageKmms/page/workAreaPuTieYearMonth'},
			workShopUrl:{value:'/pageKmms/page/workShopPuTieYearMonth'},
			segmentUrl:{value:'/pageKmms/page/segmentPuTieYearMonth'},
			orgType:{},
		}
	})
	return EntryPagePuTie;
});