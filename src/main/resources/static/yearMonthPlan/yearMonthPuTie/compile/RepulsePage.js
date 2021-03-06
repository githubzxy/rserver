/**
 * 普铁年月报表-车间/段科室编制/执行界面 '退回'按钮控件
 */
define('yearMonthPlan/yearMonthPuTie/compile/RepulsePage', [
	'bui/overlay',
	'common/data/PostLoad',
	'common/form/FormContainer',
	],function(require) {
	var Overlay = require('bui/overlay'),
	PostLoad = require('common/data/PostLoad'),
	FormContainer = require('common/form/FormContainer');
	
	var RepulsePage = Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._getForm());
		},
		renderUI : function(){
			var _self = this;
			var buttons = [{
				text:'确定',
				elCls:'button _saveData',
				handler : function(){
					var _self = this,
					success = _self.get('success');
					if(success){
						success.call(_self);
					}
				}
			},
			{
				text:'取消',
				elCls : 'button',
				handler : function(dialog,btn){
					if(this.onCancel() !== false){
						this.close();
					}
				}
			}];
			_self.set('buttons',buttons);
		},
		/**
		 * 初始化弹框Form
		 */
		_getForm : function(){
			var _self = this;
			var form = new FormContainer({
				id : 'repulseForm',
				colNum : 2,
				formChildrens : [
					{
						label : '退回原因',
						redStarFlag : true,
						itemColspan : 2,
						item : '<textarea placeholder="最多输入500个字。" name="reasons" id="reasons" style="width:99.5%;height:200px;" data-rules="{required:true, maxlength:500}"></textarea>'
					}
				]
			});
			return form;
		},
	}, {
		ATTRS : {
			id:{value:'repulseDialog'},
			title : {value:'退回'},
			workAreaIds : {},
			url : {},
			width : {value:600},
			closeAction:{value:"destroy"},
			mask : {value : true},
			events:{
	    		value: {'repulseBtn': true}
	    	},
			success:{
				value:function(){
					var _self = this,form = _self.getChild('repulseForm',true);
					if(!form.isValid()){
						return;
					}
					var data = form.serializeToObject();
					data.workAreaIds = _self.get('workAreaIds');
					var postLoad = new PostLoad({
						el : _self.get('el'),
						url : _self.get('url'),
						loadMsg : '退回中...'
					}); 
					postLoad.load(data,function(result){
						if(result!=null){
							_self.fire("repulseBtn",{
								result : result
							});
						}
					});
				}
			},
		}
	});
	return RepulsePage;
});