package org.apb.modules;

import static org.basex.query.util.Err.FILE_IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnailator;

import org.basex.core.Proc;
import org.basex.io.IOContent;
import org.basex.io.in.BufferInput;
import org.basex.query.*;
import org.basex.query.iter.ValueBuilder;
import org.basex.query.value.Value;
import org.basex.query.value.item.B64Stream;
import org.basex.query.value.item.Int;
import org.basex.query.value.node.ANode;
import org.basex.query.value.node.FDoc;
import org.basex.query.value.node.FElem;
import org.basex.util.InputInfo;
import org.basex.util.InputParser;

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
	 public void setTimeout(final int timeout){
		   Proc p=(Proc)context;
		   p.stopTimeout();
		   p.startTimeout(timeout);
	 }
	 
	 /* stream test: make a copy */
	 @Requires(Permission.NONE)
	 public B64Stream thumb(final B64Stream inputStream,final Int num) throws IOException, QueryException{
		 BufferInput b= inputStream.input(new InputInfo(new InputParser("hi")) );
		 int width=(int) num.itr();
		 int height=width; 
		 ByteArrayOutputStream os=new ByteArrayOutputStream();
		 B64Stream os2 =new B64Stream(new IOContent(os.toByteArray()), FILE_IO);
		 Thumbnailator.createThumbnail(b,os,width,height);
		 return os2;

	 }

}
