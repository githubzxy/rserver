/**
 * 施工配合协议主页
 * @author yangli
 * @date 19-3-1
 */
define('kmms/constructProtocol/ConstructProtocolPage',[
	'bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'bui/calendar',
    'common/data/PostLoad',
    'kmms/constructProtocol/ConstructProtocolAdd',
    'kmms/constructProtocol/ConstructProtocolInfo',
    'kmms/constructProtocol/ConstructProtocolEdit',
    ],function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        ConstructProtocolAdd = r('kmms/constructProtocol/ConstructProtocolAdd'),
        ConstructProtocolInfo = r('kmms/constructProtocol/ConstructProtocolInfo'),
        ConstructProtocolEdit = r('kmms/constructProtocol/ConstructProtocolEdit'),
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var ConstructProtocolPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                _self._initline();
                _self._initCJ();
                
                $('#loginUserId').val(_self.get('userId'));

                var windowHeight = $(window).height();
                var search = _self.getChild(SearchGridContainer.SEARCH_FORM_ID,true);
    			var searchHeight = search.get('el').height();
            },
            bindUI:function(){
            	
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
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
                               url : '/kmms/constructProtocolAction/removeDoc.cn',
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
                /**
                 * 修改
                 */
//                $(".editBtn").on('click',function () {
//                	var removeIds = table.getSelection();
//                	removeIds = removeIds.map(function (item) {
//                		return item.docId;
//                	});
//                	var id = removeIds.join(",");
//                	if(!id){
//                		tbar.msg({status:0,msg:'至少选择一项要修改的项目！'})
//                	}else if(removeIds.length>1){
//                		tbar.msg({status:0,msg:'只能选择一项要修改的项目！'})
//                	}else{
//                		var editDialog = new ConstructProtocolEdit({
//                          collectionName:_self.get('collectionName'),
//                          userId:_self.get('userId'),
//                          shiftId:id
//                      });
//                      editDialog.show();
//                      editDialog.on('completeAddSave',function(e){
//                          tbar.msg(e.result);
//                          editDialog.close();
//                          store.load();
//                      });
//                	}
//                });
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new ConstructProtocolAdd({
                            collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId')

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
                     * 编辑
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new ConstructProtocolEdit({
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
                     * 详情
                     */
                    if(target.hasClass('infoBtn')){
                    	var infoDialog = new ConstructProtocolInfo({
                            collectionName:_self.get('collectionName'),
                    		shiftId:docId
                        });
                        infoDialog.show();
                    }
                });
            },
            /**
             * 初始化线别下拉选查询框
             */
            _initline:function(){
            	var _self=this;
              	 $.ajax({
                       url:'/kmms/constructProtocolAction/getLines',
                       type:'post',
                       dataType:"json",
                       success:function(res){
                      	 for(var i=0;i<res.length;i++){
                      		 $("#line").append("<option  value="+res[i].text+">"+res[i].text+"</option>");
                      	 }
                       }
                   })
            },
            /**
             * 初始化车间下拉选查询框
             */
            _initCJ:function(){
            	var _self=this;
            	$.ajax({
            		url:'/kmms/constructProtocolAction/getworkShop',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				$("#workShop").append("<option  value="+res[i].text+">"+res[i].text+"</option>");
            			}
            		}
            	})
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
                        	label : '协议名称',
                        	item : '<input type="text" name="proName" id="proName" style="width: 175px;" />'+
                        	'<input type="hidden"  id="loginUserId"  readonly/>'//使操作栏中可获取到数据
                        },
                        {
                        	label : '施工单位',
                        	item : '<input type="text" name="proCompany" id="proCompany" style="width: 175px;" />'
                        },
                        {
                        	label : '所属线别',
                        	item : '<select name="line" id="line" style="width: 201px;" ><option value="" >请选择</option></select>'
                        },
                        {
                        	label : '负责车间',
                        	item : '<select name="workShop" id="workShop" style="width: 201px;" ><option value="" >请选择</option></select>'
                        },
                        {
                        	label : '开工时间',
                        	item : '<input type="text" name="beginDateStart" id="beginDateStart" class="calendar" style="width: 175px;" readonly/>'
                        },
                        {
                        	label : '至',
                        	item : '<input type="text" name="beginDateEnd" id="beginDateEnd" class="calendar" style="width: 175px;" readonly/>'
                        },
                        {
                        	label : '工程地点',
                        	item : '<input type="text" name="proPlace" id="proPlace" style="width: 175px;" />'
                        },
                        {
                        	label : '竣工时间',
                        	item : '<input type="text" name="overDateStart" id="overDateStart" class="calendar" style="width: 175px;" readonly/>'
                        },
                        {
                        	label : '至',
                        	item : '<input type="text" name="overDateEnd" id="overDateEnd" class="calendar" style="width: 175px;" readonly/>'
                        }
                    ]};
            },
            /**
             * 初始化时间查询
             * @private
             */
            _initDate: function () {
                var _self = this;
                //开工开始时间
                var beginDateStart = new Calendar.DatePicker({//加载日历控件
                    trigger: '#beginDateStart',
                    showTime: false,
                    autoRender: true,
                    textField:'#beginDateStart'
                });
                //开工结束时间
                var beginDateEnd = new Calendar.DatePicker({//加载日历控件
                    trigger: '#beginDateEnd',
                    showTime: false,
                    autoRender: true,
                    textField:'#beginDateEnd'
                });
                _self.set('beginDateStart', beginDateStart);
                _self.set('beginDateEnd', beginDateEnd);
                //竣工开始时间
                var overDateStart = new Calendar.DatePicker({//加载日历控件
                    trigger: '#overDateStart',
                    showTime: false,
                    autoRender: true,
                    textField:'#overDateStart'
                });
                //竣工结束时间
                var overDateEnd = new Calendar.DatePicker({//加载日历控件
                    trigger: '#overDateEnd',
                    showTime: false,
                    autoRender: true,
                    textField:'#overDateEnd'
                });
                _self.set('overDateStart', overDateStart);
                _self.set('overDateEnd', overDateEnd);
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
            	 var _self = this;
                var columns = [
                	{
                        title:'协议名称',
                        dataIndex:'proName',
                        elCls : 'center',
                        width:'12%',
                    }
                    ,{
                        title:'施工单位',
                        dataIndex:'proCompany',
                        elCls : 'center',
                        width:'12%',
                    },{
                        title:'所属线别',
                        dataIndex:'line',
                        elCls : 'center',
                        width:'12%',
                    },{
                        title:'工程地点',
                        dataIndex:'proPlace',
                        elCls : 'center',
                        width:'16%',
                    },{
                        title:'责任车间',
                        dataIndex:'workShop',
                        elCls : 'center',
                        width:'12%',
                    },{
                        title:'开工日期',
                        dataIndex:'beginDate',
                        elCls : 'center',
                        width:'12%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'竣工日期',
                        dataIndex:'overDate',
                        elCls : 'center',
                        width:'12%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'操作',
                        dataIndex:'userId',
                        elCls : 'center',
                        width:'12%',
                        renderer:function (e) {
                        	// var loginUserId =$('#loginUserId').val();
                        	var orgId = _self.get('orgId');
                        	var TechOrgId = "402891b45b5fd02c015b74ca35180039";//技术科机构Id
                        	
                           	 if(orgId==TechOrgId){
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
                    url : "/kmms/constructProtocolAction/findAllDatas",
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
            	var searchGrid=null;
            	if(_self.get("orgType")==1501&&_self.get("orgId")== "402891b45b5fd02c015b74ca35180039"){//orgType="1501"代表科室;402891b45b5fd02c015b74ca35180039代表技术科机构id
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
                                }
                            ],
                            plugins : [
                                Grid.Plugins.CheckSelection,
                                Grid.Plugins.RowNumber
                            ],
                        };
            	}else{
            		searchGrid = {
                            tbarItems : [
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
            	elCls : {value:'constructProtocolPageCls'},
            	perId : {},
                userId : {},//登录用户ID
                userName : {},//登录用户名称
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                orgType : {},
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                /**
                 * 存储表名(施工计划表名)
                 */
                collectionName : {value:'constructProtocol'}
            }
        });
    return ConstructProtocolPage;
});