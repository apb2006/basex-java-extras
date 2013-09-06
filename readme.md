# BaseX java Utils

A collection of Java bindings

##  setTimeout

interupt execution after specified time

````xquery
 import module namespace test = 'org.apb.modules.TestModule';
declare function local:prime($n){
$n = 2 or ($n > 2 and
(every $d in 2 to xs:integer(math:sqrt($n)) satisfies $n mod $d > 0))
};
let $t:=test:setTimeout(xs:int(5000))
return count((1 to 1000000)[local:prime(.)])
````
