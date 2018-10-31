
$(function(){
	
	$('input[type="radio"] :not(input[name="verify"])').click(function(){
		if($(this).data('waschecked')){
			$(this).prop("checked",false);
			$(this).data('waschecked', false);
		}
		else{
			$(this).prop('checked', true);
			$(this).data('waschecked', true);
		}
	});
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
			            var chknum = $(".check-instance").length;// 选项总个数
			            var chk = $('input[class=check-instance][type=checkbox]:checked').length;// 选中总数
			            if (chknum == chk)
			            {
			                $(".check-controller").prop("checked", true);
			            }
			            else
			            {
			                $(".check-controller").prop("checked", false);
			            }
			        }

			    })
	
	
    
});

//动态展示列  确定按钮
function sureToShowCol()
{	
	 var unChecks = [];
	 var checks = [];
	 $('.check-instance').each(function()
     {
     	 if($(this).is(":checked"))
         {
     		checks.push($(this).val());
         }
     	 else
     	 {
     		unChecks.push($(this).val());
     	 }
    }); 
	if(null != unChecks)
	{
		for(var i = 0;i<unChecks.length;i++)
		{
			if("Evidence" == unChecks[i])
			{	
				$testTable.bootstrapTable('hideColumn', 'HGMD');
				$testTable.bootstrapTable('hideColumn', 'clinvar');
				$testTable.bootstrapTable('hideColumn', 'InterVar');
				$testTable.bootstrapTable('hideColumn', 'MutInDatabase');
				$testTable.addClass('evidenceTable');					
			}	
			else{
				$testTable.bootstrapTable('hideColumn', unChecks[i]);
			}
		}
	}
	if(null != checks)
	{
		for(var i = 0;i<checks.length;i++)
		{
			if("Evidence" == checks[i])
			{
				$testTable.removeClass('evidenceTable');
				$testTable.bootstrapTable('showColumn', 'HGMD');
				$testTable.bootstrapTable('showColumn', 'clinvar');
				$testTable.bootstrapTable('showColumn', 'InterVar');
				$testTable.bootstrapTable('showColumn', 'MutInDatabase');
			}	
			else{
				$testTable.bootstrapTable('showColumn', checks[i]);
			}
		}
	}
}

function detailsShow(name,id)
{
	$('#detailsModalLabel').html(name);
	$('#detailsModalDiv').html("");
	$.ajax({
        type: "POST",
        url: PATH+"/testing/getGroupDetails.jsp",
        data: {groupName:name,id:id},
        dataType: "json",
        success: function(data){
        	
        	var opts = "";
        	for (var key in data)
		    {
        		if("疾病" != key && "Inhert" != key)
        		{
        			if("关联疾病|遗传方式" == name)
        			{
        				if(null != pList)
        				{
        					var s = data[key];
        					for(var i = 0;i<pList.length;i++)
        					{
        						var s = s.replace(pList[i],"<span style='color: red;'>"+pList[i]+"</span>");
        					}
        				}
        				opts += "<div class=\"col-sm-12\">"+key+"："+s+"</div>";
        			}
        			else
        			{
        				if(key == '报道文献'){
        					opts += "<div class=\"col-sm-12\">"+key+"：<a href='http://www.ncbi.nlm.nih.gov/pubmed/?term="+data[key]+"' target='_blanck'>"+data[key]+"</a></div>";
                			
                		}
        				else
        					{
        					opts += "<div class=\"col-sm-12\" style='word-wrap: break-word'>"+key+"："+data[key]+"</div>";
        					}
        				
        			}
        		}
        		
		    }
        	console.info(opts);
        	$('#detailsModalDiv').html(opts);
        	$('#detailsModal').modal({});
        },
        error:function(data){
        	window.location.href=PATH+'/login.jsp'; 
	      }
    });
}

function downloadFilterData(dataId)
{
	var layerObject = parent.parent.layer;
	var loadindex = layerObject.load();
	$.ajax({
		type : "POST",
		url : PATH+"/testing/downloadFilterData.jsp?analysisSampleId="+analysisSampleId,
		success : function(result) {
			layerObject.close(loadindex);
			$("#formValue").val(result);
			$("#hiddForm").submit();
		},
		error : function() {
			alert("failed");
			layerObject.close(loadindex);
		}
	});
}

