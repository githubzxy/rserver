/**
 * 设备履历-传输与接入网系统
 * @author yangsy
 * @date 19-6-13
 */
define('kmms/technicalManagement/communicationResumeManage/trans/transmissionNetworkSystemPage',['bui/common','bui/data','bui/grid',
	'bui/calendar',
    'common/grid/SearchGridContainer',
	'common/container/LeftRightContainer',
	'kmms/technicalManagement/communicationResumeManage/trans/LeftOrgTree',
	//trans
	'kmms/technicalManagement/communicationResumeManage/trans/dwdmAdd',
    'kmms/technicalManagement/communicationResumeManage/trans/dwdmEdit',
    'kmms/technicalManagement/communicationResumeManage/trans/dwdmInfo',
    'kmms/technicalManagement/communicationResumeManage/trans/sdhAdd',
    'kmms/technicalManagement/communicationResumeManage/trans/sdhEdit',
    'kmms/technicalManagement/communicationResumeManage/trans/sdhInfo',
    'kmms/technicalManagement/communicationResumeManage/trans/numberTransAdd',
    'kmms/technicalManagement/communicationResumeManage/trans/numberTransEdit',
    'kmms/technicalManagement/communicationResumeManage/trans/numberTransInfo',
    'kmms/technicalManagement/communicationResumeManage/trans/otherAdd',
    'kmms/technicalManagement/communicationResumeManage/trans/otherEdit',
    'kmms/technicalManagement/communicationResumeManage/trans/otherInfo',
    'kmms/technicalManagement/communicationResumeManage/trans/otnAdd',
    'kmms/technicalManagement/communicationResumeManage/trans/otnEdit',
    'kmms/technicalManagement/communicationResumeManage/trans/otnInfo',
    //tat
    'kmms/technicalManagement/communicationResumeManage/tat/spcAdd',
    'kmms/technicalManagement/communicationResumeManage/tat/spcEdit',
    'kmms/technicalManagement/communicationResumeManage/tat/spcInfo',
    'kmms/technicalManagement/communicationResumeManage/tat/otherAdd',
    'kmms/technicalManagement/communicationResumeManage/tat/otherEdit',
    'kmms/technicalManagement/communicationResumeManage/tat/otherInfo',
    //video
    'kmms/technicalManagement/communicationResumeManage/video/videoAdd',
    'kmms/technicalManagement/communicationResumeManage/video/videoEdit',
    'kmms/technicalManagement/communicationResumeManage/video/videoInfo',
    'kmms/technicalManagement/communicationResumeManage/video/irontowerAdd',
    'kmms/technicalManagement/communicationResumeManage/video/irontowerEdit',
    'kmms/technicalManagement/communicationResumeManage/video/irontowerInfo',
    'kmms/technicalManagement/communicationResumeManage/video/vidiconAdd',
    'kmms/technicalManagement/communicationResumeManage/video/vidiconEdit',
    'kmms/technicalManagement/communicationResumeManage/video/vidiconInfo',
    //wireless
    'kmms/technicalManagement/communicationResumeManage/wireless/wirelessAdd',
    'kmms/technicalManagement/communicationResumeManage/wireless/wirelessEdit',
    'kmms/technicalManagement/communicationResumeManage/wireless/wirelessInfo',
    'kmms/technicalManagement/communicationResumeManage/wireless/cableAdd',
    'kmms/technicalManagement/communicationResumeManage/wireless/cableEdit',
    'kmms/technicalManagement/communicationResumeManage/wireless/cableInfo',
    'kmms/technicalManagement/communicationResumeManage/wireless/radioAdd',
    'kmms/technicalManagement/communicationResumeManage/wireless/radioEdit',
    'kmms/technicalManagement/communicationResumeManage/wireless/radioInfo',
    'kmms/technicalManagement/communicationResumeManage/wireless/irontowerAdd',
    'kmms/technicalManagement/communicationResumeManage/wireless/irontowerEdit',
    'kmms/technicalManagement/communicationResumeManage/wireless/irontowerInfo',
    'kmms/technicalManagement/communicationResumeManage/wireless/mobileAdd',
    'kmms/technicalManagement/communicationResumeManage/wireless/mobileEdit',
    'kmms/technicalManagement/communicationResumeManage/wireless/mobileInfo',
    'kmms/technicalManagement/communicationResumeManage/wireless/handAdd',
    'kmms/technicalManagement/communicationResumeManage/wireless/handEdit',
    'kmms/technicalManagement/communicationResumeManage/wireless/handInfo',
    //wired
    'kmms/technicalManagement/communicationResumeManage/wired/wiredAdd',
    'kmms/technicalManagement/communicationResumeManage/wired/wiredEdit',
    'kmms/technicalManagement/communicationResumeManage/wired/wiredInfo',
    //conference
    'kmms/technicalManagement/communicationResumeManage/conference/mcuAdd',
    'kmms/technicalManagement/communicationResumeManage/conference/mcuEdit',
    'kmms/technicalManagement/communicationResumeManage/conference/mcuInfo',
    'kmms/technicalManagement/communicationResumeManage/conference/otherAdd',
    'kmms/technicalManagement/communicationResumeManage/conference/otherEdit',
    'kmms/technicalManagement/communicationResumeManage/conference/otherInfo',
    //data
    'kmms/technicalManagement/communicationResumeManage/data/routerAdd',
    'kmms/technicalManagement/communicationResumeManage/data/routerEdit',
    'kmms/technicalManagement/communicationResumeManage/data/routerInfo',
    'kmms/technicalManagement/communicationResumeManage/data/changerAdd',
    'kmms/technicalManagement/communicationResumeManage/data/changerEdit',
    'kmms/technicalManagement/communicationResumeManage/data/changerInfo',
    'kmms/technicalManagement/communicationResumeManage/data/otherAdd',
    'kmms/technicalManagement/communicationResumeManage/data/otherEdit',
    'kmms/technicalManagement/communicationResumeManage/data/otherInfo',
    //dispatch
    'kmms/technicalManagement/communicationResumeManage/dispatch/trunkAdd',
    'kmms/technicalManagement/communicationResumeManage/dispatch/trunkEdit',
    'kmms/technicalManagement/communicationResumeManage/dispatch/trunkInfo',
    'kmms/technicalManagement/communicationResumeManage/dispatch/dispatchChangerAdd',
    'kmms/technicalManagement/communicationResumeManage/dispatch/dispatchChangerEdit',
    'kmms/technicalManagement/communicationResumeManage/dispatch/dispatchChangerInfo',
    'kmms/technicalManagement/communicationResumeManage/dispatch/stationChangerAdd',
    'kmms/technicalManagement/communicationResumeManage/dispatch/stationChangerEdit',
    'kmms/technicalManagement/communicationResumeManage/dispatch/stationChangerInfo',
    'kmms/technicalManagement/communicationResumeManage/dispatch/dutyAdd',
    'kmms/technicalManagement/communicationResumeManage/dispatch/dutyEdit',
    'kmms/technicalManagement/communicationResumeManage/dispatch/dutyInfo',
    'kmms/technicalManagement/communicationResumeManage/dispatch/otherAdd',
    'kmms/technicalManagement/communicationResumeManage/dispatch/otherEdit',
    'kmms/technicalManagement/communicationResumeManage/dispatch/otherInfo',
    'kmms/technicalManagement/communicationResumeManage/dispatch/stationAdd',
    'kmms/technicalManagement/communicationResumeManage/dispatch/stationEdit',
    'kmms/technicalManagement/communicationResumeManage/dispatch/stationInfo',
    //line
    'kmms/technicalManagement/communicationResumeManage/line/lightAdd',
    'kmms/technicalManagement/communicationResumeManage/line/lightEdit',
    'kmms/technicalManagement/communicationResumeManage/line/lightInfo',
    'kmms/technicalManagement/communicationResumeManage/line/electricityAdd',
    'kmms/technicalManagement/communicationResumeManage/line/electricityEdit',
    'kmms/technicalManagement/communicationResumeManage/line/electricityInfo',
    'kmms/technicalManagement/communicationResumeManage/line/lineAdd',
    'kmms/technicalManagement/communicationResumeManage/line/lineEdit',
    'kmms/technicalManagement/communicationResumeManage/line/lineInfo',
    'kmms/technicalManagement/communicationResumeManage/line/lineAndLightAdd',
    'kmms/technicalManagement/communicationResumeManage/line/lineAndLightEdit',
    'kmms/technicalManagement/communicationResumeManage/line/lineAndLightInfo',
    'kmms/technicalManagement/communicationResumeManage/line/pipelineAdd',
    'kmms/technicalManagement/communicationResumeManage/line/pipelineEdit',
    'kmms/technicalManagement/communicationResumeManage/line/pipelineInfo',
    'kmms/technicalManagement/communicationResumeManage/line/holeAdd',
    'kmms/technicalManagement/communicationResumeManage/line/holeEdit',
    'kmms/technicalManagement/communicationResumeManage/line/holeInfo',
    'kmms/technicalManagement/communicationResumeManage/line/otherAdd',
    'kmms/technicalManagement/communicationResumeManage/line/otherEdit',
    'kmms/technicalManagement/communicationResumeManage/line/otherInfo',
    
    'kmms/technicalManagement/communicationResumeManage/trans/dwdmImport',
    'common/data/PostLoad'
    ], function (r) {
    var BUI = r('bui/common'),
        Data = r('bui/data'),
        Grid = r('bui/grid'),
        Calendar = r('bui/calendar'),
        PostLoad = r('common/data/PostLoad'),
    	LRC = r('common/container/LeftRightContainer'),
		LeftOrgTree = r('kmms/technicalManagement/communicationResumeManage/trans/LeftOrgTree'),
		
	    dwdmAdd = r('kmms/technicalManagement/communicationResumeManage/trans/dwdmAdd'),
	    dwdmEdit = r('kmms/technicalManagement/communicationResumeManage/trans/dwdmEdit'),
	    dwdmInfo = r('kmms/technicalManagement/communicationResumeManage/trans/dwdmInfo'),
	    
	    trans_sdhAdd = r('kmms/technicalManagement/communicationResumeManage/trans/sdhAdd'),
	    trans_sdhEdit = r('kmms/technicalManagement/communicationResumeManage/trans/sdhEdit'),
	    trans_sdhInfo = r('kmms/technicalManagement/communicationResumeManage/trans/sdhInfo'),
	    trans_numberTransAdd = r('kmms/technicalManagement/communicationResumeManage/trans/numberTransAdd'),
	    trans_numberTransEdit = r('kmms/technicalManagement/communicationResumeManage/trans/numberTransEdit'),
	    trans_numberTransInfo = r('kmms/technicalManagement/communicationResumeManage/trans/numberTransInfo'),
	    trans_otherAdd = r('kmms/technicalManagement/communicationResumeManage/trans/otherAdd'),
	    trans_otherEdit = r('kmms/technicalManagement/communicationResumeManage/trans/otherEdit'),
	    trans_otherInfo = r('kmms/technicalManagement/communicationResumeManage/trans/otherInfo'),
	    trans_otnAdd = r('kmms/technicalManagement/communicationResumeManage/trans/otnAdd'),
	    trans_otnEdit = r('kmms/technicalManagement/communicationResumeManage/trans/otnEdit'),
	    trans_otnInfo = r('kmms/technicalManagement/communicationResumeManage/trans/otnInfo'),
	    
	    tat_spcAdd = r('kmms/technicalManagement/communicationResumeManage/tat/spcAdd'),
	    tat_spcEdit = r('kmms/technicalManagement/communicationResumeManage/tat/spcEdit'),
	    tat_spcInfo = r('kmms/technicalManagement/communicationResumeManage/tat/spcInfo'),
	    tat_otherAdd = r('kmms/technicalManagement/communicationResumeManage/tat/otherAdd'),
	    tat_otherEdit = r('kmms/technicalManagement/communicationResumeManage/tat/otherEdit'),
	    tat_otherInfo = r('kmms/technicalManagement/communicationResumeManage/tat/otherInfo'),
	    
	    video_videoAdd = r('kmms/technicalManagement/communicationResumeManage/video/videoAdd'),
	    video_videoEdit = r('kmms/technicalManagement/communicationResumeManage/video/videoEdit'),
	    video_videoInfo = r('kmms/technicalManagement/communicationResumeManage/video/videoInfo'),
	    video_irontowerAdd = r('kmms/technicalManagement/communicationResumeManage/video/irontowerAdd'),
	    video_irontowerEdit = r('kmms/technicalManagement/communicationResumeManage/video/irontowerEdit'),
	    video_irontowerInfo = r('kmms/technicalManagement/communicationResumeManage/video/irontowerInfo'),
	    video_vidiconAdd = r('kmms/technicalManagement/communicationResumeManage/video/vidiconAdd'),
	    video_vidiconEdit = r('kmms/technicalManagement/communicationResumeManage/video/vidiconEdit'),
	    video_vidiconInfo = r('kmms/technicalManagement/communicationResumeManage/video/vidiconInfo'),
	    
	    wireless_wirelessAdd = r('kmms/technicalManagement/communicationResumeManage/wireless/wirelessAdd'),
	    wireless_wirelessEdit = r('kmms/technicalManagement/communicationResumeManage/wireless/wirelessEdit'),
	    wireless_wirelessInfo = r('kmms/technicalManagement/communicationResumeManage/wireless/wirelessInfo'),
	    wireless_cableAdd = r('kmms/technicalManagement/communicationResumeManage/wireless/cableAdd'),
	    wireless_cableEdit = r('kmms/technicalManagement/communicationResumeManage/wireless/cableEdit'),
	    wireless_cableInfo = r('kmms/technicalManagement/communicationResumeManage/wireless/cableInfo'),
	    wireless_radioAdd = r('kmms/technicalManagement/communicationResumeManage/wireless/radioAdd'),
	    wireless_radioEdit = r('kmms/technicalManagement/communicationResumeManage/wireless/radioEdit'),
	    wireless_radioInfo = r('kmms/technicalManagement/communicationResumeManage/wireless/radioInfo'),
	    wireless_irontowerAdd = r('kmms/technicalManagement/communicationResumeManage/wireless/irontowerAdd'),
	    wireless_irontowerEdit = r('kmms/technicalManagement/communicationResumeManage/wireless/irontowerEdit'),
	    wireless_irontowerInfo = r('kmms/technicalManagement/communicationResumeManage/wireless/irontowerInfo'),
	    wireless_mobileAdd = r('kmms/technicalManagement/communicationResumeManage/wireless/mobileAdd'),
	    wireless_mobileEdit = r('kmms/technicalManagement/communicationResumeManage/wireless/mobileEdit'),
	    wireless_mobileInfo = r('kmms/technicalManagement/communicationResumeManage/wireless/mobileInfo'),
	    wireless_handAdd = r('kmms/technicalManagement/communicationResumeManage/wireless/handAdd'),
	    wireless_handEdit = r('kmms/technicalManagement/communicationResumeManage/wireless/handEdit'),
	    wireless_handInfo = r('kmms/technicalManagement/communicationResumeManage/wireless/handInfo'),
	    
	    wired_wiredAdd = r('kmms/technicalManagement/communicationResumeManage/wired/wiredAdd'),
	    wired_wiredEdit = r('kmms/technicalManagement/communicationResumeManage/wired/wiredEdit'),
	    wired_wiredInfo = r('kmms/technicalManagement/communicationResumeManage/wired/wiredInfo'),
	    
	    conference_mcuAdd = r('kmms/technicalManagement/communicationResumeManage/conference/mcuAdd'),
	    conference_mcuEdit = r('kmms/technicalManagement/communicationResumeManage/conference/mcuEdit'),
	    conference_mcuInfo = r('kmms/technicalManagement/communicationResumeManage/conference/mcuInfo'),
	    conference_otherAdd = r('kmms/technicalManagement/communicationResumeManage/conference/otherAdd'),
	    conference_otherEdit = r('kmms/technicalManagement/communicationResumeManage/conference/otherEdit'),
	    conference_otherInfo = r('kmms/technicalManagement/communicationResumeManage/conference/otherInfo'),
	    
	    data_routerAdd = r('kmms/technicalManagement/communicationResumeManage/data/routerAdd'),
	    data_routerEdit = r('kmms/technicalManagement/communicationResumeManage/data/routerEdit'),
	    data_routerInfo = r('kmms/technicalManagement/communicationResumeManage/data/routerInfo'),
	    data_changerAdd = r('kmms/technicalManagement/communicationResumeManage/data/changerAdd'),
	    data_changerEdit = r('kmms/technicalManagement/communicationResumeManage/data/changerEdit'),
	    data_changerInfo = r('kmms/technicalManagement/communicationResumeManage/data/changerInfo'),
	    data_otherAdd = r('kmms/technicalManagement/communicationResumeManage/data/otherAdd'),
	    data_otherEdit = r('kmms/technicalManagement/communicationResumeManage/data/otherEdit'),
	    data_otherInfo = r('kmms/technicalManagement/communicationResumeManage/data/otherInfo'),
	    
	    dispatch_trunkAdd = r('kmms/technicalManagement/communicationResumeManage/dispatch/trunkAdd'),
	    dispatch_trunkEdit = r('kmms/technicalManagement/communicationResumeManage/dispatch/trunkEdit'),
	    dispatch_trunkInfo = r('kmms/technicalManagement/communicationResumeManage/dispatch/trunkInfo'),
	    dispatch_dispatchChangerAdd = r('kmms/technicalManagement/communicationResumeManage/dispatch/dispatchChangerAdd'),
	    dispatch_dispatchChangerEdit = r('kmms/technicalManagement/communicationResumeManage/dispatch/dispatchChangerEdit'),
	    dispatch_dispatchChangerInfo = r('kmms/technicalManagement/communicationResumeManage/dispatch/dispatchChangerInfo'),
	    dispatch_stationChangerAdd = r('kmms/technicalManagement/communicationResumeManage/dispatch/stationChangerAdd'),
	    dispatch_stationChangerEdit = r('kmms/technicalManagement/communicationResumeManage/dispatch/stationChangerEdit'),
	    dispatch_stationChangerInfo = r('kmms/technicalManagement/communicationResumeManage/dispatch/stationChangerInfo'),
	    dispatch_dutyAdd = r('kmms/technicalManagement/communicationResumeManage/dispatch/dutyAdd'),
	    dispatch_dutyEdit = r('kmms/technicalManagement/communicationResumeManage/dispatch/dutyEdit'),
	    dispatch_dutyInfo = r('kmms/technicalManagement/communicationResumeManage/dispatch/dutyInfo'),
	    dispatch_otherAdd = r('kmms/technicalManagement/communicationResumeManage/dispatch/otherAdd'),
	    dispatch_otherEdit = r('kmms/technicalManagement/communicationResumeManage/dispatch/otherEdit'),
	    dispatch_otherInfo = r('kmms/technicalManagement/communicationResumeManage/dispatch/otherInfo'),
	    dispatch_stationAdd = r('kmms/technicalManagement/communicationResumeManage/dispatch/stationAdd'),
	    dispatch_stationEdit = r('kmms/technicalManagement/communicationResumeManage/dispatch/stationEdit'),
	    dispatch_stationInfo = r('kmms/technicalManagement/communicationResumeManage/dispatch/stationInfo'),
	    
	    line_lightAdd = r('kmms/technicalManagement/communicationResumeManage/line/lightAdd'),
	    line_lightEdit = r('kmms/technicalManagement/communicationResumeManage/line/lightEdit'),
	    line_lightInfo = r('kmms/technicalManagement/communicationResumeManage/line/lightInfo'),
	    line_electricityAdd = r('kmms/technicalManagement/communicationResumeManage/line/electricityAdd'),
	    line_electricityEdit = r('kmms/technicalManagement/communicationResumeManage/line/electricityEdit'),
	    line_electricityInfo = r('kmms/technicalManagement/communicationResumeManage/line/electricityInfo'),
	    line_lineAdd = r('kmms/technicalManagement/communicationResumeManage/line/lineAdd'),
	    line_lineEdit = r('kmms/technicalManagement/communicationResumeManage/line/lineEdit'),
	    line_lineInfo = r('kmms/technicalManagement/communicationResumeManage/line/lineInfo'),
	    line_lineAndLightAdd = r('kmms/technicalManagement/communicationResumeManage/line/lineAndLightAdd'),
	    line_lineAndLightEdit = r('kmms/technicalManagement/communicationResumeManage/line/lineAndLightEdit'),
	    line_lineAndLightInfo = r('kmms/technicalManagement/communicationResumeManage/line/lineAndLightInfo'),
	    line_pipelineAdd = r('kmms/technicalManagement/communicationResumeManage/line/pipelineAdd'),
	    line_pipelineEdit = r('kmms/technicalManagement/communicationResumeManage/line/pipelineEdit'),
	    line_pipelineInfo = r('kmms/technicalManagement/communicationResumeManage/line/pipelineInfo'),
	    line_holeAdd = r('kmms/technicalManagement/communicationResumeManage/line/holeAdd'),
	    line_holeEdit = r('kmms/technicalManagement/communicationResumeManage/line/holeEdit'),
	    line_holeInfo = r('kmms/technicalManagement/communicationResumeManage/line/holeInfo'),
	    line_otherAdd = r('kmms/technicalManagement/communicationResumeManage/line/otherAdd'),
	    line_otherEdit = r('kmms/technicalManagement/communicationResumeManage/line/otherEdit'),
	    line_otherInfo = r('kmms/technicalManagement/communicationResumeManage/line/otherInfo'),
	    
	    dwdmImport = r('kmms/technicalManagement/communicationResumeManage/trans/dwdmImport'),
        SearchGridContainer = r('common/grid/SearchGridContainer');
	var LEFT_ORG_TREE_ID = 'leftOrgTreeId';//左边树组件的ID
		
		var Page = LRC.extend({
			initializer: function(){
				var _self = this,left = _self.get('leftChildren'),right = _self.get('rightChildren');
				var orgTree = _self._initLeftOrgTree();
				var searchGrid = _self._initSearchGridContainer();
				left.push(orgTree);
				right.push(searchGrid);
			},
            renderUI:function(){
                var _self=this;
                $("#publicType").val(_self.get('publicType'));
            	_self._getWorkshops();//获取车间下拉选数据
//				_self._showEmerMember('trans_dwdm');
				_self._showEmerMember(_self.get('publicType'));
            },
            bindUI:function(){
                //工区下拉选选项根据车间而变化
                $("#workshop").on('change',function() {
                    $("#workArea").empty();
                    var workshop = $("#workshop").val();
                    _self._getWorkAreas(workshop);

                });
                var _self = this,store = _self.get('store');
				var perId = _self.get('perId');
                var table = _self.getChild(SearchGridContainer.SEARCH_GRID_ID,true);
                var tbar = table.get('tbar');

				var treeComponent = _self.getChild(LEFT_ORG_TREE_ID,true);//获取子组件(树)
//
				var tree = _self.getChild('orgTree',true);//获取左部分TreeList，孙组件，并不是子组件(树)
				tree.on("itemclick", function(e){//定义单击选项事件
    				$("#workshop").val("");
    				$("#workArea").val("");
    				$("#deviceName").val("");
    				$("#deviceClass").val("");
    				$("#deviceType").val("");
    				$("#manufacturers").val("");
//    				$("#publicType").val("");
					var obj = e.item;
					$("#publicType").val(obj.id);
					_self._showEmerMember(obj.id);
				});
                //重写重置按钮的点击事件
    			$("button[type='reset']").on('click',function(event){
    				event.preventDefault();
    				$("#workshop").val("");
    				$("#workArea").val("");
    				$("#deviceName").val("");
    				$("#deviceClass").val("");
    				$("#deviceType").val("");
    				$("#manufacturers").val("");
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
	                           url : '/kmms/transAction/removeDoc.cn',
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
                	if($("#publicType").val()=="trans_dwdm"){
                		var addDialog = new dwdmAdd({
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
                		});
                	}
                	if($("#publicType").val()=="trans_sdh"){
                		var addDialog = new trans_sdhAdd({
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
                		});
                	}
                	if($("#publicType").val()=="trans_numberTrans"){
                		var addDialog = new trans_numberTransAdd({
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
                		});
                	}
                	if($("#publicType").val()=="trans_other"){
                		var addDialog = new trans_otherAdd({
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
                		});
                	}
                	if($("#publicType").val()=="trans_otn"){
                		var addDialog = new trans_otnAdd({
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
                		});
                	}
                	
                	if($("#publicType").val()=="tat_spc"){
                		var addDialog = new tat_spcAdd({
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
                		});
                	}
                	if($("#publicType").val()=="tat_other"){
                		var addDialog = new tat_otherAdd({
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
                		});
                	}
                	
                	if($("#publicType").val()=="video_video"){
                		var addDialog = new video_videoAdd({
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
                		});
                	}
                	if($("#publicType").val()=="video_irontower"){
                		var addDialog = new video_irontowerAdd({
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
                		});
                	}
                	if($("#publicType").val()=="video_vidicon"){
                		var addDialog = new video_vidiconAdd({
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
                		});
                	}
                	
                	if($("#publicType").val()=="wireless_wireless"){
                		var addDialog = new wireless_wirelessAdd({
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
                		});
                	}
                	if($("#publicType").val()=="wireless_cable"){
                		var addDialog = new wireless_cableAdd({
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
                		});
                	}
                	if($("#publicType").val()=="wireless_radio"){
                		var addDialog = new wireless_radioAdd({
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
                		});
                	}
                	if($("#publicType").val()=="wireless_irontower"){
                		var addDialog = new wireless_irontowerAdd({
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
                		});
                	}
                	if($("#publicType").val()=="wireless_mobile"){
                		var addDialog = new wireless_mobileAdd({
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
                		});
                	}
                	if($("#publicType").val()=="wireless_hand"){
                		var addDialog = new wireless_handAdd({
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
                		});
                	}
                	
                	if($("#publicType").val()=="wired_wired"){
                		var addDialog = new wired_wiredAdd({
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
                		});
                	}
                	
                	if($("#publicType").val()=="conference_mcu"){
                		var addDialog = new conference_mcuAdd({
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
                		});
                	}
                	if($("#publicType").val()=="conference_other"){
                		var addDialog = new conference_otherAdd({
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
                		});
                	}
                	
                	if($("#publicType").val()=="data_router"){
                		var addDialog = new data_routerAdd({
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
                		});
                	}
                	if($("#publicType").val()=="data_changer"){
                		var addDialog = new data_changerAdd({
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
                		});
                	}
                	if($("#publicType").val()=="data_other"){
                		var addDialog = new data_otherAdd({
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
                		});
                	}
                	
                	if($("#publicType").val()=="dispatch_trunk"){
                		var addDialog = new dispatch_trunkAdd({
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
                		});
                	}
                	if($("#publicType").val()=="dispatch_dispatchChanger"){
                		var addDialog = new dispatch_dispatchChangerAdd({
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
                		});
                	}
                	if($("#publicType").val()=="dispatch_stationChanger"){
                		var addDialog = new dispatch_stationChangerAdd({
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
                		});
                	}
                	if($("#publicType").val()=="dispatch_duty"){
                		var addDialog = new dispatch_dutyAdd({
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
                		});
                	}
                	if($("#publicType").val()=="dispatch_other"){
                		var addDialog = new dispatch_otherAdd({
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
                		});
                	}
                	if($("#publicType").val()=="dispatch_station"){
                		var addDialog = new dispatch_stationAdd({
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
                		});
                	}
                	
                	if($("#publicType").val()=="line_light"){
                		var addDialog = new line_lightAdd({
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
                		});
                	}
                	if($("#publicType").val()=="line_electricity"){
                		var addDialog = new line_electricityAdd({
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
                		});
                	}
                	if($("#publicType").val()=="line_line"){
                		var addDialog = new line_lineAdd({
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
                		});
                	}
                	if($("#publicType").val()=="line_lineAndLight"){
                		var addDialog = new line_lineAndLightAdd({
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
                		});
                	}
                	if($("#publicType").val()=="line_pipeline"){
                		var addDialog = new line_pipelineAdd({
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
                		});
                	}
                	if($("#publicType").val()=="line_hole"){
                		var addDialog = new line_holeAdd({
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
                		});
                	}
                	if($("#publicType").val()=="line_other"){
                		var addDialog = new line_otherAdd({
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
                		});
                	}
                });
                // 点吉导入数据按钮，弹出导入数据框
    			$('.ImportBtn').click(function (){
    				var ImportForm = new dwdmImport({
    					collectionName:_self.get('collectionName'),
                        userId:_self.get('userId'),
                        orgId:_self.get('orgId'),
                        orgName:_self.get('orgName'),
                        orgType:_self.get('orgType'),
                        publicType:$("#publicType").val(),
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
                 * 修改
                 */
                if(target.hasClass('editBtn')){
                	if($("#publicType").val()=="trans_dwdm"){
                		var editDialog = new dwdmEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="trans_sdh"){
                		var editDialog = new trans_sdhEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="trans_numberTrans"){
                		var editDialog = new trans_numberTransEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="trans_other"){
                		var editDialog = new trans_otherEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="trans_otn"){
                		var editDialog = new trans_otnEdit({
                			collectionName:_self.get('collectionName'),
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
                	
                	if($("#publicType").val()=="tat_spc"){
                		var editDialog = new tat_spcEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="tat_other"){
                		var editDialog = new tat_otherEdit({
                			collectionName:_self.get('collectionName'),
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
                	
                	if($("#publicType").val()=="video_video"){
                		var editDialog = new video_videoEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="video_irontower"){
                		var editDialog = new video_irontowerEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="video_vidicon"){
                		var editDialog = new video_vidiconEdit({
                			collectionName:_self.get('collectionName'),
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
                	
                	if($("#publicType").val()=="wireless_wireless"){
                		var editDialog = new wireless_wirelessEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="wireless_cable"){
                		var editDialog = new wireless_cableEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="wireless_radio"){
                		var editDialog = new wireless_radioEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="wireless_irontower"){
                		var editDialog = new wireless_irontowerEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="wireless_mobile"){
                		var editDialog = new wireless_mobileEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="wireless_hand"){
                		var editDialog = new wireless_handEdit({
                			collectionName:_self.get('collectionName'),
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
                	
                	if($("#publicType").val()=="wired_wired"){
                		var editDialog = new wired_wiredEdit({
                			collectionName:_self.get('collectionName'),
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
                	
                	if($("#publicType").val()=="conference_mcu"){
                		var editDialog = new conference_mcuEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="conference_other"){
                		var editDialog = new conference_otherEdit({
                			collectionName:_self.get('collectionName'),
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
                	
                	if($("#publicType").val()=="data_router"){
                		var editDialog = new data_routerEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="data_changer"){
                		var editDialog = new data_changerEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="data_other"){
                		var editDialog = new data_otherEdit({
                			collectionName:_self.get('collectionName'),
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
                	
                	if($("#publicType").val()=="dispatch_trunk"){
                		var editDialog = new dispatch_trunkEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="dispatch_dispatchChanger"){
                		var editDialog = new dispatch_dispatchChangerEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="dispatch_stationChanger"){
                		var editDialog = new dispatch_stationChangerEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="dispatch_duty"){
                		var editDialog = new dispatch_dutyEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="dispatch_other"){
                		var editDialog = new dispatch_otherEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="dispatch_station"){
                		var editDialog = new dispatch_stationEdit({
                			collectionName:_self.get('collectionName'),
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
                	
                	if($("#publicType").val()=="line_light"){
                		var editDialog = new line_lightEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="line_electricity"){
                		var editDialog = new line_electricityEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="line_line"){
                		var editDialog = new line_lineEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="line_lineAndLight"){
                		var editDialog = new line_lineAndLightEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="line_pipeline"){
                		var editDialog = new line_pipelineEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="line_hole"){
                		var editDialog = new line_holeEdit({
                			collectionName:_self.get('collectionName'),
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
                	if($("#publicType").val()=="line_other"){
                		var editDialog = new line_otherEdit({
                			collectionName:_self.get('collectionName'),
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
                	
                }
               
                /**
                * 详情
                */
                if(target.hasClass('infoBtn')){
                	if($("#publicType").val()=="trans_dwdm"){
                		var infoDialog = new dwdmInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="trans_sdh"){
                		var infoDialog = new trans_sdhInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="trans_numberTrans"){
                		var infoDialog = new trans_numberTransInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="trans_other"){
                		var infoDialog = new trans_otherInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="trans_otn"){
                		var infoDialog = new trans_otnInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	
                	if($("#publicType").val()=="tat_spc"){
                		var infoDialog = new tat_spcInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="tat_other"){
                		var infoDialog = new tat_otherInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	
                	if($("#publicType").val()=="video_video"){
                		var infoDialog = new video_videoInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="video_irontower"){
                		var infoDialog = new video_irontowerInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="video_vidicon"){
                		var infoDialog = new video_vidiconInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	
                	if($("#publicType").val()=="wireless_wireless"){
                		var infoDialog = new wireless_wirelessInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="wireless_cable"){
                		var infoDialog = new wireless_cableInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="wireless_radio"){
                		var infoDialog = new wireless_radioInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="wireless_irontower"){
                		var infoDialog = new wireless_irontowerInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="wireless_mobile"){
                		var infoDialog = new wireless_mobileInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="wireless_hand"){
                		var infoDialog = new wireless_handInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	
                	if($("#publicType").val()=="wired_wired"){
                		var infoDialog = new wired_wiredInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	
                	if($("#publicType").val()=="conference_mcu"){
                		var infoDialog = new conference_mcuInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="conference_other"){
                		var infoDialog = new conference_otherInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	
                	if($("#publicType").val()=="data_router"){
                		var infoDialog = new data_routerInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="data_changer"){
                		var infoDialog = new data_changerInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="data_other"){
                		var infoDialog = new data_otherInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	
                	if($("#publicType").val()=="dispatch_trunk"){
                		var infoDialog = new dispatch_trunkInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="dispatch_dispatchChanger"){
                		var infoDialog = new dispatch_dispatchChangerInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="dispatch_stationChanger"){
                		var infoDialog = new dispatch_stationChangerInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="dispatch_duty"){
                		var infoDialog = new dispatch_dutyInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="dispatch_other"){
                		var infoDialog = new dispatch_otherInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="dispatch_station"){
                		var infoDialog = new dispatch_stationInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	
                	if($("#publicType").val()=="line_light"){
                		var infoDialog = new line_lightInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="line_electricity"){
                		var infoDialog = new line_electricityInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="line_line"){
                		var infoDialog = new line_lineInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="line_lineAndLight"){
                		var infoDialog = new line_lineAndLightInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="line_pipeline"){
                		var infoDialog = new line_pipelineInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="line_hole"){
                		var infoDialog = new line_holeInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	if($("#publicType").val()=="line_other"){
                		var infoDialog = new line_otherInfo({
                			collectionName:_self.get('collectionName'),
                			shiftId:docId
                		});
                		infoDialog.show();
                	}
                	
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
             * 获取科室和车间
             */
            _getWorkshops:function(){
            	var _self=this;
           	 $.ajax({
    	                url:'/kmms/transAction/getCadreAndShop',
    	                type:'post',
    	                dataType:"json",
    	                success:function(res){
    		             $("#workshop").append("<option  value=''>请选择</option>");
    	               	 for(var i=0;i<res.length;i++){
    	               		 $("#workshop").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
    	               	 var workshop = $("#workshop").val();
    	               	 _self._getWorkAreas(workshop);
                    }
                });
            },
            
            /**
             * 获取工区
             */
            _getWorkAreas:function(workshop){
            	var _self=this;
           	 $.ajax({
    	                url:'/kmms/overhaulRecordAction/getworkArea',
    	                type:'post',
    	                dataType:"json",
    	                data: { workshop:workshop},
    	                success:function(res){
    			          $("#workArea").append("<option  value=''>请选择</option>");
    	               	 for(var i=0;i<res.length;i++){
    	               	  $("#workArea").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
    	               	 }
                    }
                });
            },
            /**
			 * 点击左部分树选项，右部分表格跟随显示数据
			 */
			_showEmerMember: function(id){
				var _self = this,store = _self.get('store');
					store.load({
						start : 0, //从第一页开始
						workshop:'',
	    				workArea:'',
	    				deviceName:'',
	    				publicType:id,
	    				deviceClass:'',
	    				deviceType:'',
	    				manufacturers:''
					});
			},
			/**
			 * 初始化左边组织机构树
			 */
			_initLeftOrgTree: function(){
				var _self = this;
				var leftOrgTree = new LeftOrgTree({
					id : LEFT_ORG_TREE_ID,//树控件ID
					perId: _self.get('perId'),
					systemType : _self.get('systemType')
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
                        {label : '车间',item : '<select   id="workshop" style="width: 200px;" name="workshop" readonly/>'},
                        {label : '班组',item : '<select type="text" name="workArea" id="workArea" style="width: 200px;" />'},
                        {label : '设备名称',item : '<input type="text" name="deviceName" id="deviceName" style="width: 175px;" />'+
					    '<input type="hidden"  name="publicType" id="publicType"  />'},
                        {label : '设备分类',item : '<input type="text" name="deviceClass" id="deviceClass" style="width: 175px;" />'},
                        {label : '设备型号',item : '<input type="text" name="deviceType" id="deviceType" style="width: 175px;" />'},
                        {label : '设备厂家',item : '<input type="text" name="manufacturers" id="manufacturers" style="width: 175px;" />'}
                        
                    ]};
            },
            /**
             * 初始化列表字段
             * @private
             */
            _initColumns:function () {
                var columns = [
                    {
                        title:'车间',
                        dataIndex:'workshop',
                        elCls : 'center',
                        width:'20%'
                    },{
                        title:'班组',                       
                        dataIndex:'workArea',
                        elCls : 'center',
                        width:'20%',
                        renderer:Grid.Format.datetimeRenderer
                    },{
                        title:'组合分类',
                        dataIndex:'combinationClass',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'设备分类',
                        dataIndex:'deviceClass',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'设备名称',
                        dataIndex:'deviceName',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'设备型号',
                        dataIndex:'deviceType',
                        elCls : 'center',
                        width:'20%',
                    },{
                        title:'设备厂家',
                        dataIndex:'manufacturers',
                        elCls : 'center',
                        width:'20%',
                    },{
                    	title:'操作',
                        dataIndex:'publicType',
                        elCls : 'center',
                        width:'15%',
                        renderer:function (e) {
                        	return '<span  class="grid-command editBtn">修改</span>'+
	                    	'<span  class="grid-command infoBtn">详情</span>';
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
                    url : "/kmms/transAction/findAll.cn",
                    autoLoad : true ,
                    pageSize : 10,
                    proxy : {
                        method : 'post',
                        dataType : 'json'
                    },
                    params : {collectionName:_self.get('collectionName'),orgId:_self.get('orgId')}
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
                            text : '<i class="icon-upload"></i>导入',
                        }
//                        ,{
//                            xclass : 'bar-item-separator' // 竖线分隔符
//                        },{
//                        	id : 'dow',
//                            btnCls : 'button button-small DownloadBtn',
//                            text : '<i class="icon-download"></i>导出Excel'
//	                            +'<form action="/kmms/userInfoManageAction/exportXls" id="exportForm" method="post">'
//	    						+'<input type="hidden" name="exportXlsJson" id="exportXlsJson" />'
//	    						+'</form>',
//		                  listeners : {
//		                    'click' : function(){
//		                    	 _self._exportData();
//		                    }
//		                  }
//                        }
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
                perId : {},
                userId : {},//登录用户ID
                orgId : {},//登录用户组织机构ID
                orgType : {},//登录用户组织机构类型
                orgName : {},//登录用户组织机构名称
                rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
                rootOrgText:{value:'昆明通信段'},
                publicType : {},
            	systemType : {},
                collectionName : {value:'deviceRecord'}
            }
        });
    return Page;
});