/**
 * 修改
 * @author yangli
 * @date 19-1-14
 */
define('kmms/dayToJobManagement/constructCooperate/ConstructCooperateEdit',['bui/common','common/form/FormContainer',
    'bui/form','bui/calendar','bui/data','bui/select','common/org/OrganizationPicker',],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	Select = r('bui/select'),
    	OrganizationPicker = r('common/org/OrganizationPicker'),
    	Data = r('bui/data'),
        FormContainer= r('common/form/FormContainer');
    var ConstructCooperateEdit = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
            _self.addChild(_self._initFormContainer());
        },
        renderUI:function(){
			var _self=this;
			_self._initDate();
			_self._getShowData();
			
        },
        bindUI : function(){
            var _self = this;
            
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
            
          //部门下拉选选项根据车间而变化
            $("#workShop").on('change',function(){
            	$("#depart").empty();
            	$("#cooperManId").empty();
            	var orgName=$("#workShop").val();
            	_self._getDepart(orgName,0);
            	
            });
            
            /**
             * 配合人员根据所选部门的选择而变化
             */
            $("#depart").on('change',function(){
            	var orgName=$("#depart").val();
            	$("#cooperManId").empty();
            	_self._initStore(orgName);
            	
            	var select = _self.get("cooperManSelect");
            	
                /**
                 * 配合人员下拉选值获取
                 */
                select.on('change', function(ev){
    				_self._selectChangeEvent(ev);
    		    });
            });
        },
        _selectChangeEvent:function(ev){
			var _self = this;
			if(ev=="") {
				_self.set("selectMan","");
				return
			}
			if(ev.text=="") {
				_self.set("selectMan","");
				return;
			}
			var selectMan=ev.text;
			_self.set("selectMan",selectMan);
		},
        /**
         * 获取线别
         */
        _getLines:function(line){
        	var _self=this;
       	 $.ajax({
                url:'/kmms/constructCooperateAction/getLines',
                type:'post',
                dataType:"json",
                success:function(res){
               	 for(var i=0;i<res.length;i++){
               		 $("#line").append("<option  value="+res[i]+">"+res[i]+"</option>");
               	 }
               	$("#line option[value='"+line+"']").attr("selected","selected");
                }
            })
        },
        /**
         * 获取车间下拉选数据
         */
        _getWorkShop:function(workShop){
        	 var _self = this;
             $.ajax({
                 url:'/kmms/constructCooperateAction/getWorkShop',
                 type:'post',
                 dataType:"json",
                 success:function(res){
                	 for(var i=0;i<res.length;i++){
                		 $("#workShop").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
                		$("#workShop option[value='"+workShop+"']").attr("selected","selected");
                 }
             })
        },
        /**
         * 获取部门下拉选数据
         */
        _getDepart:function(orgName,depart){
        	var _self=this;
        	 $.ajax({
                 url:'/kmms/constructCooperateAction/getDepart',
                 data:{workShopName : orgName},
                 type:'post',
                 dataType:"json",
                 success:function(res){
                    
                	 for(var i=0;i<res.length;i++){
                		 $("#depart").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
                	 if(depart==0){
                     	_self._initStore(res[0].orgName);
                     	var select = _self.get("cooperManSelect");
                     	/**
                         * 配合人员下拉选值获取
                         */
                        select.on('change', function(ev){
            				_self._selectChangeEvent(ev);
            		    });
                 	 }
                	 $("#depart option[value='"+depart+"']").attr("selected","selected");
                	 
                	 
//                	 _self._initStore(depart);
                 	
                     
                 }
             })
        },
        /**
         * 初始化配合人员下拉选
         */
        _getCooperMan:function(){
			var _self = this, cooperManStore = _self.get('cooperManStore'),cooperMan=_self.get("cooperMan");
			var render = $('#cooperManId');
			var valueField = $('#cooperMan');
			var select = new Select.Select({
		          render:render,
		          valueField:valueField,
		          multipleSelect:true,
		          store:cooperManStore
		    });
		    select.render();
		    _self.set("cooperManSelect",select);
		    _self._initCssOfSelect();
		},
		//初始化select下拉选的样式
		_initCssOfSelect:function(){
			$(".bui-select").css("width","99%");
		    $(".bui-select span").css("border","white");
		    $(".bui-select-input").css("width","99%");
		    $(".bui-select span").css("display","none");
		    $(".bui-select-list").css("overflow-y","auto");
//		    $(".bui-select-input").attr("placeholder","请选择配合人员");
		},
		/**
		 * 初始化store
		 */
		_initStore:function(depart){
			var _self = this;
//			var orgName=$("#depart").val();
			var store = new Data.Store({
			    url : '/kmms/constructCooperateAction/getcooperMan', 
			    autoLoad : true,    
			    params:{orgName:depart},
			    proxy : {
			      method : 'post',
			      dataType : 'json'
			    }
			});
			_self.set('cooperManStore',store);
			_self._getCooperMan();
		},
        //时间戳转时间
		_timestampToTime : function(timestamp) {
			if(timestamp){
				var date = new Date(timestamp);
				Y = date.getFullYear() + '-';
				M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
				D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
				h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
		        m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
		        s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
		        return Y+M+D+h+m+s;
			}else{
				return "";
			}
	    },
        
        /**
         * 获取显示数据
         */
        _getShowData : function(){
        	 var _self = this,shiftId = _self.get('shiftId');
//           var delData={};
           $.ajax({
               url:'/kmms/constructCooperateAction/findById',
               data:{id : shiftId,collectionName:_self.get('collectionName')},
               type:'post',
               dataType:"json",
                success:function(data){
                    if(data){
                    	_self._getLines(data.line);
                    	_self._getWorkShop(data.workShop);
                    	_self._getDepart(data.workShop,data.depart);
                    	_self._initStore(data.depart);
                    	
                    	$("#formContainer #date").val(data.date);
            		    $('#formContainer #local').val(data.local);
            		    $('#formContainer #constructUnit').val(data.constructUnit);
            		    $('#formContainer #constructPro').val(data.constructPro);
            		    $('#formContainer #constructContent').val(data.constructContent);
            		    $('#formContainer #cooperMan').val(data.cooperMan);
            		    $('#formContainer #remark').val(data.remark);
            		    $('#formContainer #cableSituation').val(data.cableSituation);
                    }
                }
            })
        },
        
        _initDate: function () {
            var _self = this;
            var date = new Calendar.DatePicker({//加载日历控件
                trigger: '#formContainer #date',
                showTime: false,
                autoRender: true,
                textField:'#formContainer #date'
            });
            _self.set('date', date);
        },
        
        /**
         * 初始化FormContainer
         */
        _initFormContainer : function(){
            var _self = this;
            var colNum = 2;
            var childs = [
                 {
                    label : '日期：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<input type="text"  name="date" id="date" class="calendar"  style="width:99.5%" readonly />'
                },{
                    label : '线别：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select name="line" id="line" ></select>'
                },{
                    label : '车间：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select name="workShop" id="workShop" style="width: 225px;" ></select>'
                },{
                    label : '部门：',
//                    redStarFlag : true,
                    itemColspan : 1,
                    item : '<select  name="depart" id="depart" style="width: 225px;"></select>'
                },{
                    label : '位置：',
//                  redStarFlag : true,
                  itemColspan : 1,
                  item : '<input type="text" name="local" id="local" />'
              },{
                  label : '施工单位：',
//                redStarFlag : true,
                itemColspan : 1,
                item : '<input type="text" name="constructUnit" id="constructUnit" />'
            },{
                label : '施工项目：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<input type="text" name="constructPro" id="constructPro" />'
            },{
                label : '配合人员：',
//              redStarFlag : true,
              itemColspan : 1,
              item : '<div id="cooperManId" >'+
					'<input   name="cooperMan" id="cooperMan" style="width:99%;height:27px;" readonly="readonly" type="hidden"></input>'
              		+'</div>'
            },{
                label : '施工内容：',
//              redStarFlag : true,
              itemColspan : 2,
              item : '<textarea type="text" name="constructContent" id="constructContent" />'
            },{
                label : '光电缆情况：',
//              redStarFlag : true,
              itemColspan : 2,
              item : '<textarea type="text" name="cableSituation" id="cableSituation" />'
            },{
                label : '备注：',
//              redStarFlag : true,
              itemColspan : 2,
              item : '<textarea type="text" name="remark" id="remark" />'
          }
            ];
            var form = new FormContainer({
                id : 'constructCooperateEditForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            id : {value : 'constructCooperateEditDialog'},
            elAttrs : {value: {id:"constructCooperateEdit"}},
            title:{value:'修改'},
            width:{value:640},
            height:{value:400},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            shiftId:{},
//            userId:{},
            success:{
                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('constructCooperateEditForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    formData.append('id',_self.get('shiftId'));
                    if(_self.get('selectMan')!=undefined){
                    	formData.append('cooperMan',_self.get('selectMan'));
                    }
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/kmms/constructCooperateAction/updateDoc");
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
//            rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
//            rootOrgText:{value:'昆明通信段'},
        }
    });
    return ConstructCooperateEdit;
});