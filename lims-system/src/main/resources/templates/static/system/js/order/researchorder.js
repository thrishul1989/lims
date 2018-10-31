var hetongId="" , ownerId="";
$(document).ready(function(){

	$('#uploadData').fileinput({
        language: 'zh', // 设置语言
        uploadUrl:'', // 上传的地址
        allowedFileExtensions : ['xlsx', 'xls'],// 接收的文件后缀
        showUpload: false, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        browseClass: "btn btn-primary", // 按钮样式
        showRemove:false,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>", 
    }).on("fileuploaded", function(event, data) {
    	
    });
	

	$("#orderForm").validate({
		submitHandler:function(form){
			var owner=$('#ownerId').magicSuggest().getSelection();
			if(owner.length==0){
				showTip('请选择客户！','提示');
				return;
			}
			$.each(owner,function(index,obj){
				ownerId=obj.id;
			})
			
			//合同
			var hetong=$('#hetong').magicSuggest().getSelection();
			$.each(hetong,function(index,obj){
				hetongId = obj.id;
			})
			if(hetongId.length==0){
				showTip('请选择合同！','提示');
				return;
			}
			//组装组样本数据
			var mainGroup=[];
			var mainGroups=$(".groupPanel");
			var allGroup=[];
			var productValidate =false;
			$.each(mainGroups,function(index,obj){
				
				var samples=[];
				
				$.each($(obj).find('.mainGroup').not(":hidden"),function(ii,oo){
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
								samplingDate:$(oo).find("input[name='sampleDate']").val(),
								remark:$(oo).find("input[name='remark']").val(),
								sampleSize:$(oo).find("input[name='mainSampleSize']").val(),
								diagnosis:$(oo).find("input[name='orderExamineeDiagnosis']").val(),
								focusGene:$(oo).find("input[name='orderExamineeGene']").val(),
								familyRelation:$(oo).find("input[name='familyRelation']").val(),
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
			
			});
			
			if(!productValidate){
				showTip("产品不能为空！");
				return ;
			}
			if(isRepeat(allGroup)){
				showTip("样本编号重复，请检查！");
				return ;
			};
			var layerObject = parent.parent.layer;
			var loadindex = layerObject.load();
			
			$("#createButton").attr('disabled', 'disabled');
			$("#createButton").val('订单提交中');
			$.ajax({
	            type: "post",
	            url: base+"/order/create.do",
	            data: {orderType:searcher.orderType,
	            	contractId:hetongId,
	            	ownerId:ownerId,
	            	creatorName:$("#creatorName").val(),
	            	creatorId:$("#creatorId").val(),
	            	//doctorAssist:$('input[name="doctorAssist"]:checked').val(),
	            	recipientName:$("#recipientName").val(),
	            	recipientPhone:$("#recipientPhone").val(),
	            	recipientAddress:$("#recipientAddress").val(),/*invoiceTitle:$("#invoiceTitle").val(),*/
                    projectManager:$("#projectManager").val(),
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
        	
        	mainSampleSize:{
        		required:true,
        		number:true,
        		min:0.01
        	},
        	creatorName:{
        		required:true,
        	},
        	mainSampleCode:{
        		required:true,
        		rangelength:[9,9],
        		/*remote: {
         		    url: base+"/order/validateSampleCode.do",
         		    type: "post",               
         		    dataType: "json"          
         		},*/
        		isMainSampleCode:true,
         		alnum:true
        	},
        	mainSampleName:{
        		required:true,
        	},
        	mainSampleType:{
        		required:true,
        	},
        	recipientName:{
        		required:true,	
        	},
        	recipientPhone:{
        		required:true,
        		isTel:true
        	},
        	recipientAddress:{
        		required:true,
        	}
        	
        },
        messages: {
        	
            mainSampleSize:{
            	digits:"请输入整数"
        	},
    	   creatorName:{
          	required: "该客户未绑定业务员，请先绑定业务员"
           },
        	mainSampleCode:{
        		required: "请输入样本编码",
                //remote:"样本编号已经存在",
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

	var s=	$('#hetong').magicSuggest({
	    width: 190,
	    highlight: true,
        data:base+'/contract/getContracts.do?status='+2+"&flag="+"inDate"+"&marketingCenter="+searcher.orderType,
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
	
	var owner=$('#ownerId').magicSuggest({
	    width: 190,
	    highlight: true,
        //data:base+'/customer/getSelectListByConstract.do?activateStatus='+1+'&disableStatus='+0,
	    data:base+'/customer/getSelectListByConstract.do?testingType='+searcher.orderType+'&disableStatus='+0,
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
	
	
	$(s).on('selectionchange', function(e,m){
		var hetongObj=$('#hetong').magicSuggest().getSelection();
		
		if(hetongObj != undefined && hetongObj.length >0){
			//$('#ownerId').magicSuggest().clear();
			$('#ownerId').magicSuggest().collapse();
			
			$.each($("[id^=testProduct]"),function(i,obj){
				$(obj).magicSuggest().clear();
				$(obj).magicSuggest().collapse();
			});
			
			var hid = hetongObj[0].id;
			$.post(base+"/contract/getContractUsers.do", { contractId: hid,disableStatus:0 },
			       function(data){
			     $('#ownerId').magicSuggest().setData(data);
			 });

            //合同订单--默认为改合同的项目管理人
            $.ajax({
                type:'get',
                url:base+'/contract/getPrjManager?id='+hid,
                success:function(data){
                    if(data.id!=null)
                    {
                        $('#projectManager').val(data.id);
                        $('#prjManager').magicSuggest().setSelection(data);
                    }
                    else
                    {
                        $('#projectManager').val('');
                        $('#prjManager').magicSuggest().clear();
                        var p = $('#prjManager').magicSuggest();
                        p.clear();
                        $(p.input).focus();
                        $('.ibox-title strong').trigger("click");
                    }
                }
            });
			$.post(base+"/contract/getContractProducts.do", { contractId: hid },
				       function(data){
				$.each($("[id^=testProduct]"),function(i,obj){
					$(obj).magicSuggest().setData(data);
				});
			});
			
		}else{
			//初始化所有客户
			$.post(base+"/customer/getSelectListByConstract.do", {testingType:searcher.orderType,disableStatus:0},
				       function(data){
				     $('#ownerId').magicSuggest().setData(data);
			});
			//初始化产品数据   data:base+'/product/productSelect.do?testingType.id='+searcher.orderType,
			$("#testProduct").magicSuggest().setData(base+'/product/productSelect.do?testingType.id='+searcher.orderType);
		}
		
		
	});
	
	$(owner).on('selectionchange', function(e,m){
		var ownObj=$('#ownerId').magicSuggest().getSelection();
		
		if(ownObj != undefined && ownObj.length >0){
			var sid = ownObj[0].id;
			$.post(base+"/customer/getByidAndType", { customerId: sid,testingType:searcher.orderType },function(data){
				if(data){
					$("#creatorName").val(data.realName);
					$("#creatorId").val(data.id);
                    var hd = $('#hetong').magicSuggest().getSelection();
                    if(hd =undefined||hd.length<= 0)//当前只选择了客户没有选择合同
					{
                        //直接创建订单--订单的管理人为订单业务员关联的项目管理人
                        $.ajax({//根据业务员获取绑定的项目管理人
                            type:'get',
                            url:base+'/smm/appsaleman/getPrjManager?id='+data.id,
                            success:function(data){
                                if(null !=data.id)//该业务员下有项目管理人,带入
                                {
                                    $('#prjManager').magicSuggest().setSelection(data);

                                    var p = $('#prjManager').magicSuggest();
                                    $(p.input).focus();
                                    $('.ibox-title strong').trigger("click");

                                }
                                else//如果没有项目管理人则清空已选
                                {
                                    var pm_ = $('#prjManager').magicSuggest().getSelection()[0];
                                    if(undefined !=pm_) {

                                        var p = $('#prjManager').magicSuggest();
                                        p.clear();
                                        $(p.input).focus();
                                        $('.ibox-title strong').trigger("click");
                                    }
                                }
                            }
                        });
					}

				}else{
					$("#creatorName").val('');
					$("#creatorId").val('');
				}
			         
			 });
			//设置合同
			$.post(base+"/contract/getContractByUserId.do", { userId: sid , marketingCenter:searcher.orderType },function(data){
				$("#hetong").magicSuggest().setData(data);
			 });
		}else{
			$("#creatorName").val('');
			$("#creatorId").val('');
			$.post(base+"/contract/getContracts.do", { status: 2,flag:'inDate',marketingCenter:searcher.orderType },function(data){
				$("#hetong").magicSuggest().setData(data);
			 });
		}
		
	});
	
	var testProduct=$('#testProduct').magicSuggest({
	    width: 190,
	    highlight: true,
        data:base+'/product/productSelect.do?testingType.id='+searcher.orderType,
	    //data:base+'/contract/getContractProducts.do?contractId='+$('#hetong').val(),
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
	
	

	
	
})


var sampleOption="";
$.each(sampleList,function(index,obj){
	sampleOption=sampleOption+'<option value="'+obj.id+'" unit='+obj.receivingUnit+'>'+obj.name+'</option>'
	
})


function showUnit(obj){
	
	var unit=$(obj).find('option:selected').attr('unit');
	if(typeof(unit) != 'undefined' && null != unit && "" != unit){
		$(obj).closest('.mainGroup').find('input[name="mainSampleSize"]').parent().prev().html(
				"<span>*</span>样本量("+unit+")"		
		);
	}
	else{
		$(obj).closest('.mainGroup').find('input[name="mainSampleSize"]').parent().prev().html(
				"<span>*</span>样本量"		
		);
	}
}
function removeMain(obj){
	$(obj).parent().remove();
	i = i-1;
}

var i=0;
function addMain(obj){
	i=i+1;
	
	$(obj).parent().parent().find('#mainContent').append('<div style="margin-top:17px" class="mainGroup">'+
			'<img src="'+system_images+'/rubish.png" style="float: right;cursor: pointer;" onclick="removeMain(this)"/>'+
			'<div class="form-group">'+
			    '<label class="col-sm-2 control-label control-required"><span>*</span>样本编号：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" id="mainSampleCode'+i+'" name="mainSampleCode"'+
						' value="" />'+
				'</div>'+
				'<label class="col-sm-2 control-label control-required"><span>*</span>样本类型：</label>'+
				'<div class="col-sm-3">'+
					'<select class="form-control" id="mainSampleType'+i+'" name="mainSampleType"'+
						' onchange="showUnit(this)">'+
						'<option value="">请选择样本类型</option>'+sampleOption+
					'</select>'+
				'</div>'+
			'</div>'+
				
				
			'<div class="form-group">'+
				'<label class="col-sm-2 control-label control-required"><span>*</span>样本名称：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" id="mainSampleName'+i+'" name="mainSampleName"'+
						' value="" />'+
				'</div>'+

				'<label class="col-sm-2 control-label control-required"><span>*</span>样本量：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" id="mainSampleSize'+i+'" name="mainSampleSize"'+
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

				'<label class="col-sm-2 control-label ">备注：</label>'+
				'<div class="col-sm-3">'+
						'<input type="text" class="form-control" '+
							' name="remark" value="" /></div> '+
				
				
			'</div>'+
				
				
			'<div class="form-group">'+
				
				'<label class="col-sm-2 control-label ">临床诊断：</label>'+
				'<div class="col-sm-3">'+
				'<input type="text" class="form-control"'+
					'name="orderExamineeDiagnosis" value="" />'+
				'</div>'+


				'<label class="col-sm-2 control-label ">家系关系：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control"'+
						'name="familyRelation" value="" />'+
				'</div>'+

			'</div>'+
			
			'<div class="form-group">'+
				
				'<label class="col-sm-2 control-label">重点关注基因：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control"'+
						'name="orderExamineeGene" value="" />'+
				'</div>'+
				
				'<label class="col-sm-2 control-label control-required"><span>*</span>检测产品：</label>'+
							'<div class="col-sm-3">'+
									'<div class="form-control testProduct" id="testProduct'+i+'"  placeholder="请选择检测产品"></div>'+
							'</div>'+
			'</div>'+
				
'<DIV style="BORDER-TOP: #adadad  1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV></div>');
	
	var hetong=$('#hetong').magicSuggest().getSelection();
	$.each(hetong,function(index,obj){
		hetongId = obj.id;
	});
	var url ='';
	if(hetongId != ''&& hetongId != undefined){
		url = base+'/contract/getContractProducts.do?contractId='+hetongId;
	}else{
		url = base+"/product/productSelect.do?testingType.id="+searcher.orderType;
	}
	$('#testProduct'+i).magicSuggest({
	    width: 190,
	    highlight: true,
	    data: url,
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




var key = 1000 , groupid=0;
var layerObject = layer;
function checkedForm(){
	
	var excelFileName = document.uploadForm.uploadData.value;
	var formatStr = '';
	var index = excelFileName.lastIndexOf('.');
	if(excelFileName.length == 0){
		parent.layer.alert('请选择需要上传的文件',{title:"提示"});
		return false;
	}else if(index > 0){
		
		formatStr = excelFileName.substring(index);
		if(!(".xlsx" == formatStr||".xls" == formatStr)){
			parent.layer.alert('请上传excel文件',{title:"提示"});
			return false;
		} else {
			var loadindex = layerObject.load();
			
			var hetong=$('#hetong').magicSuggest().getSelection();
			$.each(hetong,function(index,obj){
				hetongId = obj.id;
			});
		 	$('#uploadForm').ajaxSubmit({
	            type: 'post', // 提交方式 get/post
	            url: base+'/order/uploadSearcherSample.do?hetongId='+hetongId, // 需要提交的 url
	            data:'',
	            success: function(data) {
	            	
					$.each(data,function(ii,sample){
						key=key+1;
	        			var sampleClone=$(".sampleTag").clone();
	        			$(sampleClone).addClass("mainGroup");
	        			$(sampleClone).removeClass("sampleTag");
	        			//塞入数据
	        			$(sampleClone).find('input[name="mainSampleCode"]').attr('id','mainSampleCodeId'+key);
	        			$(sampleClone).find('input[name="mainSampleCode"]').val(sample.sampleCode);
	        			$(sampleClone).find('select').val(sample.sampleTypeId);
	        			$(sampleClone).find('input[name="mainSampleName"]').val(sample.sampleName);
	        			$(sampleClone).find('input[name="mainSampleSize"]').val(sample.sampleSize);
	        			$(sampleClone).find('input[name="sampleDate"]').val(sample.samplingDate);
	        			$(sampleClone).find('input[name="remark"]').val(sample.remark);
	        			$(sampleClone).find('input[name="orderExamineeDiagnosis"]').val(sample.diagnosis);
	        			$(sampleClone).find('input[name="familyRelation"]').val(sample.familyRelation);
	        			$(sampleClone).find('input[name="orderExamineeGene"]').val(sample.focusGene);
	        			
	        			
        				var  tt = true;
        				$.each($('input[name="groupName"]').not(":hidden"),function(i,obj){
	        				if(sample.groupName == $(obj).val()){
	        					$(obj).parents('.groupPanel').find('#mainContent').append($(sampleClone));
	        					$(obj).parents('.groupPanel').find('.testProduct').attr('id',"testProduct"+key);
	        					tt = false;
	        					return false;
	        				}
	        				
	        			});
        				
        				
        				
        				if(tt){
        					groupid=groupid+1;
    						var clone=$(".groupTag").clone();
    						$(clone).addClass("groupPanel");
    						$(clone).removeClass("groupTag");
    						$(clone).attr('id',groupid);
    						$(".tag").before($(clone));
    						$(clone).find('input[name="groupName"]').val(sample.groupName);
    						$(clone).find('#mainContent').append($(sampleClone));
    						$(clone).find('.testProduct').attr('id',"testProduct"+key);
        				}
        					
	        			
        				var hetong=$('#hetong').magicSuggest().getSelection();
        				$.each(hetong,function(index,obj){
        					hetongId = obj.id;
        				});
	        			var url ='';
	        			if(hetongId != ''&& hetongId != undefined){
	        				url = base+'/contract/getContractProducts.do?contractId='+hetongId;
	        			}else{
	        				url = base+"/product/productSelect.do?testingType.id="+searcher.orderType;
	        			}
	        			
	        			 $('#testProduct'+key).magicSuggest({
		        			    width: 190,
		        			    highlight: true,
		        			    data: url,
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
	        			 console.info(sample.orderResearchProduct);
	        			 $('#testProduct'+key).magicSuggest().setSelection(sample.orderResearchProduct); 
	        		
	        		}); 
					layerObject.close(loadindex);
	            },error:function(){
	            	showTip('上传异常','错误提示');
	            	layerObject.close(loadindex);
	            }
	           
	          
	        });
		 	
            $("#myModal").modal('hide');
		}
	}
	
	
}




function addgroup(obj){
	
	i=i+1;
	var clone=$(".clone").clone();
	$(clone).addClass("groupPanel")
	$(clone).attr('data-id',parseInt($(clone).attr('data-id'))+1);
	$(clone).find('a.btn.btn-sm.btn-success.addBtn').remove();
	$(clone).find('.testProduct').attr('id',"testProduct"+i)
	$(".tag").before($(clone).prop("outerHTML"));
	$(clone).find('input').val("");
	
	var hetong=$('#hetong').magicSuggest().getSelection();
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
	

}

function removeGroup(obj){
	$(obj).parent().remove()
}


