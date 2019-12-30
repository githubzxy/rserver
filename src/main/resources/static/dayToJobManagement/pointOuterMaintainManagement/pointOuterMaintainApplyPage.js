/**
 * 点外维修申请主页
 * @author yangsy
 * @date 19-1-21
 */
define('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/SelectSuggest',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyAdd',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyEdit',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyDetail',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyAuditEdit',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyApproveEdit',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyExecute',
	 	'kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplySave'
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	SelectSuggest = r('kmms/dayToJobManagement/pointOuterMaintainManagement/SelectSuggest'),
    	pointOuterMaintainApplyAdd = r('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyAdd'),
    	pointOuterMaintainApplyEdit = r('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyEdit'),
    	pointOuterMaintainApplyDetail = r('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyDetail'),
    	pointOuterMaintainApplyAuditEdit = r('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyAuditEdit'),
    	pointOuterMaintainApplyApproveEdit = r('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyApproveEdit'),
    	pointOuterMaintainApplySave = r('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplySave'),
    	pointOuterMaintainApplyExecute = r('kmms/dayToJobManagement/pointOuterMaintainManagement/pointOuterMaintainApplyExecute');
    var PointOuterMaintainApplyPage = BUI.Component.Controller.extend(
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
//                _self._initOrganizationPicker();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
//                var orgPicker=_self.get('orgPicker');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                /**
                 * 组织机构选择
                 */
//                orgPicker.on('orgSelected',function (e) {
//                    $('#orgSelectName').val(e.org.text);
//        		    $('#orgSelectId').val(e.org.id);
//                });
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
//    				$("#selectName").val("");
//    				$("#orgSelectId").val("");
//    				$("#orgSelectName").val("");
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
    				$("#flowState").val("");
    				$("#lineType").val("");
    				$("input[name='lineName']").val("");
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
	                           url : '/kmms/pointOuterMaintainApplyAction/removeDoc.cn',
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
                    var addDialog = new pointOuterMaintainApplyAdd({
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
                /**另存为*/
                $('.saveBtn').on('click',function(e){
                	var object = table.getSelection();
                	if(object.length!=1){
                		tbar.msg({status:0,msg:'请选择一项记录！'})
                    }else{
                    	var docId = object[0].docId;
//                 	   BUI.Message.Confirm('确认要删除吗？',function(){
//                 	   },'question');
                    	var addDialog = new pointOuterMaintainApplySave({
                    		collectionName:_self.get('collectionName'),
                    		shiftId:docId,
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
                    }
                });
                /**
                 * 操作按钮
                 */
                table.on('cellclick',function(ev){
                    var record = ev.record, //点击行的记录
                    	  target = $(ev.domTarget),
                    	  docId = record.docId; //点击的元素（数据库主键的值String）
                    	  busiId = record.busiId;//自定义的业务ID
                    	  unit = record.unit;
                    	  serial = record.serial;
                    	  applyDate = record.applyDate;
                    	  
            	  var Day1 = applyDate;
              	
            	  var Day2 = _self._replacepos(Day1,4,4,'年');
            	  var Day3 = _self._replacepos(Day2,7,7,'月');
                    
                    /**
                     * 导出申请表
                     */
                	  if(target.hasClass('exportBtn')){
                		  var postLoad = new PostLoad({
              				url : '/kmms/pointOuterMaintainApplyAction/exportApplyFrom.cn',
              				ajaxOptions : {
              					contentType : 'application/json;charset=utf-8',
              					dataType : 'json', 
              					data : JSON.stringify(record)
              				},
              				el : '#pointOuterMaintainExportApply',
              				loadMsg : '下载中...'
              			});
              			postLoad.load({}, function(result) {
              				window.location.href = "/kmms/commonAction/download?path=" + result.msg +'&fileName='+Day3+"日"+unit+"天窗点外作业项目作业计划申请表"+serial+"号"+".docx";// 下载路径
//              				window.location.href = encodeURI(result.msg);
              			});
                      }
                    	  
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
                    /**
                     * 编辑
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new pointOuterMaintainApplyEdit({
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
                    /**
                     * 审核不通过的编辑
                     */
                    if(target.hasClass('editAuditBtn')){
                    	var editDialog = new pointOuterMaintainApplyAuditEdit({
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
                    /**
                     * 审批不通过的编辑
                     */
                    if(target.hasClass('editApproveBtn')){
                    	var editDialog = new pointOuterMaintainApplyApproveEdit({
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
                    /**
                     * 执行情况填写
                     */
                    if(target.hasClass('executeBtn')){
                    	var editDialog = new pointOuterMaintainApplyExecute({
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
            
            
            _replacepos:function(text,start,stop,replacetext){
            	var mystr = text.substring(0,stop)+replacetext+text.substring(stop+1);
            	return mystr;
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
//                        {label : '资料名称',item : '<input type="text" name="name" id="selectName" style="width: 175px;"/>'},
//                        {label : '所属部门',item : '<input type="text" id="orgSelectName" readonly style="width: 175px;"><input type="hidden" name="orgId" id="orgSelectId"  readonly/>'},
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
             * 初始化组织机构选择
             * @private
             */
//            _initOrganizationPicker:function(){
//                var _self=this;
//                var orgPicker = new OrganizationPicker({
//                    trigger : '#orgSelectName',
//                    rootOrgId:_self.get('rootOrgId'),//必填项
//                    rootOrgText:_self.get('rootOrgText'),//必填项
//                    url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
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
                        	if(value=='0'){
                        		button += '<span  class="grid-command editBtn">修改</span>';
                        	} else if(value=='2'){
                        		button += '<span  class="grid-command editAuditBtn">重新申请</span>';
                        	} else if(value=='4'){
                        		button += '<span  class="grid-command editApproveBtn">重新申请</span>';
                        	} else if(value=='5'){
                        		button += '<span  class="grid-command executeBtn">执行情况</span>';
                        		if(true){
                        			button += '<span  class="grid-command exportBtn">导出申请表</span>';
                        		}
                        	} else if(value=='6'){
                        		button += '<span  class="grid-command exportBtn">导出申请表</span>';
                        	} else if(value=='7'){
                        		button += '<span  class="grid-command exportBtn">导出申请表</span>';
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
                    url : "/kmms/pointOuterMaintainApplyAction/findAll.cn",
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
                            text : '<i class="icon-plus"></i>申请',
                        },{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                            id : 'save',
                            btnCls : 'button button-small saveBtn',
                            text : '<i class="icon-plus"></i>另存为',
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
                collectionName : {value:'pointOuterMaintain'}//存储表名
            }
        });
    return PointOuterMaintainApplyPage;
});