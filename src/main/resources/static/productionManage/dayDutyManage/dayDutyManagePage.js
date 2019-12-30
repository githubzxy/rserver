/**
 * 值班管理主页
 * @author yangsy
 * @date 19-03-26
 */
seajs.use("/kmms/integratedManage/attendanceManage/attendanceManage.css");
define('kmms/productionManage/dayDutyManage/dayDutyManagePage',
		[
		 'bui/common',
		 'bui/data',
		 'bui/grid',
		 'bui/calendar',
		 'common/form/FormContainer',
		 'common/org/OrganizationPicker',
		 'common/grid/SearchGridContainer',
		 'kmms/productionManage/dayDutyManage/dayDutyManageAdd',
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
		dayDutyManageAdd = r('kmms/productionManage/dayDutyManage/dayDutyManageAdd'),
	    attendanceAdd = r('kmms/integratedManagement/Attendance/attendanceAdd'),
	    attendanceEdit = r('kmms/integratedManagement/Attendance/attendanceEdit'),
	    attendanceInfo = r('kmms/integratedManagement/Attendance/attendanceInfo'),
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var dayDutyManagePage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initCalendar());
            },
            renderUI:function(){
                var _self=this;
            },
            bindUI:function(){
            	var _self = this;
            	
            	_self._initCalendarStatus();
            	
            	var c = _self.getChild("calendarPageId",true);
            	
            	// 切换月份时，根据后台数据渲染当月还未填报的天为红色
    			c.on('monthchange',function(e){
    				_self._initCalendarStatus();
    			})
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
				$('.x-datepicker-active').each(function(idx,e){
					var dayTd = e,title = dayTd.title;
					//填报过的则不标红
					if(!_self._hadCommit(title,data)){
						$(dayTd.children[0]).css('color','red');
					}

				})
				
				c.get('el').find('.x-datepicker-date').click(function(e){
				$.ajax({
		                url:'/kmms/dayDutyManageAction/findByDate',
		                data:{date :BUI.Date.format(c.get('selectedDate'),"yyyy年mm月dd日"),collectionName:_self.get('collectionName')},
		                type:'post',
		                async:false,
		                dataType:"json",
		                success:function(res){
		                	if(res.data!=null){
		                    var id = res.data.docId;
		                    _self.set('docId',id);
		                	}else{
		                	 _self.set('docId',"");
		                	}
		                },
		                error:function(){
		                	_self.set('docId',"");
		                }
		            })
		            var id = _self.get('docId');
    				var Dialog = new dayDutyManageAdd({
    					collectionName : _self.get('collectionName'),
                        userId:_self.get('userId'),
                        userName:_self.get('userName'),
                        orgId:_self.get('orgId'),
                        orgName:_self.get('orgName'),
                        docId:id,
                        date:BUI.Date.format(c.get('selectedDate'),"yyyy年mm月dd日"),
	                });
    				Dialog.show();
    			    Dialog.on('completeAddSave',function(e){
                           Dialog.close();
//                           window.location.reload();
                     });
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
				var form = new FormContainer({
	                id : 'attendanceManageForm',
	                colNum : colNum,
	                formChildrens : childs,
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
                /**
                 * 存储表名
                 */
                collectionName : {value:'dayDutyManage'}
            }
        });
    return dayDutyManagePage;
});