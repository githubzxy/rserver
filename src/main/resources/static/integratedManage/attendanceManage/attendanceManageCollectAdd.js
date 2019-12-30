/**
 * 新增考勤汇总表
 * @author yangsy
 * @date 19-4-3
 */
define('kmms/integratedManage/attendanceManage/attendanceManageCollectAdd',
		[
			'bui/common',
			'bui/form',
			'bui/select',
			'bui/data',
			'bui/mask',
			'common/form/FormContainer',
			'common/data/PostLoad',
		],function(r){
    var BUI = r('bui/common'),Select = r('bui/select'),Data = r('bui/data'),Mask = r('bui/mask'),
    	FormContainer = r('common/form/FormContainer'),
    	PostLoad = r('common/data/PostLoad');
    var attendanceManageCollectAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initSelect();
        	$('#formContainer #attendanceOrgName').val(_self.get('orgName'));
        	$('#formContainer #attendanceOrgId').val(_self.get('orgId'));
		    $('#formContainer #attendanceUserName').val(_self.get('userName'));
		    $('#formContainer #attendanceUserId').val(_self.get('userId'));
        },
        bindUI : function(){
            var _self = this;
            //定义按键
            var buttons = [
                {
                    text:'生成',
                    elCls : 'button',
                    handler : function(){
                        var save = _self.get('save');
                        if(save){
                        	save.call(_self);
                        }
                    }
                },
                {
                    text:'关闭',
                    elCls : 'button',
                    handler : function(){
                        if(this.onCancel() !== false){
                            this.close();
                        }
                    }
                }
            ];
            _self.set('buttons',buttons);
        },
        
        _initSelect: function(){
			$("#formContainer #year").append("<option  value='2019年'>2019年</option>");
			$("#formContainer #year").append("<option  value='2020年'>2020年</option>");
			$("#formContainer #year").append("<option  value='2021年'>2021年</option>");
			$("#formContainer #year").append("<option  value='2022年'>2022年</option>");
			$("#formContainer #year").append("<option  value='2023年'>2023年</option>");
			$("#formContainer #year").append("<option  value='2024年'>2024年</option>");
			$("#formContainer #year").append("<option  value='2025年'>2025年</option>");
			$("#formContainer #year").append("<option  value='2026年'>2026年</option>");
			$("#formContainer #year").append("<option  value='2027年'>2027年</option>");
			$("#formContainer #year").append("<option  value='2028年'>2028年</option>");
			$("#formContainer #year").append("<option  value='2029年'>2029年</option>");
			
			$("#formContainer #month").append("<option  value='1月'>1月</option>");
			$("#formContainer #month").append("<option  value='2月'>2月</option>");
			$("#formContainer #month").append("<option  value='3月'>3月</option>");
			$("#formContainer #month").append("<option  value='4月'>4月</option>");
			$("#formContainer #month").append("<option  value='5月'>5月</option>");
			$("#formContainer #month").append("<option  value='6月'>6月</option>");
			$("#formContainer #month").append("<option  value='7月'>7月</option>");
			$("#formContainer #month").append("<option  value='8月'>8月</option>");
			$("#formContainer #month").append("<option  value='9月'>9月</option>");
			$("#formContainer #month").append("<option  value='10月'>10月</option>");
			$("#formContainer #month").append("<option  value='11月'>11月</option>");
			$("#formContainer #month").append("<option  value='12月'>12月</option>");
        },

        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label: '考勤部门：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<input type="text" name="attendanceOrgName" id="attendanceOrgName" readonly/>'+'<input type="hidden" name="attendanceOrgId" id="attendanceOrgId" readonly/>'
                },
                {
                	label: '考勤人员：',
                	redStarFlag: true,
                	itemColspan: 1,
                	item: '<input type="text" name="attendanceUserName" id="attendanceUserName" readonly/>'+'<input type="hidden" name="attendanceUserId" id="attendanceUserId" readonly/>'
                },
                {
                	label: '年份：',
                	redStarFlag: true,
                	itemColspan: 1,
                	item: '<select name="year" id="year" style="width:99.5%" data-rules="{required:true}"/></select>'
                },
                {
                	label: '月份：',
                	redStarFlag: true,
                	itemColspan: 1,
                	item: '<select name="month" id="month" style="width:99.5%" data-rules="{required:true}"/></select>'
                },
            ];
            var form = new FormContainer({
                id : 'attendanceManageCollectAddForm',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'attendanceManageCollectAddDialog'},
            elAttrs : {value : {id:"attendanceManageCollectAdd"}},
            title:{value:'汇总月考勤表'},
            width:{value:650},
            height:{value:170},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            attendanceManage:{},//考勤打卡数据库表
            attendanceManageCollect:{},//考勤汇总表存储表
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户所属机构ID
            orgName:{},//登录用户所属机构名称
            parentId:{},//登录用户所属机构上级机构ID
            orgType:{},//登陆用户组织机构类别（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
            save:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('attendanceManageCollectAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.attendanceManage=_self.get('attendanceManage');
	        		data.attendanceManageCollect=_self.get('attendanceManageCollect');
	        		data.orgType=_self.get("orgType")+"";
	        		data.flowState="0";//已生成
	        		if(_self.get("orgType")==1502){//如果为车间用户则数据添加车间需查看的数据标识
	        			data.workshopQueryData = _self.get('orgId');
	        		}else if(_self.get("orgType")==1501&&_self.get('orgId')=="402891b45b5fd02c015b74c913260035"){//办公室ID
	        			data.workshopQueryData = _self.get('orgId');//如果为办公室用户则数据添加办公室需查看的数据标识
	        		}else if(_self.get("orgType")==1501&&_self.get('orgId')=="402891b45b5fd02c015b75395c3a0099"){//职教科ID
	        			data.workshopQueryData = _self.get('orgId');//如果为职教科用户则数据添加职教科需查看的数据标识
	        		}else{
	        			data.workshopQueryData = "";
	        		}
                    var pl = new PostLoad({
        				url : '/zuul/kmms/attendanceManageCollectAction/addCollectDoc',
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
            events : {value : {'completeAddSave' : true}},
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'}
        }
    });
    return attendanceManageCollectAdd;
});