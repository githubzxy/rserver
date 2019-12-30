/**
 * 施工维修天窗信息主页
 * @author yangli
 * @date 19-1-15
 */
define('kmms/dayToJobManagement/constructRepair/ConstructRepairPage',[
	'bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'kmms/dayToJobManagement/constructRepair/ConstructRepairAdd',
    'kmms/dayToJobManagement/constructRepair/ConstructRepairEdit',
    'kmms/dayToJobManagement/constructRepair/ConstructRepairInfo',
    'kmms/dayToJobManagement/constructRepair/CooperateImport',
    'kmms/dayToJobManagement/constructRepair/ConstructRepairImport',
    'bui/calendar',
//    'common/org/OrganizationPicker',
    'common/data/PostLoad',
    ],function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        ConstructRepairAdd = r('kmms/dayToJobManagement/constructRepair/ConstructRepairAdd'),
        ConstructRepairEdit = r('kmms/dayToJobManagement/constructRepair/ConstructRepairEdit'),
        ConstructRepairInfo = r('kmms/dayToJobManagement/constructRepair/ConstructRepairInfo'),
        CooperateImport = r('kmms/dayToJobManagement/constructRepair/CooperateImport'),
        ConstructRepairImport = r('kmms/dayToJobManagement/constructRepair/ConstructRepairImport'),

//        OrganizationPicker = r('common/org/OrganizationPicker');
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var SEARCH_FORM_ID = 'searchForm';
    var ConstructRepairPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                var windowHeight = $(window).height();
                var search = _self.getChild(SearchGridContainer.SEARCH_FORM_ID,true);
    			var searchHeight = search.get('el').height();
//                $('.constructRepairPageCls .bui-grid .bui-grid-body').attr("style","overflow-x:auto;");
//                $('.constructRepairPageCls .bui-grid .bui-grid-body').height(windowHeight - searchHeight - 200);
//                $('.bui-grid .bui-grid-body').attr("style","overflow-x:auto;height: 520px;");
            },
            bindUI:function(){
            	
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
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
                    var addDialog = new ConstructRepairAdd({
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
             // 点吉导入施工维修数据按钮，弹出导入数据框
    			$('.wxImportBtn').click(function (){
    				var wxImportForm = new ConstructRepairImport({
//    					contextPath : contextPath,
    					collectionName:_self.get('collectionName'),
                        userId:_self.get('userId'),
                        userName:_self.get('userName'),
                        orgId:_self.get('orgId'),
                        orgName:_self.get('orgName')
    					});
    				wxImportForm.show();			
    				store.load();				
    			});
    			 // 点吉导入施工配合数据按钮，弹出导入数据框
    			$('.phImportBtn').click(function (){
    				var phImportForm = new CooperateImport({
//    					contextPath : contextPath,
    					collectionName:_self.get('collectionName'),
                        userId:_self.get('userId'),
                        userName:_self.get('userName'),
                        orgId:_self.get('orgId'),
                        orgName:_self.get('orgName')
    					});
    				phImportForm.show();			
    				store.load();				
    			});
                //导出Excel
				$('.exportBtn').on('click',function(e){
//					var records = store.getResult();
					_self._exportExcel();
//					tbar.msg("导出成功");
//					if(state== '0'){
//						tbar.msg({msg:'没有数据可以导出！',status:'0'});
//					}
//					else if(state== '1'){
//						tbar.msg("导出成功");
//					}
//					else if(state== '2'){
//						tbar.msg({msg:'导出数据失败！',status:'0'});
//					}
				});
                //导出Excel
				$('.exportQueryBtn').on('click',function(e){
					_self._exportQueryExcel();
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
                    	var infoDialog = new ConstructRepairInfo({
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
                        var editDialog = new ConstructRepairEdit({
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
            _delFile:function(e,self){
                var delData=self.get('delData'),tdata=e.target.dataset;
                delData[tdata.col].push(tdata.id);
                $(e.target).parents('.success').remove();
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
                        	item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 185px;" readonly/>'
                        },
                        {
                        	label : '结束时间',
                        	item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" class="calendar" style="width: 185px;" readonly/>'
                        },
                        {
                        	label : '计划号',
                        	item : '<input type="text" name="planNum" id="planNum" style="width: 185px;"/>'
                        },
                        {
                        	label : '施工内容',
                        	item : '<input type="text" name="constructContent" id="constructContent" style="width: 185px;"/>'
                        },
//                        {
//                        	label : '项目',
//                        	item : '<select type="text" name="project" id="project"  style="width: 211px;" readonly>'+
//                        				'<option value="">请选择</option>'+
//                        				'<option value="施工">施工</option>' +
//                                        '<option value="维修">维修</option>' +
//                                        '<option value="骨干网计划">骨干网计划</option>' +
//                                        '<option value="临时修">临时修</option>' +
//                                        '<option value="紧急修">紧急修</option>' +
//                                        '<option value="一体化">一体化</option>' +
//                        		'</select>'
//                        }
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
//                        width: 140,
                        width:"8%",
                        renderer:Grid.Format.datetimeRenderer
                    },
                    {
                        title:'计划号',
                        dataIndex:'planNum',
                        elCls : 'center',
//                        width:130,
                        width:"6%",
                    },
                    {
                        title:'项目',
                        dataIndex:'project',
                        elCls : 'center',
//                        width:130,
                        width:"4%",
                    },
                    {
                        title:'线别',
                        dataIndex:'line',
                        elCls : 'center',
//                        width:130,
                        width:"6%",
                    },
//                    {
//                        title:'车间',
//                        dataIndex:'workShop',
//                        elCls : 'center',
//                        width:130,
//                    },{
//                        title:'工区',
//                        dataIndex:'workArea',
//                        elCls : 'center',
//                        width:130,
//                    },{
//                        title:'机关把关干部',
//                        dataIndex:'checkLeader',
//                        elCls : 'center',
//                        width:130,
//                    },{
//                        title:'作业人数',
//                        dataIndex:'totalMan',
//                        elCls : 'center',
//                        width:130,
//                    },
                    {
                        title:'施工内容',
                        dataIndex:'constructContent',
                        elCls : 'center',
//                        width:330,
                        width:"30%",
                    },
                    {
                        title:'计划批准时间',
                        dataIndex:'planAgreeTime',
                        elCls : 'center',
//                        width:280,
                        width:"12%",
                        renderer:Grid.Format.datetimeRenderer
                    },
                    {
                        title:'实际给点时间',
                        dataIndex:'actAgreeTime',
                        elCls : 'center',
//                        width:140,
                        width:"12%",
                        renderer:Grid.Format.datetimeRenderer
                    },
                    {
                        title:'实际完成时间',
                        dataIndex:'actOverTime',
                        elCls : 'center',
//                        width:140,
                        width:"12%",
                        renderer:Grid.Format.datetimeRenderer
                    },
//                    {
//                        title:'申请时间（分钟）',
//                        dataIndex:'applyMinute',
//                        elCls : 'center',
//                        width:130,
//                    },{
//                        title:'实际批准时间（分钟）',
//                        dataIndex:'actMinute',
//                        elCls : 'center',
//                        width:140,
//                    },{
//                        title:'时间兑现率',
//                        dataIndex:'timeCash',
//                        elCls : 'center',
//                        width:130,
//                    },{
//                        title:'备注',
//                        dataIndex:'remark',
//                        elCls : 'center',
//                        width:130,
//                    },
                    {
                        title:'操作',
                        dataIndex:'summaryDate',
                        elCls : 'center',
//                        width:130,
                        width:"8%",
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
                    url : "/kmms/constructRepairAction/findAllDatas",
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
			 * 导出本页数据
			 */
            _exportExcel:function(){
				var _self = this;
				$("#exportXlsJson").val("");//清空值
				$("#exportXlsJson").val(JSON.stringify(_self._getFormObject()));
				$("#exportForm").submit();
			},
			 /**
			 * 导出本页数据
			 */
            _exportQueryExcel:function(){
				var _self = this;
				$("#exportQueryXlsJson").val("");//清空值
				$("#exportQueryXlsJson").val(JSON.stringify(_self._getFormObject()));
				$("#exportQueryForm").submit();
			},
			 /**
			 * 查询表单对象
			 */
			_getFormObject: function(){
				var _self = this;
				var form = _self.getChild(SEARCH_FORM_ID,true);
				var data = form.serializeToObject();
				data.collectionName = _self.get('collectionName');
				data.userId = _self.get('userId');
				return data;
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
                        ,{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                        	id : 'del',
                            btnCls : 'button button-small phImportBtn',
                            text : '<i class="icon-upload"></i>导入施工配合计划表',
                        },{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                        	id : 'del',
                            btnCls : 'button button-small wxImportBtn',
                            text : '<i class="icon-upload"></i>导入维修天窗计划表',
                        }
                        ,{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                            id : 'export',
                            btnCls : 'button button-small exportBtn',
                            text : '<i class="icon-download"></i>全导出完成情况表'
                            +'<form action="/kmms/constructRepairAction/exportXls" id="exportForm" method="post">'
    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
    						+'</form>',
                        }
                        ,{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                            id : 'export',
                            btnCls : 'button button-small exportQueryBtn',
                            text : '<i class="icon-download"></i>按条件导出完成情况表'
                            +'<form action="/kmms/constructRepairAction/exportQueryXls" id="exportQueryForm" method="post">'
    						+'<input type="hidden" name="exportQueryXlsJson" id="exportQueryXlsJson" />'
    						+'</form>',
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
            	elCls : {value:'constructRepairPageCls'},
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
                collectionName : {value:'constructRepair'}
            }
        });
    return ConstructRepairPage;
});