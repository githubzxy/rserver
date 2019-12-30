/**
 * 安全隐患新增
 * @author zhouxingyu
 * @date 19-3-22
 */
define('kmms/dayToJobManagement/otherProductionInfo/otherProductionSecurityAdd',
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
    var OtherProductionSecurityAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initDate();
        	_self._initUploader();
        	_self._getWorkShop();//获取车间下拉选数据
//        	_self._getLines();//获取线名下拉选数据
        },
        bindUI : function(){
            var _self = this;
            
//            var orgPicker=_self.get('orgPicker');
            
//            /**
//             * 组织机构选择
//             */
//            orgPicker.on('orgSelected',function (e) {
//                $('#formContainer #backOrgName').val(e.org.text);
//    		    $('#formContainer #backOrgId').val(e.org.id);
//            });

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
        
//        _initSelect: function(){
//			$("#formContainer #accidentLineName").append("<option  value='1'>线1</option>");
//			$("#formContainer #accidentLineName").append("<option  value='2'>线2</option>");
//        },
        
//        /**
//         * 获取线别
//         */
//        _getLines:function(){
//        	var _self=this;
//       	 $.ajax({
//	                url:'/kmms/networkManageInfoAction/getLines',
//	                type:'post',
//	                dataType:"json",
//	                success:function(res){
//	               	 $("#formContainer #securityLineName").append("<option  value=''>请选择</option>");
//	               	 for(var i=0;i<res.length;i++){
//	               		 $("#formContainer #securityLineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
//	               	 }
//                }
//            });
//        },
        
        /**
         * 初始化时间
         */
        _initDate: function () {
            var _self = this;
            var createDate = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #securityDate',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #securityDate'
            });
            _self.set('createDate', createDate);
            
            var postLoad = new PostLoad({
                url : '/kmms/networkManageInfoAction/getSystemDate.cn',
                el : _self.get('el'),
                loadMsg : '加载中...'
            });
            postLoad.load({},function (date) {
            	if(date){
            		$('#formContainer #securityDate').val(date);
            	}
            });
        },
        
//        /**
//         * 初始化组织机构选择
//         */
//        _initOrganizationPicker:function(){
//            var _self=this;
//            var orgPicker = new OrganizationPicker({
//                trigger : '#formContainer #backOrgName',
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
         * 获取车间下拉选数据
         */
        _getWorkShop:function(){
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
                 }
             })
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
//                {
//                    label : '资料名称：',
//                    redStarFlag : true,
//                    itemColspan : 2,
//                    item : '<input type="text"  name="name" id="name" data-rules="{required:true}" style="width:99.5%">'+
//                    '<input type="hidden"  name="fileCols" id="fileCols" ' +
//                        'value="uploadfile">'
//                },
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
//                    item : '<select name="accidentLevel" id="accidentLevel" style="width:99.5%" data-rules="{required:true}"/></select>'
                },
                {
                    label : '详细等级：',
                    itemColspan : 1,
                    item : '<input type="text" name="securityDetailLevel" id="securityDetailLevel" style="width:99%"/></select>'
//                    item : '<select name="accidentDetailLevel" id="accidentDetailLevel" style="width:99.5%" data-rules="{required:true}"/></select>'
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
				},
            ];
            var form = new FormContainer({
                id : 'otherProductionSecurityAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'otherProductionSecurityAddDialog'},
            elAttrs : {value: {id:"otherProductionSecurityAdd"}},
            title:{value:'新增隐患情况'},
            width:{value:620},
            height:{value:540},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},
            busiId:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('otherProductionSecurityAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		 //获取上传文件
            		var securityUploadFileArr = _self._getUploadFileData();
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('securityUploadFileArr',JSON.stringify(securityUploadFileArr));
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('busiId',_self.get('busiId'));
//                    formData.append('userId',_self.get('userId'));
//                    formData.append('orgId',_self.get('orgId'));
//                    formData.append('orgName',_self.get('orgName'));
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
    return OtherProductionSecurityAdd;
});