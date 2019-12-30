/**
 * 系统线路编辑
 * @author zhouxingyu
 * @date 19-8-14
 */
define('kmms/technicalManagement/systemDevice/systemDevicePage',['bui/common',
    'bui/data','bui/grid','bui/calendar',
    'common/org/OrganizationPicker',
    'common/grid/SearchGridContainer',
    'common/data/PostLoad',
    'bui/select',
    'kmms/technicalManagement/systemDevice/systemDeviceAdd',
    'kmms/technicalManagement/systemDevice/systemDeviceDetail',
    'kmms/technicalManagement/systemDevice/systemDeviceUpdate'
    ],function(require){
    var BUI = require('bui/common');
    var Grid = require('bui/grid');
    var Data = require('bui/data');
    var Calendar = require('bui/calendar');
    var Select = require('bui/select');
    var PostLoad=require('common/data/PostLoad');
    var OrganizationPicker = require('common/org/OrganizationPicker');
    var SearchGridContainer = require('common/grid/SearchGridContainer');
    var systemDevicePageNewAdd = require('kmms/technicalManagement/systemDevice/systemDeviceAdd');
    var systemDevicePageNewUpdate = require('kmms/technicalManagement/systemDevice/systemDeviceUpdate');
    var systemDevicePageNewDetails = require('kmms/technicalManagement/systemDevice/systemDeviceDetail');
    var SEARCH_FORM_ID = 'searchForm';
    var systemDevicePage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                $('#loginOrgId').val(_self.get('orgId'));
                var windowHeight = $(window).height();
                var search = _self.getChild(SearchGridContainer.SEARCH_FORM_ID,true);
    			var searchHeight = search.get('el').height();
    			_self._initSystem();
    			_self._initDevice();
            },
            bindUI:function(){
            	
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                store.load({});
                $("#system").on('change',function(){
                	var system = $("#system").val();
                	_self._initDevice(system);
                })
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#system").val("");
    				$("#deviceType").val("");
    				_self._initDevice();
    			});
                
              /*  
                });*/
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new systemDevicePageNewAdd({
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
                            url : '/kmms/systemDeviceAction/remove.cn',
                            el : _self.get('el'),
                            loadMsg : '删除中...'
                        });
                        postLoad.load({id:id},function (res) {
                     	   tbar.msg(res);
                     	   store.load();
                        });
						},'question');
                }
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
                    	var infoDialog = new systemDevicePageNewDetails({
                            collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId
                        });
                        infoDialog.show();
                    }
                    /**
                	   * 删除
  	              	   */
  	          	 /* if(target.hasClass('delBtn')){
  	               	   BUI.Message.Confirm('确认要删除吗？',function(){
  		                       var postLoad = new PostLoad({
  		                           url : '/kmms/systemDeviceAction/remove.cn',
  		                           el : _self.get('el'),
  		                           loadMsg : '删除中...'
  		                       });
  		                       postLoad.load({id:docId},function (res) {
  		                    	   tbar.msg(res);
  		                    	   store.load();
  		                       });
  	               	   },'question');
  	                }	  */
                    /**
                     * 修改
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new systemDevicePageNewUpdate({
                           // collectionName:_self.get('collectionName'),
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
            _initSystem:function(){
            	var _self=this;
            	$.ajax({
            		url:'/kmms/systemDeviceAction/getSystem',
            		type:'post',
            		dataType:"json",
            		success:function(res){
//            			var html = "<option>请选择</option>";
            			for(var i=0;i<res.length;i++){
            				$("#system").append("<option value='"+res[i]+"'>"+res[i]+"</option>");
//            				html +='<option>'+res[i]+'</option>';
            			}
//            			$("#system").val(html);
            		}
            	});
            	
            	
            },
            _initDevice:function(system){
            	console.log(system)
            	var _self=this;
            	$.ajax({
            		url:'/kmms/systemDeviceAction/getDevice',
            		type:'post',
            		dataType:"json",
            		data:{'system':system},
            		success:function(res){
//            			var html = "<option></option>";
            			$("#deviceType").empty();
            			$("#deviceType").append("<option value=''>请选择</option>");
            			for(var i=0;i<res.length;i++){
            				$("#deviceType").append("<option value='"+res[i]+"'>"+res[i]+"</option>");
//            				html +='<option>'+res[i]+'<option>';
            			}
//            			$("#deviceType").html(html);
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
                	items: [
                        {
                            label : '系统',
//                            item : '<select id="system" name="system" style="width: 200px;" />'
                            item : '<select name="system" id="system" style="width: 200px;"><option value="">请选择</option></select>'
                        },{
                            label : '线路',
//                            item : '<select id="deviceType" name="deviceType" style="width: 200px;"/>'
                            item : '<select name="deviceType" id="deviceType" style="width: 200px;"><option value="">请选择</option></select>'
                        }
                    ]
                };
            },
            
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
            	var columns = [
                    {
                        title:'系统',
                        dataIndex:'system',
                        elCls:'center',
                        width:'25%'
                    },{
                        title:'设备',
                        dataIndex:'deviceType',
                        elCls:'center',
                        width:'25%'
                    },{
                        title:'备注',
                        dataIndex:'remark',
                        elCls:'center',
                        width:'25%'
                    },
                    {
                        title:'操作',
                        elCls : 'center',
                        width:"25%",
                        renderer:function (e) {
                        	return '<span  class="grid-command editBtn">修改</span>';
	                    	/*'<span  class="grid-command infoBtn">详情</span>';*/
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
                    url : "/kmms/systemDeviceAction/findAll",
                    autoLoad : true ,
                    pageSize : 10,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                   // params : {collectionName:_self.get('collectionName'),userId:_self.get('userId')}
                });
                _self.set('store',store);
                return store;
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
                        },
                        {
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
                return searchGrid;
            }
        },
        {
            ATTRS : {
            	elCls : {value:'systemDeviceInfoPageCls'},
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
                collectionName : {value:'systemDeviceInfo'}
            }
        });
    return systemDevicePage;
});