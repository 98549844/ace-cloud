# FROM 指定基础镜像
FROM unitfinance/jdk17-sbt-scala:latest
#FROM linuxserver/ffmpeg:latest
#FROM linuxserver/ffmpeg:amd64-6.1.1


MAINTAINER Garlam Au

# 更新软件源并安装必要的软件包
#RUN apt-get update -y && \
#    apt-get install -y && \
#    apt-get install openjdk-17-jre -y && \
#    apt-get install openjdk-17-jdk -y && \
#    update-alternatives --config java # 查看openJDK被安装到什么地方, 这个在配置环境变量时会用到

#VOLUME /tmp
ADD target/ace-entities-module-1.0.jar ace-entities-module.jar
EXPOSE 8090
# 暴露端口 与配置文件对应
ENTRYPOINT ["java","-jar","ace-entities-module.jar","--spring.profiles.active=docker"]
# 激活名为 appliction-docker.yml 或 appliction-docker.properties 的配置
#java -jar ace-3.2.jar --spring.profiles.active=dev #一直占用terminal
#nohup java -jar ace-3.2.jar --spring.profiles.active=dev & #释放命令行




#打开shell执行docker build命令
#1. dockerfile 构建自己的docker image
#docker build -f Dockerfile -t ace-img .
#
#-f Dockerfile 指定了 Dockerfile 的名称为 Dockerfile
#-t ace-application 指定了构建的镜像名称为 ace-img
#.  Dockerfile 所在的目录为当前目录, cd到当前目录执行



#FROM            #基础镜像,一切从这里开始构建.
#MAINTAINER      #镜像是谁写的,姓名+邮箱
#RUN             #镜像构建的时候需要运行的命令
#ADD             #步骤:tomcat镜像,这个tomcat压缩包!添加内容
#WORKDIR         #镜像的工作目录
#VOLUME          #挂载的目录
#EXPOST          #保留端口配置
#CMD             #指定这个容器启动的时候要运行的命令,只有一个会生效,可被替代.
#ENTRYPOINT      #指定这个容器启动的时候要运行的命令,可以追加命令.
#ONBUILD         #当构建一个被集成DockerFile这个时候就会运行ONBUILD的指令.触发指令.
#COPY            #类似ADD,将我们文件拷贝到镜像中.
#ENV             #构建的时候设置环境变量.
