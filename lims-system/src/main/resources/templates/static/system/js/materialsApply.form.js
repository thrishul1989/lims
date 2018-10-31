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
var i=0;
function addHtml(){
	
	var html = $('.copyDiv').clone();
	html.find('.a').attr('id',"a"+i)
	html.removeClass('copyDiv');
	html.addClass('onlytable');
	$('#temDiv').append(html);
	i++;
}
function deleteHtml(obj){
	$(obj).parents(".onlytable").remove();
}


function getMaterial(obj){
	var id = $('#test').attr('data-value');
	$('#formModal').modal('hide');
	var temHtml = $(obj).parents('.search-form');
	var materialsId = temHtml.find('select[name="materialsName"]').val();
	var materialsName = temHtml.find('select[name="materialsName"] option:selected').text();
	var materialsCount = temHtml.find('input[name="materialsCount"]').val();
	var materialsSendCount = temHtml.find('input[name="materialsSendCount"]').val();
	var materialSendCount = temHtml.find('input[name="materialSendCount"]').val();
	var showhtml = 	'<tr class="count"><td class="materialsName">'+materialsName+'</td><td>'+materialsCount+'</td><td>'+materialsSendCount+
						'</td><td>'+materialSendCount+'</td><td style="display:none;">'+materialsId+'</td><td onclick="deleteTr(this)"><a href="#">删除<a></td></tr>'		
	$("#"+id).parent().next().find('.tbodyShow').append(showhtml);
}


function selectVal(obj){ 
	if(''==$(obj).val()){
		selectEmpty();
		return;
	}
	$.ajax({
		type:'post',
		data:{id:$(obj).val()},
		url:base+'/materialsApply/getDetail.do',
		success:function(data){
			$('input[name="materialsCount"]').val(data.materialsCount);
			$('input[name="materialsSendCount"]').val(data.materialsSendCount);
			if('' == data.materialsSendCount||null == data.materialsSendCount){
				materialsSendCount = 0;
			}
			$('input[name="materialSendCount"]').val(data.materialsCount - data.materialsSendCount);
		}
	});
	
}

function deleteTr(obj){
	
	$(obj).parents('tr').remove();
}

function getVal(obj){
	
		$("#materialsName").empty();
		var model = "";
		for(var i=0;i<arr.length;i++){
			model += "<option value='"+arr[i].id+"' text='"+arr[i].materialsName+"'>"+arr[i].materialsName+"</option>"
		}
		$("#materialsName").append(model); 
		$("#materialsName").prepend("<option value=''>请选择</option>");
	var vals_ = [];
	$(obj).parents(".onlytable").find(".materialsName").each(function(i,v){
		vals_.push($(v).text());
	});
	for(var i=0;i<vals_.length;i++){
		
		$("#materialsName option[text='"+vals_[i]+"']").remove(); 
	}
	$('select[name="materialsName"]').val('');
	selectEmpty();
	var id = $(obj).attr('id');
	$('#test').attr('data-value',id);
}


function insertValue(obj){
	var flag = true;
	var matFlag = true;
	for(var j=0;j<arr.length;j++){
		var count = 0;
		$('.onlytable').each(function(m,n){
			$(n).find('.materialsName').each(function(i,v){
				if(arr[j].materialsName == $(v).text()){
					count += parseInt($(v).next().next().next().text());
				}
			});
		});
		if(arr[j].count < count){
			layer.alert(arr[j].materialsName+'打包数量已经大于申请数量', {title : "提示"});
			return;
		}
	}
	var mats = [];
	$(obj).parents(".ibox-content").find('.onlytable').each(function(i,v){
		var mat = {};
		mat.sendTime = $(v).find($('input[name="sendTime"]')).val();
		mat.transportType = $(v).find($('select[name="transportType"]')).val();
		if(mat.transportType == ''){
			flag = false;
		}
		mat.transportPhone = $(v).find($('input[name="transportPhone"]')).val();
		mat.courierNumber = $(v).find($('input[name="courierNumber"]')).val();
		mat.transportName = $(v).find($('input[name="transportName"]')).val();
		
		var relations = [];
		$(v).find('.count').each(function(i,v){
			var relation = {};
			relation.materialsName = $(v).find('td').eq(0).text();
			relation.materialSendCount = $(v).find('td').eq(3).text();
			relation.materialsApplyDetailId = $(v).find('td').eq(4).text();
			if(0 != relation.materialSendCount){
				relations.push(relation);
			}
		});
		if(relations.length<1)
		{
			matFlag = false;
		}
		mat.relations = relations;
		mats.push(mat);
		});	
	var transport = JSON.stringify(mats);
	if(flag){
		if(matFlag)
		{
			$.ajax({
				url:base+"/materialsApply/handleMaterials.do",
				type:"post",
				data:{transport:transport,id:$('#materialsApplyId').val()},
				success:function(data){
					if(data == 'OK'){
						window.location.href = base+'/materialsApply/list.do';
					}
				}
			}); 
		}
		else{
			layer.alert('请添加清单信息！', {title : "提示"});
		}
	}else{
		layer.alert('请填写运输方式！', {title : "提示"});
	}
}

function checkedPhone(obj){
	var tel = /^\d{3,4}-?\d{7,8}$/; 
	var mobile = /^1\d{10}$/;
	console.log(mobile.test($(obj).val()));
	console.log(tel.test($(obj).val()));
	if (mobile.test($(obj).val())||tel.test($(obj).val())){
		
	}else{
		layer.alert('请输入正确的手机号码！', {title : "提示"});
		$(obj).val('');
	}
}

function checkedValue(obj){
	var reg = /^[1-9]\d*$/;
	var temHtml = $(obj).parents('.search-form');
	var materialsCount = temHtml.find('input[name="materialsCount"]').val();
	var materialsSendCount = temHtml.find('input[name="materialsSendCount"]').val();
	 if(materialsSendCount == ''){
		 materialsSendCount = 0
	 }
	 if(materialsCount - materialsSendCount < $(obj).val()||$(obj).val() == 0 || !reg.test($(obj).val())){
		 layer.alert('输入有误！', {title : "提示"});
		 $(obj).val("");
	 }
}

function selectEmpty(){
	$('input[name="materialsCount"]').val('');
	$('input[name="materialsSendCount"]').val('');
	$('input[name="materialSendCount"]').val('');
}