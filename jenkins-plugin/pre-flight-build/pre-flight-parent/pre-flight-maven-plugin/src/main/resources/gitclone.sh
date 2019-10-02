
#build job SH
ANT_HOME=/EBS/Install/apache-ant-1.8.4
M2_HOME=/EBS/Install/apache-maven-2.2.1
JAVA_HOME=/EBS/Install/jdk1.6.0_32
PATH=$PATH:$M2_HOME/bin:$ANT_HOME/bin:$JAVA_HOME/bin
export JAVA_HOME ANT_HOME M2_HOME PATH

echo $WORKSPACE
remote_workspace=$WORKSPACE/${workspace_name}
 if  test -s $remote_workspace/.git
    then
        echo "this is an initialized repository. hence will update"
        echo "updating ..."
        cd $remote_workspace
        git pull origin master
		echo "updating completes"
    else
	   echo "no git repository exists. need to clone first time"
       echo "cloning ..."
       cd ${WORKSPACE}
       git clone ${workspace_loc}
	   cd $remote_workspace
	   echo "cloning completes"
 fi
pwd
# build
mvn ${build_command}
# sonar
mvn -e -B sonar:sonar -Dsonar.jdbc.driver=com.mysql.jdbc.Driver -Dsonar.jdbc.url=jdbc:mysql://10.242.138.31:3306/sonar -Dsonar.host.url=http://10.242.138.36:8281/sonar/