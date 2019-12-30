/**
* 目录及文件列表下面部分是当前目录下的所有目录及文件列表，带分页
* 每个目录或文件分为上下两部分，上面是路径，下面是文件或目录名及操作按钮
* @author roysong
* @date 190518
*/
define('kmms/integratedManage/fileShare/fileGrid',
		['bui/common','bui/data','bui/list','bui/toolbar',
			'bui/overlay','common/data/PostLoad',
			'kmms/integratedManage/fileShare/updateDic'
		],
		function(r){
	var BUI = r('bui/common'),
    Data = r('bui/data'),
    List = r('bui/list'),
    Toolbar = r('bui/toolbar'),
    Overlay = r('bui/overlay'),
    PostLoad = r('common/data/PostLoad'),
    UpdateDic = r('kmms/integratedManage/fileShare/updateDic');
	var fileGridView = List.SimpleListView.extend({
		/**
		 * 列表中每个目录或文件分为二行，第一行是归属的目录路径，支持点击跳转，默认隐藏，模糊搜索后打开
		 * 第二行是目录或文件名，点击目录则进入，点击文件默认是pageoffice打开查看（如果电脑不支持，则应为下载）
		 * 后面是操作按钮栏，目录和文件分别具备不同的操作按钮
		 */
		getItemTpl : function  (record,index) {
			var _self = this,
			itemTpl = _self.get('itemRowTpl');
			//根据数据生成不同的路径
			var pathTpl = _self._getFullPathTpl(record);
			//生成文件或目录名及操作按钮
			var rowTpl = _self._getRowTpl(record);
			var items = [];
			items.push(pathTpl);
			items.push(rowTpl);
			itemTpl = BUI.substitute(itemTpl,{singleItem : items.join('')});
			return itemTpl;
		},
		/**
		 * 根据单行数据生成全路径
		 */
		_getFullPathTpl : function(item){
			var _self = this,
			pathTpl = _self.get('pathTpl'),
			pathItemTpl = _self.get('pathItemTpl');
			var items = [];
			BUI.each(item.nav,function(e){
				if(e) items.push(BUI.substitute(pathItemTpl,{id: item.id,navId : e.id,navName : e.name}));
			});
			pathTpl = BUI.substitute(pathTpl,{fullPath : items.join('')});
			return pathTpl;
		},
		/**
		 * 根据单行数据生成单行中下部分文件及目录名显示及按钮组
		 */
		_getRowTpl : function(item){
			var _self = this,
			fileAndDicTpl = _self.get('fileAndDicTpl'),
			fileNameTpl = _self.get('fileNameTpl'),
			dicNameTpl = _self.get('dicNameTpl'),
			commentTpl = _self.get('commentTpl');
			var items = [];
			switch(item.type){
			case '1' : //目录
				items.push(BUI.substitute(dicNameTpl,{id : item.id,name : item.name}));
			break;
			case '2' : //文件
				items.push(BUI.substitute(fileNameTpl,{id : item.id,name : item.name}));
			break;
			}
			items.push(BUI.substitute(commentTpl,{comment : item.comment}));
			items.push(_self._getFucllButtonTpl(item));
			fileAndDicTpl = BUI.substitute(fileAndDicTpl,{fileAndDicItem : items.join('')});
			return fileAndDicTpl;
		},
		/**
		 * 根据不同的记录生成不同的按钮
		 */
		_getFucllButtonTpl : function(item){
			var _self = this,buttonVisible = _self.get('buttonVisible'),
			buttonsTpl = _self.get('buttonsTpl'),
			buttonItemTpl = _self.get('buttonItemTpl');
			var buttons = [];
			switch(item.type){
			case '1' : //目录
				if(buttonVisible.showRenameDic) buttons.push(BUI.substitute(buttonItemTpl,{type : 'warning',icon : 'edit',id : item.id}));//修改目录名按钮
				if(buttonVisible.showDelDic) buttons.push(BUI.substitute(buttonItemTpl,{type : 'error',icon : 'trash',id : item.id}));//删除目录按钮
				buttonsTpl = BUI.substitute(buttonsTpl,{type : 'dic',buttons : buttons.join('')});
				break;
			case '2' : //文件
				if(buttonVisible.showEditFile) buttons.push(BUI.substitute(buttonItemTpl,{type : 'warning',icon : 'edit',id : item.id}));//在线编辑文件按钮
				if(buttonVisible.showPreviewFile) buttons.push(BUI.substitute(buttonItemTpl,{type : 'info',icon : 'search',id : item.id}));//在线预览文件按钮
				if(buttonVisible.showDownFile) buttons.push(BUI.substitute(buttonItemTpl,{type : 'success',icon : 'download-alt',id : item.id}));//下载文件按钮
				if(buttonVisible.showDelFile) buttons.push(BUI.substitute(buttonItemTpl,{type : 'error',icon : 'trash',id : item.id}));//删除文件按钮
				buttonsTpl = BUI.substitute(buttonsTpl,{type : 'file',buttons : buttons.join('')});
				break;
			}
			return buttonsTpl;
		},
	},{
	    ATTRS : {
	    	/**
			 * 列表中一行数据的容器模板
			 */
			itemRowTpl : {value : '<li class="bui-list-item"><div class="dicAndFile" style="border-bottom:1px dashed royalblue">{singleItem}</div></li>'},
			/**
			 * 行数据中显示全路径的容器模板
			 */
			pathTpl : {value : '<ul class="breadcrumb fileAndDicNav" style="margin:0;padding:0;display:none;background-color: #eeeeee;font-size: smaller;">路径：{fullPath}</ul>'},
			/**
			 * 行数据中显示全路径的子项模板，每个子项代表一级目录
			 */
			pathItemTpl : {value : '<li class="bui-breadcrumb-item"><a class="list-breadcrumb-item" href="#" id="{id}-{navId}">{navName}</a> <span class="divider">/</span></li>'},
			/**
			 * 单行文件或目录数据及按钮的容器模板
			 */
			fileAndDicTpl : {value : '<div style="height: 30px;line-height: 25px;">{fileAndDicItem}</div>'},
			/**
			 * 文件名显示模板
			 */
			fileNameTpl : {value : '<span style="margin-left: 10px;"><img src="/bui/img/icon_doc.svg" style="width:20px;"/><a style="margin-left: 5px;" class="fileSpan" href="#" id="file-a-{id}">{name}</a> </span>'},
			/**
			 * 目录名显示模板
			 */
			dicNameTpl : {value : '<span class="dicSpan" style="margin-left: 10px;"><img src="/bui/img/icon_file_fill.svg" style="width:20px;"/><a style="margin-left: 5px;" href="#" id="dic-a-{id}">{name}</a> </span>'},
			/**
			 * 备注显示模板
			 */
			commentTpl : {value : '<span style="color:gray;">{comment}</span>'},
			/**
			 * 文件及目录按钮容器模板
			 */
			buttonsTpl : {value : '<span class="{type}buttongroup pull-right" style="padding-top:4px;">{buttons}</span>'},
			/**
			 * 文件及目录按钮模板
			 */
			buttonItemTpl : {value : '<span class="x-icon x-icon-small x-icon-{type}" style="cursor:pointer;margin-right:5px;" id="file-{type}-{id}"><i class="icon icon-white icon-{icon}"></i></span>'},
	      }
	    });
	var fileGrid = List.SimpleList.extend({
		/**
		 * 判断客户是否具备pageoffice插件
		 */
		_hasPageOffice : function(){
			//TODO 判断客户浏览器是否具备pageoffice插件
			return false;
		},
		/**
		 * 使用pageoffice在线预览文件
		 * @param name 文件中文名称
		 * @param path 文件在服务器上的全路径，包含文件实际名称
		 * @param isEdit 是否进行在线编辑
		 */
		_previewFile : function(name,path,isEdit){
			//TODO 使用pageoffice在线预览文件
		},
		bindUI : function(){
			var _self = this,el = _self.get('el'),store = _self.get('store'),collectionName = _self.get('collectionName');
			// 点击路径时进入对应的目录
			el.delegate('.list-breadcrumb-item','click',function(){
				var pathATag = $(this),pId = pathATag.attr('id'),itemId = pId.split('-')[1],itemName = pathATag.text();
				_self.fire('enterDirectory',{dic : {id : itemId,name : itemName}});
			});
			// 点击文件名时进行预览或下载
			el.delegate('.fileSpan','click',function(){
				var fileATag = $(this),aId = fileATag.attr('id'),itemId = aId.split('-')[2];
				var item = store.find('id',itemId);
				var hasPageOffice = _self._hasPageOffice();
				if(hasPageOffice){//预览
					_self._previewFile(item.name,item.path);
				}else{//下载
					window.location = '/kmms/atachFile/download?name='+item.name+'&path='+item.path;
				}
			});
			// 点击目录时抛出事件给文件列表
			el.delegate('.dicSpan a','click',function(){
				var dicATag = $(this),dId = dicATag.attr('id'),itemId = dId.split('-')[2];
				var item = store.find('id',itemId);
				_self.fire('enterDirectory',{dic : item});
			});
			// 点击文件删除按钮弹出确认框，确认后完成删除
			el.delegate('.filebuttongroup .x-icon-error','click',function(){
				var delFile = $(this),dId = delFile.attr('id'),itemId = dId.split('-')[2];
		        BUI.Message.Confirm('确认要删除么？',function(){
		        	var pl = new PostLoad({
						url : '/kmms/fileShareAction/deleteById',
						el : _self.get('el'),
        				loadMsg : '删除文件中...'
					});
					pl.load({id : itemId,collectionName : collectionName},function(data){
						BUI.Message.Alert('删除成功','success');
						store.load();
					});
		        },'question');
			});
			// 点击文件编辑按钮，打开pageoffic在线编辑
			el.delegate('.filebuttongroup > .x-icon-warning','click',function(){
				var editFile = $(this),eId = editFile.attr('id'),itemId = eId.split('-')[2];
				var item = store.find('id',itemId);
				_self._previewFile(item.name,item.path,true);
			});
			// 点击文件预览按钮，打开pageoffic在线预览
			el.delegate('.filebuttongroup > .x-icon-info','click',function(){
				var editFile = $(this),eId = editFile.attr('id'),itemId = eId.split('-')[2];
				var item = store.find('id',itemId);
				_self._previewFile(item.name,item.path);
			});
			// 点击文件下载按钮，进行文件下载
			el.delegate('.filebuttongroup > .x-icon-success','click',function(){
				var downFile = $(this),dId = downFile.attr('id'),itemId = dId.split('-')[2];
				var item = store.find('id',itemId);
				window.location = '/kmms/atachFile/download?name='+item.name+'&path='+item.path;
			});
			// 点击目录删除按钮，首先判断目录下是否有子目录和文件，如果有，则不能删除；没有则完成删除
			el.delegate('.dicbuttongroup > .x-icon-error','click',function(){
				var delDic = $(this),dId = delDic.attr('id'),itemId = dId.split('-')[2]; 
				var hasChildren = new PostLoad({
					url : '/kmms/fileShareAction/findAll',
					el : _self.get('el'),
    				loadMsg : '删除目录中...'
				});
				hasChildren.load({parentId : itemId,collectionName : collectionName,start : 0,limit : 20},function(rs){
					if(rs.results > 0){
						BUI.Message.Alert('本目录下存在子目录或文件，无法删除','warning');
					}else{
						var pl = new PostLoad({
							url : '/kmms/fileShareAction/deleteById',
							el : _self.get('el'),
	        				loadMsg : '删除目录中...'
						});
						pl.load({id : itemId,collectionName : collectionName},function(data){
							BUI.Message.Alert('删除成功','success');
							store.load();
						});
					}
				});
			});
			// 点击修改目录名按钮
			el.delegate('.dicbuttongroup > .x-icon-warning','click',function(){
				var editDic = $(this),eId = editDic.attr('id'),itemId = eId.split('-')[2];
				var item = store.find('id',itemId);
				var updateDicDialog = new UpdateDic({
					perId : _self.get('perId'),
					userId : _self.get('userId'),
					orgId : _self.get('orgId'),
					orgName : _self.get('orgName'),
					item : item,
					collectionName : collectionName
				});
				updateDicDialog.show();
				updateDicDialog.on('completeAddSave',function(e){
					store.load();
				});
			});
		},
		navToggle : function(){
			var _self = this,el = _self.get('el'),nav = el.find('.fileAndDicNav');
			if(nav.css('display') == 'none'){
				nav.css('display','block');
			}else{
				nav.css('display','none');
			}
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
            buttonVisible : {view : true,value : {}},
            store : {},
            events : {
				value : {
					'enterDirectory' : true,
					'uploadFileEvent' : true
				}
            },
            xview : {
                value : fileGridView
            }
		}
	});
	fileGrid.View = fileGridView;
	return fileGrid;
});