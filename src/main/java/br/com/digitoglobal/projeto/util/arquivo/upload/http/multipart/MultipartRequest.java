/*
 * net/balusc/http/multipart/MultipartRequest.java
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

import br.com.digitoglobal.projeto.util.arquivo.upload.MultipartItem;
import br.com.digitoglobal.projeto.util.arquivo.upload.MultipartType;

/**
 * This class represents a multipart request. It not only abstracts the <code>{@link Part}</code>
 * away, but it also provides direct access to the <code>{@link MultipartMap}</code>, so that one
 * can get the uploaded files out of it.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2009/12/uploading-files-in-servlet-30.html
 */
public class MultipartRequest extends HttpServletRequestWrapper {

    private MultipartMap multipartMap;
    private Map<String, String[]> parameterMap;

    public MultipartRequest(HttpServletRequest request) throws ServletException, IOException {
        super(request);
        this.multipartMap = new MultipartMap(request);
        this.parameterMap = new HashMap<>(super.getRequest().getParameterMap());
    }

    public Map<String, MultipartItem> getMultPartMap() {
        return multipartMap;
    }

    public MultipartItem getMultipartItem(String name) {
        return multipartMap.get(name);
    }

    public Part getPart(String name) {
        MultipartItem multipartItem = getMultipartItem(name);
        if (multipartItem != null) return multipartItem.getPart();
        return null;
    }

    public List<MultipartItem> getMultipartItens() {
        List<MultipartItem> itens = new ArrayList<>();
        if (multipartMap != null) {
            for (MultipartItem multipartItem : multipartMap.values()) {
                itens.add(multipartItem);
            }
        }
        return itens;
    }

    public List<MultipartItem> getMultipartFiles() {
        List<MultipartItem> itens = new ArrayList<>();
        if (multipartMap != null) {
            for (MultipartItem multipartItem : multipartMap.values()) {
                if (multipartItem.getMultipartType() == MultipartType.FILE) {
                    itens.add(multipartItem);
                }
            }
        }
        return itens;
    }

    public void setParameterMap(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public void addParameterMapValues(Map<String, String[]> parameterMap) {
        for (String key : parameterMap.keySet()) {
            addParameterMapValues(key, parameterMap.get(key));
        }
    }

    public void addParameterMapValues(String key, String[] values) {
        Map<String, String[]> map = getParameterMap();
        if (map == null) map = new HashMap<>();
        map.put(key, values);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        return parameterMap.get(name);
    }

    @Override
    public String getParameter(String name) {
        String[] params = getParameterValues(name);
        return params != null && params.length > 0 ? params[0] : null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(parameterMap.keySet());
    }

}