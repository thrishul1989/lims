package com.todaysoft.lims.order.mybatis.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountCheckMistakeScratchPoolExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    public AccountCheckMistakeScratchPoolExample() {
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

        public Criteria andPaymentTimeIsNull() {
            addCriterion("PAYMENT_TIME is null");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeIsNotNull() {
            addCriterion("PAYMENT_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeEqualTo(Date value) {
            addCriterion("PAYMENT_TIME =", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotEqualTo(Date value) {
            addCriterion("PAYMENT_TIME <>", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeGreaterThan(Date value) {
            addCriterion("PAYMENT_TIME >", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("PAYMENT_TIME >=", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeLessThan(Date value) {
            addCriterion("PAYMENT_TIME <", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeLessThanOrEqualTo(Date value) {
            addCriterion("PAYMENT_TIME <=", value, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeIn(List<Date> values) {
            addCriterion("PAYMENT_TIME in", values, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotIn(List<Date> values) {
            addCriterion("PAYMENT_TIME not in", values, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeBetween(Date value1, Date value2) {
            addCriterion("PAYMENT_TIME between", value1, value2, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andPaymentTimeNotBetween(Date value1, Date value2) {
            addCriterion("PAYMENT_TIME not between", value1, value2, "paymentTime");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdIsNull() {
            addCriterion("CHECK_TASK_ID is null");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdIsNotNull() {
            addCriterion("CHECK_TASK_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdEqualTo(String value) {
            addCriterion("CHECK_TASK_ID =", value, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdNotEqualTo(String value) {
            addCriterion("CHECK_TASK_ID <>", value, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdGreaterThan(String value) {
            addCriterion("CHECK_TASK_ID >", value, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdGreaterThanOrEqualTo(String value) {
            addCriterion("CHECK_TASK_ID >=", value, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdLessThan(String value) {
            addCriterion("CHECK_TASK_ID <", value, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdLessThanOrEqualTo(String value) {
            addCriterion("CHECK_TASK_ID <=", value, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdLike(String value) {
            addCriterion("CHECK_TASK_ID like", value, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdNotLike(String value) {
            addCriterion("CHECK_TASK_ID not like", value, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdIn(List<String> values) {
            addCriterion("CHECK_TASK_ID in", values, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdNotIn(List<String> values) {
            addCriterion("CHECK_TASK_ID not in", values, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdBetween(String value1, String value2) {
            addCriterion("CHECK_TASK_ID between", value1, value2, "checkTaskId");
            return (Criteria) this;
        }

        public Criteria andCheckTaskIdNotBetween(String value1, String value2) {
            addCriterion("CHECK_TASK_ID not between", value1, value2, "checkTaskId");
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

        public Criteria andReferNoIsNull() {
            addCriterion("REFER_NO is null");
            return (Criteria) this;
        }

        public Criteria andReferNoIsNotNull() {
            addCriterion("REFER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andReferNoEqualTo(String value) {
            addCriterion("REFER_NO =", value, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoNotEqualTo(String value) {
            addCriterion("REFER_NO <>", value, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoGreaterThan(String value) {
            addCriterion("REFER_NO >", value, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoGreaterThanOrEqualTo(String value) {
            addCriterion("REFER_NO >=", value, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoLessThan(String value) {
            addCriterion("REFER_NO <", value, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoLessThanOrEqualTo(String value) {
            addCriterion("REFER_NO <=", value, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoLike(String value) {
            addCriterion("REFER_NO like", value, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoNotLike(String value) {
            addCriterion("REFER_NO not like", value, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoIn(List<String> values) {
            addCriterion("REFER_NO in", values, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoNotIn(List<String> values) {
            addCriterion("REFER_NO not in", values, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoBetween(String value1, String value2) {
            addCriterion("REFER_NO between", value1, value2, "referNo");
            return (Criteria) this;
        }

        public Criteria andReferNoNotBetween(String value1, String value2) {
            addCriterion("REFER_NO not between", value1, value2, "referNo");
            return (Criteria) this;
        }

        public Criteria andCheckAmountIsNull() {
            addCriterion("CHECK_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andCheckAmountIsNotNull() {
            addCriterion("CHECK_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andCheckAmountEqualTo(BigDecimal value) {
            addCriterion("CHECK_AMOUNT =", value, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andCheckAmountNotEqualTo(BigDecimal value) {
            addCriterion("CHECK_AMOUNT <>", value, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andCheckAmountGreaterThan(BigDecimal value) {
            addCriterion("CHECK_AMOUNT >", value, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andCheckAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CHECK_AMOUNT >=", value, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andCheckAmountLessThan(BigDecimal value) {
            addCriterion("CHECK_AMOUNT <", value, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andCheckAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CHECK_AMOUNT <=", value, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andCheckAmountIn(List<BigDecimal> values) {
            addCriterion("CHECK_AMOUNT in", values, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andCheckAmountNotIn(List<BigDecimal> values) {
            addCriterion("CHECK_AMOUNT not in", values, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andCheckAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHECK_AMOUNT between", value1, value2, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andCheckAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHECK_AMOUNT not between", value1, value2, "checkAmount");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("PAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("PAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(Integer value) {
            addCriterion("PAY_TYPE =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(Integer value) {
            addCriterion("PAY_TYPE <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(Integer value) {
            addCriterion("PAY_TYPE >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("PAY_TYPE >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(Integer value) {
            addCriterion("PAY_TYPE <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(Integer value) {
            addCriterion("PAY_TYPE <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<Integer> values) {
            addCriterion("PAY_TYPE in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<Integer> values) {
            addCriterion("PAY_TYPE not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(Integer value1, Integer value2) {
            addCriterion("PAY_TYPE between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("PAY_TYPE not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNull() {
            addCriterion("BATCH_NO is null");
            return (Criteria) this;
        }

        public Criteria andBatchNoIsNotNull() {
            addCriterion("BATCH_NO is not null");
            return (Criteria) this;
        }

        public Criteria andBatchNoEqualTo(String value) {
            addCriterion("BATCH_NO =", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotEqualTo(String value) {
            addCriterion("BATCH_NO <>", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThan(String value) {
            addCriterion("BATCH_NO >", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoGreaterThanOrEqualTo(String value) {
            addCriterion("BATCH_NO >=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThan(String value) {
            addCriterion("BATCH_NO <", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLessThanOrEqualTo(String value) {
            addCriterion("BATCH_NO <=", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoLike(String value) {
            addCriterion("BATCH_NO like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotLike(String value) {
            addCriterion("BATCH_NO not like", value, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoIn(List<String> values) {
            addCriterion("BATCH_NO in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotIn(List<String> values) {
            addCriterion("BATCH_NO not in", values, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoBetween(String value1, String value2) {
            addCriterion("BATCH_NO between", value1, value2, "batchNo");
            return (Criteria) this;
        }

        public Criteria andBatchNoNotBetween(String value1, String value2) {
            addCriterion("BATCH_NO not between", value1, value2, "batchNo");
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

        public Criteria andMerNumIsNull() {
            addCriterion("MER_NUM is null");
            return (Criteria) this;
        }

        public Criteria andMerNumIsNotNull() {
            addCriterion("MER_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andMerNumEqualTo(String value) {
            addCriterion("MER_NUM =", value, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumNotEqualTo(String value) {
            addCriterion("MER_NUM <>", value, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumGreaterThan(String value) {
            addCriterion("MER_NUM >", value, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumGreaterThanOrEqualTo(String value) {
            addCriterion("MER_NUM >=", value, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumLessThan(String value) {
            addCriterion("MER_NUM <", value, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumLessThanOrEqualTo(String value) {
            addCriterion("MER_NUM <=", value, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumLike(String value) {
            addCriterion("MER_NUM like", value, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumNotLike(String value) {
            addCriterion("MER_NUM not like", value, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumIn(List<String> values) {
            addCriterion("MER_NUM in", values, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumNotIn(List<String> values) {
            addCriterion("MER_NUM not in", values, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumBetween(String value1, String value2) {
            addCriterion("MER_NUM between", value1, value2, "merNum");
            return (Criteria) this;
        }

        public Criteria andMerNumNotBetween(String value1, String value2) {
            addCriterion("MER_NUM not between", value1, value2, "merNum");
            return (Criteria) this;
        }

        public Criteria andTermIdIsNull() {
            addCriterion("TERM_ID is null");
            return (Criteria) this;
        }

        public Criteria andTermIdIsNotNull() {
            addCriterion("TERM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTermIdEqualTo(String value) {
            addCriterion("TERM_ID =", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotEqualTo(String value) {
            addCriterion("TERM_ID <>", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdGreaterThan(String value) {
            addCriterion("TERM_ID >", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdGreaterThanOrEqualTo(String value) {
            addCriterion("TERM_ID >=", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdLessThan(String value) {
            addCriterion("TERM_ID <", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdLessThanOrEqualTo(String value) {
            addCriterion("TERM_ID <=", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdLike(String value) {
            addCriterion("TERM_ID like", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotLike(String value) {
            addCriterion("TERM_ID not like", value, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdIn(List<String> values) {
            addCriterion("TERM_ID in", values, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotIn(List<String> values) {
            addCriterion("TERM_ID not in", values, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdBetween(String value1, String value2) {
            addCriterion("TERM_ID between", value1, value2, "termId");
            return (Criteria) this;
        }

        public Criteria andTermIdNotBetween(String value1, String value2) {
            addCriterion("TERM_ID not between", value1, value2, "termId");
            return (Criteria) this;
        }

        public Criteria andTranDateIsNull() {
            addCriterion("TRAN_DATE is null");
            return (Criteria) this;
        }

        public Criteria andTranDateIsNotNull() {
            addCriterion("TRAN_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andTranDateEqualTo(String value) {
            addCriterion("TRAN_DATE =", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotEqualTo(String value) {
            addCriterion("TRAN_DATE <>", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateGreaterThan(String value) {
            addCriterion("TRAN_DATE >", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_DATE >=", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLessThan(String value) {
            addCriterion("TRAN_DATE <", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLessThanOrEqualTo(String value) {
            addCriterion("TRAN_DATE <=", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateLike(String value) {
            addCriterion("TRAN_DATE like", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotLike(String value) {
            addCriterion("TRAN_DATE not like", value, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateIn(List<String> values) {
            addCriterion("TRAN_DATE in", values, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotIn(List<String> values) {
            addCriterion("TRAN_DATE not in", values, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateBetween(String value1, String value2) {
            addCriterion("TRAN_DATE between", value1, value2, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranDateNotBetween(String value1, String value2) {
            addCriterion("TRAN_DATE not between", value1, value2, "tranDate");
            return (Criteria) this;
        }

        public Criteria andTranTypeIsNull() {
            addCriterion("TRAN_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTranTypeIsNotNull() {
            addCriterion("TRAN_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTranTypeEqualTo(String value) {
            addCriterion("TRAN_TYPE =", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotEqualTo(String value) {
            addCriterion("TRAN_TYPE <>", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeGreaterThan(String value) {
            addCriterion("TRAN_TYPE >", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TRAN_TYPE >=", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeLessThan(String value) {
            addCriterion("TRAN_TYPE <", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeLessThanOrEqualTo(String value) {
            addCriterion("TRAN_TYPE <=", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeLike(String value) {
            addCriterion("TRAN_TYPE like", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotLike(String value) {
            addCriterion("TRAN_TYPE not like", value, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeIn(List<String> values) {
            addCriterion("TRAN_TYPE in", values, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotIn(List<String> values) {
            addCriterion("TRAN_TYPE not in", values, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeBetween(String value1, String value2) {
            addCriterion("TRAN_TYPE between", value1, value2, "tranType");
            return (Criteria) this;
        }

        public Criteria andTranTypeNotBetween(String value1, String value2) {
            addCriterion("TRAN_TYPE not between", value1, value2, "tranType");
            return (Criteria) this;
        }

        public Criteria andOSerialnoIsNull() {
            addCriterion("O_SERIALNO is null");
            return (Criteria) this;
        }

        public Criteria andOSerialnoIsNotNull() {
            addCriterion("O_SERIALNO is not null");
            return (Criteria) this;
        }

        public Criteria andOSerialnoEqualTo(String value) {
            addCriterion("O_SERIALNO =", value, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoNotEqualTo(String value) {
            addCriterion("O_SERIALNO <>", value, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoGreaterThan(String value) {
            addCriterion("O_SERIALNO >", value, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoGreaterThanOrEqualTo(String value) {
            addCriterion("O_SERIALNO >=", value, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoLessThan(String value) {
            addCriterion("O_SERIALNO <", value, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoLessThanOrEqualTo(String value) {
            addCriterion("O_SERIALNO <=", value, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoLike(String value) {
            addCriterion("O_SERIALNO like", value, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoNotLike(String value) {
            addCriterion("O_SERIALNO not like", value, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoIn(List<String> values) {
            addCriterion("O_SERIALNO in", values, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoNotIn(List<String> values) {
            addCriterion("O_SERIALNO not in", values, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoBetween(String value1, String value2) {
            addCriterion("O_SERIALNO between", value1, value2, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOSerialnoNotBetween(String value1, String value2) {
            addCriterion("O_SERIALNO not between", value1, value2, "oSerialno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoIsNull() {
            addCriterion("O_BATCHNO is null");
            return (Criteria) this;
        }

        public Criteria andOBatchnoIsNotNull() {
            addCriterion("O_BATCHNO is not null");
            return (Criteria) this;
        }

        public Criteria andOBatchnoEqualTo(String value) {
            addCriterion("O_BATCHNO =", value, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoNotEqualTo(String value) {
            addCriterion("O_BATCHNO <>", value, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoGreaterThan(String value) {
            addCriterion("O_BATCHNO >", value, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoGreaterThanOrEqualTo(String value) {
            addCriterion("O_BATCHNO >=", value, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoLessThan(String value) {
            addCriterion("O_BATCHNO <", value, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoLessThanOrEqualTo(String value) {
            addCriterion("O_BATCHNO <=", value, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoLike(String value) {
            addCriterion("O_BATCHNO like", value, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoNotLike(String value) {
            addCriterion("O_BATCHNO not like", value, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoIn(List<String> values) {
            addCriterion("O_BATCHNO in", values, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoNotIn(List<String> values) {
            addCriterion("O_BATCHNO not in", values, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoBetween(String value1, String value2) {
            addCriterion("O_BATCHNO between", value1, value2, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andOBatchnoNotBetween(String value1, String value2) {
            addCriterion("O_BATCHNO not between", value1, value2, "oBatchno");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNull() {
            addCriterion("SERIAL_NO is null");
            return (Criteria) this;
        }

        public Criteria andSerialNoIsNotNull() {
            addCriterion("SERIAL_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSerialNoEqualTo(String value) {
            addCriterion("SERIAL_NO =", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotEqualTo(String value) {
            addCriterion("SERIAL_NO <>", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThan(String value) {
            addCriterion("SERIAL_NO >", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoGreaterThanOrEqualTo(String value) {
            addCriterion("SERIAL_NO >=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThan(String value) {
            addCriterion("SERIAL_NO <", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLessThanOrEqualTo(String value) {
            addCriterion("SERIAL_NO <=", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoLike(String value) {
            addCriterion("SERIAL_NO like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotLike(String value) {
            addCriterion("SERIAL_NO not like", value, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoIn(List<String> values) {
            addCriterion("SERIAL_NO in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotIn(List<String> values) {
            addCriterion("SERIAL_NO not in", values, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoBetween(String value1, String value2) {
            addCriterion("SERIAL_NO between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andSerialNoNotBetween(String value1, String value2) {
            addCriterion("SERIAL_NO not between", value1, value2, "serialNo");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeIsNull() {
            addCriterion("WANG_PAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeIsNotNull() {
            addCriterion("WANG_PAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeEqualTo(String value) {
            addCriterion("WANG_PAY_TYPE =", value, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeNotEqualTo(String value) {
            addCriterion("WANG_PAY_TYPE <>", value, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeGreaterThan(String value) {
            addCriterion("WANG_PAY_TYPE >", value, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("WANG_PAY_TYPE >=", value, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeLessThan(String value) {
            addCriterion("WANG_PAY_TYPE <", value, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeLessThanOrEqualTo(String value) {
            addCriterion("WANG_PAY_TYPE <=", value, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeLike(String value) {
            addCriterion("WANG_PAY_TYPE like", value, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeNotLike(String value) {
            addCriterion("WANG_PAY_TYPE not like", value, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeIn(List<String> values) {
            addCriterion("WANG_PAY_TYPE in", values, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeNotIn(List<String> values) {
            addCriterion("WANG_PAY_TYPE not in", values, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeBetween(String value1, String value2) {
            addCriterion("WANG_PAY_TYPE between", value1, value2, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andWangPayTypeNotBetween(String value1, String value2) {
            addCriterion("WANG_PAY_TYPE not between", value1, value2, "wangPayType");
            return (Criteria) this;
        }

        public Criteria andExt1IsNull() {
            addCriterion("EXT1 is null");
            return (Criteria) this;
        }

        public Criteria andExt1IsNotNull() {
            addCriterion("EXT1 is not null");
            return (Criteria) this;
        }

        public Criteria andExt1EqualTo(String value) {
            addCriterion("EXT1 =", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotEqualTo(String value) {
            addCriterion("EXT1 <>", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThan(String value) {
            addCriterion("EXT1 >", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1GreaterThanOrEqualTo(String value) {
            addCriterion("EXT1 >=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThan(String value) {
            addCriterion("EXT1 <", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1LessThanOrEqualTo(String value) {
            addCriterion("EXT1 <=", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Like(String value) {
            addCriterion("EXT1 like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotLike(String value) {
            addCriterion("EXT1 not like", value, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1In(List<String> values) {
            addCriterion("EXT1 in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotIn(List<String> values) {
            addCriterion("EXT1 not in", values, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1Between(String value1, String value2) {
            addCriterion("EXT1 between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt1NotBetween(String value1, String value2) {
            addCriterion("EXT1 not between", value1, value2, "ext1");
            return (Criteria) this;
        }

        public Criteria andExt2IsNull() {
            addCriterion("EXT2 is null");
            return (Criteria) this;
        }

        public Criteria andExt2IsNotNull() {
            addCriterion("EXT2 is not null");
            return (Criteria) this;
        }

        public Criteria andExt2EqualTo(String value) {
            addCriterion("EXT2 =", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotEqualTo(String value) {
            addCriterion("EXT2 <>", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThan(String value) {
            addCriterion("EXT2 >", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2GreaterThanOrEqualTo(String value) {
            addCriterion("EXT2 >=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThan(String value) {
            addCriterion("EXT2 <", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2LessThanOrEqualTo(String value) {
            addCriterion("EXT2 <=", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Like(String value) {
            addCriterion("EXT2 like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotLike(String value) {
            addCriterion("EXT2 not like", value, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2In(List<String> values) {
            addCriterion("EXT2 in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotIn(List<String> values) {
            addCriterion("EXT2 not in", values, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2Between(String value1, String value2) {
            addCriterion("EXT2 between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andExt2NotBetween(String value1, String value2) {
            addCriterion("EXT2 not between", value1, value2, "ext2");
            return (Criteria) this;
        }

        public Criteria andAddValIsNull() {
            addCriterion("ADD_VAL is null");
            return (Criteria) this;
        }

        public Criteria andAddValIsNotNull() {
            addCriterion("ADD_VAL is not null");
            return (Criteria) this;
        }

        public Criteria andAddValEqualTo(String value) {
            addCriterion("ADD_VAL =", value, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValNotEqualTo(String value) {
            addCriterion("ADD_VAL <>", value, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValGreaterThan(String value) {
            addCriterion("ADD_VAL >", value, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValGreaterThanOrEqualTo(String value) {
            addCriterion("ADD_VAL >=", value, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValLessThan(String value) {
            addCriterion("ADD_VAL <", value, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValLessThanOrEqualTo(String value) {
            addCriterion("ADD_VAL <=", value, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValLike(String value) {
            addCriterion("ADD_VAL like", value, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValNotLike(String value) {
            addCriterion("ADD_VAL not like", value, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValIn(List<String> values) {
            addCriterion("ADD_VAL in", values, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValNotIn(List<String> values) {
            addCriterion("ADD_VAL not in", values, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValBetween(String value1, String value2) {
            addCriterion("ADD_VAL between", value1, value2, "addVal");
            return (Criteria) this;
        }

        public Criteria andAddValNotBetween(String value1, String value2) {
            addCriterion("ADD_VAL not between", value1, value2, "addVal");
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