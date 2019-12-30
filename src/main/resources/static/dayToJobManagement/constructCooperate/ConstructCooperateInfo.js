/**
 * 机房机架详情模块
 */
define('kmms/dayToJobManagement/constructCooperate/ConstructCooperateInfo',['bui/common','common/form/FormContainer',
	'common/data/PostLoad'],function(r){
	var BUI = r('bui/common'),
		FormContainer= r('common/form/FormContainer');
	var ConstructCooperateInfo = BUI.Overlay.Dialog.extend({
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
				url:'/kmms/constructCooperateAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(data){
					if(data){
						 $("#formContainer #date").val(data.date);
	                        $("#formContainer #line").val(data.line);
	            		    $('#formContainer #workShop').val(data.workShop);
	            		    $('#formContainer #depart').val(data.depart);
	            		    $('#formContainer #local').val(data.local);
	            		    $('#formContainer #constructUnit').val(data.constructUnit);
	            		    $('#formContainer #constructPro').val(data.constructPro);
	            		    $('#formContainer #constructContent').val(data.constructContent);
	            		    $('#formContainer #cooperMan').val(data.cooperMan);
	            		    $('#formContainer #remark').val(data.remark);
	            		    $('#formContainer #cableSituation').val(data.cableSituation);
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
                              label : '日期：',
                              itemColspan : 1,
                              item : '<input type="text"  name="date" id="date"  style="width:99.5%" readonly />'
                          },{
                              label : '线别：',
                              itemColspan : 1,
                              item : '<input type="text" name="line" id="line" readonly/>'
                          },{
                              label : '车间：',
                              itemColspan : 1,
                              item : '<input type="text" name="workShop" id="workShop" style="width: 225px;" readonly/>'
                          },{
                              label : '部门：',
                              itemColspan : 1,
                              item : '<input type="text"  name="depart" id="depart" style="width: 225px;" readonly/>'
                          },{
                              label : '位置：',
                            itemColspan : 1,
                            item : '<input type="text" name="local" id="local" readonly/>'
                        },{
                            label : '施工单位：',
                          itemColspan : 1,
                          item : '<input type="text" name="constructUnit" id="constructUnit" readonly/>'
                      },{
                          label : '施工项目：',
                        itemColspan : 1,
                        item : '<input type="text" name="constructPro" id="constructPro" readonly />'
                      },{
                          label : '配合人员：',
                        itemColspan : 1,
                        item : '<input type="text"   name="cooperMan" id="cooperMan" style="width:99%;height:27px;" readonly="readonly" />'
                      },{
                          label : '施工内容：',
                        itemColspan : 2,
                        item : '<textarea type="text" name="constructContent" id="constructContent" readonly/>'
                      },{
                          label : '光电缆情况：',
                        itemColspan : 2,
                        item : '<textarea type="text" name="cableSituation" id="cableSituation" readonly/>'
                      },{
                          label : '备注：',
                        itemColspan : 2,
                        item : '<textarea type="text" name="remark" id="remark" readonly/>'
                    }
            ];
			var form = new FormContainer({
				id : 'constructCooperateShow',
				colNum : colNum,
				formChildrens : childs,
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'constructCooperateInfoDialog'},
			title:{value:'通知详细信息'},
            width:{value:640},
            height:{value:400},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return ConstructCooperateInfo;
});