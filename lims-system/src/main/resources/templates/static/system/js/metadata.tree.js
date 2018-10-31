$(function()
{
    var Metadata = function(opts)
    {
        this.opts = opts;
        this.tree_holder = $('#metadata_tree');
    };

    Metadata.prototype.renderTree = function()
    {
        var instance = this;

        instance.tree_holder.bind("loaded.jstree", function(e, data)
        {
            instance.tree_holder.jstree('open_all');
        });

        instance.tree_holder.on("changed.jstree", function(e, data)
        {
            var id = data.selected[0];
            var flag = id.substring(0, 1);


            if ("L" == flag)
            {
                window.location = instance.opts.metadata_url + '?key=' + data.selected[0];
            }
        });

        instance.tree_holder.jstree({
            'core' : {
                'data' : {
                    'url' : instance.opts.data_url,
                    'dataType' : 'json'
                }
            }
        });
    };

    $.init = function(opts)
    {
    
        var instance = new Metadata(opts);
        instance.renderTree();
    };
});