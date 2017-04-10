package org.apb.modules;
import org.basex.query.QueryException;
import org.basex.query.value.Value;
import org.basex.query.value.item.*;
import org.basex.query.value.map.Map;
import org.basex.query.value.seq.IntSeq;
import org.basex.query.value.type.AtomType;
import org.basex.util.InputInfo;

/**
 * Examples for creating maps in BaseX and XQuery.
 */
public final class XQueryMaps {
  /**
   * Returns an empty map.
   * @return map
   */
  public Map emptyMap() {
    return Map.EMPTY;
  }

  /**
   * Returns a map with the specified key and value.
   * @return map
   */
  public Map map(Item key, Value value) throws QueryException {
    // as the map is immutable, the input for a new map is always an existing map.
    return emptyMap().put(key, value, null);
  }

  /**
   * Returns a map with a static key and value.
   * @return map
   */
  public Map mapExample() throws QueryException {
    Item key = Str.get("key");
    Value value = IntSeq.get(new long[] { 1, 2 }, AtomType.ITR);
    return map(key, value);
  }
  
  /*
   * test handling Map as argument 
   * if map has key depth then
   *       return value of key or -1 if not integer
   * else
   *       return size of map.
   * Q{java:org.apb.modules.XQueryMaps}mapinfo(map{"depth":12})
   */
  public static int mapinfo(Map m) throws QueryException {
      Item key = Str.get("depth");
      InputInfo ii = new InputInfo("XQueryMaps.java", 0, 0);
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

