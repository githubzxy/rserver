/**
 * 其他生产信息主页
 * @author yangsy
 * @date 19-1-18
 * 修改：增加安全隐患模块
 * 修该人：zhouxingyu
 * @date 19-3-22
 */
define('kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoAdd',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoEdit',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetail',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetailSg',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetailGz',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetailZa',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetailYh',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionAccidentAdd',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionAccidentEdit',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionTroubleAdd',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionTroubleEdit',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionObstacleAdd',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionObstacleEdit',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionSecurityAdd',
	 	'kmms/dayToJobManagement/otherProductionInfo/otherProductionSecurityEdit',
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	otherProductionInfoAdd = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoAdd'),
    	otherProductionInfoEdit = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoEdit'),
    	otherProductionInfoDetail = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetail'),
    	otherProductionInfoDetailSg = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetailSg'),
    	otherProductionInfoDetailGz = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetailGz'),
    	otherProductionInfoDetailZa = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetailZa'),
    	otherProductionInfoDetailYh = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionInfoDetailYh'),
    	
    	otherProductionAccidentAdd = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionAccidentAdd'),
    	otherProductionAccidentEdit = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionAccidentEdit'),
    	otherProductionTroubleAdd = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionTroubleAdd'),
    	otherProductionTroubleEdit = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionTroubleEdit'),
    	otherProductionObstacleAdd = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionObstacleAdd'),
    	otherProductionObstacleEdit = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionObstacleEdit');
		otherProductionSecurityAdd = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionSecurityAdd'),
		otherProductionSecurityEdit = r('kmms/dayToJobManagement/otherProductionInfo/otherProductionSecurityEdit');
    var OtherProductionInfoPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                _self._initSystem();//初始化系统---类别
            	_self._initDevice();
                $("#type").append("<option  value='施工'>施工</option>");
            	$("#type").append("<option  value='通知'>通知</option>");
            	$("#type").append("<option  value='设备故障'>设备故障</option>");
            	$("#type").append("<option  value='生产任务'>生产任务</option>");
            	$("#type").append("<option  value='其他'>其他</option>");
            	
            	$("#infoResult").append("<option  value='0'>无</option>");
            	$("#infoResult").append("<option  value='1'>事故</option>");
    			$("#infoResult").append("<option  value='2'>故障</option>");
    			$("#infoResult").append("<option  value='3'>障碍</option>");
    			$("#infoResult").append("<option  value='4'>安全隐患</option>");
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
//                var orgPicker=_self.get('orgPicker');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                $("#system").on('change',function(){
                	var system = $("#system").val();
                	$("#systemType").removeAttr("disabled");
                	_self._initDevice(system);
                });
                /**
                 * 组织机构选择
                 */
