dbpediaDAO = require './data_access/dbpediaDAO'
datahubDAO = require './data_access/datahubDAO'
fs = require 'fs'

console.log('Generating LOD cloud void of Spanish Datasheets')

#Generate Data Access Objects for DBpedia & datahub
_DBPdao = new dbpediaDAO
_DHdao = new datahubDAO

#Get All Datasheets from dbpedia
console.log('  ->Fetching datasheets from DBpedia....')
_DBPdao.getEsLODcloud (err, resDBPdao) ->
  if err? #Something when wrong on DAO object
    console.log('Received error from DBpedia:' + err.message)
    process.exit(1);
    
  console.log('    >Fetched ' + resDBPdao.length + ' Datasheets ids')
  #Get Info from datahub.io
  console.log('  ->Fetching datasheets info from datahub.io....')
  #Lets parse it into javascript object
  parsetoJS resDBPdao, (err, datasheets) ->
    parsetoTurtle datasheets, (err, ttl) ->
      fs.writeFile './eslodcloud.ttl', ttl+'\n', (err) ->
        if err
          console.log err
        else
          console.log 'File eslodcloud.tll created!'
          console.log 'You can generate void-graph using this file on http://lmatteis.github.io/void-graph/'
        return



###
Datasheets to JS object parser
  returns a collection with all datasheets formatted
  something like [ { links: [], title: 'DBpedia in Spanish' },
                   { links: [ 'mismuseos-gnoss', 'geonames-semantic-web' ] }]
###
parsetoJS = (resDBPdao, next) ->
  datasheets = []
  remaining = resDBPdao.length
  for datasheet in resDBPdao then do (datasheet) ->
    console.log ('    >Fetching ' +datasheet)
    _DHdao.getDatasheetInfo datasheet, (err, resDHdao) ->  
      if err? #Something when wrong on DAO object (should NOT happend), just in case skip this datasheet
        console.log('Error fetching '+datasheet + ' skipping...');
        remaining--
        
      obj = links: []
      obj.title = resDHdao.title
      obj.name = resDHdao.name
      if resDHdao.extras.triples? 
        obj.triples = resDHdao.extras.triples

      #Check extras for links
      for k, v of resDHdao.extras        
        #Only add link if datasheet is present
        if k.substring(0,6) == 'links:' and resDBPdao.indexOf(k.substring(6, k.length)) > -1
          obj.links.push k.substring(6, k.length)

      datasheets.push obj
      remaining-- 
      if(remaining == 0)
        next null, datasheets
        
###
JS to Turtle object parser
  returns turtle from js
###   
parsetoTurtle = (data, next) ->
  ttl = '@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n
        @prefix void: <http://rdfs.org/ns/void#> .\n
        @prefix dcterms: <http://purl.org/dc/terms/> .\n
        <http://datahub.io/> a void:Dataset;\n
        dcterms:title "datahub.io";\n
        void:subset'
  #Add all datasheets as datahubs subsets
  for i of data
    ttl += '\n<http://datahub.io/dataset/' + data[i].name + '>'
    if(i < data.length-1)
      ttl += ','
    else
      ttl += ' .\n'

  for i of data
    ttl += '\n\n<http://datahub.io/dataset/' + data[i].name + '> a void:Dataset;\n
           dcterms:title "' + data[i].title + '"'
    
    if data[i].triples?
      ttl += ';\nvoid:triples ' + data[i].triples
    
    if data[i].links.length > 0
      ttl += ';\nvoid:subset'
    else
      ttl += ' .'
      
    for j of data[i].links
      ttl += '\n<http://datahub.io/dataset/' + data[i].links[j] + '>'
    
      if j < (data[i].links.length-1)
        ttl += ','
      else
        ttl += ' .'
    
  next null, ttl
  
  
  
  
  
  
  
  