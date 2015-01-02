# Group 08 - Web Service

## Software used
 * [Sesame](http://rdf4j.org/)
 * [Pubby](http://wifo5-03.informatik.uni-mannheim.de/pubby/)
 * [Tomcat](http://tomcat.apache.org/) (for running Sesame & Pubby)
 * [Vagrant](https://www.vagrantup.com/) (creation and configuration of the VM)

## How to use
Just install [Vagrant](https://www.vagrantup.com/) and [Virtual Box](https://www.virtualbox.org/)
and then run `vagrant up` in this directory.

After a few minutes (if it's the first time you run this VM) you will have our sparql endopoint and
pubby running.

If you want to stop it just run `vagrant halt`, and if you want to destroy the VM just run `vagrant
destroy`

## Important URIs
 * SPARQL endpoint: http://localhost:8080/openrdf-sesame/repositories/group08?query=
 * SPARQL graphical endpoint: http://localhost:8080/openrdf-workbench/repositories/group08/query
 * Pubby: http://localhost:8080/pubby/

## Example
If we do at the SPARQL the following query:
```
SELECT ?privSchool {
    ?privSchool a <http://www.semanticweb.org/group08/School/Private>.
} LIMIT 3
```

We get this results:

| privSchool                                                                               |
|------------------------------------------------------------------------------------------|
| <http://www.semanticweb.org/group08/School/Private/AddaClevengerJuniorPreparatory>       |
| <http://www.semanticweb.org/group08/School/Private/BrandeisHillelDaySchool>              |
| <http://www.semanticweb.org/group08/School/Private/ChildrensDaySchool>                   |

If we want to see the related pubby page we can go to:
http://localhost:8080/pubby/School/Private/AddaClevengerJuniorPreparatory

## Problems found
### Problem importing some files in Virtuoso
At first we were using [Virtuoso](http://virtuoso.openlinksw.com/) but we found a problem importing
the TTL files with our data. The problem seems to be related to the importing of `POLYGON` in fields
with type `geosparql:wktLiteral` and the error message (in command line) was: `RDF box with a
geometry RDF type and a non-geometry content`.

We tried both `stable` and `devel` branchs of Virtuoso with no favorable results so we switched to
Sesame.

There is a fork of Virtuoso that seems to solve this issue that is discussed here:
https://github.com/openlink/virtuoso-opensource/issues/195

## URIs
When you click in a external resource link at the result set of a query you get redirected to
http://www.semanticweb.org/group08/... since is the base URI we have chosen. We decided not to take
action about this (although is wrong).

A propper setup is to change the URI for example to http://localhost:8080/sanfrancisco/ install
Pubby there too.