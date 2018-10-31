
$(function(){
    
    $(".print").click(function(){
        var id=$(this).next().val();
        $("#create_task_form").attr("src", "/returnSample/print.do?id=" +id);
        setTimeout(function () { 
            
            $('#create_task_form')[0].contentWindow.myPreview();
        },1000);
        
    })
    
    
})