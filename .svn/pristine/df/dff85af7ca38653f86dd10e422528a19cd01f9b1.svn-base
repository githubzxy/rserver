/**
 * 详情
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/emergencyManage/exerciseScheme/exerciseSchemeDetail', [
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
                url: '/kmms/exerciseSchemeAction/findById',
                data: { id: shiftId, collectionName: _self.get('collectionName') },
                type: 'post',
                dataType: "json",
                success: function(data) {
                    console.log(data);
                    if (data) {
                        $("#formContainer #depart").val(data.depart);
                        $("#formContainer #createUser").val(data.createUser);
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
            var childs = [
              {
                label: '所属部门：',
                itemColspan: 2,
                item: '<input type="text" id="depart" name="depart" readOnly="readOnly"/>'
            }, {
                label: '创建人：',
                itemColspan: 2,
                item: '<input type="text" id="createUser" name="createUser" readOnly="readOnly"/>'
            }, {
                label: '附件：',
                itemColspan: 2,
                item: '<div type="text" id="uploadFile" name="uploadFile" style="height:100px;overflow-y:auto" readOnly="readOnly"/>'
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
            title: { value: '详细信息' },
            width: { value: 500 },
            height: { value: 270 },
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