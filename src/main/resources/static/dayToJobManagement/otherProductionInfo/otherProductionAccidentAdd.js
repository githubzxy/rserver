/**
 * 事故新增
 * @author yangsy
 * @date 19-1-18
 */
define('kmms/dayToJobManagement/otherProductionInfo/otherProductionAccidentAdd',
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
    var OtherProductionAccidentAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._getWorkShop();//获取车间下拉选数据
        	_self._initUploader();
        	_self._initDate();
//        	_self._getLines();//获取线名下拉选数据
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
                	 $("#formContainer #accidentObligationDepart").append("<option  value=''>请选择</option>");
                	 for(var i=0;i<res.length;i++){
                		 $("#formContainer #accidentObligationDepart").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
                 }
             })
        }, 
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
//	               	 $("#formContainer #accidentLineName").append("<option  value=''>请选择</option>");
//	               	 for(var i=0;i<res.length;i++){
//	               		 $("#formContainer #accidentLineName").append("<option  value="+res[i]+">"+res[i]+"</option>");
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
                trigger: '#formContainer #accidentDate',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #accidentDate'
            });
            _self.set('createDate', createDate);
            
            var postLoad = new PostLoad({
                url : '/kmms/otherProductionInfoAction/getSystemDate.cn',
                el : _self.get('el'),
                loadMsg : '加载中...'
            });
            postLoad.load({},function (date) {
            	if(date){
            		$('#formContainer #accidentDate').val(date);
            	}
            });
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
					label : '责任部门：',
					redStarFlag : true,
					itemColspan : 2,
					item : '<select type="text" name="accidentObligationDepart" id="accidentObligationDepart" style="width:99.5%"  data-rules="{required:true}"/>'
				},
				{
					label : '线名：',
					redStarFlag : true,
					itemColspan : 1,
//					item : '<select name="accidentLineName" id="accidentLineName" style="width:99.5%" data-rules="{required:true}"/></select>'
						item : '<input type="text" name="accidentLineName" id="accidentLineName" style="width:99%" data-rules="{required:true}"/>'
				},
				{
                    label : '时间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="accidentDate" id="accidentDate" style="width:99%" class="calendar" data-rules="{required:true}" readonly/>'
                },
                {
                    label : '事故等级：',
                    itemColspan : 1,
                    item : '<input type="text" name="accidentLevel" id="accidentLevel" style="width:99%"/>'
                },
                {
                    label : '详细等级：',
                    itemColspan : 1,
                    item : '<input type="text" name="accidentDetailLevel" id="accidentDetailLevel" style="width:99%"/></select>'
                },
                {
                    label : '事故摘要：',
                    itemColspan : 2,
                    item : '<textarea name="accidentDigest" id="accidentDigest" style="width:99.5%;height:50px"/></textarea>'
                },
                {
                	label : '事故原因：',
                	itemColspan : 2,
                	item : '<textarea name="accidentReason" id="accidentReason" style="width:99.5%;height:50px"/></textarea>'
                },
                {
                	label : '事故概况：',
                	itemColspan : 2,
                	item : '<textarea name="accidentGeneral" id="accidentGeneral" style="width:99.5%;height:50px"/></textarea>'
                },
                {
                	label : '定责情况：',
                	itemColspan : 2,
                	item : '<textarea name="accidentDutySituation" id="accidentDutySituation" style="width:99.5%;height:50px"/></textarea>'
                },
                {
					label: '附件：',
					itemColspan: 2,
					item: '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
				},
            ];
            var form = new FormContainer({
                id : 'otherProductionAccidentAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'otherProductionAccidentAddDialog'},
            elAttrs : {value: {id:"otherProductionAccidentAdd"}},
            title:{value:'新增事故情况'},
            width:{value:620},
            height:{value:540},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            contextPath : {},
            collectionName:{},//mongo数据库表名
            busiId:{},//自定义的业务ID（主键），用于查询需要更新数内容的数据
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('otherProductionAccidentAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		 //获取上传文件
            		var accidentUploadFileArr = _self._getUploadFileData();
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('accidentUploadFileArr',JSON.stringify(accidentUploadFileArr));
                    formData.append('collectionName',_self.get('collectionName'));//mongo数据库表名
                    formData.append('busiId',_self.get('busiId'));//自定义的业务ID（主键），用于查询需要更新数内容的数据
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/otherProductionInfoAction/updateAccidentDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAccidentAddSave",{
                                result : result
                            });
                        }
                    }
                }
            },
            events : {
                value : {'completeAccidentAddSave' : true,}//绑定保存按钮事件
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return OtherProductionAccidentAdd;
});