module namespace test = 'http://basex.org/modules/xqunit-tests';

(:~
 : unit tests for java extras module
 :)
declare namespace j="java:org.apb.modules.TestModule";
declare function test:resource($file) as xs:string
{
  file:resolve-path($file,static-base-uri())
};

(:~ hello :)
declare %unit:test function test:hello() {
 unit:assert(j:hello("a"))  
};
 
(:~ sequence :)
declare  %unit:test function test:sequence() {
 unit:assert(j:sequence())  
};

(:~ sequence :)
declare %unit:test function test:create() {
 unit:assert(j:create())  
};

(:~ image format :)
declare %unit:test function test:imageFormatName() {
  let $f:=function($f){test:resource("resources/" || $f)=> j:fileInputStream()=>j:imageFormatName()}

 return (
   unit:assert-equals($f("13569"),"JPEG")
   ,unit:assert-equals($f("46"),"png")
    ,unit:assert-equals($f("5084344"),"bmp")
 )
};