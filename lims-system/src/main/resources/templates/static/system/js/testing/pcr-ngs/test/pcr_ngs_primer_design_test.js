function suc(obj) {
    var dataid = $(obj).attr("data-id");
    console.log(dataid);
    if ($(obj).is(':checked')) {
        $("#info" + dataid).css("display", "none");
    } else {
        $("#info" + dataid).css("display", "block")
    }
}

function clearClass() {
    $('.fileinput-upload-button').remove();
    $('.file-preview').remove();
    $('.fileinput-remove').remove();
}
$(".remark").blur(function(){
    var a= $(this).attr("data-index");
    var data=$(this).val();
    $("#remark"+a).val(data);
 })
$(".dealWith").click(function(){
   var b= $(this).attr("data-index");
   var radioData=$(this).val();
   $("#qcResult"+b).val(radioData);
});

//导出数据
function downloadData(){
	var sheetId = $("#id").val();
	$.ajax({
		 type:"POST",
		 traditional: true,
		 url:"/testSheet/downloadPcrNgsPrimerDesignData",
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
	    url: '/testSheet/uploadPrimerDesign',
	    type: 'POST',
	    cache: false,
	    data: new FormData($('#uploadForm')[0]),
	    processData: false,
	    contentType: false
	}).done(function(list) {
		 if(list==''){
			  parent.layer.alert('Excel页面没有数据！',{title:"提示"});
		  }else{
          
	          $('#uploadDataTable tbody tr').each(function(m, v) {
	        	  if(null != list[m] && "" != list[m]){
	        		  $(this).find(".abnormalReason").empty();
	        		  
	        		  $(this).find(".gene").text(list[m][1]);
	            	  $(this).find(".codingExon").text(list[m][2]);
	            	  $(this).find(".chromosomeNumber").text(list[m][3]);
	            	  $(this).find(".chromosomeLocation").text(list[m][4]);
	            	  $(this).find(".pcrStartPoint").text(list[m][5]);
	            	  
	            	  $(this).find(".pcrEndPoint").text(list[m][6]);
	            	  $(this).find(".forwardPrimerName").text(list[m][7]);
	            	  $(this).find(".forwardPrimerSequence").text(list[m][8]);
	            	  $(this).find(".reversePrimerName").text(list[m][9]);
	            	  $(this).find(".reversePrimerSequence").text(list[m][10]);
	            	  
	            	  $(this).find(".result").text(list[m][11]);
	            	  $(this).find(".remark").text(list[m][12]);
	            	  $(this).find(".abnormalReason").text(list[m][13]);
	            	  
	            	  //对input隐藏标签赋值
	            	  $(this).find(".geneInput").val(list[m][1]);
	            	  $(this).find(".codingExonInput").val(list[m][2]);
	            	  $(this).find(".chromosomeNumberInput").val(list[m][3]);
	            	  $(this).find(".chromosomeLocationInput").val(list[m][4]);
	            	  $(this).find(".pcrStartPointInput").val(list[m][5]);
	            	  
	            	  $(this).find(".pcrEndPointInput").val(list[m][6]);
	            	  $(this).find(".forwardPrimerNameInput").val(list[m][7]);
	            	  $(this).find(".forwardPrimerSequenceInput").val(list[m][8]);
	            	  $(this).find(".reversePrimerNameInput").val(list[m][9]);
	            	  $(this).find(".reversePrimerSequenceInput").val(list[m][10]);
	            	  if("成功" == list[m][11]){
	            		  $(this).find(".resultInput").val("0");
	            	  }else{
	            		  $(this).find(".resultInput").val("1");
	            	  }
	            	  $(this).find(".remarkInput").val(list[m][12]);
	            	  
	            	  var $tr = $(this).closest('tr');
	  				  var abnormalReason = $tr.find('.abnormalReason').html();
	            	  if(null != abnormalReason && "" != abnormalReason){
	            		  $(this).css('background-color','#FF9797');
	            	  }else{
	            		  $(this).css('background-color', '');
	            	  }
	        	   }
	           });
	          $('#uploadTableName').show();
	          $('#uploadTable').show();
		  }
		 $('#myModal').modal('hide');
	}).fail(function(res) {});
});

//提交实验结果校验
$(function()
{
    $('body').on('click', '#button_submit', function()
    {
    	var flag=true;
        $('#uploadDataTable tbody tr').each(function(i, v){
        	 var reason = $(this).find(".abnormalReason").text();
        	 var chromosomeNumber = $(this).find(".chromosomeNumber").text();
        	 var pcrStartPoint = $(this).find(".pcrStartPoint").text();
        	 var pcrEndPoint = $(this).find(".pcrEndPoint").text();
        	 var forwardPrimerName = $(this).find(".forwardPrimerName").text();
        	 var forwardPrimerSequence = $(this).find(".forwardPrimerSequence").text();
             if(reason != null && reason != ""){
                 parent.layer.alert('请先处理异常记录',{title:"提示"});
                 flag=false;
             }
             if(""==chromosomeNumber && ""==pcrStartPoint && ""==pcrEndPoint && ""==forwardPrimerName && ""==forwardPrimerSequence){
            	 parent.layer.alert('未上传结果数据或上传数据与任务数量不匹配',{title:"提示"});
                 flag=false;
             }
        });
    	
        if(flag){
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
