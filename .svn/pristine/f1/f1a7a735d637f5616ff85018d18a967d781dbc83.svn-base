/**
 * 点外维修查询主页
 * @author yangsy
 * @date 19-1-21
 */
define('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainQueryPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/SelectSuggest',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyDetail',
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	SelectSuggest = r('kmms/dayToJobManagement/pointOuterMaintainManagement/SelectSuggest'),
    	pointOuterMaintainApplyDetail = r('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyDetail');
    var PointOuterMaintainQueryPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                _self._initFlowState();
                _self._getLines();
                _self._initOrganizationPicker();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
                var orgPicker=_self.get('orgPicker');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                
                orgPicker.on('orgSelected',function (e) {
                    $('#unitName').val(e.org.text);
        		    $('#unitId').val(e.org.id);
        		    $('#unitType').val(e.org.type+"");
                });
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
    				$("#flowState").val("");
    				$("#lineType").val("");
    				$("input[name='lineName']").val("");
    				$("#unitName").val("");
                	$("#unitId").val("");
                	$("#unitType").val("");
    			});
                /**
                 * 操作按钮
                 */
                table.on('cellclick',function(ev){
                    var record = ev.record, //点击行的记录
                    	  target = $(ev.domTarget),
                    	  docId = record.docId; //点击的元素（数据库主键的值String）
                    /**
                     * 详情
                     */
                    if(target.hasClass('detailBtn')){
                        var infoDialog = new pointOuterMaintainApplyDetail({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            shiftId:docId
                        });
                        infoDialog.show();
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
						{label : '单位',item : '<input type="text" name="unitName" id="unitName" readonly style="width: 185px;"><input type="hidden" name="unitId" id="unitId"  readonly/><input type="hidden" name="unitType" id="unitType"  readonly/>'},
						{label : '线别',item : '<div id="lineName" name="lineName" style="width: 209px;"/>'},
                        {label : '开始时间',item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 185px;" readonly/>'},
                        {label : '结束时间',item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" class="calendar" style="width: 185px;" readonly/>'},
                        {label : '归属',item : '<select name="lineType" id="lineType" style="width: 211px;"><option value="">请选择</option></select>'},
                        {label : '状态',item : '<select name="flowState" id="flowState" style="width: 211px;"><option value="">请选择</option></select>'},
                    ]};
            },
            
            _getLines:function(){
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/networkManageInfoAction/getLines',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	
            	var suggest = new SelectSuggest({
                    renderName: '#lineName',
                    inputName: 'lineName',
                    renderData: nameData,
                    width: 209
                });
            	
            },
            
            _initOrganizationPicker: function() {
            	var _self = this;
            	var orgPicker = new OrganizationPicker({
            		trigger : '#unitName',
            		rootOrgId:_self.get('rootOrgId'),//必填项
            		rootOrgText:_self.get('rootOrgText'),//必填项
            		url : '/kmms/jobRecordAction/getShopAndDepart',//必填项
            		autoHide : true,
            		showRoot : false,//不显示根节点
//            		checkType:"all",//有勾选框
//            		multipleSelect: true,//多选
            		align : {
            			points:['bl','tl']
            		},
            		zIndex : '10000',
            		width:210,
            		height:210
            	});
            	orgPicker.render();
            	_self.set('orgPicker',orgPicker);
            },
            
            _initFlowState: function () {
            	
            	$("#lineType").append("<option  value='0'>高铁</option>");
    			$("#lineType").append("<option  value='1'>普铁</option>");
            	
            	$("#flowState").append("<option value='0'>草稿</option>");
    			$("#flowState").append("<option value='1'>待审核</option>");//车间待审核
    			$("#flowState").append("<option value='2'>审核不通过</option>");
    			$("#flowState").append("<option value='3'>审核通过</option>");
    			$("#flowState").append("<option value='4'>审批不通过</option>");
    			$("#flowState").append("<option value='5'>审批通过</option>");
    			$("#flowState").append("<option value='6'>未完成</option>");
    			$("#flowState").append("<option value='7'>已完成</option>");
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
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                    {
                        title:'单位',
                        dataIndex:'unit',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title:'编号',
                        dataIndex:'serial',
                        elCls : 'center',
                        width:'10%',
                    },{
                        title:'作业时间',
                        dataIndex:'workTime',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'线别',
                        dataIndex:'lineName',
                        elCls : 'center',
                        width:'10%',
                    },{
                        title:'区间',
                        dataIndex:'section',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'站点',
                        dataIndex:'station',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'作业负责人',
                        dataIndex:'workPrincipal',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'审核人',
                        dataIndex:'auditor',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'审核日期',
                        dataIndex:'auditDate',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'审批人',
                        dataIndex:'approver',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'审批日期',
                        dataIndex:'approveDate',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'状态',
                        dataIndex:'flowState',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (value) {
                        	if(value=='0'){
                        		return '草稿';
                        	} else if(value=='1'){
                        		return '待审核';
                        	} else if(value=='2'){
                        		return '审核不通过';
                        	} else if(value=='3'){
                        		return '审核通过';
                        	} else if(value=='4'){
                        		return '审批不通过';
                        	} else if(value=='5'){
                        		return '审批通过';
                        	} else if(value=='6'){
                        		return '未完成';
                        	} else if(value=='7'){
                        		return '已完成';
                        	}
                        }
                    },{
                        title:'操作',
                        dataIndex:'flowState',
                        elCls : 'center',
                        width:'15%',
                        renderer:function (value) {
                        	var button = '';
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
                    url : "/kmms/pointOuterMaintainQueryAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 20,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName'),userId:_self.get('userId'),orgId:_self.get('orgId')}//当前登陆人ID用于判断是否需要查询展示的数据
                });
                _self.set('store',store);
                return store;
            },
            /**
             * 初始化列表展示对象
             * @private
             */
            _initSearchGrid:function () {
//            	var _self = this;
                var searchGrid = {
                    tbarItems : [
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
                collectionName : {value:'pointOuterMaintain'}//存储表名
            }
        });
    return PointOuterMaintainQueryPage;
});