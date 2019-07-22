package br.com.digitoglobal.projeto.util.optionalList;

public class OptionalModel implements OptionalModelable {

    private boolean selected;
    private boolean temporarilyRemoved;
    private boolean temporarilyAdded;

    public OptionalModel() {this(false, false, false);}

    public OptionalModel(boolean selected) {
        this(selected, false, false);
    }

    public OptionalModel(boolean selected, boolean temporarilyAdded) {
        this(selected, temporarilyAdded, false);
    }

    public OptionalModel(boolean selected, boolean temporarilyAdded, boolean temporarilyRemoved) {
        init(selected, temporarilyAdded, temporarilyRemoved);
    }

    private void init(boolean selected, boolean temporarilyAdded, boolean temporarilyRemoved) {
        setSelected(selected);
        setTemporarilyAdded(temporarilyAdded);
        setTemporarilyRemoved(temporarilyRemoved);
    }

    @Override
    public void select() {
        setSelected(true);
    }

    @Override
    public void unSelect() {
        setSelected(false);
    }

    @Override
    public void removeTemporarily() {
        setTemporarilyRemoved(true);
    }

    @Override
    public void addedTemporarily() {
        setTemporarilyAdded(true);
    }

    @Override
    public void reset() {
        init(false, false, false);
        setSelected(false);
        setTemporarilyAdded(false);
        setTemporarilyRemoved(false);
    }

    @Override
    public void setOptionalModel(OptionalModel optionalModel) {
        init(optionalModel.selected, optionalModel.temporarilyAdded, optionalModel.temporarilyRemoved);
    }

    public boolean isNotSelected() {
        return !isSelected();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isTemporarilyRemoved() {
        return temporarilyRemoved;
    }

    public void setTemporarilyRemoved(boolean temporarilyRemoved) {
        this.temporarilyRemoved = temporarilyRemoved;
    }

    public boolean isTemporarilyAdded() {
        return temporarilyAdded;
    }

    public void setTemporarilyAdded(boolean temporarilyAdded) {
        this.temporarilyAdded = temporarilyAdded;
    }

    @Override
    public String toString() {
        return "OptionalModel{" +
                "selected=" + selected +
                ", temporarilyRemoved=" + temporarilyRemoved +
                ", temporarilyAdded=" + temporarilyAdded +
                '}';
    }

}
