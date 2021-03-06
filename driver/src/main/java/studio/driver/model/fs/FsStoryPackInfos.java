/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package studio.driver.model.fs;

import java.util.UUID;

public class FsStoryPackInfos {

    private UUID uuid;
    private String folderName;
    private short version;
    private long sizeInBytes;

    public FsStoryPackInfos() {
    }

    public FsStoryPackInfos(UUID uuid, String folderName, short version, long sizeInBytes) {
        this.uuid = uuid;
        this.folderName = folderName;
        this.version = version;
        this.sizeInBytes = sizeInBytes;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public long getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    @Override
    public String toString() {
        return "FsStoryPackInfos{" +
                "uuid=" + uuid +
                ", folderName=" + folderName +
                ", version=" + version +
                ", sizeInBytes=" + sizeInBytes +
                '}';
    }
}
