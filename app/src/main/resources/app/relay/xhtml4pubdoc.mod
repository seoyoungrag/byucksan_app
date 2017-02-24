<?xml version="1.0" encoding="euc-kr"?>
<!-- .................................................................................................. -->
<!-- XHTML 1.0 Customized Module for pubdoc dtd ............................... -->
<!-- file: xhtml4pubdoc.mod

     This DTD module is identified by the PUBLIC and SYSTEM identifiers:

     PUBLIC "-//KORGOVERN//ENTITIES XHTML 1.0 for pubdoc//EN"
     SYSTEM "xhtml4pubdoc.mod"

     .................................................................................................... -->


<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!--                                    참     고    사    항                                        -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->

<!--  1. p태그의 style속성에 사용가능한 CSS명령들 -->
<!--
     1) 들여쓰기 : text-indent
     2) 문단여백 : margin-left (왼쪽여백), margin-right (오른쪽 여백), margin-top (위쪽 여백), margin-bottom (아래쪽 여백)
     3) 폰트종류 : font-family
     4) 글자크기 : font-size
     5) 행간격    : line-height (line-height의 정의는 W3C CSS level1에 따라 "인접한 두 행의 baseline사이의 거리"임)
-->

<!--  2. CSS및 각 태그의 속성에서 사용하는 단위의 범위와 표시법 -->
<!--
     => p태그의 style에 기술하는 CSS문에는  mm및 pt단위를 사용한다.
     => font-size는 pt를 사용하고  나머지는 모두 mm를 사용한다.
     => table 및 img등 각종 엘리먼트에 있는 width 및 height속성의 경우도 mm를 사용한다.
     => 기술할때에 해당 단위도 함께 표시한다.

          (예) <p style="font-family:바탕체;font-size:20pt;">
	  <p style="text-indent:20mm;line-height:6mm;">
	  <p style="margin-left:10mm;margin-right:10mm;">
	  <td width="20mm" height="10mm">
-->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->


<!ENTITY % HTMLlat1 PUBLIC
   "-//W3C//ENTITIES Latin 1 for XHTML//EN"
   "xhtml-lat1.ent">
%HTMLlat1;

<!ENTITY % HTMLsymbol PUBLIC
   "-//W3C//ENTITIES Symbols for XHTML//EN"
   "xhtml-symbol.ent">
%HTMLsymbol;

<!ENTITY % HTMLspecial PUBLIC
   "-//W3C//ENTITIES Special for XHTML//EN"
   "xhtml-special.ent">
%HTMLspecial;


<!ENTITY % coreattrs
 "id         ID             #IMPLIED
  class    CDATA     #IMPLIED"
  >

<!ENTITY % lists    "ul | ol">

<!ENTITY % inline  "#PCDATA | a | img | i | b | u | sub | sup">

<!ENTITY % block  "p | %lists; | table ">

<!ENTITY % flow    "%inline; | %block;">

<!ELEMENT sub   (%inline;)*>
<!ATTLIST sub      %coreattrs;>

<!ELEMENT sup   (%inline;)*>
<!ATTLIST sup     %coreattrs;>

<!ELEMENT i       (%inline;)*>
<!ATTLIST i         %coreattrs;>

<!ELEMENT b      (%inline;)*>
<!ATTLIST b        %coreattrs;>

<!ELEMENT u      (%inline;)*>
<!ATTLIST u        %coreattrs;>

<!ELEMENT p       (%flow;)*>
<!ATTLIST p
  %coreattrs;
  style     CDATA     #IMPLIED
  align (left|center|right|adjust) #IMPLIED
  >


<!--=================== Lists ============================================-->

<!-- Unordered list -->

<!ELEMENT ul (li)+>
<!ATTLIST ul
  %coreattrs;
  >

<!-- Ordered (numbered) list -->

<!ELEMENT ol (li)+>
<!ATTLIST ol
  %coreattrs;
  >

<!-- list item -->

<!ELEMENT li (%flow;)*>
<!ATTLIST li
  %coreattrs;
  >

<!--================== The Anchor Element ================================-->

