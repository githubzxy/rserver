/**
 * 文件新增模块
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/emergencyManage/skitsRescueCompletion/skitsRescueCompletionAdd',
		['bui/common',
		'bui/data',
		 'common/form/FormContainer',
		 'bui/uploader',
		 'common/org/OrganizationPicker',
		 'common/uploader/UpdateUploader',
		 'common/data/PostLoad',
		 'kmms/technicalManagement/technicalDocument/SelectSuggest'
		 ],function(r){
	var BUI = r('bui/common');
	var Data = r('bui/data');
	var	FormContainer = r('common/form/FormContainer');
	var	Uploader = r('bui/uploader');
	var	UpdateUploader = r('common/uploader/UpdateUploader');
	var	PostLoad = r('common/data/PostLoad');
	var OrganizationPicker = r('common/org/OrganizationPicker');
	var SelectSuggest = r("kmms/technicalManagement/technicalDocument/SelectSuggest");
	var skitsRescueCompletionAdd = BUI.Overlay.Dialog.extend({
		/**
		 * 初始化
		 */
		initializer: function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		
		/**
		 * 渲染
		 */
		renderUI: function(){
			var _self = this;
			_self._getLines();
			_self._initUploader();
			_self._getWorkshops();//获取部门下拉选数据
			_self._initOrganizationPicker();
		},

		/**
		 * 绑定事件
		 */
		bindUI: function(){
			var _self = this;
			var orgPicker=_self.get('orgPicker');
			   /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#formContainer #joinDepart').val(e.org.text);
            });
            	//定义按键
			var buttons = [
				{
					text: '保存',
					elCls: 'button',
					handler: function(){
						_self._saveData();
					}
				},{
					text: '关闭',
					elCls: 'button',
					handler: function(){
						_self.close();
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
				render : '#formContainer #uploadFile',
		        url : '/zuul/kmms/atachFile/upload.cn',
		        isSuccess : function(result){
					return true;
		        },
			});
			uploader.render();
			_self.set('uploader',uploader);
		},
		/**
		 * 初始化表单
		 */
		_initFormContainer: function(){
			var form = new FormContainer({
				id: 'fileFormAdd',
				colNum: 2,
				formChildrens:[
				{
					label: '时间',
					redStarFlag: true,
					itemColspan: 1,
					item : '<input type="text" id="date" name="date" class="calendar" readonly data-rules="{required:true}"/>'
				},
				{
					label: '线别',
					itemColspan: 1,
					item : '<select type="text" id="line" name="line" readonly ></select>'
				},
				{
					label: '地点',
					itemColspan: 1,
					item : '<input type="text" id="site" name="site"  />'
				},
				{
					label: '组织部门：',
//					redStarFlag: true,
					itemColspan: 1,
					item: '<select type="text" id="orgDepart" name="orgDepart" readonly ></select>'
				},{
					label: '组织者',
					itemColspan: 1,
					item : '<input type="text" id="organizer" name="organizer"  />'
				},{
					label: '参加部门',
					itemColspan: 1,
					item : '<input type="text" id="joinDepart" name="joinDepart" readonly style="width: 235px;">'
				},{
					label: '抢险/演练内容',
					itemColspan: 2,
					item : '<textarea type="text" id="content" name="content"  />'
				},{
					label: '出动车辆（公用）数量',
					itemColspan: 1,
					item : '<input type="text" id="publicCarCount" name="publicCarCount"  />'
				},{
					label: '出动车辆（租用）数量',
					itemColspan: 1,
					item : '<input type="text" id="rentCarCount" name="rentCarCount"  />'
				},{
					label: '抢险/演练报告',
					itemColspan: 2,
					item: '<div name="uploadFile" id="uploadFile" style="height:80px;overflow-y:auto"></div>'
				}] 
			});
			return form;
		},
		/**
         * 初始化组织机构选择
         * @private
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #joinDepart',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:235,
                height:250
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
        },
	     /**
         * 获取线别
         */
        _getLines: function() {
            var _self = this;
            $.ajax({
                url: '/kmms/constructCooperateAction/getLines',
                type: 'post',
                dataType: "json",
                success: function(res) {
		             $("#formContainer #line").append("<option  value=''>请选择</option>");
                    for (var i = 0; i < res.length; i++) {
                        $("#formContainer #line").append("<option  value=" + res[i] + ">" + res[i] + "</option>");
                    }
                }
            })
        },
	    /**
         * 获取科室和车间
         */
        _getWorkshops:function(){
        	var _self=this;
       	 $.ajax({
	                url:'/kmms/userInfoManageAction/getCadreAndShop',
	                type:'post',
	                dataType:"json",
	                success:function(res){
		             $("#formContainer #orgDepart").append("<option  value=''>请选择</option>");
		             $("#formContainer #orgDepart").append("<option  value='昆明局'>昆明局</option>");
		             $("#formContainer #orgDepart").append("<option  value='总公司'>总公司</option>");
	               	 for(var i=0;i<res.length;i++){
	               		 $("#formContainer #orgDepart").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
	               	 }
                }
            });
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
		//保存数据
		_saveData:function(){
			var _self = this;
    		var form = _self.getChild('fileFormAdd',true);
    		//验证不通过
    		if(!form.isValid()){
    			return;
    		}
    		var data = form.serializeToObject();
    		//获取上传文件
    		var uploadFile = _self._getUploadFileData();
    		data.uploadFile=JSON.stringify(uploadFile);
    		data.collectionName = _self.get('collectionName');
			data.userId = _self.get('userId');
			data.orgId = _self.get('orgId');
    		//提交到数据库
    		var postLoad = new PostLoad({
				url : '/zuul/kmms/skitsRescueCompletionAction/addDoc',
				el : _self.get('el'),
				loadMsg : '保存中...'
			}); 
    		
			postLoad.load(data, function(result){
				if(result != null){
					_self.fire("completeAddSave",{
						result : result
					});
				}
			});
		}
	},{
		ATTRS: {
			id: {value : 'fileAddDialog'},
			title: {value : '新增'},
			width: {value:650},
	        height: {value:380},
	        contextPath: {},
	        perId : {},
            userId : {},//登录用户ID
            userName : {},//登录用户名称
            orgId : {},//登录用户组织机构ID
            orgName : {},//登录用户组织机构名称
	        closeAction: {value: 'destroy'},//关闭时销毁加载到主页面的HTML对象
	        mask: {value: true},
	        rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
	        events: {
	        	value: {
	        		/**
	        		 * 绑定保存按钮事件
	        		 */
	        		'completeAddSave' : true,
	        	}
	        }
		}
	});
	return skitsRescueCompletionAdd;
});
