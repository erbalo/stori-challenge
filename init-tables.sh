aws dynamodb create-table \
    --region=us-east-1 \
    --endpoint-url http://localhost:8000 \
    --table-name accounts \
    --attribute-definitions AttributeName=id,AttributeType=N \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --no-cli-pager

aws dynamodb create-table \
    --region=us-east-1 \
    --endpoint-url http://localhost:8000 \
    --table-name transactions \
    --attribute-definitions \
        AttributeName=id,AttributeType=N \
        AttributeName=account_id,AttributeType=N \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --global-secondary-indexes "[{\"IndexName\": \"account-id-idx\", \"KeySchema\": [{\"AttributeName\": \"account_id\", \"KeyType\": \"HASH\"}], \"Projection\": {\"ProjectionType\": \"ALL\"}, \"ProvisionedThroughput\": {\"ReadCapacityUnits\": 5, \"WriteCapacityUnits\": 5}}]" \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --no-cli-pager

aws dynamodb create-table \
    --region=us-east-1 \
    --endpoint-url http://localhost:8000 \
    --table-name account-statements \
    --attribute-definitions \
        AttributeName=date,AttributeType=S \
        AttributeName=account_id,AttributeType=N \
    --key-schema \
        AttributeName=date,KeyType=HASH \
        AttributeName=account_id,KeyType=RANGE \
    --global-secondary-indexes "[{\"IndexName\": \"account-id-idx\", \"KeySchema\": [{\"AttributeName\": \"account_id\", \"KeyType\": \"HASH\"}], \"Projection\": {\"ProjectionType\": \"ALL\"}, \"ProvisionedThroughput\": {\"ReadCapacityUnits\": 5, \"WriteCapacityUnits\": 5}}]" \
    --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
    --no-cli-pager