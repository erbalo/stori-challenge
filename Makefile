services:
	$(MAKE) down
	$(MAKE) up

down:
	docker-compose down

up:
	docker-compose up

tables:
	./scripts/wait-for.sh localhost:8000 -t 60 -- ./init-tables.sh

app:
	docker build -t transactions-reader './transactions-reader'
	docker run -it --network rabbit-net --network-alias transactions-reader \
		--name transactions-reader --label transactions-reader \
		-e SPRING_RABBITMQ_HOST=stori-rabbitmq \
		-v $(shell pwd)/tmp/transactions:/file-repository transactions-reader

clean:
	docker container prune --filter label=transactions-reader -f
	./scripts/wait-for.sh localhost:8000 -t 60 -- ./delete-tables.sh
	docker-compose down --rmi all