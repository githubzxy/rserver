/**
 * 考勤管理打卡主页
 * @author yangsy
 * @date 19-03-26
 */
seajs.use("/kmms/integratedManage/attendanceManage/attendanceManage.css");
define('kmms/integratedManage/attendanceManage/attendanceManagePage',
		[
		 'bui/common',
		 'bui/data',
		 'bui/grid',
		 'bui/calendar',
		 'common/form/FormContainer',
		 'common/org/OrganizationPicker',
		 'common/grid/SearchGridContainer',
		 'kmms/integratedManage/attendanceManage/attendanceManageDetail',
		 'kmms/integratedManagement/Attendance/attendanceAdd',
		 'kmms/integratedManagement/Attendance/attendanceEdit',
		 'kmms/integratedManagement/Attendance/attendanceInfo',
		 'common/data/PostLoad'
		],function (r) {
	var BUI = r('bui/common'),
		Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        FormContainer = r('common/form/FormContainer'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker');
		attendanceManageDetail = r('kmms/integratedManage/attendanceManage/attendanceManageDetail'),
	    attendanceAdd = r('kmms/integratedManagement/Attendance/attendanceAdd'),
	    attendanceEdit = r('kmms/integratedManagement/Attendance/attendanceEdit'),
	    attendanceInfo = r('kmms/integratedManagement/Attendance/attendanceInfo'),
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var attendanceManagePage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initCalendar());
//                _self.addChild(_self._initUserFormArea());
            },
            renderUI:function(){
                var _self=this;
//                _self._initDate();
//                _self._initOrganizationPicker();
            },
            bindUI:function(){
            	var _self = this;
            	
            	_self._initCalendarStatus();
            	
            	var c = _self.getChild("calendarPageId",true);
            	
            	// 切换月份时，根据后台数据渲染当月还未填报的天为红色
    			c.on('monthchange',function(e){
    				_self._initCalendarStatus();
    			})
    			// 点选天的触发事件
//    			c.on('datechange',function(e){
//    				console.log(BUI.Date.format(e.date,"yyyy-mm-dd"))
//    			})
//    			c.on('datechange',function(e){
//    			c.get('el').find('.x-datepicker-date').click(function(e){
//    				console.log(this.title);
//    				console.log(BUI.Date.format(e.date,"yyyy年mm月dd日"))
//    				console.log(BUI.Date.format(this.title,"yyyy年mm月dd日"))
//    				var Dialog = new attendanceManageDetail({
//                        userInfoManage : _self.get('userInfoManage'),
//                        attendanceManage : _self.get('attendanceManage'),
//                        userId:_self.get('userId'),
//                        userName:_self.get('userName'),
//                        orgId:_self.get('orgId'),
//                        orgName:_self.get('orgName'),
//                        date:BUI.Date.format(e.date,"yyyy年mm月dd日"),
//	                });
//    				Dialog.show();
////	                addDialog.on('completeAddSave',function(e){
////	                    tbar.msg(e.result);
////	                    addDialog.close();
////	                    store.load();
////	                });
//    			})
            	
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
	                           url : '/kmms/commonAction/removeDoc.cn',
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
                    var addDialog = new attendanceAdd({
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
                    });
                });
            },
            
            _initCalendar:function(){
            	var _self = this;
            	var c = new Calendar.Calendar({
            		id:'calendarPageId',
    				width: '100%',
    				height: '100%',
    			});
            	c.get('children').pop();
            	return c;
            },
            
            /**
			* 根据传入的数据初始化日期面板上哪些日期标红
			* 标红的日期代表没有填报
			*/
			_initCalendarStatus:function(){
				var _self = this;
				var c = _self.getChild("calendarPageId",true);
				var data = [];
//				var data = [
//							{
//								day: '2019-03-10',
//							},{
//								day: '2019-03-13',
//							},{
//								day: '2019-03-17',
//							},{
//								day: '2019-03-21',
//							},{
//								day: '2019-03-22',
//							},{
//								day: '2019-03-30',
//							}
//							];
				//x-datepicker-active样式代表当月的所有天
				$('.x-datepicker-active').each(function(idx,e){
					var dayTd = e,title = dayTd.title;
					//填报过的则不标红
					if(!_self._hadCommit(title,data)){
						$(dayTd.children[0]).css('color','red');
					}

				})
				
				c.get('el').find('.x-datepicker-date').click(function(e){
//    				console.log(this.title);
//    				console.log(BUI.Date.format(e.date,"yyyy年mm月dd日"))
//    				console.log(BUI.Date.format(this.title,"yyyy年mm月dd日"))
//    				console.log(BUI.Date.format(c.get('selectedDate'),"yyyy年mm月dd日"))
    				var Dialog = new attendanceManageDetail({
                        userInfoManage : _self.get('userInfoManage'),
                        attendanceManage : _self.get('attendanceManage'),
                        attendanceUserChange : _self.get('attendanceUserChange'),
                        userId:_self.get('userId'),
                        userName:_self.get('userName'),
                        orgId:_self.get('orgId'),
                        orgName:_self.get('orgName'),
                        date:BUI.Date.format(c.get('selectedDate'),"yyyy年mm月dd日"),
	                });
    				Dialog.show();
//	                addDialog.on('completeAddSave',function(e){
//	                    tbar.msg(e.result);
//	                    addDialog.close();
//	                    store.load();
//	                });
    			})
			},
			/**
			* 判断某天是否完成检修记录填报
			* @param day 某天
			* @param data 当月完成填报的数据数组，其中每个元素中包含day属性，标识填报日期，格式yyyy-mm-dd
			*/
			_hadCommit:function(day,data){
				for(var i = 0;i < data.length;i++){
					var targetDay = data[i].day;
					if(targetDay == day)
						return true;
				}
				return false;
			},
            
			_initUserFormArea:function(){
				var _self = this;
				var form = null;
	            $.ajax({
					url:'/kmms/attendanceManageAction/getUsersByOrgId',
	          		data:{orgId : _self.get('orgId'),collectionName : _self.get('userInfoManage')},
	          		type:'post',
	          		dataType:"json",
	          		async:false,
	          		success:function(result){
						var data = result.data;
						console.log(result.data);
						form = _self._initUserFormContainer(data);
	          		}
				});
	            return form;
			},
			
			_initUserFormContainer:function(data){
				var _self = this;
				var colNum = 2;
				var childs = [{
                    label : '所属车间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="orgSelectName" style="width:98%" id="orgSelectName" readonly /><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
                }];
//				for(var i=0;i<data.length;i++){
//					childs.push({
//	                    label : '所属车间：',
//	                    redStarFlag : true,
//	                    itemColspan : 1,
//	                    item : '<input type="text" name="orgSelectName" style="width:98%" id="orgSelectName" readonly /><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
//	                });
//				}
				var form = new FormContainer({
	                id : 'attendanceManageForm',
	                colNum : colNum,
	                formChildrens : childs,
//	                elStyle:{overflowY:'scroll',height:'400px'}
	            });
	            _self.set('formContainer',form);
	            return form;
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
                        {label : '资料名称',item : '<input type="text" name="name" id="selectName" style="width: 175px;"/>'},
                        {label : '所属部门',item : '<input type="text" id="orgSelectName" readonly style="width: 175px;"><input type="hidden" name="orgId" id="orgSelectId"  readonly/>'},
                        {label : '开始时间',item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'},
                        {label : '结束时间',item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" style="width: 175px;" class="calendar" readonly/>'}
                    ]};
            },
            /**
             * 初始化组织机构选择
             * @private
             */
            _initOrganizationPicker:function(){
                var _self=this;
                var orgPicker = new OrganizationPicker({
                    trigger : '#orgSelectName',
                    rootOrgId:_self.get('rootOrgId'),//必填项
                    rootOrgText:_self.get('rootOrgText'),//必填项
                    url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                    autoHide: true,
                    align : {
                        points:['bl','tl']
                    },
                    zIndex : '10000',
                    width:200,
                    height:210
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
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                    {
                        title:'资料名称',
                        dataIndex:'name',
                        elCls : 'center',
                        width:'25%'
                    },{
                        title:'所属部门',
                        dataIndex:'orgName',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'创建时间',
                        dataIndex:'createDate',
                        elCls : 'center',
                        width:'25%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'创建人',
                        dataIndex:'createUserName',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'操作',
                        dataIndex:'id',
                        elCls : 'center',
                        width:'10%',
                        renderer:function () {
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
                    url : "/kmms/commonAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 10,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName')}
                });
                _self.set('store',store);
                return store;
            },
            /**
             * 初始化列表展示对象
             * @private
             */
            _initSearchGrid:function () {
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
                perId : {},
                userId : {},//登录用户ID
                userName : {},//登录用户名称
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                parentId : {},//上级机构ID
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                userInfoManage : {value:'userInfoManage'},
                attendanceManage : {value:'attendanceManage'},
                attendanceUserChange : {value:'attendanceUserChange'}
            }
        });
    return attendanceManagePage;
});