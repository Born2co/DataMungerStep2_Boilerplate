package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */

public class QueryParameter {
	
	private String queryString;
	private String file;
	private String baseQuery;
	private ArrayList<Restriction> restrictions;
	private ArrayList<String> fields;
	private String QUERY_TYPE;
	private ArrayList<String> logicalOperators;
	private ArrayList<AggregateFunction> aggregateFunctions;
	private ArrayList<String> orderByField;
	private ArrayList<String> groupByField;
	
	

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getQUERY_TYPE() {
		return QUERY_TYPE;
	}

	public void setQUERY_TYPE(String qUERY_TYPE) {
		QUERY_TYPE = qUERY_TYPE;
	}

	public String getFileName() {
		
		return file;
	}
	
	public void setFileName(String file) {
		this.file = file;
	}

	public String getBaseQuery() {
		return baseQuery;
	}
	
	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}
	
	public void setRestrictions(ArrayList<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public List<String> getLogicalOperators() {
		return logicalOperators;
	}
	
	public void setLogicalOperators(ArrayList<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}

	public List<String> getFields() {
		return fields;
	}
	
	public void setFields(ArrayList<String> fields) {
		this.fields = fields;
	}

	public List<AggregateFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}
	
	public void setAggregrateFunctions(ArrayList<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}
	
    public List<String> getGroupByFields() {
		return groupByField;
	}
    
    public void setGroupByFields(ArrayList<String>groupByField) {
    	this.groupByField = groupByField;
    }

	public List<String> getOrderByFields() {
		return orderByField;
	}
	
	public void setOrderByFields(ArrayList<String> orderByField) {
		this.orderByField = orderByField;
	}
}
