/**
 * 每日工作日志新增 可能不需要做
 * @author zhouxingyu
 * @date 19-3-25
 */
define('kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunAdd', ['bui/common', 'common/form/FormContainer',
    'bui/form', 'common/org/OrganizationPicker',
], function(r) {
    var BUI = r('bui/common'),
        FormContainer = r('common/form/FormContainer');
    var maintenanceMemorendunAdd = BUI.Overlay.Dialog.extend({
        initializer: function() {
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI: function() {
            var _self = this;
        },
        bindUI: function() {
            var _self = this;
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
         * 初始化FormContainer
         */
        _initFormContainer: function() {
            var _self = this;
            var colNum = 2;
            var childs = [{
                label: '检查日期：',
                redStarFlag: true,
                itemColspan: 1,
                item: '<input type="text"  name="checkDate" id="checkDate" style="width:99%" class="calendar calendar-time" readonly data-rules="{required:true}" />',
            }, {
                label: '检查人：',
                redStarFlag: true,
                itemColspan: 1,
                item: '<input type="text" name="checker" id="checker" style="width:99%"  data-rules="{required:true}"/>',
            }, {
                label: '问题出所：',
                redStarFlag: true,
                itemColspan: 1,
                item: '<input type="text" name="problemFrom" id="problemFrom"  data-rules="{required:true}" />',
            }, {
                label: '责任部门（登陆用户名）：',
                itemColspan: 1,
                item: '<input type="text" name="dutyDepartment" id="dutyDepartment" style="width:99%"/>',
            }, {
                label: '督办部门（车间或科室）：',
                itemColspan: 1,
                item: '<input type="text" name="helpDepartment" id="helpDepartment" style="width:99%"/>',
            }, {
                label: '整改时限：',
                itemColspan: 1,
                item: '<input type="text" name="endDate" id="endDate" style="width:99%"/>',
            }, {
                label: '发现问题：',
                redStarFlag: true,
                itemColspan: 2,
                item: '<textarea style="border:none;width: 99%;resize: none;" id="problemInfo" name="problemInfo" maxlength="900">',
            }, {
                label: '整改情况：',
                itemColspan: 2,
                item: '<textarea  style="border:none;width: 99%;resize: none;" name="changeInfo" id="changeInfo" style="width:99%"/>',
            }, {
                label: '整改结果：',
                itemColspan: 2,
                item: '<textarea  style="border:none;width: 99%;resize: none;" name="result" id="result" style="width:99%" />',
            }, {
                label: '备注：',
                itemColspan: 2,
                item: '<textarea style="border:none;width: 99%;resize: none;" id="note" name="note" maxlength="900">',
            }];
            var form = new FormContainer({
                id: 'maintenanceMemorendunAdd',
                colNum: colNum,
                formChildrens: childs,
                elStyle: { overflowY: 'scroll', height: '400px' }
            });
            _self.set('formContainer', form);
            return form;
        }
    }, {
        ATTRS: {
            id: { value: 'shiftAddDialog' },
            elAttrs: { value: { id: "shiftAdd" } },
            title: { value: '新增维修工作' },
            width: { value: 610 },
            height: { value: 500 },
            contextPath: {},
            closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
            mask: { value: true },
            collectionName: {},
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