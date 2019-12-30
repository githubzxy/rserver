var gztxd_id = "43c3803e730e495ab51ed1db6111dbdf";
var gztxd_name = "广州通信段";
var gtjt_id = "4e512b61-f815-411e-9dca-a096a7ebeb5e";
var gtjt_name = "广铁集团";
var wgzx_id = "fd4b1b761f0c42018673ccaccc4a93ee";
var wgzx_name = "网管中心";
var sbwhs_id = "a431a87d76c74c7d99f517b1284abb75";
var sbwhs_name = "设备维护室";
var gzbhs_id = "065827a3939749bb8a5fe06c35d45bbd";
var gzbhs_name = "广州报话所";
/**
* 判断是否为空
* @param obj
*/
function isEmptyAndNull(obj) {
	if(obj == '' || obj == null || obj == undefined || obj=='null') {
		return true;
	}
	return false;
};

/**
 * null替换为""
 * @param obj
 */
function null2EmptyStr(obj) {
	if(isEmptyAndNull) {
		return "";
	}
	return obj;
}

/**
 * 
 */
//失败提示信息
var errorMsgHtml = '<div id="errorMsg" class="tips tips-small tips-warning" style="display:none">'+
        '<span class="x-icon x-icon-small" style="background:#da4f49;color:#FFF;"><i class="icon icon-white icon-bell"></i></span>'+
        '<div id="errorResultMsg" class="tips-content">保存数据失败，请联系管理员</div>'+
        '</div>';
//成功提示信息
var sucMsgHtml = '<div id="sucMsg" class="tips tips-small  tips-success" style="display:none">'+
    '<span class="x-icon x-icon-small x-icon-success"><i class="icon icon-white icon-ok"></i></span>'+
    '<div id="sucResultMsg" class="tips-content"  style="width:100%;"></div>'+
    '</div>';

/**公用成功信息提示
 * @param msg
 */
function commonSuccess(msg){
	if(msg == null || msg.trim() == ''){
		msg = '操作成功！';
	}
	$('#sucResultMsg').empty();
	$('#sucResultMsg').append(msg);
	$('#sucMsg').show();
	$('#sucMsg').fadeOut(5000);
}

/**
 * 公用失败信息提示
 */
function commonFailure(msg){
	if(msg == null || msg.trim() == ''){
		msg = '保存数据失败，请联系管理员！';
	}
	$('#errorResultMsg').empty();
	$('#errorResultMsg').append(msg);
	$('#errorMsg').show();
	$('#errorMsg').fadeOut(5000);
}
///**
// * 
// * 获取服务器时间
// * @returns
// */
//function getSystemTime(){
//	
//	//获取服务器时间  
//	var xmlHttp = false;  
//    try {  
//        xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");  
//    } catch (e) {  
//        try {  
//            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
//        } catch (e2) {  
//            xmlHttp = false;  
//        }  
//    }  			  
//    if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {  
//        xmlHttp = new XMLHttpRequest();  
//    }  			 
//    xmlHttp.open("GET", '', false);  
//    xmlHttp.setRequestHeader("Range", "bytes=-1");  
//    xmlHttp.send(null);  			  
//    return  new Date(xmlHttp.getResponseHeader("Date"));  
//}
/**
 * 返回格式化日期
 * @param date 日期字符串
 * @param format 格式化格式“yyyy-MM-dd hh:mm:ss"
 * @returns
 */
function returnFormatDate(date,format){
	
	var x = new Date(date);
	
	var z = {      
			y: x.getFullYear(),      
			M: x.getMonth() + 1,      
			d: x.getDate(),      
			h: x.getHours(),     
			m: x.getMinutes(),      
			s: x.getSeconds()   
			};  
	return format.replace(/(y+|M+|d+|h+|m+|s+)/g, function(v) {      
		return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1))).slice(-(v.length > 2 ? v.length : 2));   
		});
	
}
/**
 * 返回星期
 * @param week
 * @returns {String}
 */
function returnDay(week){
	
	if(week =="1"){
		return "星期一";
	}else if(week =="2"){
		return "星期二";
	}else if(week =="3"){
		return "星期三";
	}else if(week =="4"){
		return "星期四";
	}else if(week =="5"){
		return "星期五";
	}else if(week =="6"){
		return "星期六";
	}else if(week =="0"){
		return "星期日";
	}
}