/**
 * 新建文件夹
 * @author roysong
 * @date 19-5-15
 */
define('kmms/integratedManage/fileShare/addDic',['bui/common','common/form/FormContainer',
    'bui/form','common/data/PostLoad',],function(r){
    var BUI = r('bui/common'),
        FormContainer= r('common/form/FormContainer'),
        PostLoad = r('common/data/PostLoad');
    var addDic = BUI.Overlay.Dialog.extend({
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
                        if(_self.onCancel() !== false){
                        	_self.close();
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
            var _self = this,colNum = 2;
            var children = [
                {
                    label : '当前路径：',
                    itemColspan : 2,
                    item : '<label>' + _self.get('fullPath') + '</label>'
                },{
                    label : '新建目录：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : '<input type="text" name="name" id="dicName" value="" data-rules="{required : true}"/>'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea  name="comment" id="dicComment" style="height:20px;width:99.5%"></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'addDicForm',
                colNum : colNum,
                formChildrens : children,
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'addDicDialog'},
            elAttrs : {value: {id:"addDic"}},
            title:{value:'新建目录'},
            width:{value:610},
            height:{value:265},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            userName:{},
            orgId:{},
            orgName:{},
            fullPath:{},
            navData : {},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('addDicForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
	        		var data = formAdd.serializeToObject();
	        		data.collectionName=_self.get('collectionName');
	        		data.userId=_self.get('userId');
	        		data.userName=_self.get('userName');
	        		data.orgId=_self.get('orgId');
	        		data.orgName=_self.get('orgName');
	        		var navData = _self.get('navData');
	        		var last = navData.pop();
	        		// 如果最后一个导航目录是查询结果，则弃用
	        		if(last.id != '-1') navData.push(last);
	        		data.nav = navData;
	        		data.parentId = navData[navData.length - 1].id;
	        		data.status="1";//有效
	        		data.type = "1";//代表目录
	        		var pl = new PostLoad({
        				url : '/kmms/fileShareAction/addDic',
        				el : _self.get('el'),
        				loadMsg : '新建目录中...'
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
    return addDic;
});