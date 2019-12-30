/**
 * 新增
 * @author yangli
 * @date 19-1-14
 */
define('kmms/dayToJobManagement/constructCooperate/ConstructCooperateAdd',
		['bui/common','common/form/FormContainer','bui/select','bui/data',
		 'common/data/PostLoad','bui/calendar','bui/form'],function(r){
    var BUI = r('bui/common'),
    	Calendar = r('bui/calendar'),
    	Select = r('bui/select'),
    	Data = r('bui/data'),
    	PostLoad = r('common/data/PostLoad'),
        FormContainer= r('common/form/FormContainer');
    var ConstructCooperateAdd = BUI.Overlay.Dialog.extend({
        initializer : function(){
            var _self = this;
        	var departId=$("#depart").val();
            _self.addChild(_self._initFormContainer());
        },
        renderUI : function(){
        	var _self=this;
        	_self._initDate();
        	_self._getWorkShop();
        	_self._getLines();
        	

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
            	_self._getDepart(orgName);
            	
            	
            });
            /**
             * 配合人员根据所选部门的选择而变化
             */
            $("#depart").on('change',function(){
            	$("#cooperManId").empty();
            	var departName=$("#depart").val();
            	_self._initStore(departName);
            	
            	var select = _self.get("cooperManSelect");
                /**
                 * 配合人员下拉选值获取
                 */
                select.on('change', function(ev){
                	console.log(212);
    				_self._selectChangeEvent(ev);
    		    });
            });
            
           

        },
        _selectChangeEvent:function(ev){
        	console.log(ev);
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
			console.log(selectMan);
			_self.set("selectMan",selectMan);
		},
        
        /**
         * 初始化时间查询
         * @private
         */
        _initDate: function () {
            var _self = this;
            var date = new Calendar.DatePicker({//加载日历控件
                trigger: '#date',
                showTime: false,
                autoRender: true,
                textField:'#date'
            });
            _self.set('date', date);
            
            var postLoad = new PostLoad({
                url : '/kmms/networkManageInfoAction/getSystemDateToYearMonthDay',
                el : _self.get('el'),
                loadMsg : '加载中...'
            });
            postLoad.load({},function (date) {
            	if(date){
            		$('#formContainer #date').val(date);
            	}
            });
        },
        /**
         * 获取线别
         */
        _getLines:function(){
        	var _self=this;
       	 $.ajax({
                url:'/kmms/constructCooperateAction/getLines',
//                data:{workShopName : orgName},
                type:'post',
                dataType:"json",
                success:function(res){
               	 for(var i=0;i<res.length;i++){
               		 $("#line").append("<option  value="+res[i]+">"+res[i]+"</option>");
               	 }
                }
            })
        },
        /**
         * 获取部门下拉选数据
         */
        _getDepart:function(orgName){
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
                	 _self._initStore(res[0].orgName);
                	 var select = _self.get("cooperManSelect");
                	 select.on('change', function(ev){
                     	console.log(212);
         				_self._selectChangeEvent(ev);
         		    });
                 }
             })
        },
        /**
         * 获取车间下拉选数据
         */
        _getWorkShop:function(){
        	 var _self = this;
             $.ajax({
                 url:'/kmms/constructCooperateAction/getWorkShop',
//                 data:{id : shiftId,collectionName:_self.get('collectionName')},
                 type:'post',
                 dataType:"json",
                 success:function(res){
                	 for(var i=0;i<res.length;i++){
                		 $("#workShop").append("<option  value="+res[i].orgName+">"+res[i].orgName+"</option>");
                	 }
                	 var orgName=$("#workShop").val();
                 	_self._getDepart(orgName);
                 }
             
             })
        },
        /**
         * 初始化配合人员下拉选
         */
        _getCooperMan:function(){
			var _self = this, cooperManStore = _self.get('cooperManStore');
			$("#cooperManId").empty();
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
		_initStore:function(departName){
			var _self = this;
//			var orgName=$("#depart").val();
			var store = new Data.Store({
			    url : '/kmms/constructCooperateAction/getcooperMan', 
			    autoLoad : true,    
			    params:{orgName:departName},
			    proxy : {
			      method : 'post',
			      dataType : 'json'
			    }
			});
			_self.set('cooperManStore',store);
			_self._getCooperMan();
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
                    item : '<select  name="line" id="line" style="width: 225px;"></select>'
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
//              item : '<select  name="cooperMan" id="cooperMan" style="width: 225px;" ><option value="" >请选择</option><option value="1" >1</option><option value="2" >2</option><option value="3" >3</option></select>'
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
                id : 'constructCooperateAddForm',
                colNum : colNum,
                formChildrens : childs,
//                elStyle:{overflowY:'scroll',height:'400px'}
            });
            _self.set('formContainer',form);
            return form;
        }
    },{
        ATTRS : {
            elAttrs : {value: {id:"constructCooperateAdd"}},
            elCls:{vale:'constructCooperateAdd_Dialog'},
            title:{value:'新增'},
            width:{value:660},
            height:{value:400},
            contextPath : {},
            closeAction : {value:'destroy'},//关闭时销毁加载到主页面的HTML对象
            mask : {value:true},
            collectionName:{},
            userId:{},//登录用户ID
            userName:{},//登录用户名称
            orgId:{},//登录用户组织机构ID
            orgName:{},//登录用户组织机构名称
            success:{

                value : function(){
                    var _self = this;
                    var formAdd = _self.getChild('constructCooperateAddForm');
                    //验证不通过
	        		if(!formAdd.isValid()){
	        			return;
	        		}
                    var formData = new FormData(formAdd.get('el')[0]);
                    formData.append('collectionName',_self.get('collectionName'));
                    if(_self.get('selectMan')!=undefined)
                    formData.append('cooperMan',_self.get('selectMan'));
                    var xhr = new XMLHttpRequest();
                    xhr.open("POST", "/kmms/constructCooperateAction/addData");
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
        }
    });
    return ConstructCooperateAdd;
});