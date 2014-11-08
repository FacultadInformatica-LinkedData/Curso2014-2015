#!/usr/bin/env bash

apt-get update
apt-get install -y git automake libtool flex bison gperf gawk make libssl-dev openssl
cd /vagrant
git clone -v https://github.com/openlink/virtuoso-opensource -b stable/7 virtuoso-git
cd virtuoso-git
./autogen.sh
./configure
make
sudo make install
cd /home/vagrant

sudo cp /vagrant/provisioning/launch_virtuoso.sh ./
sudo chmod +x ./launch_virtuoso.sh
./launch_virtuoso.sh

sleep 20
cd /usr/local/virtuoso-opensource/bin
sudo ./isql 1111 dba dba
echo "GRANT ALL PRIVILEGES TO \"SPARQL\";"
echo "exit;"

cd /vagrant
sudo rm -rf virtuoso-git
cd /home/vagrant
apt-get install -y nginx
sudo cp /vagrant/provisioning/default_php /etc/nginx/sites-available/default
sudo rm -rf /usr/share/nginx/www
sudo ln -s /vagrant/www /usr/share/nginx/
service nginx restart
