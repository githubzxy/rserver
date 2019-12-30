/**
 * 新增
 * @author xcx
 * @date 19-06-17
 */
define('kmms/technicalManagement/communicationResumeManage/wireless/mobileAdd',
		   ['bui/common',
			'common/form/FormContainer',
			'bui/form',
    ],function(r){
    var BUI = r('bui/common'),
        FormContainer= r('common/form/FormContainer');
    var IrontowerAdd = BUI.Overlay.Dialog.extend({
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
            
            $(".station").on('input propertychange', function() {
            	if($(".station").eq(0).val()!=""||$(".station").eq(1).val()!=""||$(".station").eq(2).val()!=""){
            		 $(".range").attr("placeholder","不能填写");
                     $(".range").attr("readonly","readonly");
                     $(".other").attr("placeholder","不能填写");
                     $(".other").attr("readonly","readonly");
            	}else{
            		 $(".range").attr("placeholder","");
                     $(".range").removeAttr("readonly");
                     $(".other").attr("placeholder","");
                     $(".other").removeAttr("readonly");
            	}
              
            });
            $(".range").on('input propertychange', function() {
            	if($(".range").eq(0).val()!=""||$(".range").eq(1).val()!=""||$(".range").eq(2).val()!=""){

            		 $(".station").attr("placeholder","不能填写");
                     $(".station").attr("readonly","readonly");
                     $(".other").attr("placeholder","不能填写");
                     $(".other").attr("readonly","readonly");
            	}else{
            		 $(".station").attr("placeholder","");
                     $(".station").removeAttr("readonly");
                     $(".other").attr("placeholder","");
                     $(".other").removeAttr("readonly");
            	}
              
            });
            $(".other").on('input propertychange', function() {
            	if($(".other").eq(0).val()!=""||$(".other").eq(1).val()!=""){

            		 $(".range").attr("placeholder","不能填写");
                     $(".range").attr("readonly","readonly");
                     $(".station").attr("placeholder","不能填写");
                     $(".station").attr("readonly","readonly");
            	}else{
            		 $(".range").attr("placeholder","");
                     $(".range").removeAttr("readonly");
                     $(".station").attr("placeholder","");
                     $(".station").removeAttr("readonly");
            	}
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
                    label : '安装位置-所属线路：',
                    itemColspan : 1,
                    item : '<input type="text" name="site_line" id="site_line" style="width:99%"/>'
                },{
                    label : '安装位置-车型：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="site_vehicleType" id="site_vehicleType" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '安装位置-车型代码\\车辆型号代码：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="site_vehicleCode" id="site_vehicleCode" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '安装位置-机车号\\车辆号：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="site_vehicleNumber" id="site_vehicleNumber" style="width:99%" data-rules="{required:true}"/>'
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
                    label : '权属单位编号：',
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
                    item : '<input type="text" name="maintainUnitCode" id="maintainUnitCode" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '容量：',
                    itemColspan : 1,
                    item : '<input type="text" name="capacity" id="capacity" style="width:99%"/>'
                },{
                    label : '设备厂家：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="manufacturers" id="manufacturers" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '设备型号：',
                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text" name="deviceType" id="deviceType" style="width:99%" data-rules="{required:true}"/>'
                },{
                    label : '使用单位：',
                    itemColspan : 1,
                    item : '<input type="text" name="useUnit" id="useUnit" style="width:99%" />'
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
                    itemColspan : 1,
                    item : '<input type="text" name="fixedAssetsCode" id="fixedAssetsCode" style="width:99%" />'
                },{
                    label : '备注：',
                    itemColspan : 2,
                    item : '<textarea name="remark" id="remark" style="width:98.5%;height:50px"/></textarea>'
                }
            ];
            var form = new FormContainer({
                id : 'mobileAddForm',
                colNum : colNum,
                formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
        
    },{
        ATTRS : {
            id : {value : 'mobileDialog'},
            elAttrs : {value: {id:"mobileAdd"}},
            title:{value:'新增移动设备-05'},
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
                    var formAdd = _self.getChild('mobileAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('userId',_self.get('userId'));
                    formData.append('orgId',_self.get('orgId'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/zuul/kmms/wirelessAction/addDocMobile");
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
    return IrontowerAdd;
});