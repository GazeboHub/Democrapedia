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
