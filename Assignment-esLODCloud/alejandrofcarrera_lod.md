**How to create a VoID Graph for Datasets**

This guide lets you know how to create a [VoID Graph](http://semanticweb.org/wiki/VoID) for a datasets selection or [LOD-Cloud](http://datahub.io/group/lodcloud) group on datahub.io. It is important to know that datahub.io is based on CKAN, so you can use its Application Programming Interface for use it via REST.

**First:** You should look for the datasets in datahub.io and get entity identifier and linksets of each dataset. 

You may use Datahub.io Dataset API:
- datahub.io/api/search/dataset?q=DATASET_NAME

Example with DATASET_NAME=aragodbpedia:

```json
{
   "count": 1, "results": ["aragodbpedia"]
}
```

NOTE: It is important get the last name of results.

Then you may use Datahub.io Entity API:
- datahub.io/api/rest/package/DATASET_NAME 

Example with DATASET_NAME=aragodbpedia and taking only the keys of the response ["title", "id", "triples", "extras"]:

```json
{
   "title": "AragoDBPedia",
   "id": "49300af3-c75b-48d2-9299-fffb12c2888d",
   "extras": {
       "links:dbpedia-es": "768",
       "language": "es",
       "spatial": "Aragón"
   }
}
```

To get lodcloud group you need use other Datahub.io API:
- datahub.io/api/rest/group/GROUP_NAME 

Example with GROUP_NAME=lodcloud and taking only some data of packages key (there are 265 datasets):

```json
{
   "packages":
   [ 
      "calames",
      "chem2bio2rdf",
      "chronicling-america",
      "clean-energy-data-reegle",
      "colinda",
      "courts-thesaurus",
      "data-cnr-it",
      "data-gov",
      "data-gov-uk-time-intervals",
      "data-incubator-climb",
      "data-incubator-metoffice",
      "data-incubator-our-airports",
      "data-incubator-smcjournals",
      "data-open-ac-uk",
      "datos-bcn-cl",
      "datos-bne-es",
      "dbpedia",
      "dbpedia-el",
      "dbpedia-lite",
      "dbpedia-pt",
      "dbtropes"
    ]
}
```

So, you need use Entity API to get information of each package.

**Second:** Review the datasets list ​​to create a list of inputs and outputs of each of the datasets according to linksets.

NOTE: It is easier create this list with names not ids.

**Third:** Create Uniform Resource Identifier for each dataset and for each identifier of linksets. 

For example: http://datahub.io/datasets/aragodbpedia

**Fourth:** Create Turtle file (.ttl)

Import prefix:

- @prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
- @prefix void: <http://rdfs.org/ns/void#> .
- @prefix dcterms: <http://purl.org/dc/terms/> .

Insert main dataset:

- <http://datahub.io/> a void:Dataset; dcterms:title "datahub.io" ; void:subset

Insert URIs of dataset selection and insert semicolon when there are more elements or point at end of iterator.

- <http://datahub.io/> a void:Dataset; dcterms:title "datahub.io" ; void:subset <http://datahub.io/dataset/aragodbpedia> ; void:subset <http://datahub.io/dataset/dbpedia-es> .

Same as before step with dataset selection (creation and subsets, but subsets are linksets datasets URIs)

- <http://datahub.io/dataset/aragodbpedia> a void:Dataset; dcterms:title "AragoDBPedia" ; void:subset <http://datahub.io/dataset/dbpedia-es> .

NOTE: if there is the triples key, you must add the property void: triples.

**Fifth:** Displayed on the web [lmatteis VoID-Graph](http://lmatteis.github.io/void-graph/)

I hope that guide helps you. See you :)




