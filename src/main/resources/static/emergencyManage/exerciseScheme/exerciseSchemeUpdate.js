/**
 * 修改演练方案模块
 * @author xiekun
 * @date 
 */
define('kmms/emergencyManage/exerciseScheme/exerciseSchemeUpdate',
    ['bui/common', 'common/form/FormContainer', 'bui/data',
        'common/uploader/UpdateUploader', 'common/data/PostLoad', 'bui/form',
        'kmms/technicalManagement/technicalDocument/SelectSuggest'
    ],
    function(r) {
        var BUI = r('bui/common'),
            FormContainer = r('common/form/FormContainer'),
            UpdateUploader = r('common/uploader/UpdateUploader'),
            Data = r('bui/data'),
            PostLoad = r('common/data/PostLoad'),
            Form = r('bui/form');
        var SelectSuggest = r("kmms/technicalManagement/technicalDocument/SelectSuggest");
        var ExerciseSchemeUpdate = BUI.Overlay.Dialog.extend({
            initializer: function() {
                var _self = this;
                _self.addChild(_self._initFormContainer());
            },
            renderUI: function() {
                var _self = this;
                _self._getShowData();
            	_self._getWorkshops();//获取部门下拉选数据

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
            

            /**
             * 获取显示数据
             */
            _getShowData: function() {
                var _self = this,
                    shiftId = _self.get('shiftId'),
                    form = _self.get("formContainer");
//                    delData = {};
                $.ajax({
                    url: '/kmms/exerciseSchemeAction/findById',
                    data: { id: shiftId, collectionName: _self.get('collectionName') },
                    type: 'post',
                    async: false,
                    dataType: "json",
                    success: function(data) {
                        if (data) {
                        	_self._getWorkshops(data.depart);
                            $("#formContainer #depart").val(data.depart);
                            if (data.uploadFile) {
                                _self._initUploader(data.uploadFile);
                            }
                        }
                    }
                });
//                _self.set('delData', delData);
            },
            /**
             * 获取科室和车间
             */
            _getWorkshops:function(depart){
            	var _self=this;
           	 $.ajax({
    	                url:'/kmms/userInfoManageAction/getCadreAndShop',
    	                type:'post',
    	                dataType:"json",
    	                success:function(res){
    		             $("#formContainer #depart").append("<option  value=''>请选择</option>");
    	               	 for(var i=0;i<res.length;i++){
    	               		 $("#formContainer #depart").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
                 		$("#formContainer #depart option[value='"+depart+"']").attr("selected","selected");

//    	               	 $("#formContainer #workshop").val(_self.get("orgName"));
//    	               	 var workshop = $("#formContainer #workshop").val();
//    	               	 _self._getWorkAreas(workshop);
                    }
                });
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
                var childs = [{
                    label: '所属部门：',
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<select type="text" id="depart" name="depart" readonly style="width: 384px;"></select>'
                },{
                    label: '附件：',
                    itemColspan: 2,
                    item: '<div name="uploadFile" id="uploadFile"  style="height:100px;overflow-y:auto"/>'
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
                width: { value: 500 },
                height: { value: 230 },
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
                        var uploadFile = _self._getUploadFileData();
                        //验证不通过
                        if (!formAdd.isValid()) {
                            return;
                        }
                        //序列化表单成对象，所有的键值都是字符串
                        var data = formAdd.serializeToObject();
                        data.uploadFile = JSON.stringify(uploadFile);
                        data.collectionName = _self.get('collectionName');
                        data.id = _self.get('shiftId');
                        data.userId = _self.get('userId');
                        var pl = new PostLoad({
                            url: '/kmms/exerciseSchemeAction/updateDoc',
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
        return ExerciseSchemeUpdate;
    });