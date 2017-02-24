function getAppCode(appcode) {

	var returnText = "";
	
	if(appcode == "APP010"){
		returnText = "재기안완료";
	}
	if(appcode == "APP100"){
		returnText = "기안대기";
	}
	if(appcode == "APP110"){
		returnText = "반려";
	}
	if(appcode == "APP150"){
		returnText = "기안대기";
	}
	if(appcode == "APP160"){
		returnText = "기안대기-연계";
	}
	if(appcode == "APP200"){
		returnText = "검토대기";
	}
	if(appcode == "APP201"){
		returnText = "검토대기";
	}
	if(appcode == "APP250"){
		returnText = "검토대기";
	}
	if(appcode == "APP300"){
		returnText = "협조대기";
	}
	if(appcode == "APP301"){
		returnText = "부서협조대기";
	}
	if(appcode == "APP302"){
		returnText = "부서협조대기";
	}
	if(appcode == "APP305"){
		returnText = "부서협조대기";
	}
	if(appcode == "APP350"){
		returnText = "협조대기";
	}
	if(appcode == "APP351"){
		returnText = "부서협조대기";
	}
	if(appcode == "APP360"){
		returnText = "합의대기";
	}
	if(appcode == "APP361"){
		returnText = "부서합의대기";
	}
	if(appcode == "APP362"){
		returnText = "부서합의대기";
	}
	if(appcode == "APP365"){
		returnText = "부서합의대기";
	}
	if(appcode == "APP370"){
		returnText = "합의대기";
	}
	if(appcode == "APP371"){
		returnText = "부서합의대기";
	}	
	if(appcode == "APP400"){
		returnText = "감사대기";
	}
	if(appcode == "APP401"){
		returnText = "부서감사대기";
	}
	if(appcode == "APP402"){
		returnText = "부서감사대기";
	}
	if(appcode == "APP405"){
		returnText = "부서감사대기";
	}
	if(appcode == "APP500"){
		returnText = "결재대기";
	}
	if(appcode == "APP550"){
		returnText = "결재대기";
	}
	if(appcode == "APP600"){
		returnText = "결재완료";
	}
	if(appcode == "APP610"){
		returnText = "발송대기";
	}
	if(appcode == "APP611"){
		returnText = "반려후 대장등록완료";
	}
	if(appcode == "APP615"){
		returnText = "심사반려";
	}
	if(appcode == "APP620"){
		returnText = "심사대기";
	}
	if(appcode == "APP625"){
		returnText = "심사대기";
	}
	if(appcode == "APP630"){
		returnText = "발송완료";
	}
	if(appcode == "APP640"){
		returnText = "발송보류문서";
	}
	if(appcode == "APP650"){
		returnText = "발송대기";
	}
	if(appcode == "APP660"){
		returnText = "발송대기";
	}
	if(appcode == "APP670"){
		returnText = "부분발송";
	}
	if(appcode == "APP680"){
		returnText = "발송문서";
	}
	if(appcode == "APP360"){
		returnText = "합의대기";
	}
	if(appcode == "APP361"){
		returnText = "부서합의대기";
	}
	if(appcode == "APP362"){
		returnText = "부서합의대기";
	}
	if(appcode == "APP365"){
		returnText = "부서합의대기";
	}
	if(appcode == "APP370"){
		returnText = "합의대기";
	}
	if(appcode == "APP371"){
		returnText = "부서합의대기";
	}
	if(appcode == "APP400"){
		returnText = "감사대기";
	}
	if(appcode == "APP401"){
		returnText = "부서감사대기";
	}
	if(appcode == "APP402"){
		returnText = "부서감사대기";
	}
	if(appcode == "APP405"){
		returnText = "부서감사대기";
	}
	if(appcode == "APP500"){
		returnText = "결재대기";
	}
	if(appcode == "APP550"){
		returnText = "결재대기";
	}	
	if(appcode == "APP600"){
		returnText = "결재완료";
	}
	if(appcode == "APP610"){
		returnText = "발송대기";
	}
	if(appcode == "APP611"){
		returnText = "반려후 대장등록완료";
	}
	if(appcode == "APP615"){
		returnText = "심사반려";
	}
	if(appcode == "APP620"){
		returnText = "심사대기";
	}
	if(appcode == "APP625"){
		returnText = "심사대기";
	}	
	if(appcode == "APP630"){
		returnText = "발송완료";
	}
	if(appcode == "APP640"){
		returnText = "발송보류문서";
	}
	if(appcode == "APP650"){
		returnText = "발송대기";
	}
	if(appcode == "APP660"){
		returnText = "발송대기";
	}
	if(appcode == "APP670"){
		returnText = "부분발송";
	}
	if(appcode == "APP680"){
		returnText = "발송문서";
	}
	if(appcode == "ENF100"){
		returnText = "배부대기";
	}
	if(appcode == "ENF110"){
		returnText = "재배부요청";
	}
	if(appcode == "ENF120"){
		returnText = "배부안함";
	}
	if(appcode == "ENF200"){
		returnText = "접수대기";
	}
	if(appcode == "ENF250"){
		returnText = "접수대기";
	}
	if(appcode == "ENF300"){
		returnText = "선람및담당지정대기";
	}
	if(appcode == "ENF310"){
		returnText = "선람및담당지정대기";
	}
	if(appcode == "ENF400"){
		returnText = "선람대기";
	}
	if(appcode == "ENF500"){
		returnText = "담당대기";
	}
	if(appcode == "ENF600"){
		returnText = "완료문서";
	}
	if(appcode == "ENF610"){
		returnText = "이송중인문서";
	}
	if(appcode == "ENF620"){
		returnText = "이송완료된문서";
	}
	if(appcode == "ENF630"){
		returnText = "경유중인문서";
	}
	if(appcode == "ENF640"){
		returnText = "경유완료된문서";
	}
	if(appcode == "ECT001"){
		returnText = "발송";
	}
	if(appcode == "ECT002"){
		returnText = "배부";
	}
	if(appcode == "ECT003"){
		returnText = "접수";
	}
	if(appcode == "ECT004"){
		returnText = "이송";
	}
	if(appcode == "ECT005"){
		returnText = "선람";
	}
	if(appcode == "ECT006"){
		returnText = "담당";
	}
	if(appcode == "ECT007"){
		returnText = "반송";
	}
	if(appcode == "ECT008"){
		returnText = "발송보류";
	}
	if(appcode == "ECT009"){
		returnText = "발송회수";
	}
	if(appcode == "ECT010"){
		returnText = "재발송요청";
	}
	if(appcode == "ECT011"){
		returnText = "발송불가";
	}
	if(appcode == "ECT012"){
		returnText = "발송종료";
	}
	if(appcode == "ECT100"){
		returnText = "도착";
	}
	if(appcode == "ECT110"){
		returnText = "수신";
	}
	if(appcode == "ECT120"){
		returnText = "접수";
	}
	if(appcode == "ECT130"){
		returnText = "오류";
	}
	if(appcode == "ECT140"){
		returnText = "재발신요청";
	}
	if(appcode == "ART010"){
		returnText = "기안(상신)";
	}
	if(appcode == "ART020"){
		returnText = "검토";
	}
	if(appcode == "ART021"){
		returnText = "검토";
	}
	if(appcode == "ART030"){
		returnText = "협조";
	}
	if(appcode == "ART031"){
		returnText = "병렬협조";
	}
	if(appcode == "ART032"){
		returnText = "부서협조";
	}
	if(appcode == "ART033"){
		returnText = "협조(기안)";
	}
	if(appcode == "ART034"){
		returnText = "협조(검토)";
	}
	if(appcode == "ART035"){
		returnText = "협조(결재)";
	}
	if(appcode == "ART130"){
		returnText = "합의";
	}
	if(appcode == "ART131"){
		returnText = "병렬합의";
	}
	if(appcode == "ART132"){
		returnText = "부서합의";
	}
	if(appcode == "ART133"){
		returnText = "합의(기안)";
	}
	if(appcode == "ART134"){
		returnText = "합의(검토)";
	}
	if(appcode == "ART135"){
		returnText = "합의(결재)";
	}
	if(appcode == "ART040"){
		returnText = "감사";
	}
	if(appcode == "ART041"){
		returnText = "부서감사";
	}
	if(appcode == "ART042"){
		returnText = "감사(기안)";
	}
	if(appcode == "ART043"){
		returnText = "감사(검토)";
	}
	if(appcode == "ART044"){
		returnText = "감사(결재)";
	}
	if(appcode == "ART045"){
		returnText = "일상감사";
	}
	if(appcode == "ART046"){
		returnText = "준법감시";
	}
	if(appcode == "ART047"){
		returnText = "감사위원";
	}
	if(appcode == "ART050"){
		returnText = "결재";
	}
	if(appcode == "ART051"){
		returnText = "전결";
	}
	if(appcode == "ART052"){
		returnText = "대결";
	}
	if(appcode == "ART053"){
		returnText = "1인전결";
	}
	if(appcode == "ART054"){
		returnText = "후열";
	}
	if(appcode == "ART055"){
		returnText = "통보";
	}
	if(appcode == "ART060"){
		returnText = "선람";
	}
	if(appcode == "ART070"){
		returnText = "담당";
	}
	if(appcode == "APT001"){
		returnText = "승인";
	}
	if(appcode == "APT002"){
		returnText = "반려";
	}
	if(appcode == "APT003"){
		returnText = "대기";
	}
	if(appcode == "APT004"){
		returnText = "보류중";
	}
	if(appcode == "APT005"){
		returnText = "접수";
	}
	if(appcode == "APT006"){
		returnText = "이송";
	}
	if(appcode == "APT007"){
		returnText = "반송";
	}
	if(appcode == "APT008"){
		returnText = "심사요청";
	}
	if(appcode == "APT009"){
		returnText = "발송";
	}
	if(appcode == "APT010"){
		returnText = "자동발송";
	}
	if(appcode == "APT011"){
		returnText = "발송보류";
	}
	if(appcode == "APT012"){
		returnText = "배부";
	}
	if(appcode == "APT013"){
		returnText = "재배부요청";
	}
	if(appcode == "APT014"){
		returnText = "부재미처리";
	}
	if(appcode == "APT015"){
		returnText = "심사반려";
	}
	if(appcode == "APT016"){
		returnText = "발송종료";
	}
	if(appcode == "APT017"){
		returnText = "담당자재지정요청";
	}
	if(appcode == "APT018"){
		returnText = "배부안함";
	}
	if(appcode == "APT019"){
		returnText = "배부회수";
	}
	if(appcode == "APT020"){
		returnText = "재발송요청";
	}
	if(appcode == "APT051"){
		returnText = "찬성";
	}
	if(appcode == "APT052"){
		returnText = "반대";
	}
	if(appcode == "APT099"){
		returnText = "행정기관 : 발송실패";
	}
	if(appcode == "APT100"){
		returnText = "행정기관 : 재발송성공";
	}
	if(appcode == "DET001"){
		returnText = "내부";
	}
	if(appcode == "DET002"){
		returnText = "대내";
	}
	if(appcode == "DET003"){
		returnText = "대외";
	}
	if(appcode == "DET004"){
		returnText = "대내외";
	}
	if(appcode == "DET005"){
		returnText = "외부 행정기관";
	}
	if(appcode == "DET006"){
		returnText = "외부 민간기관";
	}
	if(appcode == "DET007"){
		returnText = "민원인";
	}
	if(appcode == "DET011"){
		returnText = "행정기관";
	}
	if(appcode == "DRS001"){
		returnText = "보고경로";
	}
	if(appcode == "DRS002"){
		returnText = "부서";
	}
	if(appcode == "DRS003"){
		returnText = "본부";
	}
	if(appcode == "DRS004"){
		returnText = "기관";
	}
	if(appcode == "DRS005"){
		returnText = "회사";
	}


return returnText;

}

