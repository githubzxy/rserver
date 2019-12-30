/**
 * 普铁年月报表-段科室执行界面 主页模块，包含上查询下列表。
 */
seajs.use('yearMonthPlan/common/commonTypeMap.js');
seajs.use('yearMonthPlan/common/common.js');
define('yearMonthPlan/yearMonthPuTie/execute/SegmentPuTieYearMonthExecute',[
	'bui/common','bui/data','bui/grid',
	'bui/extensions/treegrid',
	'common/bar/MsgTbar',
	'common/date/YearPicker',
	'common/search/SearchForm',
	'common/org/OrgTreePicker',
	'common/container/FieldsetContainer',
	'yearMonthPlan/yearMonthPuTie/compile/RepulsePage',
	'yearMonthPlan/yearMonthPuTie/execute/WorkAreaPuTieYearMonthEditExecute',
	'yearMonthPlan/yearMonthPuTie/execute/WorkShopPuTieYearMonthAttachExecute',
	'yearMonthPlan/yearMonthPuTie/compile/SegmentPuTieYearMonthExport',
	'yearMonthPlan/yearMonthPuTie/compile/SegmentPuTieYearMonthEditTempl',
	],function(require){
	var BUI = require('bui/common'),Component = BUI.Component,
	Data = require('bui/data'),Grid = require('bui/grid'),
	TreeGrid = require('bui/extensions/treegrid'),
	MsgTbar = require('common/bar/MsgTbar'),
	PostLoad = require('common/data/PostLoad'),
	YearPicker = require('common/date/YearPicker'),
	SearchForm = require('common/search/SearchForm'),
	OrganizationPicker = require('common/org/OrgTreePicker'),
	FieldSet = require('common/container/FieldsetContainer'),
	RepulsePage = require('yearMonthPlan/yearMonthPuTie/compile/RepulsePage'),
	WorkAreaPuTieYearMonthEditExecute = require('yearMonthPlan/yearMonthPuTie/execute/WorkAreaPuTieYearMonthEditExecute'),
	WorkShopPuTieYearMonthAttachExecute = require('yearMonthPlan/yearMonthPuTie/execute/WorkShopPuTieYearMonthAttachExecute'),
	SegmentPuTieYearMonthExport = require('yearMonthPlan/yearMonthPuTie/compile/SegmentPuTieYearMonthExport'),
	SegmentPuTieYearMonthEditTempl = require('yearMonthPlan/yearMonthPuTie/compile/SegmentPuTieYearMonthEditTempl'),
	SEARCH_FORM_ID = 'searchForm',
	SEARCH_GRID_ID = 'searchGrid';
	
	var SegmentPuTieYearMonthExecute = Component.Controller.extend({
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
			// 渲染车间树
			var orgPicker = _self._getPicker();
			// 初始化年份控件
			_self._initYearPicker();
		},
		/**
		 *  初始化年份控件
		 */
		_initYearPicker : function(){
			var _self = this;
			var yearPicker = new YearPicker({
				trigger : '#reportYearId',
				year : new Date().getFullYear(),
				autoHide : true,
				align : {
					points:['bl','tl']
				},
				success:function(){
					var year = this.get('year');
					$('#reportYearId').val(year);
					this.hide();
				},
				cancel:function(){
					this.hide();
				},
			});
			yearPicker.render();
			//默认选中当前年份
			$('#reportYearId').val((new Date).getFullYear());
			//加载表格数据
			_self.get('store').load({year:$('#reportYearId').val()});
		},
		bindUI : function() {
			var _self = this,store = _self.get('store');
			var searchForm = _self.getChild(SEARCH_FORM_ID, true);
			var searchGrid = _self.getChild(SEARCH_GRID_ID,true);
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
				//车间详情
				if(target.hasClass('workShopDetails')){
					_self._workShopDetails(ev);
				}
			});
			//模板编辑
			$('body').on('click','.modelEdit',function(){
				_self._modelEdit();
			});
			//点击批量通过
			$('body').on('click','.approvalBatch',function(){
				_self._approvalBatch(searchGrid);
			});
			
			//点击批量退回
			$('body').on('click','.repulseBatch',function(){
				_self._repulseBatch(searchGrid);
			});
			
			//批量导出
			$('body').on('click','.exportBatch',function(){
				_self._exportBatch(searchGrid);
			});
			
		},
		/**
		 * 车间详情
		 */
		_workShopDetails:function(ev){
			var _self = this;	
			var workShopIds=ev.record.id;
			var workShopPuTieYearMonthAttachExecute = new WorkShopPuTieYearMonthAttachExecute({
				id:workShopIds,
				title:'查看车间报表',
				filePath : _self.get('filePath')
			})
			workShopPuTieYearMonthAttachExecute.show()
		},
		/**
		 * 工区详情
		 */
		_workAreaDetails:function(ev){
			var _self = this;	
			var workAreaIds=ev.record.id;
			var workAreaPuTieYearMonthEditExecute = new WorkAreaPuTieYearMonthEditExecute({
				id:workAreaIds,
				title:'查看工区报表',
				hasButton:false,
				filePath : _self.get('filePath'),
				user:_self.get('user')
			})
			workAreaPuTieYearMonthEditExecute.show()
		},
		/**
		 * 退回
		 */
		_repulse:function(ev){
			var _self = this,tbar = _self.get('tbar');
			var workAreaIds=ev.record.id;
			var repulsePage = new RepulsePage({
				workAreaIds:workAreaIds,
				url:'/kmms/yearMonthPutieCJExecuteAction/segmentNotPass.cn',
				loadMsg:'退回中...'
			})
			repulsePage.show();
			repulsePage.on('repulseBtn',function(e){
				repulsePage.close();
				tbar.msg({msg:e.result.msg,status:e.result.status});
				_self.get('store').load();
			});
		},
		/**
		 * 通过
		 */
		_approval:function(ev){
			var _self = this;	
			var workAreaIds=ev.record.id;
			BUI.Message.Confirm('确认通过选中的数据？',function(){
				_self._auditPass(workAreaIds);
    		},'question');
		},
		/**
		 * 模板编辑
		 */
		_modelEdit:function(){
			var _self = this;
			var segmentPuTieYearMonthEditTempl = new SegmentPuTieYearMonthEditTempl({
				title:'编辑年月报表模板（执行）',
				tplPath : _self.get('tplPath')
			});
			segmentPuTieYearMonthEditTempl.show();
		},
		/**
		 * 批量通过
		 */
		_approvalBatch:function(searchGrid){
			var _self = this,tbar = _self.get('tbar');
			var selections = searchGrid.getCheckedLeaf();
			if(selections.length==0){
				tbar.msg({msg:'请选择至少一条数据进行操作！'});
				return ;
			}
			var workAreaIds='';
			for (var i = 0; i < selections.length; i++) {
				//判断所选数据是否为段已审核
				if(selections[i].record.status==YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_FAIL
				 ||selections[i].record.status==YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_PASS){
					tbar.msg({msg:'不能对已审核过的数据进行操作！'});
					return ;
				}
				//判断所选工区数据是否为车间已审核通过并且车间为已上报状态--此处只能审核车间已上报且车间已审核过的工区数据
				if(selections[i].parent.status!=YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT){
					tbar.msg({msg:'只能审核车间已上报的数据！'});
					return;
				}else if(selections[i].parent.status==YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT &&
						selections[i].record.status!=YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_PASS){
					tbar.msg({msg:'所选数据中存在未通过车间审核的工区！'});
					return;
				}
				workAreaIds+=selections[i].id+',';
			}
			BUI.Message.Confirm('确认通过选中的数据？',function(){
				_self._auditPass(workAreaIds);
    		},'question');
		},
		/**
		 * 批量退回
		 */
		_repulseBatch:function(searchGrid){
			var _self = this,tbar = _self.get('tbar');
			var selections = searchGrid.getCheckedLeaf();
			if(selections.length==0){
				tbar.msg({msg:'请选择至少一条数据进行操作！'});
				return;
			}
			var workAreaIds='';
			for (var i = 0; i < selections.length; i++){
				//判断所选数据是否为段已审核
				if(selections[i].record.status==YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_FAIL
				 ||selections[i].record.status==YearMonthWorkAreaStatus.WORKAREA_SEGMENT_EXECUTE_PASS){
					tbar.msg({msg:'不能对已审核过的数据进行操作！'});
					return ;
				}
				//判断所选工区数据是否为车间已审核通过并且车间为已上报状态--此处只能审核车间已上报且车间已审核过的工区数据
				if(selections[i].parent.status!=YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT){
					tbar.msg({msg:'只能审核车间已上报的数据！'});
					return;
				}else if(selections[i].parent.status==YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT &&
						selections[i].record.status!=YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_PASS){
					tbar.msg({msg:'所选数据中存在未通过车间审核的工区！'});
					return;
				}
				workAreaIds+=selections[i].id+',';
			}
			var repulsePage = new RepulsePage({
				workAreaIds:workAreaIds,
				url:'/kmms/yearMonthPutieCJExecuteAction/segmentNotPass.cn',
				loadMsg:'退回中...'
			})
			repulsePage.show();
			repulsePage.on('repulseBtn',function(e){
				repulsePage.close();
				tbar.msg({msg:e.result.msg,status:e.result.status});
				_self.get('store').load();
			});
		},
		/**
		 * 批量导出
		 */
		_exportBatch:function(searchGrid){
			var _self = this,tbar = _self.get('tbar');
			var selections = searchGrid.getCheckedNodes();
			var ids='';
			var flag = true;
			for (var i = 0; i < selections.length; i++) {
				if(selections[i].leaf==false){
					ids+=selections[i].id+',';
					if(!(parseInt(selections[i].status)>=parseInt(YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT))){
						tbar.msg({msg:'请选择车间上报后的数据进行操作！'});
						flag = false;
						return ;
					}
				}
			}
			if(ids.length==0){
				tbar.msg({msg:'请至少选择一条【车间数据】进行操作！'});
				flag = false;
				return ;
			}
			if(flag){
				var segmentPuTieYearMonthExport = new SegmentPuTieYearMonthExport({
					id : ids,
					url : '/kmms/yearMonthPuTieGQExecuteAction/getAttachExecuteDataDuan.cn'
				});
				segmentPuTieYearMonthExport.show();
			}
		},
		/**
		 * 获取车间树函数
		 */
		_getPicker:function(){
			var orgPicker = new OrganizationPicker({
				trigger : '#workShopNameId',
				valueField  : '#workShopSelectId',//用于装选中的id的隐藏域，每次修改都会更新这个隐藏域的值
				rootOrgId : '',//必填项
				rootOrgText : '昆明铁路局',//必填项
				checkType : 'all',
				url : '/kmms/commonAction/getWorkShop.cn',//必填项
				align : {
					points : ['bl','tl']
				},
				width : 200,
				height : 342,
			});
			orgPicker.render();
		},
		/**
		 * 段科室审核通过
		 */
		_auditPass:function(workAreaIds){
			var _self = this,tbar = _self.get('tbar');
			var postLoad=new PostLoad({
				el:_self.get('el'),
				url:'/kmms/yearMonthPutieCJExecuteAction/segmentAuditPass.cn',
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
  	  				item : '<input name="name" id="reportNameId" type="text">' 
  	  			},{
	  				label : '部门',
	  				item : '<input id="workShopNameId" type="text" readonly="readonly"></input>'
	  					 + '<input name="orgId" type="text" id="workShopSelectId" style="display:none;"/>'
	  			},{
	  				label : '年份',
	  				item : '<input name="year" type="text" id="reportYearId" readonly="readonly"/>'
	  			},{
	  				label : '状态',
	  				item : '<select name="status" id="reportStatusId">' +
	  							'<option value="">请选择</option>'+  
								'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_AUDIT+'">'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_AUDIT_NAME+'</option>' + 
								'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_FAIL+'">'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_FAIL_NAME+'</option>' + 
								'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_PASS+'">'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_PASS_NAME+'</option>' + 
								'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT+'">'+YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT_NAME+'</option>' + 
//								'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_FAIL+'">'+YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_FAIL_NAME+'</option>' + 
//								'<option value="'+YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_PASS+'">'+YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_PASS_NAME+'</option>' + 
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
				width : '50%'
			},{
				title:'年份',
				dataIndex:'year',
				elCls : 'center',
				width: '80'
			},{
				title : '所属部门',
				dataIndex : 'orgName',
				elCls : 'center',
				width : '150'
			},{
				title:'上报时间',
				dataIndex:'reportTime',
				elCls : 'center',
				renderer:Grid.Format.datetimeRenderer,
				width: '80'
			},{
				title : '退回原因',
				dataIndex : 'failReason',
				elCls : 'center',
				width : '200'
			},{
				title : '审核状态',
				dataIndex : 'status',
				elCls : 'center',
				width : '150',
				renderer :function(value){
					return YearMonthWorkAreaStatus.statusName(value)
				} 
			},{
				title:'操作',
				elCls : 'operate',
				elCls : 'center',
				width: '150',
				renderer:function(value,obj){
					var detailsStr = '<span id="DetailsId" class="grid-command workAreaDetails">详情</span>';
					//状态为车间上报_执行或者段审核通过_执行或者段审核不通过_执行的车间数据
					if(
//							obj.status == YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT||
							obj.status == YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_PASS||
							obj.status == YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_PASS||
							obj.status == YearMonthWorkAreaStatus.WORKSHOP_SEGMENT_EXECUTE_FAIL){
						return '<span id="DetailsId" class="grid-command workShopDetails">详情</span>';
					}
					//状态为编制流程的工区数据
					if(parseInt(YearMonthWorkAreaStatus.WRITE_WORKAREA_NOTWRITED)<=
						parseInt(obj.status)&&parseInt(obj.status)<=parseInt(YearMonthWorkAreaStatus.WRITE_WORKAREA_SEGMENT_PASS)){
						return "";
					}
					//在执行流程可审核的工区数据
//					if(obj.parent.status==YearMonthWorkAreaStatus.WORKSHOP_EXECUTE_REPORT && 
//							obj.status==YearMonthWorkAreaStatus.WORKAREA_WORKSHOP_EXECUTE_PASS){
//						return '<span id="editId" class="grid-command approval">通过</span>&nbsp;&nbsp;'
//			 	   	       	   +'<span id="repulseId" class="grid-command repulse">退回</span>&nbsp;&nbsp;'
//			 	   	       	   +detailsStr;
//					}
					if(obj.leaf){
						return detailsStr;
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
					url : '/kmms/yearMonthPuTieWSAction/getSegmentExecuteDatas.cn',
					method : 'post',
					dataType : 'json'
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
					items :  
					[/*{
						id : 'approvalBatch',
						btnCls : 'button button-small approvalBatch',
						text : '<i class="icon-ok"></i>批量通过',
			        },{
	                	xclass : 'bar-item-separator' // 竖线分隔符
	                },{
						id : 'repulseBatch',
						btnCls : 'button button-small repulseBatch',
						text : '<i class="icon-remove"></i>批量退回',
			        },{
	                	xclass : 'bar-item-separator' // 竖线分隔符
	                },*/{
	                	id : 'exportBatch',
	                	btnCls : 'button button-small exportBatch',
	                	text : '<i class="icon-download"></i>批量导出',
	                }/*,{
	                	xclass : 'bar-item-separator' // 竖线分隔符
	                },{
	                	id : 'modelEditId',
	                	btnCls : 'button button-small modelEdit',
	                	text : '<i class="icon-edit"></i>模板编辑',
	                }*/,bar.getChild('msgTips',true)
	                ]
		        },
		    });
		    _self.set('searchGrid',searchGrid);
		    return searchGrid;
		}
	}, {
		ATTRS : {
			filePath : {},
			tplPath : {},
			user : {},
		},
		SEARCH_GRID_ID : SEARCH_GRID_ID,
		SEARCH_FORM_ID : SEARCH_FORM_ID
	})
	return SegmentPuTieYearMonthExecute;
});