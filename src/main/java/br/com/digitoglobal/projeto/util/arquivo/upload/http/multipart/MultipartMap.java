/*
 * net/balusc/http/multipart/MultipartMap.java
 *
 * Copyright (C) 2009 BalusC
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package br.com.digitoglobal.projeto.util.arquivo.upload.http.multipart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import br.com.digitoglobal.projeto.util.arquivo.upload.MultipartItem;
import br.com.digitoglobal.projeto.util.arquivo.upload.MultipartType;

public class MultipartMap extends HashMap<String, MultipartItem> {

    private static final String ATTRIBUTE_NAME = "parts";
    private static final String CONTENT_DISPOSITION = "content-disposition";
    private static final String CONTENT_DISPOSITION_FILENAME = "filename";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    private String encoding;

    public MultipartMap(HttpServletRequest multipartRequest) throws IOException, ServletException {
        this(multipartRequest, null);
    }

    private MultipartMap (HttpServletRequest multipartRequest, Servlet servlet) throws IOException, ServletException {
        multipartRequest.setAttribute(ATTRIBUTE_NAME, this);

        this.encoding = multipartRequest.getCharacterEncoding();
        if (this.encoding == null) {
            multipartRequest.setCharacterEncoding(this.encoding = DEFAULT_ENCODING);
        }

        for (Part part : multipartRequest.getParts()) {
            MultipartItem multipartItem = null;

            String filename = getFilename(part);
            if (filename == null) {
                multipartItem = processTextPart(part);
            } else if (!filename.isEmpty()) {
                multipartItem = processFilePart(part, filename);
            }

            if (multipartItem != null && !containsKey(multipartItem.getName())) {
                put(multipartItem.getName(), multipartItem);
            }

        }
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Returns the filename from the content-disposition header of the given part.
     */
    private String getFilename(Part part) {
        for (String cd : part.getHeader(CONTENT_DISPOSITION).split(";")) {
            if (cd.trim().startsWith(CONTENT_DISPOSITION_FILENAME)) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Returns the text value of the given part.
     */
    private String getValue(Part part) throws IOException {
        String value = new BufferedReader(new InputStreamReader(part.getInputStream(), encoding)).readLine();
        return (value != null) ? value : ""; // Must be empty string according HTTP spec.
    }

    /**
     * Process given part as Text part.
     */
    private MultipartItem processTextPart(Part part) throws IOException {
        String name = part.getName();
        MultipartItem item = super.get(name);

        if (item == null) {

            // Not in parameter map yet, so add as new value.
            item = new MultipartItem();
            item.setPart(part);
            List<Object> values = new ArrayList<>();
            values.add(getValue(part));
            item.setValues(values);
            item.setName(name);
            item.setMultipartType(MultipartType.TEXT);

        } else {

            // Multiple field values, so add new value to existing array.
            if (item.getValues() != null) {
                item.getValues().add(getValue(part));
            }

        }

        return item;

    }

    /**
     * Process given part as File part which is to be saved in temp dir with the given filename.
     */
    private MultipartItem processFilePart(Part part, String filename) throws IOException {
        // First fix stupid MSIE behaviour (it passes full client side path along filename).
        filename = filename
            .substring(filename.lastIndexOf('/') + 1)
            .substring(filename.lastIndexOf('\\') + 1);

        MultipartItem item = super.get(part.getName());

        if (item == null) {

            item = new MultipartItem();
            item.setName(filename);
            item.setPart(part);
            item.setMultipartType(MultipartType.FILE);

            ArrayList<Object> values = new ArrayList<>();
            values.add(IOUtils.toByteArray(part.getInputStream()));
            try {part.getInputStream().close();} catch (Exception e){/*do nothing*/};
            item.setValues(values);

        }

        return item;
    }

}
