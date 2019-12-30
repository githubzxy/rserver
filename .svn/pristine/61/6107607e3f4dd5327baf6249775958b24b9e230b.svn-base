/**
 *设备履历台账
 * @author xiekun
 * @date 19-3-26
 */
define('kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerPage',['bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerAdd',
    'bui/calendar',
    'bui/mask',
    'common/org/OrganizationPicker',
    'kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerEdit',
    'kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerInfo',
    'kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerImport',
    'common/data/PostLoad'
    ], function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        Mask = r('bui/mask'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker');
	    deviceRecordLedgerAdd = r('kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerAdd'),
	    deviceRecordLedgerEdit = r('kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerEdit'),
	    deviceRecordLedgerInfo = r('kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerInfo'),
	    deviceRecordLedgerImport = r('kmms/communicationDevice/deviceRecordLedger/deviceRecordLedgerImport'),
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var deviceRecordLedgerPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
//                _self._initDate();
//                _self._initOrganizationPicker();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
//                orgPicker=_self.get('orgPicker');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
//                /**
//                 * 组织机构选择
//                 */
//                orgPicker.on('orgSelected',function (e) {
//                    $('#orgSelectName').val(e.org.text);
//        		    $('#orgSelectId').val(e.org.id);
//                });
//                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#deviceName").val("");
    				$("#location").val("");
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
	                           url : '/kmms/deviceRecordLedgerAction/removeDoc.cn',
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
                    var addDialog = new deviceRecordLedgerAdd({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                    });
                    addDialog.show();
                    addDialog.on('completeAddSave',function(e){
                        tbar.msg(e.result);
                        addDialog.close();
                        store.load();
//                    	}
                    });
                });
               
                // 点吉导入施工维修数据按钮，弹出导入数据框
    			$('.ImportBtn').click(function (){
    				var ImportForm = new deviceRecordLedgerImport({
    					collectionName:_self.get('collectionName'),
                        userId:_self.get('userId'),
                        orgId:_self.get('orgId'),
                        orgName:_self.get('orgName')
    					});
    				ImportForm.show();	
    				ImportForm.on('completeImport',function(e){
    					  console.log(e);
    					  ImportForm.close();
                          tbar.msg(e.result);
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
                    * 详情
                    */
                   if(target.hasClass('infoBtn')){
                   	var infoDialog = new deviceRecordLedgerInfo({
                           collectionName:_self.get('collectionName'),
//                   		userId:_self.get('userId'),
                   		shiftId:docId
                       });
                       infoDialog.show();
                   }
                   /**
                    * 编辑
                    */
                   if(target.hasClass('editBtn')){
                       var editDialog = new deviceRecordLedgerEdit({
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
             * 初始化查询表单
             * @private
             */
            _initSearchForm:function(){
                return {
                    items : [
                        {label : '设备处所',item : '<input type="text" name="location" id="location" style="width: 175px;" />'},
                        {label : '设备名称',item : '<input type="text" name="deviceName" id="deviceName"  style="width: 175px;" />'}
                    ]};
            },
//            /**
//             * 初始化时间查询
//             * @private
//             */
//            _initDate: function () {
//                var _self = this;
//                var date = new Calendar.DatePicker({//加载日历控件
//                    trigger: '#date',
////                    showTime: true,
//                    autoRender: true,
//                    textField:'#date'
//                });
//            },
            //导出本页数据
    		_exportData: function(){
    			var _self = this;
    			var store=_self.get('store');
    			console.log(store.getResult());

    			// 导出本页数据
    			var records = store.getResult();	
     			var json = '[';
    			for(var i = 0 ; i < records.length ; i++){
    				var row = records[i];
    				console.log(row.createDate);
    				json += '{'
    					+'"location":'+'"'+row.location+'",'
    					+'"deviceName":'+'"'+row.deviceName+'",'
    					+'"type":'+'"'+row.type+'",'
    					+'"maintainUnit":'+'"'+row.maintainUnit+'",'
    					+'"person":'+'"'+row.person+'",'
    					+'"vender":'+'"'+row.vender+'",'
    					+'"modelNumber":'+'"'+row.modelNumber+'",'
    					+'"useTime":'+'"'+row.useTime+'",'
    					+'"railwayLine":'+'"'+row.railwayLine+'",'
    					+'"remark":'+'"'+row.remark+'"'
    					+'},';
    			}
    			json = json.substring(0, json.length - 1);
    			json += ']';
    			$("#exportXlsJson").val(json);
    			if(json != ']'){
    				$("#exportForm").submit();
    			}else{
    				commonFailure("导出失败！");
    			}
    		},
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                    {
                        title:'设备处所',
                        dataIndex:'location',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'设备名称',                       
                        dataIndex:'deviceName',
                        elCls : 'center',
                        width:'25%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'设备类别',
                        dataIndex:'type',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'维护单位',
                        dataIndex:'maintainUnit',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'包机人',
                        dataIndex:'person',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'设备厂家',
                        dataIndex:'vender',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'设备型号',
                        dataIndex:'modelNumber',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'使用时间',
                        dataIndex:'useTime',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'所属铁路线',
                        dataIndex:'railwayLine',
                        elCls : 'center',
                        width:'20%',
                    },{
                    	title:'操作',
                        dataIndex:'id',
                        elCls : 'center',
                        width:'12%',
                        renderer:function (e) {
                        	return '<span  class="grid-command editBtn">编辑</span>'+
                        	'<span  class="grid-command infoBtn">详情</span>';
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
                    url : "/kmms/deviceRecordLedgerAction/findAll.cn",
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
                            text : '<i class="icon-upload"></i>导入设备履历表',
                        },{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                        	id : 'dow',
                            btnCls : 'button button-small DownloadBtn',
                            text : '<i class="icon-download"></i>导出Excel'
	                            +'<form action="/kmms/deviceRecordLedgerAction/exportXls" id="exportForm" method="post">'
	    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
	    						+'</form>',
		                  listeners : {
		                    'click' : function(){
		                    	 _self._exportData()
		                    }
		                  }
                        }
                    ],
                    plugins : [
                        Grid.Plugins.CheckSelection,
                        Grid.Plugins.RowNumber
                    ],
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
                collectionName : {value:'deviceRecordLedger'}
            }
        });
    return deviceRecordLedgerPage;
});