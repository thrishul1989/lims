//导入解析excel
var tdHtml = $('.getTr').prop('outerHTML');
var flag = false;
var sumbitFlag=1;//正常
$("#butt").click(function()
{
    var excelFileName = $('#uploadData').val();
    var formatStr = '';
    var index = excelFileName.lastIndexOf('.');
    if (excelFileName.length == 0)
    {
        parent.layer.alert('请选择需要上传的文件', {
            title : "提示"
        });
        return;
    }
    else if (index > 0)
    {
        formatStr = excelFileName.substring(index);
        if (!(".xlsx" == formatStr || ".xls" == formatStr))
        {
            parent.layer.alert('请上传excel文件', {
                title : "提示"
            });
            return;
        }
    }
    $.ajax({
        url : '/primer/uploadPrimerDesign',
        type : 'POST',
        cache : false,
        data : new FormData($('#uploadForm')[0]),
        processData : false,
        contentType : false
    }).done(function(list)
    {
        primerList = [];
        if (list.length == 0)
        {
            parent.parent.layer.alert('您上传的Excel没有数据，检查后重新上传！', {
                title : "提示"
            });
        }
        else
        {
            
            if (flag)
            {
                $("tbody tr").remove();
                for (var m = 0; m < list.length; m++){
                        $("tbody").append(tdHtml);
                }
            }else{
                for (var m = 0; m < list.length; m++){
                    if (m > 0)
                    {
                        $("tbody").append(tdHtml);
                        
                    }
                }
            }
            $('.getTr').each(function(i, v)
            {
                
                $(this).find('input').eq(0).attr("name","primerList["+i+"].gene");
                $(this).find('input').eq(1).attr("name","primerList["+i+"].codingExon");
                $(this).find('input').eq(2).attr("name","primerList["+i+"].chromosomeNumber");
                $(this).find('input').eq(3).attr("name","primerList["+i+"].pcrStartPoint");
                $(this).find('input').eq(4).attr("name","primerList["+i+"].pcrEndPoint");
                $(this).find('input').eq(5).attr("name","primerList["+i+"].forwardPrimerName");
                $(this).find('input').eq(6).attr("name","primerList["+i+"].forwardPrimerSequence");
                $(this).find('input').eq(7).attr("name","primerList["+i+"].reversePrimerName");
                $(this).find('input').eq(8).attr("name","primerList["+i+"].reversePrimerSequence");
                $(this).find('input').eq(9).attr("name","primerList["+i+"].productCode");
                $(this).find('input').eq(10).attr("name","primerList["+i+"].applicationType");
                
                $(this).find('input').eq(0).attr("value",list[i][0]);
                $(this).find('input').eq(1).attr("value",list[i][1]);
                $(this).find('input').eq(2).attr("value",list[i][2]);
                $(this).find('input').eq(3).attr("value",list[i][3].replace(/\,/g,""));
                $(this).find('input').eq(4).attr("value",list[i][4].replace(/\,/g,""));
                $(this).find('input').eq(5).attr("value",list[i][5]);
                $(this).find('input').eq(6).attr("value",list[i][6]);
                $(this).find('input').eq(7).attr("value",list[i][7]);
                $(this).find('input').eq(8).attr("value",list[i][8]);
                $(this).find('input').eq(9).attr("value",list[i][9]);
                $(this).find('input').eq(10).attr("value",list[i][11]);
                var $this=$(this);
                for (var m = 0; m < list.length; m++)
                {   
                    primerList = list[m];
                    // 二次修改
                    // 根据导入表的行数增加tr
                    var $thisTr = $(this).parent().find('tr').eq(m);
                    if (primerList.length == 13)
                    {  sumbitFlag=0;
                            for (var i = 0; i < primerList.length; i++)
                            {
                                if(i!=11){
                                    $thisTr.find("td").eq(i).text(primerList[i]);
                                }else{
                                    $thisTr.find("td").eq(i).text(primerList[i+1]);
                                    $thisTr.css("background-color", "red");
                                }
                            }
                    }
                    else
                    {
                        for (var i = 0; i < primerList.length; i++)
                        {       
                                if(i!=11){
                                    $thisTr.find("td").eq(i).text(primerList[i]);
                                }
                        }

                    }
                }
            
                
            });
        }
        $('#myModal').modal('hide');
        flag = true;
    })
});

function validata()
{
    primerList = [];
    var content;
    $('.getTr').each(
            function()
            {
                primer = {};
                primer.gene=$(this).find('td').eq(0).text();
                primer.chromosomeNumber = $(this).find('td').eq(2).text().replace(/\,/g,"");
                primer.pcrStartPoint=$(this).find('td').eq(3).text().replace(/\,/g,"");
                primer.pcrEndPoint=$(this).find('td').eq(4).text().replace(/\,/g,"");
                primer.forwardPrimerName=$(this).find('td').eq(5).text();
                primer.reversePrimerName=$(this).find('td').eq(7).text();
                primer.productCode=$(this).find('td').eq(9).text();
                primer.applicationType=$(this).find('td').eq(10).text();    
                primerList.push(primer);
            });
    content = JSON.stringify(primerList);
    
    if(sumbitFlag!=0){
        $.ajax({
            url : '/primer/validata',
            traditional : true,
            type : "POST",
            data : {
                content : content,
            },
            async : false,
            success : function(data)
            {
                if (data.length==0)
                {
                    $('#default', window.parent.document).html("success");  
                }
                else
                {
                    for(var j=0;j<data.length;j++){
                        
                        $(".getTr").eq(data[j]).css("background-color", "red");
                        $(".getTr").eq(data[j]).find('td').eq(11).text("数据已存在");
                    }
                }
            }, error : function()
            {
                alert("failed");
            }
        })
    }
   
}

$('#myModal').on('show.bs.modal',function(e){
    $(this).find('.fileinput-upload-button').hide();
})