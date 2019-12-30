/**
 * 详情模块
 */
define('kmms/dailyShift/fault/faultInfo',['bui/common','bui/calendar','common/form/FormContainer',
	'common/uploader/ViewUploader','common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		Calendar = r('bui/calendar'),
		Uploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var FaultInfo = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
			var _self = this;
//			_self._initDate();
//        	_self._initFaultType();
//			_self._initOrganizationPicker();
			//显示数据
			_self._getShowData();
		},
		bindUI : function(){
			var _self = this;
			//定义按键
			var buttons = [
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
		/**
		 * 获取显示数据
		 */
		_getShowData : function(){
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/faultManagementAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						$("#formContainer #location").val(data.location);
						$("#formContainer #date").val(_self._timestampToTime(data.date));
						$("#formContainer #faultType").val(_self._setFaultType(data.faultType));
						$("#formContainer #occurrenceTime").val(_self._timestampToTime(data.occurrenceTime));
						$("#formContainer #recoveryTime").val(_self._timestampToTime(data.recoveryTime));
                        $("#formContainer #departmentName").val(data.departmentName);
                        $("#formContainer #orgName").val(data.orgName);
                        $("#formContainer #mainContent").val(data.mainContent);
                        $("#formContainer #createDate").val(_self._timestampToTime(data.createDate));
                        $("#formContainer #createUserName").val(data.createUserName);
                        var fileCols = $("#fileCols").val();
                        fileCols.split(",").forEach(function(col){
                        	$("#"+col).html(_self._renderUploadView(data[col]));
						});
                    }
          		}
			});
		},
		
		//时间戳转时间
		_timestampToTime : function(timestamp) {
			if(timestamp){
				var date = new Date(timestamp);
				Y = date.getFullYear() + '-';
				M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
				D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
				h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
		        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
		        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		        return Y+M+D+h+m+s;
			}else{
				return "";
			}
	    },
	    
	    /**
         * 初始化组织机构选择
         * @private
         */
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#formContainer #orgSelectName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:230,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
            
            var departmentPicker = new OrganizationPicker({
                trigger : '#formContainer #departmentName',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:230,
                height:200
            });
            departmentPicker.render();
            _self.set('departmentPicker',departmentPicker);
        },
        
        _initDate: function () {
            var _self = this;
            var date = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #date',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #date'
            });
            var occurrenceTime = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #occurrenceTime',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #occurrenceTime'
            });
            var endTime = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #recoveryTime',
                showTime: true,
                autoRender: true,
                textField:'#formContainer #recoveryTime'
            });
            _self.set('date', date);
            _self.set('occurrenceTime', occurrenceTime);
            _self.set('recoveryTime', recoveryTime);
        },
        
        _setFaultType : function(value){
        	if(value == "1") {
				return "事故";
			} else if(value == "2") {
				return "故障";
			} else if(value == "3") {
				return "障碍";
			}
        },
        _initFaultType : function(){
        	$("#formContainer #faultType").append("<option value='1'>事故</option>");
			$("#formContainer #faultType").append("<option value='2'>故障</option>");
			$("#formContainer #faultType").append("<option value='3'>障碍</option>");
        },
		
		_renderUploadView(file){
			var _self = this,html="";
			file.forEach(function(f){
                html+= '<div class="success"><label id="' + f.id + '" class="fileLabel" title=' + f.name + '>' + f.name + '</label><span style="float: right;"><a href="' + _self.get('downloadUrl') + f.path+'&fileName='+f.name + '">下载</a>&nbsp;' +
                    '<a href="' + _self.get('previewUrl') + f.path + '" target="_blank">预览</a></span></div>';
            });
			return html;
		},
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
            var childs = [
                {
                    label : '处所：',
                    itemColspan : 2,
                    item : '<input type="text"  name="location" id="location" style="width:99.5%" readonly>'+
                        '<input type="hidden"  name="fileCols" id="fileCols" ' +
                        'value="uploadfile">'
                },{
                    label : '日期：',
                    itemColspan : 1,
                    item : '<input type="text"  name="date" id="date" style="width:99%" readonly>'
                },{
                    label : '类别：',
                    itemColspan : 1,
                    item : '<input type="text"  name="faultType" id="faultType" style="width:99%;" readonly>'
                },{
                    label : '发生时间：',
                    itemColspan : 1,
                    item : '<input type="text"  name="occurrenceTime" id="occurrenceTime" style="width:99%" readonly>'
                },{
                    label : '恢复时间：',
                    itemColspan : 1,
                    item : '<input type="text"  name="recoveryTime" id="recoveryTime" style="width:99%" readonly>'
                },{
                    label : '责任部门：',
                    itemColspan : 1,
                    item : '<input type="text" name="departmentName" id="departmentName" style="width:99%" readonly>'
                },{
                    label : '所属部门：',
                    itemColspan : 1,
                    item : '<input type="text"  name="orgName" id="orgName" style="width:99%" readonly>'
                },{
                    label : '主要内容：',
                    itemColspan : 2,
                    item : '<textarea  name="mainContent" id="mainContent" style="height:20px;width:99.5%" readonly></textarea>'
                },{
                    label : '创建时间：',
                    itemColspan : 1,
                    item : '<input type="text"  name="createDate" id="createDate" style="width:99%" readonly>'
                },{
                    label : '创建人：',
                    itemColspan : 1,
                    item : '<input type="text"  name="createUserName" id="createUserName" style="width:99%" readonly>'
                },{
                    label : '附件：',
                    itemColspan : 2,
                    item : '<div name="uploadfile" id="uploadfile" style="height:70px;overflow-y :auto"></div>'
                }
            ];
			var form = new FormContainer({
				id : 'faultInfoShow',
				colNum : colNum,
				formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'faultInfoDialog'},
            previewUrl:{value:'/pageoffice/openPage?filePath='},
            downloadUrl:{value:'/kmms/faultManagementAction/download?path='},
			title:{value:'详细信息'},
            width:{value:610},
            height:{value:390},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return FaultInfo;
});