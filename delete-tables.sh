#!/usr/bin/env bash

aws dynamodb delete-table \
    --table-name accounts \
    --endpoint-url http://localhost:8000 \
    --region=us-east-1 \
    --no-cli-pager --profile test-profile

aws dynamodb delete-table \
    --table-name transactions \
    --endpoint-url http://localhost:8000 \
    --region=us-east-1 \
    --no-cli-pager --profile test-profile

aws dynamodb delete-table \
    --table-name account-statements \
    --endpoint-url http://localhost:8000 \
    --region=us-east-1 \
    --no-cli-pager --profile test-profile
