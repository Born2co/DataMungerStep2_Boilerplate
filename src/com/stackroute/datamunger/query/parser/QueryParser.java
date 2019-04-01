package com.stackroute.datamunger.query.parser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		queryParameter.setFileName(getFileName(queryString));
		queryParameter.setBaseQuery(getBaseQuery(queryString));
		queryParameter.setOrderByFields(getOrderByFields(queryString));
		queryParameter.setGroupByFields(getGroupByFields(queryString));
		queryParameter.setFields(getFields(queryString));
		queryParameter.setAggregrateFunctions(getAggregrateFunctions(queryString));
		queryParameter.setRestrictions(getRestrictions(queryString));
		queryParameter.setLogicalOperators(getLogicalOperators(queryString));
		return queryParameter;
	}

	private String getBaseQuery(String queryString) {
		 String[] splitInput = queryString.split(" ");
         StringBuilder output = new StringBuilder();
         for (String str : splitInput) {
                if (str.equalsIgnoreCase("where") || str.equalsIgnoreCase("group")) {
                      break;
                }
                output = output.append(str + " ");
         }
         String result = output.substring(0, output.length() - 1);

         return result;
	}

	private String getFileName(String queryString) {
		  Boolean flag = false;
          String result = null;
          String[] splitInput = queryString.split(" ");
          for (String str : splitInput) {
                 if (flag) {
                       flag = false;
                       result = str;
                 }
                 if (str.equalsIgnoreCase("from")) {
                       flag = true;
                 }

          }
          return result;
	}

	private ArrayList<String> getOrderByFields(String queryString) {
		List<String> orderByList = new ArrayList<>();
		if(queryString.contains(" order by ")) {
		String orderBy = queryString.split(" order by ")[1];
		orderByList.add(orderBy);
		}
		return (ArrayList<String>) orderByList;
	}

	private ArrayList<String> getGroupByFields(String queryString) {
		List<String> groupByList = new ArrayList<>();
		if(queryString.contains(" group by ") && !queryString.contains(" order by ")) {
		String groupBy = queryString.split(" group by ")[1];
		groupByList.add(groupBy);
		} else if (queryString.contains(" group by ") && queryString.contains(" order by ")) {
			String groupBy = queryString.split(" group by ")[1].split(" order by ")[0];
			groupByList.add(groupBy);
		}
		return (ArrayList<String>) groupByList;
	}

	private ArrayList<String> getFields(String queryString) {
		List<String> getFields = new ArrayList<>();
		String[] fields = queryString.split("select ")[1].split(" ")[0].split(",");
		for (String s : fields) {
			getFields.add(s);
		}
		return (ArrayList<String>) getFields;
	}

	private ArrayList<AggregateFunction> getAggregrateFunctions(String queryString) {
		String field = null;
		String function = null;
		List<AggregateFunction> returnArgs = new ArrayList<>();
		AggregateFunction agf = new AggregateFunction(field, function);

		if (queryString.contains("min") || queryString.contains("max") || queryString.contains("count")) {
			String count = queryString.split("select ")[1].split(" from ")[0];
			System.out.println(count);
			String[] maxGet = count.split(",");
			for (String s : maxGet) {
				if (s.contains("min") || s.contains("max") || s.contains("count") || s.contains("avg")) {
					function = s.split("\\(")[0];
					System.out.println(function+"f");
					field = s.split("\\(")[1].split("\\)")[0];
					System.out.println(field+"field");
					agf.setField(field);
					agf.setFunction(function);
					AggregateFunction agfn = new AggregateFunction(field, function);
					agfn.setField(agf.getField());
					agfn.setFunction(agf.getFunction());
					returnArgs.add(agfn);
					
					
					
					
					
				}

			}
		} else {
			return null;
		}

		return (ArrayList<AggregateFunction>) returnArgs;
	}

	private ArrayList<Restriction> getRestrictions(String queryString) {
		String name = null;
		String value = null;
		String condition = null;
		String query = queryString;
		
		int count = 0;
		List<Restriction> returnList = new ArrayList<>();
		Restriction rest = new Restriction(name, value, condition);
		if(query.contains(" where ")){
			String[] trims = null;
			String trim = null;
		if(query.contains(" group by ") || query.contains(" order by ")){
			count++;
			if(query.contains(" group by") && query.contains(" order by ")){
				
				trim = query.split("where ")[1].split(" group by")[0];
				trim = trim.replace(" and ", "-");
			}if(query.contains(" order by ") && !query.contains(" group by ") && query.contains(" where ")){
				trim = query.split(" where ")[1].split(" order by")[0];
				System.out.println("trim order by : "+trim);
				trim = trim.replace(" and ", "-");
			}
		}
		if(count==0){ if (query.contains(" where ")) {
			System.out.println("Inside where");
			 trim = query.split("where ")[1];
			if(trim.contains(" or ")){
			trim = trim.replace(" or ", "-");
			}
			if(trim.contains(" and ")){
			trim = trim.replaceAll(" and ", "-");
			}
		}
		}
			if(trim!=null){
				trims = trim.split("-");
				for(String s : trims) {
					System.out.println("trims"+s);
					if(s.contains("=")) {
						String[] set1 = s.split("=");
						Restriction res = new Restriction(name, value, condition);
					    rest.setName(set1[0].toString().trim());
						rest.setValue(set1[1].toString().trim());
						rest.setCondition("=");
						if(rest.getValue().contains("'")){
							System.out.println(rest.getValue());
							 Pattern pt = Pattern.compile("[^a-zA-Z0-9]");
						        Matcher match= pt.matcher(rest.getValue());
						        while(match.find())
						        {
						            String valu= match.group();
						        rest.setValue(rest.getValue().replaceAll("\\"+valu,""));
						        System.out.println(rest.getValue());
						        }
						}
						res.setName(rest.getName());
						res.setValue(rest.getValue());
						res.setCondition(rest.condition);
						returnList.add(res);
						System.out.println(rest.toString());
					} else if (s.contains(" > ")) {
						System.out.println(">>");
						String[] set1 = s.split(" > ");
						Restriction res = new Restriction(name, value, condition);
						rest.setName(set1[0].toString());
						rest.setValue(set1[1].toString());
						rest.setCondition(">");
						res.setName(rest.getName());
						res.setValue(rest.getValue());
						res.setCondition(rest.getCondition());
						returnList.add(res);
						System.out.println(rest.toString()+"end");
					} else if (s.contains(" < ")) {
					
						String[] set1 = s.split(" < ");
						rest.setName(set1[0].toString());
						rest.setValue(set1[1].toString());
						rest.setCondition("<");
						returnList.add(rest);
				}
					
			}
				
			} 
			
			
	}else {
		return null;
		}
		return (ArrayList<Restriction>) returnList;
	}

		
		
	


	private ArrayList<String> getLogicalOperators(String queryString) {
	     List<String> operator = new ArrayList<>();
		if(queryString.contains(" where ")) {
			String split = queryString.split(" where ")[1];
			String[] splits = split.split(" ");
	    for(String s : splits) {
	    	if(s.equals("and") || s.equals("or")) {
	    	operator.add(s);	
	    	}
	    }
		} else {
			return null;
		}
		return (ArrayList<String>) operator;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */

	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */

	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */

	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */

	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */

	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */

	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */

	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */
}

