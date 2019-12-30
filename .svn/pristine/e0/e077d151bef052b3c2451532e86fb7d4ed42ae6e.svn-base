/**
 * 普铁年月报表-工区编制界面 '新增'按钮控件
 */
seajs.use('yearMonthPlan/common/common.js');
define('yearMonthPlan/yearMonthPuTie/compile/WorkAreaPuTieYearMonthAdd', [
	'bui/overlay',
	'common/data/PostLoad',
	'common/date/YearPicker',
	'common/form/FormContainer',
	],function(require) {
	var Overlay = require('bui/overlay'),
	PostLoad = require('common/data/PostLoad'),
	YearPicker = require('common/date/YearPicker'),
	FormContainer = require('common/form/FormContainer');
	
	var WorkAreaYearMonthReportAdd = Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._getForm());
		},
		renderUI : function(){
			var _self = this;
			var buttons = [{
				text:'保存',
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
			$("#workAreaId").val(_self.get('user').organization.name);
			$("#userNameId").val(_self.get('user').name);
			
			// 初始化年份控件
			_self._initYearPicker();
		},
		/**
		 *  初始化年份控件
		 */
		_initYearPicker : function(){
			var yearPicker = new YearPicker({
				trigger : '#yearId',
				year : new Date().getFullYear(),
				autoHide : true,
				align : {
					points:['bl','tl']
				},
				success:function(){
					var year = this.get('year');
					$('#yearId').val(year);
					this.hide();
				},
				cancel:function(){
					this.hide();
				},
				zIndex : 1200
			});
			yearPicker.render();
		},
		/**
		 * 初始化弹框Form
		 */
		_getForm : function(){
			var _self = this;
			var date = returnFormatDate(new Date(),'yyyy-MM-dd hh:mm:ss');
			var form = new FormContainer({
				id : 'addReportForm',
				colNum : 2,
				formChildrens : [
					{
						label : '年月表名称',
						redStarFlag : true,
						itemColspan : 2,
			    		item:'<input type="text" name="name" id="reportNameId" data-rules="{required:true,maxlength:100}" style="width:99.5%"/>'
					},{
						label : '年份',
						redStarFlag : true,
						itemColspan : 1,
			    		item:   '<input name="year" type="text" id="yearId" value="'+(new Date()).getFullYear()+'" data-rules="{required:true}" readonly="readonly" />'
					},{
						label : '创建时间',
						redStarFlag : true,
						itemColspan : 1,
			    		item:'<input type="text" name="createTime" id="createTimeId" disabled="disabled" style="width:99%" value="'+date+'"/>'
					},{
						label : '填报人',
						redStarFlag : true,
						itemColspan : 1,
			    		item:'<input type="text" id="userNameId" style="width:99.5%" disabled="disabled"/>'
					},{
						label : '所属部门',
						redStarFlag : true,
						itemColspan : 1,
			    		item:'<input type="text" id="workAreaId" style="width:99.5%" disabled="disabled"/>'
					}
				]
			});
			return form;
		},
	}, {
		ATTRS : {
			user : {},
			mask:{value:true},
			id:{value:'addReportDialog'},
			title : {value:'新增年月报表'},
			closeAction:{value:"destroy"},
			events:{
	    		value: {'saveBtn': true}
	    	},
			success:{
				value:function(){
					var _self=this,form=_self.getChild('addReportForm',true);
					if(!form.isValid()){
						return;
					}
					var data = form.serializeToObject();
					data.userString = JSON.stringify(_self.get('user'));
					var postLoad = new PostLoad({
						url:'/kmms/yearMonthPuTieGQAction/addReport.cn',
						el:_self.get('el'),
						loadMsg:'保存中...'
					}); 
					postLoad.load(data,function(result){
						if(result!=null){
							_self.fire("saveBtn",{
								result : result
							});
						}
					});
				}
			},
		}
	});
	return WorkAreaYearMonthReportAdd;
});