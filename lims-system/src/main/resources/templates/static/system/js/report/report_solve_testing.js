var tipMessage ="确定保存样本结果吗？";
$(function()
{
	var checkRecordIds = $('#checkRecordIds').val();
    var isFamilySample = $("#isFamilySample").val();

    if (null !=isFamilySample && isFamilySample!="" && isFamilySample == "true")
    {
        $("#btn_testing_sample").hide();//家系检测样本保存样本结果操作隐藏
        $('.toggle-qualified').each(function(){
            $(this).attr("readonly","readonly");
            $(this).attr("disabled","disabled");
        });
    }

	$('.check-instance').each(function()
    {
		var pId = $(this).val().trim();
		if(checkRecordIds.indexOf(pId) > -1){
			$(this).prop("checked", true);
		}
    });
	
	$('.toggle-qualified').each(function(){
        if(!$(this).is(':checked')){
        	$('#btn_testing_sample').html('不合格上报');
        	tipMessage = "确定上报不合格样本吗？";
        }
    });
	
    // 不合格详情联动显示
    $('.toggle-qualified').on('ifChecked', function()
    {
        var id = $(this).attr('data-id');
        $('#unqualified_' + id).hide();
        var flag = true;
        $('.toggle-qualified').each(function(){
            if(!$(this).is(':checked')){
                flag = false;
            }
        });
        if(flag)
        {
        	$('#btn_testing_sample').html('保存样本结果');
        	tipMessage ="确定保存样本结果吗？";
        }
    });

    $('.toggle-qualified').on('ifUnchecked', function()
    {
        var id = $(this).attr('data-id');
        $('#unqualified_' + id).show();
        $('#btn_testing_sample').html('不合格上报');
        tipMessage = "确定上报不合格样本吗？";
    });

    var asUnqualified = function($tr)
    {
        var $toggle = $tr.find(".toggle-qualified");
        var id = $toggle.attr('data-id');
        $toggle.prop("checked", false);
        $toggle.iCheck("update");
        $('#unqualified_' + id).show();
    }

    var asQualified = function($tr)
    {
        var $toggle = $tr.find(".toggle-qualified");
        var id = $toggle.attr('data-id');
        $toggle.prop("checked", true);
        $toggle.iCheck("update");
        $('#unqualified_' + id).hide();
    }

    
    $('body').on('click', '.check-controller', function()
	{
		if ($(this).is(":checked"))
		{
			$(".check-instance").prop("checked", true);
			var semantic = $('input[name=semantic]').val();
			$(".check-instance").each(function()
			{
				var recordId=$(this).val();
			    if(semantic=='DT'){
			    	if($.inArray(recordId, parent.previewParams.dtDetectionResultIds)<0){
					    parent.previewParams.dtDetectionResultIds.push(recordId);
				  		var $tr = $(this).closest('tr');
		        		var clinicalJudgment = $tr.find('.clinicalJudgment').val();
					    parent.previewParams.dtDetectionResultClinicals.push({"id":recordId,"clinicalJudgment":clinicalJudgment});
				    } 
				   }else if(semantic=='MLPA-TEST'){
					   if($.inArray(recordId, parent.previewParams.mlpaDetectionResultIds)<0){
						    parent.previewParams.mlpaDetectionResultIds.push(recordId);
					  		var $tr = $(this).closest('tr');
			        		var clinicalJudgment = $tr.find('.clinicalJudgment').val();
						    var mutationSource = $tr.find('.mutationSource').val();
						    parent.previewParams.mlpaDetectionResultClinicals.push({"id":recordId,"clinicalJudgment":clinicalJudgment,"mutationSource":mutationSource});
					    }
				   }
			});
		}
		else
		{
			var semantic = $('input[name=semantic]').val();
			$(".check-instance").prop("checked", false);
			$(".check-instance").each(function()
			{
			    if(semantic=='DT'){
			    	if($.inArray(recordId, parent.previewParams.dtDetectionResultIds)>-1){
			    		var idIndex=$.inArray(recordId, parent.previewParams.dtDetectionResultIds);
						parent.previewParams.dtDetectionResultIds.splice(idIndex, 1);
						parent.previewParams.dtDetectionResultClinicals.splice(idIndex,1);
				    }
				   }else if(semantic=='MLPA-TEST'){
					   if($.inArray(recordId, parent.previewParams.mlpaDetectionResultIds)>-1){
						    var idIndex=$.inArray(recordId, parent.previewParams.mlpaDetectionResultIds);
							parent.previewParams.mlpaDetectionResultIds.splice(idIndex, 1);
							parent.previewParams.mlpaDetectionResultClinicals.splice(idIndex,1);
					    }
				 }
				
			});
		}
    }).on('click', '.check-instance', function()
    {
		if (!$(this).is(":checked"))
	    {
	       $(".check-controller").prop("checked", false);
		   var recordId=$(this).val();
		   var semantic = $('input[name=semantic]').val();
		   if(semantic=='DT'){
			  if($.inArray(recordId, parent.previewParams.dtDetectionResultIds)>-1){
				 var idIndex= $.inArray(recordId, parent.previewParams.dtDetectionResultIds);
				 parent.previewParams.dtDetectionResultIds.splice(idIndex, 1);
				 parent.previewParams.dtDetectionResultClinicals.splice(idIndex,1);
			} 
		   }else if(semantic=='MLPA-TEST'){
			   if($.inArray(recordId, parent.previewParams.mlpaDetectionResultIds)>-1){
				     var idIndex=$.inArray(recordId, parent.previewParams.mlpaDetectionResultIds);
					 parent.previewParams.mlpaDetectionResultIds.splice(idIndex, 1);
					 parent.previewParams.mlpaDetectionResultClinicals.splice(idIndex,1);
				}
		   }
	    }else{
	    	var recordId=$(this).val();
	 	    var semantic = $('input[name=semantic]').val();
		    if(semantic=='DT'){
		    	if($.inArray(recordId, parent.previewParams.dtDetectionResultIds)<0){
					  parent.previewParams.dtDetectionResultIds.push(recordId);
					  var $tr = $(this).closest('tr');
		        	  var clinicalJudgment = $tr.find('.clinicalJudgment').val();
					  parent.previewParams.dtDetectionResultClinicals.push({"id":recordId,"clinicalJudgment":clinicalJudgment});
				}
			   }else if(semantic=='MLPA-TEST'){
				   if($.inArray(recordId, parent.previewParams.mlpaDetectionResultIds)<0){
						parent.previewParams.mlpaDetectionResultIds.push(recordId);
				  		var $tr = $(this).closest('tr');
		        		var clinicalJudgment = $tr.find('.clinicalJudgment').val();
					    var mutationSource = $tr.find('.mutationSource').val();
					    parent.previewParams.mlpaDetectionResultClinicals.push({"id":recordId,"clinicalJudgment":clinicalJudgment,"mutationSource":mutationSource});
					}
			   }
			var chknum = $(".check-instance").size();// 选项总个数
			var chk = $('input[type=checkbox][class=check-instance]:checked').length;// 选中总数
			if (chknum == chk)
	         {
	            $(".check-controller").prop("checked", true);
	         }
			 else
			 {
			 	$(".check-controller").prop("checked", false);
			 }
		}

    }).on('click', '#btn_testing_sample', function()
    {
        var layerObject = parent.parent.layer;
        var buttons = [ '确定', '取消' ];

        layerObject.confirm(tipMessage, {
            icon : 3,
            title : '操作确认',
            btn : buttons,
            shade : 'transparent'
        }, function(index)
        {
        	var flag = true;
        	$('.son').each(function()
        	{
        		var display = $(this).css('display');
        		if(display != 'none')
        		{
        			var value = $(this).find(".checknull").val();
        			if(null==value || ""==value ||"null" == value)
        			{
        				flag = false;
        			}
        		}
        	});
        	if(flag)
        	{
            	$.ajax({
                    type: "POST",
                    dataType: "html",
                    url: "/bpm/report/handle/save_testing_sample.do",
                    data: $('#testing_sample_form').serialize(),
                    success: function (result) {
                    	var ids = result.split(',');
                    	$('.toggle-qualified').each(function(){
                    		if(ids.length > 0)
                    		{
                    			for(var i = 0;i<ids.length;i++)
                    			{
                    				if(ids[i] == $(this).attr('data-id'))
                    				{
                    					$(this).parents('.checkbox').hide();
                    				}
                    			}
                    		}
            	        });
                    	if(ids.length > 0)
                		{
                			for(var i = 0;i<ids.length;i++)
                			{
                				$('#unqualified_'+ids[i]).find("input[class='form-control checknull']").attr('readonly',true);
                			}
                		}
                    	parent.document.getElementById("iframe_sample").value = "true";
                    },
                    error: function(data) {
                        alert("error");
                    }
            	});
        	}
        	else
        	{
        		layerObject.alert("请填写备注原因!", {title : "提示"});
        	}
            layerObject.close(index);
        }, function(index)
        {
            layerObject.close(index);
        });
    }).on('click', '#btn_testing_test_result', function()
    {
    	var semantic = $('input[name=semantic]').val();
    	var reportId = $("#id" , parent.document).val();
    	var instances = $('.check-instance');
    	var processResults = [];
    	var isCheckPass=true;
    	instances.each(function(index,element)
                {
                	if($(this).is(":checked"))
                    {
                		var $tr = $(this).closest('tr');
                		var clinicalJudgment = $tr.find('.clinicalJudgment').val();
                		var mutationSource = $tr.find('.mutationSource').val();
                		var obj = {};
                		obj.id = $(this).val();
                		obj.clinicalJudgment = clinicalJudgment;
                		obj.mutationSource = mutationSource;
                		if(semantic=='MLPA-TEST'){
                			if(typeof(clinicalJudgment) == "undefined"||clinicalJudgment==null||clinicalJudgment==''){
                				isCheckPass=false;
                				alert("临床相关性判断不能为空");
                				return;
                			}
                			if(typeof(mutationSource) == "undefined"||mutationSource==null||mutationSource==''){
                				isCheckPass=false;
                				alert("临床相关性判断不能为空");
                				return;
                			}
                		}
                		if(semantic=='DT'){
                			if(typeof(clinicalJudgment) == "undefined"||clinicalJudgment==null||clinicalJudgment==''){
                				isCheckPass=false;
                				alert("临床相关性判断不能为空");
                				return;
                			}
                		}
                		processResults.push(obj);
                    }
                });  
    	if(!isCheckPass) return;
        var layerObject = parent.parent.layer;
        if(processResults.length>0)
        {
        	var buttons = [ '确定', '取消' ];
            layerObject.confirm('确定保存样本结果吗？', {
                icon : 3,
                title : '操作确认',
                btn : buttons,
                shade : 'transparent'
            }, function(index)
            {
            	$.ajax({
                    type: "POST",
                    dataType: "html",
                    url: "/bpm/report/handle/save_testing_result.do",
                    data: {reportId:reportId,semantic:semantic,testingResult:JSON.stringify(processResults)},
                    success: function (result) {
                    	alert("保存成功");
                    	parent.document.getElementById("iframe_result").value = "true";
                    },
                    error: function(data) {
                        alert("error");
                     }
                });
                layerObject.close(index);
            }, function(index)
            {
                layerObject.close(index);
            });
        }
        else
        {
        	layerObject.alert("请至少选择一条数据!");
        }
    }).on('click', '.resultTr', function()
	{
    	$('.resultTr').each(function(){
    		$(this).css('background-color', '');
    	});
    	$(this).css('background-color', '#3EBBB3');
    });
    
});
