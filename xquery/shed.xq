declare namespace context="java:org.basex.core.Context";
declare namespace qp="java:org.basex.query.QueryProcessor";
declare namespace Executor="java.util.concurrent.ScheduledThreadPoolExecutor";
declare namespace TimeUnit="java:java.util.concurrent.TimeUnit";
declare namespace apb="java:org.apb.modules.TestModule";

let $shed:=Executor:new(xs:int(1))
let $c:=context:new()

let $xq:="
declare function local:prime($n){
  $n = 2 or ($n > 2 and (every $d in 2 to xs:integer(math:sqrt($n)) satisfies $n mod $d > 0))
};
(1 to 100000)[local:prime(.)]=>count()
"

let $run:=  apb:runnable($xq)
return Executor:schedule($shed,$run, xs:int(4), apb:timeUnit("SECONDS"))