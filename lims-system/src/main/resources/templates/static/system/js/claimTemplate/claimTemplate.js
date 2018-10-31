$(function(){
	function getIndex(){
		var a = 0;
		$('.table tbody tr').each(function(i,v){
			a = i;
			$(this).find('.index').empty().text(i+1);
			$(this).find('.id').attr('name','columnList['+i+'].id');
			$(this).find('.dataTemplateColumnId').attr('name','columnList['+i+'].dataTemplateColumnId');
			$(this).find('.filterName').attr('id','filterName'+i);
			$(this).find('.defaultValue').attr('id','defaultValue'+i);
			$(this).find('.radioValue').attr('name','columnList['+i+'].radioValue');
			$(this).find('.rangeValue1').attr('name','columnList['+i+'].rangeValue1');
			$(this).find('.rangeValue2').attr('name','columnList['+i+'].rangeValue2');
			$(this).find('.columnIndex').attr('name','columnList['+i+'].columnIndex');
			$(this).find('.inputFilterName').attr('name','columnList['+i+'].filterName');
			$(this).find('.inputDefaultValue').attr('name','columnList['+i+'].defaultValue');
		});
		return a;
	}
	
	function msRemoveIndex()
	{
		$('.table tbody tr').each(function(i,v){
			$(this).find('div[id^=\'filterName\']').attr('id','filterName'+i);
			$(this).find('div[id^=\'defaultValue\']').attr('id','defaultValue'+i);
			loadMagicSuggest(i);
		});
	}
	function loadSelect(b)
	{
		var templateId = $('#templateId').val();
		if(null != templateId && "" != templateId)
		{
			$.ajax({
	            type: "POST",
	            url: base+"/biAnalysis/dataTemplate/getColumnList.do",
	            data: {templateId : templateId},
	            dataType: "json",
	            success: function(data){
	            	var opts = "";
	            	for(var i = 0;i<data.length;i++)
	            	{
	            		opts += "<option value=\""+data[i].id+"\" attr-semantic=\""+data[i].semantic+"\" attr-type=\""+data[i].type+"\">"+data[i].columnName+"</option>";
	            	}
	            	$("select[name='columnList["+b+"].dataTemplateColumnId']").append(opts);
	            }
	        })
		}
	}
	//
	function loadMagicSuggest(b)
	{
		var opts = {
				   width: 190,
				    highlight: true,
				    data:base+'/claimTemplate/getItemList.do',
				    method:'get',
				    queryParam:"name",
				    allowFreeEntries:false,
				    renderer: function(v){
				    return '<div>' +
				        '<div >' +
				        
				            '<div  class="probe">' + v.name + '</div>' +
				           
				        '<div style="clear:both;"></div>';
				    } 
		   };
		   $('#filterName'+b).magicSuggest(opts);
		   $($('#filterName'+b).magicSuggest(opts)).on('beforeload', function(e, s){
				var $tr = $('#filterName'+b).closest('tr');
		        var attrSemantic = $tr.find(".dataTemplateColumnId").find("option:selected").attr("attr-semantic");
		        if(null != attrSemantic || undefined != attrSemantic)
		        {
		        	s.setDataUrlParams({semantic:attrSemantic});
		        }
		        else
		        {
		        	s.setDataUrlParams({semantic:''});
		        }
			}); 
		   $('#defaultValue'+b).magicSuggest(opts);
		   $($('#defaultValue'+b).magicSuggest(opts)).on('beforeload', function(e, s){
				var $tr = $('#defaultValue'+b).closest('tr');
		        var attrSemantic = $tr.find(".dataTemplateColumnId").find("option:selected").attr("attr-semantic");
		        if(null != attrSemantic || undefined != attrSemantic)
		        {
		        	s.setDataUrlParams({semantic:attrSemantic});
		        }
		        else
		        {
		        	s.setDataUrlParams({semantic:''});
		        }
			}); 
	}
	var addTemp = $("#addTemp").html();
	$('body').on('click', '.add', function(){
		   $('.table tbody').append(addTemp);
		   var b = getIndex();
		   loadSelect(b);
		   loadMagicSuggest(b);
		}).on('click','.remove',function(){
			   	$(this).parent().parent().remove();
			   	getIndex();
			   	msRemoveIndex();
			}).on('blur', '.columnIndex', function(){
			    		var $tr = $(this).closest('tr');
				        var val = $(this).val();
				        var $that = $(this);
				        if(isNaN(val)){
				        	parent.parent.layer.alert('列索引必须为数字', {title : "提示"});
				            $that.val("");
				        }
				        var index = $tr.find('.index').html();
				       	$('.table tbody tr').each(function(i,v){
				       		//var value = $(this).find('.columnIndex').val();
				       		//if("" != val && i != (index - 1) && value == val){
				       		//	parent.layer.alert('列索引已存在，重新输入',{title:"提示"});
				       		//	$that.val('');
				       		//}
				       	});
				    }).on('blur', '#name', function(){
				    		var id = $('#id').val();
				    		var name = $('#name').val();
				    		var $that = $(this);
					    	$.ajax({
					            type: "POST",
					            url: base+"/claimTemplate/validate.do",
					            data: {id : id , name : name},
					            dataType: "json",
					            success: function(data){
					                if(!data){
					                    parent.parent.layer.alert('分析要求已存在，请重新输入', {title : "提示"});
					                    $that.val('');
					                }
					            }
					        })
				    	}).on('change','#templateId',function(){
				    		var templateId = $(this).val();
				    		if(null != templateId && "" != templateId)
				    		{
				    			$(".dataTemplateColumnId").empty();
				    			$(".dataTemplateColumnId").append("<option value=\"\">--请选择--</option>");
				    			$.ajax({
						            type: "POST",
						            url: base+"/biAnalysis/dataTemplate/getColumnList.do",
						            data: {templateId : templateId},
						            dataType: "json",
						            success: function(data){
						            	var opts = "";
						            	for(var i = 0;i<data.length;i++)
						            	{
						            		opts += "<option value=\""+data[i].id+"\" attr-semantic=\""+data[i].semantic+"\" attr-type=\""+data[i].type+"\">"+data[i].columnName+"</option>";
						            	}
						            	$(".dataTemplateColumnId").append(opts);
						            }
						        })
				    		}
				    		else
				    		{
				    			$(".dataTemplateColumnId").empty();
				    			$(".dataTemplateColumnId").append("<option value=\"\">--请选择--</option>");
				    		}
				    	}).on('change','.dataTemplateColumnId',function(){
				    		var $tr = $(this).closest('tr');
					        var attrType = $(this).find("option:selected").attr("attr-type");
					        var attrSemantic = $(this).find("option:selected").attr("attr-semantic");
					        if(attrType=="3")
					        {
					        	$tr.find('.moveDiv').hide();
					        	$tr.find('.defaultValueMoveDiv').hide();
					        	$tr.find('.radioDiv').hide();
					        	$tr.find('.rangeDiv').show();
					        }
					        else
					        {
					        	if(attrType=="1")
					        	{
					        		$tr.find('.defaultValueMoveDiv').show(); 
					        		$tr.find('.radioDiv').hide();
					        		$tr.find('.rangeDiv').hide();
					        	}
					        	else if(attrType=="2")
					        	{
					        		$tr.find('.defaultValueMoveDiv').hide();
					        		$tr.find('.radioDiv').show();
					        		$tr.find('.rangeDiv').hide();
					        		$.ajax({
					    	            type: "POST",
					    	            url: base+"/claimTemplate/getItemList.do",
					    	            data: {semantic : attrSemantic},
					    	            dataType: "json",
					    	            success: function(data){
					    	            	var opts = "";
					    	            	for(var i = 0;i<data.length;i++)
					    	            	{
					    	            		opts += "<option value=\""+data[i].name+"\" >"+data[i].name+"</option>";
					    	            	}
					    	            	$tr.find(".radioValue").append(opts);
					    	            }
					    	        })
					        	}
					        	else
					        	{
					        		$tr.find('.defaultValueMoveDiv').hide();
					        		$tr.find('.radioDiv').hide();
					        		$tr.find('.rangeDiv').hide();
					        	}
					        	$tr.find('.moveDiv').css('display','table'); 
					        }
					        var index = $tr.find('.index').text()-1;
				        	$("#filterName"+index).magicSuggest().clear();
				        	$("#filterName"+index).blur();
				        	var $td = $tr.find('td').eq(3);
				        	$td.find("input").each(function(){  
				                $(this).val('');  
				            });  
				        	$td.find("select").each(function(){  
				        		$(this).val('');
				            });
				    	});
	
	$("#claimTemplate_form").validate();
});