Democrapedia - Tool Documentation - uDig
========================================

## Summary

[uDig][udig] is an extension of the [Eclipse][eclipse] platform, such
that provides some geoinformatics functionality via the open source
[GeoTools][geotools] toolkit.

### Availability

* [uDig - Downloads][udig-dl]

## Installation

### Installation on Debian Linux Installations

**Notes: 64-bit Architectures (Debian Linux)**

* The uDig _[download now][udig-dl-now]_ link may indicate as though
  there is no Linux `x86_64` edition available. However, at the regular
  _[downloads][udig-dl]_ page, there is a Linux `x86_64` edition
  listed ([1.4.0][udig-linux-x86-64-1.4.0])
* The following prerequisite packages _must_ be installed:
    * `java-runtime` (virtual package) (see also:
      [Debian policy for Java][debian-java-policy], shell command
      `update-java-alternatives`, Debian
      [`java-common`][deb-java-common] package, and
      for Java package distribution on Debian, shell command `make-jpkg`)
* The following prerequisite packages _should_ be installed:
    * [`libjpeg62`][deb-libjpeg62] (nb. this is a dependency of the
      [`lsb-desktop`][deb-lsb-desktop] package)
* Notice [libsoup2.4.1-: Problem with Eclipse and java][soup-eclipse-bug]
 (cf. Eclipse platform version). For affected platforms, there may be
 a workaround developed in regards to X.org or alternately VNC,
 towards using a second machine on a network, such that the second
 machine would not be running a platform affected with the issue. uDig
 could be installed on the second machine, along with either _an X
 server and an SSH server_ or alternately VNC, then used over the
 network.

[udig]: http://udig.refractions.net/
[geotools]: http://geotools.org/
[eclipse]: http://www.eclipse.org/
[udig-dl-now]: http://udig.refractions.net/download/latest.php
[udig-dl]: http://udig.refractions.net/download/
[udig-linux-x86-64-1.4.0]: http://udig.refractions.net/files/downloads/udig-1.4.0.linux.gtk.x86_64.zip
[java-policy]: http://www.debian.org/doc/packaging-manuals/java-policy/
[debian-java-policy]: http://www.debian.org/doc/packaging-manuals/java-policy/
[deb-java-common]: http://packages.debian.org/java-common
[deb-libjpeg62]: http://packages.debian.org/libjpeg62
[deb-lsb-desktop]: http://packages.debian.org/lsb-desktop
[soup-eclipse-bug]: http://bugs.debian.org/cgi-bin/bugreport.cgi?bug=705420
