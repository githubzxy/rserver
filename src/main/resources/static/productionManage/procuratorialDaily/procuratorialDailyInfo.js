/**
 * 详情
 */
define('kmms/productionManage/procuratorialDaily/procuratorialDailyInfo',
								['bui/common','common/form/FormContainer',
	'common/uploader/ViewUploader','common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		Uploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var procuratorialDailyInfo = BUI.Overlay.Dialog.extend({
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
				url:'/kmms/procuratorialDailyAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						   $("#formContainer #workshop").val(data.workshop);
	            		    $('#formContainer #department').val(data.department);
	            		    $("#formContainer #date").val(data.date);
	                        $("#formContainer #inspector").val(data.inspector);
	            		    $('#formContainer #site').val(data.site); 
	            		    $("#formContainer #content").val(data.content);
	                        $("#formContainer #problem").val(data.problem);
	            		    $('#formContainer #require').val(data.require); 
	            		    $("#formContainer #condition").val(data.condition);
	                        $("#formContainer #functionary").val(data.functionary);
	            		    $('#formContainer #remark').val(data.remark);
                    }
          		}
			});
		},
		/**
		 * 初始化FormContainer
		 */
		_initFormContainer : function(){
			var _self = this;
			var colNum = 2;
		            var childs = [
							{
							    label : '填报车间：',
							    itemColspan : 1,
							    item : '<input type="text" name="workshop" id="workshop" readonly style="width:99%" />'
							},{
							    label : '填报部门：',
							    itemColspan : 1,
							    item : '<input type="text" name="department" id="department" readonly style="width:99%" />'
							},{
							    label : '检查日期：',
							    itemColspan : 1,
							    item : '<input type="text" name="date" id="date"  readonly  style="width:99%"/>'
							},{
							    label : '检查人：',
							    itemColspan : 1,
							    item : '<input type="text" name="inspector" id="inspector" readonly  style="width:99%" />'
							},{
							    label : '检查地点：',
							    itemColspan : 2,
							    item : '<input type="text" name="site" id="site" readonly  style="width:99%" />'
							},{
							    label : '检查内容：',
							    itemColspan : 2,
							    item : '<textarea  name="content" id="content" readonly style="width:99.5%;height:50px"  />'
							},{
							    label : '发现问题：',
							    itemColspan : 2,
							    item : '<textarea  name="problem" id="problem" readonly style="width:99.5%;height:50px"  />'
							},{
							    label : '整改要求：',
							    itemColspan : 2,
							    item : '<textarea  name="require" id="require" readonly style="width:99.5%;height:50px" />'
							},{
							    label : '整改落实情况：',
							    itemColspan : 2,
							    item : '<textarea name="condition" id="condition" readonly style="width:99.5%;height:50px" />'
							},{
							    label : '整改负责人：',
							    itemColspan : 2,
							    item : '<input type="text" name="functionary" readonly id="functionary" style="width:99%" />'
							},{
							    label : '备注：',
							    itemColspan : 2,
							    item : '<textarea name="remark" id="remark" readonly style="width:99.5%;height:50px"/></textarea>'
							}
            ];
			var form = new FormContainer({
				id : 'procuratorialDailyInfoShow',
				colNum : colNum,
				formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'procuratorialDailyInfoDialog'},
			title:{value:'详细信息'},
            width:{value:630},
            height:{value:540},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return procuratorialDailyInfo;
});