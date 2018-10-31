$(function()
{
    var ConfigForm = function(opts)
    {
        this.opts = opts;
        this.form_holder = $('#config_form');
    };

    ConfigForm.prototype.validateForm = function()
    {
        $.instance.form_holder.validate({
            submitHandler : function(form)
            {
                /* 在提交form表单之前 换掉名字,并重新排序,这样后台就不需过滤空 对象 */
                var i = 0;

                $('.config-task-select').each(function()
                {
                    $(this).attr("name", 'tasks[' + i + ']');
                    i++;
                });

                form.submit();
            }
        });
    };

    ConfigForm.prototype.events = function()
    {
        $('#target_sample').change(function()
        {
            var val = $(this).val();

            var select = $('.config-group-default .config-task-select');
            select.find('option.task').remove();

            if ('' == val)
            {
                select.val('');
                select.attr('disabled', 'disabled');
                $('.config-group-default .config-step').hide();
            }
            else
            {
                var select = $('.config-group-default .config-task-select');
                $.instance.renderTaskSelect($.instance.opts.sourceSampleId, select);
                select.val('');
                select.attr('disabled', false);
                $('.config-group-default .config-step').hide();
            }
        });

        $('.modal-body').on('change', '.config-task-select', function()
        {
            $(this).parents('.config-group').find('.config-step').val('0');
            $(this).parents('.config-group').find('.config-step').hide();
            $(this).parents('.config-group').nextAll().remove();

            var val = $(this).val();

            if ('' != val)
            {
                $(this).parents('.config-group').find('.config-step').show();
            }
        });

        $('.modal-body').on(
                'change',
                '.config-step',
                function()
                {
                    var val = $(this).val();

                    if ('0' == val)
                    {
                        $(this).parents('.config-group').nextAll().remove();
                    }
                    else
                    {
                        var group = $('#config_group_default').clone();
                        group.find('label').empty();
                        group.removeClass('.config-group-default');
                        group.attr('id', '');
                        group.find('.config-step').hide();

                        var output = $(this).parents('.config-group').find('.config-task-select option:selected').attr(
                                'data-output-id');
                        var select = group.find('.config-task-select');
                        select.find('option.task').remove();
                        $.instance.renderTaskSelect(output, select);
                        select.val('');
                        $(this).parents('.config-group').parent().append(group);
                    }
                });
    };

    ConfigForm.prototype.renderTaskSelect = function(sourceSampleId, select)
    {
        var url = $.instance.opts.task_filter_url + '?inputType=1&input=' + sourceSampleId;

        $.get(url, function(tasks)
        {
            var task;
            var option;

            for ( var i in tasks)
            {
                task = tasks[i];
                option = $('<option class="task"></option>');
                option.append(task.taskName);
                option.attr('value', task.id);
                option.attr('data-output-id', task.outputId);
                select.append(option);
            }
        });
    };

    $.instance;

    $.init = function(opts)
    {
        $.instance = new ConfigForm(opts);
        $.instance.validateForm();
        $.instance.events();
    };
});