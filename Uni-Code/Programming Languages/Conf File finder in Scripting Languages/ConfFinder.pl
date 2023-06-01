#!/usr/bin/perl

use strict;
use warnings;
use File::Find;

find(sub{
	if(-f and /\.conf$/){
		print ".$File::Find::name\n";}
	},"/");
