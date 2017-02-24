/**
 * 
 */
package com.sds.acube.app.common.vo;

/**
 * Class Name  : OrganizationVO.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 5. 2. <br> 수 정  자 : redcomet  <br> 수정내용 :  <br>
 * @author   redcomet 
 * @since  2011. 5. 2.
 * @version  1.0 
 * @see  com.sds.acube.app.common.vo.OrganizationVO.java
 */

public class OrganizationVO {
    /**
	 */
    private String orgName;
    /**
	 */
    private String orgOtherName;
    /**
	 */
    private String orgAbbrName;
    /**
	 */
    private String orgID;
    /**
	 */
    private String orgCode;
    /**
	 */
    private String orgParentID;
    /**
	 */
    private int orgOrder;
    /**
	 */
    private int orgType;
    /**
	 */
    private String description;
    private String oDCDCode;
    /**
	 */
    private boolean isODCD;
    /**
	 */
    private boolean isInstitution;
    /**
	 */
    private boolean isHeadOffice;
    /**
	 */
    private boolean isInspection;
    /**
	 */
    private String addrSymbol;
    /**
	 */
    private boolean isProxyDocHandleDept;
    /**
	 */
    private String proxyDocHandleDeptCode;
    /**
	 */
    private String chiefPosition;
    /**
	 */
    private boolean formBoxInfo;
    /**
	 */
    private String reserved1;
    /**
	 */
    private String reserved2;
    /**
	 */
    private String reserved3;
    /**
	 */
    private String outgoingName;
    /**
	 */
    private String companyID;
    /**
	 */
    private String institutionDisplayName;
    /**
	 */
    private String homepage;
    /**
	 */
    private String email;
    /**
	 */
    private String address;
    /**
	 */
    private String addressDetail;
    /**
	 */
    private String zipCode;
    /**
	 */
    private String telephone;
    /**
	 */
    private String fax;
    /**
	 */
    private boolean isDeleted;
    /**
	 */
    private boolean isProcess;
    /**
	 */
    private String roleCodes;
    /**
	 */
    private String recvSymbol;
    /**
	 */
    private String recvParentSymbol;
    
