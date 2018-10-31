$(function()
{
    $('body').on('click', '.check-controller', function()
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
            var chknum = $(".check-instance").size();//选项总个数
            var chk = $('input[type=checkbox]:checked').length;//选中总数
            if(chknum==chk){
                $(".check-controller").prop("checked", true);
            }else{
                $(".check-controller").prop("checked", false);
            }
        }

    }).on('click', '#btn_upload', function(){
    	 $('#dialog_content').attr('src', '/bmm/defaultInvoice/uploading.do');
         $('#data_dialog').modal('show');
    });

    $('#released').on('click', function()
    {
    	var flag = true;
    	$('iframe#dialog_content')[0].contentWindow.$('iframe#impotFrame')[0].contentWindow.$('.record-invalid').each(function(){
    		flag = false;
    	});
    	if(flag){
    		$('iframe#dialog_content')[0].contentWindow.$('iframe#impotFrame')[0].contentWindow.$('#import_form').submit();
    	}else{
    		parent.parent.layer.alert("请先处理异常数据",{title:"提示"});
    	}
    })
});

function downloadData()
{

    var code = $("#code").val();
    var testingType = $("#testingType").val();
    var ownerName = $("#ownerName").val();
    var creatorName = $("#creatorName").val();
    var contractCode = $("#contractCode").val();
    var solveStatus = $("#solveStatus").val();
    var instances = $('.check-instance');
    var isFullPay = $("#isFullPay").val();
    var keys = [];
    instances.each(function()
    {
        if($(this).is(":checked"))
        {
            keys.push($(this).val());
        }
    });
    $.ajax({
        type : "POST",
        traditional : true,
        timeout:0,
        url : "/bmm/defaultInvoice/download.do",
        data : {
        	code : code,
        	testingType : testingType,
        	ownerName : ownerName,
        	creatorName : creatorName,
        	contractCode : contractCode,
        	solveStatus : solveStatus,
            keys:keys.join(','),
        	isFullPay:isFullPay
        },
        async : false,
        success : function(data)
        {
            $("#formValue").val(data);
            $("#hiddForm").submit();
        },
        error : function()
        {
            alert("failed");
        }
    });
}

function delayAdvanceInvoice(id)
{
    var layerObject = parent.parent.layer;
    var buttons = [ '确定', '取消' ];
    layerObject.confirm('确认将该延迟付款申请的订单，同步到金税系统开票？', {
        icon : 3,
        title : '操作提示',
        btn : buttons,
        shade : 'transparent'
    },  function(index)
    {
        layerObject.close(index);
        $.ajax({
            type : "POST",
            traditional : true,
            timeout:0,
            data : {
                id : id,
            },
            url : "/bmm/defaultInvoice/delayAdvanceInvoice.do",
            async : false,
            success : function(data)
            {
                window.location.href="/bmm/defaultInvoice/selectList.do?solveStatus=4"
                layerObject.alert("同步成功！")
            },
            error : function()
            {
                layerObject.alert("failed");
            }
        });
    }, function(index)
    {
        layerObject.close(index);
    });

}


