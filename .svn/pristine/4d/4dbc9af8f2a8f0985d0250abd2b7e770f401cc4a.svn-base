seajs.use("/kmms/integratedManage/attendanceManage/attendanceManage.css");
define("kmms/integratedManage/attendanceManage/resTransmission",
		[
			"bui/common",
			"bui/tree",
			"bui/data",
			"bui/calendar",
			'common/container/FieldsetContainer',
			"common/form/FormContainer",
			"common/container/LeftRightContainer",
		],function(r){
	var BUI = r("bui/common"),
	Tree = r("bui/tree"),
	Data = r('bui/data'),
    Grid = r('bui/grid'),
    Calendar = r('bui/calendar'),
    FieldSet = r('common/container/FieldsetContainer'),
    FormContainer = r('common/form/FormContainer'),
	LRC = r("common/container/LeftRightContainer");
	var ResTransmission = LRC.extend({
		initializer:function(){
			var _self = this;
			_self.set("date",BUI.Date.format(new Date(),"yyyy-mm-dd"));
			var left = _self.get("leftChildren");
			var right = _self.get("rightChildren");
			var calendar = _self._initCalendar();
			left.push(calendar);
			var rightForm = new FieldSet({id : 'userFormFieldSet',items:[_self._initUserFormArea()]});
			right.push(rightForm);
		},
		renderUI:function(){
            var _self=this;
        },
        bindUI:function(){
        	var _self = this;
        	
        	_self._initCalendarStatus();
        	
        	var c = _self.getChild("calendarPageId",true);
        	
        	// 切换月份时，根据后台数据渲染当月还未填报的天为红色
			c.on('monthchange',function(e){
				_self._initCalendarStatus();
			})
			// 点选天的触发事件
			c.on('datechange',function(e){
				_self.set("date",BUI.Date.format(e.date,"yyyy-mm-dd"));
				var right = _self.get("rightChildren");
				var rightForm = new FieldSet({id : 'userFormFieldSet',items:[_self._initUserFormArea()]});
				right.push(rightForm);
				console.log(BUI.Date.format(e.date,"yyyy-mm-dd"))
			})
        },
		_initCalendar:function(){
        	var _self = this;
        	var c = new Calendar.Calendar({
        		id:'calendarPageId',
				width: '100%',
				height: '100%',
			});
        	c.get('children').pop();
        	return c;
        },
        
        /**
		* 根据传入的数据初始化日期面板上哪些日期标红
		* 标红的日期代表没有填报
		*/
		_initCalendarStatus:function(){
			var _self = this;
			var data = [
						{
							day: '2019-03-10',
						},{
							day: '2019-03-13',
						},{
							day: '2019-03-17',
						},{
							day: '2019-03-21',
						},{
							day: '2019-03-22',
						},{
							day: '2019-03-30',
						}
						];
			//x-datepicker-active样式代表当月的所有天
			$('.x-datepicker-active').each(function(idx,e){
				var dayTd = e,title = dayTd.title;
				//填报过的则不标红
				if(!_self._hadCommit(title,data)){
					$(dayTd.children[0]).css('color','red');
				}

			})
		},
		/**
		* 判断某天是否完成检修记录填报
		* @param day 某天
		* @param data 当月完成填报的数据数组，其中每个元素中包含day属性，标识填报日期，格式yyyy-mm-dd
		*/
		_hadCommit:function(day,data){
			for(var i = 0;i < data.length;i++){
				var targetDay = data[i].day;
				if(targetDay == day)
					return true;
			}
			return false;
		},
		
//		_initDialog:function(){
//			var _self = this;
//			var d = new BUI.Overlay.Dialog({});
//			d.addChild(_self._initUserFormArea());
//			return d;
//		},
		
		
		
		_initUserFormArea:function(){
			var _self = this;
			var form = null;
            $.ajax({
				url:'/kmms/attendanceManageAction/getUsersByOrgId',
          		data:{orgId : _self.get('orgId'),collectionName : _self.get('userInfoManage')},
          		type:'post',
          		dataType:"json",
          		async:false,
          		success:function(result){
					var data = result.data;
					console.log(result.data);
					form = _self._initUserFormContainer(data);
          		}
			});
            return form;
		},
		
		_initUserFormContainer:function(data){
			var _self = this;
			console.log(data[0]);
//			var contentForm = '<table class="tableLayout" style="width:100%;table-layout:fixed" ><tbody>'+
//			'<tr class="thTdPadding">'+
//				'<th style="width:20%" colspan="2">'+BUI.Date.format(e.date,"yyyy-mm-dd")+'</th>'+
//			'</tr>'+
//			'</tbody>'+
//		    '</table>';
			var contentForm = '<table class="tableLayout" style="width:100%;table-layout:fixed" >'+
			'<tbody>'+	
			'<tr class="thTdPadding">'+
				'<th colspan="3">'+_self.get("date")+"的考勤情况"+'</th>'+
			'</tr>'+
			'<tr class="thTdPadding">'+
		       '<th >姓名</th>'+
		       '<th >考勤情况</th>'+
			   '<th >打卡</th>'+
		     '</tr>';
		     
			for(var i=0;i<data.length;i++){
				contentForm +='<tr class="thTdPadding">'+
			       '<td style="width:30%">'+
			   		'<input type="text" id="'+data[i].docId+'" name="'+data[i].docId+'" style="width:99%;" value="'+data[i].staffName+'" readonly/>'+
			   '</td>'+
		       '<td style="width:30%">'+
			   		'<input type="text" id="" name=""  style="width:99%;" value="'+""+'" readonly/>'+
			   '</td>'+
			   '<td style="width:30%">'+
		   		'<input type="text" id="countYJ" name="countYJ"  style="width:99%;" readonly/>'+
		   		'</td>'+
		   		'</tr>';
			}
			contentForm += '</tbody></table>';
			var form = new FormContainer({
                id : 'attendanceManageForm',
                content:contentForm,
//                content:'<table class="tableLayout" style="width:100%;table-layout:fixed" ><tbody>'+	
//                '<tr class="thTdPadding">'+
//			       '<th >列调便携电台</th>'+
//			       '<th style="width:20%">型号：'+
//			       '</th>'+
//			       '<td style="width:30%">'+
//				   '<input type="text" id="typeLT" name="typeLT"  style="width:98%;" />'+
//				   '</td>'+
//				   '<th style="width:20%">数量：'+
//			       '</th>'+
//			       '<td style="width:30%">'+
//				   '<input type="text" id="countLT" name="countLT"  style="width:98%;" />'+
//				   '</td>'+
//			     '</tr>'+
//			       '<tr class="thTdPadding">'+
//			       '<th >预警便携电台</th>'+
//			       '<th style="width:20%">型号：'+
//			       '</th>'+
//			       '<td style="width:30%">'+
//				   '<input type="text" id="typeYJ" name="typeYJ"  style="width:98%;" />'+
//				   '</td>'+
//				   '<th style="width:20%">数量：'+
//			       '</th>'+
//			       '<td style="width:30%">'+
//				   '<input type="text" id="countYJ" name="countYJ"  style="width:98%;" />'+
//				   '</td>'+
//			       '<tr class="thTdPadding">'+
//			       '<th >固定电台</th>'+
//			       '<th style="width:20%">型号：'+
//			       '</th>'+
//			       '<td colspan="3">'+
//				   '<input type="text" id="typeGD" name="typeGD"  style="width:98%;" />'+
//				   '</td>'+
//				   '</tr>'+
//				   '<tr class="thTdPadding"><th style="width:20%" colspan="2">守机联控情况：'+
//			       '</th>'+
//			       '<td colspan="3">'+
//				   '<input type="text" id="condition" name="condition"  style="width:98%;" />'+
//				   '</td>'+
//				   '</tr>'+
//			       '</tbody>'+
//			       '</table>',
                elStyle:{overflowY:'scroll',height:'500px'}
            });
            _self.set('formContainer',form);
            return form;
		},
		
        
//		初始化右侧的拓扑图
//		_initTopo:function () {
//			var _self = this;
//            return new Topo({id: 'topo',serverDateYear:_self.get('serverDateYear')});
//        },
//		bindUI:function(){
//			var _self = this;
//			var treeList = _self.getChild("leftTree",true);
//			var topo = _self.getChild('topo', true);
//			treeList.on('itemclick',function(e){
//				if(e.item.level==1){//点击的节点为传输系统则展示拓扑图
//					topo.refreshDeviceTopo(e);
//				}else{
//					topo.getServerData();//点击根节点会重新从拓扑展示数据库中取数据
//					topo.refreshTransTopo(e);
//				}
//			});
//			$(window).on('resize',function(){
//				_self.get('treeList').set('height',window.innerHeight-20);
//			});
//		}
	},
	{
		ATTRS:{
			perId : {},
			userId : {},//登录用户ID
			userName : {},//登录用户名称
			orgId : {},//登录用户组织机构ID
			orgName : {},//登录用户组织机构名称
			parentId : {},//上级机构ID
			rootOrgId:{value:'8affa073533aa3d601533bbef63e0010'},
			rootOrgText:{value:'昆明通信段'},
			userInfoManage : {value:'userInfoManage'},
			attendanceManage : {value:'attendanceManage'},
			leftSize:{value : 10},
//			height:{value:'800px'},
		}
	});
	return ResTransmission;
});