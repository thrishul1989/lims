$(function()
{
    $("#testing_task_form").validate({
        submitHandler: function(form){
            
            $("#testerName").val($("#extract_tester option:selected").text());
            $("#qcTesterName").val($("#qcTesterId option:selected").text());
            $("#InputQuantity").val($(".inputQuantity").val());
            var values = new Array();
            console.info($("#testing_task_detail_table").serializeArray());
            $(".getTr").each(function(index,object){
                    var tr = $(this);
                    var data  = {};
                    data.testCode=tr.find(".testCode").text();
                    data.sampleCode=tr.find(".sampleCode").text();   //实验编号 
                    data.sampleTypeName = tr.find(".sampleTypeName").text(); //样本主键
                    data.products = tr.find(".products").text();
                    data.inspectName = tr.find(".inspectName").text(); //目标样本编号
                    data.samplingDate = tr.find(".samplingDate").text();
                    data.notes= tr.find(".getNotes").text();
                    data.inputQuantity=tr.find(".input-quantity").val();
                    data.testingTaskId=tr.find(".testingTaskId").text();
                    values.push(data);
                tscr={};
                tscr.sendName=$("#sendName").val();
                tscr.sendDate=$("#sendDate").val();
                tscr.testingName=$("#testingName").val();
                var a="";
                $(".method").each(function(){
                    if ($(this).is(":checked")) {
                    a+=$(this).val()+",";
                    tscr.method= a;
                    }
                });
                tscr.description=$("#description").val();
            });
            var content=JSON.stringify(values);
            $("#content").val(content);
            document.forms["testing_task_form"].submit();
        },
        rules: {
            qcTesterId: "required",
            testerId: "required",
            qualityControlMethod:"required"
        },
        messages: {
            qcTesterId: "请选择质检负责人",
            testerId: "请选择提取负责人",
            qualityControlMethod:"请选择质检方法"
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.parent().parent());  
        }
    }); 
    
    $(".noteTr").hide();
    
    $('body').on('change', '#reagentKit', function()
    {
        var selected = $(this).find('option:selected');
        var quantity = selected.attr('data-input-quantity');

        if (undefined == quantity)
        {
            quantity ='';
        }
        
        $('.input-quantity').val(quantity);
    });
    
    $(function(){
        $("#按钮id").click(function(){//按钮绑定点击事件
            $("#文本框id").show();//文本框显示出来  注：之前设置display:none的样式
        });
    });
    
    
    $(".btn").click( function(){
        var flag=$(this).val();
        if(flag=='备注'){
        $(this).parents(".getTr").next('tr').show();
        $(this).parents(".getTr").next('tr').children('td').append("<textarea class='form-control notes'  style='width: 100%; margin-bottom: 5px;' name='description' id='description' ></textarea>");
        $(this).parents(".getTr").next(".notes").val($(this).next().val());
        $(this).val("保存");
        }
        if(flag=='保存')
        {
        $(this).next().val($(this).parents(".getTr").next(".notes").val());
        $(this).parents(".noteTr").find(".notes").remove();
        $(this).parents(".getTr").next('tr').hide();
        $(this).val("修改");
        }
        if(flag=='修改')
        {
        $(this).parents(".getTr").next('tr').show();
        $(this).parents(".getTr").next(".notes").val($(this).next().val());
        $(this).val("保存");
        }
    } );
});
