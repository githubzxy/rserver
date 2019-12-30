/**
 * 添加人员到本部门进行考勤
 * @author yangsy
 * @date 19-5-14
 */
define('kmms/integratedManage/attendanceManage/attendanceUserChange',
		[
			'bui/common',
			'bui/form',
			'bui/select',
			'bui/data',
			'bui/mask',
			'common/form/FormContainer',
			'common/org/OrganizationPicker',
			'common/data/PostLoad',
			'common/uploader/UpdateUploader',
			'kmms/integratedManage/attendanceManage/SelectSuggest',
		],function(r){
    var BUI = r('bui/common'),Select = r('bui/select'),Data = r('bui/data'),
    	FormContainer = r('common/form/FormContainer'),
    	Mask = r('bui/mask'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	PostLoad = r('common/data/PostLoad'),
    	UpdateUploader = r('common/uploader/UpdateUploader'),
    	SelectSuggest = r('kmms/integratedManage/attendanceManage/SelectSuggest');
    var AttendanceUserChange = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initOrganizationPicker();
        	$("#attendanceUserChange .bui-ext-close").css("display","none");
        	_self._initSelect();
//        	_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            
            var orgPicker=_self.get('orgPicker');
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
    		    var data = orgPicker.getSelection();
    		    var text = "";
    		    var id = "";
    		    var type = "";
    		    for(var i=0;i<data.length;i++){
    		    	text = text + ',' + data[i].text
    		    	id = id + ',' + data[i].id
    		    	type = type + ',' + data[i].type
    		    }
    		    $('#formContainer #department').val(text.substring(1));
    		    $('#formContainer #departmentId').val(id.substring(1));
    		    
    		    if(id.substring(1)==_self.get("orgId")){
    		    	 $('#formContainer #department').val("不能选择本部门，请重新选择！");
    	    		 $('#formContainer #departmentId').val("");
    	    		 $("#formContainer #user").empty();
    		    }else{
    		    	$("#formContainer #user").empty();
    		    	_self._getUser(text.substring(1),id.substring(1));
    		    }
    		    
            });
            
            //定义按键
            var buttons = [
                {
                    text:'添加',
                    elCls : 'button',
                    handler : function(){
                        var save = _self.get('save');
                        if(save){
                        	save.call(_self);
                        }
                    }
                },{
                    text:'关闭',
                    elCls : 'button',
                    handler : function(){
                    	var close = _self.get('close');
                        if(close){
                        	close.call(_self);
                        }
//                        if(this.onCancel() !== false){
//                            this.close();
//                        }
                    }
                }
            ];
            _self.set('buttons',buttons);
        },
        
        _initOrganizationPicker:function(){
        	var _self=this;
        	var orgPicker = new OrganizationPicker({
        		trigger : '#department',
        		rootOrgId:_self.get('rootOrgId'),//必填项
        		rootOrgText:_self.get('rootOrgText'),//必填项
        		url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
        		autoHide : true,
        		showRoot: false,//不显示根节点
//        		checkType:"all",//有勾选框
//        		multipleSelect: true,//多选
        		align : {
        			points:['bl','tl']
        		},
        		zIndex : '10000',
        		width:225,
        		height:225
        	});
        	orgPicker.render();
        	_self.set('orgPicker',orgPicker);
        },
        
        _getUser : function(department,orgId){
        	var _self=this;
        	$.ajax({
        		url:'/kmms/attendanceManageAction/getUserByDepartmentName',
        		type:'post',
        		dataType:"json",
        		data:{department : department , orgId : orgId+"", userInfoManage:_self.get('userInfoManage')},
        		success:function(res){
        			console.log(res);
        			$("#formContainer #user").append("<option  value=''>请选择</option>");
        			for(var i=0;i<res.length;i++){
        				if(res[i].noShow=="1"){
        					continue;
        				}
        				$("#formContainer #user").append("<option  value="+res[i].docId+','+res[i].staffName+">"+res[i].staffName+"</option>");
        			}
        		}
        	});
        },
        
        _getShowData : function(){
            var _self = this,docId = _self.get('docId'),date = _self.get('date');
            $.ajax({
                url:'/kmms/attendanceManageAction/findById',
                data:{docId : docId,date : date,collectionName : _self.get('attendanceManage')},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                        $("#formContainer #morning").val(data.morning);
                        $("#formContainer #noon").val(data.noon);
                        $("#formContainer #night").val(data.night);
//                        $("#formContainer #shifts").val(data.shifts);
                        $("#formContainer #daily").val(data.daily);
                        $("#formContainer #turn").val(data.turn);
                    }
                }
            })
        },
        
        _initSelect: function(){
			
			$("#formContainer #morning").append("<option  value=''>请选择</option>");
			$("#formContainer #morning").append("<option  value='／'>／</option>");//白班
			$("#formContainer #morning").append("<option  value='×'>×</option>");//小夜班
			$("#formContainer #morning").append("<option  value='*'>*</option>");//大夜班
			$("#formContainer #morning").append("<option  value='○'>○</option>");//休班
			$("#formContainer #morning").append("<option  value='补'>补</option>");//补休
			$("#formContainer #morning").append("<option  value='学'>学</option>");//学习
			$("#formContainer #morning").append("<option  value='差'>差</option>");//公差
			$("#formContainer #morning").append("<option  value='探'>探</option>");//探亲
			$("#formContainer #morning").append("<option  value='年'>年</option>");//年休
			$("#formContainer #morning").append("<option  value='婚'>婚</option>");//婚假
			$("#formContainer #morning").append("<option  value='丧'>丧</option>");//丧假
			$("#formContainer #morning").append("<option  value='病'>病</option>");//病假
			$("#formContainer #morning").append("<option  value='住'>住</option>");//住院
			$("#formContainer #morning").append("<option  value='事'>事</option>");//事假
			$("#formContainer #morning").append("<option  value='产'>产</option>");//产假
			$("#formContainer #morning").append("<option  value='护'>护</option>");//护理
			$("#formContainer #morning").append("<option  value='息'>息</option>");//息工假
			$("#formContainer #morning").append("<option  value='计'>计</option>");//计生假
			$("#formContainer #morning").append("<option  value='旷'>旷</option>");//旷工
//			$("#formContainer #morning").append("<option  value='助'>助</option>");//旷工
			
			$("#formContainer #noon").append("<option  value=''>请选择</option>");
			$("#formContainer #noon").append("<option  value='／'>／</option>");//白班
			$("#formContainer #noon").append("<option  value='×'>×</option>");//小夜班
			$("#formContainer #noon").append("<option  value='*'>*</option>");//大夜班
			$("#formContainer #noon").append("<option  value='○'>○</option>");//休班
			$("#formContainer #noon").append("<option  value='补'>补</option>");//补休
			$("#formContainer #noon").append("<option  value='学'>学</option>");//学习
			$("#formContainer #noon").append("<option  value='差'>差</option>");//公差
			$("#formContainer #noon").append("<option  value='探'>探</option>");//探亲
			$("#formContainer #noon").append("<option  value='年'>年</option>");//年休
			$("#formContainer #noon").append("<option  value='婚'>婚</option>");//婚假
			$("#formContainer #noon").append("<option  value='丧'>丧</option>");//丧假
			$("#formContainer #noon").append("<option  value='病'>病</option>");//病假
			$("#formContainer #noon").append("<option  value='住'>住</option>");//住院
			$("#formContainer #noon").append("<option  value='事'>事</option>");//事假
			$("#formContainer #noon").append("<option  value='产'>产</option>");//产假
			$("#formContainer #noon").append("<option  value='护'>护</option>");//护理
			$("#formContainer #noon").append("<option  value='息'>息</option>");//息工假
			$("#formContainer #noon").append("<option  value='计'>计</option>");//计生假
			$("#formContainer #noon").append("<option  value='旷'>旷</option>");//旷工
//			$("#formContainer #noon").append("<option  value='助'>助</option>");//旷工
			
			$("#formContainer #night").append("<option  value=''>请选择</option>");
			$("#formContainer #night").append("<option  value='／'>／</option>");//白班
			$("#formContainer #night").append("<option  value='×'>×</option>");//小夜班
			$("#formContainer #night").append("<option  value='*'>*</option>");//大夜班
			$("#formContainer #night").append("<option  value='○'>○</option>");//休班
			$("#formContainer #night").append("<option  value='补'>补</option>");//补休
			$("#formContainer #night").append("<option  value='学'>学</option>");//学习
			$("#formContainer #night").append("<option  value='差'>差</option>");//公差
			$("#formContainer #night").append("<option  value='探'>探</option>");//探亲
			$("#formContainer #night").append("<option  value='年'>年</option>");//年休
			$("#formContainer #night").append("<option  value='婚'>婚</option>");//婚假
			$("#formContainer #night").append("<option  value='丧'>丧</option>");//丧假
			$("#formContainer #night").append("<option  value='病'>病</option>");//病假
			$("#formContainer #night").append("<option  value='住'>住</option>");//住院
			$("#formContainer #night").append("<option  value='事'>事</option>");//事假
			$("#formContainer #night").append("<option  value='产'>产</option>");//产假
			$("#formContainer #night").append("<option  value='护'>护</option>");//护理
			$("#formContainer #night").append("<option  value='息'>息</option>");//息工假
			$("#formContainer #night").append("<option  value='计'>计</option>");//计生假
			$("#formContainer #night").append("<option  value='旷'>旷</option>");//旷工
//			$("#formContainer #night").append("<option  value='助'>助</option>");//旷工
        },

        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label: '部门：',
                    itemColspan: 1,
                    redStarFlag : true,
                    item: '<input type="text" name="department" id="department" readonly style="width:99.5%"/>'+'<input type="hidden" name="departmentId" id="departmentId" readonly/>'
                },
                {
                	label: '人员：',
                	itemColspan: 1,
                	redStarFlag : true,
                	item: '<select name="user" id="user" style="width:100%" data-rules="{required:true}"/></select>'
                },
                {
                	label: '备注：',
                	itemColspan: 2,
                	item: '<textarea style="width:99.5%;height:10px"/></textarea>'
                },
            ];
            var form = new FormContainer({
                id : 'attendanceUserChangeForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'attendanceUserChangeDialog'},
            elAttrs : {value : {id:"attendanceUserChange"}},
            title:{value:'添加人员'},
            width:{value:600},
            height:{value:200},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            date:{},
            docId:{},
            staffName:{},
            userInfoManage:{},
            attendanceManage:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户所属机构ID
            orgName:{},//登录用户所属机构名称
            parentId:{},//登录用户所属机构上级机构ID
            save:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('attendanceUserChangeForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.collectionName="attendanceUserChange";
//	        		data.userId=_self.get('userId');
//	        		data.userName=_self.get('userName');
	        		data.toOrgId=_self.get('orgId');
	        		data.toOrgName=_self.get('orgName');
//	        		data.staffName=_self.get('staffName');
//	        		data.docId=_self.get('docId');
	        		data.date=_self.get('date');
	        		console.log(data);
                    var pl = new PostLoad({
        				url : '/zuul/kmms/attendanceManageAction/addUserChangeDoc',
        				el : _self.get('el'),
        				loadMsg : '上传中...'
        			}); 
        			pl.load(data, function(e){
        				if (e) {
                            _self.fire("completeAddSave",{
                                result : e
                            });
                        }
        			});
                }
            },
            close:{
            	 value : function(){
            		 var _self = this;
            		 _self.fire("closeDialog",{
                         result : "close"
                     });
            	 }
            },
            events : {value : {'completeAddSave' : true}},
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'}
        }
    });
    return AttendanceUserChange;
});