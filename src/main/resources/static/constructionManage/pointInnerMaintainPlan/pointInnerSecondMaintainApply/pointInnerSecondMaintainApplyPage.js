/**
 * 二级维修方案申请主页
 * @author yangsy
 * @date 19-2-20
 */
define('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyAdd',
	 	'kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyEdit',
	 	'kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyDetail',
	 	'kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyApproveEdit',
	 	'kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyReply'
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	pointInnerSecondMaintainApplyAdd = r('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyAdd'),
    	pointInnerSecondMaintainApplyEdit = r('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyEdit'),
    	pointInnerSecondMaintainApplyDetail = r('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyDetail'),
    	pointInnerSecondMaintainApplyApproveEdit = r('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyApproveEdit'),
    	pointInnerSecondMaintainApplyReply = r('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyReply');
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
    				$("#constructionProject").val("");
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
                       tbar.msg({status:0,msg:'至少选择一项要删除的方案！'})
                   }else{
                	   BUI.Message.Confirm('确认要删除吗？',function(){
	                       var postLoad = new PostLoad({
	                           url : '/kmms/pointInnerSecondMaintainApplyAction/removeDoc.cn',
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
                    var addDialog = new pointInnerSecondMaintainApplyAdd({
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
                    	  applyDate = record.createDate;
                    	  orgName = record.submitOrgName;
                    	  project = record.constructionProject;
                     console.log(record);
                     
               	  
               	  var Day1 = applyDate;
                 	
               	  var Day2 = _self._replacepos(Day1,4,4,'年');
               	  var Day3 = _self._replacepos(Day2,7,7,'月');
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
                    /**
                     * 编辑
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new pointInnerSecondMaintainApplyEdit({
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
                     * 审批不通过的编辑
                     */
                    if(target.hasClass('approveEditBtn')){
                    	var editDialog = new pointInnerSecondMaintainApplyApproveEdit({
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
                    if(target.hasClass('exportBtn')){
                		  var postLoad = new PostLoad({
              				url : '/kmms/pointInnerSecondMaintainApplyAction/exportApplyFrom.cn',
              				ajaxOptions : {
              					contentType : 'application/json;charset=utf-8',
              					dataType : 'json', 
              					data : JSON.stringify(record)
              				},
              				el : '',
              				loadMsg : '下载中...'
              			});
              			postLoad.load({}, function(result) {
              				console.log(result);
              				window.location.href = "/kmms/commonAction/download?path=" + result.msg +'&fileName='+Day3+"日"+orgName+project+"II级维修方案申请表"+".docx";// 下载路径
              			});
                      }
                    /**
                     * 回复情况填写
                     */
                    if(target.hasClass('replyBtn')){
                    	var editDialog = new pointInnerSecondMaintainApplyReply({
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
                        }
                    ]};
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
            
            _initFlowState: function () {
            	$("#flowState").append("<option value='0'>草稿</option>");
    			$("#flowState").append("<option value='1'>待审批</option>");
    			$("#flowState").append("<option value='2'>待回复</option>");
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
        						return "待回复";//审批通过
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
                        	if(value=='0'){
                        		button += '<span  class="grid-command editBtn">修改</span>';
                        	} else if(value=='2'){
                        		button += '<span  class="grid-command replyBtn">回复</span>';
                        		button += '<span  class="grid-command exportBtn">导出申请表</span>';
                        	} else if(value=='3'){
                        		button += '<span  class="grid-command approveEditBtn">重新申请</span>';
                        	} else if(value=='4'){
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
                    url : "/kmms/pointInnerSecondMaintainApplyAction/findAll.cn",
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
                collectionName : {value:'pointInnerSecondMaintain'}//存储表名（二级维修）
            }
        });
    	return PointInnerSecondMaintainApplyPage;
});