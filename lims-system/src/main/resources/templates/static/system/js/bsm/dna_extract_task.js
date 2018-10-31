$(function()
{
	$("#task_form").validate();

$('.dataSize').each(function(){
    var value=$(this).parents('.inputType').find('.inputSampleValue').val();
    var $this=$(this).parents(".inputType").find('.dataSize');
    $.ajax({
        url : '/sample/getUnit.do',
        traditional : true,
        type : "POST",
        data : {
            id : value,
        },
        async : false,
        success : function(data)
        {
            $this.text("样本投入量("+data+")");
        }, error : function()
        {
            parent.layer.alert('错误', {
                title : "提示"
            });
        }
    })
     });

    
    $(document).on('change','.reagentKitValue',function () {  
        var a=[];
//        $(this).parents(".reagentKit").siblings().css("background-color",'red');
        var $sib =$(this).parents(".reagentKit").siblings();
        $sib.each(function(){
            a.push($(this).find('option:selected').val());
        })
        if($.inArray($(this).val(),a)!=-1){
            $(this).val('');
            parent.layer.alert('同一个任务输入,试剂盒不能重复', {
                title : "提示"
            });
        }
    });  
  



    $(document).on('change','.inputSampleValue',function(){
           var value=$(this).val();
           var $this=$(this).parents(".inputType").find('.reagentKit');
           $.ajax({
               url : '/sample/getUnit.do',
               traditional : true,
               type : "POST",
               data : {
                   id : value,
               },
               async : false,
               success : function(data)
               {
                   $this.find('.dataSize').each(function(){
                       $(this).text("样本投入量("+data+")");
                   }) 
               }, error : function()
               {
                   parent.layer.alert('错误', {
                       title : "提示"
                   });
               }
           })
       })
    
    
    $("input[name='outputVolume']").blur(function(){
        if(!$.isNumeric($(this).val()) && "" != $(this).val()){
            parent.layer.alert('请输入合法数字', {
                title : "提示"
            });
            $(this).val("");
        }
    });
    
    $(document).on('blur',"input[name='input_quantity']",function(){
        if(!$.isNumeric($(this).val())){
            parent.layer.alert('请输入合法数字', {
                title : "提示"
            });
            $(this).val("");
        }
    });
    
    $(document).on('blur',"input[name='input_volume']",function(){
        if(!$.isNumeric($(this).val())){
            parent.layer.alert('请输入合法数字', {
                title : "提示"
            });
            $(this).val("");
        }
    });
    var addHtml = $("#taskInput_add").html();
    $('body').on('click', '.addInputSample', function(){
       $(this).parents(".inputType").after(addHtml);
    }).on('click','.removeInputSample',function(){
        var inputSize=$(".inputSample").length; 
        if(inputSize>1){
        $(this).parent().parent().remove();
        }else{
            alert("至少一个任务输入");
        }
    }).on('click',".addReagentKit" ,function(){
        var addReagentKit=$("#template").html();
        $(this).parents(".reagentKit").after(addReagentKit);
        
        var value=$(this).parents('.inputType').find('.inputSampleValue').val();
        var $this=$(this).parents(".inputType").find('.dataSize');
        $.ajax({
            url : '/sample/getUnit.do',
            traditional : true,
            type : "POST",
            data : {
                id : value,
            },
            async : false,
            success : function(data)
            {
                $this.text("样本投入量("+data+")");
            }, error : function()
            {
                parent.layer.alert('错误', {
                    title : "提示"
                });
            }
        })
        
    }).on('click','.removeReagentKit', function(){
        var reagentSize=$(this).parents(".inputType").find(".removeReagentKit").length; 
        if(reagentSize>1){
        $(this).parents(".reagentKit").remove();
        }else{
            alert("至少一个试剂盒");  
        }
    }).on('click',"#btn",function(){
       taskInputList=[];
       $(".reagentKitValue").each(function(){
           taskInput={};
           taskInput.reagentKitId= $(this).val();
           taskInput.inputSampleId=$(this).parents(".inputType").find(".inputSampleValue").val();
           taskInput.inputSize=$(this).parents(".reagentKit").find(".inputSize").val();
           taskInput.inputVolume=$(this).parents(".reagentKit").find(".inputVolume").val();
           taskInputList.push(taskInput);
       })
       var content=JSON.stringify(taskInputList);
       $("#inputContent").val(content);
       var flag=true;
       $('.inputSize').each(function(){
           if($(this).val()==''){
               parent.layer.alert('请保证输入样本投入量有值',{
                   title:'提示' 
                }); 
               flag=false;
           }
       })
       if(flag)
           {
               $("#task_form").submit();
           }
    })
   
});

