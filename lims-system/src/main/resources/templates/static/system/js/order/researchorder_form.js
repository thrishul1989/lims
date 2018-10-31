

$(document).ready(function(){

	var hetongId="", ownerId="";
	$("#orderForm").validate({
		
		submitHandler:function(form){
			
			
			var ownerSelections=$('#ownerId').magicSuggest().getSelection();
			if(ownerSelections.length==0){
				showTip('请选择客户！','提示');
				return false;
			}
			$.each(ownerSelections,function(index,obj){
				ownerId=obj.id;
			})

			//项目管理人
            var prjManagerVal = $('#prjManager').magicSuggest().getSelection()[0];
            if(undefined==prjManagerVal) {
                parent.layer.alert('请选择项目管理人！',{title:"提示"});
                return false;
            }


            //合同
			var hetong=$('#hetong').magicSuggest().getSelection();
			$.each(hetong,function(index,obj){
				hetongId=obj.id;
			})
			
			//组装组样本数据
			var mainGroup=[];
			var mainGroups=$(".groupPanel");
			var allGroup=[];
			var productValidate =false;
			$.each(mainGroups,function(index,obj){
				
				var samples=[];
				$.each($(obj).find('.mainGroup'),function(ii,oo){
					var testProductId=$(oo).find(".ms-ctn").attr('id');
					//组装每个样本的产品
					var products=[];
					var da=$('#'+testProductId).magicSuggest().getSelection();
					if(da!=null && da.length >0 ){
						$.each(da,function(p,pp){
							products.push({id:pp.id});
						});
					
						var sample={sampleCode:$(oo).find("input[name='mainSampleCode']").val(),
							sampleTypeId:$(oo).find('select[name="mainSampleType"]').val(),
								sampleName:$(oo).find("input[name='mainSampleName']").val(),
								sampleSize:$(oo).find("input[name='mainSampleSize']").val(),
								samplingDate:$(oo).find("input[name='sampleDate']").val(),
								remark:$(oo).find("input[name='remark']").val(),
								diagnosis:$(oo).find("input[name='orderExamineeDiagnosis']").val(),
								focusGene:$(oo).find("input[name='orderExamineeGene']").val(),
								familyRelation:$(oo).find("input[name='familyRelation']").val(),
								id:$(oo).find("input[name='samplePackageStatus']").val(),
								orderResearchProduct:products};
						samples.push(sample);
						allGroup.push(sample.sampleCode);
						productValidate =true;
					}else{
						productValidate =false;
						return false;
					}
				});
				mainGroup.push({name:$(obj).find('input[name="groupName"]').val(),orderResearchSample:samples})
			
			})
			if(!productValidate){
				showTip("产品不能为空！");
				return ;
			}
			if(isRepeat(allGroup)){
				showTip("样本编号重复，请检查！");
				return ;
			}
			
			//遮罩开始
			var layerObject = parent.parent.layer;
			var loadindex = layerObject.load();
			
			$("#createButton").attr('disabled', 'disabled');
			$("#createButton").val('订单提交中');
				$.ajax({

		            type: "post",
		            url: base+"/order/create.do",
		            data: {id:order.id,orderType:order.orderType,code:$("#orderCode").val(),
		            	contractId:hetongId,
		            	ownerId:ownerId,
		            	creatorName:$("#creatorName").val(),
		            	creatorId:$("#creatorId").val(),
                        belongArea:$("#belongArea").val(),
                        projectManager:$("#projectManager").val(),
		            	//doctorAssist:$('input[name="doctorAssist"]:checked').val(),
		            	recipientName:$("#recipientName").val(),recipientPhone:$("#recipientPhone").val(),
		            	recipientAddress:$("#recipientAddress").val(),invoiceTitle:$("#invoiceTitle").val(),
		            	parmarySample:JSON.stringify(mainGroup)},
		            dataType: "json",
		            success: function(data){
		            	window.location.href=base+"/order/paging.do";
		            	layerObject.close(loadindex);
		            },
		            error:function(){
		            	layerObject.close(loadindex);
		            	$("#createButton").removeAttr("disabled");
		            	$("#createButton").val('提交订单');
		            }
	        });
			
			
			
		},
        rules: {
        	orderCode:  {
        		required:true,
        		remote: {
         		    url: base+"/order/validate.do",
         		    type: "post",               
         		    dataType: "json",          
         		    data: {
         		        code: function() {
         		            return $("#orderCode").val();
         		        },
         		        id:function() {
         		            return order.id;
         		        }
         		    }
         		}
        	},
        	creatorName:{
        		required:true,
        	},
        	mainSampleSize:{
        		required:true,
        		number:true,
        		min:0.01
        	},
        	mainSampleCode:{
        		required:true,
        		rangelength:[9,9],
        		isMainSampleCode:true,
        		alnum:true
        		
        	},
        	mainSampleType:{
        		required:true,
        	},
        	recipientPhone:{
        		required:true,
        		isTel:true
        	},
      
           
        },
        messages: {
        	orderCode:  {
                required: "请输入订单编号",
            	remote:"订单编号已经存在"
            },
           
            creatorName:{
              	required: "该客户未绑定业务员，请先绑定业务员"
            },
            mainSampleSize:{
            	digits:"请输入整数"
        	},
        	mainSampleCode:{
        		required: "请输入样本编码",
        		rangelength:"样本编号长度不对,应该为9位"
        	},
        	mainSampleType:{
        		required: "请选择样本类型"
        	}
        }
    });


    var prjManager =  $('#prjManager').magicSuggest({
        width: 190,
        highlight: true,
        data:base+'/smm/role/getPrjManagerList.do',
        method:'get',
        queryParam:"key",
        maxSelection: 1,
        displayField:'name',
        allowFreeEntries:false,
        renderer: function(v){
            return '<div >'
                +'<span>' + v.name + '</span>'
                +'<span style="float:right;">' + v.phone + '</span>'
                +'</div>';
        }
    });

    $(prjManager).on('selectionchange',function(e,m){
        var obj_pm = this.getSelection()[0];
        if(obj_pm != undefined )
        {
            $('#projectManager').val(obj_pm.id);
        }

    });

    if (prjManagerUser!=1)
    {
        $('#prjManager').magicSuggest().setSelection(prjManagerUser);
    }
	

	var s=	$('#hetong').magicSuggest({
	    width: 190,
	    highlight: true,
        data:base+'/contract/getContracts.do?status='+2+"&flag="+"inDate"+"&marketingCenter="+order.orderType,
	    method:'get',
	    queryParam:"key",
	    maxSelection: 1,
	    allowFreeEntries:false,
	renderer: function(v){
	    return '<div>' +
	        '<div >' +
	            '<div  class="probe">' + v.name + '</div>' +
	            '</div>' +
	        '<div style="clear:both;"></div>';
	    }
	});
	
	var owner = $('#ownerId').magicSuggest({
	    width: 190,
	    highlight: true,
        data:base+'/customer/getSelectListByConstract.do?testingType='+order.orderType+'&disableStatus='+0,
	    method:'get',
	    queryParam:"query",
	    allowFreeEntries:false,
	    maxSelection: 1,
	    displayField:'realName',
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	            '<div  class="probe">' + v.realName + '</div>' +
	        '<div style="clear:both;"></div>';
	    }
	});
	
	
	var hetongse=order.contract;
	if( hetongse!=undefined && hetongse!="" ){
		$('#hetong').magicSuggest().setSelection(hetongse);
		hetongId = hetongse.id;
	}
	
	$(s).on('selectionchange', function(e,m){
		var hetongObj=$('#hetong').magicSuggest().getSelection();
		if(hetongObj != undefined && hetongObj.length >0){
			//$('#ownerId').magicSuggest().clear();
			$.each($("[id^=testProduct]"),function(i,obj){
				$(obj).magicSuggest().clear();
			});
			var hid = hetongObj[0].id;
			$.post(base+"/contract/getContractUsers.do", { contractId: hid,disableStatus:0 },
				       function(data){
				     $('#ownerId').magicSuggest().setData(data);
				     
			});
			
			$.post(base+"/contract/getContractProducts.do", { contractId: hid },
				       function(data){
				$.each($("[id^=testProduct]"),function(i,obj){
					$(obj).magicSuggest().setData(data);
				});
			});
		}else{
			//初始化所有客户
			$.post(base+"/customer/getSelectListByConstract.do", {testingType:order.orderType,disableStatus:0},
				       function(data){
				     $('#ownerId').magicSuggest().setData(data);
			});
			//初始化产品数据    '/product/productSelect.do?testingType.id='+order.orderType
			$("#testProduct").magicSuggest().setData(base+'/product/productSelect.do?testingType.id='+order.orderType);
		}
		
	});
	
	var ownerValue = order.owner;
	if( ownerValue!=undefined && ownerValue!="" ){
		$('#ownerId').magicSuggest().setSelection(ownerValue);
	}
	
	$(owner).on('selectionchange', function(e,m){
		var ownObj=$('#ownerId').magicSuggest().getSelection();
		if(ownObj != undefined && ownObj.length >0){
			var sid = ownObj[0].id;
			$.post(base+"/customer/getByidAndType", { customerId: sid,testingType:order.orderType },function(data){
				if(data){
					$("#creatorName").val(data.realName);
					$("#creatorId").val(data.id);
				}else{
					$("#creatorName").val('');
					$("#creatorId").val('');
				}
			 });
			//设置合同
			$.post(base+"/contract/getContractByUserId.do", { userId: sid, marketingCenter:order.orderType },function(data){
				$("#hetong").magicSuggest().setData(data);
			 });
		}else{
			$("#creatorName").val('');
			$("#creatorId").val('');
			$.post(base+"/contract/getContracts.do", { status: 2,flag:'inDate',marketingCenter:order.orderType },function(data){
				$("#hetong").magicSuggest().setData(data);
			 });
		}
		
		
	});
	
	var testProduct=$('#testProduct').magicSuggest({
	    width: 190,
	    highlight: true,
        data:base+'/product/productSelect.do?testingType.id='+order.orderType,
	    method:'get',
	    queryParam:"query",
	    allowFreeEntries:false,
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	            '<div  class="probe">' + v.name + '</div>' +
	        '<div style="clear:both;"></div>';
	    }
	});

	
	
	//初始化组样本
	//迭代组
	$.each(order.orderSampleGroup,function(index,group){

		groupid=groupid+1;
		
		var clone=$(".groupTag").clone();
		$(clone).addClass("groupPanel");
		$(clone).removeClass("groupTag");
		$(clone).attr('id',groupid);
		$(clone).find("img").remove();
		$(".tag").before($(clone));
		$(clone).find('input[name="groupName"]').val(group.name);
		
		//迭代样本
		$.each(group.orderResearchSample,function(ii,sample){
			i=i+1;
			var sampleClone=$(".sampleTag").clone();
			$(sampleClone).addClass("mainGroup");
			$(sampleClone).removeClass("sampleTag");
			//塞入数据
			$(sampleClone).find('input[name="samplePackageStatus"]').val(sample.id);
			$(sampleClone).find('input[name="mainSampleCode"]').val(sample.sampleCode);
			$(sampleClone).find('input[name="mainSampleCode"]').attr("id","mainSampleCode"+i);
		
			$(sampleClone).find('select').val(sample.sampleTypeId);
			$(sampleClone).find('input[name="mainSampleName"]').val(sample.sampleName);
			$(sampleClone).find('input[name="mainSampleSize"]').val(sample.sampleSize);
			
			$(sampleClone).find('input[name="sampleDate"]').val(sample.samplingDate);
			$(sampleClone).find('input[name="remark"]').val(sample.remark);
			
			$(sampleClone).find('input[name="orderExamineeDiagnosis"]').val(sample.diagnosis);
			$(sampleClone).find('input[name="familyRelation"]').val(sample.familyRelation);
			$(sampleClone).find('input[name="orderExamineeGene"]').val(sample.focusGene);
			$("#"+groupid).find('#mainContent').append($(sampleClone));
		$("#"+groupid).find('.testProduct').attr('id',"testProduct"+i);
		$('#testProduct'+i).magicSuggest({
			    width: 190,
			    highlight: true,
		       // data:base+'/product/productSelect.do',
			    data:base+'/contract/getContractProducts.do?contractId='+hetongId,
			    method:'get',
			    queryParam:"query",
			    allowFreeEntries:false,
			    renderer: function(v){
			    return '<div>' +
			        '<div >' +
			            '<div  class="probe">' + v.name + '</div>' +
			        '<div style="clear:both;"></div>';
			    }
			});
		
		//设置多选框值
		var products=[];
		$.each(sample.orderResearchProduct,function(iii,product){
			products.push(product.product);
			
		});
		 $('#testProduct'+i).magicSuggest().setSelection(products);
		
		}); //循环样本
		
		
	})//循环组
	
	
})


