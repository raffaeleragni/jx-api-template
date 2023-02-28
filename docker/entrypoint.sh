#!/bin/sh

if [ "$1" = "migrate" ]; then

  exec java --classpath=postgresql-42.2.1.jar:liquibase.jar \
      --driver=org.postgresql.Driver \
      --changeLogFile=db/changelog.xml \
      --url="jdbc:postgresql://${API_DB_HOSTNAME}/${API_DB_NAME}" \
      --username=${API_DB_USERNAME} \
      --password=${API_DB_PASSWORD} \
      update

else

  args="java "

  [ -f /app/dd-java-agent.jar ] && args="${args} -javaagent:/app/dd-java-agent.jar"

  args="${args} -jar app.jar"
  echo "Command will be: \"${args}\""

  set -- ${args}
  exec "$@"

fi
