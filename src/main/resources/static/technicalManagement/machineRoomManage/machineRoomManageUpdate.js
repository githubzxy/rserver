/**
 * 修改机房账号模块
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/technicalManagement/machineRoomManage/machineRoomManageUpdate',
    [
    	'bui/common', 
    	'common/form/FormContainer', 
    	'bui/data',
        'common/data/PostLoad', 
        'bui/form',
        'common/org/OrganizationPicker',
        'kmms/commonUtil/SelectSuggest'
    ],
    function(r) {
        var BUI = r('bui/common'),
            FormContainer = r('common/form/FormContainer'),
            Data = r('bui/data'),
            PostLoad = r('common/data/PostLoad'),
        	OrganizationPicker = r('common/org/OrganizationPicker');
            Form = r('bui/form');
        var SelectSuggest = r("kmms/commonUtil/SelectSuggest");
        var machineRoomManageUpdate = BUI.Overlay.Dialog.extend({
            initializer: function() {
                var _self = this;
                _self.addChild(_self._initFormContainer());
                
            },
            renderUI: function() {
                var _self = this;
                _self._getShowData();
            	_self._initOrganizationPicker();

            },
            
            bindUI: function() {
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
                var buttons = [{
                    text: '保存',
                    elCls: 'button',
                    handler: function() {
                        var success = _self.get('success');
                        if (success) {
                            success.call(_self);
                        }
                    }
                }, {
                    text: '关闭',
                    elCls: 'button',
                    handler: function() {
                        if (this.onCancel() !== false) {
                            this.close();
                        }
                    }
                }];
                _self.set('buttons', buttons);
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
            _getLines:function(lineName){
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
            	$("input[name='name']").val(lineName);

            },
            /**
             * 获取显示数据
             */
            _getShowData: function() {
                var _self = this,
                    shiftId = _self.get('shiftId');
                $.ajax({
                    url: '/kmms/machineRoomManageAction/findById',
                    data: { id: shiftId},
                    type: 'post',
                    async: false,
                    dataType: "json",
                    success: function(data) {
                        if (data) {
                        	_self._getLines(data.name);
                            $("#formContainer #machineCode").val(data.machineCode);
                            $("#formContainer #machineName").val(data.machineName);
                            $("#formContainer #maintenanceOrg").val(data.maintenanceOrg);
                            $("#formContainer #commissioningDate").val(data.commissioningDate);
                            $("#formContainer #remark").val(data.remark);
                        }
                    }
                });
            },

            /**
             * 初始化FormContainer
             */
            _initFormContainer: function() {
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
    				}];
                var form = new FormContainer({
                    id: 'machineRoomManageUpdateForm',
                    colNum: colNum,
                    formChildrens: childs,
//                    elStyle: { overflowY: 'scroll'}
                });
                _self.set('formContainer', form);
                return form;
            }
        }, {
            ATTRS: {
                id: { value: 'machineRoomManageUpdateFormDialog' },
                elAttrs: { value: { id: "machineRoomManageEdit" } },
                title: { value: '修改' },
                width: {value:600},
    	        height: {value:300},
    	        rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
                mask: { value: true },
                collectionName: {},
                userId: {}, //登录用户ID
                userName: {}, //登录用户名称
                orgId: {}, //登录用户组织机构ID
                orgName: {}, //登录用户组织机构名称
                shiftId: {},
                success: {
                    value: function() {
                        var _self = this;
                        var formAdd = _self.getChild('machineRoomManageUpdateForm');
                        //验证不通过
                        if (!formAdd.isValid()) {
                            return;
                        }
                        //序列化表单成对象，所有的键值都是字符串
                        var data = formAdd.serializeToObject();
                        data.id = _self.get('shiftId');
                        data.userId = _self.get('userId');
                        var pl = new PostLoad({
                            url: '/kmms/machineRoomManageAction/update',
                            ajaxOptions : {
            					contentType : 'application/json;charset=utf-8',
            					dataType : 'json', 
            					data : JSON.stringify(data)
            				},
                            el: _self.get('el'),
                            loadMsg: '上传中...'
                        });
                        pl.load(data, function(e) {
                        	if(e.status==2){
            					//消息提示框
            					BUI.Message.Confirm(e.msg,null,'error');
            				}else if (e) {
                                _self.fire("completeAddSave", {
                                    result: e
                                });
                            }
                        });

                    }
                },
                events: {
                    value: {
                        'completeAddSave': true,
                    }
                },
            }
        });
        return machineRoomManageUpdate;
    });