//                orgPicker.on('orgSelected',function (e) {
//                    $('#orgSelectName').val(e.org.text);
//        		    $('#orgSelectId').val(e.org.id);
//                });
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#infoResult").val("");
    				$("#type").val("");
    				$("#startUploadDateSearch").val("");
    				$("#system").val("");
    				$("#systemType").val("");
    				$("#endUploadDateSearch").val("");
    			});
                
                /**
                 * 批量删除
                 */
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
//	                       var postLoad = new PostLoad({
//	                           url : '/kmms/otherProductionInfoAction/removeDoc.cn',
//	                           el : _self.get('el'),
//	                           loadMsg : '删除中...'
//	                       });
//	                       postLoad.load({id:id,collectionName:_self.get('collectionName')},function (res) {
//	                    	   tbar.msg(res);
//	                    	   store.load();
//	                       });
//                	   },'question');
//                   }
//                });
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new otherProductionInfoAdd({
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
                        if(e.result.data.infoResult=="1"){//事故
                        	var accidentAddDialog = new otherProductionAccidentAdd({
                                collectionName:_self.get('collectionName'),
                                busiId:e.result.data.busiId
    	                    });
                            accidentAddDialog.show();
                            accidentAddDialog.on('completeAccidentAddSave',function(e){
    	                        tbar.msg(e.result);
    	                        accidentAddDialog.close();
    	                        store.load();
    	                    });
                        }else if(e.result.data.infoResult=="2"){//故障
                        	var troubleAddDialog = new otherProductionTroubleAdd({
                                collectionName:_self.get('collectionName'),
                                busiId:e.result.data.busiId
    	                    });
                        	troubleAddDialog.show();
                        	troubleAddDialog.on('completeTroubleAddSave',function(e){
    	                        tbar.msg(e.result);
    	                        troubleAddDialog.close();
    	                        store.load();
    	                    });
                        }else if(e.result.data.infoResult=="3"){//障碍
                        	var obstacleAddDialog = new otherProductionObstacleAdd({
                                collectionName:_self.get('collectionName'),
                                busiId:e.result.data.busiId
    	                    });
                        	obstacleAddDialog.show();
                        	obstacleAddDialog.on('completeObstacleAddSave',function(e){
    	                        tbar.msg(e.result);
    	                        obstacleAddDialog.close();
    	                        store.load();
    	                    });
                        }else if(e.result.data.infoResult=="4"){//安全隐患
                        	var securityAddDialog = new otherProductionSecurityAdd({
                                collectionName:_self.get('collectionName'),
                                busiId:e.result.data.busiId
    	                    });
                        	securityAddDialog.show();
                        	securityAddDialog.on('completeSecurityAddSave',function(e){
    	                        tbar.msg(e.result);
    	                        securityAddDialog.close();
    	                        store.load();
    	                    });
                        }
                        
                    });
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
                     * 事故详情
                     */
                    if(target.hasClass('infoBtnSg')){
                    	var infoDialog = new otherProductionAccidentEdit({
                    		collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId,
                    		busiId:busiId
                    	});
                    	infoDialog.show();
                    	infoDialog.on('completeAccidentAddSave',function(e){
	                        tbar.msg(e.result);
	                        infoDialog.close();
	                        store.load();
	                    });
                    }
                    /**
                     * 故障详情
                     */
                    if(target.hasClass('infoBtnGz')){
                    	var infoDialog = new otherProductionTroubleEdit({
                    		collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId,
                    		busiId:busiId
                    	});
                    	infoDialog.show();
                    	infoDialog.on('completeTroubleAddSave',function(e){
	                        tbar.msg(e.result);
	                        infoDialog.close();
	                        store.load();
	                    });
                    }
                    /**
                     * 障碍详情
                     */
                    if(target.hasClass('infoBtnZa')){
                    	var infoDialog = new otherProductionObstacleEdit({
                    		collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId,
                    		busiId:busiId
                    	});
                    	infoDialog.show();
                    	infoDialog.on('completeObstacleAddSave',function(e){
	                        tbar.msg(e.result);
	                        infoDialog.close();
	                        store.load();
	                    });
                    }
                    /**
                     * 隐患详情
                     */
                    if(target.hasClass('infoBtnYh')){
                    	var infoDialog = new otherProductionSecurityEdit({
                    		collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId,
                    		busiId:busiId
                    	});
                    	infoDialog.show();
                    	infoDialog.on('completeSecurityAddSave',function(e){
	                        tbar.msg(e.result);
	                        infoDialog.close();
	                        store.load();
	                    });
                    }
                    /**
                     * 详情
                     */
                    if(target.hasClass('detailBtn')){
                        var infoDialog = new otherProductionInfoDetail({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            shiftId:docId
                        });
                        infoDialog.show();
                    }
                    
                    /**
                     * 带有事故的详情
                     */
                    if(target.hasClass('detailBtnSg')){
                    	var infoDialog = new otherProductionInfoDetailSg({
                    		collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId
                    	});
                    	infoDialog.show();
                    }
                    /**
                     * 带有故障的详情
                     */
                    if(target.hasClass('detailBtnGz')){
                    	var infoDialog = new otherProductionInfoDetailGz({
                    		collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId
                    	});
                    	infoDialog.show();
                    }
                    /**
                     * 带有障碍的详情
                     */
                    if(target.hasClass('detailBtnZa')){
                    	var infoDialog = new otherProductionInfoDetailZa({
                    		collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId
                    	});
                    	infoDialog.show();
                    }
                    /**
                     * 带有隐患的详情
                     */
                    if(target.hasClass('detailBtnYh')){
                    	var infoDialog = new otherProductionInfoDetailYh({
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
                        var editDialog = new otherProductionInfoEdit({
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
                    	{label : '信息后果',item : '<select type="text" name="infoResult" id="infoResult" style="width: 211px;"><option value="" >请选择</option></select>'},
                        {label : '类型',item : '<select type="text" id="type" name="type" style="width: 211px;"><option value="" >请选择</option></select>'},                  
                     	{label : '设备所属系统',item : '<select type="text" name="system" id="system" style="width: 211px;"></select>'},
                        {label : '设备类别',item : '<select type="text" id="systemType" name="systemType" style="width: 211px;" disabled></select>'},
                        {label : '内容及处理情况',item : '<input type="text" name="detail" id="detail"  style="width: 211px;" />'},
                        {label : '开始时间',item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 185px;" readonly/>'},
                        {label : '结束时间',item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" class="calendar" style="width: 185px;" readonly/>'}
                    ]};
            },
            /**
             * 初始化组织机构选择
             * @private
             */
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
            _initSystem:function(){
            	var _self=this;
            	$.ajax({
            		url:'/kmms/systemDeviceAction/getSystem',
            		type:'post',
            		dataType:"json",
            		success:function(res){
//            			var html = "<option>请选择</option>";
            			$("#system").append("<option value=''>请选择</option>");
            			for(var i=0;i<res.length;i++){
            				$("#system").append("<option value='"+res[i]+"'>"+res[i]+"</option>");
//            				html +='<option>'+res[i]+'</option>';
            			}
//            			$("#system").val(html);
            		}
            	});
            	
            	
            },
            _initDevice:function(system){
            	var _self=this;
            	$.ajax({
            		url:'/kmms/systemDeviceAction/getDevice',
            		type:'post',
            		dataType:"json",
            		data:{'system':system},
            		success:function(res){
//            			var html = "<option></option>";
            			$("#systemType").empty();
            			$("#systemType").append("<option value=''>请选择</option>");
            			for(var i=0;i<res.length;i++){
            				$("#systemType").append("<option value='"+res[i]+"'>"+res[i]+"</option>");
//            				html +='<option>'+res[i]+'<option>';
            			}
//            			$("#deviceType").html(html);
            		}
            	});
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
                        title:'时间',
                        dataIndex:'createDate',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'信息反馈部门',
                        dataIndex:'backOrgName',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'信息反馈人',
                        dataIndex:'backPerson',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'类型',
                        dataIndex:'type',
                        elCls : 'center',
                        width:'10%',
                    },{
                        title:'内容及处理情况',
                        dataIndex:'detail',
                        elCls : 'center',
                        width:'25%',
                    },{
                        title:'信息后果',
                        dataIndex:'infoResult',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (value) {
                        	if(value=='0'){//无
                        		return "无";
                        	} else if(value=='1'){//事故
                        		return "事故";
                        	} else if(value=='2'){//故障
                        		return "故障";
                        	} else if(value=='3'){//障碍
                        		return "障碍";
                        	} else if(value=='4'){
                        		return "安全隐患";
                        	}
                        }
                    },{
                        title:'是否遗留',
                        dataIndex:'lost',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (value) {
                        	if(value=='0'){
                        		return "否";
                        	} else if(value=='1'){
                        		return "是";
                        	} 
                        }
                    },{
                        title:'备注',
                        dataIndex:'remark',
                        elCls : 'center',
                        width:'25%',
                    },{
                        title:'操作',
                        dataIndex:'summaryPersonIdAndInfoResult',
                        elCls : 'center',
                        width:'15%',
                        renderer:function (value) {
                        	
                        	var valueArray = value.split(",");
                            var button = '';
                            if(true){
                            	button = '<span  class="grid-command editBtn">修改</span>'+
                            	'<span  class="grid-command delBtn">删除</span>';
                            	if(valueArray[1]=='1'){//事故
                            		button += '<span  class="grid-command infoBtnSg">事故情况</span>'+
														'<span  class="grid-command detailBtnSg">详情</span>';
                            	} else if(valueArray[1]=='2'){//故障
                            		button += '<span  class="grid-command infoBtnGz">故障情况</span>'+
        												'<span  class="grid-command detailBtnGz">详情</span>';
                            	} else if(valueArray[1]=='3'){//障碍
                            		button += '<span  class="grid-command infoBtnZa">障碍情况</span>'+
                            							'<span  class="grid-command detailBtnZa">详情</span>';
                            	} else if(valueArray[1]=='4'){
                            		button += '<span  class="grid-command infoBtnYh">隐患情况</span>'+
        							'<span  class="grid-command detailBtnYh">详情</span>';
                            	}else {
                            		button += '<span  class="grid-command detailBtn">详情</span>';
                            	}
                            }else {
                            	if(valueArray[1]=='1'){//事故
                            		button += '<span  class="grid-command detailBtnSg">详情</span>';
                            	} else if(valueArray[1]=='2'){//故障
                            		button += '<span  class="grid-command detailBtnGz">详情</span>';
                            	} else if(valueArray[1]=='3'){//障碍
                            		button += '<span  class="grid-command detailBtnZa">详情</span>';
                            	} else if(valueArray[1]=='4'){
                            		button += '<span  class="grid-command detailBtnYh">详情</span>';
                            	} else {
                            		button += '<span  class="grid-command detailBtn">详情</span>';
                            	}
                        	}
                            return button;
                        	
//                        	var button = '<span  class="grid-command editBtn">修改</span>';
//                        	if(value=='1'){//事故
//                        		button += '<span  class="grid-command infoBtnSg">事故情况</span>';
//                        	} else if(value=='2'){//故障
//                        		button += '<span  class="grid-command infoBtnGz">故障情况</span>';
//                        	} else if(value=='3'){//障碍
//                        		button += '<span  class="grid-command infoBtnZa">障碍情况</span>';
//                        	}
//                        	button += '<span  class="grid-command detailBtn">详情</span>';
//                            return button;
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
                    url : "/kmms/otherProductionInfoAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 20,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName'),userId:_self.get('userId'),orgId:_self.get('orgId')}//当前登陆人ID用于判断是否需要查询展示的数据
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
                        }
//                        ,{
//                            xclass : 'bar-item-separator' // 竖线分隔符
//                        },{
//                            id : 'del',
//                            btnCls : 'button button-small delBtn',
//                            text : '<i class="icon-remove"></i>批量删除',
//                        }
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
    return OtherProductionInfoPage;
});