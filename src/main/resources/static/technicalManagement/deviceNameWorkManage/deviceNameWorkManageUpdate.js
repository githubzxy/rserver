/**
 * 修改机房账号模块
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/technicalManagement/deviceNameWorkManage/deviceNameWorkManageUpdate',
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
        var deviceNameWorkManagePageNewUpdate = BUI.Overlay.Dialog.extend({
            initializer: function() {
                var _self = this;
                _self.addChild(_self._initFormContainer());
            },
            renderUI: function() {
                var _self = this;
                _self._initYearOrMonth();
                _self._getShowData();
//                _self._initRailAndRoom();
               
            },
            bindUI: function() {
                var _self = this;
              //标识下拉选触发
                $("#yearOrMonth").on('change',function() {
                    var yearOrMonth = $("#yearOrMonth").val();
                    $("#type").empty();
                    _self._initType(yearOrMonth);

                });
                //类别下拉触发
                $("#type").on('change',function() {
                    var type = $("#type").val();
                    _self._initCountYear(type);

                });
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
            _initCountYear:function(type){
            	if(type=='检修（月）'){
            		$("#countYear").val("12");
            	}else if(type=='检修（双月）'){
            		$("#countYear").val("6");
            	}else if(type=='检修（季度）'){
            		$("#countYear").val("4");
            	}else if(type=='检修（半年）'){
            		$("#countYear").val("2");
            	}else if(type=='检修（年）'){
            		$("#countYear").val("1");
            	}else if(type=='整修'){
            		$("#countYear").val("根据需要");
            	}else if(type=='网管巡视（日）'){
            		$("#countYear").val("31");
            	}else if(type=='日常检修（周）'){
            		$("#countYear").val("4");
            	}else if(type=='日常检修（月）'){
            		$("#countYear").val("1");
            	}
            },
            _initType:function(yearOrMonth){
            	if(yearOrMonth=='year'){
            		$("#type").append("<option  value=''>请选择</option>");
            		$("#type").append("<option  value='检修（月）'>检修（月）</option>");
            		$("#type").append("<option  value='检修（双月）'>检修（双月）</option>");
            		$("#type").append("<option  value='检修（季度）'>检修（季度）</option>");
            		$("#type").append("<option  value='检修（半年）'>检修（半年）</option>");
            		$("#type").append("<option  value='检修（年）'>检修（年）</option>");
            		$("#type").append("<option  value='整修'>整修</option>");
            		return;
            	}
            	if(yearOrMonth=='month'){
            		$("#type").append("<option  value=''>请选择</option>");
            		$("#type").append("<option  value='网管巡视（日）'>网管巡视（日）</option>");
            		$("#type").append("<option  value='日常检修（周）'>日常检修（周）</option>");
            		$("#type").append("<option  value='日常检修（月）'>日常检修（月）</option>");
            		return;
            	}
            },
            _initYearOrMonth:function(){
            	$("#yearOrMonth").append("<option  value=''>请选择</option>");
            	$("#yearOrMonth").append("<option  value='year'>年表</option>");
            	$("#yearOrMonth").append("<option  value='month'>月表</option>");
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
//                    url: '/kmms/deviceNameWorkManageAction/getMachineRooms',
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
                    url: '/kmms/deviceNameWorkManageAction/findById',
                    data: { id: shiftId, collectionName: _self.get('collectionName') },
                    type: 'post',
                    async: false,
                    dataType: "json",
                    success: function(r) {
                        if (r.data) {
                            $("#formContainer #deviceName").val(r.data.deviceName);
                            $("#formContainer #workContent").val(r.data.workContent);
                            $("#formContainer #remark").val(r.data.remark);
                            $("#formContainer #unit").val(r.data.unit);
                            _self._initType(r.data.yearOrMonth);
                            $("#formContainer #type").val(r.data.type);
                            $("#formContainer #countYear").val(r.data.countYear);
                            $("#formContainer #yearOrMonth").val(r.data.yearOrMonth);
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
    					label: '设备名称：',
    					redStarFlag: true,
    					itemColspan: 2,
    					item: '<input type="text" name="deviceName" id="deviceName" style="width:99.5%;" data-rules="{required:true,maxlength:200}"/>'
    				},{
    					label: '工作内容：',
    					redStarFlag: true,
    					itemColspan: 2,
    					item : '<textarea id="workContent" name="workContent" style="width:99.5%;height:80px;overflow-y:auto" data-rules="{required:true}"/>'
    				},{
    					label: '标识：',
    					redStarFlag: true,
    					itemColspan: 1,
    					item : '<select id="yearOrMonth" name="yearOrMonth" style="width:99.5%;" data-rules="{required:true}"/>'
    				},{
    					label: '类别：',
    					redStarFlag: true,
    					itemColspan: 1,
    					item : '<select id="type" name="type" style="width:99.5%;" data-rules="{required:true}"/>'
    				},{
    					label: '周期：',
    					itemColspan: 1,
    					item : '<input type="text" id="countYear" name="countYear" />'
    				},{
    					label: '单位：',
    					itemColspan: 1,
    					item : '<input type="text" id="unit" name="unit" />'
    				},{
    					label: '备注：',
    					itemColspan : 2,
    					item:'<textarea name="remark" id="remark" style="width:99.5%;height:100px;overflow-y:auto" maxlength="200" placeholder="最多输入200字"/>'
    				}];
                var form = new FormContainer({
                    id: 'deviceNameWorkManageForm',
                    colNum: colNum,
                    formChildrens: childs,
//                    elStyle: { overflowY: 'scroll'}
                });
                _self.set('formContainer', form);
                return form;
            }
        }, {
            ATTRS: {
                id: { value: 'deviceNameWorkManageFormDialog' },
                elAttrs: { value: { id: "deviceNameWorkManageUpdate" } },
                title: { value: '修改' },
                width: { value: 650 },
                height: { value: 380 },
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
                        var formAdd = _self.getChild('deviceNameWorkManageForm');
//                            delData = _self.get('delData');
                        //获取上传文件
                       // var uploadFile = _self._getUploadFileData();
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
                            url: '/kmms/deviceNameWorkManageAction/modifyDoc',
                            el: _self.get('el'),
                            loadMsg: '修改中...'
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
        return deviceNameWorkManagePageNewUpdate;
    });