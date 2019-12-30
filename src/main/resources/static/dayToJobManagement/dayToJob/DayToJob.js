/**
 * 日交班信息主页
 * @author yangli
 * @date 19-1-14
 */
define('kmms/dayToJobManagement/dayToJob/DayToJob',[
	'bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'bui/calendar',
    'common/org/OrganizationPicker',
    'common/data/PostLoad',
    'kmms/dayToJobManagement/dayToJob/DayToJobAdd',
    'kmms/dayToJobManagement/dayToJob/DayToJobEdit',
    ],function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        DayToJobAdd = r('kmms/dayToJobManagement/dayToJob/DayToJobAdd'),
        DayToJobEdit = r('kmms/dayToJobManagement/dayToJob/DayToJobEdit'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var DayToJob = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
//                _self._initOrganizationPicker();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
    			//重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
//    				$("#selectName").val("");
//    				$("#orgSelectId").val("");
//    				$("#orgSelectName").val("");
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
//    				$("#auditStatus").val("");
    			});
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
                	var systemBeforeDate = _self.get('systemBeforeDate').split('"');
                	var beforeDate = systemBeforeDate[1]
                	var postLoad = new PostLoad({
                        url : '/kmms/dayToJobAction/getDataByBeforeDate.cn',
                        el : _self.get('el'),
                        loadMsg : '加载中...'
                    });
                    postLoad.load({systemBeforeDate:beforeDate,collectionName:_self.get('collectionName')},function (result) {
                    	if(result.data){
//                    		console.log(result);
//                    		console.log(result.data);
                    		tbar.msg({status:"0",msg:"值班记录已存在无法再新增！"});
                    	}else{
                    		var addDialog = new DayToJobAdd({
                             	userId:_self.get('userId'),
                                 userName:_self.get('userName'),
                                 orgId:_self.get('orgId'),
                                 orgName:_self.get('orgName'),
                             	collectionName:_self.get('collectionName'),
                             });
                             addDialog.show();
                             addDialog.on('completeAddSave',function(e){
                                 tbar.msg(e.result);
                                 addDialog.close();
                                 store.load();
                             });
                    	}
                    });
                });
                /**
                 * 操作按钮
                 */
                table.on('cellclick',function(ev){
                    var record = ev.record, //点击行的记录
                        target = $(ev.domTarget),
                        docId = record.docId, //点击的元素（数据库主键的值String）
                        dataId = record.id, //点击的元素
                        userId = record.userId, //登陆用户ID（日交班管理模块）
                        leader = record.leader, //值班领导
                        cadre = record.cadre, //值班干部
                        date = record.date, //日期（当前系统时间的前一天）
                        dispatch = record.dispatch, //值班调度
                        filePath = record.filePath; //值班调度
                    
//                    console.log(record);
                    /**
                     * 日交班信息查看
                     */
                    if(target.hasClass('infoBtn')){
                    	if(filePath){
                    		var tempv = window.open("../tempFileShow.html");
                    		tempv.location.href = '/pageoffice/openPage?filePath='+filePath;
                    	}else{
                    		BUI.Message.Alert('还未生成汇总信息！');
    	        			return;
                    	}
                    }
                    /**
                     * 日交班信息
                     */
                    if(target.hasClass('generateBtn')){
                    	
//                    	var date = new Date();
//                    	var time = date.getTime();
//                    	date.setHours(8);
//                    	date.setMinutes(0);
//                    	date.setSeconds(0);
//                     	var time8 = date.getTime();
//                     	
//                    	console.log(time);
//                    	console.log(time8);
//                    	
//                    	if(time>time8){
//                    		console.log("8点后");
//                    	}
                    	
                    	var filePath = "";
                		$.ajax({
                			 type: 'POST',
                			 url:'/kmms/dayToJobAction/generateSumTable.cn',
                			 dataType: 'json',
                			 data:{
                				 collectionName:_self.get('collectionName'),
                				 docId:docId,
                				 userName:_self.get('userName'),
                				 userId:userId,
                				 leader:leader,
                				 cadre:cadre,
                				 date:date,
                				 dispatch:dispatch,
                			 },
                			 async:false,
                			 success:function(result){
                				 filePath = result.msg;
                			 }
                		});
                		if(filePath==""){
                		    BUI.Message.Alert('查看失败，请联系管理员！！','error');
                			return;
                		}
//                    	var filePath = "D:/201901.xlsx";
                		var tempv = window.open("../tempFileShow.html");
                		tempv.location.href = '/pageoffice/openPage?filePath='+filePath;
                		_self.set("filePath",filePath);
                    }
                    /**
                     * 修改
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new DayToJobEdit({
                            collectionName:_self.get('collectionName'),
                            dataId:docId
                        });
                        editDialog.show();
                        editDialog.on('completeAddSave',function(e){
                            tbar.msg(e.result);
                            editDialog.close();
                            store.load();
                        });
                    }
                    /**
                     * 下载
                     */
                    if(target.hasClass('downBtn')){
                    	if(filePath){
                    		window.location.href = "/kmms/commonAction/download?path=" + filePath+'&fileName='+date+'日交班信息表.xlsx';// 下载路径
                    	}else{
                    		BUI.Message.Alert('还未生成汇总信息！');
    	        			return;
                    	}
                    	
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
//                    showTime: true,
                    autoRender: true,
                    textField:'#startUploadDateSearch'
                });
                var endUploadDate = new Calendar.DatePicker({//加载日历控件
                    trigger: '#endUploadDateSearch',
//                    showTime: true,
                    autoRender: true,
                    textField:'#endUploadDateSearch'
                });
                _self.set('startUploadDate', startUploadDate);
                _self.set('endUploadDate', endUploadDate);
            },
