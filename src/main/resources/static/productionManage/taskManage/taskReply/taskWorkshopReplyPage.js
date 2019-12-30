/**
 * 临时任务车间回复主页
 * @author zhouxingyu
 * @date 19-5-13
 */
define('kmms/productionManage/taskManage/taskReply/taskWorkshopReplyPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/productionManage/taskManage/taskReply/taskWorkshopReplyAdd',
	 	'kmms/productionManage/taskManage/taskReply/taskWorkshopReplyDistribute',
	 	'kmms/productionManage/taskManage/taskApply/taskApplyDetail',
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	taskWorkshopReplyAdd = r('kmms/productionManage/taskManage/taskReply/taskWorkshopReplyAdd'),
    	taskWorkshopReplyDistribute = r('kmms/productionManage/taskManage/taskReply/taskWorkshopReplyDistribute'),
    	taskApplyDetail = r('kmms/productionManage/taskManage/taskApply/taskApplyDetail');
    var taskWorkshopReplyPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                _self._initFlowState();
                _self._initSystemType();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#taskName").val("");
    				$("#systemType").val("");
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
    				$("#flowState").val("");
    			});
                
                /**
                 * 操作按钮
                 */
                table.on('cellclick',function(ev){
                    var record = ev.record, //点击行的记录
                    	  target = $(ev.domTarget),
                    	  docId = record.docId; //点击的元素（数据库主键的值String）
                    	  busiId = record.busiId;//自定义的业务ID
                    /**
                     * 详情
                     */
                    if(target.hasClass('detailBtn')){
                        var infoDialog = new taskApplyDetail({
                            collectionName:_self.get('task'),
                            shiftId:docId
                        });
                        infoDialog.show();
                    }
                    /**
                     * 车间回复
                     */
                    if(target.hasClass('replyBtn')){
                        var approveDialog = new taskWorkshopReplyAdd({
                        	IssueTaskOfWorkShopReply:_self.get('IssueTaskOfWorkShopReply'),
                        	IssueTaskOfWorkShop:_self.get('IssueTaskOfWorkShop'),
                            userId : _self.get("userId"),
                            userName : _self.get("userName"),
                            orgId : _self.get("orgId"),
                            orgName : _self.get("orgName"),
                            shiftId:docId
                        });
                        approveDialog.show();
                        approveDialog.on('completeAddSave',function(e){
                            tbar.msg(e.result);
                            approveDialog.close();
                            store.load();
                        });
                    }
                    /**
                     * 派发工区
                     */
                    if(target.hasClass('distributeBtn')){
                    	var approveDialog = new taskWorkshopReplyDistribute({
                    		collectionName:_self.get('IssueTaskOfWorkShop'),
                    		userId : _self.get("userId"),
                    		userName : _self.get("userName"),
                    		orgId : _self.get("orgId"),
                    		orgName : _self.get("orgName"),
                    		shiftId:docId
                    	});
                    	approveDialog.show();
                    	approveDialog.on('completeAddSave',function(e){
                    		tbar.msg(e.result);
                    		approveDialog.close();
                    		store.load();
                    	});
                    }
                    /**
                     * 点击完成则结束该工单的回复流程状态修改为8（已完成）
                     */
                    if(target.hasClass('finishBtn')){
                    	BUI.Message.Confirm('确认要完成吗？完成后则不能继续回复，请确认是否任务已完成！',function(){
 	                       var postLoad = new PostLoad({
 	                           url : '/kmms/taskWorkshopReplyAction/finishDoc.cn',
 	                           el : _self.get('el'),
 	                           loadMsg : '执行中...'
 	                       });
 	                       postLoad.load({id:docId,executiveOrgId:_self.get("orgId"),flowState:"8",collectionName:_self.get('IssueTaskOfWorkShop')},function (res) {
 	                    	   tbar.msg(res);
 	                    	   store.load();
 	                       });
                 	   },'question');
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
                        {
                        	label : '任务标题',
                        	item : '<input type="text" name="taskName" id="taskName" style="width: 175px;"/>'
                        },
                        {
                        	label : '任务类别',
                        	item : '<select name="systemType" id="systemType" style="width: 210px;"><option value="">请选择</option></select>'
                        },
                        {
                        	label : '开始时间',
                        	item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
                        },
                        {
                        	label : '结束时间',
                        	item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
                        },
                        {
                        	label : '状态',
                        	item : '<select name="flowState" id="flowState" style="width: 210px;"><option value="">请选择</option></select>'
                        },
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
                var endUploadDate = new Calendar.DatePicker({//加载日历控件
                    trigger: '#endUploadDateSearch',
                    showTime: true,
                    autoRender: true,
                    textField:'#endUploadDateSearch'
                });
                _self.set('startUploadDate', startUploadDate);
                _self.set('endUploadDate', endUploadDate);
            },
            
            _initFlowState: function () {
    			$("#flowState").append("<option value='6'>待回复</option>");//审核通过
    			$("#flowState").append("<option value='6.5'>待完成</option>");//审核通过
    			$("#flowState").append("<option value='7'>已派发</option>");//派发工区
    			$("#flowState").append("<option value='8'>已完成</option>");//车间直接回复
//    			$("#flowState").append("<option value='-1'>已结束</option>");
            },
            
            _initSystemType: function () {
            	$("#systemType").append("<option  value='电报'>电报</option>");
            	$("#systemType").append("<option  value='无线'>无线</option>");
            	$("#systemType").append("<option  value='有线'>有线</option>");
            	$("#systemType").append("<option  value='高铁'>高铁</option>");
            	$("#systemType").append("<option  value='通用'>通用</option>");
            	$("#systemType").append("<option  value='秋鉴'>秋鉴</option>");
            },
            
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                	{
                        title:'任务标题',
                        dataIndex:'taskName',
                        elCls : 'center',
                        width:'35%'
                    },{
                        title : '任务类别',
                        dataIndex:'systemType',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title : '下发时间',
                        dataIndex:'issueDate',
                        elCls : 'center',
                        width:'30%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
        				title : '状态',
        				dataIndex:'flowState',
        				elCls : 'center',
        				width : '10%',
        				renderer : function(e) {
        					if(e == "6"){
        						return "待回复"//审批通过
        					} else if(e == "6.5"){
        						return "待完成"
        					} else if(e == "7"){
        						return "已派发"
        					} else if(e == "8"){
        						return "已完成"
        					}
        				}
        			},{
                        title:'操作',
                        dataIndex:'flowState',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (value) {
                        	var button = '';
                        	if(value=='6'){
                        		button += '<span  class="grid-command replyBtn">回复</span>'+'<span  class="grid-command distributeBtn">派发</span>';
                        	}
                        	if(value=='6.5'){
                        		button += '<span  class="grid-command replyBtn">回复</span>'+'<span  class="grid-command finishBtn">完成</span>';
                        	}
                        	button += '<span  class="grid-command detailBtn">详情</span>';
                            return button;
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
                    url : "/kmms/taskWorkshopReplyAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 20,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('IssueTaskOfWorkShop'),orgId:_self.get('orgId')}//当前登陆人组织机构ID用于判断是否需要查询展示的数据
                });
                _self.set('store',store);
                return store;
            },
            
//            _initPermissionStore:function () {
//                var _self = this;
//                var store = new Data.Store({
//                    url : "/kmms/permission/getBtnPers.cn",
//                    autoLoad : true ,
//                    proxy : {
//                        method : 'post',
//                        dataType : 'json'
//                    },
//                    params : {perId:_self.get('perId'),userId:_self.get('userId')}
//                });
//                _self.set('store',store);
//                return store;
//            },
            
            /**
             * 初始化列表展示对象
             * @private
             */
            _initSearchGrid:function () {
//            	var _self = this;
                var searchGrid = {
                    tbarItems : [],
                    plugins : [
                        Grid.Plugins.CheckSelection,
                        Grid.Plugins.RowNumber
                    ],
//                    permissionStore : _self._initPermissionStore()
                };
                return searchGrid;
            }
        },
        {
            ATTRS : {
                perId : {},
                userId : {},//登录用户ID
                userName : {},//登录用户名称
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                parentId : {},//登录用户上级组织机构ID
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                IssueTaskOfWorkShopReply : {value:'IssueTaskOfWorkShopReply'},
                IssueTaskOfWorkShop : {value:'IssueTaskOfWorkShop'},//存储表名（下发子单）
                task : {value:'task'}//存储表名（主单）
            }
        });
    	return taskWorkshopReplyPage;
});