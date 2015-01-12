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
