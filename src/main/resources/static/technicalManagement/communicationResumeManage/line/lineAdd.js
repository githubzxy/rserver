/**
 * 新增
 * @author Bili
 * @date 18-11-21
 */
define('kmms/technicalManagement/communicationResumeManage/line/lineAdd',
		   ['bui/common',
			'common/form/FormContainer',
			'bui/form',
    ],function(r){
    var BUI = r('bui/common'),
        FormContainer= r('common/form/FormContainer');
    var lineAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._getWorkshops();//获取车间下拉选数据

        },
        bindUI : function(){
            var _self = this;
            //工区下拉选选项根据车间而变化
            $("#formContainer #workshop").on('change', function() {
                $("#formContainer #workArea").empty();
                var workshop = $("#formContainer #workshop").val();
                _self._getWorkAreas(workshop);

            });
            
            //定义按键
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(e){
                        var success = _self.get('success');
                        if(success){
                            success.call(_self);
                        }
                    }
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
         * 获取车间
         */
        _getWorkshops:function(){
        	var _self=this;
       	 $.ajax({
	                url:'/kmms/transAction/getCadreAndShop',
	                type:'post',
	                dataType:"json",
	                success:function(res){
		             $("#formContainer #workshop").append("<option  value=''>请选择</option>");
	               	 for(var i=0;i<res.length;i++){
	               		 $("#formContainer #workshop").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
	               	 }
	               	 var workshop = $("#formContainer #workshop").val();
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
			          $("#formContainer #workArea").append("<option  value=''>请选择</option>");
	               	 for(var i=0;i<res.length;i++){
	               	  $("#formContainer #workArea").append("<option  value="+res[i].value+">"+res[i].value+"</option>");
	               	 }
                }
            });
        },
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
				{
                    label : '车间：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select type="text" name="workshop" style="width:98%" id="workshop" data-rules="{required:true}"></select>'
                },{
                    label : '班组：',
                    redStarFlag : true,
                    itemColspan : 1,
					item : '<select name="workArea" id="workArea" style="width:99.5%" data-rules="{required:true}"/></select>'
                },{
                    label : '组合分类：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="combinationClass" id="combinationClass" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '设备分类：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="deviceClass" id="deviceClass" style="width:98.5%" data-rules="{required:true}"/>'
                },{
                    label : '设备（设施）编码：',
                    itemColspan : 1,
                    item : '<input type="text" name="deviceCode" id="deviceCode" style="width:98.5%" />'
                },{
                    label : '设备名称：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="deviceName" id="deviceName" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '敷设长度（皮长公里）：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="laidLength" id="laidLength" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '敷设方式：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="laidMethod" id="laidMethod" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '安装位置：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : 
                    	   '<table class="wxsbTable" style="width:100%;table-layout:fixed" ><tbody>'+	
                    	   '<tr class="thTdPadding">'+
						   '<th style="width:20%"  colspan="2" >所属线路：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_line" name="site_line"  style="width:97%;"  />'+
						   '</td>'+
						   '</tr>'+
	                       '<tr class="thTdPadding">'+
					       '<th rowspan="3">起点</th>'+
					       '<th style="width:20%" >车站：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_station" name="site_start_station" class="start_station" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" >安装地点：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_place" name="site_start_place" class="start_station" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" "><span style="color:red">*</span>机房、接入点编码：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_machineRoomCode" name="site_start_machineRoomCode" class="start_station" data-rules="{required:true}" style="width:98%;" />'+
						   '</td>'+
					       '</tr>'+
					       '<tr class="thTdPadding">'+
					       '<th rowspan="3">止点</th>'+
					       '<th style="width:20%" >车站：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_station" name="site_end_station" class="start_range" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" >安装地点：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_place" name="site_end_place" class="start_range" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" "><span style="color:red">*</span>机房、接入点编码：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_machineRoomCode" name="site_end_machineRoomCode" class="start_station" data-rules="{required:true}" style="width:98%;" />'+
						   '</td>'+
					       '</tr>'+
					       '</tbody>'+
					       '</table>'
					     
                },{
                    label : '资产归属：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="assetOwnership" id="assetOwnership" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '权属单位名称：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="ownershipUnitName" id="ownershipUnitName" style="width:98.5%" data-rules="{required:true}"/>'
                },{
                    label : '权属单位编码：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="ownershipUnitCode" id="ownershipUnitCode" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '维护主体：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="maintainBody" id="maintainBody" style="width:98.5%" data-rules="{required:true}"/>'
                },{
                    label : '维护单位名称：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="maintainUnitName" id="maintainUnitName" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '维护单位编码：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="maintainUnitCode" id="maintainUnitCode" style="width:98.5%" data-rules="{required:true}"/>'
                },{
                    label : '设备厂家：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="manufacturers" id="manufacturers" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '设备型号：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="deviceType" id="deviceType" style="width:98.5%" data-rules="{required:true}"/>'
                },{
                    label : '使用单位：',
                    itemColspan : 1,
                    redStarFlag : true,
                    item : '<input type="text" name="useUnit" id="useUnit" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '线路类型：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="lineType" id="lineType" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '在用对数：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="useLogarithm" id="useLogarithm" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '出厂日期：',
                    itemColspan : 1,
                    item : '<input type="text" class="calendar" readonly name="productionDate" id="productionDate" style="width:98.5%" />'
                },{
                    label : '使用日期：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" class="calendar" readonly name="useDate" id="useDate" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '设备运行状态：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="deviceOperationStatus" id="deviceOperationStatus" style="width:98.5%" data-rules="{required:true}"/>'
                },{
                    label : '停用日期：',
                    itemColspan : 1,
                    item : '<input type="text" class="calendar"  readonly name="stopDate" id="stopDate" style="width:99%" />'
                },{
                    label : '报废日期：',
                    itemColspan : 1,
                    item : '<input type="text" class="calendar" readonly name="scrapDate" id="scrapDate" style="width:98.5%" />'
                },{
                    label : '固资编号：',
                    itemColspan : 2,
                    item : '<input type="text" name="fixedAssetsCode" id="fixedAssetsCode" style="width:99%" />'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea name="remark" id="remark" style="width:98.5%;height:50px"/></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'lineAddForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
        
    },{
        ATTRS : {
            id : {value : 'lineDialog'},
            elAttrs : {value: {id:"lineAdd"}},
            title:{value:'新增线条属性'},
            width:{value:630},
            height:{value:500},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},
            orgId:{},
            orgName:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('lineAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('orgId',_self.get('orgId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/lineAction/lineAddDoc");
                    xhr.send(formData);
                    xhr.onload = function (e) {
                        if (e.target.response) {
                            var result = JSON.parse(e.target.response);
                            _self.fire("completeAddSave",{
                                result : result
                            });
                        }
                    }

                }
            },
            events : {
                value : {
                    /**
                     * 绑定保存按钮事件
                     */
                    'completeAddSave' : true,

                }
            },
            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
            rootOrgText:{value:'昆明通信段'},
        }
    });
    return lineAdd;
});