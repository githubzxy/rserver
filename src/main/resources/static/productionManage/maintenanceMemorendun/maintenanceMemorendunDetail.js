/**
 * 维修备忘录详情
 * @author zhouxingyu
 * @date 19-3-25
 */
define('kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunDetail',['bui/common','common/form/FormContainer',
	'common/uploader/ViewUploader','common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		Uploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var maintenanceMemorendunDetail = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
			var _self = this;
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
				url:'/kmms/commonAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						   $("#formContainer #checkDate").val(data.checkDate);
						   $("#formContainer #checker").val(data.checker);
						   $("#formContainer #problemFrom").val(data.problemFrom);
						   $("#formContainer #dutyDepartment").val(data.dutyDepartment);
						   $("#formContainer #endDate").val(data.endDate);
						   $("#formContainer #problemInfo").val(data.problemInfo);
						   $("#formContainer #changeInfo").val(data.changeInfo);
						   $("#formContainer #result").val(data.result);
						   $("#formContainer #note").val(data.note);
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
	                    label : '所属部门：',
	                    itemColspan : 2,
	                    item : '<input type="text" name="dutyDepartment" id="dutyDepartment" style="width:99.5%" readonly/>',
	                },{
	                    label : '检查日期：',
	                    itemColspan : 1,
	                    item : '<input type="text"  name="checkDate" id="checkDate" style="width:99%" readonly data-rules="{required:true}" readonly/>',
	                },{
	                    label : '检查人：',
	                    itemColspan : 1,
	                    item : '<input type="text" name="checker" id="checker" style="width:99%"  data-rules="{required:true}" readonly/>',
	                },{
	                    label : '问题处所：',
	                    itemColspan : 1,
	                    item : '<input type="text" name="problemFrom" id="problemFrom" style="width:99%" data-rules="{required:true}" readonly/>',
	                },{
	                    label : '整改时限：',
	                    itemColspan : 1,
	                    item : '<input type="text" name="endDate" id="endDate" style="width:99%"  readonly/>',
	                },{
	                    label : '发现问题：',
	                    itemColspan : 2,
	                    item : '<textarea style="border:none;width: 99.5%;resize: none;" id="problemInfo" name="problemInfo" maxlength="900"readonly />',
	                },{
	                    label : '整改情况：',
	                    itemColspan : 2,
	                    item : '<textarea  style="border:none;width: 99.5%;resize: none;" name="changeInfo" id="changeInfo" style="width:99%" readonly/>',
	                },{
	                    label : '整改结果：',
	                    itemColspan : 2,
	                    item : '<textarea  style="border:none;width: 99.5%;resize: none;" name="result" id="result" style="width:99%" readonly/>',
	                },{
	                    label : '备注：',
	                    itemColspan : 2,
	                    item : '<textarea style="border:none;width: 99.5%;resize: none;height:100px" id="note" name="note" maxlength="900" readonly />',
	                }
	            ];
			var form = new FormContainer({
				id : 'shiftShow',
				colNum : colNum,
				formChildrens : childs,
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'shiftInfoDialog'},
			title:{value:'维修工作备忘录信息'},
            width:{value:650},
            height:{value:485},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return maintenanceMemorendunDetail;
});