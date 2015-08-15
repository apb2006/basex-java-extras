module namespace xmark = 'apb.xmark.query';
(:
 : "C:\Users\U8009642\Desktop\xmark\xmlgen.exe"
 : unit:test-uris("C:\Users\U8009642\Desktop\xmark\queries\xmark.xq")
 :)
 (:~ Initializing function, which is called before each test. :)
import module namespace test = 'org.apb.modules.TestModule';

declare %unit:test
function xmark:q1(){
	let $auction := doc("xmlgen/auction.xml") return
	for $b in $auction/site/people/person[@id = "person0"] return $b/name/text()
};

declare %unit:test
function xmark:q2(){
	let $auction := doc("xmlgen/auction.xml") return
	for $b in $auction/site/open_auctions/open_auction
	return <increase>{$b/bidder[1]/increase/text()}</increase>
};

declare %unit:test
function xmark:q3(){
	let $auction := doc("xmlgen/auction.xml") return
	for $b in $auction/site/open_auctions/open_auction
	where zero-or-one($b/bidder[1]/increase/text()) * 2 <= $b/bidder[last()]/increase/text()
	return
	  <increase
	  first="{$b/bidder[1]/increase/text()}"
	  last="{$b/bidder[last()]/increase/text()}"/>
};

declare %unit:test
function xmark:q4(){
	let $auction := doc("xmlgen/auction.xml") return
	for $b in $auction/site/open_auctions/open_auction
	where
	  some $pr1 in $b/bidder/personref[@person = "person20"],
		   $pr2 in $b/bidder/personref[@person = "person51"]
	  satisfies $pr1 << $pr2
	return <history>{$b/reserve/text()}</history>
};

declare %unit:test
function xmark:q5(){
	let $auction := doc("xmlgen/auction.xml") return
	count(
	  for $i in $auction/site/closed_auctions/closed_auction
	  where $i/price/text() >= 40
	  return $i/price
	)
};

declare %unit:test
function xmark:q6(){
	let $auction := doc("xmlgen/auction.xml") return
    for $b in $auction//site/regions return count($b//item)
};

