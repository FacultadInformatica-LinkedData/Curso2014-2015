#!/bin/bash
#
# Copyright (C) 2014, 2015 Alejandro Barahona Alvarez, Sergio Conde Gomez, Sandra Saez Raspeño
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, version 3.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#

max_mem=$((`cat /proc/meminfo | grep "MemTotal:" | awk '{print $2}'` / 1024 * 75/100))

export JAVA_OPTS="-Djava.awt.headless=true -Dfile.encoding=UTF-8 -server -Xms${max_mem}m -Xmx${max_mem}m -XX:NewSize=256m -XX:MaxNewSize=256m -XX:PermSize=256m  -XX:MaxPermSize=256m -XX:+DisableExplicitGC"

/opt/tomcat/bin/startup.sh > /tmp/tomcat.log 2>&1
