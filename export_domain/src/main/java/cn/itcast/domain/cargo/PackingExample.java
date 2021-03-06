package cn.itcast.domain.cargo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PackingExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PackingExample() {
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

    protected abstract static class GeneratedCriteria implements Serializable{
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

        public Criteria andPackingListIdIsNull() {
            addCriterion("packing_list_id is null");
            return (Criteria) this;
        }

        public Criteria andPackingListIdIsNotNull() {
            addCriterion("packing_list_id is not null");
            return (Criteria) this;
        }

        public Criteria andPackingListIdEqualTo(String value) {
            addCriterion("packing_list_id =", value, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdNotEqualTo(String value) {
            addCriterion("packing_list_id <>", value, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdGreaterThan(String value) {
            addCriterion("packing_list_id >", value, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdGreaterThanOrEqualTo(String value) {
            addCriterion("packing_list_id >=", value, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdLessThan(String value) {
            addCriterion("packing_list_id <", value, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdLessThanOrEqualTo(String value) {
            addCriterion("packing_list_id <=", value, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdLike(String value) {
            addCriterion("packing_list_id like", value, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdNotLike(String value) {
            addCriterion("packing_list_id not like", value, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdIn(List<String> values) {
            addCriterion("packing_list_id in", values, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdNotIn(List<String> values) {
            addCriterion("packing_list_id not in", values, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdBetween(String value1, String value2) {
            addCriterion("packing_list_id between", value1, value2, "packingListId");
            return (Criteria) this;
        }

        public Criteria andPackingListIdNotBetween(String value1, String value2) {
            addCriterion("packing_list_id not between", value1, value2, "packingListId");
            return (Criteria) this;
        }

        public Criteria andExportIdsIsNull() {
            addCriterion("export_ids is null");
            return (Criteria) this;
        }

        public Criteria andExportIdsIsNotNull() {
            addCriterion("export_ids is not null");
            return (Criteria) this;
        }

        public Criteria andExportIdsEqualTo(String value) {
            addCriterion("export_ids =", value, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsNotEqualTo(String value) {
            addCriterion("export_ids <>", value, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsGreaterThan(String value) {
            addCriterion("export_ids >", value, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsGreaterThanOrEqualTo(String value) {
            addCriterion("export_ids >=", value, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsLessThan(String value) {
            addCriterion("export_ids <", value, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsLessThanOrEqualTo(String value) {
            addCriterion("export_ids <=", value, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsLike(String value) {
            addCriterion("export_ids like", value, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsNotLike(String value) {
            addCriterion("export_ids not like", value, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsIn(List<String> values) {
            addCriterion("export_ids in", values, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsNotIn(List<String> values) {
            addCriterion("export_ids not in", values, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsBetween(String value1, String value2) {
            addCriterion("export_ids between", value1, value2, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportIdsNotBetween(String value1, String value2) {
            addCriterion("export_ids not between", value1, value2, "exportIds");
            return (Criteria) this;
        }

        public Criteria andExportNosIsNull() {
            addCriterion("export_nos is null");
            return (Criteria) this;
        }

        public Criteria andExportNosIsNotNull() {
            addCriterion("export_nos is not null");
            return (Criteria) this;
        }

        public Criteria andExportNosEqualTo(String value) {
            addCriterion("export_nos =", value, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosNotEqualTo(String value) {
            addCriterion("export_nos <>", value, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosGreaterThan(String value) {
            addCriterion("export_nos >", value, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosGreaterThanOrEqualTo(String value) {
            addCriterion("export_nos >=", value, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosLessThan(String value) {
            addCriterion("export_nos <", value, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosLessThanOrEqualTo(String value) {
            addCriterion("export_nos <=", value, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosLike(String value) {
            addCriterion("export_nos like", value, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosNotLike(String value) {
            addCriterion("export_nos not like", value, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosIn(List<String> values) {
            addCriterion("export_nos in", values, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosNotIn(List<String> values) {
            addCriterion("export_nos not in", values, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosBetween(String value1, String value2) {
            addCriterion("export_nos between", value1, value2, "exportNos");
            return (Criteria) this;
        }

        public Criteria andExportNosNotBetween(String value1, String value2) {
            addCriterion("export_nos not between", value1, value2, "exportNos");
            return (Criteria) this;
        }

        public Criteria andPackingTimeIsNull() {
            addCriterion("packing_time is null");
            return (Criteria) this;
        }

        public Criteria andPackingTimeIsNotNull() {
            addCriterion("packing_time is not null");
            return (Criteria) this;
        }

        public Criteria andPackingTimeEqualTo(Date value) {
            addCriterion("packing_time =", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeNotEqualTo(Date value) {
            addCriterion("packing_time <>", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeGreaterThan(Date value) {
            addCriterion("packing_time >", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("packing_time >=", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeLessThan(Date value) {
            addCriterion("packing_time <", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeLessThanOrEqualTo(Date value) {
            addCriterion("packing_time <=", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeIn(List<Date> values) {
            addCriterion("packing_time in", values, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeNotIn(List<Date> values) {
            addCriterion("packing_time not in", values, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeBetween(Date value1, Date value2) {
            addCriterion("packing_time between", value1, value2, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeNotBetween(Date value1, Date value2) {
            addCriterion("packing_time not between", value1, value2, "packingTime");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeIsNull() {
            addCriterion("total_volume is null");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeIsNotNull() {
            addCriterion("total_volume is not null");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeEqualTo(BigDecimal value) {
            addCriterion("total_volume =", value, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeNotEqualTo(BigDecimal value) {
            addCriterion("total_volume <>", value, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeGreaterThan(BigDecimal value) {
            addCriterion("total_volume >", value, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_volume >=", value, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeLessThan(BigDecimal value) {
            addCriterion("total_volume <", value, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_volume <=", value, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeIn(List<BigDecimal> values) {
            addCriterion("total_volume in", values, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeNotIn(List<BigDecimal> values) {
            addCriterion("total_volume not in", values, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_volume between", value1, value2, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andTotalVolumeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_volume not between", value1, value2, "totalVolume");
            return (Criteria) this;
        }

        public Criteria andNetWeightsIsNull() {
            addCriterion("net_weights is null");
            return (Criteria) this;
        }

        public Criteria andNetWeightsIsNotNull() {
            addCriterion("net_weights is not null");
            return (Criteria) this;
        }

        public Criteria andNetWeightsEqualTo(BigDecimal value) {
            addCriterion("net_weights =", value, "netWeights");
            return (Criteria) this;
        }

        public Criteria andNetWeightsNotEqualTo(BigDecimal value) {
            addCriterion("net_weights <>", value, "netWeights");
            return (Criteria) this;
        }

        public Criteria andNetWeightsGreaterThan(BigDecimal value) {
            addCriterion("net_weights >", value, "netWeights");
            return (Criteria) this;
        }

        public Criteria andNetWeightsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("net_weights >=", value, "netWeights");
            return (Criteria) this;
        }

        public Criteria andNetWeightsLessThan(BigDecimal value) {
            addCriterion("net_weights <", value, "netWeights");
            return (Criteria) this;
        }

        public Criteria andNetWeightsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("net_weights <=", value, "netWeights");
            return (Criteria) this;
        }

        public Criteria andNetWeightsIn(List<BigDecimal> values) {
            addCriterion("net_weights in", values, "netWeights");
            return (Criteria) this;
        }

        public Criteria andNetWeightsNotIn(List<BigDecimal> values) {
            addCriterion("net_weights not in", values, "netWeights");
            return (Criteria) this;
        }

        public Criteria andNetWeightsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("net_weights between", value1, value2, "netWeights");
            return (Criteria) this;
        }

        public Criteria andNetWeightsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("net_weights not between", value1, value2, "netWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsIsNull() {
            addCriterion("gross_weights is null");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsIsNotNull() {
            addCriterion("gross_weights is not null");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsEqualTo(BigDecimal value) {
            addCriterion("gross_weights =", value, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsNotEqualTo(BigDecimal value) {
            addCriterion("gross_weights <>", value, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsGreaterThan(BigDecimal value) {
            addCriterion("gross_weights >", value, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gross_weights >=", value, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsLessThan(BigDecimal value) {
            addCriterion("gross_weights <", value, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gross_weights <=", value, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsIn(List<BigDecimal> values) {
            addCriterion("gross_weights in", values, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsNotIn(List<BigDecimal> values) {
            addCriterion("gross_weights not in", values, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross_weights between", value1, value2, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andGrossWeightsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross_weights not between", value1, value2, "grossWeights");
            return (Criteria) this;
        }

        public Criteria andMarksIsNull() {
            addCriterion("marks is null");
            return (Criteria) this;
        }

        public Criteria andMarksIsNotNull() {
            addCriterion("marks is not null");
            return (Criteria) this;
        }

        public Criteria andMarksEqualTo(String value) {
            addCriterion("marks =", value, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksNotEqualTo(String value) {
            addCriterion("marks <>", value, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksGreaterThan(String value) {
            addCriterion("marks >", value, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksGreaterThanOrEqualTo(String value) {
            addCriterion("marks >=", value, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksLessThan(String value) {
            addCriterion("marks <", value, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksLessThanOrEqualTo(String value) {
            addCriterion("marks <=", value, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksLike(String value) {
            addCriterion("marks like", value, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksNotLike(String value) {
            addCriterion("marks not like", value, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksIn(List<String> values) {
            addCriterion("marks in", values, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksNotIn(List<String> values) {
            addCriterion("marks not in", values, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksBetween(String value1, String value2) {
            addCriterion("marks between", value1, value2, "marks");
            return (Criteria) this;
        }

        public Criteria andMarksNotBetween(String value1, String value2) {
            addCriterion("marks not between", value1, value2, "marks");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyIsNull() {
            addCriterion("packing_money is null");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyIsNotNull() {
            addCriterion("packing_money is not null");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyEqualTo(BigDecimal value) {
            addCriterion("packing_money =", value, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyNotEqualTo(BigDecimal value) {
            addCriterion("packing_money <>", value, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyGreaterThan(BigDecimal value) {
            addCriterion("packing_money >", value, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("packing_money >=", value, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyLessThan(BigDecimal value) {
            addCriterion("packing_money <", value, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("packing_money <=", value, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyIn(List<BigDecimal> values) {
            addCriterion("packing_money in", values, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyNotIn(List<BigDecimal> values) {
            addCriterion("packing_money not in", values, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("packing_money between", value1, value2, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andPackingMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("packing_money not between", value1, value2, "packingMoney");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("state like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("state not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(String value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(String value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(String value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(String value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(String value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(String value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLike(String value) {
            addCriterion("create_by like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotLike(String value) {
            addCriterion("create_by not like", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<String> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<String> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(String value1, String value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(String value1, String value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateDeptIsNull() {
            addCriterion("create_dept is null");
            return (Criteria) this;
        }

        public Criteria andCreateDeptIsNotNull() {
            addCriterion("create_dept is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDeptEqualTo(String value) {
            addCriterion("create_dept =", value, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptNotEqualTo(String value) {
            addCriterion("create_dept <>", value, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptGreaterThan(String value) {
            addCriterion("create_dept >", value, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptGreaterThanOrEqualTo(String value) {
            addCriterion("create_dept >=", value, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptLessThan(String value) {
            addCriterion("create_dept <", value, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptLessThanOrEqualTo(String value) {
            addCriterion("create_dept <=", value, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptLike(String value) {
            addCriterion("create_dept like", value, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptNotLike(String value) {
            addCriterion("create_dept not like", value, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptIn(List<String> values) {
            addCriterion("create_dept in", values, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptNotIn(List<String> values) {
            addCriterion("create_dept not in", values, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptBetween(String value1, String value2) {
            addCriterion("create_dept between", value1, value2, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateDeptNotBetween(String value1, String value2) {
            addCriterion("create_dept not between", value1, value2, "createDept");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNull() {
            addCriterion("company_id is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNotNull() {
            addCriterion("company_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdEqualTo(String value) {
            addCriterion("company_id =", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotEqualTo(String value) {
            addCriterion("company_id <>", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThan(String value) {
            addCriterion("company_id >", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThanOrEqualTo(String value) {
            addCriterion("company_id >=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThan(String value) {
            addCriterion("company_id <", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThanOrEqualTo(String value) {
            addCriterion("company_id <=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLike(String value) {
            addCriterion("company_id like", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotLike(String value) {
            addCriterion("company_id not like", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIn(List<String> values) {
            addCriterion("company_id in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotIn(List<String> values) {
            addCriterion("company_id not in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdBetween(String value1, String value2) {
            addCriterion("company_id between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotBetween(String value1, String value2) {
            addCriterion("company_id not between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("company_name =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("company_name >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("company_name <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("company_name like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("company_name not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("company_name in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "companyName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable{

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable{
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