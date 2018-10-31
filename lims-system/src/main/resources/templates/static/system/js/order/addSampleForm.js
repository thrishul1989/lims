var sampleOption="";

$(document).ready(function(){

	$.each(sampleList,function(index,obj){
		sampleOption=sampleOption+'<option value="'+obj.id+'" unit='+obj.receivingUnit+'>'+obj.name+'</option>'
	});
	
	

	$("#orderForm").validate({
		submitHandler:function(form){
			//组装附属样本数据
			var allGroup=[];
			var seGroup=[];
			var seGroups=$(".seGroup");
			var checkcount =0;
			var subSampleList = order.orderSubsidiarySample;
			if(subSampleList && subSampleList.length>0){
				for(var i in subSampleList ) {
					if(subSampleList[i].purpose == 3){
						checkcount ++;
					}
				}
			}
		
			
			$.each(seGroups,function(index,obj){
				var se={sampleCode:$(obj).find("input[name='seSampleCode']").val(),sampleTypeId:$(obj).find('select[name="seSampleType"]').val(),
						sampleSize:$(obj).find("input[name='seSampleSize']").val(),samplingDate:$(obj).find("input[name='seSampleDate']").val(),
						ownerName:$(obj).find("input[name='ownerName']").val(),ownerAge:$(obj).find("input[name='ownerAge']").val(),
						purpose:$(obj).find("select[name='purpose']").val(),ownerPhenotype:$(obj).find("select[name='ownerPhenotype']").val(),
						familyRelation:$(obj).find("select[name='familyRelation']").val()};
				if(se.purpose == 3){
					checkcount ++;
				}
				seGroup.push(se);
				allGroup.push(se.sampleCode.toUpperCase());
			})
			if(checkcount>1){
				showTip("本人对照样本至多一个","提示！");
				return ;
			}
			if(isRepeat(allGroup)){
				showTip("样本编号重复，请检查！");
				return ;
			}
			
			$("#createButton").attr('disabled', 'disabled');
			$("#createButton").val('提交中');
			var layerObject = parent.parent.layer;
			var loadindex = layerObject.load();
			
			$.ajax({
	            type: "post",
	            url: base+"/order/addOrderSample.do",
	            data: {
	            	id:order.id,
	            	subsidiarySample:JSON.stringify(seGroup)},
	            dataType: "json",
	            success: function(data){
	            	window.location.href=base+"/order/paging.do";
	            	layerObject.close(loadindex);
	            },
	            error:function(){
	            	layerObject.close(loadindex);
	            	$("#createButton").removeAttr("disabled");
	            	$("#createButton").val('提交');
	            }
	           
	        });
			
			
			
		},
        rules: {
        	
        	seSampleCode:{
        		required:true,
        		remote: {
         		    url: base+"/order/validateSeCode.do",
         		    type: "post",               
         		    dataType: "json"          
         		},
         		rangelength:[9,9],
         		alnum:true
        	},
        	seSampleSize:{
        		required:true,
        		number:true,
        		min:0.01
        	},
        	
        	seSampleType:{
        		required:true
        	},
        	seSampleDate:{
        		required:true
        	},
        	
        	purpose:{
        		required:true
        	},
        	familyRelation:{
        		required:true
        	},
        	ownerAge:{
        		required:true,
        		digits:true,
        		min:0
        	},
        	ownerName:{
        		required:true
        	},
        	ownerPhenotype:{
        		required:true
        	}
        	
           
        },
        messages: {
            seSampleCode:{
            	required: "请输入附属样本编号",
            	remote:"附属样本编号已经存在",
            	rangelength:"样本编号长度不对,应该为9位"
            },
            seSampleSize:{
            	digits:"请输入整数"
            },
           
        }
    });
	
	
	
})



function showSeUnit(obj){
	
	var unit=$(obj).find('option:selected').attr('unit');
	if(typeof(unit) != 'undefined' && null != unit && "" != unit){
		$.post(base+"/sample/getUnitName.do", { id: unit,},function(data){
	         $(obj).closest('.seGroup').find('input[name="seSampleSize"]').parent().prev().html(
	 				"<span>*</span>样本量("+data.name+")"		
	 		);
		});
		
	}
	else{
		$(obj).closest('.seGroup').find('input[name="seSampleSize"]').parent().prev().html(
				"<span>*</span>样本量"		
		);
	}
}


