/**
 * 修改演练方案模块
 * @author xiekun
 * @date 
 */
define('kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionUpdate',
    [ 'bui/common',
      'common/form/FormContainer', 
      'bui/data',
	  'common/org/OrganizationPicker',
      'common/uploader/UpdateUploader', 
      'common/data/PostLoad',
      'bui/form',
      'kmms/technicalManagement/technicalDocument/SelectSuggest'
    ],
    function(r) {
        var BUI = r('bui/common'),
            FormContainer = r('common/form/FormContainer'),
            UpdateUploader = r('common/uploader/UpdateUploader'),
            Data = r('bui/data'),
            PostLoad = r('common/data/PostLoad'),
            OrganizationPicker = r('common/org/OrganizationPicker');
            Form = r('bui/form');
        var SelectSuggest = r("kmms/technicalManagement/technicalDocument/SelectSuggest");
        var skitsRescueCompletionUpdate = BUI.Overlay.Dialog.extend({
            initializer: function() {
                var _self = this;
                _self.addChild(_self._initFormContainer());
            },
            renderUI: function() {
                var _self = this;
    			_self._getLines();
                _self._getShowData();
            	_self._getWorkshops();//获取部门下拉选数据
    			_self._initOrganizationPicker();

            },
            bindUI: function() {
                var _self = this;
                var orgPicker=_self.get('orgPicker');
 			   /**
              * 组织机构选择
              */
              orgPicker.on('orgSelected',function (e) {
                 $('#formContainer #joinDepart').val(e.org.text);
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
            

            /**
             * 获取显示数据
             */
            _getShowData: function() {
                var _self = this,
                    shiftId = _self.get('shiftId'),
                    form = _self.get("formContainer");
//                    delData = {};
                $.ajax({
                    url: '/kmms/skitsRescueCompletionAction/findById',
                    data: { id: shiftId, collectionName: _self.get('collectionName') },
                    type: 'post',
                    async: false,
                    dataType: "json",
                    success: function(data) {
                        if (data) {
                        	_self._getWorkshops(data.orgDepart);
                        	_self._getLines(data.line);
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
            _getWorkshops:function(orgDepart){
            	var _self=this;
           	 $.ajax({
    	                url:'/kmms/userInfoManageAction/getCadreAndShop',
    	                type:'post',
    	                dataType:"json",
    	                success:function(res){
    		             $("#formContainer #orgDepart").append("<option  value=''>请选择</option>");
    		             $("#formContainer #orgDepart").append("<option  value='昆明局'>昆明局</option>");
    		             $("#formContainer #orgDepart").append("<option  value='总公司'>总公司</option>");
    	               	 for(var i=0;i<res.length;i++){
    	               		 $("#formContainer #orgDepart").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
                 		$("#formContainer #orgDepart option[value='"+orgDepart+"']").attr("selected","selected");

//    	               	 $("#formContainer #workshop").val(_self.get("orgName"));
//    	               	 var workshop = $("#formContainer #workshop").val();
//    	               	 _self._getWorkAreas(workshop);
                    }
                });
            },
            /**
             * 获取线别
             */
            _getLines: function(line) {
                var _self = this;
                $.ajax({
                    url: '/kmms/constructCooperateAction/getLines',
                    type: 'post',
                    dataType: "json",
                    success: function(res) {
    		             $("#formContainer #line").append("<option  value=''>请选择</option>");
                        for (var i = 0; i < res.length; i++) {
                            $("#formContainer #line").append("<option  value=" + res[i] + ">" + res[i] + "</option>");
                        }
                 		$("#formContainer #line option[value='"+line+"']").attr("selected","selected");
                    }
                })
            },
            /**
             * 初始化组织机构选择
             * @private
             */
            _initOrganizationPicker:function(){
                var _self=this;
                var orgPicker = new OrganizationPicker({
                    trigger : '#formContainer #joinDepart',
                    rootOrgId:_self.get('rootOrgId'),//必填项
                    rootOrgText:_self.get('rootOrgText'),//必填项
                    url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                    autoHide: true,
                    align : {
                        points:['bl','tl']
                    },
                    zIndex : '10000',
                    width:235,
                    height:250
                });
                orgPicker.render();
                _self.set('orgPicker',orgPicker);
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
					label: '时间',
					itemColspan: 1,
					item : '<input type="text" id="date" name="date" class="calendar" readonly />'
				},
				{
					label: '线别',
					itemColspan: 1,
					item : '<select type="text" id="line" name="line" readonly ></select>'
				},
				{
					label: '地点',
					itemColspan: 1,
					item : '<input type="text" id="site" name="site"  />'
				},
				{
					label: '组织部门：',
//					redStarFlag: true,
					itemColspan: 1,
					item: '<select type="text" id="orgDepart" name="orgDepart" readonly ></select>'
				},{
					label: '组织者',
					itemColspan: 1,
					item : '<input type="text" id="organizer" name="organizer"  />'
				},{
					label: '参加部门',
					itemColspan: 1,
					item : '<input type="text" id="joinDepart" name="joinDepart" readonly style="width: 235px;">'
				},{
					label: '抢险/演练内容',
					itemColspan: 2,
					item : '<textarea type="text" id="content" name="content"  />'
				},{
					label: '出动车辆（公用）数量',
					itemColspan: 1,
					item : '<input type="text" id="publicCarCount" name="publicCarCount"  />'
				},{
					label: '出动车辆（租用）数量',
					itemColspan: 1,
					item : '<input type="text" id="rentCarCount" name="rentCarCount"  />'
				},{
					label: '抢险/演练报告',
					itemColspan: 2,
					item: '<div name="uploadFile" id="uploadFile" style="height:80px;overflow-y:auto"></div>'
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
                width: {value:650},
    	        height: {value:380},
                contextPath: {},
                closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
                mask: { value: true },
                collectionName: {},
                userId: {}, //登录用户ID
                userName: {}, //登录用户名称
                orgId: {}, //登录用户组织机构ID
                orgName: {}, //登录用户组织机构名称
                shiftId: {},
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
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
                            url: '/kmms/skitsRescueCompletionAction/updateDoc',
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
        return skitsRescueCompletionUpdate;
    });