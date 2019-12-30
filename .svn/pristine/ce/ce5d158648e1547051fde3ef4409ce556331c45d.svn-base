/**
 * 上传文件
 * @author roysong
 * @date 19-5-15
 */
define('kmms/integratedManage/fileShare/addFile',['bui/common','common/form/FormContainer',
    'bui/form','common/uploader/UpdateUploader','common/data/PostLoad',],function(r){
    var BUI = r('bui/common'),
        FormContainer= r('common/form/FormContainer'),
        PostLoad = r('common/data/PostLoad'),
        UpdateUploader = r('common/uploader/UpdateUploader');
    var addFile = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initUploader();
        },
        bindUI : function(){
            var _self = this;
            //定义按键
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : _self.get('success')
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
            var _self = this,colNum = 2;
            var childs = [
            	{
                    label : '当前路径：',
                    itemColspan : 2,
                    item : '<label>' + _self.get('fullPath') + '</label>'
                },{
                    label : '上传文件：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea  name="comment" id="dicComment" style="height:20px;width:99.5%"></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'uploadFileForm',
                colNum : colNum,
                formChildrens : childs,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
        	id : {value : 'addFileDialog'},
            elAttrs : {value: {id:"addFile"}},
            title:{value:'上传文件'},
            width:{value:610},
            height:{value:265},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            fullPath:{},
            navData : {},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('uploadFileForm');
                    //获取上传文件
            		var uploadFileArr = _self._getUploadFileData();
            		if(!uploadFileArr || uploadFileArr.length == 0){
            			BUI.Message.Alert('并未上传任何文件，请检查后重新保存。','error');
            			return;
            		}
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		var data = formAdd.serializeToObject();
	        		data.collectionName=_self.get('collectionName');
	        		data.userId=_self.get('userId');
	        		data.userName=_self.get('userName');
	        		data.orgId=_self.get('orgId');
	        		data.orgName=_self.get('orgName');
	        		var navData = _self.get('navData');
	        		// 如果最后一个导航目录是查询结果，则弃用
	        		var last = navData.pop();
	        		if(last.id != '-1') navData.push(last);
	        		data.nav = navData;
	        		data.parentId = navData[navData.length - 1].id;
	        		data.status="1";//有效
	        		data.type = "2";//代表文件
	        		var datas = [];
	        		var pl = new PostLoad({
	        			url : '/kmms/fileShareAction/uploadFile',
	        			el : _self.get('el'),
	        			loadMsg : '上传中...'
	        		}); 
	        		BUI.each(uploadFileArr,function(e){
	        			var f = BUI.cloneObject(data);
	        			f.name = e.name;
	        			f.path = e.path;
	        			datas.push(f);
	        		});
        			pl.load({data : JSON.stringify(datas)}, function(e){
        				if (e) {
                            _self.fire("completeAddSave",{
                                result : e
                            });
                        }
        				_self.close();
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
            }
        }
    });
    return addFile;
});