var sampleOption="";
$.each(sampleList,function(index,obj){
	sampleOption=sampleOption+'<option value="'+obj.id+'">'+obj.name+'</option>'
	
})



function removeMain(obj){
	$(obj).parent().remove();
}

var i=0;
var groupid=1;
function addMain(obj){
	i=i+1;
	$(obj).parent().parent().find('#mainContent').append('<div style="margin-top:17px" class="mainGroup">'+
			
			
			'<img src="'+system_images+'/rubish.png" style="float: right;cursor: pointer;" onclick="removeMain(this)"/>'+
			'<div class="form-group">'+
				'<label class="col-sm-2 control-label control-required"><span>*</span>样本编号：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" name="mainSampleCode"'+
						' value="" />'+
				'</div>'+
				'<label class="col-sm-2 control-label control-required"><span>*</span>样本类型：</label>'+
				'<div class="col-sm-3">'+
					'<select class="form-control" name="mainSampleType"'+
						'>'+
						'<option value="">请选择样本类型</option>'+sampleOption+
					'</select>'+
				'</div></div><div class="form-group">'+
				
				'<label class="col-sm-2 control-label control-required"><span>*</span>样本名称：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" id="mainSampleName'+i+'" name="mainSampleName"'+
						' value="" />'+
				'</div>'+
				'<label class="col-sm-2 control-label control-required"><span>*</span>样本量：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" name="mainSampleSize"'+
						' value="" />'+
				'</div>'+

				'</div>'+
				

				'<div class="form-group">'+
				
				'<label class="col-sm-2 control-label ">采样时间：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control laydate-icon"'+
						'name="sampleDate"  id="sampleDate'+i+'" value=""'+
						'readonly="readonly" style="height: 34px"'+
						'onclick="laydate({istime: true, format: \'YYYY-MM-DD\',max: laydate.now()})" />'+
				'</div>'+

				
				
					'<label class="col-sm-2 control-label control-required"><span>*</span>检测产品：</label>'+
					'<div class="col-sm-3">'+
							'<div class="form-control testProduct" id="testProduct'+i+'"  placeholder="请选择检测产品"></div>'+
					'</div>'+
							
				
				'</div>'+
				
				
				'<div class="form-group">'+
				
				
				'<label class="col-sm-2 control-label control-required">临床诊断：</label>'+
				'<div class="col-sm-3">'+
				'<input type="text" class="form-control"'+
					'name="orderExamineeDiagnosis" value="" />'+
			    '</div>'+


				'<label class="col-sm-2 control-label control-required">家系关系：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control"'+
						'name="familyRelation" value="" />'+
				'</div>'+

				

			'</div>'+
				'<div class="form-group">'+
				
				'<label class="col-sm-2 control-label control-required">重点关注基因：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control"'+
						'name="orderExamineeGene" value="" />'+
				'</div>'+
				
				'<label class="col-sm-2 control-label ">备注：</label>'+
				'<div class="col-sm-3">'+
						'<input type="text" class="form-control" '+
							' name="remark" value="" /></div> '+
				
				'</div>'+
				
'<DIV style="BORDER-TOP: #adadad  1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV></div>');
	

	var hetong=$('#hetong').magicSuggest().getSelection();
	$.each(hetong,function(index,obj){
		hetongId = obj.id;
	});
	$('#testProduct'+i).magicSuggest({
	    width: 190,
	    highlight: true,
        //data:base+'/product/productSelect.do?testingType.id='+order.orderType,
	    data: base+'/contract/getContractProducts.do?contractId='+hetongId,
	    method:'get',
	    queryParam:"query",
	    allowFreeEntries:false,
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	            '<div  class="probe">' + v.name + '</div>' +
	        '<div style="clear:both;"></div>';
	    }
	});
	
}

