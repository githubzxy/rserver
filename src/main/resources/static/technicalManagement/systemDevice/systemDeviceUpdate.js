/**
 * 系统线路修改
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/technicalManagement/systemDevice/systemDeviceUpdate',
    ['bui/common', 'common/form/FormContainer', 'bui/data',
        'common/uploader/UpdateUploader', 'common/data/PostLoad', 'bui/form',
    ],
    function(r) {
        var BUI = r('bui/common'),
            FormContainer = r('common/form/FormContainer'),
            UpdateUploader = r('common/uploader/UpdateUploader'),
            Data = r('bui/data'),
            PostLoad = r('common/data/PostLoad'),
            Form = r('bui/form');
        var systemDeviceNewUpdate = BUI.Overlay.Dialog.extend({
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
             * 获取显示数据
             */
            _getShowData: function() {
                var _self = this,
                    shiftId = _self.get('shiftId'),
                    form = _self.get("formContainer");
//                    delData = {};
                $.ajax({
                    url: '/kmms/systemDeviceAction/findById',
                    data: { id: shiftId},
                    type: 'post',
                    async: false,
                    dataType: "json",
                    success: function(data) {
                        if (data) {
                            $("#formContainer #system").val(data.system);
                            $("#formContainer #deviceType").val(data.deviceType);
                            $("#formContainer #remark").val(data.remark);
                        }
                    }
                });
            },
            /**
             * 初始化FormContainer
             */
            _initFormContainer: function() {
                var _self = this;
                var colNum = 2;
                var childs = [{
                    label: '系统：',
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<input type="text" name="system" id="system" data-rules="{required:true,maxlength:40}" style="width:99.5%;height:30px">'
                }, {
                    label: '设备：',
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<input type="text" name="deviceType" id="deviceType" data-rules="{required:true,maxlength:40}" style="width:99.5%;height:30px"/>' 
                }, {
                    label: '备注：',
                    itemColspan: 2,
                    item: '<textarea name="remark" id="remark" style="width:99.5%;height:100px;" maxlength="250" placeholder="最多输入250字"/>'
                }];
                var form = new FormContainer({
                    id: 'TechnicalPageNewUpdateForm',
                    colNum: colNum,
                    formChildrens: childs,
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
                height: { value: 280 },
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
//                        data.uploadFile = JSON.stringify(uploadFile);
//                        data.collectionName = _self.get('collectionName');
                        data.id = _self.get('shiftId');
//                        data.userId = _self.get('userId');
                        var pl = new PostLoad({
                            url: '/kmms/systemDeviceAction/updateInfo',
                            el: _self.get('el'),
                            loadMsg: '上传中...'
                        });
                        pl.load(data, function(e) {
                        	if(e.status==2){
            					//消息提示框
            					BUI.Message.Confirm(e.msg,null,'error');
            				}else if (e) {
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
        return systemDeviceNewUpdate;
    });