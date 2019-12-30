/**
 * 显示注释
 * @author roysong
 * @date 190520
 */
define('kmms/integratedManage/fileShare/commentLabel',
		['bui/common','bui/data','bui/list','bui/toolbar',
			'bui/overlay','common/data/PostLoad'
		],
		function(r){
	var BUI = r('bui/common'),
    Data = r('bui/data');
	var commentLabel = BUI.Component.Controller.extend({
	},{
		ATTRS : {
			elTagName  : {value : 'span'},
			elStyle : {value : {'color': 'gray'}},
			tpl : {value : '{text}'},
			text : {},
		}
	});
	return commentLabel;
});