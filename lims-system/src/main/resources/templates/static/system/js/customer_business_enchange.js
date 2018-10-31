var s=null;
function changeType(){
    s.clear();
    s.setDataUrlParams({testingType:$('#selectType').val()})
}



$(document).ready(
    function() {
        s = $('#business').magicSuggest(
            {
                width : 190,
                highlight : true,
                data : '/business/businessSelect.do',
                method : 'get',
                queryParam : "realName",
                maxSelection : 1,
                displayField:"realName",
                allowFreeEntries : false,
                renderer : function(v) {
                    return '<div>' + '<div >'
                        + '<div  class="probe">' + v.realName
                        + '</div>' + '</div>'
                        + '<div style="clear:both;"></div>';
                }
            });


        $("#testing_task_form").validate({
            submitHandler:function(form){
                var flag =true;
                var type = $('#selectType').val();
                if (type.length==0){
                    parent.parent.showTip('请选择营销中心！','提示');
                    flag=false;
                }

                var enchange = $('#business').magicSuggest().getSelection();
                if (enchange.length==0){
                    parent.parent.showTip('请选择改绑业务员！','提示');
                    flag=false;
                }
                $.each(enchange,function(index,obj){
                    business=obj.id;
                })

                var current = $('#current').val();
                if (enchange[0].realName==current){
                    parent.parent.showTip('改绑后业务员不可以和当前业务员相同！','提示');
                    flag=false;
                }

                $('#testing_task_detail_table tr').each(function(index,object){
                    if(index>0){
                        var  tr = $(this);
                        var currentBusiness = tr.find('.currentBusiness').text();
                        if (currentBusiness.indexOf(current)<0){
                            parent.parent.showTip('请确保所选客户的当前业务员全部存在：'+current,'提示');
                            flag=false;
                        }
                    }
                })

                var layerObject = parent.parent.layer;
                if (flag){

                    var loadindex = layerObject.load();
                    $.ajax({
                        type: "post",
                        url:"/customer/allUpdate.do",
                        data: {
                            currentName:current,
                            business:enchange[0].id,
                            keys:$('#keys').val()
                        },
                        dataType:"json",
                        success: function (data) {
                           parent.window.gotohtml(loadindex);
                        },
                        error:function(){
                            layerObject.close(loadindex);
                            $("#released").removeAttr("disabled");
                            $("#released").val('确认');
                        }

                    });
                }
            },
            rules:{
                current : {
                    required:true,
                    remote: {
                        url: "/customer/validateType.do",
                        type: "post",
                        dataType: "json",
                        data: {
                            currentName: function () {
                                return $('#current').val();
                            },
                            testingType:function () {
                                return $('#selectType').val();
                            }
                        }
                    }
                },
            },
            messages: {
                current : {
                    required:"请输入需要修改的业务员",
                    remote: "当前业务员与营销中心不匹配"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parent());
            }


        });

})





