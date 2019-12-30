/**
 * 工作日志详情
 * @author zhouxingyu
 * @date 19-4-1
 */
define('kmms/productionManage/JobRecord/jobRecordDetail', ['bui/common', 'common/form/FormContainer',
    'common/uploader/ViewUploader', 'common/data/PostLoad'
], function(r) {
    var BUI = r('bui/common'),
        Uploader = r('common/uploader/ViewUploader'),
        FormContainer = r('common/form/FormContainer');
    var jobRecordDetail = BUI.Overlay.Dialog.extend({
        initializer: function() {
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI: function() {
            var _self = this;
            _self._getShowData();
        },
        bindUI: function() {
            var _self = this;
            //定义按键
            var buttons = [{
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
         * 获取显示数据
         */
        _getShowData: function() {
            var _self = this,
                shiftId = _self.get('shiftId'),
                form = _self.get("formContainer");
            $.ajax({
                url: '/kmms/commonAction/findById',
                data: { id: shiftId, collectionName: _self.get('collectionName') },
                type: 'post',
                dataType: "json",
                success: function(res) {
                    var data = res.data;
                    if (data) {
                        $("#formContainer #workArea").val(data.workArea);
                        $("#formContainer #date").val(data.date);
                        $("#formContainer #onlineNumber").val(data.onlineNumber);
                        $("#formContainer #project").val(data.project);
                        $("#formContainer #content").val(data.content);
                        $("#formContainer #joiner").val(data.joiner);
                        $("#formContainer #joinerNumber").val(data.joinerNumber);
                        $("#formContainer #orgName").val(data.orgName);
                    }
                }
            });
        },
        //时间戳转时间
        _timestampToTime: function(timestamp) {
            if (timestamp) {
                var date = new Date(timestamp);
                Y = date.getFullYear() + '-';
                M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
                D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
                h = (date.getHours() < 10 ? '0' + (date.getHours()) : date.getHours()) + ':';
                m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes()) + ':';
                s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
                return Y + M + D + h + m + s;
            } else {
                return "";
            }
        },
        _renderUploadView(file) {
            var _self = this,
                html = "";
            file.forEach(function(f) {
                html += '<div class="success"><label id="' + f.id + '" class="fileLabel" title=' + f.name + '>' + f.name + '</label><span style="float: right;"><a href="' + _self.get('downloadUrl') + f.path + '&fileName=' + f.name + '">下载</a>&nbsp;' +
                    '<a href="' + _self.get('previewUrl') + f.path + '" target="_blank">预览</a></span></div>';
            });
            return html;
        },
        /**
         * 初始化FormContainer
         */
        _initFormContainer: function() {
            var _self = this;
            var colNum = 2;
            var childs = [{
                label: '工长：',
                itemColspan: 1,
                item: '<input type="text" name="workArea" id="workArea" style="width:99%"  readOnly/>',
            }, {
                label: '在册人数：',
                itemColspan: 1,
                item: '<input type="text" name="onlineNumber" id="onlineNumber" style="width:99%" readOnly/>',
            }, {
                label: '日期：',
                itemColspan: 1,
                item: '<input type="text" name="date" id="date" style="width:99%" readOnly/>',
            }, {
                label: '项目：',
                itemColspan: 2,
                item: '<input type="text" name="project" id="project" style="width:99%" readOnly/>',
            }, {
                label: '填报部门：',
                itemColspan: 1,
                item: '<input  type="text" name="orgName" id="orgName" style="width:99%" readOnly/>',
            }, {
                label: '人数：',
                itemColspan: 1,
                item: '<input  type="text" name="joinerNumber" id="joinerNumber" style="width:99%" readOnly/>',
            }, {
                label: '具体内容：',
                itemColspan: 2,
                item: '<textarea  name="content" id="content" style="width:99.5%;border:none;width: 99.5%;resize: none;" readOnly/>',
            }, {
                label: '人员姓名：',
                itemColspan: 2,
                item: '<textarea style="border:none;width: 99.5%;resize: none;" id="joiner" name="joiner" maxlength="900" readOnly>',
            }];
            var form = new FormContainer({
                id: 'shiftShow',
                colNum: colNum,
                formChildrens: childs,
            });
            _self.set('formContainer', form);
            return form;
        },
    }, {
        ATTRS: {
            id: { value: 'shiftInfoDialog' },
            title: { value: '每日工作日志信息' },
            width: { value: 650 },
            height: { value: 320 },
            contextPath: {},
            shiftId: {},
            closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
            mask: { value: true },
            collectionName: {},
            userId: {},
        }
    });
    return jobRecordDetail;
});