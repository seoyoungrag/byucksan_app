package com.sds.acube.app.env.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.sds.acube.app.idir.org.option.Codes;
import com.sds.acube.app.idir.org.user.Substitute;
import com.sds.acube.app.idir.org.user.UserImage;
import com.sds.acube.app.idir.org.user.UserStatus;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.env.vo.EmptyReasonVO;

/**
 * Class Name  : UserEnvService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 4. 18. <br> 수 정  자 : redcomet  <br> 수정내용 :  <br>
 * @author   redcomet 
 * @since  2011. 4. 18.
 * @version  1.0 
 * @see  com.sds.acube.app.env.service.impl.EnvUserService.java
 */

@SuppressWarnings("serial")
@Service
public class EnvUserService extends BaseService implements IEnvUserService {

    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    
    /* 
     * 회사아이디에 대한 부재설정이유 리스트를 가져온다.
     * @see com.sds.acube.app.env.service.IEnvUserService#selectEmptyReasonList(java.lang.String)
     */
    public List<EmptyReasonVO> selectEmptyReasonList(String compId) throws Exception {
	Codes codes = orgService.selectCodes(0, compId);
	List<EmptyReasonVO> emptyReasonVOList = null;
	if(codes != null) {
	    emptyReasonVOList = new ArrayList<EmptyReasonVO>();
	    int codesSize = codes.size();
	    for(int i=0; i<codesSize; i++) {
//		String description = codes.getDescription(i);
//		if(description != null && description.equals(compId)) {
		    EmptyReasonVO emptyReasonVO = new EmptyReasonVO();
		    emptyReasonVO.setEmptyReasonId(codes.getCodeID(i));
		    emptyReasonVO.setEmptyReason(codes.getCodeName(i));
		    emptyReasonVOList.add(emptyReasonVO);
//		}
	    }
	}
	return emptyReasonVOList;
    }

    /* 
     * 사용자 아이디에 대한 부재 정보를 가져온다.
     * @see com.sds.acube.app.env.service.IEnvUserService#selectEmptyInfo(java.lang.String)
     */
    public EmptyInfoVO selectEmptyInfo(String userId) throws Exception {
	UserStatus userStatus = orgService.selectEmptyStatus(userId);
	String currentDate = DateUtil.getCurrentDate();
	EmptyInfoVO emptyInfoVO = new EmptyInfoVO();
	emptyInfoVO.setUserId(userId);
	emptyInfoVO.setIsEmpty(false);
	emptyInfoVO.setIsSubstitute(false);

	if(userStatus != null && userStatus.getEmptySet()) {
	    if(currentDate.compareTo(userStatus.getStartDate()) >= 0 && currentDate.compareTo(userStatus.getEndDate()) <= 0) {
		emptyInfoVO.setIsEmpty(true);
		emptyInfoVO.setEmptyReason(userStatus.getEmptyReason());
		emptyInfoVO.setEmptyStartDate(userStatus.getStartDate());
		emptyInfoVO.setEmptyEndDate(userStatus.getEndDate());
		Substitute substitute = orgService.selectSubstitute(userId);
		if(substitute != null) {

		    emptyInfoVO.setIsSubstitute(true);
		    emptyInfoVO.setCompId(substitute.getCompID());
		    emptyInfoVO.setSubstituteId(substitute.getUserUID());
		    emptyInfoVO.setSubstituteName(substitute.getUserName());
		    emptyInfoVO.setSubstitutePositionName(substitute.getPositionName());
		    emptyInfoVO.setSubstituteDeptId(substitute.getDeptID());
		    emptyInfoVO.setSubstituteDeptName(substitute.getDeptName());
		    emptyInfoVO.setSubstituteStartDate(substitute.getStartDate());
		    emptyInfoVO.setSubstituteEndDate(substitute.getEndDate());

		    UserVO userVO = orgService.selectUserByUserId(substitute.getUserUID());
		    emptyInfoVO.setSubstituteDisplayPosition(userVO.getDisplayPosition());
		}
	    }
	    
	}
	return emptyInfoVO;
    }
    
