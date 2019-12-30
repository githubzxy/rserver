/**
 * 维修方案申请数据修改
 * @author yangsy
 * @date 19-1-21
 */
define('kmms/constructionManage/pointInnerMaintainPlan/communicationNetwork/communicationNetworkApplyEdit',
	[
	 	'bui/common',
	 	'bui/form',
		'bui/calendar',
		'common/form/FormContainer',
		'common/org/OrganizationPicker',
		'common/data/PostLoad',
		'common/uploader/UpdateUploader',
	],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer'),
        PostLoad = r('common/data/PostLoad'),
	    UpdateUploader = r('common/uploader/UpdateUploader');
    var communicationNetworkApplyEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._initOrganizationPicker();
			_self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            var orgPicker=_self.get('orgPicker');
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
                    text:'申请',
                    elCls : 'button',
                    handler : function(){
                        var successApply = _self.get('successApply');
                        if(successApply){
                        	successApply.call(_self);
                        }
                    }
                },{
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
		 * 初始化上传文件
		 */
		_initUploader:function(uploadFiles){
			var _self = this;
			var uploader = new UpdateUploader({
				render : '#formContainer #uploadfile',
		        url : '/zuul/kmms/atachFile/upload.cn',
		        alreadyItems : uploadFiles,
		        isSuccess : function(result){
					return true;
		        },
			});
			uploader.render();
			_self.set('uploader',uploader);
		},
        /**
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId'),delData={};
            $.ajax({
                url:'/kmms/communicationNetworkApplyAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                        $("#formContainer #project").val(data.project);
                        $('#formContainer #orgSelectName').val(data.orgSelectName);
                        $('#formContainer #orgSelectId').val(data.orgSelectId);
            		    $('#formContainer #type').val(data.type);
            		    if(data.uploadFileArr) {
            				_self._initUploader(data.uploadFileArr);
            			}
                     }
                   }
               })
        },
        /**
		 * 获取上传文件数据(上传的)
		 */
		_getUploadFileData : function(){
			var _self = this,uploader = _self.get('uploader');
			var arr = new Array(); //声明数组,存储数据发往后台
			//获取上传文件的队列
			var fileArray = uploader.getSucFiles();
			for(var i in fileArray){
		    	var dto = new _self._UploadFileDto(fileArray[i].name,fileArray[i].path); //声明对象
				arr[i] = dto; // 向集合添加对象
			};
			return arr;
		},
		/**
		  * 声明上传文件对象
		  * @param name 上传文件名
		  * @param path 上传文件路径
		  */
		_UploadFileDto : function(name,path){
			this.name = name;
			this.path = path;
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
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:493,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
            {
                label :'施工项目：',
                redStarFlag : true,
                itemColspan : 2,
                item : '<input type="text"  name="project" id="project" data-rules="{required:true}" style="width:99.5%"/>'
            },{
                label : '提交部门：',
                redStarFlag : true,
                itemColspan : 2,
                item : '<input type="text" name="orgSelectName" id="orgSelectName" readonly data-rules="{required:true}"/><input type="hidden" name="orgSelectId" id="orgSelectId"  readonly/>'
            },{
                label : '类型：',
                itemColspan : 2,
                item : '<input type="text" name="type" id="type"  style="width:99.5%" readOnly/>'
            },{
                label : '附件：',
                itemColspan : 2,
				item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
            }
            ];
            var form = new FormContainer({
                id : 'communicationNetworkApplyEditForm',
                colNum : colNum,
                formChildrens : childs
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'communicationNetworkApplyEditDialog'},
            elAttrs : {value: {id:"communicationNetworkApplyEdit"}},
            title:{value:'修改'},
            width:{value:650},
            height:{value:300},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},
            shiftId:{},
            userId:{},
            successApply:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('communicationNetworkApplyEditForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.uploadFileArr=JSON.stringify(uploadFileArr);
	        		data.collectionName=_self.get('collectionName');
	        		data.flowState="1";
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/communicationNetworkApplyAction/updateDoc',
        				el : _self.get('el'),
        				loadMsg : '上传中...'
        			}); 
        			pl.load(data, function(e){
        				if (e) {
                            _self.fire("completeAddSave",{
                                result : e
                            });
                        }
        			});
//                    var formData = new FormData(formAdd.get('el')[0]);
//                    formData.append('collectionName',_self.get('collectionName'));
//                    formData.append('flowState',"1");//流程状态（待审核）
//                    formData.append('id',_self.get('shiftId'));
//                    for (var key in delData) {
//                        formData.append('del-'+key,delData[key].join(","));
//                    }
//                    var xhr = new XMLHttpRequest();
//                    xhr.open("POST", "/zuul/kmms/communicationNetworkApplyAction/updateDoc");
//                    xhr.send(formData);
//                    xhr.onload = function (e) {
//                        if (e.target.response) {
//                            var result = JSON.parse(e.target.response);
//                            _self.fire("completeAddSave",{
//                                result : result
//                            });
//                        }
//                    }
                }
            },
            success:{
            	value : function(){
            		var _self = this;
            		var formAdd = _self.getChild('communicationNetworkApplyEditForm');
            		//获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
            		//验证不通过
            		if(!formAdd.isValid()){
            			return;
            		}
            		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.uploadFileArr=JSON.stringify(uploadFileArr);
	        		data.collectionName=_self.get('collectionName');
	        		data.flowState="0";
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/communicationNetworkApplyAction/updateDoc',
        				el : _self.get('el'),
        				loadMsg : '上传中...'
        			}); 
        			pl.load(data, function(e){
        				if (e) {
                            _self.fire("completeAddSave",{
                                result : e
                            });
                        }
        			});
//            		var formData = new FormData(formAdd.get('el')[0]);
//            		formData.append('collectionName',_self.get('collectionName'));
//            		formData.append('flowState',"0");//流程状态（草稿）
//            		formData.append('id',_self.get('shiftId'));
//            		 for (var key in delData) {
//                         formData.append('del-'+key,delData[key].join(","));
//                     }
//            		var xhr = new XMLHttpRequest();
//            		xhr.open("POST", "/zuul/kmms/communicationNetworkApplyAction/updateDoc");
//            		xhr.send(formData);
//            		xhr.onload = function (e) {
//            			if (e.target.response) {
//            				var result = JSON.parse(e.target.response);
//            				_self.fire("completeAddSave",{
//            					result : result
//            				});
//            			}
//            		}
            	}
            },
            events : {
                value : {'completeAddSave' : true,}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return communicationNetworkApplyEdit;
});