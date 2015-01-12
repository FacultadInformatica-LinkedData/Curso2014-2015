function replaceUri(uri) {
    return uri.replace("http://www.semanticweb.org/group08/", "/pubby/");
}

self.addEventListener('message', function(e) {
    var content = '';

    e.data.results.bindings.forEach(function(b) {
        content += '<tr>';
        e.data.head.vars.forEach(function(v) {
            if (b[v].type == "uri") {
                content += '<td><a href="' + replaceUri(b[v].value) + '">' + b[v].value + '</a></td>';
            } else {
                content += '<td>' + b[v].value + '</td>';
            }
        });
        content += '</tr>';
    });

    self.postMessage(content);
    self.close();
});
