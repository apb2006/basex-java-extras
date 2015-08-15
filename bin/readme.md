# BaseX java Utils

Testbed for a collection of Java bindings for BaseX
## installation
copy `jx-extras-x.y.z.jar` to the basex lib folder. 
Note: basex.exe on windows does not pickup additional jars use `bin/basexgui.bat`

## Async
Background task experiments. See `shed.xq`
### runnable

````
declare namespace async="java:org.apb.modules.Async";

async:runnable("2+2")
````

### timeUnit
`async:timeUnit("SECONDS")`

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

### thumb
returns thumbnail

### makeCollection
returns collection from iterable

````
declare namespace apb="java:org.apb.modules.TestModule";

let $a:=apb:makeCollection($items)
````

