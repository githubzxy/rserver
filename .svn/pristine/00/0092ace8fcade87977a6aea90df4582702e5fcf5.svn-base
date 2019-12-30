/**
* 文件共享首页，从上到下分为三部分：
* 第一部分是操作区，包含添加目录和添加文件按钮及一个模糊查询框；
* 第二部分是导航区，包含导航面包屑，支持文件目录的显示和跳转；
* 第三部分是列表区，包含当前目录下的所有目录及文件列表，带分页
* @author roysong
* @date 190514
*/
define('kmms/integratedManage/fileShare/fileSharePage',
		['bui/common','bui/data','common/data/PostLoad',
			'bui/toolbar',
			'kmms/integratedManage/fileShare/fileOper',
			'kmms/integratedManage/fileShare/fileList'],
		function(r){
	var BUI = r('bui/common'),
    Data = r('bui/data'),
    PostLoad = r('common/data/PostLoad'),
    Toolbar = r('bui/toolbar'),
    FileOper = r('kmms/integratedManage/fileShare/fileOper'),
    FileGrid = r('kmms/integratedManage/fileShare/fileList');
	var defaultNavData = {id : '',name : '主目录'},
		searchNavData = {id : '-1',name : '关于<span style="font-weight: initial;">"xxx"</span>的查询结果'};
	var fileSharePage = BUI.Component.Controller.extend({
		initializer : function(){
			var _self = this,collectionName = _self.get('collectionName');
			var navStore = new Data.Store({data : [defaultNavData]});
			//第一部分操作区初始化
			var fileOper = new FileOper({
				id : 'fileOper',
				height : 30,
				elStyle : {'background-color': 'aliceblue'},
				perId : _self.get('perId'),
				userId : _self.get('userId'),
				orgId : _self.get('orgId'),
				orgName : _self.get('orgName'),
				navStore : navStore,
				collectionName:_self.get('collectionName'),
				buttonVisible : _self._isOperButtonVisible()//是否显示新建文件夹按钮和上传文件按钮
			});
			_self.addChild(fileOper);
			//第二部分导航区初始化
			var topNav = new Toolbar.Breadcrumb({
				id: 'topNav',
				store : navStore,
				elStyle : {'background-color': 'honeydew','font-weight': 'bold'}
			});
			_self.addChild(topNav);
			//第三部分列表区初始化
			var fileStore = new Data.Store({
				url : "/kmms/fileShareAction/findAll.cn",
                autoLoad : true ,
                pageSize : 20,
                proxy : {
                    method : 'post',
                    dataType : 'json'
                },
                params : {collectionName:collectionName,parentId: ''}
			});
			var fileGrid = new FileGrid({
				id : 'fileGrid',
				perId : _self.get('perId'),
				userId : _self.get('userId'),
				orgId : _self.get('orgId'),
				orgName : _self.get('orgName'),
				store : fileStore,
				collectionName:_self.get('collectionName'),
				buttonVisible : _self._isGridButtonVisible()//是否显示列表上的操作按钮
			});
			_self.addChild(fileGrid);
			var paging = new Toolbar.NumberPagingBar({ //创建数字分页控件
	            elCls : 'pagination pull-right',
	            elStyle : {'margin':'5px'},
	            store : fileStore //共同使用store
	        });
			_self.addChild(paging);
		},
		bindUI : function(){
			var _self = this;
			_self._handleFileOperEvent();
			_self._handleTopNavEvent();
			_self._handleFileListEvent();
		},
		/**
		 * 处理第一部分操作区的事件
		 */
		_handleFileOperEvent : function(){
			var _self = this,
			fileOper = _self.getChild('fileOper'),
			fileList = _self.getChild('fileGrid'),
			topNav = _self.getChild('topNav'),
			navStore = topNav.get('store'),
			collectionName = _self.get('collectionName'),
			fileStore = fileList.get('store');
			// 新建文件夹完成后，列表数据刷新
			fileOper.on('addDicEvent',function(e){
				fileStore.load();
			});
			// 上传文件完成后，列表数据刷新
			fileOper.on('uploadFileEvent',function(e){
				fileStore.load();
			});
			// 发起模糊查询时，列表数据显示查询结果，并通知导航显示“主目录/查询结果”
			fileOper.on('searchFile',function(e){
				var name = e.text;
				fileStore.load({name : name,parentId : '',start : 0,limit : 20,status : '1'},function(){
					fileList.showItemNav();
				});
				var searchData = BUI.cloneObject(searchNavData);
				searchData.name = searchData.name.replace('xxx',name);
				var data = [defaultNavData,searchData];
				navStore.setResult(data);
			});
		},
		/**
		 * 处理第二部分导航区事件
		 */
		_handleTopNavEvent : function(){
			var _self = this,
			topNav = _self.getChild('topNav'),
			fileGrid = _self.getChild('fileGrid'),
			collectionName = _self.get('collectionName'),
			fileStore = fileGrid.get('store');
			// 点击导航路径上某目录，文件列表展示该层目录下的所有目录及文件
			topNav.on('jumpToDirectory',function(e){
				var dicId = e.dic.id;
				if(dicId < 0){//代表点击的是导航上的“查询结果”显示 
					fileStore.load();
				}else{
					fileStore.load({parentId : dicId,collectionName:collectionName,name : ''});
				}
			});
		},
		/**
		 * 处理第三部分列表区事件
		 */
		_handleFileListEvent : function(){
			var _self = this,
			fileList = _self.getChild('fileGrid'),
			topNav = _self.getChild('topNav'),
			navStore = topNav.get('store'),
			collectionName = _self.get('collectionName'),
			fileStore = fileList.get('store');
			// 点击文件列表上某目录，导航上变化，列表刷新
			fileList.on('enterDirectory',function(e){
				var path = e.nav,id = e.id;
				if(path){//从文件列表上点击目录进入时，返回的是目录的数据，可直接读取其路径信息并加上本目录
					path.push({id : id,name : e.name});
					navStore.setResult(path);
				}else{//从文件列表的路径上点击进入时，返回的是导航的信息，而不是目录的数据，需要根据id查询到目录的数据再生成路径
					if(!id){
						navStore.setResult([defaultNavData]);
					}else{
						var pl = new PostLoad({
							url : '/kmms/fileShareAction/getById',
							el : _self.get('el'),
							loadMsg : '加载中...'
						});
						pl.load({id : id,collectionName : collectionName},function(data){
							path = data.nav;
							path.push({id : data.id,name : data.name});
							navStore.setResult(path);
						});
					}
				}
				fileStore.load({parentId : id,name : ''});
			});
		},
		/**
		 * 根据权限或组织机构判断是否显示新建文件夹按钮和上传文件按钮
		 */
		_isOperButtonVisible : function(){
			var _self = this;
			//TODO 根据权限或组织机构判断是否显示新建文件夹按钮和上传文件按钮
			return {
				showAddDic : true,//新建文件夹按钮
				showUploadFile : true//上传文件按钮
			};
		},
		/**
		 * 根据权限或组织机构判断是否显示列表上的操作按钮
		 */
		_isGridButtonVisible : function(){
			var _self = this;
			//TODO 根据权限或组织机构判断是否显示列表上的操作按钮
			return {
				showDelDic : true,//删除目录按钮
				showRenameDic : true,//重命名目录按钮
				showDelFile : true,//删除文件按钮
				showRenameFile : true,//重命名文件按钮
				showPreviewFile : true,//预览文件按钮
				showEditFile : true,//在线编辑文件按钮
				showDownFile : true,//下载文件按钮
			};
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
            elStyle : {value : {'background-color': 'aliceblue'}},
            /**
             * 存储表名
             */
            collectionName : {value:'kmfileshare'}
		}
	});
	return fileSharePage;
});