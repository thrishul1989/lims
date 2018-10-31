$(function()
{
	
	/*$("#defaultmenu").click(function(){
		$(".sys-nav li").removeClass('active');
		$(this).addClass('active');
	});
	
	$(".sys-child-nav li").click(function(){
		$(".sys-nav li").removeClass('active');
		$(this).parent().parent().addClass('active');
	});*/

	
    $('select[data-value]').each(function()
    {
        $(this).val($(this).attr('data-value'));
    });

    // 菜单
    $('.sys-nav .menu').click(function(e)
    {
        e.preventDefault();
        var iframe = $('#iframe0');
        iframe.attr('src', $(this).attr('href'));
    });
    //修改资料
    $('.editinfo a').click(function(e){
        e.preventDefault();
        var iframe = $('#iframe0');
        iframe.attr('src', $(this).attr('href'));
    });
    	    

    // 返回按钮
    $('.goback').click(function()
    {
        var url = $(this).attr('data-url');

        if (typeof (url) == "undefined")
        {
            window.history.go(-1);
        }
        else
        {
            window.location = url;
        }
    });

    // 必填项星号
    $('.control-label.control-required').each(function()
    {
        $(this).prepend('<span>*</span>')
    });

    // 表格操作列动态列宽
    $('th.flexible-btns').each(function()
    {
        var table = $(this).parents('table');
        var td = table.find('td.flexible-btns').first();

        if (td.length > 0)
        {
            var count = td.find('a').length;

            if (count > 5)
            {
                $(this).addClass('flexible-btns-max');
            }
            else if (count < 2)
            {
                $(this).addClass('flexible-btns-min');
            }
            else
            {
                $(this).addClass('flexible-btns-' + count);
            }
        }

    });
    
    var checkboxs = $('.i-checks');

    if (checkboxs.length > 0)
    {
        checkboxs.iCheck({
            checkboxClass : "icheckbox_square-green",
            radioClass : "iradio_square-green"
        });
    }

    // 多选
    var chosens = $('select.chosen');

    if (chosens.length > 0)
    {
        chosens.chosen({
            disable_search_threshold : 10,
            no_results_text : '未检索到相关的角色'
        });
    }

    // 每页记录数
    $('#pageSizeSelect').change(function()
    {
        var form = $('.search-form');
        var holder = $(this).parents('.pagination-wrap').find('.pagination');
        var pageNo = parseInt(holder.attr('data-page-no') || 1);
        var pageSize = $(this).val();

        if (form.find('#pageNo').length == 0)
        {
            form.append('<input type="hidden" name="pageNo" id="pageNo"/>');
        }

        if (form.find('#pageSize').length == 0)
        {
            form.append('<input type="hidden" name="pageSize" id="pageSize"/>');
        }

        form.find('#pageNo').val(pageNo);
        form.find('#pageSize').val(pageSize);
        form.submit();
    });

    //跳页
    $('body').on('blur', '#gotoPageNo', function()
    {
        var val = $(this).val();
        var number = parseInt(val);

        if (isNaN(number) || number < 1)
        {
            var historyValue = $(this).attr('data-history-value');
            $(this).val(historyValue);
        }
        else
        {
            $(this).val(number);
            $(this).attr('data-history-value', number);
        }
    }).on('click', '#goto_button', function()
    {
        var form = $(this).parents('body').find('.search-form');

        if (form.find('#pageNo').length == 0)
        {
            form.append('<input type="hidden" name="pageNo" id="pageNo"/>');
        }

        if (form.find('#pageSize').length == 0)
        {
            form.append('<input type="hidden" name="pageSize" id="pageSize"/>');
        }

        form.find('#pageNo').val($('#gotoPageNo').val());
        form.find('#pageSize').val(parseInt($('#pageSizeSelect').val() || 10));
        form.submit();
    }).on('click', '.clearClass', function(){
        $('.fileinput-upload-button').remove();
        $('.fileinput-upload-button').remove();
        $('.file-preview').remove();
        $('.fileinput-remove').remove();
    });

    // 分页
    $('.pagination').each(function()
    {
        var form = $(this).parents().find('.search-form');
        var pageNo = parseInt($(this).attr('data-page-no') || 1);
        var pageSize = parseInt($(this).attr('data-page-size') || 10);
        var count = parseInt($(this).attr('data-count') || 0);

        $(this).pagination(count, {
            items_per_page : pageSize,
            current_page : pageNo - 1,
            num_display_entries : 5,
            num_edge_entries : 2,
            prev_text : '上一页',
            next_text : '下一页',
            show_if_single_page : true,
            callback : function(pageIndex)
            {
                if (form.find('#pageNo').length == 0)
                {
                    form.append('<input type="hidden" name="pageNo" id="pageNo"/>');
                }
                if (form.find('#pageSize').length == 0)
                {
                    form.append('<input type="hidden" name="pageSize" id="pageSize"/>');
                }
                form.find('#pageNo').val(pageIndex + 1);
                form.find('#pageSize').val(pageSize);
                form.submit();
            }
        });
    });

    var layerObject = parent.parent.layer;

    // 操作确认
    $('.layer-confirm').each(function()
    {
        var object = $(this);

        if (object.is("a"))
        {
            object.click(function(e)
            {
                e.preventDefault();

                var message = object.attr('data-confirm-message');
                var buttons = [ '确定', '取消' ];

                layerObject.confirm(message, {
                    icon : 3,
                    title : '操作确认',
                    btn : buttons,
                    shade : 'transparent'
                }, function(index)
                {
                    window.location = object.attr("href");
                    layerObject.close(index);
                }, function(index)
                {
                    layerObject.close(index);
                });
            });
        }
    });
    
    if ($.validator) {
        $.validator.prototype.elements = function () {
            var validator = this,
              rulesCache = {};

            return $(this.currentForm)
            .find("input, select, textarea")
            .not(":submit, :reset, :image, [disabled]")
            .not(this.settings.ignore)
            .filter(function () {
                if (!this.name && validator.settings.debug && window.console) {
                    console.error("%o has no name assigned", this);
                }
                rulesCache[this.name] = true;
                return true;
            });
        }
    }

    
});
function openLink(url){
	if(url!=''){
		 var iframe = $('#iframe0');
		 iframe.attr('src', '//'+url);
	}
}
function sortTable(str,flag,n,td,tbody){
    var arr = [];
    for(var i = 0;i < td.length;i++){
      arr.push(td[i]);
    };
    arr.sort(function(a,b){
    	if(b.cells[n] ==undefined){
    		return false;
    	}
      return changeOrderCell(str,a.cells[n].innerHTML,b.cells[n].innerHTML) * flag;
    });
    for(var i = 0;i < arr.length;i++){
      tbody.appendChild(arr[i]);
    };
};
function changeOrderCell(str,a,b){
	switch(str){
	case 'num': 
	  return a-b;
	  break;
	case 'string': 
	  return a.localeCompare(b);
	  break;
	default:
	  return new Date(a.split('-').join('/')).getTime()-new Date(b.split('-').join('/')).getTime();
	    };
};



