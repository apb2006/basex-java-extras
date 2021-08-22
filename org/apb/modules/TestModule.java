package org.apb.modules;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.basex.query.QueryModule;
import org.basex.query.value.Value;
import org.basex.query.value.ValueBuilder;
import org.basex.query.value.node.ANode;
import org.basex.query.value.node.FDoc;
import org.basex.query.value.node.FElem;



public class TestModule extends QueryModule {

    public static String hello(final String world) {
        return "Hello.. " + world;
    }

    public Value sequence() {
        FElem elem1 = new FElem("root1");
        FElem elem2 = new FElem("root2");
        ValueBuilder vb = new ValueBuilder(queryContext);
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


    public static InputStream fileInputStream(String file) throws IOException {
        return (InputStream) new FileInputStream(file);
    }

    // @return image format
    // http://stackoverflow.com/questions/11425521/how-to-get-the-formatexjpen-png-gif-of-image-file-bufferedimage-in-java/31513596#31513596
    public static String imageFormatName(InputStream input) throws IOException {
        ImageInputStream stream = ImageIO.createImageInputStream(input);

        Iterator<ImageReader> iter = ImageIO.getImageReaders(stream);
        if (!iter.hasNext()) {
            return null;
        }
        ImageReader reader = (ImageReader) iter.next();
        reader.setInput(stream, true, true);

        try {
            return reader.getFormatName();
        } finally {
            reader.dispose();
            stream.close();
        }
    }

    /*
     * arraylist from iterable
     * http://stackoverflow.com/questions/6416706/easy-way
     * -to-change-iterable-into-collection/6416921#6416921
     */
    @Requires(Permission.NONE)
    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
        Collection<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

}
