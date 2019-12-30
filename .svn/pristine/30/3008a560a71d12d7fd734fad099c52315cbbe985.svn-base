/**
 * 详情模块
 */
define('kmms/technicalManagement/communicationResumeManage/wireless/handInfo',['bui/common',
	'common/form/FormContainer',
	'common/uploader/ViewUploader',
	'common/data/PostLoad'],
	function(r){
	var BUI = r('bui/common'),
		Uploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var IrontowerInfo = BUI.Overlay.Dialog.extend({
		initializer : function(){
			var _self = this;
			_self.addChild(_self._initFormContainer());
		},
		renderUI : function(){
			var _self = this;
			//显示数据
			_self._getShowData();
		},
		bindUI : function(){
			var _self = this;
			//定义按键
			var buttons = [
                {
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
			var _self = this,shiftId = _self.get('shiftId'),form=_self.get("formContainer");
			$.ajax({
				url:'/kmms/wirelessAction/findById',
          		data:{id : shiftId,collectionName:_self.get('collectionName')},
          		type:'post',
          		dataType:"json",
          		success:function(res){
					var data = res.data;
					if(data){
						$("#formContainer #workshop").val(data.workshop);
						$('#formContainer #workArea').val(data.workArea);
						$("#formContainer #combinationClass").val(data.combinationClass);
						$("#formContainer #deviceClass").val(data.deviceClass);
						$("#formContainer #deviceCode").val(data.deviceCode);
						$("#formContainer #deviceName").val(data.deviceName);
						$("#formContainer #site_line").val(data.site_line);

						$("#formContainer #site_department").val(data.site_department);

						$('#formContainer #assetOwnership').val(data.assetOwnership);
						$('#formContainer #ownershipUnitName').val(data.ownershipUnitName);
						$('#formContainer #ownershipUnitCode').val(data.ownershipUnitCode);
						$('#formContainer #maintainBody').val(data.maintainBody);
						$('#formContainer #maintainUnitName').val(data.maintainUnitName);
						$('#formContainer #maintainUnitCode').val(data.maintainUnitCode);
						$('#formContainer #manufacturers').val(data.manufacturers);
						$('#formContainer #deviceType').val(data.deviceType);
						$('#formContainer #useUnit').val(data.useUnit);
						$('#formContainer #productionDate').val(data.productionDate);
						$('#formContainer #useDate').val(data.useDate);
						$('#formContainer #middleRepairDate').val(data.middleRepairDate);
						$('#formContainer #largeRepairDate').val(data.largeRepairDate);
						$('#formContainer #deviceOperationStatus').val(data.deviceOperationStatus);
						$('#formContainer #stopDate').val(data.stopDate);
						$('#formContainer #scrapDate').val(data.scrapDate);
						$('#formContainer #fixedAssetsCode').val(data.fixedAssetsCode);
						$('#formContainer #remark').val(data.remark);
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
		                    itemColspan : 1,
		                    item : '<input type="text" name="workshop" style="width:98%" id="workshop" readOnly/>'
		                },{
		                    label : '班组：',
		                    itemColspan : 1,
							item : '<input type="text" name="workArea" id="workArea" style="width:99.5%" readOnly/>'
		                },{
		                    label : '组合分类：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="combinationClass" id="combinationClass" style="width:99%" readOnly/>'
		                },{
		                    label : '设备分类：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="deviceClass" id="deviceClass" style="width:98.5%" readOnly/>'
		                },{
		                    label : '设备（设施）编码：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="deviceCode" id="deviceCode" style="width:98.5%" readOnly/>'
		                },{
		                    label : '设备名称：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="deviceName" id="deviceName" style="width:99%" readOnly/>'
		                },{
							label : '安装位置-所属线路：',
							itemColspan : 1,
							item : '<input type="text" name="site_line" id="site_line" style="width:99%" readOnly/>'
						},{
							label : '安装位置-专业部门：',
							itemColspan : 1,
							item : '<input type="text" name="site_department" id="site_department" style="width:99%" readOnly/>'
						},{
		                    label : '资产归属：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="assetOwnership" id="assetOwnership" style="width:99%" readOnly/>'
		                },{
		                    label : '权属单位名称：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="ownershipUnitName" id="ownershipUnitName" style="width:98.5%" readOnly/>'
		                },{
		                    label : '权属单位编号：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="ownershipUnitCode" id="ownershipUnitCode" style="width:99%" readOnly/>'
		                },{
		                    label : '维护主体：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="maintainBody" id="maintainBody" style="width:98.5%" readOnly/>'
		                },{
		                    label : '维护单位名称：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="maintainUnitName" id="maintainUnitName" style="width:99%" readOnly/>'
		                },{
		                    label : '维护单位编码：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="maintainUnitCode" id="maintainUnitCode" style="width:98.5%" readOnly/>'
		                },{
		                    label : '设备厂家：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="manufacturers" id="manufacturers" style="width:99%" readOnly/>'
		                },{
		                    label : '设备型号：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="deviceType" id="deviceType" style="width:98.5%" readOnly/>'
		                },{
		                    label : '使用单位：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="useUnit" id="useUnit" style="width:99%" readOnly />'
		                },{
		                    label : '出厂日期：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="productionDate" id="productionDate" readOnly style="width:98.5%" />'
		                },{
		                    label : '使用日期：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="useDate" id="useDate" style="width:99%" readOnly/>'
		                },{
		                    label : '设备运行状态：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="deviceOperationStatus" id="deviceOperationStatus" style="width:98.5%" readOnly/>'
		                },{
		                    label : '停用日期：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="stopDate" id="stopDate" readOnly style="width:99%" />'
		                },{
		                    label : '报废日期：',
		                    itemColspan : 1,
		                    item : '<input type="text" name="scrapDate" id="scrapDate" readOnly style="width:98.5%" />'
		                },{
		                    label : '固资编号：',
		                    itemColspan : 2,
		                    item : '<input type="text" name="fixedAssetsCode" id="fixedAssetsCode" readOnly style="width:99%" />'
		                },{
		                    label : '备注：',
		                    itemColspan : 2,
		                    item : '<textarea name="remark" id="remark" readOnly style="width:98.5%;height:50px"/></textarea>'
		                }
            ];
			var form = new FormContainer({
				id : 'handInfoShow',
				colNum : colNum,
				formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'handInfoDialog'},
            previewUrl:{value:'/pageoffice/openPage?filePath='},
            downloadUrl:{value:'/kmms/commonAction/download?path='},
			title:{value:'手持设备-06详细信息'},
            width:{value:630},
            height:{value:500},
            contextPath : {},
            shiftId : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
			collectionName:{},
			userId:{},
		}
	});
	return IrontowerInfo;
});