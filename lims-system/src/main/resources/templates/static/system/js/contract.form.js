
var contractPro;

$(function() {
	
	$('#deliveryMode').selectpicker({noneSelectedText : '请选择'}); 
	if(deliveryModes.length > 0){
		
		$('#deliveryMode').selectpicker('val', deliveryModes); 
	}

	var vm;
	var task = function() {
		this.sampleCategory = "";
		this.sampleTypeKeys = "";

	};
	vm = avalon.define({
		$id : "test",
		inputLists : inputArrs,
		add : function(e) {
			this.inputLists.push(new task());
			$('#tab').find('.selectpicker').selectpicker({
				noneSelectedText : '请选择'
			});
		},
		remove : function(e, index) {
			var obj = this;
			layer.confirm('确定删除该样本么？', { btn: ['确定','取消']}, function(){
				layer.closeAll('dialog');
				obj.inputLists.splice(index, 1);
			});
			
		}
	});

	avalon.scan();

	var testSample = $('#testSample').magicSuggest(
			{
				width : 190,
				highlight : true,
				data : base+'/sample/getSelecteList',
				method : 'get',
				queryParam : "name",
				allowFreeEntries : false,
				renderer : function(v) {
					return '<div>' + '<div >' + '<div  class="probe">' + v.name
							+ '</div>' + '<div style="clear:both;"></div>';
				}
			});

	 contractPro = $('#contractPro').magicSuggest(
			{
				width : 190,
				highlight : true,
				data : base+'/product/productSelect.do',
				method : 'get',
				queryParam : "query",
				maxSelection : 1,
				allowFreeEntries : false,
				renderer : function(v) {
					return '<div>' + '<div >' + '<div  class="probe">' + v.name
							+ '</div>' + '<div style="clear:both;"></div>';
				}
			});

	$(contractPro).on(
			'selectionchange',
			function(e, m) {
				var obj_ = this.getSelection()[0];
				$('#contractPro').parents('.form-group').find(
						'input[name="contractPrice"]').val(obj_.realPrice)
			});
	 if(null != $('#marketingCenter').val()){
		 if(undefined != contractPro.getName){
				
			 contractPro.setDataUrlParams({
				tType : $('#marketingCenter').val()
			});  
		} 
	 } 
	
	
	$("#choseForm").validate({
		
		
		submitHandler : function(form) {
				var startTime = new Date($('#effectiveStart').val()).getTime();
				var endTime = new Date($('#effectiveEnd').val()).getTime();
				var signTime = new Date($('#signDate').val()).getTime();
				var thisTime = new Date().getTime();
				if (startTime > endTime) {
					layer.alert('合同有效期结束日期不能小于开始日期', {
						title : "提示"
					});
					return;
				}
				if(signTime > startTime){
					layer.alert('合同签订日期不能大于合同开始日期', {
						title : "提示"
					});
					return;
				}
				
				if (signTime > thisTime) {
					layer.alert(new Date($('#signDate').val()).getMonth()+1+'月'+new Date($('#signDate').val()).getDate()+'还没到 你就签合同了么？', {
						title : "提示"
					});
					return;
				}
				var business = $('#business').magicSuggest()
						.getSelection()[0];
				if(undefined != business){

					$('input[name="contract.signUser"]').val(business.id);
				}

				if($('#projectManager').val() !=null){
					$('#projectManager').val('')
				}
				if($('#projectManager').val() !=''){
					$('#projectManager').val('')
				}
				var pm_ = $('#prjManager').magicSuggest().getSelection()[0];
				if(undefined !=pm_){
					$('#projectManager').val(pm_.id);
				}
            var contractproducts = [];
				
						$.each($('.mainGroup'),function(i, v) {

							var productId = $(v).find('.ms-ctn').attr('id');
							var product = $('#' + productId).magicSuggest().getSelection()[0];
							var cp = {};
							if(null != product){
								cp.productId = product.id;
								cp.productName = product.name;
								cp.contractPrice = Number($(v).find('input[name="contractPrice"]').val()*100);
								cp.signCount = $(v).find('input[name="signCount"]').val();
								cp.signAmount = Number($(v).find('input[name="signAmount"]').val()*100);
								cp.requirement = $(v).find('textarea').val();
								contractproducts.push(cp);
							}
						});
				var param = JSON.stringify(contractproducts);
				$("input[name='contractProductJson']").val(JSON.stringify(contractproducts));
				$("input[name='contractSampleJson']").val(JSON.stringify(vm.inputLists.$model)); 
				var val_ = $('#id').val();
				var formUrl = base+'/contract/create.do';
				if (val_ != null && val_ != "") {
					formUrl = base+'/contract/modify.do';
				}
				if('true' == $('#isBbuildContract').val()){
					formUrl = base+'/contract/buildContract.do';
				}
				$.ajax({
	                cache: true,
	                type: "POST",
	                url:formUrl,
	                data:$('#choseForm').serialize(),
	                async: false,
	                error: function(request) {
	                    alert("Connection error");
	                },
	                success: function(data) {
	                	if(data != 'OK'){
	                		$('#formValue').val(data);
	                		$('#hiddForm').submit();
	                		return;
	                	}
	                	if($('input[name="contract.status"]').val() == '1'){
	                		var flag = 'create';
	                		parent.window.gotoHtml(flag);
	                	}
	                	if($('input[name="contract.status"]').val() == '0'||$('input[name="contract.status"]').val() == ''){
	                		parent.window.gotoHtml(flag);
	                	}
	               	}
	            });
			},
			
			 rules : {
					
					"contractpa.contactPhone" : {
						isTel:true
					},
					"contractpa.contactEmai" : {
						email:true
					},
					"contractpa.zipcode" : {
						number:true
					},
					
					"contractpb.contactPhone" : {
						isTel:true
					},
					"signCount" : {
						number : true
					},
					"signAmount" : {
						number : true
					}
				},	
		});

	var j = 0
	$.each(cProducts, function(ii, sample) {
		j = j + 1;
		var sampleClone = $(".sampleTag").clone();
		$(sampleClone).addClass("mainGroup");
		$(sampleClone).removeClass("sampleTag");
		$(sampleClone).find('input[name="contractPrice"]').val(Number(sample.realContractPrice));
		$(sampleClone).find('input[name="signCount"]').val(sample.signCount);
		$(sampleClone).find('input[name="signAmount"]').val(Number(sample.realSignAmount));
		$(sampleClone).find('textarea').val(sample.requirement);
		$(sampleClone).find('.testProduct').attr('id', "testProduct" + j);
		$("#mainContent").append(sampleClone);
		var x = '#testProduct' + j;
		tp_ = $(x).magicSuggest(
				{
					width : 190,
					highlight : true,
					data : base+'/product/productSelect.do',
					method : 'get',
					maxSelection : 1,
					queryParam : "query",
					allowFreeEntries : false,
					renderer : function(v) {
						return '<div>' + '<div >' + '<div  class="probe">'
								+ v.name + '</div>'
								+ '<div style="clear:both;"></div>';
					}
				});

		$(x).magicSuggest().setSelection(sample.p);

		$(tp_).on('selectionchange',function(e, m) {
				var pVal_ = this.getSelection()[0];
				$(x).parents('.form-group').find('.price').val(pVal_.realPrice)
		});
		
		if($('#marketingCenter').val()!=null){
			tp_.setDataUrlParams({
				tType : $('#marketingCenter').val()
			}); 
		} 
	});
});


