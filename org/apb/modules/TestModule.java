package org.apb.modules;

import static org.basex.query.QueryError.IOERR_X;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.basex.io.IOContent;
import org.basex.io.in.BufferInput;
import org.basex.query.QueryException;
import org.basex.query.QueryModule;
import org.basex.query.value.Value;
import org.basex.query.value.ValueBuilder;
import org.basex.query.value.item.ANum;
import org.basex.query.value.item.B64Stream;
import org.basex.query.value.item.Int;
import org.basex.query.value.item.Item;
import org.basex.query.value.item.Str;
import org.basex.query.value.map.Map;
import org.basex.query.value.node.ANode;
import org.basex.query.value.node.FDoc;
import org.basex.query.value.node.FElem;
import org.basex.util.InputInfo;
import org.basex.util.InputParser;

import net.coobird.thumbnailator.Thumbnailator;

public class TestModule extends QueryModule {

    public static String hello(final String world) {
        return "Hello.. " + world;
    }

    public static Value sequence() {
        FElem elem1 = new FElem("root1");
        FElem elem2 = new FElem("root2");
        ValueBuilder vb = new ValueBuilder();
        vb.add(elem1);
        vb.add(elem2);
        return vb.value();
    }

    public static Value filewalk(final String path) throws IOException {
        Path startingDir = Paths.get(path);
        FileWalk pf = new FileWalk();
        Files.walkFileTree(startingDir, pf);
        return pf.result();
    }

    @Requires(Permission.NONE)
    public static ANode create() {
        FDoc doc = new FDoc("http://www.example.com");
        FElem elem = new FElem("root").add("attr", "value");
        doc.add(elem);
        return doc;
    }

    /*
     * test handling Map as argument 
     * if map has key depth then
     *       return value of key 
     * else
     * return size of map.
     */
    public static int mapinfo(Map m) throws QueryException {
        Item key = Str.get("depth");
        InputInfo ii = new InputInfo("what?", 0, 0);
        Value v = m.get(key, ii);
        if (v.isEmpty()) {
            return m.mapSize();
        }
        Item item = v.itemAt(0);
        if (item instanceof ANum) {
            return (int) ((ANum) item).itr(null);
        } else {
            return -1;
        }

    }
    /* stop current execution after timeout ms */

    /* stream test: make a copy */
    @Requires(Permission.NONE)
    public static B64Stream thumb(final B64Stream inputStream, final Int num) throws IOException, QueryException {
        BufferInput b = inputStream.input(new InputInfo(new InputParser("hi")));
        int width = (int) num.itr();
        int height = width;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        B64Stream os2 = new B64Stream(new IOContent(os.toByteArray()), IOERR_X);
        Thumbnailator.createThumbnail(b, os, width, height);
        return os2;

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
        ImageReadParam param = reader.getDefaultReadParam();
        reader.setInput(stream, true, true);
        BufferedImage bi;
        try {
            bi = reader.read(0, param);
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
