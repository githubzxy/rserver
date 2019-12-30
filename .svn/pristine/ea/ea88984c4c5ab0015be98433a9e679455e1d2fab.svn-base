/**
 * 派发
 * @author yangsy
 * @date 19-3-6
 */
define('kmms/productionManage/circuitWorkOrderManage/circuitWorkOrderReply/circuitWorkOrderWorkshopReplyDistribute',
		[
			'bui/common',
			'bui/form',
			'bui/select',
			'bui/data',
			'common/form/FormContainer',
			'common/org/OrganizationPicker',
			'common/data/PostLoad',
			'common/uploader/UpdateUploader',
		],function(r){
    var BUI = r('bui/common'),Select = r('bui/select'),Data = r('bui/data'),
    	FormContainer = r('common/form/FormContainer'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	PostLoad = r('common/data/PostLoad'),
    	UpdateUploader = r('common/uploader/UpdateUploader');
    var CircuitWorkOrderWorkshopReplyDistribute = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
//        	_self._getDepartData();
//        	_self._initUploader();
        	_self._initDepartPicker();
//        	$('#formContainer #submitOrgName').val(_self.get('orgName'));
//		    $('#formContainer #submitOrgId').val(_self.get('orgId'));
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
    		    $('#formContainer #distributeWorkAreaName').val(text.substring(1));
    		    $('#formContainer #distributeWorkAreaId').val(id.substring(1));
    		    console.log($('#formContainer #distributeWorkAreaName').val());
    		    console.log($('#formContainer #distributeWorkAreaId').val());
            });
            //定义按键
            var buttons = [
            	{
                    text:'派发',
                    elCls : 'button',
                    handler : function(){
                        var apply = _self.get('apply');
                        if(apply){
                        	apply.call(_self);
                        }
                    }
                },
                {
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
        
        _initDepartPicker:function(){
        	var _self=this;
        	var orgPicker = new OrganizationPicker({
        		trigger : '#distributeWorkAreaName',
        		rootOrgId:_self.get('rootOrgId'),//必填项
        		rootOrgText:_self.get('rootOrgText'),//必填项
        		url : '/kmms/circuitWorkOrderWorkshopReplyAction/getDepart',//必填项
        		autoHide : true,
        		showRoot: false,//不显示根节点
//        		checkType:"all",//有勾选框
        		multipleSelect: true,//多选
        		params:{workShopName:_self.get("orgId")},
        		align : {
        			points:['bl','tl']
        		},
        		zIndex : '10000',
        		width:484,
        		height:125
        	});
        	orgPicker.render();
        	_self.set('orgPicker',orgPicker);
        },
        
        _getDepartData:function(){
        	var _self = this;
			var store = new Data.Store({
			    url : '/kmms/circuitWorkOrderWorkshopReplyAction/getDepart', 
			    params:{workShopName:_self.get("orgId")},
			    autoLoad : true,    
			    proxy : {
			      method : 'post',
			      dataType : 'json'
			    }
			});
			_self._setDepartSelect(store);
        },
        
        _setDepartSelect:function(store){
			var _self = this;
			$("#distributeWorkAreaId").empty();
			var render = $('#distributeWorkAreaId');
			var valueField = $('#distributeWorkAreaName');
			var select = new Select.Select({
		          render:render,
		          valueField:valueField,
		          multipleSelect:true,
		          store:store
		    });
		    select.render();
		    _self.set("distributeWorkAreaSelect",select);
		    _self._initCssOfSelect();
		},
		
		_initCssOfSelect:function(){
			$(".bui-select").css("width","99.5%");
		    $(".bui-select span").css("border","white");
		    $(".bui-select-input").css("width","99.5%");
		    $(".bui-select span").css("display","none");
		    $(".bui-select-list").css("overflow-y","auto");
//		    $(".bui-select-input").attr("placeholder","请选择配合人员");
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

        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                {
                	label: '派发工区：',
                	redStarFlag: true,
                	itemColspan: 2,
                	item: '<input type="text" name="distributeWorkAreaName" id="distributeWorkAreaName" style="width:99.5%" readonly/>'+'<input type="hidden" name="distributeWorkAreaId" id="distributeWorkAreaId" readonly/>'
//                	item: '<div name="distributeWorkAreaName" id="distributeWorkAreaName">'+
//		       					'<input  name="distributeWorkAreaId" id="distributeWorkAreaId" style="width:100%;height:30px;" readonly="readonly" type="hidden"/>'+
//	     	  				'</div>'
                },
                {
                	label: '派发备注：',
                	itemColspan: 2,
                	item: '<textarea name="distributeRemark" id="distributeRemark" style="width:99.5%;height:120px"/></textarea>'
                },
            ];
            var form = new FormContainer({
                id : 'circuitWorkOrderWorkshopReplyDistributeForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'circuitWorkOrderWorkshopReplyDistributeDialog'},
            elAttrs : {value : {id:"circuitWorkOrderWorkshopReplyDistribute"}},
            title:{value:'派发'},
            width:{value:600},
            height:{value:260},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},//登录用户ID（技术科用户）
            userName:{},//登录用户名称（技术科用户）
            orgId:{},//登录用户所属机构ID
            orgName:{},//登录用户所属机构名称
            parentId:{},//登录用户所属机构上级机构ID
            shiftId:{},
            apply:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('circuitWorkOrderWorkshopReplyDistributeForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
//	        		if(uploadFileArr.length==0){
//	        			console.log("加个提示框");
//	        			return;
//	        		}
	        		//序列化表单成对象，所有的键值都是字符串
	    	    	var data = formAdd.serializeToObject();
	        		data.collectionName=_self.get('collectionName');
	        		data.flowState="7";
	        		data.executiveUserName = _self.get("userName");
	        		data.executiveOrgId = _self.get("orgId");
	        		data.id=_self.get('shiftId');
                    var pl = new PostLoad({
        				url : '/zuul/kmms/circuitWorkOrderWorkshopReplyAction/distributeDoc',
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
                value : {'completeAddSave' : true}
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'}
        }
    });
    return CircuitWorkOrderWorkshopReplyDistribute;
});