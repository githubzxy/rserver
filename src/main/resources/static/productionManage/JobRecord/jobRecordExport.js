/**
 * 工作日志导出
 * @author zhouxingyu
 * @date 19-4-2
 */
define('kmms/productionManage/JobRecord/jobRecordExport', ['bui/common', 'bui/calendar', 'common/form/FormContainer',
    'bui/form', 'common/org/OrganizationPicker', 'kmms/productionManage/JobRecord/MonthPic'
], function(r) {
    var BUI = r('bui/common'),
        Calendar = r('bui/calendar'),
        MonthPic = r('kmms/productionManage/JobRecord/MonthPic'),
        FormContainer = r('common/form/FormContainer');
    var jobRecordExport = BUI.Overlay.Dialog.extend({
        initializer: function() {
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI: function() {
            var _self = this;
            _self._getWorkShop();
        },
        bindUI: function() {
            var _self = this;
            //工区下拉选选项根据车间而变化
            $("#workShop").on('change', function() {
                $("#workArea").empty();
                var orgName = $("#workShop").val();
                _self._getDepart(orgName);

            });
            //定义按键
            var buttons = [{
                text: '导出',
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
         * 获取车间下拉选数据
         */
        _getWorkShop: function() {
            var _self = this;
            $.ajax({
                url: '/kmms/constructCooperateAction/getWorkShop',
                //                 data:{id : shiftId,collectionName:_self.get('collectionName')},
                type: 'post',
                dataType: "json",
                success: function(res) {
                    for (var i = 0; i < res.length; i++) {
                        $("#workShop").append("<option  value=" + res[i].orgName + ">" + res[i].orgName + "</option>");
                    }
                    var orgName = $("#workShop").val();
                    _self._getDepart(orgName);
                }
            })
        },
        /**
         * 获取工区下拉选数据
         */
        _getDepart: function(orgName) {
            var _self = this;
            $.ajax({
                url: '/kmms/constructCooperateAction/getDepart',
                data: { workShopName: orgName },
                type: 'post',
                dataType: "json",
                success: function(res) {
                    $("#workArea").append("<option  value='所有工区' >所有工区</option>");
                    for (var i = 0; i < res.length; i++) {
                        $("#workArea").append("<option  value=" + res[i].orgName + ">" + res[i].orgName + "</option>");
                    }
                }
            })
        },
        /**
         * 初始化FormContainer
         */
        _initFormContainer: function() {
            var _self = this;
            var colNum = 2;
            var childs = [{
                    label: '日期：',
                    itemColspan: 2,
                    item: '<input type="text"  name="date" id="date" style="width:99%" class="calendar" />'
                },
                {
                    label: '车间：',
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<select  name="workShop" id="workShop"></select>'
                },
                {
                    label: '工区：',
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<select name="workArea" id="workArea" ></select>',
                }
            ];
            var form = new FormContainer({
                id: 'formContainerExport',
                colNum: colNum,
                formChildrens: childs,
            });
            _self.set('formContainerExport', form);
            return form;
        }
    }, {
        ATTRS: {
            id: { value: 'shiftExportDialog' },
            elAttrs: { value: { id: "shiftExport" } },
            title: { value: '工作日志导出' },
            width: { value: 600 },
            height: { value: 200 },
            contextPath: {},
            closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
            mask: { value: true },
            collectionName: {},
            userId: {},
            success: {
                value: function() {
                    var _self = this;
                    //alert($("#exportForm").val());
                    var formAdd = _self.getChild('formContainerExport');
                    //验证不通过
                    if (!formAdd.isValid()) {
                        return;
                    }
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName', _self.get('collectionName'));
                    formData.append('userId', _self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/jobRecordAction/ExportXls");

                    xhr.send(formData);
                    xhr.onload = function(e) {
                        var result = { 'status': '1', 'msg': '操作成功！' };
                        if (e.target.response == "error") {
                            result = { 'status': '0', 'msg': '操作失败！' };
                        }
                        var path = e.target.response;
                        var arr = path.split('/');
                        var fileName = arr[arr.length - 1];
                        window.location.href = '/zuul/kmms/commonAction/download?path=' + path + '&fileName=' + fileName;
                        _self.fire("completeAddSave", {
                            result: result
                        });
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
    return jobRecordExport;
});