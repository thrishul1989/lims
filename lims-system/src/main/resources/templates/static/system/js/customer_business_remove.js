$(document).ready(
    function() {

        var flag;
        $("#remove_task_form").validate({
            submitHandler:function(form) {
                var current = $('#current').val();

                $('#testing_task_detail_table tr').each(function (index, object) {
                    if (index > 0) {
                        var tr = $(this);
                        var currentBusiness = tr.find('.currentBusiness').text();
                        console.info(current);
                        if (currentBusiness.indexOf(current) < 0) {
                            parent.parent.showTip('请确保所选客户的当前务员全部存在：' + current, '提示');
                            flag = false;
                        }
                    }
                })

                var layerObject = parent.parent.layer;
                if (flag) {
                    var loadindex = layerObject.load();
                    $.ajax({
                        type: "post",
                        url: "/customer/allRemove.do",
                        data: {
                            currentName: current,
                            keys: $('#keys').val()
                        },
                        dataType: "json",
                        success: function (data) {
                            parent.window.gotohtml(loadindex);
                        },
                        error: function () {
                            layerObject.close(loadindex);
                            $("#released").removeAttr("disabled");
                            $("#released").val('确认');
                        }

                    });
                }
            },
            rules:{
                current : {
                    required:true
                },
            },
            messages: {
                current : {
                    required:"请输入需要修改的业务员",
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parent());
            }


        });
    })

