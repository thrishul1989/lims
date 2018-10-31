$(function()
{
    var RoleForm = function(opts)
    {
        this.opts = opts;
        this.base = opts.base || '';
        this.checked_nodes = opts.checked_nodes || [];
        this.tree_holder = $('#authority_tree');
    };

    RoleForm.prototype.renderAuthorityTree = function()
    {
        var instance = this;

        instance.tree_holder.bind("loaded.jstree", function(e, data)
        {
            instance.tree_holder.jstree('check_node', instance.checked_nodes);
            //instance.tree_holder.jstree(true).disable_node ($('li:last'));
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
    $.init = function(opts)
    {
        var instance = new RoleForm(opts);
        instance.renderAuthorityTree();
    };
});