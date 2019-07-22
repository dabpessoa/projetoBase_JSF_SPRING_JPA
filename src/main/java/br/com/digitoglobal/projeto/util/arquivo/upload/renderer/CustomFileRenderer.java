/*
 * net/balusc/jsf/renderer/html/FileRenderer.java
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

package br.com.digitoglobal.projeto.util.arquivo.upload.renderer;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.sun.faces.renderkit.html_basic.FileRenderer;

import br.com.digitoglobal.projeto.util.arquivo.upload.ArquivoUpload;
import br.com.digitoglobal.projeto.util.arquivo.upload.MultipartItem;
import br.com.digitoglobal.projeto.util.arquivo.upload.http.multipart.MultipartRequest;

/**
 * Faces renderer for <code><h:inputFile></h:inputFile></code> JSF field.
 *
 * @link http://balusc.blogspot.com/2009/12/uploading-files-with-jsf-20-and-servlet.html
 */
@FacesRenderer(componentFamily = "javax.faces.Input", rendererType = "javax.faces.File")
public class CustomFileRenderer extends FileRenderer {

    private static final String REQUEST_METHOD_POST = "POST";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/";

    @Override
    public void decode(FacesContext context, UIComponent component) {
        rendererParamsNotNull(context, component);
        if (!shouldDecode(component)) {
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        if (isMultipartRequest(httpRequest)) {

            try {

                MultipartRequest multipartRequest = new MultipartRequest(httpRequest);

                List<MultipartItem> multipartItems = multipartRequest.getMultipartFiles();
                List<ArquivoUpload> arquivos = ArquivoUpload.parse(multipartItems);

                // If no file is specified, set empty String to trigger required="true" validator. JSF will
                // set it back to null afterwards. Although, that is the default, it's configureable by the
                // context init-param javax.faces.INTERPRET_EMPTY_STRING_SUBMITTED_VALUES_AS_NULL
                Object submittedValue = (arquivos != null && !arquivos.isEmpty()) ? arquivos : "";
                ((UIInput) component).setSubmittedValue(submittedValue);

            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            String message = "File upload component requires a form with an enctype of multipart/form-data";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
            context.addMessage(component.getClientId(context), facesMessage);
            System.out.println(message);
        }

    }

    /**
     * Returns true if the given request is a multipart request.
     * @param request The request to be checked.
     * @return True if the given request is a multipart request.
     */
    private static final boolean isMultipartRequest(HttpServletRequest request) {
        return REQUEST_METHOD_POST.equalsIgnoreCase(request.getMethod())
                && request.getContentType() != null
                && request.getContentType().toLowerCase().startsWith(CONTENT_TYPE_MULTIPART);
    }

}