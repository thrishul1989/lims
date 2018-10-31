var zhiqingFile=[];
var fujianFile=[],familyFile=[];


	var recordFiguresInit = order.orderExamineeList[0].recordFiguresShow;
	var fujianConfig=[]
	if(recordFiguresInit!=undefined){
		if(recordFiguresInit.indexOf(",")!=0){
			var initPath = recordFiguresInit.split(",");
			var truePath = order.orderExamineeList[0].recordFigures.split(",");
			var picInit =[];
			for(var i=0;i<initPath.length;i++){
				if("" != initPath[i]){
					if(initPath[i].indexOf("doc")>0 || initPath[i].indexOf("txt")>0 || initPath[i].indexOf("xlsx")>0){ 
						picInit.push ("<div class='file-preview-other-frame'><div class='file-preview-other'><span class='file-icon-4x'><i class='fa fa-file-text-o text-info'></i></span></div></div><a style='display:flex' href='"+base+"/order/getSrcByPath.do?fileName="+truePath[i]+"'>下载</a>")
					}
					else{
						picInit.push ("<img src='"+initPath[i]+"' class='file-preview-image' style='max-width:700px;height:150px;'  /><a style='display:flex' href='"+base+"/order/getSrcByPath.do?fileName="+truePath[i]+"'>下载</a>");		
					}
					
					fujianConfig.push({caption:truePath[i],width:'120px',key:truePath[i]});
					fujianFile.push(truePath[i])
				}
				
			}
		
			
		}
		
	}
	
	var consentFiguresInit = order.orderExamineeList[0].consentFiguresShow;
	var zhiqingConfig=[];

	if(consentFiguresInit!=undefined){
		if(consentFiguresInit.indexOf(",")!=0){
			var initConsentPath = consentFiguresInit.split(",");
			var trueConsentPath=order.orderExamineeList[0].consentFigures.split(",")
			var picInit2 =[];
			for(var i=0;i<initConsentPath.length;i++){
				if("" != initConsentPath[i]){
					picInit2.push("<img  src='"+initConsentPath[i]+"' class='file-preview-image' style='max-width:700px;height:150px;'/><a style='display:flex' href='"+base+"/order/getSrcByPath.do?fileName="+trueConsentPath[i]+"'>下载</a>")
					zhiqingConfig.push({caption:trueConsentPath[i],width:'120px',key:trueConsentPath[i]});
					zhiqingFile.push(trueConsentPath[i])
				}
				
			}
			
			picInit2.join(",")
		
		}
	}

	

	var familyFiguresInit = order.orderExamineeList[0].familyFiguresShow;
	var familyConfig=[];
	if(familyFiguresInit!=undefined){
		if(familyFiguresInit.indexOf(",")!=0){
			var initConsentPath = familyFiguresInit.split(",");
			var trueConsentPath=order.orderExamineeList[0].familyFigures.split(",")
			var picInit3 =[];
			for(var i=0;i<initConsentPath.length;i++){
				if("" != initConsentPath[i]){
					picInit3.push("<img  src='"+initConsentPath[i]+"' class='file-preview-image' style='max-width:700px;height:200px;'/><a style='display:flex' href='"+base+"/order/getSrcByPath.do?fileName="+trueConsentPath[i]+"'>下载</a>")
					familyConfig.push({caption:trueConsentPath[i],width:'120px',key:trueConsentPath[i]});
					familyFile.push(trueConsentPath[i]);
				}
				
			}
			
			picInit3.join(",")
		}
	}

$(document).ready(function(){
	
	
	$("#familyFigures").fileinput({
		language: 'zh', // 设置语言
        uploadUrl: base+'/order/upload.do', 
        allowedFileExtensions : ['jpg', 'png','gif'],
        maxFileSize: 5000,
        showUpload: false, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        showRemove:false,
        initialPreview:picInit3,
        initialPreviewConfig:familyConfig,
	});

	$('#zhiqing').fileinput({
        language: 'zh', // 设置语言
        uploadUrl: base+'/order/upload.do', // 上传的地址
        allowedFileExtensions : ['jpg', 'png','gif'],// 接收的文件后缀
        maxFileSize: 5000,
        showUpload: false, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        showRemove:false,
        uploadAsync: false,
        initialPreview: picInit2,
        initialPreviewConfig:zhiqingConfig,
                        
                        
    });
	
	
	
	
	$("#fujian").fileinput({
		language: 'zh', // 设置语言
        uploadUrl: base+'/order/upload.do', // you must set a valid URL here else you will get an
        allowedFileExtensions : [],
        overwriteInitial: false,
        maxFileSize: 5000,
        showUpload: false, // 是否显示上传按钮
        showCaption: false,// 是否显示标题
        showRemove:false,
        
        initialPreview:picInit,
        initialPreviewConfig:fujianConfig,
        slugCallback: function(filename) {
            return filename.replace('(', '_').replace(']', '_');
        }
	})
   

$('.btn.btn-primary.btn-file').css('display','none')
	
	
})