declare %unit:test
function xmark:q7(){
	let $auction := doc("xmlgen/auction.xml") return
	for $p in $auction/site
	return
	  count($p//description) + count($p//annotation) + count($p//emailaddress)
};

declare %unit:test
function xmark:q8(){
	let $auction := doc("xmlgen/auction.xml") return
	for $p in $auction/site/people/person
	let $a :=
	  for $t in $auction/site/closed_auctions/closed_auction
	  where $t/buyer/@person = $p/@id
	  return $t
	return <item person="{$p/name/text()}">{count($a)}</item>
};

declare %unit:test
function xmark:q9(){
	let $auction := doc("xmlgen/auction.xml") return
	let $ca := $auction/site/closed_auctions/closed_auction return
	let
		$ei := $auction/site/regions/europe/item
	for $p in $auction/site/people/person
	let $a :=
	  for $t in $ca
	  where $p/@id = $t/buyer/@person
	  return
		let $n := for $t2 in $ei where $t/itemref/@item = $t2/@id return $t2
		return <item>{$n/name/text()}</item>
	return <person name="{$p/name/text()}">{$a}</person>
};

declare %unit:test
function xmark:q10(){
	let $auction := doc("xmlgen/auction.xml") return
for $i in
  distinct-values($auction/site/people/person/profile/interest/@category)
	let $p :=
	  for $t in $auction/site/people/person
	  where $t/profile/interest/@category = $i
	  return
		<personne>
		  <statistiques>
			<sexe>{$t/profile/gender/text()}</sexe>
			<age>{$t/profile/age/text()}</age>
			<education>{$t/profile/education/text()}</education>
			<revenu>{fn:data($t/profile/@income)}</revenu>
		  </statistiques>
		  <coordonnees>
			<nom>{$t/name/text()}</nom>
			<rue>{$t/address/street/text()}</rue>
			<ville>{$t/address/city/text()}</ville>
			<pays>{$t/address/country/text()}</pays>
			<reseau>
			  <courrier>{$t/emailaddress/text()}</courrier>
			  <pagePerso>{$t/homepage/text()}</pagePerso>
			</reseau>
		  </coordonnees>
		  <cartePaiement>{$t/creditcard/text()}</cartePaiement>
		</personne>
	return <categorie>{<id>{$i}</id>, $p}</categorie>
};

declare %unit:test
%unit:ignore("slow")
function xmark:q11(){
	let $auction := doc("xmlgen/auction.xml") return
	for $p in $auction/site/people/person
	let $l :=
	  for $i in $auction/site/open_auctions/open_auction/initial
	  where $p/profile/@income > 5000 * $i/text()
	  return $i
	return <items name="{$p/name/text()}">{count($l)}</items>
};

declare %unit:test
%unit:ignore("slow")
function xmark:q12(){
	let $auction := doc("xmlgen/auction.xml") return
	for $p in $auction/site/people/person
	let $l :=
	  for $i in $auction/site/open_auctions/open_auction/initial
	  where $p/profile/@income > 5000 * exactly-one($i/text())
	  return $i
	where $p/profile/@income > 50000
	return <items person="{$p/profile/@income}">{count($l)}</items>
};

declare %unit:test
function xmark:q13(){
	let $auction := doc("xmlgen/auction.xml") return
	for $i in $auction/site/regions/australia/item
	return <item name="{$i/name/text()}">{$i/description}</item>
};

declare %unit:test
function xmark:q14(){
	let $auction := doc("xmlgen/auction.xml") return
	for $i in $auction/site//item
	where contains(string(exactly-one($i/description)), "gold")
	return $i/name/text()
};

declare %unit:test
function xmark:q15(){
	let $auction := doc("xmlgen/auction.xml") return
	for $a in
	  $auction/site/closed_auctions/closed_auction/annotation/description/parlist/
	   listitem/
	   parlist/
	   listitem/
	   text/
	   emph/
	   keyword/
	   text()
	return <text>{$a}</text>
};

declare %unit:test
function xmark:q16(){
	let $auction := doc("xmlgen/auction.xml") return
	for $a in $auction/site/closed_auctions/closed_auction
	where
	  not(
		empty(
		  $a/annotation/description/parlist/listitem/parlist/listitem/text/emph/
		   keyword/
		   text()
		)
	  )
	return <person id="{$a/seller/@person}"/>
};

declare %unit:test
function xmark:q17(){
	let $auction := doc("xmlgen/auction.xml") return
	for $p in $auction/site/people/person
	where empty($p/homepage/text())
	return <person name="{$p/name/text()}"/>
};

declare function xmark:convert($v as xs:decimal?) as xs:decimal?
{
  2.20371 * $v (: convert Dfl to Euro :)
};

declare %unit:test
function xmark:q18(){
	let $auction := doc("xmlgen/auction.xml") return
	for $i in $auction/site/open_auctions/open_auction
	return xmark:convert(zero-or-one($i/reserve))
};

declare %unit:test
function xmark:q19(){
	let $auction := doc("xmlgen/auction.xml") return
	for $b in $auction/site/regions//item
	let $k := $b/name/text()
	order by zero-or-one($b/location) ascending empty greatest
	return <item name="{$k}">{$b/location/text()}</item>
};

declare %unit:test
function xmark:q20(){
	let $auction := doc("xmlgen/auction.xml") return
	<result>
	  <preferred>
		{count($auction/site/people/person/profile[@income >= 100000])}
	  </preferred>
	  <standard>
		{
		  count(
			$auction/site/people/person/
			 profile[@income < 100000 and @income >= 30000]
		  )
		}
	  </standard>
	  <challenge>
		{count($auction/site/people/person/profile[@income < 30000])}
	  </challenge>
	  <na>
		{
		  count(
			for $p in $auction/site/people/person
			where empty($p/profile/@income)
			return $p
		  )
		}
	  </na>
	</result>
};
