$(function()
{
    $('#match').click(function(){
      var chromosomeNumber=  $("input[name='chromosomeNumber']").val();
      var pcrPoint=  $("input[name='pcrPoint']").val()  
              if(chromosomeNumber=='' || pcrPoint==''){
                  parent.layer.alert('请输入染色体编号和染色体体位置1', {
                      title : "提示"
                  });
                  return false;
              }
          if(isNaN(pcrPoint)){
              parent.layer.alert('染色体体位置1必须为数字',{title:"提示"});
              return false;
          }
    })
});


//导出样本编号
function downloadCode() {
  var sheetId = $("#id").val();
  $.ajax({
      type : "POST",
      traditional: true,
      url : "/primerReferenceLibrary/downloadPrimerDatum",
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