#!/bin/bash
git pull
git fetch upstream
git merge upstream/master
git push