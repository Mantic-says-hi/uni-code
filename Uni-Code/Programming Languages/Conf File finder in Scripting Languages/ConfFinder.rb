#!/usr/bin/ruby

Dir.chdir("/");
configFiles = Dir.glob("#{'.'}/**/*.conf", File::FNM_DOTMATCH);
puts configFiles;
