/**
 * 普铁年月报表-工区编制界面 '修改'按钮控件
 */
define("yearMonthPlan/yearMonthPuTie/compile/WorkAreaPuTieYearMonthUpdate",[
	'bui/overlay',
	'common/date/YearPicker',
	'common/form/FormContainer',
	'common/data/PostLoad'
	],function(require){
	var Overlay = require('bui/overlay'),
	YearPicker = require('common/date/YearPicker'),
	FormContainer = require('common/form/FormContainer'),
	PostLoad = require('common/data/PostLoad');
	
	var WorkAreaYearMonthReportUpdate = Overlay.Dialog.extend({
		initializer:function(){
			var _self=this;	
			_self.addChild(_self._getForm());
		},
		renderUI:function(){
			var _self=this;
			var buttons = [{
				text:'修改',
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
			// 初始化年月表数据
			_self._initReportData();
		},
		/**
		 *  初始化年份控件
		 *  @param year 年份
		 */
		_initYearPicker : function(year){
			var yearPicker = new YearPicker({
				trigger : '#yearUpdateId',
				year : year,
				autoHide : true,
				align : {
					points:['bl','tl']
				},
				success:function(){
					var year = this.get('year');
					$('#yearUpdateId').val(year);
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
		 * 初始化年月表数据
		 */
		_initReportData:function(){
			var _self=this;
			var postLoad = new PostLoad({
				url:'/kmms/yearMonthPuTieGQAction/queryAllDataById.cn',
				el:_self.get('el'),
				loadMsg:'查询中...'
			}); 
			postLoad.load({id:_self.get('reportId')},function(result){
				if(result!=null){
					$("#reportnameId").val(result[0].NAME_);
					$("#yearUpdateId").val(result[0].YEAR_);
					$("#createTimeId").val(returnFormatDate(result[0].CREATE_TIME_,'yyyy-MM-dd hh:mm:ss'));
					$("#userNameId").val(result[0].USER_NAME_);
					$("#orgNameId").val(result[0].ORG_NAME_);
				  
					// 初始化年份控件
					_self._initYearPicker(result[0].YEAR_);
				}
			});
		},
		/**
		 * 初始化表单
		 */
		_getForm:function(){
			var _self=this;
			var form = new FormContainer({
				id:'reportUpdateForm',
				colNum:2,
			    formChildrens:[
			    	{
						label : '年月表名称',
						redStarFlag : true,
						itemColspan : 2,
			    		item:'<input type="text" name="name" id="reportnameId" data-rules="{required:true,maxlength:100}" />'+
			    			 '<input type="hidden" name="id" id="reportId"/>'
					},{
						label : '年份',
						redStarFlag : true,
						itemColspan : 1,
			    		item:'<input name="year" type="text" id="yearUpdateId" data-rules="{required:true}" readonly="readonly"/>'
					},{
						label : '创建时间',
						redStarFlag : true,
						itemColspan : 1,
			    		item:'<input type="text" name="createTime" id="createTimeId" disabled="disabled"/>'
					},{
						label : '填报人',
						redStarFlag : true,
						itemColspan : 1,
			    		item:'<input type="text" name="userName" id="userNameId" disabled="disabled"/>'
					},{
						label : '所属部门',
						redStarFlag : true,
						itemColspan : 1,
			    		item:'<input type="text" name="orgName" id="orgNameId" disabled="disabled"/>'
					},
			    ]
			});
			return form;
		},
	},{
		ATTRS:{
			id:{value:'reportUpdateDialog'},
			title:{value:'修改年月报表'},
			closeAction:{value:"destroy"},
			reportId:{},//被修改的年月表的id
			user:{},
			mask : {value : true},
			events:{
	    		value: {'updateBtn': true}
	    	},
			success:{
				value:function(){
					var _self=this,form=_self.getChild('reportUpdateForm',true);
					if(!form.isValid( )){
						return;
					}
					var data=form.serializeToObject();
					data.id = _self.get('reportId');
					data.userString = JSON.stringify(_self.get('user'));
					var postLoad =new PostLoad({
						url:'/kmms/yearMonthPuTieGQAction/updateReportById.cn',
						el:'#reportUpdateDialog',
						loadMsg:'保存中...'
					}); 
					postLoad.load(data,function(result){
						if(result!=null){
							_self.fire("updateBtn",{
								result : result
							});
						}
					});
				}
			},
		}		
	});
	return WorkAreaYearMonthReportUpdate;
});