function clearCondition(formId){
	   $.each($("#"+formId).find('input,select').not(":hidden"),function(){
		   $(this).val("");  
	   });
}

function clearHiddenCondition(formId){ //此方法去除隐藏，不去隐藏用上面clearCondition
	   $.each($("#"+formId).find('input,select'),function(){
		   $(this).val("");  
	   });
	   if($("#"+formId).find('#business').length>0){
		   $("#"+formId).find('#business').magicSuggest().clear();
		   $("#"+formId).find('#business').magicSuggest().collapse()
	   }
	   
}

function showTip(content,title){
	parent.layer.alert(content,{title:title});
}



function Map() {
    /** 存放键的数组(遍历用到) */
    this.keys = new Array();
    /** 存放数据 */
    this.data = new Object();
     
    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if(this.data[key] == null){
            this.keys.push(key);
        }
        this.data[key] = value;
       
    };
     
    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };
     
    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function(key) {
        this.keys.remove(key);
        this.data[key] = null;
    };
     
    /**
     * 遍历Map,执行处理函数
     * 
     * @param {Function} 回调函数 function(key,value,index){..}
     */
    this.each = function(fn){
        if(typeof fn != 'function'){
            return;
        }
        var len = this.keys.length;
        for(var i=0;i<len;i++){
            var k = this.keys[i];
            fn(k,this.data[k],i);
        }
    };
     
    /**
     * 获取键值数组(类似Java的entrySet())
     * @return 键值对象{key,value}的数组
     */
    this.entrys = function() {
        var len = this.keys.length;
        var entrys = new Array(len);
        for (var i = 0; i < len; i++) {
            entrys[i] = {
                key : this.keys[i],
                value : this.data[i]
            };
            console.log(this.data[i]);
        }
        return entrys;
    };
     
    /**
     * 判断Map是否为空
     */
    this.isEmpty = function() {
        return this.keys.length == 0;
    };
     
    /**
     * 获取键值对数量
     */
    this.size = function(){
        return this.keys.length;
    };
     
    /**
     * 重写toString 
     */
    this.toString = function(){
        var s = "{";
        for(var i=0;i<this.keys.length;i++,s+=','){
            var k = this.keys[i];
            s += k+"="+this.data[k];
        }
        s+="}";
        return s;
    };
}


