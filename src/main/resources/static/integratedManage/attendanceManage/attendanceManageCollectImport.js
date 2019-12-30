/**
 * 导入汇总表
 * @author yangsy
 * @date 19-2-21
 */
define('kmms/integratedManage/attendanceManage/attendanceManageCollectImport',
		[
			'bui/common',
			'bui/form',
			'common/form/FormContainer',
			'common/org/OrganizationPicker',
			'common/data/PostLoad',
			'common/uploader/UpdateUploader',
		],function(r){
    var BUI = r('bui/common'),
    	FormContainer = r('common/form/FormContainer'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	PostLoad = r('common/data/PostLoad'),
    	UpdateUploader = r('common/uploader/UpdateUploader');
    var attendanceManageCollectImport = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initUploader();
//        	_self._initOrganizationPicker();
        	$('#formContainer #submitOrgName').val(_self.get('orgName'));
		    $('#formContainer #submitOrgId').val(_self.get('orgId'));
        },
        bindUI : function(){
            var _self = this;
//            var orgPicker=_self.get('orgPicker');
//            /**
//             * 组织机构选择
//             */
//            orgPicker.on('orgSelected',function (e) {
//                $('#formContainer #submitOrgName').val(e.org.text);
//    		    $('#formContainer #submitOrgId').val(e.org.id);
//            });
            //定义按键
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(){
                        var save = _self.get('save');
                        if(save){
                        	save.call(_self);
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
		
//		_getUploadFileData : function(form,uploadfile){
//			var _self = this,uploader = form.get(uploadfile);
//			var arr = new Array();
//			// 获取上传文件的对列
//			var fileArray = uploader.getSucFiles();
//			for(var i in fileArray){
//		 		var dto = {name : fileArray[i].name,path : fileArray[i].path};
//		 		arr.push(dto);
//			};
//			return arr;
//		},
		
		/**
		 * 获取上传文件数据(上传的)
		 */
		_getUploadFileData:function(){
			var _self = this,uploader = _self.get('uploader');
			var arr = new Array(); //声明数组,存储数据发往后台
			//获取上传文件的队列
//			var fileArray = uploader.get("queue").getItemsByStatus('success');
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

//        /**
//         * 初始化组织机构选择
//         * @private
//         */
//        _initOrganizationPicker:function(){
//            var _self=this;
//            var orgPicker = new OrganizationPicker({
//                trigger : '#formContainer #submitOrgName',
//                rootOrgId:_self.get('rootOrgId'),//必填项
//                rootOrgText:_self.get('rootOrgText'),//必填项
//                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
//                autoHide: true,
//                align : {
//                    points:['bl','tl']
//                },
//                zIndex : '10000',
//                width:493,
//                height:200
//            });
//            orgPicker.render();
//            _self.set('orgPicker',orgPicker);
//        },
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
					label: '附件：',
					itemColspan: 2,
					item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
				},
            ];
            var form = new FormContainer({
                id : 'attendanceManageCollectImportForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'attendanceManageCollectImportDialog'},
            elAttrs : {value : {id:"attendanceManageCollectImport"}},
            title:{value:'导入考勤表'},
            width:{value:600},
            height:{value:210},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            id:{},
            userId:{},//登录用户ID（工区用户）
            userName:{},//登录用户名称（工区用户）
            orgId:{},//登录用户所属机构ID
            orgName:{},//登录用户所属机构名称
            parentId:{},//登录用户所属机构上级机构ID
            save:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('attendanceManageCollectImportForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
            		console.log(uploadFileArr[0].path);
            		if(uploadFileArr.length!=1){
            			return;
            		}
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.uploadFileArr=JSON.stringify(uploadFileArr);
	        		data.filePath=uploadFileArr[0].path;
	        		data.id=_self.get('id');
	        		data.collectionName=_self.get('collectionName');
//                    var formData = new FormData(formAdd.get('el')[0]);
//                    formData.append('collectionName',_self.get('collectionName'));
//                    formData.append('userId',_self.get('userId'));
                    console.log(data);
                    var pl = new PostLoad({
        				url : '/zuul/kmms/attendanceManageCollectAction/Import',
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
//                    var xhr = new XMLHttpRequest();
//                    xhr.open("POST", "/zuul/kmms/attendanceManageCollectAction/Import");
//                    xhr.send(data);
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
            events : {
                value : {'completeAddSave' : true}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'}
        }
    });
    return attendanceManageCollectImport;
});