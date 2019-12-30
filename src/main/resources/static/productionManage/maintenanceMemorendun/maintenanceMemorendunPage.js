/**
 * 维修工作备忘录
 * @author zhouxingyu
 * @date 19-3-25
 */
define('kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunPage', ['bui/common', 'bui/data', 'bui/grid',
    'common/grid/SearchGridContainer', 'bui/calendar', 'common/org/OrganizationPicker', 'common/data/PostLoad', 'common/form/FormContainer',
    'kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunAdd', 'kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunUpdate', 'kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunDetail', 'kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunImport'
], function(r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker');
    	SearchGridContainer = r('common/grid/SearchGridContainer'),
        maintenanceMemorendunAdd = r('kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunAdd'),
        maintenanceMemorendunUpdate = r('kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunUpdate'),
        maintenanceMemorendunDetail = r('kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunDetail'),
        maintenanceMemorendunImport = r('kmms/productionManage/maintenanceMemorendun/maintenanceMemorendunImport');
    FormContainer = r('common/form/FormContainer');
    var maintenanceMemorendunPage = BUI.Component.Controller.extend({
        initializer: function() {
            var _self = this;
            _self.addChild(_self._initSearchGridContainer());
        },
        renderUI: function() {
            var _self = this;
            _self._initDate();
            _self._initOrganizationPicker();
        },
        
        _initOrganizationPicker:function(){
            var _self=this;
            var orgPicker = new OrganizationPicker({
                trigger : '#dutyDepartment',
                rootOrgId:_self.get('rootOrgId'),//必填项
                rootOrgText:_self.get('rootOrgText'),//必填项
                url : '/kmms/commonAction/getChildrenByPidAndCurId',//必填项
                autoHide: true,
                align : {
                    points:['bl','tl']
                },
                zIndex : '10000',
                width:220,
                height:200
            });
            orgPicker.render();
            _self.set('orgPicker',orgPicker);
        },
        
        bindUI: function() {
            var _self = this,
                store = _self.get('store'),
                userId = _self.get('userId'),
                orgPicker = _self.get('orgPicker');
            contextPath = _self.get('contextPath');
            
            var orgPicker=_self.get('orgPicker');
            
            /**
             * 组织机构选择
             */
            orgPicker.on('orgSelected',function (e) {
                $('#dutyDepartment').val(e.org.text);
    		    $('#dutyDepartmentId').val(e.org.id);
            });
            
            var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID, true);
            var tbar = table.get('tbar');
            //重写重置按钮的点击事件
            $("button[type='reset']").on('click', function(event) {
                event.preventDefault();
                $("#dutyDepartment").val("");
                $("#checkDate").val("");
                $("#endDate").val("");
            });

            /**
             * 批量删除
             */
            $(".delBtn").on('click', function() {
                var removeIds = table.getSelection();
                removeIds = removeIds.map(function(item) {
                    return item.docId;
                });
                var id = removeIds.join(",");
                if (!id) {
                    tbar.msg({ status: 0, msg: '至少选择一项要删除的项目！' })
                    
                } else {
                	var isRm = table.getSelection().map(function(item,index) {
                		if(item.userId != userId){
                			return index+1;
                		}else{
                			return "";
                		}
                	});
                	var flag = 1;//是否需要弹出删除提示框（1：是，0：否）
                	var count = "";//重新拼接不能删除的序号字符串
                	for(var i = 0 ; i<isRm.length; i++){
                		if(isRm[i] != ""){
                			count += isRm[i]+",";
                		}
                	}
                	count = count.substring(0,count.length-1);
                	
                	for(var i = 0 ; i<isRm.length; i++){
                		if(isRm[i] != ""){
                			tbar.msg({ status: 0, msg: "勾选中的第"+count+'条数据不是创建者，不能删除！' });
                			flag = 0;
                			break;
                		}
                	}
                	if(flag == 1){
                		BUI.Message.Confirm('确认要删除吗？', function() {
                            var postLoad = new PostLoad({
                                url: '/kmms/commonAction/removeDoc.cn',
                                el: _self.get('el'),
                                loadMsg: '删除中...'
                            });
                            postLoad.load({ id: id,userId: _self.get('userId'), collectionName: _self.get('collectionName') }, function(res) {
                                tbar.msg(res);
                                store.load();
                            });
                        }, 'question');
                	}
                    
                }
            });
            /**新增*/
            $('.addBtn').on('click', function(e) {
                var addDialog = new maintenanceMemorendunAdd({
                    collectionName: _self.get('collectionName'),
                    orgId:_self.get('orgId'),
                    orgName:_self.get('orgName'),
                    userId: _self.get('userId'),
                });
                addDialog.show();
                addDialog.on('completeAddSave', function(e) {
                    tbar.msg(e.result);
                    addDialog.close();
                    store.load();
                });
            });
            // 点击导入数据按钮，弹出导入数据框
            $('.importBtn').click(function() {
            	var ImportForm = new maintenanceMemorendunImport({
					collectionName:_self.get('collectionName'),
                    userId:userId,
                    perId: _self.get('perId')
				});
				ImportForm.show();	
				ImportForm.on('completeImport',function(e){
					  ImportForm.close();
                      tbar.msg(e.result);
                      store.load();
				});
//                var importForm = new maintenanceMemorendunImport({ collectionName: _self.get('collectionName'), userId: _self.get('userId'), perId: _self.get('perId') });
//                importForm.show();
//                store.load();
            });
            /**
             * 操作按钮
             */
            table.on('cellclick', function(ev) {
                var record = ev.record, //点击行的记录
                    target = $(ev.domTarget),
                    docId = record.docId; //点击的元素
                	rowUserId = record.userId;
                /**
                 * 详情
                 */
                if (target.hasClass('infoBtn')) {
                    var infoDialog = new maintenanceMemorendunDetail({
                        collectionName: _self.get('collectionName'),
                        userId: _self.get('userId'),
                        shiftId: docId
                    });
                    infoDialog.show();
                }
                /**
                 * 编辑
                 */
                if (target.hasClass('editBtn') ) {
                		var editDialog = new maintenanceMemorendunUpdate({
                            collectionName: _self.get('collectionName'),
                            userId: _self.get('userId'),
                            shiftId: docId
                        });
                        editDialog.show();
                        editDialog.on('completeAddSave', function(e) {
                            tbar.msg(e.result);
                            editDialog.close();
                            store.load();
                        });
                }
            });
        },
        /**
         * 初始化上查询下列表
         * @private
         */
        _initSearchGridContainer: function() {
            var _self = this;
            var searchGridContainer = new SearchGridContainer({
                searchForm: _self._initSearchForm(),
                columns: _self._initColumns(),
                store: _self._initStore(),
                searchGrid: _self._initSearchGrid()
            });
            return searchGridContainer;
        },
        /**
         * 初始化查询表单
         * @private
         */
        _initSearchForm: function() {
            return {
                items: [
					{
						label : '所属部门',
						item : '<input type="text" name="dutyDepartment" id="dutyDepartment" style="width: 175px;"/>'
					},
                    { label: '开始日期', item: '<input type="text" name="checkDate" class="calendar calendar-time" id="checkDate" style="width: 175px;"/>' },
                    { label: '结束日期', item: '<input type="text" name="endDate" class="calendar calendar-time" id="endDate" style="width: 175px;"/>' }
                ]
            };
        },
        /**
         * 初始化时间查询
         * @private
         */
        _initDate: function() {
            var _self = this;
            var startUploadDate = new Calendar.DatePicker({ //加载日历控件
                trigger: '#checkDate',
                autoRender: true,
                textField: '#checkDate'
            });
            var endUploadDateSearch = new Calendar.DatePicker({ //加载日历控件
                trigger: '#endDate',
                autoRender: true,
                textField: '#endDate'
            });
            _self.set('checkDate', checkDate);
            _self.set('endDate', endDate);
        },
        /**
         * 初始化列表字段
         * @private
         */
        _initColumns: function() {
            var columns = [
            {
                title: '所属部门',
                dataIndex: 'dutyDepartment',
                elCls: 'center',
                width: '20%',
            }, {
                title: '检查日期',
                dataIndex: 'checkDate',
                elCls: 'center',
                width: '10%',
                renderer: Grid.Format.datetimeRenderer
            }, {
                title: '检查人',
                dataIndex: 'checker',
                elCls: 'center',
                width: '10%'
            }, {
                title: '问题处所',
                dataIndex: 'problemFrom',
                elCls: 'center',
                width: '15%'
            },
            {
                title: '发现问题',
                dataIndex: 'problemInfo',
                elCls: 'center',
                width: '15%',
            },
            {
                title: '整改情况',
                dataIndex: 'changeInfo',
                elCls: 'center',
                width: '15%',
            },
            {
                title: '整改结果',
                dataIndex: 'result',
                elCls: 'center',
                width: '15%',
            }, {
                title: '操作',
                dataIndex: 'id',
                elCls: 'center',
                width: '10%',
                renderer: function() {
                    return '<span  class="grid-command editBtn">编辑</span>' +
                        '<span  class="grid-command infoBtn">详情</span>';
                }
            }];
            return columns;
        },
        /**
         * 初始化列表数据对象
         * @private
         */
        _initStore: function() {
            var _self = this;
            var store = new Data.Store({
                url: "/kmms/maintenanceMemorendunAction/findAll.cn",
                autoLoad: true,
                pageSize: 10,
                proxy: {
                    method: 'post',
                    dataType: 'json'
                },
                params: { collectionName: _self.get('collectionName') }
            });
            _self.set('store', store);
            return store;
        },
        /**
         * 初始化列表展示对象
         * @private
         */
        _initSearchGrid: function() {
            var searchGrid = {
                tbarItems: [{
                    id: 'add',
                    btnCls: 'button button-small addBtn',
                    text: '<i class="icon-plus"></i>新增',
                }, {
                    xclass: 'bar-item-separator' // 竖线分隔符
                }, {
                    id: 'del',
                    btnCls: 'button button-small delBtn',
                    text: '<i class="icon-remove"></i>批量删除',
                },
                {
                    xclass: 'bar-item-separator' // 竖线分隔符
                }, {
                    id: 'import',
                    btnCls: 'button button-small importBtn',
                    text: '<i class="icon-upload"></i>导入',
                }
                ],
                plugins: [
                    Grid.Plugins.CheckSelection,
                    Grid.Plugins.RowNumber
                ],
            };
            return searchGrid;
        }
    }, {
        ATTRS: {
            /**
             * 当前页ID
             */
            perId: {},
            /**
             * 当前用户Id
             */
            userId: {}, //登录用户ID
            orgId: {}, //登录用户组织机构ID
            orgName: {}, //登录用户组织机构名称
            rootOrgId: { value: '8affa073533aa3d601533bbef63e0010' },
            rootOrgText: { value: '昆明通信段' },
            /**
             * 存储表名
             */
            collectionName: { value: 'maintenanceMemorendun' },
            /**
             * 项目名
             */
            contextPath: {}
        }
    });
    return maintenanceMemorendunPage;
});