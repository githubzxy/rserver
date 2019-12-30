/**
 *人员管理管理主页（劳人科以外的部门使用）
 * @author yangsy
 * @date 18-11-22
 */
define('kmms/userInfoManager/userInfoManageOtherPage',['bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'bui/calendar',
    'bui/mask',
    'common/org/OrganizationPicker',
    'kmms/userInfoManager/userInfoManageOtherEdit',
    'kmms/userInfoManager/userInfoManageOtherInfo',
    'common/data/PostLoad'
    ], function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        Mask = r('bui/mask'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker');
	    userInfoManageEdit = r('kmms/userInfoManager/userInfoManageOtherEdit'),
	    userInfoManageInfo = r('kmms/userInfoManager/userInfoManageOtherInfo'),
        SearchGridContainer = r('common/grid/SearchGridContainer');

    var userInfoManagePage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
            	$("#loginOrgId").val(_self.get("orgId"));
            	$("#loginOrgType").val(_self.get("orgType"));
            	if(_self.get("orgType")==1503){//工区
//            		_self._getWorkAreas(_self.get("orgName"));
            	}
            	if(_self.get("orgType")==1502){//车间
            		$("#workshop").append("<option  value=''>请选择</option>");
            		$("#workshop").append("<option  value="+_self.get("orgName")+">"+_self.get("orgName")+"</option>");
            		 _self._getWorkAreas(_self.get("orgName"));
            	}
            	if(_self.get("orgType")==1501||_self.get("orgType")==1500){
            		_self._getWorkshops();//获取车间下拉选数据
            	}
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                //工区下拉选选项根据车间而变化
                $("#workshop").on('change',function() {
                    $("#teamGroup").empty();
                    var workshop = $("#workshop").val();
                    _self._getWorkAreas(workshop);

                });
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#phoneNum").val("");
    				$("#workshop").val("");
    				$("#staffName").val("");
    				$("#teamGroup").val("");
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
                	   var infoDialog = new userInfoManageInfo({
                		   collectionName:_self.get('collectionName'),
                		   shiftId:docId
                	   });
                	   infoDialog.show();
                   }
              
               /**
                * 编辑
                */
               if(target.hasClass('editBtn')){
                   var editDialog = new userInfoManageEdit({
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
            	var _self = this;
            	if(_self.get("orgType")==1503){//工区
            		return {
                        items : [
                            {label : '联系电话',item : '<input type="text" name="phoneNum" id="phoneNum" style="width: 175px;" />'},
                            {label : '姓名',item : '<input type="text" name="staffName" id="staffName" style="width: 175px;" >'+
                            					'<input type="hidden"  id="loginOrgId"  readonly/>'+//使操作栏中可获取到数据
                            					'<input type="hidden"  id="loginOrgType"  readonly/>'//使操作栏中可获取到数据
                            }
                        ]};
            	}
            	if(_self.get("orgType")==1502){//车间
            		return {
                        items : [
                            {label : '车间',item : '<select type="text" id="workshop" name="workshop" readonly style="width: 200px;"></select>'},
                            {label : '班组',item : '<select type="text" name="teamGroup" id="teamGroup" style="width: 200px;"></select>'},
                            {label : '联系电话',item : '<input type="text" name="phoneNum" id="phoneNum" style="width: 175px;" />'},
                            {label : '姓名',item : '<input type="text" name="staffName" id="staffName" style="width: 175px;" >'+
                            					'<input type="hidden"  id="loginOrgId"  readonly/>'+//使操作栏中可获取到数据
                            					'<input type="hidden"  id="loginOrgType"  readonly/>'//使操作栏中可获取到数据
                            }
                        ]};
            	}
                return {
                    items : [
                        {label : '车间/科室',item : '<select type="text" id="workshop" name="workshop" readonly style="width: 200px;"></select>'},
                        {label : '班组',item : '<select type="text" name="teamGroup" id="teamGroup" style="width: 200px;"></select>'},
                        {label : '联系电话',item : '<input type="text" name="phoneNum" id="phoneNum" style="width: 175px;" />'},
                        {label : '姓名',item : '<input type="text" name="staffName" id="staffName" style="width: 175px;" >'+
                        					'<input type="hidden"  id="loginOrgId"  readonly/>'+//使操作栏中可获取到数据
                        					'<input type="hidden"  id="loginOrgType"  readonly/>'//使操作栏中可获取到数据
                        }
                    ]};
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
    		             $("#workshop").append("<option  value=''>请选择</option>");
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
    			          $("#teamGroup").append("<option  value=''>请选择</option>");
    	               	 for(var i=0;i<res.length;i++){
    	               	  $("#teamGroup").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
                    }
                });
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
            	var _self = this;
                var columns = [
                    {
                        title:'劳资号',
                        dataIndex:'number',
                        elCls : 'center',
                        width:'15%'
                    },
                    {
                        title:'姓名',                       
                        dataIndex:'staffName',
                        elCls : 'center',
                        width:'10%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'性别',
                        dataIndex:'gender',
                        elCls : 'center',
                        width:'5%',
                    },{
                        title:'职名',
                        dataIndex:'position',
                        elCls : 'center',
                        width:'10%',
                    },{
                        title:'车间/科室',
                        dataIndex:'workshop',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'班组',
                        dataIndex:'teamGroup',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'联系电话',
                        dataIndex:'phoneNum',
                        elCls : 'center',
                        width:'15%',
                    },{
                    	title:'操作',
                        dataIndex:'orgIdAndorgType',
                        elCls : 'center',
                        width:'15%',
                        renderer:function (value) {
                        	var valueArray = value.split(","),
                        	loginOrgId = $('#loginOrgId').val();
                        	loginOrgType=$('#loginOrgType').val();
//                        	console.log(loginOrgId);
//                        	console.log(loginOrgType);
                        	if(loginOrgType==1500||loginOrgType==1501){//段用户登录的情况
                        		if(loginOrgId=="402891b45b5fd02c015b74c913260035"){//办公室登录情况：办公室可修改其他段人员、汽车班、后勤班人员电话，其他段人员只能看所有人信息和修改自己部门的电话
                        			if(valueArray[1]=="1500"||valueArray[1]=="1501"||valueArray[0]=="2c9781856a1bb4ac016a2945cfed0037"||valueArray[0]=="2c9781856a1bb4ac016a29460c420038"){
                        				return '<span  class="grid-command editBtn">编辑</span>'+
                        				'<span  class="grid-command infoBtn">详情</span>';
                        			}else{
                        				return '<span  class="grid-command infoBtn">详情</span>';
                        			}
                        		}else{
                        			if(loginOrgId==valueArray[0]){
                        				return '<span  class="grid-command editBtn">编辑</span>'+
                        				'<span  class="grid-command infoBtn">详情</span>';
                        			}else{
                        				return '<span  class="grid-command infoBtn">详情</span>';
                        			}
                        		}
                        	} 
                        	else{//车间和工区用户登录的情况
                        			return '<span  class="grid-command editBtn">编辑</span>'+
                        			'<span  class="grid-command infoBtn">详情</span>';
                        	}
//                        	else{//车间和工区用户登录的情况
//                        		var list=new Array();
//                        		$.ajax({
//                    				url:'/kmms/userInfoManageAction/getchildOrgId',
//                              		data:{orgId :loginOrgId },
//                              		type:'post',
////                              		async:false,
//                              		dataType:"json",
//                              		success:function(e){ 
//                              			list=e;
//                              		}
//                    			});
//                        		Array.prototype.in_array=function(e){
//                        			for(var i = 0;i<this.length;i++){
//                        				if(this[i]==e){
//                        					return true;
//                        				}
//                        			}
//                        			return false;
//                        		}
//                        	    if(list.in_array(valueArray[0])==true||loginOrgId==valueArray[0]){
//                        	    	return '<span  class="grid-command editBtn">编辑</span>'+
//                        	    	'<span  class="grid-command infoBtn">详情</span>';
//                        	    }else{
//                        	    	return '<span  class="grid-command infoBtn">详情</span>';
//                        	    }
//                        	}
//                        	return '<span  class="grid-command infoBtn">详情</span>';
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
//                console.log(_self.get('orgId'));
                var store = new Data.Store({
                    url : "/kmms/userInfoManageAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 10,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName'),userId:_self.get('userId'),orgId:_self.get('orgId'),orgType:_self.get('orgType')+""}
                });
//                console.log(_self.get('orgId'));
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
                        
                    ],
                    plugins : [
                        Grid.Plugins.CheckSelection,
                        Grid.Plugins.RowNumber
                    ],
//                 permissionStore : _self._initPermissionStore()
                };
                return searchGrid;
            }
        },
        {
            ATTRS : {
                perId : {},
                userId : {},//登录用户ID
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                orgType : {},//登录用户组织类型
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                collectionName : {value:'userInfoManage'}
            }
        });
    return userInfoManagePage;
});