function viewValue(obj) {
	$.ajax({
		type : "post",
		data:{name:$(obj).val()},
		url : base+"/contract/getContractOrgByName",
		success : function(data) {
			$('#depositBank').val(data.depositBank);
			$('#bankAccountNo').val(data.bankAccountNo);
			$('#bankAccountName').val(data.bankAccountName);
			$('#testInstitution').val(data.testInstitution);
		}
	});
}


var i = 0;
function addMain(obj) {

	i = i + 1;
	$(obj)
			.parent()
			.parent()
			.find('#mainContent')
			.append(
					'<div><DIV style="BORDER-TOP: #adadad  1px dashed; OVERFLOW: hidden; HEIGHT: 1px"></DIV></div>'
							+ '<div style="margin-top:17px" class="mainGroup">'
							+ '<img src="'
							+ system_images
							+ '/rubish.png" style="float: right;cursor: pointer;" onclick="removeMain(this)"/>'
							+

							'<div class="form-group">'
							+ '<label class="col-sm-2 control-label ">产品名称：</label>'
							+ '<div class="col-sm-3">'
							+ '<div class="form-control testProduct" id="teProduct'
							+ i
							+ '"  placeholder="请选择检测产品"></div>'
							+ '</div>'
							+

							'<label class="col-sm-2 control-label control-required">单价：</label>'
							+ '<div class="col-sm-3">'
							+ '<input type="text" class="form-control" name="contractPrice" onblur = getPriceByContractPrice(this) id="contractPrice'+i+'" /></div></div>'
							+

							'<div class="form-group">'
							+ '<label class="col-sm-2 control-label control-required">数量：</label>'
							+ '<div class="col-sm-3">'
							+ '<input type="text" class="form-control" name="signCount" onblur = getPriceBySignCount(this) id="signCount'+i+'"/></div>'
							+

							'<label class="col-sm-2 control-label control-required">价格：</label>'
							+ '<div class="col-sm-3">'
							+ '<input type="text" class="form-control" name="signAmount" id="signAmount'+i+'" /></div></div>'
							+

							'<div class="form-group">'
							+ '<label class="col-sm-2 control-label control-required">服务要求：</label>'
							+ '<div class="col-sm-8">'
							+ '<textarea class="form-control" name="requirement" rows="2"></textarea></div></div>');

	var p = 'teProduct' + i;
		m = $('#' + p).magicSuggest(
			{
				width : 190,
				highlight : true,
				data : base+'/product/productSelect.do',
				method : 'get',
				queryParam : "query",
				maxSelection : 1,
				allowFreeEntries : false,
				renderer : function(v) {
					return '<div>' + '<div >' + '<div  class="probe">' + v.name
							+ '</div>' + '<div style="clear:both;"></div>';
				}
			});

	 $(m).on('selectionchange',function(e, m) {
			var obj_ = this.getSelection()[0];
			$('#' + p).parents('.form-group').find(
					'input[name="contractPrice"]').val(obj_.realPrice)
			}).on('beforeload',function(e, m) {
				var val_ = getms_sel_items();
				m.setDataUrlParams({
					id : val_,tType : $('#marketingCenter').val()
			});
	});
	
}

