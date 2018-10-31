package com.todaysoft.lims.testing.abnormal.service.core;

import java.util.List;

import com.todaysoft.lims.testing.abnormal.model.AbnormalTaskSolveRequest;

public interface IAbnormalSolver
{
    String solve(AbnormalTaskSolveRequest request, String token, List<String> scheduleIds);
}
