/**
 * 新增
 * @author yangli
 * @date 19-1-14
 */
/**
 * 修改文件上传
 * @author zhouxingyu
 * @date 19-3-21
 */
define('kmms/dayToJobManagement/constructRepair/ConstructRepairAdd',
    ['bui/common', 'common/form/FormContainer', 'bui/select', 'bui/data',
        'common/data/PostLoad', 'bui/calendar', 'bui/form', 'common/uploader/UpdateUploader'
    ],
    function(r) {
        var BUI = r('bui/common'),
            Calendar = r('bui/calendar'),
            Select = r('bui/select'),
            Data = r('bui/data'),
            PostLoad = r('common/data/PostLoad'),
            FormContainer = r('common/form/FormContainer');
        UpdateUploader = r('common/uploader/UpdateUploader');
        var ConstructRepairAdd = BUI.Overlay.Dialog.extend({
            initializer: function() {
                var _self = this;
                _self.addChild(_self._initFormContainer());
            },
            renderUI: function() {
                var _self = this;
                _self._initDate();
                _self._getWorkShop();
                _self._getLines();
                _self._initPlanDate();
                _self._initUploader();
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

                //工区下拉选选项根据车间而变化
                $("#workShop").on('change', function() {
                    $("#workArea").empty();
                    var orgName = $("#workShop").val();
                    _self._getDepart(orgName);

                });
                /**
                 * 等级下拉选选项和维修类型根据项目而变化
                 */
                $("#formContainer #project").on('change', function() {
                    var project = $("#formContainer #project").val();
                    _self._initGrade(project);
                    _self._initRepairType(project);

                });
                /**
                 * 申请时间的计算(开始)
                 */
                $("#formContainer #planAgreeTimeStart").on('change', function() {
                    _self._applyTime();
                    _self._timeCash();
                });
                /**
                 * 申请时间的计算（截止）
                 */
                $("#formContainer #planAgreeTimeEnd").on('change', function() {
                    _self._applyTime();
                    _self._timeCash();
                });
                /**
                 * 实际给点时间的计算
                 */
                $("#formContainer #actAgreeTime").on('change', function() {
                    _self._actrTime();
                    _self._timeCash();
                });
                /**
                 * 实际完成时间的计算
                 */
                $("#formContainer #actOverTime").on('change', function() {
                    _self._actrTime();
                    _self._timeCash();
                });


            },
            /**
             * 时间兑现率计算
             */
            _timeCash: function() {
                var applyMinute = $("#formContainer #applyMinute").val();
                var actMinute = $("#formContainer #actMinute").val();
                if (applyMinute == 0) {
                    return;
                }
                if (applyMinute != '' && actMinute != '') {
                    var v = Math.pow(10, 2);
                    var minutes = Math.round(actMinute / applyMinute * 100 * v) / v;
                    var minutesStr = minutes + '%';
                    $("#timeCash").val(minutesStr);

                }
            },
            /**
             * 申请时间计算
             */
            _applyTime: function() {
                var planAgreeTimeStart = $("#formContainer #planAgreeTimeStart").val();
                var planAgreeTimeEnd = $("#formContainer #planAgreeTimeEnd").val();
                if (planAgreeTimeStart != '' && planAgreeTimeEnd != '') {
                    startTime = Date.parse(new Date(planAgreeTimeStart));
                    endTime = Date.parse(new Date(planAgreeTimeEnd));
                    var tamp = endTime - startTime;
                    var minutes = tamp / 60000;
                    $("#applyMinute").val(minutes);

                }
            },
            /**
             * 实际批准时间
             */
            _actrTime: function() {
                var actAgreeTime = $("#formContainer #actAgreeTime").val();
                var actOverTime = $("#formContainer #actOverTime").val();
                if (actAgreeTime != '' && actOverTime != '') {
                    startTime = Date.parse(new Date(actAgreeTime));
                    endTime = Date.parse(new Date(actOverTime));
                    var tamp = endTime - startTime;
                    var minutes = tamp / 60000;
                    $("#actMinute").val(minutes);

                }
            },
            /**
             * 初始化等级下拉选
             */
            _initGrade: function(project) {
                $("#formContainer #grade").empty();
                $("#formContainer #grade").append("<option value='Ⅰ'>Ⅰ</option>");
                $("#formContainer #grade").append("<option value='Ⅱ'>Ⅱ</option>");
                if (project == '施工') {
                    $("#formContainer #grade").append("<option value='Ⅲ'>Ⅲ</option>");
                    $("#formContainer #grade").append("<option value='ⅢA'>ⅢA</option>");
                    $("#formContainer #grade").append("<option value='ⅢB'>ⅢB</option>");
                    $("#formContainer #grade").append("<option value='ⅢC'>ⅢC</option>");
                }
            },
            /**
             * 初始化维修类型下拉选
             */
            _initRepairType: function(project) {
                $("#formContainer #repairType").empty();
                if (project == '施工') {
                    $("#formContainer #repairType").attr("disabled", "disabled");
                } else {
                    $("#formContainer #repairType").removeAttr("disabled");
                    $("#formContainer #repairType").append("<option value='垂直'>垂直</option>");
                    $("#formContainer #repairType").append("<option value='V型'>V型</option>");
                    $("#formContainer #repairType").append("<option value='协议修'>协议修</option>");
                }
            },
            /**
             * 计划给点时间
             */
            _initPlanDate: function() {
                var _self = this;
                var planAgreeTimeStart = new Calendar.DatePicker({ //加载日历控件
                    trigger: '#planAgreeTimeStart',
                    showTime: true,
                    autoRender: true,
                    textField: '#planAgreeTimeStart'
                });
                var planAgreeTimeEnd = new Calendar.DatePicker({ //加载日历控件
                    trigger: '#planAgreeTimeEnd',
                    showTime: true,
                    autoRender: true,
                    textField: '#planAgreeTimeEnd'
                });
                var actAgreeTime = new Calendar.DatePicker({ //加载日历控件
                    trigger: '#actAgreeTime',
                    showTime: true,
                    autoRender: true,
                    textField: '#actAgreeTime'
                });
                var actOverTime = new Calendar.DatePicker({ //加载日历控件
                    trigger: '#actOverTime',
                    showTime: true,
                    autoRender: true,
                    textField: '#actOverTime'
                });
                _self.set('planAgreeTimeStart', planAgreeTimeStart);
                _self.set('planAgreeTimeEnd', planAgreeTimeEnd);
                _self.set('actAgreeTime', planAgreeTimeEnd);
                _self.set('actOverTime', planAgreeTimeEnd);
            },

            /**
             * 初始化时间查询
             * @private
             */
            _initDate: function() {
                var _self = this;
                var date = new Calendar.DatePicker({ //加载日历控件
                    trigger: '#date',
                    showTime: false,
                    autoRender: true,
                    textField: '#date'
                });
                _self.set('date', date);

                var postLoad = new PostLoad({
                    url: '/kmms/networkManageInfoAction/getSystemDateToYearMonthDay',
                    el: _self.get('el'),
                    loadMsg: '加载中...'
                });
                postLoad.load({}, function(date) {
                    if (date) {
                        $('#formContainer #date').val(date);
                    }
                });
            },
            /**
             * 获取线别
             */
            _getLines: function() {
                var _self = this;
                $.ajax({
                    url: '/kmms/constructCooperateAction/getLines',
                    //                data:{workShopName : orgName},
                    type: 'post',
                    dataType: "json",
                    success: function(res) {
                        for (var i = 0; i < res.length; i++) {
                            $("#line").append("<option  value=" + res[i] + ">" + res[i] + "</option>");
                        }
                    }
                })
            },
            /**
             * 获取工区下拉选数据
             */
            _getDepart: function(orgName) {
                var _self = this;
                $.ajax({
                    url: '/kmms/constructCooperateAction/getDepart',
                    data: { workShopName: orgName },
                    type: 'post',
                    dataType: "json",
                    success: function(res) {
                        for (var i = 0; i < res.length; i++) {
                            $("#workArea").append("<option  value=" + res[i].orgName + ">" + res[i].orgName + "</option>");
                        }
                    }
                })
            },
            /**
             * 获取车间下拉选数据
             */
            _getWorkShop: function() {
                var _self = this;
                $.ajax({
                    url: '/kmms/constructCooperateAction/getWorkShop',
                    //                 data:{id : shiftId,collectionName:_self.get('collectionName')},
                    type: 'post',
                    dataType: "json",
                    success: function(res) {
                        for (var i = 0; i < res.length; i++) {
                            $("#workShop").append("<option  value=" + res[i].orgName + ">" + res[i].orgName + "</option>");
                        }
                        var orgName = $("#workShop").val();
                        _self._getDepart(orgName);
                    }
                })
            },
            /**
             * 初始化上传文件
             */
            _initUploader: function() {
                var _self = this;
                //上传附件
                var uploader = new UpdateUploader({
                    render: '#formContainer #uploadfile',
                    url: '/zuul/kmms/atachFile/upload.cn',
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
                var fileArray = uploader.getSucFiles();
                for (var i in fileArray) {
                    var dto = new _self.UploadFileDto(fileArray[i].name, fileArray[i].path); //声明对象
                    arr[i] = dto; // 向集合添加对象
                };
                return arr;
            },
            /**
             * 声明上传文件对象
             * @param name 上传文件名
             * @param path 上传文件路径
             */
            UploadFileDto: function(name, path) {
                this.name = name;
                this.path = path;
            },
            /**
             * 初始化FormContainer
             */
            _initFormContainer: function() {
                var _self = this;
                var colNum = 2;
                var childs = [{
                    label: '日期：',
                    //                    redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text"  name="date" id="date" class="calendar"   readonly />'
                }, {
                    label: '项目：',
                    itemColspan: 1,
                    item: '<select  name="project" id="project" >' +
                        '<option value="施工">施工</option>' +
                        '<option value="维修">维修</option>' +
                        '<option value="骨干网计划">骨干网计划</option>' +
                        '<option value="临时修">临时修</option>' +
                        '<option value="紧急修">紧急修</option>' +
                        '<option value="一体化">一体化</option>' +
                        '</select>'
                }, {
                    label: '等级：',
                    //                    redStarFlag : true,
                    itemColspan: 1,
                    item: '<select name="grade" id="grade"  >' +
                        '<option value="Ⅰ">Ⅰ</option>' +
                        '<option value="Ⅱ">Ⅱ</option>' +
                        '<option value="Ⅲ">Ⅲ</option>' +
                        '<option value="ⅢA">ⅢA</option>' +
                        '<option value="ⅢB">ⅢB</option>' +
                        '<option value="ⅢC">ⅢC</option>' +
                        '</select>'
                }, {
                    label: '维修类型：',
                    //                    redStarFlag : true,
                    itemColspan: 1,
                    item: '<select  name="repairType" id="repairType" disabled="disabled">' +
                        '</select>'
                }, {
                    label: '线别：',
                    //                  redStarFlag : true,
                    itemColspan: 1,
                    item: '<select  name="line" id="line" ></select>'
                }, {
                    label: '计划号：',
                    //                redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text" name="planNum" id="planNum" />'
                }, {
                    label: '车间：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<select  name="workShop" id="workShop"></select>'
                }, {
                    label: '工区：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<select name="workArea" id="workArea" ></select>'
                }, {
                    label: '计划批准时间（开始）：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text" name="planAgreeTimeStart" id="planAgreeTimeStart" class="calendar" readonly/>'
                }, {
                    label: '计划批准时间（结束）：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text" name="planAgreeTimeEnd" id="planAgreeTimeEnd" class="calendar" readonly/>'
                }, {
                    label: '申请时间（分钟）：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text" name="applyMinute" id="applyMinute" readonly/>'
                }, {
                    label: '实际给点时间：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text" name="actAgreeTime" id="actAgreeTime" class="calendar"   readonly/>'
                }, {
                    label: '实际完成时间：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text" name="actOverTime" id="actOverTime" class="calendar"   readonly/>'
                }, {
                    label: '实际批准时间（分钟）：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text" name="actMinute" id="actMinute" readonly/>'
                }, {
                    label: '时间兑现率：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text" name="timeCash" id="timeCash" readonly/>'
                }, {
                    label: '作业人数：',
                    //              redStarFlag : true,
                    itemColspan: 1,
                    item: '<input type="text" name="totalMan" id="totalMan" />'
                }, {
                    label: '机关把关干部：',
                    //              redStarFlag : true,
                    itemColspan: 2,
                    item: '<input type="text" name="checkLeader" id="checkLeader" />'
                }, {
                    label: '施工内容：',
                    //              redStarFlag : true,
                    itemColspan: 2,
                    item: '<textarea type="text" name="constructContent" id="constructContent" />'
                }, {
                    label: '备注：',
                    //              redStarFlag : true,
                    itemColspan: 2,
                    item: '<textarea type="text" name="remark" id="remark" />'
                }, {
                    label: '上传：',
                    //            redStarFlag : true,
                    itemColspan: 2,
                    item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
                    /*item : '<input name="uploadfile" id="uploadfile" style="height:70px;overflow-y :auto" type="file" multiple />'*/
                }];
                var form = new FormContainer({
                    id: 'constructRepairAddForm',
                    colNum: colNum,
                    formChildrens: childs,
                    elStyle:{overflowY:'scroll',height:'400px'}
                });
                _self.set('formContainer', form);
                return form;
            }
        }, {
            ATTRS: {
                elAttrs: { value: { id: "constructRepairAdd" } },
                elCls: { vale: 'constructRepairAdd_Dialog' },
                title: { value: '新增' },
                width: { value: 640 },
                height: { value: 500 },
                contextPath: {},
                closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
                mask: { value: true },
                collectionName: {},
                userId: {}, //登录用户ID
                userName: {}, //登录用户名称
                orgId: {}, //登录用户组织机构ID
                orgName: {}, //登录用户组织机构名称
                success: {
                    value: function() {
                        var _self = this;
                        var formAdd = _self.getChild('constructRepairAddForm');
                        //获取上传文件
                        var uploadfile = _self._getUploadFileData();
                        //验证不通过
                        if (!formAdd.isValid()) {
                            return;
                        }
                        var data = formAdd.serializeToObject();
                        data.uploadfile = JSON.stringify(uploadfile);
                        data.collectionName = _self.get('collectionName');
                        var pl = new PostLoad({
                            url: '/zuul/kmms/constructRepairAction/addData',
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
                        /**
                         * 绑定保存按钮事件
                         */
                        'completeAddSave': true,

                    }
                },
            }
        });
        return ConstructRepairAdd;
    });