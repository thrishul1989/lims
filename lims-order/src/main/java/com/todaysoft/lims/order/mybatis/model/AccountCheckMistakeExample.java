package com.todaysoft.lims.order.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountCheckMistakeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AccountCheckMistakeExample() {
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

        public Criteria andAccountCheckTaskIdIsNull() {
            addCriterion("ACCOUNT_CHECK_TASK_ID is null");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdIsNotNull() {
            addCriterion("ACCOUNT_CHECK_TASK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdEqualTo(String value) {
            addCriterion("ACCOUNT_CHECK_TASK_ID =", value, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdNotEqualTo(String value) {
            addCriterion("ACCOUNT_CHECK_TASK_ID <>", value, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdGreaterThan(String value) {
            addCriterion("ACCOUNT_CHECK_TASK_ID >", value, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdGreaterThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_CHECK_TASK_ID >=", value, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdLessThan(String value) {
            addCriterion("ACCOUNT_CHECK_TASK_ID <", value, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdLessThanOrEqualTo(String value) {
            addCriterion("ACCOUNT_CHECK_TASK_ID <=", value, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdLike(String value) {
            addCriterion("ACCOUNT_CHECK_TASK_ID like", value, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdNotLike(String value) {
            addCriterion("ACCOUNT_CHECK_TASK_ID not like", value, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdIn(List<String> values) {
            addCriterion("ACCOUNT_CHECK_TASK_ID in", values, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdNotIn(List<String> values) {
            addCriterion("ACCOUNT_CHECK_TASK_ID not in", values, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdBetween(String value1, String value2) {
            addCriterion("ACCOUNT_CHECK_TASK_ID between", value1, value2, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andAccountCheckTaskIdNotBetween(String value1, String value2) {
            addCriterion("ACCOUNT_CHECK_TASK_ID not between", value1, value2, "accountCheckTaskId");
            return (Criteria) this;
        }

        public Criteria andBillDateIsNull() {
            addCriterion("BILL_DATE is null");
            return (Criteria) this;
        }

        public Criteria andBillDateIsNotNull() {
            addCriterion("BILL_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andBillDateEqualTo(String value) {
            addCriterion("BILL_DATE =", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotEqualTo(String value) {
            addCriterion("BILL_DATE <>", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateGreaterThan(String value) {
            addCriterion("BILL_DATE >", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateGreaterThanOrEqualTo(String value) {
            addCriterion("BILL_DATE >=", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateLessThan(String value) {
            addCriterion("BILL_DATE <", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateLessThanOrEqualTo(String value) {
            addCriterion("BILL_DATE <=", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateLike(String value) {
            addCriterion("BILL_DATE like", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotLike(String value) {
            addCriterion("BILL_DATE not like", value, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateIn(List<String> values) {
            addCriterion("BILL_DATE in", values, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotIn(List<String> values) {
            addCriterion("BILL_DATE not in", values, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateBetween(String value1, String value2) {
            addCriterion("BILL_DATE between", value1, value2, "billDate");
            return (Criteria) this;
        }

        public Criteria andBillDateNotBetween(String value1, String value2) {
            addCriterion("BILL_DATE not between", value1, value2, "billDate");
            return (Criteria) this;
        }

        public Criteria andBankTypeIsNull() {
            addCriterion("BANK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andBankTypeIsNotNull() {
            addCriterion("BANK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andBankTypeEqualTo(String value) {
            addCriterion("BANK_TYPE =", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotEqualTo(String value) {
            addCriterion("BANK_TYPE <>", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeGreaterThan(String value) {
            addCriterion("BANK_TYPE >", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_TYPE >=", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLessThan(String value) {
            addCriterion("BANK_TYPE <", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLessThanOrEqualTo(String value) {
            addCriterion("BANK_TYPE <=", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeLike(String value) {
            addCriterion("BANK_TYPE like", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotLike(String value) {
            addCriterion("BANK_TYPE not like", value, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeIn(List<String> values) {
            addCriterion("BANK_TYPE in", values, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotIn(List<String> values) {
            addCriterion("BANK_TYPE not in", values, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeBetween(String value1, String value2) {
            addCriterion("BANK_TYPE between", value1, value2, "bankType");
            return (Criteria) this;
        }

        public Criteria andBankTypeNotBetween(String value1, String value2) {
            addCriterion("BANK_TYPE not between", value1, value2, "bankType");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("ORDER_NO is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("ORDER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("ORDER_NO =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("ORDER_NO <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("ORDER_NO >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_NO >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("ORDER_NO <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("ORDER_NO <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("ORDER_NO like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("ORDER_NO not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("ORDER_NO in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("ORDER_NO not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("ORDER_NO between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("ORDER_NO not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andTradeTimeIsNull() {
            addCriterion("TRADE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andTradeTimeIsNotNull() {
            addCriterion("TRADE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTradeTimeEqualTo(String value) {
            addCriterion("TRADE_TIME =", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotEqualTo(String value) {
            addCriterion("TRADE_TIME <>", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeGreaterThan(String value) {
            addCriterion("TRADE_TIME >", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeGreaterThanOrEqualTo(String value) {
            addCriterion("TRADE_TIME >=", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeLessThan(String value) {
            addCriterion("TRADE_TIME <", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeLessThanOrEqualTo(String value) {
            addCriterion("TRADE_TIME <=", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeLike(String value) {
            addCriterion("TRADE_TIME like", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotLike(String value) {
            addCriterion("TRADE_TIME not like", value, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeIn(List<String> values) {
            addCriterion("TRADE_TIME in", values, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotIn(List<String> values) {
            addCriterion("TRADE_TIME not in", values, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeBetween(String value1, String value2) {
            addCriterion("TRADE_TIME between", value1, value2, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTradeTimeNotBetween(String value1, String value2) {
            addCriterion("TRADE_TIME not between", value1, value2, "tradeTime");
            return (Criteria) this;
        }

        public Criteria andTrxNoIsNull() {
            addCriterion("TRX_NO is null");
            return (Criteria) this;
        }

        public Criteria andTrxNoIsNotNull() {
            addCriterion("TRX_NO is not null");
            return (Criteria) this;
        }

        public Criteria andTrxNoEqualTo(String value) {
            addCriterion("TRX_NO =", value, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoNotEqualTo(String value) {
            addCriterion("TRX_NO <>", value, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoGreaterThan(String value) {
            addCriterion("TRX_NO >", value, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoGreaterThanOrEqualTo(String value) {
            addCriterion("TRX_NO >=", value, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoLessThan(String value) {
            addCriterion("TRX_NO <", value, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoLessThanOrEqualTo(String value) {
            addCriterion("TRX_NO <=", value, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoLike(String value) {
            addCriterion("TRX_NO like", value, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoNotLike(String value) {
            addCriterion("TRX_NO not like", value, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoIn(List<String> values) {
            addCriterion("TRX_NO in", values, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoNotIn(List<String> values) {
            addCriterion("TRX_NO not in", values, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoBetween(String value1, String value2) {
            addCriterion("TRX_NO between", value1, value2, "trxNo");
            return (Criteria) this;
        }

        public Criteria andTrxNoNotBetween(String value1, String value2) {
            addCriterion("TRX_NO not between", value1, value2, "trxNo");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIsNull() {
            addCriterion("ORDER_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIsNotNull() {
            addCriterion("ORDER_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAmountEqualTo(BigDecimal value) {
            addCriterion("ORDER_AMOUNT =", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotEqualTo(BigDecimal value) {
            addCriterion("ORDER_AMOUNT <>", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountGreaterThan(BigDecimal value) {
            addCriterion("ORDER_AMOUNT >", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_AMOUNT >=", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountLessThan(BigDecimal value) {
            addCriterion("ORDER_AMOUNT <", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_AMOUNT <=", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIn(List<BigDecimal> values) {
            addCriterion("ORDER_AMOUNT in", values, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotIn(List<BigDecimal> values) {
            addCriterion("ORDER_AMOUNT not in", values, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_AMOUNT between", value1, value2, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_AMOUNT not between", value1, value2, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andFeeIsNull() {
            addCriterion("FEE is null");
            return (Criteria) this;
        }

        public Criteria andFeeIsNotNull() {
            addCriterion("FEE is not null");
            return (Criteria) this;
        }

        public Criteria andFeeEqualTo(BigDecimal value) {
            addCriterion("FEE =", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotEqualTo(BigDecimal value) {
            addCriterion("FEE <>", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThan(BigDecimal value) {
            addCriterion("FEE >", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FEE >=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThan(BigDecimal value) {
            addCriterion("FEE <", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FEE <=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeIn(List<BigDecimal> values) {
            addCriterion("FEE in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotIn(List<BigDecimal> values) {
            addCriterion("FEE not in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FEE between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FEE not between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeIsNull() {
            addCriterion("BANK_TRADE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeIsNotNull() {
            addCriterion("BANK_TRADE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeEqualTo(String value) {
            addCriterion("BANK_TRADE_TIME =", value, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeNotEqualTo(String value) {
            addCriterion("BANK_TRADE_TIME <>", value, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeGreaterThan(String value) {
            addCriterion("BANK_TRADE_TIME >", value, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_TRADE_TIME >=", value, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeLessThan(String value) {
            addCriterion("BANK_TRADE_TIME <", value, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeLessThanOrEqualTo(String value) {
            addCriterion("BANK_TRADE_TIME <=", value, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeLike(String value) {
            addCriterion("BANK_TRADE_TIME like", value, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeNotLike(String value) {
            addCriterion("BANK_TRADE_TIME not like", value, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeIn(List<String> values) {
            addCriterion("BANK_TRADE_TIME in", values, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeNotIn(List<String> values) {
            addCriterion("BANK_TRADE_TIME not in", values, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeBetween(String value1, String value2) {
            addCriterion("BANK_TRADE_TIME between", value1, value2, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankTradeTimeNotBetween(String value1, String value2) {
            addCriterion("BANK_TRADE_TIME not between", value1, value2, "bankTradeTime");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoIsNull() {
            addCriterion("BANK_ORDER_NO is null");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoIsNotNull() {
            addCriterion("BANK_ORDER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoEqualTo(String value) {
            addCriterion("BANK_ORDER_NO =", value, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoNotEqualTo(String value) {
            addCriterion("BANK_ORDER_NO <>", value, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoGreaterThan(String value) {
            addCriterion("BANK_ORDER_NO >", value, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_ORDER_NO >=", value, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoLessThan(String value) {
            addCriterion("BANK_ORDER_NO <", value, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoLessThanOrEqualTo(String value) {
            addCriterion("BANK_ORDER_NO <=", value, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoLike(String value) {
            addCriterion("BANK_ORDER_NO like", value, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoNotLike(String value) {
            addCriterion("BANK_ORDER_NO not like", value, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoIn(List<String> values) {
            addCriterion("BANK_ORDER_NO in", values, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoNotIn(List<String> values) {
            addCriterion("BANK_ORDER_NO not in", values, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoBetween(String value1, String value2) {
            addCriterion("BANK_ORDER_NO between", value1, value2, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankOrderNoNotBetween(String value1, String value2) {
            addCriterion("BANK_ORDER_NO not between", value1, value2, "bankOrderNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoIsNull() {
            addCriterion("BANK_TRX_NO is null");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoIsNotNull() {
            addCriterion("BANK_TRX_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoEqualTo(String value) {
            addCriterion("BANK_TRX_NO =", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoNotEqualTo(String value) {
            addCriterion("BANK_TRX_NO <>", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoGreaterThan(String value) {
            addCriterion("BANK_TRX_NO >", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_TRX_NO >=", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoLessThan(String value) {
            addCriterion("BANK_TRX_NO <", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoLessThanOrEqualTo(String value) {
            addCriterion("BANK_TRX_NO <=", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoLike(String value) {
            addCriterion("BANK_TRX_NO like", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoNotLike(String value) {
            addCriterion("BANK_TRX_NO not like", value, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoIn(List<String> values) {
            addCriterion("BANK_TRX_NO in", values, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoNotIn(List<String> values) {
            addCriterion("BANK_TRX_NO not in", values, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoBetween(String value1, String value2) {
            addCriterion("BANK_TRX_NO between", value1, value2, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankTrxNoNotBetween(String value1, String value2) {
            addCriterion("BANK_TRX_NO not between", value1, value2, "bankTrxNo");
            return (Criteria) this;
        }

        public Criteria andBankAmountIsNull() {
            addCriterion("BANK_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andBankAmountIsNotNull() {
            addCriterion("BANK_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andBankAmountEqualTo(BigDecimal value) {
            addCriterion("BANK_AMOUNT =", value, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankAmountNotEqualTo(BigDecimal value) {
            addCriterion("BANK_AMOUNT <>", value, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankAmountGreaterThan(BigDecimal value) {
            addCriterion("BANK_AMOUNT >", value, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BANK_AMOUNT >=", value, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankAmountLessThan(BigDecimal value) {
            addCriterion("BANK_AMOUNT <", value, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BANK_AMOUNT <=", value, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankAmountIn(List<BigDecimal> values) {
            addCriterion("BANK_AMOUNT in", values, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankAmountNotIn(List<BigDecimal> values) {
            addCriterion("BANK_AMOUNT not in", values, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANK_AMOUNT between", value1, value2, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANK_AMOUNT not between", value1, value2, "bankAmount");
            return (Criteria) this;
        }

        public Criteria andBankFeeIsNull() {
            addCriterion("BANK_FEE is null");
            return (Criteria) this;
        }

        public Criteria andBankFeeIsNotNull() {
            addCriterion("BANK_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andBankFeeEqualTo(BigDecimal value) {
            addCriterion("BANK_FEE =", value, "bankFee");
            return (Criteria) this;
        }

        public Criteria andBankFeeNotEqualTo(BigDecimal value) {
            addCriterion("BANK_FEE <>", value, "bankFee");
            return (Criteria) this;
        }

        public Criteria andBankFeeGreaterThan(BigDecimal value) {
            addCriterion("BANK_FEE >", value, "bankFee");
            return (Criteria) this;
        }

        public Criteria andBankFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BANK_FEE >=", value, "bankFee");
            return (Criteria) this;
        }

        public Criteria andBankFeeLessThan(BigDecimal value) {
            addCriterion("BANK_FEE <", value, "bankFee");
            return (Criteria) this;
        }

        public Criteria andBankFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BANK_FEE <=", value, "bankFee");
            return (Criteria) this;
        }

        public Criteria andBankFeeIn(List<BigDecimal> values) {
            addCriterion("BANK_FEE in", values, "bankFee");
            return (Criteria) this;
        }

        public Criteria andBankFeeNotIn(List<BigDecimal> values) {
            addCriterion("BANK_FEE not in", values, "bankFee");
            return (Criteria) this;
        }

        public Criteria andBankFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANK_FEE between", value1, value2, "bankFee");
            return (Criteria) this;
        }

        public Criteria andBankFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BANK_FEE not between", value1, value2, "bankFee");
            return (Criteria) this;
        }

        public Criteria andErrTypeIsNull() {
            addCriterion("ERR_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andErrTypeIsNotNull() {
            addCriterion("ERR_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andErrTypeEqualTo(String value) {
            addCriterion("ERR_TYPE =", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotEqualTo(String value) {
            addCriterion("ERR_TYPE <>", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeGreaterThan(String value) {
            addCriterion("ERR_TYPE >", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ERR_TYPE >=", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeLessThan(String value) {
            addCriterion("ERR_TYPE <", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeLessThanOrEqualTo(String value) {
            addCriterion("ERR_TYPE <=", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeLike(String value) {
            addCriterion("ERR_TYPE like", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotLike(String value) {
            addCriterion("ERR_TYPE not like", value, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeIn(List<String> values) {
            addCriterion("ERR_TYPE in", values, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotIn(List<String> values) {
            addCriterion("ERR_TYPE not in", values, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeBetween(String value1, String value2) {
            addCriterion("ERR_TYPE between", value1, value2, "errType");
            return (Criteria) this;
        }

        public Criteria andErrTypeNotBetween(String value1, String value2) {
            addCriterion("ERR_TYPE not between", value1, value2, "errType");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNull() {
            addCriterion("HANDLE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNotNull() {
            addCriterion("HANDLE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusEqualTo(String value) {
            addCriterion("HANDLE_STATUS =", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotEqualTo(String value) {
            addCriterion("HANDLE_STATUS <>", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThan(String value) {
            addCriterion("HANDLE_STATUS >", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLE_STATUS >=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThan(String value) {
            addCriterion("HANDLE_STATUS <", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThanOrEqualTo(String value) {
            addCriterion("HANDLE_STATUS <=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLike(String value) {
            addCriterion("HANDLE_STATUS like", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotLike(String value) {
            addCriterion("HANDLE_STATUS not like", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIn(List<String> values) {
            addCriterion("HANDLE_STATUS in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotIn(List<String> values) {
            addCriterion("HANDLE_STATUS not in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusBetween(String value1, String value2) {
            addCriterion("HANDLE_STATUS between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotBetween(String value1, String value2) {
            addCriterion("HANDLE_STATUS not between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleValueIsNull() {
            addCriterion("HANDLE_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andHandleValueIsNotNull() {
            addCriterion("HANDLE_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andHandleValueEqualTo(String value) {
            addCriterion("HANDLE_VALUE =", value, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueNotEqualTo(String value) {
            addCriterion("HANDLE_VALUE <>", value, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueGreaterThan(String value) {
            addCriterion("HANDLE_VALUE >", value, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLE_VALUE >=", value, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueLessThan(String value) {
            addCriterion("HANDLE_VALUE <", value, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueLessThanOrEqualTo(String value) {
            addCriterion("HANDLE_VALUE <=", value, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueLike(String value) {
            addCriterion("HANDLE_VALUE like", value, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueNotLike(String value) {
            addCriterion("HANDLE_VALUE not like", value, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueIn(List<String> values) {
            addCriterion("HANDLE_VALUE in", values, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueNotIn(List<String> values) {
            addCriterion("HANDLE_VALUE not in", values, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueBetween(String value1, String value2) {
            addCriterion("HANDLE_VALUE between", value1, value2, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleValueNotBetween(String value1, String value2) {
            addCriterion("HANDLE_VALUE not between", value1, value2, "handleValue");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkIsNull() {
            addCriterion("HANDLE_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkIsNotNull() {
            addCriterion("HANDLE_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkEqualTo(String value) {
            addCriterion("HANDLE_REMARK =", value, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkNotEqualTo(String value) {
            addCriterion("HANDLE_REMARK <>", value, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkGreaterThan(String value) {
            addCriterion("HANDLE_REMARK >", value, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("HANDLE_REMARK >=", value, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkLessThan(String value) {
            addCriterion("HANDLE_REMARK <", value, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkLessThanOrEqualTo(String value) {
            addCriterion("HANDLE_REMARK <=", value, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkLike(String value) {
            addCriterion("HANDLE_REMARK like", value, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkNotLike(String value) {
            addCriterion("HANDLE_REMARK not like", value, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkIn(List<String> values) {
            addCriterion("HANDLE_REMARK in", values, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkNotIn(List<String> values) {
            addCriterion("HANDLE_REMARK not in", values, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkBetween(String value1, String value2) {
            addCriterion("HANDLE_REMARK between", value1, value2, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andHandleRemarkNotBetween(String value1, String value2) {
            addCriterion("HANDLE_REMARK not between", value1, value2, "handleRemark");
            return (Criteria) this;
        }

        public Criteria andOperatorNameIsNull() {
            addCriterion("OPERATOR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOperatorNameIsNotNull() {
            addCriterion("OPERATOR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorNameEqualTo(String value) {
            addCriterion("OPERATOR_NAME =", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotEqualTo(String value) {
            addCriterion("OPERATOR_NAME <>", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameGreaterThan(String value) {
            addCriterion("OPERATOR_NAME >", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATOR_NAME >=", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameLessThan(String value) {
            addCriterion("OPERATOR_NAME <", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameLessThanOrEqualTo(String value) {
            addCriterion("OPERATOR_NAME <=", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameLike(String value) {
            addCriterion("OPERATOR_NAME like", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotLike(String value) {
            addCriterion("OPERATOR_NAME not like", value, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameIn(List<String> values) {
            addCriterion("OPERATOR_NAME in", values, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotIn(List<String> values) {
            addCriterion("OPERATOR_NAME not in", values, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameBetween(String value1, String value2) {
            addCriterion("OPERATOR_NAME between", value1, value2, "operatorName");
            return (Criteria) this;
        }

        public Criteria andOperatorNameNotBetween(String value1, String value2) {
            addCriterion("OPERATOR_NAME not between", value1, value2, "operatorName");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CREATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CREATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CREATE_TIME =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CREATE_TIME <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CREATE_TIME >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CREATE_TIME <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_TIME <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CREATE_TIME in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CREATE_TIME not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_TIME not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeIsNull() {
            addCriterion("PAYMENT_FULL_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeIsNotNull() {
            addCriterion("PAYMENT_FULL_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeEqualTo(Date value) {
            addCriterion("PAYMENT_FULL_TIME =", value, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeNotEqualTo(Date value) {
            addCriterion("PAYMENT_FULL_TIME <>", value, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeGreaterThan(Date value) {
            addCriterion("PAYMENT_FULL_TIME >", value, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PAYMENT_FULL_TIME >=", value, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeLessThan(Date value) {
            addCriterion("PAYMENT_FULL_TIME <", value, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeLessThanOrEqualTo(Date value) {
            addCriterion("PAYMENT_FULL_TIME <=", value, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeIn(List<Date> values) {
            addCriterion("PAYMENT_FULL_TIME in", values, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeNotIn(List<Date> values) {
            addCriterion("PAYMENT_FULL_TIME not in", values, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeBetween(Date value1, Date value2) {
            addCriterion("PAYMENT_FULL_TIME between", value1, value2, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andPaymentFullTimeNotBetween(Date value1, Date value2) {
            addCriterion("PAYMENT_FULL_TIME not between", value1, value2, "paymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeIsNull() {
            addCriterion("BANK_PAYMENT_FULL_TIME is null");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeIsNotNull() {
            addCriterion("BANK_PAYMENT_FULL_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeEqualTo(Date value) {
            addCriterion("BANK_PAYMENT_FULL_TIME =", value, "bankPaymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeNotEqualTo(Date value) {
            addCriterion("BANK_PAYMENT_FULL_TIME <>", value, "bankPaymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeGreaterThan(Date value) {
            addCriterion("BANK_PAYMENT_FULL_TIME >", value, "bankPaymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("BANK_PAYMENT_FULL_TIME >=", value, "bankPaymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeLessThan(Date value) {
            addCriterion("BANK_PAYMENT_FULL_TIME <", value, "bankPaymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeLessThanOrEqualTo(Date value) {
            addCriterion("BANK_PAYMENT_FULL_TIME <=", value, "bankPaymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeIn(List<Date> values) {
            addCriterion("BANK_PAYMENT_FULL_TIME in", values, "bankPaymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeNotIn(List<Date> values) {
            addCriterion("BANK_PAYMENT_FULL_TIME not in", values, "bankPaymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeBetween(Date value1, Date value2) {
            addCriterion("BANK_PAYMENT_FULL_TIME between", value1, value2, "bankPaymentFullTime");
            return (Criteria) this;
        }

        public Criteria andBankPaymentFullTimeNotBetween(Date value1, Date value2) {
            addCriterion("BANK_PAYMENT_FULL_TIME not between", value1, value2, "bankPaymentFullTime");
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