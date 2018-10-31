
function clearClass() {
    $('.fileinput-upload-button').remove();
    $('.file-preview').remove();
    $('.fileinput-remove').remove();
}

//导出数据
function downloadData(){
	var sheetId = $("#id").val();
	$.ajax({
		 type:"POST",
		 traditional: true,
		 url:"/testing/downloadMultipcrqc",
		 data:{sheetId : sheetId},
		 async: false,
		 success:function(data){
			$("#formValue").val(data);
			$("#hiddForm").submit();
	    },
		 error:function(){
			alert("failed");
		 }
	}); 
}

$('body').on('click', '#button_submit', function()
        {
            
            var layerObject = parent.parent.layer;
            var buttons = [ '确定', '取消' ];

            layerObject.confirm('确定完成任务，提交任务单吗？', {
                icon : 3,
                title : '操作确认',
                btn : buttons,
                shade : 'transparent'
            }, function(index)
            {
                var flag = true;
                var checknull = $(".checknull:visible");
                $.each(checknull, function(index, check)
                {//判断必填
                    if ($(check).val() == null || $(check).val() == "")
                    {
                        parent.layer.alert('请输入必填项', {
                            title : "提示"
                        });
                        $(check).addClass('addEroBorder');
                        flag = false;
                        return false;
                    }
                    else
                    {
                        if ($(check).hasClass('addEroBorder'))
                        {
                            $(check).removeClass('addEroBorder');
                        }
                    }
                })
                if (flag)
                {
                    $('#submit_form').submit();
                    layerObject.close(index);
                }
            }, function(index)
            {
                layerObject.close(index);
            });
        });


//导入解析excel
$("#butt").click(function(){

    var excelFileName = $('#uploadData').val();
	var formatStr = '';
	var index = excelFileName.lastIndexOf('.');
	if(excelFileName.length == 0){
		parent.layer.alert('请选择需要上传的文件',{title:"提示"});
		return ;
	}else if(index > 0){
		formatStr = excelFileName.substring(index);
		if(!(".xlsx" == formatStr||".xls" == formatStr)){
			parent.layer.alert('请上传excel文件',{title:"提示"});
			return ;
		}
	}
	   
  $.ajax({
	    url: '/testing/uploadMultipcrqc',
	    type: 'POST',
	    cache: false,
	    data: new FormData($('#uploadForm')[0]),
	    processData: false,
	    contentType: false
	}).done(function(list) {
		
		 if(list==''){
			  parent.layer.alert('Excel页面没有数据！',{title:"提示"});
		  }else{
              
			  $('#uploadDataTable tbody').empty();
	          $('#uploadTableName').show();
	          $('#uploadTable').show();
	          $('.concn').each(function(index,obj){
	              $(obj).text(list[index].concn);
	              $(obj).next().val(list[index].concn);
                  $(obj).next().attr('name',"tasks["+index+"].concn");
	          })
	         $('.volumn').each(function(index,obj){
                  $(obj).text(list[index].volume);
                  $(obj).next().val(list[index].volume);
                  $(obj).next().attr('name',"tasks["+index+"].volume");
              })
              $('.a260280').each(function(index,obj){
                  $(obj).text(list[index].ratio2628);
                  $(obj).next().val(list[index].ratio2628);
                  $(obj).next().attr('name',"tasks["+index+"].ratio2628");
              })
              $('.a260230').each(function(index,obj){
                  $(obj).text(list[index].ratio2623);
                  $(obj).next().val(list[index].ratio2623);
                  $(obj).next().attr('name',"tasks["+index+"].ratio2623");
              })
	            $('.qualityLevel').each(function(index,obj){
                  $(obj).text(list[index].qualityLevel);
                  $(obj).next().val(list[index].qualityLevel);
                  $(obj).next().attr('name',"tasks["+index+"].qualityLevel");
                  if(list[index].qualityLevel=='D' || list[index].qualityLevel=='C'){
                      var $checkb=  $(obj).next().next().find('.toggle-qualified');
                      $checkb.attr("checked",false);
                      $checkb.iCheck("update");
                      $("#unqualified_"+$checkb.attr('data-id')).show(); 
                    }
              })
		  }
		 $('#myModal').modal('hide');
	}).fail(function(res) {});
});

// 不合格详情联动显示
$('.toggle-qualified').on('ifChecked', function()
{
    var code = $(this).attr('data-id');
    $('#unqualified_' + code).hide();
});

$('.toggle-qualified').on('ifUnchecked', function()
{
    var code = $(this).attr('data-id');
    $('#unqualified_' + code).show();
});




//提交实验结果校验
$(function()
{
    
   
    $('body').on('click', '#button_submit', function()
    {
    	
        
        if(true){
        	var layerObject = parent.parent.layer;
        	var buttons = [ '确定', '取消' ];
        	
        	layerObject.confirm('确定完成任务，提交任务单吗？', {
        		icon : 3,
        		title : '操作确认',
        		btn : buttons,
        		shade : 'transparent'
        	}, function(index)
        	{
    			$('#submit_form').submit();
    			layerObject.close(index);
        	}, function(index)
        	{
        		layerObject.close(index);
        	});
        }
    });
});
