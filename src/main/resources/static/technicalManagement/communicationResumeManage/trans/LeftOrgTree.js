/**
 * 左边树结构模块
* @author zhouxy
 * @date 19-6-19
 */
define('kmms/technicalManagement/communicationResumeManage/trans/LeftOrgTree',[
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
				var data = [];
				if(_self.get('systemType')=="trans"){
					data = [{id : 'trans_dwdm',text : "波分(DWDM)-01"},
			    	    {id : 'trans_sdh',text : "同步数字体系(SDH)-02"},
			    	    {id : 'trans_numberTrans',text : "准同步数字传输体系-03"},
			    	    {id : 'trans_other',text : "其它设备-04"},
			    	    {id : 'trans_otn',text : "光传送网(OTN)-05"}
			    	]
				}
				if(_self.get('systemType')=="tat"){
					data = [{id : 'tat_spc',text : "程控交换机-01"},
			    	    {id : 'tat_other',text : "其它设备-02"},
			    	]
				}
				if(_self.get('systemType')=="video"){
					data = [{id : 'video_video',text : "视频监控类-01"},
						{id : 'video_irontower',text : "铁塔-02"},
						{id : 'video_vidicon',text : "摄像机-03"},
					]
				}
				if(_self.get('systemType')=="wireless"){
					data = [{id : 'wireless_wireless',text : "无线类-01"},
						{id : 'wireless_cable',text : "漏泄同轴电缆-02"},
						{id : 'wireless_radio',text : "车站电台-03"},
						{id : 'wireless_irontower',text : "铁塔-04"},
						{id : 'wireless_mobile',text : "移动设备-05"},
						{id : 'wireless_hand',text : "手持设备-06"},
					]
				}
				if(_self.get('systemType')=="wired"){
					data = [{id : 'wired_wired',text : "有线类-01"},
					]
				}
				if(_self.get('systemType')=="conference"){
					data = [{id : 'conference_mcu',text : "MCU-01"},
						{id : 'conference_other',text : "其它-02"},
					]
				}
				if(_self.get('systemType')=="data"){
					data = [{id : 'data_router',text : "路由器-01"},
						{id : 'data_changer',text : "交换机-02"},
						{id : 'data_other',text : "其它设备-03"},
					]
				}
				if(_self.get('systemType')=="dispatch"){
					data = [{id : 'dispatch_trunk',text : "干线调度-01"},
						{id : 'dispatch_dispatchChanger',text : "调度交换机-02"},
						{id : 'dispatch_stationChanger',text : "车站交换机-03"},
						{id : 'dispatch_duty',text : "车站值班台-04"},
						{id : 'dispatch_other',text : "其它设备-05"},
						{id : 'dispatch_station',text : "调度操作台-06"},
					]
				}
				if(_self.get('systemType')=="line"){
					data = [{id : 'line_light',text : "光缆属性-01"},
						{id : 'line_electricity',text : "电缆属性-02"},
						{id : 'line_line',text : "线条属性-03"},
						{id : 'line_lineAndLight',text : "线缆与光缆配套(含电杆类)-04"},
						{id : 'line_pipeline',text : "管道-05"},
						{id : 'line_hole',text : "人孔(手孔)-06"},
						{id : 'line_other',text : "其他线缆-07"},
					]
				}
				
//				var orgStore = new Data.TreeStore({
//					 map:{
//						  'name' : 'text',	// 节点显示文本
//						  'value' : 'id', //用于区分是否为传输系统和保护子网
//						  'isdept' : 'leaf'	// 是否为叶子节点
//					  },
//					  root:{
//						  text : "昆明通信段",
//						  id : "8affa073533aa3d601533bbef63e0010"
//					  },
//					  pidField : "text", // 异步树必须设置该属性：标示父元素id的字段名称
//					  proxy : {
//							url : '/kmms/commonAction/getChildrenByPidAndCurId',
//							method : 'post'
//					  },
//					  autoHide: true,
//					  align : {
//	                        points:['bl','tl']
//	                    },
//			          autoLoad : true
//				});
				var orgStore = new Data.TreeStore({
					    root : {
					      text : '传输与接入网系统',
					      id : 'root'
					    },
//					    data : [{id : 'trans_dwdm',text : "波分(DWDM)-01"},
//					    	    {id : 'trans_sdh',text : "同步数字体系(SDH)-02"},
//					    	    {id : 'trans_numberTrans',text : "准同步数字传输体系-03"},
//					    	    {id : 'trans_other',text : "其它设备-04"},
//					    	    {id : 'trans_otn',text : "光传送网(OTN)-05"}
//					    	    
//					    ] //会加载成root的children
					    data : data
				});

				_self.set('orgStore',orgStore);
				var orgTree = new Tree.TreeList({
					id : 'orgTree',
					itemTpl : '<li id="{id}">{text}</li>', //列表项的模板
					height : _self.get('height')-2,
					store : orgStore,
					showLine : true ,//显示连接线
//					showRoot : true,
					elStyle:{'overflow-x':'hidden','overflow-y':'auto'},
				});
				_self.set('orgTree',orgTree);
				return orgTree;
			}
		},{
			ATTRS : {
				id : {},
				elCls:{value:'leftOrgTree'},
				width:{value:'215px'},
				height : {value : $(window).height() - 20},
				elStyle:{value:{border:'1px solid #CDC9C9'}},
				perId:{},
				systemType : {},//用于确定树的展示内容
				events : {
					value : {
						'updateOrgName' : true,
					}
				}
			}
		});
		return LeftOrgTree;
});