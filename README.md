:bangbang: The Lunii firmware has been updated this summer, initial support is avaible with [version > 0.0.3 (beta)](https://github.com/marian-m12l/studio/releases). You can follow the progress on issue [#122](https://github.com/marian-m12l/studio/issues/122)
:---:

[![Release](https://img.shields.io/github/v/release/marian-m12l/studio)](https://github.com/marian-m12l/studio/releases/latest)
[![Gitter](https://badges.gitter.im/STUdio-Story-Teller-Unleashed/general.svg)](https://gitter.im/STUdio-Story-Teller-Unleashed/general?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

# STUdio - Story Teller Unleashed


[Instruction en Français](README_fr.md)

A set of tools to read, create and transfer story packs from and to the Lunii\* story teller device.


## DISCLAIMER

> **USE AT YOUR OWN RISK:** This software relies on reverse engineering research, which is limited to gathering the information necessary to ensure interoperability with the Lunii\* story teller device, and does not distribute any protected content.
> The software is still in an early stage of development, and as such has not yet been thoroughly tested. In particular, it has only been used on a very small number of devices, and may brick your device.

> \* Lunii is a registered trademark of Lunii SAS. I am (and this work is) in no way affiliated with Lunii SAS.


## Installation

### Prerequisite

To run the application:
* [Java JDK 11+](https://www.oracle.com/java/technologies/javase-downloads.html)
* On Windows, this application requires the _libusb_ driver to be installed. The easiest way to achieve this is to have
the official Luniistore\* software installed.

### Quick Install

* Download and Unzip STUdio ([check the latest release](https://github.com/marian-m12l/studio/releases/latest) (alternativaly you can Build from the code source, see below)
* **For firmware 2.x only:** configure the mount point of your device in the launch script using `FS_MOUNTPOINT`.
* Run the launcher script: either `studio-linux.sh`, `studio-macos.sh` or `studio-windows.bat` depending on your platform. You may need to make them executable first.
If run in a terminal, it should display some logs, ending with `INFOS: Succeeded in deploying verticle`.
* If it does not open automatically, open a browser and type the url `http://localhost:8080` to load the web UI.

Note: aavoid running the script as superuser/administrator, as this may create permissions issues.

#### Display the official pack metadata (optional)

By default, STUdio displays question marks and numeric identifiers to represent the official story packs already present on the Lunii. Here are the steps to display the real information. This is optional for creating and uploading your own stories.

1) Copy from the official Luniistore\* software the requiere assets;

  * On Linux run `cp ~/.local/share/Luniitheque/* ~/.local/share/Luniitheque`
  * On macOS run `cp ~/Library/Application\ Support/Luniitheque/* ~/.studio`
  * On Windows run `cp %UserProfile%\AppData\Roaming\Luniitheque/* %UserProfile%\.studio`

2) Fetch the story pack metadata

Start the official Luniistore\* software to get a fresh authentication token (valid for one hour).

Open `.local.properties` in a text editor located in:
  * On Linux `~/.local/share/Luniitheque/.local.properties`
  * On macOS `~/Library/Application\ Support/Luniitheque/.local.properties`
  * On Windows `%UserProfile%\AppData\Roaming\Luniitheque/.local.properties`

Mote the token value :
  * If your are logged in on the Luniistore\* software, the token is located on the `tokens` property, `tokens.access_tokens.data.server` attribute
  * If your are not logged in on the Luniistore\* software, the token is located on the `token` property, `server` attribute
  
Using curl, query `https://server-data-prod.lunii.com/v2/packs` with the following HTTP headers:
  * `Accept: application/json`
  * `Content-Type: application/json; charset=UTF-8`
  * `X-AUTH-TOKEN: value_of_your_jwt_token`
  
and save the result as `$DOT_STUDIO/db/official.json`.

Here is an example for curl command to retrieve the database (replace VALUE_OF_YOUR_TOKEN with your token):
```
curl -o ~/.studio/db/official.json -v -X GET -H 'Accept: application/json' -H 'Content-Type: application/json; charset=UTF-8' -H 'X-AUTH-TOKEN: VALUE_OF_YOUR_TOKEN' https://server-data-prod.lunii.com/v2/packs
```

## Usage of the application

The web UI is made of two screens:

* The pack library, to manage your local library and transfer to / from your device
* The pack editor, to create or edit a story pack

#### Local library and transfer to the device

The pack library screen always shows the story packs in your local library. The packs may be either in binary format (the official format, understood by the device) or archive format (the unofficial format, used for story pack creation and edition).

When the device is plugged, the the left side pane appear, showing the device metadata and story packs. Dragging and dropping a pack from or to the device will initiate the transfer.

#### Pack editor

The pack editor screen show the current story pack being edited. By default, it shows a sample story pack intended as a model of correct usage.

A pack is composed of a few metadata and the diagram describing the various steps in the story:

* Stage nodes are used to display an image and/or play a sound
* Action nodes are used to transition from one stage to the next, and to manage the available options

The editor supports several file formats for audio and image assets.

##### Images

Image files may use the following formats (formats marked with asterisks are automatically converted when transferring
to the device) :
* PNG\*\*
* JPEG\*\*
* BMP (24-bits)

Image dimensions must be 320x240. Images may use colors, even though some colors may not render accurately due to
the screen being behind the plastic cover. Bear in mind that the color of the cover may change, as seen with the
recently released Christmas edition.

##### Audio

Audio files may use the following formats (formats marked with asterisks are automatically converted when transferring
to the device) :
* MP3\*\*
* OGG/Vorbis \*\*
* WAVE (signed 16-bits, mono, 32000 Hz)

MP3 and OGG files are expected to be sampled at 44100Hz.

### [Experimental] Seeing unofficial metadata in Luniistore\* application / Loading official database from Luniistore\* application

These **experimental** features allow:
* to display correct(-ish) metadata for unofficial story packs (stored on the device) in the official Luniistore\* application.
* to automatically load / refresh the official metadata database when running the official Luniistore\* application.

To enable these features, locate the configuration file `Luniistore.cfg`:
  * On Linux, in `/opt/Luniistore/app`
  * On macOS, in `/Applications/Luniistore.app/Contents/Java`
  * On Windows, in `%ProgramFiles%\Luniistore\app`

Then add this line under the `[JVMOptions]` section (replace `$DOT_STUDIO` with the actual path)(use forward slashes
even on Windows):

```
-javaagent:$DOT_STUDIO/agent/studio-agent.jar
```


## Devloppement

### Build from the code source

* Install [Maven 3+](https://maven.apache.org/index.html)
* Clone this repository `git clone git@github.com:marian-m12l/studio.git`
* Build the application `mvn clean install`

This will produce the **distribution archive** in `web-ui/target/`.


LICENSE
-------

This project is licensed under the terms of the **Mozilla Public License 2.0**. The terms of the license are in
the `LICENSE` file.

The `vorbis-java` library, as well as the `VorbisEncoder` class are licensed by the Xiph.org Foundation. The terms of
the license can found in the `LICENSE.vorbis-java` file.

The `com.jhlabs.image` package is licensed by Jerry Huxtable under the terms of the Apache License 2.0. The terms of
the license can be found in the `LICENSE.jhlabs` file.
