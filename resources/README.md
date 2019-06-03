# resources

Resources / files for various things. For more, find me on twitter - https://twitter.com/shellsharks

### burpconfig-*options(.json)
Burp config files that I use for assessments

### feeds(.md)
This is a list of subreddits, rss feeds and twitter accounts I think are interesting...

### feeds.opml.xml
List of my current RSS feeds in a format that can be imported into the RSS aggregator of your choice. (I recommend Feedly - https://feedly.com)

### nessus-weapon-inventory
This is a list of .nasl plugins which could be weaponized. Based on the ideas from https://www.shellntel.com/blog/2016/6/7/weaponizing-nessus.

`grep -rnw /opt/nessus/lib/nessus/plugins/ -e ".*Nessus was able.*" | cut -d ":" -f 1 > nessus-weapon-inventory`

### terminatorconfig
This is a config profile for Terminator. It contains a customized 'redteam' color profile.
* install terminator (https://gnometerminator.blogspot.com/p/introduction.html) - `apt-get install terminator`
* download terminatorconfig - `wget  https://raw.githubusercontent.com/shellsharks/resources/master/terminatorconfig`
* rename to "config" - `mv terminatorconfig config`
* place in ~/.config/terminator/ directory - `mv config ~/.config/terminator/`
