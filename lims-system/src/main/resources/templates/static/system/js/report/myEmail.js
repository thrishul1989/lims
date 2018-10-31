$(function() {


    $("#prjManager").bsSuggest('init', {
        url : base+"/smm/role/getSelectList.do?disableStatus="+0+"&name=",
        getDataMethod : "url",
        idField : "id",
        keyField : "name",
        indexKey: 0,
        effectiveFields : [ "name" ],
        effectiveFieldsAlias : {
            name : "项目管理人名称"
        }
    }).on('onDataRequestSuccess', function (e, result) {
    }).on('onSetSelectValue', function (e, keyword, data) {
        $('#projectManager').val(data.id);
    }).on('onUnsetSelectValue', function () {
        $('#projectManager').val('');
    });


	$('body').on('click', '.check-controller', function() {
		if ($(this).is(":checked")) {
			$(".check-instance").prop("checked", true);
		} else {
			$(".check-instance").prop("checked", false);
		}
	}).on('click', '.check-instance', function() {
		if (!$(this).is(":checked")) {
			$(".check-controller").prop("checked", false);
		} else {
			var totalCount = $(".check-instance").size();// 选项总个数
			var checkedCount = $('input[type=checkbox]:checked').length;// 选中总数
			if (totalCount == checkedCount) {
				$(".check-controller").prop("checked", true);
			} else {
				$(".check-controller").prop("checked", false);
			}
		}

	}).on('click', '#subtn', function(e) {
		e.preventDefault();
		var instances = $('.check-instance:checked');
		var count = instances.length;

		if (count == 0) {
			alert("请至少选择一条任务");
			return false;
		}
		var orderIds = [];
		var rNames=[];
		var rPhones=[];
		var addresss=[];
		instances.each(function() {
			orderIds.push($(this).val());
			var $tr = $(this).closest('tr');
        	var receivedName = $tr.find('.receivedName').text();
        	rNames.push(receivedName);
        	var receivedPhone = $tr.find('.receivedPhone').text();
        	rPhones.push(receivedPhone);
        	var address = $tr.find('.address').text();
        	addresss.push(address);
		});
		if(rNames.length == 0 || rPhones.length == 0 || addresss.length == 0)
		{
			alert("收件信息为空！");
			return false;
		}
		
		var flag = true;
		for(var i = 0;i<rNames.length;i++)
		{
			if(null == rNames[i] || "" == rNames[i] || null == rPhones[i] || "" == rPhones[i] || null == addresss[i] || "" == addresss[i])
			{
				flag = false;
			}
		}
		
		// 校验勾选是否订单是否有可以寄送的报告，并且关联出收件人一样的报告
		if(flag)
		{
			$.ajax({
				type : "POST",
				url : base + "whetherCanEmail.do",
				data : {
					orderIds : orderIds.join(","),
					emailOrNot:'1'
				},
				dataType : 'json',

				success : function(data) {
					if(!data.whether){
						alert("所选订单不满足寄送条件或收件信息不一致无法寄送！")
					}
					else{
						if(data.allReportEmailIds.length > data.reportEmailIds.length ){
							parent.parent.layer.confirm('检测到有其他可一并寄送报告，是否一并寄送？', {
								btn : [ '是', '否' ]
							}, function(index) {

								parent.window.location.href=base+'/repors/reportEmail/report.do?reportEmails='+data.allReportEmailIds.join(",")

								parent.parent.layer.close(index);
							}, function(index) {
								parent.window.location.href=base+'/repors/reportEmail/report.do?reportEmails='+data.reportEmailIds.join(",")
								parent.parent.layer.close(index);
							});
						}
						else{
							parent.parent.layer.confirm('是否寄送？', {
								btn : [ '是', '否' ]
							}, function(index) {

								parent.window.location.href=base+'/repors/reportEmail/report.do?reportEmails='+data.reportEmailIds.join(",")

								parent.parent.layer.close(index);
							}, function(index) {

								parent.parent.layer.close(index);
							});
						}
					}

				}
			});
		}
		else
		{
			alert("收件信息为空！");
		}
		
	}).on('click', '#nosubtn', function(e) {
		e.preventDefault();
		var instances = $('.check-instance:checked');
		var count = instances.length;

		if (count == 0) {
			alert("请至少选择一条任务");
			return false;
		}
		var orderIds = [];
		instances.each(function() {
			orderIds.push($(this).val());

		});
		

		
		// 校验勾选是否订单是否有可以寄送的报告，并且关联出收件人一样的报告

		$.ajax({
			type : "POST",
			url : base + "whetherCanEmail.do",
			data : {
				orderIds : orderIds.join(","),
				emailOrNot:'0'
			},
			dataType : 'json',

			success : function(data) {
			
				if(!data.whether){
					alert("所选订单不满足寄送条件！")
				}
				else{

					parent.parent.layer.confirm('是否不寄送？', {
						btn : [ '是', '否' ]
					}, function(index) {

						parent.window.location.href=base+'/repors/reportEmail/noReport.do?noReportEmails='+data.reportEmailIds.join(",")

						parent.parent.layer.close(index);
					}, function(index) {

						parent.parent.layer.close(index);
					});
				}
					
					
					
				

			}
		});
		
		
		
		
	})
	

	var person = $('#person')
			.magicSuggest(
					{
						width : 190,
						highlight : true,
						data : base + '/smm/user/list_json.do?',
						method : 'get',
						queryParam : "key",
						allowFreeEntries : false,
						maxSelection : 1,
						renderer : function(v) {
							return '<div>'
									+ '<div >'
									+

									'<div  class="probe">'
									+ v.name
									+ '</div>'
									+ '<div style="font-weight:700;color:#676a6c;float:right;padding-top: 10px;margin-right:50px">'
									+ v.phone + '</div>' + '</div>'
									+ '<div style="clear:both;"></div>';
						}
					});

	$(person).on('selectionchange', function(e, m) {

		var obj_ = this.getSelection()[0];
		$('#assignedId').val(obj_.id)
	});

})
