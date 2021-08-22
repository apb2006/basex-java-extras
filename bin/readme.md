# BaseX java Utils

Testbed for a collection of Java bindings for BaseX
## installation
copy `jx-extras-x.y.z.jar` to the basex lib/custom/ folder. 


## Async 
moved to async project

## TestModule

### hello
Returns "hello world"
````
Q{java:org.apb.modules.TestModule}hello("aa")
````

### sequence
`Q{java:org.apb.modules.TestModule}sequence()`
Returns (<root1/>,<root2/>)

### create
Returns element


### makeCollection
returns collection from iterable

````
declare namespace apb="java:org.apb.modules.TestModule";

let $a:=apb:makeCollection($items)
````
## XQueryMaps
sample map usage.
Q{java:org.apb.modules.XQueryMaps}mapinfo(map{"depth":12})
