package com.hunterltd.ssw.Curse.data;

import com.dablenparty.jsondata.UserDataObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class Manifest extends JSONObject {
    private final File manifestFile;

    public Manifest(File manifestFile) {
        this.manifestFile = manifestFile;
    }

    public String getType() {
        return (String) this.get("manifestType");
    }

    public String getName() {
        return (String) this.get("name");
    }

    public void load() throws IOException, ParseException {
        UserDataObject.readData(manifestFile, this);
    }

    public File getFile() {
        return manifestFile;
    }
}
