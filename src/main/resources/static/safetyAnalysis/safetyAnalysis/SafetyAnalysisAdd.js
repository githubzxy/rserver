/**
 * 新增
 * @author zhouxy
 * @date 19-7-17
 */
define('kmms/safetyAnalysis/safetyAnalysis/SafetyAnalysisAdd',['bui/common','common/form/FormContainer',
    'bui/form','common/org/OrganizationPicker','common/uploader/UpdateUploader'],function(r){
    var BUI = r('bui/common'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	UpdateUploader = r('common/uploader/UpdateUploader'),
        FormContainer= r('common/form/FormContainer');
    var SafetyAnalysisAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
            var _self=this;
            _self._initOrganizationPicker();
            _self._initUploader();
            $('#formContainer #orgSelectName').val(_self.get('orgName'));
		    $('#formContainer #orgSelectId').val(_self.get('orgId'));
        },
        bindUI : function(){
        	var _self = this,orgPicker=_self.get('orgPicker');
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #orgSelectName').val(e.org.text);
    		    $('#formContainer #orgSelectId').val(e.org.id);
            });

            //定义按键
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(){
                        var success = _self.get('success');
                        if(success){
                            success.call(_self);
                        }
                    }
                },{
                    text:'关闭',
                    elCls : 'button',
                    handler : function(){
                        if(this.onCancel() !== false){
                            this.close();
                        }
                    }
                }
            ];
            _self.set('buttons',buttons);
        },

        /**
         * 初始化组织机构选择
         * @private
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #orgSelectName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getShopAndDepart',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '1200',
                width:482,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
        },
        
        /**
		 * 初始化上传文件
		 */
		_initUploader:function(){
			var _self = this;
			//上传附件
			var uploader = new UpdateUploader({
				render : '#formContainer #uploadfile',
		        url : '/zuul/kmms/atachFile/upload.cn',
		        isSuccess : function(result){
					return true;
		        },
			});
			uploader.render();
			_self.set('uploader',uploader);
		},
		
		/**
		 * 获取上传文件数据(上传的)
		 */
		_getUploadFileData:function(){
			var _self = this,uploader = _self.get('uploader');
			var arr = new Array(); //声明数组,存储数据发往后台
			//获取上传文件的队列
			var fileArray = uploader.getSucFiles();
			for(var i in fileArray){
		    	var dto = new _self.UploadFileDto(fileArray[i].name,fileArray[i].path); //声明对象
				arr[i] = dto; // 向集合添加对象
			};
			return arr;
		},
		
		/**
		  * 声明上传文件对象
		  * @param name 上传文件名
		  * @param path 上传文件路径
		  */
		UploadFileDto: function(name,path){
			this.name = name;
			this.path = path;
		},
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '年月份：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99.5%">'
                }, 
                {
                    label: '召开时间：',
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<input type="text"  name="checkDate" id="checkDate" style="width:99.5%" class="calendar calendar-time" data-rules="{required:true}"  placeholder="每月23日前录入"/>',
                }, 
                {
                    label : '所属部门：',
                    itemColspan : 2,
                    item : '<input type="text" name="orgSelectName" id="orgSelectName"  style="width:99.5%" readonly data-rules="{required:true}"/><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
                },{
                    label : '附件：',
                    itemColspan : 2,
//                    item : '<input name="uploadfile" id="uploadfile" style="height:70px;overflow-y :auto" type="file" multiple accept=".doc,.docx,.xls,.xlsx,.ppt,.pptx"/>'
                    item : '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
                }
            ];
            var form = new FormContainer({
                id : 'safetyAnalysisAddForm',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'safetyAnalysisAddDialog'},
            elAttrs : {value: {id:"safetyAnalysisAdd"}},
            title:{value:'新增'},
            width:{value:600},
            height:{value:300},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            orgId:{},
            orgName:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('safetyAnalysisAddForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('uploadFileArr',JSON.stringify(uploadFileArr));
                    formData.append('userId',_self.get('userId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/safetyAnalysisAction/addDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSave",{
                                result : result
                            });
                        }
                    }

                }
            },
            events : {
                value : {
                    'completeAddSave' : true,
                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'}
        }
    });
    return SafetyAnalysisAdd;
});