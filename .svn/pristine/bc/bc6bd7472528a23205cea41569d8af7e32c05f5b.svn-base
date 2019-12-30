/**
 * 编辑
 * @author yangli
 * @date 19-2-20
 */
define('kmms/constructionManage/constructionPlan/constructionApply/constructionApplyEdit',[
	'bui/common','common/form/FormContainer','common/data/PostLoad',
	'common/uploader/UpdateUploader',
   'bui/form'],function(r){
    var BUI = r('bui/common'),
        FormContainer= r('common/form/FormContainer'),
	    PostLoad = r('common/data/PostLoad'),
		UpdateUploader = r('common/uploader/UpdateUploader');
    var ConstructionApplyEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
          var _self=this;
            _self._getShowData();
        },
        bindUI : function(){
            var _self = this;
            
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
                },
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
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId');
            $.ajax({
                url:'/kmms/commonAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(res){
                    var data = res.data;
                    if(data){
                        $("#formContainer #name").val(data.name);
                        $("#formContainer #depart").val(data.depart);
                        if(data.uploadFileArr) {
            				_self._initUploader(data.uploadFileArr);
            			}
                    }
                }
            })
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
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                    label : '施工项目：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99%" >'
                },{
                    label : '提交部门：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="depart" id="depart" readonly data-rules="{required:true}"/>'
                },{
                    label : '附件：',
                    itemColspan : 2,
                    item : '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
                }
            ];
            var form = new FormContainer({
                id : 'constructionApplyEditForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'constructionApplyEditDialog'},
            elAttrs : {value: {id:"constructionApplyEdit"}},
            title:{value:'编辑'},
            width:{value:610},
            height:{value:265},
            contextPath : {},
            downloadUrl:{value:'/kmms/commonAction/download?path='},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            parentId:{},
            successApply:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('constructionApplyEditForm'),delData=_self.get('delData');
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
	        		data.orgId=_self.get('orgId');
	        		data.orgName=_self.get('orgName');
	        		data.parentId=_self.get('parentId');
	        		data.userId=_self.get('userId');
	        		data.userName=_self.get('userName');
	        		data.flowState="1";
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/constructApplyAction/updateDoc',
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
//                    formData.append('flowState',"1");//流程状态（草稿）
//                    formData.append('orgId',_self.get('orgId'));//用于该页面过滤查询数据（登陆用户的组织机构与申情人的组织机构相同）
//                    formData.append('orgName',_self.get('orgName'));
//                    formData.append('parentId',_self.get('parentId'));//用于审核页面过滤查询数据（登陆用户的组织机构与申情人的上级组织机构相同）
//                    formData.append('userId',_self.get('userId'));
//                    formData.append('userName',_self.get('userName'));
//                    formData.append('id',_self.get('shiftId'));
//                    for (var key in delData) {
//                        formData.append('del-'+key,delData[key].join(","));
//                    }
//                    var xhr = new XMLHttpRequest();
//                    xhr.open("POST", "/zuul/kmms/constructApplyAction/updateDoc");
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
                    var formAdd = _self.getChild('constructionApplyEditForm'),delData=_self.get('delData');
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
	        		data.orgId=_self.get('orgId');
	        		data.orgName=_self.get('orgName');
	        		data.parentId=_self.get('parentId');
	        		data.userId=_self.get('userId');
	        		data.userName=_self.get('userName');
	        		data.flowState="0";
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/constructApplyAction/updateDoc',
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
                    

                }
            },
            events : {
                value : {
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeAddSave' : true,

                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'}
        }
    });
    return ConstructionApplyEdit;
});