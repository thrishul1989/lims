$(function()
{
    $('#match').click(function(){
      var chromosomeNumber=  $("input[name='chromosomeNumber']").val();//染色体编号
      var pcrPoint=  $("input[name='pcrPoint']").val()//染色体位置1  
      var applicationType=$("select[name='applicationType']").find('option:selected').val();
      var applicationType1=$("select[name='applicationType']").find('option:selected').text();
      var productCode=$("input[name='productCode']").val();
      if(applicationType==''){
          parent.layer.alert('请选择应用类型',{title:"提示"});
          return false;
      }else{
          if(applicationType1=='Sanger验证'||applicationType1=='PCR-NGS'){
              if(chromosomeNumber=='' || pcrPoint==''){
                  parent.layer.alert('请输入染色体编号和染色体体位置1', {
                      title : "提示"
                  });
                  return false;
              }
          }/*else{
              if(productCode==''){
                  parent.layer.alert('请输入产品编号',{title:"提示"});
                  return false;
              }
          }*/
          if(pcrPoint==''){
              parent.layer.alert('请输入染色体体位置1',{title:"提示"});
              return false;
          }
          if(chromosomeNumber==''){
              parent.layer.alert('请输入染色体编号',{title:"提示"});
              return false;
          }
          if(isNaN(pcrPoint)){
              parent.layer.alert('染色体体位置1必须为数字',{title:"提示"});
              return false;
          }
      }
    })
    
    $("#search").click(function(){
        var pcrPoint=  $("input[name='pcrPoint']").val() ;
        if(isNaN(pcrPoint)){
            parent.layer.alert('染色体体位置1必须为数字',{title:"提示"});
            return false;
        }
    })
    
    
    
    $('body').on('click', '#primerUpload', function()
    {
        $('#dialog_content').attr('src', '/primer/primer_design.do');
        $('#assign_dialog').modal('show');
    }).on('click', '.check-controller', function()
	{
        if ($(this).is(":checked"))
        {
            $(".check-instance").prop("checked", true);
        }
        else
        {
            $(".check-instance").prop("checked", false);
        }
    }).on('click', '.check-instance', function()
    {
        if (!$(this).is(":checked"))
        {

            $(".check-controller").prop("checked", false);
        }
        else
        {
            var chknum = $(".check-instance").size();// 选项总个数
            var chk = $('input[type=checkbox]:checked').length;// 选中总数
            if (chknum == chk)
            {
                $(".check-controller").prop("checked", true);
            }
            else
            {
                $(".check-controller").prop("checked", false);
            }
        }

    });
    
    $('#released').on('click', function()
    {    
        $('iframe#dialog_content')[0].contentWindow.validata();
        if($("#default").html()=="success"){
            $('iframe#dialog_content')[0].contentWindow.$('#submit_form').submit();
        }
    })
    
    
});

//导出样本编号
function downloadCode() {

    var instances = $('.check-instance');
    var ids = [];
    instances.each(function() {
        if ($(this).is(":checked")) {
            ids.push($(this).val());
        }
    });
    var flag = true;
    if(ids.length == 0)
    {
        parent.layer.alert("请至少选择一条数据",{title:"提示"});
        flag = false;
    }

    if(flag){
        // var sheetId = $('input[name="ids"]').val(ids.join(','));
        var sheetId = ids.join(',');
        console.info(sheetId);
        console.info("aaa:"+sheetId);
        $.ajax({
            type : "POST",
            traditional: true,
            url : "/primer/downloadPrimer",
            data : {
                id : sheetId
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

}