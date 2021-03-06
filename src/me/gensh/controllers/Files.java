package me.gensh.controllers;

import me.gensh.core.request.RequestHeader;
import me.gensh.core.Config;
import me.gensh.core.response.Controller;
import me.gensh.core.response.ResponseHeader;
import me.gensh.core.response.session.HttpSession;
import me.gensh.core.response.error.NotFoundError;
import me.gensh.core.utils.json.JSONObject;

import java.io.File;
import java.io.OutputStream;

/**
 * Created by 根深 on 2016/4/27.
 */
@Deprecated
public class Files extends Controller {
    public Files(OutputStream os, RequestHeader header, HttpSession session) {
        super(os, header, session);
    }

    public void indexAction() {
        render("files/index.html", new JSONObject());
    }

    public void uploadAction() {
        String path = Config.TempPath + getPostData().getString("file"); //上传文件保存路径
        File f = new File(path);
        if (!f.exists()) { // file not exist
            new NotFoundError(bos);
            return;
        }
        JSONObject data = new JSONObject();
        data.put("title", "File Upload");
        data.put("file_size", f.length());
        data.put("file_path", f.getAbsolutePath());
        data.put("file_name", f.getName());
        data.put("name", getPostData().getString("name"));
        render("files/upload.html", data);
    }

    public void downloadAction() {
        String video = "/public/media/big_buck_bunny.mp4";
        String audio = "/public/media/music.mp4";
        JSONObject data = new JSONObject();
        data.put("title", "File Download");
        data.put("video", video);
        data.put("audio", audio);
        render("files/download.html", data);
    }

    public void down_loadAction() {
        String path = Config.BasePath + getParams().getString("path");
        File f = new File(path);
        if (!f.exists()) {
            notFound();
            return;
        }
        responseHead.setHeadValue(ResponseHeader.Content_Length, f.length() + "");
        responseHead.setHeadValue(ResponseHeader.Content_Transfer_Encoding, "binary");
        responseHead.setHeadValue(ResponseHeader.Content_Type, "application/octet-stream");
        outFile(path);
    }


}