function showRefilterModal()
{
	var disease=$('#disease').magicSuggest({
	    width: 190,
	    highlight: true,
	    data:PATH+'/disease/diseaseSelect.do',
	    method:'get',
	    queryParam:"name",
	    allowFreeEntries:false,
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	        
	            '<div  class="probe">' + v.name + '</div>' +
	           
	        '<div style="clear:both;"></div>';
	    }
	});
 	if ($('#disease').val() != null && "" != $('#disease').val())
    {
        s.setDataUrlParams({
            name : $('#disease').val()
        });
    }
 	
	var gene=	$('#gene').magicSuggest({
	    width: 190,
	    highlight: true,
	    data:PATH+'/disease/geneSelect.do',
	    method:'get',
	    queryParam:"name",
	    displayField:'name',
	    allowFreeEntries:false,
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	            '<div  class="probe">' + v.name + '</div>' +
	        '<div style="clear:both;"></div>';
	    }
	});
	
	var phenotype=	$('#phenotype').magicSuggest({
	    width: 190,
	    highlight: true,
	    data:PATH+'/phenotype/getPhenotypeSelected.do',
	    method:'get',
	    queryParam:"name",
	    allowFreeEntries:false,
	    renderer: function(v){
	    return '<div>' +
	        '<div >' +
	            '<div  class="probe">' + v.name + '</div>' + 
	        '<div style="clear:both;"></div>';
	    }
	});
	
	$('#refilterModal').modal({
		width : '1220px'
	});
}

//重置
function clearAll()
{
	clearBase();
	clearCrowd();
	clearProtein();
	clearEvidence();
	clearMaijinuo();
}

//迈基诺
function clearMaijinuo()
{
	$("#maijinuoDiv :text").each(function(){  
        $(this).val('');  
    });  
    $("#maijinuoDiv :checkbox").each(function(){  
        $(this).prop('checked' ,false);  
    });
    $("#maijinuoDiv :radio").each(function(){  
        $(this).prop('checked' ,false);  
    });
    
}


//基础重置
function clearBase()
{
	$("#baseDiv :text").each(function(){  
        $(this).val('');  
    });  
    $("#baseDiv :checkbox").each(function(){  
        $(this).prop('checked' ,false);  
    });
    $("#baseDiv :radio").each(function(){  
        $(this).prop('checked' ,false);  
    });
    
}
//人群重置
function clearCrowd()
{
	$("#crowdDiv :text").each(function(){  
        $(this).val('');  
    });  
    $("#crowdDiv :checkbox").each(function(){  
        $(this).prop('checked' ,false);  
    });
    $("#crowdDiv :radio").each(function(){  
        $(this).prop('checked' ,false);  
    });
    
}
//蛋白功能预测重置
function clearProtein()
{
	$("#proteinDiv :text").each(function(){  
        $(this).val('');  
    });  
    $("#proteinDiv :checkbox").each(function(){  
        $(this).prop('checked' ,false);  
    });
    $("#proteinDiv :radio").each(function(){  
        $(this).prop('checked' ,false);  
    });
    
}
//Evidence重置
function clearEvidence()
{
	$("#evidenceDiv :text").each(function(){  
        $(this).val('');  
    });  
    $("#evidenceDiv :checkbox").each(function(){  
        $(this).prop('checked' ,false);  
    });
    $("#evidenceDiv :radio").each(function(){  
        $(this).prop('checked' ,false);  
    });
    
}

//最近筛选项
function recentFilter(e,index)
{
	var d = $('#disease').magicSuggest();
    var g = $('#gene').magicSuggest();
    var p = $('#phenotype').magicSuggest();
    if(d.getSelection().length>0 || g.getSelection().length>0 || p.getSelection().length>0){
    	if(typeof g.clear == 'function'){
            g.clear();
        }
        if(typeof d.clear == 'function'){
            d.clear();
        }
        if(typeof p.clear == 'function'){
            p.clear();
        }
        $('#disease').blur();
        $('#gene').blur();
        $('#phenotype').blur();
        return ;
    }
    
	var attrData = $(e).attr("attr-data");
	var obj = JSON.parse(attrData);
	if(null != obj)
	{
		clearMaijinuo();
		clearBase();
		clearCrowd();
		clearProtein();
		clearEvidence();
		
		for (var key in obj)
	    {
	        //console.info(key); console.info(obj[key]);
	        if(obj[key] instanceof Array)
	        {
	        	for(var i = 0;i<obj[key].length;i++)
	        	{
	        		$("input[name='"+key+"'][value='"+obj[key][i]+"']").prop('checked' ,true);
	        	}
	        }else if(key.indexOf("Min")>0 || key.indexOf("Max")>0)
	        {
	        	$("input[name='"+key+"']").val(obj[key]);
	        }
	        else
	        {
	        	 $("input[name='"+key+"'][value='"+obj[key]+"']").prop('checked' ,true);
	        }
	        
	        if("diseases" == key)
	        {
	        	if(null != obj[key] && "" != obj[key])
	        	{
	        		$.ajax({
	     			   type:"POST",
	     			   url:PATH+'/disease/getDiseaseByIds?ids='+obj[key],
	     			   success:function(data){
	     				   if(null != data && data.length > 0)
	     				   {
	     					  $('#disease').magicSuggest().setSelection(data);
	     				   }
	     				}
	     		   }); 
	        	}
	        }

	        if("genes" == key)
	        {
	        	if(null != obj[key] && "" != obj[key])
	        	{
	        		$.ajax({
	     			   type:"POST",
	     			   url:PATH+'/disease/getGeneByIds?ids='+obj[key],
	     			   success:function(data){
	     				   if(null != data && data.length > 0)
	     				   {
	     					  $('#gene').magicSuggest().setSelection(data);
	     				   }
	     				}
	     		   }); 
	        	}
	        }
	        if("phenotypes" == key)
	        {
	        	if(null != obj[key] && "" != obj[key])
	        	{
	        		$.ajax({
	     			   type:"POST",
	     			   url:PATH+'/phenotype/getPhenotypeByIds?ids='+obj[key],
	     			   success:function(data){
	     				   if(null != data && data.length > 0)
	     				   {
	     					  $('#phenotype').magicSuggest().setSelection(data);
	     				   }
	     				}
	     		   }); 
	        	}
	        }
	        
	    }
	}
}


