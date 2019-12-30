/**
 * 新增
 * @author yangli
 * @date 19-1-14
 */
define('kmms/constructProtocol/ConstructProtocolAdd',
		['bui/common','common/form/FormContainer','bui/select','bui/data',
		 'common/data/PostLoad','bui/calendar','bui/form','common/uploader/UpdateUploader'],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	Select = r('bui/select'),
    	Data = r('bui/data'),
    	PostLoad = r('common/data/PostLoad'),
        FormContainer= r('common/form/FormContainer'),
        UpdateUploader = r('common/uploader/UpdateUploader');
    var ConstructProtocolAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initDate();
        	_self._initLineStore();
        	_self._initWorkShopStore();
        	_self._initUploader();
        	

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
            var lineSelect = _self.get("lineSelect");
        	var workShopSelect = _self.get("workShopSelect");
          /**
           * 线别下拉选值获取
           */
        	lineSelect.on('change', function(ev){
				_self._selectChangeEvent(ev);
		    });
        	/**
        	 * 车间下拉选值获取
        	 */
        	workShopSelect.on('change', function(ev){
        		_self._selectCJChangeEvent(ev);
        	});
            

        },
        _selectChangeEvent:function(ev){
			var _self = this;
			var selectLines=ev.text;
			_self.set("selectLines",selectLines);
		},
		_selectCJChangeEvent:function(ev){
			var _self = this;
			var selectWorkShop=ev.text;
			_self.set("selectWorkShop",selectWorkShop);
		},
        
        /**
         * 初始化时间查询
         * @private
         */
        _initDate: function () {
            var _self = this;
            var beginDate = new Calendar.DatePicker({//加载日历控件
                trigger: '#beginDate',
                showTime: false,
                autoRender: true,
                textField:'#beginDate'
            });
            var overDate = new Calendar.DatePicker({//加载日历控件
            	trigger: '#overDate',
            	showTime: false,
            	autoRender: true,
            	textField:'#overDate'
            });
            _self.set('beginDate', beginDate);
            _self.set('overDate', overDate);
            
            var postLoad = new PostLoad({
                url : '/kmms/networkManageInfoAction/getSystemDateToYearMonthDay',
                el : _self.get('el'),
                loadMsg : '加载中...'
            });
            postLoad.load({},function (date) {
            	if(date){
            		$('#formContainer #beginDate').val(date);
            		$('#formContainer #overDate').val(date);
            	}
            });
        },
       
        //获取线别数据
        _initLineStore:function(){
        	var _self = this;
			var store = new Data.Store({
			    url : '/kmms/constructProtocolAction/getLines', 
			    autoLoad : true,    
			    proxy : {
			      method : 'post',
			      dataType : 'json'
			    }
			});
			_self.set('lineStore',store);
			_self._initLineSelect();
		},
		//获取车间数据
		_initWorkShopStore:function(){
			var _self = this;
			var store = new Data.Store({
				url : '/kmms/constructProtocolAction/getworkShop', 
				autoLoad : true,    
				proxy : {
					method : 'post',
					dataType : 'json'
				}
			});
			_self.set('workShopStore',store);
			_self._initWorkShopSelect();
		},
        /**
         * 初始化线别下拉选
         */
		_initLineSelect:function(){
			var _self = this, lineStore = _self.get('lineStore');
			$("#lineId").empty();
			var render = $('#lineId');
			var valueField = $('#line');
			var select = new Select.Select({
		          render:render,
		          valueField:valueField,
		          multipleSelect:true,
		          store:lineStore
		    });
		    select.render();
		    _self.set("lineSelect",select);
		    _self._initCssOfSelect();
		},
		//初始化车间下拉选
		_initWorkShopSelect:function(){
			var _self = this, workShopStore = _self.get('workShopStore');
			$("#workShopId").empty();
			var render = $('#workShopId');
			var valueField = $('#workShop');
			var select = new Select.Select({
		          render:render,
		          valueField:valueField,
		          multipleSelect:true,
		          store:workShopStore
		    });
		    select.render();
		    _self.set("workShopSelect",select);
		    _self._initCssOfSelect();
		},
		//初始化select下拉选的样式
		_initCssOfSelect:function(){
			$(".bui-select").css("width","99%");
		    $(".bui-select span").css("border","white");
		    $(".bui-select-input").css("width","99%");
		    $(".bui-select span").css("display","none");
		    $(".bui-select-list").css("overflow-y","auto");
		    $(".bui-select-list").css("height","270px");
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
                    label : '协议名称：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="proName" id="proName"  style="width:99.5%"  />'
                },{
                    label : '施工单位：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="proCompany" id="proCompany" style="width: 225px;"/>'
                },{
                    label : '所属线别：',
//                  redStarFlag : true,
                  itemColspan : 1,
                  item : '<div id="lineId" >'+
                	  '<input  name="line" id="line" style="width:99%;height:27px;" readonly="readonly" type="hidden"/>'+
                	  '</div>'
              },{
                    label : '责任车间：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<div id="workShopId" >'+
              	  '<input  name="workShop" id="workShop" style="width:99%;height:27px;" readonly="readonly" type="hidden"/>'+
            	  '</div>'
                },{
                    label : '开工日期：',
//                  redStarFlag : true,
	                itemColspan : 1,
	                item : '<input type="text" name="beginDate" id="beginDate" class="calendar" style="width: 99%;height:27px;" readonly/>'
              },{
	                label : '竣工日期：',
	//              redStarFlag : true,
	                itemColspan : 1,
	                item : '<input type="text" name="overDate" id="overDate" class="calendar" style="width: 99%;height:27px;" readonly/>'
              },{
                    label : '工程地点：',
//                  redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="proPlace" id="proPlace" style="width: 99%;"/>'
              },{
                  label : '是否签订技术服务合同：',
//                redStarFlag : true,
                  itemColspan : 1,
                  item : '<select type="text" name="isContract" id="isContract" style="width: 99%;">'+
                  '<option value="">请选择</option>'+
                  '<option value="是">是</option>'+
                  '<option value="否">否</option></select>'
            },{
                  label : '上传附件：',
                  itemColspan : 2,
                  item : '<div name="uploadfile" id="uploadfile" style="height:100px;overflow-y:auto"></div>'
              }
            ];
            var form = new FormContainer({
                id : 'constructProtocolAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            elAttrs : {value: {id:"constructProtocolAdd"}},
            elCls:{vale:'constructProtocolAdd_Dialog'},
            title:{value:'新增'},
            width:{value:660},
            height:{value:340},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            success:{
                value : function(){
                	  var _self = this;
                      var formAdd = _self.getChild('constructProtocolAddForm');
                      //获取上传文件
              		var uploadFileArr = _self._getUploadFileData();
                      //验证不通过
  	        		if(!formAdd.isValid()){
  	        			return;
  	        		}
  	        		var data = formAdd.serializeToObject();
  	        		data.uploadFileArr=JSON.stringify(uploadFileArr);
  	        		data.collectionName=_self.get('collectionName');
  	        		data.line=_self.get('selectLines');
  	        		data.workShop=_self.get('selectWorkShop');
  	        		data.userId=_self.get('userId');
  	        		var pl = new PostLoad({
          				url : '/zuul/kmms/constructProtocolAction/addDoc',
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
        }
    });
    return ConstructProtocolAdd;
});