#!/usr/bin/env bash

QUEUES="queue"
TOPICS="topic"

HOST=http://localhost:4566

awsc() {
  aws --no-sign-request --endpoint-url ${HOST} --region ${AWS_DEFAULT_REGION} $*
}

sqs() {
  awsc sqs $*
}

sns() {
  awsc sns $*
}

createqueue() {
  sqs create-queue --queue-name $1
}

createtopic() {
  createqueue ${1}_output
  sns create-topic --name $1
  sns subscribe \
   --topic-arn arn:aws:sns:${AWS_DEFAULT_REGION}:000000000000:${1} \
   --protocol sqs \
   --notification-endpoint ${HOST}/000000000000/${1}_output
}

for queue in $QUEUES; do
  createqueue $queue
done

for topic in $TOPICS; do
  createtopic $topic
done
