/**
 * 电路工单工区回复主页
 * @author yangsy
 * @date 19-3-6
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderReply/circuitWorkOrderWorkAreaReplyPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderReply/circuitWorkOrderWorkAreaReplyAdd',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyDetail',
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	circuitWorkOrderWorkAreaReplyAdd = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderReply/circuitWorkOrderWorkAreaReplyAdd'),
    	circuitWorkOrderApplyDetail = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyDetail');
    var CircuitWorkOrderWorkAreaReplyPage = BUI.Component.Controller.extend(
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
    				$("#workOrderName").val("");
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
//                        console.log(record);
                    /**
                     * 详情
                     */
                    if(target.hasClass('detailBtn')){
                        var infoDialog = new circuitWorkOrderApplyDetail({
                            collectionName:_self.get('circuitWorkOrder'),
                            shiftId:docId
                        });
                        infoDialog.show();
                    }
                    /**
                     * 工区回复
                     */
                    if(target.hasClass('replyBtn')){
                        var approveDialog = new circuitWorkOrderWorkAreaReplyAdd({
                        	circuitDistributeWorkOrderReply:_self.get('circuitDistributeWorkOrderReply'),
                            circuitDistributeWorkOrder:_self.get('circuitDistributeWorkOrder'),
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
                    	BUI.Message.Confirm('确认要完成吗？完成后则不能继续回复，请确认是否工单已完成！',function(){
 	                       var postLoad = new PostLoad({
 	                           url : '/kmms/circuitWorkOrderWorkAreaReplyAction/finishDoc.cn',
 	                           el : _self.get('el'),
 	                           loadMsg : '执行中...'
 	                       });
 	                       postLoad.load({id:docId,distributeOrgId:_self.get("orgId"),flowState:"8",collectionName:_self.get('circuitDistributeWorkOrder')},function (res) {
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
                        	label : '工单名称',
                        	item : '<input type="text" name="workOrderName" id="workOrderName" style="width: 175px;"/>'
                        },
                        {
                        	label : '系统类别',
                        	item : '<select name="systemType" id="systemType" style="width: 200px;"><option value="">请选择</option></select>'
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
                        	item : '<select name="flowState" id="flowState" style="width: 200px;"><option value="">请选择</option></select>'
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
            
            _initSystemType: function () {
            	$("#systemType").append("<option  value='传输接入系统'>传输接入系统</option>");
            	$("#systemType").append("<option  value='数据网系统'>数据网系统</option>");
            	$("#systemType").append("<option  value='GSM-R系统'>GSM-R系统</option>");
            	$("#systemType").append("<option  value='其他'>其他</option>");
            },
            
            _initFlowState: function () {
    			$("#flowState").append("<option value='7'>待回复</option>");
    			$("#flowState").append("<option value='7.5'>待完成</option>");
    			$("#flowState").append("<option value='8'>已完成</option>");
//    			$("#flowState").append("<option value='-1'>已结束</option>");
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                	{
                        title:'工单名称',
                        dataIndex:'workOrderName',
                        elCls : 'center',
                        width:'35%'
                    },{
                        title : '系统类别',
                        dataIndex:'systemType',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title : '下发时间',
                        dataIndex:'distributeDate',
                        elCls : 'center',
                        width:'30%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
        				title : '状态',
        				dataIndex:'flowState',
        				elCls : 'center',
        				width : '10%',
        				renderer : function(e) {
        					if(e == "7"){
        						return "待回复"
        					}  else if(e == "7.5"){
        						return "待完成"
        					}  else if(e == "8"){
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
                        	if(value=='7'){
                        		button += '<span  class="grid-command replyBtn">回复</span>';
                        	}
                        	if(value=='7.5'){
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
                    url : "/kmms/circuitWorkOrderWorkAreaReplyAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 20,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('circuitDistributeWorkOrder'),orgId:_self.get('orgId')}//当前登陆人组织机构ID用于判断是否需要查询展示的数据
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
                circuitDistributeWorkOrderReply : {value:'circuitDistributeWorkOrderReply'},//存储表名（派发子单的回复子单：存放车间直接回复的内容）
                circuitDistributeWorkOrder : {value:'circuitDistributeWorkOrder'},//存储表名（派发子单）
                circuitIssueWorkOrder : {value:'circuitIssueWorkOrder'},//存储表名（下发子单）
                circuitWorkOrder : {value:'circuitWorkOrder'}//存储表名（主单）
            }
        });
    	return CircuitWorkOrderWorkAreaReplyPage;
});