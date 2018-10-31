$(function(){
	 jQuery.validator.addMethod("decimal", function(value, element) {
			var decimal = /^-?\d+(\.\d{1,2})?$/;
			return this.optional(element) || (decimal.test(value));
	 });
	 
	 jQuery.validator.addMethod("lrunlv", function(value, element) {         
		    return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value);         
	 }, "小数位不能超过三位"); 
	
  /**
     * 验证手机号码
     */
    jQuery.validator.addMethod("isMobile",function(value, element) {
		var length = value.length;
		var mobile = /^1\d{10}$/;
		return this.optional(element)|| mobile.test(value);
	}, "请正确填写您的手机号码");
	    
    jQuery.validator.addMethod("isLet", function(value, element) {  
    	var reg = /^[A-Za-z]+$/;
    	return this.optional(element) || reg.test($.trim(value));
    }, "请输入字母"); 
    
    jQuery.validator.addMethod("isEmpty", function(value, element) {  
		 return this.optional(element) || ($.trim(value) != "");
		}, "不能为空串");  
	 
	// 电话号码验证
	jQuery.validator.addMethod("isTel", function(value, element) {
	  var tel = /^\d{3,4}-?\d{7,8}$/; //电话号码格式010-12345678
	  var mobile = /^1\d{10}$/;
	  return this.optional(element) || (tel.test(value))|| mobile.test(value);
	}, "请正确填写您的电话号码"); 
	
	// 0-100比例
	jQuery.validator.addMethod("precent", function(value, element) {
	  var reg = /^(\\d|[1-9]\\d|100)$/;  
	  return this.optional(element) || (reg.test(value));
	}, "请正确填写比例"); 

	jQuery.validator.addMethod("alnum",function(value, element){
		return this.optional(element) ||/^[a-zA-Z0-9]+$/.test(value);
	}, "只能包括英文字母和数字");
	
	
	jQuery.validator.addMethod("samplenumber",function(value, element){
		var reg = /^d+(.d+)?$/
		return this.optional(element) || (!reg.test(value));
	}, "请输入大于0的数字（包含小数）");



    jQuery.validator.addMethod("noxiahua",function(value, element){
        var reg = /^(?=.*_)/
        return this.optional(element) || (!reg.test(value));
    }, "请不要输入下划线");

	
jQuery.validator.addMethod("isMainSampleCode", function(value, element) {   
		
		var ids = $(element).parent().parent().parent().find("input[name=samplePackageStatus]").val();
		
		var result = false;
		$.ajax({
            type: "POST",
            url: base+"/order/validateSampleCode.do",
            data: {
            		id:ids,
            		mainSampleCode:value
            	},
            async:false,
            dataType: "json",
            success: function(data){
            	result = data;
            },
             
        });
		
	    return result;
	}, "样本编号重复");
});

