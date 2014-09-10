Course 2014-2015
================

This is the repository that we will use for our collaborative work and for uploading all the assignments for course 2014-2015

As I have received several questions about the best way to work with GitHub (and git in general), this is the normal process that you will have to follow:

* Fork the main repository into your own account (this will generate a new repository in your GitHub account). This is done only once during the course. 
* If you had already forked the repository some time ago, you may want to sync your repository to the latest version that is now available. This is done by [configuring the remote for a fork](https://help.github.com/articles/configuring-a-remote-for-a-fork) and [syncing your fork](https://help.github.com/articles/syncing-a-fork). Basically, you have to:
 * Establish remote: 
 
        git remote add upstream https://github.com/FacultadInformatica-LinkedData/Curso2014-2015

 * Fetch any changes to it: 
 
        git fetch upstream
 
 * Checkout the local master branch of your fork: 
 
        git checkout master
 
 * Merge changes from the remote into your master branch: 
 
        git merge upstream/master

* Make any changes to your repository, according to the specific assignment (for instance, in the first assignment, you will have to update the corresponding CSV file)
* Commit your changes into your local repository
* Push your changes to your online repository
* Make a pull request, so that we can check your changes and accept them into the master of the general repository. If everything is ok, your changes will be incorporated into the main repository. If not, you will receive a message of why not.

**Assignment 1**. Please fill in a line with a dataset description at folder [Assignment 1](./Assignment1/DatasetDescriptions.csv) and make a pull request

**Assignment 2**. Please add your RDF files in your preferred format (e.g., Turtle, RDF/XML, JSON-LD) at folder [Assignment 2](./Assignment2) in your personal forked repository and make a pull request
