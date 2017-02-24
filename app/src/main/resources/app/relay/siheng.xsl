<?xml version="1.0" encoding="euc-kr"?>

<!--=========================-->
<!--          시행문         -->
<!--=========================-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl">
	<xsl:template match="/">


	 <DIV STYLE="width:175mm">
	  <table width="100%" border="0" height="20">
	  	<tr>
	  		<td rowspan="2" width="10%"> 	  <!-- sendinfo / logo -->
			 <xsl:if test="pubdoc/foot/sendinfo/logo/img/@src[.!='']">
	 			<IMG>
		              <xsl:attribute name="src"><xsl:value-of select="pubdoc/foot/sendinfo/logo/img/@src"/></xsl:attribute>
		              <xsl:attribute name="alt"><xsl:value-of select="pubdoc/foot/sendinfo/logo/img/@alt"/></xsl:attribute>
		              <xsl:attribute name="style">width:<xsl:value-of select="pubdoc/foot/sendinfo/logo/img/@width"/>;height:
		              <xsl:value-of select="pubdoc/foot/sendinfo/logo/img/@height"/>;</xsl:attribute>
	             	</IMG>
			 </xsl:if>

	  		</td>
	  		<td width="80%"> 	  <!-- 머리 표제 -->
	   			<xsl:if test="pubdoc/foot/campaign/headcampaign[.!='']">
				<DIV STYLE="font-size:10pt; font-family:바탕체; width:100%; margin-bottom:2mm">
				<P ALIGN="CENTER"><xsl:value-of select="pubdoc/foot/campaign/headcampaign"/></P>
				</DIV>
	   			</xsl:if>
	   		</td>
	   <td rowspan="2" width="10%"> 	  <!-- symbol -->
			 <xsl:if test="pubdoc/foot/sendinfo/symbol/img/@src[.!='']">
	 			<IMG>
		              <xsl:attribute name="src"><xsl:value-of select="pubdoc/foot/sendinfo/symbol/img/@src"/></xsl:attribute>
		              <xsl:attribute name="alt"><xsl:value-of select="pubdoc/foot/sendinfo/symbol/img/@alt"/></xsl:attribute>
		              <xsl:attribute name="style">width:<xsl:value-of select="pubdoc/foot/sendinfo/symbol/img/@width"/>;height:
		              <xsl:value-of select="pubdoc/foot/sendinfo/symbol/img/@height"/>;</xsl:attribute>
	             	</IMG>
			 </xsl:if>
	   </td>
	   </tr>
	   <tr><td>
	   			<!-- 행정 기관명 -->
	   		<DIV STYLE="font-size:20pt; font-family:바탕체; width:100%; margin-bottom:2mm">
              <P ALIGN="CENTER"><xsl:value-of select="pubdoc/head/organ"/></P>
              </DIV>
	   	</td></tr>
	   </table>

	  <!-- 수신 -->
		<table width="100%">
		  <TR>
			<TD VALIGN="BOTTOM" align="left" WIDTH="60" STYLE="font-size:12pt; font-family:바탕체">수신자</TD>
			<TD VALIGN="BOTTOM" align="left" STYLE="font-size:12pt; font-family:바탕체">
				<xsl:choose>
					<xsl:when test="pubdoc/head/receiptinfo/recipient/@refer[.='false']">
						<xsl:value-of select="pubdoc/head/receiptinfo/recipient/rec"/>
					</xsl:when>
					<xsl:otherwise>
						수신자 참조
					</xsl:otherwise>
				</xsl:choose>
			</TD>
		  </TR>
		<!-- 경유(via) -->
		<xsl:if test="pubdoc/head/receiptinfo/via[.!='']">
		  <TR>
			<TD VALIGN="BOTTOM" align="left" WIDTH="60" STYLE="font-size:12pt; font-family:바탕체">(경유)</TD>
			<TD  VALIGN="BOTTOM" align="left" STYLE="font-size:12pt; font-family:바탕체">
				<xsl:value-of select="pubdoc/head/receiptinfo/via"/>
			</TD>
		  </TR>
		 </xsl:if>
		 <!-- 제목(title) -->
		  <TR>
			<TD VALIGN="BOTTOM" align="left" WIDTH="60" STYLE="font-size:12pt; font-family:바탕체">제<font color="white">--</font>목</TD>
			<TD  VALIGN="BOTTOM" align="left" STYLE="font-size:12pt; font-family:바탕체">
				<xsl:value-of select="pubdoc/body/title"/>
			</TD>
		  </TR>
	     </table>
		<HR width="100%" ></HR>
	<!-- 본문 -->
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr><td height="450" valign="top" >
			<xsl:if test="pubdoc/body/@separate[.='true']">
				<center><br/><p STYLE="font-size:12pt; font-family:바탕체">(본문 내용) 본문부 참조</p></center>
			</xsl:if>
			<xsl:if test="pubdoc/body/@separate[.='false']">
	      		<xsl:apply-templates select="pubdoc/body/content"/>
	      	</xsl:if>

       	</td></tr>
	</table>

	<table width="100%">
		<tr><td width="28%" align="left" valign="top"></td>
		<td width="44%" valign="center" align="center" height="40">
	      <!-- 발신명의 -->
			<DIV STYLE="font-size:20pt; font-family:바탕체;">
		        <xsl:value-of select="pubdoc/foot/sendername"/>
		     </DIV>
		</td>
		<td width="28%" align="left" valign="top">
		<!-- seal -->
			<xsl:if test="pubdoc/foot/seal/@omit[.='true']">
			 <DIV STYLE="font-size:12pt; font-family:바탕체; width:100%; margin-bottom:2mm" ALIGN="CENTER">
			   <P STYLE="border:1px solid black; padding:1pt 1pt 1pt 1pt;width:40mm" ALIGN="CENTER" VALIGN="MIDDLE">관 인  생 략</P>
			 </DIV>
			 </xsl:if>
			 <xsl:if test="pubdoc/foot/seal/@omit[.='false']">
	 				<IMG>
		              <xsl:attribute name="src"><xsl:value-of select="pubdoc/foot/seal/img/@src"/></xsl:attribute>
		              <xsl:attribute name="alt"><xsl:value-of select="pubdoc/foot/seal/img/@alt"/></xsl:attribute>
		              <xsl:attribute name="style">width:<xsl:value-of select="pubdoc/foot/seal/img/@width"/>;height:
		              <xsl:value-of select="pubdoc/foot/seal/img/@height"/>;</xsl:attribute>
	             	</IMG>
			 </xsl:if>
		</td></tr>
		<tr>
			<td VALIGN="middle" align="left" colspan="3" STYLE="font-size:12pt; font-family:바탕체">
				<xsl:if  test="pubdoc/head/receiptinfo/recipient/@refer[.='true']">
					수신자: <xsl:value-of select="pubdoc/head/receiptinfo/recipient/rec"/>
				</xsl:if>
			</td>
		</tr>
	</table>

	<TABLE width="100%">
		<TR>
			<TD colspan="2"><HR noshade="noshade" size="4"></HR></TD>
		</TR>
		<TR><td colspan="2">
		<!-- 결재 정보 -->
		<xsl:for-each select="pubdoc/foot/approvalinfo/approval">
			 <xsl:apply-templates />
				 <xsl:choose>
					<xsl:when test="@order[.='1']">
							<xsl:value-of select="signposition"/>
							<xsl:choose>
							 <xsl:when test="signimage/img/@src[.!='']">
								 <IMG>
									   <xsl:attribute name="src"><xsl:value-of select="signimage/img/@src"/></xsl:attribute>
						              <xsl:attribute name="alt"><xsl:value-of select="signimage/img/@alt"/></xsl:attribute>
						              <xsl:attribute name="style">width:<xsl:value-of select="signimage/img/@width"/>;height:
						              <xsl:value-of select="signimage/img/@height"/>;</xsl:attribute>
					             </IMG>
				             </xsl:when>
				            <xsl:otherwise>
				           		<xsl:value-of select="name"/>
							</xsl:otherwise>
						   </xsl:choose>
						   <font color="white">-</font>
					</xsl:when>

					<xsl:when test="@order[.='final']">
						<xsl:value-of select="signposition"/>
						<xsl:if test="type[.='대결']">
							<b><xsl:value-of select="type"/></b>
						</xsl:if>
						<xsl:if test="type[.='전결']">
							<b><xsl:value-of select="type"/></b>
						</xsl:if>
						<xsl:if test="@opinion[.='yes']">
						 	의견붙임
						 </xsl:if>
							<xsl:choose>
							 <xsl:when test="signimage/img/@src[.!='']">
								 <IMG>
									   <xsl:attribute name="src"><xsl:value-of select="signimage/img/@src"/></xsl:attribute>
						              <xsl:attribute name="alt"><xsl:value-of select="signimage/img/@alt"/></xsl:attribute>
						              <xsl:attribute name="style">width:<xsl:value-of select="signimage/img/@width"/>;height:
						              <xsl:value-of select="signimage/img/@height"/>;</xsl:attribute>
					             </IMG>
				             </xsl:when>
				            <xsl:otherwise>
				           		<xsl:value-of select="name"/>
							</xsl:otherwise>
						   </xsl:choose>
					</xsl:when>

					<xsl:when test="@order[.='4']">
						<xsl:value-of select="signposition"/>
						<xsl:if test="type[.='대결']">
							<b><xsl:value-of select="type"/></b>
						</xsl:if>
						<xsl:if test="type[.='전결']">
							<b><xsl:value-of select="type"/></b>
						</xsl:if>
						<xsl:if test="@opinion[.='yes']">
						 	의견붙임
						 </xsl:if>
							<xsl:choose>
							 <xsl:when test="signimage/img/@src[.!='']">
								 <IMG>
									   <xsl:attribute name="src"><xsl:value-of select="signimage/img/@src"/></xsl:attribute>
						              <xsl:attribute name="alt"><xsl:value-of select="signimage/img/@alt"/></xsl:attribute>
						              <xsl:attribute name="style">width:<xsl:value-of select="signimage/img/@width"/>;height:
						              <xsl:value-of select="signimage/img/@height"/>;</xsl:attribute>
					             </IMG>
				             </xsl:when>
				            <xsl:otherwise>
				           		<xsl:value-of select="name"/>
							</xsl:otherwise>
						   </xsl:choose>
						   <br/>
					</xsl:when>

					<xsl:when test="@order[.='8']">
						<xsl:value-of select="signposition"/>
						<xsl:if test="type[.='대결']">
							<b><xsl:value-of select="type"/></b>
						</xsl:if>
						<xsl:if test="type[.='전결']">
							<b><xsl:value-of select="type"/></b>
						</xsl:if>
						<xsl:if test="@opinion[.='yes']">
						 	의견붙임
						 </xsl:if>
							<xsl:choose>
							 <xsl:when test="signimage/img/@src[.!='']">
								 <IMG>
									   <xsl:attribute name="src"><xsl:value-of select="signimage/img/@src"/></xsl:attribute>
						              <xsl:attribute name="alt"><xsl:value-of select="signimage/img/@alt"/></xsl:attribute>
						              <xsl:attribute name="style">width:<xsl:value-of select="signimage/img/@width"/>;height:
						              <xsl:value-of select="signimage/img/@height"/>;</xsl:attribute>
					             </IMG>
				             </xsl:when>
				            <xsl:otherwise>
				           		<xsl:value-of select="name"/>
							</xsl:otherwise>
						   </xsl:choose>
						   <br/>
					</xsl:when>

					<xsl:otherwise>
						<xsl:value-of select="signposition"/>
						<xsl:if test="type[.='대결']">
							<b><xsl:value-of select="type"/></b>
						</xsl:if>
						<xsl:if test="type[.='전결']">
							<b><xsl:value-of select="type"/></b>
						</xsl:if>
						<xsl:if test="@opinion[.='yes']">
						 	의견붙임
						 </xsl:if>
							<xsl:choose>
							 <xsl:when test="signimage/img/@src[.!='']">
								 <IMG>
									   <xsl:attribute name="src"><xsl:value-of select="signimage/img/@src"/></xsl:attribute>
						              <xsl:attribute name="alt"><xsl:value-of select="signimage/img/@alt"/></xsl:attribute>
						              <xsl:attribute name="style">width:<xsl:value-of select="signimage/img/@width"/>;height:
						              <xsl:value-of select="signimage/img/@height"/>;</xsl:attribute>
					             </IMG>
				             </xsl:when>
				            <xsl:otherwise>
				           		<xsl:value-of select="name"/>
							</xsl:otherwise>
						   </xsl:choose>
						   <font color="white">-</font>
					</xsl:otherwise>

				 </xsl:choose>
			</xsl:for-each>

			</td></TR>
	<!--  결재 정보 끝 -->
		<TR>
			<TD colspan="2">협조자
				<xsl:for-each select="pubdoc/foot/approvalinfo/assist">
					<xsl:apply-templates />
							<xsl:value-of select="signposition"/>
							<xsl:choose>
							 <xsl:when test="signimage/img/@src[.!='']">
								 <IMG>
									   <xsl:attribute name="src"><xsl:value-of select="signimage/img/@src"/></xsl:attribute>
						              <xsl:attribute name="alt"><xsl:value-of select="signimage/img/@alt"/></xsl:attribute>
						              <xsl:attribute name="style">width:<xsl:value-of select="signimage/img/@width"/>;height:
						              <xsl:value-of select="signimage/img/@height"/>;</xsl:attribute>
					             </IMG>
				             </xsl:when>
				            <xsl:otherwise>
				           		<xsl:value-of select="name"/>
							</xsl:otherwise>
						   </xsl:choose>
						   <font color="white">-</font>
					</xsl:for-each>
				</TD>
		</TR>
		<TR>
			<TD>시행
				 <xsl:value-of select="pubdoc/foot/processinfo/regnumber"/>
				(<xsl:value-of select="pubdoc/foot/processinfo/enforcedate"/>)</TD>
			<TD>
				<!-- 접수정보 -->
				<xsl:if test="pubdoc/foot/processinfo/receipt[.!='']">
					접수 <xsl:value-of select="pubdoc/foot/processinfo/receipt/number"/>
					(<xsl:value-of select="pubdoc/foot/processinfo/receipt/date"/>)
				</xsl:if>

			</TD>
		</TR>
		<TR>
			<TD colspan="2">우
				<xsl:value-of select="pubdoc/foot/sendinfo/zipcode"/>
			 <xsl:value-of select="pubdoc/foot/sendinfo/address"/>
			/ <xsl:value-of select="pubdoc/foot/sendinfo/homeurl"/></TD>
		</TR>
		<TR>
			<TD colspan="2">전화 <xsl:value-of select="pubdoc/foot/sendinfo/telephone"/>
			<font color="white">--</font>전송 <xsl:value-of select="pubdoc/foot/sendinfo/fax"/>
			/ <xsl:value-of select="pubdoc/foot/sendinfo/email"/>
			/ <xsl:value-of select="pubdoc/foot/sendinfo/publication"/></TD>
		</TR>

	</TABLE>
	<BR/>

	<table width="100%"><tr><td>
		<!-- 꼬리 표제 -->
		   <xsl:if test="pubdoc/foot/campaign/footcampaign[.!='']">
				<DIV STYLE="font-size:10pt; font-family:바탕체; width:100%; margin-bottom:2mm">
					<P ALIGN="CENTER"><xsl:value-of select="pubdoc/foot/campaign/footcampaign"/></P>
				</DIV>
		   </xsl:if>
	</td></tr></table>
