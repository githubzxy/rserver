/**
 * II级维修申请数据修改（审批不通过的时候）
 * @author yangsy
 * @date 19-2-22
 */
define('kmms/constructionManage/pointInnerMaintainPlan/pointInnerSecondMaintainApply/pointInnerSecondMaintainApplyApproveEdit',
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
    	FormContainer= r('common/form/FormContainer'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	PostLoad = r('common/data/PostLoad'),
    	UpdateUploader = r('common/uploader/UpdateUploader');
    var PointInnerSecondMaintainApplyApproveEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
//			_self._initUploader();
//			_self._initOrganizationPicker();
			_self._getShowData();
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
                    text:'申请',
                    elCls : 'button',
                    handler : function(){
                        var apply = _self.get('apply');
                        if(apply){
                        	apply.call(_self);
                        }
                    }
                },{
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
            var _self = this,shiftId = _self.get('shiftId');
            $.ajax({
                url:'/kmms/pointInnerSecondMaintainApplyAction/findById',
                data:{id : shiftId,collectionName : _self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                        $("#formContainer #constructionProject").val(data.constructionProject);
                        $("#formContainer #submitOrgName").val(data.submitOrgName);
                        if(data.uploadFileArr) {
            				_self._initUploader(data.uploadFileArr);
            			}
                        $('#formContainer #approver').val(data.approver);
            		    $('#formContainer #approveDate').val(data.approveDate);
            		    $('#formContainer #approveAdvice').val(data.approveAdvice);
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
//                width:220,
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
                    label: '维修方案：',
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<input type="text" name="constructionProject" id="constructionProject" data-rules="{required:true}" style="width:99.5%"/>'
                },
                {
                    label: '提交部门：',
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<input type="text" name="submitOrgName" id="submitOrgName" readonly data-rules="{required:true}"/>'+'<input type="hidden" name="submitOrgId" id="submitOrgId" readonly/>'
                },
                {
					label: '附件：',
					itemColspan: 2,
					item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
				},
				{
					label : '审批人：',
					itemColspan : 1,
					item : '<input type="text" name="approver" id="approver" style="width:99%" readonly/>'
				},
				{
					label : '审批日期：',
					itemColspan : 1,
					item : '<input type="text" name="approveDate" id="approveDate" style="width:99%" readonly/>'
				},
				{
					label : '车间审批意见：',
					itemColspan : 2,
					item : '<textarea name="approveAdvice" id="approveAdvice" style="width:99.5%;height:50px" readonly/></textarea>'
				},
            ];
            var form = new FormContainer({
                id : 'pointInnerSecondMaintainApplyApproveEditForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id:{value : 'pointInnerSecondMaintainApplyApproveEditDialog'},
            elAttrs:{value: {id:"pointInnerSecondMaintainApplyApproveEdit"}},
            title:{value:'重新申请'},
            width:{value:600},
            height:{value:360},
            closeAction:{value:'destroy'},
            mask:{value:true},
            collectionName:{},
            shiftId:{},//用于查询单条数据
            apply:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('pointInnerSecondMaintainApplyApproveEditForm');
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
        				url : '/zuul/kmms/pointInnerSecondMaintainApplyAction/updateDoc',
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
            save:{
            	value : function(){
            		var _self = this;
            		var formAdd = _self.getChild('pointInnerSecondMaintainApplyApproveEditForm');
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
        				url : '/zuul/kmms/pointInnerSecondMaintainApplyAction/updateDoc',
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
                value : {'completeAddSave' : true,}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return PointInnerSecondMaintainApplyApproveEdit;
});