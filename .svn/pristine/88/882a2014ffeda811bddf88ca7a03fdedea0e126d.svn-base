/**
 *人员信息管理主页（劳人科）
 * @author yangsy
 * @date 18-11-22
 */
define('kmms/userInfoManager/userInfoManagePage',['bui/common','bui/data','bui/grid',
    'common/grid/SearchGridContainer',
    'kmms/userInfoManager/userInfoManageAdd',
    'bui/calendar',
    'bui/mask',
	'common/container/LeftRightContainer',
	'kmms/userInfoManager/LeftOrgTree',
    'common/org/OrganizationPicker',
    'kmms/userInfoManager/userInfoManageEdit',
    'kmms/userInfoManager/userInfoManageInfo',
    'kmms/userInfoManager/userInfoManageImport',
    'common/data/PostLoad'
    ], function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        Mask = r('bui/mask'),
        PostLoad = r('common/data/PostLoad'),
    	LRC = r('common/container/LeftRightContainer'),
		LeftOrgTree = r('kmms/userInfoManager/LeftOrgTree'),
        OrganizationPicker = r('common/org/OrganizationPicker');
	    userInfoManageAdd = r('kmms/userInfoManager/userInfoManageAdd'),
	    userInfoManageEdit = r('kmms/userInfoManager/userInfoManageEdit'),
	    userInfoManageInfo = r('kmms/userInfoManager/userInfoManageInfo'),
	    userInfoManageImport = r('kmms/userInfoManager/userInfoManageImport'),
        SearchGridContainer = r('common/grid/SearchGridContainer');
	var LEFT_ORG_TREE_ID = 'leftOrgTreeId';//左边树组件的ID
		
		var userInfoManagePage = LRC.extend({
			initializer: function(){
				var _self = this,left = _self.get('leftChildren'),right = _self.get('rightChildren');
				var orgTree = _self._initLeftOrgTree();
				var searchGrid = _self._initSearchGridContainer();
				left.push(orgTree);
				right.push(searchGrid);
			},
            renderUI:function(){
                var _self=this;
                $("#selectFind").append("<option  value=''>请选择查看人员</option>");
                $("#selectFind").append("<option  value='0'>全部人员</option>");
    			$("#selectFind").append("<option  value='1'>车间人员</option>");
				_self._showEmerMember('昆明通信段');
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
				var perId = _self.get('perId');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
//                if($(" #workShop option[value='1']").attr("selected","selected")){
//                	$("#selectOrgType").val("1502");
//                }else{
//                	$("#selectOrgType").val("");
//                }
                $("#selectFind").on('change',function() {
                	  if($("#selectFind").val()=="1"){
                      	$("#selectOrgType").val("1502");
                      	console.log($("#selectOrgType").val());
                      }else{
                      	$("#selectOrgType").val("")
                      }
                });
				var treeComponent = _self.getChild(LEFT_ORG_TREE_ID,true);//获取子组件(树)
//				treeComponent.on('updateOrgName',function(e){
//					console.log(e)
//					_self._showEmerMember(e.orgName);
//				})
				
				var tree = _self.getChild('orgTree',true);//获取左部分TreeList，孙组件，并不是子组件(树)
				tree.on("itemclick", function(e){//定义单击选项事件
					$("#phoneNum").val("");
    				$("#workshop").val("");
    				$("#selectOrgType").val("");
    				$("#selectFind").val("");
    				$("#staffName").val("");
    				$("#teamGroup").val("");
					var orgObj = e.item;
					console.log(orgObj);
					if(orgObj.id == '8affa073533aa3d601533bbef63e0010'){
						$('#workshop').val('');
						$('#orgId').val('');
						$('#selectFind').css('display','none');
					}else if(orgObj.type==1503){
						$('#teamGroup').val(orgObj.text);
						$('#selectFind').css('display','none');
					}else if(orgObj.type==1502){
						$('#selectFind').css('display','inline-block');
						$('#workshop').val(orgObj.text);
					}else{
						$('#workshop').val(orgObj.text);
						$('#selectFind').css('display','none');
					}
					_self._showEmerMember(orgObj.text,orgObj.type);
				});
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#phoneNum").val("");
    				$("#staffName").val("");
    				$("#selectFind").val("");

    			});
                
                /**
                 * 批量删除
                 */
                $(".delBtn").on('click',function () {
                   var removeIds = table.getSelection();
                   removeIds = removeIds.map(function (item) {
                       return item.docId;
                   });
                   var id = removeIds.join(",");
                   if(!id){
                       tbar.msg({status:0,msg:'至少选择一项要删除的项目！'})
                   }else{
                	   BUI.Message.Confirm('确认要删除吗？',function(){
	                       var postLoad = new PostLoad({
	                           url : '/kmms/userInfoManageAction/removeDoc.cn',
	                           el : _self.get('el'),
	                           loadMsg : '删除中...'
	                       });
	                       postLoad.load({id:id,collectionName:_self.get('collectionName')},function (res) {
	                    	   tbar.msg(res);
	                    	   store.load();
	                       });
                	   },'question');
                   }
                });
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new userInfoManageAdd({
                            collectionName:_self.get('collectionName'),
                            userId:_self.get('userId'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                    });
                    addDialog.show();
                    addDialog.on('completeAddSave',function(e){
                        tbar.msg(e.result);
                        addDialog.close();
                        store.load();
//                    	}
                    });
                });
                /**修改*/
                $('.updateBtn').on('click',function(e){
                	var removeIds = table.getSelection();
                	removeIds = removeIds.map(function (item) {
                		return item.docId;
                	});
                	var id = removeIds.join(",");
                	if(!id){
                		tbar.msg({status:0,msg:'至少选择一项要修改的项目！'})
                	}else if(removeIds.length>1){
                		tbar.msg({status:0,msg:'只能选择一项要修改的项目！'})
                	}else{
                		var editDialog = new userInfoManageEdit({
                          collectionName:_self.get('collectionName'),
//                        userId:_self.get('userId'),
                          shiftId:id
                      });
                      editDialog.show();
                      editDialog.on('completeAddSave',function(e){
                          tbar.msg(e.result);
                          editDialog.close();
                          store.load();
                      });
                	}
                });
                // 点吉导入施工维修数据按钮，弹出导入数据框
    			$('.ImportBtn').click(function (){
    				var ImportForm = new userInfoManageImport({
    					collectionName:_self.get('collectionName'),
                        userId:_self.get('userId'),
                        orgId:_self.get('orgId'),
                        orgName:_self.get('orgName'),
                        orgType:_self.get('orgType')
    					});
    				ImportForm.show();	
    				ImportForm.on('completeImport',function(e){
    					  console.log(e);
    					  ImportForm.close();
                          tbar.msg(e.result);
                           store.load();
                      });
    			});
                /**
                * 操作按钮
                */
               table.on('cellclick',function(ev){
               	var record = ev.record, //点击行的记录
                   target = $(ev.domTarget),
                   docId = record.docId; //点击的元素
                   /**
                    * 详情
                    */
                   if(target.hasClass('infoBtn')){
                   	var infoDialog = new userInfoManageInfo({
                           collectionName:_self.get('collectionName'),
//                   		userId:_self.get('userId'),
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
            /**
			 * 点击左部分树选项，右部分表格跟随显示数据
			 */
			_showEmerMember: function(orgName,type){
				var _self = this,store = _self.get('store');
				console.log(type);
				if(orgName=="昆明通信段"){
					store.load({
						start : 0, //从第一页开始
						phoneNum:'',
	    				workshop:'',
	    				selectOrgType:'',
	    				staffName:'',
	    				teamGroup:''
					});
				}else if(type==1503){
					store.load({
						start : 0, //从第一页开始
						phoneNum:'',
	    				workshop:'',
	    				selectOrgType:'',
	    				staffName:'',
	    				teamGroup:orgName
					});
				}else{
					store.load({
						start : 0, //从第一页开始
						phoneNum:'',
						selectOrgType:'',
	    				workshop: orgName,
	    				staffName:'',
	    				teamGroup:''
					});
				}
			},
			/**
			 * 初始化左边组织机构树
			 */
			_initLeftOrgTree: function(){
				var _self = this;
				var leftOrgTree = new LeftOrgTree({
					id : LEFT_ORG_TREE_ID,//树控件ID
					perId: _self.get('perId')
				});
				return leftOrgTree;
			},
            /**
             * 初始化查询表单
             * @private
             */
            _initSearchForm:function(){
                return {
                    items : [
                        {label : '联系电话',item : '<input type="hidden"  id="workshop"  name="workshop" readonly/>'+
    					    '<input type="hidden"  name="teamGroup" id="teamGroup"  readonly/>'+
                    	    '<input type="hidden" name="selectOrgType" id="selectOrgType" readonly/>'+
                        	'<input type="text" name="phoneNum" id="phoneNum" style="width: 175px;" />'},
                        {label : '姓名',item : '<input type="text" name="staffName" id="staffName" style="width: 175px;" />'+
                        		'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
                        		'<select type="text"  id="selectFind" style= "width:215px;display:none"/ ></select>'
                        }

                    ]};
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                    {
                        title:'劳资号',
                        dataIndex:'number',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'姓名',                       
                        dataIndex:'staffName',
                        elCls : 'center',
                        width:'25%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'性别',
                        dataIndex:'gender',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'出生日期',
                        dataIndex:'birthday',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'参加工作时间',
                        dataIndex:'entryDate',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'学历',
                        dataIndex:'education',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'车间(科室)',
                        dataIndex:'workshop',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'班组',
                        dataIndex:'teamGroup',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'职名',
                        dataIndex:'position',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'联系电话',
                        dataIndex:'phoneNum',
                        elCls : 'center',
                        width:'20%',
                    },{
                    	title:'操作',
                        dataIndex:'id',
                        elCls : 'center',
                        width:'12%',
                        renderer:function (e) {
                        	return '<span  class="grid-command infoBtn">详情</span>';
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
                    url : "/kmms/userInfoManageAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 10,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName'),userId:_self.get('userId'),orgId:_self.get('orgId'),orgType:_self.get('orgType')+""}//将int类型的orgType转为字符串
                });
                _self.set('store',store);
                return store;
            },
            
//
            //导出本页数据
    		_exportData: function(){
    			var _self = this;
    			var store=_self.get('store');
    			// 导出本页数据
    			var records = store.getResult();	
     			var json = '[';
    			for(var i = 0 ; i < records.length ; i++){
    				var row = records[i];
    				console.log(row.createDate);
    				json += '{'
    					+'"number":'+'"'+row.number+'",'
    					+'"staffName":'+'"'+row.staffName+'",'
    					+'"gender":'+'"'+row.gender+'",'
    					+'"birthday":'+'"'+row.birthday+'",'
    					+'"entryDate":'+'"'+row.entryDate+'",'
    					+'"education":'+'"'+row.education+'",'
    					+'"workshop":'+'"'+row.workshop+'",'
    					+'"teamGroup":'+'"'+row.teamGroup+'",'
    					+'"position":'+'"'+row.position+'",'
    					+'"phoneNum":'+'"'+row.phoneNum+'",'
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
             * 初始化列表展示对象
             * @private
             */
            _initSearchGrid:function () {
            	var _self = this;
                var searchGrid = {
                    tbarItems : [
                        {
                            id : 'add',
                            btnCls : 'button button-small addBtn',
                            text : '<i class="icon-plus"></i>新增',
                        },{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        }, {
                            id : 'updd',
                            btnCls : 'button button-small updateBtn',
                            text : '<i class="icon-edit"></i>修改',
                        },{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                            id : 'del',
                            btnCls : 'button button-small delBtn',
                            text : '<i class="icon-remove"></i>批量删除',
                        },{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                        	id : 'imp',
                            btnCls : 'button button-small ImportBtn',
                            text : '<i class="icon-upload"></i>导入人员表',
                        },{
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                        	id : 'dow',
                            btnCls : 'button button-small DownloadBtn',
                            text : '<i class="icon-download"></i>导出Excel'
	                            +'<form action="/kmms/userInfoManageAction/exportXls" id="exportForm" method="post">'
	    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
	    						+'</form>',
		                  listeners : {
		                    'click' : function(){
		                    	 _self._exportData();
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
                /**
                 * 当前页ID
                 */
                perId : {},
                /**
                 * 当前用户Id
                 */
                userId : {},//登录用户ID
                orgId : {},//登录用户组织机构ID
                orgType : {},//登录用户组织机构类型
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                /**
                 * 存储表名
                 */
                collectionName : {value:'userInfoManage'}
            }
        });
    return userInfoManagePage;
});