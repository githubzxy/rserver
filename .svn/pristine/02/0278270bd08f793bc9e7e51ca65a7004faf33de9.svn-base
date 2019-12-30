/**
 * 二级维修方案查询主页
 * @author yangsy
 * @date 19-2-26
 */
define('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainQuery/pointInnerSecondMaintainQueryPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyDetail',
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	pointInnerSecondMaintainApplyDetail = r('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyDetail');
    var PointInnerSecondMaintainQueryPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                _self._initFlowState();
            	_self._getWorkAreas();//获取车间下拉选数据

            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
//                var orgPicker=_self.get('orgPicker');
//                /**
//                 * 组织机构选择
//                 */
//                orgPicker.on('orgSelected',function (e) {
//                    $('#submitOrgName').val(e.org.text);
//        		    $('#orgSelectId').val(e.org.id);
//                });
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#constructionProject").val("");
    				$("#flowState").val("");
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
    				$("#submitOrgName").val("");

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
                        var infoDialog = new pointInnerSecondMaintainApplyDetail({
                            collectionName:_self.get('collectionName'),
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
//            /**
//             * 初始化组织机构选择
//             * @private
//             */
//            _initOrganizationPicker:function(){
//                var _self=this;
//                var orgPicker = new OrganizationPicker({
//                    trigger : '#orgSelectName',
//                    rootOrgId:_self.get('rootOrgId'),//必填项
//                    rootOrgText:_self.get('rootOrgText'),//必填项
//                    url : '/kmms/commonAction/getShopAndDepart',//必填项
//                    autoHide: true,
//                    align : {
//                        points:['bl','tl']
//                    },
//                    zIndex : '10000',
//                    width:200,
//                    height:210
//                });
//                orgPicker.render();
//                _self.set('orgPicker',orgPicker);
//            },
            /**
             * 获取所有工区
             */
            _getWorkAreas:function(){
            	var _self=this;
           	 $.ajax({
    	                url:'/kmms/pointInnerSecondMaintainQueryAction/getAllworkArea',
    	                type:'post',
    	                dataType:"json",
//    	                data: { workshop:workshop},
    	                success:function(res){
    			          $("#submitOrgName").append("<option  value=''>请选择</option>");
    	               	 for(var i=0;i<res.length;i++){
    	               	  $("#submitOrgName").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
                    }
                });
            },
            /**
             * 初始化查询表单
             * @private
             */
            _initSearchForm:function(){
                return {
                    items : [
                        {
                        	
                        	label : '维修方案',
                        	item : '<input type="text" name="constructionProject" id="constructionProject" style="width: 175px;"/>'
                        },
                        {
        	  				label : '状态',
        	  				item : '<select name="flowState" id="flowState" style="width: 201px;" ><option value="">请选择</option></select>'
        	  			},
                        {
                        	label : '开始时间',
                        	item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
                        },
                        {
                        	label : '结束时间',
                        	item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" style="width: 175px;" class="calendar" readonly/>'
                        },{
        	  				label : '提交部门',
                            item : '<select type="text" id="submitOrgName" name="submitOrgName"  style="width: 201px;"/>'
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
            
            _initFlowState: function () {
            	$("#flowState").append("<option value='0'>草稿</option>");
    			$("#flowState").append("<option value='1'>待审批</option>");
    			$("#flowState").append("<option value='2'>审批通过</option>");
    			$("#flowState").append("<option value='3'>审批不通过</option>");
    			$("#flowState").append("<option value='4'>已结束</option>");
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                	{
                        title:'维修方案',
                        dataIndex:'constructionProject',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'提交部门',
                        dataIndex:'submitOrgName',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title : '创建时间',
                        dataIndex:'createDate',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title : '创建人',
                        dataIndex:'userName',
                        elCls : 'center',
                        width:'15%',
                    },{
        				title : '状态',
        				dataIndex : 'flowState',
        				elCls : 'center',
        				width : '15%',
        				renderer : function(e) {
        					if(e == "0") {
        						return "草稿";
        					} else if(e == "1") {
        						return "待审批";
        					} else if(e == "2") {
        						return "审批通过";
        					} else if(e == "3") {
        						return "审批不通过";
        					} else if(e == "4") {
        						return "已结束";
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
                    url : "/kmms/pointInnerSecondMaintainQueryAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 20,
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
                collectionName : {value:'pointInnerSecondMaintain'}//存储表名（二级维修）
            }
        });
    	return PointInnerSecondMaintainQueryPage;
});