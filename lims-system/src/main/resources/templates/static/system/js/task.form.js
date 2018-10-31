
	  
	
 var currentShowCity=0;
$(document).ready(function(e){

   $("#province").change(function(){
	   $("#province option").each(function(i,o){
		   if($(this).attr("selected"))
		   {
		 
			   $(".city").hide();
			   $(".city").eq(i).show();
			   currentShowCity=i;
		   }
	   });
   });
   $("#province").change();
   
   $("#type_" ).change(function(){
	   $("#type_  option").each(function(i,o){
		   if($(this).attr("selected"))
		   {	
			   $(".choice_").hide();
			   $(".choice_").eq(i).show();
		   }
	   });
   });
   $("#type_").change(); 
   

});
var j =0;

function addTaskInput() {
	var replaceId = "taskInput" +j;
	var tpi="taskInputTitle"+j;
	var typeId="type_"+j;
	var choiceId="choice_"+j;
	re = new RegExp("choice_", "g");
	var taskInputHtml = $("#taskInput").clone().html();	
	alert(taskInputHtml);
	var dd=taskInputHtml.replace("taskInputTitle", tpi).replace("type_", typeId).replace(re,choiceId);
/*	$("#selectOut").append("<div id=\""+replaceId+"\" class=\"form-group\" style=\"height: 80\">"+dd+			
			"<input type=\"button\"  onclick=\"removeDom(this)\" value=\"-\" \/>"	
	        +"</div>");*/
	if(!isNaN(j)){
	$("#"+tpi).html("");
	}	
	j++;	
}

function removeDom(obj){
	jQuery(obj).parent().remove();
}
$(document).change(function (e) {
	var  id= $(e.target).attr('id'); 
	var arr=id.split('_');
	 $("#"+id+" "+" option").each(function(i,o){
		   if($(this).attr("selected"))
		   {	
               $(".choice_"+arr[1]).hide();
			   $(".choice_"+arr[1]).eq(i).show();
		   }
	   });	
})
 