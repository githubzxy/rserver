/**
 *重点工作完成情况
 * @author Bili
 * @date 18-11-21
 */
define('kmms/dayToJobManagement/completionOfKeyTasks/completionOfKeyTasksPage',['bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer','kmms/dayToJobManagement/completionOfKeyTasks/completionOfKeyTasksAdd','bui/calendar',
    'kmms/dayToJobManagement/completionOfKeyTasks/completionOfKeyTasksEdit','kmms/dayToJobManagement/completionOfKeyTasks/completionOfKeyTasksInfo','common/data/PostLoad'],function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        completionOfKeyTasksAdd = r('kmms/dayToJobManagement/completionOfKeyTasks/completionOfKeyTasksAdd'),
        completionOfKeyTasksEdit = r('kmms/dayToJobManagement/completionOfKeyTasks/completionOfKeyTasksEdit'),
        completionOfKeyTasksInfo = r('kmms/dayToJobManagement/completionOfKeyTasks/completionOfKeyTasksInfo'),
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var completionOfKeyTasksPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store'),orgPicker=_self.get('orgPicker');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                
//                /**
//                 * 批量删除
//                 */
//                $(".delBtn").on('click',function () {
//                   var removeIds = table.getSelection();
//                   removeIds = removeIds.map(function (item) {
//                       return item.docId;
//                   });
//                   var id = removeIds.join(",");
//                   if(!id){
//                       tbar.msg({status:0,msg:'至少选择一项要删除的项目！'})
//                   }else{
//                	   BUI.Message.Confirm('确认要删除吗？',function(){
//	                       var postLoad = new PostLoad({
//	                           url : '/kmms/completionOfKeyTasksAction/removeDoc.cn',
//	                           el : _self.get('el'),
//	                           loadMsg : '删除中...'
//	                       });
//	                       postLoad.load({id:id,collectionName:_self.get('collectionName')},function (res) {
//	                    	   tbar.msg(res);
//	                    	   store.load();
//	                       });
//                	   },'question');
//                   }
//                });
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new completionOfKeyTasksAdd({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                    });
                    addDialog.show();
                    addDialog.on('completeAddSave',function(e){
                        tbar.msg(e.result);
                        addDialog.close();
                        store.load();
                    });
                });
                /**
                 * 操作按钮
                 */
                table.on('cellclick',function(ev){
                    var record = ev.record, //点击行的记录
                        target = $(ev.domTarget),
                        docId = record.docId; //点击的元素
                    /**
                     * 详情
                     */
                    if(target.hasClass('infoBtn')){
                        var infoDialog = new completionOfKeyTasksInfo({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            shiftId:docId
                        });
                        infoDialog.show();
                    }
                    /**
              	   * 删除
              	   */
		          	  if(target.hasClass('delBtn')){
		               	   BUI.Message.Confirm('确认要删除吗？',function(){
			                       var postLoad = new PostLoad({
			                           url : '/kmms/otherProductionInfoAction/removeDoc.cn',
			                           el : _self.get('el'),
			                           loadMsg : '删除中...'
			                       });
			                       postLoad.load({id:docId,collectionName:_self.get('collectionName')},function (res) {
			                    	   tbar.msg(res);
			                    	   store.load();
			                       });
		               	   },'question');
		                }	  
                    /**
                     * 编辑
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new completionOfKeyTasksEdit({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            shiftId:docId
                        });
                        editDialog.show();
                        editDialog.on('completeAddSave',function(e){
                            tbar.msg(e.result);
                            editDialog.close();
                            store.load();
                        });
                    }
                });
            },
            /**
             * 初始化上查询下列表
             * @private
             */
            _initSearchGridContainer:function(){
                var _self = this;
                var searchGridContainer = new SearchGridContainer({
                    searchForm : _self._initSearchForm(),
                    columns : _self._initColumns(),
                    store : _self._initStore(),
                    searchGrid : _self._initSearchGrid()
                });
                return searchGridContainer;
            },
            /**
             * 初始化查询表单
             * @private
             */
            _initSearchForm:function(){
                return {
                    items : [
                        {label : '开始时间',item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'},
                        {label : '结束时间',item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" style="width: 175px;" class="calendar" readonly/>'}
                    ]};
            },
            /**
             * 初始化时间查询
             * @private
             */
            _initDate: function () {
                var _self = this;
                var startUploadDate = new Calendar.DatePicker({//加载日历控件
                    trigger: '#startUploadDateSearch',
                    showTime: true,
                    autoRender: true,
                    textField:'#startUploadDateSearch'
                });
                var endUploadDateSearch = new Calendar.DatePicker({//加载日历控件
                    trigger: '#endUploadDateSearch',
                    showTime: true,
                    autoRender: true,
                    textField:'#endUploadDateSearch'
                });
                _self.set('startUploadDate', startUploadDate);
                _self.set('endUploadDateSearch', endUploadDateSearch);
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                    {
                        title:'日期',
                        dataIndex:'noticeDateStr',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'工作项目',
                        dataIndex:'workProgram',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'完成情况',
                        dataIndex:'noticeContent',
                        elCls : 'center',
                        width:'25%',
                    },{
                        title:'备注',
                        dataIndex:'remark',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'操作',
                        dataIndex:'summaryDate',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (e) {
//                        	if(e==''){
//                        		return '<span  class="grid-command infoBtn">详情</span>'+
//                        		'<span  class="grid-command editBtn">修改</span>';
                        		return '<span  class="grid-command editBtn">修改</span>'+
                            	'<span  class="grid-command delBtn">删除</span>'+
                        		'<span  class="grid-command infoBtn">详情</span>';
//                        	}else{
//                        		return '<span  class="grid-command infoBtn">详情</span>'
//                        	}
                        }
                    }
                ];
                return columns;
            },
            /**
             * 初始化列表数据对象
             * @private
             */
            _initStore:function () {
                var _self = this;
                var store = new Data.Store({
                    url : "/kmms/completionOfKeyTasksAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 10,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName'),userId:_self.get('userId')}//当前登陆人ID用于判断是否需要查询展示的数据}
                });
                _self.set('store',store);
                return store;
            },
            /**
             * 初始化列表展示对象
             * @private
             */
            _initSearchGrid:function () {
                var searchGrid = {
                    tbarItems : [
                        {
                            id : 'add',
                            btnCls : 'button button-small addBtn',
                            text : '<i class="icon-plus"></i>新增',
                        }
//                        ,{
//                            xclass : 'bar-item-separator' // 竖线分隔符
//                        },{
//                            id : 'del',
//                            btnCls : 'button button-small delBtn',
//                            text : '<i class="icon-remove"></i>批量删除',
//                        }
                    ],
                    plugins : [
                        Grid.Plugins.CheckSelection,
                        Grid.Plugins.RowNumber
                    ],
                };
                return searchGrid;
            }
        },
        {
            ATTRS : {
                /**
                 * 当前页ID
                 */
                perId : {},
                /**
                 * 当前用户Id
                 */
                userId : {},//登录用户ID
                userName : {},//登录用户名称
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                /**
                 * 存储表名
                 */
                collectionName : {value:'completionOfKeyTasks'}
            }
        });
    return completionOfKeyTasksPage;
});