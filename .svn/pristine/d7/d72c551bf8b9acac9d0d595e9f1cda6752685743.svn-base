/**
 *障碍查询主页
 * @author yangsy
 * @date 19-2-27
 * 修改：在该页面增加对安全隐患的查看
 * 修改人：zhouxingyu
 * @date 19-3-24
 */
define('kmms/securityManage/obstacleQuery/obstacleQueryPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailZa',
	],function (r) {
    var BUI = r('bui/common'),
    	  Data = r('bui/data'),
          Grid = r('bui/grid'),
          Calendar = r('bui/calendar'),
          PostLoad = r('common/data/PostLoad'),
          OrganizationPicker = r('common/org/OrganizationPicker'),
          SearchGridContainer = r('common/grid/SearchGridContainer'),
          accidentTroubleObstacleQueryDetailZa = r('kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailZa');
    var SEARCH_FORM_ID = 'searchForm';
    var AccidentTroubleObstacleQueryPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
//              	_self._getLines();//获取线名下拉选数据
             	_self._getWorkShop();//获取车间下拉选数据
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
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
    				$("#obstacleType").val("");
    				$("#obstacleObligationDepart").val("");

    			});
    			 //导出Excel
				$('#export').on('click',function(e){
					_self._exportExcel();

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
                     * 带有障碍的详情
                     */
                    if(target.hasClass('detailBtnZa')){
                    	var infoDialog = new accidentTroubleObstacleQueryDetailZa({
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
//          //导出本页数据
//    		_exportData: function(){
//    			var _self = this;
//    			var store=_self.get('store');
//    			console.log(store.getResult());
//
//    			// 导出本页数据
//    			var records = store.getResult();	
//     			var json = '[';
//    			for(var i = 0 ; i < records.length ; i++){
//    				var row = records[i];
//    				json += '{'
//    					+'"obstacleType":'+'"'+row.obstacleType+'",'
//    					+'"obstacleDuty":'+'"'+row.obstacleDuty+'",'
//    					+'"obstacleDate":'+'"'+row.obstacleDate+'",'
//    					+'"obligationDepart":'+'"'+row.obligationDepart+'",'
//    					+'"obstaclePlace":'+'"'+row.obstaclePlace+'",'
//    					+'"obstacleDeviceType":'+'"'+row.obstacleDeviceType+'",'
//    					+'"obstacleDeviceName":'+'"'+row.obstacleDeviceName+'",'
//    					+'"obstacleOccurDate":'+'"'+row.obstacleOccurDate+'",'
//    					+'"obstacleRecoverDate":'+'"'+row.obstacleRecoverDate+'",'
//    					+'"obstacleDelayMinutes":'+'"'+row.obstacleDelayMinutes+'",'
//    					
//    					+'"obstacleReceiver":'+'"'+row.obstacleReceiver+'"'
//    					+'},';
//    			}
//    			json = json.substring(0, json.length - 1);
//    			json += ']';
//    			$("#exportXlsJson").val(json);
//    			if(json != ']'){
//    				$("#exportForm").submit();
//    			}else{
//    				commonFailure("导出失败！");
//    			}
//    		},
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
//    	    /**
//             * 获取线别
//             */
//            _getLines:function(){
//            	var _self=this;
//           	 $.ajax({
//    	                url:'/kmms/networkManageInfoAction/getLines',
//    	                type:'post',
//    	                dataType:"json",
//    	                success:function(res){
//    	               	 $("#troubleLineName").append("<option  value=''>请选择</option>");
//    	               	 for(var i=0;i<res.length;i++){
//    	               		 $("#troubleLineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
//    	               	 }
//                    }
//                });
//            },
            /**
             * 获取车间下拉选数据
             */
            _getWorkShop:function(){
            	 var _self = this;
                 $.ajax({
                     url:'/kmms/networkManageInfoAction/getWorkShop',
                     type:'post',
                     dataType:"json",
                     success:function(res){
                    	 $("#obstacleObligationDepart").append("<option  value=''>请选择</option>");
                    	 for(var i=0;i<res.length;i++){
                    		 $("#obstacleObligationDepart").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                    	 }
                     }
                 })
            }, 
            /**
             * 初始化查询表单
             * @private
             */
            _initSearchForm:function(){
                return {
                    items : [
                        {label : '开始时间',item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'},
                        {label : '结束时间',item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'},
                        {label : '责任部门',item : '<select name="obstacleObligationDepart" id="obstacleObligationDepart" style="width: 210px;" ></select>'},
//                        {label : '线别',item : '<select name="troubleLineName" id="troubleLineName" style="width: 210px;" ></select>'},
                        {label : '障碍类别',item : '<input type="text" name="obstacleType" id="obstacleType" style="width: 175px;" />'}
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
                        title:'日期',
                        dataIndex:'obstacleDate',
                        elCls : 'center',
                        width:'25%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'责任部门',
                        dataIndex:'obstacleObligationDepart',
                        elCls : 'center',
                        width:'15%'
                    },{
                        title:'障碍处所',
                        dataIndex:'obstaclePlace',
                        elCls : 'center',
                        width:'20%'
                    }, {
                        title:'设备类别',
                        dataIndex:'obstacleDeviceType',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'设备名称',
                        dataIndex:'obstacleDeviceName',
                        elCls : 'center',
                        width:'20%'
                    },{
                    	title:'发生时间',
                    	dataIndex:'obstacleOccurDate',
                    	elCls : 'center',
                    	width:'25%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                    	title:'恢复时间',
                    	dataIndex:'obstacleRecoverDate',
                    	elCls : 'center',
                    	width:'25%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                    	title:'延时',
                    	dataIndex:'obstacleDelayMinutes',
                    	elCls : 'center',
                    	width:'15%'
                    },{
                 
                    	title:'受理人',
                    	dataIndex:'obstacleReceiver',
                    	elCls : 'center',
                    	width:'20%'
                    },{
                        title:'障碍类别',
                        dataIndex:'obstacleType',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'障碍责任',
                        dataIndex:'obstacleDuty',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'操作',
                        dataIndex:'infoResult',
                        elCls : 'center',
                        width:'15%',
                        renderer:function (value) {
                            var button = '';
                        	button += '<span  class="grid-command detailBtnZa">详情</span>';
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
                    url : "/kmms/obstacleQueryAction/findAll.cn",
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
            
            
            /**
             * 初始化列表展示对象
             * @private
             */
            _initSearchGrid:function () {
            	var _self = this;
                var searchGrid = {
				   tbarItems : [{
					      id : 'export',
					      btnCls : 'button button-small',
					      text : '<i class="icon-download"></i>导出Excel'
	                            +'<form action="/kmms/obstacleQueryAction/exportXls" id="exportForm" method="post">'
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
                perId : {},
                userId : {},//登录用户ID
                userName : {},//登录用户名称
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                collectionName : {value:'informationData'}//存储表名
            }
        });
    return AccidentTroubleObstacleQueryPage;
});