$(function()
{
    var UserForm = function(opts)
    {
        this.opts = opts;
        this.base = opts.base || '';
        this.form_holder = $('#user_form');
    };

    UserForm.prototype.validateForm = function()
    {
        var instance = this;

        instance.form_holder.validate({
            rules : {
                'username' : {
                    required : true,
                    remote : instance.base + '/smm/user/unique.do'
                },
                'password' : {
                    required : true,
                    minlength : 6
                },
                'password_sure' : {
                    equalTo : "#password"
                },
                'archive.name' : {
                	required : true,
                },
                'archive.gender' : {
                	required : true,
                },
                'roles' : {
                	required : true,
                },
                'archive.phone' : {
                	required : true,
                	isMobile : true,
                	remote: {
            		    url: instance.base + "/smm/user/validate.do",     
            		    type: "post",               
            		    dataType: "json",              
            		    data: {                    
            		        phone: function() {
            		            return $("#phone").val();
            		        },
            		        id:function() {
            		            return $("#id").val();
            		        },
            		    }
            		}
                },
                'archive.employeeNo' : {
                	required : true,
                	remote :{
            		    url: instance.base + '/smm/user/uniqueNum.do',     
            		    type: "post",               
            		    dataType: "json",              
            		    data: {                    
            		    	employeeNo: function() {
            		            return $("#employeeNo").val();
            		            
            		        },
            		        id:function() {
            		            return $("#id").val();
            		        },
            		    }
            		}
                }
            },
            messages : {
                'username' : {
                    required : "请输入用户名",
                    remote : '用户名已存在'
                },
                'password' : {
                    required : "请输入密码",
                    minlength : "密码长度不能小于 6 个字符"
                },
                'password_sure' : {
                    equalTo : "重复密码不一致，请重新输入"
                },
                'archive.phone' :{
                	remote : '手机号码重复'
                },
                'archive.employeeNo' :{
                	remote : '该工号已存在'
                }
            }
        });
    };

    UserForm.prototype.isModify = function()
    {
        var instance = this;
        return instance.opts.modify != undefined;
    };

    UserForm.prototype.renderAsModify = function()
    {
        var instance = this;

        $('.toggle-title').empty().append(instance.opts.modify_title);
        instance.form_holder.attr('action', instance.opts.modify_action);
        $('#username').attr('readonly', 'readonly');
        $('#username').attr('name', '');
        $('.password-group').hide();

        var roles = instance.opts.roles || [];

        $("#roles option").each(function()
        {
            for (var i = 0; i < roles.length; i++)
            {
                if ($(this).val() == roles[i])
                {
                    $(this).attr("selected", "selected");
                }
            }
        });

        $('#roles').trigger("chosen:updated");

    };

    $.init = function(opts)
    {
        var instance = new UserForm(opts);
        instance.validateForm();

        if (instance.isModify())
        {
            instance.renderAsModify();
        }
    };

});