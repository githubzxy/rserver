/**
 * 线别新增
 * @author zhouxingyu
 * @date 19-3-27
 */
define('kmms/technicalManagement/lineNameMangement/lineNameMangementAdd',
		[
			'bui/common',
			'bui/data',
			'common/form/FormContainer',
			'common/data/PostLoad',
		],function(r){
	var BUI = r('bui/common');
	var Data = r('bui/data');
	var	FormContainer = r('common/form/FormContainer');
	var	PostLoad = r('common/data/PostLoad');
	var lineNameMangementAdd = BUI.Overlay.Dialog.extend({
		/**
		 * 初始化
		 */
		initializer: function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		
		/**
		 * 渲染
		 */
		renderUI: function(){
			var _self = this;
		},

		/**
		 * 绑定事件
		 */
		bindUI: function(){
			var _self = this;
            //定义按键
			var buttons = [
				{
					text: '保存',
					elCls: 'button',
					handler: function(){
						_self._saveData();
					}
				},{
					text: '关闭',
					elCls: 'button',
					handler: function(){
						_self.close();
					}
				}
			];
			_self.set('buttons',buttons);
		},
		
		/**
		 * 初始化表单
		 */
		_initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
			   {
					label: '线别：',
					itemColspan: 2,
					item : '<input type="text" id="railLine" name="railLine" style="width:99%;"/>'
				},{
					label: '备注：',
					itemColspan : 2,
					item : '<textarea name="desc" id="desc" style="width:98%;height:100px;overflow-y :auto" maxlength="250" placeholder="最多输入250字"/>'
				}
            ];
            var form = new FormContainer({
                id : 'fileFormAdd',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        },
		
		//保存数据
		_saveData:function(){
			var _self = this;
    		var form = _self.getChild('fileFormAdd',true);
    		//验证不通过
    		if(!form.isValid()){
    			return;
    		}
    		var data = form.serializeToObject();
//			data.userId = _self.get('userId');
//			data.orgId = _self.get('orgId');
    		//提交到数据库
    		var postLoad = new PostLoad({
				url : '/zuul/kmms/lineNameMangementAction/addLine',
				el : _self.get('el'),
				loadMsg : '保存中...'
			}); 
    		
			postLoad.load(data, function(result){
				
				if(result.status==2){
					//消息提示框
					BUI.Message.Confirm(result.msg,null,'error');
				}else if(result != null){
					_self.fire("completeAddSave",{
						result : result
					});
				}
			});
		}
	},{
		ATTRS: {
			id: {value : 'fileAddDialog'},
			title: {value : '新增线别'},
			width: {value:400},
	        height: {value:240},
	        contextPath: {},
	        userId:{},
            orgId:{},
	        closeAction: {value: 'destroy'},//关闭时销毁加载到主页面的HTML对象
	        mask: {value: true},
	        events: {
	        	value: {
	        		'completeAddSave' : true,
	        	}
	        }
		}
	});
	return lineNameMangementAdd;
});