$(function()
{
    var RoleForm = function(opts)
    {
        this.opts = opts;
        this.base = opts.base || '';
        this.checked_nodes = opts.checked_nodes || [];
        this.tree_holder = $('#authority_tree');
        this.form_holder = $('#role_form');
    };

    RoleForm.prototype.renderAuthorityTree = function()
    {
        var instance = this;

        instance.tree_holder.bind("loaded.jstree", function(e, data)
        {
            instance.tree_holder.jstree('check_node', instance.checked_nodes);
           
        });

        instance.tree_holder.jstree({
            'core' : {
                'data' : {
                    'url' : instance.opts.data_url,
                    'dataType' : 'json'
                }
            },
            'checkbox' : {
                'keep_selected_style' : false
            },
            'plugins' : [ 'checkbox' ]
        });
    };

    RoleForm.prototype.validateForm = function()
    {
        var instance = this;

        instance.form_holder.validate({
            rules : {
                'name' : {
                    'required' : true,
                    'maxlength':20,
                    'remote': {
                        'url': "/smm/role/validate.do",       //后台进行唯一性校证
                        'type': "post",
                        'dataType': "json",
                        'data': {
                            name: function () {
                                return $("#name").val();
                            },
                            id : function () {
                                return $('#id').val();
                            }
                        }
                    }
                }
            },
            messages : {
                'name' : {
                    'required' : "请输入角色名称",
                    'remote' : "当前角色已存在"
                }
            },
            submitHandler : function(form)
            {
                var nodes = instance.tree_holder.jstree('get_selected');


                for (var i = 0; i < nodes.length; i++)
                {
                    var element = $('<input>');
                    element.attr('type', 'hidden');
                    element.attr('name', 'authorities[' + i + ']');
                    element.attr('value', nodes[i]);
                    instance.form_holder.append(element);
                }

                form.submit();
            }
        });
    };

    RoleForm.prototype.isModify = function()
    {
        var instance = this;
        return instance.opts.modify != undefined;
    };

    $.init = function(opts)
    {
        var instance = new RoleForm(opts);
        instance.renderAuthorityTree();
        instance.validateForm();

        if (instance.isModify())
        {
            $('.toggle-title').empty().append(instance.opts.modify_title);
            instance.form_holder.attr('action', instance.opts.modify_action);
        }
    };
});