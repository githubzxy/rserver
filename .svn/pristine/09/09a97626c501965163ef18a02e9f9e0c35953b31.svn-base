/**
 * 提交电路工单
 * @author yangsy
 * @date 19-3-4
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderApply/circuitWorkOrderApplySubmit',
		[
		 	'bui/common',
		 	'bui/form',
		 	'bui/select',
			'bui/data',
			'bui/calendar',
			'common/form/FormContainer',
			'common/org/OrganizationPicker',
			'common/data/PostLoad',
			'common/uploader/UpdateUploader',
			'common/uploader/ViewUploader',
		],function(r){
    var BUI = r('bui/common'),Select = r('bui/select'),Data = r('bui/data'),
    	  Calendar = r('bui/calendar'),
    	  FormContainer= r('common/form/FormContainer'),
    	  OrganizationPicker = r('common/org/OrganizationPicker'),
    	  PostLoad = r('common/data/PostLoad'),
    	  UpdateUploader = r('common/uploader/UpdateUploader'),
    	  ViewUploader = r('common/uploader/ViewUploader');
    var CircuitWorkOrderApplySubmit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._getShowData();
			_self._initSelect();
			_self._initWorkShopPicker();
        },
        bindUI : function(){
            var _self = this;
            
            var orgPicker=_self.get('orgPicker');
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
            	var data = orgPicker.getSelection();
    		    var text = "";
    		    var id = "";
    		    for(var i=0;i<data.length;i++){
    		    	text = text + ',' + data[i].text
    		    	id = id + ',' + data[i].value
    		    }
    		    $('#formContainer #executiveStaff').val(text.substring(1));
    		    $('#formContainer #executiveStaffId').val(id.substring(1));
            });
            
            //定义按键
            var buttons = [
                 {
                    text:'提交审核',
                    elCls : 'button',
                    handler : function(){
                        var submit = _self.get('submit');
                        if(submit){
                        	submit.call(_self);
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
        
        _initWorkShopPicker:function(){
        	var _self=this;
        	var orgPicker = new OrganizationPicker({
        		trigger : '#executiveStaff',
        		rootOrgId:_self.get('rootOrgId'),//必填项
        		rootOrgText:_self.get('rootOrgText'),//必填项
        		url : '/kmms/circuitWorkOrderApplyAction/getWorkShop',//必填项
        		autoHide : true,
        		showRoot : false,//不显示根节点
//        		checkType:"all",//有勾选框
        		multipleSelect: true,//多选
        		align : {
        			points:['bl','tl']
        		},
        		zIndex : '10000',
        		width:225,
        		height:225
        	});
        	orgPicker.render();
        	_self.set('orgPicker',orgPicker);
        },
        
        _initSelect: function(){
			$("#formContainer #systemType").append("<option value=''>请选择</option>");
			$("#formContainer #systemType").append("<option  value='传输接入系统'>传输接入系统</option>");
			$("#formContainer #systemType").append("<option  value='数据网系统'>数据网系统</option>");
			$("#formContainer #systemType").append("<option  value='GSM-R系统'>GSM-R系统</option>");
			$("#formContainer #systemType").append("<option  value='其他'>其他</option>");
			
			$("#formContainer #workOrderType").append("<option value=''>请选择</option>");
			$("#formContainer #workOrderType").append("<option  value='电路开通'>电路开通</option>");
			$("#formContainer #workOrderType").append("<option  value='电路变更'>电路变更</option>");
			$("#formContainer #workOrderType").append("<option  value='电路停用'>电路停用</option>");
			$("#formContainer #workOrderType").append("<option  value='网络优化'>网络优化</option>");
        },
        
        /**
		 * 初始化上传文件（仅用于查看）
		 */
		_initViewUploader:function(uploadFiles){
			var _self = this;
			var viewFiles = new ViewUploader({
				render: '#formContainer #viewUploadfile',
				alreadyItems : uploadFiles,
				editOnline : true
			});
			viewFiles.render();
		},
		
		_initUploader:function(uploadFiles){
			var _self = this;
			var uploader = new UpdateUploader({
				render : '#formContainer #viewUploadfile',
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
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId');
            $.ajax({
                url:'/kmms/circuitWorkOrderApplyAction/findById',
                data:{id : shiftId,collectionName : _self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                        $("#formContainer #workOrderName").val(data.workOrderName);
                        $("#formContainer #receivePeople").val(data.receivePeople);
                        $("#formContainer #receivePeopleId").val(data.receivePeopleId);
                        $("#formContainer #executiveStaff").val(data.executiveStaff);
                        $("#formContainer #executiveStaffId").val(data.executiveStaffId);
                        $("#formContainer #systemType").val(data.systemType);
                        $("#formContainer #workOrderType").val(data.workOrderType);
                        $("#formContainer #remark").val(data.remark);
                        if(data.uploadFileArr) {
            				_self._initUploader(data.uploadFileArr);
            			}
                    }
                }
            })
        },
		
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
            	{
                    label: '工单名称：',
                    redStarFlag: true,
                    itemColspan: 2,
                    item: '<input type="text" name="workOrderName" id="workOrderName" data-rules="{required:true}" style="width:99.5%"/>'
                },
                {
                	label: '网管中心：',
                	itemColspan: 2,
                	item: '<input type="text" name="receivePeople" id="receivePeople" readonly />'+'<input type="hidden" name="receivePeopleId" id="receivePeopleId" readonly/>'
                },
                {
                	label: '执行部门：',
                	redStarFlag: true,
                	itemColspan: 2,
                	item: '<input type="text" name="executiveStaff" id="executiveStaff" readonly/>'+'<input type="hidden" name="executiveStaffId" id="executiveStaffId" readonly/>'
//                	item: '<div name="executiveStaff" id="executiveStaff">'+
//		       					'<input  name="executiveStaffId" id="executiveStaffId" style="width:100%;" readonly="readonly" type="hidden"/>'+
//	     	  				'</div>'
                },
                {
                    label: '系统类别：',
                    redStarFlag: true,
                    itemColspan: 1,
                    item: '<select name="systemType" id="systemType" style="width:99.5%" data-rules="{required:true}"/></select>'
                },
                {
                	label: '工单类型：',
                	redStarFlag: true,
                	itemColspan: 1,
                	item: '<select name="workOrderType" id="workOrderType" style="width:99.5%" data-rules="{required:true}"/></select>'
                },
                {
                	label: '备注：',
                	itemColspan: 2,
                	item: '<textarea name="remark" id="remark" style="width:99.5%;height:50px"/></textarea>'
                },
                {
					label: '附件：',
					itemColspan: 2,
					item: '<div name="viewUploadfile" id="viewUploadfile" style="height:100px;overflow-y:auto"></div>'
				},
            ];
            var form = new FormContainer({
                id : 'circuitWorkOrderApplySubmitForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id:{value : 'circuitWorkOrderApplySubmitDialog'},
            elAttrs:{value: {id:"circuitWorkOrderApplySubmit"}},
            title:{value:'提交'},
            width:{value:650},
            height:{value:400},
            closeAction:{value:'destroy'},
            mask:{value:true},
            collectionName:{},
            shiftId:{},//用于查询单条数据
            submit:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('circuitWorkOrderApplySubmitForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
                    
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		
	        		if(uploadFileArr.length==0){
	        			Mask.maskElement('#circuitWorkOrderApplySubmit');
	        			BUI.Message.Alert("请上传附件！",function(){
	        				Mask.unmaskElement('#circuitWorkOrderApplySubmit');
	        			},'error');
	        			$("#circuitWorkOrderApplySubmit .bui-message .bui-ext-close").css("display","none");
	        			return;
	        		}
	        		
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	    	    	
	    	    	if(!data.executiveStaff){
	        			Mask.maskElement('#circuitWorkOrderApplySubmit');
	        			BUI.Message.Alert("请选择执行部门！",function(){
	        				Mask.unmaskElement('#circuitWorkOrderApplySubmit');
	        			},'error');
	        			$("#circuitWorkOrderApplySubmit .bui-message .bui-ext-close").css("display","none");
	        			return;
	        		}
	    	    	
	        		data.collectionName=_self.get('collectionName');
	        		data.flowState="4";
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/circuitWorkOrderApplyAction/submitDoc',
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
            		var formAdd = _self.getChild('circuitWorkOrderApplySubmitForm');
            		//验证不通过
            		if(!formAdd.isValid()){
            			return;
            		}
            		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.collectionName=_self.get('collectionName');
//	        		data.flowState="0";
	        		data.id=_self.get('shiftId');
	        		var pl = new PostLoad({
        				url : '/zuul/kmms/circuitWorkOrderApplyAction/updateDoc',
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
    return CircuitWorkOrderApplySubmit;
});