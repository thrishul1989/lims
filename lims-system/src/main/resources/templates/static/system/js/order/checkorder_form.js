var freecount = order.freeCount;
var countmoney = order.extraMoney;
var sampleCount = order.orderSubsidiarySample.length , sub_SamplePrice =0, product_price =order.amount;
var sampleOption_new ='';
var checkSampleAmount = 0;
var productRuleArr =[];
var j = 0; //附属样本个数
var map = new Map();
var sample_purpose_select ='';
var sampleResult = true;
var samplePurposeResult = true;
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
			
			

$(document).ready(function(){
	
	sub_SamplePrice = order.subsidiarySampleAmount/100 ; //初始化附属样本钱
	var zhiqingFile=[];
	var fujianFile=[];
	var familyFile=[];
	var hetongId="", ownerId="";

	$("#origin").lSelect({
        url: base+"/order/areaTree.do",
	});
	
	
	$("#examineeName").keyup(function(){
		$("#invoiceTitle").val($(this).val());
	});
	
	laydate({
		  elem: '#birthday',
		  format: 'YYYY-MM-DD', // 分隔符可以任意定义，该例子表示只显示年月
		  festival: true, //显示节日
		  choose: function(datas){ //选择日期完毕的回调
			 $('#age').val(ages(datas));
		  }
	});
	
	
	$("input:radio[name=sendStrategy]").change(function (){ //拨通
		if (this.value == '1') {
			$("#send_info_div").show();
			$("#recipientName").rules("add",{required:true});
			$("#recipientPhone").rules("add",{required:true});
			$("#recipientAddress").rules("add",{required:true});
        }
        else if (this.value == '0') {
        	$("#send_info_div").hide();
        	$("#recipientName").rules("remove");
			$("#recipientPhone").rules("remove");
			$("#recipientAddress").rules("remove");
			$("#recipientName").val("");
			$("#recipientPhone").val("");
			$("#recipientAddress").val("");
        }
	});

    //开票形式
    $("input:radio[name=billingType]").change(function (){
        if (this.value == '1') {
            $("#need_info_div").show();
            $("#head_type_info_div").show();
            $("#electron_info_div").show();
            $("input:radio[name='headerType'][value='1']").prop("checked", true);
            $("#headerType").rules("add",{required:true});
            $("#mailbox").rules("add",{required:true});
            $("#dutyParagraph").rules("add",{required:true});

        }
        else if (this.value == '0') {
            $("#need_info_div").hide();
            $("#headerType").rules("add",{required:true});
            $("input:radio[name='headerType'][value='0']").prop("checked", true);
            $("#mailbox").rules("remove");
            $("#mailbox").val("");
            $("#dutyParagraph").rules("remove");
            $("#dutyParagraph").val("");
        }
        else if(this.value == '2')
        {
            $("#need_info_div").show();
            $("#head_type_info_div").show();
            $("input:radio[name='headerType'][value='1']").prop("checked", true);
            $("#headerType").rules("add",{required:true});
            $("#mailbox").rules("remove");
            $("#mailbox").val("");
            $("#electron_info_div").hide();
            $("#dutyParagraph").rules("add",{required:true});
        }
    });
    //抬头类型
    $("input:radio[name=headerType]").change(function (){
        if (this.value == '1') {
            $("#head_type_info_div").show();
            $("#dutyParagraph").rules("add",{required:true});
        }
        else if (this.value == '0') {
            $("#head_type_info_div").hide();
            $("#dutyParagraph").rules("remove");
            $("#dutyParagraph").val("");
        }
    });

	$("#orderForm").validate({
		
		submitHandler:function(form){


            var prjManagerVal = $('#prjManager').magicSuggest().getSelection()[0];
            if(undefined==prjManagerVal) {
                parent.layer.alert('请选择项目管理人！',{title:"提示"});
                return false;
            }
			
			var samplingAmount = 0;
			if($("#samplingAmount").val()!=null && $("#samplingAmount").val()!=undefined && $("#samplingAmount").val()!='' ){
				samplingAmount = accMul($("#samplingAmount").val(),100);
			}
			
			if(!sampleResult){
				showTip("此产品不支持已入样本的样本类型,请重新选择产品","错误！");
				return false;
			} ;
			if(!samplePurposeResult){  
				showTip("此产品不支持已入样本的样本用途，请重新选择产品","错误！！！"); 
			}
			//客户
			var ownerSelections=$('#ownerId').magicSuggest().getSelection();
			if(ownerSelections.length==0){
				showTip('请选择客户！','提示');
				return false;
			}
			$.each(ownerSelections,function(index,obj){
				ownerId=obj.id;
			})

			//合同
			var hetong=$('#hetong').magicSuggest().getSelection();
			$.each(hetong,function(index,obj){
				hetongId=obj.id;
			})

            //检测产品
            var tp=$('#testProduct').magicSuggest().getSelection();
            if(tp.length==0){
                showTip('请选择检测产品！','提示');
                return false;
            }
      /*      //表型
			var py=$('#phenotype').magicSuggest().getSelection();
			if(py.length==0){
				showTip('请选择表型！','提示');
				return false;
			}*/
			var tpid=[];
			$.each(tp,function(index,obj){
				tpid.push(obj.id);
			})
			var testProducts = tpid.join(",");
			
			//临床表型
			var pt=$('#phenotype').magicSuggest().getSelection();
			var ptid=[];
			$.each(pt,function(index,obj){
				ptid.push(obj.id);
			})
			var phenotypes = ptid.join(",");
			
			//临床诊断
			var ds=$('#disease').magicSuggest().getSelection();
			
			var dsid=[];
			$.each(ds,function(index,obj){
				dsid.push(obj.id);
			})
			var diseases = dsid.join(",");
			
			//关注基因
			var gn=$('#gene').magicSuggest().getSelection();
			var gnid=[];
			$.each(gn,function(index,obj){
				gnid.push(obj.id);
			})
			var genes = gnid.join(",");
			
			//组装主样本数据
			var mainGroup=[];
			var mainGroups=$(".mainGroup");
			var allGroup=[];
			$.each(mainGroups,function(index,obj){
				var main={sampleCode:$(obj).find("input[name='mainSampleCode']").val(),sampleTypeId:$(obj).find('select[name="mainSampleType"]').val(),
						sampleSize:$(obj).find("input[name='mainSampleSize']").val(),samplingDate:$(obj).find("input[name='mainSampleDate']").val(),
						id:$(obj).find("input[name='samplePackageStatus']").val()};
				
				mainGroup.push(main);
				allGroup.push(main.sampleCode);
			})
			
			
			//组装附属样本数据
			var seGroup=[];
			var seGroups=$(".seGroup");
			var checkcount =0;
			$.each(seGroups,function(index,obj){
				var se={sampleCode:$(obj).find("input[name='seSampleCode']").val(),sampleTypeId:$(obj).find('select[name="seSampleType"]').val(),
						sampleSize:$(obj).find("input[name='seSampleSize']").val(),samplingDate:$(obj).find("input[name='seSampleDate']").val(),
						ownerName:$(obj).find("input[name='ownerName']").val(),ownerAge:$(obj).find("input[name='ownerAge']").val(),
						purpose:$(obj).find("select[name='purpose']").val(),ownerPhenotype:$(obj).find("select[name='ownerPhenotype']").val(),
						familyRelation:$(obj).find("select[name='familyRelation']").val(),
						id:$(obj).find("input[name='samplePackageStatus']").val()};
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
			
			if(phenotypes == ''&& diseases == '' && $("#medicalHistory").val() == '' ){
				showTip('临床诊断、表型、病史摘要至少填写一项！','提示');
				return false;
			}
			
			
			 //遮罩开始
			var layerObject = parent.parent.layer;
			var loadindex = layerObject.load();
			//保存
			
			$("#createButton").attr('disabled', 'disabled');
			$("#createButton").val('订单提交中');
			var subprice = sub_SamplePrice+checkSampleAmount;
			$.ajax({
	            type: "POST",
	            url: base+"/order/create.do",
	            data: {id:order.id,
	            	orderType:order.orderType,
	            	code:$("#orderCode").val(),
	            	contractId:hetongId,
	            	ownerId:ownerId,
	            	creatorName:$("#creatorName").val(),
	            	creatorId:$("#creatorId").val(),
	            	amount:$("#productsPrice_new").val(),
	            	//subsidiarySampleAmount:accMul(sub_SamplePrice,100),
	            	subsidiarySampleAmount:accMul(subprice,100),
	            	doctorAssist:$('input[name="doctorAssist"]:checked').val(),
	            	orderProduct:testProducts,
	            	name:$("#examineeName").val(),sex:$("input[name='examineeSex']:checked").val(),
	            	birthday:$("#birthday").val(),ageSnapshot:$("#age").val(),
	            	contactName:$("#contactName").val(),contactPhone:$("#contactPhone").val(),
	            	contactEmail:$("#contactEmail").val(),
	            	nation:$("#nation").val(),origin:$("#origin").val(),
	            	recordNo:$("#recordNo").val(),orderExamineePhenotype:phenotypes,
	            	orderExamineeDiagnosis:diseases,orderExamineeGene:genes,
	            	medicalHistory:$("#medicalHistory").val(),
	            	familyMedicalHistory:$("#familyMedicalHistory").val(),
	            	clinicalRecommend:$("#clinicalRecommend").val(),
	            	consentFigures:zhiqingFile.join(","),
	            	recordFigures:fujianFile.join(","),
	            	familyFigures:familyFile.join(","),
	            	recipientName:$("#recipientName").val(),recipientPhone:$("#recipientPhone").val(),
	            	recipientAddress:$("#recipientAddress").val(),
	            	invoiceTitle:$("#invoiceTitle").val(),
	            	samplingAmount:samplingAmount,
					projectManager:$("#projectManager").val(),
                    billingType:$('input[name="billingType"]:checked').val(),
                    headerType:$('input[name="headerType"]:checked').val(),
                    dutyParagraph:$("#dutyParagraph").val(),
                    belongArea:$("#belongArea").val(),
                    mailbox:$("#mailbox").val(),
	            	parmarySample:JSON.stringify(mainGroup),subsidiarySample:JSON.stringify(seGroup)},
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
        		rangelength:[11,11],
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
         		},
         		alnum:true
        	},
        	doctorAssist: {
        		required:true
        	},
        	
        	examineeName: {
        		required:true,
        		maxlength: 10
        	},
            invoiceTitle: {
                required:true,
                maxlength: 200
            },
        	examineeSex: {
        		required:true,
        		
        	},
            headerType: {
                required:true,
            },
            mailbox: {
                required:true,
            },
        	nation: {
        		required:true,
        	},
        	origin: {
        		required:true,
        	},
        	contactName: {
        		required:true,
        	},
        	creatorName:{
        		required:true,
        	},
        	mainSampleCode:{
        		required:true,
        		rangelength:[9,9],
        		alnum:true,
        		isMainSampleCode:true
        	},
        	mainSampleType:{
        		required:true,
        	},
        	mainSampleSize:{
        		required:true,
        		number:true,
        		min:0.01
        	},
        	mainSampleDate:{
        		required:true,
        	},
        	seSampleCode:{
        		required:true,
        		rangelength:[9,9],
        		alnum:true
        	},
        	seSampleSize:{
        		required:true,
        		number:true,
        		min:0.01
        	},
        	recipientName:{
        		required:true,	
        	},
        	recipientPhone:{
        		required:true,
        		isTel:true
        	},
        	contactPhone:{
        		required:true,
        		isTel:true
        	},
        	recipientAddress:{
        		required:true,
        	},
        	ownerAge:{
        		required:true,
        		digits:true,
        		min:0
        	},
        	seSampleType:{
        		required:true
        	},
        	seSampleDate:{
        		required:true
        	},
        	ownerName:{
        		required:true
        	},
        	purpose:{
        		required:true
        	},
        	familyRelation:{
        		required:true	
        	},
        	ownerPhenotype:{
        		required:true
        	},
        	samplingAmount:{
        		number:true,
        		min:0.01,
        		lrunlv:true
        	}
           
        },
        messages: {
        	orderCode:  {
                required: "请输入订单编号",
                rangelength:"订单编号长度不对,应该为11位",
            	remote:"订单编号已经存在"
            },
            doctorAssist:  {
            	required: "请选择参与方式"
            },
            creatorName:{
            	required: "该客户未绑定业务员，请先绑定业务员"
            },
            mainSampleSize:{
            	digits:"请输入整数"
            },
            seSampleSize:{
            	digits:"请输入整数"
            },
            examineeName: {
            	required: "请填写受检人姓名"
        	},
            headerType:{
                required: "请选择发票抬头类型"
            },
            invoiceTitle: {
                required: "请填写发票抬头"
            },
            dutyParagraph:{
                required: "请填写企业税号"
            },
            mailbox:{
                required: "请填写邮箱"
            },
	    	mainSampleCode:{
	         	required: "请输入样本编号",
	         	rangelength:"样本编号长度不对,应该为9位",
	        },
	        seSampleCode:{
	         	required: "请输入附属样本编号",
	         	rangelength:"样本编号长度不对,应该为9位",
	        }
        	
        }
    });
	
	
	
	var recordFiguresInit = order.orderExamineeList[0].recordFiguresShow;
	var fujianConfig=[];
	if(recordFiguresInit!=undefined){
		if(recordFiguresInit.indexOf(",")!=0){
			var initPath = recordFiguresInit.split(",");
			var truePath = order.orderExamineeList[0].recordFigures.split(",");
			var picInit =[];
			for(var i=0;i<initPath.length;i++){
				if("" != initPath[i]){
					
					if(initPath[i].indexOf("doc")>0 || initPath[i].indexOf("txt")>0 || initPath[i].indexOf("xlsx")>0){ 
						picInit.push ("<div class='file-preview-other-frame'><div class='file-preview-other'><span class='file-icon-4x'><i class='fa fa-file-text-o text-info'></i></span></div></div><a style='display:flex' href='"+base+"/order/getSrcByPath.do?fileName="+truePath[i]+"'>下载</a>")
					}
					else{
						picInit.push ("<img src='"+initPath[i]+"' class='file-preview-image' style='max-width:700px;height:150px;'  /><a style='display:flex' href='"+base+"/order/getSrcByPath.do?fileName="+truePath[i]+"'>下载</a>");		
					}
					
					fujianConfig.push({caption:truePath[i],width:'120px',key:truePath[i]});
					fujianFile.push(truePath[i])
				}
				
			}
			
		}
		
	}
	
	var consentFiguresInit = order.orderExamineeList[0].consentFiguresShow;
	var zhiqingConfig=[];

	if(consentFiguresInit!=undefined){
		if(consentFiguresInit.indexOf(",")!=0){
			var initConsentPath = consentFiguresInit.split(",");
			var trueConsentPath=order.orderExamineeList[0].consentFigures.split(",")
			var picInit2 =[];
			for(var i=0;i<initConsentPath.length;i++){
				if("" != initConsentPath[i]){
					
					console.info(initConsentPath[i]);
					picInit2.push("<img  src='"+initConsentPath[i]+"' class='file-preview-image' style='max-width:700px;height:200px;'/><a style='display:flex' href='"+base+"/order/getSrcByPath.do?fileName="+trueConsentPath[i]+"'>下载</a>")
					zhiqingConfig.push({caption:trueConsentPath[i],width:'120px',key:trueConsentPath[i]});
					zhiqingFile.push(trueConsentPath[i]);
				}
				
			}
			
			picInit2.join(",")
		}
	}
	
	
	var familyFiguresInit = order.orderExamineeList[0].familyFiguresShow;
	var familyConfig=[];

	if(familyFiguresInit!=undefined){
		if(familyFiguresInit.indexOf(",")!=0){
			var initConsentPath = familyFiguresInit.split(",");
			var trueConsentPath=order.orderExamineeList[0].familyFigures.split(",")
			var picInit3 =[];
			for(var i=0;i<initConsentPath.length;i++){
				if("" != initConsentPath[i]){
					picInit3.push("<img  src='"+initConsentPath[i]+"' class='file-preview-image' style='max-width:700px;height:200px;'/><a style='display:flex' href='"+base+"/order/getSrcByPath.do?fileName="+trueConsentPath[i]+"'>下载</a>")
					familyConfig.push({caption:trueConsentPath[i],width:'120px',key:trueConsentPath[i]});
					familyFile.push(trueConsentPath[i]);
				}
				
			}
			
			picInit3.join(",")
		}
	}

	
	$("#familyFigures").fileinput({
		language: 'zh', // 设置语言
        uploadUrl: base+'/order/upload.do', 
        allowedFileExtensions : [],
        overwriteInitial: false,
        maxFileSize: 5000,
        showUpload: true, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        maxFileCount: 9,
        validateInitialCount:true,
        showRemove:false,
        deleteUrl:base+'/order/deletePic.do',
        initialPreview:picInit3,
        initialPreviewConfig:familyConfig,
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
	}).on("fileuploaded", function(event, data) {
    	if(data.response.fileList.length==1){
    		familyFile.push(data.response.fileList[0])
    	}
    	else{
    		$.each(data.response.fileList,function(index,obj){
    			familyFile.push(obj)
    		})
    	}
    }).on('filedeleted', function(event, key) {  
    	familyFile.remove(key);
    });
	
	
	$('#zhiqing').fileinput({
        language: 'zh', // 设置语言
        uploadUrl: base+'/order/upload.do', // 上传的地址
        allowedFileExtensions : ['jpg', 'png','gif'],// 接收的文件后缀
        maxFileSize: 5000,
        showUpload: true, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        maxFileCount: 5,
        validateInitialCount:true,
        showRemove:false,
        overwriteInitial: false,
        uploadAsync: true,
        deleteUrl:base+'/order/deletePic.do',
        initialPreview: picInit2,
        initialPreviewConfig:zhiqingConfig,
    }).on("fileuploaded", function(event, data) {
    	if(data.response.fileList.length==1){
    		zhiqingFile.push(data.response.fileList[0])
    	}
    	else{
    		$.each(data.response.fileList,function(index,obj){
    			zhiqingFile.push(obj)
    			
    		})
    	}
    }).on('filedeleted', function(event, key) {  
    	zhiqingFile.remove(key);
    }); 
	
	
	$("#file-1").fileinput({
		language: 'zh', // 设置语言
        uploadUrl: base+'/order/upload.do', // you must set a valid URL here else you will get an
        allowedFileExtensions : [],
        overwriteInitial: false,
        maxFileSize: 5000,
        showUpload: true, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        maxFileCount: 9,
        validateInitialCount:true,
        showRemove:false,
        deleteUrl:base+'/order/deletePic.do',
        initialPreview:picInit,
        initialPreviewConfig:fujianConfig,
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
	}).on("fileuploaded", function(event, data) {
    	if(data.response.fileList.length==1){
    		fujianFile.push(data.response.fileList[0])
    	}
    	else{
    		$.each(data.response.fileList,function(index,obj){
    			fujianFile.push(obj)
    		})
    	}
    }).on('filedeleted', function(event, key) {  
    	fujianFile.remove(key);
    });
   
	if(order.sheduleStatus >= 2){
		$('.btn.btn-primary.btn-file').css('display','none');
	}	
	
   
	
	

	var s=	$('#hetong').magicSuggest({ //初始化合同
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
	var contractId ='';
	if( hetongse!=undefined && hetongse!="" ){
	 $('#hetong').magicSuggest().setSelection(hetongse);
	 contractId = hetongse.id;
	$.post(base+"/contract/getContractProducts.do", { contractId: contractId },
		       function(data){
		$("#testProduct").magicSuggest().setData(data);
	});
		
	}

	var ownerValue = order.owner;
	if( ownerValue!=undefined && ownerValue!="" ){
		$('#ownerId').magicSuggest().setSelection(ownerValue);
	}

	$(s).on('selectionchange', function(e,m){
		var hetongObj=$('#hetong').magicSuggest().getSelection();
	
		if(hetongObj != undefined && hetongObj.length >0){
			$("#testProduct").magicSuggest().clear();
			$("#ownerId").magicSuggest().collapse();
			$("#testProduct").magicSuggest().collapse();
			
			var hid = hetongObj[0].id;
			$.post(base+"/contract/getContractUsers.do", { contractId: hid,disableStatus:0 },
			       function(data){
			     $('#ownerId').magicSuggest().setData(data);
			 });

			//根据合同设置产品
			$.post(base+"/contract/getContractProducts.do", { contractId: hid },
				       function(data){
				$("#testProduct").magicSuggest().setData(data);
			});
			
		}else{
			$.post(base+"/customer/getSelectListByConstract.do", {testingType:order.orderType,disableStatus:0},
				       function(data){
				 $('#ownerId').magicSuggest().setData(data);
			});
			//初始化产品数据
			$("#testProduct").magicSuggest().setData(base+'/product/productSelect.do?testingType.id='+order.orderType);
		}
		
	});
	
	$(owner).on('selectionchange', function(e,m){
		var ownObj=$('#ownerId').magicSuggest().getSelection();
		if(ownObj != undefined && ownObj.length >0){
			var sid = ownObj[0].id;
			$("input:radio[name='doctorAssist'][value='" + ownObj[0].analyseType + "']").prop("checked", "checked");
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
			$.post(base+"/contract/getContractByUserId.do", { userId: sid , marketingCenter:order.orderType },function(data){
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
 	
 	

	
	//初始化已有数据 ----默认初始化 也执行一次
	//检测产品
	var mainmap=[];
	var orderProcut=order.orderProductList
	var products=[];
	var mainsample=[];
	$.each(orderProcut,function(index,obj){
		products.push(obj.product);
		
	})
	
	$('#testProduct').magicSuggest().setSelection(products);
	//初始化样本
	productRuleArr =[];
	var checkcount = 0; //重置个数
	checkSampleAmount = 0; //全局重置检测样本金额
	//计算检测样本个数
	$.each($("[id^=purpose]"),function(i,obt){
		if($(this).val()==2){
		 checkcount = checkcount+1;;
		}
	});
	
	$.each(products,function(index,obj){
		
		var ruleListArr = obj.productAmountRuleList;
		if(ruleListArr!=undefined && ruleListArr.length>0){
			var rulemap = new Map();
			ruleListArr.forEach(function(ob){
				rulemap.put(ob.count,ob.amount);
			});
			productRuleArr.push(rulemap);
		}
		
		$.each(obj.productSampleList,function(index,ob){
			var id= ob.sample.id;
			var name = ob.sample.name;
			var unit = ob.sample.receivingUnit;
			if(mainmap.indexOf(id)<0){
				sampleOption_new=sampleOption_new+'<option value="'+id+'"  unit='+unit+'>'+name+'</option>'
				mainmap.push(id);
			}
		});
		
		$.each(obj.samplePurpose.split(','),function(index,ob){
			if(mainsample.indexOf(ob)<0){
				sample_purpose_select = sample_purpose_select+'<option value="'+ob+'" >'+purposeEntry[ob]+'</option>';
				mainsample.push(ob);
			}
		});
		
		
		
	});

	
	
	
	
	//初始化money
	$(testProduct).on('selectionchange', function(e,m){
		 //计算产品价格
		var productsPrice=$('#testProduct').magicSuggest().getSelection();
		var relprice=0,product_price = 0 ;
		

		if(productsPrice.length >0){
			
			productRuleArr =[]; //价格变动又需要重新计算规则
			var checkcount = 0; //重置个数
			checkSampleAmount = 0; //全局重置检测样本金额
			var notfreecount = 0;
			sub_SamplePrice = 0; //同时要从新计算非检测及暂存样本金额
			sample_purpose_select ='';
			mainsample.splice(0,mainsample.length);  
			
			//如果去除产品--校验样本用途是否还在
			var insampemap=[];
			
			var samplePurposeResult = true;
			var  existsPurposeArr = [];
			$.each($("[id^=purpose]"),function(i,obt){
				if(existsPurposeArr.indexOf($(this).val())<0){
					existsPurposeArr.push($(this).val());
				}
			});
			$.each($("[id^=purpose]"),function(i,obt){
				if($(this).val()==2){
				 checkcount = checkcount+1;;
				}
			});
			$.each($("[id^=purpose]"),function(i,obt){
				if( $(this).val()== 1 || $(this).val() == 3){
					notfreecount =notfreecount+1;
				}
			});
			
			
			if(notfreecount >1){
				sub_SamplePrice = (notfreecount-freecount)*countmoney; //重新计算附属样本钱
			}
			
			$.each(productsPrice,function(index,obj){
				
				var ruleListArr = obj.productAmountRuleList;
				if(ruleListArr!=undefined && ruleListArr.length>0){
					var rulemap = new Map();
					ruleListArr.forEach(function(ob){
						rulemap.put(ob.count,ob.amount);
					});
					productRuleArr.push(rulemap);
				}
				
				relprice=accAdd(relprice,obj.realPrice);
				product_price = accAdd(product_price,obj.price);
				
				
				sampleOption_new ='';
				mainmap.splice(0,mainmap.length);
				$.each($("[name^=mainSampleType]"),function(i,obt){
					if(insampemap.indexOf($(obt).val())<0){
						insampemap.push($(obt).val());
					}
				});
				$.each($("[name^=seSampleType]"),function(i,obt){
					if(insampemap.indexOf($(obt).val())<0){
						insampemap.push($(obt).val());
					}
				});
				
				$.each(obj.productSampleList,function(index,ob){
					var id= ob.sample.id;
					var name = ob.sample.name;
					var unit = ob.sample.receivingUnit;
					if(mainmap.indexOf(id)<0){
						sampleOption_new=sampleOption_new+'<option value="'+id+'" unit='+unit+' >'+name+'</option>';
						mainmap.push(id);
					}
				});
				
				$.each(insampemap,function(index,ob){
					var ii = $.inArray(ob, mainmap);  //返回 -1 即错误,
					if(ii == -1){
						sampleResult = false;
						return false;
					}
				});
				
			
				if(!sampleResult){  //sample数组元素在product组里面
					showTip("此产品不支持已入样本的样本类型，请重新选择产品","错误！"); 
				}
				
				
				
				$.each(obj.samplePurpose.split(','),function(index,ob){
					if(mainsample.indexOf(ob)<0){
						sample_purpose_select = sample_purpose_select+'<option value="'+ob+'" >'+purposeEntry[ob]+'</option>';
						mainsample.push(ob);
					}
				});
				
				$.each(existsPurposeArr,function(index,ob){
					if(ob != ""){
						var ii = $.inArray(ob, mainsample);  //返回 -1 即错误,
						if(ii == -1){
							samplePurposeResult = false;
							return false;
						}
					}
					
				});
				if(!samplePurposeResult){  
					showTip("此产品不支持已入样本的样本用途，请重新选择产品","错误！"); 
				}
				
				
				$.each($("[id^=purpose]").not("[disabled]"),function(i,obt){
					var oldvalue = $(this).val(); //记忆之前选中值
					$(obt)[0].options.length=1;
					$(obt).append(sample_purpose_select);
					$(this).val(oldvalue)
				});
				
				
			});
		}else{
			/*$.each($("[name^=mainSampleType]"),function(i,obt){
				$(obt)[0].options.length=1;
			});
			$.each($("[name^=seSampleType]"),function(i,obt){
				$(obt)[0].options.length=1;
			});*/
		}
		if(checkcount>0){
			checkSampleAmount =  caclAmount(checkcount);
		}
		
		$("#productsPrice").html(accAdd(relprice,sub_SamplePrice)+checkSampleAmount);
		$("#productsPrice_new").val(product_price);
	});
	
	
	var disease=$('#disease').magicSuggest({
	    width: 190,
	    highlight: true,
        data:base+'/disease/diseaseSelect.do',
	    method:'get',
	    queryParam:"name",
	    allowFreeEntries:false,
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	            '<div  class="probe">' + v.name + '</div>' +
	        '<div style="clear:both;"></div>';
	    }
	});
	
	var phenotype=	$('#phenotype').magicSuggest({
	    width: 190,
	    highlight: true,
        data:base+'/phenotype/getPhenotypeSelected.do',
	    method:'get',
	    queryParam:"name",
	    allowFreeEntries:false,
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	        
	            '<div  class="probe">' + v.name + '</div>' +
	            
	        '<div style="clear:both;"></div>';
	    }
	});
	
	var gene= $('#gene').magicSuggest({
	    width: 190,
	    highlight: true,
        data:base+'/disease/geneSelect.do',
	    method:'get',
	    queryParam:"symbol",
	    displayField:'symbol',
	    allowFreeEntries:false,
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	        
	            '<div  class="probe">' + v.symbol + '</div>' +
	           
	        '<div style="clear:both;"></div>';
	    }
	});
	

	

	 
	
	
	//初始化临床诊断（disease）
	var examineeDiseaselist=order.orderExamineeList[0].orderExamineeDiagnosis;
	var examineeDiseases=[];
	$.each(examineeDiseaselist,function(index,obj){
		examineeDiseases.push(obj.disease)
	})
	 $('#disease').magicSuggest().setSelection(examineeDiseases);

	//初始化临床表型
	var examineePhenotypeList=order.orderExamineeList[0].orderExamineePhenotype;
	var examineePhenotypes=[];
	$.each(examineePhenotypeList,function(index,obj){
		examineePhenotypes.push(obj.phenotype)
	})
	 $('#phenotype').magicSuggest().setSelection(examineePhenotypes);

	//初始化关注基因
	var geneList=order.orderExamineeList[0].orderExamineeGene;
	var examineeGenes=[];
	$.each(geneList,function(index,obj){
		examineeGenes.push(obj.gene)
	})
	 $('#gene').magicSuggest().setSelection(examineeGenes);

	//初始化主样本
	//样本类型
	var mainsampleop="";
	var products=$('#testProduct').magicSuggest().getSelection();
	$.each(order.orderPrimarySample,function(index,obj){

        if(obj.sampleSize ==undefined)
        {
            obj.sampleSize ="";
        }
        if(obj.samplingDate ==undefined)
        {
            obj.samplingDate ="";
        }
        if(obj.samplereceivingUnit ==undefined)
        {
            obj.samplereceivingUnit ="*";
        }
		$.each(products,function(index,ob){
			
				$.each(ob.productSampleList,function(index,o){

					var id= o.sample.id;
					var name = o.sample.name;
					var unit = o.sample.receivingUnit;
					if(id==obj.sampleTypeId){
						mainsampleop=mainsampleop+'<option value="'+id+'" selected="selected" unit='+unit+'>'+name+'</option>'
					}else{
						mainsampleop=mainsampleop+'<option value="'+id+'"  unit='+unit+'>'+name+'</option>'
					}
					
				});
						
		});
		$('#mainContent').append('<div style="margin-top:17px" class="mainGroup">'+

				'<input type="hidden" name="samplePackageStatus"'+
				' value="'+obj.id+'" />'+


				'<div class="form-group">'+
	'<label class="col-sm-2 control-label control-required "><span>*</span>样本编号：</label>'+
					'<div class="col-sm-3">'+
						'<input type="text" class="form-control" name="mainSampleCode" readonly="readonly"  '+
							' value="'+obj.sampleCode+'" />'+
					'</div>'+
	'<label class="col-sm-2 control-label control-required"><span>*</span>样本类型：</label>'+
					'<div class="col-sm-3">'+
						'<select class="form-control" name="mainSampleType" disabled="disabled"   '+
							' >'+
							'<option value="">请选择样本类型</option>'+mainsampleop+
						'</select>'+
					'</div></div><div class="form-group">'+
	'<label class="col-sm-2 control-label control-required"><span>*</span>样本量('+obj.samplereceivingUnit+')：</label>'+
					'<div class="col-sm-3">'+
						'<input type="text" class="form-control" name="mainSampleSize" readonly="readonly"  '+
							'value="'+obj.sampleSize+'" />'+
					'</div>'+
	'<label class="col-sm-2 control-label control-required"><span>*</span>采样时间：</label>'+
					'<div class="col-sm-3">'+
						'<input type="text" class="form-control laydate-icon"'+
							'name="mainSampleDate"  value="'+ obj.samplingDate+'"'+
							'disabled="disabled" style="height: 34px"'+
							'onclick="laydate({istime: true, format: \'YYYY-MM-DD\',max: laydate.now()})" />'+
					'</div>'+
	'</div><DIV style="BORDER-TOP: #adadad  1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV></div>');
		
		mainsampleop ='';
	});
	
	//初始化附属样本
	//样本类型
		var sesampleop="";
		
		$.each(order.orderSubsidiarySample,function(index,obj){
			j = j+1; 
			map.put(j,obj.purpose); //初始化数组
			
			var products=$('#testProduct').magicSuggest().getSelection();
			
			if(products.length>0){
				
				$.each(products,function(index,ob){
					
					$.each(ob.productSampleList,function(index,o){
						var id= o.sample.id;
                        var name = o.sample.name;
                        var unit = o.sample.receivingUnit;
						if(id==obj.sampleTypeId){
							sesampleop=sesampleop+'<option value="'+id+'" selected="selected" unit='+unit+'>'+name+'</option>'
						}else{
							sesampleop=sesampleop+'<option value="'+id+'"  unit='+unit+'>'+name+'</option>'
						}
						
					});
				
					
				});
				
				
				
			}
			
			
			//判断用途
			if(obj.purpose == 1){
				var purposeOption='<option value="1" selected="selected">一代验证</option><option value="2">检测</option><option value="3">本人对照</option><option value="4">暂存</option>'
				
			}else if(obj.purpose == 2){
				var purposeOption='<option value="1" >一代验证</option><option value="2" selected="selected">检测</option><option value="3">本人对照</option><option value="4">暂存</option>'
			}
			else if(obj.purpose == 3){
				var purposeOption='<option value="1" >一代验证</option><option value="2" >检测</option><option value="3" selected="selected">本人对照</option><option value="4">暂存</option>'
			}else{
				var purposeOption='<option value="1" >一代验证</option><option value="2" >检测</option><option value="3">本人对照</option><option value="4" selected="selected">暂存</option>'
			}
			
			if(obj.ownerPhenotype == 0){
				var ownPhenotypeOption='<option value="0" selected="selected">正常</option><option value="1">确诊</option><option value="2">与受检人相同</option><option value="3" >未知</option>'
				
			}else if(obj.ownerPhenotype == 1){
				var ownPhenotypeOption='<option value="0" >正常</option><option value="1" selected="selected">确诊</option><option value="2">与受检人相同</option><option value="3" >未知</option>'
			}else  if(obj.ownerPhenotype == 2){
				var ownPhenotypeOption='<option value="0" >正常</option><option value="1" >确诊</option><option value="2" selected="selected">与受检人相同</option><option value="3" >未知</option>'
			}else{
				var ownPhenotypeOption='<option value="0" >正常</option><option value="1" >确诊</option><option value="2">与受检人相同</option><option value="3" selected="selected">未知</option>'
			}
			
			var disableHtml = "",readonlyHtml ="";
			if(order.sheduleStatus >= 2){
				disableHtml +=' disabled = "disabled" ';
				readonlyHtml +=' readonly="readonly" ';
			}

			if(obj.sampleCode ==undefined)
            {
                obj.sampleCode ="";
            }
            if(obj.sampleSize ==undefined)
            {
                obj.sampleSize ="";
            }
            if(obj.samplingDate ==undefined)
            {
                obj.samplingDate ="";
            }
            if(obj.samplereceivingUnit ==undefined)
			{
                obj.samplereceivingUnit ="*";
			}
            if(obj.ownerName ==undefined)
            {
                obj.ownerName ="";
            }
			$('#seContent').append('<div style="margin-top:17px" class="seGroup"> '+
					'<input type="hidden" name="samplePackageStatus" value="'+obj.id+'" /> '
					+'<div class="form-group">'+
					'<label class="col-sm-2 control-label control-required"><span>*</span>样本编号：</label>'+
					'<div class="col-sm-3">'+
						'<input type="text" class="form-control"'+
							'name="seSampleCode" id="seSampleCode'+j+'" readonly="readonly" value="'+obj.sampleCode+'"  /></div>'+

					'<label class="col-sm-2 control-label control-required"><span>*</span>样本类型：</label>'+
					'<div class="col-sm-3">'+
						'<select class="form-control" name="seSampleType"  disabled="disabled" >'+
							'<option value="">请选择样本类型</option>'+sesampleop+
						'</select>'+
					'</div>'+

				'</div>'+

				'<div class="form-group">'+


					'<label class="col-sm-2 control-label control-required"><span>*</span>样本量('+obj.samplereceivingUnit+')：</label>'+
					'<div class="col-sm-3">'+
						'<input type="text" class="form-control"'+
							'name="seSampleSize" readonly="readonly" value="'+obj.sampleSize+'" />'+
					'</div>'+

					'<label class="col-sm-2 control-label control-required"><span>*</span>采样时间：</label>'+
					'<div class="col-sm-3">'+
						'<input type="text" class="form-control laydate-icon"'+
							'name="seSampleDate" disabled="disabled" value="'+ obj.samplingDate+'" readonly="readonly"'+
							'style="height: 34px"'+
							'onclick="laydate({istime: true, format: \'YYYY-MM-DD\',max: laydate.now()})" />'+
					'</div>'+

				'</div>'+

				'<div class="form-group">'+


					'<label class="col-sm-2 control-label control-required"><span>*</span>年龄：</label>'+
					'<div class="col-sm-3">'+
						'<input type="text" class="form-control" name="ownerAge"'+
							'   value="'+obj.ownerAge+'" "'+readonlyHtml+'" />'+
					'</div>'+

					'<label class="col-sm-2 control-label control-required"><span>*</span>姓名：</label>'+
					'<div class="col-sm-3">'+
						'<input type="text" class="form-control" name="ownerName"'+
							'value="'+obj.ownerName+'"  "'+readonlyHtml+'"   />'+
					'</div>'+

				'</div>'+

				'<div class="form-group">'+

					'<label class="col-sm-2 control-label control-required"><span>*</span>用途：</label>'+
					'<div class="col-sm-3">'+
						'<select class="form-control" name="purpose" ids="'+j+'" id="purpose'+j+'" disabled="disabled" '+
							'>'+
							'<option value="">请选择用途</option>'+
								purposeOption+
						'</select>'+
					'</div>'+
					'<span class="phenotype" > <label class="col-sm-2 control-label control-required"><span>*</span>家属症状：</label>'+
						'<div class="col-sm-3">'+
							'<select class="form-control" name="ownerPhenotype" "'+disableHtml+'"  >  '+
								'<option value="">请选择家属症状</option>'+
									ownPhenotypeOption+
							'</select>'+
						'</div>'+

					'</span>'+


				'</div>'+

				'<div class="form-group">'+
				'<label class="col-sm-2 control-label control-required"><span>*</span>家系关系：</label>'+
				'<div class="col-sm-3">'+
					
				$("#"+obj.id).prop("outerHTML")+
				'</div>'+
					'</div>'+
				'<DIV '+
					'style="BORDER-TOP: #adadad 1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV>'+
			'</div>');
			sesampleop='';
			
		})
		
		
	
})



function showUnit(obj){
	
	var unit=$(obj).find('option:selected').attr('unit');
	if(typeof(unit) != 'undefined' && null != unit && "" != unit){
		/*$(obj).closest('.mainGroup').find('input[name="mainSampleSize"]').parent().prev().html(
				"<span>*</span>样本量("+unit+")"		
		);*/
		
		$.post(base+"/sample/getUnitName.do", { id: unit }, function(data){
			
			$(obj).closest('.mainGroup').find('input[name="mainSampleSize"]').parent().prev().html(
					"<span>*</span>样本量("+data.name+")"		
			);
			
		      
		 });
		
	}
	else{
		$(obj).closest('.mainGroup').find('input[name="mainSampleSize"]').parent().prev().html(
				"<span>*</span>样本量"		
		);
	}
}


function showSeUnit(obj){
	
	var unit=$(obj).find('option:selected').attr('unit');
	if(typeof(unit) != 'undefined' && null != unit && "" != unit){
		/*$(obj).closest('.seGroup').find('input[name="seSampleSize"]').parent().prev().html(
				"<span>*</span>样本量("+unit+")"		
		);*/
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
	
	var examineeName = $("#examineeName").val();
	if(examineeName){
		var index = -1;
		var relationName=$(obj).find('option:selected')[0].innerHTML;
		var relationId = $(obj).find('option:selected').val();
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
		showTip("请先填写受检人姓名","提示");
		 $("#examineeName").focus();
		 $(obj).val('');
	}
	
}


function removeMain(obj){
	$(obj).parent().remove();
}

function addMain(){
	$('#mainContent').append('<div style="margin-top:17px" class="mainGroup">'+
			'<img src="'+system_images+'/rubish.png" style="float: right;cursor: pointer;" onclick="removeMain(this)"/>'+
			'<div class="form-group">'+
'<label class="col-sm-2 control-label control-required"><span>*</span>样本编号：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" name="mainSampleCode"    '+
						 'value="" />'+
				'</div>'+
'<label class="col-sm-2 control-label control-required"><span>*</span>样本类型：</label>'+
				'<div class="col-sm-3">'+
					'<select class="form-control" name="mainSampleType"'+
						' onchange="showUnit(this)">'+
						'<option value="">请选择样本类型</option>'+sampleOption_new+
					'</select>'+
				'</div></div><div class="form-group">'+
'<label class="col-sm-2 control-label control-required"><span>*</span>样本量：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" name="mainSampleSize"    '+
						' value="" />'+
				'</div>'+
'<label class="col-sm-2 control-label control-required"><span>*</span>采样时间：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control laydate-icon"'+
						'name="mainSampleDate"  value=""'+
						'readonly="readonly" style="height: 34px"'+
						'onclick="laydate({istime: true, format: \'YYYY-MM-DD\',max: laydate.now()})" />'+
				'</div>'+
'</div><DIV style="BORDER-TOP: #adadad  1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV></div>');
	
}

function addSe(){
	j =j+1;
	$('#seContent').append('<div style="margin-top:17px" class="seGroup"><img src="'
			+system_images+'/rubish.png" style="float: right;cursor: pointer;" onclick="removeSeSample(this)"/><div class="form-group">'+
			'<label class="col-sm-2 control-label control-required"><span>*</span>样本编号：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" id="seSampleCode'+j+'" class="form-control"'+
					'name="seSampleCode" value="" /></div>'+

			'<label class="col-sm-2 control-label control-required"><span>*</span>样本类型：</label>'+
			'<div class="col-sm-3">'+
				'<select class="form-control" name="seSampleType" onchange="showSeUnit(this)" >'+
					'<option value="">请选择样本类型</option>'+sampleOption_new+
				'</select>'+
			'</div>'+

		'</div>'+

		'<div class="form-group">'+


			'<label class="col-sm-2 control-label control-required"><span>*</span>样本量：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" class="form-control"'+
					'name="seSampleSize" value="" />'+
			'</div>'+

			'<label class="col-sm-2 control-label control-required"><span>*</span>采样时间：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" class="form-control laydate-icon"'+
					'name="seSampleDate" value="" readonly="readonly"'+
					'style="height: 34px"'+
					'onclick="laydate({istime: true, format: \'YYYY-MM-DD\',max: laydate.now()})" />'+
			'</div>'+

		'</div>'+

		'<div class="form-group">'+


			'<label class="col-sm-2 control-label control-required"><span>*</span>年龄：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" class="form-control" name="ownerAge"'+
					'value="" />'+
			'</div>'+

			'<label class="col-sm-2 control-label control-required"><span>*</span>姓名：</label>'+
			'<div class="col-sm-3">'+
				'<input type="text" class="form-control" name="ownerName"'+
					'value="" />'+
			'</div>'+

		'</div>'+

		'<div class="form-group">'+

			'<label class="col-sm-2 control-label control-required"><span>*</span>用途：</label>'+
			'<div class="col-sm-3">'+
			//$("#purposeClone").clone().removeAttr("id").attr("id","purpose"+j).attr("ids",j).prop("outerHTML")+
			'<select class="form-control" name="purpose" id="purpose'+j+'" ids="ids'+j+'" >'+
			'<option value="">请选择</option>'+sample_purpose_select+
			'</select>'+
			'</div>'+
			'<span class="phenotype" > <label class="col-sm-2 control-label control-required"><span>*</span>家属症状：</label>'+
				'<div class="col-sm-3">'+
					'<select class="form-control" name="ownerPhenotype">'+
						'<option value="">请选择家属症状</option>'+
						'<option value="0">正常</option>'+
						'<option value="1">确诊</option>'+
						'<option value="2">与受检人相同</option>'+
						'<option value="3">未知</option>'+
					'</select>'+
				'</div>'+

			'</span>'+


		'</div>'+

		'<div class="form-group">'+
		'<label class="col-sm-2 control-label control-required"><span>*</span>家系关系：</label>'+
		'<div class="col-sm-3">'+
			
		$("#familyRelation").prop("outerHTML")+
		'</div>'+
		'</div>'+

		'<DIV '+
			'style="BORDER-TOP: #adadad 1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV>'+
	'</div>');
	
	sampleCount = sampleCount+1;//附属样本自增;
	cacl_addprice();
	

}



function cacl_addprice(){
	
	$("#purpose"+j).on("change",function(){
		
		map.put($(this).attr("ids"),$(this).val());
		
		var price = $("#productsPrice_new").val()/100;  //初始产品价格  /100用于显示
		var notfreecount = 0;
		var checkcount = 0;
		checkSampleAmount = 0;
		map.each(function(value){
			if( (map.get(value)== 1 || map.get(value) == 3)){
				notfreecount =notfreecount+1;
			}
		})
		
		//检测样本数量
		map.each(function(value){
			if( (map.get(value)==2)){
				checkcount = checkcount+1;
			}
		})
		
		if(checkcount>0){
			checkSampleAmount = caclAmount(checkcount);
		}
		
		if(notfreecount >1){
			price  = price+ (notfreecount-freecount)*countmoney+checkSampleAmount;
			sub_SamplePrice = (notfreecount-freecount)*countmoney; //附属样本钱
		}else{
			price  = price + checkSampleAmount; //+检测样本费用
		}
		$("#productsPrice").html(price);
	});
	
	
}

function caclAmount(countNum){
	//默认从1开始
	//debugger;
	var cc = 0;
	var precc = 0;
	for(var i in productRuleArr){
		if(typeof productRuleArr[i] == 'object'){
			 var objmap = productRuleArr[i];
			    for(var i = 1 ;i< countNum+1 ;i++){
			    	if(objmap.get(i) != undefined){
			    		cc =  cc + objmap.get(i)/100;
						precc = objmap.get(i)/100;
			    	}else{
						cc = cc + precc;//没有找上一个
					}
				}
		}
	}
	
	return cc;	
}

function cacl_minsprice(obj){
	
	map.remove(obj.find('select[name="purpose"]').attr('ids')); //map去掉删除元素
	var price = $("#productsPrice_new").val()/100; //初始产品价格  /100用于显示
	var notfreecount = 0;
	var checkcount = 0;
	checkSampleAmount = 0;
	map.each(function(value){
		if((map.get(value)== 1 || map.get(value) == 3)){
			notfreecount =notfreecount+1;
		}
	})
	
	map.each(function(value){
		if( (map.get(value)==2)){
			checkcount = checkcount+1;
		}
	})
	
	if(checkcount>0){
		checkSampleAmount = caclAmount(checkcount);
	}
	if(notfreecount >1){
		price  = price+ (notfreecount-freecount)*countmoney+checkSampleAmount;
		sub_SamplePrice = (notfreecount-freecount)*countmoney; //附属样本钱
	}else{
		price  = price + checkSampleAmount; //+检测样本费用
	}
	$("#productsPrice").html(price);
	
	$(obj).remove(); //去除元素
	
}



function accAdd(arg1,arg2){
	  var r1,r2,m;
	  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	  m=Math.pow(10,Math.max(r1,r2))
	  return (accMul(arg1,m)+accMul(arg2,m))/m
	   
}


function accSub(arg1,arg2){
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}

function accMul(arg1,arg2)
{
  var m=0,s1=arg1.toString(),s2=arg2.toString();
  try{m+=s1.split(".")[1].length}catch(e){}
  try{m+=s2.split(".")[1].length}catch(e){}
  return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
}

function removeSeSample(obj){
	sampleCount = sampleCount-1;
	cacl_minsprice($(obj).parent());
}

