

rm:
	docker compose stop \
	&& docker compose rm \
	&& sudo rm -rf pg_profcom/

up:
	docker compose up -d

clang:
	@ clang-format -i src/main/java/ru/profcom/*.java src/main/java/ru/profcom/*/*.java
	@ clang-format -i src/test/java/ru/profcom/*.java src/test/java/ru/profcom/*/*.java