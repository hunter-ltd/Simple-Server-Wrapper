package com.hunterltd.ssw.curse.data;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * A CurseForge file wrapper class based upon the manifest.json file
 */
@SuppressWarnings("unchecked")
public class CurseManifestFileEntry extends JSONObject {
    /**
     * Takes all key/value pairs from the given object and puts them into the Manifest entry
     *
     * @param obj JSON data
     */
    public CurseManifestFileEntry(JSONObject obj) {
        this.putAll(obj);
    }

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://addons-ecs.forgesvc.net/api/v2/addon/310806")
                .request(MediaType.TEXT_PLAIN_TYPE)
                .get();

        System.out.println("status: " + response.getStatus());
        System.out.println("headers: " + response.getHeaders());
        System.out.println("body:" + response.readEntity(String.class));
        System.out.println("Done!");

        FileDialog fd = new FileDialog((Dialog) null, "Choose ZIP", FileDialog.LOAD);
        fd.setVisible(true);
        try {
            ZipFile zip = new ZipFile(String.valueOf(Paths.get(fd.getDirectory(), fd.getFile())));
            zip.extractAll(String.valueOf(Paths.get(fd.getDirectory(), FilenameUtils.getBaseName(fd.getFile()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return ID for the project in the CurseForge database
     */
    public int getProjectID() {
        // Sometimes the JSON parser reads as an int, other times as a long. Rather annoying
        try {
            return (int) this.get("projectID");
        } catch (ClassCastException e) {
            return ((Long) this.get("projectID")).intValue();
        }
    }

    /**
     * @return ID for the file in the CurseForge database
     */
    public int getFileID() {
        try {
            return (int) this.get("fileID");
        } catch (ClassCastException e) {
            return ((Long) this.get("fileID")).intValue();
        }
    }
}
