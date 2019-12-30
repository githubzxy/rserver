/**
 * 遗留信息主页
 * @author yangsy
 * @date 19-1-18
 * 修改：增加安全隐患遗留信息的查看
 * 修该人：zhouxingyu
 * @date 19-3-24
 */
define('kmms/dayToJobManagement/remainInfo/remainInfoPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/dayToJobManagement/remainInfo/remainInfoEdit',
	 	'kmms/dayToJobManagement/remainInfo/remainInfoDetail',
	 	'kmms/dayToJobManagement/remainInfo/remainInfoDetailSg',
	 	'kmms/dayToJobManagement/remainInfo/remainInfoDetailGz',
	 	'kmms/dayToJobManagement/remainInfo/remainInfoDetailZa',
	 	'kmms/dayToJobManagement/remainInfo/remainInfoDetailYh',
	 	'kmms/dayToJobManagement/remainInfo/remainAccidentEdit',
	 	'kmms/dayToJobManagement/remainInfo/remainTroubleEdit',
	 	'kmms/dayToJobManagement/remainInfo/remainObstacleEdit',
	 	'kmms/dayToJobManagement/remainInfo/remainSecurityEdit'
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	remainInfoEdit = r('kmms/dayToJobManagement/remainInfo/remainInfoEdit'),
    	remainInfoDetail = r('kmms/dayToJobManagement/remainInfo/remainInfoDetail'),
    	remainInfoDetailSg = r('kmms/dayToJobManagement/remainInfo/remainInfoDetailSg'),
    	remainInfoDetailGz = r('kmms/dayToJobManagement/remainInfo/remainInfoDetailGz'),
    	remainInfoDetailZa = r('kmms/dayToJobManagement/remainInfo/remainInfoDetailZa'),
    	remainInfoDetailYh = r('kmms/dayToJobManagement/remainInfo/remainInfoDetailYh'),
    	remainAccidentEdit = r('kmms/dayToJobManagement/remainInfo/remainAccidentEdit'),
    	remainTroubleEdit = r('kmms/dayToJobManagement/remainInfo/remainTroubleEdit'),
    	remainObstacleEdit = r('kmms/dayToJobManagement/remainInfo/remainObstacleEdit'),
    	remainSecurityEdit = r('kmms/dayToJobManagement/remainInfo/remainSecurityEdit');
    var RemainInfoPage = BUI.Component.Controller.extend(
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
                $("#type").append("<option  value='动环'>动环</option>");
            	$("#type").append("<option  value='G网'>G网</option>");
            	$("#type").append("<option  value='传输'>传输</option>");
            	$("#type").append("<option  value='ONU'>ONU</option>");
            	$("#type").append("<option  value='闭塞'>闭塞</option>");
            	$("#type").append("<option  value='交换'>交换</option>");
            	$("#type").append("<option  value='无线列调'>无线列调</option>");
            	$("#type").append("<option  value='气压监测'>气压监测</option>");
            	$("#type").append("<option  value='综合视频'>综合视频</option>");
            	$("#type").append("<option  value='图像监控'>图像监控</option>");
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
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#infoResult").val("");
    				$("#type").val("");
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
    				$("#system").val("");
    				$("#systemType").val("");
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
                     * 事故详情
                     */
                    if(target.hasClass('infoBtnSg')){
                    	var infoDialog = new remainAccidentEdit({
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
                    	var infoDialog = new remainTroubleEdit({
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
                    	var infoDialog = new remainObstacleEdit({
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
                     * 安全隐患详情
                     */
                    if(target.hasClass('infoBtnYh')){
                    	var infoDialog = new remainSecurityEdit({
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
                        var infoDialog = new remainInfoDetail({
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
                    	var infoDialog = new remainInfoDetailSg({
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
                    	var infoDialog = new remainInfoDetailGz({
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
                    	var infoDialog = new remainInfoDetailZa({
                    		collectionName:_self.get('collectionName'),
                    		userId:_self.get('userId'),
                    		shiftId:docId
                    	});
                    	infoDialog.show();
                    }
                    /**
                     * 带有障碍的详情
                     */
                    if(target.hasClass('detailBtnYh')){
                    	var infoDialog = new remainInfoDetailYh({
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
                        var editDialog = new remainInfoEdit({
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
                        	} else if(value=='4'){//安全隐患
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
                        title:'是否打印',
                        dataIndex:'print',
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
                        width:'20%',
                        renderer:function (value) {
                        	
                        	var valueArray = value.split(",");
                            var button = '';
                            button = '<span  class="grid-command editBtn">修改</span>';
                        	if(valueArray[1]=='1'){//事故
                        		button += '<span  class="grid-command infoBtnSg">事故情况</span>'+
													'<span  class="grid-command detailBtnSg">详情</span>';
                        	} else if(valueArray[1]=='2'){//故障
                        		button += '<span  class="grid-command infoBtnGz">故障情况</span>'+
    												'<span  class="grid-command detailBtnGz">详情</span>';
                        	} else if(valueArray[1]=='3'){//障碍
                        		button += '<span  class="grid-command infoBtnZa">障碍情况</span>'+
                        							'<span  class="grid-command detailBtnZa">详情</span>';
                        	} else if(valueArray[1]=='4'){//安全隐患
                        		button += '<span  class="grid-command infoBtnYh">隐患情况</span>'+
    												'<span  class="grid-command detailBtnYh">详情</span>';
                        	} else {
                        		button += '<span  class="grid-command detailBtn">详情</span>';
                        	}
                            /*if(valueArray[0]==""){
                            	button = '<span  class="grid-command editBtn">修改</span>';
                            	if(valueArray[1]=='1'){//事故
                            		button += '<span  class="grid-command infoBtnSg">事故情况</span>'+
														'<span  class="grid-command detailBtnSg">详情</span>';
                            	} else if(valueArray[1]=='2'){//故障
                            		button += '<span  class="grid-command infoBtnGz">故障情况</span>'+
        												'<span  class="grid-command detailBtnGz">详情</span>';
                            	} else if(valueArray[1]=='3'){//障碍
                            		button += '<span  class="grid-command infoBtnZa">障碍情况</span>'+
                            							'<span  class="grid-command detailBtnZa">详情</span>';
                            	} else if(valueArray[1]=='4'){//安全隐患
                            		button += '<span  class="grid-command infoBtnYh">隐患情况</span>'+
        												'<span  class="grid-command detailBtnYh">详情</span>';
                            	} else {
                            		button += '<span  class="grid-command detailBtn">详情</span>';
                            	}
                            }else {
                            	button = '<span  class="grid-command editBtn">修改</span>';
                            	if(valueArray[1]=='1'){//事故
                            		button += '<span  class="grid-command detailBtnSg">详情</span>';
                            	} else if(valueArray[1]=='2'){//故障
                            		button += '<span  class="grid-command detailBtnGz">详情</span>';
                            	} else if(valueArray[1]=='3'){//障碍
                            		button += '<span  class="grid-command detailBtnZa">详情</span>';
                            	} else if(valueArray[1]=='4'){//安全隐患
                            		button += '<span  class="grid-command detailBtnYh">详情</span>';
                            	} else {
                            		button += '<span  class="grid-command detailBtn">详情</span>';
                            	}
                        	}*/
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
                    url : "/kmms/remainInfoAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 20,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName'),userId:_self.get('userId')}//当前登陆人ID用于判断是否需要查询展示的数据
                });
                _self.set('store',store);
                return store;
            },
            
            
            /**
             * 初始化列表展示对象
             * @private
             */
            _initSearchGrid:function () {
//            	var _self = this;
                var searchGrid = {
                    tbarItems : [],
                    plugins : [
//                        Grid.Plugins.CheckSelection,
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
    return RemainInfoPage;
});