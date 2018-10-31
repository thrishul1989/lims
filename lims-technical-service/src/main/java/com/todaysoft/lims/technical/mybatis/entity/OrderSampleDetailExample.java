package com.todaysoft.lims.technical.mybatis.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OrderSampleDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderSampleDetailExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNull() {
            addCriterion("ORDER_CODE is null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIsNotNull() {
            addCriterion("ORDER_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCodeEqualTo(String value) {
            addCriterion("ORDER_CODE =", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotEqualTo(String value) {
            addCriterion("ORDER_CODE <>", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThan(String value) {
            addCriterion("ORDER_CODE >", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_CODE >=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThan(String value) {
            addCriterion("ORDER_CODE <", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLessThanOrEqualTo(String value) {
            addCriterion("ORDER_CODE <=", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeLike(String value) {
            addCriterion("ORDER_CODE like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotLike(String value) {
            addCriterion("ORDER_CODE not like", value, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeIn(List<String> values) {
            addCriterion("ORDER_CODE in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotIn(List<String> values) {
            addCriterion("ORDER_CODE not in", values, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeBetween(String value1, String value2) {
            addCriterion("ORDER_CODE between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderCodeNotBetween(String value1, String value2) {
            addCriterion("ORDER_CODE not between", value1, value2, "orderCode");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("ORDER_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("ORDER_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Boolean value) {
            addCriterion("ORDER_TYPE =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Boolean value) {
            addCriterion("ORDER_TYPE <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Boolean value) {
            addCriterion("ORDER_TYPE >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("ORDER_TYPE >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Boolean value) {
            addCriterion("ORDER_TYPE <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("ORDER_TYPE <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Boolean> values) {
            addCriterion("ORDER_TYPE in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Boolean> values) {
            addCriterion("ORDER_TYPE not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("ORDER_TYPE between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("ORDER_TYPE not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNull() {
            addCriterion("CUSTOMER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("CUSTOMER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(String value) {
            addCriterion("CUSTOMER_ID =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(String value) {
            addCriterion("CUSTOMER_ID <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(String value) {
            addCriterion("CUSTOMER_ID >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(String value) {
            addCriterion("CUSTOMER_ID >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(String value) {
            addCriterion("CUSTOMER_ID <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(String value) {
            addCriterion("CUSTOMER_ID <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLike(String value) {
            addCriterion("CUSTOMER_ID like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotLike(String value) {
            addCriterion("CUSTOMER_ID not like", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<String> values) {
            addCriterion("CUSTOMER_ID in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<String> values) {
            addCriterion("CUSTOMER_ID not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(String value1, String value2) {
            addCriterion("CUSTOMER_ID between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(String value1, String value2) {
            addCriterion("CUSTOMER_ID not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNull() {
            addCriterion("CUSTOMER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNotNull() {
            addCriterion("CUSTOMER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameEqualTo(String value) {
            addCriterion("CUSTOMER_NAME =", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotEqualTo(String value) {
            addCriterion("CUSTOMER_NAME <>", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThan(String value) {
            addCriterion("CUSTOMER_NAME >", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("CUSTOMER_NAME >=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThan(String value) {
            addCriterion("CUSTOMER_NAME <", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("CUSTOMER_NAME <=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLike(String value) {
            addCriterion("CUSTOMER_NAME like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotLike(String value) {
            addCriterion("CUSTOMER_NAME not like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIn(List<String> values) {
            addCriterion("CUSTOMER_NAME in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotIn(List<String> values) {
            addCriterion("CUSTOMER_NAME not in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameBetween(String value1, String value2) {
            addCriterion("CUSTOMER_NAME between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotBetween(String value1, String value2) {
            addCriterion("CUSTOMER_NAME not between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIsNull() {
            addCriterion("SALESMAN_ID is null");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIsNotNull() {
            addCriterion("SALESMAN_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdEqualTo(String value) {
            addCriterion("SALESMAN_ID =", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotEqualTo(String value) {
            addCriterion("SALESMAN_ID <>", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdGreaterThan(String value) {
            addCriterion("SALESMAN_ID >", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdGreaterThanOrEqualTo(String value) {
            addCriterion("SALESMAN_ID >=", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLessThan(String value) {
            addCriterion("SALESMAN_ID <", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLessThanOrEqualTo(String value) {
            addCriterion("SALESMAN_ID <=", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdLike(String value) {
            addCriterion("SALESMAN_ID like", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotLike(String value) {
            addCriterion("SALESMAN_ID not like", value, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdIn(List<String> values) {
            addCriterion("SALESMAN_ID in", values, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotIn(List<String> values) {
            addCriterion("SALESMAN_ID not in", values, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdBetween(String value1, String value2) {
            addCriterion("SALESMAN_ID between", value1, value2, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanIdNotBetween(String value1, String value2) {
            addCriterion("SALESMAN_ID not between", value1, value2, "salesmanId");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameIsNull() {
            addCriterion("SALESMAN_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameIsNotNull() {
            addCriterion("SALESMAN_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameEqualTo(String value) {
            addCriterion("SALESMAN_NAME =", value, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameNotEqualTo(String value) {
            addCriterion("SALESMAN_NAME <>", value, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameGreaterThan(String value) {
            addCriterion("SALESMAN_NAME >", value, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameGreaterThanOrEqualTo(String value) {
            addCriterion("SALESMAN_NAME >=", value, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameLessThan(String value) {
            addCriterion("SALESMAN_NAME <", value, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameLessThanOrEqualTo(String value) {
            addCriterion("SALESMAN_NAME <=", value, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameLike(String value) {
            addCriterion("SALESMAN_NAME like", value, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameNotLike(String value) {
            addCriterion("SALESMAN_NAME not like", value, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameIn(List<String> values) {
            addCriterion("SALESMAN_NAME in", values, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameNotIn(List<String> values) {
            addCriterion("SALESMAN_NAME not in", values, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameBetween(String value1, String value2) {
            addCriterion("SALESMAN_NAME between", value1, value2, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSalesmanNameNotBetween(String value1, String value2) {
            addCriterion("SALESMAN_NAME not between", value1, value2, "salesmanName");
            return (Criteria) this;
        }

        public Criteria andSampleIdIsNull() {
            addCriterion("SAMPLE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSampleIdIsNotNull() {
            addCriterion("SAMPLE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSampleIdEqualTo(String value) {
            addCriterion("SAMPLE_ID =", value, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdNotEqualTo(String value) {
            addCriterion("SAMPLE_ID <>", value, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdGreaterThan(String value) {
            addCriterion("SAMPLE_ID >", value, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdGreaterThanOrEqualTo(String value) {
            addCriterion("SAMPLE_ID >=", value, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdLessThan(String value) {
            addCriterion("SAMPLE_ID <", value, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdLessThanOrEqualTo(String value) {
            addCriterion("SAMPLE_ID <=", value, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdLike(String value) {
            addCriterion("SAMPLE_ID like", value, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdNotLike(String value) {
            addCriterion("SAMPLE_ID not like", value, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdIn(List<String> values) {
            addCriterion("SAMPLE_ID in", values, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdNotIn(List<String> values) {
            addCriterion("SAMPLE_ID not in", values, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdBetween(String value1, String value2) {
            addCriterion("SAMPLE_ID between", value1, value2, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleIdNotBetween(String value1, String value2) {
            addCriterion("SAMPLE_ID not between", value1, value2, "sampleId");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeIsNull() {
            addCriterion("SAMPLE_BTYPE is null");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeIsNotNull() {
            addCriterion("SAMPLE_BTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeEqualTo(Long value) {
            addCriterion("SAMPLE_BTYPE =", value, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeNotEqualTo(Long value) {
            addCriterion("SAMPLE_BTYPE <>", value, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeGreaterThan(Long value) {
            addCriterion("SAMPLE_BTYPE >", value, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeGreaterThanOrEqualTo(Long value) {
            addCriterion("SAMPLE_BTYPE >=", value, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeLessThan(Long value) {
            addCriterion("SAMPLE_BTYPE <", value, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeLessThanOrEqualTo(Long value) {
            addCriterion("SAMPLE_BTYPE <=", value, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeIn(List<Long> values) {
            addCriterion("SAMPLE_BTYPE in", values, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeNotIn(List<Long> values) {
            addCriterion("SAMPLE_BTYPE not in", values, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeBetween(Long value1, Long value2) {
            addCriterion("SAMPLE_BTYPE between", value1, value2, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleBtypeNotBetween(Long value1, Long value2) {
            addCriterion("SAMPLE_BTYPE not between", value1, value2, "sampleBtype");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdIsNull() {
            addCriterion("SAMPLE_TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdIsNotNull() {
            addCriterion("SAMPLE_TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdEqualTo(String value) {
            addCriterion("SAMPLE_TYPE_ID =", value, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdNotEqualTo(String value) {
            addCriterion("SAMPLE_TYPE_ID <>", value, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdGreaterThan(String value) {
            addCriterion("SAMPLE_TYPE_ID >", value, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("SAMPLE_TYPE_ID >=", value, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdLessThan(String value) {
            addCriterion("SAMPLE_TYPE_ID <", value, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdLessThanOrEqualTo(String value) {
            addCriterion("SAMPLE_TYPE_ID <=", value, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdLike(String value) {
            addCriterion("SAMPLE_TYPE_ID like", value, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdNotLike(String value) {
            addCriterion("SAMPLE_TYPE_ID not like", value, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdIn(List<String> values) {
            addCriterion("SAMPLE_TYPE_ID in", values, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdNotIn(List<String> values) {
            addCriterion("SAMPLE_TYPE_ID not in", values, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdBetween(String value1, String value2) {
            addCriterion("SAMPLE_TYPE_ID between", value1, value2, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIdNotBetween(String value1, String value2) {
            addCriterion("SAMPLE_TYPE_ID not between", value1, value2, "sampleTypeId");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameIsNull() {
            addCriterion("SAMPLE_TYPE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameIsNotNull() {
            addCriterion("SAMPLE_TYPE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameEqualTo(String value) {
            addCriterion("SAMPLE_TYPE_NAME =", value, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameNotEqualTo(String value) {
            addCriterion("SAMPLE_TYPE_NAME <>", value, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameGreaterThan(String value) {
            addCriterion("SAMPLE_TYPE_NAME >", value, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("SAMPLE_TYPE_NAME >=", value, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameLessThan(String value) {
            addCriterion("SAMPLE_TYPE_NAME <", value, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameLessThanOrEqualTo(String value) {
            addCriterion("SAMPLE_TYPE_NAME <=", value, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameLike(String value) {
            addCriterion("SAMPLE_TYPE_NAME like", value, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameNotLike(String value) {
            addCriterion("SAMPLE_TYPE_NAME not like", value, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameIn(List<String> values) {
            addCriterion("SAMPLE_TYPE_NAME in", values, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameNotIn(List<String> values) {
            addCriterion("SAMPLE_TYPE_NAME not in", values, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameBetween(String value1, String value2) {
            addCriterion("SAMPLE_TYPE_NAME between", value1, value2, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNameNotBetween(String value1, String value2) {
            addCriterion("SAMPLE_TYPE_NAME not between", value1, value2, "sampleTypeName");
            return (Criteria) this;
        }

        public Criteria andSampleCodeIsNull() {
            addCriterion("SAMPLE_CODE is null");
            return (Criteria) this;
        }

        public Criteria andSampleCodeIsNotNull() {
            addCriterion("SAMPLE_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andSampleCodeEqualTo(String value) {
            addCriterion("SAMPLE_CODE =", value, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeNotEqualTo(String value) {
            addCriterion("SAMPLE_CODE <>", value, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeGreaterThan(String value) {
            addCriterion("SAMPLE_CODE >", value, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeGreaterThanOrEqualTo(String value) {
            addCriterion("SAMPLE_CODE >=", value, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeLessThan(String value) {
            addCriterion("SAMPLE_CODE <", value, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeLessThanOrEqualTo(String value) {
            addCriterion("SAMPLE_CODE <=", value, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeLike(String value) {
            addCriterion("SAMPLE_CODE like", value, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeNotLike(String value) {
            addCriterion("SAMPLE_CODE not like", value, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeIn(List<String> values) {
            addCriterion("SAMPLE_CODE in", values, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeNotIn(List<String> values) {
            addCriterion("SAMPLE_CODE not in", values, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeBetween(String value1, String value2) {
            addCriterion("SAMPLE_CODE between", value1, value2, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleCodeNotBetween(String value1, String value2) {
            addCriterion("SAMPLE_CODE not between", value1, value2, "sampleCode");
            return (Criteria) this;
        }

        public Criteria andSampleNameIsNull() {
            addCriterion("SAMPLE_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSampleNameIsNotNull() {
            addCriterion("SAMPLE_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSampleNameEqualTo(String value) {
            addCriterion("SAMPLE_NAME =", value, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameNotEqualTo(String value) {
            addCriterion("SAMPLE_NAME <>", value, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameGreaterThan(String value) {
            addCriterion("SAMPLE_NAME >", value, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameGreaterThanOrEqualTo(String value) {
            addCriterion("SAMPLE_NAME >=", value, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameLessThan(String value) {
            addCriterion("SAMPLE_NAME <", value, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameLessThanOrEqualTo(String value) {
            addCriterion("SAMPLE_NAME <=", value, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameLike(String value) {
            addCriterion("SAMPLE_NAME like", value, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameNotLike(String value) {
            addCriterion("SAMPLE_NAME not like", value, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameIn(List<String> values) {
            addCriterion("SAMPLE_NAME in", values, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameNotIn(List<String> values) {
            addCriterion("SAMPLE_NAME not in", values, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameBetween(String value1, String value2) {
            addCriterion("SAMPLE_NAME between", value1, value2, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleNameNotBetween(String value1, String value2) {
            addCriterion("SAMPLE_NAME not between", value1, value2, "sampleName");
            return (Criteria) this;
        }

        public Criteria andSampleSizeIsNull() {
            addCriterion("SAMPLE_SIZE is null");
            return (Criteria) this;
        }

        public Criteria andSampleSizeIsNotNull() {
            addCriterion("SAMPLE_SIZE is not null");
            return (Criteria) this;
        }

        public Criteria andSampleSizeEqualTo(BigDecimal value) {
            addCriterion("SAMPLE_SIZE =", value, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSampleSizeNotEqualTo(BigDecimal value) {
            addCriterion("SAMPLE_SIZE <>", value, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSampleSizeGreaterThan(BigDecimal value) {
            addCriterion("SAMPLE_SIZE >", value, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSampleSizeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SAMPLE_SIZE >=", value, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSampleSizeLessThan(BigDecimal value) {
            addCriterion("SAMPLE_SIZE <", value, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSampleSizeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SAMPLE_SIZE <=", value, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSampleSizeIn(List<BigDecimal> values) {
            addCriterion("SAMPLE_SIZE in", values, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSampleSizeNotIn(List<BigDecimal> values) {
            addCriterion("SAMPLE_SIZE not in", values, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSampleSizeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SAMPLE_SIZE between", value1, value2, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSampleSizeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SAMPLE_SIZE not between", value1, value2, "sampleSize");
            return (Criteria) this;
        }

        public Criteria andSamplingDateIsNull() {
            addCriterion("SAMPLING_DATE is null");
            return (Criteria) this;
        }

        public Criteria andSamplingDateIsNotNull() {
            addCriterion("SAMPLING_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andSamplingDateEqualTo(Date value) {
            addCriterionForJDBCDate("SAMPLING_DATE =", value, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andSamplingDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("SAMPLING_DATE <>", value, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andSamplingDateGreaterThan(Date value) {
            addCriterionForJDBCDate("SAMPLING_DATE >", value, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andSamplingDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("SAMPLING_DATE >=", value, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andSamplingDateLessThan(Date value) {
            addCriterionForJDBCDate("SAMPLING_DATE <", value, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andSamplingDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("SAMPLING_DATE <=", value, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andSamplingDateIn(List<Date> values) {
            addCriterionForJDBCDate("SAMPLING_DATE in", values, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andSamplingDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("SAMPLING_DATE not in", values, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andSamplingDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("SAMPLING_DATE between", value1, value2, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andSamplingDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("SAMPLING_DATE not between", value1, value2, "samplingDate");
            return (Criteria) this;
        }

        public Criteria andPurposeIsNull() {
            addCriterion("PURPOSE is null");
            return (Criteria) this;
        }

        public Criteria andPurposeIsNotNull() {
            addCriterion("PURPOSE is not null");
            return (Criteria) this;
        }

        public Criteria andPurposeEqualTo(Byte value) {
            addCriterion("PURPOSE =", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotEqualTo(Byte value) {
            addCriterion("PURPOSE <>", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeGreaterThan(Byte value) {
            addCriterion("PURPOSE >", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeGreaterThanOrEqualTo(Byte value) {
            addCriterion("PURPOSE >=", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLessThan(Byte value) {
            addCriterion("PURPOSE <", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeLessThanOrEqualTo(Byte value) {
            addCriterion("PURPOSE <=", value, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeIn(List<Byte> values) {
            addCriterion("PURPOSE in", values, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotIn(List<Byte> values) {
            addCriterion("PURPOSE not in", values, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeBetween(Byte value1, Byte value2) {
            addCriterion("PURPOSE between", value1, value2, "purpose");
            return (Criteria) this;
        }

        public Criteria andPurposeNotBetween(Byte value1, Byte value2) {
            addCriterion("PURPOSE not between", value1, value2, "purpose");
            return (Criteria) this;
        }

        public Criteria andTransportStatusIsNull() {
            addCriterion("TRANSPORT_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andTransportStatusIsNotNull() {
            addCriterion("TRANSPORT_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andTransportStatusEqualTo(Byte value) {
            addCriterion("TRANSPORT_STATUS =", value, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andTransportStatusNotEqualTo(Byte value) {
            addCriterion("TRANSPORT_STATUS <>", value, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andTransportStatusGreaterThan(Byte value) {
            addCriterion("TRANSPORT_STATUS >", value, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andTransportStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("TRANSPORT_STATUS >=", value, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andTransportStatusLessThan(Byte value) {
            addCriterion("TRANSPORT_STATUS <", value, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andTransportStatusLessThanOrEqualTo(Byte value) {
            addCriterion("TRANSPORT_STATUS <=", value, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andTransportStatusIn(List<Byte> values) {
            addCriterion("TRANSPORT_STATUS in", values, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andTransportStatusNotIn(List<Byte> values) {
            addCriterion("TRANSPORT_STATUS not in", values, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andTransportStatusBetween(Byte value1, Byte value2) {
            addCriterion("TRANSPORT_STATUS between", value1, value2, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andTransportStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("TRANSPORT_STATUS not between", value1, value2, "transportStatus");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UPDATE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UPDATE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("UPDATE_TIME =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("UPDATE_TIME <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("UPDATE_TIME >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("UPDATE_TIME <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_TIME <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("UPDATE_TIME in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("UPDATE_TIME not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_TIME not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusIsNull() {
            addCriterion("SAMPLE_ERROR_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusIsNotNull() {
            addCriterion("SAMPLE_ERROR_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusEqualTo(Byte value) {
            addCriterion("SAMPLE_ERROR_STATUS =", value, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusNotEqualTo(Byte value) {
            addCriterion("SAMPLE_ERROR_STATUS <>", value, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusGreaterThan(Byte value) {
            addCriterion("SAMPLE_ERROR_STATUS >", value, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("SAMPLE_ERROR_STATUS >=", value, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusLessThan(Byte value) {
            addCriterion("SAMPLE_ERROR_STATUS <", value, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusLessThanOrEqualTo(Byte value) {
            addCriterion("SAMPLE_ERROR_STATUS <=", value, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusIn(List<Byte> values) {
            addCriterion("SAMPLE_ERROR_STATUS in", values, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusNotIn(List<Byte> values) {
            addCriterion("SAMPLE_ERROR_STATUS not in", values, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusBetween(Byte value1, Byte value2) {
            addCriterion("SAMPLE_ERROR_STATUS between", value1, value2, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andSampleErrorStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("SAMPLE_ERROR_STATUS not between", value1, value2, "sampleErrorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorReasonIsNull() {
            addCriterion("ERROR_REASON is null");
            return (Criteria) this;
        }

        public Criteria andErrorReasonIsNotNull() {
            addCriterion("ERROR_REASON is not null");
            return (Criteria) this;
        }

        public Criteria andErrorReasonEqualTo(String value) {
            addCriterion("ERROR_REASON =", value, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonNotEqualTo(String value) {
            addCriterion("ERROR_REASON <>", value, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonGreaterThan(String value) {
            addCriterion("ERROR_REASON >", value, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonGreaterThanOrEqualTo(String value) {
            addCriterion("ERROR_REASON >=", value, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonLessThan(String value) {
            addCriterion("ERROR_REASON <", value, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonLessThanOrEqualTo(String value) {
            addCriterion("ERROR_REASON <=", value, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonLike(String value) {
            addCriterion("ERROR_REASON like", value, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonNotLike(String value) {
            addCriterion("ERROR_REASON not like", value, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonIn(List<String> values) {
            addCriterion("ERROR_REASON in", values, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonNotIn(List<String> values) {
            addCriterion("ERROR_REASON not in", values, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonBetween(String value1, String value2) {
            addCriterion("ERROR_REASON between", value1, value2, "errorReason");
            return (Criteria) this;
        }

        public Criteria andErrorReasonNotBetween(String value1, String value2) {
            addCriterion("ERROR_REASON not between", value1, value2, "errorReason");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeIsNull() {
            addCriterion("ACCEPT_SAMPLE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeIsNotNull() {
            addCriterion("ACCEPT_SAMPLE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeEqualTo(Date value) {
            addCriterion("ACCEPT_SAMPLE_TIME =", value, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeNotEqualTo(Date value) {
            addCriterion("ACCEPT_SAMPLE_TIME <>", value, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeGreaterThan(Date value) {
            addCriterion("ACCEPT_SAMPLE_TIME >", value, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ACCEPT_SAMPLE_TIME >=", value, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeLessThan(Date value) {
            addCriterion("ACCEPT_SAMPLE_TIME <", value, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeLessThanOrEqualTo(Date value) {
            addCriterion("ACCEPT_SAMPLE_TIME <=", value, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeIn(List<Date> values) {
            addCriterion("ACCEPT_SAMPLE_TIME in", values, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeNotIn(List<Date> values) {
            addCriterion("ACCEPT_SAMPLE_TIME not in", values, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeBetween(Date value1, Date value2) {
            addCriterion("ACCEPT_SAMPLE_TIME between", value1, value2, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andAcceptSampleTimeNotBetween(Date value1, Date value2) {
            addCriterion("ACCEPT_SAMPLE_TIME not between", value1, value2, "acceptSampleTime");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoIsNull() {
            addCriterion("SAMPLE_ERROR_NEW_NO is null");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoIsNotNull() {
            addCriterion("SAMPLE_ERROR_NEW_NO is not null");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoEqualTo(String value) {
            addCriterion("SAMPLE_ERROR_NEW_NO =", value, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoNotEqualTo(String value) {
            addCriterion("SAMPLE_ERROR_NEW_NO <>", value, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoGreaterThan(String value) {
            addCriterion("SAMPLE_ERROR_NEW_NO >", value, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoGreaterThanOrEqualTo(String value) {
            addCriterion("SAMPLE_ERROR_NEW_NO >=", value, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoLessThan(String value) {
            addCriterion("SAMPLE_ERROR_NEW_NO <", value, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoLessThanOrEqualTo(String value) {
            addCriterion("SAMPLE_ERROR_NEW_NO <=", value, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoLike(String value) {
            addCriterion("SAMPLE_ERROR_NEW_NO like", value, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoNotLike(String value) {
            addCriterion("SAMPLE_ERROR_NEW_NO not like", value, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoIn(List<String> values) {
            addCriterion("SAMPLE_ERROR_NEW_NO in", values, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoNotIn(List<String> values) {
            addCriterion("SAMPLE_ERROR_NEW_NO not in", values, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoBetween(String value1, String value2) {
            addCriterion("SAMPLE_ERROR_NEW_NO between", value1, value2, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andSampleErrorNewNoNotBetween(String value1, String value2) {
            addCriterion("SAMPLE_ERROR_NEW_NO not between", value1, value2, "sampleErrorNewNo");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkIsNull() {
            addCriterion("ERROR_DEAL_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkIsNotNull() {
            addCriterion("ERROR_DEAL_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkEqualTo(String value) {
            addCriterion("ERROR_DEAL_REMARK =", value, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkNotEqualTo(String value) {
            addCriterion("ERROR_DEAL_REMARK <>", value, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkGreaterThan(String value) {
            addCriterion("ERROR_DEAL_REMARK >", value, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("ERROR_DEAL_REMARK >=", value, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkLessThan(String value) {
            addCriterion("ERROR_DEAL_REMARK <", value, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkLessThanOrEqualTo(String value) {
            addCriterion("ERROR_DEAL_REMARK <=", value, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkLike(String value) {
            addCriterion("ERROR_DEAL_REMARK like", value, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkNotLike(String value) {
            addCriterion("ERROR_DEAL_REMARK not like", value, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkIn(List<String> values) {
            addCriterion("ERROR_DEAL_REMARK in", values, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkNotIn(List<String> values) {
            addCriterion("ERROR_DEAL_REMARK not in", values, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkBetween(String value1, String value2) {
            addCriterion("ERROR_DEAL_REMARK between", value1, value2, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andErrorDealRemarkNotBetween(String value1, String value2) {
            addCriterion("ERROR_DEAL_REMARK not between", value1, value2, "errorDealRemark");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationIsNull() {
            addCriterion("FAMILY_RELATION is null");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationIsNotNull() {
            addCriterion("FAMILY_RELATION is not null");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationEqualTo(String value) {
            addCriterion("FAMILY_RELATION =", value, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationNotEqualTo(String value) {
            addCriterion("FAMILY_RELATION <>", value, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationGreaterThan(String value) {
            addCriterion("FAMILY_RELATION >", value, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationGreaterThanOrEqualTo(String value) {
            addCriterion("FAMILY_RELATION >=", value, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationLessThan(String value) {
            addCriterion("FAMILY_RELATION <", value, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationLessThanOrEqualTo(String value) {
            addCriterion("FAMILY_RELATION <=", value, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationLike(String value) {
            addCriterion("FAMILY_RELATION like", value, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationNotLike(String value) {
            addCriterion("FAMILY_RELATION not like", value, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationIn(List<String> values) {
            addCriterion("FAMILY_RELATION in", values, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationNotIn(List<String> values) {
            addCriterion("FAMILY_RELATION not in", values, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationBetween(String value1, String value2) {
            addCriterion("FAMILY_RELATION between", value1, value2, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andFamilyRelationNotBetween(String value1, String value2) {
            addCriterion("FAMILY_RELATION not between", value1, value2, "familyRelation");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusIsNull() {
            addCriterion("SYNCHRONIZED_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusIsNotNull() {
            addCriterion("SYNCHRONIZED_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusEqualTo(Byte value) {
            addCriterion("SYNCHRONIZED_STATUS =", value, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusNotEqualTo(Byte value) {
            addCriterion("SYNCHRONIZED_STATUS <>", value, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusGreaterThan(Byte value) {
            addCriterion("SYNCHRONIZED_STATUS >", value, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("SYNCHRONIZED_STATUS >=", value, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusLessThan(Byte value) {
            addCriterion("SYNCHRONIZED_STATUS <", value, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusLessThanOrEqualTo(Byte value) {
            addCriterion("SYNCHRONIZED_STATUS <=", value, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusIn(List<Byte> values) {
            addCriterion("SYNCHRONIZED_STATUS in", values, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusNotIn(List<Byte> values) {
            addCriterion("SYNCHRONIZED_STATUS not in", values, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusBetween(Byte value1, Byte value2) {
            addCriterion("SYNCHRONIZED_STATUS between", value1, value2, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andSynchronizedStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("SYNCHRONIZED_STATUS not between", value1, value2, "synchronizedStatus");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeIsNull() {
            addCriterion("OWNER_PHENOTYPE is null");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeIsNotNull() {
            addCriterion("OWNER_PHENOTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeEqualTo(Byte value) {
            addCriterion("OWNER_PHENOTYPE =", value, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeNotEqualTo(Byte value) {
            addCriterion("OWNER_PHENOTYPE <>", value, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeGreaterThan(Byte value) {
            addCriterion("OWNER_PHENOTYPE >", value, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("OWNER_PHENOTYPE >=", value, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeLessThan(Byte value) {
            addCriterion("OWNER_PHENOTYPE <", value, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeLessThanOrEqualTo(Byte value) {
            addCriterion("OWNER_PHENOTYPE <=", value, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeIn(List<Byte> values) {
            addCriterion("OWNER_PHENOTYPE in", values, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeNotIn(List<Byte> values) {
            addCriterion("OWNER_PHENOTYPE not in", values, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeBetween(Byte value1, Byte value2) {
            addCriterion("OWNER_PHENOTYPE between", value1, value2, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerPhenotypeNotBetween(Byte value1, Byte value2) {
            addCriterion("OWNER_PHENOTYPE not between", value1, value2, "ownerPhenotype");
            return (Criteria) this;
        }

        public Criteria andOwnerNameIsNull() {
            addCriterion("OWNER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andOwnerNameIsNotNull() {
            addCriterion("OWNER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerNameEqualTo(String value) {
            addCriterion("OWNER_NAME =", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotEqualTo(String value) {
            addCriterion("OWNER_NAME <>", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameGreaterThan(String value) {
            addCriterion("OWNER_NAME >", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameGreaterThanOrEqualTo(String value) {
            addCriterion("OWNER_NAME >=", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameLessThan(String value) {
            addCriterion("OWNER_NAME <", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameLessThanOrEqualTo(String value) {
            addCriterion("OWNER_NAME <=", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameLike(String value) {
            addCriterion("OWNER_NAME like", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotLike(String value) {
            addCriterion("OWNER_NAME not like", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameIn(List<String> values) {
            addCriterion("OWNER_NAME in", values, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotIn(List<String> values) {
            addCriterion("OWNER_NAME not in", values, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameBetween(String value1, String value2) {
            addCriterion("OWNER_NAME between", value1, value2, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotBetween(String value1, String value2) {
            addCriterion("OWNER_NAME not between", value1, value2, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeIsNull() {
            addCriterion("OWNER_AGE is null");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeIsNotNull() {
            addCriterion("OWNER_AGE is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeEqualTo(Short value) {
            addCriterion("OWNER_AGE =", value, "ownerAge");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeNotEqualTo(Short value) {
            addCriterion("OWNER_AGE <>", value, "ownerAge");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeGreaterThan(Short value) {
            addCriterion("OWNER_AGE >", value, "ownerAge");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeGreaterThanOrEqualTo(Short value) {
            addCriterion("OWNER_AGE >=", value, "ownerAge");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeLessThan(Short value) {
            addCriterion("OWNER_AGE <", value, "ownerAge");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeLessThanOrEqualTo(Short value) {
            addCriterion("OWNER_AGE <=", value, "ownerAge");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeIn(List<Short> values) {
            addCriterion("OWNER_AGE in", values, "ownerAge");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeNotIn(List<Short> values) {
            addCriterion("OWNER_AGE not in", values, "ownerAge");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeBetween(Short value1, Short value2) {
            addCriterion("OWNER_AGE between", value1, value2, "ownerAge");
            return (Criteria) this;
        }

        public Criteria andOwnerAgeNotBetween(Short value1, Short value2) {
            addCriterion("OWNER_AGE not between", value1, value2, "ownerAge");
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