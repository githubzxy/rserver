/**
* 目录及文件列表上面部分的添加目录和添加文件按钮及一个模糊查询框
* 分为左右两侧，左边是添加目录和添加文件按钮，
* 右边是模糊查询框和搜索按钮
* @author roysong
* @date 190515
*/
define('kmms/integratedManage/fileShare/fileOper',
		['bui/common','bui/data','bui/overlay',
			'kmms/integratedManage/fileShare/addDic',
			'kmms/integratedManage/fileShare/addFile'
		],
		function(r){
	var BUI = r('bui/common'),
    Data = r('bui/data'),
    Overlay = r('bui/overlay'),
    AddDic = r('kmms/integratedManage/fileShare/addDic'),//新建目录弹出框
    AddFile = r('kmms/integratedManage/fileShare/addFile');//上传文件弹出框
	var fileOper = BUI.Component.Controller.extend({
		createDom : function(){
			var _self = this,el = _self.get('el'),buttonVisible = _self.get('buttonVisible'),
			buttonContainer = $('<span class="pull-left" style="margin: 1px 0 0 15px;"></span>'),
			searchContainer = $('<span class="pull-right" style="margin-right: 15px;"></span>');
			if(buttonVisible.showAddDic)
				buttonContainer.append(
					'<button class="addDicButton button-small button-info" style="margin-right: 10px;"><i class="icon-plus icon-white"></i>新建目录</button>'
				);
			if(buttonVisible.showUploadFile)
				buttonContainer.append(
					'<button class="uploadFileButton button-small button-info"><i class="icon-upload icon-white"></i>上传文件</button>' 
				);
			searchContainer.append(
				'<input type="text" id="searchFileKeyword" style="padding: 0;width: 260px;margin-right: 6px;">' + 
				'<button class="searchFileButton button-info button-small" style="margin-top: 1px;"><i class="icon-search icon-white"></i>搜索</button>'
			);
			el.append(buttonContainer);
			el.append(searchContainer);
		},
		renderUI : function(){
			var _self = this,el = _self.get('el'),buttonVisible = _self.get('buttonVisible');
			if(!buttonVisible.showAddDic){
				el.find('.addDicButton').hide();
			}
			if(!buttonVisible.showUploadFile){
				el.find('.uploadFileButton').hide();
			}
		},
		_getCurrentDicFullPath : function(){
			var _self = this,navStore = _self.get('navStore'),
			records = navStore.getResult(),last = records[records.length - 1];
			if(last.id == -1) records.pop();
			var fullpath = '';
			BUI.each(records,function(e){fullpath += e.name + '/'});
			return fullpath;
		},
		_getNavData : function(){
			var _self = this;
			var records = _self.get('navStore').getResult();
			var navData = [];
			BUI.each(records,function(e){if(e) navData.push(e)});
			return navData;
		},
		bindUI : function(){
			var _self = this,el = _self.get('el'),collectionName = _self.get('collectionName');
			var records = _self.get('navStore').getResult();
			var navData = [];
			BUI.each(records,function(e){if(e) navData.push(e)});
			//点击新建目录按钮，弹出新建目录对话框
			el.find('.addDicButton').click(function(){
				var addDicDialog = new AddDic({
					perId : _self.get('perId'),
					userId : _self.get('userId'),
					orgId : _self.get('orgId'),
					orgName : _self.get('orgName'),
					collectionName : collectionName,
					fullPath : _self._getCurrentDicFullPath(),
					navData : _self._getNavData()
				});
				addDicDialog.show();
				addDicDialog.on('completeAddSave',function(e){
					_self.fire('addDicEvent');
				});
			});
			//点击上传文件按钮，弹出上传文件选择框
			el.find('.uploadFileButton').click(function(){
				var addFileDialog = new AddFile({
					perId : _self.get('perId'),
					userId : _self.get('userId'),
					orgId : _self.get('orgId'),
					orgName : _self.get('orgName'),
					collectionName : collectionName,
					fullPath : _self._getCurrentDicFullPath(),
					navData : _self._getNavData()
				});
				addFileDialog.show();
				addFileDialog.on('completeAddSave',function(e){
					_self.fire('uploadFileEvent');
				});
			})
			//点击搜索按钮，抛出事件给文件列表组件
			el.find('.searchFileButton').click(function(){
				var searchText = $('#searchFileKeyword').val();
				if(searchText){
					_self.fire('searchFile',{text : searchText});
				}
			});
		}
	},{
		ATTRS : {
			/**
             * 当前权限ID
             */
            perId : {},
            /**
             * 当前用户Id
             */
            userId : {},//登录用户ID
            orgId : {},//登录用户组织机构ID
            orgName : {},//登录用户组织机构名称
            collectionName : {},
            navStore : {},
            buttonVisible : {},
            events : {
				value : {
					'addDicEvent' : true,
					'uploadFileEvent' : true
				}
            }
		}
	});
	return fileOper;
});