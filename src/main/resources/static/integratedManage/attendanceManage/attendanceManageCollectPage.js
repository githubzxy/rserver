/**
 * 考勤管理汇总页面
 * @author yangsy
 * @date 19-4-2
 */
define('kmms/integratedManage/attendanceManage/attendanceManageCollectPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/integratedManage/attendanceManage/attendanceManageCollectAdd',
	 	'kmms/integratedManage/attendanceManage/attendanceManageCollectImport',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyAdd',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyModify',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyDetail',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplySubmit',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyCheckEdit',
	 	'kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyApproveEdit',
	],function (r) {
    var BUI = r('bui/common'),
    	Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
    	attendanceManageCollectAdd = r('kmms/integratedManage/attendanceManage/attendanceManageCollectAdd'),
    	attendanceManageCollectImport = r('kmms/integratedManage/attendanceManage/attendanceManageCollectImport'),
    	circuitWorkOrderApplyAdd = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyAdd'),
    	circuitWorkOrderApplyModify = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyModify'),
    	circuitWorkOrderApplyDetail = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyDetail'),
    	circuitWorkOrderApplySubmit = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplySubmit'),
    	circuitWorkOrderApplyCheckEdit = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyCheckEdit'),
    	circuitWorkOrderApplyApproveEdit = r('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplyApproveEdit');
    var AttendanceManageCollectPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                _self._initFlowState();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#flowState").val("");
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
    			});
                
                /**
                 * 批量删除
                 */
                $(".delBtn").on('click',function () {
                   var removeIds = table.getSelection();
                   
                   attendanceOrgIds = removeIds.map(function (item) {
                       return item.attendanceOrgId;
                   });
                   
                   for(var i = 0;i<attendanceOrgIds.length;i++){
                	   if(attendanceOrgIds[i]!=_self.get("orgId")){
                		   tbar.msg({status:0,msg:'所选的删除项中存在工区提交的数据，不能进行删除！'})
                		   return;
                	   }
                   }
                   
                   removeIds = removeIds.map(function (item) {
                       return item.docId;
                   });
                   var id = removeIds.join(",");
                   if(!id){
                       tbar.msg({status:0,msg:'至少选择一项要删除的项目！'})
                   }else{
                	   BUI.Message.Confirm('确认要删除吗？',function(){
	                       var postLoad = new PostLoad({
	                           url : '/kmms/attendanceManageCollectAction/removeDoc.cn',
	                           el : _self.get('el'),
	                           loadMsg : '删除中...'
	                       });
	                       postLoad.load({id:id,collectionName:_self.get('attendanceManageCollect')},function (res) {
	                    	   tbar.msg(res);
	                    	   store.load();
	                       });
                	   },'question');
                   }
                });
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new attendanceManageCollectAdd({
                    		attendanceManage:_self.get('attendanceManage'),
                    		attendanceManageCollect:_self.get('attendanceManageCollect'),
                            userId:_self.get('userId'),
                            userName:_self.get('userName'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                            parentId:_self.get('parentId'),
                            orgType:_self.get('orgType'),
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
                    	  docId = record.docId; //点击的元素（数据库主键的值String）
                    	  busiId = record.busiId;//自定义的业务ID
                    	  filePath = record.filePath;
                    	  date = record.date;
                    	  attendanceOrgName = record.attendanceOrgName;
                    /**
                     * 详情
                     */
                    if(target.hasClass('detailBtn')){
                        var infoDialog = new circuitWorkOrderApplyDetail({
                            collectionName:_self.get('collectionName'),
                            shiftId:docId
                        });
                        infoDialog.show();
                    }
                    /**
                     * 编辑
                     */
                    if(target.hasClass('editBtn')){
//                        var editDialog = new circuitWorkOrderApplyModify({
//                            collectionName:_self.get('collectionName'),
//                            shiftId:docId
//                        });
//                        editDialog.show();
//                        editDialog.on('completeAddSave',function(e){
//                            tbar.msg(e.result);
//                            editDialog.close();
//                            store.load();
//                        });
                    	if(filePath){
                    		var tempv = window.open("../tempFileShow.html");
                    		tempv.location.href = '/pageoffice/?filePath='+filePath;
                    	}else{
                    		BUI.Message.Alert('还未生成汇总信息！');
    	        			return;
                    	}
                    }
                    /**
                     * 导入（覆盖原有的filePtah）
                     */
                    if(target.hasClass('importBtn')){
                    	var addDialog = new attendanceManageCollectImport({
                            collectionName:_self.get('attendanceManageCollect'),
                            id:docId,
                            userId:_self.get('userId'),
                            userName:_self.get('userName'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                            parentId:_self.get('parentId'),
                    	});
	                    addDialog.show();
	                    addDialog.on('completeAddSave',function(e){
	                        tbar.msg(e.result);
	                        addDialog.close();
	                        store.load();
	                    });
                    }
                    /**
                     * 回退
                     */
                    if(target.hasClass('backBtn')){
                    	BUI.Message.Confirm('确认要回退吗？',function(){
  	                       var postLoad = new PostLoad({
  	                           url : '/kmms/attendanceManageCollectAction/backCollectDoc.cn',
  	                           el : _self.get('el'),
  	                           loadMsg : '回退中...'
  	                       });
                    	  postLoad.load({id:docId,flowState:"3",workshopQueryData:"",attendanceManageCollect:_self.get('attendanceManageCollect')},function (res) {
                    		   tbar.msg(res);
                    		   store.load();
                    	   });
                  	   },'question');
                    }
                    /**
                     * 查看
                     */
                    if(target.hasClass('searchBtn')){
                    	if(filePath){
                    		var tempv = window.open("../tempFileShow.html");
                    		tempv.location.href = '/pageoffice/openPage?filePath='+filePath;
                    	}else{
                    		BUI.Message.Alert('还未生成汇总信息！');
                    		return;
                    	}
                    }
                    /**
                     * 下载
                     */
                    if(target.hasClass('downBtn')){
                    	if(filePath){
                    		window.location.href = "/kmms/commonAction/download?path=" + filePath+'&fileName='+"中国铁路昆明局集团有限公司"+attendanceOrgName+date+"考勤表.xlsx";// 下载路径
                    	}else{
                    		BUI.Message.Alert('还未生成汇总信息！');
    	        			return;
                    	}
                    }
                    /**
                     * 工区提交车间审核
                     */
                    if(target.hasClass('submitBtn')){
                    	BUI.Message.Confirm('确认要提交吗？',function(){
 	                       var postLoad = new PostLoad({
 	                           url : '/kmms/attendanceManageCollectAction/updateCollectDoc.cn',
 	                           el : _self.get('el'),
 	                           loadMsg : '提交中...'
 	                       });
 	                       if(_self.get('orgType')==1503){//工区用户提交给车间（办公室/职教科）用户设置给车间（办公室/职教科）用户需要查看的数据标识为该工区的上级机构ID（workshopQueryData）
 	                    	  postLoad.load({id:docId,flowState:"0",workshopQueryData:_self.get('parentId'),attendanceManageCollect:_self.get('attendanceManageCollect')},function (res) {
	                    		   tbar.msg(res);
	                    		   store.load();
	                    	   });
 	                       }else{
 	                    	   postLoad.load({id:docId,flowState:"1",attendanceManageCollect:_self.get('attendanceManageCollect')},function (res) {
 	                    		   tbar.msg(res);
 	                    		   store.load();
 	                    	   });
 	                       }
                 	   },'question');
                    }
//                    if(target.hasClass('submitBtn')){
//                    	var submitDialog = new circuitWorkOrderApplySubmit({
//                    		collectionName:_self.get('collectionName'),
//                    		shiftId:docId
//                    	});
//                    	submitDialog.show();
//                    	submitDialog.on('completeAddSave',function(e){
//                    		tbar.msg(e.result);
//                    		submitDialog.close();
//                    		store.load();
//                    	});
//                    }
                    /**
                     * 审核不通过的编辑
                     */
                    if(target.hasClass('checkEditBtn')){
                    	var editDialog = new circuitWorkOrderApplyCheckEdit({
                    		collectionName:_self.get('collectionName'),
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
                        {
                        	label : '日期',
                        	item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
                        },
                        {
                        	label : '至',
                        	item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'
                        },
//                        {
//                        	label : '状态',
//                        	item : '<select name="flowState" id="flowState" style="width: 200px;"><option value="">请选择</option></select>'
//                        }
                    ]};
            },
            
            _initFlowState: function () {
            	var _self = this;
    			
    			if(_self.get("orgType")==1501){
    				$("#flowState").append("<option value='0'>草稿</option>");
        			$("#flowState").append("<option value='1'>待劳人科审核</option>");
        			$("#flowState").append("<option value='2'>待财务科审核</option>");
        			$("#flowState").append("<option value='3'>退回</option>");
        			$("#flowState").append("<option value='-1'>已通过</option>");
    			}else if(_self.get("orgType")==1502){
    				$("#flowState").append("<option value='0'>草稿</option>");
        			$("#flowState").append("<option value='0'>待审核</option>");
        			$("#flowState").append("<option value='1'>待劳人科审核</option>");
        			$("#flowState").append("<option value='2'>待财务科审核</option>");
        			$("#flowState").append("<option value='3'>退回</option>");
        			$("#flowState").append("<option value='-1'>已通过</option>");
    			}else if(_self.get("orgType")==1503){
    				$("#flowState").append("<option value='0'>草稿</option>");
        			$("#flowState").append("<option value='0'>待车间审核</option>");
        			$("#flowState").append("<option value='1'>待劳人科审核</option>");
        			$("#flowState").append("<option value='2'>待财务科审核</option>");
        			$("#flowState").append("<option value='3'>退回</option>");
        			$("#flowState").append("<option value='-1'>已通过</option>");
    			}
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
            	var _self = this;
                var columns = [
                	{
                        title:'日期',
                        dataIndex:'date',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title : '考勤部门',
                        dataIndex:'attendanceOrgName',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title : '状态',
                        dataIndex:'f&w&a',
                        elCls : 'center',
                        width:'20%',
                        renderer:function (value) {
                        	var flowState = value.split(",")[0];//状态
                        	var workshopQueryData = value.split(",")[1];//车间
                        	var attendanceOrgId = value.split(",")[2];
                        	if(_self.get("orgType")==1503){//工区
                        		if(flowState=="0"&&workshopQueryData==""){
                        			return "草稿";
                        		}
                        		if(flowState=="0"&&workshopQueryData==_self.get("parentId")){
                        			if(_self.get("parentId")=="402891b45b5fd02c015b74c913260035"){
                        				return "待办公室审核";
                        			}else if(_self.get("parentId")=="402891b45b5fd02c015b75395c3a0099"){
                        				return "待职教科审核";
                        			}else{
                        				return "待车间审核";
                        			}
                        		}
                        		if(flowState=="1"){
                        			return "待劳人科审核"
                        		}
                        		if(flowState=="2"){
                        			return "待财务科审核"
                        		}
                        		if(flowState=="3"){
                        			return "回退"
                        		}
                        		if(flowState=="-1"){
                        			return "已通过"
                        		}
                        	}
                        	if(_self.get("orgType")==1502){//车间
                        		if(flowState=="0"&&workshopQueryData==attendanceOrgId){
                        			return "草稿";
                        		}
                        		if(flowState=="0"&&workshopQueryData!=attendanceOrgId){
                        			return "待审核";
                        		}
                        		if(flowState=="1"){
                        			return "待劳人科审核"
                        		}
                        		if(flowState=="2"){
                        			return "待财务科审核"
                        		}
                        		if(flowState=="3"){
                        			return "回退"
                        		}
                        		if(flowState=="-1"){
                        			return "已通过"
                        		}
                        	}
                        	if(_self.get("orgType")==1501){//科室
                        		if(_self.get("orgId")=="402891b45b5fd02c015b74c913260035"){//办公室
                        			if(flowState=="0"&&workshopQueryData==attendanceOrgId){
                            			return "草稿";
                            		}
                            		if(flowState=="0"&&workshopQueryData!=attendanceOrgId){
                            			return "待审核";
                            		}
                            		if(flowState=="1"){
                            			return "待劳人科审核"
                            		}
                            		if(flowState=="2"){
                            			return "待财务科审核"
                            		}
                            		if(flowState=="3"){
                            			return "回退"
                            		}
                            		if(flowState=="-1"){
                            			return "已通过"
                            		}
                        		}else if(_self.get("orgId")=="402891b45b5fd02c015b75395c3a0099"){//职教科
                        			if(flowState=="0"&&workshopQueryData==attendanceOrgId){
                            			return "草稿";
                            		}
                            		if(flowState=="0"&&workshopQueryData!=attendanceOrgId){
                            			return "待审核";
                            		}
                            		if(flowState=="1"){
                            			return "待劳人科审核"
                            		}
                            		if(flowState=="2"){
                            			return "待财务科审核"
                            		}
                            		if(flowState=="3"){
                            			return "回退"
                            		}
                            		if(flowState=="-1"){
                            			return "已通过"
                            		}
                        		}else{
                        			if(flowState=="0"){
                            			return "草稿";
                            		}
                            		if(flowState=="1"){
                            			return "待劳人科审核"
                            		}
                            		if(flowState=="2"){
                            			return "待财务科审核"
                            		}
                            		if(flowState=="3"){
                            			return "回退"
                            		}
                            		if(flowState=="-1"){
                            			return "已通过"
                            		}
                        		}
                        	}
                        	if(_self.get("orgType")==1500){//段领导
                        		if((flowState=="0"&&workshopQueryData=="")||(flowState=="0"&&workshopQueryData==attendanceOrgId)){
                        			return "草稿";
                        		}
                        		if(flowState=="0"&&workshopQueryData!=attendanceOrgId){
                        			return "待车间审核";
                        		}
                        		if(flowState=="1"){
                        			return "待劳人科审核"
                        		}
                        		if(flowState=="2"){
                        			return "待财务科审核"
                        		}
                        		if(flowState=="3"){
                        			return "回退"
                        		}
                        		if(flowState=="-1"){
                        			return "已通过"
                        		}
                        	}
                        }
                    },{
                        title : '创建时间',
                        dataIndex:'createTime',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title : '操作',
                        dataIndex:'f&w&a',
                        elCls : 'center',
                        width:'20%',
                        renderer:function (value) {
                        	var button = '';
                        	var flowState = value.split(",")[0];
                        	var workshopQueryData = value.split(",")[1];
                        	var attendanceOrgId = value.split(",")[2];
                        	if(_self.get("orgType")==1503){
                        		if(flowState=="0"&&workshopQueryData==""){
                        			button += '<span  class="grid-command editBtn">编辑</span>';
                            		button += '<span  class="grid-command submitBtn">提交</span>';
                            		button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                                	button += '<span  class="grid-command importBtn">导入</span>';
                        		}
                        		if(flowState=="0"&&workshopQueryData==_self.get("parentId")){
                        			button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                        		}
                        		if(flowState=="1"){
                        			button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                        		}
                        		if(flowState=="2"){
                        			button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                        		}
                        		if(flowState=="3"){
                        			button += '<span  class="grid-command editBtn">编辑</span>';
                            		button += '<span  class="grid-command submitBtn">提交</span>';
                            		button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                                	button += '<span  class="grid-command importBtn">导入</span>';
                        		}
                        		if(flowState=="-1"){
                        			button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                        		}
                        	}
                        	if(_self.get("orgType")==1502){//车间
                        		if(flowState=="0"&&workshopQueryData==attendanceOrgId){
                        			button += '<span  class="grid-command editBtn">编辑</span>';
                            		button += '<span  class="grid-command submitBtn">提交</span>';
                            		button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                                	button += '<span  class="grid-command importBtn">导入</span>';
                        		}
                        		if(flowState=="0"&&workshopQueryData!=attendanceOrgId){
                        			button += '<span  class="grid-command editBtn">编辑</span>';
                            		button += '<span  class="grid-command submitBtn">提交</span>';
                            		button += '<span  class="grid-command backBtn">回退</span>';
                            		button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                                	button += '<span  class="grid-command importBtn">导入</span>';
                        		}
                        		if(flowState=="1"){
                        			button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                        		}
                        		if(flowState=="2"){
                        			button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                        		}
                        		if(flowState=="3"){
                        			button += '<span  class="grid-command editBtn">编辑</span>';
                            		button += '<span  class="grid-command submitBtn">提交</span>';
                            		button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                                	button += '<span  class="grid-command importBtn">导入</span>';
                        		}
                        		if(flowState=="-1"){
                        			button += '<span  class="grid-command searchBtn">查看</span>';
                                	button += '<span  class="grid-command downBtn">下载</span>';
                        		}
                        	}
                        	if(_self.get("orgType")==1501){//科室
                        		if(_self.get("orgId")=="402891b45b5fd02c015b74c913260035"){//办公室
                        			if(flowState=="0"&&workshopQueryData==attendanceOrgId){
                            			button += '<span  class="grid-command editBtn">编辑</span>';
                                		button += '<span  class="grid-command submitBtn">提交</span>';
                                		button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                                    	button += '<span  class="grid-command importBtn">导入</span>';
                            		}
                            		if(flowState=="0"&&workshopQueryData!=attendanceOrgId){
                            			button += '<span  class="grid-command editBtn">编辑</span>';
                                		button += '<span  class="grid-command submitBtn">提交</span>';
                                		button += '<span  class="grid-command backBtn">回退</span>';
                                		button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                                    	button += '<span  class="grid-command importBtn">导入</span>';
                            		}
                            		if(flowState=="1"){
                            			button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                            		}
                            		if(flowState=="2"){
                            			button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                            		}
                            		if(flowState=="3"){
                            			button += '<span  class="grid-command editBtn">编辑</span>';
                                		button += '<span  class="grid-command submitBtn">提交</span>';
                                		button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                                    	button += '<span  class="grid-command importBtn">导入</span>';
                            		}
                            		if(flowState=="-1"){
                            			button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                            		}
                        		}else if(_self.get("orgId")=="402891b45b5fd02c015b75395c3a0099"){//职教科
                        			if(flowState=="0"&&workshopQueryData==attendanceOrgId){
                            			button += '<span  class="grid-command editBtn">编辑</span>';
                                		button += '<span  class="grid-command submitBtn">提交</span>';
                                		button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                                    	button += '<span  class="grid-command importBtn">导入</span>';
                            		}
                            		if(flowState=="0"&&workshopQueryData!=attendanceOrgId){
                            			button += '<span  class="grid-command editBtn">编辑</span>';
                                		button += '<span  class="grid-command submitBtn">提交</span>';
                                		button += '<span  class="grid-command backBtn">回退</span>';
                                		button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                                    	button += '<span  class="grid-command importBtn">导入</span>';
                            		}
                            		if(flowState=="1"){
                            			button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                            		}
                            		if(flowState=="2"){
                            			button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                            		}
                            		if(flowState=="3"){
                            			button += '<span  class="grid-command editBtn">编辑</span>';
                                		button += '<span  class="grid-command submitBtn">提交</span>';
                                		button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                                    	button += '<span  class="grid-command importBtn">导入</span>';
                            		}
                            		if(flowState=="-1"){
                            			button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                            		}
                        		}else{
                        			if(flowState=="0"){
                            			button += '<span  class="grid-command editBtn">编辑</span>';
                                		button += '<span  class="grid-command submitBtn">提交</span>';
                                		button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                                    	button += '<span  class="grid-command importBtn">导入</span>';
                            		}
                            		if(flowState=="1"){
                            			button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                            		}
                            		if(flowState=="2"){
                            			button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                            		}
                            		if(flowState=="3"){
                            			button += '<span  class="grid-command editBtn">编辑</span>';
                                		button += '<span  class="grid-command submitBtn">提交</span>';
                                		button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                                    	button += '<span  class="grid-command importBtn">导入</span>';
                            		}
                            		if(flowState=="-1"){
                            			button += '<span  class="grid-command searchBtn">查看</span>';
                                    	button += '<span  class="grid-command downBtn">下载</span>';
                            		}
                        		}
                        	}
                        	if(_self.get("orgType")==1500){//段领导
                    			button += '<span  class="grid-command searchBtn">查看</span>';
                    			button += '<span  class="grid-command downBtn">下载</span>';
                        	}
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
                    url : "/kmms/attendanceManageCollectAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 20,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {attendanceManageCollect:_self.get('attendanceManageCollect'),orgId:_self.get('orgId'),orgType:_self.get('orgType')}
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
            	var _self = this;
            	var searchGrid = null;
            	if(_self.get("orgType")==1500){//段领导只有查看
            		searchGrid = {
                            tbarItems : [
                            ],
                            plugins : [
                                Grid.Plugins.CheckSelection,
                                Grid.Plugins.RowNumber
                            ],
//                            permissionStore : _self._initPermissionStore()
                        };
            	}else{
            		searchGrid = {
                            tbarItems : [
                                {
                                    id : 'add',
                                    btnCls : 'button button-small addBtn',
                                    text : '<i class="icon-plus"></i>生成考勤汇总表',
                                },
                                {
                                    xclass : 'bar-item-separator' // 竖线分隔符
                                },
                                {
                                    id : 'del',
                                    btnCls : 'button button-small delBtn',
                                    text : '<i class="icon-remove"></i>批量删除',
                                }
                            ],
                            plugins : [
                                Grid.Plugins.CheckSelection,
                                Grid.Plugins.RowNumber
                            ],
//                            permissionStore : _self._initPermissionStore()
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
                parentId : {},//登录用户上级组织机构ID
                orgType : {},//登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                attendanceManage : {value:'attendanceManage'},
                attendanceManageCollect : {value:'attendanceManageCollect'}//存储表名
            }
        });
    	return AttendanceManageCollectPage;
});