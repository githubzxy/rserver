/**
 * 电路工单技术科科长审核主页
 * @author yangsy
 * @date 19-3-4
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderCheck/circuitWorkOrderCheckPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderCheck/circuitWorkOrderCheckAdd',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyDetail',
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	circuitWorkOrderCheckAdd = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderCheck/circuitWorkOrderCheckAdd'),
    	circuitWorkOrderApplyDetail = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyDetail');
    var CircuitWorkOrderCheckPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                _self._initSystemType();
                _self._initFlowState();
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
                    /**
                     * 详情
                     */
                    if(target.hasClass('detailBtn')){
                        var infoDialog = new circuitWorkOrderApplyDetail({
                            collectionName:_self.get('collectionName'),
                            shiftId:docId
                        });
                        infoDialog.show();
                    }
                    /**
                     * 审核
                     */
                    if(target.hasClass('checkBtn')){
                        var checkDialog = new circuitWorkOrderCheckAdd({
                            collectionName:_self.get('collectionName'),
                            userId : _self.get("userId"),
                            userName : _self.get("userName"),
                            shiftId:docId
                        });
                        checkDialog.show();
                        checkDialog.on('completeAddSave',function(e){
                            tbar.msg(e.result);
                            checkDialog.close();
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
        	  				item : '<select name="flowState" id="flowState" style="width: 210px;"><option value="">请选择</option></select>'
        	  			}
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
            	$("#flowState").append("<option value='4'>待审核</option>");//提交到技术科科长待审核
    			$("#flowState").append("<option value='5'>已回退</option>");//回退（审核不通过）
    			$("#flowState").append("<option value='6'>待回复</option>");//技术科科长审核通过
    			$("#flowState").append("<option value='-1'>已结束</option>");//所有消息都已回复
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
                        width:'25%'
                    },{
                        title : '系统类别',
                        dataIndex:'systemType',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title : '创建时间',
                        dataIndex:'createDate',
                        elCls : 'center',
                        width:'25%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title : '创建人',
                        dataIndex:'createUserName',
                        elCls : 'center',
                        width:'15%',
                    },{
        				title : '状态',
        				dataIndex:'flowState',
        				elCls : 'center',
        				width : '10%',
        				renderer : function(e) {
        					if(e == "4") {
        						return "待审核";
        					} else if(e == "5") {
        						return "已回退";//审核不通过
        					} else if(e == "6"){
        						return "待回复";//待执行部门回复
        					} else if(e == "-1"){
        						return "已结束"//全部回复
        					}
        				}
        			},{
                        title:'操作',
                        dataIndex:'flowState',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (value) {
                        	var button = '';
                        	if(value=='4'){
                        		button += '<span  class="grid-command checkBtn">审核</span>';
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
                    url : "/kmms/circuitWorkOrderCheckAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 20,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName'),userId:_self.get('userId')}//当前登陆人ID用于判断是否需要查询展示的数据
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
                collectionName : {value:'circuitWorkOrder'}//存储表名
            }
        });
    	return CircuitWorkOrderCheckPage;
});