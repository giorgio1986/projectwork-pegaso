# Variabili da .env
include .env
export $(shell sed 's/=.*//' .env)

all: help
.DEFAULT_GOAL := help

##---------------------------------------------------
## HELP MAKE
##---------------------------------------------------

help:
	@echo +-------------------------------+
	@echo [ Development Environment ]
	@echo +-------------------------------+
	@echo Commands start with \"make\" e.g. make docker-up
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

##---------------------------------------------------
## CONTAINERS CONFIGURATION
##---------------------------------------------------

docker-prune: ## Prune all except volumes
	@echo Prune all except volumes
	@docker system prune -f
	@docker image prune -a -f
	@docker container prune -f

docker-container-prune: ## Prune all container
	@echo Prune all container
	@docker container prune -f

docker-volumes: ## List all volumes
	@echo List all volumes
	@docker volume ls

docker-build: ## Builds all images for containers
	@echo Build or Rebuild all images for containers
	@docker-compose build --no-cache
	
##---------------------------------------------------
## DOCKER SHORT OPT
##---------------------------------------------------

docker-up: ## Start docker containers
	@echo Bringing Docker up
	@docker-compose up -d

docker-down: ## Stop all Docker containers
	@echo Stopping Docker containers
	@docker-compose down --remove-orphans
	
docker-status: ## Status of Docker containers
	@echo Getting Docker containers status
	@docker ps -a

##---------------------------------------------------
## CONTAINERS ACCESS
##---------------------------------------------------

cmd-postgresql: ## Start container shell
	@echo Start postgresql container shell
	@docker exec -it previ-suite-postgresql bash
	
cmd-edoc: ## Start container shell
	@echo Start edoc container shell
	@docker exec -it previ-suite-quarkus bash
	
##---------------------------------------------------
## CONTAINERS LOGS
##---------------------------------------------------

logs-postgresql: ## Save container logs into file
	@echo Save postgresql container logs into file postgresql.log
	@docker logs previ-suite-postgresql > ./logs/postgresql.log
	
logs-quarkus: ## Save container logs into file
	@echo Save quarkus container logs into file quarkus.log
	@docker logs previ-suite-quarkus > ./logs/quarkus.log