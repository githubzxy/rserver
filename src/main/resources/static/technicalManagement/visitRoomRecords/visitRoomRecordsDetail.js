/**
 * 进出机房人员详情
 * @author zhouxy
 * @date 19-5-30
 */
define('kmms/technicalManagement/visitRoomRecords/visitRoomRecordsDetail', [
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
                    if (data) {
                        $("#formContainer #railLine").val(data.railLine);
                        $("#formContainer #machineRoom").val(data.machineRoom);
                        $("#formContainer #date").val(data.date);
                        $("#formContainer #visitDate").val(data.visitDate);
                        $("#formContainer #visitName").val(data.visitName);
                        $("#formContainer #visitFrom").val(data.visitFrom);
                        $("#formContainer #jobContent").val(data.jobContent);
                        $("#formContainer #helper").val(data.helper);
                        $("#formContainer #leaveDate").val(data.leaveDate);
                        $("#formContainer #remark").val(data.remark);
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
                label: '线别：',
                itemColspan: 1,
                item: '<input type="text" id="railLine" name="railLine" readOnly="readOnly"/>'
            }, {
                label: '机房：',
                itemColspan: 1,
                item: '<input type="text" id="machineRoom" name="machineRoom" readOnly="readOnly"/>'
            }, 
            {
                label: '日期：',
                itemColspan: 1,
                item: '<input type="text" name="date" id="date" readOnly="readOnly"/>'
            },
            {
                label: '进入时间：',
                itemColspan: 1,
                item: '<input type="text" name="visitDate" id="visitDate" readOnly="readOnly"/>'
            },
            {
                label: '进入的人员姓名：',
                itemColspan: 2,
                item: '<input type="text" name="visitName" id="visitName" readOnly="readOnly"/>'
            },
            {
                label: '单位：',
                itemColspan: 2,
                item: '<input type="text" name="visitFrom" id="visitFrom" readOnly="readOnly"/>'
            },
			{
				label: '工作内容：',
				itemColspan: 2,
				item: '<input type="text" name="jobContent" id="jobContent"   >'
			},
            {
                label: '接待人：',
                itemColspan: 1,
                item: '<input type="text" name="helper" id="helper" readOnly="readOnly"/>'
            },
            {
                label: '离开时间：',
                itemColspan: 1,
                item: '<input type="text" name="leaveDate" id="leaveDate" readOnly="readOnly"/>'
            },
            {
                label: '备注：',
                itemColspan: 2,
                item: '<textarea type="text" name="remark" id="remark" readonly style="width:99.5%;height:100px;" maxlength="250" placeholder="最多输入250字"/>'
            }];
            var form = new FormContainer({
                id: 'visitRoomRecordsShow',
                colNum: colNum,
                formChildrens: childs,
//                elStyle: { overflowY: 'scroll'}
            });
            _self.set('formContainer', form);
            return form;
        },

    }, {
        ATTRS: {
            id: { value: 'visitRoomRecordsInfoDialog' },
            title: { value: '详细信息' },
            width: { value: 650 },
            height: { value: 400 },
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