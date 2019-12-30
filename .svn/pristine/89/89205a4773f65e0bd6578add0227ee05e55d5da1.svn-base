/**
 * 年月报表入口界面 工区、车间、段科室流程按钮
 */
define('yearMonthPlan/common/EntryButton',[
	'bui/common',
	],function(require){
	var BUI = require('bui/common'),Component = BUI.Component;
	
	var EntryButton = Component.Controller.extend({
		initializer : function() {
			var _self = this;
			_self.addChild(_self._initBtn());
		},
		/**
		 * 初始化按钮
		 */
		_initBtn:function(){
			var _self = this;
			var background = "#1296DB";
			var imgSrc = "/kmms/yearMonthPlan/common/images/gq.png";
			var text = "工区用户";
			switch (_self.get('orgType')) {
			case 4:
				background = "#db1233";
				imgSrc = "/kmms/yearMonthPlan/common/images/cj.png";
				text = "车间用户";
				break;
			case 3:
				background = "#db8412";
				imgSrc = "/kmms/yearMonthPlan/common/images/dks.png";
				text = "段科室用户";
				break;
			}
			
			var btnBox = new Component.Controller({
				xclass:'controller',
				elCls : 'btnBox',
				width:180,
				height:210,
				elStyle:{
					'background': '#F7F7F7',
					'margin': 'auto',
					'border': '1px solid #999',
					'border-radius': '10px'
				}
			});
			
			var img = "<center style='padding-top: 15px;'><div style ='width:80px;height：80px;border-radius:40px;background:"+background+"'><img style='margin: 5px auto;width:70px;height：70px;' src='"+imgSrc+"'></img></div></center>";
			var text = "<div style='margin-top:20px;font-weight: bold;font-size: 20px;'><center>"+text+"</center></div>";
			var button = "<div><center><input type='button' style='margin-top:15px; width:90px;height：22px;' class='button button-info' value='点击进入'></input></center></div>";
			btnBox.addChild(_self._initContent(img));
			btnBox.addChild(_self._initContent(text));
			btnBox.addChild(_self._initContent(button));
			return btnBox;
		},
		_initContent:function(content){
			var _self = this,orgType = _self.get('orgType');
			var controller = new Component.Controller({
				elCls : "type" + orgType,
				content : content
			});
			return controller;
		},
	}, {
		ATTRS : {
			elCls : {value:"span"},
			orgType : {},
			width : {value:240},
			height :{value:210},
		}
	})
	return EntryButton;
});