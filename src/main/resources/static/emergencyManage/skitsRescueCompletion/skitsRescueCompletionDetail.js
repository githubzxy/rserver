/**
 * 详情
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionDetail', [
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
                url: '/kmms/skitsRescueCompletionAction/findById',
                data: { id: shiftId, collectionName: _self.get('collectionName') },
                type: 'post',
                dataType: "json",
                success: function(data) {
                    if (data) {
                    	  $("#formContainer #date  ").val(data.date);
                          $("#formContainer #line").val(data.line);
                          $("#formContainer #site").val(data.site);
                          $("#formContainer #orgDepart").val(data.orgDepart);
                          $("#formContainer #organizer").val(data.organizer);
                          $("#formContainer #content").val(data.content);
                          $("#formContainer #joinDepart").val(data.joinDepart);
                          $("#formContainer #publicCarCount").val(data.publicCarCount);
                          $("#formContainer #rentCarCount").val(data.rentCarCount);
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
						label: '时间',
						itemColspan: 1,
						item : '<input type="text" id="date" name="date"  readonly />'
					},
					{
						label: '线别',
						itemColspan: 1,
						item : '<input type="text" id="line" name="line" readonly />'
					},
					{
						label: '地点',
						itemColspan: 1,
						item : '<input type="text" id="site" name="site"  readonly/>'
					},
					{
						label: '组织部门：',
					//	redStarFlag: true,
						itemColspan: 1,
						item: '<input type="text" id="orgDepart" name="orgDepart" readonly />'
					},{
						label: '组织者',
						itemColspan: 1,
						item : '<input type="text" id="organizer" name="organizer" readonly />'
					},{
						label: '参加部门',
						itemColspan: 1,
						item : '<input type="text" id="joinDepart" name="joinDepart" readonly style="width: 235px;">'
					},{
						label: '抢险/演练内容',
						itemColspan: 2,
						item : '<textarea type="text" id="content" name="content"  readonly/>'
					},{
						label: '出动车辆（公用）数量',
						itemColspan: 1,
						item : '<input type="text" id="publicCarCount" name="publicCarCount"  readonly/>'
					},{
						label: '出动车辆（租用）数量',
						itemColspan: 1,
						item : '<input type="text" id="rentCarCount" name="rentCarCount" readonly />'
					},{
						label: '抢险/演练报告',
						itemColspan: 2,
						item: '<div name="uploadFile" id="uploadFile" style="height:80px;overflow-y:auto" readonly></div>'
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
            width: {value:650},
	        height: {value:380},
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