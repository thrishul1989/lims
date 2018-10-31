	$(function() {

		contractPro = $('#contractPro').magicSuggest(
				{
					width : 190,
					highlight : true,
					data : base + '/product/productSelect.do',
					method : 'get',
					queryParam : "query",
					maxSelection : 1,
					allowFreeEntries : false,
					renderer : function(v) {
						return '<div>' + '<div >' + '<div  class="probe">'
								+ v.name + '</div>'
								+ '<div style="clear:both;"></div>';
					}
				});
		
		$(contractPro).on(
				'selectionchange',
				function(e, m) {
					var obj_ = this.getSelection()[0];
					$('#contractPro').parents('.form-group').find(
							'input[name="contractPrice"]').val(obj_.realPrice)
				}).on('beforeload',function(e, m) {
					var val_ = getms_sel_items()+productsString;
					m.setDataUrlParams({id : val_,tType:$('#marketingCenter').val()});
				});
		
	});

	var i = 0;
	function addMain(obj) {

		i = i + 1;
		$(obj)
				.parents('#mainContent')
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
					data : base + '/product/productSelect.do',
					method : 'get',
					queryParam : "query",
					maxSelection : 1,
					allowFreeEntries : false,
					renderer : function(v) {
						return '<div>' + '<div >' + '<div  class="probe">'
								+ v.name + '</div>'
								+ '<div style="clear:both;"></div>';
					}
				});

		$(m).on(
				'selectionchange',
				function(e, m) {
					var obj_ = this.getSelection()[0];
					$('#' + p).parents('.form-group').find(
							'input[name="contractPrice"]').val(obj_.realPrice)
				}).on('beforeload',function(e, m) {
					var val_ = getms_sel_items()+productsString;
					m.setDataUrlParams({id : val_,tType : $('#marketingCenter').val()});
				});
	}

	function removeMain(obj) {
		layer.confirm('确定删除该产品么？', {
			btn : [ '确定', '取消' ]
		}, function() {
			layer.closeAll('dialog');
			$(obj).parent().remove();
		});
	}

	function insertProduct() {
		
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
		$.ajax({
			type:"POST",
			data:{param:param,contractId:$('#contractId').val()},
			url:base+'/contract/insertContractProducts.do',
			success:function(data){
				parent.window.gotoHtml('confirm');
			},
			error:function(){
				alert("something error!")
			}
		});
	}
	
	
	function canDelete(obj){
		var obj_ = $(obj)
		var productId = obj_.attr('proid');
		$.ajax({
			type:"POST",
			data:{productId:productId,contractId:$('#contractId').val()},
			url:base+'/contract/canDelete.do',
			success:function(flag){
				if(flag){
					layer.alert('已有产品订单，不可删除！',{title:"提示"});
				}else{
							obj_.parents('tr').remove();
				}
			},
			error:function(){
				alert("something error!")
			}
		});
		
	}
	
	 function validate(val){  
	     var reg = /^\+?[1-9][0-9]*$/;  
	     return reg.test(val);
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
	 	if(validate(signCount_value))
	 	{
	 		
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
	 	else
	 	{
			layer.alert('输入非0数字！',{title:"提示"});
			$(obj).val('')
		}
	 	
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