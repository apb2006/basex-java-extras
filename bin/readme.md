# BaseX java Utils

Testbed for a collection of Java bindings for BaseX
## installation
copy `apbtest.jar` to the basex lib folder. 
Note: basex.exe on windows does not pickup additional jars use `bin/basexgui.bat`

## hello
Returns "hello world"
````
Q{java:org.apb.modules.TestModule}hello("aa")
````

## sequence
`Q{java:org.apb.modules.TestModule}sequence()`
Returns (<root1/>,<root2/>)

## create
Returns element

## thumb
returns thumbnail

## makeCollection
returns collection from iterable

````
declare namespace apb="java:org.apb.modules.TestModule";

let $a:=apb:makeCollection($items)
````

## runnable

````
declare namespace apb="java:org.apb.modules.TestModule";

apb:runnable("2+2")

## timeUnit
````