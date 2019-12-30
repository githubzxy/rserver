/**
 * 维修备忘录新增
 * @author zhouxingyu
 * @date 19-3-25
 */
define('kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunAdd', ['bui/common', 'common/form/FormContainer',
    'bui/form', 'common/org/OrganizationPicker',
], function(r) {
    var BUI = r('bui/common'),
    OrganizationPicker = r('common/org/OrganizationPicker'),
    FormContainer = r('common/form/FormContainer');
    var maintenanceMemorendunAdd = BUI.Overlay.Dialog.extend({
        initializer: function() {
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI: function() {
            var _self = this;
            _self._initOrganizationPicker();
            $('#formContainer #dutyDepartment').val(_self.get('orgName'));
		    $('#formContainer #dutyDepartmentId').val(_self.get('orgId'));
        },
        bindUI: function() {
            var _self = this;
            
            var orgPicker=_self.get('orgPicker');
            
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #dutyDepartment').val(e.org.text);
    		    $('#formContainer #dutyDepartmentId').val(e.org.id);
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
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #dutyDepartment',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:220,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
        },

        /**
         * 初始化FormContainer
         */
        _initFormContainer: function() {
            var _self = this;
            var colNum = 2;
            var childs = [
            {
            	label: '所属部门：',
            	itemColspan: 2,
            	item: '<input type="text" name="dutyDepartment" id="dutyDepartment" style="width:99.5%"/>'
            		+'<input type="hidden" name="dutyDepartmentId" id="dutyDepartmentId"  readonly/>',
            }, {
                label: '检查日期：',
                redStarFlag: true,
                itemColspan: 1,
                item: '<input type="text"  name="checkDate" id="checkDate" style="width:99%" class="calendar" readonly data-rules="{required:true}" />',
            }, {
                label: '检查人：',
                redStarFlag: true,
                itemColspan: 1,
                item: '<input type="text" name="checker" id="checker" style="width:99%"  data-rules="{required:true}"/>',
            }, {
                label: '问题处所：',
                redStarFlag: true,
                itemColspan: 1,
                item: '<input type="text" name="problemFrom" id="problemFrom" style="width:99%"  data-rules="{required:true}" />',
            },{
                label: '整改时限：',
                itemColspan: 1,
                item: '<input type="text" name="endDate" id="endDate" style="width:99%"/>',
            }, {
                label: '发现问题：',
                redStarFlag: true,
                itemColspan: 2,
                item: '<textarea style="border:none;width: 99.5%;resize: none;" id="problemInfo" name="problemInfo" maxlength="900" data-rules="{required:true}">',
            }, {
                label: '整改情况：',
                itemColspan: 2,
                item: '<textarea  style="border:none;width: 99.5%;resize: none;" name="changeInfo" id="changeInfo" style="width:99%"/>',
            }, {
                label: '整改结果：',
                itemColspan: 2,
                item: '<textarea  style="border:none;width: 99.5%;resize: none;" name="result" id="result" style="width:99%" />',
            }, {
                label: '备注：',
                itemColspan: 2,
                item: '<textarea style="border:none;width: 99.5%;resize: none;height:100px" id="note" name="note" maxlength="900">',
            }];
            var form = new FormContainer({
                id: 'maintenanceMemorendunAdd',
                colNum: colNum,
                formChildrens: childs,
            });
            _self.set('formContainer', form);
            return form;
        }
    }, {
        ATTRS: {
            id: { value: 'shiftAddDialog' },
            elAttrs: { value: { id: "shiftAdd" } },
            title: { value: '新增维修工作' },
            width: { value: 650 },
            height: { value: 485 },
            contextPath: {},
            closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
            mask: { value: true },
            collectionName: {},
            orgId:{},
            orgName:{},
            userId: {},
            success: {
                value: function() {
                    var _self = this;
                    var formAdd = _self.getChild('maintenanceMemorendunAdd');
                    //验证不通过
                    if (!formAdd.isValid()) {
                        return;
                    }
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName', _self.get('collectionName'));
                    formData.append('userId', _self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/maintenanceMemorendunAction/addDoc");
                    xhr.send(formData);
                    xhr.onload = function(e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSave", {
                                result: result
                            });
                        }
                    }

                }
            },
            events: {
                value: {
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeAddSave': true,

                }
            },
            rootOrgId: { value: '8affa073533aa3d601533bbef63e0010' },
            rootOrgText: { value: '昆明通信段' },
        }
    });
    return maintenanceMemorendunAdd;
});