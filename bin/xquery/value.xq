(: async test :)
declare namespace Executor="java.util.concurrent.ScheduledThreadPoolExecutor";
declare namespace async="java:org.apb.modules.Async";
declare namespace ft="java.util.concurrent.FutureTask";
let $shed:=Executor:new(xs:int(10))

let $xq:='
let $state:=db:open("doc-doc","/state.xml")/state
return (replace value of node $state/hits with 1+$state/hits,
            db:output(1+$state/hits))
'
(: let $fut:= Executor:schedule($shed,$run, xs:int(4), async:timeUnit("SECONDS")) :)
let $task:= async:futureTask($xq)
let $fut2:= Executor:submit($shed,$task)

let $_:=(Executor:shutdown($shed),prof:sleep(1000))
return( "# ",ft:get($task)
)