/**
 * 维修方案管理申请主页
 * @author yangli
 * @date 19-2-20
 */
define('kmms/maintainManage/maintainPlan/maintainApply/maintainApplyPage',[
	'bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'bui/calendar',
    'common/org/OrganizationPicker',
    'common/data/PostLoad',
    'kmms/maintainManage/maintainPlan/maintainApply/maintainApplyAdd',
    'kmms/maintainManage/maintainPlan/maintainApply/maintainApplyEdit',
    'kmms/maintainManage/maintainPlan/maintainApply/maintainApplyInfo',
    ],function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker');
        maintainApplyAdd = r('kmms/maintainManage/maintainPlan/maintainApply/maintainApplyAdd'),
        maintainApplyEdit = r('kmms/maintainManage/maintainPlan/maintainApply/maintainApplyEdit'),
        maintainApplyInfo = r('kmms/maintainManage/maintainPlan/maintainApply/maintainApplyInfo'),
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var MaintainPlan = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                $("#auditStatus").append("<option  value='0'>草稿</option>");
    			$("#auditStatus").append("<option  value='1'>待审核</option>");
    			$("#auditStatus").append("<option  value='2'>待审批</option>");
    			$("#auditStatus").append("<option  value='3'>等待调度科审核</option>");
    			$("#auditStatus").append("<option  value='4'>等待安全科审核</option>");
    			$("#auditStatus").append("<option  value='5'>审批通过</option>");
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
    			//重写重置按钮的点击事件
//    			$("button[type='reset']").on('click',function(event){
//    				event.preventDefault();
//    				$("#selectName").val("");
//    				$("#orgSelectId").val("");
//    				$("#orgSelectName").val("");
//    				$("#startUploadDateSearch").val("");
//    				$("#endUploadDateSearch").val("");
//    				$("#auditStatus").val("");
//    			});
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
                               url : '/kmms/commonAction/removeDoc.cn',
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
                    var addDialog = new maintainApplyAdd({
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
                        docId = record.docId; //点击的元素
                    /**
                     * 详情
                     */
                    if(target.hasClass('infoBtn')){
                        var infoDialog = new maintainApplyInfo({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            shiftId:docId
                        });
                        infoDialog.show();
                    }
                    /**
                     * 修改
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new maintainApplyEdit({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            userName:_self.get('userName'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                            parentId:_self.get('parentId'),
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
                        {
                        	
                        	label : '维修项目',
                        	item : '<input type="text" name="name" id="selectName" style="width: 175px;"/>'
                        },
                        {
                        	label : '开始时间',
                        	item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
                        },
                        {
                        	label : '结束时间',
                        	item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" style="width: 175px;" class="calendar" readonly/>'
                        },
                        {
        	  				label : '审核状态',
        	  				item : '<select name="auditStatus" id="auditStatus" style="width: 201px;" ><option value="" >请选择</option></select>'
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
                        title:'维修项目',
                        dataIndex:'name',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'提交部门',
                        dataIndex:'depart',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title:'创建时间',
                        dataIndex:'createDate',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    }
                    ,{
                        title:'创建人',
                        dataIndex:'userName',
                        elCls : 'center',
                        width:'15%',
                    },{
        				title : '审核状态',
        				dataIndex : 'auditStatus',
        				elCls : 'center',
        				width : '15%',
        				renderer : function(e) {
        					if(e == "0") {
        						return "草稿";
        					} else if(e == "1") {
        						return "待审核";
        					} else if(e == "2") {
        						return "待审批";
        					} else if(e == "3") {
        						return "等待调度科审核";
        					} else if(e == "4") {
        						return "等待安全科审核";
        					} else if(e == "5") {
        						return "审批通过";
        					} 
        				}
        			},{
                        title:'操作',
                        dataIndex:'auditStatus',
                        elCls : 'center',
                        width:'15%',
                        renderer:function (e) {
                        	if(e == "0"){
                        		return '<span  class="grid-command editBtn">修改</span>'+
                        		'<span  class="grid-command infoBtn">详情</span>';
                        	}else{
                        		return '<span  class="grid-command infoBtn">详情</span>';
                        	}
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
                    url : "/kmms/maintainApplyAction/findByUserId.cn",
                    autoLoad : true ,
                    pageSize : 10,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName'),userId:_self.get('userId')}
                });
                _self.set('store',store);
                return store;
            },
            
            
            /**
             * 初始化列表展示对象
             * @private
             */
            _initSearchGrid:function () {
            	var _self = this;
                var searchGrid = {
                    tbarItems : [
                        {
                            id : 'add',
                            btnCls : 'button button-small addBtn',
                            text : '<i class="icon-plus"></i>申请',
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
                /**
                 * 当前页ID
                 */
                perId : {},
                /**
                 * 当前用户Id
                 */
                userId : {},
                userName : {},
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                parentId : {},//上一级id
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                /**
                 * 存储表名(维修方案表名)
                 */
                collectionName : {value:'maintainPlan'}
            }
        });
    return MaintainPlan;
});