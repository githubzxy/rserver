/**
 * 事故故障障碍查询主页
 * @author yangsy
 * @date 19-2-27
 * 修改：在该页面增加对安全隐患的查看
 * 修改人：zhouxingyu
 * @date 19-3-24
 */
define('kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryPage',
	[
	 	'bui/common',
	 	'bui/data',
	 	'bui/grid',
	 	'bui/calendar',
	 	'common/data/PostLoad',
	 	'common/org/OrganizationPicker',
	 	'common/grid/SearchGridContainer',
	 	'kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailSg',
	 	'kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailGz',
	 	'kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailZa',
	 	'kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailYh',
	],function (r) {
    var BUI = r('bui/common'),
    	  Data = r('bui/data'),
          Grid = r('bui/grid'),
          Calendar = r('bui/calendar'),
          PostLoad = r('common/data/PostLoad'),
          OrganizationPicker = r('common/org/OrganizationPicker'),
          SearchGridContainer = r('common/grid/SearchGridContainer'),
          accidentTroubleObstacleQueryDetailSg = r('kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailSg'),
          accidentTroubleObstacleQueryDetailGz = r('kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailGz'),
          accidentTroubleObstacleQueryDetailZa = r('kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailZa');
    	  accidentTroubleObstacleQueryDetailYh = r('kmms/securityManage/accidentTroubleObstacleManage/accidentTroubleObstacleQuery/accidentTroubleObstacleQueryDetailYh');
    var AccidentTroubleObstacleQueryPage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
                _self._initDate();
                _self._initInfoResult();
//                _self._initOrganizationPicker();
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
//                var orgPicker=_self.get('orgPicker');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                /**
                 * 组织机构选择
                 */
                
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#infoResult").val("");
    				$("#startUploadDateSearch").val("");
    				$("#endUploadDateSearch").val("");
    			});
    			 //导出Excel
				$('.exportBtn').on('click',function(e){
					_self._exportExcel();

				});
                /**
                 * 操作按钮
                 */
                table.on('cellclick',function(ev){
                    var record = ev.record, //点击行的记录
                    	  target = $(ev.domTarget),
                    	  docId = record.docId; //点击的元素（数据库主键的值String）
                    	  busiId = record.busiId;//自定义的业务ID
                    /**
                     * 带有事故的详情
                     */
                    if(target.hasClass('detailBtnSg')){
                    	var infoDialog = new accidentTroubleObstacleQueryDetailSg({
                    		collectionName:_self.get('collectionName'),
                    		shiftId:docId
                    	});
                    	infoDialog.show();
                    }
                    /**
                     * 带有故障的详情
                     */
                    if(target.hasClass('detailBtnGz')){
                    	var infoDialog = new accidentTroubleObstacleQueryDetailGz({
                    		collectionName:_self.get('collectionName'),
                    		shiftId:docId
                    	});
                    	infoDialog.show();
                    }
                    /**
                     * 带有障碍的详情
                     */
                    if(target.hasClass('detailBtnZa')){
                    	var infoDialog = new accidentTroubleObstacleQueryDetailZa({
                    		collectionName:_self.get('collectionName'),
                    		shiftId:docId
                    	});
                    	infoDialog.show();
                    }
                    /**
                     * 带有安全隐患的详情
                     */
                    if(target.hasClass('detailBtnYh')){
                    	var infoDialog = new accidentTroubleObstacleQueryDetailYh({
                    		collectionName:_self.get('collectionName'),
                    		shiftId:docId
                    	});
                    	infoDialog.show();
                    }
                });
            },
            /**
             * 初始化上查询下列表
             * @private
             */
            _initSearchGridContainer:function(){
                var _self = this;
                var searchGridContainer = new SearchGridContainer({
                    searchForm : _self._initSearchForm(),
                    columns : _self._initColumns(),
                    store : _self._initStore(),
                    searchGrid : _self._initSearchGrid()
                });
                return searchGridContainer;
            },
            _initInfoResult: function () {
    			$("#infoResult").append("<option value='1'>事故</option>");
    			$("#infoResult").append("<option value='2'>故障</option>");
    			$("#infoResult").append("<option value='3'>障碍</option>");
    			$("#infoResult").append("<option value='4'>安全隐患</option>");
            },
          //导出本页数据
    		_exportData: function(){
    			var _self = this;
    			var store=_self.get('store');
    			console.log(store.getResult());

    			// 导出本页数据
    			var records = store.getResult();	
     			var json = '[';
    			for(var i = 0 ; i < records.length ; i++){
    				var row = records[i];
    				console.log(row.createDate);
    				json += '{'
    					+'"createDate":'+'"'+row.createDate+'",'
    					+'"backOrgName":'+'"'+row.backOrgName+'",'
    					+'"backPerson":'+'"'+row.backPerson+'",'
    					+'"type":'+'"'+row.type+'",'
    					+'"detail":'+'"'+row.detail+'",'
    					+'"infoResult":'+'"'+row.infoResult+'",'
    					+'"remark":'+'"'+row.remark+'"'
    					+'},';
    			}
    			json = json.substring(0, json.length - 1);
    			json += ']';
    			$("#exportXlsJson").val(json);
    			if(json != ']'){
    				$("#exportForm").submit();
    			}else{
    				commonFailure("导出失败！");
    			}
    		},
            /**
             * 初始化查询表单
             * @private
             */
            _initSearchForm:function(){
                return {
                    items : [
                        {label : '开始时间',item : '<input type="text" name="startUploadDate" id="startUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'},
                        {label : '结束时间',item : '<input type="text" name="endUploadDate" id="endUploadDateSearch" class="calendar" style="width: 175px;" readonly/>'},
                        {label : '信息后果',item : '<select name="infoResult" id="infoResult" style="width: 210px;" ><option value="">请选择</option></select>'},
                    ]};
            },
            /**
             * 初始化时间查询
             * @private
             */
            _initDate: function () {
                var _self = this;
                var startUploadDate = new Calendar.DatePicker({//加载日历控件
                    trigger: '#startUploadDateSearch',
                    showTime: true,
                    autoRender: true,
                    textField:'#startUploadDateSearch'
                });
                var endUploadDate = new Calendar.DatePicker({//加载日历控件
                    trigger: '#endUploadDateSearch',
                    showTime: true,
                    autoRender: true,
                    textField:'#endUploadDateSearch'
                });
                _self.set('startUploadDate', startUploadDate);
                _self.set('endUploadDate', endUploadDate);
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                    {
                        title:'时间',
                        dataIndex:'createDate',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'信息反馈部门',
                        dataIndex:'backOrgName',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'信息反馈人',
                        dataIndex:'backPerson',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'类型',
                        dataIndex:'type',
                        elCls : 'center',
                        width:'15%',
                    },{
                        title:'内容及处理情况',
                        dataIndex:'detail',
                        elCls : 'center',
                        width:'25%',
                    },{
                        title:'信息后果',
                        dataIndex:'infoResult',
                        elCls : 'center',
                        width:'10%',
                        renderer:function (value) {
                        	if(value=='1'){//事故
                        		return "事故";
                        	} else if(value=='2'){//故障
                        		return "故障";
                        	} else if(value=='3'){//障碍
                        		return "障碍";
                        	}else if(value=='4'){//安全隐患
                        		return "安全隐患";
                        	}
                        }
                    },
                    {
                        title:'备注',
                        dataIndex:'remark',
                        elCls : 'center',
                        width:'25%',
                    },{
                        title:'操作',
                        dataIndex:'infoResult',
                        elCls : 'center',
                        width:'15%',
                        renderer:function (value) {
                            var button = '';
                        	if(value=='1'){//事故
                        		button += '<span  class="grid-command detailBtnSg">详情</span>';
                        	} else if(value=='2'){//故障
                        		button += '<span  class="grid-command detailBtnGz">详情</span>';
                        	} else if(value=='3'){//障碍
                        		button += '<span  class="grid-command detailBtnZa">详情</span>';
                        	}else if(value=='4'){
                        		button += '<span  class="grid-command detailBtnYh">详情</span>';
                        	}
                            return button;
                        }
                    }
                ];
                return columns;
            },
            /**
             * 初始化列表数据对象
             * @private
             */
            _initStore:function () {
                var _self = this;
                var store = new Data.Store({
                    url : "/kmms/accidentTroubleObstacleQueryAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 20,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName')}
                });
                _self.set('store',store);
                return store;
            },
            
            
            /**
             * 初始化列表展示对象
             * @private
             */
            _initSearchGrid:function () {
            	var _self = this;
                var searchGrid = {
				   tbarItems : [{
					      btnCls : 'button button-small',
					      text : '<i class="icon-download"></i>导出Excel'
	                            +'<form action="/kmms/accidentTroubleObstacleQueryAction/exportXls" id="exportForm" method="post">'
	    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
	    						+'</form>',
		                  listeners : {
		                    'click' : function(){
		                    	 _self._exportData()
		                    }
		                  }
								}
				   			   ],
                    plugins : [
                        Grid.Plugins.CheckSelection,
                        Grid.Plugins.RowNumber
                    ],
//                    permissionStore : _self._initPermissionStore()
                };
                return searchGrid;
            }
        },
        {
            ATTRS : {
                perId : {},
                userId : {},//登录用户ID
                userName : {},//登录用户名称
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                collectionName : {value:'informationData'}//存储表名
            }
        });
    return AccidentTroubleObstacleQueryPage;
});