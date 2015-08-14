package org.apb.modules;

import static org.basex.query.QueryError.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import net.coobird.thumbnailator.Thumbnailator;

import org.basex.io.IOContent;
import org.basex.io.in.BufferInput;
import org.basex.query.*;
import org.basex.query.value.ValueBuilder;
import org.basex.query.value.Value;
import org.basex.query.value.item.B64Stream;
import org.basex.query.value.item.Int;
import org.basex.query.value.node.ANode;
import org.basex.query.value.node.FDoc;
import org.basex.query.value.node.FElem;
import org.basex.util.InputInfo;
import org.basex.util.InputParser;

public class TestModule  extends QueryModule{
	
	 public static String hello(final String world) {
		    return "Hello " + world;
		  }
	 
	 public static Value sequence() {
		    FElem elem1 = new FElem("root1");
		    FElem elem2 = new FElem("root2");
		    ValueBuilder vb = new ValueBuilder();
		    vb.add(elem1);
		    vb.add(elem2);
		    return vb.value();
		  }
	 
	 @Requires(Permission.NONE)
	 public static ANode create() {
		    FDoc doc = new FDoc("http://www.example.com");
		    FElem elem = new FElem("root").add("attr", "value");
		    doc.add(elem);
		    return doc;
		  }
	 /* stop current execution after timeout ms */

	 
	 /* stream test: make a copy */
	 @Requires(Permission.NONE)
	 public static B64Stream thumb(final B64Stream inputStream,final Int num) throws IOException, QueryException{
		 BufferInput b= inputStream.input(new InputInfo(new InputParser("hi")) );
		 int width=(int) num.itr();
		 int height=width; 
		 ByteArrayOutputStream os=new ByteArrayOutputStream();
		 B64Stream os2 =new B64Stream(new IOContent(os.toByteArray()),IOERR_X);
		 Thumbnailator.createThumbnail(b,os,width,height);
		 return os2;

	 }
	 
	 /* arraylist from iterable 
	  * http://stackoverflow.com/questions/6416706/easy-way-to-change-iterable-into-collection/6416921#6416921 
	  */
	 @Requires(Permission.NONE)
	 public static <E> Collection<E> makeCollection(Iterable<E> iter) {
		    Collection<E> list = new ArrayList<E>();
		    for (E item : iter) {
		        list.add(item);
		    }
		    return list;
		}
	 
	 /* time unit
	  * http://stackoverflow.com/questions/6416706/easy-way-to-change-iterable-into-collection/6416921#6416921 
	  */
	 @Requires(Permission.NONE)
	 public static  TimeUnit timeUnit(final String unit) { 
		    return TimeUnit.valueOf(unit);
		}
	 /* runnable for query
	  */
	 @Requires(Permission.ADMIN)
	 public static Runnable  runnable(final String xquery){
		 Runnable runnabledelayedTask = new Runnable()
		 	        {
		 	            @Override
		 	            public void run()
		 	            {
		 	                 System.out.println(Thread.currentThread().getName()+" is Running Delayed Task");
		 	            }
		 	        };
		 return runnabledelayedTask;	        
		}
	 
}