    /* 
     * 사용자 아이디에 대한 부재 정보를 가져온다.(관리자용)
     * @see com.sds.acube.app.env.service.IEnvUserService#selectEmptyInfo(java.lang.String)
     */
    public EmptyInfoVO selectEmptyInfoForAdmin(String userId) throws Exception {
	UserStatus userStatus = orgService.selectEmptyStatus(userId);
	String currentDate = DateUtil.getCurrentDate();
	EmptyInfoVO emptyInfoVO = new EmptyInfoVO();
	emptyInfoVO.setUserId(userId);
	emptyInfoVO.setIsEmpty(false);
	emptyInfoVO.setIsSubstitute(false);

	if(userStatus != null && userStatus.getEmptySet()) {
	    if(currentDate.compareTo(userStatus.getEndDate()) <= 0) {
		emptyInfoVO.setIsEmpty(true);
		emptyInfoVO.setEmptyReason(userStatus.getEmptyReason());
		emptyInfoVO.setEmptyStartDate(userStatus.getStartDate());
		emptyInfoVO.setEmptyEndDate(userStatus.getEndDate());
		Substitute substitute = orgService.selectSubstituteForAdmin(userId);
		if(substitute != null) {

		    emptyInfoVO.setIsSubstitute(true);
		    emptyInfoVO.setCompId(substitute.getCompID());
		    emptyInfoVO.setSubstituteId(substitute.getUserUID());
		    emptyInfoVO.setSubstituteName(substitute.getUserName());
		    emptyInfoVO.setSubstitutePositionName(substitute.getPositionName());
		    emptyInfoVO.setSubstituteDeptId(substitute.getDeptID());
		    emptyInfoVO.setSubstituteDeptName(substitute.getDeptName());
		    emptyInfoVO.setSubstituteStartDate(substitute.getStartDate());
		    emptyInfoVO.setSubstituteEndDate(substitute.getEndDate());

		    UserVO userVO = orgService.selectUserByUserId(substitute.getUserUID());
		    emptyInfoVO.setSubstituteDisplayPosition(userVO.getDisplayPosition());
		}
	    }
	    
	}
	return emptyInfoVO;
    }

    /* 
     * 사용자 아이디에 대한 부재 정보를 저장한다.
     * @see com.sds.acube.app.env.service.IEnvUserService#insertEmptyInfo(com.sds.acube.app.env.vo.EmptyInfoVO)
     */
    public boolean insertEmptyInfo(EmptyInfoVO emptyInfoVO) throws Exception {
	UserStatus userStatus = new UserStatus();
	userStatus.setUserUID(emptyInfoVO.getUserId());
	userStatus.setEmptySet(emptyInfoVO.getIsEmpty());
	userStatus.setEmptyReason(emptyInfoVO.getEmptyReason());
	userStatus.setStartDate(emptyInfoVO.getEmptyStartDate());
	userStatus.setEndDate(emptyInfoVO.getEmptyEndDate());

	Substitute substitute = null;
	if(emptyInfoVO.getIsSubstitute()) {
	    substitute = new Substitute();
	    substitute.setUserUID(emptyInfoVO.getSubstituteId());
	    substitute.setStartDate(emptyInfoVO.getSubstituteStartDate());
	    substitute.setEndDate(emptyInfoVO.getSubstituteEndDate());
	}
	return orgService.insertEmptyStatus(userStatus, substitute);
    }
    
