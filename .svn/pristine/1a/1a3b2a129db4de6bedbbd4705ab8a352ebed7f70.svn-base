/**
 * 操作按钮组
 * @author roysong
 * @date 190520
 */
define('kmms/integratedManage/fileShare/buttonGroup',
		['bui/common','bui/data','bui/list','bui/toolbar',
			'bui/overlay','common/data/PostLoad',
			'kmms/integratedManage/fileShare/buttonItem'
		],
		function(r){
	var BUI = r('bui/common'),
	Component = BUI.Component,
    UIBase = Component.UIBase,
    Data = r('bui/data'),
    ButtonItem = r('kmms/integratedManage/fileShare/buttonItem');
	var buttonGroup = BUI.Component.Controller.extend([UIBase.ChildList],{
		initializer : function(){
			var _self = this,buttons = _self.get('buttons'),items = [],events = _self.get('events');
			BUI.each(buttons,function(e){
				items.push(new ButtonItem({
					type : e.type,
					icon : e.icon,
					eventName : e.eventName,
					item : e.item
				}));
				events[e.eventName] = true;
			});
			_self.set('events',events);
			_self.addItems(items);
		}
	},{
		ATTRS : {
			elTagName  : {view : true,value : 'span'},
			elCls : {value : 'pull-right'},
			elStyle : {value : {'padding-top': '4px'}},
			buttons : {},
			events : {},
		}
	});
	return buttonGroup;
});