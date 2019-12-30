/**
 * 机房技术资料及台账
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/technicalManagement/technicalDocument/technicalDocumentPage',['bui/common',
    'bui/data','bui/grid','bui/calendar',
    'common/org/OrganizationPicker',
    'common/grid/SearchGridContainer',
    'common/data/PostLoad',
    'bui/select',
    'kmms/technicalManagement/technicalDocument/technicalDocumentAdd',
    'kmms/technicalManagement/technicalDocument/technicalDocumentDetail',
    'kmms/technicalManagement/technicalDocument/technicalDocumentUpdate',
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
    var TechnicalDocumentPageNewAdd = require('kmms/technicalManagement/technicalDocument/technicalDocumentAdd');
    var TechnicalDocumentPageNewUpdate = require('kmms/technicalManagement/technicalDocument/technicalDocumentUpdate');
    var TechnicalDocumentPageNewDetails = require('kmms/technicalManagement/technicalDocument/technicalDocumentDetail');
    var SEARCH_FORM_ID = 'searchForm';
    var TechnicalDocumentPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                $('#loginOrgId').val(_self.get('orgId'));
                var windowHeight = $(window).height();
                var search = _self.getChild(SearchGridContainer.SEARCH_FORM_ID,true);
    			var searchHeight = search.get('el').height();
//    			_self._initRailAndRoom();
    			_self._getLines();//获取线别下拉选数据
            	_self._getStation();//获取机房下拉选数据
            },
            bindUI:function(){
            	
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                
                store.load({});
                
                
//                $("input[name='railLine']").on('change',function(){
//                	$("#machineRoom").html("");
//                	var  railLine = $("input[name='railLine']").val();
//                	_self._initMachineRoom(railLine);
//                })
               
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
                    var addDialog = new TechnicalDocumentPageNewAdd({
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
                
             
                //导出Excel
				$('.exportBtn').on('click',function(e){
					var records = store.getResult();
    				if(records.length != 0){
    					_self._exportXls(records);
    				}else{
    					tbar.msg({msg:'没有数据可以导出！',status:'0'});
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
                    	var infoDialog = new TechnicalDocumentPageNewDetails({
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
                        var editDialog = new TechnicalDocumentPageNewUpdate({
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
                            label : '所属线别',
                            item : '<div id="railLine" name="railLine" style="width: 200px;" />'
                        },{
                            label : '所属机房',
                            item : '<div id="machineRoom" name="machineRoom" style="width: 200px;"/>'
                        },{
                            label : '资料名称',
                            item : '<input type="text" name="name" id="name" style="width: 175px;" class="input-normal"/>'+
                        	'<input type="hidden"  id="loginOrgId"  readonly/>'//使操作栏中可获取到数据
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
             * 获取线别
             */
            _getLines:function(){
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/networkManageInfoAction/getLines',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	
            	var suggest = new SelectSuggest({
                    renderName: '#railLine',
                    inputName: 'railLine',
                    renderData: nameData,
                    width: 200
                });
            	
            },
            
            _getStation:function(){
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/networkManageInfoAction/getStationNoCondition',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	
            	var suggest = new SelectSuggest({
                    renderName: '#machineRoom',
                    inputName: 'machineRoom',
                    renderData: nameData,
                    width: 200
                });
            },
            
            
            /**
             * 初始化线别
             */
//            _initRailAndRoom:function(){
//            	var _self = this;
//    			var nameData = [];
//    			var postLoad = new PostLoad({
//    				url:'/kmms/constructCooperateAction/getLines',
//    			});
//    			postLoad.load({},function(result){
//    				if(result==null){
//    					return;
//    				}
//    				for(var i=0;i<result.length;i++){
//    					nameData.push(result[i]);
//    				}
//    			});
//    			var suggest = new SelectSuggest({
//    				renderName : '#railLine',
//    				inputName : 'railLine',
//    				renderData : nameData,
//    				width:200
//    			});
//    			var suggest = new SelectSuggest({
//    				renderName : '#machineRoom',
//    				inputName : 'machineRoom',
//    				renderData : [],
//    				width:200
//    			});
//            },
            /**
             * _initRoom初始化机房数据
             */
//            _initMachineRoom:function(railLine){
//    				var nameData = [];
//        			var postLoad = new PostLoad({
//        				url:'/kmms/technicalDocumentAction/getMachineRooms',
//        			});
//        			postLoad.load({railLine:railLine},function(result){
//        				if(result==null){
//        					return;
//        				}
//        				for(var i=0;i<result.length;i++){
//        					nameData.push(result[i]);
//        				}
//        			});
//        			var suggest = new SelectSuggest({
//        				renderName : '#machineRoom',
//        				inputName : 'machineRoom',
//        				renderData : nameData,
//        				width:200
//        			});
//        			
//  
//            },
            
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
            	var columns = [
                    {
                        title:'资料名称',
                        dataIndex:'name',
                        elCls:'center',
                        width:'25%'
                    },{
                        title:'所属线别',
                        dataIndex:'railLine',
                        elCls:'center',
                        width:'25%'
                    },{
                        title:'所属机房',
                        dataIndex:'machineRoom',
                        elCls:'center',
                        width:'20%'
                    },{
                        title:'创建时间',
                        dataIndex:'creatDate',
                        elCls:'center',
                        width:'20%',
                        renderer: Grid.Format.datetimeRenderer
                    },
                    {
                        title:'操作',
                        dataIndex:'orgId',
                        elCls : 'center',
                        width:"10%",
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
                    url : "/kmms/technicalDocumentAction/findAllDatas",
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
		 * 导出本页数据
		 */
		_exportXls:function(records){
			$('#exportXlsJson').val('');//清空值
			// 拼接导出数据的JSON字符串
			var json = '[';
			for(var i = 0 ; i < records.length ; i++){
				var row = records[i];
				json += '{'
					+'"name":'+'"'+row.name+'",'
					+'"railLine":'+'"'+row.railLine+'",'
					+'"machineRoom":'+'"'+row.machineRoom+'",'
					+'"creatDate":'+'"'+Grid.Format.datetimeRenderer(row.creatDate)+'"'
					+'},';
			}
			json = json.substring(0, json.length - 1);
			json += ']';
			$('#exportXlsJson').val(json);
			$('#exportForm').submit();
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
//                        ,{
//                            xclass : 'bar-item-separator' // 竖线分隔符
//                        },{
//                        	id : 'del',
//                            btnCls : 'button button-small delBtn',
//                            text : '<i class="icon-remove"></i>批量删除',
//                        },
                        {
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                            id : 'export',
                            btnCls : 'button button-small exportBtn',
                            text : '<i class="icon-download "></i>导出'
                            +'<form action="/kmms/technicalDocumentAction/exportXls" id="exportForm" method="post">'
    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
    						+'</form>',
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
                collectionName : {value:'technicalDocumentInfo'}
            }
        });
    return TechnicalDocumentPage;
});