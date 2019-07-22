package br.com.digitoglobal.projeto.util.arquivo.upload;

import javax.servlet.http.Part;
import java.util.List;

public class MultipartItem {

    private Part part;
    private String name;
    private List<Object> values;
    private Integer size;
    private MultipartType multipartType;

    public MultipartItem() {}

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public MultipartType getMultipartType() {
        return multipartType;
    }

    public void setMultipartType(MultipartType multipartType) {
        this.multipartType = multipartType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultipartItem)) return false;

        MultipartItem that = (MultipartItem) o;

        if (part != null ? !part.equals(that.part) : that.part != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = part != null ? part.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MultipartItem{" +
                "part=" + part +
                ", name='" + name + '\'' +
                ", values=" + values +
                ", size=" + size +
                '}';
    }

}
