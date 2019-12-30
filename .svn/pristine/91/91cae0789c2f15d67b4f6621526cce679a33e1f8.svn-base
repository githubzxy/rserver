/**
 * 签到数据保存
 * @author yangsy
 * @date 19-4-1
 */
define('kmms/integratedManage/attendanceManage/attendanceManageAdd',
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
    var AttendanceManageAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.set("title",_self.get("staffName"));
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	$("#attendanceManageAdd .bui-ext-close").css("display","none");
        	_self._initSelect();
        	_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            //定义按键
            var buttons = [
                {
                    text:'保存',
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
                    label: '早：',
                    itemColspan: 2,
//                    item: '<div id="morningId" name="morning"></div>'
                    item: '<select name="morning" id="morning" style="width:100%"/></select>'
                },
                {
                	label: '中：',
                	itemColspan: 2,
//                	item: '<div id="noonId" name="noon"></div>'
                	item: '<select name="noon" id="noon" style="width:100%"/></select>'
                },
                {
                	label: '晚：',
                	itemColspan: 2,
//                	item: '<div id="nightId" name="night"></div>'
                	item: '<select name="night" id="night" style="width:100%"/></select>'
                },
//                {
//                	label: '班制：',
//                	itemColspan: 2,
//                	item: '<input type="text" name="shifts" id="shifts" style="width:99.5%"/>'
//                },
                {
                	label: '日勤加班：',
                	itemColspan: 2,
                	item: '<input type="number" name="daily" id="daily" style="width:99.6%;height:25px" />'
                },
                {
                	label: '轮班加班：',
                	itemColspan: 2,
                	item: '<input type="number" name="turn" id="turn" style="width:99.6%;height:25px"/>'
                },
                {
                	label: '备注：',
                	itemColspan: 2,
                	item: '<textarea readonly style="width:99.5%;height:125px" placeholder="1、符号规定：白班（／）、小夜班（×）、大夜班（*）、休班（○）、补休（补）、学习（学）、公差（差）、探亲（探）、年休（年）、婚假（婚）、丧假（丧）、病假（病）、住院（住）、事假（事）、产假（产）、护理（护）、息工假（息）、计生假（计）、旷工（旷）。									  2、填记规定：考勤表为电子格式，由指定考勤员负责填写。每人每天分早、中、晚三个部分记录，早为8：00-12：00（白班）、中为14：00-18：00（白班）、晚为18：00-8：00（夜班）。每位职工每天考勤必须按规定符号全面反映。统计栏为自动生成，加班工时须与附件3折算加班工时一致。									  3、日勤加班和轮班加班可以保留小数点后最多2位"/></textarea>'
                },
            ];
            var form = new FormContainer({
                id : 'attendanceManageAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'attendanceManageAddDialog'},
            elAttrs : {value : {id:"attendanceManageAdd"}},
            title:{value:''},
            width:{value:600},
            height:{value:380},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            date:{},
            docId:{},
            staffName:{},
            attendanceManage:{},
            userId:{},//登录用户ID（技术科用户）
            userName:{},//登录用户名称（技术科用户）
            orgId:{},//登录用户所属机构ID
            orgName:{},//登录用户所属机构名称
            parentId:{},//登录用户所属机构上级机构ID
            save:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('attendanceManageAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.collectionName=_self.get('attendanceManage');
	        		data.userId=_self.get('userId');
	        		data.userName=_self.get('userName');
	        		data.orgId=_self.get('orgId');
	        		data.orgName=_self.get('orgName');
	        		data.staffName=_self.get('staffName');
	        		data.docId=_self.get('docId');
	        		data.date=_self.get('date');
                    var pl = new PostLoad({
        				url : '/zuul/kmms/attendanceManageAction/addDoc',
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
    return AttendanceManageAdd;
});