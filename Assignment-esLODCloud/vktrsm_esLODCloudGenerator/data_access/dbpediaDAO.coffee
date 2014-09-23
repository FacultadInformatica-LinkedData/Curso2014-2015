#
# This module implements bussiness logic for accessing Dbpedia.org data
#
http = require('http')

class dbpediaDAO
	constructor: () ->

    #This method should be improved (maybe dbpedia has rest api?)
	getEsLODcloud: (next) ->
      #Get data from dbpedia.org
      options =
        host: 'es.dbpedia.org'
        path: '/Wiki.jsp?page=EsLODcloud&skin=raw'

      request = http.get options, (response) ->
        str = ''
        response.on 'data', (chunk) -> 
          str += chunk
          return
        
        #Data received, lets parse it to a javascript object
        #This parsing should be improved, spliting is not very elegant
        response.on 'end', ->
          table = str.split('%%table-filter')[1].split('%%')[0]
          table = table.split('\n')
          
          rows = []
          
          for line in table then do (line) ->
            entry = line.split('[link|')[1]
            if entry?
              rows.push entry.substring(0, entry.length - 2).split('http://datahub.io/dataset/')[1];
          
          next null, rows

      request.on 'error', (error) ->
        next error       

module.exports = dbpediaDAO