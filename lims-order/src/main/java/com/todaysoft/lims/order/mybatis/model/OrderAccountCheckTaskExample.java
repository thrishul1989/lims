package com.todaysoft.lims.order.mybatis.model;

import java.util.ArrayList;
import java.util.List;

public class OrderAccountCheckTaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public OrderAccountCheckTaskExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTradingDateIsNull() {
            addCriterion("TRADING_DATE is null");
            return (Criteria) this;
        }

        public Criteria andTradingDateIsNotNull() {
            addCriterion("TRADING_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andTradingDateEqualTo(String value) {
            addCriterion("TRADING_DATE =", value, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateNotEqualTo(String value) {
            addCriterion("TRADING_DATE <>", value, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateGreaterThan(String value) {
            addCriterion("TRADING_DATE >", value, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateGreaterThanOrEqualTo(String value) {
            addCriterion("TRADING_DATE >=", value, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateLessThan(String value) {
            addCriterion("TRADING_DATE <", value, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateLessThanOrEqualTo(String value) {
            addCriterion("TRADING_DATE <=", value, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateLike(String value) {
            addCriterion("TRADING_DATE like", value, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateNotLike(String value) {
            addCriterion("TRADING_DATE not like", value, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateIn(List<String> values) {
            addCriterion("TRADING_DATE in", values, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateNotIn(List<String> values) {
            addCriterion("TRADING_DATE not in", values, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateBetween(String value1, String value2) {
            addCriterion("TRADING_DATE between", value1, value2, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andTradingDateNotBetween(String value1, String value2) {
            addCriterion("TRADING_DATE not between", value1, value2, "tradingDate");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeIsNull() {
            addCriterion("INTERFACE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeIsNotNull() {
            addCriterion("INTERFACE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeEqualTo(Integer value) {
            addCriterion("INTERFACE_CODE =", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeNotEqualTo(Integer value) {
            addCriterion("INTERFACE_CODE <>", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeGreaterThan(Integer value) {
            addCriterion("INTERFACE_CODE >", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeGreaterThanOrEqualTo(Integer value) {
            addCriterion("INTERFACE_CODE >=", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeLessThan(Integer value) {
            addCriterion("INTERFACE_CODE <", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeLessThanOrEqualTo(Integer value) {
            addCriterion("INTERFACE_CODE <=", value, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeIn(List<Integer> values) {
            addCriterion("INTERFACE_CODE in", values, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeNotIn(List<Integer> values) {
            addCriterion("INTERFACE_CODE not in", values, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeBetween(Integer value1, Integer value2) {
            addCriterion("INTERFACE_CODE between", value1, value2, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andInterfaceCodeNotBetween(Integer value1, Integer value2) {
            addCriterion("INTERFACE_CODE not between", value1, value2, "interfaceCode");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateIsNull() {
            addCriterion("RECONCILIATION_DATE is null");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateIsNotNull() {
            addCriterion("RECONCILIATION_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateEqualTo(String value) {
            addCriterion("RECONCILIATION_DATE =", value, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateNotEqualTo(String value) {
            addCriterion("RECONCILIATION_DATE <>", value, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateGreaterThan(String value) {
            addCriterion("RECONCILIATION_DATE >", value, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateGreaterThanOrEqualTo(String value) {
            addCriterion("RECONCILIATION_DATE >=", value, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateLessThan(String value) {
            addCriterion("RECONCILIATION_DATE <", value, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateLessThanOrEqualTo(String value) {
            addCriterion("RECONCILIATION_DATE <=", value, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateLike(String value) {
            addCriterion("RECONCILIATION_DATE like", value, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateNotLike(String value) {
            addCriterion("RECONCILIATION_DATE not like", value, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateIn(List<String> values) {
            addCriterion("RECONCILIATION_DATE in", values, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateNotIn(List<String> values) {
            addCriterion("RECONCILIATION_DATE not in", values, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateBetween(String value1, String value2) {
            addCriterion("RECONCILIATION_DATE between", value1, value2, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationDateNotBetween(String value1, String value2) {
            addCriterion("RECONCILIATION_DATE not between", value1, value2, "reconciliationDate");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultIsNull() {
            addCriterion("RECONCILIATION_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultIsNotNull() {
            addCriterion("RECONCILIATION_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultEqualTo(Integer value) {
            addCriterion("RECONCILIATION_RESULT =", value, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultNotEqualTo(Integer value) {
            addCriterion("RECONCILIATION_RESULT <>", value, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultGreaterThan(Integer value) {
            addCriterion("RECONCILIATION_RESULT >", value, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("RECONCILIATION_RESULT >=", value, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultLessThan(Integer value) {
            addCriterion("RECONCILIATION_RESULT <", value, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultLessThanOrEqualTo(Integer value) {
            addCriterion("RECONCILIATION_RESULT <=", value, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultIn(List<Integer> values) {
            addCriterion("RECONCILIATION_RESULT in", values, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultNotIn(List<Integer> values) {
            addCriterion("RECONCILIATION_RESULT not in", values, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultBetween(Integer value1, Integer value2) {
            addCriterion("RECONCILIATION_RESULT between", value1, value2, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andReconciliationResultNotBetween(Integer value1, Integer value2) {
            addCriterion("RECONCILIATION_RESULT not between", value1, value2, "reconciliationResult");
            return (Criteria) this;
        }

        public Criteria andSettleStatusIsNull() {
            addCriterion("SETTLE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andSettleStatusIsNotNull() {
            addCriterion("SETTLE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andSettleStatusEqualTo(Integer value) {
            addCriterion("SETTLE_STATUS =", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusNotEqualTo(Integer value) {
            addCriterion("SETTLE_STATUS <>", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusGreaterThan(Integer value) {
            addCriterion("SETTLE_STATUS >", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("SETTLE_STATUS >=", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusLessThan(Integer value) {
            addCriterion("SETTLE_STATUS <", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusLessThanOrEqualTo(Integer value) {
            addCriterion("SETTLE_STATUS <=", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusIn(List<Integer> values) {
            addCriterion("SETTLE_STATUS in", values, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusNotIn(List<Integer> values) {
            addCriterion("SETTLE_STATUS not in", values, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusBetween(Integer value1, Integer value2) {
            addCriterion("SETTLE_STATUS between", value1, value2, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("SETTLE_STATUS not between", value1, value2, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusIsNull() {
            addCriterion("RECONCILIATION_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusIsNotNull() {
            addCriterion("RECONCILIATION_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusEqualTo(Integer value) {
            addCriterion("RECONCILIATION_STATUS =", value, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusNotEqualTo(Integer value) {
            addCriterion("RECONCILIATION_STATUS <>", value, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusGreaterThan(Integer value) {
            addCriterion("RECONCILIATION_STATUS >", value, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("RECONCILIATION_STATUS >=", value, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusLessThan(Integer value) {
            addCriterion("RECONCILIATION_STATUS <", value, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusLessThanOrEqualTo(Integer value) {
            addCriterion("RECONCILIATION_STATUS <=", value, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusIn(List<Integer> values) {
            addCriterion("RECONCILIATION_STATUS in", values, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusNotIn(List<Integer> values) {
            addCriterion("RECONCILIATION_STATUS not in", values, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusBetween(Integer value1, Integer value2) {
            addCriterion("RECONCILIATION_STATUS between", value1, value2, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andReconciliationStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("RECONCILIATION_STATUS not between", value1, value2, "reconciliationStatus");
            return (Criteria) this;
        }

        public Criteria andSolveResultIsNull() {
            addCriterion("SOLVE_RESULT is null");
            return (Criteria) this;
        }

        public Criteria andSolveResultIsNotNull() {
            addCriterion("SOLVE_RESULT is not null");
            return (Criteria) this;
        }

        public Criteria andSolveResultEqualTo(Integer value) {
            addCriterion("SOLVE_RESULT =", value, "solveResult");
            return (Criteria) this;
        }

        public Criteria andSolveResultNotEqualTo(Integer value) {
            addCriterion("SOLVE_RESULT <>", value, "solveResult");
            return (Criteria) this;
        }

        public Criteria andSolveResultGreaterThan(Integer value) {
            addCriterion("SOLVE_RESULT >", value, "solveResult");
            return (Criteria) this;
        }

        public Criteria andSolveResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("SOLVE_RESULT >=", value, "solveResult");
            return (Criteria) this;
        }

        public Criteria andSolveResultLessThan(Integer value) {
            addCriterion("SOLVE_RESULT <", value, "solveResult");
            return (Criteria) this;
        }

        public Criteria andSolveResultLessThanOrEqualTo(Integer value) {
            addCriterion("SOLVE_RESULT <=", value, "solveResult");
            return (Criteria) this;
        }

        public Criteria andSolveResultIn(List<Integer> values) {
            addCriterion("SOLVE_RESULT in", values, "solveResult");
            return (Criteria) this;
        }

        public Criteria andSolveResultNotIn(List<Integer> values) {
            addCriterion("SOLVE_RESULT not in", values, "solveResult");
            return (Criteria) this;
        }

        public Criteria andSolveResultBetween(Integer value1, Integer value2) {
            addCriterion("SOLVE_RESULT between", value1, value2, "solveResult");
            return (Criteria) this;
        }

        public Criteria andSolveResultNotBetween(Integer value1, Integer value2) {
            addCriterion("SOLVE_RESULT not between", value1, value2, "solveResult");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}