var s = null;
function changeType(obj) {
	
	if ($(obj).val() == 4) {
		$('#canShow').css('display', 'block');
		$('#settlementMode option[value="4"]').remove();
	} else {
		$('#canShow').css('display', 'none');
		if($('#settlementMode option:last').val() == '3'){
			$('#settlementMode').append('<option value="4">一单一结</option>');
		$('#testingPeriod').val('');	
		}
	}

	s.setDataUrlParams({
		testingType : $(obj).val()
	}); 
	
	if(undefined != contractPro.getName){
		
		 contractPro.setDataUrlParams({
			tType : $(obj).val()
		});  
	}
	$(obj).parents('.panel-group').find('.mainGroup').each(function(i,v){
		var obj_ = $(v).find('.ms-ctn');
		obj_.magicSuggest().setDataUrlParams({
			tType : $(obj).val()
		});  
	})
	
}
function removeMain(obj) {
	layer.confirm('确定删除该产品么？', { btn: ['确定','取消']}, function(){
		layer.closeAll('dialog');
		$(obj).parent().remove();
	});
}

function goBack() {
	window.history.back(-1);
}


function subForm(val) {
	var businessEmpty = $('#business').magicSuggest().getSelection()[0];
	if(undefined == businessEmpty)
	{
		layer.alert('业务员不能为空', {
			title : "提示"
		});
		return;
	}

    var prjManagerVal = $('#prjManager').magicSuggest().getSelection()[0];
    if(undefined == prjManagerVal)
    {
        layer.alert('请选择项目管理人！', {
            title : "提示"
        });
        return;
    }


    $('input[name="contract.status"]').val("1");
	$('input[name="contract.hospitalAdmited"]').rules("add",{required:true});
	$('input[name="contractpb.depositBank"]').rules("add",{required:true});
	$('input[name="contractpb.bankAccountNo"]').rules("add",{required:true});
	$('input[name="contractpb.bankAccountName"]').rules("add",{required:true});
	$('input[name="ontractcontent.deliveryMode"]').rules("add",{required:true});
	$('select[name="contractcontent.settlementMode"]').rules("add",{required:true});
	$('select[name="contractcontent.deliveryResult"]').rules("add",{required:true});
	$('textarea[name="contractcontent.settlementRemark"]').rules("add",{required:true});
	$('select[name="contractcontent.deliveryMode"]').rules("add",{required:true});
	$('input[name="contract.name"]').rules("add",{required:true});
	$('input[name="contract.effectiveStart"]').rules("add",{required:true});
	$('input[name="contract.effectiveEnd"]').rules("add",{required:true});
	$('select[name="contract.marketingCenter"]').rules("add",{required:true});
	$('input[name="contractpa.companyName"]').rules("add",{required:true});
	$('input[name="contract.effectiveEnd"]').rules("add",{required:true});
	$('input[name="contractpa.address"]').rules("add",{required:true});
/*	$('input[name="contractpa.invoiceTitle"]').rules("add",{required:true});*/
	$('input[name="contractpb.contactName"]').rules("add",{required:true});
	$('select[name="contractpb.companyName"]').rules("add",{required:true});
	$('select[name="contractcontent.invoiceMethod"]').rules("add",{required:true});
	$('input[name="contractpa.contactPhone"]').rules("add",{required:true});
	$('input[name="contractpa.contactName"]').rules("add",{required:true});
	$('input[name="contractpa.contactEmai"]').rules("add",{required:true});
	$('input[name="contractpa.zipcode"]').rules("add",{required:true});
	$('input[name="contractpb.contactPhone"]').rules("add",{required:true});
	//$('input[name="contractPrice"]').rules("add",{required:true});
	$('input[name="contract.signDate"]').rules("add",{required:true});
	$('select[name="contract.startMode"]').rules("add",{required:true});
	
	
	$("#choseForm").submit();
}	

	

