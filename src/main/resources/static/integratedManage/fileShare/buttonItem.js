/**
 * 操作按钮
 * @author roysong
 * @date 190520
 */
define('kmms/integratedManage/fileShare/buttonItem',
		['bui/common','bui/data','bui/list','bui/toolbar',
			'bui/overlay','common/data/PostLoad'
		],
		function(r){
	var BUI = r('bui/common'),
	Component = BUI.Component,
    UIBase = Component.UIBase,
    Data = r('bui/data');
	var buttonItem = BUI.Component.Controller.extend({
		initializer : function(){
			var _self = this,elCls = _self.get('elCls'),
			type = _self.get('type'),
			eventName = _self.get('eventName'),
			events = _self.get('events');
			elCls += ' x-icon-' + type;
			events[eventName] = true;
			_self.set('events',events);
			_self.set('elCls',elCls);
		},
		bindUI : function(){
			var _self = this,eventName = _self.get('eventName'),item = _self.get('item');
			_self.on('click',function(){
				_self.fire(eventName,item);
			});
		}
	},{
		ATTRS : {
			elTagName  : {view : true,value : 'span'},
			elCls : {value : 'x-icon x-icon-small'},
			elStyle : {value : {'cursor': 'pointer','margin-right' : '5px'}},
			tpl : {value : '<i class="icon icon-white icon-{icon}"></i>'},
			type : {},
			icon : {},
			item : {},
			eventName : {},
		}
	});
	return buttonItem;
});