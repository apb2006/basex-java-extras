package org.apb.modules;
import org.basex.query.QueryException;
import org.basex.query.value.Value;
import org.basex.query.value.item.*;
import org.basex.query.value.map.MapBuilder;
import org.basex.query.value.map.XQMap;
import org.basex.query.value.seq.IntSeq;
import org.basex.query.value.type.AtomType;
import org.basex.util.InputInfo;

/**
 * Examples for creating XQMaps in BaseX and XQuery.
 */
public final class XQueryMaps {
  /**
   * Returns an empty XQMap.
   * @return XQMap
   */
  public static XQMap emptyMap() {
    return new MapBuilder().finish();
  }

  /**
   * Returns a XQMap with the specified key and value.
   * @return XQMap
   */
  public XQMap entry(Item key, Value value) throws QueryException {
    // as the XQMap is immutable, the input for a new XQMap is always an existing XQMap.
    return emptyMap().put(key, value, null);
  }

  /**
   * Returns a XQMap with a static key and value.
   * @return XQMap
   */
  public XQMap mapExample() throws QueryException {
    Item key = Str.get("key");
    Value value = IntSeq.get(new long[] { 1, 2 }, AtomType.INTEGER);
    return entry(key, value);
  }
  
  /*
   * test handling XQMap as argument 
   * if XQMap has key depth then
   *       return value of key or -1 if not integer
   * else
   *       return size of XQMap.
   * Q{java:org.apb.modules.XQueryXQMaps}XQMapinfo(XQMap{"depth":12})
   */
  public static int mapinfo(XQMap m) throws QueryException {
      Item key = Str.get("depth");
      InputInfo ii = new InputInfo("XQueryXQMaps.java", 0, 0);
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
}

