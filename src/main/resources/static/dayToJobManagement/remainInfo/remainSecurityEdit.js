/**
 * 安全隐患修改
 * @author zhouxingyu
 * @date 19-3-22
 */
define('kmms/dayToJobManagement/remainInfo/remainSecurityEdit',
	[
	 	'bui/common',
	 	'bui/form',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/form/FormContainer',
	 	'common/org/OrganizationPicker',
		'common/uploader/UpdateUploader',
	 ],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	PostLoad = r('common/data/PostLoad'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
        FormContainer= r('common/form/FormContainer'),
    	UpdateUploader = r('common/uploader/UpdateUploader');

    var RemainSecurityEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initDate();
        	_self._getShowData();
//        	_self._getLines();
        },
        bindUI : function(){
            var _self = this;
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
        
//        
//        /**
//         * 获取线别
//         */
//        _getLines:function(){
//        	var _self=this;
//        	$.ajax({
//	                url:'/kmms/networkManageInfoAction/getLines',
//	                type:'post',
//	                dataType:"json",
//	                success:function(res){
//	               	 $("#formContainer #securityLineName").append("<option  value=''>请选择</option>");
//	               	 for(var i=0;i<res.length;i++){
//	               		 $("#formContainer #securityLineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
//	               	 
//	               	 }
//	               	_self._getShowData();
//                }
//            });
//        },
        
        /**
         * 初始化时间
         */
        _initDate: function () {
            var _self = this;
            var securityDate = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #securityDate',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #securityDate'
            });
            _self.set('securityDate', securityDate);
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
                url:'/kmms/networkManageInfoAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(result){
                    var data = result.data;
                    if(data){
                    	_self._getWorkShop(data.securityObligationDepart);
						$("#formContainer #securityObligationDepart").val(data.securityObligationDepart);
                        $("#formContainer #securityLineName").val(data.securityLineName);
                        $("#formContainer #securityDate").val(data.securityDate);
                        $('#formContainer #securityLevel').val(data.securityLevel);
            		    $('#formContainer #securityDetailLevel').val(data.securityDetailLevel);
            		    $('#formContainer #securityDigest').val(data.securityDigest);
            		    $('#formContainer #securityReason').val(data.securityReason);
            		    $('#formContainer #securityGeneral').val(data.securityGeneral);
            		    $('#formContainer #securityDutySituation').val(data.securityDutySituation);
            		    if(data.securityUploadFileArr) {
            				_self._initUploader(data.securityUploadFileArr);
            			}else{
            				_self._initUploader();
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
        _getWorkShop:function(securityObligationDepart){
       	 var _self = this;
            $.ajax({
                url:'/kmms/networkManageInfoAction/getWorkShop',
                type:'post',
                dataType:"json",
                success:function(res){
               	 $("#formContainer #securityObligationDepart").append("<option  value=''>请选择</option>");
               	 for(var i=0;i<res.length;i++){
               		 $("#formContainer #securityObligationDepart").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
               	 }
	               	 $("#formContainer #securityObligationDepart option[value='"+securityObligationDepart+"']").attr("selected","selected");
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
					label : '责任部门：',
					redStarFlag : true,
					itemColspan : 2,
					item : '<select type="text" name="securityObligationDepart" id="securityObligationDepart" style="width:99.5%"  data-rules="{required:true}"/>'
				},
				{
					label : '线名：',
					redStarFlag : true,
					itemColspan : 1,
					item : '<input type="text" name="securityLineName" id="securityLineName" style="width:99%" data-rules="{required:true}"/>'
//						item : '<select name="securityLineName" id="securityLineName" style="width:99.5%" data-rules="{required:true}"/></select>'
				},
				{
                    label : '时间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="securityDate" id="securityDate" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
                },
                {
                    label : '隐患等级：',
                    itemColspan : 1,
                    item : '<input type="text" name="securityLevel" id="securityLevel" style="width:99%"/>'
                },
                {
                    label : '详细等级：',
                    itemColspan : 1,
                    item : '<input type="text" name="securityDetailLevel" id="securityDetailLevel" style="width:99%"/></select>'
                },
                {
                    label : '隐患摘要：',
                    itemColspan : 2,
                    item : '<textarea name="securityDigest" id="securityDigest" style="width:99.5%;height:50px"/></textarea>'
                },
                {
                	label : '隐患原因：',
                	itemColspan : 2,
                	item : '<textarea name="securityReason" id="securityReason" style="width:99.5%;height:50px"/></textarea>'
                },
                {
                	label : '隐患概况：',
                	itemColspan : 2,
                	item : '<textarea name="securityGeneral" id="securityGeneral" style="width:99.5%;height:50px"/></textarea>'
                },
                {
                	label : '定责情况：',
                	itemColspan : 2,
                	item : '<textarea name="securityDutySituation" id="securityDutySituation" style="width:99.5%;height:50px"/></textarea>'
                },
                {
					label: '附件：',
					itemColspan: 2,
					item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
				}
            ];
            var form = new FormContainer({
                id : 'otherProductionSecurityEditForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}

            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'otherProductionSecurityEditDialog'},
            elAttrs : {value: {id:"otherProductionSecurityEdit"}},
            title:{value:'修改隐患情况'},
            width:{value:620},
            height:{value:540},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},
            shiftId:{},
            busiId:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('otherProductionSecurityEditForm');
                    //获取上传文件
            		var securityUploadFileArr = _self._getUploadFileData();
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('securityUploadFileArr',JSON.stringify(securityUploadFileArr));
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('busiId',_self.get('busiId'));
                    formData.append('edit','edit');
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/otherProductionInfoAction/updateSecurityDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeSecurityAddSave",{
                                result : result
                            });
                        }
                    }
                }
            },
            events : {
                value : {
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeSecurityAddSave' : true,
                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return RemainSecurityEdit;
});