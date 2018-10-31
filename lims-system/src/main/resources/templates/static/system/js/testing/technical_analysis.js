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
            var totalCount = $(".check-instance").size();// 选项总个数
            var checkedCount = $('input[type=checkbox]:checked').length;// 选中总数
            if (totalCount == checkedCount)
            {
                $(".check-controller").prop("checked", true);
            }
            else
            {
                $(".check-controller").prop("checked", false);
            }
        }

    }).on('click', '#btn_assign', function(e)
    {
        e.preventDefault();
        var instances = $('.check-instance:checked');
        var count = instances.length;

        if (count == 0)
        {
            parent.parent.layer.alert('请至少选择一条任务', {
                title : "提示"
            });
            return false;
        }

        var doctorAssist = "";
        var instanceDoctorAssist;
        var keys = [];
        var assignable = true;
        var assignableDataTemplate = true;
        var dataTemplateId;
        var dataTemplateIdType = "";
        var productName="";
        var str = [];

        instances.each(function()
        {
            keys.push($(this).val());
            instanceDoctorAssist = $(this).attr('data-doctorAssist');
            dataTemplateId = $(this).attr('data-templateId');
            productName = $(this).attr('data-productName');
            if(dataTemplateId=="")
            {
                if(str.indexOf(productName) < 0)
                {
                    str.push(productName);
                }
            }

            if ("" == doctorAssist)
            {
            	doctorAssist = instanceDoctorAssist;
            }
            else
            {
                if (doctorAssist != instanceDoctorAssist)
                {
                    assignable = false;
                }
            }
            if ("" == dataTemplateIdType)
            {
                dataTemplateIdType = dataTemplateId;
            }
            else
            {
                if (dataTemplateIdType != dataTemplateId)
                {
                    assignableDataTemplate = false;
                }
            }
        });
        if(0 < str.length)
        {
            parent.layer.alert(str.join("、")+'产品的数据上传模板未配置，请配置好再下达',{title:"提示"});
            return false;
        }

        if (!assignable)
        {
            parent.layer.alert('客户参与方式不一致，请重新选择',{title:"提示"});
            return false;
        }
        if (!assignableDataTemplate)
        {
            parent.layer.alert('数据上传模本不一致，请重新选择',{title:"提示"});
            return false;
        }
        
        $('#keys').val(keys.join(','));
        $('#testForm').submit();
        $('#assign_dialog').modal('show');
    }).on('click', '#btn_export', function(e)
    {
        e.preventDefault();
        $('#export_dialog').modal('show');
    });
    
    $('#released').on('click', function()
    {
        $('iframe#assign_frame')[0].contentWindow.$('#testing_task_form').submit();
    });
    $('#export_summary').on('click', function()
    {
    	var sequencingCode = $('#sequencingCode').val();
    	if('' == sequencingCode){
    		parent.layer.alert('测序编号不能为空',{title:"提示"});
    	}else{
    		$.ajax({
    			 type:"POST",
    			 traditional: true,
    			 url:"/testing/technicalAnalysis/exportSummary.do",
    			 data:{sequencingCode : sequencingCode},
    			 async: false,
    			 success:function(data){
    				 if(data == 'false'){
    					 parent.layer.alert('未查询到与该测序编号匹配的记录',{title:"提示"});
    				 }else{
    					 $("#formValue").val(data);
    					 $("#hiddForm").submit();
    				 }
    				 $('#export_dialog').modal('hide');
    		    },
    			 error:function(){
    				 alert("failed");
    			}
    		});
    	}
    });
});