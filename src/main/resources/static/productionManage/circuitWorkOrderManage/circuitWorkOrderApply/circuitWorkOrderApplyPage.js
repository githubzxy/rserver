/**
 * 电路工单申请主页
 * @author yangsy
 * @date 19-2-28
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyAdd',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyModify',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyDetail',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplySubmit',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyCheckEdit',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyApproveEdit',
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	circuitWorkOrderApplyAdd = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyAdd'),
    	circuitWorkOrderApplyModify = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyModify'),
    	circuitWorkOrderApplyDetail = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyDetail'),
    	circuitWorkOrderApplySubmit = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplySubmit'),
    	circuitWorkOrderApplyCheckEdit = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyCheckEdit'),
    	circuitWorkOrderApplyApproveEdit = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyApproveEdit');
    var PointInnerSecondMaintainApplyPage = BUI.Component.Controller.extend(
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
                 * 批量删除
                 */
                $(".delBtn").on('click',function () {
                   var removeIds = table.getSelection();
                   removeIds = removeIds.map(function (item) {
                       return item.docId;
                   });
                   var id = removeIds.join(",");
                   if(!id){
                       tbar.msg({status:0,msg:'至少选择一项要删除的项目！'})
                   }else{
                	   BUI.Message.Confirm('确认要删除吗？',function(){
	                       var postLoad = new PostLoad({
	                           url : '/kmms/circuitWorkOrderApplyAction/removeDoc.cn',
	                           el : _self.get('el'),
	                           loadMsg : '删除中...'
	                       });
	                       postLoad.load({id:id,collectionName:_self.get('collectionName')},function (res) {
	                    	   tbar.msg(res);
	                    	   store.load();
	                       });
                	   },'question');
                   }
                });
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new circuitWorkOrderApplyAdd({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            userName:_self.get('userName'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                            parentId:_self.get('parentId'),
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
                     * 草稿编辑
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new circuitWorkOrderApplyModify({
                            collectionName:_self.get('collectionName'),
                            shiftId:docId
                        });
                        editDialog.show();
                        editDialog.on('completeAddSave',function(e){
                            tbar.msg(e.result);
                            editDialog.close();
                            store.load();
                        });
                    }
                    /**
                     * 提交审核编辑
                     */
                    if(target.hasClass('submitBtn')){
                    	var submitDialog = new circuitWorkOrderApplySubmit({
                    		collectionName:_self.get('collectionName'),
                    		shiftId:docId
                    	});
                    	submitDialog.show();
                    	submitDialog.on('completeAddSave',function(e){
                    		tbar.msg(e.result);
                    		submitDialog.close();
                    		store.load();
                    	});
                    }
                    /**
                     * 审核不通过的编辑
                     */
                    if(target.hasClass('checkEditBtn')){
                    	var editDialog = new circuitWorkOrderApplyCheckEdit({
                    		collectionName:_self.get('collectionName'),
                    		shiftId:docId
                    	});
                    	editDialog.show();
                    	editDialog.on('completeAddSave',function(e){
                    		tbar.msg(e.result);
                    		editDialog.close();
                    		store.load();
                    	});
                    }
                    /**
                     * 审批不通过的编辑 TODO 段领导审批流程废弃
                     */
//                    if(target.hasClass('approveEditBtn')){
//                    	var editDialog = new circuitWorkOrderApplyApproveEdit({
//                    		collectionName:_self.get('collectionName'),
//                    		shiftId:docId
//                    	});
//                    	editDialog.show();
//                    	editDialog.on('completeAddSave',function(e){
//                    		tbar.msg(e.result);
//                    		editDialog.close();
//                    		store.load();
//                    	});
//                    }
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
            	$("#flowState").append("<option value='0'>草稿</option>");
    			$("#flowState").append("<option value='1'>待签收</option>");//待网管中心签收
    			$("#flowState").append("<option value='2'>已签收</option>");//待网管中心编辑附件
    			$("#flowState").append("<option value='3'>待提交</option>");//网管中心编辑附件完成待提交技术科科长
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
        					if(e == "0") {
        						return "草稿";
        					} else if(e == "1") {
        						return "待签收";
        					} else if(e == "2") {
        						return "已签收";
        					} else if(e == "3") {
        						return "待提交";
        					} else if(e == "4") {
        						return "待审核";
        					} else if(e == "5") {
        						return "已回退";
        					}  else if(e == "6"){
        						return "待回复"
        					} else if(e == "-1"){
        						return "已结束"
        					}
        				}
        			},{
                        title:'操作',
                        dataIndex:'flowState',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (value) {
                        	var button = '';
                        	if(value=='0'){
                        		button += '<span  class="grid-command editBtn">修改</span>';
                        	} else if(value=='3'){
                        		button += '<span  class="grid-command submitBtn">提交</span>';
                        	} else if(value=='5'){
                        		button += '<span  class="grid-command checkEditBtn">重新编辑</span>';
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
                    url : "/kmms/circuitWorkOrderApplyAction/findAll.cn",
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
                    tbarItems : [
                        {
                            id : 'add',
                            btnCls : 'button button-small addBtn',
                            text : '<i class="icon-plus"></i>新增',
                        },{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                            id : 'del',
                            btnCls : 'button button-small delBtn',
                            text : '<i class="icon-remove"></i>批量删除',
                        }
                    ],
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
    	return PointInnerSecondMaintainApplyPage;
});