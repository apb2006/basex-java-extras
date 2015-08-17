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
import org.basex.core.users.UserText;
import org.basex.query.QueryProcessor;
import org.basex.query.value.Value;

public class CallableQuery implements Callable<Value> {
	private String xquery;
	private Context ctx;
	public Date started;
	public Date ended;
	public CallableQuery(String xquery,Context context) {
		this.xquery = xquery;
		this.ctx = new Context(context);
	    this.ctx.user(ctx.users.get(UserText.ADMIN));
	}

	@Override
	public Value call() throws Exception {
		try {
			started=new Date();
			// Create a query processor
			@SuppressWarnings("resource")
			QueryProcessor proc = new QueryProcessor(xquery, ctx);
			// Execute the query
			Value value = proc.value();
			ended=new Date();
			// Print result as string.
			System.out.println("result----------------");
			System.out.println(value);
			return value;

		} catch (Exception ex) {
			// @TODO raise good error
			System.out.println(ex.toString());
			return null;
		}
	}

}
