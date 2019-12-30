/**
 * 机房机架详情模块
 */
define('kmms/dayToJobManagement/constructRepair/ConstructRepairInfo', ['bui/common', 'common/form/FormContainer',
    'common/data/PostLoad','common/uploader/ViewUploader'
], function(r) {
    var BUI = r('bui/common'),
    	ViewUploader = r('common/uploader/ViewUploader'),
        FormContainer = r('common/form/FormContainer');
    var ConstructRepairInfo = BUI.Overlay.Dialog.extend({
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
                        $("#formContainer #date").val(data.date);
                        $("#formContainer #project").val(data.project);
                        $("#formContainer #grade").val(data.grade);
                        $("#formContainer #repairType").val(data.repairType);
                        $("#formContainer #line").val(data.line);
                        $("#formContainer #planNum").val(data.planNum);
                        $("#formContainer #workShop").val(data.workShop);
                        $("#formContainer #workArea").val(data.workArea);
                        $("#formContainer #planAgreeTimeStart").val(data.planAgreeTimeStart);
                        $("#formContainer #planAgreeTimeEnd").val(data.planAgreeTimeEnd);
                        $("#formContainer #applyMinute").val(data.applyMinute);
                        $("#formContainer #actAgreeTime").val(data.actAgreeTime);
                        $("#formContainer #actOverTime").val(data.actOverTime);
                        $("#formContainer #actMinute").val(data.actMinute);
                        $("#formContainer #timeCash").val(data.timeCash);
                        $("#formContainer #totalMan").val(data.totalMan);
                        $("#formContainer #checkLeader").val(data.checkLeader);
                        $("#formContainer #constructContent").val(data.constructContent);
                        $("#formContainer #remark").val(data.remark);
                        /*var fileCols = $("#fileCols").val();
                        fileCols.split(",").forEach(function(col) {
                            $("#" + col).html(_self._renderUploadView(data[col]));
                        });*/
                        if(data.uploadfile) {
            				_self._initViewUploader(data.uploadfile);
            			}
                    }
                }
            });
        },
        /**
         * 原始初始化文件显示
         */
       /* _renderUploadView(file) {
            var _self = this,
                html = "";
            file.forEach(function(f) {
                html += '<div class="success"><label id="' + f.id + '" class="fileLabel" title=' + f.name + '>' + f.name + '</label><span style="float: right;"><a href="' + _self.get('downloadUrl') + f.path + '&fileName=' + f.name + '">下载</a></span></div>'
                //                    +'<a href="' + _self.get('previewUrl') + f.path + '" target="_blank">预览</a></span></div>';
            });
            return html;
        },*/
        /**
		 * 初始化上传文件（仅用于查看）
		 */
		_initViewUploader:function(uploadFiles){
			var _self = this;
			var viewFiles = new ViewUploader({
				render: '#formContainer #viewUploadfile',
				alreadyItems : uploadFiles,
				previewOnline:true
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
                label: '日期：',
                itemColspan: 1,
                item: '<input type="text"  name="date" id="date"   readonly />' +
                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
                    'value="uploadfile">'
            }, {
                label: '项目：',
                itemColspan: 1,
                item: '<input type="text"  name="project" id="project" readonly/>'
            }, {
                label: '等级：',
                itemColspan: 1,
                item: '<input type="text" name="grade" id="grade"  readonly/>'
            }, {
                label: '维修类型：',
                itemColspan: 1,
                item: '<input type="text"  name="repairType" id="repairType" readonly/>'
            }, {
                label: '线别：',
                itemColspan: 1,
                item: '<input type="text"  name="line" id="line" readonly/ >'
            }, {
                label: '计划号：',
                itemColspan: 1,
                item: '<input type="text" name="planNum" id="planNum" readonly/>'
            }, {
                label: '车间：',
                itemColspan: 1,
                item: '<input type="text"  name="workShop" id="workShop" readonly/>'
            }, {
                label: '工区：',
                itemColspan: 1,
                item: '<input type="text" name="workArea" id="workArea" readonly/>'
            }, {
                label: '计划批准时间（开始）：',
                itemColspan: 1,
                item: '<input type="text" name="planAgreeTimeStart" id="planAgreeTimeStart"  readonly/>'
            }, {
                label: '计划批准时间（结束）：',
                itemColspan: 1,
                item: '<input type="text" name="planAgreeTimeEnd" id="planAgreeTimeEnd"  readonly/>'
            }, {
                label: '申请时间（分钟）：',
                itemColspan: 1,
                item: '<input type="text" name="applyMinute" id="applyMinute" readonly/>'
            }, {
                label: '实际给点时间：',
                itemColspan: 1,
                item: '<input type="text" name="actAgreeTime" id="actAgreeTime"   readonly/>'
            }, {
                label: '实际完成时间：',
                itemColspan: 1,
                item: '<input type="text" name="actOverTime" id="actOverTime"   readonly/>'
            }, {
                label: '实际批准时间（分钟）：',
                itemColspan: 1,
                item: '<input type="text" name="actMinute" id="actMinute" readonly/>'
            }, {
                label: '时间兑现率：',
                itemColspan: 1,
                item: '<input type="text" name="timeCash" id="timeCash" readonly/>'
            }, {
                label: '作业人数：',
                itemColspan: 1,
                item: '<input type="text" name="totalMan" id="totalMan" readonly/>'
            }, {
                label: '机关把关干部：',
                itemColspan: 2,
                item: '<input type="text" name="checkLeader" id="checkLeader" readonly/>'
            }, {
                label: '施工内容：',
                itemColspan: 2,
                item: '<textarea type="text" name="constructContent" id="constructContent" readonly/>'
            }, {
                label: '备注：',
                itemColspan: 2,
                item: '<textarea type="text" name="remark" id="remark" readonly/>'
            }, {
                label: '附件：',
                itemColspan: 2,
                item : '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
            }];
            var form = new FormContainer({
                id: 'constructRepairShow',
                colNum: colNum,
                formChildrens: childs,
                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer', form);
            return form;
        },

    }, {
        ATTRS: {
            id: { value: 'constructRepairInfoDialog' },
            title: { value: '通知详细信息' },
            width: { value: 640 },
            height: { value: 500 },
            previewUrl: { value: '/pageoffice/openPage?filePath=' },
            downloadUrl: { value: '/kmms/commonAction/download?path=' },
            contextPath: {},
            shiftId: {},
            closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
            mask: { value: true },
            collectionName: {},
            userId: {},
        }
    });
    return ConstructRepairInfo;
});