
JAR1="/acube/ep/ep_lib/lib/SSOAgent.jar"
JAR2="/acube/ep/ep_lib/lib/bcprov-jdk14-133.jar"
JAR3="/acube/ep/ep_lib/lib/jce-jdk13-130.jar"

SECURITY_CLASSPATH="${JAR1}:${JAR2}:${JAR3}"

java -classpath ${SECURITY_CLASSPATH} com.sds.acube.esso.security.MakeSecurityKeys
