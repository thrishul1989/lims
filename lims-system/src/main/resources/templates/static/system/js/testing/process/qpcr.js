$(function() {
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
			var chknum = $(".check-instance").size();//选项总个数 
			var chk = $('input[type=checkbox]:checked').length;//选中总数
			if (chknum == chk) {
				$(".check-controller").prop("checked", true);
			} else {
				$(".check-controller").prop("checked", false);
			}
		}

	}).on('click', '#btn_assign', function(e) {
		e.preventDefault();
		var instances = $('.check-instance:checked');
		var count = instances.length;

		if (count == 0) {
			alert("请至少选择一条任务");
			return false;
		}

		if (count > 96) {
			alert("下单任务数量不能超过96，请重新选择");
			return false;
		}

		var keys = [];
		var storageStatus = "";
		var assignableStatus = true;
		var assignableDataTemplate = true;
		var dataTemplateId;
		var dataTemplateIdType = "";
		var productName = "";
		var str = [];
		 var cansubmit = true;
		instances.each(function() {
			var flag = $(this).parents('tr').find('.storageStatus span').text();
        	if('未出库' != flag){
        		alert("请选择状态是未出库的任务！");
        		cansubmit =  false;
        		return false;
        	}
			keys.push($(this).val());
			storageStatus = $(this).attr('data-storage-status');
			if (2 == storageStatus) {
				assignableStatus = false;
			}
			dataTemplateId = $(this).attr('data-templateId');
			productName = $(this).attr('data-productName');
			if (dataTemplateId == "") {
				if (str.indexOf(productName) < 0) {
					str.push(productName);
				}
			}
			if ("" == dataTemplateIdType) {
				dataTemplateIdType = dataTemplateId;
			} else {
				if (dataTemplateIdType != dataTemplateId) {
					assignableDataTemplate = false;
				}
			}
		});
		if(!cansubmit){
        	return false;
        }
//		if (!assignableStatus) {
//			parent.layer.alert('出库状态样本，不可下达任务!', {
//				title : "提示"
//			});
//			return false;
//		}
		if (0 < str.length) {
			parent.layer.alert(str.join("、") + '产品的数据上传模板未配置，请配置好再下达', {
				title : "提示"
			});
			return false;
		}

		if (!assignableDataTemplate) {
			parent.layer.alert('数据上传模本不一致，请重新选择', {
				title : "提示"
			});
			return false;
		}
		$('#keys').val(keys.join(','));
		$('#testForm').submit();
		$('#assign_dialog').modal('show');
	});
	$('#released').on(
			'click',
			function() {
				$('iframe#assign_frame')[0].contentWindow.$(
						'#testing_task_form').submit();
			})
});