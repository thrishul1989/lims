$(function(){
	function getIndex(){
		$('.table tbody tr').each(function(i,v){
			$(this).find('.index').empty().text(i+1);
			$(this).find('.id').attr('name','columnList['+i+'].id');
			$(this).find('.columnIndex').attr('name','columnList['+i+'].columnIndex');
			$(this).find('.columnName').attr('name','columnList['+i+'].columnName');
			$(this).find('.type').attr('name','columnList['+i+'].type');
			$(this).find('.semantic').attr('name','columnList['+i+'].semantic');
			$(this).find('.groupName').attr('name','columnList['+i+'].groupName');
		});
	}
	
	var addTemp = $("#addTemp").html();
	$('body').on('click', '.add', function(){
		   $('.table tbody').append(addTemp);
		   getIndex();
		}).on('click','.remove',function(){
			   	$(this).parent().parent().remove();
			   	getIndex();
			}).on('blur', '.columnIndex', function(){
			    		var $tr = $(this).closest('tr');
				        var val = $(this).val();
				        var $that = $(this);
				        if(isNaN(val)){
				        	parent.parent.layer.alert('列索引必须为数字', {title : "提示"});
				            $that.val("");
				        }
				        var index = $tr.find('.index').html();
				       	
				    }).on('blur', '#name', function(){
				    		var id = $('#id').val();
				    		var name = $('#name').val();
				    		var $that = $(this);
					    	$.ajax({
					            type: "POST",
					            url: base+"/biAnalysis/dataTemplate/validate.do",
					            data: {id : id , name : name},
					            dataType: "json",
					            success: function(data){
					                if(!data){
					                    parent.parent.layer.alert('模板名称已存在，请重新输入', {title : "提示"});
					                    $that.val('');
					                }
					            }
					        })
				    	});
	
	$("#dataTemplate_form").validate();
});