/**
 * 机房技术资料及台账详情
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/technicalManagement/technicalDocument/technicalDocumentDetail', [
    'bui/overlay', 'common/uploader/ViewUploader',
    'common/form/FormContainer',
    'common/data/PostLoad'
], function(r) {
    var Overlay = r('bui/overlay'),
        ViewUploader = r('common/uploader/ViewUploader'),
        PostLoad = r('common/data/PostLoad'),
        FormContainer = r('common/form/FormContainer');
    var TechnicalAccountPageNewDetails = Overlay.Dialog.extend({
        initializer: function() {
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI: function() {
            var _self = this;
            //显示数据
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
                url: '/kmms/constructRepairAction/findById',
                data: { id: shiftId, collectionName: _self.get('collectionName') },
                type: 'post',
                dataType: "json",
                success: function(data) {
                    console.log(data);
                    if (data) {
                        $("#formContainer #name").val(data.name);
                        $("#formContainer #railLine").val(data.railLine);
                        $("#formContainer #machineRoom").val(data.machineRoom);
                        $("#formContainer #remark").val(data.remark);
                        if (data.uploadFile) {
                            _self._initViewUploader(data.uploadFile);
                        }
                    }
                }
            });
        },
        /**
         * 初始化上传文件（仅用于查看）
         */
        _initViewUploader: function(uploadFiles) {
            var _self = this;
            var viewFiles = new ViewUploader({
                render: '#formContainer #uploadFile',
                alreadyItems: uploadFiles,
                previewOnline: true
            });
            viewFiles.render();
        },
        /**
         * 初始化FormContainer
         */
        _initFormContainer: function() {
            var _self = this;
            var colNum = 2;
            var childs = [{
                label: '资料名称：',
                itemColspan: 2,
                item: '<input type="text" name="name" id="name" readOnly="readOnly"/>'
            }, {
                label: '线别：',
                itemColspan: 1,
                item: '<input type="text" id="railLine" name="railLine" readOnly="readOnly"/>'
            }, {
                label: '机房：',
                itemColspan: 1,
                item: '<input type="text" id="machineRoom" name="machineRoom" readOnly="readOnly"/>'
            }, {
                label: '附件：',
                itemColspan: 2,
                item: '<div type="text" id="uploadFile" name="uploadFile" style="height:100px;overflow-y:auto" readOnly="readOnly"/>'
            }, {
                label: '备注：',
                itemColspan: 2,
                item: '<textarea type="text" name="remark" id="remark" readonly style="width:99.5%;height:100px;" maxlength="250" placeholder="最多输入250字"/>'
            }];
            var form = new FormContainer({
                id: 'technicalDocumentShow',
                colNum: colNum,
                formChildrens: childs,
//                elStyle: { overflowY: 'scroll'}
            });
            _self.set('formContainer', form);
            return form;
        },

    }, {
        ATTRS: {
            id: { value: 'technicalDocumentInfoDialog' },
            title: { value: '资料 详细信息' },
            width: { value: 650 },
            height: { value: 370 },
            contextPath: {},
            shiftId: {},
            closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
            mask: { value: true },
            collectionName: {},
            userId: {},
        }
    });
    return TechnicalAccountPageNewDetails;
});