    /**
	 * @return  the orgName
	 */
    public String getOrgName() {
        return orgName;
    }
    /**
	 * @param orgName  the orgName to set
	 */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    /**
	 * @return  the orgOtherName
	 */
    public String getOrgOtherName() {
        return orgOtherName;
    }
    /**
	 * @param orgOtherName  the orgOtherName to set
	 */
    public void setOrgOtherName(String orgOtherName) {
        this.orgOtherName = orgOtherName;
    }
    /**
	 * @return  the orgAbbrName
	 */
    public String getOrgAbbrName() {
        return orgAbbrName;
    }
    /**
	 * @param orgAbbrName  the orgAbbrName to set
	 */
    public void setOrgAbbrName(String orgAbbrName) {
        this.orgAbbrName = orgAbbrName;
    }
    /**
	 * @return  the orgID
	 */
    public String getOrgID() {
        return orgID;
    }
    /**
	 * @param orgID  the orgID to set
	 */
    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }
    /**
	 * @return  the orgCode
	 */
    public String getOrgCode() {
        return orgCode;
    }
    /**
	 * @param orgCode  the orgCode to set
	 */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    /**
	 * @return  the orgParentID
	 */
    public String getOrgParentID() {
        return orgParentID;
    }
    /**
	 * @param orgParentID  the orgParentID to set
	 */
    public void setOrgParentID(String orgParentID) {
        this.orgParentID = orgParentID;
    }
    /**
	 * @return  the orgOrder
	 */
    public int getOrgOrder() {
        return orgOrder;
    }
    /**
	 * @param orgOrder  the orgOrder to set
	 */
    public void setOrgOrder(int orgOrder) {
        this.orgOrder = orgOrder;
    }
    /**
	 * @return  the orgType
	 */
    public int getOrgType() {
        return orgType;
    }
    /**
	 * @param orgType  the orgType to set
	 */
    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }
    /**
	 * @return  the description
	 */
    public String getDescription() {
        return description;
    }
    /**
	 * @param description  the description to set
	 */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the oDCDCode
     */
    public String getODCDCode() {
        return oDCDCode;
    }
    /**
     * @param oDCDCode the oDCDCode to set
     */
    public void setODCDCode(String oDCDCode) {
        this.oDCDCode = oDCDCode;
    }
    /**
	 * @return  the isODCD
	 */
    public boolean getIsODCD() {
        return isODCD;
    }
    /**
     * @param isODCD the isODCD to set
     */
    public void setIsODCD(boolean isODCD) {
        this.isODCD = isODCD;
    }
    /**
	 * @return  the isInstitution
	 */
    public boolean getIsInstitution() {
        return isInstitution;
    }
    /**
     * @param isInstitution the isInstitution to set
     */
    public void setIsInstitution(boolean isInstitution) {
        this.isInstitution = isInstitution;
    }
    /**
	 * @return  the isHeadOffice
	 */
    public boolean getIsHeadOffice() {
        return isHeadOffice;
    }
    /**
     * @param isHeadOffice the isHeadOffice to set
     */
    public void setIsHeadOffice(boolean isHeadOffice) {
        this.isHeadOffice = isHeadOffice;
    }
    /**
	 * @return  the isInspection
	 */
    public boolean getIsInspection() {
        return isInspection;
    }
    /**
     * @param isInspection the isInspection to set
     */
    public void setIsInspection(boolean isInspection) {
        this.isInspection = isInspection;
    }
    /**
	 * @return  the addrSymbol
	 */
    public String getAddrSymbol() {
        return addrSymbol;
    }
    /**
	 * @param addrSymbol  the addrSymbol to set
	 */
    public void setAddrSymbol(String addrSymbol) {
        this.addrSymbol = addrSymbol;
    }
    /**
	 * @return  the isProxyDocHandleDept
	 */
    public boolean getIsProxyDocHandleDept() {
        return isProxyDocHandleDept;
    }
    /**
     * @param isProxyDocHandleDept the isProxyDocHandleDept to set
     */
    public void setIsProxyDocHandleDept(boolean isProxyDocHandleDept) {
        this.isProxyDocHandleDept = isProxyDocHandleDept;
    }
    /**
	 * @return  the proxyDocHandleDeptCode
	 */
    public String getProxyDocHandleDeptCode() {
        return proxyDocHandleDeptCode;
    }
    /**
	 * @param proxyDocHandleDeptCode  the proxyDocHandleDeptCode to set
	 */
    public void setProxyDocHandleDeptCode(String proxyDocHandleDeptCode) {
        this.proxyDocHandleDeptCode = proxyDocHandleDeptCode;
    }
    /**
	 * @return  the chiefPosition
	 */
    public String getChiefPosition() {
        return chiefPosition;
    }
    /**
	 * @param chiefPosition  the chiefPosition to set
	 */
    public void setChiefPosition(String chiefPosition) {
        this.chiefPosition = chiefPosition;
    }
    /**
	 * @return  the formBoxInfo
	 */
    public boolean getFormBoxInfo() {
        return formBoxInfo;
    }
    /**
	 * @param formBoxInfo  the formBoxInfo to set
	 */
    public void setFormBoxInfo(boolean formBoxInfo) {
        this.formBoxInfo = formBoxInfo;
    }
    /**
	 * @return  the reserved1
	 */
    public String getReserved1() {
        return reserved1;
    }
    /**
	 * @param reserved1  the reserved1 to set
	 */
    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }
    /**
	 * @return  the reserved2
	 */
    public String getReserved2() {
        return reserved2;
    }
    /**
	 * @param reserved2  the reserved2 to set
	 */
    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }
    /**
	 * @return  the reserved3
	 */
    public String getReserved3() {
        return reserved3;
    }
    /**
	 * @param reserved3  the reserved3 to set
	 */
    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }
    /**
	 * @return  the outgoingName
	 */
    public String getOutgoingName() {
        return outgoingName;
    }
    /**
	 * @param outgoingName  the outgoingName to set
	 */
    public void setOutgoingName(String outgoingName) {
        this.outgoingName = outgoingName;
    }
    /**
	 * @return  the companyID
	 */
    public String getCompanyID() {
        return companyID;
    }
    /**
	 * @param companyID  the companyID to set
	 */
    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }
    /**
	 * @return  the institutionDisplayName
	 */
    public String getInstitutionDisplayName() {
        return institutionDisplayName;
    }
    /**
	 * @param institutionDisplayName  the institutionDisplayName to set
	 */
    public void setInstitutionDisplayName(String institutionDisplayName) {
        this.institutionDisplayName = institutionDisplayName;
    }
    /**
	 * @return  the homepage
	 */
    public String getHomepage() {
        return homepage;
    }
    /**
	 * @param homepage  the homepage to set
	 */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }
    /**
	 * @return  the email
	 */
    public String getEmail() {
        return email;
    }
    /**
	 * @param email  the email to set
	 */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
	 * @return  the address
	 */
    public String getAddress() {
        return address;
    }
    /**
	 * @param address  the address to set
	 */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
	 * @return  the addressDetail
	 */
    public String getAddressDetail() {
        return addressDetail;
    }
    /**
	 * @param addressDetail  the addressDetail to set
	 */
    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
    /**
	 * @return  the zipCode
	 */
    public String getZipCode() {
        return zipCode;
    }
    /**
	 * @param zipCode  the zipCode to set
	 */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    /**
	 * @return  the telephone
	 */
    public String getTelephone() {
        return telephone;
    }
    /**
	 * @param telephone  the telephone to set
	 */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    /**
	 * @return  the fax
	 */
    public String getFax() {
        return fax;
    }
    /**
	 * @param fax  the fax to set
	 */
    public void setFax(String fax) {
        this.fax = fax;
    }
    /**
	 * @return  the isDeleted
	 */
    public boolean getIsDeleted() {
        return isDeleted;
    }
    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    /**
	 * @return  the isProcess
	 */
    public boolean getIsProcess() {
        return isProcess;
    }
    /**
     * @param isProcess the isProcess to set
     */
    public void setIsProcess(boolean isProcess) {
        this.isProcess = isProcess;
    }
    /**
	 * @return  the roleCodes
	 */
    public String getRoleCodes() {
        return roleCodes;
    }
    /**
	 * @param roleCodes  the roleCodes to set
	 */
    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }
    
	/**
	 * @param recvSymbol  the recvSymbol to set
	 */
	public void setRecvSymbol(String recvSymbol) {
		this.recvSymbol = recvSymbol;
	}
	/**
	 * @return  the recvSymbol
	 */
	public String getRecvSymbol() {
		return recvSymbol;
	}
	/**
	 * @param recvParentSymbol  the recvParentSymbol to set
	 */
	public void setRecvParentSymbol(String recvParentSymbol) {
		this.recvParentSymbol = recvParentSymbol;
	}
	/**
	 * @return  the recvParentSymbol
	 */
	public String getRecvParentSymbol() {
		return recvParentSymbol;
	}

}
