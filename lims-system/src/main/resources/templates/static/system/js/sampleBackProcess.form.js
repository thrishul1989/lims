function selectType(obj){
	var d1 = $(obj).parents("tr").next();
	var d2 = $(obj).parents("tr").next().next();
	if($(obj).val() != "0"){
		d1.hide();
		d2.show();
	}else{
		d1.show();
		d2.hide();
	}
}
