(:~  
 :time queries with timeout 
 :)
declare variable $target:=fn:resolve-uri("xmark.xq"); 
 
declare function local:exec($c,$label,$exp,$timeout){
  let $s:=prof:current-ms()  
  let $result:=try{
               let $x:=client:query($c,$exp,map{'TIMEOUT':=$timeout})
               return ()
             }catch * {
               $err:description
             }
  return <exec label="{$label}" time="{prof:current-ms()-$s}" result="{$result}"/>
};

declare function local:timemod($mod,$timeout){
  let $i:=inspect:module($mod)
  let $import:=<text>
  import module namespace test = 'org.apb.modules.TestModule';
  import module namespace {$i/@prefix/fn:string()} = '{$i/@uri/fn:string()}' at '{$mod }';
  </text>
               
  let $c := client:connect('localhost', 1984, 'admin', 'admin')
  for $f in $i/function[annotation/@name="unit:test"] 
  let $label:=$f/@name/fn:string(.) 
  let $exp:=$import || " let $t:=test:setTimeout(xs:int($TIMEOUT)) return " || $label ||"()"
  return local:exec($c,$label,$exp,$timeout)
 };

local:timemod($target,20000)