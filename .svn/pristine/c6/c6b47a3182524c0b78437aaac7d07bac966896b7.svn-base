/**
 * 编辑
 * @author Bili
 * @date 18-11-21
 */
define('kmms/technicalManagement/communicationResumeManage/line/electricityEdit',['bui/common',
	'common/form/FormContainer',
    'bui/form',
    ],function(r){
    var BUI = r('bui/common'),
        FormContainer= r('common/form/FormContainer');
    var electricityEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
        	_self._getWorkshops();//获取车间下拉选数据
        	_self._getShowData();


        },
        bindUI : function(){
            var _self = this;
            //工区下拉选选项根据车间而变化
            $("#formContainer #workshop").on('change', function() {
                $("#formContainer #workArea").empty();
                var workshop = $("#formContainer #workshop").val();
                _self._getWorkAreas(workshop,0);

            });
            
            
            
            $(".start_station").on('input propertychange', function() {
            	if($(".start_station").eq(0).val()!=""||$(".start_station").eq(1).val()!=""||$(".start_station").eq(2).val()!=""){
            		 $(".start_range").attr("placeholder","不能填写");
                     $(".start_range").attr("readonly","readonly");
                     $(".start_other").attr("placeholder","不能填写");
                     $(".start_other").attr("readonly","readonly");
            	}else{
            		 $(".start_range").attr("placeholder","");
                     $(".start_range").removeAttr("readonly");
                     $(".start_other").attr("placeholder","");
                     $(".start_other").removeAttr("readonly");
            	}
              
            });
            $(".end_station").on('input propertychange', function() {
            	if($(".end_station").eq(0).val()!=""||$(".end_station").eq(1).val()!=""||$(".end_station").eq(2).val()!=""){
            		 $(".end_range").attr("placeholder","不能填写");
                     $(".end_range").attr("readonly","readonly");
                     $(".end_other").attr("placeholder","不能填写");
                     $(".end_other").attr("readonly","readonly");
            	}else{
            		 $(".end_range").attr("placeholder","");
                     $(".end_range").removeAttr("readonly");
                     $(".end_other").attr("placeholder","");
                     $(".end_other").removeAttr("readonly");
            	}
              
            });
            $(".start_range").on('input propertychange', function() {
            	if($(".start_range").eq(0).val()!=""||$(".start_range").eq(1).val()!=""||$(".start_range").eq(2).val()!=""){
            		//$(".range").val("不能填写");
            		 $(".start_station").attr("placeholder","不能填写");
                     $(".start_station").attr("readonly","readonly");
                     $(".start_other").attr("placeholder","不能填写");
                     $(".start_other").attr("readonly","readonly");
            	}else{
            		 $(".start_station").attr("placeholder","");
                     $(".start_station").removeAttr("readonly");
                     $(".start_other").attr("placeholder","");
                     $(".start_other").removeAttr("readonly");
            	}
              
            });
            $(".end_range").on('input propertychange', function() {
            	if($(".end_range").eq(0).val()!=""||$(".end_range").eq(1).val()!=""||$(".end_range").eq(2).val()!=""){
            		//$(".range").val("不能填写");
            		 $(".end_station").attr("placeholder","不能填写");
                     $(".end_station").attr("readonly","readonly");
                     $(".end_other").attr("placeholder","不能填写");
                     $(".end_other").attr("readonly","readonly");
            	}else{
            		 $(".end_station").attr("placeholder","");
                     $(".end_station").removeAttr("readonly");
                     $(".end_other").attr("placeholder","");
                     $(".end_other").removeAttr("readonly");
            	}
              
            });
            $(".start_other").on('input propertychange', function() {
            	if($(".start_other").eq(0).val()!=""||$(".start_other").eq(1).val()!=""){
            		//$(".range").val("不能填写");
            		 $(".start_range").attr("placeholder","不能填写");
                     $(".start_range").attr("readonly","readonly");
                     $(".start_station").attr("placeholder","不能填写");
                     $(".start_station").attr("readonly","readonly");
            	}else{
            		 $(".start_range").attr("placeholder","");
                     $(".start_range").removeAttr("readonly");
                     $(".start_station").attr("placeholder","");
                     $(".start_station").removeAttr("readonly");
            	}
            });
            $(".end_other").on('input propertychange', function() {
            	if($(".end_other").eq(0).val()!=""||$(".end_other").eq(1).val()!=""){
            		//$(".range").val("不能填写");
            		 $(".end_range").attr("placeholder","不能填写");
                     $(".end_range").attr("readonly","readonly");
                     $(".end_station").attr("placeholder","不能填写");
                     $(".end_station").attr("readonly","readonly");
            	}else{
            		 $(".end_range").attr("placeholder","");
                     $(".end_range").removeAttr("readonly");
                     $(".end_station").attr("placeholder","");
                     $(".end_station").removeAttr("readonly");
            	}
            });
            
            
            
            //定义按键
            var buttons = [
                {
                    text:'保存',
                    elCls : 'button',
                    handler : function(){
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
         * 获取显示数据
         */
        _getShowData : function(){
            var _self = this,shiftId = _self.get('shiftId');
            $.ajax({
                url:'/kmms/transAction/findById',
                data:{id : shiftId,collectionName:_self.get('collectionName')},
                type:'post',
                dataType:"json",
                success:function(res){
                    var data = res.data;
                    if(data){
                    	_self._getWorkshops(data.workshop);
                    	
                    	    $("#formContainer #workshop").val(data.workshop);
                    	    $("#formContainer #workArea").val(data.workArea);
                    	    $("#formContainer #combinationClass").val(data.combinationClass);
                    	    $("#formContainer #deviceClass").val(data.deviceClass);
                    	    $("#formContainer #deviceCode").val(data.deviceCode);
                    	    $("#formContainer #deviceName").val(data.deviceName);
                    	    $("#formContainer #laidLength").val(data.laidLength);
                    	    $("#formContainer #laidMethod").val(data.laidMethod);
                    	    $("#formContainer #site_start_station_line").val(data.site_start_station_line);
                    	    $("#formContainer #site_start_station_name").val(data.site_start_station_name);
            	    		$("#formContainer #site_start_station_place").val(data.site_start_station_place);
    	    				$("#formContainer #site_start_range_line").val(data.site_start_range_line);
    						$("#formContainer #site_start_range_post").val(data.site_start_range_post);
							$("#formContainer #site_start_range_place").val(data.site_start_range_place);
							$("#formContainer #site_start_other_line").val(data.site_start_other_line);
							$("#formContainer #site_start_other_place").val(data.site_start_other_place);
							$("#formContainer #site_start_machineRoomCode").val(data.site_start_machineRoomCode);
							$("#formContainer #site_end_station_line").val(data.site_end_station_line);
							$("#formContainer #site_end_station_name").val(data.site_end_station_name);
							$("#formContainer #site_end_station_place").val(data.site_end_station_place);
							$("#formContainer #site_end_range_line").val(data.site_end_range_line);
							$("#formContainer #site_end_range_post").val(data.site_end_range_post);
							$("#formContainer #site_end_range_place").val(data.site_end_range_place);
							$("#formContainer #site_end_other_line").val(data.site_end_other_line);
							$("#formContainer #site_end_other_place").val(data.site_end_other_place);
							$("#formContainer #site_end_machineRoomCode").val(data.site_end_machineRoomCode);
							$("#formContainer #assetOwnership").val(data.assetOwnership);
							$("#formContainer #ownershipUnitName").val(data.ownershipUnitName);
							$("#formContainer #ownershipUnitCode").val(data.ownershipUnitCode);
							$("#formContainer #maintainBody").val(data.maintainBody);
							$("#formContainer #maintainUnitName").val(data.maintainUnitName);
							$("#formContainer #maintainUnitCode").val(data.maintainUnitCode);
							$("#formContainer #manufacturers").val(data.manufacturers);
							$("#formContainer #deviceType").val(data.deviceType);
							$("#formContainer #useUnit").val(data.useUnit);
							$("#formContainer #totalCapacity").val(data.totalCapacity);
							$("#formContainer #coreNumber").val(data.coreNumber);
							$("#formContainer #useCoreNumber").val(data.useCoreNumber);
							$("#formContainer #assetRatio").val(data.assetRatio);
							$("#formContainer #productionDate").val(data.productionDate);
							$("#formContainer #useDate").val(data.useDate);
							$("#formContainer #middleRepairDate").val(data.middleRepairDate);
							$("#formContainer #largeRepairDate").val(data.largeRepairDate);
							$("#formContainer #deviceOperationStatus").val(data.deviceOperationStatus);
							$("#formContainer #stopDate").val(data.stopDate);
							$("#formContainer #scrapDate").val(data.scrapDate);
							$("#formContainer #fixedAssetsCode").val(data.fixedAssetsCode);
							$("#formContainer #remark").val(data.remark);
	            		    
            		    if($(".start_station").eq(0).val()!=""||$(".start_station").eq(1).val()!=""||$(".start_station").eq(2).val()!=""){
                   		 $(".start_range").attr("placeholder","不能填写");
                            $(".start_range").attr("readonly","readonly");
                            $(".start_other").attr("placeholder","不能填写");
                            $(".start_other").attr("readonly","readonly");
            		    }
            		    if($(".start_range").eq(0).val()!=""||$(".start_range").eq(1).val()!=""||$(".start_range").eq(2).val()!=""){
                    		//$(".range").val("不能填写");
                    		 $(".start_station").attr("placeholder","不能填写");
                             $(".start_station").attr("readonly","readonly");
                             $(".start_other").attr("placeholder","不能填写");
                             $(".start_other").attr("readonly","readonly");
                    	}
            		    if($(".start_other").eq(0).val()!=""||$(".start_other").eq(1).val()!=""){
                    		//$(".range").val("不能填写");
                    		 $(".start_range").attr("placeholder","不能填写");
                             $(".start_range").attr("readonly","readonly");
                             $(".start_station").attr("placeholder","不能填写");
                             $(".start_station").attr("readonly","readonly");
                    	}
            		    //
            		    if($(".end_station").eq(0).val()!=""||$(".end_station").eq(1).val()!=""||$(".end_station").eq(2).val()!=""){
                      		 $(".end_range").attr("placeholder","不能填写");
                               $(".end_range").attr("readonly","readonly");
                               $(".end_other").attr("placeholder","不能填写");
                               $(".end_other").attr("readonly","readonly");
               		    }
               		    if($(".end_range").eq(0).val()!=""||$(".end_range").eq(1).val()!=""||$(".end_range").eq(2).val()!=""){
                       		//$(".range").val("不能填写");
                       		 $(".end_station").attr("placeholder","不能填写");
                                $(".end_station").attr("readonly","readonly");
                                $(".end_other").attr("placeholder","不能填写");
                                $(".end_other").attr("readonly","readonly");
                       	}
               		    if($(".end_other").eq(0).val()!=""||$(".end_other").eq(1).val()!=""){
                       		//$(".range").val("不能填写");
                       		 $(".end_range").attr("placeholder","不能填写");
                                $(".end_range").attr("readonly","readonly");
                                $(".end_station").attr("placeholder","不能填写");
                                $(".end_station").attr("readonly","readonly");
                       	}
            		    _self._getWorkAreas(data.workshop,data.workArea);

                    }
                }
            })
        },
        /**
         * 获取车间
         */
        _getWorkshops:function(workshop){
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
             		$("#formContainer #workShop option[value='"+workshop+"']").attr("selected","selected");
                     }
       	 
            });

        },
        /**
         * 获取工区
         */
        _getWorkAreas:function(workshop,workArea){
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
                	 $("#formContainer #workArea option[value='"+workArea+"']").attr("selected","selected");
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
                    label : '安装位置(起点)*(车站、区间、其它三选一)：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : 
                    	   '<table class="wxsbTable" style="width:100%;table-layout:fixed" ><tbody>'+	
	                       '<tr class="thTdPadding">'+
					       '<th rowspan="3">车站</th>'+
					       '<th style="width:20%" >所属线路：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_station_line" name="site_start_station_line" class="start_station" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" >车站名称：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_station_name" name="site_start_station_name" class="start_station" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" ">安装地点：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_station_place" name="site_start_station_place" class="start_station" style="width:98%;" />'+
						   '</td>'+
					       '</tr>'+
					       '<tr class="thTdPadding">'+
					       '<th rowspan="3">区间</th>'+
					       '<th style="width:20%" >所属线路：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_range_line" name="site_start_range_line" class="start_range" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" >公里标（米）：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_range_post" name="site_start_range_post" class="start_range" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" ">安装地点：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_range_place" name="site_start_range_place" class="start_range" style="width:98%;" />'+
						   '</td>'+
					       '</tr>'+
					       '<tr class="thTdPadding">'+
					       '<th rowspan="2">其他</th>'+
					       '<th style="width:20%" >所属线路：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_other_line" name="site_start_other_line" class="start_other" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" >安装地点：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_other_place" name="site_start_other_place" class="start_other" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%"  colspan="2" ><span style="color:red">*</span>机房、接入点编码：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_start_machineRoomCode" name="site_start_machineRoomCode"  style="width:97%;" data-rules="{required:true}" />'+
						   '</td>'+
						   '</tr>'+
					       '</tbody>'+
					       '</table>'
					     
                },{
                    label : '安装位置(止点)*(车站、区间、其它三选一)：',
                    redStarFlag : true,
                    itemColspan : 2,
                    item : 
                    	   '<table class="wxsbTable" style="width:100%;table-layout:fixed" ><tbody>'+	
	                       '<tr class="thTdPadding">'+
					       '<th rowspan="3">车站</th>'+
					       '<th style="width:20%" >所属线路：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_station_line" name="site_end_station_line" class="end_station" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" >车站名称：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_station_name" name="site_end_station_name" class="end_station" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" ">安装地点：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_station_place" name="site_end_station_place" class="end_station" style="width:98%;" />'+
						   '</td>'+
					       '</tr>'+
					       '<tr class="thTdPadding">'+
					       '<th rowspan="3">区间</th>'+
					       '<th style="width:20%" >所属线路：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_range_line" name="site_end_range_line" class="end_range" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" >公里标（米）：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_range_post" name="site_end_range_post" class="end_range" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" ">安装地点：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_range_place" name="site_end_range_place" class="end_range" style="width:98%;" />'+
						   '</td>'+
					       '</tr>'+
					       '<tr class="thTdPadding">'+
					       '<th rowspan="2">其他</th>'+
					       '<th style="width:20%" >所属线路：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_other_line" name="site_end_other_line" class="end_other" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%" >安装地点：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_other_place" name="site_end_other_place" class="end_other" style="width:98%;" />'+
						   '</td>'+
						   '</tr>'+
						   '<tr class="thTdPadding">'+
						   '<th style="width:20%"  colspan="2" ><span style="color:red">*</span>机房、接入点编码：'+
					       '</th>'+
					       '<td style="width:30%">'+
						   '<input type="text" id="site_end_machineRoomCode" name="site_end_machineRoomCode"  style="width:97%;" data-rules="{required:true}" />'+
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
                    label : '总容量：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="totalCapacity" id="totalCapacity" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '在用芯（对）数：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="useCoreNumber" id="useCoreNumber" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '资产比例：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="assetRatio" id="assetRatio" style="width:99%" data-rules="{required:true}"/>'
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
                    label : '中修日期：',
                    itemColspan : 1,
                    item : '<input type="text" class="calendar" readonly name="middleRepairDate" id="middleRepairDate" style="width:98.5%" />'
                },{
                    label : '大修日期：',
                    itemColspan : 1,
                    item : '<input type="text" class="calendar" readonly name="largeRepairDate" id="largeRepairDate" style="width:98.5%" />'
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
                id : 'electricityEditForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'electricityEditDialog'},
            elAttrs : {value: {id:"electricityPointEdit"}},
            title:{value:'编辑电缆属性'},
            width:{value:630},
            height:{value:500},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            shiftId:{},
            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('electricityEditForm'),delData=_self.get('delData');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('id',_self.get('shiftId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/lineAction/electricityUpdateDoc");
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
    return electricityEdit;
});