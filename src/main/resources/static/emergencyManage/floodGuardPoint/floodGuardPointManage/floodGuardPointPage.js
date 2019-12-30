/**
 * 防洪看守点管理主页
 * @author yangsy
 * @date 18-11-22
 */
define('kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointPage',['bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointAdd',
    'bui/calendar',
    'bui/mask',
    'common/org/OrganizationPicker',
    'kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointEdit',
    'kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointInfo',
    'kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointImport',
    'common/data/PostLoad'
    ], function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        Mask = r('bui/mask'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker');
    	floodGuardPointAdd = r('kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointAdd'),
    	floodGuardPointEdit = r('kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointEdit'),
    	floodGuardPointInfo = r('kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointInfo'),
    	floodGuardPointImport = r('kmms/emergencyManage/floodGuardPoint/floodGuardPointManage/floodGuardPointImport'),
        SearchGridContainer = r('common/grid/SearchGridContainer');
    var SEARCH_FORM_ID = 'searchForm';
    var FloodGuardPointPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
//                _self._initDate();
                _self._getLines();
             	_self._getWorkshops();//获取车间下拉选数据

//                _self._initOrganizationPicker();
                $('#loginUserId').val(_self.get('userId'));
              	$("#ksStatus").append("<option  value='是'>是</option>");
     			$("#ksStatus").append("<option  value='否'>否</option>");

            },
            bindUI:function(){
                var _self = this,store = _self.get('store'),orgPicker=_self.get('orgPicker');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
//                /**
//                 * 组织机构选择
//                 */
//                orgPicker.on('orgSelected',function (e) {
//                    $('#orgSelectName').val(e.org.text);
//        		    $('#orgSelectId').val(e.org.id);
//                });
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#lineName").val("");
    				$("#workArea").val("");
    				$("#orgSelectName").val("");
    				$("#section").val("");
    				$("#ksStatus").val("");
    				$("#guardName").val("");
//    				$("#startUploadDateSearch").val("");
//    				$("#endUploadDateSearch").val("");
    			});
    		      
                //工区下拉选选项根据车间而变化
                $("#orgSelectName").on('change', function() {
                    $("#workArea").empty();
                    var workshop = $("#orgSelectName").val();
                    _self._getWorkAreas(workshop);

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
	                           url : '/kmms/floodGuardPointAction/removeDoc.cn',
	                           el : _self.get('el'),
	                           loadMsg : '删除中...'
	                       });
	                       postLoad.load({id:id,collectionName:_self.get('collectionName'),userId:_self.get('userId')},function (res) {
	                    	   tbar.msg(res);
	                    	   store.load();
	                       });
                	   },'question');
                   }
                });
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new floodGuardPointAdd({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                    });
                    addDialog.show();
                    addDialog.on('completeAddSave',function(e){
//                    	console.log(e.result.data);
//                    	console.log(e.result.msg);
//                    	console.log(tbar.msg(e.result));
//                    	if(e.result.msg=="新增失败！该线别下已有该站点！"){
//                    		BUI.Message.Alert("该线别下已有该看守点！请重新输入！");
//                    	}else{
                        tbar.msg(e.result);
                        addDialog.close();
                        store.load();
//                    	}
                    });
                });
                // 点吉导入施工维修数据按钮，弹出导入数据框
    			$('.ImportBtn').click(function (){
    				var ImportForm = new floodGuardPointImport({
    					collectionName:_self.get('collectionName'),
                        userId:_self.get('userId'),
//                      userName:_self.get('userName'),
                        orgId:_self.get('orgId'),
                        orgName:_self.get('orgName')
    					});
    				ImportForm.show();	
    				ImportForm.on('completeImport',function(e){
    					  console.log(e);
    					  ImportForm.close();
                          tbar.msg(e.result);
//                        ImportForm.close();
//                        loadMask.hide();
                           store.load();
                      });
    			});
                //导出Excel
				$('.exportBtn').on('click',function(e){
					_self._exportExcel();
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
                        var infoDialog = new floodGuardPointInfo({
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
                        var editDialog = new floodGuardPointEdit({
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
             * 获取线别
             */
            _getLines:function(){
            	var _self=this;
            	$.ajax({
            		url:'/kmms/networkManageInfoAction/getLines',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			$("#lineName").append("<option  value=''>请选择</option>");
            			for(var i=0;i<res.length;i++){
            				$("#lineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
            			}
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
				data.userId = _self.get('userId');
				return data;
			},
            /**
             * 初始化查询表单
             * @private
             */
            _initSearchForm:function(){
                return {
                    items : [
                        {label : '所属车间',item : '<select type="text" name="orgSelectName" id="orgSelectName" style="width: 200px;"></select>'},
                        {label : '所属工区',item : '<select type="text" name="workArea" id="workArea" style="width: 200px;"></select>'},
                        {label : '铁路线别',item : '<select name="lineName" id="lineName" style="width:200px" /></select>'+
                        	'<input type="hidden"  id="loginUserId"  readonly/>'//使操作栏中可获取到数据
                        },
                        {label : '区间',item : '<input type="text" name="section" id="section" style="width: 175px;" />'},
                        {label : '看守点名称',item : '<input type="text" name="guardName" id="guardName" style="width: 175px;" />'},
                        {label : '是否常年看守点',item : '<select type="text" name="ksStatus" id="ksStatus" style="width: 200px;"><option value="" >请选择</option></select>'}
                    ]};
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
             * 获取车间
             */
            _getWorkshops:function(){
            	var _self=this;
           	 $.ajax({
    	                url:'/kmms/overhaulRecordAction/getworkShop',
    	                type:'post',
    	                dataType:"json",
    	                success:function(res){
    	               	 $(" #orgSelectName").append("<option  value=''>请选择</option>");
    	               	 for(var i=0;i<res.length;i++){
    	               		 $("#orgSelectName").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
    	               	 var workshop = $("#orgSelectName").val();
    	               	 _self._getWorkAreas(workshop);
                    }
                });
            },
            /**
             * 获取工区
             */
            _getWorkAreas:function(workshop){
            	var _self=this;
           	 $.ajax({
    	                url:'/kmms/overhaulRecordAction/getworkArea',
    	                type:'post',
    	                dataType:"json",
    	                data: { workshop:workshop},
    	                success:function(res){
    	               	 $("#workArea").append("<option  value=''>请选择</option>");
    	               	 for(var i=0;i<res.length;i++){
//    	               		 console.log(res[i].value);
    	               		 $("#workArea").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
                    }
                });
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                   {
                        title:'所属车间',
                        dataIndex:'orgSelectName',
                        elCls : 'center',
                        width:'20%'
                    }, {
                        title:'所属工区',
                        dataIndex:'workArea',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'铁路线别',
                        dataIndex:'lineName',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'区间',
                        dataIndex:'section',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'看守点名称',
                        dataIndex:'guardName',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'是否常年看守点',
                        dataIndex:'ksStatus',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'操作',
                        dataIndex:'userId',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (e) {
                         var loginUserId =$('#loginUserId').val();
                       	 if(loginUserId==e){
                            return '<span  class="grid-command editBtn">编辑</span>'+
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
                    url : "/kmms/floodGuardPointAction/findAll.cn",
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
                        	id : 'imp',
                            btnCls : 'button button-small ImportBtn',
                            text : '<i class="icon-upload"></i>导入统计表',
                        }
                        ,{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                            id : 'export',
                            btnCls : 'button button-small exportBtn',
                            text : '<i class="icon-download"></i>导出统计表'
                            +'<form action="/kmms/floodGuardPointAction/exportXls" id="exportForm" method="post">'
    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
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
                collectionName : {value:'floodGuardPointManage'}
            }
        });
    return FloodGuardPointPage;
});