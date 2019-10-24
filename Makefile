.PHONY : help deploy deploy-production build-prod-cljs build-prod-js-deps build-dev-js-deps run-dev-server develop

DEFAULT_GOAL: help

API_URL?="http://localhost:4000/api/graphql"

require-%:
	@ if [ "${${*}}" = "" ]; then \
		echo "ERROR: Environment variable not set: \"$*\""; \
		exit 1; \
	fi

build-dev-js-deps :
	yarn
	NODE_ENV=development yarn parcel build src/js/index.js -d dist --no-minify

build-prod-js-deps :
	yarn
	yarn parcel build src/js/index.js -d dist

run-dev-server :
	lein figwheel dev

build-prod-cljs :
	lein clean
	lein cljsbuild once min

develop : build-dev-js-deps run-dev-server

production: require-API_URL build-prod-js-deps build-prod-cljs
