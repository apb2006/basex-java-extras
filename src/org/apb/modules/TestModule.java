package org.apb.modules;

import org.basex.core.Proc;
import org.basex.query.*;
import org.basex.query.iter.ValueBuilder;
import org.basex.query.value.Value;
import org.basex.query.value.node.ANode;
import org.basex.query.value.node.FDoc;
import org.basex.query.value.node.FElem;

public class TestModule  extends QueryModule{
	
	 public String hello(final String world) {
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
	 public ANode create() {
		    FDoc doc = new FDoc("http://www.example.com");
		    FElem elem = new FElem("root").add("attr", "value");
		    doc.add(elem);
		    return doc;
		  }
	 /* stop current execution after timeout ms */
	 @Requires(Permission.NONE)
	 public void setTimeout(int timeout){
		   Proc p=(Proc)context;
		   p.stopTimeout();
		   p.startTimeout(timeout);
	 }

}
