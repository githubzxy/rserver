/**
 * 新增模块
 * @author 
 * @date 
 */
define('kmms/technicalManagement/machineRoomManage/machineRoomManageAdd',
		[
			'bui/common',
			'bui/data',
			'common/form/FormContainer',
			'common/data/PostLoad',
			'common/org/OrganizationPicker',
			'kmms/commonUtil/SelectSuggest',
		 ],function(r){
	var BUI = r('bui/common');
	var Data = r('bui/data');
	var	FormContainer = r('common/form/FormContainer');
	var OrganizationPicker = r('common/org/OrganizationPicker');
	var	PostLoad = r('common/data/PostLoad');
	var SelectSuggest = r("kmms/commonUtil/SelectSuggest");
	var machineRoomManageAdd = BUI.Overlay.Dialog.extend({
		/**
		 * 初始化
		 */
		initializer: function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
			
		},
		
		/**
		 * 渲染
		 */
		renderUI: function(){
			var _self = this;
			_self._getLines();
        	_self._initOrganizationPicker();

		},

		/**
		 * 绑定事件
		 */
		bindUI: function(){
			var _self = this;
            var orgPicker=_self.get('orgPicker');
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #maintenanceOrg').val(e.org.text);
    		    $('#formContainer #orgSelectId').val(e.org.id);
            });
            	//定义按键
			var buttons = [
				{
					text: '保存',
					elCls: 'button',
					handler: function(){
						_self._saveData();
					}
				},{
					text: '关闭',
					elCls: 'button',
					handler: function(){
						_self.close();
					}
				}
			];
			_self.set('buttons',buttons);
		},
		/**
         * 初始化组织机构选择
         * @private
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #maintenanceOrg',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:200,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
        },
		/**
		 * 初始化表单
		 */
		_initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
            	 {
 					label: '机房编码：',
 					itemColspan: 1,
 					redStarFlag : true,
 					item : '<input type="text" id="machineCode" name="machineCode" style="width:99%;" data-rules="{required:true}"/>'
 				},{
 					label: '机房名称：',
 					itemColspan: 1,
 					redStarFlag : true,
 					item : '<input type="text" id="machineName" name="machineName" style="width:99%;" data-rules="{required:true}"/>'
 				},
			    {
					label: '线别：',
					itemColspan: 1,
					redStarFlag : true,
					item : '<div id="name" name="name" style="width: 206px;" data-rules="{required:true}"/>'
				},{
 					label: '维护单位：',
 					itemColspan: 1,
 					item : '<input type="text" id="maintenanceOrg" name="maintenanceOrg" style="width:99%;" readonly/><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
 				}, {
 					label: '投产日期：',
 					itemColspan: 2,
 					item : '<input type="text" id="commissioningDate" name="commissioningDate" style="width:99%;" class="calendar"  readonly/>'
 				},{
					label: '备注：',
					itemColspan : 2,
					item:'<textarea name="remark" id="remark" style="width:99%;height:100px;overflow-y :auto" maxlength="250" placeholder="最多输入250字"/>'
				}
            ];
            var form = new FormContainer({
                id : 'fileFormAdd',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        },
		 /**
         * 获取线别
         */
        _getLines:function(){
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
                renderName: '#formContainer #name',
                inputName: 'name',
                renderData: nameData,
                width: 206
            });
        },
		
		//保存数据
		_saveData:function(){
			var _self = this;
    		var form = _self.getChild('fileFormAdd',true);
    		//验证不通过
    		if(!form.isValid()){
    			return;
    		}
    		var data = form.serializeToObject();
			data.userId = _self.get('userId');
			data.orgId=_self.get('orgId');
		
    		//提交到数据库
    		var postLoad = new PostLoad({
				url : '/zuul/kmms/machineRoomManageAction/addLine',
				ajaxOptions : {
					contentType : 'application/json;charset=utf-8',
					dataType : 'json', 
					data : JSON.stringify(data)
				},
				el : _self.get('el'),
				loadMsg : '保存中...'
			}); 
			postLoad.load({}, function(result){
				if(result.status==2){
					//消息提示框
					BUI.Message.Confirm(result.msg,null,'error');
				}else if(result != null){
					_self.fire("completeAddSave",{
						result : result
					});
				}
			});
		}
	},{
		ATTRS: {
			id: {value : 'fileAddDialog'},
			title: {value : '新增线别'},
			width: {value:600},
	        height: {value:300},
	        contextPath: {},
	        userId:{},
            orgId:{},
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
	        closeAction: {value: 'destroy'},//关闭时销毁加载到主页面的HTML对象
	        mask: {value: true},
	        events: {
	        	value: {
	        		'completeAddSave' : true,
	        	}
	        }
		}
	});
	return machineRoomManageAdd;
});