    /* 
     * 감사자 여부를 가져온다.
     * @see com.sds.acube.app.env.service.IEnvUserService#selectAuditorList(java.lang.String)
     */
    public boolean isAuditor(String compId, String auditorId, String auditorType) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("auditorId", auditorId);
	map.put("auditorType", auditorType);
	AuditDeptVO auditVO = (AuditDeptVO) commonDAO.getMap("env.user.selectAuditor", map);
	if (auditVO == null) {
	    return false;
	} else {
	    return true;
	}
    }

    /* 
     * 감사자  목록을 가져온다.
     * @see com.sds.acube.app.env.service.IEnvUserService#selectAuditorList(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<UserVO> selectAuditorList(String compId) throws Exception {
	return selectAuditorList(compId, "");
    }

    /* 
     * 감사자  목록을 가져온다.(auditorType : 임원(O), 일반감사자(A), 협조문서함 담당자(C))
     * @see com.sds.acube.app.env.service.IEnvUserService#selectAuditorList(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<UserVO> selectAuditorList(String compId, String auditorType) throws Exception {
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("auditorType", auditorType);
	List<AuditDeptVO> auditDeptVOList = (List<AuditDeptVO>) commonDAO.getListMap("env.user.selectAuditorList", map);
	List<UserVO> auditorList = null;
	if(auditDeptVOList != null) {
	    auditorList = new ArrayList<UserVO>();
	    int auditDeptVOListSize = auditDeptVOList.size();
	    for(int i=0; i<auditDeptVOListSize; i++) {
		String auditorId = ((AuditDeptVO)auditDeptVOList.get(i)).getAuditorId();
		UserVO userVO = orgService.selectUserByUserId(auditorId);
		if(userVO != null) {
		    auditorList.add(userVO);
		}
	    }
	}
	return auditorList;
    }

    /* 
     * 감사자 아이디에 대한 담당부서 목록을 가져온다.
     * @see com.sds.acube.app.env.service.IEnvUserService#selectAuditDeptList(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<AuditDeptVO> selectAuditDeptList(AuditDeptVO auditDeptVO) throws Exception {
	return (List<AuditDeptVO>) commonDAO.getList("env.user.selectAuditDeptList", auditDeptVO);
    }

    /* 
     * 감사자를 등록한다.
     * @see com.sds.acube.app.env.service.IEnvUserService#insertAuditor(com.sds.acube.app.env.vo.AuditDeptVO)
     */
    public void insertAuditor(AuditDeptVO auditDeptVO) throws Exception {
	commonDAO.insert("env.user.insertAuditor", auditDeptVO);
    }

    /* 
     * 감사자를 삭제한다.
     * @see com.sds.acube.app.env.service.IEnvUserService#deleteAuditor(com.sds.acube.app.env.vo.AuditDeptVO)
     */
    public void deleteAuditor(AuditDeptVO auditDeptVO) throws Exception {
	commonDAO.delete("env.user.deleteAuditor", auditDeptVO);
    }

    /* 
     * 감사자 아이디에 대한 담당부서를 등록한다.
     * @see com.sds.acube.app.env.service.IEnvUserService#insertAuditDept(com.sds.acube.app.env.vo.AuditDeptVO)
     */
    public void insertAuditDept(AuditDeptVO auditDeptVO) throws Exception {
	commonDAO.insert("env.user.insertAuditDept", auditDeptVO);
    }

    /* 
     * 감사자 아이디에 대한 선택한 담당부서를 삭제한다.
     * @see com.sds.acube.app.env.service.IEnvUserService#deleteAuditDept(com.sds.acube.app.env.vo.AuditDeptVO)
     */
    public void deleteAuditDept(AuditDeptVO auditDeptVO) throws Exception {
	commonDAO.delete("env.user.deleteAuditDept", auditDeptVO);
    }

    // 사용자 이미지 select(사진(0)/도장(1)/사인(2))
    public FileVO selectUserImage(String compId, String userId, int nType) throws Exception {

	FileVO fileVO = null;
	UserImage userImage = orgService.selectUserImage(userId);

	if(userImage != null) {
	    fileVO = new FileVO();
	    fileVO.setCompId(compId);
	    fileVO.setProcessorId(userId);
	    String fileType = null;
	    byte[] imageData = null;

	    switch(nType) {
		case 0:
		    fileType = userImage.getPictureType();
		    imageData = userImage.getPictureImage();
		    fileVO.setFileType(userImage.getPictureType());
		    fileVO.setImageData(userImage.getPictureImage());
		    break;
		case 1:
		    fileType = userImage.getStampType();
		    imageData = userImage.getStampImage();
		    break;
		case 2:
		    fileType = userImage.getSignType();
		    imageData = userImage.getSignImage();
		    break;
	    }
	    if(imageData != null && imageData.length > 0) {
		String fileName = GuidUtil.getGUID() + "." + fileType;
		String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		String filePath = uploadTemp + "/" + compId + "/" + fileName;
    	    
		File outFile = new File(filePath);
		OutputStream outputStream = new FileOutputStream(outFile);
		outputStream.write(imageData);
		outputStream.close();
    
		fileVO.setFileName(fileName);
		fileVO.setFilePath(filePath);
		fileVO.setFileType(fileType);
		fileVO.setImageData(imageData);
	    }

	}

	return fileVO;
    }

    // 사용자 이미지 update(사진(0)/도장(1)/사인(2))
    public boolean updateUserImage(FileVO fileVO, int nType) throws Exception {

	UserImage userImage = new UserImage();
	userImage.setUserUID(fileVO.getProcessorId());
	userImage.setStampOrSign(1);
	
	//String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");	
	//String filePath = uploadTemp + "/" + fileVO.getCompId() + "/" + fileVO.getFileName();
	String filePath = fileVO.getFilePath();
	File fileImage = new File(filePath);
	InputStream inputStream = new FileInputStream(fileImage);
	byte imageData[] = new byte[(int)(fileImage.length())];
	inputStream.read(imageData);
	
	switch(nType) {
	    case 0:
		userImage.setPictureType(fileVO.getFileType());
		userImage.setPictureImage(imageData);
		break;
	    case 1:
		userImage.setStampType(fileVO.getFileType());
		userImage.setStampImage(imageData);
		break;
	    case 2:
		userImage.setSignType(fileVO.getFileType());
		userImage.setSignImage(imageData);
		break;
	}
	return orgService.updateUserImage(userImage, nType);
    }

    // 사용자 이미지 delete(사진(0)/도장(1)/사인(2))
    public boolean deleteUserImage(String userId, int nType) throws Exception {
	return orgService.deleteUserImage(userId, nType);
    }
}

