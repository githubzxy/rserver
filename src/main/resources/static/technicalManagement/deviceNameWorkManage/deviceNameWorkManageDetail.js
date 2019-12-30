/**
 * 机房技术资料及台账详情
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/technicalManagement/deviceNameWorkManage/deviceNameWorkManageDetail', [
    'bui/overlay', 'common/uploader/ViewUploader',
    'common/form/FormContainer',
    'common/data/PostLoad'
], function(r) {
    var Overlay = r('bui/overlay'),
        ViewUploader = r('common/uploader/ViewUploader'),
        PostLoad = r('common/data/PostLoad'),
        FormContainer = r('common/form/FormContainer');
    var deviceNameWorkManagePageNewDetails = Overlay.Dialog.extend({
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
                url: '/kmms/deviceNameWorkManageAction/findById',
                data: { id: shiftId, collectionName: _self.get('collectionName') },
                type: 'post',
                dataType: "json",
                success: function(r) {
                	if (r.data) {
                        $("#formContainer #deviceName").val(r.data.deviceName);
                        $("#formContainer #workContent").val(r.data.workContent);
                        $("#formContainer #remark").val(r.data.remark);
                        $("#formContainer #unit").val(r.data.unit);
                        $("#formContainer #type").val(r.data.type);
                        $("#formContainer #countYear").val(r.data.countYear);
                        $("#formContainer #yearOrMonth").val(r.data.yearOrMonth);
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
				label: '设备名称：',
				itemColspan: 2,
				item: '<input type="text" name="deviceName" id="deviceName" style="width:99.5%;" data-rules="{required:true,maxlength:200}" readonly/>'
			},{
				label: '工作内容：',
				itemColspan: 2,
				item : '<textarea  id="workContent" name="workContent" style="width:99.5%;height:80px;overflow-y :auto" readonly/>'
			},{
				label: '标识：',
				itemColspan: 1,
				item : '<input type="text" id="yearOrMonth" name="yearOrMonth"  readonly />'
			},{
				label: '类别：',
				itemColspan: 1,
				item : '<input type="text" id="type" name="type"  readonly/>'
			},{
				label: '周期：',
				itemColspan: 1,
				item : '<input  type="text" id="countYear" name="countYear" readonly/>'
			},{
				label: '单位：',
				itemColspan: 1,
				item : '<input type="text" id="unit" name="unit" readonly/>'
			},{
				label: '备注：',
				itemColspan : 2,
				item:'<textarea name="remark" id="remark"  style="width:99.5%;height:100px;overflow-y:auto" readonly/>'
			}];
            var form = new FormContainer({
                id: 'deviceNameWorkManageShow',
                colNum: colNum,
                formChildrens: childs,
//                elStyle: { overflowY: 'scroll'}
            });
            _self.set('formContainer', form);
            return form;
        },

    }, {
        ATTRS: {
            id: { value: 'deviceNameWorkManageInfoDialog' },
            title: { value: '详细信息' },
            width: { value: 650 },
            height: { value: 380 },
            contextPath: {},
            shiftId: {},
            closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
            mask: { value: true },
            collectionName: {},
            userId: {},
        }
    });
    return deviceNameWorkManagePageNewDetails;
});