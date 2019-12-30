/**
 * 工作日志查询（领导查看使用）
 * @author 杨伸远
 * @date 19-5-20
 */
define('kmms/productionManage/JobRecord/jobRecordQueryPage', ['bui/common', 'bui/data', 'bui/grid',
    'common/grid/SearchGridContainer', 'bui/calendar', 'common/org/OrganizationPicker', 'common/data/PostLoad', 'common/form/FormContainer',
    'kmms/productionManage/JobRecord/jobRecordImport', 'kmms/productionManage/JobRecord/jobRecordUpdate', 'kmms/productionManage/JobRecord/jobRecordDetail',
    'kmms/productionManage/JobRecord/jobRecordExport', 'kmms/productionManage/JobRecord/SelectSuggest'
], function(r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
        OrganizationPicker = r('common/org/OrganizationPicker'),
        SearchGridContainer = r('common/grid/SearchGridContainer'),
        jobRecordImport = r('kmms/productionManage/JobRecord/jobRecordImport'),
        jobRecordUpdate = r('kmms/productionManage/JobRecord/jobRecordUpdate'),
        jobRecordDetail = r('kmms/productionManage/JobRecord/jobRecordDetail'),
        SelectSuggest = r('kmms/productionManage/JobRecord/SelectSuggest'),
        jobRecordExport = r('kmms/productionManage/JobRecord/jobRecordExport'),
        FormContainer = r('common/form/FormContainer');
    var jobRecordQueryPage = BUI.Component.Controller.extend({
        initializer: function() {
            var _self = this;
            _self.addChild(_self._initSearchGridContainer());
        },
        renderUI: function() {
            var _self = this;
            _self._initDate();
            _self._initProject();
            if (_self.get("orgType") == 1502) { //1502==车间
                $("#workarea").val(_self.get("orgName"));
                $("#workareaId").val(_self.get("orgId"));
                _self._initOrganizationPicker1502();
            }
            if (_self.get("orgType") == 1501 || _self.get("orgType") == 1500) { //1500==段，1501==科室
                _self._initOrganizationPicker();
            }
        },
        bindUI: function() {
            var _self = this,
                store = _self.get('store'),
                workareaOrgPicker = _self.get('workareaOrgPicker'),
                workshopOrgPicker = _self.get('workshopOrgPicker');
            contextPath = _self.get('contextPath');
            var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID, true);
            var tbar = table.get('tbar');

            /**
             * 组织机构选择
             */
            if (_self.get("orgType") == 1502) { //1502==车间
                workareaOrgPicker.on('orgSelected', function(e) {
                    var data = workareaOrgPicker.getSelection();
                    var text = "";
                    var id = "";
                    for (var i = 0; i < data.length; i++) {
                        text = text + ',' + data[i].text
                        id = id + ',' + data[i].value
                    }
                    $('#workarea').val(text.substring(1));
                    $('#workareaId').val(id.substring(1));
                });
            }
            if (_self.get("orgType") == 1501 || _self.get("orgType") == 1500) { //1500==段，1501==科室
                workshopOrgPicker.on('orgSelected', function(e) {
                    var data = workshopOrgPicker.getSelection();
                    var text = "";
                    var id = "";
                    var type = "";
                    for (var i = 0; i < data.length; i++) {
                        text = text + ',' + data[i].text
                        id = id + ',' + data[i].id
                        type = type + ',' + data[i].type
                    }
                    $('#workshop').val(text.substring(1));
                    $('#workshopId').val(id.substring(1));
                    $('#workshopType').val(type.substring(1));
                });
            }

            //重写重置按钮的点击事件
            $("button[type='reset']").on('click', function(event) {
                event.preventDefault();
                if (_self.get("orgType") == 1502) { //1502==车间
                    $("#workarea").val(_self.get("orgName"));
                    $("#workareaId").val(_self.get("orgId"));
                }
                if (_self.get("orgType") == 1501 || _self.get("orgType") == 1500) { //1500==段，1501==科室
                    $("#workshop").val("");
                    $("#workshopId").val("");
                }
                $("#startDate").val("");
                $("#endDate").val("");
                $(".bui-combox-input").val("");
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
                    BUI.Message.Confirm('确认要删除吗？', function() {
                        var postLoad = new PostLoad({
                            url: '/kmms/commonAction/removeDoc.cn',
                            el: _self.get('el'),
                            loadMsg: '删除中...'
                        });
                        postLoad.load({ id: id, collectionName: _self.get('collectionName') }, function(res) {
                            tbar.msg(res);
                            store.load();
                        });
                    }, 'question');
                }
            });

            // 点击导入数据按钮，弹出导入数据框
            $('.importBtn').click(function() {
                var importForm = new jobRecordImport({
                    collectionName: _self.get('collectionName'),
                    userId: _self.get('userId'),
                    orgId: _self.get('orgId'),
                    parentId: _self.get('parentId'),
                    userName: _self.get('userName'),
                    orgName: _self.get('orgName'),
                    currentDay: _self.get("currentDay"),
                    perId: _self.get('perId')
                });
                importForm.show();
                importForm.on('completeAddSave', function(e) {
                    tbar.msg(e.result);
                    importForm.close();
                    store.load();
                });
            });
            //按页面查询条件导出Excel
            $('#exportBy').on('click', function(e) {
                var records = store.getResult();
                if (records.length != 0) {
                    _self._exportXls(records);
                } else {
                    tbar.msg({ msg: '没有数据可以导出！', status: '0' });
                }
            });
            /**
             * 操作按钮
             */
            table.on('cellclick', function(ev) {
                var record = ev.record, //点击行的记录
                    target = $(ev.domTarget),
                    docId = record.docId; //点击的元素
                /**
                 * 详情
                 */
                if (target.hasClass('infoBtn')) {
                    var file = record.files;
                    if (file[0].path) {
                        var tempv = window.open("../tempFileShow.html");
                        tempv.location.href = '/pageoffice/openPage?filePath=' + file[0].path;
                    } else {
                        BUI.Message.Alert('文件损坏，请重新上传文件！');
                        return;
                    }
                }
                /**
                 * 编辑
                 */
                if (target.hasClass('editBtn')) {
                    var editDialog = new jobRecordUpdate({
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
         * 导出本页数据
         */
        _exportXls: function(records) {
            $('#exportXlsJson').val(''); //清空值
            // 拼接导出数据的JSON字符串
            var json = '[';
            for (var i = 0; i < records.length; i++) {
                var row = records[i];
                json += '{' +
                    '"content":' + '"' + row.content + '",' +
                    '"project":' + '"' + row.project + '",' +
                    '"orgName":' + '"' + row.orgName + '",' +
                    '"joiner":' + '"' + row.joiner + '",' +
                    '"joinerNumber":' + '"' + row.joinerNumber + '",' +
                    '"date":' + '"' + Grid.Format.datetimeRenderer(row.date) + '"' +
                    '},';
            }
            json = json.substring(0, json.length - 1);
            json += ']';
            $('#exportXlsJson').val(json);
            $('#exportForm').submit();
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
            var _self = this;
            if (_self.get("orgType") == 1503) { //工区页面能够导入并且只能看到本工区导入的数据
                return {
                    items: [
                        { label: '日期', item: '<input type="text" name="startDate" class="calendar calendar-time" id="startDate" style="width: 175px;"/>' },
                        { label: '至', item: '<input type="text" name="endDate" class="calendar calendar-time" id="endDate" style="width: 175px;"/>' },
                        { label: '项目', item: '<div  name="projectDiv"  id="projectDiv" style="width: 175px;"/>' },

                    ]
                };
            } else if (_self.get("orgType") == 1502) { //车间页面不能导入只能查看本车间管辖的工区导入的数据
                return {
                    items: [
                        { label: '部门', item: '<input type="text" name="workarea" id="workarea" readonly style="width: 175px;"/>' + '<input type="hidden" name="workareaId" id="workareaId" readonly/>' },
                        { label: '日期', item: '<input type="text" name="startDate" class="calendar calendar-time" id="startDate" style="width: 175px;"/>' },
                        { label: '至', item: '<input type="text" name="endDate" class="calendar calendar-time" id="endDate" style="width: 175px;"/>' },
                        { label: '项目', item: '<div  name="projectDiv"  id="projectDiv" style="width: 175px;"/>' },

                    ]
                };
            } else { //领导页面不能导入能查看所有工区导入的数据
                return {
                    items: [
                        { label: '部门', item: '<input type="text" name="workshop" id="workshop" readonly style="width: 175px;"/>' + '<input type="hidden" name="workshopId" id="workshopId" readonly/>' + '<input type="hidden" name="workshopType" id="workshopType" readonly/>' },
                        { label: '日期', item: '<input type="text" name="startDate" class="calendar calendar-time" id="startDate" style="width: 175px;"/>' },
                        { label: '至', item: '<input type="text" name="endDate" class="calendar calendar-time" id="endDate" style="width: 175px;"/>' },
                    ]
                };
            }
        },
        /**
         * 初始化组织机构选择
         * @private
         */
        _initOrganizationPicker1502: function() {
            var _self = this;
            var orgPicker = new OrganizationPicker({
                trigger: '#workarea',
                rootOrgId: _self.get('rootOrgId'), //必填项
                rootOrgText: _self.get('rootOrgText'), //必填项
                url: '/kmms/jobRecordAction/getDepart', //必填项
                autoHide: true,
                showRoot: false, //不显示根节点
                params: { workShopName: _self.get("orgName") },
                align: {
                    points: ['bl', 'tl']
                },
                zIndex: '10000',
                width: 200,
                height: 200
            });
            orgPicker.render();
            _self.set('workareaOrgPicker', orgPicker);
        },
        _initOrganizationPicker: function() {
            var _self = this;
            var orgPicker = new OrganizationPicker({
                trigger: '#workshop',
                rootOrgId: _self.get('rootOrgId'), //必填项
                rootOrgText: _self.get('rootOrgText'), //必填项
                url: '/kmms/jobRecordAction/getShopAndDepart', //必填项
                autoHide: true,
                showRoot: false, //不显示根节点
                align: {
                    points: ['bl', 'tl']
                },
                zIndex: '10000',
                width: 200,
                height: 200
            });
            orgPicker.render();
            _self.set('workshopOrgPicker', orgPicker);
        },
        /**
         * 初始化时间查询
         * @private
         */
        _initDate: function() {
            var _self = this;
            var startDate = new Calendar.DatePicker({ //加载日历控件
                trigger: '#startDate',
                autoRender: true,
                textField: '#startDate'
            });
            _self.set('startDate', startDate);
            var endDate = new Calendar.DatePicker({ //加载日历控件
                trigger: '#endDate',
                autoRender: true,
                textField: '#endDate'
            });
            _self.set('endDate', endDate);
        },
        _initProject: function() {
            var nameData = ['值班情况', '日常维修工作', '故障处理', '专项活动', '施工配合', '重要文电学习', '外出培训学习情况', '休假情况', '其他'];
            var suggest = new SelectSuggest({
                renderName: '#projectDiv',
                inputName: 'project',
                renderData: nameData,
                width: 200
            });
        },
        /**
         * 初始化列表字段
         * @private
         */
        _initColumns: function() {
            var _self = this;
            var columns = [{
                    title: '日期',
                    dataIndex: 'date',
                    elCls: 'center',
                    width: '35%',
                }, {
                    title: '填报部门',
                    dataIndex: 'orgName',
                    elCls: 'center',
                    width: '35%'
                },
                {
                    title: '操作',
                    dataIndex: 'dateAndOrgId',
                    elCls: 'center',
                    width: '30%',
                    renderer: function(e) {
                        var date = e.split(",")[0]; //date
                        var orgId = e.split(",")[1]; //orgId
                        if (_self.get("orgType") == 1503) {
                            if (date == _self.get("currentDay")) {
                                return '<span  class="grid-command editBtn">编辑</span>' + '<span  class="grid-command infoBtn">详情</span>';
                            } else {
                                return '<span  class="grid-command infoBtn">详情</span>';
                            }
                        } else if (_self.get("orgType") == 1502) {
                            if (date == _self.get("currentDay") && orgId == _self.get("orgId")) {
                                return '<span  class="grid-command editBtn">编辑</span>' + '<span  class="grid-command infoBtn">详情</span>';
                            } else {
                                return '<span  class="grid-command infoBtn">详情</span>';
                            }
                        } else {
                            return '<span  class="grid-command infoBtn">详情</span>';
                        }
                    }
                }
            ];
            return columns;
        },
        /**
         * 初始化列表数据对象
         * @private
         */
        _initStore: function() {
            var _self = this;
            var store = new Data.Store({
                url: "/kmms/jobRecordQueryAction/findAll.cn",
                autoLoad: true,
                pageSize: 20,
                proxy: {
                    method: 'post',
                    dataType: 'json'
                },
                params: { collectionName: _self.get('collectionName'), orgType: _self.get("orgType"), orgId: _self.get("orgId") }
            });
            _self.set('store', store);
            return store;
        },
        /**
         * 初始化列表展示对象
         * @private
         */
        _initSearchGrid: function() {
            var _self = this;
            var searchGrid = null;
            if (_self.get("orgType") == 1503 || _self.get("orgType") == 1502) {
                searchGrid = {
                    tbarItems: [{
                        id: 'import',
                        btnCls: 'button button-small importBtn',
                        text: '<i class="icon-upload"></i>导入',
                    }, {
                        xclass: 'bar-item-separator' // 竖线分隔符
                    }, {
                        id: 'exportBy',
                        btnCls: 'button button-small exportBtn',
                        text: '<i class="icon-download "></i>导出' +
                            '<form action="/kmms/jobRecordAction/exportXlsBy" id="exportForm" method="post">' +
                            '<input type="hidden" name="exportXlsJson" id="exportXlsJson" />' +
                            '</form>',
                    }],
                    plugins: [
                        Grid.Plugins.CheckSelection,
                        Grid.Plugins.RowNumber
                    ],
                };
            } else {
                searchGrid = {
                    tbarItems: [

                    ],
                    plugins: [
                        Grid.Plugins.CheckSelection,
                        Grid.Plugins.RowNumber
                    ],
                };
            }
            return searchGrid;
        }
    }, {
        ATTRS: {
            perId: {},
            currentDay: {}, //当前系统时间
            userId: {}, //登录用户ID
            userName: {}, //登录用户名称
            orgId: {}, //登录用户组织机构ID
            parentId: {}, //登陆用户的上级机构ID
            orgName: {}, //登录用户组织机构名称
            orgType: {}, //登录用户组织机构类型（1503：工区，1502：车间，1501：科室，1500：昆明铁路局）
            rootOrgId: { value: '8affa073533aa3d601533bbef63e0010' },
            rootOrgText: { value: '昆明通信段' },
            collectionName: { value: 'jobRecord' },
            contextPath: {}
        }
    });
    return jobRecordQueryPage;
});