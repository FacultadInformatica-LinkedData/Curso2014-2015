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

# URLs
tomcat_url="http://apache.rediris.es/tomcat/tomcat-7/v7.0.57/bin/apache-tomcat-7.0.57.tar.gz"
sesame_url="http://sourceforge.net/projects/sesame/files/Sesame%202/2.7.14/openrdf-sesame-2.7.14-sdk.tar.gz"
pubby_url="http://wifo5-03.informatik.uni-mannheim.de/pubby/download/pubby-0.3.3.zip"

# Configs
tomcat_user="group08"
tomcat_pass="linked-data"

# Paths
tomcat_pwd="/opt/tomcat"
sesame_pwd="/opt/sesame"

# Command run function
verbose=0

run() {
    echo -e "\t$@"
    if [ $verbose -eq 1 ]; then
	$@
    else
	$@ > /dev/null 2>&1
    fi

    if [ $? -ne 0 ]; then
	echo " !!! Failed, aborting..." 1>&2
	exit 1
    fi
}

# Create temp dir
echo "Creating temp dir..."
temp_dir="/vagrant/tmp"
run sudo rm -rf $temp_dir
run sudo mkdir -p $temp_dir

# Do things... stuff...
echo "Installing utils..."
run sudo apt-get update
run sudo apt-get install -y python-software-properties curl unzip htop

echo "Installing Oracle Java 7..."
run sudo add-apt-repository -y ppa:webupd8team/java
run sudo apt-get update

echo debconf shared/accepted-oracle-license-v1-1 select true | run sudo debconf-set-selections
run sudo apt-get install -y oracle-java7-installer
run sudo apt-get install -y oracle-java7-set-default

echo "Installing Tomcat..."
run sudo mkdir -p $tomcat_pwd
run sudo curl -L "$tomcat_url" -o $temp_dir/$(basename "$tomcat_url")
run sudo tar -xvf $temp_dir/$(basename "$tomcat_url") -C $tomcat_pwd --strip-components=1
run sudo rm $temp_dir/$(basename "$tomcat_url")

echo "Configuring Tomcat..."
echo "<?xml version='1.0' encoding='utf-8'?><tomcat-users><user username=\"$tomcat_user\" password=\"$tomcat_pass\" roles=\"manager-gui, manager-status, manager-script, manager-jmx\"/></tomcat-users>" | run sudo tee $tomcat_pwd/conf/tomcat-users.xml

cat << EOF | run sudo tee -a /etc/crontab
@reboot vagrant /vagrant/run-tomcat.sh
EOF

echo "Installing Sesame..."
run sudo mkdir -p $sesame_pwd
run sudo curl -L "$sesame_url" -o $temp_dir/$(basename "$sesame_url")
run sudo tar -xvf $temp_dir/$(basename "$sesame_url") -C $sesame_pwd --strip-components=1
run sudo rm $temp_dir/$(basename "$sesame_url")
cp $sesame_pwd/war/*.war $tomcat_pwd/webapps/

echo "Installing Pubby..."
run sudo curl -L "$pubby_url" -o $temp_dir/$(basename "$pubby_url")
run sudo unzip $temp_dir/$(basename "$pubby_url") -d $temp_dir/
run sudo cp -r $temp_dir/pubby-*/webapp/ $tomcat_pwd/webapps/pubby/

echo "Configuring Pubby..."
run sudo cp /vagrant/pubby-config.ttl $tomcat_pwd/webapps/pubby/WEB-INF/config.ttl

echo "Fix permissions..."
run sudo chown -R vagrant:vagrant $tomcat_pwd
run sudo chown -R vagrant:vagrant $sesame_pwd

echo "Starting Tomcat..."
run sudo -u vagrant /vagrant/run-tomcat.sh
sleep 60

echo "Data deploy (this may take a while)..."
run sudo -u vagrant /opt/sesame/bin/console.sh <<EOF
connect http://localhost:8080/openrdf-sesame.
create native.
group08
LinkedData UPM - 2014/2015 - Group 08

open group08.
load /linked-data/ont/SF_ont-updated.rdf.
load /linked-data/ttl/SF_311-updated.ttl.
load /linked-data/ttl/SF_Buildings.ttl.
load /linked-data/ttl/SF_Crime-updated.ttl.
load /linked-data/ttl/SF_GIS-updated.ttl.
load /linked-data/ttl/SF_Private_School-updated.ttl.
load /linked-data/ttl/SF_Public_Buildings.ttl.
load /linked-data/ttl/SF_Public_School-updated.ttl.
load /linked-data/ttl/SF_Transit_Stops-updated.ttl.
load /linked-data/ttl/void.ttl.
exit.
EOF

echo "Cleanup..."
run rm -rf $temp_dir