</DIV>
</xsl:template>



  <!-- pubdoc Body -->
  <xsl:template match="pubdoc/body/content">
     <DIV STYLE="padding-top:20px; margin-bottom:2em; color:black; width:100%;">
      <xsl:apply-templates/>
     </DIV>
  </xsl:template>

  <xsl:template match="p">
    <P>
      <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
      <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
      <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
      <xsl:attribute name="style">margin:0cm;<xsl:value-of select="./@style"/></xsl:attribute>
      <xsl:apply-templates/>
    </P>
  </xsl:template>

  <xsl:template match="b">
    <B>
      <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
      <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
      <xsl:apply-templates/>
    </B>
  </xsl:template>


  <xsl:template match="i">
    <I>
      <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
      <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
      <xsl:apply-templates/>
    </I>
  </xsl:template>

  <xsl:template match="u">
    <U>
      <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
      <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
      <xsl:apply-templates/>
    </U>
  </xsl:template>


  <xsl:template match="sub">
    <SUB>
      <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
      <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
      <xsl:apply-templates/>
    </SUB>
  </xsl:template>


  <xsl:template match="sup">
    <SUP>
      <xsl:apply-templates/>
    </SUP>
  </xsl:template>


  <xsl:template match="ol">
    <DIV STYLE="font-size:14pt; font-family:바탕체">
      <OL>
        <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
        <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
        <xsl:for-each select="li">
          <LI>
            <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
            <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
            <xsl:apply-templates/>
          </LI>
        </xsl:for-each>
      </OL>
    </DIV>
  </xsl:template>


  <xsl:template match="ul">
    <DIV STYLE="font-size:14pt; font-family:바탕체">
      <UL>
        <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
        <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
        <xsl:for-each select="li">
          <LI>
            <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
            <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
            <xsl:apply-templates/>
          </LI>
        </xsl:for-each>
      </UL>
    </DIV>
  </xsl:template>

  <!-- Image -->
  <xsl:template match="img">
    <DIV STYLE="margin-top:1em; margin-bottom:1em;">
        <IMG>
         <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
         <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
          <xsl:attribute name="src"><xsl:value-of select="./@src"/></xsl:attribute>
          <xsl:attribute name="alt"><xsl:value-of select="./@alt"/></xsl:attribute>
          <xsl:attribute name="name"><xsl:value-of select="./@name"/></xsl:attribute>
          <xsl:attribute name="longdesc"><xsl:value-of select="./@longdesc"/></xsl:attribute>
          <xsl:attribute name="style">width:<xsl:value-of select="./@width"/>;height:<xsl:value-of select="./@height"/>;</xsl:attribute>
          <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
          <xsl:attribute name="border"><xsl:value-of select="./@border"/></xsl:attribute>
          <xsl:attribute name="hspace"><xsl:value-of select="./@hspace"/></xsl:attribute>
          <xsl:attribute name="vspace"><xsl:value-of select="./@vspace"/></xsl:attribute>
        </IMG>
    </DIV>
  </xsl:template>

  <!-- HTML Table -->
  <xsl:template match="table">
    <TABLE>
      <xsl:attribute name="style">width:<xsl:value-of select="./@width"/>;height:<xsl:value-of select="./@height"/>;margin-top:1em; margin-bottom:1em; color:black; font-size:14pt; font-family:바탕체;</xsl:attribute>
      <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
      <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
      <xsl:attribute name="summary"><xsl:value-of select="./@summary"/></xsl:attribute>
      <xsl:attribute name="border"><xsl:value-of select="./@border"/></xsl:attribute>
      <xsl:attribute name="cellspacing"><xsl:value-of select="./@cellspacing"/></xsl:attribute>
      <xsl:attribute name="cellpadding"><xsl:value-of select="./@cellpadding"/></xsl:attribute>
      <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
      <xsl:for-each select="caption">
        <CAPTION>
          <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
          <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
          <xsl:apply-templates/>
        </CAPTION>
      </xsl:for-each>
      <xsl:for-each select="col">
        <COL>
          <xsl:attribute name="style">width:<xsl:value-of select="./@width"/>;</xsl:attribute>
          <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
          <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
          <xsl:attribute name="span"><xsl:value-of select="./@span"/></xsl:attribute>
          <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
          <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
          <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
          <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>
        </COL>
      </xsl:for-each>
      <xsl:for-each select="colgroup">
        <COLGROUP>
          <xsl:attribute name="style">width:<xsl:value-of select="./@width"/>;</xsl:attribute>
          <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
          <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
          <xsl:attribute name="span"><xsl:value-of select="./@span"/></xsl:attribute>
          <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
          <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
          <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
          <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>
        </COLGROUP>
      </xsl:for-each>
      <xsl:for-each select="thead">
        <THEAD>
          <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
          <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
          <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
          <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
          <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
          <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>
          <xsl:for-each select="tr">
            <TR>
              <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
              <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
              <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
              <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
              <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
              <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>

              <xsl:apply-templates/>

            </TR>
          </xsl:for-each>
        </THEAD>
      </xsl:for-each>
      <xsl:for-each select="tfoot">
        <TFOOT>
          <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
          <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
          <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
          <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
          <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
          <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>
          <xsl:for-each select="tr">
            <TR>
              <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
              <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
              <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
              <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
              <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
              <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>

              <xsl:apply-templates/>

            </TR>
          </xsl:for-each>
        </TFOOT>
      </xsl:for-each>
      <xsl:for-each select="tbody">
        <TBODY>
          <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
          <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
          <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
          <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
          <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
          <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>
          <xsl:for-each select="tr">
            <TR>
              <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
              <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
              <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
              <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
              <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
              <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>

              <xsl:apply-templates/>

            </TR>
          </xsl:for-each>
        </TBODY>
      </xsl:for-each>
    </TABLE>
  </xsl:template>

  <xsl:template match="td">
    <TD>
      <xsl:attribute name="style">width:<xsl:value-of select="./@width"/>;height:<xsl:value-of select="./@height"/>;padding:0cm;</xsl:attribute>
      <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
      <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
      <xsl:attribute name="abbr"><xsl:value-of select="./@abbr"/></xsl:attribute>
      <xsl:attribute name="axis"><xsl:value-of select="./@axis"/></xsl:attribute>
      <xsl:attribute name="headers"><xsl:value-of select="./@headers"/></xsl:attribute>
      <xsl:attribute name="scope"><xsl:value-of select="./@scope"/></xsl:attribute>
      <xsl:attribute name="rowspan"><xsl:value-of select="./@rowspan"/></xsl:attribute>
      <xsl:attribute name="colspan"><xsl:value-of select="./@colspan"/></xsl:attribute>
      <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
      <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
      <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
      <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>
      <xsl:attribute name="nowrap"><xsl:value-of select="./@nowrap"/></xsl:attribute>
      <xsl:apply-templates/>
    </TD>
  </xsl:template>
  <xsl:template match="th">
    <TH>
      <xsl:attribute name="style">width:<xsl:value-of select="./@width"/>;height:<xsl:value-of select="./@height"/>;</xsl:attribute>
      <xsl:attribute name="id"><xsl:value-of select="./@id"/></xsl:attribute>
      <xsl:attribute name="class"><xsl:value-of select="./@class"/></xsl:attribute>
      <xsl:attribute name="abbr"><xsl:value-of select="./@abbr"/></xsl:attribute>
      <xsl:attribute name="axis"><xsl:value-of select="./@axis"/></xsl:attribute>
      <xsl:attribute name="headers"><xsl:value-of select="./@headers"/></xsl:attribute>
      <xsl:attribute name="scope"><xsl:value-of select="./@scope"/></xsl:attribute>
      <xsl:attribute name="rowspan"><xsl:value-of select="./@rowspan"/></xsl:attribute>
      <xsl:attribute name="colspan"><xsl:value-of select="./@colspan"/></xsl:attribute>
      <xsl:attribute name="align"><xsl:value-of select="./@align"/></xsl:attribute>
      <xsl:attribute name="char"><xsl:value-of select="./@char"/></xsl:attribute>
      <xsl:attribute name="charoff"><xsl:value-of select="./@charoff"/></xsl:attribute>
      <xsl:attribute name="valign"><xsl:value-of select="./@valign"/></xsl:attribute>
      <xsl:attribute name="nowrap"><xsl:value-of select="./@nowrap"/></xsl:attribute>
      <xsl:apply-templates/>
    </TH>
  </xsl:template>

 <xsl:template match="text()"><xsl:value-of /></xsl:template>

</xsl:stylesheet>
