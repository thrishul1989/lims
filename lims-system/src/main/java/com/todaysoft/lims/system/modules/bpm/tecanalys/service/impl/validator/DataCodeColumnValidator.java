package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.validator;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyParseRecord;
import com.todaysoft.lims.utils.Collections3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.swing.text.StyledEditorKit;
import java.util.List;
import java.util.Map;

public class DataCodeColumnValidator implements ITechnicalAnalyRecordValidator
{
    private static Logger log = LoggerFactory.getLogger(DataCodeColumnValidator.class);
    
    @Override
    public void validate(TechnicalAnalyRecordValidateContext context)
    {
        TechnicalAnalyParseRecord target = context.getValidateTarget();
        List<String> taskComStr = context.getValidateDataCode();
        Map<String,Boolean> map = context.getValidateSubmitted();

        String dataCode = target.getData().getDataCode();
        String sample = target.getData().getSample();
        String combineStr = dataCode+"_"+sample;
        
        if (log.isInfoEnabled())
        {
            log.info("Validate dataCode {}, valid taskComStr {}",  dataCode, taskComStr);
        }
        
        if (StringUtils.isEmpty(dataCode))
        {
            target.setValid(false);
            target.setMessage("数据编号字段为空");
            return;
        }
        
        if (Collections3.isEmpty(taskComStr) || !taskComStr.contains(combineStr))
        {
            target.setValid(false);
            target.setMessage("数据-数据编号在任务单中不存在或和样本不对应");
            return;
        }

        if (map.containsKey(combineStr))
        {
            if(!map.get(combineStr))
            {
                target.setValid(false);
                target.setMessage("数据-该条数据对应的任务已经提交过验证或者未审核通过");
            }
            return;
        }
    }
}