function buildContract(){
	$('#isBbuildContract').val('true');
	$("#choseForm").submit();
}

function submitForm(){

	$('#isBbuildContract').val('false');
	$("#choseForm").submit();
	
}

function getms_sel_items()
{
	var vals_ = "";
	
	$('.ms-sel-ctn').each(function(i,v){
		var val_ = $(v).find("input:hidden").val();
		if(undefined != val_)
		{
			vals_ += val_ + ",";
		}
	});
	
	return vals_;
}

function getPriceBySignCount(obj)
{
	var signCount_value = $(obj).val();
	var contractPrice_value = $(obj).parents('.mainGroup').find('input[name="contractPrice"]').val();
	var signAmount = $(obj).parents('.mainGroup').find('input[name="signAmount"]')
	if(signCount_value ==''||signCount_value==null||signCount_value==undefined)
	{
		signCount_value = 0;
	}
	if(contractPrice_value ==''||contractPrice_value==null||contractPrice_value==undefined)
	{
		contractPrice_value = 0;
	}
	var value = Number(signCount_value)*Number(contractPrice_value);
	signAmount.val(value.toFixed(2));
}

function getPriceByContractPrice(obj)
{
	var signCount_value = $(obj).parents('.mainGroup').find('input[name="signCount"]').val();
	var contractPrice_value = $(obj).val();
	var signAmount = $(obj).parents('.mainGroup').find('input[name="signAmount"]')
	if(signCount_value ==''||signCount_value==null||signCount_value==undefined)
	{
		signCount_value = 0;
	}
	if(contractPrice_value ==''||contractPrice_value==null||contractPrice_value==undefined)
	{s
		contractPrice_value = 0;
	}
	var value = Number(signCount_value)*Number(contractPrice_value);
	signAmount.val(value.toFixed(2));
}
