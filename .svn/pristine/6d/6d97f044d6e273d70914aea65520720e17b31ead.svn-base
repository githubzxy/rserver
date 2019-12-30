/**
 * 机房技术资料及台账
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionPage',['bui/common',
    'bui/data','bui/grid','bui/calendar',
    'common/org/OrganizationPicker',
    'common/grid/SearchGridContainer',
    'common/data/PostLoad',
    'bui/select',
    'kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionAdd',
    'kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionDetail',
    'kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionUpdate',
    'kmms/technicalManagement/technicalDocument/SelectSuggest'],function(require){
    var BUI = require('bui/common');
    var Grid = require('bui/grid');
    var Data = require('bui/data');
    var Calendar = require('bui/calendar');
    var Select = require('bui/select');
    var SelectSuggest = require("kmms/technicalManagement/technicalDocument/SelectSuggest");
    var PostLoad=require('common/data/PostLoad');
    var OrganizationPicker = require('common/org/OrganizationPicker');
    var SearchGridContainer = require('common/grid/SearchGridContainer');
    var SkitsRescueCompletionAdd = require('kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionAdd');
    var SkitsRescueCompletionUpdate = require('kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionUpdate');
    var SkitsRescueCompletionDetail = require('kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionDetail');
    var SEARCH_FORM_ID = 'searchForm';
    var SkitsRescueCompletionPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate(); 
            	_self._getLines();
    			_self._initOrganizationPicker();
                $('#loginOrgId').val(_self.get('orgId'));
            	_self._getWorkshops();//获取车间下拉选数据
                var windowHeight = $(window).height();
                var search = _self.getChild(SearchGridContainer.SEARCH_FORM_ID,true);
    			var searchHeight = search.get('el').height();
            },
            bindUI:function(){
            	
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var orgPicker=_self.get('orgPicker');
                var tbar = table.get('tbar');
                /**
                 * 组织机构选择
                 */
                orgPicker.on('orgSelected',function (e) {
                    $('#joinDepart').val(e.org.text);
                });
                store.load({});
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new SkitsRescueCompletionAdd({
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
                     * 详情
                     */
                    if(target.hasClass('infoBtn')){
                    	var infoDialog = new SkitsRescueCompletionDetail({
                            collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId
                        });
                        infoDialog.show();
                    }
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
                     * 修改
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new SkitsRescueCompletionUpdate({
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
                	items: [
						{
							label : '线别',
						    item : '<select type="text" name="line" id="line" style="width: 175px;"  readonly="readonly"></select>'
						},{
                            label : '组织部门',
                            item : '<select type="text" id="orgDepart" name="orgDepart" style="width: 175px;" readonly="readonly" ></select>'+
                        	'<input type="hidden"  id="loginOrgId"  readonly/>'//使操作栏中可获取到数据
                        },{
                            label : '参加部门',
                            item : '<input type="text" name="joinDepart" id="joinDepart" style="width: 175px;"  readonly="readonly"/>'
                        },{
                            label : '创建时间',
                            item : '<input type="text" name="createStartDate" id="createStartDate" style="width: 175px;" class="calendar"  readonly="readonly"/>'
                        },{
                            label : '至',
                            item : '<input type="text" name="createEndDate" id="createEndDate" class="calendar" style="width: 175px;"  readonly="readonly" />'
                        }
                    ]
                };
            },
            /**
             * 获取线别
             */
            _getLines: function() {
                var _self = this;
                $.ajax({
                    url: '/kmms/constructCooperateAction/getLines',
                    type: 'post',
                    dataType: "json",
                    success: function(res) {
    		             $("#line").append("<option  value=''>请选择</option>");
                        for (var i = 0; i < res.length; i++) {
                            $("#line").append("<option  value=" + res[i] + ">" + res[i] + "</option>");
                        }
                    }
                })
            },
            /**
             * 获取科室和车间
             */
            _getWorkshops:function(){
            	var _self=this;
           	 $.ajax({
    	                url:'/kmms/userInfoManageAction/getCadreAndShop',
    	                type:'post',
    	                dataType:"json",
    	                success:function(res){
    		             $("#orgDepart").append("<option  value=''>请选择</option>");
    		             $("#orgDepart").append("<option  value='昆明局'>昆明局</option>");
    		             $("#orgDepart").append("<option  value='总公司'>总公司</option>");
    	               	 for(var i=0;i<res.length;i++){
    	               		 $("#orgDepart").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
                    }
                });
            },
            /**
             * 初始化组织机构选择
             * @private
             */
            _initOrganizationPicker:function(){
                var _self=this;
                var orgPicker = new OrganizationPicker({
                    trigger : ' #joinDepart',
                    rootOrgId:_self.get('rootOrgId'),//必填项
                    rootOrgText:_self.get('rootOrgText'),//必填项
                    url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                    autoHide: true,
                    align : {
                        points:['bl','tl']
                    },
                    zIndex : '10000',
                    width:235,
                    height:250
                });
                orgPicker.render();
                _self.set('orgPicker',orgPicker);
            },
            /**
             * 初始化时间查询
             * @private
             */
            _initDate: function () {
                var _self = this;
                var createStartDate = new Calendar.DatePicker({//加载日历控件
                    trigger: '#createStartDate',
                    showTime: false,
                    autoRender: true,
                    textField:'#createStartDate'
                });
                var createEndDate = new Calendar.DatePicker({//加载日历控件
                    trigger: '#createEndDate',
                    showTime: false,
                    autoRender: true,
                    textField:'#createEndDate'
                });
                _self.set('createStartDate', createStartDate);
                _self.set('createEndDate', createEndDate);
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
            	var columns = [
					{
					    title:'时间',
					    dataIndex:'date',
					    elCls:'center',
					    width:'25%',
					    renderer: Grid.Format.datetimeRenderer
					},
                    {
                        title:'线别',
                        dataIndex:'line',
                        elCls:'center',
                        width:'25%'
                    },{
                        title:'地点',
                        dataIndex:'site',
                        elCls:'center',
                        width:'25%'
                    },{
                        title:'组织部门',
                        dataIndex:'orgDepart',
                        elCls:'center',
                        width:'25%',
                    },{
                        title:'组织者',
                        dataIndex:'organizer',
                        elCls:'center',
                        width:'25%',
                    },{
                        title:'抢险/演练内容',
                        dataIndex:'content',
                        elCls:'center',
                        width:'25%',
                    },{
                        title:'参加部门',
                        dataIndex:'joinDepart',
                        elCls:'center',
                        width:'25%',
                    },{
                        title:'出动车辆（公用）数量',
                        dataIndex:'publicCarCount',
                        elCls:'center',
                        width:'25%',
                    },{
                        title:'出动车辆（租用）数量',
                        dataIndex:'rentCarCount',
                        elCls:'center',
                        width:'25%',
                    },{
                        title:'操作',
                        dataIndex:'orgId',
                        elCls : 'center',
                        width:"25%",
                        renderer:function (e) {
                            var loginOrgId =$('#loginOrgId').val();
                            if(loginOrgId==e){
                            	return '<span  class="grid-command editBtn">修改</span>'+
    	                        '<span  class="grid-command delBtn">删除</span>'+
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
                    url : "/kmms/skitsRescueCompletionAction/findAllDatas",
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
		
//			 /**
//			 * 查询表单对象
//			 */
//			_getFormObject: function(){
//				var _self = this;
//				var form = _self.getChild(SEARCH_FORM_ID,true);
//				var data = form.serializeToObject();
//				data.collectionName = _self.get('collectionName');
//				data.userId = _self.get('userId');
//				return data;
//			},
            
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
//                        },{
//                            xclass : 'bar-item-separator' // 竖线分隔符
//                        },{
//                            id : 'export',
//                            btnCls : 'button button-small exportBtn',
//                            text : '<i class="icon-download "></i>导出'
//                            +'<form action="/kmms/technicalDocumentAction/exportXls" id="exportForm" method="post">'
//    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
//    						+'</form>',
//                        }
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
            	elCls : {value:'technicalDocumentInfoPageCls'},
            	perId : {},
                userId : {},//登录用户ID
                userName : {},//登录用户名称
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                /**
                 * 存储表名(机房台账表名)
                 */
                collectionName : {value:'skitsRescueCompletion'}
            }
        });
    return SkitsRescueCompletionPage;
});