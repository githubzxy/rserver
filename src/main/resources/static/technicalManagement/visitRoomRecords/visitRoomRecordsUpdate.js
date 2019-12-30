/**
 * 修改进出机房模块
 * @author zhouxingyu
 * @date 19-5-30
 */
define('kmms/technicalManagement/visitRoomRecords/visitRoomRecordsUpdate',
    ['bui/common', 'common/form/FormContainer', 'bui/data',
        'common/uploader/UpdateUploader', 'common/data/PostLoad', 'bui/form',
        'kmms/technicalManagement/visitRoomRecords/SelectSuggest'
    ],
    function(r) {
        var BUI = r('bui/common'),
            FormContainer = r('common/form/FormContainer'),
            UpdateUploader = r('common/uploader/UpdateUploader'),
            Data = r('bui/data'),
            PostLoad = r('common/data/PostLoad'),
            Form = r('bui/form');
        var SelectSuggest = r("kmms/technicalManagement/visitRoomRecords/SelectSuggest");
        var TechnicalAccountPageNewUpdate = BUI.Overlay.Dialog.extend({
            initializer: function() {
                var _self = this;
                _self.addChild(_self._initFormContainer());
            },
            renderUI: function() {
                var _self = this;
                _self._getShowData();
//                _self._initRailAndRoom();
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
            
            _getLines:function(lineName){
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/networkManageInfoAction/getLines',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	
            	var suggest = new SelectSuggest({
                    renderName: '#formContainer #railLineEditDialog',
                    inputName: 'railLineEditDialog',
                    renderData: nameData,
                    width: 222
                });
            	
            	$("input[name='railLineEditDialog']").val(lineName);
            	
            },
            
            _getStation:function(station){
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/networkManageInfoAction/getStationNoCondition',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	
            	var suggest = new SelectSuggest({
                    renderName: '#formContainer #machineRoomEditDialog',
                    inputName: 'machineRoomEditDialog',
                    renderData: nameData,
                    width: 222
                });
            	
            	$("input[name='machineRoomEditDialog']").val(station);
            	
            },
            
            /**
             * 初始化线别
             */
//            _initRailAndRoom: function() {
//                var _self = this;
//                var nameData = [];
//                var postLoad = new PostLoad({
//                    url: '/kmms/constructCooperateAction/getLines',
//                });
//                postLoad.load({}, function(result) {
//                    if (result == null) {
//                        return;
//                    }
//                    for (var i = 0; i < result.length; i++) {
//                        nameData.push(result[i]);
//                    }
//                });
//                /**
//                 * 初始化线别
//                 */
//                var suggest = new SelectSuggest({
//                    renderName: '#railLineEditDialog',
//                    inputName: 'railLineEditDialog',
//                    renderData: nameData,
//                    width: 510
//                });
//                var railLineStr = _self.get('railLine');
//                $("input[name='railLineEditDialog']").val(railLineStr);
//                /**
//                 * 初始化机房
//                 */
//                var suggest2 = new SelectSuggest({
//                    renderName: '#machineRoomEditDialog',
//                    inputName: 'machineRoomEditDialog',
//                    renderData: [],
//                    width: 510
//                });
//                var machineRoomStr = _self.get('machineRoom');
//                $("input[name='machineRoomEditDialog']").val(machineRoomStr);
//            },
            /**
             * 选择线别_initRoom初始化机房数据
             */
//            _initMachineRoom: function(railLine) {
//                var nameData = [];
//                var postLoad = new PostLoad({
//                    url: '/kmms/visitRoomRecordsAction/getMachineRooms',
//                });
//                postLoad.load({ railLine: "" }, function(result) {
//                    if (result == null) {
//                        return;
//                    }
//                    for (var i = 0; i < result.length; i++) {
//                        nameData.push(result[i]);
//                    }
//                });
//                var suggest = new SelectSuggest({
//                    renderName: '#machineRoomEditDialog',
//                    inputName: 'machineRoomEditDialog',
//                    renderData: nameData,
//                    width: 510
//                });
//
//
//            },

//            _delFile: function(e, self) {
//                var delData = self.get('delData'),
//                    tdata = e.target.dataset;
//                delData[tdata.col].push(tdata.id);
//                $(e.target).parents('.success').remove();
//            },

            /**
             * 获取显示数据
             */
            _getShowData: function() {
                var _self = this,
                    shiftId = _self.get('shiftId'),
                    form = _self.get("formContainer");
//                    delData = {};
                $.ajax({
                    url: '/kmms/constructRepairAction/findById',
                    data: { id: shiftId, collectionName: _self.get('collectionName') },
                    type: 'post',
                    async: false,
                    dataType: "json",
                    success: function(data) {
                        if (data) {
                        	_self._getLines(data.railLine);
                        	_self._getStation(data.machineRoom);
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
//                _self.set('delData', delData);
            },

            /**
             * 初始化上传文件
             */
            _initUploader: function(uploadFiles) {
                var _self = this;
                var uploader = new UpdateUploader({
                    render: '#formContainer #uploadFile',
                    url: '/zuul/kmms/atachFile/upload.cn',
                    alreadyItems: uploadFiles,
                    isSuccess: function(result) {
                        return true;
                    },
                });
                uploader.render();
                _self.set('uploader', uploader);
            },
            /**
             * 获取上传文件数据(上传的)
             */
            _getUploadFileData: function() {
                var _self = this,
                uploader = _self.get('uploader');
                var arr = new Array(); //声明数组,存储数据发往后台
                //获取上传文件的队列
                var fileArray = uploader.getSucFiles();
                for (var i in fileArray) {
                    var dto = new _self._UploadFileDto(fileArray[i].name, fileArray[i].path); //声明对象
                    arr[i] = dto; // 向集合添加对象
                };
                return arr;
            },
            /**
             * 声明上传文件对象
             * @param name 上传文件名
             * @param path 上传文件路径
             */
            _UploadFileDto: function(name, path) {
                this.name = name;
                this.path = path;
            },


            /**
             * 初始化FormContainer
             */
            _initFormContainer: function() {
                var _self = this;
                var colNum = 2;
                var childs = [
                 {
                    label: '所属线别：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<div name="railLineEditDialog" id="railLineEditDialog" style="width: 200px;"/>' 
                }, {
                    label: '所属机房：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<div name="machineRoomEditDialog" id="machineRoomEditDialog" style="width: 200px;"/>' 
                },
				{
					label: '日期：',
					redStarFlag: true,
					itemColspan: 1,
					item: '<input type="text" name="date" id="date" class="calendar"  />'
				},
				{
					label: '进入时间：',
					redStarFlag: true,
					itemColspan: 1,
					item: '<input type="text" name="visitDate" id="visitDate" class="calendar calendar-time"  />'
				},
				{
					label: '进入的人员姓名：',
					itemColspan: 2,
					item: '<input type="text" name="visitName" id="visitName"   >'
				},
				{
					label: '单位：',
					itemColspan: 2,
					item: '<input type="text" name="visitFrom" id="visitFrom"   >'
				},
				{
					label: '工作内容：',
					itemColspan: 2,
					item: '<input type="text" name="jobContent" id="jobContent"   >'
				},
				{
					label: '接待人：',
					itemColspan: 1,
					item: '<input type="text" name="helper" id="helper"   >'
				},
				{
					label: '离开时间：',
					itemColspan: 1,
					item: '<input type="text" name="leaveDate" id="leaveDate"  class="calendar calendar-time" />'
				},{
					label: '备注：',
					itemColspan : 2,
					item:'<textarea name="remark" id="remark" style="width:99.5%;height:100px;overflow-y :auto" maxlength="250" placeholder="最多输入250字"/>'
				}];
                var form = new FormContainer({
                    id: 'TechnicalPageNewUpdateForm',
                    colNum: colNum,
                    formChildrens: childs,
//                    elStyle: { overflowY: 'scroll'}
                });
                _self.set('formContainer', form);
                return form;
            }
        }, {
            ATTRS: {
                id: { value: 'technicalPageNewUpdateFormDialog' },
                elAttrs: { value: { id: "constructRepairEditEdit" } },
                title: { value: '修改' },
                downloadUrl: { value: '/kmms/commonAction/download?path=' },
                width: { value: 650 },
                height: { value: 400 },
                contextPath: {},
                closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
                mask: { value: true },
                collectionName: {},
                userId: {}, //登录用户ID
                userName: {}, //登录用户名称
                orgId: {}, //登录用户组织机构ID
                orgName: {}, //登录用户组织机构名称
                shiftId: {},
                //            userId:{},
                success: {
                    value: function() {
                        var _self = this;
                        var formAdd = _self.getChild('TechnicalPageNewUpdateForm');
//                            delData = _self.get('delData');
                        //获取上传文件
                        //var uploadFile = _self._getUploadFileData();
                        //验证不通过
                        if (!formAdd.isValid()) {
                            return;
                        }
                        //序列化表单成对象，所有的键值都是字符串
                        var data = formAdd.serializeToObject();
                        //data.uploadFile = JSON.stringify(uploadFile);
                        data.collectionName = _self.get('collectionName');
                        data.id = _self.get('shiftId');
                        data.userId = _self.get('userId');
                        var pl = new PostLoad({
                            url: '/kmms/visitRoomRecordsAction/updateTechnical',
                            el: _self.get('el'),
                            loadMsg: '上传中...'
                        });
                        pl.load(data, function(e) {
                            if (e) {
                                _self.fire("completeAddSave", {
                                    result: e
                                });
                            }
                        });

                    }
                },
                events: {
                    value: {
                        'completeAddSave': true,
                    }
                },
            }
        });
        return TechnicalAccountPageNewUpdate;
    });