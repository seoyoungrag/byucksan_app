<?xml version="1.0" encoding="euc-kr"?>
<!-- .................................................................................................. -->
<!-- XHTML 1.0 Customized Module for pubdoc dtd ............................... -->
<!-- file: xhtml4pubdoc.mod

     This DTD module is identified by the PUBLIC and SYSTEM identifiers:

     PUBLIC "-//KORGOVERN//ENTITIES XHTML 1.0 for pubdoc//EN"
     SYSTEM "xhtml4pubdoc.mod"

     .................................................................................................... -->


<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!--                                    ��     ��    ��    ��                                        -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->

<!--  1. p�±��� style�Ӽ��� ��밡���� CSS��ɵ� -->
<!--
     1) �鿩���� : text-indent
     2) ���ܿ��� : margin-left (���ʿ���), margin-right (������ ����), margin-top (���� ����), margin-bottom (�Ʒ��� ����)
     3) ��Ʈ���� : font-family
     4) ����ũ�� : font-size
     5) �ణ��    : line-height (line-height�� ���Ǵ� W3C CSS level1�� ���� "������ �� ���� baseline������ �Ÿ�"��)
-->

<!--  2. CSS�� �� �±��� �Ӽ����� ����ϴ� ������ ������ ǥ�ù� -->
<!--
     => p�±��� style�� ����ϴ� CSS������  mm�� pt������ ����Ѵ�.
     => font-size�� pt�� ����ϰ�  �������� ��� mm�� ����Ѵ�.
     => table �� img�� ���� ������Ʈ�� �ִ� width �� height�Ӽ��� ��쵵 mm�� ����Ѵ�.
     => ����Ҷ��� �ش� ������ �Բ� ǥ���Ѵ�.

          (��) <p style="font-family:����ü;font-size:20pt;">
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

