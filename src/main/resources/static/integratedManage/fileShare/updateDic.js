/**
 * 修改目录名弹出框
 * @author roysong
 * @date 19-5-16
 */
define('kmms/integratedManage/fileShare/updateDic',['bui/common','common/form/FormContainer',
    'bui/form','common/data/PostLoad',],function(r){
    var BUI = r('bui/common'),
        FormContainer= r('common/form/FormContainer'),
        PostLoad = r('common/data/PostLoad');
    var updateDic = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        bindUI : function(){
            var _self = this;
            //定义按键
            var buttons = [
            	{
                    text:'保存',
                    elCls : 'button',
                    handler : _self.get('success')
                },{
                    text:'关闭',
                    elCls : 'button',
                    handler : function(){
                        if(this.onCancel() !== false){
                            this.close();
                        }
                    }
                }
            ];
            _self.set('buttons',buttons);
        },
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this,colNum = 2,item = _self.get('item'),fullpath = _self._getFullPath(item);
            var children = [
                {
                    label : '当前路径：',
                    itemColspan : 2,
                    item : '<label>' + fullpath + '</label>'
                },{
                    label : '目录名：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="name" id="dicName" value="'+item.name+'" />'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea  name="comment" id="dicComment" value="'+item.comment+'" style="height:20px;width:99.5%"></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'updateDicForm',
                colNum : colNum,
                formChildrens : children,
            });
            _self.set('formContainer',form);
            return form;
        },
        _getFullPath : function(item){
        	var fullpath = '';
			BUI.each(item.nav,function(e){fullpath += e.name + '/'});
			return fullpath;
        }
    },{
        ATTRS : {
            id : {value : 'updateDicDialog'},
            elAttrs : {value: {id:"updateDic"}},
            title:{value:'修改目录'},
            width:{value:610},
            height:{value:265},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            item:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('updateDicForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		var data = formAdd.serializeToObject();
	        		data.id = _self.get('item').id;
	        		data.collectionName=_self.get('collectionName');
	        		data.userId=_self.get('userId');
	        		data.userName=_self.get('userName');
	        		data.orgId=_self.get('orgId');
	        		data.orgName=_self.get('orgName');
	        		var pl = new PostLoad({
        				url : '/kmms/fileShareAction/updateDic',
        				el : _self.get('el'),
        				loadMsg : '修改目录中...'
        			}); 
        			pl.load({data : JSON.stringify(data)}, function(e){
        				if (e) {
                            _self.fire("completeAddSave",{
                                result : e
                            });
                        }
        				_self.close();
        			});
                }
            },
            events : {
                value : {
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeAddSave' : true,

                }
            }
        }
    });
    return updateDic;
});