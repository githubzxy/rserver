/**
 * 详情模块
 */
define('kmms/technicalManagement/communicationResumeManage/data/routerInfo',['bui/common',
	'common/form/FormContainer',
	'common/uploader/ViewUploader',
	'common/data/PostLoad'],
	function(r){
	var BUI = r('bui/common'),
		Uploader = r('common/uploader/ViewUploader'),
		FormContainer= r('common/form/FormContainer');
	var routerInfo = BUI.Overlay.Dialog.extend({
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
				url:'/kmms/dataAction/findById',
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
						$("#formContainer #site_station_line").val(data.site_station_line);
						$("#formContainer #site_station_name").val(data.site_station_name);
						$('#formContainer #site_station_place').val(data.site_station_place);
						$("#formContainer #site_range_line").val(data.site_range_line);
						$("#formContainer #site_range_post").val(data.site_range_post);
						$('#formContainer #site_range_place').val(data.site_range_place);
						$("#formContainer #site_other_line").val(data.site_other_line);
						$('#formContainer #site_other_place').val(data.site_other_place);
						$('#formContainer #site_machineRoomCode').val(data.site_machineRoomCode);
						$('#formContainer #slotType').val(data.slotType);
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
						$('#formContainer #deviceOperationStatus').val(data.deviceOperationStatus);
						$('#formContainer #stopDate').val(data.stopDate);
						$('#formContainer #scrapDate').val(data.scrapDate);
						$('#formContainer #applicationLevel').val(data.applicationLevel);
						$('#formContainer #slotTotalNumber').val(data.slotTotalNumber);
						$('#formContainer #slotUseNumber').val(data.slotUseNumber);
						$('#formContainer #_622MportConfigNumber').val(data._622MportConfigNumber);
						$('#formContainer #_622MportUseNumber').val(data._622MportUseNumber);
						$('#formContainer #_155MportConfigNumber').val(data._155MportConfigNumber);
						$('#formContainer #_155MportUseNumber').val(data._155MportUseNumber);
						$('#formContainer #_2MportConfigNumber').val(data._2MportConfigNumber);
						$('#formContainer #_2MportUseNumber').val(data._2MportUseNumber);
						$('#formContainer #GEportConfigNumber').val(data.GEportConfigNumber);
						$('#formContainer #GEportUseNumber').val(data.GEportUseNumber);
						$('#formContainer #FEportConfigNumber').val(data.FEportConfigNumber);
						$('#formContainer #FEportUseNumber').val(data.FEportUseNumber);
						$('#formContainer #otherPortConfigNumber').val(data.otherPortConfigNumber);
						$('#formContainer #otherPortUseNumber').val(data.otherPortUseNumber);
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
					item : '<input type="text" name="combinationClass" id="combinationClass" style="width:99%" readOnly"/>'
				},{
					label : '设备分类：',
					itemColspan : 1,
					item : '<input type="text" name="deviceClass" id="deviceClass" style="width:98.5%" readOnly"/>'
				},{
					label : '设备（设施）编码：',
					itemColspan : 1,
					item : '<input type="text" name="deviceCode" id="deviceCode" style="width:98.5%" readOnly/>'
				},{
					label : '设备名称：',
					itemColspan : 1,
					item : '<input type="text" name="deviceName" id="deviceName" style="width:99%" readOnly/>'
				},{
					label : '安装位置(车站、区间、其它三选一)：',
					itemColspan : 2,
					item :
						'<table class="wxsbTable" style="width:100%;table-layout:fixed" ><tbody>'+
						'<tr class="thTdPadding">'+
						'<th rowspan="3">车站</th>'+
						'<th style="width:20%" >所属线路：'+
						'</th>'+
						'<td style="width:30%">'+
						'<input type="text" id="site_station_line" name="site_station_line" class="station" style="width:98%;" />'+
						'</td>'+
						'</tr>'+
						'<tr class="thTdPadding">'+
						'<th style="width:20%" >车站名称：'+
						'</th>'+
						'<td style="width:30%">'+
						'<input type="text" id="site_station_name" name="site_station_name" class="station" style="width:98%;" />'+
						'</td>'+
						'</tr>'+
						'<tr class="thTdPadding">'+
						'<th style="width:20%" ">安装地点：'+
						'</th>'+
						'<td style="width:30%">'+
						'<input type="text" id="site_station_place" name="site_station_place" class="station" style="width:98%;" />'+
						'</td>'+
						'</tr>'+
						'<tr class="thTdPadding">'+
						'<th rowspan="3">区间</th>'+
						'<th style="width:20%" >所属线路：'+
						'</th>'+
						'<td style="width:30%">'+
						'<input type="text" id="site_range_line" name="site_range_line" class="range" style="width:98%;" />'+
						'</td>'+
						'</tr>'+
						'<tr class="thTdPadding">'+
						'<th style="width:20%" >公里标（米）：'+
						'</th>'+
						'<td style="width:30%">'+
						'<input type="text" id="site_range_post" name="site_range_post" class="range" style="width:98%;" />'+
						'</td>'+
						'</tr>'+
						'<tr class="thTdPadding">'+
						'<th style="width:20%" ">安装地点：'+
						'</th>'+
						'<td style="width:30%">'+
						'<input type="text" id="site_range_place" name="site_range_place" class="range" style="width:98%;" />'+
						'</td>'+
						'</tr>'+
						'<tr class="thTdPadding">'+
						'<th rowspan="2">其他</th>'+
						'<th style="width:20%" >所属线路：'+
						'</th>'+
						'<td style="width:30%">'+
						'<input type="text" id="site_other_line" name="site_other_line" class="other" style="width:98%;" />'+
						'</td>'+
						'</tr>'+
						'<tr class="thTdPadding">'+
						'<th style="width:20%" >安装地点：'+
						'</th>'+
						'<td style="width:30%">'+
						'<input type="text" id="site_other_place" name="site_other_place" class="other" style="width:98%;" />'+
						'</td>'+
						'</tr>'+
						'<tr class="thTdPadding">'+
						'<th style="width:20%"  colspan="2">机房、接入点编码：'+
						'</th>'+
						'<td style="width:30%">'+
						'<input type="text" id="site_machineRoomCode" name="site_machineRoomCode"  style="width:98%;" />'+
						'</td>'+
						'</tr>'+
						'</tbody>'+
						'</table>'

				},{
					label : '资产归属：',
					
					itemColspan : 1,
					item : '<input type="text" name="assetOwnership" id="assetOwnership" style="width:99%" readOnly"/>'
				},{
					label : '权属单位名称：',
					
					itemColspan : 1,
					item : '<input type="text" name="ownershipUnitName" id="ownershipUnitName" style="width:98.5%" readOnly"/>'
				},{
					label : '权属单位编号：',
					
					itemColspan : 1,
					item : '<input type="text" name="ownershipUnitCode" id="ownershipUnitCode" style="width:99%" readOnly"/>'
				},{
					label : '维护主体：',
					
					itemColspan : 1,
					item : '<input type="text" name="maintainBody" id="maintainBody" style="width:98.5%" readOnly"/>'
				},{
					label : '维护单位名称：',
					
					itemColspan : 1,
					item : '<input type="text" name="maintainUnitName" id="maintainUnitName" style="width:99%" readOnly"/>'
				},{
					label : '维护单位编码：',
					
					itemColspan : 1,
					item : '<input type="text" name="maintainUnitCode" id="maintainUnitCode" style="width:98.5%" readOnly"/>'
				},{
					label : '插槽类型：',
					
					itemColspan : 1,
					item : '<input type="text" name="slotType" id="slotType" style="width:99%" readOnly"/>'
				},{
					label : '设备厂家：',
					
					itemColspan : 1,
					item : '<input type="text" name="manufacturers" id="manufacturers" style="width:98.5%" readOnly"/>'
				},{
					label : '设备型号：',
					itemColspan : 1,
					item : '<input type="text" name="deviceType" id="deviceType" style="width:99%" readOnly"/>'
				},{
					label : '使用单位：',
					itemColspan : 1,
					item : '<input type="text" name="useUnit" id="useUnit" style="width:98.5%" readOnly/>'
				},{
					label : '出厂日期：',
					itemColspan : 1,
					item : '<input type="text" name="productionDate" id="productionDate" style="width:99%" readOnly/>'
				},{
					label : '使用日期：',
					itemColspan : 1,
					item : '<input type="text" name="useDate" id="useDate" style="width:98.5%" readOnly"/>'
				},{
					label : '设备运行状态：',
					itemColspan : 1,
					item : '<input type="text" name="deviceOperationStatus" id="deviceOperationStatus" style="width:99%" readOnly"/>'
				},{
					label : '停用日期：',
					itemColspan : 1,
					item : '<input type="text"  readonly name="stopDate" id="stopDate" style="width:98.5%"/>'
				},{
					label : '报废日期：',
					itemColspan : 1,
					item : '<input type="text" readonly name="scrapDate" id="scrapDate" style="width:99%"/>'
				},{
					label : '应用层次：',
					itemColspan : 1,
					item : '<input type="text" name="applicationLevel" id="applicationLevel" style="width:98.5%" readOnly/>'
				},{
					label : '槽位总数：',
					itemColspan : 1,
					item : '<input type="text" name="slotTotalNumber" id="slotTotalNumber" style="width:99%" readOnly/>'
				},{
					label : '槽位占用数：',
					itemColspan : 1,
					item : '<input type="text" name="slotUseNumber" id="slotUseNumber" style="width:98.5%" readOnly/>'
				},{
					label : '622M端口配置数：',
					itemColspan : 1,
					item : '<input type="text" name="_622MportConfigNumber" id="_622MportConfigNumber" style="width:99%" readOnly/>'
				},{
					label : '622M端口占用数：',
					itemColspan : 1,
					item : '<input type="text" name="_622MportUseNumber" id="_622MportUseNumber" style="width:98.5%" readOnly/>'
				},{
					label : '155M端口配置数：',
					itemColspan : 1,
					item : '<input type="text" name="_155MportConfigNumber" id="_155MportConfigNumber" style="width:99%" readOnly/>'
				},{
					label : '155M端口占用数：',
					itemColspan : 1,
					item : '<input type="text" name="_155MportUseNumber" id="_155MportUseNumber" style="width:98.5%" readOnly/>'
				},{
					label : '2M端口配置数：',
					itemColspan : 1,
					item : '<input type="text" name="_2MportConfigNumber" id="_2MportConfigNumber" style="width:99%" readOnly/>'
				},{
					label : '2M端口占用数：',
					itemColspan : 1,
					item : '<input type="text" name="_2MportUseNumber" id="_2MportUseNumber" style="width:98.5%" readOnly/>'
				},{
					label : 'GE端口配置数：',
					itemColspan : 1,
					item : '<input type="text" name="GEportConfigNumber" id="GEportConfigNumber" style="width:99%" readOnly/>'
				},{
					label : 'GE端口占用数：',
					itemColspan : 1,
					item : '<input type="text" name="GEportUseNumber" id="GEportUseNumber" style="width:98.5%" readOnly/>'
				},{
					label : 'FE端口配置数：',
					itemColspan : 1,
					item : '<input type="text" name="FEportConfigNumber" id="FEportConfigNumber" style="width:99%" readOnly/>'
				},{
					label : 'FE端口占用数：',
					itemColspan : 1,
					item : '<input type="text" name="FEportUseNumber" id="FEportUseNumber" style="width:98.5%" readOnly/>'
				},{
					label : '其它端口配置数：',
					itemColspan : 1,
					item : '<input type="text" name="otherPortConfigNumber" id="otherPortConfigNumber" style="width:99%" readOnly/>'
				},{
					label : '其它端口占用数：',
					itemColspan : 1,
					item : '<input type="text" name="otherPortUseNumber" id="otherPortUseNumber" style="width:98.5%" readOnly/>'
				},{
					label : '固资编号：',
					itemColspan : 2,
					item : '<input type="text" name="fixedAssetsCode" id="fixedAssetsCode" style="width:99%" readOnly/>'
				},{
					label : '备注：',
					itemColspan : 2,
					item : '<textarea name="remark" id="remark" style="width:98.5%;height:50px" readOnly/></textarea>'
				}
			];
			var form = new FormContainer({
				id : 'routerInfoShow',
				colNum : colNum,
				formChildrens : childs,
                elStyle:{overflowY:'scroll',height:'400px'}
			});
			_self.set('formContainer',form);
			return form;
		},
		
	},{
		ATTRS : {
			id : {value : 'routerInfoDialog'},
            previewUrl:{value:'/pageoffice/openPage?filePath='},
            downloadUrl:{value:'/kmms/commonAction/download?path='},
			title:{value:'路由器-01详细信息'},
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
	return routerInfo;
});