deploy:
	docker build -t spring-boot .
	docker run --rm --name spring-boot-app --network=host -p 8081:8080 spring-boot
