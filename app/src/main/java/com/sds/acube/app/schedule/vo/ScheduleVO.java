package com.sds.acube.app.schedule.vo;

/**
 * Class Name  : ScheduleVO.java <br> Description : 스케줄 VO 클래스 <br> Modification Information <br> <br> 수 정 일 : <br> 수 정 자 :  <br> 수정내용 :  <br>
 * @author   허주 
 * @since  2011. 3. 18 
 * @version  1.0 
 * @see  ScheduleVO
 */ 
public class ScheduleVO {
	
	/**
	 * 설명
	 */ 
	private String description;
	/**
	 * 대상서비스
	 */ 
	private String target;
	/**
	 * 서비스 주기설정
	 */ 
	private String cronExpression;
	/**
	 * SFB
	 */ 
	private String scheduleFactoryBean;
	/**
	 * CTB
	 */ 
	private String cronTriggerBean;
	/**
	 * JFB
	 */ 
	private String jobDetailFactoryBean;
	
	
	/**
	 * @param description  the description to set
	 */
        public void setDescription(String description) {
	    this.description = description;
        }
	/**
	 * @return  the description
	 */
        public String getDescription() {
	    return description;
        }
	/**
	 * @param target  the target to set
	 */
        public void setTarget(String target) {
	    this.target = target;
        }
	/**
	 * @return  the target
	 */
        public String getTarget() {
	    return target;
        }
	/**
	 * @param cronExpression  the cronExpression to set
	 */
        public void setCronExpression(String cronExpression) {
	    this.cronExpression = cronExpression;
        }
	/**
	 * @return  the cronExpression
	 */
        public String getCronExpression() {
	    return cronExpression;
        }
	/**
	 * @param cronTriggerBean  the cronTriggerBean to set
	 */
        public void setCronTriggerBean(String cronTriggerBean) {
	    this.cronTriggerBean = cronTriggerBean;
        }
	/**
	 * @return  the cronTriggerBean
	 */
        public String getCronTriggerBean() {
	    return cronTriggerBean;
        }
	/**
	 * @param scheduleFactoryBean  the scheduleFactoryBean to set
	 */
        public void setScheduleFactoryBean(String scheduleFactoryBean) {
	    this.scheduleFactoryBean = scheduleFactoryBean;
        }
	/**
	 * @return  the scheduleFactoryBean
	 */
        public String getScheduleFactoryBean() {
	    return scheduleFactoryBean;
        }
	/**
	 * @param jobDetailFactoryBean  the jobDetailFactoryBean to set
	 */
        public void setJobDetailFactoryBean(String jobDetailFactoryBean) {
	    this.jobDetailFactoryBean = jobDetailFactoryBean;
        }
	/**
	 * @return  the jobDetailFactoryBean
	 */
        public String getJobDetailFactoryBean() {
	    return jobDetailFactoryBean;
        }

}
