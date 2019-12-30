/**
 * 用于业务详情中已上传附件的展示和下载。
 */
define('common/uploader/ViewUploader', ['bui/uploader'], function(r) {
	var Uploader = r('bui/uploader');
	var ViewUploader = Uploader.Queue.extend({
		renderUI:function () {
			var _self = this,items = _self.get('alreadyItems');
			if(items && Array.isArray(items)){
				items.map(function(item){
					var name = item.name.replace("#","%23");
					var path = item.path;
					item.filePath = path;
					item.path = "/kmms/atachFile/download?name=" + name + "&path=" + path;// 下载路径
					item.previewUrl = '/pageoffice/openPage?filePath=';// 预览路径
					item.editUrl = '/pageoffice/?filePath=';// 编辑路径
				});
				_self.addItems(items);
			}
		},
		bindUI:function(){
			var _self = this,items = _self.getItems();
			if(items){
				items.forEach(function(item){
//					if(_self.isShowPreviewBtn(item)){
//						// 存在预览按钮
//						_self.updateFileStatus(item,'successPreview');
//					}else{
						// 不存在预览按钮,只存在下载按钮
//						_self.updateFileStatus(item,'success');
//					}
					if(_self.get("editOnline")==true){
						_self.updateFileStatus(item,'successEdit');
					}else if(_self.get("previewOnline")==true){
						_self.updateFileStatus(item,'successPreview');
					}else{
						_self.updateFileStatus(item,'success');
					}
				});
			}
		},
		/**
		 * 判断是否存在预览按钮
		 * @parpam item 一条文件数据
		 * @return true-存在,否则不存在
		 */
		isShowPreviewBtn : function(item){
			var _self = this;
			if(item){
				var filePath = item.filePath;
				// 获取后缀名
				var postfix = filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length);
				switch(postfix){
					case 'doc':
						return true;
					case 'docx':
						return true;
					case 'xls':
						return true;
					case 'xlsx':
						return true;
					case 'vsd':
						return true;
					case 'pdf':
						return true;
					case 'jpg':
						return true;
					case 'tif':
						return true;
					case 'dwg':
						return true;
					default : 
						return false;
				}
			}
			return false;
		},
	}, {
		ATTRS : {
			editOnline : {value:false},
			previewOnline : {value:false},
			alreadyItems : {value : []},
			itemTpl: {
		        value: '<li>{resultTpl} </li>'
			},
			resultTpl: {
				value: {
					'success': '<div class="success"><label id="{id}" class="fileLabel" title={title}>{name}</label><span style="float: right;"><a href="{path}">下载</a></span></div>',
					'successPreview': '<div class="success"><label id="{id}" class="fileLabel" title={title}>{name}</label><span style="float: right;"><a href="{path}">下载</a>&nbsp;'+
						'<a href="{previewUrl}{filePath}" target="_blank">预览</a></span></div>',	
					'successEdit': '<div class="success"><label id="{id}" class="fileLabel" title={title}>{name}</label><span style="float: right;"><a href="{path}">下载</a>&nbsp;'+
					'<a href="{editUrl}{filePath}" target="_blank">编辑</a></span></div>',
				}
			}
		}
	});
	return ViewUploader;
});