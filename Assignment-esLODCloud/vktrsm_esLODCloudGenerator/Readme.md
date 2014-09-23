## esLODCloudGenerator

**esLODCloudGenerator is a NodeJS application written in coffeescript**. It allows you to rip all spanish datasheets posted at http://es.dbpedia.org/Wiki.jsp?page=EsLODcloud (ignores entries without datahub link) and generate a turtle file with them, 
ready to be pasted at http://lmatteis.github.io/void-graph/

## Design

**esLODCloudGenerator** has been implemented using DAO architecture to abstract data model from business logic.

## Requirements

* **Nodejs**: Refer to http://nodejs.org/ to get installation instructions.
* **CoffeeScript**: Refer to http://coffeescript.org/ to get installation instructions.

## Usage

> coffee eslodcloudgen.coffee

## Output

> eslodcloud.ttl

## TODO

* Improve data scraping from dbpedia.org, no idea if they have an API rest (no luck finding it yet)
* Improve Turtle conversion using node-rdf module (https://www.npmjs.org/package/rdf)