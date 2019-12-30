/**
 * 施工配合主页
 * @author yangli
 * @date 19-1-15
 */
define('kmms/dayToJobManagement/constructCooperate/ConstructCooperatePage',[
	'bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'kmms/dayToJobManagement/constructCooperate/ConstructCooperateAdd',
    'kmms/dayToJobManagement/constructCooperate/ConstructCooperateEdit',
    'kmms/dayToJobManagement/constructCooperate/ConstructCooperateInfo',
    'bui/calendar',
//    'common/org/OrganizationPicker',
    'common/data/PostLoad',
    ],function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        ConstructCooperateAdd = r('kmms/dayToJobManagement/constructCooperate/ConstructCooperateAdd'),
        ConstructCooperateEdit = r('kmms/dayToJobManagement/constructCooperate/ConstructCooperateEdit'),
        ConstructCooperateInfo = r('kmms/dayToJobManagement/constructCooperate/ConstructCooperateInfo'),
//        OrganizationPicker = r('common/org/OrganizationPicker');
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var ConstructCooperatePage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
//                _self._setGridHeight();
                var windowHeight = $(window).height();
                var search = _self.getChild(SearchGridContainer.SEARCH_FORM_ID,true);
    			var searchHeight = search.get('el').height();
                $('.constructCooperatePageCls .bui-grid .bui-grid-body').attr("style","overflow-x:auto;");
                $('.constructCooperatePageCls .bui-grid .bui-grid-body').height(windowHeight - searchHeight - 200);
//                _self._initOrganizationPicker();
            },
            bindUI:function(){
            	
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
//                $(window).on('resize',function(){_self._setGridHeight();});
//    			//重写重置按钮的点击事件
//    			$("button[type='reset']").on('click',function(event){
//    				event.preventDefault();
//    				$("#selectName").val("");
//    				$("#orgSelectId").val("");
//    				$("#orgSelectName").val("");
//    				$("#startUploadDateSearch").val("");
//    				$("#endUploadDateSearch").val("");
//    				$("#auditStatus").val("");
//    			});
//                /**
//                 * 批量删除
//                 */
//                $(".delBtn").on('click',function () {
//                   var removeIds = table.getSelection();
//                   removeIds = removeIds.map(function (item) {
//                       return item.docId;
//                   });
//                   var id = removeIds.join(",");
//                   if(!id){
//                       tbar.msg({status:0,msg:'至少选择一项要删除的项目！'})
//                   }else{
//                	   BUI.Message.Confirm('确认要删除吗？',function(){
//                		   var postLoad = new PostLoad({
//                               url : '/kmms/commonAction/removeDoc.cn',
//                               el : _self.get('el'),
//                               loadMsg : '删除中...'
//                           });
//                           postLoad.load({id:id,collectionName:_self.get('collectionName')},function (res) {
//                        	   tbar.msg(res);
//                        	   store.load();
//                           });
//						},'question');
//                   }
//                });
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new ConstructCooperateAdd({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            userName:_self.get('userName'),
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
                /**
                 * 操作按钮
                 */
                table.on('cellclick',function(ev){
                	var record = ev.record, //点击行的记录
                    target = $(ev.domTarget),
                    docId = record.docId; //点击的元素
                /**
              	  * 删除
              	 */
          	  if(target.hasClass('delBtn')){
               	   BUI.Message.Confirm('确认要删除吗？',function(){
	                       var postLoad = new PostLoad({
	                           url : '/kmms/otherProductionInfoAction/removeDoc.cn',
	                           el : _self.get('el'),
	                           loadMsg : '删除中...'
	                       });
	                       postLoad.load({id:docId,collectionName:_self.get('collectionName')},function (res) {
	                    	   tbar.msg(res);
	                    	   store.load();
	                       });
               	   },'question');
                }	  
                    /**
                     * 详情
                     */
                    if(target.hasClass('infoBtn')){
                    	var infoDialog = new ConstructCooperateInfo({
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
                        var editDialog = new ConstructCooperateEdit({
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
            _setGridHeight:function(){
    			var _self = this,grid = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
    			var search = _self.getChild(SearchGridContainer.SEARCH_FORM_ID,true);
    			var windowHeight = $(window).height();
    			var searchHeight = search.get('el').height();
    			grid.set('height',windowHeight-searchHeight-88);
    			$(".constructCooperatePageCls .bui-grid-height .bui-grid-body").css("height",windowHeight-searchHeight-175);
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
                        	label : '开始时间',
                        	item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
                        },
                        {
                        	label : '结束时间',
                        	item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
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
                    showTime: false,
                    autoRender: true,
                    textField:'#startUploadDateSearch'
                });
                var endUploadDateSearch = new Calendar.DatePicker({//加载日历控件
                    trigger: '#endUploadDateSearch',
                    showTime: false,
                    autoRender: true,
                    textField:'#endUploadDateSearch'
                });
                _self.set('startUploadDate', startUploadDate);
                _self.set('endUploadDateSearch', endUploadDateSearch);
            },
            /**
             * 初始化组织机构选择
             * @private
             */
            _initOrganizationPicker:function(){
                var _self=this;
                var orgPicker = new OrganizationPicker({
                    trigger : '#orgSelectName',
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
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                	{
                        title:'日期',
                        dataIndex:'date',
                        elCls : 'center',
                        width: 130,
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'车间',
                        dataIndex:'workShop',
                        elCls : 'center',
                        width:130,
                    }
                    ,{
                        title:'部门',
                        dataIndex:'depart',
                        elCls : 'center',
                        width:130,
                    },{
                        title:'线别',
                        dataIndex:'line',
                        elCls : 'center',
                        width:130,
                    },{
                        title:'位置',
                        dataIndex:'local',
                        elCls : 'center',
                        width:130,
                    },{
                        title:'施工单位',
                        dataIndex:'constructUnit',
                        elCls : 'center',
                        width:130,
                    },{
                        title:'施工项目',
                        dataIndex:'constructPro',
                        elCls : 'center',
                        width:130,
                    },
//                    {
//                        title:'施工内容',
//                        dataIndex:'constructContent',
//                        elCls : 'center',
//                        width:130,
//                    },
                    {
                        title:'配合人员',
                        dataIndex:'cooperMan',
                        elCls : 'center',
                        width:130,
                    },{
                        title:'光电缆情况',
                        dataIndex:'cableSituation',
                        elCls : 'center',
                        width:130,
                    },{
                        title:'备注',
                        dataIndex:'remark',
                        elCls : 'center',
                        width:130,
                    },{
                        title:'操作',
                        dataIndex:'summaryDate',
                        elCls : 'center',
                        width:130,
                        renderer:function (e) {
                        	return '<span  class="grid-command editBtn">修改</span>'+
                        	'<span  class="grid-command delBtn">删除</span>'+
                        	'<span  class="grid-command infoBtn">详情</span>';
//                        	if(e==''){
//                        		return '<span  class="grid-command infoBtn">详情</span>'+
//                        		'<span  class="grid-command editBtn">修改</span>';
//                        	}else{
//                        		return '<span  class="grid-command infoBtn">详情</span>'
//                        	}
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
                    url : "/kmms/constructCooperateAction/findAllDatas",
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
            
            _initPermissionStore:function () {
                var _self = this;
                var store = new Data.Store({
                    url : "/kmms/permission/getBtnPers.cn",
//                    autoLoad : true ,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {perId:_self.get('perId'),userId:_self.get('userId')}
                });
                _self.set('permissionStore',store);
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
                            text : '<i class="icon-plus"></i>新增',
                        }
//                        ,{
//                            xclass : 'bar-item-separator' // 竖线分隔符
//                        },{
//                        	id : 'del',
//                            btnCls : 'button button-small delBtn',
//                            text : '<i class="icon-remove"></i>批量删除',
//                        }
//                        ,{
//                            xclass : 'bar-item-separator' // 竖线分隔符
//                        },{
//                            id : 'export',
//                            btnCls : 'button button-small exportBtn',
//                            text : '<i class="icon-download"></i>导出EXCEL'
//                            +'<form action="/kmms/faultManagementAction/exportExcel" id="exportForm" method="post">'
//    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
//    						+'</form>',
//                        }
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
            	elCls : {value:'constructCooperatePageCls'},
            	perId : {},
                userId : {},//登录用户ID
                userName : {},//登录用户名称
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                /**
                 * 存储表名(施工计划表名)
                 */
                collectionName : {value:'constructCooperate'}
            }
        });
    return ConstructCooperatePage;
});