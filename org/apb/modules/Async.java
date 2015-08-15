package org.apb.modules;

/*
 * Async query utilities
 * @copyright Quodatum
 * @licence BSD
 * @author andy bunce
 * @since aug 2015
 * 
 */
import java.util.concurrent.TimeUnit;

import org.basex.core.Context;
import org.basex.query.QueryModule;
import org.basex.query.QueryProcessor;
import org.basex.query.value.Value;

public class Async extends QueryModule {
	/*
	 * time unit
	 */
	@Requires(Permission.NONE)
	public static TimeUnit timeUnit(final String unit) {
		return TimeUnit.valueOf(unit);
	}

	/*
	 * runnable for query
	 */
	@Requires(Permission.ADMIN)
	public static Runnable runnable(final String xquery) {
		Runnable runnabledelayedTask = new Runnable() {
			@SuppressWarnings("resource")
			@Override
			public void run() {

				System.out.println(Thread.currentThread().getName()
						+ " is Running Delayed Task");
				System.out.println(xquery);
				try {
					Context context = new Context();
					// Create a query processor
					QueryProcessor proc = new QueryProcessor(xquery, context); 
						// Execute the query
						Value result = proc.value();

						// Print result as string.
						System.out.println("result----------------");
						System.out.println(result);
					
				}
					catch ( Exception ex) {
						System.out.println(ex.getMessage());
				}	
			}


		};
		return runnabledelayedTask;
	}
}
