var sj = $.extend({}, sj);

var setting = {  
    async: {  
        enable: true,  
        url:''  
    },  
    check: {  
        enable: false  
    },  
    data: {  
        simpleData: {  
            enable: true  
        }  
    },  
    view: {  
        expandSpeed: "" ,
        showLine: true
    },  
    callback: {  
        //beforeExpand: beforeExpand,  
        //onAsyncSuccess: onAsyncSuccess,  
        //onAsyncError: onAsyncError,  
        onClick: zTreeOnClick  
    }  
}; 
sj.getContextPath = function() { 
	//获取页面完整地址
	var host = window.location.host;
	return "http://"+host+"/"; 
} 

var zNodes =[  
             {name:"分类管理", id:"1",pId:null,iconOpen:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_open.png", iconClose:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_close.png"},  
             {name:"设备", id:"2",pId:1,iconOpen:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_open.png", iconClose:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_close.png"},  
             {name:"试剂", id:"3",pId:1,iconOpen:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_open.png", iconClose:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_close.png"}, 
             {name:"样本", id:"4",pId:1,iconOpen:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_open.png", iconClose:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_close.png"},
             {name:"实验产物", id:"5",pId:1,iconOpen:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_open.png", iconClose:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_close.png"},
             {name:"实验设备", id:"6",pId:2,iconOpen:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_open.png", iconClose:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_close.png"},
             {name:"存储容器", id:"7",pId:2,iconOpen:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_open.png", iconClose:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_close.png"},
             {name:"原料", id:"8",pId:3,iconOpen:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_open.png", iconClose:sj.getContextPath()+"static/plugins/ztreeBase/css/zTreeStyle/img/diy/1_close.png"}
           ];  

function getUrlByNodeId(treeId, treeNode) {  
   return "getNodesDataById?treeNodeId="+treeNode.id;
}  


function zTreeOnClick(event, treeId, treeNode) {  
    document.getElementById("iframepage").src="treeClickPage.do?treeNodeId="+treeNode.id;  
}


function bp() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};



var ztree;

$(document).ready(function(){
	
	ztree = $.fn.zTree.init($("#catetree"), setting, zNodes);//初始化zTree树  
	ztree.expandAll(true); 
}); 