$.fn.serializeObject = function()    
{    
   var o = {};    
   var a = this.serializeArray();   
   $.each(a, function() { 
	   if(null != this.value && "" != this.value)
	   {
		   if (o[this.name]) {    
	           if (!o[this.name].push) {    
	               o[this.name] = [o[this.name]];    
	           }    
	           o[this.name].push(this.value || '');    
	       } else {    
	           o[this.name] = this.value || '';    
	       } 
	   }
   });    
   return o;    
};  
function checkSize() {
	var flag = true;
	$(".range").each(function(index){
		var name = $(this).find("input[name$='Min']").attr('name');
		name = name.substring(0,name.length-3);
		var min = $.trim($(this).find("input[name$='Min']").val());
		var max = $.trim($(this).find("input[name$='Max']").val());
		if(null != min && "" !=min && null != max && "" != max)
	    {
	    	if(parseFloat(max)<parseFloat(min))
	    	{
	    		layer.alert(name+':后值必须大于前值！', {title : "提示"});
	            flag = false;
	    	}
	    }
	});
    return flag;
}

function check(e) { 
    var re = /^\d+(?=\.{0,1}\d+$|$)/ 
    if (e.value != "") { 
        if (!re.test(e.value)) { 
            e.value = ""; 
            e.focus(); 
            alert("请输入正确的数字");
        } 
    } 
} 

function packData()
{
	//临床诊断(疾病)
	var ds=$('#disease').magicSuggest().getSelection();
	var dsid=[];
	$.each(ds,function(index,obj){
		dsid.push(obj.id);
	})
	var diseases = dsid.join(",");
	
	//关注基因
	var gn=$('#gene').magicSuggest().getSelection();
	var gnid=[];
	$.each(gn,function(index,obj){
		gnid.push(obj.id);
	})
	var genes = gnid.join(",");
	
	//临床表型
	var pt=$('#phenotype').magicSuggest().getSelection();
	var ptid=[];
	$.each(pt,function(index,obj){
		ptid.push(obj.id);
	})
	var phenotypes = ptid.join(",");
	
	$("input[name='diseases']").val(diseases);
	$("input[name='genes']").val(genes);
	$("input[name='phenotypes']").val(phenotypes);
	//$('#mutationForm').submit();
	var flag = checkSize();
	
	if(flag)
	{
		var jsonInfo = $('#refilterForm').serializeObject();  
		//console.info(JSON.stringify(jsonInfo));
		$.ajax({
			   type:"POST",
			   url:PATH+'/testing/saveFilterCreate.jsp',
			   data:{jsonStr:JSON.stringify(jsonInfo)},
			   success:function(flag){
				   if(flag)
				   {
					   console.info('保存完成');
					   parent.window.location.href=PATH+'/testing/tab.do?analysisFamilyId='+analysisSampleId+"&sampleCode="+sampleCode+"&dataCode="+dataCode+"&ifRecheck="+ifRecheck;
				   }
				}
		   }); 
		$('#refilterModal').modal("hide");
	}
}

	
	var $testTable = $('#dataTable').bootstrapTable({
		 striped: true,  
         height: 510
	});  
	
	$(window).on('load',function(e){
		var a = top.layer;
		a.close(a.index);
	})

	

	