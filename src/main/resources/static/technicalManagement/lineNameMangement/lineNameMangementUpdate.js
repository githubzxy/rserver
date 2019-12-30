/**
 * 修改线别名称
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/technicalManagement/lineNameMangement/lineNameMangementUpdate',
    [
    	'bui/common',
    	'common/form/FormContainer',
    	'bui/data',
        'common/data/PostLoad', 
        'bui/form',
    ],
    function(r) {
        var BUI = r('bui/common'),
            FormContainer = r('common/form/FormContainer'),
            Data = r('bui/data'),
            PostLoad = r('common/data/PostLoad'),
            Form = r('bui/form');
        var lineNameMangementUpdate = BUI.Overlay.Dialog.extend({
            initializer: function() {
                var _self = this;
                _self.addChild(_self._initFormContainer());
            },
            renderUI: function() {
                var _self = this;
                _self._getShowData();
            },
            bindUI: function() {
                var _self = this;
                //定义按键
                var buttons = [{
                    text: '保存',
                    elCls: 'button',
                    handler: function() {
                        var success = _self.get('success');
                        if (success) {
                            success.call(_self);
                        }
                    }
                }, {
                    text: '关闭',
                    elCls: 'button',
                    handler: function() {
                        if (this.onCancel() !== false) {
                            this.close();
                        }
                    }
                }];
                _self.set('buttons', buttons);
            },
            
            /**
             * 获取显示数据
             */
            _getShowData: function() {
                var _self = this,
                    shiftId = _self.get('shiftId');
                $.ajax({
                    url: '/kmms/lineNameMangementAction/findById',
                    data: { id: shiftId},
                    type: 'post',
                    async: false,
                    dataType: "json",
                    success: function(data) {
                        if (data) {
                            $("#formContainer #desc").val(data.desc);
                            $("#formContainer #railLineEditDialog").val(data.name);
//                         	console.log(data);
                        }
                    }
                });
            },

            /**
             * 初始化FormContainer
             */
            _initFormContainer: function() {
                var _self = this;
                var colNum = 2;
                var childs = [
                {
                    label: '线别名称：',
                    itemColspan: 2,
                    item: '<input type="text" name="railLineEditDialog" id="railLineEditDialog" style="width:99%;"/>' 
                }, {
                    label: '备注：',
                    itemColspan: 2,
                    item: '<textarea name="desc" id="desc" style="width:99%;height:100px;" maxlength="250" placeholder="最多输入250字"/>'
                }];
                var form = new FormContainer({
                    id: 'lineNameMangementUpdateForm',
                    colNum: colNum,
                    formChildrens: childs,
//                    elStyle: { overflowY: 'scroll'}
                });
                _self.set('formContainer', form);
                return form;
            }
        }, {
            ATTRS: {
                id: { value: 'lineNameMangementUpdateFormDialog' },
                elAttrs: { value: { id: "lineNameMangementEdit" } },
                title: { value: '修改' },
        		width: {value:400},
    	        height: {value:240},
                contextPath: {},
                closeAction: { value: 'destroy' }, //关闭时销毁加载到主页面的HTML对象
                mask: { value: true },
                collectionName: {},
                userId: {}, //登录用户ID
                userName: {}, //登录用户名称
                orgId: {}, //登录用户组织机构ID
                orgName: {}, //登录用户组织机构名称
                shiftId: {},
                success: {
                    value: function() {
                        var _self = this;
                        var formAdd = _self.getChild('lineNameMangementUpdateForm');
                        //验证不通过
                        if (!formAdd.isValid()) {
                            return;
                        }
                        //序列化表单成对象，所有的键值都是字符串
                        var data = formAdd.serializeToObject();
                        data.id = _self.get('shiftId');
                        data.userId = _self.get('userId');
                        var pl = new PostLoad({
                            url: '/kmms/lineNameMangementAction/update',
                            el: _self.get('el'),
                            loadMsg: '上传中...'
                        });
                        pl.load(data, function(e) {
                        	if(e.status==2){
            					//消息提示框
            					BUI.Message.Confirm(e.msg,null,'error');
            				}else if (e) {
                                _self.fire("completeAddSave", {
                                    result: e
                                });
                            }
                        });

                    }
                },
                events: {
                    value: {
                        'completeAddSave': true,
                    }
                },
            }
        });
        return lineNameMangementUpdate;
    });