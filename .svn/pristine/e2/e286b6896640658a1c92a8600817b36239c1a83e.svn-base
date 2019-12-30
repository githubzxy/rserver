/**
 * 左边树结构模块
 */
define('kmms/userInfoManager/LeftOrgTree',[
	'bui/common',
	'bui/data',
	'bui/toolbar',
	'bui/tree',
	'common/data/PostLoad',
	'common/org/OrganizationPicker'
	],function(r){
		var BUI = r('bui/common'),
		Toolbar = r('bui/toolbar'),
		Data = r('bui/data'),
		Tree = r('bui/tree'),
		PostLoad = r('common/data/PostLoad'),
		OrganizationPicker = r('common/org/OrganizationPicker');
		var LeftOrgTree = BUI.Component.Controller.extend({
			initializer : function(){
				var _self=this;
				_self.addChild(_self._initTreeList());//应急机构树
			},
			/**
			 * 初始化应急机构树
			 */
			_initTreeList : function(){
				var _self = this;
				var orgStore = new Data.TreeStore({
					 map:{
						  'name' : 'text',	// 节点显示文本
						  'value' : 'id', //用于区分是否为传输系统和保护子网
						  'isdept' : 'leaf'	// 是否为叶子节点
					  },
					  root:{
						  text : "昆明通信段",
						  id : "8affa073533aa3d601533bbef63e0010"
					  },
					  pidField : "text", // 异步树必须设置该属性：标示父元素id的字段名称
					  proxy : {
							url : '/kmms/commonAction/getChildrenByPidAndCurId',
							method : 'post'
					  },
					  autoHide: true,
					  align : {
	                        points:['bl','tl']
	                    },
			          autoLoad : true
				});
				_self.set('orgStore',orgStore);
				var orgTree = new Tree.TreeList({
					id : 'orgTree',
					itemTpl : '<li id="{id}">{text}</li>', //列表项的模板
					height : _self.get('height')-28,
					store : orgStore,
					showLine : true ,//显示连接线				
					showRoot : true,
					elStyle:{'overflow-x':'hidden','overflow-y':'auto'},
				});
				_self.set('orgTree',orgTree);
				return orgTree;
			}
		},{
			ATTRS : {
				id : {},
				elCls:{value:'leftOrgTree'},
				width:{value:'258px'},
				height : {value : $(window).height() - 30},
				elStyle:{value:{border:'1px solid #CDC9C9'}},
				perId:{},
				events : {
					value : {
						'updateOrgName' : true,
					}
				}
			}
		});
		return LeftOrgTree;
});