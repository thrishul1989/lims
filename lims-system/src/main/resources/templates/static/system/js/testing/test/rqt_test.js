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
function downloadData() {
    var sheetId = $("#id").val();
    var ctv=[];
    $(".ctv").each(function(){
    	ctv.push($(this).val());
    });
    $.ajax({
        type : "POST",
        traditional: true,
        url : "/testSheet/downloadRQTData",
        data : {
            sheetId : sheetId,
            ctv:ctv,
        },
        async : false,
        success : function(data) {
            $("#formValue").val(data);
            $("#hiddForm").submit();
        },
        error : function() {
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
	    url: '/testSheet/uploadRQTctv',
	    type: 'POST',
	    cache: false,
	    data: new FormData($('#uploadForm')[0]),
	    processData: false,
	    contentType: false
	}).done(function(list) {
		 if(list==''){
			  parent.layer.alert('Excel页面没有数据！',{title:"提示"});
		  }else{
              var code = list[0];
              var ctv = list[1];
              var flag = false;
              $('#dataTable tbody tr').each(function(i, v) {
                  var value =$(this).find("td").eq(0).text();
                  for (var m = 0; m < code.length; m++) {
                       if (code[m] == value)
                       {
                           flag = true;
                           if(null != ctv[m] && "" != ctv[m]){
                        	   $(this).find(".ctv").val(parseFloat(ctv[m]).toFixed(2));
                           }
                      }
                  }
               });
               if (!flag) {parent.layer.alert('存在不匹配的实验编号！', { title : "提示"});
               }
		  }
		 $('#myModal').modal('hide');
	}).fail(function(res) {});
});