//           
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
            	var _self = this;
                var columns = [
//                	{
//                        title:'序号',
//                        dataIndex:'dataNumber',
//                        elCls : 'center',
//                        width:'5%'
//                    },
                    {
                        title:'日期',
                        dataIndex:'date',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },
                    {
                        title:'值班调度',
                        dataIndex:'dispatch',
                        elCls : 'center',
                        width:'20%',
                    },
                    {
                        title:'值班领导',
                        dataIndex:'leader',
                        elCls : 'center',
                        width:'20%',
                    },
                    {
                        title:'值班干部',
                        dataIndex:'cadre',
                        elCls : 'center',
                        width:'20%',
                    },
                    {
                        title:'操作',
                        dataIndex:'userIdAndDate',
                        elCls : 'center',
                        width:'20%',
                        renderer:function (value) {
                        	var valueArray = value.split(",");
                        	var dateArray = _self.get('systemBeforeDate').split('"');
                        	var button = '<span  class="grid-command infoBtn">查看</span>';
                        	if(valueArray[1] == dateArray[1]&&valueArray[0] == _self.get('userId')){
                        		button += '<span  class="grid-command generateBtn">生成</span>'+'<span  class="grid-command editBtn">修改</span>';
                        	}
                            return button+ '<span  class="grid-command downBtn">下载</span>';
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
                    url : "/kmms/dayToJobAction/findAllDatas",
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
////                    autoLoad : true ,
//                    proxy : {
//                        method : 'post',
//                        dataType : 'json'
//                    },
//                    params : {perId:_self.get('perId'),userId:_self.get('userId')}
//                });
//                _self.set('permissionStore',store);
//                return store;
//            },
            
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
                        },
//                        {
//                            xclass : 'bar-item-separator' // 竖线分隔符
//                        },
//                        {
//                        	id : 'del',
//                            btnCls : 'button button-small delBtn',
//                            text : '<i class="icon-remove"></i>批量删除',
//                        },
//                        {
//                            xclass : 'bar-item-separator' // 竖线分隔符
//                        },
//                        {
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
                perId : {},
                userId : {},
                userName : {},
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                systemBeforeDate : {},//当前系统日期的前一天
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                collectionName : {value:'dayToJob'}
            }
        });
    return DayToJob;
});