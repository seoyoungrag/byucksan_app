var ce_acN={ce_bh:null,ce_cc:null,ce_bS:null,ce_bO:null,ce_iZ:null,ce_bo:null,ce_fF:null,ce_oS:null,ce_br:null,ce_lj:10000,ce_pT:10,ce_nl:0,ce_MS:"\x62\x61\x73\x65\x6c\x69\x6e\x65",ce_aoB:"\x4e\x61\x6d\x6f\x20\x50\x68\x6f\x74\x6f\x45\x64\x69\x74\x6f\x72",ce_ef: -1,ce_Qy:null,ce_Ht:null,ce_Ho:0,start:function(){t=this;t.ce_bS=t.ce_bh.pCmd;t.ce_bO=t.ce_bh.pBtn;t.ce_iZ=null;t.ce_bo=null;t.ce_oS=null;t.ce_br=null;ce_xE=null;t.ce_ef= -1;t.ce_Qy=960;t.ce_Ht=600;t.ce_Ho=ce_ab().split('\x2c').shift();ce_bj=t.ce_bh.getSelection();var sel=ce_bj.sel=ce_bj.getSelection();var range=ce_bj.range=ce_bj.ce_bG();if(t.ce_fF==null){if(ce_bj.ce_dn()=="\x43\x6f\x6e\x74\x72\x6f\x6c"){ce_bn=ce_bj.ce_eS();if(ce_bn==null)return;t.ce_iZ=ce_bn;if(ce_bn.tagName.toLowerCase()=="\x69\x6d\x67"){t.ce_bo=ce_bn;}}}else{if(ce_bj.ce_BG(t.ce_fF,"\x69\x6d\x67")){t.ce_bo=t.ce_fF;}}if(ce_bi.IsIE&&ce_bj.ce_dn()=="\x43\x6f\x6e\x74\x72\x6f\x6c"){ce_bj.ce_gN(t.ce_iZ);range=ce_bj.range=ce_bj.ce_bG();}var ce_od=this.ce_do();if(!ce_od)return null;if(t.ce_Ho>0&&t.ce_Ho>=10)NamoSE.Util.ce_bv(function(){t.ce_amz();},10);return ce_od;},ce_amz:function(){var t=this;var ce_acm=this.ce_bh.baseURL+t.ce_bh.config.ce_anU+"\x4e\x61\x6d\x6f\x50\x68\x6f\x74\x6f\x45\x64\x69\x74\x6f\x72\x2f\x50\x68\x6f\x74\x6f\x45\x64\x69\x74\x6f\x72\x2e\x73\x77\x66";var maxImageNum=t.ce_bh.config.ce_Dd;var maxImageWidth=0;if(t.ce_bh.params.ImageWidthLimit&&String(t.ce_bh.params.ImageWidthLimit)!=""){var ce_rP=parseInt(t.ce_bh.params.ImageWidthLimit);if(!isNaN(ce_rP)&&ce_rP>0)maxImageWidth=ce_rP;}var maxImageBytes=t.ce_bh.ce_MQ().image;var checkImageTitle=(["\x65\x6e\x61\x62\x6c\x65","\x73\x74\x72\x69\x63\x74"].ce_bm(t.ce_bh.ce_xJ()))?"\x74\x72\x75\x65":"\x66\x61\x6c\x73\x65";var mode=(t.ce_bo==null)?"\x6e\x65\x77":"\x65\x64\x69\x74\x5f\x69\x6d\x61\x67\x65";var locale="\x65\x6e\x75";switch(t.ce_bh.ce_cg){case "\x6b\x6f":locale="\x6b\x6f\x72";break;case "\x65\x6e":locale="\x65\x6e\x75";break;case "\x6a\x61":locale="\x6a\x70\x6e";break;case "\x7a\x68\x2d\x63\x6e":locale="\x63\x68\x73";break;case "\x7a\x68\x2d\x74\x77":locale="\x63\x68\x74";break;default:locale="\x65\x6e\x75";break;}var uploadURL=escape(t.ce_bh.ce_qn("\x49\x6d\x61\x67\x65\x55\x70\x6c\x6f\x61\x64"));var imageUPath=(t.ce_bh.params.ImageSavePath==null)?"":t.ce_bh.params.ImageSavePath;var defaultUPath=t.ce_bh.baseURL+t.ce_bh.config.ce_Du;var imageKind="\x69\x6d\x61\x67\x65";var ce_hL=t.ce_bh.ce_Cn();var imageUNameType=ce_hL.ce_Cr;var imageUNameEncode=ce_hL.ce_Cp;var imageViewerPlay=(t.ce_bh.params.UploadFileViewer==null)?"\x66\x61\x6c\x73\x65":t.ce_bh.params.UploadFileViewer;var imageSizeLimit=maxImageBytes;var editorFrame=t.ce_bh.editorFrame.id;var ce_hx=uploadURL;var ce_jB=t.ce_bh.params.WebLanguage.toLowerCase();var imageDomain=(t.ce_bh.params.UserDomain&&t.ce_bh.params.UserDomain.Trim()!="")?t.ce_bh.params.UserDomain:"";var uploadFileSubDir=(t.ce_bh.params.UploadFileSubDir==null)?"\x74\x72\x75\x65":t.ce_bh.params.UploadFileSubDir;var ce_YQ=checkImageTitle;var ce_Vl=maxImageWidth;if(t.ce_bh.params.UploadFileExecutePath&&t.ce_bh.params.UploadFileExecutePath.indexOf(t.ce_bh.ce_AH)!=0){if(t.ce_bh.params.UploadFileExecutePath.indexOf("\x2e")!= -1){var ce_mN=t.ce_bh.params.UploadFileExecutePath.substring(t.ce_bh.params.UploadFileExecutePath.lastIndexOf("\x2e")+1);if(["\x61\x73\x70","\x6a\x73\x70","\x61\x73\x70\x78","\x70\x68\x70"].ce_bm(ce_mN.toLowerCase()))ce_jB=ce_mN.toLowerCase();}}var ce_jT="";if(['\x6a\x73\x70','\x73\x65\x72\x76\x6c\x65\x74'].ce_bm(ce_jB)){var ce_hY=t.ce_bh.ce_uL();ce_jT=ce_hY+"\x69\x6d\x61\x67\x65\x45\x64\x69\x74\x6f\x72\x46\x6c\x61\x67\x3d\x66\x6c\x61\x73\x68\x50\x68\x6f\x74\x6f\x26\x69\x6d\x61\x67\x65\x53\x69\x7a\x65\x4c\x69\x6d\x69\x74\x3d"+imageSizeLimit+"\x26\x69\x6d\x61\x67\x65\x55\x50\x61\x74\x68\x3d"+imageUPath+"\x26\x64\x65\x66\x61\x75\x6c\x74\x55\x50\x61\x74\x68\x3d"+defaultUPath+"\x26\x69\x6d\x61\x67\x65\x56\x69\x65\x77\x65\x72\x50\x6c\x61\x79\x3d"+imageViewerPlay+"\x26\x69\x6d\x61\x67\x65\x44\x6f\x6d\x61\x69\x6e\x3d"+imageDomain+"\x26\x75\x70\x6c\x6f\x61\x64\x46\x69\x6c\x65\x53\x75\x62\x44\x69\x72\x3d"+uploadFileSubDir;}ce_hx=ce_hx+escape(ce_jT);var ce_OZ="\x6d\x61\x78\x49\x6d\x61\x67\x65\x4e\x75\x6d\x3d"+maxImageNum+"\x26\x6d\x61\x78\x49\x6d\x61\x67\x65\x42\x79\x74\x65\x73\x3d"+maxImageBytes+"\x26\x63\x68\x65\x63\x6b\x49\x6d\x61\x67\x65\x54\x69\x74\x6c\x65\x3d"+checkImageTitle+"\x26\x6d\x6f\x64\x65\x3d"+mode+"\x26\x6c\x6f\x63\x61\x6c\x65\x3d"+locale+"\x26\x75\x70\x6c\x6f\x61\x64\x55\x52\x4c\x3d"+ce_hx+"\x26\x69\x6d\x61\x67\x65\x55\x50\x61\x74\x68\x3d"+imageUPath+"\x26\x64\x65\x66\x61\x75\x6c\x74\x55\x50\x61\x74\x68\x3d"+defaultUPath+"\x26\x69\x6d\x61\x67\x65\x4d\x61\x78\x43\x6f\x75\x6e\x74\x3d"+maxImageNum+"\x26\x69\x6d\x61\x67\x65\x4b\x69\x6e\x64\x3d"+imageKind+"\x26\x69\x6d\x61\x67\x65\x55\x4e\x61\x6d\x65\x54\x79\x70\x65\x3d"+imageUNameType+"\x26\x69\x6d\x61\x67\x65\x55\x4e\x61\x6d\x65\x45\x6e\x63\x6f\x64\x65\x3d"+imageUNameEncode+"\x26\x69\x6d\x61\x67\x65\x56\x69\x65\x77\x65\x72\x50\x6c\x61\x79\x3d"+imageViewerPlay+"\x26\x69\x6d\x61\x67\x65\x4f\x72\x67\x50\x61\x74\x68\x3d"+t.ce_bh.config.ce_IK+"\x26\x69\x6d\x61\x67\x65\x53\x69\x7a\x65\x4c\x69\x6d\x69\x74\x3d"+imageSizeLimit+"\x26\x65\x64\x69\x74\x6f\x72\x46\x72\x61\x6d\x65\x3d"+editorFrame+"\x26\x69\x6d\x61\x67\x65\x44\x6f\x6d\x61\x69\x6e\x3d"+imageDomain+"\x26\x75\x70\x6c\x6f\x61\x64\x46\x69\x6c\x65\x53\x75\x62\x44\x69\x72\x3d"+uploadFileSubDir+"\x26\x63\x65\x5f\x59\x51\x3d"+ce_YQ+"\x26\x50\x68\x6f\x74\x6f\x45\x64\x69\x74\x6f\x72\x4c\x6f\x63\x61\x6c\x65\x3d"+t.ce_bh.ce_cg+"\x26\x69\x6d\x61\x67\x65\x45\x64\x69\x74\x6f\x72\x46\x6c\x61\x67\x3d\x66\x6c\x61\x73\x68\x50\x68\x6f\x74\x6f"+"\x26\x5f\x5f\x43\x6c\x69\x63\x6b\x3d\x30";if(ce_Vl>0)ce_OZ+="\x26\x6d\x61\x78\x49\x6d\x61\x67\x65\x57\x69\x64\x74\x68\x3d"+maxImageWidth+"\x26\x63\x65\x5f\x56\x6c\x3d"+ce_Vl;if(t.ce_bo!=null){var iSrc=t.ce_bo.src;iSrc=iSrc.replace(/\'/g,"\x25\x32\x37");var ce_iE="";if(t.ce_bo.title!=""||t.ce_bo.alt!=""){if(t.ce_bo.alt!="")ce_iE=t.ce_bo.alt;if(ce_iE==""&&t.ce_bo.title!="")ce_iE=t.ce_bo.title;}ce_iE=encodeURI(ce_iE);var ce_hQ=t.ce_bo.style.borderWidth;ce_hQ=ce_hQ.substring(0,ce_hQ.indexOf("\x70\x78"));if(isNaN(ce_hQ))ce_hQ="";if(ce_hQ==""&&t.ce_bo.border!="")ce_hQ=t.ce_bo.border;var ce_cE=(t.ce_bo.style.width.replace("\x70\x78","")=="")?t.ce_bo.width:t.ce_bo.style.width.replace("\x70\x78","");if(isNaN(ce_cE))ce_cE="";var ce_cF=(t.ce_bo.style.height.replace("\x70\x78","")=="")?t.ce_bo.height:t.ce_bo.style.height.replace("\x70\x78","");if(isNaN(ce_cF))ce_cF="";var iId=t.ce_bo.id;if(!iId)iId="";var ce_gR=t.ce_bo.className;if(!ce_gR)ce_gR="";var ce_gp="";var ce_vW=(ce_bi.IsIE)?t.ce_bo.style.styleFloat:t.ce_bo.style.cssFloat;if(ce_vW&&ce_vW!=""){ce_gp=ce_vW;}else{ce_gp=(t.ce_bo.style.verticalAlign)?t.ce_bo.style.verticalAlign:t.ce_bo.align;}if(!ce_gp||ce_gp=="")ce_gp=t.ce_MS;ce_gp=ce_gp.toLowerCase();if(ce_gp=="\x74\x65\x78\x74\x74\x6f\x70")ce_gp="\x74\x65\x78\x74\x2d\x74\x6f\x70";ce_OZ+="\x26\x65\x64\x69\x74\x49\x6d\x61\x67\x65\x55\x52\x4c\x3d"+iSrc+"\x26\x65\x64\x69\x74\x49\x6d\x61\x67\x65\x54\x69\x74\x6c\x65\x3d"+encodeURI(ce_iE)+"\x26\x65\x64\x69\x74\x49\x6d\x61\x67\x65\x57\x69\x64\x74\x68\x3d"+ce_cE+"\x26\x65\x64\x69\x74\x49\x6d\x61\x67\x65\x48\x65\x69\x67\x68\x74\x3d"+ce_cF+"\x26\x69\x6d\x61\x67\x65\x54\x69\x74\x6c\x65\x3d"+encodeURI(ce_iE)+"\x26\x69\x6d\x61\x67\x65\x42\x6f\x72\x64\x65\x72\x3d"+ce_hQ+"\x26\x69\x6d\x61\x67\x65\x41\x6c\x69\x67\x6e\x3d"+ce_gp+"\x26\x69\x6d\x61\x67\x65\x49\x64\x3d"+iId+"\x26\x69\x6d\x61\x67\x65\x43\x6c\x61\x73\x73\x3d"+ce_gR+"\x26\x64\x65\x66\x61\x75\x6c\x74\x49\x6d\x61\x67\x65\x55\x52\x4c\x3d"+iSrc+"\x26\x69\x6d\x61\x67\x65\x6d\x6f\x64\x69\x66\x79\x3d\x74\x72\x75\x65";}var ce_gV="\x3c\x6f\x62\x6a\x65\x63\x74\x20\x63\x6c\x61\x73\x73\x69\x64\x3d\x27\x63\x6c\x73\x69\x64\x3a\x44\x32\x37\x43\x44\x42\x36\x45\x2d\x41\x45\x36\x44\x2d\x31\x31\x63\x66\x2d\x39\x36\x42\x38\x2d\x34\x34\x34\x35\x35\x33\x35\x34\x30\x30\x30\x30\x27\x20\x63\x6f\x64\x65\x62\x61\x73\x65\x3d\x27\x68\x74\x74\x70\x3a\x2f\x2f\x64\x6f\x77\x6e\x6c\x6f\x61\x64\x2e\x6d\x61\x63\x72\x6f\x6d\x65\x64\x69\x61\x2e\x63\x6f\x6d\x2f\x70\x75\x62\x2f\x73\x68\x6f\x63\x6b\x77\x61\x76\x65\x2f\x63\x61\x62\x73\x2f\x66\x6c\x61\x73\x68\x2f\x73\x77\x66\x6c\x61\x73\x68\x2e\x63\x61\x62\x23\x76\x65\x72\x73\x69\x6f\x6e\x3d\x39\x2c\x30\x2c\x30\x2c\x30\x27\x20\x77\x69\x64\x74\x68\x3d\x27\x31\x30\x30\x25\x27\x20\x68\x65\x69\x67\x68\x74\x3d\x27"+(t.ce_Ht-44)+"\x70\x78\x27\x20\x69\x64\x3d\x27\x49\x6d\x61\x67\x65\x45\x64\x69\x74\x6f\x72\x27\x3e";ce_gV+="\x3c\x70\x61\x72\x61\x6d\x20\x6e\x61\x6d\x65\x3d\x27\x6d\x6f\x76\x69\x65\x27\x20\x76\x61\x6c\x75\x65\x3d\x27"+ce_acm+"\x27\x2f\x3e";ce_gV+="\x3c\x70\x61\x72\x61\x6d\x20\x6e\x61\x6d\x65\x3d\x27\x66\x6c\x61\x73\x68\x56\x61\x72\x73\x27\x20\x76\x61\x6c\x75\x65\x3d\x27"+ce_OZ+"\x27\x2f\x3e";ce_gV+="\x3c\x70\x61\x72\x61\x6d\x20\x6e\x61\x6d\x65\x3d\x27\x61\x6c\x6c\x6f\x77\x53\x63\x72\x69\x70\x74\x41\x63\x63\x65\x73\x73\x27\x20\x76\x61\x6c\x75\x65\x3d\x27\x61\x6c\x77\x61\x79\x73\x27\x2f\x3e";ce_gV+="\x3c\x65\x6d\x62\x65\x64";ce_gV+="\x20\x6e\x61\x6d\x65\x3d\x27\x49\x6d\x61\x67\x65\x45\x64\x69\x74\x6f\x72\x27";ce_gV+="\x20\x61\x6c\x6c\x6f\x77\x53\x63\x72\x69\x70\x74\x41\x63\x63\x65\x73\x73\x3d\x27\x61\x6c\x77\x61\x79\x73\x27";ce_gV+="\x20\x68\x73\x70\x61\x63\x65\x3d\x27\x30\x27";ce_gV+="\x20\x68\x65\x69\x67\x68\x74\x3d\x27"+(t.ce_Ht-44)+"\x70\x78\x27";ce_gV+="\x20\x77\x69\x64\x74\x68\x3d\x27\x31\x30\x30\x25\x27";ce_gV+="\x20\x76\x73\x70\x61\x63\x65\x3d\x27\x30\x27";ce_gV+="\x20\x62\x6f\x72\x64\x65\x72\x3d\x27\x30\x27";ce_gV+="\x20\x61\x6c\x69\x67\x6e\x3d\x27\x27";ce_gV+="\x20\x73\x72\x63\x3d\x27"+ce_acm+"\x27";ce_gV+="\x20\x66\x6c\x61\x73\x68\x56\x61\x72\x73\x3d\x27"+ce_OZ+"\x27";ce_gV+="\x20\x71\x75\x61\x6c\x69\x74\x79\x3d\x27\x68\x69\x67\x68\x27";ce_gV+="\x20\x74\x79\x70\x65\x3d\x27\x61\x70\x70\x6c\x69\x63\x61\x74\x69\x6f\x6e\x2f\x78\x2d\x73\x68\x6f\x63\x6b\x77\x61\x76\x65\x2d\x66\x6c\x61\x73\x68\x27";ce_gV+="\x20\x70\x6c\x75\x67\x69\x6e\x73\x70\x61\x67\x65\x3d\x27\x68\x74\x74\x70\x3a\x2f\x2f\x77\x77\x77\x2e\x6d\x61\x63\x72\x6f\x6d\x65\x64\x69\x61\x2e\x63\x6f\x6d\x2f\x67\x6f\x2f\x67\x65\x74\x66\x6c\x61\x73\x68\x70\x6c\x61\x79\x65\x72\x27\x3e";ce_gV+="\x3c\x2f\x6f\x62\x6a\x65\x63\x74\x3e";t.ce_br.getElementById("\x66\x6c\x61\x73\x68\x54\x64").innerHTML=ce_gV;if(ce_bi.ce_co&&ce_dL=="\x4d\x61\x63\x69\x6e\x74\x6f\x73\x68")t.ce_YN(null,t.ce_br.getElementById(t.ce_bh.pCmd+"\x5f\x70\x6c\x75\x67\x69\x6e"));},ce_do:function(){var t=this;t.ce_oS=t.ce_cc.ce_aqP(t.ce_bh.pCmd,t.ce_Qy,t.ce_Ht,t.ce_aoB);var ce_dg=t.ce_br=t.ce_bh.ce_aox(t.ce_oS);if(!ce_dg)return null;var ce_bl=ce_dg.createElement("\x64\x69\x76");ce_bl.id=this.ce_bh.pCmd+"\x5f\x70\x6c\x75\x67\x69\x6e";ce_bl.style.width=String(t.ce_Qy)+"\x70\x78";ce_bl.className="\x63\x65\x5f\x64\x68";ce_bl.alt="\x6e\x6f\x43\x6c\x6f\x73\x65";ce_bl.style.zIndex=10;ce_bl.style.display="\x6e\x6f\x6e\x65";if(!(t.ce_Ho>0&&t.ce_Ho>=10)){ce_bl.innerHTML="\x3c\x64\x69\x76\x20\x63\x6c\x61\x73\x73\x3d\x27\x63\x65\x5f\x66\x4f\x20\x70\x36\x27\x20\x73\x74\x79\x6c\x65\x3d\x27\x77\x69\x64\x74\x68\x3a\x31\x30\x30\x25\x3b\x20\x68\x65\x69\x67\x68\x74\x3a\x31\x30\x30\x25\x3b\x20\x74\x65\x78\x74\x2d\x61\x6c\x69\x67\x6e\x3a\x63\x65\x6e\x74\x65\x72\x3b\x27\x3e\x3c\x74\x61\x62\x6c\x65\x20\x63\x65\x6c\x6c\x70\x61\x64\x64\x69\x6e\x67\x3d\x27\x30\x27\x20\x63\x65\x6c\x6c\x73\x70\x61\x63\x69\x6e\x67\x3d\x27\x30\x27\x20\x63\x6c\x61\x73\x73\x3d\x27\x70\x6c\x75\x67\x69\x6e\x5f\x70\x68\x6f\x74\x6f\x65\x64\x69\x74\x6f\x72\x27\x20\x73\x74\x79\x6c\x65\x3d\x27\x6d\x61\x72\x67\x69\x6e\x3a\x31\x30\x30\x70\x78\x20\x61\x75\x74\x6f\x3b\x27\x3e\x3c\x74\x72\x3e\x3c\x74\x64\x3e\x3c\x69\x6d\x67\x20\x61\x6c\x74\x3d\x27\x70\x68\x6f\x74\x6f\x65\x64\x69\x74\x6f\x72\x5f\x6d\x73\x67\x5f\x74\x69\x74\x6c\x65\x2e\x67\x69\x66\x27\x3e\x3c\x2f\x74\x64\x3e\x3c\x2f\x74\x72\x3e\x3c\x74\x72\x3e\x3c\x74\x64\x20\x69\x64\x3d\x27\x70\x65\x64\x69\x74\x6f\x72\x4d\x73\x67\x54\x69\x74\x6c\x65\x27\x20\x63\x6c\x61\x73\x73\x3d\x27\x70\x65\x64\x69\x74\x6f\x72\x5f\x74\x69\x74\x6c\x65\x27\x3e"+ce_bk.ce_Hn+"\x3c\x2f\x74\x64\x3e\x3c\x2f\x74\x72\x3e\x3c\x74\x72\x3e\x3c\x74\x64\x3e\x3c\x61\x20\x68\x72\x65\x66\x3d\x27\x23\x27\x3e\x3c\x69\x6d\x67\x20\x69\x64\x3d\x27\x70\x65\x64\x69\x74\x6f\x72\x46\x6c\x61\x73\x68\x50\x6c\x61\x79\x65\x72\x44\x6f\x77\x6e\x6c\x6f\x61\x64\x27\x20\x61\x6c\x74\x3d\x27\x66\x6c\x61\x73\x68\x70\x6c\x61\x79\x65\x72\x5f\x64\x6f\x77\x6e\x6c\x6f\x61\x64\x2e\x67\x69\x66\x27\x3e\x3c\x2f\x61\x3e\x3c\x2f\x74\x64\x3e\x3c\x2f\x74\x72\x3e\x3c\x2f\x74\x61\x62\x6c\x65\x3e\x3c\x2f\x64\x69\x76\x3e";ce_dg.body.appendChild(ce_bl);var ce_akz=t.ce_aqG(ce_bl);return ce_akz;}else{ce_bl.innerHTML="\x3c\x64\x69\x76\x20\x63\x6c\x61\x73\x73\x3d\x27\x63\x65\x5f\x66\x4f\x20\x70\x36\x27\x20\x73\x74\x79\x6c\x65\x3d\x27\x70\x61\x64\x64\x69\x6e\x67\x3a\x30\x3b\x27\x20\x61\x6c\x74\x3d\x27\x6e\x6f\x43\x6c\x6f\x73\x65\x27\x3e\x3c\x74\x61\x62\x6c\x65\x20\x63\x65\x6c\x6c\x70\x61\x64\x64\x69\x6e\x67\x3d\x27\x30\x27\x20\x63\x65\x6c\x6c\x73\x70\x61\x63\x69\x6e\x67\x3d\x27\x30\x27\x20\x63\x6c\x61\x73\x73\x3d\x27\x70\x6c\x75\x67\x69\x6e\x5f\x70\x68\x6f\x74\x6f\x65\x64\x69\x74\x6f\x72\x27\x20\x61\x6c\x74\x3d\x27\x6e\x6f\x43\x6c\x6f\x73\x65\x27\x3e\x3c\x74\x72\x3e\x3c\x74\x64\x20\x69\x64\x3d\x27\x66\x6c\x61\x73\x68\x54\x64\x27\x3e\x3c\x2f\x74\x64\x3e\x3c\x2f\x74\x72\x3e\x3c\x2f\x74\x61\x62\x6c\x65\x3e\x3c\x64\x69\x76\x20\x63\x6c\x61\x73\x73\x3d\x27\x62\x74\x4c\x69\x6e\x65\x27\x20\x73\x74\x79\x6c\x65\x3d\x27\x70\x61\x64\x64\x69\x6e\x67\x2d\x62\x6f\x74\x74\x6f\x6d\x3a\x37\x70\x78\x3b\x27\x20\x61\x6c\x74\x3d\x27\x6e\x6f\x43\x6c\x6f\x73\x65\x27\x3e\x3c\x69\x6d\x67\x20\x6e\x61\x6d\x65\x3d\x27\x63\x6f\x6e\x66\x69\x72\x6d\x27\x20\x61\x6c\x74\x3d\x27\x62\x74\x6e\x5f\x70\x6c\x75\x67\x69\x6e\x5f\x62\x6b\x5f\x73\x6d\x61\x6c\x6c\x2e\x67\x69\x66\x27\x20\x63\x6c\x61\x73\x73\x3d\x27\x4e\x61\x6d\x6f\x53\x45\x5f\x62\x74\x6e\x5f\x73\x74\x79\x6c\x65\x20\x4e\x61\x6d\x6f\x53\x45\x5f\x62\x74\x6e\x5f\x73\x6d\x61\x6c\x6c\x27\x20\x2f\x3e\x20\x3c\x69\x6d\x67\x20\x6e\x61\x6d\x65\x3d\x27\x63\x65\x5f\x62\x4c\x27\x20\x61\x6c\x74\x3d\x27\x62\x74\x6e\x5f\x70\x6c\x75\x67\x69\x6e\x5f\x62\x6b\x5f\x73\x6d\x61\x6c\x6c\x2e\x67\x69\x66\x27\x20\x63\x6c\x61\x73\x73\x3d\x27\x4e\x61\x6d\x6f\x53\x45\x5f\x62\x74\x6e\x5f\x73\x74\x79\x6c\x65\x20\x4e\x61\x6d\x6f\x53\x45\x5f\x62\x74\x6e\x5f\x73\x6d\x61\x6c\x6c\x27\x20\x2f\x3e\x3c\x2f\x64\x69\x76\x3e\x3c\x2f\x64\x69\x76\x3e";ce_dg.body.appendChild(ce_bl);}var ce_hw=ce_dg.getElementsByTagName("\x68\x65\x61\x64")[0].getElementsByTagName("\x73\x63\x72\x69\x70\x74");var ce_nW="\x6a\x73\x2f\x6e\x61\x6d\x6f\x5f\x63\x6f\x6e\x6e\x65\x63\x74\x6f\x72\x2e\x6a\x73";var ce_Zt=false;for(i=0;i<ce_hw.length;i++){if(ce_hw[i].src.indexOf(ce_nW)!= -1){ce_Zt=true;break;}}if(ce_Zt==false){var ce_WU=ce_dg.createElement("\x73\x63\x72\x69\x70\x74");ce_WU.type="\x74\x65\x78\x74\x2f\x6a\x61\x76\x61\x73\x63\x72\x69\x70\x74";ce_WU.src=this.ce_bh.baseURL+ce_nW;ce_dg.getElementsByTagName("\x68\x65\x61\x64")[0].appendChild(ce_WU);}var ce_kd=null;var ce_bY=[];var ce_cR=ce_e();var x=this.ce_bh.util.ce_bu(ce_bl,"\x69\x6d\x67");for(var i=0;i<x.length;i++){if(x[i].tagName.toLowerCase()=="\x69\x6d\x67"){var ce_ct=false;switch(x[i].name){case '\x63\x6f\x6e\x66\x69\x72\x6d':x[i].title=ce_bk.ce_fV;ce_ct=true;ce_bY.push({'\x65\x6c\x65':x[i],'\x66\x75\x6e\x63':'\x63\x65\x5f\x63\x79'});break;case '\x63\x65\x5f\x62\x4c':x[i].title=ce_bk.ce_fW;ce_ct=true;ce_bY.push({'\x65\x6c\x65':x[i],'\x66\x75\x6e\x63':'\x63\x65\x5f\x62\x4c'});ce_kd=x[i].parentNode;break;}if(ce_ct){this.ce_bh.ce_fG(ce_dg,x[i]);this.ce_bh.util.ce_bs(x[i].parentNode,ce_bi.ce_bp?'\x6b\x65\x79\x70\x72\x65\x73\x73':'\x6b\x65\x79\x64\x6f\x77\x6e',function(e){t.ce_cp(e,ce_bl)});}}}for(var i=0;i<ce_bY.length;i++){var ce_bD=ce_bY[i];if(ce_bD.ele&&ce_bD.ele.parentNode&&ce_bD.ele.parentNode.nodeName=="\x41"){if(ce_bD.func){switch(ce_bD.func){case '\x63\x65\x5f\x63\x79':this.ce_bh.util.ce_bs(ce_bD.ele.parentNode,'\x6d\x6f\x75\x73\x65\x64\x6f\x77\x6e',function(evt){t.ce_cy(evt,t)});break;case '\x63\x65\x5f\x62\x4c':this.ce_bh.util.ce_bs(ce_bD.ele.parentNode,'\x6d\x6f\x75\x73\x65\x64\x6f\x77\x6e',function(evt){t.ce_bL(evt,t)});break;}}this.ce_bh.ce_ha(ce_dg,ce_bD.ele);}}var ce_acV=function(){if(!t.ce_oS.closed)NamoSE.Util.ce_bv(ce_acV,100);else t.ce_SO();};if(ce_bi.ce_bp)ce_acV();else NamoSE.Util.ce_bs(t.ce_oS,'\x75\x6e\x6c\x6f\x61\x64',function(e){t.ce_SO(e);});NamoSE.Util.ce_bs(t.ce_oS,'\x72\x65\x73\x69\x7a\x65',function(e){t.ce_YN(e,ce_bl);});NamoSE.Util.ce_bs(t.ce_br,'\x63\x6f\x6e\x74\x65\x78\x74\x6d\x65\x6e\x75',function(e){NamoSE.Util.stop(e);return false;});NamoSE.Util.ce_bs(t.ce_br,'\x6b\x65\x79\x64\x6f\x77\x6e',function(e){if(e.keyCode==116){if(ce_bi.IsIE)e.keyCode=0;else NamoSE.Util.stop(e);return false;}});t.ce_cc.ce_hl(t.ce_bS,ce_bl);t.ce_aot(t);NamoSE.ce_cQ.ce_fx(ce_bl,this.ce_bh);return ce_bl;},ce_cy:function(e,t){var val='';var ecmd;if(ce_bi.IsIE){try{if(t.ce_bh.ce_bt().body.createTextRange().inRange(ce_bj.range)){ce_bj.ce_bB();}else{if(ce_bj.ce_ej())ce_bj.ce_bB();}}catch(e){}}else{ce_bj.ce_bB();}var ce_dJ=function(){t.ce_bh.ce_fw(ecmd,val);};ce_kT=t.ce_bh;if(t.ce_bo!=null)ce_xE=t.ce_bo;t.ce_oS.ce_av();},ce_bL:function(e,t){if(ce_bi.IsIE){var ce_eG=function(){try{if(t.ce_bh.ce_bt().body.createTextRange().inRange(ce_bj.range)){ce_bj.ce_bB();}else{if(ce_bj.ce_ej())ce_bj.ce_bB();}}catch(e){}};NamoSE.Util.ce_bv(ce_eG,10);}t.ce_bh.ce_dD.close(t.ce_bS,t.ce_bO);t.ce_oS.ce_ay();},ce_aot:function(t){var ce_xO=30;var ce_Eg="\x4e\x61\x6d\x6f\x53\x45\x5f\x42\x61\x63\x6b\x44\x69\x76";try{var pDoc=t.ce_bh.ce_fs();var ce_aoG=t.ce_SO;var ce_iJ=NamoSE.Util.ce_LJ(pDoc);var ce_oP=NamoSE.Util.ce_LI(pDoc);var xPos=(ce_oP.x>ce_iJ.x)?ce_oP.x:ce_iJ.x;var yPos=(ce_oP.y>ce_iJ.y)?ce_oP.y:ce_iJ.y;var ce_kv=pDoc.createElement("\x44\x49\x56");ce_kv.id=ce_Eg;ce_kv.style.display="";ce_kv.style.position="\x61\x62\x73\x6f\x6c\x75\x74\x65";ce_kv.style.zIndex=10010;ce_kv.style.backgroundColor="\x23\x30\x30\x30\x30\x30\x30";ce_kv.style.width=xPos+"\x70\x78";ce_kv.style.height=yPos+"\x70\x78";ce_kv.style.left=0;ce_kv.style.top=0;if(ce_bi.IsIE){ce_kv.style.filter="\x61\x6c\x70\x68\x61\x28\x6f\x70\x61\x63\x69\x74\x79\x3d"+ce_xO+"\x29";}else{ce_kv.style.opacity="\x30\x2e\x33\x30";}pDoc.body.appendChild(ce_kv);NamoSE.Util.ce_bs(ce_kv,'\x6d\x6f\x75\x73\x65\x64\x6f\x77\x6e',function(){if(t.ce_oS.closed==true)ce_aoG();});}catch(exp){}NamoSE.Util.ce_bs(parent.window,'\x72\x65\x73\x69\x7a\x65',t.ce_aaL);},ce_SO:function(e){try{var pDoc=parent.window.document;var ce_LG=pDoc.getElementById("\x4e\x61\x6d\x6f\x53\x45\x5f\x42\x61\x63\x6b\x44\x69\x76");if(ce_LG&&ce_LG.parentNode){ce_LG.parentNode.removeChild(ce_LG);}NamoSE.Util.ce_JO(parent.window,'\x72\x65\x73\x69\x7a\x65',t.ce_aaL);}catch(exp){}},ce_aqG:function(ce_bl){var x=this.ce_bh.util.ce_bu(ce_bl,"\x69\x6d\x67");for(var i=0;i<x.length;i++){if(x[i].tagName.toLowerCase()=="\x69\x6d\x67"){x[i].src=this.ce_bh.baseURL+this.ce_bh.config.ce_ds+x[i].alt;x[i].alt="";if(x[i].id=="\x70\x65\x64\x69\x74\x6f\x72\x46\x6c\x61\x73\x68\x50\x6c\x61\x79\x65\x72\x44\x6f\x77\x6e\x6c\x6f\x61\x64"&&x[i].parentNode&&x[i].parentNode.nodeName=="\x41"){x[i].parentNode.href=this.ce_bh.config.ce_amx;x[i].alt=x[i].title="\x41\x64\x6f\x62\x65\x20\x46\x6c\x61\x73\x68\x20\x50\x6c\x61\x79\x65\x72\x20\x44\x6f\x77\x6e\x6c\x6f\x61\x64";}}}var ce_jE=this.ce_bh.ce_yc();var ce_hF=ce_jE.family+"\x20"+ce_jE.size;this.ce_br.getElementById("\x70\x65\x64\x69\x74\x6f\x72\x4d\x73\x67\x54\x69\x74\x6c\x65").className+="\x20"+ce_hF;ce_bl.style.height=this.ce_Ht+"\x70\x78";this.ce_cc.ce_hl(t.ce_bS,ce_bl);NamoSE.ce_cQ.ce_fx(ce_bl,this.ce_bh);return ce_bl;},ce_aaL:function(e){var ce_Eg="\x4e\x61\x6d\x6f\x53\x45\x5f\x42\x61\x63\x6b\x44\x69\x76";var pDoc=parent.window.document;if(pDoc&&pDoc.getElementById(ce_Eg)){var ce_iJ=NamoSE.Util.ce_LJ(pDoc);var ce_oP=NamoSE.Util.ce_LI(pDoc);var xPos=(ce_oP.x>ce_iJ.x)?ce_oP.x:ce_iJ.x;var yPos=(ce_oP.y>ce_iJ.y)?ce_oP.y:ce_iJ.y;pDoc.getElementById(ce_Eg).style.width=xPos+"\x70\x78";pDoc.getElementById(ce_Eg).style.height=yPos+"\x70\x78";}},ce_YN:function(e,ce_bl){if(!ce_bl)return;ce_bl.style.width=(this.ce_br.documentElement.clientWidth+((ce_bi.IsIE)? -1: -2))+"\x70\x78";var ce_aty=(ce_bi.IsIE)?"\x6f\x62\x6a\x65\x63\x74":"\x65\x6d\x62\x65\x64";var ce_adt=NamoSE.Util.ce_gf(this.ce_br.body,ce_aty);if(ce_adt){ce_adt.height=(this.ce_br.documentElement.clientHeight-47+((ce_bi.IsIE)?4:0))+"\x70\x78";}}};