/**
 * 目录及文件列表上单行，包括上下两部分
 * 上面部分为首次渲染时隐藏的路径
 * 下面部分为目录及文件名
 * 
 * @author roysong
 * @date 190518
 */
define('kmms/integratedManage/fileShare/fileItem',
		['bui/common','bui/data','bui/list','bui/toolbar',
			'bui/overlay','common/data/PostLoad',
			'kmms/integratedManage/fileShare/fileRow'
		],
		function(r){
	var BUI = r('bui/common'),
    Data = r('bui/data'),
    List = r('bui/list'),
    Toolbar = r('bui/toolbar'),
    Overlay = r('bui/overlay'),
    PostLoad = r('common/data/PostLoad'),
    FileRow = r('kmms/integratedManage/fileShare/fileRow'),
    UpdateDic = r('kmms/integratedManage/fileShare/updateDic');
	var fileItem = List.ListItem.extend({
		initializer : function(){
			var _self = this,item = _self.get('item'),store = new Data.Store({data : item.nav});
			var topNav = new Toolbar.Breadcrumb({
				id : 'itemNav',
				elStyle : {'background-color': '#eeeeee','font-size': 'smaller','padding':'0'},
				elCls : 'fileAndDicNav',
				store : store
			});
			topNav.hide();
			_self.addChild(topNav);
			var fileRow = new FileRow({
				item : item,
				perId : _self.get('perId'),
				userId : _self.get('userId'),
				orgId : _self.get('orgId'),
				orgName : _self.get('orgName'),
				collectionName : _self.get('collectionName'),
				buttonVisible : _self.get('buttonVisible')
			});
			_self.addChild(fileRow);
		},
		bindUI : function(){
			var _self = this,nav = _self.getChild('itemNav');
			// 点击路径上的目录进入指定目录
			nav.on('jumpToDirectory',function(e){
				console.log(e)
				_self.fire('enterDirectory',e.dic);
			});
		},
		showNav : function(){
			var _self = this,nav = _self.getChild('itemNav');
			nav.show();
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
            userId : {},// 登录用户ID
            orgId : {},// 登录用户组织机构ID
            orgName : {},// 登录用户组织机构名称
            collectionName : {},
            buttonVisible : {view : true},
            item : {view : true},
            tpl : {view: true,value : '<div class="dicAndFile" style="border-bottom:1px dashed royalblue"></div>'},
            childContainer : {value : '.dicAndFile'},
            events : {
				value : {
					'enterDirectory' : true,
					'dicEditOver' : true,
					'dicDelOver' : true,
					'fileDelOver' : true,
				}
            }
		}
	});
	return fileItem;
});