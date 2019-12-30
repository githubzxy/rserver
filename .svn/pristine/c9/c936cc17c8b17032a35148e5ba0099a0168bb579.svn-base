/**
 * 机房名称管理
 * @param require
 * @returns
 * @author zhouxingyu
 * @date 19-7-23
 */
define('kmms/technicalManagement/machineRoomManage/machineRoomManagePage',[
	'bui/common',
	'bui/grid',
    'bui/data',
    'common/grid/SearchGridContainer',
    'common/data/PostLoad',
    'kmms/commonUtil/SelectSuggest',
    'kmms/technicalManagement/machineRoomManage/machineRoomManageAdd',
    'kmms/technicalManagement/machineRoomManage/machineRoomManageUpdate'],function(require){
    var BUI = require('bui/common');
    var Grid = require('bui/grid');
    var Data = require('bui/data');
    var SelectSuggest = require("kmms/commonUtil/SelectSuggest");
    var SearchGridContainer = require('common/grid/SearchGridContainer');
    var PostLoad=require('common/data/PostLoad');
    var machineRoomManageAdd = require('kmms/technicalManagement/machineRoomManage/machineRoomManageAdd');
    var machineRoomManageUpdate = require('kmms/technicalManagement/machineRoomManage/machineRoomManageUpdate');
    var SEARCH_FORM_ID = 'searchForm';
    var machineRoomManagePage = BUI.Component.Controller.extend(
        {
            initializer : function(){
                var _self = this;
                _self.addChild(_self._initSearchGridContainer());
            },
            renderUI:function(){
                var _self=this;
    			_self._getLines();//获取线别下拉选数据
            },
            bindUI:function(){
                var _self = this,store = _self.get('store');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');
                store.load({});
                /**新增*/
                $('.addBtn').on('click',function(e){
                    var addDialog = new machineRoomManageAdd({
                            userId:_self.get('userId'),
                            userName:_self.get('userName'),
                            orgId:_self.get('orgId'),
                            orgName:_self.get('orgName'),
                    });
                    addDialog.show();
                    addDialog.on('completeAddSave',function(e){
                        tbar.msg(e.result);
                        addDialog.close();
                        store.load();
                    });
                });
                
                /**
                 * 批量删除
                 */
                $(".delBtn").on('click', function() {
                    var removeIds = table.getSelection();
                    removeIds = removeIds.map(function(item) {
                        return item.id;
                    });
                    var id = removeIds.join(",");
                    if (!id) {
                        tbar.msg({ status: 0, msg: '至少选择一项要删除的项目！' })
                    } else {
                    	BUI.Message.Confirm('确认要删除吗？', function() {
                            var postLoad = new PostLoad({
                                url: '/kmms/machineRoomManageAction/removeDoc.cn',
                                el: _self.get('el'),
                                loadMsg: '删除中...'
                            });
                            postLoad.load({ id: id }, function(res) {
                                tbar.msg(res);
                                store.load();
                            });
                        }, 'question');
                    }
                });
                /**
                 * 操作按钮
                 */
                table.on('cellclick',function(ev){
                	var record = ev.record, //点击行的记录
                    target = $(ev.domTarget),
                    docId = record.id; //点击的元素
                    /**
                     * 修改
                     */
                    if(target.hasClass('editBtn')){
                        var editDialog = new machineRoomManageUpdate({
                            userId:_self.get('userId'),
                            shiftId:docId
                        });
                        editDialog.show();
                        editDialog.on('completeAddSave',function(e){
                            tbar.msg(e.result);
                            editDialog.close();
                            store.load();
                        });
                    }
                    
                });
            },
            
            /**
             * 获取线别
             */
            _getLines:function(){
            	var _self=this;
            	var nameData = [];
            	$.ajax({
            		url:'/kmms/lineNameMangementAction/getLines',
            		type:'post',
            		dataType:"json",
            		success:function(res){
            			for(var i=0;i<res.length;i++){
            				nameData.push(res[i]);
            			}
            		}
            	});
            	
            	var suggest = new SelectSuggest({
                    renderName: '#nameCondition',
                    inputName: 'nameCondition',
                    renderData: nameData,
                    width: 200
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
             * 初始化查询表单
             * @private
             */
            _initSearchForm:function(){
                return {
                	items: [
                		{
                            label : '机器编码',
                            item : '<input type="text" name="machineCode" id="machineCode" style="width: 175px;" class="input-normal"/>'
                        },
                    	{
                            label : '机房名称',
                            item : '<input type="text" name="machineName" id="machineName" style="width: 175px;" class="input-normal"/>'
                        },
                		{
                            label : '线别名称',
                            item : '<div id="nameCondition" name="nameCondition" style="width: 200px;" />'
                        },
                        {
                            label : '备注',
                            item : '<input type="text" name="remark" id="remark" style="width: 175px;" class="input-normal"/>'
                        }
                    ]
                };
            },
            
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
            	var columns = [
            		  {
                          title:'机房编码',
                          dataIndex:'machineCode',
                          elCls:'center',
                          width:'15%'
                      },{
                        title:'机房名称',
                        dataIndex:'machineName',
                        elCls:'center',
                        width:'15%'
                     },
                    {
                        title:'线别',
                        dataIndex:'name',
                        elCls:'center',
                        width:'15%'
                    },{
                        title:'维护单位',
                        dataIndex:'maintenanceOrg',
                        elCls:'center',
                        width:'15%'
                    },{
                        title:'投产日期',
                        dataIndex:'commissioningDate',
                        elCls:'center',
                        width:'15%'
                    },{
                        title:'备注',
                        dataIndex:'remark',
                        elCls:'center',
                        width:'15%'
                    },{
                        title:'操作',
                        dataIndex:'id',
                        elCls : 'center',
                        width:"10%",
                        renderer:function (e) {
                        	return '<span  class="grid-command editBtn">修改</span>'
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
                    url : "/kmms/machineRoomManageAction/findAll",
                    autoLoad : true ,
                    pageSize : 10,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {}
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
                    tbarItems : [
                        {
                            id : 'add',
                            btnCls : 'button button-small addBtn',
                            text : '<i class="icon-plus"></i>新增',
                        },
                        {
                            xclass : 'bar-item-separator' // 竖线分隔符
                        },{
                        	id : 'del',
                            btnCls : 'button button-small delBtn',
                            text : '<i class="icon-remove"></i>批量删除',
                        },
                    ],
                    plugins : [
                        Grid.Plugins.CheckSelection,
                        Grid.Plugins.RowNumber
                    ],
                };
                return searchGrid;
            }
        },
        {
            ATTRS : {
            	elCls : {value:'machineRoomManageInfoPageCls'},
            	perId : {},
                userId : {},//登录用户ID
                userName : {},//登录用户名称
                orgId : {},//登录用户组织机构ID
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'}
            }
        });
    return machineRoomManagePage;
});