function isRepeat(arr) {
    var hash = {};
    for(var i in arr) {
        if(hash[arr[i]])
        {
            
        	return true;
        }
        // 不存在该元素，则赋值为true，可以赋任意值，相应的修改if判断条件即可
        hash[arr[i]] = true;
    }
    return false;
}

Date.prototype.pattern=function(fmt) {         
    var o = {         
    "M+" : this.getMonth()+1, // 月份
    "d+" : this.getDate(), // 日
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, // 小时
    "H+" : this.getHours(), // 小时
    "m+" : this.getMinutes(), // 分
    "s+" : this.getSeconds(), // 秒
    "q+" : Math.floor((this.getMonth()+3)/3), // 季度
    "S" : this.getMilliseconds() // 毫秒
    };         
    var week = {         
    "0" : "/u65e5",         
    "1" : "/u4e00",         
    "2" : "/u4e8c",         
    "3" : "/u4e09",         
    "4" : "/u56db",         
    "5" : "/u4e94",         
    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
} 


function   ages(str)  {   
	
    var  r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);     
    if(r==null)return   false;     
    var   d=   new   Date(r[1],   r[3]-1,   r[4]);  
    if   (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4])   
    {   
  	  var today = new Date();   
  	  var NowYear   =   today.getFullYear();
  	  var NowMonth = today.getMonth()+1;   
  	  var NowDate = today.getDate();   
          
  	  var Dateleft =NowDate-r[4]; 
  	  var Monthleft =NowMonth-r[3];
      var Yearleft = NowYear-r[1]; 
      var result ="";
  	  if (Dateleft<0)   
  	  {   
  	  Dateleft=31+Dateleft;   
  	  Monthleft=Monthleft-1;   
  	  }  
  	  if (Monthleft<0)   
  	  {   
  	  Monthleft=12+Monthleft;   
  	  Yearleft=Yearleft-1;   
  	  }   
  	  
  	  if(Yearleft>0){
  		  result=Yearleft+"岁";
  	  }
  	  if(Monthleft>0){
  		  result=result+Monthleft+"月";
  	  }
  	  if(Dateleft>0){
  		  result=result+Dateleft+"天";
  	  }else{
      	result=result+"0天";
  	  }
  	  return result ;
    }   
    return("输入的日期格式错误！");   
}  	
	

Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	if (this[i] == val) return i;
	}
	return -1;
};
	
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	this.splice(index, 1);
	}
};
	

Number.prototype.sub = function (arg){ 
	return accSub(this,arg); 
} 
Number.prototype.add = function (arg){ 
	return accAdd(this,arg); 
} 
Number.prototype.mul = function (arg)  
{  
  return accMul(arg, this);  
}  
Number.prototype.div = function (arg)  
{  
  return accDiv(this, arg);  
}  

function accSub(arg1,arg2){
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}

function accAdd(arg1,arg2){
	  var r1,r2,m;
	  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	  m=Math.pow(10,Math.max(r1,r2))
	  return (accMul(arg1,m)+accMul(arg2,m))/m
}

function accMul(arg1,arg2)
{
  var m=0,s1=arg1.toString(),s2=arg2.toString();
  try{m+=s1.split(".")[1].length}catch(e){}
  try{m+=s2.split(".")[1].length}catch(e){}
  return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

function accDiv(arg1,arg2){ 
	var t1=0,t2=0,r1,r2; 
	try{t1=arg1.toString().split(".")[1].length}catch(e){} 
	try{t2=arg2.toString().split(".")[1].length}catch(e){} 
	with(Math){ 
	r1=Number(arg1.toString().replace(".","")) 
	r2=Number(arg2.toString().replace(".","")) 
	return (r1/r2)*pow(10,t2-t1); 
	} 
}