function showSeName(obj){
	var examineeName = order.orderExamineeList[0].name;
	if(examineeName){
		
		var relationName=$(obj).find('option:selected')[0].innerHTML;
		var relationId = $(obj).find('option:selected').val();
		
		//根据关系查找个数  设置初始值
		if(typeof(relationId) != 'undefined' && null != relationId && "" != relationId){
			var index =-1 ;
			
			$.ajax({  
		         type : "post",  
		          url : base+"/order/countSubSampleByRelationId.do",  
		          data : { relationId:relationId,orderId:order.id}, 
		          async : false,  
		          success : function(data){  
		            index = data;
		         }  
		    }); 
			
		}
		
		var relations = $(".seGroup").find('select[name="familyRelation"]');
		$.each(relations,function(i,ob){
			if($(ob).val() == relationId ){
				index++ ;
			}
		});
		var ownerName = examineeName+relationName;
		if(index >0){
			ownerName = ownerName+index;
		}
		$(obj).closest('.seGroup').find('input[name="ownerName"]').val(ownerName);
	
	}else{
		showTip("无受检人姓名","提示");
		
	}
	
}

function removeSeSample(obj){
	$(obj).parent().remove();
}

var j =0;
function addSe(){
	j =j+1;
	$('#seContent').append('<div style="margin-top:17px" class="seGroup"><img src="'
			+system_images+'/rubish.png" style="float: right;cursor: pointer;" onclick="removeSeSample(this)"/><div class="form-group">'+
			'<label class="col-sm-2 control-label control-required"><span>*</span>样本编号：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" class="form-control"'+
					'name="seSampleCode" id="seSampleCode'+j+'" value="" /></div>'+

			'<label class="col-sm-2 control-label control-required"><span>*</span>样本类型：</label>'+
			'<div class="col-sm-3">'+
				'<select class="form-control" name="seSampleType" id="seSampleType'+j+'" onchange="showSeUnit(this)" >'+
					'<option value="">请选择样本类型</option>'+sampleOption+
				'</select>'+
			'</div>'+
		'</div>'+
		
		'<div class="form-group">'+
			'<label class="col-sm-2 control-label control-required"><span>*</span>样本量：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" class="form-control"'+
					'name="seSampleSize"  id="seSampleSize'+j+'" value="" />'+
			'</div>'+

			'<label class="col-sm-2 control-label control-required"><span>*</span>采样时间：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" class="form-control laydate-icon"'+
					'name="seSampleDate" id="seSampleDate'+j+'" value="" readonly="readonly"'+
					'style="height: 34px"'+
					'onclick="laydate({istime: true, format: \'YYYY-MM-DD\',max: laydate.now()})" />'+
			'</div>'+

		'</div>'+

		'<div class="form-group">'+

			'<label class="col-sm-2 control-label control-required"><span>*</span>年龄：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" class="form-control" id="ownerAge'+j+'" name="ownerAge"'+
					'value="" />'+
			'</div>'+

			'<label class="col-sm-2 control-label control-required"><span>*</span>姓名：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" class="form-control" id="ownerName'+j+'" name="ownerName"'+
					'value="" />'+
			'</div>'+

		'</div>'+

		'<div class="form-group">'+

			'<label class="col-sm-2 control-label control-required"><span>*</span>用途：</label>'+
			'<div class="col-sm-3">'+$("#purposeClone").clone().prop("outerHTML")+
			'</div>'+
			'<span class="phenotype"> <label class="col-sm-2 control-label control-required"><span>*</span>家属症状：</label>'+
				'<div class="col-sm-3">'+
				$("#ownerPhenotype").prop("outerHTML")+
				'</div>'+

			'</span>'+


		'</div>'+

		'<div class="form-group">'+
		'<label class="col-sm-2 control-label control-required"><span>*</span>家系关系：</label>'+
		'<div class="col-sm-3">'+
			
		$("#familyRelation").prop("outerHTML")+
		'</div>'+
		'</div>'+

		'<DIV style="BORDER-TOP: #adadad 1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV>'+
	'</div>');

	
}




