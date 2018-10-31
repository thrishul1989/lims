
//重新给用户list下标排序
var i=0;
function setName(){
    $('.deleteTr').each(function(e){
       var $name= $(this).parents('tr').find("input[name='name']");
       $name.attr('name','userArchiveList['+e+'].name');
       var $phone=$(this).parents('tr').find("input[name='phone']");
       $phone.attr('name','userArchiveList['+e+'].phone');
       var $sex=$(this).parents('tr').find("input[name='sex']");
       $sex.attr('name','userArchiveList['+e+'].sex');
       var $role=$(this).parents('tr').find("input[name='role']");
       $role.attr('name','userArchinveList['+e+'].role');
       i++;
    })
}

