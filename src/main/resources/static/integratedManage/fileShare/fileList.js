/**
 * 目录及文件列表下面部分是当前目录下的所有目录及文件列表，带分页 每个目录或文件分为上下两部分，上面是路径，下面是文件或目录名及操作按钮
 * 
 * @author roysong
 * @date 190518
 */
define('kmms/integratedManage/fileShare/fileList',
		['bui/common','bui/data','bui/list','bui/toolbar',
			'bui/overlay','common/data/PostLoad',
			'kmms/integratedManage/fileShare/fileItem'
		],
		function(r){
	var BUI = r('bui/common'),
	Component = BUI.Component,
    UIBase = Component.UIBase,
    Data = r('bui/data'),
    List = r('bui/list'),
    Toolbar = r('bui/toolbar'),
    Overlay = r('bui/overlay'),
    FileItem = r('kmms/integratedManage/fileShare/fileItem'),
    PostLoad = r('common/data/PostLoad');
	var fileList = List.List.extend([UIBase.Bindable],{
		bindUI : function(){
			var _self = this,store = _self.get('store');
			_self.on('dicEditOver',function(item){
				store.load();
			});
			_self.on('dicDelOver',function(item){
				store.load();
			});
			_self.on('fileDelOver',function(item){
				store.load();
			});
		},
		/**
		 * 显示每条记录上方的路径栏
		 */
		showItemNav : function(){
			var _self = this,items = _self.get('items');
			BUI.each(items,function(item){
				item.showNav();
			});
		},
		/**
		 * 将后台返回的数据转化为子组件
		 */
		_recordToItem : function(record){
			var _self = this;
			return new FileItem({
				item : record,
				perId : _self.get('perId'),
				userId : _self.get('userId'),
				orgId : _self.get('orgId'),
				orgName : _self.get('orgName'),
				collectionName : _self.get('collectionName'),
				buttonVisible : _self.get('buttonVisible')
			});
		},
		/**
		 * 添加
		 * @protected
		 */
	  	onAdd : function(e){
	  	  var _self = this,
	  	    store = _self.get('store'),
	  	    item = _self._recordToItem(e.record);
	  	  if(_self.getCount() == 0){
	  	    _self.set('items',[item]);
	  	  }else{
	  	    _self.addItemAt(item,e.index);
	  	  }
	  	},
	  	/**
		 * 删除
		 * 
		 * @protected
		 */
	  	onRemove : function(e){
	  	  var _self = this,
	  	    item = _self._recordToItem(e.record);
	  	  _self.removeItem(item);
	  	},
	  	/**
		 * 加载数据
		 * 
		 * @protected
		 */
	  	onLoad:function(){
	  	  var _self = this,
	  	    store = _self.get('store'),
	  	    records = store.getResult(),
	  	    items = [];
	  	  BUI.each(records,function(r){
	  		  items.push(_self._recordToItem(r));
	  	  });
	  	  _self.set('items',items);
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
            buttonVisible : {},
            events : {
				value : {
					'enterDirectory' : true,
					'dicEditOver' : false,
					'dicDelOver' : false,
					'fileDelOver' : false,
				}
            }
		}
	});
	return fileList;
});