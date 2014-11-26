#
# This module implements bussiness logic for accessing datahub.io data
#
http = require('http')

class datahubDAO
	constructor: () ->

	getDatasheetInfo: (id, next) ->
      #Get data from datahub
      options =
        host: 'datahub.io'
        path: '/api/rest/package/' + id

      request = http.get options, (response) ->
        str = ''
        response.on 'data', (chunk) -> 
          str += chunk
          return
        
        response.on 'end', ->
          next null, JSON.parse(str)

      request.on 'error', (error) ->
        next error       
        
module.exports = datahubDAO