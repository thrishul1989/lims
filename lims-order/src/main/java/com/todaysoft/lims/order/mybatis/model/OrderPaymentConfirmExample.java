package com.todaysoft.lims.order.mybatis.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderPaymentConfirmExample
{
    protected String orderByClause;
    
    protected boolean distinct;
    
    protected List<Criteria> oredCriteria;
    
    protected int limitStart = -1;
    
    protected int limitEnd = -1;
    
    public OrderPaymentConfirmExample()
    {
        oredCriteria = new ArrayList<Criteria>();
    }
    
    public void setOrderByClause(String orderByClause)
    {
        this.orderByClause = orderByClause;
    }
    
    public String getOrderByClause()
    {
        return orderByClause;
    }
    
    public void setDistinct(boolean distinct)
    {
        this.distinct = distinct;
    }
    
    public boolean isDistinct()
    {
        return distinct;
    }
    
    public List<Criteria> getOredCriteria()
    {
        return oredCriteria;
    }
    
    public void or(Criteria criteria)
    {
        oredCriteria.add(criteria);
    }
    
    public Criteria or()
    {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }
    
    public Criteria createCriteria()
    {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0)
        {
            oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected Criteria createCriteriaInternal()
    {
        Criteria criteria = new Criteria();
        return criteria;
    }
    
    public void clear()
    {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }
    
    public void setLimitStart(int limitStart)
    {
        this.limitStart = limitStart;
    }
    
    public int getLimitStart()
    {
        return limitStart;
    }
    
    public void setLimitEnd(int limitEnd)
    {
        this.limitEnd = limitEnd;
    }
    
    public int getLimitEnd()
    {
        return limitEnd;
    }
    
    protected abstract static class GeneratedCriteria
    {
        protected List<Criterion> criteria;
        
        protected GeneratedCriteria()
        {
            super();
            criteria = new ArrayList<Criterion>();
        }
        
        public boolean isValid()
        {
            return criteria.size() > 0;
        }
        
        public List<Criterion> getAllCriteria()
        {
            return criteria;
        }
        
        public List<Criterion> getCriteria()
        {
            return criteria;
        }
        
        protected void addCriterion(String condition)
        {
            if (condition == null)
            {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }
        
        protected void addCriterion(String condition, Object value, String property)
        {
            if (value == null)
            {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }
        
        protected void addCriterion(String condition, Object value1, Object value2, String property)
        {
            if (value1 == null || value2 == null)
            {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }
        
        public Criteria andIdIsNull()
        {
            addCriterion("ID is null");
            return (Criteria)this;
        }
        
        public Criteria andIdIsNotNull()
        {
            addCriterion("ID is not null");
            return (Criteria)this;
        }
        
        public Criteria andIdEqualTo(String value)
        {
            addCriterion("ID =", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(String value)
        {
            addCriterion("ID <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(String value)
        {
            addCriterion("ID >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(String value)
        {
            addCriterion("ID >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(String value)
        {
            addCriterion("ID <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(String value)
        {
            addCriterion("ID <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLike(String value)
        {
            addCriterion("ID like", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotLike(String value)
        {
            addCriterion("ID not like", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdIn(List<String> values)
        {
            addCriterion("ID in", values, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotIn(List<String> values)
        {
            addCriterion("ID not in", values, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdBetween(String value1, String value2)
        {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotBetween(String value1, String value2)
        {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdIsNull()
        {
            addCriterion("ORDER_ID is null");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdIsNotNull()
        {
            addCriterion("ORDER_ID is not null");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdEqualTo(String value)
        {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdNotEqualTo(String value)
        {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdGreaterThan(String value)
        {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdGreaterThanOrEqualTo(String value)
        {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdLessThan(String value)
        {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdLessThanOrEqualTo(String value)
        {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdLike(String value)
        {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdNotLike(String value)
        {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdIn(List<String> values)
        {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdNotIn(List<String> values)
        {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdBetween(String value1, String value2)
        {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOrderIdNotBetween(String value1, String value2)
        {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdIsNull()
        {
            addCriterion("OPOS_ID is null");
            return (Criteria)this;
        }
        
        public Criteria andOposIdIsNotNull()
        {
            addCriterion("OPOS_ID is not null");
            return (Criteria)this;
        }
        
        public Criteria andOposIdEqualTo(String value)
        {
            addCriterion("OPOS_ID =", value, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdNotEqualTo(String value)
        {
            addCriterion("OPOS_ID <>", value, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdGreaterThan(String value)
        {
            addCriterion("OPOS_ID >", value, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdGreaterThanOrEqualTo(String value)
        {
            addCriterion("OPOS_ID >=", value, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdLessThan(String value)
        {
            addCriterion("OPOS_ID <", value, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdLessThanOrEqualTo(String value)
        {
            addCriterion("OPOS_ID <=", value, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdLike(String value)
        {
            addCriterion("OPOS_ID like", value, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdNotLike(String value)
        {
            addCriterion("OPOS_ID not like", value, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdIn(List<String> values)
        {
            addCriterion("OPOS_ID in", values, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdNotIn(List<String> values)
        {
            addCriterion("OPOS_ID not in", values, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdBetween(String value1, String value2)
        {
            addCriterion("OPOS_ID between", value1, value2, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOposIdNotBetween(String value1, String value2)
        {
            addCriterion("OPOS_ID not between", value1, value2, "oposId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdIsNull()
        {
            addCriterion("OTRS_ID is null");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdIsNotNull()
        {
            addCriterion("OTRS_ID is not null");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdEqualTo(String value)
        {
            addCriterion("OTRS_ID =", value, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdNotEqualTo(String value)
        {
            addCriterion("OTRS_ID <>", value, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdGreaterThan(String value)
        {
            addCriterion("OTRS_ID >", value, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdGreaterThanOrEqualTo(String value)
        {
            addCriterion("OTRS_ID >=", value, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdLessThan(String value)
        {
            addCriterion("OTRS_ID <", value, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdLessThanOrEqualTo(String value)
        {
            addCriterion("OTRS_ID <=", value, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdLike(String value)
        {
            addCriterion("OTRS_ID like", value, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdNotLike(String value)
        {
            addCriterion("OTRS_ID not like", value, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdIn(List<String> values)
        {
            addCriterion("OTRS_ID in", values, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdNotIn(List<String> values)
        {
            addCriterion("OTRS_ID not in", values, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdBetween(String value1, String value2)
        {
            addCriterion("OTRS_ID between", value1, value2, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andOtrsIdNotBetween(String value1, String value2)
        {
            addCriterion("OTRS_ID not between", value1, value2, "otrsId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdIsNull()
        {
            addCriterion("CHECK_ID is null");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdIsNotNull()
        {
            addCriterion("CHECK_ID is not null");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdEqualTo(String value)
        {
            addCriterion("CHECK_ID =", value, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdNotEqualTo(String value)
        {
            addCriterion("CHECK_ID <>", value, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdGreaterThan(String value)
        {
            addCriterion("CHECK_ID >", value, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdGreaterThanOrEqualTo(String value)
        {
            addCriterion("CHECK_ID >=", value, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdLessThan(String value)
        {
            addCriterion("CHECK_ID <", value, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdLessThanOrEqualTo(String value)
        {
            addCriterion("CHECK_ID <=", value, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdLike(String value)
        {
            addCriterion("CHECK_ID like", value, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdNotLike(String value)
        {
            addCriterion("CHECK_ID not like", value, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdIn(List<String> values)
        {
            addCriterion("CHECK_ID in", values, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdNotIn(List<String> values)
        {
            addCriterion("CHECK_ID not in", values, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdBetween(String value1, String value2)
        {
            addCriterion("CHECK_ID between", value1, value2, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckIdNotBetween(String value1, String value2)
        {
            addCriterion("CHECK_ID not between", value1, value2, "checkId");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameIsNull()
        {
            addCriterion("CHECK_NAME is null");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameIsNotNull()
        {
            addCriterion("CHECK_NAME is not null");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameEqualTo(String value)
        {
            addCriterion("CHECK_NAME =", value, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameNotEqualTo(String value)
        {
            addCriterion("CHECK_NAME <>", value, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameGreaterThan(String value)
        {
            addCriterion("CHECK_NAME >", value, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameGreaterThanOrEqualTo(String value)
        {
            addCriterion("CHECK_NAME >=", value, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameLessThan(String value)
        {
            addCriterion("CHECK_NAME <", value, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameLessThanOrEqualTo(String value)
        {
            addCriterion("CHECK_NAME <=", value, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameLike(String value)
        {
            addCriterion("CHECK_NAME like", value, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameNotLike(String value)
        {
            addCriterion("CHECK_NAME not like", value, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameIn(List<String> values)
        {
            addCriterion("CHECK_NAME in", values, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameNotIn(List<String> values)
        {
            addCriterion("CHECK_NAME not in", values, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameBetween(String value1, String value2)
        {
            addCriterion("CHECK_NAME between", value1, value2, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckNameNotBetween(String value1, String value2)
        {
            addCriterion("CHECK_NAME not between", value1, value2, "checkName");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeIsNull()
        {
            addCriterion("CHECK_TIME is null");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeIsNotNull()
        {
            addCriterion("CHECK_TIME is not null");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeEqualTo(Date value)
        {
            addCriterion("CHECK_TIME =", value, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeNotEqualTo(Date value)
        {
            addCriterion("CHECK_TIME <>", value, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeGreaterThan(Date value)
        {
            addCriterion("CHECK_TIME >", value, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeGreaterThanOrEqualTo(Date value)
        {
            addCriterion("CHECK_TIME >=", value, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeLessThan(Date value)
        {
            addCriterion("CHECK_TIME <", value, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeLessThanOrEqualTo(Date value)
        {
            addCriterion("CHECK_TIME <=", value, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeIn(List<Date> values)
        {
            addCriterion("CHECK_TIME in", values, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeNotIn(List<Date> values)
        {
            addCriterion("CHECK_TIME not in", values, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeBetween(Date value1, Date value2)
        {
            addCriterion("CHECK_TIME between", value1, value2, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckTimeNotBetween(Date value1, Date value2)
        {
            addCriterion("CHECK_TIME not between", value1, value2, "checkTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeIsNull()
        {
            addCriterion("PAYMENT_TIME is null");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeIsNotNull()
        {
            addCriterion("PAYMENT_TIME is not null");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeEqualTo(Date value)
        {
            addCriterion("PAYMENT_TIME =", value, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeNotEqualTo(Date value)
        {
            addCriterion("PAYMENT_TIME <>", value, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeGreaterThan(Date value)
        {
            addCriterion("PAYMENT_TIME >", value, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeGreaterThanOrEqualTo(Date value)
        {
            addCriterion("PAYMENT_TIME >=", value, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeLessThan(Date value)
        {
            addCriterion("PAYMENT_TIME <", value, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeLessThanOrEqualTo(Date value)
        {
            addCriterion("PAYMENT_TIME <=", value, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeIn(List<Date> values)
        {
            addCriterion("PAYMENT_TIME in", values, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeNotIn(List<Date> values)
        {
            addCriterion("PAYMENT_TIME not in", values, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeBetween(Date value1, Date value2)
        {
            addCriterion("PAYMENT_TIME between", value1, value2, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTimeNotBetween(Date value1, Date value2)
        {
            addCriterion("PAYMENT_TIME not between", value1, value2, "paymentTime");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountIsNull()
        {
            addCriterion("CHECK_AMOUNT is null");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountIsNotNull()
        {
            addCriterion("CHECK_AMOUNT is not null");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountEqualTo(Long value)
        {
            addCriterion("CHECK_AMOUNT =", value, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountNotEqualTo(Long value)
        {
            addCriterion("CHECK_AMOUNT <>", value, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountGreaterThan(Long value)
        {
            addCriterion("CHECK_AMOUNT >", value, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountGreaterThanOrEqualTo(Long value)
        {
            addCriterion("CHECK_AMOUNT >=", value, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountLessThan(Long value)
        {
            addCriterion("CHECK_AMOUNT <", value, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountLessThanOrEqualTo(Long value)
        {
            addCriterion("CHECK_AMOUNT <=", value, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountIn(List<Long> values)
        {
            addCriterion("CHECK_AMOUNT in", values, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountNotIn(List<Long> values)
        {
            addCriterion("CHECK_AMOUNT not in", values, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountBetween(Long value1, Long value2)
        {
            addCriterion("CHECK_AMOUNT between", value1, value2, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andCheckAmountNotBetween(Long value1, Long value2)
        {
            addCriterion("CHECK_AMOUNT not between", value1, value2, "checkAmount");
            return (Criteria)this;
        }
        
        public Criteria andRemarkIsNull()
        {
            addCriterion("REMARK is null");
            return (Criteria)this;
        }
        
        public Criteria andRemarkIsNotNull()
        {
            addCriterion("REMARK is not null");
            return (Criteria)this;
        }
        
        public Criteria andRemarkEqualTo(String value)
        {
            addCriterion("REMARK =", value, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkNotEqualTo(String value)
        {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkGreaterThan(String value)
        {
            addCriterion("REMARK >", value, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkGreaterThanOrEqualTo(String value)
        {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkLessThan(String value)
        {
            addCriterion("REMARK <", value, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkLessThanOrEqualTo(String value)
        {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkLike(String value)
        {
            addCriterion("REMARK like", value, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkNotLike(String value)
        {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkIn(List<String> values)
        {
            addCriterion("REMARK in", values, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkNotIn(List<String> values)
        {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkBetween(String value1, String value2)
        {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria)this;
        }
        
        public Criteria andRemarkNotBetween(String value1, String value2)
        {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterIsNull()
        {
            addCriterion("PAYMENTER is null");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterIsNotNull()
        {
            addCriterion("PAYMENTER is not null");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterEqualTo(String value)
        {
            addCriterion("PAYMENTER =", value, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterNotEqualTo(String value)
        {
            addCriterion("PAYMENTER <>", value, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterGreaterThan(String value)
        {
            addCriterion("PAYMENTER >", value, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterGreaterThanOrEqualTo(String value)
        {
            addCriterion("PAYMENTER >=", value, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterLessThan(String value)
        {
            addCriterion("PAYMENTER <", value, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterLessThanOrEqualTo(String value)
        {
            addCriterion("PAYMENTER <=", value, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterLike(String value)
        {
            addCriterion("PAYMENTER like", value, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterNotLike(String value)
        {
            addCriterion("PAYMENTER not like", value, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterIn(List<String> values)
        {
            addCriterion("PAYMENTER in", values, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterNotIn(List<String> values)
        {
            addCriterion("PAYMENTER not in", values, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterBetween(String value1, String value2)
        {
            addCriterion("PAYMENTER between", value1, value2, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymenterNotBetween(String value1, String value2)
        {
            addCriterion("PAYMENTER not between", value1, value2, "paymenter");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeIsNull()
        {
            addCriterion("PAYMENT_TYPE is null");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeIsNotNull()
        {
            addCriterion("PAYMENT_TYPE is not null");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeEqualTo(Integer value)
        {
            addCriterion("PAYMENT_TYPE =", value, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeNotEqualTo(Integer value)
        {
            addCriterion("PAYMENT_TYPE <>", value, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeGreaterThan(Integer value)
        {
            addCriterion("PAYMENT_TYPE >", value, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeGreaterThanOrEqualTo(Integer value)
        {
            addCriterion("PAYMENT_TYPE >=", value, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeLessThan(Integer value)
        {
            addCriterion("PAYMENT_TYPE <", value, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeLessThanOrEqualTo(Integer value)
        {
            addCriterion("PAYMENT_TYPE <=", value, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeIn(List<Integer> values)
        {
            addCriterion("PAYMENT_TYPE in", values, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeNotIn(List<Integer> values)
        {
            addCriterion("PAYMENT_TYPE not in", values, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeBetween(Integer value1, Integer value2)
        {
            addCriterion("PAYMENT_TYPE between", value1, value2, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPaymentTypeNotBetween(Integer value1, Integer value2)
        {
            addCriterion("PAYMENT_TYPE not between", value1, value2, "paymentType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeIsNull()
        {
            addCriterion("PAY_TYPE is null");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeIsNotNull()
        {
            addCriterion("PAY_TYPE is not null");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeEqualTo(Integer value)
        {
            addCriterion("PAY_TYPE =", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeNotEqualTo(Integer value)
        {
            addCriterion("PAY_TYPE <>", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeGreaterThan(Integer value)
        {
            addCriterion("PAY_TYPE >", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeGreaterThanOrEqualTo(Integer value)
        {
            addCriterion("PAY_TYPE >=", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeLessThan(Integer value)
        {
            addCriterion("PAY_TYPE <", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeLessThanOrEqualTo(Integer value)
        {
            addCriterion("PAY_TYPE <=", value, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeIn(List<Integer> values)
        {
            addCriterion("PAY_TYPE in", values, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeNotIn(List<Integer> values)
        {
            addCriterion("PAY_TYPE not in", values, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeBetween(Integer value1, Integer value2)
        {
            addCriterion("PAY_TYPE between", value1, value2, "payType");
            return (Criteria)this;
        }
        
        public Criteria andPayTypeNotBetween(Integer value1, Integer value2)
        {
            addCriterion("PAY_TYPE not between", value1, value2, "payType");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoIsNull()
        {
            addCriterion("TRANSFER_NO is null");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoIsNotNull()
        {
            addCriterion("TRANSFER_NO is not null");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoEqualTo(String value)
        {
            addCriterion("TRANSFER_NO =", value, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoNotEqualTo(String value)
        {
            addCriterion("TRANSFER_NO <>", value, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoGreaterThan(String value)
        {
            addCriterion("TRANSFER_NO >", value, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoGreaterThanOrEqualTo(String value)
        {
            addCriterion("TRANSFER_NO >=", value, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoLessThan(String value)
        {
            addCriterion("TRANSFER_NO <", value, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoLessThanOrEqualTo(String value)
        {
            addCriterion("TRANSFER_NO <=", value, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoLike(String value)
        {
            addCriterion("TRANSFER_NO like", value, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoNotLike(String value)
        {
            addCriterion("TRANSFER_NO not like", value, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoIn(List<String> values)
        {
            addCriterion("TRANSFER_NO in", values, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoNotIn(List<String> values)
        {
            addCriterion("TRANSFER_NO not in", values, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoBetween(String value1, String value2)
        {
            addCriterion("TRANSFER_NO between", value1, value2, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andTransferNoNotBetween(String value1, String value2)
        {
            addCriterion("TRANSFER_NO not between", value1, value2, "transferNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoIsNull()
        {
            addCriterion("POS_NO is null");
            return (Criteria)this;
        }
        
        public Criteria andPosNoIsNotNull()
        {
            addCriterion("POS_NO is not null");
            return (Criteria)this;
        }
        
        public Criteria andPosNoEqualTo(String value)
        {
            addCriterion("POS_NO =", value, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoNotEqualTo(String value)
        {
            addCriterion("POS_NO <>", value, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoGreaterThan(String value)
        {
            addCriterion("POS_NO >", value, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoGreaterThanOrEqualTo(String value)
        {
            addCriterion("POS_NO >=", value, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoLessThan(String value)
        {
            addCriterion("POS_NO <", value, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoLessThanOrEqualTo(String value)
        {
            addCriterion("POS_NO <=", value, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoLike(String value)
        {
            addCriterion("POS_NO like", value, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoNotLike(String value)
        {
            addCriterion("POS_NO not like", value, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoIn(List<String> values)
        {
            addCriterion("POS_NO in", values, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoNotIn(List<String> values)
        {
            addCriterion("POS_NO not in", values, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoBetween(String value1, String value2)
        {
            addCriterion("POS_NO between", value1, value2, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andPosNoNotBetween(String value1, String value2)
        {
            addCriterion("POS_NO not between", value1, value2, "posNo");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsIsNull()
        {
            addCriterion("RECEIPT_ROLLS is null");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsIsNotNull()
        {
            addCriterion("RECEIPT_ROLLS is not null");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsEqualTo(String value)
        {
            addCriterion("RECEIPT_ROLLS =", value, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsNotEqualTo(String value)
        {
            addCriterion("RECEIPT_ROLLS <>", value, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsGreaterThan(String value)
        {
            addCriterion("RECEIPT_ROLLS >", value, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsGreaterThanOrEqualTo(String value)
        {
            addCriterion("RECEIPT_ROLLS >=", value, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsLessThan(String value)
        {
            addCriterion("RECEIPT_ROLLS <", value, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsLessThanOrEqualTo(String value)
        {
            addCriterion("RECEIPT_ROLLS <=", value, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsLike(String value)
        {
            addCriterion("RECEIPT_ROLLS like", value, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsNotLike(String value)
        {
            addCriterion("RECEIPT_ROLLS not like", value, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsIn(List<String> values)
        {
            addCriterion("RECEIPT_ROLLS in", values, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsNotIn(List<String> values)
        {
            addCriterion("RECEIPT_ROLLS not in", values, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsBetween(String value1, String value2)
        {
            addCriterion("RECEIPT_ROLLS between", value1, value2, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andReceiptRollsNotBetween(String value1, String value2)
        {
            addCriterion("RECEIPT_ROLLS not between", value1, value2, "receiptRolls");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoIsNull()
        {
            addCriterion("TRADE_NO is null");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoIsNotNull()
        {
            addCriterion("TRADE_NO is not null");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoEqualTo(String value)
        {
            addCriterion("TRADE_NO =", value, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoNotEqualTo(String value)
        {
            addCriterion("TRADE_NO <>", value, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoGreaterThan(String value)
        {
            addCriterion("TRADE_NO >", value, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoGreaterThanOrEqualTo(String value)
        {
            addCriterion("TRADE_NO >=", value, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoLessThan(String value)
        {
            addCriterion("TRADE_NO <", value, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoLessThanOrEqualTo(String value)
        {
            addCriterion("TRADE_NO <=", value, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoLike(String value)
        {
            addCriterion("TRADE_NO like", value, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoNotLike(String value)
        {
            addCriterion("TRADE_NO not like", value, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoIn(List<String> values)
        {
            addCriterion("TRADE_NO in", values, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoNotIn(List<String> values)
        {
            addCriterion("TRADE_NO not in", values, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoBetween(String value1, String value2)
        {
            addCriterion("TRADE_NO between", value1, value2, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeNoNotBetween(String value1, String value2)
        {
            addCriterion("TRADE_NO not between", value1, value2, "tradeNo");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsIsNull()
        {
            addCriterion("TRADE_PARAMS is null");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsIsNotNull()
        {
            addCriterion("TRADE_PARAMS is not null");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsEqualTo(String value)
        {
            addCriterion("TRADE_PARAMS =", value, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsNotEqualTo(String value)
        {
            addCriterion("TRADE_PARAMS <>", value, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsGreaterThan(String value)
        {
            addCriterion("TRADE_PARAMS >", value, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsGreaterThanOrEqualTo(String value)
        {
            addCriterion("TRADE_PARAMS >=", value, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsLessThan(String value)
        {
            addCriterion("TRADE_PARAMS <", value, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsLessThanOrEqualTo(String value)
        {
            addCriterion("TRADE_PARAMS <=", value, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsLike(String value)
        {
            addCriterion("TRADE_PARAMS like", value, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsNotLike(String value)
        {
            addCriterion("TRADE_PARAMS not like", value, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsIn(List<String> values)
        {
            addCriterion("TRADE_PARAMS in", values, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsNotIn(List<String> values)
        {
            addCriterion("TRADE_PARAMS not in", values, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsBetween(String value1, String value2)
        {
            addCriterion("TRADE_PARAMS between", value1, value2, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andTradeParamsNotBetween(String value1, String value2)
        {
            addCriterion("TRADE_PARAMS not between", value1, value2, "tradeParams");
            return (Criteria)this;
        }
        
        public Criteria andMerNumIsNull()
        {
            addCriterion("MER_NUM is null");
            return (Criteria)this;
        }
        
        public Criteria andMerNumIsNotNull()
        {
            addCriterion("MER_NUM is not null");
            return (Criteria)this;
        }
        
        public Criteria andMerNumEqualTo(String value)
        {
            addCriterion("MER_NUM =", value, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumNotEqualTo(String value)
        {
            addCriterion("MER_NUM <>", value, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumGreaterThan(String value)
        {
            addCriterion("MER_NUM >", value, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumGreaterThanOrEqualTo(String value)
        {
            addCriterion("MER_NUM >=", value, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumLessThan(String value)
        {
            addCriterion("MER_NUM <", value, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumLessThanOrEqualTo(String value)
        {
            addCriterion("MER_NUM <=", value, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumLike(String value)
        {
            addCriterion("MER_NUM like", value, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumNotLike(String value)
        {
            addCriterion("MER_NUM not like", value, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumIn(List<String> values)
        {
            addCriterion("MER_NUM in", values, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumNotIn(List<String> values)
        {
            addCriterion("MER_NUM not in", values, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumBetween(String value1, String value2)
        {
            addCriterion("MER_NUM between", value1, value2, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andMerNumNotBetween(String value1, String value2)
        {
            addCriterion("MER_NUM not between", value1, value2, "merNum");
            return (Criteria)this;
        }
        
        public Criteria andTermIdIsNull()
        {
            addCriterion("TERM_ID is null");
            return (Criteria)this;
        }
        
        public Criteria andTermIdIsNotNull()
        {
            addCriterion("TERM_ID is not null");
            return (Criteria)this;
        }
        
        public Criteria andTermIdEqualTo(String value)
        {
            addCriterion("TERM_ID =", value, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdNotEqualTo(String value)
        {
            addCriterion("TERM_ID <>", value, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdGreaterThan(String value)
        {
            addCriterion("TERM_ID >", value, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdGreaterThanOrEqualTo(String value)
        {
            addCriterion("TERM_ID >=", value, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdLessThan(String value)
        {
            addCriterion("TERM_ID <", value, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdLessThanOrEqualTo(String value)
        {
            addCriterion("TERM_ID <=", value, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdLike(String value)
        {
            addCriterion("TERM_ID like", value, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdNotLike(String value)
        {
            addCriterion("TERM_ID not like", value, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdIn(List<String> values)
        {
            addCriterion("TERM_ID in", values, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdNotIn(List<String> values)
        {
            addCriterion("TERM_ID not in", values, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdBetween(String value1, String value2)
        {
            addCriterion("TERM_ID between", value1, value2, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTermIdNotBetween(String value1, String value2)
        {
            addCriterion("TERM_ID not between", value1, value2, "termId");
            return (Criteria)this;
        }
        
        public Criteria andTranDateIsNull()
        {
            addCriterion("TRAN_DATE is null");
            return (Criteria)this;
        }
        
        public Criteria andTranDateIsNotNull()
        {
            addCriterion("TRAN_DATE is not null");
            return (Criteria)this;
        }
        
        public Criteria andTranDateEqualTo(String value)
        {
            addCriterion("TRAN_DATE =", value, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateNotEqualTo(String value)
        {
            addCriterion("TRAN_DATE <>", value, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateGreaterThan(String value)
        {
            addCriterion("TRAN_DATE >", value, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateGreaterThanOrEqualTo(String value)
        {
            addCriterion("TRAN_DATE >=", value, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateLessThan(String value)
        {
            addCriterion("TRAN_DATE <", value, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateLessThanOrEqualTo(String value)
        {
            addCriterion("TRAN_DATE <=", value, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateLike(String value)
        {
            addCriterion("TRAN_DATE like", value, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateNotLike(String value)
        {
            addCriterion("TRAN_DATE not like", value, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateIn(List<String> values)
        {
            addCriterion("TRAN_DATE in", values, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateNotIn(List<String> values)
        {
            addCriterion("TRAN_DATE not in", values, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateBetween(String value1, String value2)
        {
            addCriterion("TRAN_DATE between", value1, value2, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andTranDateNotBetween(String value1, String value2)
        {
            addCriterion("TRAN_DATE not between", value1, value2, "tranDate");
            return (Criteria)this;
        }
        
        public Criteria andReferNoIsNull()
        {
            addCriterion("REFER_NO is null");
            return (Criteria)this;
        }
        
        public Criteria andReferNoIsNotNull()
        {
            addCriterion("REFER_NO is not null");
            return (Criteria)this;
        }
        
        public Criteria andReferNoEqualTo(String value)
        {
            addCriterion("REFER_NO =", value, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoNotEqualTo(String value)
        {
            addCriterion("REFER_NO <>", value, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoGreaterThan(String value)
        {
            addCriterion("REFER_NO >", value, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoGreaterThanOrEqualTo(String value)
        {
            addCriterion("REFER_NO >=", value, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoLessThan(String value)
        {
            addCriterion("REFER_NO <", value, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoLessThanOrEqualTo(String value)
        {
            addCriterion("REFER_NO <=", value, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoLike(String value)
        {
            addCriterion("REFER_NO like", value, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoNotLike(String value)
        {
            addCriterion("REFER_NO not like", value, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoIn(List<String> values)
        {
            addCriterion("REFER_NO in", values, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoNotIn(List<String> values)
        {
            addCriterion("REFER_NO not in", values, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoBetween(String value1, String value2)
        {
            addCriterion("REFER_NO between", value1, value2, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andReferNoNotBetween(String value1, String value2)
        {
            addCriterion("REFER_NO not between", value1, value2, "referNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoIsNull()
        {
            addCriterion("BATCH_NO is null");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoIsNotNull()
        {
            addCriterion("BATCH_NO is not null");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoEqualTo(String value)
        {
            addCriterion("BATCH_NO =", value, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoNotEqualTo(String value)
        {
            addCriterion("BATCH_NO <>", value, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoGreaterThan(String value)
        {
            addCriterion("BATCH_NO >", value, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoGreaterThanOrEqualTo(String value)
        {
            addCriterion("BATCH_NO >=", value, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoLessThan(String value)
        {
            addCriterion("BATCH_NO <", value, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoLessThanOrEqualTo(String value)
        {
            addCriterion("BATCH_NO <=", value, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoLike(String value)
        {
            addCriterion("BATCH_NO like", value, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoNotLike(String value)
        {
            addCriterion("BATCH_NO not like", value, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoIn(List<String> values)
        {
            addCriterion("BATCH_NO in", values, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoNotIn(List<String> values)
        {
            addCriterion("BATCH_NO not in", values, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoBetween(String value1, String value2)
        {
            addCriterion("BATCH_NO between", value1, value2, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andBatchNoNotBetween(String value1, String value2)
        {
            addCriterion("BATCH_NO not between", value1, value2, "batchNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoIsNull()
        {
            addCriterion("SERIAL_NO is null");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoIsNotNull()
        {
            addCriterion("SERIAL_NO is not null");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoEqualTo(String value)
        {
            addCriterion("SERIAL_NO =", value, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoNotEqualTo(String value)
        {
            addCriterion("SERIAL_NO <>", value, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoGreaterThan(String value)
        {
            addCriterion("SERIAL_NO >", value, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoGreaterThanOrEqualTo(String value)
        {
            addCriterion("SERIAL_NO >=", value, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoLessThan(String value)
        {
            addCriterion("SERIAL_NO <", value, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoLessThanOrEqualTo(String value)
        {
            addCriterion("SERIAL_NO <=", value, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoLike(String value)
        {
            addCriterion("SERIAL_NO like", value, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoNotLike(String value)
        {
            addCriterion("SERIAL_NO not like", value, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoIn(List<String> values)
        {
            addCriterion("SERIAL_NO in", values, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoNotIn(List<String> values)
        {
            addCriterion("SERIAL_NO not in", values, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoBetween(String value1, String value2)
        {
            addCriterion("SERIAL_NO between", value1, value2, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andSerialNoNotBetween(String value1, String value2)
        {
            addCriterion("SERIAL_NO not between", value1, value2, "serialNo");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoIsNull()
        {
            addCriterion("O_BATCHNO is null");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoIsNotNull()
        {
            addCriterion("O_BATCHNO is not null");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoEqualTo(String value)
        {
            addCriterion("O_BATCHNO =", value, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoNotEqualTo(String value)
        {
            addCriterion("O_BATCHNO <>", value, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoGreaterThan(String value)
        {
            addCriterion("O_BATCHNO >", value, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoGreaterThanOrEqualTo(String value)
        {
            addCriterion("O_BATCHNO >=", value, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoLessThan(String value)
        {
            addCriterion("O_BATCHNO <", value, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoLessThanOrEqualTo(String value)
        {
            addCriterion("O_BATCHNO <=", value, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoLike(String value)
        {
            addCriterion("O_BATCHNO like", value, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoNotLike(String value)
        {
            addCriterion("O_BATCHNO not like", value, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoIn(List<String> values)
        {
            addCriterion("O_BATCHNO in", values, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoNotIn(List<String> values)
        {
            addCriterion("O_BATCHNO not in", values, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoBetween(String value1, String value2)
        {
            addCriterion("O_BATCHNO between", value1, value2, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOBatchnoNotBetween(String value1, String value2)
        {
            addCriterion("O_BATCHNO not between", value1, value2, "oBatchno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoIsNull()
        {
            addCriterion("O_SERIALNO is null");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoIsNotNull()
        {
            addCriterion("O_SERIALNO is not null");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoEqualTo(String value)
        {
            addCriterion("O_SERIALNO =", value, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoNotEqualTo(String value)
        {
            addCriterion("O_SERIALNO <>", value, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoGreaterThan(String value)
        {
            addCriterion("O_SERIALNO >", value, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoGreaterThanOrEqualTo(String value)
        {
            addCriterion("O_SERIALNO >=", value, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoLessThan(String value)
        {
            addCriterion("O_SERIALNO <", value, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoLessThanOrEqualTo(String value)
        {
            addCriterion("O_SERIALNO <=", value, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoLike(String value)
        {
            addCriterion("O_SERIALNO like", value, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoNotLike(String value)
        {
            addCriterion("O_SERIALNO not like", value, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoIn(List<String> values)
        {
            addCriterion("O_SERIALNO in", values, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoNotIn(List<String> values)
        {
            addCriterion("O_SERIALNO not in", values, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoBetween(String value1, String value2)
        {
            addCriterion("O_SERIALNO between", value1, value2, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andOSerialnoNotBetween(String value1, String value2)
        {
            addCriterion("O_SERIALNO not between", value1, value2, "oSerialno");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeIsNull()
        {
            addCriterion("TRAN_TYPE is null");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeIsNotNull()
        {
            addCriterion("TRAN_TYPE is not null");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeEqualTo(String value)
        {
            addCriterion("TRAN_TYPE =", value, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeNotEqualTo(String value)
        {
            addCriterion("TRAN_TYPE <>", value, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeGreaterThan(String value)
        {
            addCriterion("TRAN_TYPE >", value, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeGreaterThanOrEqualTo(String value)
        {
            addCriterion("TRAN_TYPE >=", value, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeLessThan(String value)
        {
            addCriterion("TRAN_TYPE <", value, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeLessThanOrEqualTo(String value)
        {
            addCriterion("TRAN_TYPE <=", value, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeLike(String value)
        {
            addCriterion("TRAN_TYPE like", value, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeNotLike(String value)
        {
            addCriterion("TRAN_TYPE not like", value, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeIn(List<String> values)
        {
            addCriterion("TRAN_TYPE in", values, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeNotIn(List<String> values)
        {
            addCriterion("TRAN_TYPE not in", values, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeBetween(String value1, String value2)
        {
            addCriterion("TRAN_TYPE between", value1, value2, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andTranTypeNotBetween(String value1, String value2)
        {
            addCriterion("TRAN_TYPE not between", value1, value2, "tranType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeIsNull()
        {
            addCriterion("WANG_PAY_TYPE is null");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeIsNotNull()
        {
            addCriterion("WANG_PAY_TYPE is not null");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeEqualTo(String value)
        {
            addCriterion("WANG_PAY_TYPE =", value, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeNotEqualTo(String value)
        {
            addCriterion("WANG_PAY_TYPE <>", value, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeGreaterThan(String value)
        {
            addCriterion("WANG_PAY_TYPE >", value, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeGreaterThanOrEqualTo(String value)
        {
            addCriterion("WANG_PAY_TYPE >=", value, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeLessThan(String value)
        {
            addCriterion("WANG_PAY_TYPE <", value, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeLessThanOrEqualTo(String value)
        {
            addCriterion("WANG_PAY_TYPE <=", value, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeLike(String value)
        {
            addCriterion("WANG_PAY_TYPE like", value, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeNotLike(String value)
        {
            addCriterion("WANG_PAY_TYPE not like", value, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeIn(List<String> values)
        {
            addCriterion("WANG_PAY_TYPE in", values, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeNotIn(List<String> values)
        {
            addCriterion("WANG_PAY_TYPE not in", values, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeBetween(String value1, String value2)
        {
            addCriterion("WANG_PAY_TYPE between", value1, value2, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andWangPayTypeNotBetween(String value1, String value2)
        {
            addCriterion("WANG_PAY_TYPE not between", value1, value2, "wangPayType");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeIsNull()
        {
            addCriterion("ORDER_CODE is null");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeIsNotNull()
        {
            addCriterion("ORDER_CODE is not null");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeEqualTo(String value)
        {
            addCriterion("ORDER_CODE =", value, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeNotEqualTo(String value)
        {
            addCriterion("ORDER_CODE <>", value, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeGreaterThan(String value)
        {
            addCriterion("ORDER_CODE >", value, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeGreaterThanOrEqualTo(String value)
        {
            addCriterion("ORDER_CODE >=", value, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeLessThan(String value)
        {
            addCriterion("ORDER_CODE <", value, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeLessThanOrEqualTo(String value)
        {
            addCriterion("ORDER_CODE <=", value, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeLike(String value)
        {
            addCriterion("ORDER_CODE like", value, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeNotLike(String value)
        {
            addCriterion("ORDER_CODE not like", value, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeIn(List<String> values)
        {
            addCriterion("ORDER_CODE in", values, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeNotIn(List<String> values)
        {
            addCriterion("ORDER_CODE not in", values, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeBetween(String value1, String value2)
        {
            addCriterion("ORDER_CODE between", value1, value2, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andOrderCodeNotBetween(String value1, String value2)
        {
            addCriterion("ORDER_CODE not between", value1, value2, "orderCode");
            return (Criteria)this;
        }
        
        public Criteria andExt1IsNull()
        {
            addCriterion("EXT1 is null");
            return (Criteria)this;
        }
        
        public Criteria andExt1IsNotNull()
        {
            addCriterion("EXT1 is not null");
            return (Criteria)this;
        }
        
        public Criteria andExt1EqualTo(String value)
        {
            addCriterion("EXT1 =", value, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1NotEqualTo(String value)
        {
            addCriterion("EXT1 <>", value, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1GreaterThan(String value)
        {
            addCriterion("EXT1 >", value, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1GreaterThanOrEqualTo(String value)
        {
            addCriterion("EXT1 >=", value, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1LessThan(String value)
        {
            addCriterion("EXT1 <", value, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1LessThanOrEqualTo(String value)
        {
            addCriterion("EXT1 <=", value, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1Like(String value)
        {
            addCriterion("EXT1 like", value, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1NotLike(String value)
        {
            addCriterion("EXT1 not like", value, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1In(List<String> values)
        {
            addCriterion("EXT1 in", values, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1NotIn(List<String> values)
        {
            addCriterion("EXT1 not in", values, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1Between(String value1, String value2)
        {
            addCriterion("EXT1 between", value1, value2, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt1NotBetween(String value1, String value2)
        {
            addCriterion("EXT1 not between", value1, value2, "ext1");
            return (Criteria)this;
        }
        
        public Criteria andExt2IsNull()
        {
            addCriterion("EXT2 is null");
            return (Criteria)this;
        }
        
        public Criteria andExt2IsNotNull()
        {
            addCriterion("EXT2 is not null");
            return (Criteria)this;
        }
        
        public Criteria andExt2EqualTo(String value)
        {
            addCriterion("EXT2 =", value, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2NotEqualTo(String value)
        {
            addCriterion("EXT2 <>", value, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2GreaterThan(String value)
        {
            addCriterion("EXT2 >", value, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2GreaterThanOrEqualTo(String value)
        {
            addCriterion("EXT2 >=", value, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2LessThan(String value)
        {
            addCriterion("EXT2 <", value, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2LessThanOrEqualTo(String value)
        {
            addCriterion("EXT2 <=", value, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2Like(String value)
        {
            addCriterion("EXT2 like", value, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2NotLike(String value)
        {
            addCriterion("EXT2 not like", value, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2In(List<String> values)
        {
            addCriterion("EXT2 in", values, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2NotIn(List<String> values)
        {
            addCriterion("EXT2 not in", values, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2Between(String value1, String value2)
        {
            addCriterion("EXT2 between", value1, value2, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andExt2NotBetween(String value1, String value2)
        {
            addCriterion("EXT2 not between", value1, value2, "ext2");
            return (Criteria)this;
        }
        
        public Criteria andAddValIsNull()
        {
            addCriterion("ADD_VAL is null");
            return (Criteria)this;
        }
        
        public Criteria andAddValIsNotNull()
        {
            addCriterion("ADD_VAL is not null");
            return (Criteria)this;
        }
        
        public Criteria andAddValEqualTo(String value)
        {
            addCriterion("ADD_VAL =", value, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValNotEqualTo(String value)
        {
            addCriterion("ADD_VAL <>", value, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValGreaterThan(String value)
        {
            addCriterion("ADD_VAL >", value, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValGreaterThanOrEqualTo(String value)
        {
            addCriterion("ADD_VAL >=", value, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValLessThan(String value)
        {
            addCriterion("ADD_VAL <", value, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValLessThanOrEqualTo(String value)
        {
            addCriterion("ADD_VAL <=", value, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValLike(String value)
        {
            addCriterion("ADD_VAL like", value, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValNotLike(String value)
        {
            addCriterion("ADD_VAL not like", value, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValIn(List<String> values)
        {
            addCriterion("ADD_VAL in", values, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValNotIn(List<String> values)
        {
            addCriterion("ADD_VAL not in", values, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValBetween(String value1, String value2)
        {
            addCriterion("ADD_VAL between", value1, value2, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andAddValNotBetween(String value1, String value2)
        {
            addCriterion("ADD_VAL not between", value1, value2, "addVal");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusIsNull()
        {
            addCriterion("RECONCILIATION_STATUS is null");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusIsNotNull()
        {
            addCriterion("RECONCILIATION_STATUS is not null");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusEqualTo(Integer value)
        {
            addCriterion("RECONCILIATION_STATUS =", value, "reconciliationStatus");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusNotEqualTo(Integer value)
        {
            addCriterion("RECONCILIATION_STATUS <>", value, "reconciliationStatus");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusGreaterThan(Integer value)
        {
            addCriterion("RECONCILIATION_STATUS >", value, "reconciliationStatus");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusGreaterThanOrEqualTo(Integer value)
        {
            addCriterion("RECONCILIATION_STATUS >=", value, "reconciliationStatus");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusLessThan(Integer value)
        {
            addCriterion("RECONCILIATION_STATUS <", value, "reconciliationStatus");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusLessThanOrEqualTo(Integer value)
        {
            addCriterion("RECONCILIATION_STATUS <=", value, "reconciliationStatus");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusIn(List<Integer> values)
        {
            addCriterion("RECONCILIATION_STATUS in", values, "reconciliationStatus");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusNotIn(List<Integer> values)
        {
            addCriterion("RECONCILIATION_STATUS not in", values, "reconciliationStatus");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusBetween(Integer value1, Integer value2)
        {
            addCriterion("RECONCILIATION_STATUS between", value1, value2, "reconciliationStatus");
            return (Criteria)this;
        }
        
        public Criteria andReconciliationStatusNotBetween(Integer value1, Integer value2)
        {
            addCriterion("RECONCILIATION_STATUS not between", value1, value2, "reconciliationStatus");
            return (Criteria)this;
        }
        
    }
    
    public static class Criteria extends GeneratedCriteria
    {
        
        protected Criteria()
        {
            super();
        }
    }
    
    public static class Criterion
    {
        private String condition;
        
        private Object value;
        
        private Object secondValue;
        
        private boolean noValue;
        
        private boolean singleValue;
        
        private boolean betweenValue;
        
        private boolean listValue;
        
        private String typeHandler;
        
        public String getCondition()
        {
            return condition;
        }
        
        public Object getValue()
        {
            return value;
        }
        
        public Object getSecondValue()
        {
            return secondValue;
        }
        
        public boolean isNoValue()
        {
            return noValue;
        }
        
        public boolean isSingleValue()
        {
            return singleValue;
        }
        
        public boolean isBetweenValue()
        {
            return betweenValue;
        }
        
        public boolean isListValue()
        {
            return listValue;
        }
        
        public String getTypeHandler()
        {
            return typeHandler;
        }
        
        protected Criterion(String condition)
        {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        
        protected Criterion(String condition, Object value, String typeHandler)
        {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>)
            {
                this.listValue = true;
            }
            else
            {
                this.singleValue = true;
            }
        }
        
        protected Criterion(String condition, Object value)
        {
            this(condition, value, null);
        }
        
        protected Criterion(String condition, Object value, Object secondValue, String typeHandler)
        {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }
        
        protected Criterion(String condition, Object value, Object secondValue)
        {
            this(condition, value, secondValue, null);
        }
    }
}