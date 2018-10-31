$(function(){
	$("#sampleId").bsSuggest({
        url: "/sample/getSelecteList.do",
        getDataMethod: "url",
        effectiveFields : [ "name" ],
        effectiveFieldsAlias:{
        	name: "样本类型名称"
		},
        ignorecase: true,
        showHeader: true,
        showBtn: true,
        idField: "id", //每组数据的哪个字段作为 data-id
        keyField: "name" //每组数据的哪个字段作为输入框内容
    }).on('onDataRequestSuccess', function (e, result) {
    }).on('onSetSelectValue', function (e, keyword, data) {
    	$('#code').val(data.id);
    }).on('onUnsetSelectValue', function () {
    });
	
	
	$("#sampleCount").blur(function(){
		if($("#sampleCount").val() > 0 
				 && $("#temporaryStorageAmount").val() > 0 &&
				 	$("#sampleCount").val()-$("#temporaryStorageAmount").val() > 0){
				 $("#longtermStorageAmount").val($("#sampleCount").val()-$("#temporaryStorageAmount").val());
		}else if($("#sampleCount").val()-$("#temporaryStorageAmount").val() < 0){
			parent.layer.alert('临时取样量不能大于送样数量！',{title:"提示"});
			$("#temporaryStorageAmount").val(0)
			 $("#longtermStorageAmount").val($("#sampleCount").val()-$("#temporaryStorageAmount").val());
	 }
	});
	
	$("#temporaryStorageAmount").blur(function(){
		
	 if($("#sampleCount").val()-$("#temporaryStorageAmount").val() < 0){
			parent.layer.alert('临时取样量不能大于送样数量！',{title:"提示"});
			$("#temporaryStorageAmount").val(0);
			$("#longtermStorageAmount").val($("#sampleCount").val()-$("#temporaryStorageAmount").val());
	 }else if($("#sampleCount").val() > 0 
			 && $("#temporaryStorageAmount").val() > 0 &&
			 	$("#sampleCount").val()-$("#temporaryStorageAmount").val() > 0||
			 	$("#sampleCount").val()-$("#temporaryStorageAmount").val() == 0){
			 $("#longtermStorageAmount").val($("#sampleCount").val()-$("#temporaryStorageAmount").val());
	 }else{
			parent.layer.alert('输入的数字不能小于0！',{title:"提示"});
		}
	});
	
	
	$("#sampleIdentification").change(function(){
		if($("#sampleIdentification :selected").val() == "2"){
			$("#div").hide();
		}
		if($("#sampleIdentification :selected").val() == "1"){
			$("#div").show();
		}
	});
	
});