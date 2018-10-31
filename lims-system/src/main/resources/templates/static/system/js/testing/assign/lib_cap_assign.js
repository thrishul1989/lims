$(function()
{
    $('body').on('click', '.modify', function(){
        /*var modifyTr=$(this).parents("tr").prop("outerHTML");
        var a=this;*/
        var $btn = $(this);
        var $table = $(this).closest('table');            
        var $crtRow = $(this).closest('tr');
        var cellNum = $table.find('thead th').length;
        var crtCol = $crtRow.find('td:visible').length; 
        
        //var isNeedOrder = false;          
        $crtRow.addClass('hightLight').siblings('.hightLight').removeClass('hightLight');
        layer.open({
            content: '请选择处理方式'
            ,btn: ['新增探针组', '转移探针']
            ,btn1:  function(index, layero){
                //$btn.parents('tr').find('td:eq(1)').attr("rowspan",1);                    
                layer.close(index); 
                addRow();
                restDataSize();
                var rowNum=($("td[rowspan]").length)/4; 
                $('#num').val(rowNum);
                unpdateCode();
                updateinputNameValue();
                setSubTaskTd();
            },btn2: function(index, layero){
                  layer.prompt({
                      title: '输入探针组号，并确认' 
                    
                      }, function(pass, index){ 
                          var crtGroupNum = getGrupNum(1);
                          pass = parseInt(pass);
                          var probeGroup=$('#'+pass).next().text();
                          var dataSizeGroup=$('#'+pass).next().next().find('.probeDatasize').data('size');
                          var $crtGroupTr = $('#' + crtGroupNum ).closest('tr');
                          var probe= $crtGroupTr.find('.probeName').val();
                          var dataSize=$crtGroupTr.find('.probeDatasize').data('size');
                         if(crtGroupNum == pass){
                             alert("不能转移到自身探针组");
                         }
                         else if(pass < 0 || pass > parseInt($("#num").val())){
                             alert("没有这个探针组");                                
                         }else if(probeGroup!=probe||dataSizeGroup!=dataSize){
                             alert("不能转移到探针不相同的组");
                         }else{
                             layer.close(index); 
                             updateRow($('#' + pass),1); 
                             restDataSize();
                             unpdateCode();
                             updateinputNameValue();
                             setSubTaskTd();
                         }                             
                  });
            }
          });
        
        //转移时候获取探针组号码
        function getGrupNum(hasIdCol){
            var crtGroupNum = null;              
            if(crtCol != cellNum){ 
                //不是第一行。
                $crtRow.prevAll('tr').each(function(index){           
                   var $tmpTd = $(this).find('td').eq(hasIdCol);
                   if($tmpTd.attr('id')){
                       crtGroupNum = parseInt($tmpTd.html());
                       return false;
                   }
                });             
            }else{
                crtGroupNum = parseInt($crtRow.find('td').eq(hasIdCol).html());
            }
            return crtGroupNum;
        }
        
        function updateRow($pass,hasIdCol){
            if(crtCol != cellNum){ 
                //不是第一行，调整第一行数值。
                $crtRow.prevAll('tr').each(function(index){
                    var $tmpTd = $(this).find('td').eq(hasIdCol);
                   if($tmpTd.attr('id')){
                       var newRowSpan = parseInt($tmpTd.attr('rowspan'))-1;
                       $tmpTd.attr('rowspan',newRowSpan);
                       $tmpTd.prev('td').attr('rowspan',newRowSpan);
                       $tmpTd.next('td').attr('rowspan',newRowSpan).next('td').attr('rowspan',newRowSpan);
                       return false;
                   }
                });             
            }else{
                //是第一行，下面有多余行和没多余行。
                var $tmpTd = $crtRow.find('td').eq(hasIdCol);
                var tmpRowSpan = parseInt($tmpTd.attr('rowspan'));
                var $tmpNextRow = null;            
                if(tmpRowSpan == 1){
                    //alert('没多余行，要重新排序');
                    //isNeedOrder = true;
                    $crtRow.nextAll('tr').each(function(index){
                        var $hasIdTd = $(this).find('td').eq(1);
                        if($hasIdTd.attr('id')){
                            var crtNum = parseInt($hasIdTd.html()) - 1;
                            $hasIdTd.attr('id',crtNum);
                            $hasIdTd.html(crtNum);                            
                        }
                    });                      
                }else{
                    $tmpNextRow = $crtRow.next('tr');
                    $tmpNextRow.prepend('<td class="code"></td><td></td><td></td><td></td>');
                }
                $crtRow.find('td').each(function(index){
                    if(index < 4){
                        if($tmpNextRow){
                            //$tmpNextRow.css('background-color','blue'); 
                            $tmpNextRow.find('td').eq(index).html($(this).html());
                            if(index==1){
                                $tmpNextRow.find('td').eq(index).attr('rowspan',tmpRowSpan-1).attr('id',$(this).attr('id'));
                            }else{
                                $tmpNextRow.find('td').eq(index).attr('rowspan',tmpRowSpan-1);
                            }
                        }
                        $(this).remove();                                         
                    }else{
                        return false;
                    }
                });                                       
            }
            
            var $passTd = $pass;
            var $passRow = $passTd.closest('tr');                            
            var tmpNum = parseInt($pass.attr('rowspan')); 
            $passTd.attr('rowspan',tmpNum + 1);
            $passTd.prev('td').attr('rowspan',tmpNum + 1);
            $passTd.next('td').attr('rowspan',tmpNum + 1).next('td').attr('rowspan',tmpNum + 1);
            var $endRow = null;
            if(tmpNum == 1 ){
                $endRow = $passRow;
            }else{
                $endRow = $passRow.nextAll('tr').eq(tmpNum-2);
            }
            //$endRow.css('background-color','red');
            $endRow.after($crtRow);
            //return isNeedOrder;
        }
        
        function unpdateCode(){
            var val=$("#batchCode").val();
            var i = 1;
            if(val!=''){ 
                $('.code').each(function()
                    {
                    if(i<10){
                        var code = val+'-'+'0'+i;
                    }else{
                        var code = val+'-'+i;
                    }
                if (undefined == code || '' == code)
                {
                    return;
                }
                var tr = $(this).closest('tr');
                tr.find('.code').empty().append(code);
                tr.find('.testingCode').val(code);

                i++;
            });}
           
        }
        //修改隐藏input name值
        function updateinputNameValue(){
            $('.code').each(function(){
                var index= $(this).next().text()-1;
                $thisTr=$(this).parent();
                $thisTr.find('.testingCode').attr('name','groups['+index+'].testingCode');
                $thisTr.find('.probeId').attr('name','groups['+index+'].probeId');
                $thisTr.find('.probeDatasize').attr('name','groups['+index+'].probeDatasize');
                
            });
        }   
        
        function addRow(){
            var hasIdCol = 1;
            var crtGroupNum = null;
            var $nextHasIdRow = null;
            $crtRow.nextAll('tr').each(function(index){
                var $tmpTd = $(this).find('td').eq(hasIdCol);
                if($tmpTd.attr('id')){
                    $nextHasIdRow = $(this);
                    return false;
                }
            });
            if(crtCol != cellNum){ 
                //不是第一行，调整第一行数值。
                $crtRow.prevAll('tr').each(function(index){
                   var $tmpTd = $(this).find('td').eq(hasIdCol);
                   if($tmpTd.attr('id')){
                       //当前组第一行rowspan-1
                       var newRowSpan = parseInt($tmpTd.attr('rowspan'))-1;
                       $tmpTd.attr('rowspan',newRowSpan);
                       $tmpTd.prev('td').attr('rowspan',newRowSpan);
                       $tmpTd.next('td').attr('rowspan',newRowSpan).next('td').attr('rowspan',newRowSpan);
                       //当前行单独成组
                       $crtRow.prepend('<td rowspan="1" class="code"></td><td rowspan="1" id="TMP"></td><td rowspan="1"></td><td rowspan="1"></td>');
                       $crtRow.find('td:eq(0)').html($tmpTd.prev('td').html());
                       $crtRow.find('td:eq(1)').html($tmpTd.html())
                       $crtRow.find('td:eq(2)').html($tmpTd.next('td').html())
                       $crtRow.find('td:eq(3)').html($tmpTd.next('td').next('td').html());
                       crtGroupNum = parseInt($tmpTd.html());
                       return false;
                   }
                });                    
            }else{
                //是第一行，下面有多余行和没多余行。
                var $tmpTd = $crtRow.find('td').eq(hasIdCol);
                var tmpRowSpan = parseInt($tmpTd.attr('rowspan'));                           
                if(tmpRowSpan == 1){
                    alert('仅一行，没必要新增。');
                    return;
                }
                crtGroupNum = parseInt($crtRow.find('td').eq(1).attr('id'));
                var $tmpNextRow = null;                    
                $tmpNextRow = $crtRow.next('tr');
                $tmpNextRow.prepend('<td class="code"></td><td></td><td></td><td></td>');
                $crtRow.find('td').each(function(index){
                    if(index < 4){
                        //当前行下一行，增加rowspan属性 
                        if($tmpNextRow){
                           // $tmpNextRow.css('background-color','blue'); 
                            $tmpNextRow.find('td').eq(index).html($(this).html());
                            if(index==1){
                                $tmpNextRow.find('td').eq(index).attr('rowspan',tmpRowSpan-1).attr('id',$(this).attr('id'));
                            }else{
                                $tmpNextRow.find('td').eq(index).attr('rowspan',tmpRowSpan-1);
                            }
                        }
                        //当前rowspan 设置为1
                        $(this).attr('rowspan',1); 
                        if(index==1){
                            $(this).attr('id','TMP');
                        }
                    }else{
                        //$tmpNextRow.after($crtRow);
                        return false;
                    }
                });                                      
            }  
                            
            if($nextHasIdRow){
                //$nextHasIdRow.before($crtRow);
                $crtRow.insertBefore($nextHasIdRow);
                updateNextGroupNum($crtRow,crtGroupNum);
            }else{
                //$table.find('tr:last').after($crtRow);
                $crtRow.find('td').eq(1).attr('id',crtGroupNum + 1).html(crtGroupNum + 1);
                $crtRow.insertAfter($table.find('tr:last'));
            }
        }
        
        function updateNextGroupNum($rowStart,prevGroupNum){
            var crtGroupNum = prevGroupNum + 1; 
            $rowStart.find('td').eq(1).attr('id',crtGroupNum).html(crtGroupNum);                
            $rowStart.nextAll('tr').each(function(){
               var $tmpTd = $(this).find('td').eq(1);
               if($tmpTd.attr('id')){
                   crtGroupNum++;
                   $tmpTd.attr('id',crtGroupNum).html(crtGroupNum);
               }
            }); 
        }
        
        function restDataSize(){
            $('#testing_task_detail_table .probeDatasize').each(function(e){
                var $td = $(this).closest('td');
                var crtRow = $td.attr('rowspan');
                var tmpv = $(this).data('size');
                if(!crtRow){
                    crtRow = 0;
                }
                if(!tmpv){
                    tmpv = 0;
                }
                debugger;
                var newV = (crtRow*(tmpv*1000))/1000;
                //alert(newV );
                $td.find('span').text(newV);
                $(this).val(newV);             
            })
        }
    });
    
    
    $("#lib_cap_form").validate();
    $('body').on('change', '#libraryInputSize', function()
    {
        var val = $(this).val();

        console.log(val);
        if (undefined == val || '' == val)
        {
            return;
        }
        $('.concn').each(function()
        {
            var concn = $(this).context.innerText;
            if (undefined == concn || '' == concn || 0 == concn)
            {
                return;
            }
            var tr = $(this).parents('tr');
            var volume = (val/concn).toFixed(2);
            tr.find('.volume').empty().append(volume);
            tr.find('.sampleInputSize').val(val);
            tr.find('.volu').val(volume);
        });
    }).on('change', '#batchCode', function()
    {
        var val = $(this).val();
        $.ajax({
            type: "GET",
            url: "/testing/wk_catch/validateBatchCode.do",
            data: {batchCode:val},
            dataType: "json",
            traditional: true, 
            success: function(data){
                if(!data){
                    parent.parent.layer.alert('此编号已被使用', {title : "提示"});
                    console.log("~~!!!2222");
                    $("#batchCode").val('');
                    $('.code').each(function()
                            {
                                var tr = $(this).closest('tr');
                                tr.find('.code').empty();
                            });
                }else{
                    var i = 1;
                    console.log(val);
                    if (undefined == val || '' == val)
                    {
                        return;
                    }
                    $('.code').each(function()
                            {
                                if(i>9) {
                                var code = val+'-'+i;
                                }else{
                                    var code = val+'-'+'0'+i;                                
                                }
                                if (undefined == code || '' == code)
                                {
                                    return;
                                }
                                var tr = $(this).closest('tr');
                                tr.find('.code').empty().append(code);
                                tr.find('.testingCode').val(code);
                                i++;
                            });
                }
            },
        });
    });
    
    
    function setSubTaskTd() {
        var groupNum = 0;
        var childNum = 0;
        $('#testing_task_detail_table tbody tr').each(function(index){
            var $hasIdTd = $(this).find('td').eq(1);
            if($hasIdTd.attr('id')){                 
                groupNum = parseInt($hasIdTd.attr('id')) - 1;
                childNum = 0;
            }
            //groups[2].tasks[0].id
            //groups[2].tasks[0].sampleInputSize
            //groups[2].tasks[0].sampleInputVolume
            var crtName = 'groups[' + groupNum +'].tasks[' + childNum + ']';
            $(this).find('.subTask input:eq(0)').attr('name',crtName + '.id');
            $(this).find('.subTask input:eq(1)').attr('name',crtName + '.sampleInputSize');
            $(this).find('.subTask input:eq(2)').attr('name',crtName + '.sampleInputVolume');
            childNum ++;
        });
    }
});


