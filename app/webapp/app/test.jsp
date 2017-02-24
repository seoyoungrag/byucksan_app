<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/app/jsp/common/header.jsp"%>

<script>
function setCompleteList(data){
	
	alert(data.ListVos.length);
	alert(data.ListVos[0].docid);
}



function getCompleteList() {

	 $.ajax({
			type:'post',
			timeout: 5000,
			async:false,
			dataType:'json',
			url:'/ep/app/list/webService/approval/ListApprovalCompleteBox.do?D1=b20e6e621ca92f8f4146d52b453cc66f3acec9affa7610a4997c75d5a75e17a1d3bc70b954cda3810760974a32d80da01d6605a80d4a5dff0ca54fce9f08c790b15f74d27d10a1be671ae1adb3aaa9e4839ee71c0505314b1e7ac19db3b533ec2181c48e14c6c82a536317c08b083a8e7d4a913d23904cad188e028e9e145224c0576981185b9ce7733277830a6fa5503b54e2264f04e759f1d836d1f10bad3be490b09f1dce7b758d2dc9f76632270ec3d161d50692e3461e7ac19db3b533ec8cdcbb61188a64b069182272e8ffe6e21237e2f14bb47996ba1c9edc2762cccd5cb2601dc5bffa89893ea7a0eddbf382b759f28f54db07d8671bf9391f77bd42ff35b8385a5a7ad270249cf515c5c39f41304fb40bb0513f41b76c4c82ddcaa1893ea7a0eddbf382f9eebf37180723734d4969250e4213db1444c7c314823a89d931e6c0e2b89147b617cf33842ec27cc86abf83f8e4a36718ee57aefdf34e1f02967bf0675b08c11ce1b03e1687cae07f655f57375de8f0df6423d72438d44e83c3ece1d78021b9b822195129264f13f9916f8b18b073ebc3f3fa5ebb692824b2dfc6f7012ef34b3b094e73d5050864a54a931a6ae2342548fadf2e3694d104b759f28f54db07d829c5f35e41a98b3b1d6c9459233922def0e47a4c88d70d33b0d56916ffdf64887615e2f67ba7d4aff1e966690a719cb23b44192799eee067',
			success:function(data) {
				if(data.webServiceStatus == 200){
					setCompleteList(data);
				}
			},
			error:function(data,sataus,err) {						
					alert("로그인 실패 했습니다.");
			}
		}); 
}
</script>
<script type="text/javascript" src="<%=webUri%>/app/ref/js/jquery.js"></script>
<body onload="getCompleteList()">
</body>