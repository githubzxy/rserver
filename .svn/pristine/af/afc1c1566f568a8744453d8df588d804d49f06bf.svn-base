/**
 * 维修方案申请主页
 * @author yangsy
 * @date 19-1-21
 */
define('kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyAdd',
	 	'kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyEdit',
	 	'kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyDetail',
	 	'kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceRestApplyEdit',
	 	'kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyExecute'
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	skylightMaintenanceApplyAdd = r('kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyAdd'),
    	skylightMaintenanceApplyEdit = r('kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyEdit'),
    	skylightMaintenanceApplyDetail = r('kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyDetail'),
    	skylightMaintenanceRestApplyEdit = r('kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceRestApplyEdit'),
    	skylightMaintenanceApplyExecute = r('kmms/constructionManage/pointInnerMaintainPlan/skylightMaintenanceApplyExecute');
    var SkylightMaintenanceApplyPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                $("#flowState").append("<option  value='0'>草稿</option>");
    			$("#flowState").append("<option  value='1'>待技术科审核</option>");
    			$("#flowState").append("<option  value='2'>技术科已审核</option>");
    			$("#flowState").append("<option  value='3'>待安全科审核</option>");
    			$("#flowState").append("<option  value='4'>待调度科审核</option>");
    			$("#flowState").append("<option  value='5'>待审批</option>");
    			$("#flowState").append("<option  value='6'>审批通过</option>");
    			$("#flowState").append("<option  value='7'>已回复</option>");
    			$("#flowState").append("<option  value='9'>退回</option>");
//    			$("#selectType").append("<option  value='I级维修方案'>I级维修方案</option>");
//    			$("#selectType").append("<option  value='骨干网'>骨干网</option>");
//                _self._initOrganizationPicker();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
//                var orgPicker=_self.get('orgPicker');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
//    				$("#selectName").val("");
//    				$("#orgSelectId").val("");
//    				$("#orgSelectName").val("");
    				$("#project").val("");
//    				$("#selectType").val("");
    				$("#flowState").val("");
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
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
	                           url : '/kmms/skylightMaintenanceApplyAction/removeDoc.cn',
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
                    var addDialog = new skylightMaintenanceApplyAdd({
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
                    	  applyDate = record.applyDate;
                    	  orgName = record.orgName;
                    	  project = record.project;
                     console.log(record);
                     
               	  
               	  var Day1 = applyDate;
                 	
               	  var Day2 = _self._replacepos(Day1,4,4,'年');
               	  var Day3 = _self._replacepos(Day2,7,7,'月');
                    /**
                     * 详情
                     */
                    if(target.hasClass('detailBtn')){
                        var infoDialog = new skylightMaintenanceApplyDetail({
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
                        var editDialog = new skylightMaintenanceApplyEdit({
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
                    	var editDialog = new skylightMaintenanceRestApplyEdit({
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
                    	var editDialog = new skylightMaintenanceRestApplyEdit({
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
                    	var editDialog = new skylightMaintenanceApplyExecute({
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
                    
                    if(target.hasClass('exportBtn')){
              		  var postLoad = new PostLoad({
            				url : '/kmms/skylightMaintenanceApplyAction/exportApplyFrom.cn',
            				ajaxOptions : {
            					contentType : 'application/json;charset=utf-8',
            					dataType : 'json', 
            					data : JSON.stringify(record)
            				},
            				el : '#skylightExportApply',
            				loadMsg : '下载中...'
            			});
            			postLoad.load({}, function(result) {
            				console.log(result);
            				window.location.href = "/kmms/commonAction/download?path=" + result.msg +'&fileName='+Day3+"日"+orgName+project+"维修方案申请表"+".docx";// 下载路径
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
                    	{
                        	label : '施工项目',
                        	item : '<input type="text" name="project" id="project" style="width: 175px;"/>'
                        },{
                        	label : '开始时间',item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
                        },{
                        	label : '结束时间',item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
                        },{
        	  				label : '审核状态',
        	  				item : '<select name="flowState" id="flowState" style="width: 201px;" ><option value="" >请选择</option></select>'
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
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                	{
                        title:'施工项目',
                        dataIndex:'project',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'类型',
                        dataIndex:'type',
                        elCls : 'center',
                        width:'10%'
                    },{
                        title:'提交部门',
                        dataIndex:'orgSelectName',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title:'创建时间',
                        dataIndex:'createDate',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'创建人',
                        dataIndex:'createUserName',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'状态',
                        dataIndex:'flowState',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (value) {
                        	if(value=='0'){
                        		return '草稿';
                        	} else if(value=='1'){
                        		return '待技术科审核';
                        	} else if(value=='2'){
                        		return '技术科已审核';	
                        	}else if(value=='3'){
                        		return '待安全科审核';
                        	} else if(value=='4'){
                        		return '待调度科审核';
                        	}  else if(value=='5'){
                        		return '待审批';
                        	} else if(value=='6'){
                        		return '审批通过';
                        	} else if(value=='7'){
                        		return '已回复';
                        	}
                        	else if(value=='9'){
                        		return '退回';
                           }
                        }
                    },{
                        title:'操作',
                        dataIndex:'flowState',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (value) {
                        	var button = '';
                        	if( value =='0'){
                        		button += '<span  class="grid-command editBtn">修改</span>';
                        	}
                        	 else if(value=='9'){
                        		button += '<span  class="grid-command editApproveBtn">重新申请</span>';
                        	} else if(value=='6'){
                        		button += '<span  class="grid-command executeBtn">回复</span>';
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
                    url : "/kmms/skylightMaintenanceApplyAction/findAll.cn",
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
                collectionName : {value:'skylightMaintenanceApply'}//存储表名
            }
        });
    return SkylightMaintenanceApplyPage;
});