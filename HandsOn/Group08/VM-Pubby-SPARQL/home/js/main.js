var defaultQuery = "PREFIX base: <http://www.semanticweb.org/group08/>\nPREFIX prop: <http://www.semanticweb.org/group08#>\n\nSELECT * {\n  ?a ?b ?c\n} LIMIT 100";

$(document).ready(function() {
    $("#sparqlQuery").val(defaultQuery);

    $("#runQuery").click(function() {
	doQuery($("#sparqlQuery").val());
    });
});

function doQuery(query) {
    $.ajax({
        url: '/openrdf-sesame/repositories/group08',
        dataType: 'json',
        beforeSend: function(xhr){
            xhr.setRequestHeader("Accept", "application/sparql-results+json");
        },
        data: {
            Accept: 'application/sparql-results+json',
            queryLn: 'SPARQL',
            query: query
        },
        timeout: 2000,
        success: displayResult,
        error: function(xhr, textStatus, errorThrown) {
            if (xhr.status == 400)
                alert(xhr.responseText);
            else if (xhr.statusText == "timeout")
                alert("Timeout, try using LIMIT");
            console.log(xhr);
            console.log("Status: " + xhr.status + "\n\nResponse:\n" + xhr.responseText);
        }
    });
}

function displayResult(data) {
    var header = $('#result thead').append('<tr/>');
    $.each(data.head.vars, function(key,value) {
        header.append("<th>" + value + "</th>");
    });

    $.each(data.results.bindings, function(index, bs) {
        var row = $('<tr/>');
        $.each(data.head.vars, function(key, varname) {
            row.append("<td>" + bs[varname].value + "</td>");
        });
        $("#result tbody").after(row);
    });
}
