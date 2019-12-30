/**
 *检查日报主页
 * @author xiekun
 * @date 19-3-25
 */
define('kmms/productionManage/procuratorialDaily/procuratorialDailyPage',['bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'kmms/productionManage/procuratorialDaily/procuratorialDailyAdd',
    'bui/calendar',
    'bui/mask',
    'common/org/OrganizationPicker',
    'kmms/productionManage/procuratorialDaily/procuratorialDailyEdit',
    'kmms/productionManage/procuratorialDaily/procuratorialDailyInfo',
    'kmms/productionManage/procuratorialDaily/procuratorialDailyImport',
    'common/data/PostLoad'
    ], function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        Mask = r('bui/mask'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker');
	    procuratorialDailyAdd = r('kmms/productionManage/procuratorialDaily/procuratorialDailyAdd'),
	    procuratorialDailyEdit = r('kmms/productionManage/procuratorialDaily/procuratorialDailyEdit'),
	    procuratorialDailyInfo = r('kmms/productionManage/procuratorialDaily/procuratorialDailyInfo'),
	    procuratorialDailyImport = r('kmms/productionManage/procuratorialDaily/procuratorialDailyImport'),
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var procuratorialDailyPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
//                _self._initOrganizationPicker();
                $('#loginUserId').val(_self.get('userId'));

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
    				$("#workshop").val("");
    				$("#startdate").val("");
    				$("#enddate").val("");
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
	                           url : '/kmms/procuratorialDailyAction/removeDoc.cn',
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
                    var addDialog = new procuratorialDailyAdd({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                            parentOrgName:_self.get('parentOrgName'),
                    });
                    addDialog.show();
                    addDialog.on('completeAddSave',function(e){
                        tbar.msg(e.result);
                        addDialog.close();
                        store.load();
                    });
                });
                //按页面查询条件导出Excel
    			$('#exportBy').on('click',function(e){
    				var records = store.getResult();
    				if(records.length != 0){
    					_self._exportXls(records);
    				}else{
    					tbar.msg({msg:'没有数据可以导出！',status:'0'});
    				}
    			});
                // 点吉导入施工维修数据按钮，弹出导入数据框
    			$('.ImportBtn').click(function (){
    				var ImportForm = new procuratorialDailyImport({
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
                   	var infoDialog = new procuratorialDailyInfo({
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
                       var editDialog = new procuratorialDailyEdit({
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
     		 * 导出本页数据
     		 */
             _exportXls:function(records){
            	 var workshop = $('#workshop').val();
            	 var startdate = $('#startdate').val();
            	 var enddate = $('#enddate').val();
            	$('#workshopOfexport').val(workshop);
     			$('#startdateOfexport').val(startdate);
     			$('#enddateOfexport').val(enddate);
     			// 拼接导出数据的JSON字符串
     			/*var json = '[';
     			for(var i = 0 ; i < records.length ; i++){
     				var row = records[i];
     				json += '{'
     					+'"workshop":'+'"'+row.workshop+'",'
     					+'"department":'+'"'+row.department+'",'
     					+'"date":'+'"'+row.date+'",'
     					+'"inspector":'+'"'+row.inspector+'",'
     					+'"site":'+'"'+row.site+'",'
     					+'"content":'+'"'+row.content+'",'
     					+'"condition":'+'"'+row.condition+'",'
     					+'"functionary":'+'"'+row.functionary+'",'
     					+'"remark":'+'"'+row.remark+'",'
     					+'"problem":'+'"'+row.problem+'",'
     					+'"require":'+'"'+row.require+'"'
     					+'},';
     			}
     			json = json.substring(0, json.length - 1);
     			json += ']';
     			$('#exportXlsJson').val(json);*/
     			$('#exportForm').submit();
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
                        {label : '填报车间',item : '<input type="text" name="workshop" id="workshop" style="width: 175px;" />'+
                        	'<input type="hidden"  id="loginUserId"  readonly/>'//使操作栏中可获取到数据
                        	},
                        {label : '查询起始日期',item : '<input type="text" name="startdate" id="startdate" class="calendar" readonly style="width: 175px;" />'},
                        {label : '查询终止日期',item : '<input type="text" name="enddate" id="enddate" class="calendar" readonly style="width: 175px;" />'},
                            
                    ]};
            },
            /**
             * 初始化时间查询
             * @private
             */
            _initDate: function () {
                var _self = this;
                var date = new Calendar.DatePicker({//加载日历控件
                    trigger: '#startdate',
//                    showTime: true,
                    autoRender: true,
                    textField:'#startdate'
                });
                var date = new Calendar.DatePicker({//加载日历控件
                    trigger: '#enddate',
//                    showTime: true,
                    autoRender: true,
                    textField:'#enddate'
                });
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
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                    {
                        title:'填报车间',
                        dataIndex:'workshop',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'填报部门',                       
                        dataIndex:'department',
                        elCls : 'center',
                        width:'25%',
//                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'检查日期',
                        dataIndex:'date',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'检查人',
                        dataIndex:'inspector',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'检查地点',
                        dataIndex:'site',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'检查内容',
                        dataIndex:'content',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'发现问题',
                        dataIndex:'problem',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'整改要求',
                        dataIndex:'require',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'整改落实情况',
                        dataIndex:'condition',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'整改负责人',
                        dataIndex:'functionary',
                        elCls : 'center',
                        width:'20%',
                    },{
                    	title:'操作',
                        dataIndex:'userId',
                        elCls : 'center',
                        width:'12%',
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
                    url : "/kmms/procuratorialDailyAction/findAll.cn",
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
            	var searchGrid = null;
            	if(_self.get("orgType")==1502||_self.get("orgType")==1503){
            		searchGrid = {
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
                                    text : '<i class="icon-upload"></i>导入检查日报表',
                                },
                                {
                                    xclass : 'bar-item-separator' // 竖线分隔符
                                },
                                {
                                    id : 'exportBy',
                                    btnCls : 'button button-small exportBtn',
                                    text : '<i class="icon-download "></i>导出'
                                    +'<form action="/kmms/procuratorialDailyAction/exportXlsBy" id="exportForm" method="post">'
            						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
            						+'<input type="hidden" name="workshopOfexport" id="workshopOfexport" />'
            						+'<input type="hidden" name="startdateOfexport" id="startdateOfexport" />'
            						+'<input type="hidden" name="enddateOfexport" id="enddateOfexport" />'
            						+'</form>',
                                },
                            ],
                            plugins : [
                                Grid.Plugins.CheckSelection,
                                Grid.Plugins.RowNumber
                            ],
                        };
            	}else{
            		searchGrid = {
                            tbarItems : [
                            	{
                                    id : 'exportBy',
                                    btnCls : 'button button-small exportBtn',
                                    text : '<i class="icon-download "></i>导出'
                                    +'<form action="/kmms/procuratorialDailyAction/exportXlsBy" id="exportForm" method="post">'
            						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
            						+'<input type="hidden" name="workshopOfexport" id="workshopOfexport" />'
            						+'<input type="hidden" name="startdateOfexport" id="startdateOfexport" />'
            						+'<input type="hidden" name="enddateOfexport" id="enddateOfexport" />'
            						+'</form>',
                                },
                            ],
                            plugins : [
                                Grid.Plugins.CheckSelection,
                                Grid.Plugins.RowNumber
                            ],
                        };
            	}
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
                parentOrgName:{},
                orgType : {},
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                collectionName : {value:'procuratorialDaily'}
            }
        });
    return procuratorialDailyPage;
});