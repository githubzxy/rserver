/**
 * 普铁年月报表-车间编制界面 主页模块，包含上查询下列表。
 */
seajs.use('yearMonthPlan/common/commonTypeMap.js');
seajs.use('yearMonthPlan/common/common.js');
define('yearMonthPlan/yearMonthPuTie/compile/WorkShopPuTieYearMonth',[
	'bui/common','bui/data','bui/grid',
	'bui/extensions/treegrid',
	'common/bar/MsgTbar',
	'common/data/PostLoad',
	'common/date/YearPicker',
	'common/search/SearchForm',
	'common/container/FieldsetContainer',
	'yearMonthPlan/yearMonthPuTie/compile/RepulsePage',
	'yearMonthPlan/yearMonthPuTie/compile/WorkAreaPuTieYearMonthEdit',
	'yearMonthPlan/yearMonthPuTie/compile/WorkShopPuTieYearMonthAttach'
	],function(require){
	var BUI = require('bui/common'),Component = BUI.Component,
	Data = require('bui/data'),Grid = require('bui/grid'),
	TreeGrid = require('bui/extensions/treegrid'),
	MsgTbar = require('common/bar/MsgTbar'),
	PostLoad = require('common/data/PostLoad'),
	YearPicker = require('common/date/YearPicker'),
	SearchForm = require('common/search/SearchForm'),
	FieldSet = require('common/container/FieldsetContainer'),
	RepulsePage = require('yearMonthPlan/yearMonthPuTie/compile/RepulsePage'),
	WorkAreaPuTieYearMonthEdit = require('yearMonthPlan/yearMonthPuTie/compile/WorkAreaPuTieYearMonthEdit'),
	WorkShopPuTieYearMonthAttach = require('yearMonthPlan/yearMonthPuTie/compile/WorkShopPuTieYearMonthAttach'),
	SEARCH_FORM_ID = 'searchForm',
	SEARCH_GRID_ID = 'searchGrid';
	
	var WorkShopPuTieYearMonth = Component.Controller.extend({
		initializer : function() {
			var _self = this;
			var searchForm = _self._initSerchForm();
			var up = new FieldSet({id : 'searchFormFieldSet',title : '查询',items:[searchForm]});
			_self.addChild(up);
			var searchGrid = _self._initGrid();
			var down = new FieldSet({id : 'searchGridFieldSet',title : '列表',items:[searchGrid]});
			_self.addChild(down);
		},
		renderUI : function() {
			var _self = this;
			_self._calGridSize();
			// 初始化年份控件
			_self._initYearPicker();
		},
		/**
		 *  初始化年份控件
		 */
		_initYearPicker : function(){
			var _self = this;
			var yearPicker = new YearPicker({
				trigger : '#reportYear',
				year : new Date().getFullYear(),
				autoHide : true,
				align : {
					points:['bl','tl']
				},
				success:function(){
					var year = this.get('year');
					$('#reportYear').val(year);
					this.hide();
				},
				cancel:function(){
					this.hide();
				},
			});
			yearPicker.render();
			//默认选中当前年份
			$('#reportYear').val((new Date).getFullYear())
			 //加载表格数据
			_self.get('store').load({year : $('#reportYear').val()});
		},
		bindUI : function() {
			var _self = this,store = _self.get('store'),
			searchForm = _self.getChild(SEARCH_FORM_ID, true),
			table = _self.getChild(SEARCH_GRID_ID,true),
			searchGrid = _self.get('searchGrid');
			$(window).on('resize',function(){_self._calGridSize();});
			
			// 点击上方查询按钮刷新列表
			searchForm.on('formSearch',function(e){
				var param = e.param;
				store.load(param);
			});
			//点击通过、退回、详情、上报
			searchGrid.on('cellclick',function(ev){
				var target = $(ev.domTarget);
				//通过
				if(target.hasClass('approval')){
					_self._approval(ev);
				}
				//退回
				if(target.hasClass('repulse')){
					_self._repulse(ev);
				}
				//工区详情
				if(target.hasClass('workAreaDetails')){
					_self._workAreaDetails(ev);
				}
				//点击上报
				if(target.hasClass('report')){
					var reportId=ev.record.id;
					BUI.Message.Confirm('确认上报选中的数据？',function(){
						_self._report(reportId);
		    		},'question');
				}
				//车间详情
				if(target.hasClass('workShopDetails')){
					_self._workShopDetails(ev);
				}
				//车间汇总
				if(target.hasClass('summary')){
					_self._summary(ev,store);
				}
				//车间执行部分
				if(target.hasClass('execute')){
					window.parent.menuClick("车间年月表执行","/pageKmms/page/workShopPuTieYearMonthExecute",null);
				}
			});
			//点击批量通过
			$('body').on('click','.approvalBatch',function(){
				_self._approvalBatch(table);
			});
			//点击批量退回
			$('body').on('click','.repulseBatch',function(){
				_self._repulseBatch(table);
			});
		},
		/**
		 * 通过
		 */
		_approval : function(ev){
			var _self = this, tbar = _self.get('tbar');
			var workAreaIds=ev.record.id;
			BUI.Message.Confirm('确认通过选中的数据？',function(){
				var postLoad = new PostLoad({
					el:_self.get('el'),
					url:'/kmms/yearMonthPuTieWSAction/auditPass.cn',
					loadMsg:'通过中...'
				});
				postLoad.load({workAreaIds:workAreaIds},function(result){
					if(result.status=='1'){
						tbar.msg({msg:result.msg,status:result.status});
					}else{
						tbar.msg({msg:result.msg});
					}
					_self.get('store').load();
				});
			},'question');
		},
		/**
		 * 退回
		 */
		_repulse : function(ev){
			var _self = this, tbar = _self.get('tbar');
			var workAreaIds=ev.record.id;
			var repulsePage = new RepulsePage({
				workAreaIds : workAreaIds,
				url : '/kmms/yearMonthPuTieWSAction/auditNotPass.cn',
				loadMsg:'退回中...'
			});
			repulsePage.show();
			repulsePage.on('repulseBtn',function(e){
				repulsePage.close();
				tbar.msg({msg:e.result.msg,status:e.result.status});
				_self.get('store').load();
			});
		},
		/**
		 * 工区详情
		 */
		_workAreaDetails : function(ev){
			var _self = this;
			var workAreaIds=ev.record.id;
			var workAreaPuTieYearMonthEdit=new WorkAreaPuTieYearMonthEdit({
				id:workAreaIds,
				title:'查看工区报表',
				filePath : _self.get('filePath')
			})
			workAreaPuTieYearMonthEdit.show()
		},
		/**
		 * 车间详情
		 */
		_workShopDetails: function(ev){
			var _self = this;
			var workShopIds=ev.record.id;
			var workShopDetail=new WorkShopPuTieYearMonthAttach({
				id:workShopIds,
				title:'查看车间报表',
				filePath : _self.get('filePath')
			})
			workShopDetail.show()
		},
		/**
		 * 车间汇总
		 */
		_summary : function(ev,store){
			var _self = this;
			var workShopIds=ev.record.id;
			// 首先生成遮罩层,防止多次点击
			var workShopGather=new WorkShopPuTieYearMonthAttach({
				id:workShopIds,
				title:'车间汇总工区报表数据',
				isWorkShopGather:true,
				filePath : _self.get('filePath'),
				user : _self.get('user'),
				listeners : {
					closing : function(){//关闭窗口时刷新列表
						store.load();
					}
	   			},
			})
			workShopGather.show()
		},
		/**
		 * 批量通过
		 */
		_approvalBatch : function(table){
			var _self = this, tbar = _self.get('tbar');
			var selections = table.getCheckedLeaf();
			var s=table.getCheckedNodes();
			if(selections.length==0){
				tbar.msg({msg:'请选择至少一条数据进行操作！'});
				return;
			}
			var workAreaIds='';
			for (var i = 0; i < selections.length; i++){
				//判断所选数据是否已审核
				if(selections[i].record.status!=YearMonthWorkAreaStatus.WRITE_WORKAREA_REPORT){
					tbar.msg({msg:'只能对工区上报_编制状态的数据进行操作！'});
					return;
				}
				workAreaIds+=selections[i].id+',';
			}
			BUI.Message.Confirm('确认通过选中的数据？',function(){
				var postLoad = new PostLoad({
					el:_self.get('el'),
					url:'/kmms/yearMonthPuTieWSAction/auditPass.cn',
					loadMsg:'通过中...'
				});
				postLoad.load({workAreaIds:workAreaIds},function(result){
					if(result.status=='1'){
						tbar.msg({msg:result.msg,status:result.status});
					}else{
						tbar.msg({msg:result.msg});
					}
					_self.get('store').load();
				});
			},'question');
		
		},
		/**
		 * 批量退回
		 */
		_repulseBatch : function(table){
			var _self = this, tbar = _self.get('tbar');
			var selections = table.getCheckedLeaf();
			if(selections.length==0){
				tbar.msg({msg:'请选择至少一条数据进行操作！'});
				return;
			}
			var workAreaIds='';
			for (var i = 0; i < selections.length; i++){
				//判断所选数据是否已审核
				if(selections[i].record.status!=YearMonthWorkAreaStatus.WRITE_WORKAREA_REPORT){
					tbar.msg({msg:'只能对工区上报_编制状态的数据进行操作！'});
					return;
				}
				workAreaIds+=selections[i].id+',';
			}
			var repulsePage = new RepulsePage({
				workAreaIds : workAreaIds,
				url : '/kmms/yearMonthPuTieWSAction/auditNotPass.cn',
				loadMsg:'退回中...'
			});
			repulsePage.show();
			repulsePage.on('repulseBtn',function(e){
				repulsePage.close();
				tbar.msg({msg:e.result.msg,status:e.result.status});
				_self.get('store').load();
			});
		},
		/**
		 * 上报
		 */
		_report:function(reportId){
			var _self = this, tbar = _self.get('tbar');
			var postLoad = new PostLoad({
				el:_self.get('el'),
				url:'/kmms/yearMonthPuTieWSAction/reportToDuan.cn',
				loadMsg:'上报中...'
			});
			postLoad.load({reportId:reportId},function(result){
				if(result.status=='1'){
					tbar.msg({msg:result.msg,status:result.status});
				}else{
					tbar.msg({msg:result.msg});
				}
				_self.get('store').load();
			});
		},
		/**
		 * 根据窗口高度和上面查询区域高度计算下方列表高度和宽度
		 */
		_calGridSize : function(){
			var _self = this;
			var searchGrid = _self.getChild(SEARCH_GRID_ID,true);
			var searchForm = _self.getChild(SEARCH_FORM_ID, true);
			var formHeight = searchForm.get('el').outerHeight(true),
			extHeight = formHeight - searchForm.get('el').height();
			searchGrid.set('height', $(window).height() - 100 - formHeight - extHeight);
			searchGrid.set('width', searchForm.get('el').width());
		},
		/**
		 * 上查询模块
		 */
		_initSerchForm : function() {
			var _self = this;
	        var searchForm = new SearchForm({
	        	id : SEARCH_FORM_ID,
				items : [{
	  				label : '名称',
	  				item : '<input name="name" id="reportName" type="text"></input>' 
	  			},{
	  				label : '年份',
	  				item : '<input name="year" type="text" id="reportYear" readonly="readonly" />'
	  			},{
	  				label : '状态',
	  				item : '<select name="status" id="reportStatus" >' +
	  							'<option value="">请选择</option>' + 
	  							'<option value="'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_AUDIT+'">'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_AUDIT_NAME+'</option>' + 
	  							'<option value="'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_FAIL+'">'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_FAIL_NAME+'</option>' + 
	  							'<option value="'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_PASS+'">'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_PASS_NAME+'</option>' + 
	  							'<option value="'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_REPORT+'">'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_REPORT_NAME+'</option>' + 
	  							'<option value="'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_SEGMENT_FAIL+'">'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_SEGMENT_FAIL_NAME+'</option>' + 
	  							'<option value="'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_SEGMENT_PASS+'">'+YearMonthWorkAreaStatus.WRITE_WORKSHOP_SEGMENT_PASS_NAME+'</option>' + 
	  							
	  							'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_AUDIT+'">'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_AUDIT_NAME+'</option>' + 
	  							'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_FAIL+'">'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_FAIL_NAME+'</option>' + 
	  							'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_PASS+'">'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_PASS_NAME+'</option>' + 
	  							'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT+'">'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT_NAME+'</option>' + 
//	  							'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_FAIL+'">'+YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_FAIL_NAME+'</option>' + 
//	  							'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_PASS+'">'+YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_PASS_NAME+'</option>' + 
	  						'</select>'
	  			}]
			});
			return searchForm;
		},
		/**
		 * 下列表表头模块
		 */
		_initColumns : function(){
			var _self = this;
			/*表格列设置*/
			var columns = [{
				title : '年月表名称',
				dataIndex : 'name',
				elCls : 'left',
				width : '10%'
			},{
				title:'年份',
				dataIndex:'year',
				elCls : 'center',
				width: '10%'
			},{
				title : '所属部门',
				dataIndex : 'orgName',
				elCls : 'center',
				width : '10%'
			},{
				title:'上报时间',
				dataIndex:'reportTime',
				elCls : 'center',
				width: '10%',
				renderer:Grid.Format.datetimeRenderer,
			},{
				title : '退回原因',
				dataIndex : 'failReason',
				elCls : 'center',
				width : '20%'
			},{
				title : '审核状态',
				dataIndex : 'status',
				elCls : 'center',
				width : '10%',
				renderer : function(value,obj){
					return YearMonthWorkAreaStatus.statusName(value);
				}
			},{
				title:'操作',
				elCls : 'center',
				dataIndex : 'operate',
				width: '10%',
				renderer:function(value,obj){
					//根据字段attachPath8_1等决定是否为空决定 车间“上报”及“详情”的展示
					var btnValid = (obj.attachPath8_1||obj.attachPath8_2||obj.attachPath8_3||obj.attachPath8_4)?'inline-block' : 'none';
					if(obj.status == YearMonthWorkAreaStatus.WRITE_WORKAREA_NOTWRITED){
						return "";
					}
					if(obj.status == YearMonthWorkAreaStatus.WRITE_WORKAREA_REPORT){
						return  '<span class="grid-command approval">通过</span>&nbsp;&nbsp;'
								+'<span class="grid-command repulse">退回</span>&nbsp;&nbsp;'
								+'<span class="grid-command workAreaDetails">详情</span>';
					}
					if(obj.leaf&&obj.status != YearMonthWorkAreaStatus.WRITE_WORKAREA_REPORT){
						return '<span class="grid-command workAreaDetails">详情</span>';
					}
					if(!obj.leaf&&parseInt(obj.status) >= parseInt(YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_AUDIT)){
						return '<span class="grid-command workShopDetails">详情</span>&nbsp;&nbsp;'+
						'<span id="execute" class="grid-command execute">年月表执行</span>';
					}
					if(obj.status == YearMonthWorkAreaStatus.WRITE_WORKSHOP_PASS){
						return '<span class="grid-command summary">汇总</span>&nbsp;&nbsp;'
					 			+'<span class="grid-command report" style="display:'+btnValid+'">上报</span>&nbsp;&nbsp;'
					 			+'<span class="grid-command workShopDetails" style="display:'+btnValid+'">详情</span>';
					}
					if(obj.status==YearMonthWorkAreaStatus.WRITE_WORKSHOP_SEGMENT_PASS||
							obj.status==YearMonthWorkAreaStatus.WRITE_WORKSHOP_SEGMENT_FAIL||
							obj.status==YearMonthWorkAreaStatus.WRITE_WORKSHOP_REPORT){
						return '<span class="grid-command workShopDetails">详情</span>';
					}
				},
			}];
			return columns;
		},
		/**
		 * 获取下列表数据函数
		 */	
		_initGridStore : function(data){
			var _self = this;
			var store = new Data.TreeStore({
				proxy : {
					url : '/kmms/yearMonthPuTieWSAction/getDatas.cn',
					method : 'post',
					dataType : 'json'
				},
				params : {
					name : '',
					year : '',
					status : '',
					userOrgId : _self.get('user').organization.id,
				},
				map : {	// 节点映射
					'isdept' : 'leaf'	// 是否为叶子节点
				},
			});
			_self.set('store',store);
			return store;
		},
		/**
		 * 下查询表格函数
		 */
		_initGrid : function(){
			var _self = this,bar = new MsgTbar();
			_self.set('tbar', bar);
		    var searchGrid = new TreeGrid({
		        columns : _self._initColumns(),
				store :_self._initGridStore(),
		        checkType : 'all',
		        id:SEARCH_GRID_ID,
		        multipleSelect : false,
		        tbar: {
					items :  [{
						id : 'approvalBatch',
						btnCls : 'button button-small approvalBatch',
						text : '<i class="icon-ok"></i>批量通过',
			        },{
	                	xclass : 'bar-item-separator' // 竖线分隔符
	                },{
						id : 'repulseBatch',
						btnCls : 'button button-small repulseBatch',
						text : '<i class="icon-remove"></i>批量退回',
			        },bar.getChild('msgTips',true)
			        ]
				},
		      });
		    _self.set('searchGrid',searchGrid);
			return searchGrid;
		}
	}, {
		ATTRS : {
			filePath : {},
			user : {}
		},
	   SEARCH_GRID_ID : SEARCH_GRID_ID,
	   SEARCH_FORM_ID : SEARCH_FORM_ID
	})
	return WorkShopPuTieYearMonth;
});