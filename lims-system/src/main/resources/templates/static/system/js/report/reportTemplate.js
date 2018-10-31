function checkData(){
	var url = $('#url').val();
	var temp = $('#dataTemplateId').val();
	if('' == temp || null == temp){
		parent.layer.alert('请选择数据导入模板', {title : "提示"});
		return false;
	}else if('' == url || null == url){
		parent.layer.alert('请上传word文件', {title : "提示"});
		return false;
	}
	
	bookmarkList=[];
    $(".bookmarkTemp").each(function(){
        bookmark={};
        bookmark.builtinId= $(this).find("input").eq(2).val();
        bookmark.name=$(this).find(".bookmarkName").val();
        bookmark.contentType=$(this).find(".contentTypeValue").val();
        var filterIndex = $(this).find(".filterIndex").val();
        var filterName = $(this).find(".filterName").val();
        bookmark.lineFilter = filterIndex + "-" + filterName;
        bookmark.tableColumnList=[];
        $(this).find(".tableColumnTemp").each(function(){
        	tableColumn={};
        	tableColumn.columnIndex = $(this).find(".columnIndex").val();
        	tableColumn.variableType = $(this).find(".variableTypeValue").val();
        	tableColumn.builtinId = $(this).find("input").eq(2).val();
        	tableColumn.dataType = $(this).find(".dataTypeValue").val();
        	tableColumn.dataTemplateColumnId = $(this).find(".dataTemplateColumn").val();
        	tableColumn.imgUrl = $(this).find(".imgUrl").val();
        	bookmark.tableColumnList.push(tableColumn);
        });
        bookmarkList.push(bookmark);
    });
    var content=JSON.stringify(bookmarkList);
    $("#bookmarkContent").val(content);
}

