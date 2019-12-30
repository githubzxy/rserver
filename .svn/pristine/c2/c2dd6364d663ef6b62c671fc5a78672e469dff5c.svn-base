/**
 * 事故故障障碍管理主页
 * @author yangsy
 * @date 18-12-12
 */
define('kmms/dailyShift/fault/faultPage',['bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer','kmms/dailyShift/fault/faultAdd','bui/calendar','common/org/OrganizationPicker',
    'kmms/dailyShift/fault/faultEdit','kmms/dailyShift/fault/faultInfo','common/data/PostLoad'],function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker');
    	faultAdd = r('kmms/dailyShift/fault/faultAdd'),
    	faultEdit = r('kmms/dailyShift/fault/faultEdit'),
    	faultInfo = r('kmms/dailyShift/fault/faultInfo'),
        SearchGridContainer = r('common/grid/SearchGridContainer');
    var SEARCH_FORM_ID = 'searchForm';
    var FaultPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                _self._initOrganizationPicker();
                _self._initFaultType();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store'),orgPicker=_self.get('orgPicker');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                /**
                 * 组织机构选择
                 */
                orgPicker.on('orgSelected',function (e) {
                    $('#departmentName').val(e.org.text);
        		    $('#departmentId').val(e.org.id);
                });
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#location").val("");
    				$("#faultType").val("");
    				$("#departmentName").val("");
    				$("#departmentId").val("");
    				$("#startTimeSearch").val("");
    				$("#endTimeSearch").val("");
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
	                           url : '/kmms/faultManagementAction/removeDoc.cn',
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
                    var addDialog = new faultAdd({
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
                /**导出*/
                $('.exportBtn').on('click',function(e){
                	var records = store.getResult();
					if(records.length != 0){
						_self._exportExcel();
					}else{
						tbar.msg({msg:'没有数据可以导出！',status:'0'});
					}
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
                        var infoDialog = new faultInfo({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            shiftId:docId
                        });
                        infoDialog.show();
                    }
                    /**
                     * 编辑
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new faultEdit({
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
			 * 导出本页数据
			 */
            _exportExcel:function(){
				var _self = this;
				$("#exportXlsJson").val("");//清空值
				$("#exportXlsJson").val(JSON.stringify(_self._getFormObject()));
				$("#exportForm").submit();
			},
			
			/**
			 * 查询表单对象
			 */
			_getFormObject: function(){
				var _self = this;
				var form = _self.getChild(SEARCH_FORM_ID,true);
				var data = form.serializeToObject();
				data.collectionName = _self.get('collectionName');
				return data;
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
                        {label : '处所',item : '<input type="text" name="location" id="location" style="width: 175px;"/>'},
                        {label : '类别',item : '<select name="faultType" id="faultType" style="width: 201px;"><option value="">请选择</option></select>'},
                        {label : '责任部门',item : '<input type="text" id="departmentName" readonly style="width: 175px;"><input type="hidden" name="departmentId" id="departmentId" readonly/>'},
                        {label : '发生开始时间',item : '<input type="text" name="startTime" id="startTimeSearch" class="calendar" style="width: 175px;" readonly/>'},
                        {label : '发生结束时间',item : '<input type="text" name="endTime" id="endTimeSearch" style="width: 175px;" class="calendar" readonly/>'}
                    ]};
            },
            /**
             * 初始化组织机构选择
             * @private
             */
            _initOrganizationPicker:function(){
                var _self=this;
                var orgPicker = new OrganizationPicker({
                    trigger : '#departmentName',
                    rootOrgId:_self.get('rootOrgId'),//必填项
                    rootOrgText:_self.get('rootOrgText'),//必填项
                    url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                    autoHide: true,
                    align : {
                        points:['bl','tl']
                    },
                    zIndex : '10000',
                    width:200,
                    height:210
                });
                orgPicker.render();
                _self.set('orgPicker',orgPicker);
            },
            /**
             * 初始化时间查询
             * @private
             */
            _initDate: function () {
                var _self = this;
                var startTime = new Calendar.DatePicker({//加载日历控件
                    trigger: '#startTimeSearch',
                    showTime: true,
                    autoRender: true,
                    textField:'#startTimeSearch'
                });
                var endTime = new Calendar.DatePicker({//加载日历控件
                    trigger: '#endTimeSearch',
                    showTime: true,
                    autoRender: true,
                    textField:'#endTimeSearch'
                });
                _self.set('startTime', startTime);
                _self.set('endTime', endTime);
            },
            /**
             * 初始化类别
             */
            _initFaultType : function(){
            	$("#faultType").append("<option value='1'>事故</option>");
    			$("#faultType").append("<option value='2'>故障</option>");
    			$("#faultType").append("<option value='3'>障碍</option>");
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                	{
                        title:'处所',
                        dataIndex:'location',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title:'日期',
                        dataIndex:'date',
                        elCls : 'center',
                        width:'15%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
						title:'发生时间',
						dataIndex:'occurrenceTime',
						elCls : 'center',
						width:'15%',
						renderer:Grid.Format.datetimeRenderer
                    },{
						title:'恢复时间',
						dataIndex:'recoveryTime',
						elCls : 'center',
						width:'15%',
						renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'类别',
                        dataIndex:'faultType',
                        elCls : 'center',
                        width:'5%',
                        renderer : function(value) {
        					if(value == "1") {
        						return "事故";
        					} else if(value == "2") {
        						return "故障";
        					} else if(value == "3") {
        						return "障碍";
        					}
        				}
                    },{
                        title:'主要内容',
                        dataIndex:'mainContent',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title:'责任部门',
                        dataIndex:'departmentName',
                        elCls : 'center',
                        width:'10%'
                    },
//                    {
//                        title:'所属部门',
//                        dataIndex:'orgName',
//                        elCls : 'center',
//                        width:'10%'
//                    },
//                    {
//                        title:'创建时间',
//                        dataIndex:'createDate',
//                        elCls : 'center',
//                        width:'25%',
//                        renderer:Grid.Format.datetimeRenderer
//                    },{
//                        title:'创建人',
//                        dataIndex:'createUserName',
//                        elCls : 'center',
//                        width:'20%',
//                    },
                    {
                        title:'操作',
                        dataIndex:'id',
                        elCls : 'center',
                        width:'10%',
                        renderer:function () {
                            return '<span  class="grid-command editBtn">编辑</span>'+
                                '<span  class="grid-command infoBtn">详情</span>';
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
                    url : "/kmms/faultManagementAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 10,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName')}
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
                        },{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                            id : 'export',
                            btnCls : 'button button-small exportBtn',
                            text : '<i class="icon-download"></i>导出EXCEL'
                            +'<form action="/kmms/faultManagementAction/exportExcel" id="exportForm" method="post">'
    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
    						+'</form>',
                        }
                    ],
                    plugins : [
                        Grid.Plugins.CheckSelection,
                        Grid.Plugins.RowNumber
                    ],
//                  permissionStore : _self._initPermissionStore()
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
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                /**
                 * 存储表名
                 */
                collectionName : {value:'faultManagement'}
            }
        });
    return FaultPage;
});