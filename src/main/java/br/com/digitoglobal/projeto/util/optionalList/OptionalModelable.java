package br.com.digitoglobal.projeto.util.optionalList;

public interface OptionalModelable {

    boolean isNotSelected();
    boolean isSelected();
    void setSelected(boolean selected);
    void select();
    void unSelect();
    void removeTemporarily();
    void addedTemporarily();
    void reset(); // all false
    boolean isTemporarilyRemoved();
    void setTemporarilyRemoved(boolean temporarilyRemoved);
    boolean isTemporarilyAdded();
    void setTemporarilyAdded(boolean temporarilyAdded);
    void setOptionalModel(OptionalModel optionalModel);

}