<!ELEMENT a (#PCDATA | img | i | b | u | sub | sup)*>
<!ATTLIST a
  %coreattrs;
  name     CDATA            #IMPLIED
  href        CDATA            #IMPLIED
  rel          CDATA            #IMPLIED
  rev         CDATA            #IMPLIED
  >

<!--=================== Images ===========================================-->

<!ELEMENT img EMPTY>
<!ATTLIST img
  %coreattrs;
  src            CDATA       #REQUIRED
  alt             CDATA       #REQUIRED
  name        CDATA        #IMPLIED
  longdesc   CDATA        #IMPLIED
  height        CDATA       #IMPLIED
  width         CDATA       #IMPLIED
  align         (top|middle|bottom|left|right)     #IMPLIED
  border       CDATA       #IMPLIED
  hspace      CDATA       #IMPLIED
  vspace      CDATA       #IMPLIED
  >


<!--======================= Tables =======================================-->

<!ENTITY % cellhalign
  "align      (left|center|right|justify|char) #IMPLIED
   char       CDATA                                #IMPLIED
   charoff    CDATA                                #IMPLIED"
   >

<!ENTITY % cellvalign
  "valign     (top|middle|bottom|baseline) #IMPLIED"
  >

<!ELEMENT table       (caption?, (col*|colgroup*), thead?, tfoot?, (tbody+|tr+))>
<!ELEMENT caption    (%inline;)*>
<!ELEMENT thead      (tr)+>
<!ELEMENT tfoot        (tr)+>
<!ELEMENT tbody      (tr)+>
<!ELEMENT colgroup  (col)*>
<!ELEMENT col          EMPTY>
<!ELEMENT tr            (th|td)+>
<!ELEMENT th           (%flow;)*>
<!ELEMENT td           (%flow;)*>

<!ATTLIST table
  %coreattrs;
  summary     CDATA                #IMPLIED
  width           CDATA                #IMPLIED
  height          CDATA                #IMPLIED
  border         CDATA                 #IMPLIED
  cellspacing  CDATA                 #IMPLIED
  cellpadding  CDATA                 #IMPLIED
  align           (left|center|right|adjust)   #IMPLIED
  >

<!ATTLIST caption
  %coreattrs;
  align           (top|bottom|left|right)       #IMPLIED
  >

<!ATTLIST colgroup
  %coreattrs;
  span           CDATA       "1"
  width           CDATA      #IMPLIED
  %cellhalign;
  %cellvalign;
  >

<!ATTLIST col
  %coreattrs;
  span           CDATA       "1"
  width           CDATA       #IMPLIED
  %cellhalign;
  %cellvalign;
  >

<!ATTLIST thead
  %coreattrs;
  %cellhalign;
  %cellvalign;
  >

<!ATTLIST tfoot
  %coreattrs;
  %cellhalign;
  %cellvalign;
  >

<!ATTLIST tbody
  %coreattrs;
  %cellhalign;
  %cellvalign;
  >

<!ATTLIST tr
  %coreattrs;
  %cellhalign;
  %cellvalign;
  >

<!ATTLIST th
  %coreattrs;
  abbr          CDATA                                  #IMPLIED
  axis          CDATA                                  #IMPLIED
  headers     IDREFS                                  #IMPLIED
  scope       (row|col|rowgroup|colgroup)   #IMPLIED
  rowspan    CDATA                                  "1"
  colspan     CDATA                                  "1"
  %cellhalign;
  %cellvalign;
  nowrap      (nowrap)                               #IMPLIED
  width         CDATA                                  #IMPLIED
  height        CDATA                                  #IMPLIED
  >

<!ATTLIST td
  %coreattrs;
  abbr           CDATA                                 #IMPLIED
  axis           CDATA                                  #IMPLIED
  headers      IDREFS                                 #IMPLIED
  scope        (row|col|rowgroup|colgroup)   #IMPLIED
  rowspan     CDATA                                  "1"
  colspan      CDATA                                  "1"
  %cellhalign;
  %cellvalign;
  nowrap       (nowrap)                               #IMPLIED
  width          CDATA                                  #IMPLIED
  height         CDATA                                  #IMPLIED
  >