$(function(){
	var $imgNode;
	var bookmarkTemp = $("#bookmarkTemp").html();
	var tableColumnTemp = $("#tableColumnTemp").html();
	
	$('body').on('blur', '#name', function(){
   		var id = $('#id').val();
   		var name = $('#name').val();
   		var $that = $(this);
    	$.ajax({
            type: "POST",
            url: "/report/reportTemplate/validate.do",
            data: {id : id , name : name},
            dataType: "json",
            success: function(data){
                if(!data){
                    parent.parent.layer.alert('模板名称已存在，请重新输入', {title : "提示"});
                    $that.val('');
                }
            }
        })
   	}).on('click', '.addBookmark', function(){
       $(this).parents(".bookmark").append(bookmarkTemp);
    }).on('change', '.contentTypeValue', function(){
    	if("1" == $(this).val()){
    		$(this).parents(".contentType").next().show();
    		$(this).parents(".bookmarkTemp").find('.tableVariable').hide();
    		$(this).parents(".bookmarkTemp").find('.addTableColumn').hide();
    		
    		var builtinVariable = $('.builtinVariable').magicSuggest({
    	        width : 190,
    	        highlight : true,
    	        data : '/report/builtinVariable/selectBuiltinVariable.do?',
    	        method : 'get',
    	        queryParam : "key",
    	        allowFreeEntries : false,
    	        maxSelection : 1,
    	     
    	        renderer : function(v)
    	        {   
    	          return '<div>' + '<div >' + '<div>' + v.name + '</div>' + '<div style="clear:both;"></div>';
    	        },
    	    });
    		
    		$(builtinVariable).on('selectionchange', function(e, cb, s)
    	            {
    	                var str = cb.getValue();
    	                $.ajax({
    	                    url : '/report/builtinVariable/getBuiltinVariable.do',
    	                    traditional : true,
    	                    type : "POST",
    	                    data : {
    	                        id : str,
    	                    },
    	                    async : false,
    	                    success : function(data)
    	                    {
    	                    	
    	                    },
    	                    error : function()
    	                    {
    	                        parent.layer.alert('错误', {
    	                            title : "提示"
    	                        });
    	                    }
    	                })
    	            });
    	}else if("2" == $(this).val()){
    		$(this).parents(".contentType").next().hide();
    		$(this).parents(".bookmarkTemp").find('.tableVariable').show();
    		$(this).parents(".bookmarkTemp").find('.addTableColumn').show();
    	}else{
    		$(this).parents(".contentType").next().hide();
    		$(this).parents(".bookmarkTemp").find('.tableVariable').hide();
    		$(this).parents(".bookmarkTemp").find('.addTableColumn').hide();
    	}
    }).on('change', '.variableTypeValue', function(){
    	if("1" == $(this).val()){
    		$(this).parents(".tableColumnTemp").find('.builtin').show();
    		$(this).parents(".tableColumnTemp").find('.dataVariable').hide();
    		
    		var builtinVariable = $('.builtinVariable').magicSuggest({
    	        width : 190,
    	        highlight : true,
    	        data : '/report/builtinVariable/selectBuiltinVariable.do?',
    	        method : 'get',
    	        queryParam : "key",
    	        allowFreeEntries : false,
    	        maxSelection : 1,
    	     
    	        renderer : function(v)
    	        {   
    	          return '<div>' + '<div >' + '<div>' + v.name + '</div>' + '<div style="clear:both;"></div>';
    	        },
    	    });
    		
    		$(builtinVariable).on('selectionchange', function(e, cb, s)
    	            {
    	                var str = cb.getValue();
    	                $.ajax({
    	                    url : '/report/builtinVariable/getBuiltinVariable.do',
    	                    traditional : true,
    	                    type : "POST",
    	                    data : {
    	                        id : str,
    	                    },
    	                    async : false,
    	                    success : function(data)
    	                    {
    	                    	
    	                    },
    	                    error : function()
    	                    {
    	                        parent.layer.alert('错误', {
    	                            title : "提示"
    	                        });
    	                    }
    	                })
    	            });
    	}else if("2" == $(this).val()){
    		$(this).parents(".tableColumnTemp").find('.builtin').hide();
    		$(this).parents(".tableColumnTemp").find('.dataVariable').show();
    	}else{
    		$(this).parents(".tableColumnTemp").find('.builtin').hide();
    		$(this).parents(".tableColumnTemp").find('.dataVariable').hide();
    	}
    }).on('change', '.dataTypeValue', function(){
    	if("1" == $(this).val()){
    		var $that = $(this);
    		$(this).parents(".dataVariable").find('.imageUpload').hide();
    		var dataTemplateId = $('#dataTemplateId').val();
    		var columnIndex = $(this).parents(".tableVariable").find('.filterIndex').val();
    		var columnName = $(this).parents(".tableVariable").find('.filterName').val();
    		
    		$.ajax({
                url : '/data/dataTemplate/dataTemplColumnList.do',
                traditional : true,
                type : "POST",
                data : {
                	dataTemplateId : dataTemplateId,
                	columnIndex : columnIndex,
                	columnName : columnName
                },
                async : false,
                success : function(data)
                {
                	if(null != data){
                		var selectList = "";
                		for(var i = 0; i<data.length; i++){
                			selectList = selectList + "<option value='"+data[i].id+"'>"+data[i].columnName+"</option>";
                		}
                		$that.parents(".dataVariableSelect").append("<div class='col-sm-7'><label class='col-sm-3 control-label'  style='width: 116px;'>数据模板列：</label><div class='col-sm-4'><select name='dataTemplateColumn' class='dataTemplateColumn' style='width: 140px; height: 30px;'><option value=''>请选择</option>"+selectList+"</select></div></div>");
                	}
                },
                error : function()
                {
                    parent.layer.alert('错误', {
                        title : "提示"
                    });
                }
            })
    	}else if("2" == $(this).val()){
    		$(this).parents(".dataVariable").find('.dataTemplateColumn').remove();
    		$(this).parents(".dataVariable").find('.imageUpload').show();
    	}else{
    		$(this).parents(".dataVariable").find('.dataTemplateColumn').remove();
    		$(this).parents(".dataVariable").find('.imageUpload').hide();
    	}
    }).on('click', '.removeBookmark', function(){
    	$(this).parents('.bookmarkTemp').remove();
    }).on('click', '.addTableColumn', function(){
       $(this).parents(".bookmarkTemp").find(".tableVariable").append(tableColumnTemp);
    }).on('click', '.removeTableColumn', function(){
    	$(this).parents('.tableColumnTemp').remove();
    }).on('click', '#uploadModal', function(){
   		$('.fileinput-upload-button').remove();
   	}).on('click', '#uploadImg', function(){
   		$('.fileinput-upload-button').remove();
   	}).on('click', '#butt', function(){
   		var fileName = $('#uploadData').val();
   	    var formatStr = '';
   	    var index = fileName.lastIndexOf('.');
   	    if (fileName.length == 0) {
   	        parent.layer.alert('请选择需要上传的文件', {title : "提示"});
   	        return;
   	    } else if (index > 0) {
   	        formatStr = fileName.substring(index);
   	        if (!(".doc" == formatStr || ".docx" == formatStr)) {
   	            parent.layer.alert('请上传word文件', {title : "提示"});
   	            return;
   	        }
   	    }
   	    $.ajax({
   	        url : '/report/reportTemplate/uploadTemplate.do',
   	        type : 'POST',
   	        cache : false,
   	        data : new FormData($('#uploadForm')[0]),
   	        processData : false,
   	        contentType : false,
   	       }).done(function(data) {
                $('#url').val(data);      
    			$('#myModal').modal('hide');
   		   }).fail(function(res){});
   	}).on('click', '.uploadImg', function(){
   		$imgNode = $(this);
   		$('#imgModal').modal('show');
   	}).on('click', '#imgButt', function(){
   		var fileName = $('#uploadImg').val();
   	    var formatStr = '';
   	    var index = fileName.lastIndexOf('.');
   	    if (fileName.length == 0) {
   	        parent.layer.alert('请选择需要上传的文件', {title : "提示"});
   	        return;
   	    } 
   	    $.ajax({
   	        url : '/report/reportTemplate/uploadImage.do',
   	        type : 'POST',
   	        cache : false,
   	        data : new FormData($('#uploadImgForm')[0]),
   	        processData : false,
   	        contentType : false,
   	       }).done(function(data) {
   	    	   	$imgNode.parents(".imageUpload").find('.imgUrl').val(data);     
    			$('#imgModal').modal('hide');
   		   }).fail(function(res){});
   	}).on('blur', '.bookmarkName', function(){
   		var $that = $(this);
   		var i = 0;
   	   	$('.bookmarkTemp').each(function(){
   	   		var name = $(this).find('.bookmarkName').val();
   	   		if("" != $that.val() && name == $that.val()){
   	   			i = i + 1;
   	   		}
   	   	});
   	   	if(i > 1){
   	   		parent.layer.alert('标签名称已存在，重新输入',{title:"提示"});
   	   		$that.val('');
   	   	}
   	});
	
	$("#reportTemplate_form").validate();
});