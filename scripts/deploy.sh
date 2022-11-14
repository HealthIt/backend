#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/app
DEPLOY_LOG="$REPOSITORY/deploy.log"

APP_LOG="$REPOSITORY/application.log"
ERROR_LOG="$REPOSITORY/error.log"

TIME_NOW=$(date +%c)

echo "$TIME_NOW > 현재 구동 중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fla action | grep java | awk '{print $1}')

echo "$TIME_NOW > 현재 구동 중인 애플리케이션 pid: $CURRENT_PID" > DEPLOY_LOG

if [ -z "$CURRENT_PID" ]; then
  echo "$TIME_NOW > 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다." > DEPLOY_LOG
else
  echo "$TIME_NOW > kill -15 $CURRENT_PID" > DEPLOY_LOG
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "$TIME_NOW >  새 애플리케이션 배포" > DEPLOY_LOG

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "$TIME_NOW >  JAR NAME: $JAR_NAME" > DEPLOY_LOG

echo "$TIME_NOW >  $JAR_NAME 에 실행권한 추가" > DEPLOY_LOG

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행" > DEPLOY_LOG

nohup java -jar -Duser.timezone=Asia/Seoul $JAR_NAME >> $APP_LOG 2> ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_NAME)
echo "$TIME_NOW > 프로세스 아이디: $CURRENT_PID" >> $DEPLOY_LOG