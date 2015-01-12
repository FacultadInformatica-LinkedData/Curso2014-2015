/**
 * Copyright (C) 2014, 2015 Alejandro Barahona Alvarez, Sergio Conde Gomez, Sandra Saez Raspeño
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

var defaultQuery = 'PREFIX base: <http://www.semanticweb.org/group08/>\nPREFIX prop: <http://www.semanticweb.org/group08#>\n\nSELECT * {\n    base: ?prop ?obj\n} LIMIT 1000';

function doQuery(query, fnAction) {
    $.ajax({
        url: '/openrdf-sesame/repositories/group08',
        dataType: 'json',
        beforeSend: function(xhr){
            xhr.setRequestHeader('Accept', 'application/sparql-results+json');
        },
        data: {
            Accept: 'application/sparql-results+json',
            queryLn: 'SPARQL',
            query: query
        },
        timeout: 2000,
        success: fnAction,
        error: function(xhr, textStatus, errorThrown) {
            $('#resultContainer').append('<pre class="alert alert-danger" role="alert"></pre>');

            if (xhr.status == 400)
                $('#resultContainer pre').text(xhr.responseText);
            else if (xhr.statusText == 'timeout')
                $('#resultContainer pre').html('SPARQL endpoint timeout');

            console.log(xhr);
            $('#loading').hide();
        }
    });
}

function displayResult(data) {
    $('#resultContainer').append('<div class="panel panel-info"><div class="panel-heading"><h3 class="panel-title">Results</h3></div><table id="result" class="table table-striped"><thead><tr></tr><tbody /></table></div>');

    var header = $('#resultContainer #result thead tr');
    $.each(data.head.vars, function(key, value) {
        header.append('<th>' + value + '</th>');
    });

    $.each(data.results.bindings, function(index, bs) {
        var row = $('<tr/>');
        $.each(data.head.vars, function(key, vn) {
            if (bs[vn].type == "uri") {
                row.append('<td><a href="' + replaceUri(bs[vn].value) + '">' + bs[vn].value + '</a></td>');
            } else {
                row.append('<td>' + bs[vn].value + '</td>');
            }
        });
        $('#resultContainer #result tbody').append(row);
    });

    $('#loading').hide();
}

function replaceUri(uri) {
    return uri.replace("http://www.semanticweb.org/group08/", "http://localhost:8080/pubby/");
}

function displayClass(data) {
    var ulContainer = $("#classList");
    $.each(data.results.bindings, function(index, bs) {
        var className = bs['class'].value.replace("http://www.semanticweb.org/group08/","");
        ulContainer.append('<li><a class="classLink" href="#">' + className + '</a></li>');
    });

    $('.classLink').click(function(e) {
        e.preventDefault();
        $('#resultContainer').empty();
        $('#loading').show();
        $('#sparqlQuery').val('SELECT ?element {\n    ?element a <http://www.semanticweb.org/group08/' + $(this).html() + '>\n} LIMIT 1000');
        doQuery($('#sparqlQuery').val(), displayResult);
    });
}

$(document).ready(function() {
    $('#loading').hide();

    doQuery('PREFIX owl: <http://www.w3.org/2002/07/owl#> SELECT ?class { ?class a owl:Class }', displayClass);

    $('#sparqlQuery').val(defaultQuery);

    $('#runQuery').click(function() {
        $('#resultContainer').empty();
        $('#loading').show();
        doQuery($('#sparqlQuery').val(), displayResult);
    });
});
