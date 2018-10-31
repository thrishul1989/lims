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
		var persons = $('#person').magicSuggest().getSelection()
		if (persons.length == 0) {
			alert("请选择处理人");
			return false;
		}

		parent.layer.confirm('确认操作？', {
			btn : [ '是', '否' ]
		}, function(index) {
			var orderIds = [];
			instances.each(function() {
				orderIds.push($(this).val());

			});

			$.ajax({
				type : "POST",
				url : base + "/repors/reportEmail/assign.do",
				data : {
					req : JSON.stringify({
						person : persons[0].id,
						orderIds : orderIds
					})
				},

				success : function(data) {

					window.location.reload();
				},
				error : function(data) {

				}
			});

			parent.layer.close(index);
		}, function(index) {

			parent.layer.close(index);
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

})