function addgroup(obj){
	
    groupid=groupid+1;
	i=i+1;
	var clone=$(".groupTag").clone();
	$(clone).addClass("groupPanel");
	$(clone).removeClass("groupTag")
	$(clone).attr('id',groupid);
	//$(clone).find('a.btn.btn-sm.btn-success.addBtn').remove();
	//$(clone).find('.testProduct').attr('id',"testProduct"+i)
	$(clone).find('input').val("");
	
	
	$(".tag").before($(clone).prop("outerHTML"));

	/*var hetong=$('#hetong').magicSuggest().getSelection();
	$.each(hetong,function(index,obj){
		hetongId = obj.id;
	});
	
	$('#testProduct'+i).magicSuggest({
	    width: 190,
	    highlight: true,
       // data:base+'/product/productSelect.do',
	    data: base+'/contract/getContractProducts.do?contractId='+hetongId,
	    method:'get',
	    queryParam:"query",
	    allowFreeEntries:false,
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	            '<div  class="probe">' + v.name + '</div>' +
	        '<div style="clear:both;"></div>';
	    }
	});
	*/

}
function removeGroup(obj){
	//最后一个分组不让删除
	if($('.groupPanel').length == 1){
		return false;
	}
	else{
		$(obj).parent().remove()
	}

}

