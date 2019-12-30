/**
 * 文件名或目录名显示容器
 * @author roysong
 * @date 190520
 */
define('kmms/integratedManage/fileShare/rowLabel',
		['bui/common','bui/data','bui/list','bui/toolbar',
			'bui/overlay','common/data/PostLoad'
		],
		function(r){
	var BUI = r('bui/common'),
    Data = r('bui/data');
	var rowLabel = BUI.Component.Controller.extend({
		createDom : function(){
			var _self = this,el = _self.get('el'),
			itemTpl = _self.get('itemTpl'),
			item = _self.get('item'),
			iconPath = _self.get('iconPath')
			;
			var itemDom = BUI.substitute(itemTpl,{iconPath : iconPath,itemId : item.id,itemName : item.name});
			el.append(itemDom);
		},
		bindUI : function(){
			var _self = this,item = _self.get('item'),itemType = item.type;
			_self.on('click',function(e){
				switch(itemType){
				case '1' : 
					_self.fire('enterDirectory',item);
					break;
				case '2' :
					var hasPageOffice = _self._hasPageOffice();
					if(hasPageOffice){
						_self.fire('filePreview',item);
					}else{
						_self.fire('fileDownload',item);
					}
					break;
				}
			});
		},
		/**
		 * 判断客户是否具备pageoffice插件
		 */
		_hasPageOffice : function(){
			//TODO 判断客户浏览器是否具备pageoffice插件
			return false;
		},
	},{
		ATTRS : {
			elTagName  : {value : 'span'},
			elStyle : {value : {'margin-left': '10px'}},
			itemTpl : {value : '<img src="{iconPath}" style="width:20px;"/><a style="margin-left: 5px;" href="#" id="file-a-{itemId}">{itemName}</a>'},
			item : {},
			iconPath : {},
			events : {
				value : {
					'enterDirectory' : true,
					'filePreview' : true,
					'fileDownload' : true,
				}
            },
		}
	});
	return rowLabel;
});