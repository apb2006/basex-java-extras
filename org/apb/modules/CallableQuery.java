package org.apb.modules;

/*
 * Async query utilities
 * @copyright Quodatum
 * @licence BSD
 * @author andy bunce
 * @since aug 2015
 * 
 */
import java.util.Date;
import java.util.concurrent.Callable;

import org.basex.core.Context;
import org.basex.query.QueryProcessor;
import org.basex.query.value.Value;
import org.basex.query.value.ValueBuilder;
import org.basex.query.value.item.AStr;
import org.basex.query.value.item.Item;
import org.basex.query.value.node.ANode;
import org.basex.query.value.node.FElem;

public class CallableQuery implements Callable<Value> {
	private String xquery;
	private Date started;
	private Date ended;
	public CallableQuery(String xquery) {
		this.xquery = xquery;
	}

	@Override
	public Value call() throws Exception {
		try {
			started=new Date();
			Context context = new Context();
			// Create a query processor
			@SuppressWarnings("resource")
			QueryProcessor proc = new QueryProcessor(xquery, context);
			// Execute the query
			Value value = proc.value();
			ended=new Date();
			// Print result as string.
			System.out.println("result----------------");
			System.out.println(value);
			return value;

		} catch (Exception ex) {
			// @TODO raise good error
			System.out.println(ex.getMessage());
			return null;
		}
	}

}
