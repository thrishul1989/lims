//新增用的样本类型 --免费xx个，额外新增一个xx元
var freecount = searcher.freeCount;
var countmoney = searcher.extraMoney;
var  sampleCount = 0, sub_SamplePrice =0 ,product_price;
var sampleOption_new ='';
var sample_purpose_select ='';
var productRuleArr =[];
var checkSampleAmount = 0 ;
$(document).ready(function(){
	var zhiqingFile=[];
	var fujianFile=[];
	var familyFile =[];
	var hetongId="" , ownerId="";

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
				
	$("#examineeName").keyup(function(){
		$("#invoiceTitle").val($(this).val());
	});

    $("#contactEmail").keyup(function(){
        $("#mailbox").val($(this).val());
    });
	$("input:radio[name=examineeSex]").change(function (){
		if (this.value == '2') {
			$("#birthday").val(laydate.now());
			$("#age").val('0天');
        }
	});
	
	$("#origin").lSelect({
        url: base+"/order/areaTree.do",
	});
	
	laydate({
		  elem: '#birthday',
		  format: 'YYYY-MM-DD', 
		  festival: true, //显示节日
		  max: laydate.now(),
		  choose: function(datas){ //选择日期完毕的回调
			 $('#age').val(ages(datas));
		  }
	});
	
	$("input:radio[name=sendStrategy]").change(function (){ 
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
            $("#headerType").rules("add",{required:true});
            $("input:radio[name='headerType'][value='1']").prop("checked", true);
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
            $("#headerType").rules("add",{required:true});
            $("input:radio[name='headerType'][value='1']").prop("checked", true);
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
			
			var samplingAmount = 0;
			if($("#samplingAmount").val()!=null && $("#samplingAmount").val()!=undefined && $("#samplingAmount").val()!='' ){
				samplingAmount = accMul($("#samplingAmount").val(),100);
			}
			
			var owner=$('#ownerId').magicSuggest().getSelection();
			if(owner.length==0){
				showTip('请选择客户！','提示');
				return false;
			}
			$.each(owner,function(index,obj){
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
        /*    //表型
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
			
			
			if(phenotypes == ''&& diseases == '' && $("#medicalHistory").val() == '' ){
				showTip('临床诊断、表型、病史摘要至少填写一项！','提示');
				return false;
			}
			
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
						sampleSize:$(obj).find("input[name='mainSampleSize']").val(),samplingDate:$(obj).find("input[name='mainSampleDate']").val()};
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
						familyRelation:$(obj).find("select[name='familyRelation']").val()};
				if(se.purpose == 3){
					checkcount ++;
				}
				
				seGroup.push(se);
				allGroup.push(se.sampleCode.toUpperCase());
			});
			if(checkcount>1){
			showTip("本人对照样本至多一个","提示！");
				return ;
			}
			if(isRepeat(allGroup)){
				showTip("样本编号重复，请检查！");
				return ;
			}
			
			
		
			$("#createButton").attr('disabled', 'disabled');
			$("#createButton").val('订单提交中');
			var layerObject = parent.parent.layer;
			var loadindex = layerObject.load();
			
			var subprice = sub_SamplePrice+checkSampleAmount;
			$.ajax({
	            type: "post",
	            url: base+"/order/create.do",
	            data: {
	            	amount:$("#productsPrice_new").val(),
	            	//subsidiarySampleAmount:accMul(sub_SamplePrice,100),
	            	subsidiarySampleAmount:accMul(subprice,100),
	            	orderType:searcher.orderType,
	            	code:$("#orderCode").val(),
	            	contractId:hetongId,
	            	ownerId:ownerId,
	            	creatorName:$("#creatorName").val(),
	            	creatorId:$("#creatorId").val(),
	            	doctorAssist:$('input[name="doctorAssist"]:checked').val(),
	            	orderProduct:testProducts,consentFigures:zhiqingFile.join(","),
	            	name:$("#examineeName").val(),sex:$('input[name="examineeSex"]:checked').val(),
	            	birthday:$("#birthday").val(),ageSnapshot:$("#age").val(),
	            	contactName:$("#contactName").val(),contactPhone:$("#contactPhone").val(),
	            	contactEmail:$("#contactEmail").val(),
	            	nation:$("#nation").val(),origin:$("#origin").val(),recordNo:$("#recordNo").val(),
	            	orderExamineePhenotype:phenotypes,
	            	orderExamineeDiagnosis:diseases,orderExamineeGene:genes,
	            	medicalHistory:$("#medicalHistory").val(),
	            	familyMedicalHistory:$("#familyMedicalHistory").val(),
	            	clinicalRecommend:$("#clinicalRecommend").val(),
	            	recordFigures:fujianFile.join(","),
	            	familyFigures:familyFile.join(","),
	            	recipientName:$("#recipientName").val(),
	            	recipientPhone:$("#recipientPhone").val(),
	            	recipientAddress:$("#recipientAddress").val(),
                    projectManager:$("#projectManager").val(),
	            	invoiceTitle:$("#invoiceTitle").val(),
                    billingType:$('input[name="billingType"]:checked').val(),
                    headerType:$('input[name="headerType"]:checked').val(),
                    dutyParagraph:$("#dutyParagraph").val(),
                    mailbox:$("#mailbox").val(),
	            	samplingAmount:samplingAmount,
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
            headerType: {
                required:true,
            },
        	examineeSex: {
        		required:true,
        	},
            dutyParagraph: {
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
        		remote: {
         		    url: base+"/order/validateSampleCode.do",
         		    type: "post",               
         		    dataType: "json"          
         		},
         		alnum:true
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
        		remote: {
         		    url: base+"/order/validateSeCode.do",
         		    type: "post",               
         		    dataType: "json"          
         		},
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
        		digits:true,
        		required:true,
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
            mainSampleCode:{
            	required: "请输入样本编号",
            	rangelength:"样本编号长度不对,应该为9位",
            	remote:"样本编号已经存在"
            },
            seSampleCode:{
            	required: "请输入附属样本编号",
            	rangelength:"样本编号长度不对,应该为9位",
            	remote:"附属样本编号已经存在"
            },
            mainSampleSize:{
            	digits:"请输入整数"
            },
            headerType:{
                required: "请选择发票抬头类型"
            },
            dutyParagraph:{
                required: "请填写企业税号"
            },
            mailbox:{
                required: "请填写邮箱"
            },
            seSampleSize:{
            	digits:"请输入整数"
            },
            examineeName: {
            	required: "请填写受检人姓名"
        	},
            invoiceTitle: {
                required: "请填写发票抬头"
            }
        	
        },
        errorPlacement: function(error, element) {  
            error.appendTo(element.parent());  
        }
    });
	
	
	
	$('#zhiqing').fileinput({
        language: 'zh', // 设置语言
        uploadUrl: base+'/order/upload.do', // 上传的地址
        allowedFileExtensions : ['jpg', 'png','gif'],// 接收的文件后缀
        maxFileSize: 5000,
        validateInitialCount:true,
        showUpload: true, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        maxFileCount: 5,
        deleteUrl:base+'/order/deletePic.do',
        showRemove:false,
        initialPreview:[],
        initialPreviewConfig:[],
    }).on("fileuploaded", function(event, data) {
    	if(data.response.fileList.length==1){
    		zhiqingFile.push(data.response.fileList[0])
    	} else{
    		$.each(data.response.fileList,function(index,obj){
    			zhiqingFile.push(obj)
    		})
    	}
    }).on('filepredelete', function(event, key) {  
    	zhiqingFile.remove(key);
    });
	
	
	
	
	$("#file-1").fileinput({
		language: 'zh', // 设置语言
        uploadUrl: base+'/order/upload.do',
        maxFileSize: 5000,
        deleteUrl:base+'/order/deletePic.do',
        showUpload: true, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        maxFileCount: 9,
        validateInitialCount:true,
        showRemove:false,
        allowedFileExtensions : ['jpg', 'png','gif'],// 接收的文件后缀
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
   
	
	$('#familyFigures').fileinput({
        language: 'zh', // 设置语言
        uploadUrl: base+'/order/upload.do', // 上传的地址
        allowedFileExtensions : ['jpg', 'png','gif'],// 接收的文件后缀
        maxFileSize: 5000,
        deleteUrl:base+'/order/deletePic.do',
        showUpload: true, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        maxFileCount: 9,
        validateInitialCount:true,
        showRemove:false,
    }).on("fileuploaded", function(event, data) {
    	if(data.response.fileList.length==1){
    		familyFile.push(data.response.fileList[0]);
    	}
    	else{
    		$.each(data.response.fileList,function(index,obj){
    			familyFile.push(obj);
    		})
    	}
    }).on('filedeleted', function(event, key) {  
    	familyFile.remove(key);
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


	var testProduct=$('#testProduct').magicSuggest({
	    width: 190,
	    highlight: true,
        data:base+'/product/productSelect.do?testingType.id='+searcher.orderType,
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
	
	$(s).on('selectionchange', function(e,m){ //合同
		$("#testProduct").magicSuggest().clear();
		$("#ownerId").magicSuggest().collapse();
		$("#testProduct").magicSuggest().collapse();
		var hetongObj=$('#hetong').magicSuggest().getSelection();
		
		if(hetongObj != undefined && hetongObj.length >0){
			var hid = hetongObj[0].id;
			$.post(base+"/contract/getContractUsers.do", { contractId: hid , disableStatus:0 },
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

			//根据合同设置产品
			$.post(base+"/contract/getContractProducts.do", { contractId: hid },
				       function(data){
				$("#testProduct").magicSuggest().setData(data);
			});
			
		}else{
			
			//初始化所有客户
			$.post(base+"/customer/getSelectListByConstract.do", {testingType:searcher.orderType,disableStatus:0},
				       function(data){
				     $('#ownerId').magicSuggest().setData(data);
			});
			//初始化产品数据
			$("#testProduct").magicSuggest().setData(base+'/product/productSelect.do?testingType.id='+searcher.orderType);
		}
		
	});
	

	
	$(owner).on('selectionchange', function(e,m){
		var ownObj=$('#ownerId').magicSuggest().getSelection();
		if(ownObj != undefined && ownObj.length >0){
			var sid = ownObj[0].id;
			
			$("input:radio[name='doctorAssist'][value='" + ownObj[0].analyseType + "']").prop("checked", "checked");
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
	
	
	
	
	var mainmap=[];
	var semap = [];
	var mainsample =[];
	
	$(testProduct).on('selectionchange', function(e,m){
		
		 //计算产品价格
		var productsPrice=$('#testProduct').magicSuggest().getSelection();
		var relprice=0,product_price = 0;
		
		if(productsPrice.length >0){
			
			
			productRuleArr =[];
			var checkcount = 0; //重置个数
			checkSampleAmount = 0; //全局重置检测样本金额
			sample_purpose_select =''; //清空原来样本用途字符串+样本用途数组
			mainsample.splice(0,mainsample.length);
			
			//计算检测样本个数
			$.each($("[id^=purpose]"),function(i,obt){
				if($(this).val()==2){
				 checkcount = checkcount+1;
				}
			});
			
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
				$.each(obj.productSampleList,function(index,ob){
					var id= ob.sample.id;
					var name = ob.sample.name;
					var unit = ob.sample.receivingUnit;
					if(mainmap.indexOf(id)<0){
						sampleOption_new=sampleOption_new+'<option value="'+id+'" unit='+unit+' >'+name+'</option>';
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
			$.each($("[id^=mainSampleType]").not("[id$=error]"),function(i,obt){
				$(obt)[0].options.length=1;
				$(obt).append(sampleOption_new);
			});
			$.each($("[id^=seSampleType]").not("[id$=error]"),function(i,obt){
				$(obt)[0].options.length=1;
				$(obt).append(sampleOption_new);
			});
			
			$.each($("[id^=purpose]").not("[id$=error]"),function(i,obt){
				var oldvalue = $(this).val(); //记忆之前选中值
				$(obt)[0].options.length=1;
				$(obt).append(sample_purpose_select);
				$(this).val(oldvalue)
			});
			
			
		}else{
			
			$.each($("[id^=mainSampleType]").not("[id$=error]"),function(i,obt){
				$(obt)[0].options.length=1;
			});
			$.each($("[id^=seSampleType]").not("[id$=error]"),function(i,obt){
				$(obt)[0].options.length=1;
			});
			$.each($("[id^=purpose]").not("[id$=error]"),function(i,obt){
				$(obt)[0].options.length=1;
			});
			
		}
		if(checkcount>0){
			checkSampleAmount =  caclAmount(checkcount);
		}
		$("#productsPrice").html(accAdd(relprice,sub_SamplePrice)+checkSampleAmount);
		$("#productsPrice_new").val(product_price);
		
		
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
	
	var gene=	$('#gene').magicSuggest({
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
	
	
	
	
	
	
})


var sampleOption="";
$.each(sampleList,function(index,obj){
	sampleOption=sampleOption+'<option value="'+obj.id+'" unit='+obj.receivingUnit+'>'+obj.name+'</option>'
	
});




function removeMain(obj){
	$(obj).parent().remove();
}


var i=0;
function addMain(){
	i=i+1;
	$('#mainContent').append('<div style="margin-top:17px" class="mainGroup">'+
			'<img src="'+system_images+'/rubish.png" style="float: right;cursor: pointer;" onclick="removeMain(this)"/>'+
			'<div class="form-group">'+
'<label class="col-sm-2 control-label control-required"><span>*</span>样本编号：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" id="sampleCode'+i+'" name="mainSampleCode"'+
						 'value="" />'+
				'</div>'+
'<label class="col-sm-2 control-label control-required"><span>*</span>样本类型：</label>'+
				'<div class="col-sm-3">'+
					'<select class="form-control" name="mainSampleType" id="mainSampleType'+i+'" '+
						' onchange="showUnit(this)">'+
						'<option value="0">请选择样本类型</option>'+sampleOption_new+
					'</select>'+
				'</div></div><div class="form-group">'+
'<label class="col-sm-2 control-label control-required"><span>*</span>样本量：</label>'+
				'<div class="col-sm-3">'+
					'<input type="text" class="form-control" id="mainSampleSize'+i+'" name="mainSampleSize"'+
						 'value="" />'+
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

function showUnit(obj){
	
	var unit=$(obj).find('option:selected').attr('unit');
	if(typeof(unit) != 'undefined' && null != unit && "" != unit){
		
		//通过unit---得到name
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
					'<option value="">请选择样本类型</option>'+sampleOption_new+
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
			'<div class="col-sm-3">'+
			'<select class="form-control" name="purpose" id="purpose'+j+'" ids="ids'+j+'" >'+
			'<option value="">请选择</option>'+sample_purpose_select+
			'</select>'+
			
			//$("#purposeClone").clone().removeAttr("id").attr("id","purpose"+j).attr("ids",j).prop("outerHTML")+
			
			'</div>'+
			'<span class="phenotype"> <label class="col-sm-2 control-label control-required"><span>*</span>家属症状：</label>'+
				'<div class="col-sm-3">'+
					'<select class="form-control" id="ownerPhenotype'+j+'" name="ownerPhenotype">'+
						'<option value="">请选择家属症状</option>'+
						'<option value="0">正常</option>'+
						'<option value="1">确诊</option>'+
						'<option value="2">与受检人类似</option>'+
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

		'<DIV style="BORDER-TOP: #adadad 1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV>'+
	'</div>');
	sampleCount = sampleCount+1;
	cacl_addprice();
	
	
	
}



var map = new Map();

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
			price  = price+ (notfreecount-freecount)*countmoney+checkSampleAmount; //+检测样本费用
			sub_SamplePrice = (notfreecount-freecount)*countmoney; //附属样本钱+检测样本费用
			//sub_SamplePrice = (notfreecount-freecount)*countmoney+checkSampleAmount; //附属样本钱+检测样本费用
		}else{
			price = accAdd(price,checkSampleAmount);
		}
		$("#productsPrice").html(price);
	});
	
}


function caclAmount(countNum){
	//默认从1开始
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
		price = accAdd(price,checkSampleAmount);
	}
	$("#productsPrice").html(price);
	
	$(obj).remove(); //去除元素
}



function accAdd(arg1,arg2){
	  var r1,r2,m;
	  try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0};
	  try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0};
	  m=Math.pow(10,Math.max(r1,r2));
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


function removeSeSample(obj){
	sampleCount = sampleCount-1;
	cacl_minsprice($(obj).parent());
}


