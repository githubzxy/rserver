/**
 * 检修记录主页
 * @author xiekun
 * @date 19-3-25
 */
define('kmms/productionManage/overhaulRecord/overhaulRecordPage',
		[
			'bui/common',
			'bui/data',
			'bui/grid',
			'bui/calendar',
			'bui/mask',
			'common/data/PostLoad',
			'common/grid/SearchGridContainer',
			'kmms/commonUtil/SelectSuggest',
			'kmms/productionManage/overhaulRecord/overhaulRecordAdd',
		], function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        Mask = r('bui/mask'),
        PostLoad = r('common/data/PostLoad'),
        SearchGridContainer = r('common/grid/SearchGridContainer'),
        SelectSuggest = r('kmms/commonUtil/SelectSuggest'),
	    overhaulRecordAdd = r('kmms/productionManage/overhaulRecord/overhaulRecordAdd');

    var overhaulRecordPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
             	_self._getWorkshops();//获取车间下拉选数据
            	_self._getLines();//获取线别下拉选数据
            	_self._getMachineRooms();//获取机房下拉选数据
            	$("#overhaulName").append("<option  value='高铁防灾设备检修'>高铁防灾设备检修</option>");
     			$("#overhaulName").append("<option  value='高铁中间站设备检修'>高铁中间站设备检修</option>");
     			$("#overhaulName").append("<option  value='普铁中间站设备检修'>普铁中间站设备检修</option>");
//     			$("#overhaulName").append("<option  value='直放站通信设备检修'>直放站通信设备检修</option>");
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                //工区下拉选选项根据车间而变化
                $("#workshop").on('change', function() {
                    $("#workArea").empty();
                    var workshop = $("#workshop").val();
                    _self._getWorkAreas(workshop);

                });
                //机房下拉选选项根据线别而变化
//                $("#line").on('change', function() {
//                    $("#machineRoom").empty();
//                    var line = $("#line").val();
//                    _self._getMachineRooms(line);
//
//                });
                $("#lineCondition").on('change', function() {
//                    $("#machineRoomCondition").empty();
                    var lineValue = $("#lineCondition").context.activeElement.value;
                    if(lineValue){
                    	_self._getMachineRoomsByLine(lineValue);
                    }else{
                    	_self._getAllMachineRooms();
                    }
                });
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#workshop").val("");
    				$("#workArea").val("");
    				$("input[name='lineCondition']").val("");
//    				$("#lineCondition").val("");
    				$("input[name='machineRoomCondition']").val("");
    				_self._getAllMachineRooms();
//    				$("#machineRoomCondition").val("");
    				$("#overhaulName").val("");
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
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
	                           url : '/kmms/overhaulRecordAction/removeDoc.cn',
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
                    var addDialog = new overhaulRecordAdd({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            parentId:_self.get('parentId'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                    });
                    addDialog.show();
                });
               
                /**
                * 操作按钮
                */
               table.on('cellclick',function(ev){
            	   var record = ev.record; //点击行的记录
            	   var target = $(ev.domTarget);
            	   var docId = record.docId; //点击的元素
            	   var overhaulName = record.overhaulName;
            	   var overhaulDate = record.overhaulDate;
            	   var day = overhaulDate.substring(0,10);
//            	   console.log(record);
                /**
                 * 查看检修记录
                 */
            	  if(target.hasClass('exportBtn')){
            		  if(overhaulName=="高铁防灾设备检修"){
            			var url = '/kmms/overhaulRecordAction/exportHighTrainFZ.cn';
            		  }
            		  if(overhaulName=="高铁中间站设备检修"){
              			var url = '/kmms/overhaulRecordAction/exportHighTrainZJZ.cn';
              		  }
            		  if(overhaulName=="普铁中间站设备检修"){
                		var url = '/kmms/overhaulRecordAction/exportTrainZJZ.cn';
                	  }
            		  if(overhaulName=="直放站通信设备检修"){
                  		var url = '/kmms/overhaulRecordAction/exportHighTrainZFZ.cn';
                  	  }
            		  var postLoad = new PostLoad({
          				url : url,
          				ajaxOptions : {
          					contentType : 'application/json;charset=utf-8',
          					dataType : 'json', 
          					data : JSON.stringify(record),
          				},
          				el : '',
          				loadMsg : '下载中...'
          			});
          			postLoad.load({}, function(result) {
//          				console.log(result);
//          				window.location.href = result.msg;
          				window.location.href = "/kmms/commonAction/download?path=" + result.msg +'&fileName='+day+overhaulName+".docx";// 下载路径
          			});
                  }
//            	  if(target.hasClass('queryBtn')){
//            		  if(overhaulName=="高铁防灾设备检修"){
//            			  var url = '/kmms/overhaulRecordAction/exportHighTrainFZ.cn';
//            		  }
//            		  if(overhaulName=="高铁中间站设备检修"){
//            			  var url = '/kmms/overhaulRecordAction/exportHighTrainZJZ.cn';
//            		  }
//            		  if(overhaulName=="普铁中间站设备检修"){
//            			  var url = '/kmms/overhaulRecordAction/exportTrainZJZ.cn';
//            		  }
//            		  if(overhaulName=="直放站通信设备检修"){
//            			  var url = '/kmms/overhaulRecordAction/exportHighTrainZFZ.cn';
//            		  }
//            		  var postLoad = new PostLoad({
//            			  url : url,
//            			  ajaxOptions : {
//            				  contentType : 'application/json;charset=utf-8',
//            				  dataType : 'json', 
//            				  data : JSON.stringify(record)
//            			  },
//            			  el : '',
//            			  loadMsg : '下载中...'
//            		  });
//            		  postLoad.load({}, function(result) {
//            			  console.log(result);
//            			  window.location.href = "/pageoffice/openPage?path=" + result.msg;
//            		  });
////            		  var tempv = window.open("../tempFileShow.html");
////                	  tempv.location.href = '/pageoffice/openPage?filePath='+filePath;
//            	  }
                   /**
                    * 编辑
                    */
                   if(target.hasClass('editBtn')){
                       var editDialog = new overhaulRecordEdit({
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
                        {label : '车间',item : '<select type="text" name="workshop" id="workshop" style="width: 200px;"></select>'},
                        {label : '工区',item : '<select type="text" name="workArea" id="workArea" style="width: 200px;"></select>'},
//                        {label : '线别',item : '<select type="text" name="line" id="line" style="width: 175px;"></select>'},
                        {label : '线别',item : '<div id="lineCondition" name="lineCondition" style="width: 200px;" />'},
//                        {label : '机房',item : '<select type="text" name="machineRoom" id="machineRoom" style="width: 175px;"></select>'},
                        {label : '机房',item : '<div id="machineRoomCondition" name="machineRoomCondition" style="width: 200px;" />'},
                        {label : '检修名称',item : '<select type="text" name="overhaulName" id="overhaulName" style="width: 200px;"><option value="" >请选择</option></select>'},
                        {label : '检修时间',item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'},
                        {label : '至',item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" style="width: 175px;" class="calendar" readonly/>'}
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
                var endUploadDateSearch = new Calendar.DatePicker({//加载日历控件
                    trigger: '#endUploadDateSearch',
                    showTime: true,
                    autoRender: true,
                    textField:'#endUploadDateSearch'
                });
                _self.set('startUploadDate', startUploadDate);
                _self.set('endUploadDateSearch', endUploadDateSearch);
            },
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
    	               	 $(" #workshop").append("<option  value=''>请选择</option>");
    	               	 for(var i=0;i<res.length;i++){
    	               		 $("#workshop").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
    	               	 var workshop = $("#workshop").val();
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
             * 获取线别
             */
            _getLines:function(){
//            	var _self=this;
//            	$.ajax({
//    	                url:'/kmms/overhaulRecordAction/getLines',
//    	                type:'post',
//    	                dataType:"json",
//    	                success:function(res){
//    	               	 $("#line").append("<option  value=''>请选择</option>");
//    	               	 for(var i=0;i<res.length;i++){
//    	               		 $("#line").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
//    	               	 }
//    	               	 var line = $("#line").val();
//    	               	 _self._getMachineRooms(line);
//                    }
//                });
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/lineNameMangementAction/getLines',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	
            	var suggest = new SelectSuggest({
                    renderName: '#lineCondition',
                    inputName: 'lineCondition',
                    renderData: nameData,
                    width: 200
                });
            },
            /**
             * 获取机房（初始化 无条件获取全部数据）
             */
            _getMachineRooms:function(){
//            	var _self=this;
//            	$.ajax({
//    	                url:'/kmms/overhaulRecordAction/getMachineroom',
//    	                type:'post',
//    	                dataType:"json",
//    	                data: { line:line},
//    	                success:function(res){
//    	               	 $("#machineRoom").append("<option  value=''>请选择</option>");
//    	               	 for(var i=0;i<res.length;i++){
//    	               		 $("#machineRoom").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
//    	               	 }
//                    }
//                });
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/machineRoomManageAction/getMachineRooms',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	
            	var suggest = new SelectSuggest({
                    renderName: '#machineRoomCondition',
                    inputName: 'machineRoomCondition',
                    renderData: nameData,
                    width: 200
                });
            	_self.set("machineRoomSuggest",suggest);
            },
            /**
             * 根据所选择线别名称获取机房数据
             */
            _getMachineRoomsByLine:function(line){
//            	console.log(line);
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/machineRoomManageAction/getMachineRoomsByLine',
            		type:'post',
            		dataType:"json",
            		data: {line:line},
            		async:false,
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	_self.get("machineRoomSuggest").resetRender(nameData);
            },
            /**
             * 点击重置按钮或者使用回退键将线别输入框中的内容清除掉时调用的方法（获取全部机房数据）
             */
            _getAllMachineRooms:function(){
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/machineRoomManageAction/getMachineRooms',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	_self.get("machineRoomSuggest").resetRender(nameData);
            },
            
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                    {
                        title:'检修人',
                        dataIndex:'overhaulPerson',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'检修时间',                       
                        dataIndex:'overhaulDate',
                        elCls : 'center',
                        width:'25%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'车间',
                        dataIndex:'workshop',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'工区',
                        dataIndex:'workArea',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'线别',
                        dataIndex:'line',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'机房',
                        dataIndex:'machineRoom',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'检修名称',
                        dataIndex:'overhaulName',
                        elCls : 'center',
                        width:'20%',
                    },{
                    	title:'操作',
                        dataIndex:'id',
                        elCls : 'center',
                        width:'12%',
                        renderer:function (e) {
//                        	return '<span  class="grid-command queryBtn">查看</span>'+'<span  class="grid-command exportBtn">下载</span>';
                        	return '<span  class="grid-command exportBtn">下载</span>';
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
                    url : "/kmms/overhaulRecordAction/findAll.cn",
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
            	if(_self.get("orgType")==1503){
            		searchGrid = {
                            tbarItems : [
                                {
                                    id : 'add',
                                    btnCls : 'button button-small addBtn',
                                    text : '<i class="icon-plus"></i>检修记录填报',
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
                perId : {},
                orgType : {},//登录用户上级机构的组织机构
                parentId : {},//登录用户上级机构的组织机构
                userId : {},//登录用户ID
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                collectionName : {value:'overhaulRecord'}//存储表名
            }
        });
    return overhaulRecordPage;
});